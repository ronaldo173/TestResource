package ua.nure.efimov.summarytask4.services;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import ua.nure.efimov.summarytask4.db.dao.DaoFactory;
import ua.nure.efimov.summarytask4.db.dao.jdbc.impl.DaoFactoryMySqlImpl;
import ua.nure.efimov.summarytask4.db.dao.jdbc.impl.QuestionDaoImpl;
import ua.nure.efimov.summarytask4.db.dao.jdbc.impl.TestQuestionsDaoImpl;
import ua.nure.efimov.summarytask4.entity.Question;
import ua.nure.efimov.summarytask4.entity.TestQuestions;
import ua.nure.efimov.summarytask4.exception.BusinessException;

public class QuestionService {
	/**
	 * Dao factory object.
	 */
	private DaoFactory<Connection> factory;

	/**
	 * Remove question from bd.
	 * 
	 * @param questId
	 * @param idTestForUpdate
	 * @throws BusinessException
	 */
	public void deleteQuestionFromTest(int questId, Integer idTestForUpdate) throws BusinessException {
		if (factory == null) {
			factory = DaoFactoryMySqlImpl.getInstance();
		}

		try (Connection connection = factory.getContext()) {

			TestQuestionsDaoImpl testsDao = (TestQuestionsDaoImpl) factory.getDao(connection, TestQuestions.class);

			/**
			 * Remove from TestQuestions
			 */
			TestQuestions testQuestions = new TestQuestions();
			testQuestions.setTestId(idTestForUpdate);
			List<Integer> questionsList = new ArrayList<>();
			questionsList.add(questId);
			testQuestions.setQuestionsList(questionsList);
			testsDao.delete(testQuestions);

			/**
			 * Remove from questions
			 */
			QuestionDaoImpl questionDao = (QuestionDaoImpl) factory.getDao(connection, Question.class);
			Question questByPK = questionDao.getByPK(questId);
			questionDao.delete(questByPK);

		} catch (Exception e) {
			throw new BusinessException(e);
		}

	}

	/**
	 * Save question without answers. Return question id.
	 * 
	 * @param question
	 * @return question id
	 * @throws BusinessException
	 */
	public int saveQuestionWitoutAnswers(Question question) throws BusinessException {
		if (factory == null) {
			factory = DaoFactoryMySqlImpl.getInstance();
		}

		try (Connection connection = factory.getContext()) {

			QuestionDaoImpl questionDao = (QuestionDaoImpl) factory.getDao(connection, Question.class);

			Question persistedQuest = questionDao.persist(question);
			return persistedQuest.getId();
		} catch (Exception e) {
			throw new BusinessException(e);
		}

	}

	public void saveQuestionForTest(Question question, Integer idTestForAdd) throws BusinessException {

		if (factory == null) {
			factory = DaoFactoryMySqlImpl.getInstance();
		}

		try (Connection connection = factory.getContext()) {

			QuestionDaoImpl questionDao = (QuestionDaoImpl) factory.getDao(connection, Question.class);
			questionDao.update(question);

			/**
			 * And save to test questions
			 */
			TestQuestionsDaoImpl testQuestionsDao = (TestQuestionsDaoImpl) factory.getDao(connection,
					TestQuestions.class);
			TestQuestions testQuestions = new TestQuestions();
			testQuestions.setTestId(idTestForAdd);
			List<Integer> questionsList = new ArrayList<>();
			questionsList.add(question.getId());
			testQuestions.setQuestionsList(questionsList);
			testQuestionsDao.persist(testQuestions);

		} catch (Exception e) {
			throw new BusinessException(e);
		}

	}

}
