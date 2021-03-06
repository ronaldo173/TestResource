package ua.nure.efimov.summarytask4.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.efimov.summarytask4.utils.LogUtils;

/**
 * Logout command. Invalidate session.
 * 
 * @author Alexandr Efimov
 *
 */
public class LogoutCommand extends Command {
	private static final long serialVersionUID = 2600661206393135830L;
	private static final Logger LOGGER = Logger.getLogger(LogoutCommand.class);

	@Override
	public EntryPath execute(HttpServletRequest request, HttpServletResponse response) {
		LogUtils.logDebug(LOGGER, "Command starts");

		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		LOGGER.info("Session invalidated");
		LogUtils.logDebug(LOGGER, "Command finished");
		return new EntryPath(request.getContextPath(), true);
	}

}