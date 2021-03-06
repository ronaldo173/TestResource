package ua.nure.efimov.summarytask4.db.dao.jdbc.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.nure.efimov.summarytask4.db.dao.PersistException;
import ua.nure.efimov.summarytask4.db.dao.jdbc.AbstractJDBCDAO;
import ua.nure.efimov.summarytask4.entity.Subject;
import ua.nure.efimov.summarytask4.entity.Test;

public class SubjectDaoImpl extends AbstractJDBCDAO<Subject, Integer> {

	private Connection connection;

	/**
	 * For constraining acces to set id.
	 * 
	 * @author Alexandr Efimov
	 *
	 */
	private class PersistSubject extends Subject {
		private static final long serialVersionUID = -1092970486753679970L;

		public void setId(int id) {
			super.setId(id);
		}
	}

	public SubjectDaoImpl(Connection connection) {
		super(connection);
		this.connection = connection;
	}

	@Override
	public String getSelectQuery() {
		return "SELECT * FROM subjects";
	}

	@Override
	public String getCreateQuery() {
		return "INSERT INTO subjects (subject) VALUES (?);";
	}

	@Override
	public String getUpdateQuery() {
		return "UPDATE subjects SET subject = ? WHERE id = ?;";
	}

	@Override
	public String getDeleteQuery() {
		return "DELETE FROM subjects WHERE id= ?;";
	}

	@Override
	protected List<Subject> parseResSet(ResultSet rs) throws PersistException {
		List<Subject> subjectsList = new ArrayList<>();
		try {
			while (rs.next()) {
				PersistSubject subject = new PersistSubject();
				subject.setId(rs.getInt("id"));
				subject.setSubject(rs.getString("subject"));

				subjectsList.add(subject);
			}
		} catch (Exception e) {
			throw new PersistException(e);
		}
		return subjectsList;
	}

	@Override
	protected void prepareStForUpdateSetArgs(PreparedStatement statement, Subject subject) throws PersistException {
		/**
		 * "UPDATE subjects SET subject = ? WHERE id = ?;"
		 */
		try {
			statement.setString(1, subject.getSubject());

			statement.setInt(2, subject.getId());
		} catch (SQLException e) {
			throw new PersistException(e);
		}
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement statement, Subject subject) throws PersistException {
		/**
		 * "INSERT INTO subjects (subject) VALUES (?);"
		 */
		try {
			statement.setString(1, subject.getSubject());

		} catch (SQLException e) {
			throw new PersistException(e);
		}
	}

	/*
	 * Delete subject. If test has this subject deletes all relevant tests.
	 * 
	 * @see
	 * ua.nure.efimov.summarytask4.db.dao.jdbc.AbstractJDBCDAO#delete(ua.nure.
	 * efimov.summarytask4.db.dao.Identified)
	 */
	@Override
	public void delete(Subject subject) throws PersistException {

		// remove test
		TestDaoImpl testDao = (TestDaoImpl) DaoFactoryMySqlImpl.getInstance().getDao(connection, Test.class);
		List<Test> testsBySubject = testDao.getBySubject(subject);

		if (testsBySubject != null) {
			for (Test test : testsBySubject) {
				testDao.delete(test);
			}
		}
		// remove subject
		super.delete(subject);
	}

	/*
	 * Persist object without checking existing of object id.
	 * 
	 * @see
	 * ua.nure.efimov.summarytask4.db.dao.jdbc.AbstractJDBCDAO#persist(ua.nure.
	 * efimov.summarytask4.db.dao.Identified)
	 */
	@Override
	public Subject persist(Subject object) throws PersistException {
		if (object == null) {
			throw new PersistException(object.getClass() + " Object is null.");
		}

		Subject persistInstance;
		String sql = getCreateQuery();
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			prepareStatementForInsert(statement, object);
			statement.executeUpdate();
		} catch (Exception e) {
			throw new PersistException(e);
		}

		sql = getSelectQuery() + " WHERE id = last_insert_id();";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet rs = statement.executeQuery();
			List<Subject> list = parseResSet(rs);
			if ((list == null) || (list.size() != 1)) {
				throw new PersistException("Exception on findByPK new persist data.");
			}
			persistInstance = list.iterator().next();
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistException(e);
		}
		return persistInstance;
	}

}
