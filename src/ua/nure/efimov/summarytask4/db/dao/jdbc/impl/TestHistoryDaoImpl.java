package ua.nure.efimov.summarytask4.db.dao.jdbc.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import ua.nure.efimov.summarytask4.db.dao.PersistException;
import ua.nure.efimov.summarytask4.db.dao.jdbc.AbstractJDBCDAO;
import ua.nure.efimov.summarytask4.entity.Test;
import ua.nure.efimov.summarytask4.entity.TestHistory;

public class TestHistoryDaoImpl extends AbstractJDBCDAO<TestHistory, Integer> {
	private Connection connection;

	public TestHistoryDaoImpl(Connection connection) {
		super(connection);
		this.connection = connection;
	}

	@Override
	public String getSelectQuery() {
		return "SELECT * FROM testing.tests_history";
	}

	@Override
	public String getCreateQuery() {
		return "INSERT INTO testing.tests_history (test_id, test_result, time_test_pass, user_id) VALUES (?, ?, ?, ?);";
	}

	@Override
	public String getUpdateQuery() {
		return "UPDATE testing.tests_history SET test_id = ?, test_result  = ?, time_test_pass = ?, user_id = ? WHERE id = ?;";
	}

	@Override
	public String getDeleteQuery() {
		return "DELETE FROM testing.tests_history WHERE id = ?;";
	}

	@Override
	protected List<TestHistory> parseResSet(ResultSet rs) throws PersistException {
		List<TestHistory> result = new ArrayList<>();
		try {
			// Dao for get test by id.
			TestDaoImpl testDao = (TestDaoImpl) DaoFactoryMySqlImpl.getInstance().getDao(connection, Test.class);
			while (rs.next()) {
				TestHistory testHistory = new TestHistory();
				testHistory.setId(rs.getInt("id"));
				int testId = rs.getInt("test_id");
				Test testByPk = testDao.getByPK(testId);

				testHistory.setTest(testByPk);
				testHistory.setTestResult(rs.getInt("test_result"));
				testHistory.setTimeTestPass(rs.getDate("time_test_pass"));

				result.add(testHistory);
			}
		} catch (Exception e) {
			throw new PersistException(e);
		}
		return result;
	}

	@Override
	protected void prepareStForUpdateSetArgs(PreparedStatement statement, TestHistory testHistory)
			throws PersistException {
		/**
		 * "UPDATE testing.tests_history SET test_id = ?, test_result = ?,
		 * time_test_pass = ?, user_id = ? WHERE id = ?;"
		 */

		try {
			Timestamp sqlDate = DaoUtils.convertDateToTimestamp(testHistory.getTimeTestPass());

			statement.setInt(1, testHistory.getTest().getId());
			statement.setInt(2, testHistory.getTestResult());
			statement.setTimestamp(3, sqlDate);
			statement.setInt(4, testHistory.getUserId());

			statement.setInt(5, testHistory.getId());
		} catch (SQLException e) {
			throw new PersistException(e);
		}
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement statement, TestHistory testHistory)
			throws PersistException {
		/**
		 * "INSERT INTO testing.tests_history (test_id, test_result,
		 * time_test_pass, user_id) VALUES (?, ?, ?, ?);";
		 */

		try {
			Timestamp sqlDate = DaoUtils.convertDateToTimestamp(testHistory.getTimeTestPass());

			statement.setInt(1, testHistory.getTest().getId());
			statement.setInt(2, testHistory.getTestResult());
			statement.setTimestamp(3, sqlDate);
			statement.setInt(4, testHistory.getUserId());

		} catch (SQLException e) {
			throw new PersistException(e);
		}
	}

	public List<TestHistory> getByUserId(Integer userId) throws PersistException {
		List<TestHistory> list = null;

		String sql = getSelectQuery() + " WHERE user_id = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, userId);

			ResultSet rs = statement.executeQuery();
			list = parseResSet(rs);
		} catch (Exception e) {
			throw new PersistException(e);
		}
		return list;
	}

}
