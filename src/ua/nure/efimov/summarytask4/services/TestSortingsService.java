package ua.nure.efimov.summarytask4.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ua.nure.efimov.summarytask4.beans.SortingsBean;
import ua.nure.efimov.summarytask4.constants.SortTypes;
import ua.nure.efimov.summarytask4.entity.Test;

public class TestSortingsService {

	public static List<SortingsBean> sortingsTypes;

	/**
	 * Init list with {@link SortingsBean} by values from enum {@link SortTypes}
	 * 
	 */
	public void sortTypesInit() {
		if (sortingsTypes == null) {
			sortingsTypes = new ArrayList<>();
			for (SortTypes sortType : SortTypes.values()) {
				SortingsBean sortingsBean = new SortingsBean();
				sortingsBean.setSortingName(sortType.getValue());
				sortingsTypes.add(sortingsBean);
			}
		}

	}

	/**
	 * Sort list with tests by selected type. Get selected type from
	 * {@code List sortingsTypes} of this class, if no selected(no
	 * {@link SortingsBean} boolean true field) sort by {@link Test name}.
	 * 
	 * @param testsBySubject
	 */
	public void sortTestsBySelectedSortType(List<Test> testsBySubject) {
		Comparator<Test> activeComparator = getActiveComparator();

		Collections.sort(testsBySubject, activeComparator);
	}

	/**
	 * Get active comparator by selected sorting, no selected -> return
	 * comparator by test name.
	 */
	private Comparator<Test> getActiveComparator() {
		if (sortingsTypes == null) {
			return new ComparatorTestsByName();
		}
		String sortingName = null;
		for (SortingsBean sortingsBean : sortingsTypes) {
			if (sortingsBean.getIsSelected()) {
				sortingName = sortingsBean.getSortingName();
			}
		}

		if (sortingName == null) {
			return new ComparatorTestsByName();
		}

		SortTypes activeSort = null;
		for (SortTypes sortTypeElem : SortTypes.values()) {
			if (sortTypeElem.getValue().equals(sortingName)) {
				activeSort = sortTypeElem;
			}
		}

		switch (activeSort) {
		case SORT_BY_DIFFICULTY:
			return new ComparatorTestsByDifficulty();
		case SORT_BY_QUESTION_AMOUNT:
			return new ComparatorTestsByQuestionAmount();
		default:
			return new ComparatorTestsByName();
		}
	}

	/**
	 * Comparators.
	 */

	/**
	 * Comparator for comparing test by their test name.
	 * 
	 * @author Alexandr Efimov
	 * 
	 */
	private final class ComparatorTestsByName implements Comparator<Test>, Serializable {
		private static final long serialVersionUID = -6616213797653697892L;

		@Override
		public int compare(Test o1, Test o2) {
			return o1.getName().compareTo(o2.getName());
		}
	}

	/**
	 * Comparator for comparing test by their Difficulty.
	 * 
	 * @author Alexandr Efimov
	 * 
	 */
	private final class ComparatorTestsByDifficulty implements Comparator<Test>, Serializable {
		private static final long serialVersionUID = -2144900091555990386L;

		@Override
		public int compare(Test o1, Test o2) {
			int difficultyOb1 = o1.getDifficulty();
			int difficultyOb2 = o2.getDifficulty();
			return Integer.compare(difficultyOb1, difficultyOb2);
		}
	}

	/**
	 * Comparator for comparing test by their Qestion list size(question
	 * amount).
	 * 
	 * @author Alexandr Efimov
	 * 
	 */
	private final class ComparatorTestsByQuestionAmount implements Comparator<Test>, Serializable {
		private static final long serialVersionUID = -5051018757355099140L;

		@Override
		public int compare(Test o1, Test o2) {
			int sizeQuest1 = o1.getQuestions().size();
			int sizeQuest2 = o2.getQuestions().size();
			return Integer.compare(sizeQuest1, sizeQuest2);
		}
	}

}
