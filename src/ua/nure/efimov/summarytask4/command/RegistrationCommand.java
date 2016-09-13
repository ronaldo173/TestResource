package ua.nure.efimov.summarytask4.command;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.efimov.summarytask4.beans.RegistrationMessagesBean;
import ua.nure.efimov.summarytask4.constants.ComonConstants;
import ua.nure.efimov.summarytask4.exception.ApplicationException;
import ua.nure.efimov.summarytask4.exception.BusinessException;
import ua.nure.efimov.summarytask4.services.RegistrationService;
import ua.nure.efimov.summarytask4.services.VerifyRecaptcha;
import ua.nure.efimov.summarytask4.utils.LogUtils;

/**
 * Registration command.
 * 
 * @author Alexandr Efimov
 *
 */
public class RegistrationCommand extends Command {
	private static final long serialVersionUID = -6266207843846395967L;
	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(RegistrationCommand.class);

	@Override
	public EntryPath execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LogUtils.logDebug(LOGGER, "Command starts");

		@SuppressWarnings("unchecked")
		Map<String, String[]> parameterMap = request.getParameterMap();

		RegistrationMessagesBean validationRegRes = validateRegistrData(parameterMap, request);

		if (request.getSession() != null) {
			request.getSession().invalidate();
		}
		HttpSession session = request.getSession(true);
		LOGGER.info("Logged, reg message: " + validationRegRes);
		session.setAttribute("validationRegRes", validationRegRes);

		LogUtils.logDebug(LOGGER, "Command finished");
		/**
		 * Return to previous page.
		 */
		String referer = request.getHeader(ComonConstants.SERVLET_REFERER.getValue());
		return new EntryPath(referer, true);
	}

	private RegistrationMessagesBean validateRegistrData(Map<String, String[]> parameterMap,
			HttpServletRequest request) {

		RegistrationService registrationService = new RegistrationService();
		RegistrationMessagesBean validationResult = registrationService.validateData(parameterMap);

		// check captcha
		String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
		boolean verifyCaptcha = false;
		try {
			verifyCaptcha = VerifyRecaptcha.verify(gRecaptchaResponse);
		} catch (IOException e) {
			LOGGER.info("Captcha not verified");
		}
		System.out.println("Captcha ---> " + verifyCaptcha);

		/**
		 * If validation is ok, capcha true - save in db
		 */
		if (validationResult.isSuccess() && verifyCaptcha) {
			try {
				registrationService.saveUserInfoToDatabase(parameterMap, validationResult);
			} catch (BusinessException e) {
				validationResult.getMap().put("Save error", "Can't save to db");
				LOGGER.info("Save user to db error!");
			}
		}

		if (!verifyCaptcha) {
			validationResult.setSuccess(false);
			validationResult.getMap().put("Captcha check", "Sorry captcha check wrong!");
			LOGGER.info("Captcha not check didn't pass!");
		}

		return validationResult;
	}

}
