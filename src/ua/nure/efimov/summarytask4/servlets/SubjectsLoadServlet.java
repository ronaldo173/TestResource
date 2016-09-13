package ua.nure.efimov.summarytask4.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.efimov.summarytask4.constants.ComonConstants;
import ua.nure.efimov.summarytask4.entity.Subject;
import ua.nure.efimov.summarytask4.entity.Test;
import ua.nure.efimov.summarytask4.exception.BusinessException;
import ua.nure.efimov.summarytask4.services.SubjectsService;
import ua.nure.efimov.summarytask4.services.TestsLoadService;
import ua.nure.efimov.summarytask4.utils.LogUtils;

/**
 * Load list of all subjects and put to the session.
 * 
 * @author Alexandr Efimov
 *
 */
@WebServlet("/SubjectsLoad")
public class SubjectsLoadServlet extends HttpServlet {

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -4632118372313709270L;

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(SubjectsLoadServlet.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		loadAllSubjects(req);
		loadAllTests(req);
	}

	/**
	 * Load all tests, set tests list to the {@link HttpServletRequest}.
	 * 
	 * @param req
	 *            is current {@link HttpServletRequest}
	 */
	public void loadAllTests(HttpServletRequest req) {
		TestsLoadService testsLoadService = new TestsLoadService();
		List<Test> testsList = null;

		try {
			testsList = testsLoadService.getTests();
			LOGGER.info("Loaded tests list: " + testsList);
		} catch (BusinessException e) {
			LOGGER.error("Can't load all tests!");
			LOGGER.error(e);
		}

		/**
		 * Set list with tests to request.
		 */
		req.setAttribute(ComonConstants.TESTS_ALL.getValue(), testsList);
		LogUtils.logDebug(LOGGER, "Loaded tests: " + testsList);
	}

	/**
	 * Load all subjects, set subject list to the session.
	 * 
	 * @param req
	 *            is current {@link HttpServletRequest}
	 */
	public void loadAllSubjects(HttpServletRequest req) {
		if (req.getSession() == null) {
			return;
		}

		SubjectsService subjectsService = new SubjectsService();
		List<Subject> subjectList = null;

		try {
			subjectList = subjectsService.getSubjects();
			subjectsService.sortSubjects(subjectList);
			LOGGER.info("Loaded subject list: " + subjectList);
		} catch (BusinessException e) {
			LOGGER.error(e);
		}

		HttpSession session = req.getSession();
		if (session.getAttribute(ComonConstants.SUBJECT_BY_ID.getValue()) != null) {
			session.removeAttribute(ComonConstants.SUBJECT_BY_ID.getValue());
		}

		/**
		 * Set list with subjects to session.
		 */
		session.setAttribute("subjects", subjectList);
		LogUtils.logDebug(LOGGER, "Set to session subjects: " + subjectList);
	}
}
