package ua.nure.efimov.summarytask4.beans;

import java.io.Serializable;
import java.util.List;

public class ResultTestPass implements Serializable {
	private static final long serialVersionUID = 4422474062143527558L;

	private int testId;
	private int correctAnswers;

	private List<AnswerCheckEntry> listAnsweredEntries;
	private List<AnswerCheckEntry> listCorrectEntries;

	/**
	 * @return the testId
	 */
	public int getTestId() {
		return testId;
	}

	/**
	 * @param testId
	 *            the testId to set
	 */
	public void setTestId(int testId) {
		this.testId = testId;
	}

	/**
	 * @return the correctAnswers
	 */
	public int getCorrectAnswers() {
		return correctAnswers;
	}

	/**
	 * @param correctAnswers
	 *            the correctAnswers to set
	 */
	public void setCorrectAnswers(int correctAnswers) {
		this.correctAnswers = correctAnswers;
	}

	/**
	 * @return the listAnsweredEntries
	 */
	public List<AnswerCheckEntry> getListAnsweredEntries() {
		return listAnsweredEntries;
	}

	/**
	 * @param listAnsweredEntries
	 *            the listAnsweredEntries to set
	 */
	public void setListAnsweredEntries(List<AnswerCheckEntry> listAnsweredEntries) {
		this.listAnsweredEntries = listAnsweredEntries;
	}

	/**
	 * @return the listCorrectEntries
	 */
	public List<AnswerCheckEntry> getListCorrectEntries() {
		return listCorrectEntries;
	}

	/**
	 * @param listCorrectEntries
	 *            the listCorrectEntries to set
	 */
	public void setListCorrectEntries(List<AnswerCheckEntry> listCorrectEntries) {
		this.listCorrectEntries = listCorrectEntries;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ResultTestPass [testId=" + testId + ", correctAnswers=" + correctAnswers + ", listAnsweredEntries="
				+ listAnsweredEntries + ", listCorrectEntries=" + listCorrectEntries + "]";
	}

}
