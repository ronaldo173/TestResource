package ua.nure.efimov.summarytask4.db.dao.jdbc.impl;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import ua.nure.efimov.summarytask4.db.DbHelper;
import ua.nure.efimov.summarytask4.db.dao.DaoFactory;
import ua.nure.efimov.summarytask4.db.dao.GenericDao;
import ua.nure.efimov.summarytask4.db.dao.Identified;
import ua.nure.efimov.summarytask4.db.dao.PersistException;
import ua.nure.efimov.summarytask4.entity.Answer;
import ua.nure.efimov.summarytask4.entity.Question;
import ua.nure.efimov.summarytask4.entity.Role;
import ua.nure.efimov.summarytask4.entity.Subject;
import ua.nure.efimov.summarytask4.entity.Test;
import ua.nure.efimov.summarytask4.entity.TestHistory;
import ua.nure.efimov.summarytask4.entity.TestQuestions;
import ua.nure.efimov.summarytask4.entity.User;
import ua.nure.efimov.summarytask4.entity.UserRoles;

/**
 * Mysql DAO Factory. Use {@link getInstance()} for get DAO class for any
 * instance.
 * 
 * @author Alexandr Efimov
 *
 */
public class DaoFactoryMySqlImpl implements DaoFactory<Connection> {

	/**
	 * Instance of fabric.
	 */
	private static volatile DaoFactoryMySqlImpl instance;

	/**
	 * Map with dao implementations.
	 */
	private Map<Class<? extends Identified<Integer>>, DaoCreator<Connection>> creators;

	/**
	 * Get connection to database.
	 * 
	 * @return {@link Connection} sql
	 */
	public Connection getContext() throws PersistException {
		return DbHelper.getConnection();
	}

	@Override
	public GenericDao<? extends Identified<Integer>, Integer> getDao(Connection connection, Class<?> dtoClass)
			throws PersistException {
		DaoCreator<Connection> creator = creators.get(dtoClass);
		if (creator == null) {
			throw new PersistException("Dao object for " + dtoClass + " not found.");
		}
		return creator.create(connection);
	}

	private DaoFactoryMySqlImpl() {
		creators = new HashMap<>();

		creators.put(Answer.class, new DaoCreator<Connection>() {
			@Override
			public GenericDao<? extends Identified<Integer>, Integer> create(Connection connection) {
				return new AnswerDaoImpl(connection);
			}
		});

		creators.put(Subject.class, new DaoCreator<Connection>() {
			@Override
			public GenericDao<? extends Identified<Integer>, Integer> create(Connection connection) {
				return new SubjectDaoImpl(connection);
			}
		});

		creators.put(Question.class, new DaoCreator<Connection>() {

			@Override
			public GenericDao<? extends Identified<Integer>, Integer> create(Connection connection) {
				return new QuestionDaoImpl(connection);
			}
		});

		creators.put(Test.class, new DaoCreator<Connection>() {

			@Override
			public GenericDao<? extends Identified<Integer>, Integer> create(Connection connection) {
				return new TestDaoImpl(connection);
			}
		});

		creators.put(TestQuestions.class, new DaoCreator<Connection>() {

			@Override
			public GenericDao<? extends Identified<Integer>, Integer> create(Connection connection) {
				return new TestQuestionsDaoImpl(connection);
			}
		});

		creators.put(Role.class, new DaoCreator<Connection>() {

			@Override
			public GenericDao<? extends Identified<Integer>, Integer> create(Connection connection) {
				return new RoleDaoImpl(connection);
			}
		});

		creators.put(UserRoles.class, new DaoCreator<Connection>() {

			@Override
			public GenericDao<? extends Identified<Integer>, Integer> create(Connection connection) {
				return new UserRolesDaoImpl(connection);
			}
		});

		creators.put(User.class, new DaoCreator<Connection>() {

			@Override
			public GenericDao<? extends Identified<Integer>, Integer> create(Connection connection) {
				return new UserDaoImpl(connection);
			}
		});
		creators.put(TestHistory.class, new DaoCreator<Connection>() {

			@Override
			public GenericDao<? extends Identified<Integer>, Integer> create(Connection connection) {
				return new TestHistoryDaoImpl(connection);
			}
		});

	}

	public static DaoFactoryMySqlImpl getInstance() {
		DaoFactoryMySqlImpl localInstance = instance;
		if (localInstance == null) {
			synchronized (DaoFactoryMySqlImpl.class) {
				localInstance = instance;
				if (localInstance == null) {
					instance = localInstance = new DaoFactoryMySqlImpl();
				}
			}
		}
		return localInstance;
	}

}