package ua.nure.efimov.summarytask4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.efimov.summarytask4.command.Command;
import ua.nure.efimov.summarytask4.command.CommandContainer;
import ua.nure.efimov.summarytask4.command.EntryPath;
import ua.nure.efimov.summarytask4.constants.ComonConstants;
import ua.nure.efimov.summarytask4.constants.PathConstants;
import ua.nure.efimov.summarytask4.exception.ApplicationException;

/**
 * Main controller of web application. Responses for all commands.
 * 
 * Forward to pages depends on command.
 * 
 * @author Alexandr Efimov
 *
 */
@WebServlet(name = "Controller", description = "Main controller of web application. Responses for all commands.", urlPatterns = {
		"/Controller" })
public class MainController extends HttpServlet {

	private static final long serialVersionUID = 8130528958801885669L;

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(MainController.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	/**
	 * Main method of controller. Responses for forwarding to different pages
	 * depends on commands.
	 * 
	 * @param request
	 *            is {@link HttpServletRequest} object
	 * @param response
	 *            is {@link HttpServletResponse} object
	 * @throws IOException
	 *             if some I/O exception of some sort has occurred
	 * @throws ServletException
	 *             if servlet encounters some difficulty
	 */
	private void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		LOGGER.debug("Controller starts");

		// extract command name from the request
		String commandName = request.getParameter("command");
		LOGGER.trace("Request parameter: command --> " + commandName);

		// obtain command object by its name
		Command command = CommandContainer.get(commandName);
		LOGGER.info("\nObtained command --> " + command);

		// execute command and get forward address
		String forward = PathConstants.PAGE_ERROR_PAGE.getValue();
		EntryPath executeResult = null;
		try {
			executeResult = command.execute(request, response);
			forward = executeResult.getPath();
		} catch (ApplicationException ex) {
			LOGGER.error(ex.getMessage(), ex);
			request.setAttribute("errorMessage", ex.getMessage());
		}

		LOGGER.debug("Controller finished, forward address --> " + forward);
		/**
		 * Forward or redirect to page.
		 */
		// check if not authoriz user try to call controller, if so do nothing
		if (request.getAttribute(ComonConstants.NOT_AUTHORIZATED.getValue()) == null) {

			if (executeResult.isRediect()) {
				response.sendRedirect(forward);
			} else {
				request.getRequestDispatcher(forward).forward(request, response);
			}
		}

	}
}
