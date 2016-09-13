package ua.nure.efimov.summarytask4.db.dao.jdbc.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.nure.efimov.summarytask4.beans.SuccessRate;
import ua.nure.efimov.summarytask4.constants.ComonConstants;
import ua.nure.efimov.summarytask4.db.dao.PersistException;
import ua.nure.efimov.summarytask4.db.dao.jdbc.AbstractJDBCDAO;
import ua.nure.efimov.summarytask4.entity.Role;
import ua.nure.efimov.summarytask4.entity.TestHistory;
import ua.nure.efimov.summarytask4.entity.User;
import ua.nure.efimov.summarytask4.entity.UserRoles;

public class UserDaoImpl extends AbstractJDBCDAO<User, Integer> {
	private Connection connection;
	private static final String totalResultsByUserSql = "SELECT difficulty, avg(test_result) as 'AVG', count(test_id) as 'TESTS_AMOUNT'"
			+ "  FROM testing.tests_history join tests on tests_history.test_id=tests.id "
			+ "where user_id = ? group by difficulty;";

	public UserDaoImpl(Connection connection) {
		super(connection);
		this.connection = connection;
	}

	/**
	 * For constraining access to set id.
	 * 
	 * @author Alexandr Efimov
	 *
	 */
	private class PersistUser extends User {
		private static final long serialVersionUID = -4089871260069534150L;

		public void setId(int id) {
			super.setId(id);
		}
	}

	@Override
	public String getSelectQuery() {
		return "SELECT id, login, password, first_name, last_name, email, is_verified, is_banned "
				+ "FROM testing.users";
	}

	@Override
	public String getCreateQuery() {
		return "INSERT INTO testing.users (login, password, first_name, last_name, email, is_verified, is_banned)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?);";
	}

	@Override
	public String getUpdateQuery() {
		return "UPDATE testing.users SET login = ?, password = ?, first_name = ?, "
				+ "last_name = ?, email = ?, is_verified = ?, is_banned = ? WHERE id = ?;";
	}

	@Override
	public String getDeleteQuery() {
		return "DELETE FROM testing.users WHERE id = ?;";
	}

	@Override
	protected List<User> parseResSet(ResultSet rs) throws PersistException {
		List<User> usersList = new ArrayList<>();

		try {
			while (rs.next()) {
				User user = new PersistUser();
				user.setId(rs.getInt("id"));
				String login = rs.getString("login");
				user.setLogin(login);
				user.setPassword(rs.getString("password"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setEmail(rs.getString("email"));
				user.setVerified(rs.getBoolean("is_verified"));
				user.setBanned(rs.getBoolean("is_banned"));

				/**
				 * Get user roles.
				 */
				UserRolesDaoImpl userRolesDao = (UserRolesDaoImpl) DaoFactoryMySqlImpl.getInstance().getDao(connection,
						UserRoles.class);
				List<Role> userRolesList = userRolesDao.getRolesByLogin(login);

				user.setRoles(userRolesList);
				usersList.add(user);
			}
		} catch (Exception e) {
			throw new PersistException(e);
		}
		return usersList;
	}

	@Override
	protected void prepareStForUpdateSetArgs(PreparedStatement statement, User user) throws PersistException {

		/**
		 * "UPDATE testing.users SET login = ?, password = ?, first_name = ?, "
		 * + "last_name = ?, email = ?, is_verified = ?, is_banned = ? WHERE id
		 * = ?;"
		 */

		try {
			statement.setString(1, user.getLogin());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getFirstName());
			statement.setString(4, user.getLastName());
			statement.setString(5, user.getEmail());
			statement.setBoolean(6, user.getIsVerified());
			statement.setBoolean(7, user.isBanned());

			statement.setInt(8, user.getId());

		} catch (SQLException e) {
			throw new PersistException(e);
		}
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement statement, User user) throws PersistException {
		/**
		 * "INSERT INTO testing.users (login, password, first_name, last_name,
		 * email, is_verified, is_banned)" + " VALUES (?, ?, ?, ?, ?, ?, ?);
		 */
		try {
			statement.setString(1, user.getLogin());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getFirstName());
			statement.setString(4, user.getLastName());
			statement.setString(5, user.getEmail());
			statement.setBoolean(6, user.getIsVerified());
			statement.setBoolean(7, user.isBanned());

		} catch (SQLException e) {
			throw new PersistException(e);
		}

	}

	/*
	 * Delete user from db. First deletes dependencies and then from user table.
	 * 
	 * @see
	 * ua.nure.efimov.summarytask4.db.dao.jdbc.AbstractJDBCDAO#delete(ua.nure.
	 * efimov.summarytask4.db.dao.Identified)
	 */
	@Override
	public void delete(User user) throws PersistException {
		/**
		 * Delete dependencies from UserRoles.
		 */
		List<Role> rolesList = user.getRoles();
		if (rolesList != null) {
			UserRolesDaoImpl userRolesDao = (UserRolesDaoImpl) DaoFactoryMySqlImpl.getInstance().getDao(connection,
					UserRoles.class);
			List<UserRoles> allUserRolesList = userRolesDao.getAll();

			for (UserRoles userRole : allUserRolesList) {
				if (userRole.getLogin().equals(user.getLogin())) {
					userRolesDao.delete(userRole);
				}
			}
		}

		/**
		 * Delete dependencies from Test History.
		 */
		TestHistoryDaoImpl testHistoryDao = (TestHistoryDaoImpl) DaoFactoryMySqlImpl.getInstance().getDao(connection,
				TestHistory.class);
		List<TestHistory> allTestHistory = testHistoryDao.getAll();
		for (TestHistory testHistory : allTestHistory) {
			if (testHistory.getUserId() == user.getId().intValue()) {
				testHistoryDao.delete(testHistory);
			}
		}

		super.delete(user);
	}

	/*
	 * Persist user object. Also add dependencies to userRoles table.
	 * 
	 * @see
	 * ua.nure.efimov.summarytask4.db.dao.jdbc.AbstractJDBCDAO#persist(ua.nure.
	 * efimov.summarytask4.db.dao.Identified)
	 */
	@Override
	public User persist(User user) throws PersistException {
		User persistedUser = super.persist(user);

		UserRolesDaoImpl userRolesDao = (UserRolesDaoImpl) DaoFactoryMySqlImpl.getInstance().getDao(connection,
				UserRoles.class);

		List<Role> roles = user.getRoles();
		if (!roles.isEmpty()) {

			for (Role role : roles) {
				UserRoles userRoles = new UserRoles();
				userRoles.setLogin(user.getLogin());
				userRoles.setRoleName(role.getRoleName());

				userRolesDao.persist(userRoles);
			}
		}

		return persistedUser;
	}

	/**
	 * Specified method for get user by login. If no user -> return null.
	 * 
	 * @param login
	 *            is login
	 * @return user
	 * @throws PersistException
	 *             if smth happened
	 */
	public User getByLogin(String login) throws PersistException {
		List<User> all = getAll();
		for (User user : all) {
			if (user.getLogin().equals(login)) {
				return user;
			}
		}
		return null;
	}

	/**
	 * Specified method for get user PHOTO(BLOB object) by user id.
	 * 
	 * @param id
	 *            is user id
	 * @return byte[] array with photo
	 * @throws PersistException
	 *             if smth happened
	 */
	public byte[] getUserPhoto(int id) throws PersistException {
		byte[] imageBytes = null;
		String sql = "SELECT photo FROM testing.users WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			rs.next();
			Blob imageBlob = rs.getBlob("photo");
			if (imageBlob != null) {
				imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
			}
		} catch (Exception e) {
			throw new PersistException(e);
		}
		return imageBytes;
	}

	/**
	 * Specified method for get user success rate
	 * 
	 * @param userId
	 *            is id of user
	 * @return object {@link SuccessRate} with results
	 * @throws PersistException
	 *             if smth happened
	 */
	public List<SuccessRate> getTotalSuccessRate(Integer userId) throws PersistException {
		List<SuccessRate> listWithRates = new ArrayList<>();

		try (PreparedStatement statement = connection.prepareStatement(totalResultsByUserSql)) {
			statement.setInt(1, userId);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {

				int difficulty = rs.getInt(ComonConstants.DIFFICALTY_COLUMN.getValue());
				double totalSuccess = rs.getDouble(ComonConstants.AVERAGE_COLUMN.getValue());
				int testsAmount = rs.getInt(ComonConstants.TOTAL_TESTS_COLUMN.getValue());

				SuccessRate successRate = new SuccessRate();
				/**
				 * Round double
				 */
				BigDecimal succesRate = new BigDecimal(totalSuccess);
				succesRate = succesRate.setScale(2, RoundingMode.DOWN);
				// get double value
				successRate.setDifficulty(difficulty);
				successRate.setSuccessRate(succesRate.doubleValue());
				successRate.setTestsPassedAmount(testsAmount);

				listWithRates.add(successRate);

			}
		} catch (Exception e) {
			throw new PersistException(e);
		}

		return listWithRates;
	}

}
