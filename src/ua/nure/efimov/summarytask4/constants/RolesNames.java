package ua.nure.efimov.summarytask4.constants;

public enum RolesNames implements Meaningful {

	ADMIN("admin"), USER("user");

	private RolesNames(String value) {
		this.value = value;
	}

	/**
	 * Is value;
	 */
	private String value;

	@Override
	public String getValue() {
		return value;
	}

}
