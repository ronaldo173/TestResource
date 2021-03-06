package ua.nure.efimov.summarytask4.db.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runners.Parameterized.Parameters;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

import ua.nure.efimov.summarytask4.db.dao.DaoFactory;
import ua.nure.efimov.summarytask4.db.dao.GenericDao;
import ua.nure.efimov.summarytask4.db.dao.Identified;
import ua.nure.efimov.summarytask4.db.dao.PersistException;
import ua.nure.efimov.summarytask4.db.dao.jdbc.impl.DaoFactoryMySqlImpl;
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
 * Mysql dao implementation of different domain daos tests.
 * 
 * @author Alexandr Efimov
 *
 */
public class MySqlDaoTest extends GenericDaoTest<Connection> {
	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(MySqlDaoTest.class);

	/**
	 * Datasource for getting connections.
	 */
	private static DataSource dataSourceMySql;

	/**
	 * Connection for test.
	 */
	private Connection connection;

	/**
	 * Generic dao for test.
	 */
	private GenericDao<? extends Identified<Integer>, Integer> dao;

	/**
	 * Dao factory.
	 */
	private static final DaoFactory<Connection> factory = DaoFactoryMySqlImpl.getInstance();

	@BeforeClass
	public static void setDatasource() {
		BasicConfigurator.configure();
		LOGGER.info("BEFORE ALL TESTS");

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
			dataSourceMySql = (DataSource) ic.lookup("java:/comp/env/jdbc/testing");

			LOGGER.info(dataSourceMySql.getClass() + " init succes");
		} catch (NamingException e) {
			LOGGER.error("Error init: " + e.getMessage());
		}
	}

	@Before
	public void setUp() throws PersistException, SQLException {
		connection = dataSourceMySql.getConnection();
		connection.setAutoCommit(false);
		dao = factory.getDao(connection, daoClass);

		LOGGER.debug("TEST started for: " + daoClass.getName());
	}

	@After
	public void tearDown() throws SQLException {
		context().rollback();
		context().close();

		LOGGER.debug("TEST ended: " + daoClass.getName() + "\n");
	}

	@Parameters
	public static Collection<Object[]> getParameters() {
		Answer answer = new Answer();
		// set necessary elements
		answer.setBody("answer test");
		answer.setQuestionId(1);
		answer.setVariant('e');
		answer.setCorrect(true);

		Subject subject = new Subject();
		subject.setSubject("subject test");

		/**
		 * For testing QuestionDao with one to many relation...
		 */
		Question question = new Question();
		question.setBody("question test");
		List<Answer> listAnswers = new ArrayList<>();
		listAnswers.add(answer);
		question.setAnswers(listAnswers);

		// Test obj for test with dependencies
		Test test = new Test();
		test.setName("test");
		test.setSubject(subject);
		test.setDifficulty(2);
		test.setAddDate(new Date());
		test.setPassTime(100);
		ArrayList<Question> questions = new ArrayList<>();
		questions.add(question);
		test.setQuestions(questions);

		// TestQuestions obj for test
		TestQuestions testQuestions = new TestQuestions();
		ArrayList<Integer> questionsList = new ArrayList<>();
		testQuestions.setQuestionsList(questionsList);
		questionsList.add(1);
		questionsList.add(2);
		questionsList.add(3);
		testQuestions.setTestId(1);

		Role role = new Role();
		role.setRoleName("test");
		role.setDescription("test Descr");

		UserRoles userRoles = new UserRoles();
		userRoles.setLogin("admin");
		userRoles.setRoleName("user");

		User user = new User();
		user.setLogin("testLogin");
		user.setPassword("dsa");
		user.setEmail("@");
		List<Role> listRoles = new ArrayList<>();
		Role roleForUser = new Role();
		roleForUser.setRoleName("user");
		listRoles.add(roleForUser);
		user.setRoles(listRoles);

		TestHistory testHistory = new TestHistory();
		Test testForHistory = new Test();
		testForHistory.setId(1);
		testHistory.setTest(testForHistory);
		testHistory.setUserId(1);
		testHistory.setTestResult(44);
		testHistory.setTimeTestPass(new Date());

		List<Object[]> list = new ArrayList<>();
		list.add(new Object[] { Answer.class, answer });
		list.add(new Object[] { Subject.class, subject });
		list.add(new Object[] { Question.class, question });
		list.add(new Object[] { Test.class, test });
		list.add(new Object[] { TestQuestions.class, testQuestions });
		list.add(new Object[] { Role.class, role });
		list.add(new Object[] { UserRoles.class, userRoles });
		list.add(new Object[] { User.class, user });
		list.add(new Object[] { TestHistory.class, testHistory });
		return list;
	}

	@Override
	public GenericDao<? extends Identified<Integer>, Integer> dao() {
		return dao;
	}

	@Override
	public Connection context() {
		return connection;
	}

	/**
	 * Dao test. By class dao object and goten not persisted object of entity.
	 * 
	 * @param clazz
	 *            is dao for test
	 * @param notPersistedDto
	 *            is object of dao domain
	 */
	public MySqlDaoTest(Class<? extends Identified<Integer>> clazz, Identified<Integer> notPersistedDto) {
		super(clazz, notPersistedDto);
	}
}