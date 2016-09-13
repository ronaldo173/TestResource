package ua.nure.efimov.summarytask4.filter;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.efimov.summarytask4.constants.ComonConstants;
import ua.nure.efimov.summarytask4.constants.PathConstants;
import ua.nure.efimov.summarytask4.entity.User;
import ua.nure.efimov.summarytask4.exception.BusinessException;
import ua.nure.efimov.summarytask4.services.UserService;

/**
 * Servlet Filter implementation class UserInfoFilter
 */
@WebFilter(description = "Checks containing user detail information if user authorizated", urlPatterns = { "/*" })
public class UserInfoFilter implements Filter {
	private boolean isBanned;

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(UserInfoFilter.class);

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		LOGGER.trace("Filter init.");
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		LOGGER.trace("Filter destroyed.");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String reqPath = request.getRequestURI();
		String contextPath = request.getContextPath();

		/*
		 * If not auth user try to use controller -> redirect to login page. If
		 * registration afford to register not auth user.
		 */
		String commandName = request.getParameter(ComonConstants.COMMAND.getValue());
		if (reqPath.contains(PathConstants.CONTROLLER_REL_PATH.getValue())) {
			if (commandName != null && commandName.equals(ComonConstants.COMMAND_REGISTRATION.getValue())) {
				LOGGER.info("Registration can be done!");
			} else if (request.getUserPrincipal() == null) {
				LOGGER.info("Not authorizated user try yo use controller! Redirect to login page.");
				req.setAttribute(ComonConstants.NOT_AUTHORIZATED.getValue(), true);
				HttpServletResponse respons = (HttpServletResponse) resp;
				respons.sendRedirect(contextPath);
			}
		}

		/**
		 * Ingore all resources by path.
		 */
		if (reqPath.startsWith(contextPath + PathConstants.RESOURCES_REL_PATH.getValue())) {
			LOGGER.trace("Ignore resources URL");
			chain.doFilter(req, resp); // Just continue chain.
		} else {
			/**
			 * Put to session USER object if it's not already in session
			 */
			Principal userPrincipal = request.getUserPrincipal();
			if (userPrincipal != null) {
				checkIsUserObjectInSession(request, response, userPrincipal);
			}

			if (isBanned) {
				if (!request.getRequestURI().contains("registration")) {
					boolean isRegistration = commandName != null
							&& commandName.equals(ComonConstants.COMMAND_REGISTRATION.getValue());
					if (!isRegistration) {

						String errorMessasge = "You do not have permission to enter! YOU WAS BANNED";
						request.setAttribute("errorMessage", errorMessasge);
						request.getRequestDispatcher(PathConstants.ERROR.getValue()).forward(req, resp);
						request.getSession().invalidate();
						return;
					}
				}

			}
			// pass the request along the filter chain
			chain.doFilter(req, resp);
		}

	}

	/**
	 * Check if user object with info about current user in session. If not ->
	 * get info from db and put to the session.
	 * 
	 * @param response
	 * 
	 * @param session
	 *            is current {@link HttpSession}
	 * @param userPrincipal
	 *            is obj {@link Principal}
	 * @throws IOException
	 */
	private void checkIsUserObjectInSession(HttpServletRequest request, HttpServletResponse response,
			Principal userPrincipal) throws IOException {
		LOGGER.debug("Check authorization, login: " + userPrincipal.getName());

		boolean isInSession = request.getSession().getAttribute(ComonConstants.USER_INFO_OBJECT.getValue()) != null;
		if (isInSession) {
			LOGGER.debug("User object already in the session");
		} else {
			LOGGER.debug("User object IS NOT in the session");
			/**
			 * Get user object from db and put to the session.
			 */

			UserService userService = new UserService();
			try {
				User userByLogin = userService.getUserByLogin(userPrincipal.getName());

				/**
				 * CHECK IF USER BANNED, YES -> INVALIDATE SESSION.
				 */
				if (userByLogin.isBanned()) {
					LOGGER.info("USER IS BANNED! ACCESS REFUSED");
					// TODO
					this.isBanned = true;
				} else {
					this.isBanned = false;
					LOGGER.info("Put to session user: " + userByLogin);
					request.getSession().setAttribute(ComonConstants.USER_INFO_OBJECT.getValue(), userByLogin);
				}

			} catch (BusinessException e) {
				LOGGER.error(e);
			}
		}

	}

}
