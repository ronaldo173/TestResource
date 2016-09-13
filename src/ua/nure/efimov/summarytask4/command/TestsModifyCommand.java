package ua.nure.efimov.summarytask4.command;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.efimov.summarytask4.constants.ComonConstants;
import ua.nure.efimov.summarytask4.constants.PathConstants;
import ua.nure.efimov.summarytask4.entity.Subject;
import ua.nure.efimov.summarytask4.entity.Test;
import ua.nure.efimov.summarytask4.exception.ApplicationException;
import ua.nure.efimov.summarytask4.exception.BusinessException;
import ua.nure.efimov.summarytask4.services.SubjectsService;
import ua.nure.efimov.summarytask4.services.TestsLoadService;
import ua.nure.efimov.summarytask4.servlets.SubjectsLoadServlet;
import ua.nure.efimov.summarytask4.utils.LogUtils;

public class TestsModifyCommand extends Command {
	private static final long serialVersionUID = 1658755568996939859L;
	private static final Logger LOGGER = Logger.getLogger(TestsModifyCommand.class);

	@Override
	public EntryPath execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LogUtils.logDebug(LOGGER, "Command starts");

		// add all tests to session
		setTestsToSession(request);

		String action_with = request.getParameter(ComonConstants.ACTION_WITH.getValue());
		if (action_with != null) {
			doAction(action_with, request);
		}

		LogUtils.logDebug(LOGGER, "Command finished");
		return new EntryPath(request.getContextPath() + PathConstants.TESTS_MODIFY.getValue(), true);
	}

	private void setTestsToSession(HttpServletRequest request) {
		if (request.getSession() == null) {
			return;
		}
		new SubjectsLoadServlet().loadAllTests(request);
		@SuppressWarnings("unchecked")
		List<Test> testsList = (List<Test>) request.getAttribute(ComonConstants.TESTS_ALL.getValue());
		request.getSession().setAttribute(ComonConstants.TESTS_ALL.getValue(), testsList);

	}

	/**
	 * Do action depend on received parameter 'action_with'
	 * 
	 * @param action_with
	 *            is param from request for detecting what to do
	 * @param request
	 *            is request
	 */
	private void doAction(String action_with, HttpServletRequest request) {
		if (request.getSession() == null || action_with.isEmpty()) {
			return;
		}
		Map<String, String> resultMessages = new HashMap<>();
		try {
			switch (action_with) {
			case "subjects_edit":
				LOGGER.info("Subject edit action.");
				editSubject(request, resultMessages);
				break;
			case "subjects_add":
				LOGGER.info("Subject add action.");
				addSubject(request, resultMessages);
				break;
			case "tests_edit":
				LOGGER.info("Tests edit action.");
				editTests(request, resultMessages);
				break;
			case "test_add":
				LOGGER.info("Tests add action.");
				addTest(request, resultMessages);
				break;

			default:
				break;
			}
		} catch (BusinessException e) {
			resultMessages.put("Error: ", e.getMessage());
		}
		request.getSession().setAttribute("resultMessages", resultMessages);
	}

	/**
	 * Add test.
	 * 
	 * @param request
	 * @param resultMessages
	 */
	private void addTest(HttpServletRequest request, Map<String, String> resultMessages) {

		String testName = request.getParameter("test_name");
		String testSubjId = request.getParameter("test_subject_id");
		String testDescr = request.getParameter("test_descr");
		String testPassTime = request.getParameter("test_passTime");
		String testDifficulty = request.getParameter("test_difficulty");

		boolean isValid = validateTestData(resultMessages, testName, testSubjId, testDescr, testPassTime,
				testDifficulty);
		LOGGER.info("Validation test add passed: " + isValid);
		if (isValid) {
			Test test = new Test();
			test.setAddDate(new Date());
			test.setName(testName);
			test.setDescription(testDescr);
			test.setPassTime(Integer.parseInt(testPassTime));
			test.setDifficulty(Integer.parseInt(testDifficulty));

			Subject subject = new Subject();
			subject.setId(Integer.parseInt(testSubjId));
			test.setSubject(subject);

			TestsLoadService testsLoadService = new TestsLoadService();
			try {
				testsLoadService.saveTest(test);
			} catch (BusinessException e) {
				resultMessages.put("Test add", "Success");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Validate data for test.
	 * 
	 * @param resultMessages
	 * @param testName
	 * @param testSubjId
	 * @param testDescr
	 * @param testPassTime
	 * @param testDiff
	 * @return
	 */
	private boolean validateTestData(Map<String, String> resultMessages, String testName, String testSubjId,
			String testDescr, String testPassTime, String testDiff) {
		String message = "New test data";
		if (testName == null || testName.isEmpty() || testDiff == null || testSubjId == null || testDescr == null
				|| testPassTime == null) {
			resultMessages.put(message, "Sorry not valid");
			return false;
		}

		try {
			Integer.parseInt(testSubjId);
			int testDiffInt = Integer.parseInt(testDiff);
			int timePass = Integer.parseInt(testPassTime);
			if (timePass <= 0) {
				return false;
			}
			if (testDiffInt <= 0) {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}

		resultMessages.put(message, "Data valid");
		return true;
	}

	/**
	 * Edit tests(update/delete) depends on action.
	 * 
	 * @param request
	 * @param resultMessages
	 * @throws BusinessException
	 */
	private void editTests(HttpServletRequest request, Map<String, String> resultMessages) throws BusinessException {
		String updateValue = request.getParameter("update_test");
		String deleteValue = request.getParameter("delete_test");

		String actualTypeId = updateValue != null ? updateValue : deleteValue;
		int testEditId = 0;
		try {
			testEditId = Integer.parseInt(actualTypeId);
		} catch (NumberFormatException e) {
			throw new BusinessException("Error during test edit action. Wrong test number");
		}

		LOGGER.info("Test edit id: " + actualTypeId);
		resultMessages.put("Edit test, id:", actualTypeId);
		String message = null;
		TestsLoadService testsService = new TestsLoadService();

		/**
		 * check what happened, which action
		 */
		String updateTestAttrName = ComonConstants.UPDATE_TEST_ID.getValue();
		if (updateValue != null) {
			/**
			 * update test
			 */
			message = "Test update";
			LOGGER.info("Test update: id = " + testEditId);
			/**
			 * if update set to session attribute that update
			 */
			request.getSession().setAttribute(updateTestAttrName, testEditId);

		} else {
			/**
			 * delete test
			 */
			message = "subject delete";
			testsService.deleteTest(testEditId);
			message = "Test deleted";
			setTestsToSession(request);
			resultMessages.put("Test deleted", String.valueOf(testEditId));

			/**
			 * delete info about update in session
			 */
			Object attributeUpdateTest = request.getSession().getAttribute(updateTestAttrName);
			if (attributeUpdateTest != null) {
				request.getSession().removeAttribute(updateTestAttrName);
			}
			LOGGER.info("Test deleted: id = " + testEditId);
		}
		resultMessages.put("Edit test", message);

	}

	/**
	 * Add subject.
	 * 
	 * @param request
	 * @param resultMessages
	 * @throws BusinessException
	 */
	private void addSubject(HttpServletRequest request, Map<String, String> resultMessages) throws BusinessException {
		String subjName = request.getParameter("subject_name");

		if (subjName != null && !subjName.isEmpty()) {
			resultMessages.put("Subject added: ", subjName);
			SubjectsService subjectsService = new SubjectsService();
			subjectsService.addSubject(subjName);
			LOGGER.info("Subject added: " + subjName);
		}
	}

	/**
	 * Edit subject.
	 * 
	 * @param request
	 *            is current request
	 * @param resultMessages
	 *            is map with messages for print in table as log
	 * @throws BusinessException
	 *             if smth happened
	 */
	private void editSubject(HttpServletRequest request, Map<String, String> resultMessages) throws BusinessException {

		String updateValue = request.getParameter("update_subj");
		String deleteValue = request.getParameter("delete_subj");

		String actTypeId = updateValue != null ? updateValue : deleteValue;
		int subjEditId = 0;
		try {
			subjEditId = Integer.parseInt(actTypeId);
		} catch (NumberFormatException e) {
			throw new BusinessException("Error during subject edit action");
		}

		resultMessages.put("Edit subject, id:", actTypeId);
		String message = null;
		SubjectsService subjectsService = new SubjectsService();

		// check what happened, what action
		if (updateValue != null) {
			// update subject
			String subjName = request.getParameter("subject_name");
			if (subjName == null || subjName.isEmpty()) {
				resultMessages.put("info update: ", "Empty new name !");
				LOGGER.info("Subject not updated! Empty new name");
				return;
			}
			message = "subject update";
			subjectsService.updateSubject(subjEditId, subjName);
			resultMessages.put("Subject updated: ", "Success!");
			LOGGER.info("Subject updated: id = " + subjEditId);

		} else {
			// delete subject
			message = "subject delete";
			subjectsService.deleteSubject(subjEditId);
			resultMessages.put("Subject deleted, tests deleted: ", "Success!");
			LOGGER.info("Subject deleted: id = " + subjEditId);
		}

		resultMessages.put("Edit subject", message);
	}

}
