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

public class RoleDaoImpl extends AbstractJDBCDAO<Role, Integer> {

	public RoleDaoImpl(Connection connection) {
		super(connection);
	}

	/**
	 * For constraining access to set id.
	 * 
	 * @author Alexandr Efimov
	 *
	 */
	private class PersistRole extends Role {
		private static final long serialVersionUID = -7933517489636852678L;

		public void setId(int id) {
			super.setId(id);
		}
	}

	@Override
	public String getSelectQuery() {
		return "SELECT * FROM testing.roles";
	}

	@Override
	public String getCreateQuery() {
		return "INSERT INTO testing.roles (role, description) VALUES (?, ?);";
	}

	@Override
	public String getUpdateQuery() {
		return "UPDATE testing.roles SET role = ?, description = ? WHERE id = ?;";
	}

	@Override
	public String getDeleteQuery() {
		return "DELETE FROM testing.roles WHERE id= ?;";
	}

	@Override
	protected List<Role> parseResSet(ResultSet rs) throws PersistException {
		List<Role> rolesList = new ArrayList<>();
		try {
			while (rs.next()) {
				Role role = new PersistRole();
				role.setId(rs.getInt("id"));
				role.setRoleName(rs.getString("role"));
				role.setDescription(rs.getString("description"));

				rolesList.add(role);
			}
		} catch (Exception e) {
			throw new PersistException(e);
		}
		return rolesList;
	}

	@Override
	protected void prepareStForUpdateSetArgs(PreparedStatement statement, Role role) throws PersistException {
		/**
		 * "UPDATE testing.roles SET role = ?, description = ? WHERE id = ?;"
		 */
		try {
			statement.setString(1, role.getRoleName());
			statement.setString(2, role.getDescription());

			statement.setInt(3, role.getId());
		} catch (SQLException e) {
			throw new PersistException(e);
		}

	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement statement, Role role) throws PersistException {
		/**
		 * "INSERT INTO testing.roles (role, description) VALUES (?, ?);"
		 */
		try {
			statement.setString(1, role.getRoleName());
			statement.setString(2, role.getDescription());

		} catch (SQLException e) {
			throw new PersistException(e);
		}

	}

}
