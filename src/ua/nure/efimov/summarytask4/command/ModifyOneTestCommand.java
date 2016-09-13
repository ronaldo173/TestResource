package ua.nure.efimov.summarytask4.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.efimov.summarytask4.constants.ComonConstants;
import ua.nure.efimov.summarytask4.constants.PathConstants;
import ua.nure.efimov.summarytask4.entity.Answer;
import ua.nure.efimov.summarytask4.entity.Question;
import ua.nure.efimov.summarytask4.entity.Subject;
import ua.nure.efimov.summarytask4.entity.Test;
import ua.nure.efimov.summarytask4.exception.ApplicationException;
import ua.nure.efimov.summarytask4.exception.BusinessException;
import ua.nure.efimov.summarytask4.services.QuestionService;
import ua.nure.efimov.summarytask4.services.TestsLoadService;
import ua.nure.efimov.summarytask4.servlets.SubjectsLoadServlet;
import ua.nure.efimov.summarytask4.utils.LogUtils;

public class ModifyOneTestCommand extends Command {
	private static final long serialVersionUID = 3334888998785740881L;
	private static final Logger LOGGER = Logger.getLogger(ModifyOneTestCommand.class);
	/**
	 * Amount of questions.
	 */
	private static final int ANSWERS_AMOUNT = 4;

	@Override
	public EntryPath execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LogUtils.logDebug(LOGGER, "Command starts");
		if (request.getSession() != null) {

			getTestInSession(request);
			String action = request.getParameter(ComonConstants.ACTION.getValue());
			if (action != null) {
				// put subjects to sesion again
				new SubjectsLoadServlet().loadAllSubjects(request);
				doAction(action, request);
			}
		}
		LogUtils.logDebug(LOGGER, "Command finished");
		return new EntryPath(request.getContextPath() + PathConstants.MODIFY_ONE_TEST.getValue(), true);
	}

	/**
	 * Do depends on action.
	 * 
	 * @param action
	 * @param request
	 */
	private void doAction(String action, HttpServletRequest request) {
		if (request.getSession() == null || action.isEmpty()) {
			return;
		}
		LOGGER.info("Action: " + action);
		Map<String, String> resultMessages = new HashMap<>();
		switch (action) {
		case "test_edit":
			updateTest(request, resultMessages);
			break;
		case "questions_edit":
			updateQuestions(request, resultMessages);
			break;
		case "questions_add":
			addQuestions(request, resultMessages);
			break;

		default:
			break;
		}
		request.getSession().setAttribute("resultMessages", resultMessages);
		// update test in session
		try {
			getTestInSession(request);
			LOGGER.debug("Test updated in session!");
		} catch (BusinessException e) {
			LOGGER.info("Can't update in session test");
		}
	}

	private void addQuestions(HttpServletRequest request, Map<String, String> resultMessages) {
		String questBody = request.getParameter("question_body");
		if (questBody != null) {
			List<Answer> listAnswers = new ArrayList<>();

			for (int i = 1; i <= ANSWERS_AMOUNT; i++) {
				String answerBody = request.getParameter("answer_" + i);
				if (answerBody != null) {
					String answerResult = request.getParameter("answ_" + i);
					boolean isCorrect = answerResult.equals("correct");

					Answer answer = new Answer();
					answer.setBody(answerBody);
					answer.setCorrect(isCorrect);
					answer.setVariant((char) ('a' + (i - 1)));
					listAnswers.add(answer);
				}
			}

			resultMessages.put("Question data", "is correct");

			if (!listAnswers.isEmpty()) {
				Question question = new Question();
				question.setBody(questBody);
				/**
				 * Save to db question for get quest id.
				 */

				QuestionService questionService = new QuestionService();
				int questId = 0;
				try {
					questId = questionService.saveQuestionWitoutAnswers(question);
				} catch (BusinessException e) {
					LOGGER.error(e);
					resultMessages.put("Error while saving question", e.getMessage());
					return;
				}
				/**
				 * Set id for questions.
				 */
				for (Answer answer : listAnswers) {
					answer.setQuestionId(questId);
				}
				question.setId(questId);
				question.setAnswers(listAnswers);
				Integer idTestForAdd = (Integer) request.getSession()
						.getAttribute(ComonConstants.UPDATE_TEST_ID.getValue());
				try {
					questionService.saveQuestionForTest(question, idTestForAdd);
				} catch (BusinessException e) {
					e.printStackTrace();
					LOGGER.error(e);
					LOGGER.info("Cant save question by saveQuestionForTest");
					resultMessages.put("Can't save", "can't add question to test");
				}
				resultMessages.put("Question add", "added to db success");

			}
		} else {
			resultMessages.put("Question body", "is empty");
		}
	}

	/**
	 * Update questions.
	 * 
	 * @param request
	 * @param resultMessages
	 */
	private void updateQuestions(HttpServletRequest request, Map<String, String> resultMessages) {
		String deleteIdQuest = request.getParameter("delete_question");
		if (deleteIdQuest != null) {
			int questId = Integer.parseInt(deleteIdQuest);
			Integer idTestForUpdate = (Integer) request.getSession()
					.getAttribute(ComonConstants.UPDATE_TEST_ID.getValue());

			QuestionService questionService = new QuestionService();
			try {
				questionService.deleteQuestionFromTest(questId, idTestForUpdate);
			} catch (BusinessException e) {
				LOGGER.error(e);
			}
			resultMessages.put("Question removed, id:", String.valueOf(questId));
			LOGGER.info("Removed question, id : " + questId);
		}

	}

	/**
	 * Check if data valid and update the test.
	 * 
	 * @param request
	 * @param resultMessages
	 */
	private void updateTest(HttpServletRequest request, Map<String, String> resultMessages) {

		Test test = new Test();
		String nameParam = request.getParameter("name");
		String difficParam = request.getParameter("difficulty");
		String descrParam = request.getParameter("description");
		String passTimeParam = request.getParameter("passTime");
		String subjIdParam = request.getParameter("item_subjects");
		boolean isValid = false;

		try {
			isValid = validateTestData(nameParam, difficParam, descrParam, passTimeParam, subjIdParam);
		} catch (Exception e) {
			resultMessages.put("Validation data update test", "Not valid!");
		}

		/**
		 * check if validation passed or do nothing
		 */
		if (isValid) {
			LOGGER.info("Validation passed");
			test.setName(nameParam);
			test.setDifficulty(Integer.parseInt(difficParam));
			test.setDescription(descrParam);
			test.setPassTime(Integer.parseInt(passTimeParam));

			Subject subject = new Subject();
			subject.setId(Integer.parseInt(subjIdParam));
			test.setSubject(subject);

			Integer idTestForUpdate = (Integer) request.getSession()
					.getAttribute(ComonConstants.UPDATE_TEST_ID.getValue());
			test.setId(idTestForUpdate);

			try {
				new TestsLoadService().updateTest(test);
			} catch (BusinessException e) {
				LOGGER.error(e);
				LOGGER.info("Cant update in DB test");
				resultMessages.put("Can't update", "Error in db, sorry!");
			}

			resultMessages.put("Test updated! Id = " + idTestForUpdate, "Success");

		} else {
			resultMessages.put("Validation data update test", "Not valid!");
			LOGGER.info("Validation not passed");
		}
	}

	/**
	 * Validate if correct values for test update
	 * 
	 * @param nameParam
	 * @param descrParam
	 * @param descrParam2
	 * @param passTimeParam
	 * @param subjIdParam
	 * @return
	 */
	private boolean validateTestData(String nameParam, String difficParam, String descrParam, String passTimeParam,
			String subjIdParam) {
		if (nameParam.isEmpty() || descrParam.isEmpty() || difficParam.isEmpty() || passTimeParam.isEmpty()) {
			return false;
		}
		try {
			int difficult = Integer.parseInt(difficParam);
			int passTime = Integer.parseInt(passTimeParam);
			Integer.parseInt(subjIdParam);

			if (difficult <= 0 || difficult > 5 || passTime <= 10) {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}

		return true;

	}

	/**
	 * Get from session id of test, get test object and put to the session.
	 * 
	 * @param session
	 * @throws BusinessException
	 */
	private void getTestInSession(HttpServletRequest request) throws BusinessException {
		Test testForUpdate = null;
		try {
			Integer idTestForUpdate = (Integer) request.getSession()
					.getAttribute(ComonConstants.UPDATE_TEST_ID.getValue());

			TestsLoadService testsLoadService = new TestsLoadService();
			testForUpdate = testsLoadService.getTestById(idTestForUpdate);

		} catch (Exception e) {
			throw new BusinessException();
		}

		LOGGER.info("Test for update: " + testForUpdate);
		request.getSession().setAttribute(ComonConstants.UPDATE_TEST.getValue(), testForUpdate);
	}

}
