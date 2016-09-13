package ua.nure.efimov.summarytask4.db.dao.jdbc.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.nure.efimov.summarytask4.db.dao.PersistException;
import ua.nure.efimov.summarytask4.db.dao.jdbc.AbstractJDBCDAO;
import ua.nure.efimov.summarytask4.entity.TestQuestions;

public class TestQuestionsDaoImpl extends AbstractJDBCDAO<TestQuestions, java.lang.Integer> {

	private Connection connection;

	public TestQuestionsDaoImpl(Connection connection) {
		super(connection);
		this.connection = connection;
	}

	@Override
	public String getSelectQuery() {
		return "SELECT * FROM test_questions";
	}

	@Override
	public String getCreateQuery() {
		return "INSERT INTO test_questions (test_id, question_id) VALUES (?, ?);";
	}

	@Override
	public String getUpdateQuery() {
		return "UPDATE test_questions SET question_id = ? WHERE test_id = ?;";
	}

	@Override
	public String getDeleteQuery() {
		return "DELETE FROM test_questions WHERE test_id= ? AND question_id = ?;";
	}

	@Override
	protected List<TestQuestions> parseResSet(ResultSet rs) throws PersistException {
		List<TestQuestions> testQuestionsList = new ArrayList<>();
		try {
			while (rs.next()) {
				int testId = rs.getInt("test_id");
				TestQuestions testQuestions;

				if (DaoUtils.listContainsId(testQuestionsList, testId)) {
					testQuestions = DaoUtils.getFromListById(testQuestionsList, testId);
				} else {
					testQuestions = new TestQuestions();
					testQuestions.setTestId(testId);
					testQuestionsList.add(testQuestions);
				}

				List<Integer> questionsList = testQuestions.getQuestionsList();
				questionsList.add(rs.getInt("question_id"));
			}
		} catch (Exception e) {
			throw new PersistException(e);
		}
		return testQuestionsList;
	}

	@Override
	protected void prepareStForUpdateSetArgs(PreparedStatement statement, TestQuestions testQuestions)
			throws PersistException {

		throw new UnsupportedOperationException("Not available for TestQuestions entity.");
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement statement, TestQuestions testQuestions)
			throws PersistException {
		/**
		 * "INSERT INTO test_questions (test_id, question_id) VALUES (?, ?);"
		 */
		try {
			statement.setInt(1, testQuestions.getTestId());
			statement.setInt(2, testQuestions.getCurrentQuestionId());

		} catch (SQLException e) {
			throw new PersistException(e);
		}
	}

	/**
	 * This is dao for special many-to-many entity that hasn't id key. So find
	 * by test id.
	 */
	@Override
	public TestQuestions getByPK(Integer key) throws PersistException {
		List<TestQuestions> list;
		String sql = getSelectQuery();
		sql += " WHERE test_id = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, (int) key);
			ResultSet rs = statement.executeQuery();
			list = parseResSet(rs);
		} catch (Exception e) {
			throw new PersistException(e);
		}
		if (list == null || list.isEmpty()) {
			return null;
		}

		return list.iterator().next();
	}

	/**
	 * Hasn't id key, so use test_id like id for persist. last_insert_id will be
	 * connected to test_id.
	 */
	@Override
	public TestQuestions persist(TestQuestions testQuestions) throws PersistException {
		if (testQuestions == null) {
			throw new PersistException("Object is null.");
		}

		String sql = getCreateQuery();
		for (Integer questionId : testQuestions.getQuestionsList()) {
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				testQuestions.setCurrentQuestionId(questionId);
				prepareStatementForInsert(statement, testQuestions);
				statement.executeUpdate();
			} catch (Exception e) {
				throw new PersistException(e);
			}
		}

		return testQuestions;
	}

	/**
	 * Delete by 2 values: DELETE FROM test_questions WHERE test_id= ? AND
	 * question_id = ?.
	 */
	@Override
	public void delete(TestQuestions testQuestions) throws PersistException {
		String sql = getDeleteQuery();

		for (Integer questionId : testQuestions.getQuestionsList()) {

			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setInt(1, testQuestions.getTestId());
				testQuestions.setCurrentQuestionId(questionId);
				statement.setInt(2, testQuestions.getCurrentQuestionId());
				statement.executeUpdate();
			} catch (Exception e) {
				throw new PersistException(e);
			}
		}
	}

}
