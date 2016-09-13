package ua.nure.efimov.summarytask4.services;

import java.sql.Connection;
import java.util.List;

import ua.nure.efimov.summarytask4.db.dao.DaoFactory;
import ua.nure.efimov.summarytask4.db.dao.jdbc.impl.DaoFactoryMySqlImpl;
import ua.nure.efimov.summarytask4.db.dao.jdbc.impl.TestHistoryDaoImpl;
import ua.nure.efimov.summarytask4.entity.TestHistory;
import ua.nure.efimov.summarytask4.exception.BusinessException;

public class TestHistoryServise {
	/**
	 * Dao factory object.
	 */
	DaoFactory<Connection> factory;

	/**
	 * Save {@link TestHistory} object to db.
	 * 
	 * @param testHistory
	 *            is object for save
	 * @throws BusinessException
	 *             if smth happened
	 */
	public void saveToHistory(TestHistory testHistory) throws BusinessException {
		if (factory == null) {
			factory = DaoFactoryMySqlImpl.getInstance();
		}

		try (Connection connection = factory.getContext()) {

			TestHistoryDaoImpl testHistoryDao = (TestHistoryDaoImpl) factory.getDao(connection, TestHistory.class);
			testHistoryDao.persist(testHistory);
		} catch (Exception e) {
			throw new BusinessException(e);
		}

	}

	public List<TestHistory> getUserTestHistory(Integer userId) throws BusinessException {
		List<TestHistory> result = null;
		if (factory == null) {
			factory = DaoFactoryMySqlImpl.getInstance();
		}

		try (Connection connection = factory.getContext()) {
			TestHistoryDaoImpl testHistoryDao = (TestHistoryDaoImpl) factory.getDao(connection, TestHistory.class);
			result = testHistoryDao.getByUserId(userId);
		} catch (Exception e) {
			throw new BusinessException(e);
		}

		return result;
	}

}
