package ua.nure.efimov.summarytask4.beans;

import java.util.ArrayList;
import java.util.List;

import ua.nure.efimov.summarytask4.db.dao.Identified;

/**
 * Bean for check test answers. Contains idQuestion, idAnswersList.
 * 
 * @author Alexandr Efimov
 *
 */
public class AnswerCheckEntry implements Identified<Integer> {
	private static final long serialVersionUID = -2197005650083876754L;

	/**
	 * Id of question.
	 */
	int idQuestion;
	/**
	 * List with answers id to current questions.
	 */
	List<Integer> idAnswersList = new ArrayList<>();

	/**
	 * @return the idQuestion
	 */
	public int getIdQuestion() {
		return idQuestion;
	}

	/**
	 * @param idQuestion
	 *            the idQuestion to set
	 */
	public void setIdQuestion(int idQuestion) {
		this.idQuestion = idQuestion;
	}

	/**
	 * @return the idAnswersList
	 */
	public List<Integer> getIdAnswersList() {
		return idAnswersList;
	}

	/**
	 * @param idAnswersList
	 *            the idAnswersList to set
	 */
	public void setIdAnswersList(List<Integer> idAnswersList) {
		this.idAnswersList = idAnswersList;
	}

	@Override
	public String toString() {
		return "AnswerCheckEntry [idQuestion=" + idQuestion + ", idAnswersList=" + idAnswersList + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idAnswersList == null) ? 0 : idAnswersList.hashCode());
		result = prime * result + idQuestion;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AnswerCheckEntry other = (AnswerCheckEntry) obj;
		if (idAnswersList == null) {
			if (other.idAnswersList != null)
				return false;
		} else if (!idAnswersList.equals(other.idAnswersList))
			return false;
		if (idQuestion != other.idQuestion)
			return false;
		return true;
	}

	/**
	 * Get id of question.
	 */
	@Override
	public Integer getId() {
		return idQuestion;
	}

}