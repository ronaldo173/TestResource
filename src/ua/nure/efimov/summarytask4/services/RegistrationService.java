package ua.nure.efimov.summarytask4.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ua.nure.efimov.summarytask4.beans.RegistrationMessagesBean;
import ua.nure.efimov.summarytask4.entity.Role;
import ua.nure.efimov.summarytask4.entity.User;
import ua.nure.efimov.summarytask4.exception.BusinessException;

public class RegistrationService {
	private static String LOGIN_FIELD = "login";
	private static String FIRST_NAME_FIELD = "first_name";
	private static String LAST_NAME_FIELD = "last_name";
	private static String EMAIL_FIELD = "email";
	private static String PASSWORD_FIELD = "password";
	private static String CONFIRM_PASSW_FIELD = "confirm_password";

	/**
	 * Pattern for log validation.
	 */
	private static final String LOGIN_PATTERN = "^[a-zA-Z0-9_-]{3,15}$";

	private static final Pattern EMAIL_ADDRESS_REGEX_PATTERN = Pattern
			.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	/**
	 * List with names of fields.
	 */
	private static List<String> fieldsNames;

	/**
	 * Size of map with parameters fo check registration data.
	 * 
	 * Values +1 more param(name of command or smth else).
	 */
	private static final int MAP_REGISTER_SIZE = 7;

	/**
	 * Init list with fields names.
	 */
	public RegistrationService() {
		if (fieldsNames == null) {
			fieldsNames = new ArrayList<>();

			Collections.addAll(fieldsNames, LOGIN_FIELD, FIRST_NAME_FIELD, LAST_NAME_FIELD, EMAIL_FIELD, PASSWORD_FIELD,
					CONFIRM_PASSW_FIELD);
		}

	}

	public RegistrationMessagesBean validateData(Map<String, String[]> parameterMap) {
		RegistrationMessagesBean regMessagesBean = new RegistrationMessagesBean();
		if (parameterMap == null || parameterMap.size() < MAP_REGISTER_SIZE) {
			checkContainsFields(parameterMap, regMessagesBean);
			return regMessagesBean;
		}

		String loginValue = parameterMap.get(LOGIN_FIELD)[0];
		boolean isCheckLoginSuccess = checkLoginCorrect(loginValue, regMessagesBean);
		if (!isCheckLoginSuccess) {
			return regMessagesBean;
		}

		String emailValue = parameterMap.get(EMAIL_FIELD)[0];
		boolean isCheckEmailSuccess = checkEmailCorrect(emailValue, regMessagesBean);
		if (!isCheckEmailSuccess) {
			return regMessagesBean;
		}

		String passwordValue = parameterMap.get(PASSWORD_FIELD)[0];
		String confirmPasswordValue = parameterMap.get(CONFIRM_PASSW_FIELD)[0];
		boolean isPasswordsEquals = checkPasswordEquals(passwordValue, confirmPasswordValue, regMessagesBean);
		if (!isPasswordsEquals) {
			return regMessagesBean;
		}

		boolean isCheckLoginDataBaseSuccess;
		try {
			isCheckLoginDataBaseSuccess = checkLoginInDbUniq(loginValue, regMessagesBean);
		} catch (BusinessException e) {
			isCheckLoginDataBaseSuccess = false;
			regMessagesBean.getMap().put(LOGIN_FIELD, "Error with db for check login.");
		}
		if (!isCheckLoginDataBaseSuccess) {
			return regMessagesBean;
		}

		regMessagesBean.setSuccess(true);
		return regMessagesBean;
	}

	public void saveUserInfoToDatabase(Map<String, String[]> parameterMap, RegistrationMessagesBean regMessagesBean)
			throws BusinessException {
		String login = parameterMap.get(LOGIN_FIELD)[0];
		String firstName = parameterMap.get(FIRST_NAME_FIELD)[0];
		String lastName = parameterMap.get(LAST_NAME_FIELD)[0];
		String email = parameterMap.get(EMAIL_FIELD)[0];
		String passwordNotEncr = parameterMap.get(PASSWORD_FIELD)[0];

		UserService userService = new UserService();
		String password = userService.encryptPassword(passwordNotEncr);

		User user = new User();
		user.setLogin(login);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
		// set user role
		List<Role> listRoles = new ArrayList<>();
		Role roleForUser = new Role();
		roleForUser.setRoleName("user");
		listRoles.add(roleForUser);
		user.setRoles(listRoles);

		// set to db
		userService.persistUser(user);
	}

	/**
	 * Check if user with current login already in database.
	 * 
	 * @param loginValue
	 * @param regMessagesBean
	 * @return true if login is free
	 * @throws BusinessException
	 */
	private boolean checkLoginInDbUniq(String loginValue, RegistrationMessagesBean regMessagesBean)
			throws BusinessException {
		String errMessage = "Login already busy";

		UserService userService = new UserService();
		User userByLogin = userService.getUserByLogin(loginValue);

		boolean isLoginFree = userByLogin == null;
		if (!isLoginFree) {
			regMessagesBean.getMap().put(LOGIN_FIELD, errMessage);
		}
		return isLoginFree;
	}

	/**
	 * Check if 2 passwords equals.
	 * 
	 * @param passwordValue
	 *            is first
	 * @param confirmPasswordValue
	 *            is second
	 * @param regMessagesBean
	 *            is bean for error message
	 * @return true if equals
	 */
	public boolean checkPasswordEquals(String passwordValue, String confirmPasswordValue,
			RegistrationMessagesBean regMessagesBean) {
		String errMessage = "Not equal passwords.";
		if (passwordValue == null || confirmPasswordValue == null) {
			regMessagesBean.getMap().put(PASSWORD_FIELD, errMessage);
			return false;
		}

		boolean isEquals = passwordValue.equals(confirmPasswordValue);
		if (!isEquals) {
			regMessagesBean.getMap().put(PASSWORD_FIELD, errMessage);
		}
		return isEquals;
	}

	/**
	 * Validate email.
	 * 
	 * @param emailValue
	 *            is email Value
	 * @param regMessagesBean
	 * @return true if valid
	 */
	public boolean checkEmailCorrect(String emailValue, RegistrationMessagesBean regMessagesBean) {
		String errMessage = "Not valid email.";
		if (emailValue == null) {
			regMessagesBean.getMap().put(EMAIL_FIELD, errMessage);
			return false;
		}

		Matcher matcher = EMAIL_ADDRESS_REGEX_PATTERN.matcher(emailValue);
		boolean isValidEmail = matcher.find();
		if (!isValidEmail) {
			regMessagesBean.getMap().put(EMAIL_FIELD, errMessage);
		}
		return isValidEmail;
	}

	/**
	 * Validate login. Must be at least 3 chars, max 15(can change -> see regex
	 * validation) start not from digit.
	 * 
	 * @param login
	 *            is login
	 * @param regMessagesBean
	 * @return true if valid
	 */
	public boolean checkLoginCorrect(String login, RegistrationMessagesBean regMessagesBean) {
		String errMessage = "Not valid. Must be 3-15 symbols.";
		if (login == null) {
			regMessagesBean.getMap().put(LOGIN_FIELD, errMessage);
			return false;
		}
		Pattern pattern = Pattern.compile(LOGIN_PATTERN);
		Matcher matcher = pattern.matcher(login);

		boolean matches = matcher.matches();
		if (!matches) {
			regMessagesBean.getMap().put(LOGIN_FIELD, errMessage);
		}
		return matches;
	}

	/**
	 * Check if contains and not null/empty fields. If smth wrong write message
	 * to {@code regMessagesBean}
	 * 
	 * @param parameterMap
	 *            is map with data
	 * @param regMessagesBean
	 *            is bean for result messages
	 */
	private void checkContainsFields(Map<String, String[]> parameterMap, RegistrationMessagesBean regMessagesBean) {
		if (parameterMap == null || parameterMap.isEmpty()) {
			return;
		}
		for (String fieldName : fieldsNames) {

			if (!parameterMap.containsKey(fieldName)) {
				regMessagesBean.getMap().put(fieldName, "Can't be empty");
			} else {
				String[] values = parameterMap.get(fieldName);
				if (values == null || values.length < 1) {
					regMessagesBean.getMap().put(fieldName, "No values for fieldS");
				} else {
					String value = values[0];
					if (value.isEmpty()) {
						regMessagesBean.getMap().put(fieldName, "Can't be empty");
					}
				}
			}
		}

	}

}
