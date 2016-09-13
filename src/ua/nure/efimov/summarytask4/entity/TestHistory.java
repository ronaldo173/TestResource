package ua.nure.efimov.summarytask4.entity;

import java.util.Date;

import ua.nure.efimov.summarytask4.db.dao.Identified;

public class TestHistory implements Identified<Integer> {
	/**
	 * SerialVersion UID.
	 */
	private static final long serialVersionUID = 3365008981744094718L;

	private Integer id;

	/**
	 * Test for this history.
	 */
	private Test test;

	/**
	 * User id.
	 */
	private int userId;

	/**
	 * Result of test passing in %. If test has 10 questions, 6 user answered
	 * right -> 60 == 60%.
	 */
	private int testResult;

	/**
	 * Time of start passing test.
	 */
	private Date timeTestPass;

	@Override
	public Integer getId() {
		return id;
	}

	/**
	 * @return the test
	 */
	public Test getTest() {
		return test;
	}

	/**
	 * @param test
	 *            the test to set
	 */
	public void setTest(Test test) {
		this.test = test;
	}

	/**
	 * @return the testResult
	 */
	public int getTestResult() {
		return testResult;
	}

	/**
	 * @param testResult
	 *            the testResult to set
	 */
	public void setTestResult(int testResult) {
		this.testResult = testResult;
	}

	/**
	 * @return the timeTestPass
	 */
	public Date getTimeTestPass() {
		return timeTestPass;
	}

	/**
	 * @param timeTestPass
	 *            the timeTestPass to set
	 */
	public void setTimeTestPass(Date timeTestPass) {
		this.timeTestPass = timeTestPass;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "TestHistory [id=" + id + ", test: =" + test.getName() + ", userId=" + userId + ", testResult="
				+ testResult + ", timeTestPass=" + timeTestPass + "]";
	}

}
