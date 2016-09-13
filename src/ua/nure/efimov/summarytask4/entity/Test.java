package ua.nure.efimov.summarytask4.entity;

import java.util.Date;
import java.util.List;

import ua.nure.efimov.summarytask4.db.dao.Identified;

public class Test implements Identified<Integer> {
	/**
	 * SerialVersion UID.
	 */
	private static final long serialVersionUID = -5678540924163354165L;
	/**
	 * Id of test.
	 */
	private Integer id;
	/**
	 * Name of test.
	 */
	private String name;
	/**
	 * Subject of test.
	 */
	private Subject subject;
	/**
	 * Description of test.
	 */
	private String description;
	/**
	 * Equivalent to enum difficulty -> can be from 1 to 5.
	 */
	private int difficulty;
	/**
	 * Date of adding test to db.
	 */
	private Date addDate;
	/**
	 * Time for passing test. In seconds.
	 */
	private int passTime;

	/**
	 * List with questions for current test.
	 */
	private List<Question> questions;

	@Override
	public Integer getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the subject
	 */
	public Subject getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the difficulty
	 */
	public int getDifficulty() {
		return difficulty;
	}

	/**
	 * @param difficulty
	 *            the difficulty to set
	 */
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * @return the addDate
	 */
	public Date getAddDate() {
		return addDate;
	}

	/**
	 * @param addDate
	 *            the addDate to set
	 */
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	/**
	 * @return the passTime
	 */
	public int getPassTime() {
		return passTime;
	}

	/**
	 * @param passTime
	 *            the passTime to set
	 */
	public void setPassTime(int passTime) {
		this.passTime = passTime;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the questions
	 */
	public List<Question> getQuestions() {
		return questions;
	}

	/**
	 * @param questions
	 *            the questions to set
	 */
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	@Override
	public String toString() {
		return "Test [id=" + id + ", name=" + name + ", subject=" + subject + ", description=" + description
				+ ", difficulty=" + difficulty + ", addDate=" + addDate + ", passTime=" + passTime + ", questions="
				+ questions + "]";
	}

}
