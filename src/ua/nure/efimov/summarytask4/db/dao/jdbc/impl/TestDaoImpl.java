package ua.nure.efimov.summarytask4.db.dao.jdbc.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.nure.efimov.summarytask4.db.dao.GenericDao;
import ua.nure.efimov.summarytask4.db.dao.Identified;
import ua.nure.efimov.summarytask4.db.dao.PersistException;
import ua.nure.efimov.summarytask4.db.dao.jdbc.AbstractJDBCDAO;
import ua.nure.efimov.summarytask4.entity.Question;
import ua.nure.efimov.summarytask4.entity.Subject;
import ua.nure.efimov.summarytask4.entity.Test;
import ua.nure.efimov.summarytask4.entity.TestQuestions;

public class TestDaoImpl extends AbstractJDBCDAO<Test, Integer> {
	private Connection connection;

	public TestDaoImpl(Connection connection) {
		super(connection);
		this.connection = connection;
	}

	/**
	 * For constraining access to set id.
	 * 
	 * @author Alexandr Efimov
	 *
	 */
	private class PersistTest extends Test {
		private static final long serialVersionUID = 6367610763027979450L;

		public void setId(int id) {
			super.setId(id);
		}
	}

	@Override
	public String getSelectQuery() {
		return "SELECT * FROM testing.tests";
	}

	@Override
	public String getCreateQuery() {
		return "INSERT INTO testing.tests (name, subject_id, description, difficulty, add_date, pass_time) VALUES (?, ?, ?, ?, ?, ?);";
	}

	@Override
	public String getUpdateQuery() {
		return "UPDATE testing.tests SET name = ?, subject_id  = ?, description = ?, difficulty = ?, pass_time = ?  WHERE id = ?;";
	}

	@Override
	public String getDeleteQuery() {
		return "DELETE FROM testing.tests WHERE id= ?;";
	}

	@Override
	protected List<Test> parseResSet(ResultSet rs) throws PersistException {
		List<Test> testsList = new ArrayList<>();
		try {

			while (rs.next()) {
				Test test = new PersistTest();
				int testId = rs.getInt("id");
				test.setId(testId);
				test.setName(rs.getString("name"));

				int subjId = rs.getInt("subject_id");
				// get subject
				GenericDao<? extends Identified<Integer>, Integer> subjectDaoImpl = DaoFactoryMySqlImpl.getInstance()
						.getDao(connection, Subject.class);
				Subject subjByPK = (Subject) subjectDaoImpl.getByPK(subjId);
				test.setSubject(subjByPK);

				test.setDescription(rs.getString("description"));
				test.setDifficulty(rs.getInt("difficulty"));
				test.setAddDate(rs.getDate("add_date"));
				test.setPassTime(rs.getInt("pass_time"));

				/**
				 * Now get questions for current test.
				 */
				ArrayList<Question> questionsList = new ArrayList<>();
				test.setQuestions(questionsList);

				// get from testQuestions dao all questions id for this test
				GenericDao<? extends Identified<Integer>, Integer> testQuestionsDao = DaoFactoryMySqlImpl.getInstance()
						.getDao(connection, TestQuestions.class);
				TestQuestions testQuestions = (TestQuestions) testQuestionsDao.getByPK(testId);

				if (testQuestions != null) {

					// and from question dao get questions by id
					GenericDao<? extends Identified<Integer>, Integer> questionsDao = DaoFactoryMySqlImpl.getInstance()
							.getDao(connection, Question.class);

					for (Integer questionId : testQuestions.getQuestionsList()) {
						Identified<Integer> question = questionsDao.getByPK(questionId);
						questionsList.add((Question) question);
					}
				}

				testsList.add(test);
			}
		} catch (Exception e) {
			throw new PersistException(e);
		}
		return testsList;
	}

	@Override
	protected void prepareStForUpdateSetArgs(PreparedStatement statement, Test test) throws PersistException {
		/**
		 * "UPDATE testing.tests SET name = ?, subject_id = ?, description = ?,
		 * difficulty = ?, pass_time = ? WHERE id = ?;"
		 */
		try {
			statement.setString(1, test.getName());
			statement.setInt(2, test.getSubject().getId());
			statement.setString(3, test.getDescription());
			statement.setInt(4, test.getDifficulty());
			statement.setInt(5, test.getPassTime());

			statement.setInt(6, test.getId());
		} catch (SQLException e) {
			throw new PersistException(e);
		}
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement statement, Test test) throws PersistException {
		/**
		 * "INSERT INTO testing.tests (name, subject_id, description,
		 * difficulty, add_date, pass_time) VALUES (?, ?, ?, ?, ?, ?);"
		 */
		try {

			Date sqlDate = DaoUtils.convertDateToSql(test.getAddDate());

			statement.setString(1, test.getName());
			statement.setInt(2, test.getSubject().getId());
			statement.setString(3, test.getDescription());
			statement.setInt(4, test.getDifficulty());
			statement.setDate(5, sqlDate);
			statement.setInt(6, test.getPassTime());

		} catch (SQLException e) {
			throw new PersistException(e);
		}
	}

	/*
	 * Persists Test object. Except test table it put all data(questions,
	 * answers, dependencies to db tables).
	 * 
	 * @see
	 * ua.nure.efimov.summarytask4.db.dao.jdbc.AbstractJDBCDAO#persist(ua.nure.
	 * efimov.summarytask4.db.dao.Identified)
	 */
	@Override
	public Test persist(Test test) throws PersistException {
		/**
		 * If subject id null -> persist subject
		 */
		Subject subject = test.getSubject();
		SubjectDaoImpl subjectDao = (SubjectDaoImpl) DaoFactoryMySqlImpl.getInstance().getDao(connection,
				Subject.class);

		if (subject.getId() == null || !DaoUtils.isContainsByPK(subjectDao, subject)) {
			subject = subjectDao.persist(subject);
			test.setSubject(subject);
		}

		// persist test
		Test persistedTest = super.persist(test);

		/**
		 * Persist dependencies with questions.
		 */
		List<Question> questionsListOfTest = test.getQuestions();
		List<Question> persistedQauestionsListOfTest;
		if (questionsListOfTest != null) {
			QuestionDaoImpl questionsDao = (QuestionDaoImpl) DaoFactoryMySqlImpl.getInstance().getDao(connection,
					Question.class);
			// list with id of questions for adding the, to 3rd table
			List<Integer> questionsIdList = new ArrayList<>();
			persistedQauestionsListOfTest = new ArrayList<>();

			for (Question question : questionsListOfTest) {
				Question persistedQuestion = questionsDao.persist(question);
				questionsIdList.add(persistedQuestion.getId());
				persistedQauestionsListOfTest.add(persistedQuestion);
			}

			// and persist 3rd table
			TestQuestionsDaoImpl testQuestionsDao = (TestQuestionsDaoImpl) DaoFactoryMySqlImpl.getInstance()
					.getDao(connection, TestQuestions.class);
			TestQuestions testQuestions = new TestQuestions();
			testQuestions.setQuestionsList(questionsIdList);
			testQuestions.setTestId(persistedTest.getId());
			testQuestionsDao.persist(testQuestions);

			persistedTest.setQuestions(persistedQauestionsListOfTest);
		}
		return persistedTest;
	}

	/*
	 * Deletes Test object.
	 * 
	 * First delete dependency from -> TestQuestions Then delete Test from ->
	 * Test table
	 * 
	 * @see
	 * ua.nure.efimov.summarytask4.db.dao.jdbc.AbstractJDBCDAO#delete(ua.nure.
	 * efimov.summarytask4.db.dao.Identified)
	 */
	@Override
	public void delete(Test test) throws PersistException {

		TestQuestionsDaoImpl testQuestionsDao = (TestQuestionsDaoImpl) DaoFactoryMySqlImpl.getInstance()
				.getDao(connection, TestQuestions.class);

		TestQuestions testQuestions = new TestQuestions();
		testQuestions.setTestId(test.getId());

		List<Integer> questionsIdList = DaoUtils.getIdListFromIdentifiedList(test.getQuestions());
		testQuestions.setQuestionsList(questionsIdList);
		testQuestionsDao.delete(testQuestions);

		QuestionDaoImpl questionDaoImpl = (QuestionDaoImpl) DaoFactoryMySqlImpl.getInstance().getDao(connection,
				Question.class);
		for (Integer idQuestion : questionsIdList) {
			try {
				Question questForDelete = questionDaoImpl.getByPK(idQuestion);
				questionDaoImpl.delete(questForDelete);
			} catch (PersistException e) {
				// means that question used in another test, don't remove it
			}

		}

		super.delete(test);
	}

	/**
	 * Specified method for get all test by subject.
	 * 
	 * @param subject
	 *            is subject for finding
	 * @return list with tests
	 * @throws PersistException
	 *             if problem with db
	 */
	public List<Test> getBySubject(Subject subject) throws PersistException {
		if (subject == null) {
			return null;
		}
		List<Test> tests;
		String sql = getSelectQuery();
		sql += " WHERE subject_id = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, (int) subject.getId());
			ResultSet rs = statement.executeQuery();
			tests = parseResSet(rs);
		} catch (Exception e) {
			throw new PersistException(e);
		}

		if (tests == null || tests.isEmpty()) {
			return null;
		}

		return tests;
	}
}
