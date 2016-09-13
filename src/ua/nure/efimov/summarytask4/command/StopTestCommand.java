package ua.nure.efimov.summarytask4.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.efimov.summarytask4.constants.ComonConstants;
import ua.nure.efimov.summarytask4.exception.ApplicationException;

public class StopTestCommand extends Command {
	private static final long serialVersionUID = -3467061065942801215L;
	private static final Logger LOGGER = Logger.getLogger(StopTestCommand.class);

	@Override
	public EntryPath execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOGGER.debug("Command starts");

		if (request.getSession() != null) {
			SessionHelperTestPass.removeAllAttributesFromSessionSet(request.getSession());
			request.getSession().removeAttribute(ComonConstants.SUBJECT_BY_ID.getValue());
			LOGGER.info("Session cleaned, test interrupted");
		}
		LOGGER.debug("Command finished");
		return new EntryPath(request.getContextPath(), true);
	}

}
