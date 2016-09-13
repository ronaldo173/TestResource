package ua.nure.efimov.summarytask4.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.efimov.summarytask4.beans.SortingsBean;
import ua.nure.efimov.summarytask4.constants.ComonConstants;
import ua.nure.efimov.summarytask4.constants.PathConstants;
import ua.nure.efimov.summarytask4.entity.Subject;
import ua.nure.efimov.summarytask4.exception.ApplicationException;
import ua.nure.efimov.summarytask4.services.TestSortingsService;
import ua.nure.efimov.summarytask4.utils.LogUtils;

public class ChangeSortTypeCommand extends Command {
	private static final long serialVersionUID = 5829963209049110444L;
	private static final Logger LOGGER = Logger.getLogger(ChangeSortTypeCommand.class);

	@Override
	public EntryPath execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException {
		LogUtils.logDebug(LOGGER, "Command starts");
		EntryPath entryPath = new EntryPath(request.getContextPath(), true);

		String sortType = request.getParameter(ComonConstants.SORT_TYPE.getValue());
		changeSortTypeInSession(sortType, request.getSession());
		LOGGER.info("Sort type changed to: " + sortType);

		if (request.getSession() != null) {
			request.getSession().setAttribute(ComonConstants.SORT_TYPE.getValue(), sortType);

			// check if subject id -> go to prev page
			Object subjById = request.getSession().getAttribute(ComonConstants.SUBJECT_BY_ID.getValue());
			if (subjById != null) {
				LOGGER.debug("subjById not null -> go to tests by subject");
				Subject subjId = (Subject) subjById;
				entryPath = new EntryPath(PathConstants.SUBJECT_SEL_PAGE.getValue() + subjId.getId(), false);
			}
		}

		LogUtils.logDebug(LOGGER, "Command finished");

		return entryPath;
	}

	private void changeSortTypeInSession(String sortType, HttpSession session) {
		if (session == null) {
			return;
		}

		List<SortingsBean> sortingsTypes = TestSortingsService.sortingsTypes;
		for (SortingsBean sortingsBean : sortingsTypes) {
			if (sortingsBean.getSortingName().equals(sortType)) {
				sortingsBean.setSelected(true);

			} else {
				sortingsBean.setSelected(false);
			}
		}

		LogUtils.logDebug(LOGGER, "Changed sort list in session, selected: " + sortType);
		session.setAttribute(ComonConstants.SORT_TYPES_LIST.getValue(), sortingsTypes);
	}

}
