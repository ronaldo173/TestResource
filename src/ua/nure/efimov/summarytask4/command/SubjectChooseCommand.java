package ua.nure.efimov.summarytask4.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.efimov.summarytask4.constants.ComonConstants;
import ua.nure.efimov.summarytask4.entity.Subject;
import ua.nure.efimov.summarytask4.entity.Test;
import ua.nure.efimov.summarytask4.exception.ApplicationException;
import ua.nure.efimov.summarytask4.exception.BusinessException;
import ua.nure.efimov.summarytask4.services.SubjectsService;
import ua.nure.efimov.summarytask4.services.TestsLoadService;
import ua.nure.efimov.summarytask4.utils.LogUtils;

public class SubjectChooseCommand extends Command {
	private static final long serialVersionUID = 3042226515798754461L;
	private static final Logger LOGGER = Logger.getLogger(SubjectChooseCommand.class);
	private static String subjByIdAttrName = ComonConstants.SUBJECT_BY_ID.getValue();

	@Override
	public EntryPath execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LOGGER.debug("Command starts");
		EntryPath entryPath;

		if (request.getSession() != null) {

			if (request.getParameter(ComonConstants.SUBJECTS_ALL.getValue()) == null) {
				Object subjId = request.getParameter(ComonConstants.SUBJECT_ID.getValue());
				LogUtils.logDebug(LOGGER, "Select subject id = " + subjId);
				loadSubject(subjId.toString(), request);
			} else {
				request.getSession().removeAttribute(subjByIdAttrName);
				LogUtils.logDebug(LOGGER, "Go to all subjects");
			}

			if (SessionHelperTestPass.isTestStarted()) {
				LOGGER.info("Test already started. Clear info in session.");
			}
		}
		LOGGER.debug("Command finished");
		entryPath = new EntryPath("\\", false);
		return entryPath;

	}

	/**
	 * Load subject by index and put subject to session.
	 * 
	 * @param subjectIndex
	 *            is index
	 * @param req
	 *            is current {@link HttpServletRequest}
	 */
	private void loadSubject(String subjectIndex, HttpServletRequest req) {
		SubjectsService subjectsService = new SubjectsService();
		Subject subjectChosen = null;

		try {
			int index = Integer.parseInt(subjectIndex);
			subjectChosen = subjectsService.getSubjectByIndex(index);
			loadTestsBySubject(subjectChosen, req);
			LOGGER.info("Loaded subject by id: " + subjectChosen);
		} catch (BusinessException e) {
			LOGGER.error(e);
		}
		/**
		 * Set chosen subject to session.
		 */
		req.getSession().setAttribute(subjByIdAttrName, subjectChosen);
		LogUtils.logDebug(LOGGER, "Set to session: " + subjectChosen);
	}

	/**
	 * Load tests by subject, set tests list to the {@link HttpServletRequest}.
	 * 
	 * @param req
	 *            is current {@link HttpServletRequest}
	 */
	private void loadTestsBySubject(Subject subject, HttpServletRequest req) {
		TestsLoadService testsLoadService = new TestsLoadService();
		List<Test> testsList = null;

		try {
			testsList = testsLoadService.getTestsBySubject(subject);
			LOGGER.info("Loaded tests by subject success");
		} catch (BusinessException e) {
			LOGGER.error(e);
		}

		/**
		 * Set list with tests to request.
		 */
		String testsAttrName = ComonConstants.TESTS.getValue();
		req.setAttribute(testsAttrName, testsList);
		LogUtils.logDebug(LOGGER, "Loaded tests by subject: " + testsList);
	}
}
