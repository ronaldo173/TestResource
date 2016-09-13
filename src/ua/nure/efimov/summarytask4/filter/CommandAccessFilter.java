package ua.nure.efimov.summarytask4.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.efimov.summarytask4.constants.ComonConstants;
import ua.nure.efimov.summarytask4.constants.PathConstants;
import ua.nure.efimov.summarytask4.constants.RolesNames;
import ua.nure.efimov.summarytask4.entity.Role;
import ua.nure.efimov.summarytask4.entity.User;

/**
 * Security filter.
 * 
 * @author Alexandr Efimov
 *
 */
public class CommandAccessFilter implements Filter {

	private static final Logger LOGGER = Logger.getLogger(CommandAccessFilter.class);

	// commands access
	private Map<String, List<String>> accessMap = new HashMap<>();

	public void destroy() {
		LOGGER.trace("Filter destruction starts");
		LOGGER.trace("Filter destruction finished");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		LOGGER.debug("Filter starts");

		if (accessAllowed(request)) {
			LOGGER.debug("Filter finished");
			chain.doFilter(request, response);
		} else {
			String errorMessasge = "You do not have permission to access the requested resource";
			request.setAttribute("errorMessage", errorMessasge);
			LOGGER.trace("Set the request attribute: errorMessage --> " + errorMessasge);

			request.getRequestDispatcher(PathConstants.ERROR.getValue()).forward(request, response);
			return;
		}
	}

	private boolean accessAllowed(ServletRequest request) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String commandName = request.getParameter("command");
		if (commandName == null || commandName.isEmpty()) {
			return false;
		}

		HttpSession session = httpRequest.getSession(false);
		if (session == null) {
			return false;
		}

		User user = (User) session.getAttribute(ComonConstants.USER_INFO_OBJECT.getValue());
		if (user == null) {
			return true;
		}
		List<Role> roles = user.getRoles();
		if (roles == null) {
			return false;
		}

		boolean isAdmin = false;
		List<String> listForbidenCommands = null;

		for (Role role : roles) {
			listForbidenCommands = accessMap.get(role.getRoleName());
			if (listForbidenCommands != null) {
				isAdmin = true;
			}
		}
		if (isAdmin) {
			return true;
		} else {
			listForbidenCommands = accessMap.get(RolesNames.ADMIN.getValue());
			boolean isAllowedCommand = !listForbidenCommands.contains(commandName);
			if (!isAllowedCommand) {
				LOGGER.info("FORBIDEN COMMAND for this user");
			} else {
				LOGGER.info("COMMAND allowed!");
			}
			return isAllowedCommand;
		}

	}

	public void init(FilterConfig fConfig) throws ServletException {
		LOGGER.trace("Filter initialization starts");

		accessMap.put(RolesNames.ADMIN.getValue(), asList(fConfig.getInitParameter("admin")));
		LOGGER.trace("Access map --> " + accessMap);
		LOGGER.trace("Filter initialization finished");
	}

	/**
	 * Extracts parameter values from string.
	 * 
	 * @param str
	 *            parameter values string.
	 * @return list of parameter values.
	 */
	private List<String> asList(String str) {
		List<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(str);
		while (st.hasMoreTokens()) {
			list.add(st.nextToken());
		}
		return list;
	}

}