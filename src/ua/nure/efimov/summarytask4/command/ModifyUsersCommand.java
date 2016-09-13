package ua.nure.efimov.summarytask4.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.efimov.summarytask4.constants.ComonConstants;
import ua.nure.efimov.summarytask4.constants.PathConstants;
import ua.nure.efimov.summarytask4.entity.User;
import ua.nure.efimov.summarytask4.exception.ApplicationException;
import ua.nure.efimov.summarytask4.exception.BusinessException;
import ua.nure.efimov.summarytask4.services.UserService;
import ua.nure.efimov.summarytask4.utils.LogUtils;

public class ModifyUsersCommand extends Command {
	private static final long serialVersionUID = -7714610737608641646L;
	private static final Logger LOGGER = Logger.getLogger(ModifyUsersCommand.class);

	@Override
	public EntryPath execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LogUtils.logDebug(LOGGER, "Command starts");

		if (request.getSession() != null) {
			loadAllUsers(request.getSession());

			String action = request.getParameter(ComonConstants.ACTION.getValue());
			if (action != null) {
				if (action.equals(ComonConstants.USER_BAN.getValue())) {
					banAction(request);
				}
			}
		}

		LogUtils.logDebug(LOGGER, "Command finished");
		return new EntryPath(request.getContextPath() + PathConstants.MODIFY_USERS.getValue(), true);
	}

	private void banAction(HttpServletRequest request) {

		Map<Integer, String[]> mapForBan = new HashMap<>();

		@SuppressWarnings("unchecked")
		Map<String, String[]> parameterMap = request.getParameterMap();
		for (Entry<String, String[]> entry : parameterMap.entrySet()) {
			String key = entry.getKey();
			if (key.startsWith("userBan_")) {
				int indexOfUnderSymb = key.lastIndexOf('_');
				String idUser = key.substring(++indexOfUnderSymb);
				mapForBan.put(Integer.parseInt(idUser), entry.getValue());
			}
		}

		if (!mapForBan.isEmpty()) {
			UserService userService = new UserService();
			try {
				for (Entry<Integer, String[]> entry : mapForBan.entrySet()) {
					Integer userId = entry.getKey();
					String[] valuesForUser = entry.getValue();

					if (valuesForUser.length != 2) {
						// user unban

						userService.banUserById(userId, false);

					} else {
						// user ban
						userService.banUserById(userId, true);
					}
				}
				LOGGER.info("Ban/unban users success");
				loadAllUsers(request.getSession());
			} catch (BusinessException e) {
				LOGGER.info("Can't ban/unban users in db", e.getCause());
			}

		}
	}

	/**
	 * Load all users to session.
	 * 
	 * @param session
	 */
	private void loadAllUsers(HttpSession session) {
		UserService userService = new UserService();

		try {
			List<User> usersList = userService.loadUsers();
			LOGGER.info("Loaded users list, size: " + usersList.size());

			session.setAttribute(ComonConstants.ALL_USERS.getValue(), usersList);
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e.getCause());
		}
	}

}
