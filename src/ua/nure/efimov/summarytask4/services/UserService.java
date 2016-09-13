package ua.nure.efimov.summarytask4.services;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.List;

import ua.nure.efimov.summarytask4.beans.SuccessRate;
import ua.nure.efimov.summarytask4.db.dao.DaoFactory;
import ua.nure.efimov.summarytask4.db.dao.jdbc.impl.DaoFactoryMySqlImpl;
import ua.nure.efimov.summarytask4.db.dao.jdbc.impl.UserDaoImpl;
import ua.nure.efimov.summarytask4.entity.User;
import ua.nure.efimov.summarytask4.exception.BusinessException;

/**
 * Service for work with user. Load them, modify, etc.
 * 
 * @author Alexandr Efimov
 *
 */
public class UserService {
	private static final String DIGEST_TYPE = "MD5";
	/**
	 * Dao factory object.
	 */
	DaoFactory<Connection> factory;

	public User getUserByLogin(String login) throws BusinessException {
		if (factory == null) {
			factory = DaoFactoryMySqlImpl.getInstance();
		}

		try (Connection connection = factory.getContext()) {

			UserDaoImpl usersDao = (UserDaoImpl) factory.getDao(connection, User.class);

			return usersDao.getByLogin(login);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	/**
	 * Load user photo by id. If no photo or some error return null.
	 * 
	 * @param id
	 *            is id of user
	 * @throws BusinessException
	 *             if smth happened
	 */
	public byte[] loadUserPhoto(int id) throws BusinessException {
		if (factory == null) {
			factory = DaoFactoryMySqlImpl.getInstance();
		}
		byte[] userPhoto;

		try (Connection connection = factory.getContext()) {

			UserDaoImpl usersDao = (UserDaoImpl) factory.getDao(connection, User.class);
			userPhoto = usersDao.getUserPhoto(id);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		return userPhoto;
	}

	/**
	 * Encrypt password to hashsum.
	 * 
	 * @param passwordNotEncr
	 *            is not encr password
	 * @return is encrypted password
	 * @throws BusinessException
	 *             if smth happened
	 */
	public String encryptPassword(String passwordNotEncr) throws BusinessException {
		MessageDigest messageDigest = null;
		byte[] digest = new byte[0];
		String hexSum = null;

		try {
			messageDigest = MessageDigest.getInstance(DIGEST_TYPE);
			messageDigest.reset();
			messageDigest.update(passwordNotEncr.getBytes());
			digest = messageDigest.digest();

			BigInteger bigInt = new BigInteger(1, digest);
			hexSum = bigInt.toString(16);

			while (hexSum.length() < 32) {
				hexSum = "0" + hexSum;
			}
		} catch (NoSuchAlgorithmException e) {
			throw new BusinessException();
		}

		return hexSum;
	}

	/**
	 * Save user object to db.
	 * 
	 * @param user
	 *            is user
	 * @throws BusinessException
	 *             if smth happened
	 */
	public void persistUser(User user) throws BusinessException {
		if (factory == null) {
			factory = DaoFactoryMySqlImpl.getInstance();
		}

		try (Connection connection = factory.getContext()) {

			UserDaoImpl usersDao = (UserDaoImpl) factory.getDao(connection, User.class);
			usersDao.persist(user);
		} catch (Exception e) {
			throw new BusinessException(e);
		}

	}

	/**
	 * Get all users.
	 * 
	 * @return list with users
	 * @throws BusinessException
	 */
	public List<User> loadUsers() throws BusinessException {
		if (factory == null) {
			factory = DaoFactoryMySqlImpl.getInstance();
		}

		try (Connection connection = factory.getContext()) {

			UserDaoImpl usersDao = (UserDaoImpl) factory.getDao(connection, User.class);
			return usersDao.getAll();
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	/**
	 * Ban user by id. Set field isBanned to the true.
	 * 
	 * @param idUser
	 * @param isBanned
	 *            is value(true->ban)
	 * @throws BusinessException
	 */
	public void banUserById(int idUser, boolean isBanned) throws BusinessException {
		if (factory == null) {
			factory = DaoFactoryMySqlImpl.getInstance();
		}

		try (Connection connection = factory.getContext()) {

			UserDaoImpl usersDao = (UserDaoImpl) factory.getDao(connection, User.class);
			User userByPK = usersDao.getByPK(idUser);

			userByPK.setBanned(isBanned);
			usersDao.update(userByPK);
		} catch (Exception e) {
			throw new BusinessException(e);
		}

	}

	/**
	 * Confirm mail of user. Set field of user to true.
	 * 
	 * @param idUserConf
	 * @throws BusinessException
	 */
	public void confirmUserMailById(int idUserConf) throws BusinessException {
		if (factory == null) {
			factory = DaoFactoryMySqlImpl.getInstance();
		}

		try (Connection connection = factory.getContext()) {

			UserDaoImpl usersDao = (UserDaoImpl) factory.getDao(connection, User.class);
			User userByPK = usersDao.getByPK(idUserConf);

			userByPK.setVerified(true);
			usersDao.update(userByPK);
		} catch (Exception e) {
			throw new BusinessException(e);
		}

	}

	/**
	 * Load from db total raiting by user id
	 * 
	 * @param userId
	 *            is id of user
	 * @return list with results
	 * @throws BusinessException
	 */
	public List<SuccessRate> loadTotalSuccessRate(Integer userId) throws BusinessException {
		if (factory == null) {
			factory = DaoFactoryMySqlImpl.getInstance();
		}

		try (Connection connection = factory.getContext()) {

			UserDaoImpl usersDao = (UserDaoImpl) factory.getDao(connection, User.class);
			return usersDao.getTotalSuccessRate(userId);

		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}
}
