package ua.nure.efimov.summarytask4.db.dao.jdbc.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.nure.efimov.summarytask4.db.dao.PersistException;
import ua.nure.efimov.summarytask4.db.dao.jdbc.AbstractJDBCDAO;
import ua.nure.efimov.summarytask4.entity.Role;
import ua.nure.efimov.summarytask4.entity.UserRoles;

public class UserRolesDaoImpl extends AbstractJDBCDAO<UserRoles, Integer> {

	private Connection connection;

	public UserRolesDaoImpl(Connection connection) {
		super(connection);
		this.connection = connection;
	}

	/**
	 * For constraining access to set id.
	 * 
	 * @author Alexandr Efimov
	 *
	 */
	private class PersistUserRoles extends UserRoles {
		private static final long serialVersionUID = -7755613038051936692L;

		public void setId(int id) {
			super.setId(id);
		}
	}

	@Override
	public String getSelectQuery() {
		return "SELECT * FROM testing.users_roles";
	}

	@Override
	public String getCreateQuery() {
		return "INSERT INTO testing.users_roles (login, role) VALUES (?, ?);";
	}

	@Override
	public String getUpdateQuery() {
		return "UPDATE testing.users_roles SET login = ?, role = ?  WHERE id = ?;";
	}

	@Override
	public String getDeleteQuery() {
		return "DELETE FROM testing.users_roles WHERE id= ?;";
	}

	@Override
	protected List<UserRoles> parseResSet(ResultSet rs) throws PersistException {
		List<UserRoles> userRolesList = new ArrayList<>();
		try {
			while (rs.next()) {
				UserRoles userRoles = new PersistUserRoles();
				userRoles.setId(rs.getInt("id"));
				userRoles.setLogin(rs.getString("login"));
				userRoles.setRoleName(rs.getString("role"));

				userRolesList.add(userRoles);
			}
		} catch (Exception e) {
			throw new PersistException(e);
		}
		return userRolesList;
	}

	@Override
	protected void prepareStForUpdateSetArgs(PreparedStatement statement, UserRoles userRoles) throws PersistException {
		/**
		 * "UPDATE testing.users_roles SET login = ?, role = ? WHERE id = ?;";
		 */
		try {
			statement.setString(1, userRoles.getLogin());
			statement.setString(2, userRoles.getRoleName());

			statement.setInt(3, userRoles.getId());
		} catch (SQLException e) {
			throw new PersistException(e);
		}
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement statement, UserRoles userRoles) throws PersistException {
		/**
		 * "INSERT INTO testing.users_roles (login, role) VALUES (?, ?);"
		 */

		try {
			statement.setString(1, userRoles.getLogin());
			statement.setString(2, userRoles.getRoleName());

		} catch (SQLException e) {
			throw new PersistException(e);
		}
	}

	/**
	 * Get list with roles by user {@link login}
	 * 
	 * @param login
	 *            is login of user
	 * @return list with roles
	 * @throws PersistException
	 *             if smth with db
	 */
	public List<Role> getRolesByLogin(String login) throws PersistException {
		List<String> roleNameList = new ArrayList<>();
		List<Role> rolesList = new ArrayList<>();

		/**
		 * Get all role names for user with login like param.
		 */
		for (UserRoles userRole : getAll()) {
			if (userRole.getLogin().equals(login)) {
				roleNameList.add(userRole.getRoleName());
			}
		}

		/**
		 * Get roles.
		 */
		RoleDaoImpl rolesDao = (RoleDaoImpl) DaoFactoryMySqlImpl.getInstance().getDao(connection, Role.class);
		List<Role> allRolesList = rolesDao.getAll();
		for (String roleName : roleNameList) {
			for (Role role : allRolesList) {
				if (role.getRoleName().equals(roleName)) {
					rolesList.add(role);
				}
			}
		}
		return rolesList;
	}

}
