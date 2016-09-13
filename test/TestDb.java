import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.junit.Test;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

import ua.nure.efimov.summarytask4.db.dao.GenericDao;
import ua.nure.efimov.summarytask4.db.dao.PersistException;
import ua.nure.efimov.summarytask4.db.dao.jdbc.impl.DaoFactoryMySqlImpl;
import ua.nure.efimov.summarytask4.entity.Answer;

public class TestDb {

	@Test
	public void testGetData() throws SQLException, PersistException {

		String user = "root";// Логин пользователя
		String password = "root";// Пароль пользователя
		String url = "jdbc:mysql://localhost:3306/testing";// URL адрес
		Connection connection = DriverManager.getConnection(url, user, password);

		GenericDao<?, ?> answersDao = DaoFactoryMySqlImpl.getInstance().getDao(connection, Answer.class);
		List<?> answers = answersDao.getAll();
		for (Object object : answers) {
			System.out.println(object);
		}

		connection.close();
	}

	@Test
	public void test() throws SQLException {
		try {
			// Create initial context
			System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
			System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");
			InitialContext ic = new InitialContext();

			ic.createSubcontext("java:");
			ic.createSubcontext("java:/comp");
			ic.createSubcontext("java:/comp/env");
			ic.createSubcontext("java:/comp/env/jdbc");

			// Construct DataSource
			MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();
			String user = "root";// Логин пользователя
			String password = "root";// Пароль пользователя
			String url = "jdbc:mysql://localhost:3306/testing";// URL адрес
			ds.setUser(user);
			ds.setPassword(password);
			ds.setUrl(url);

			ic.bind("java:/comp/env/jdbc/testing", ds); // <--insert name of
														// binding here
			DataSource mysqlDs = (DataSource) ic.lookup("java:/comp/env/jdbc/testing");
			Connection connection = mysqlDs.getConnection();

			System.out.println(connection);
			System.out.println(connection.isClosed());
			System.out.println(connection.getMetaData());
		} catch (NamingException ex) {
			ex.printStackTrace();
		}
	}

}