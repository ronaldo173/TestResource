package ua.nure.efimov.summarytask4.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.efimov.summarytask4.constants.PathConstants;

/**
 * If no one command from all doesn't suits, this is used. Forward to error
 * page.
 * 
 * @author Alexandr Efimov
 *
 */
public class NoSuchCommand extends Command {
	private static final long serialVersionUID = 2787866692577821592L;

	private static final Logger LOGGER = Logger.getLogger(NoSuchCommand.class);

	@Override
	public EntryPath execute(HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("Command starts");

		String errorMessage = "No such command";
		request.setAttribute("errorMessage", errorMessage);
		LOGGER.error("Set the request attribute: errorMessage --> " + errorMessage);

		LOGGER.debug("Command finished");
		return new EntryPath(PathConstants.PAGE_ERROR_PAGE.getValue(), false);
	}

}
