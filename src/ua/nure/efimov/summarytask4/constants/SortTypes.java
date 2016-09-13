package ua.nure.efimov.summarytask4.constants;

import ua.nure.efimov.summarytask4.entity.Test;

/**
 * Names of sort types for {@link Test} objects.
 * 
 * @author Alexandr Efimov
 *
 */
public enum SortTypes implements Meaningful {
	SORT_BY_NAME("by_name"), SORT_BY_DIFFICULTY("by_difficulty"), SORT_BY_QUESTION_AMOUNT("by_question_amount");

	@Override
	public String getValue() {
		return value;
	}

	/**
	 * Value of constant.
	 */
	private String value;

	private SortTypes(String value) {
		this.value = value;
	}

}
