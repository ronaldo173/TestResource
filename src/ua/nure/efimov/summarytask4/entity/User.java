package ua.nure.efimov.summarytask4.entity;

import java.util.ArrayList;
import java.util.List;

import ua.nure.efimov.summarytask4.db.dao.Identified;

public class User implements Identified<Integer> {
	/**
	 * SerialVersion UID.
	 */
	private static final long serialVersionUID = 8944109576203372190L;

	private Integer id;
	private String login;
	private String password;

	private String firstName;
	private String lastName;
	private String email;
	private boolean isVerified;
	private boolean isBanned;
	private byte[] photo;
	private List<Role> roles = new ArrayList<>();
	private List<TestHistory> testsHistory = new ArrayList<>();

	@Override
	public Integer getId() {
		return id;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login
	 *            the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the isVerified
	 */
	public boolean getIsVerified() {
		return isVerified;
	}

	/**
	 * @param isVerified
	 *            the isVerified to set
	 */
	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	/**
	 * @return the isBanned
	 */
	public boolean isBanned() {
		return isBanned;
	}

	/**
	 * @return the isBanned
	 */
	public boolean getIsBanned() {
		return isBanned;
	}

	/**
	 * @param isBanned
	 *            the isBanned to set
	 */
	public void setBanned(boolean isBanned) {
		this.isBanned = isBanned;
	}

	/**
	 * @return the photo
	 */
	public byte[] getPhoto() {
		return photo;
	}

	/**
	 * @param photo
	 *            the photo to set
	 */
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	/**
	 * @return the roles
	 */
	public List<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles
	 *            the roles to set
	 */
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the testsHistory
	 */
	public List<TestHistory> getTestsHistory() {
		return testsHistory;
	}

	/**
	 * @param testsHistory
	 *            the testsHistory to set
	 */
	public void setTestsHistory(List<TestHistory> testsHistory) {
		this.testsHistory = testsHistory;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", isVerified=" + isVerified + ", isBanned=" + isBanned + ", roles=" + roles
				+ ", tests passed=" + testsHistory.size() + "]";
	}

}
