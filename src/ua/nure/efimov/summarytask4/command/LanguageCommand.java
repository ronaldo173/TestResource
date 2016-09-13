package ua.nure.efimov.summarytask4.command;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.efimov.summarytask4.constants.ComonConstants;
import ua.nure.efimov.summarytask4.exception.ApplicationException;
import ua.nure.efimov.summarytask4.utils.LogUtils;

/**
 * Language command. Responses for storing current language in session.
 * 
 * @author Alexandr Efimov
 *
 */
public class LanguageCommand extends Command {
	private static final long serialVersionUID = 9160412352975608824L;

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(LanguageCommand.class);

	@Override
	public EntryPath execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LogUtils.logDebug(LOGGER, "Command starts");
		@SuppressWarnings("unchecked")
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String param = (String) names.nextElement();
			if (!param.equals(ComonConstants.COMMAND.getValue())) {

				HttpSession session = request.getSession();
				Object attribute = session.getAttribute(ComonConstants.LANGUAGE.getValue());
				if (attribute.toString().equals(param)) {
					LOGGER.info("Session already has lang-> " + param);
				} else {
					session.setAttribute(ComonConstants.LANGUAGE.getValue(), param);
					LOGGER.info("Change language to -> " + param);
				}
			}
		}

		LogUtils.logDebug(LOGGER, "Command finished");
		/**
		 * Return to previous page.
		 */
		String referer = request.getHeader(ComonConstants.SERVLET_REFERER.getValue());
		return new EntryPath(referer, true);
	}

}
