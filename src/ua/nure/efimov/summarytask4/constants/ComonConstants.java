/**
 * 
 */
package ua.nure.efimov.summarytask4.constants;

/**
 * 
 * Common constants of application.
 * 
 * @author Alexandr Efimov
 *
 */
public enum ComonConstants implements Meaningful {
	SERVLET_REFERER("Referer"), ENCODING("UTF-8"),

	/**
	 * Command names.
	 */
	COMMAND_NOT_COMMAND("noCommand"),

	COMMAND_LANGUAGE("languageCommand"), COMMAND_LOGOUT("logoutCommand"), COMMAND_SUBCJECT_CHOOSE(
			"subjectSelectCommand"), COMMAND_START_TEST("startTest"), COMMAND_STOP_TEST(
					"interruptTest"), COMMAND_CHECK_RESULT("resultCheck"), COMMAND_MY_PAGE(
							"myPage"), COMMAND_REGISTRATION("registrationCommand"), TESTS_MODIFY_COMMAND(
									"testsModify"), MODIFY_TEST_COMMAND("modifyTest"),

	COMMAND("command"), LANGUAGE("language"), SUBJECT_ID("subj_id"), SUBJECTS_ALL("subjectsAll"), SUBJECT_BY_ID(
			"subjectById"), TESTS("tests"), TEST_STARTED("testStarted"), TEST_ID("testId"), QUESTIONS(
					"questions"), TEST_NAME("testName"), TEST_PASS_TIME("testPassTime"), ANSWER(
							"Answer"), RESULT_BEANS_LIST("listResultTestBeans"), RESULT_CORR_ANSWERS(
									"result_correct_answers"), RESULT_CORR_ANSWERS_PERCENTS(
											"result_correct_answers_percents"), RESULT_ANSWERS_MAX_SIZE(
													"result_answers_max_size)"), NOT_AUTHORIZATED(
															"not_authorizated"), USER_INFO_OBJECT(
																	"user_info_object"), USER_PHOTO(
																			"photoOfUser"), TEST_HISTORY_LIST(
																					"testHistoryList"), COMMAND_CHANGE_SORT_TYPE(
																							"changeSortType"), SORT_TYPE(
																									"sortType"), SORT_TYPES_LIST(
																											"sort_types_list"), USER_ROLE(
																													"user_role"), ACTION_WITH(
																															"action_with"), SUBJECTS_EDIT(
																																	"subjects_edit"), UPDATE_TEST_ID(
																																			"updateTestId"), TESTS_ALL(
																																					"tests"), UPDATE_TEST(
																																							"testForUpdate"), ACTION(
																																									"action"), MODIFY_USERS_COMMAND(
																																											"usersControl"), ALL_USERS(
																																													"allUsers"), USER_BAN(
																																															"user_ban"), MAIL_CONFIRM_COMMAND(
																																																	"verificateMail"), CONFIRM_MAIL_USER_ID(
																																																			"userIdMail"), MAIL_CONFIRMED(
																																																					"mailConfirmed"), TOTAL_RATE(
																																																							"totalSuccesRate"), DIFFICALTY_COLUMN(
																																																									"difficulty"), AVERAGE_COLUMN(
																																																											"AVG"), TOTAL_TESTS_COLUMN(
																																																													"TESTS_AMOUNT"),;

	/**
	 * Value of constant.
	 */
	private String value;

	private ComonConstants(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}
}
