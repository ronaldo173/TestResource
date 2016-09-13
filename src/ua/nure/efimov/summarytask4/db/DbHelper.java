package ua.nure.efimov.summarytask4.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import ua.nure.efimov.summarytask4.utils.LogUtils;

public class DbHelper {

	private static Connection connection;
	private static InitialContext iContext;
	private static DataSource dataSource;
	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(DbHelper.class);

	/**
	 * Get connection from datasourse.
	 * 
	 * @return sql connection
	 */
	public static Connection getConnection() {
		try {
			if (iContext == null) {
				iContext = new InitialContext();
				dataSource = (DataSource) iContext.lookup("java:comp/env/jdbc/database");
			}

			connection = dataSource.getConnection();
			LogUtils.logDebug(LOGGER, "Db connection got successfully.");
		} catch (NamingException | SQLException e) {
			LOGGER.error("Data Base error while getting connection. " + e.getMessage());
		}
		return connection;
	}

}
