package ua.nure.efimov.summarytask4.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.efimov.summarytask4.constants.ComonConstants;
import ua.nure.efimov.summarytask4.entity.Question;
import ua.nure.efimov.summarytask4.entity.Test;
import ua.nure.efimov.summarytask4.exception.ApplicationException;
import ua.nure.efimov.summarytask4.exception.BusinessException;
import ua.nure.efimov.summarytask4.services.TestsLoadService;
import ua.nure.efimov.summarytask4.utils.LogUtils;

public class StartTestCommand extends Command {
	private static final long serialVersionUID = 3344184604393385687L;
	private static final Logger LOGGER = Logger.getLogger(StartTestCommand.class);

	@Override
	public EntryPath execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LogUtils.logDebug(LOGGER, "Command starts");

		String testId = request.getParameter(ComonConstants.TEST_ID.getValue());
		HttpSession session = request.getSession();

		// get passing test
		Test test = loadTest(testId);
		// load questions and set to session
		loadQuestionsList(request, test);

		setAttrToSession(session, test);

		LogUtils.logDebug(LOGGER, "Command finished");
		return new EntryPath(request.getContextPath() + "/tests/testPass", true);
	}

	/**
	 * Add to sessions information that test started, and smth about test.Also
	 * add info to {@link SessionHelper}.
	 * 
	 * @param session
	 *            is current sessiong
	 * @param test
	 *            is current test
	 */
	private void setAttrToSession(HttpSession session, Test test) {
		if (session == null) {
			return;
		}
		// set to session and add to list session that test started.
		String testStartedAttrName = ComonConstants.TEST_STARTED.getValue();
		session.setAttribute(testStartedAttrName, true);
		SessionHelperTestPass.addAtrNameToSessionSet(testStartedAttrName);

		// set test name, pass time to session
		String testNameAttrName = ComonConstants.TEST_NAME.getValue();
		session.setAttribute(testNameAttrName, test.getName());
		SessionHelperTestPass.addAtrNameToSessionSet(testNameAttrName);
		String testPassTimeAttrName = ComonConstants.TEST_PASS_TIME.getValue();
		session.setAttribute(testPassTimeAttrName, test.getPassTime());
		SessionHelperTestPass.addAtrNameToSessionSet(testPassTimeAttrName);

		// and add testId
		String testIdAttrName = ComonConstants.TEST_ID.getValue();
		session.setAttribute(testIdAttrName, test.getId());
		SessionHelperTestPass.addAtrNameToSessionSet(testIdAttrName);

	}

	/**
	 * Load questions list and set them to session.
	 * 
	 * @param request
	 *            is current {@link HttpServletRequest}
	 * @param test
	 *            is test
	 */
	private void loadQuestionsList(HttpServletRequest request, Test test) {

		List<Question> questionsList = null;

		questionsList = test.getQuestions();
		LOGGER.info("Loaded questions list: " + questionsList);

		/**
		 * Set list with questions to session.
		 */
		String questionsAttrName = ComonConstants.QUESTIONS.getValue();
		request.getSession().setAttribute(questionsAttrName, questionsList);
		SessionHelperTestPass.addAtrNameToSessionSet(questionsAttrName);
		LogUtils.logDebug(LOGGER, "Set to session questions: " + questionsList);
	}

	/**
	 * Load test by test id.
	 * 
	 * @param testId
	 * @return is test by id
	 */
	private Test loadTest(String testId) {
		TestsLoadService service = new TestsLoadService();
		Test test = null;

		try {
			int testIndex = Integer.parseInt(testId);
			test = service.getTestById(testIndex);
			LOGGER.info("Loaded test by id: " + test);
		} catch (BusinessException e) {
			LOGGER.error(e);
		}
		return test;
	}
}
