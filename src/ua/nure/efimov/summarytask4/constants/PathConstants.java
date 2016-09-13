package ua.nure.efimov.summarytask4.constants;

public enum PathConstants implements Meaningful {
	PAGE_LOGIN("/login.jsp"), PAGE_ERROR_PAGE("/error_page.jsp"), MAIN_PAGE("/tests/main.jsp"), MY_PAGE(
			"/tests/myPage"), RESOURCES_REL_PATH("/resources/"), CONTROLLER_REL_PATH("/Controller"), TEST_RESULT(
					"/tests/testResult"), SUBJECT_SEL_PAGE(
							"/Controller?command=subjectSelectCommand&subj_id="), REGISTRATION_REL_PATH(
									"/registration"), TESTS_MODIFY("/tests/admin/testsEdit"), MODIFY_ONE_TEST(
											"/tests/admin/modifyTest"), MODIFY_USERS(
													"/tests/admin/usersEdit"), ERROR("/error");

	/**
	 * Value of constant.
	 */
	private String value;

	private PathConstants(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}

}
