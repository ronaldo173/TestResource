package ua.nure.efimov.summarytask4.entity;

import java.util.ArrayList;
import java.util.List;

import ua.nure.efimov.summarytask4.db.dao.Identified;

/**
 * Special entity of common fields from questions and tests.
 * 
 * Show many-to-many 3rd table test_questions.
 * 
 * Now this entity represents one-to-many relation with saving value of test_id
 * and list<questions> for this test_id.
 * 
 * @author Alexandr Efimov
 *
 */
public class TestQuestions implements Identified<Integer> {
	/**
	 * SerialVersion UID.
	 */
	private static final long serialVersionUID = 3461347084975455039L;

	private int testId;
	/**
	 * It's questions for test with test_id = this.testId; List contains
	 * qustions_id.
	 */
	private List<Integer> questionsList = new ArrayList<>();

	/**
	 * It's one of question_id from list. Need for correct insert to db.
	 */
	private int currentQuestionId;

	/**
	 * @return the currentQuestionId
	 */
	public int getCurrentQuestionId() {
		return currentQuestionId;
	}

	/**
	 * @param currentQuestionId
	 *            the currentQuestionId to set
	 */
	public void setCurrentQuestionId(int currentQuestionId) {
		this.currentQuestionId = currentQuestionId;
	}

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
	 * This class hasn't clean id, so return test id.
	 */
	@Override
	public Integer getId() {
		return testId;
	}

	/**
	 * @return the questionsList
	 */
	public List<Integer> getQuestionsList() {
		return questionsList;
	}

	/**
	 * @param questionsList
	 *            the questionsList to set
	 */
	public void setQuestionsList(List<Integer> questionsList) {
		this.questionsList = questionsList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TestQuestions [testId=" + testId + ", questionsList=" + questionsList + "]";
	}

}
