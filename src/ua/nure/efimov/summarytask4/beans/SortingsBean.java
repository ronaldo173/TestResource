package ua.nure.efimov.summarytask4.beans;

import java.io.Serializable;

public class SortingsBean implements Serializable {
	private static final long serialVersionUID = 8936601736429352369L;
	/**
	 * It's displayed name of current sorting
	 */
	private String sortingName;
	private boolean isSelected;

	/**
	 * @return the sortingName
	 */
	public String getSortingName() {
		return sortingName;
	}

	/**
	 * @param sortingName
	 *            the sortingName to set
	 */
	public void setSortingName(String sortingName) {
		this.sortingName = sortingName;
	}

	/**
	 * @return the isSelected
	 */
	public boolean isSelected() {
		return isSelected;
	}

	/**
	 * @return the isSelected
	 */
	public boolean getIsSelected() {
		return isSelected;
	}

	/**
	 * @param isSelected
	 *            the isSelected to set
	 */
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SortingsBean [sortingName=" + sortingName + ", isSelected=" + isSelected + "]";
	}

}
