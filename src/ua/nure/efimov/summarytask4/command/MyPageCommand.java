package ua.nure.efimov.summarytask4.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.util.Base64;
import org.apache.log4j.Logger;

import ua.nure.efimov.summarytask4.beans.SuccessRate;
import ua.nure.efimov.summarytask4.constants.ComonConstants;
import ua.nure.efimov.summarytask4.constants.PathConstants;
import ua.nure.efimov.summarytask4.constants.RolesNames;
import ua.nure.efimov.summarytask4.entity.Role;
import ua.nure.efimov.summarytask4.entity.TestHistory;
import ua.nure.efimov.summarytask4.entity.User;
import ua.nure.efimov.summarytask4.exception.ApplicationException;
import ua.nure.efimov.summarytask4.exception.BusinessException;
import ua.nure.efimov.summarytask4.services.TestHistoryServise;
import ua.nure.efimov.summarytask4.services.UserService;
import ua.nure.efimov.summarytask4.utils.LogUtils;

@SuppressWarnings("deprecation")
public class MyPageCommand extends Command {
	private static final long serialVersionUID = 8672963569580208726L;
	private static final Logger LOGGER = Logger.getLogger(MyPageCommand.class);

	@Override
	public EntryPath execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LogUtils.logDebug(LOGGER, "Command starts");

		if (request.getSession() != null) {
			User user = (User) request.getSession().getAttribute(ComonConstants.USER_INFO_OBJECT.getValue());
			if (request.getSession().getAttribute(ComonConstants.USER_PHOTO.getValue()) == null) {
				if (user != null) {
					loadPhotoToSession(user.getId(), request.getSession());
				}
			} else {
				LOGGER.debug("Photo not loaded, already in session.");
			}
			if (user != null) {
				loadTestsHistoryOfUserToSession(user.getId(), request.getSession());
				loadMainUserRoleToSession(user.getRoles(), request.getSession());
				/**
				 * LOAD success rate by difficulty
				 */
				loadSuccessRateByDifficulty(user.getId(), request);
			}

			LogUtils.logDebug(LOGGER, "Command finished");
			// check if session has subjectId -> removeIt
			request.getSession().removeAttribute(ComonConstants.SUBJECT_BY_ID.getValue());

		}
		return new EntryPath(PathConstants.MY_PAGE.getValue(), false);
	}

	/**
	 * Load to the session rate of passing tests by user, grouped by difficulty
	 * 
	 * @param userId
	 *            is id of user
	 * @param request
	 *            is current request
	 */

	private void loadSuccessRateByDifficulty(Integer userId, HttpServletRequest request) {
		UserService userService = new UserService();

		try {
			List<SuccessRate> successRate = userService.loadTotalSuccessRate(userId);
			LOGGER.info("Total results received, size: " + successRate.size());

			request.getSession().setAttribute(ComonConstants.TOTAL_RATE.getValue(), successRate);

		} catch (BusinessException e) {
			LOGGER.error("Error get total rate", e.getCause());
		}

	}

	/**
	 * Get from list higher role and add it to the session.
	 * 
	 * @param roles
	 *            is list with roles
	 * @param session
	 *            is currect session
	 */
	private void loadMainUserRoleToSession(List<Role> roles, HttpSession session) {
		Role mainRole = null;
		for (Role role : roles) {
			if (role.getRoleName().equals(RolesNames.ADMIN.getValue())) {
				mainRole = role;
				break;
			} else {
				mainRole = role;
			}
		}

		session.setAttribute(ComonConstants.USER_ROLE.getValue(), mainRole);
	}

	/**
	 * Get all users history of passing tests and put to the session.
	 * 
	 * @param userId
	 *            is id of user
	 * @param session
	 *            is current session
	 */
	private void loadTestsHistoryOfUserToSession(Integer userId, HttpSession session) {
		TestHistoryServise testHistoryServise = new TestHistoryServise();

		try {
			List<TestHistory> testHistoryList = testHistoryServise.getUserTestHistory(userId);

			session.setAttribute(ComonConstants.TEST_HISTORY_LIST.getValue(), testHistoryList);
			LOGGER.debug("Get from test history list, size: " + testHistoryList.size());
		} catch (ApplicationException e) {
			LOGGER.info("Can't load image from db.", e);
		}
	}

	/**
	 * Load photo to the session by userId.
	 * 
	 * @param userId
	 * @param session
	 */
	private void loadPhotoToSession(int userId, HttpSession session) {
		UserService service = new UserService();
		byte[] photo = null;

		try {
			photo = service.loadUserPhoto(userId);
		} catch (ApplicationException e) {
			LOGGER.info("Can't load image from db.", e);
		}

		if (photo == null) {
			LOGGER.debug("No photo for user, or some error");
		} else {
			String url = "data:image/png;base64," + Base64.encode(photo);
			session.setAttribute(ComonConstants.USER_PHOTO.getValue(), url);
			LOGGER.debug("Loaded to session user photo.");
		}

	}

}
