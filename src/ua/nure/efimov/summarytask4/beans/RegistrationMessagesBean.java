package ua.nure.efimov.summarytask4.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RegistrationMessagesBean implements Serializable {
	private static final long serialVersionUID = -5585004066256105156L;

	/**
	 * Map with result validation messages. Name of field -> reason of fail.
	 */
	private Map<String, String> map = new HashMap<>();
	private boolean isSuccess;

	/**
	 * @return the map
	 */
	public Map<String, String> getMap() {
		return map;
	}

	/**
	 * @param map
	 *            the map to set
	 */
	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	/**
	 * @return the isSuccess
	 */
	public boolean isSuccess() {
		return isSuccess;
	}

	/**
	 * Geter.
	 * 
	 * @return the isSuccess
	 */
	public boolean getIsSuccess() {
		return isSuccess;
	}

	/**
	 * @param isSuccess
	 *            the isSuccess to set
	 */
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RegistrationMessagesBean [map=" + map + ", isSuccess=" + isSuccess + "]";
	}

}
