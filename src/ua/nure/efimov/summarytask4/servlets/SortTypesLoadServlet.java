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

import ua.nure.efimov.summarytask4.beans.SortingsBean;
import ua.nure.efimov.summarytask4.constants.ComonConstants;
import ua.nure.efimov.summarytask4.services.TestSortingsService;

@WebServlet("/SortTypesLoadServlet")
public class SortTypesLoadServlet extends HttpServlet {
	private static final long serialVersionUID = 1978557170872423651L;
	private static final Logger LOGGER = Logger.getLogger(SortTypesLoadServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.debug("Load sort types");
		loadSortTypes(request.getSession());
	}

	/**
	 * Load to the session list with sort types = list with {@link SortingsBean}
	 * 
	 * @param session
	 */
	private void loadSortTypes(HttpSession session) {
		if (session == null) {
			return;
		}

		if (session.getAttribute(ComonConstants.SORT_TYPES_LIST.getValue()) == null) {
			LOGGER.debug("In session no list with sort types, init and load to the session.");

			if (TestSortingsService.sortingsTypes == null) {
				new TestSortingsService().sortTypesInit();
			}
			List<SortingsBean> sortingsBeans = TestSortingsService.sortingsTypes;
			session.setAttribute(ComonConstants.SORT_TYPES_LIST.getValue(), sortingsBeans);
			LOGGER.info("Set to session sortings: " + sortingsBeans);

		}

	}

}
