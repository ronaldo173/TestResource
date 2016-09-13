package ua.nure.efimov.summarytask4.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.efimov.summarytask4.beans.AnswerCheckEntry;
import ua.nure.efimov.summarytask4.beans.ResultTestDetailBean;
import ua.nure.efimov.summarytask4.beans.ResultTestPass;
import ua.nure.efimov.summarytask4.constants.ComonConstants;
import ua.nure.efimov.summarytask4.constants.PathConstants;
import ua.nure.efimov.summarytask4.entity.Test;
import ua.nure.efimov.summarytask4.entity.TestHistory;
import ua.nure.efimov.summarytask4.entity.User;
import ua.nure.efimov.summarytask4.exception.ApplicationException;
import ua.nure.efimov.summarytask4.services.AnswersCheckService;
import ua.nure.efimov.summarytask4.services.TestHistoryServise;
import ua.nure.efimov.summarytask4.utils.LogUtils;

public class CheckResultCommand extends Command {
	private static final long serialVersionUID = -4383576819012415528L;
	private static final Logger LOGGER = Logger.getLogger(CheckResultCommand.class);

	@Override
	public EntryPath execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LogUtils.logDebug(LOGGER, "Command starts");

		if (request.getAttribute(ComonConstants.NOT_AUTHORIZATED.getValue()) != null) {
			return new EntryPath(request.getContextPath(), true);
		}

		/**
		 * Get answers.
		 */
		List<String> idAnswersVariantsAnsweredList = new ArrayList<>();
		@SuppressWarnings("unchecked")
		Map<String, String[]> parameterMap = request.getParameterMap();
		for (Entry<String, String[]> entry : parameterMap.entrySet()) {
			String key = entry.getKey();
			if (key.startsWith(ComonConstants.ANSWER.getValue())) {
				idAnswersVariantsAnsweredList.add(key);
			}
		}

		/**
		 * Check answers and set result to the session.
		 */
		int testId = (int) request.getSession().getAttribute(ComonConstants.TEST_ID.getValue());
		ResultTestPass checkedResult = checkAnswers(idAnswersVariantsAnsweredList, testId, request.getSession());

		/**
		 * Put result to db.
		 */
		User user = (User) request.getSession().getAttribute(ComonConstants.USER_INFO_OBJECT.getValue());
		saveResultToHistory(checkedResult, user.getId());

		LOGGER.info("check test -> id=" + testId);
		LogUtils.logDebug(LOGGER, "Command finished");
		return new EntryPath(request.getContextPath() + PathConstants.TEST_RESULT.getValue(), true);
	}

	/**
	 * Save result of passing the test to database!
	 * 
	 * @param checkedResult
	 *            is {@link ResultTestPass} object
	 * 
	 * @param userId
	 *            is id of user
	 */
	private void saveResultToHistory(ResultTestPass checkedResult, int userId) {
		TestHistory testHistory = new TestHistory();
		Test testTemp = new Test();
		testTemp.setId(checkedResult.getTestId());
		testHistory.setTest(testTemp);

		int correctAnswersPercents = (int) (((double) checkedResult.getCorrectAnswers()
				/ checkedResult.getListAnsweredEntries().size()) * 100);
		testHistory.setTestResult(correctAnswersPercents);
		testHistory.setTimeTestPass(new Date());
		testHistory.setUserId(userId);

		TestHistoryServise testHistoryServise = new TestHistoryServise();
		try {
			testHistoryServise.saveToHistory(testHistory);
			LogUtils.logDebug(LOGGER, "Saved history obj: " + testHistory);
		} catch (ApplicationException e) {
			LOGGER.error(e);
		}

	}

	/**
	 * Check result of the test. Return result of type {@link ResultTestPass}
	 * and set result to the session.
	 * 
	 * @param idAnswersVariantsAnsweredList
	 * @param testId
	 * @param session
	 * @return
	 */
	private ResultTestPass checkAnswers(List<String> idAnswersVariantsAnsweredList, int testId, HttpSession session) {
		ResultTestPass checkedResult = null;
		AnswersCheckService checkService = new AnswersCheckService();

		try {
			List<AnswerCheckEntry> listParsedEntries = checkService
					.parseResultsToEntries(idAnswersVariantsAnsweredList);

			/**
			 * Get result.
			 */
			checkedResult = checkService.checkResult(listParsedEntries, testId);
			int correctAnswers = checkedResult.getCorrectAnswers();
			int correctAnswersPercents = (int) (((double) correctAnswers
					/ checkedResult.getListAnsweredEntries().size()) * 100);

			// parse result
			List<ResultTestDetailBean> resultTestDetailBeanList = checkService
					.convertResultToDetailBeansList(checkedResult);

			session.setAttribute(ComonConstants.RESULT_BEANS_LIST.getValue(), resultTestDetailBeanList);
			session.setAttribute(ComonConstants.RESULT_CORR_ANSWERS.getValue(), correctAnswers);
			session.setAttribute(ComonConstants.RESULT_CORR_ANSWERS_PERCENTS.getValue(), correctAnswersPercents);

			// set bigger amount of answers
			Integer greaterAmountOfAnswers = getGreaterAmountOfAnswers(resultTestDetailBeanList);
			session.setAttribute("greaterAmountOfAnswers", greaterAmountOfAnswers);
			LOGGER.info("Answers checked successfully. Result: " + checkedResult.getCorrectAnswers());

		} catch (ApplicationException e) {
			LOGGER.error(e);
		}
		return checkedResult;

	}

	/**
	 * Count the greatest amount of answers for question.
	 * 
	 * @param resultTestDetailBeanList
	 *            is result
	 * @return int amount
	 */
	private int getGreaterAmountOfAnswers(List<ResultTestDetailBean> resultTestDetailBeanList) {
		int greaterAmount = 0;

		for (ResultTestDetailBean bean : resultTestDetailBeanList) {
			int answSize = bean.getAnsweredList().size();
			int corrSize = bean.getCorrectList().size();
			int maxSize = Math.max(answSize, corrSize);
			greaterAmount = maxSize > greaterAmount ? maxSize : greaterAmount;
		}
		return greaterAmount;
	}

}
