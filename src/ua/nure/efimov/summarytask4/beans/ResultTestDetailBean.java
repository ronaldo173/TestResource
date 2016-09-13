package ua.nure.efimov.summarytask4.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ua.nure.efimov.summarytask4.entity.Answer;
import ua.nure.efimov.summarytask4.entity.Question;

public class ResultTestDetailBean implements Serializable {
	private static final long serialVersionUID = 154053669496546093L;

	private Question question;
	private List<Answer> answeredList = new ArrayList<>();
	private List<Answer> correctList = new ArrayList<>();

	public ResultTestDetailBean() {
	}

	/**
	 * Create detail result bean with params.
	 * 
	 * @param question
	 *            is question
	 * @param answeredList
	 *            is list with answers from user
	 * @param correctList
	 *            is list with correct answers
	 */
	public ResultTestDetailBean(Question question, List<Answer> answeredList, List<Answer> correctList) {
		this.question = question;
		this.answeredList = answeredList;
		this.correctList = correctList;
	}

	/**
	 * @return the question
	 */
	public Question getQuestion() {
		return question;
	}

	/**
	 * @param question
	 *            the question to set
	 */
	public void setQuestion(Question question) {
		this.question = question;
	}

	/**
	 * @return the answeredList
	 */
	public List<Answer> getAnsweredList() {
		return answeredList;
	}

	/**
	 * @param answeredList
	 *            the answeredList to set
	 */
	public void setAnsweredList(List<Answer> answeredList) {
		this.answeredList = answeredList;
	}

	/**
	 * @return the correctList
	 */
	public List<Answer> getCorrectList() {
		return correctList;
	}

	/**
	 * @param correctList
	 *            the correctList to set
	 */
	public void setCorrectList(List<Answer> correctList) {
		this.correctList = correctList;
	}

	@Override
	public String toString() {
		return "ResultTestDetailBean [questionBody=" + question.getBody() + ", answeredList=" + answeredList
				+ ", correctList=" + correctList + "]";
	}
}
