package ua.nure.efimov.summarytask4.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import ua.nure.efimov.summarytask4.beans.AnswerCheckEntry;
import ua.nure.efimov.summarytask4.beans.ResultTestDetailBean;
import ua.nure.efimov.summarytask4.beans.ResultTestPass;
import ua.nure.efimov.summarytask4.db.dao.jdbc.impl.DaoUtils;
import ua.nure.efimov.summarytask4.entity.Answer;
import ua.nure.efimov.summarytask4.entity.Question;
import ua.nure.efimov.summarytask4.entity.Test;
import ua.nure.efimov.summarytask4.exception.BusinessException;

public class AnswersCheckService {

	List<Answer> listAllCorrectAnswers = new ArrayList<>();
	List<Answer> listAllTestAnswers = new ArrayList<>();
	List<Question> listAllTestQuestions = new ArrayList<>();

	/**
	 * Parse result, gotten in format:
	 * 
	 * {@code Answer_aId_${answer.id}_qId_${answer.questionId}} to list of
	 * {@link AnswerCheckEntry}.
	 * 
	 * @param idAnswersVariantsAnsweredList
	 * @return
	 */
	public List<AnswerCheckEntry> parseResultsToEntries(List<String> idAnswersVariantsAnsweredList)
			throws BusinessException {
		if (idAnswersVariantsAnsweredList == null || idAnswersVariantsAnsweredList.isEmpty()) {
			throw new BusinessException("Empty list with data");
		}
		List<AnswerCheckEntry> resultList = new ArrayList<>();
		List<Entry> listEntriesSimple = new ArrayList<>();

		try {
			for (String param : idAnswersVariantsAnsweredList) {
				String[] splitedParams = param.split("_");
				List<String> listSplitedParams = Arrays.asList(splitedParams);
				Iterator<String> iterator = listSplitedParams.iterator();

				int answId = 0;
				int questId = 0;
				while (iterator.hasNext()) {
					String next = iterator.next();
					if (next.equals("aId")) {
						answId = Integer.parseInt(iterator.next());
					} else if (next.equals("qId")) {
						questId = Integer.parseInt(iterator.next());
						break;
					}
				}
				listEntriesSimple.add(new Entry(questId, answId));
			}

			/**
			 * Now convert entries to AnswerCheckEntry objects.
			 */
			convertEntriesToAnswerCheckEntries(listEntriesSimple, resultList);

			return resultList;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	private void convertEntriesToAnswerCheckEntries(List<Entry> listEntriesSimple, List<AnswerCheckEntry> resultList) {
		for (Entry entry : listEntriesSimple) {
			if (DaoUtils.listContainsId(resultList, entry.idQuestion)) {
				AnswerCheckEntry answerCheckEntry = DaoUtils.getFromListById(resultList, entry.idQuestion);
				answerCheckEntry.getIdAnswersList().add(entry.idAnswer);
			} else {
				AnswerCheckEntry answerCheckEntry = new AnswerCheckEntry();
				answerCheckEntry.setIdQuestion(entry.idQuestion);
				answerCheckEntry.getIdAnswersList().add(entry.idAnswer);

				resultList.add(answerCheckEntry);
			}
		}

	}

	/**
	 * Check result of the test. Return {@link ResultTestPass} object with
	 * number of correct answers and lists with {@link AnswerCheckEntry}
	 * entries(passed, correct).
	 * 
	 * @param listParsedEntries
	 * @param testId
	 * @return
	 * @throws BusinessException
	 */
	public ResultTestPass checkResult(List<AnswerCheckEntry> listParsedEntries, int testId) throws BusinessException {
		ResultTestPass result;
		try {
			Test testById = new TestsLoadService().getTestById(testId);
			List<Question> questions = testById.getQuestions();
			// for better performance add to class list.
			this.listAllTestQuestions = questions;

			List<AnswerCheckEntry> listCorrectEntries = parseQuestionsToEntries(questions);

			int correctAnswers = compareTestAnswers(listParsedEntries, listCorrectEntries);
			result = new ResultTestPass();
			result.setCorrectAnswers(correctAnswers);
			result.setTestId(testId);
			result.setListAnsweredEntries(listParsedEntries);
			result.setListCorrectEntries(listCorrectEntries);

		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return result;
	}

	/**
	 * Compare 2 lists with {@link AnswerCheckEntry objects} and get result of
	 * equals elements.
	 * 
	 * @param listParsedEntries
	 *            is list with answered objects
	 * @param listCorrectEntries
	 *            is list with correct objects
	 * @return number of correct answers
	 */
	private int compareTestAnswers(List<AnswerCheckEntry> listParsedEntries,
			List<AnswerCheckEntry> listCorrectEntries) {
		int result = 0;

		for (AnswerCheckEntry answeredEntry : listParsedEntries) {
			AnswerCheckEntry correctEntry = DaoUtils.getFromListById(listCorrectEntries, answeredEntry.getId());

			if (answeredEntry.equals(correctEntry)) {
				result++;
			}
		}

		return result;
	}

	/**
	 * Parse list with {@link Question} to list with {@link AnswerCheckEntry}.
	 * 
	 * @param questions
	 *            is list with questions
	 * @return list with {@link AnswerCheckEntry}
	 * @throws BusinessException
	 */
	public List<AnswerCheckEntry> parseQuestionsToEntries(List<Question> questions) throws BusinessException {
		if (questions == null || questions.isEmpty()) {
			throw new BusinessException("Empty list with questions.");
		}
		List<AnswerCheckEntry> result = new ArrayList<>();

		for (Question question : questions) {
			List<Answer> answers = question.getAnswers();
			List<Integer> correctAnswersId = getCorrectAnswersId(answers);
			// and already add answer to list with all answers for
			// better performance
			listAllTestAnswers.addAll(answers);

			AnswerCheckEntry entry = new AnswerCheckEntry();
			entry.setIdQuestion(question.getId());
			entry.getIdAnswersList().addAll(correctAnswersId);
			result.add(entry);
		}

		return result;
	}

	/**
	 * Go through list with answers and add to result list id of correct
	 * answers.
	 * 
	 * @param answers
	 * @return
	 */
	private List<Integer> getCorrectAnswersId(List<Answer> answers) {
		List<Integer> correctList = new ArrayList<>();
		for (Answer answer : answers) {
			if (answer.isCorrect()) {
				correctList.add(answer.getId());
				// and already add answer to list with all correct answers for
				// better performance
				listAllCorrectAnswers.add(answer);
			}

		}
		return correctList;
	}

	/**
	 * Simple entry. Has 2 int numbers -> idQuestion, idAnswer.
	 * 
	 * @author Alexandr Efimov
	 *
	 */
	class Entry {
		int idQuestion;
		int idAnswer;

		/**
		 * Create object.
		 * 
		 * @param idQuestion
		 *            is quest id
		 * @param idAnswer
		 *            is answer id
		 */
		public Entry(int idQuestion, int idAnswer) {
			this.idQuestion = idQuestion;
			this.idAnswer = idAnswer;
		}
	}

	/**
	 * Convert {@link ResultTestPass} to list with {@linkResultTestDetailBean}.
	 * 
	 * @param result
	 * @return
	 * @throws BusinessException
	 */
	public List<ResultTestDetailBean> convertResultToDetailBeansList(ResultTestPass result) throws BusinessException {
		List<ResultTestDetailBean> resultList = new ArrayList<>();

		List<AnswerCheckEntry> listCorrectEntries = result.getListCorrectEntries();
		List<AnswerCheckEntry> listAnsweredEntries = result.getListAnsweredEntries();

		for (AnswerCheckEntry correctEntry : listCorrectEntries) {
			ResultTestDetailBean bean = new ResultTestDetailBean();
			int idQuest = correctEntry.getId();

			// set question
			bean.setQuestion(DaoUtils.getFromListById(listAllTestQuestions, idQuest));

			// get answered bean
			AnswerCheckEntry answeredEntry = DaoUtils.getFromListById(listAnsweredEntries, idQuest);
			// add to detail bean answered answers

			/**
			 * If no answer -> add empty row.
			 */
			if (answeredEntry != null) {

				List<Integer> idAnswersAnsweredList = answeredEntry.getIdAnswersList();
				for (Integer id : idAnswersAnsweredList) {
					bean.getAnsweredList().add(DaoUtils.getFromListById(listAllTestAnswers, id));
				}
			}
			// add to detail bean correct answers
			List<Integer> idAnswersCorrectList = correctEntry.getIdAnswersList();
			for (Integer id : idAnswersCorrectList) {
				bean.getCorrectList().add(DaoUtils.getFromListById(listAllCorrectAnswers, id));
			}

			// add bean to result list
			resultList.add(bean);

		}

		return resultList;
	}

}
