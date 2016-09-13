package ua.nure.efimov.summarytask4.command;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.efimov.summarytask4.constants.ComonConstants;
import ua.nure.efimov.summarytask4.constants.PathConstants;
import ua.nure.efimov.summarytask4.entity.User;
import ua.nure.efimov.summarytask4.exception.ApplicationException;
import ua.nure.efimov.summarytask4.exception.BusinessException;
import ua.nure.efimov.summarytask4.services.MailHelper;
import ua.nure.efimov.summarytask4.services.UserService;
import ua.nure.efimov.summarytask4.utils.LogUtils;

/**
 * Command for verify user mail.
 * 
 * @author Alexandr Efimov
 *
 */
public class MailVerifyCommand extends Command {
	private static final long serialVersionUID = 8095885537352763530L;
	private static final Logger LOGGER = Logger.getLogger(MailVerifyCommand.class);

	@Override
	public EntryPath execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LogUtils.logDebug(LOGGER, "Command starts");

		// send mail
		User user = (User) request.getSession().getAttribute(ComonConstants.USER_INFO_OBJECT.getValue());

		String action = request.getParameter(ComonConstants.ACTION.getValue());
		if (action != null) {
			if (action.equals("send")) {
				if (user != null && user.getEmail() != null) {
					String email = user.getEmail();
					sendMail(email, user.getId());
					LOGGER.info("Email confirm send to: " + email);
					request.getSession().setAttribute(ComonConstants.MAIL_CONFIRMED.getValue(),
							"Mail sent for confirm! To: " + email);
				}
			}

			if (action.equals("mailConfirm")) {
				String confirmIdParam = request.getParameter(ComonConstants.CONFIRM_MAIL_USER_ID.getValue());
				try {
					int idUserConf = Integer.parseInt(confirmIdParam);
					UserService userService = new UserService();

					userService.confirmUserMailById(idUserConf);
					LOGGER.info("CONFIRMED MAIL! ");
					request.getSession().setAttribute(ComonConstants.MAIL_CONFIRMED.getValue(),
							"Mail confirmed! Try relog.");
				} catch (BusinessException e) {
					LOGGER.error("Error confirm in db or with mail", e);
				}
			}

		}

		LogUtils.logDebug(LOGGER, "Command finished");
		return new EntryPath(request.getContextPath() + PathConstants.MY_PAGE.getValue(), true);
	}

	/**
	 * Send mail to user.
	 * 
	 * @param userId
	 */
	private void sendMail(String emailAddres, Integer userId) {
		String subject = "Mail confirm";
		String link = "http://localhost:8080/TestResource/Controller?command=verificateMail&action=mailConfirm"
				+ "&userIdMail=" + userId;
		String message = "For confirm mail go: " + link;

		try {
			MailHelper.sendMail(emailAddres, subject, message);
			LOGGER.info("MAIL SENT SUCCESS!");
		} catch (MessagingException e) {
			LOGGER.error("MAIL SEND ERROR", e);
		}
	}

}
