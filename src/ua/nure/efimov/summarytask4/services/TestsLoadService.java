package ua.nure.efimov.summarytask4.services;

import java.sql.Connection;
import java.util.List;

import ua.nure.efimov.summarytask4.db.dao.DaoFactory;
import ua.nure.efimov.summarytask4.db.dao.jdbc.impl.DaoFactoryMySqlImpl;
import ua.nure.efimov.summarytask4.db.dao.jdbc.impl.TestDaoImpl;
import ua.nure.efimov.summarytask4.entity.Subject;
import ua.nure.efimov.summarytask4.entity.Test;
import ua.nure.efimov.summarytask4.exception.BusinessException;

/**
 * Service for work with tests. Load them, sort, etc.
 * 
 * @author Alexandr Efimov
 *
 */
public class TestsLoadService {
	/**
	 * Dao factory object.
	 */
	private DaoFactory<Connection> factory;

	/**
	 * Get list with tests.
	 * 
	 * @return list with tests
	 * @throws BusinessException
	 *             if smth happened
	 */
	public List<Test> getTests() throws BusinessException {
		if (factory == null) {
			factory = DaoFactoryMySqlImpl.getInstance();
		}

		try (Connection connection = factory.getContext()) {

			TestDaoImpl testsDao = (TestDaoImpl) factory.getDao(connection, Test.class);
			List<Test> allTests = testsDao.getAll();

			new TestSortingsService().sortTestsBySelectedSortType(allTests);
			return allTests;
		} catch (Exception e) {
			throw new BusinessException(e);
		}

	}

	/**
	 * Get list with tests by subject.
	 * 
	 * @param subject
	 *            is subject.
	 * @return list with tests
	 * @throws BusinessException
	 *             if smth happened
	 */
	public List<Test> getTestsBySubject(Subject subject) throws BusinessException {
		if (factory == null) {
			factory = DaoFactoryMySqlImpl.getInstance();
		}

		try (Connection connection = factory.getContext()) {

			TestDaoImpl testsDao = (TestDaoImpl) factory.getDao(connection, Test.class);

			List<Test> testsBySubject = testsDao.getBySubject(subject);
			new TestSortingsService().sortTestsBySelectedSortType(testsBySubject);
			return testsBySubject;
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	/**
	 * Get {@link Test} object by test.id.
	 * 
	 * @param testIndex
	 *            is id of test
	 * @return {@link Test} object
	 * @throws BusinessException
	 *             if smth happened
	 */
	public Test getTestById(int testIndex) throws BusinessException {
		if (factory == null) {
			factory = DaoFactoryMySqlImpl.getInstance();
		}

		try (Connection connection = factory.getContext()) {

			TestDaoImpl testsDao = (TestDaoImpl) factory.getDao(connection, Test.class);

			return testsDao.getByPK(testIndex);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	/**
	 * Remove test by id.
	 * 
	 * @param testEditId
	 * @throws BusinessException
	 */
	public void deleteTest(int testEditId) throws BusinessException {
		if (factory == null) {
			factory = DaoFactoryMySqlImpl.getInstance();
		}

		try (Connection connection = factory.getContext()) {

			TestDaoImpl testsDao = (TestDaoImpl) factory.getDao(connection, Test.class);
			Test testByPK = testsDao.getByPK(testEditId);

			testsDao.delete(testByPK);
		} catch (Exception e) {
			throw new BusinessException(e);
		}

	}

	/**
	 * Update test by {@link Test} object.
	 * 
	 * @param test
	 *            is test obj
	 * @throws BusinessException
	 */
	public void updateTest(Test test) throws BusinessException {
		if (factory == null) {
			factory = DaoFactoryMySqlImpl.getInstance();
		}

		try (Connection connection = factory.getContext()) {

			TestDaoImpl testsDao = (TestDaoImpl) factory.getDao(connection, Test.class);

			testsDao.update(test);
		} catch (Exception e) {
			throw new BusinessException(e);
		}

	}

	/**
	 * Save test to db.
	 * 
	 * @param test
	 * @throws BusinessException
	 */
	public void saveTest(Test test) throws BusinessException {
		if (factory == null) {
			factory = DaoFactoryMySqlImpl.getInstance();
		}

		try (Connection connection = factory.getContext()) {

			TestDaoImpl testsDao = (TestDaoImpl) factory.getDao(connection, Test.class);

			testsDao.persist(test);
		} catch (Exception e) {
			throw new BusinessException(e);
		}

	}

}
