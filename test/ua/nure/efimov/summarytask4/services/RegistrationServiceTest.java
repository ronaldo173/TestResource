package ua.nure.efimov.summarytask4.services;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import ua.nure.efimov.summarytask4.beans.RegistrationMessagesBean;

public class RegistrationServiceTest {

	@Test
	public void chackAllFieldsValidateData() {
		Map<String, String[]> map = new HashMap<>();
		map.put("login", new String[] { "Motomoto" });
		map.put("first_name", new String[] { "Motomoto" });
		map.put("last_name", new String[] { "Motomoto" });
		map.put("email", new String[] { "Motomoto" });
		map.put("password", new String[] { "Motomoto" });
		map.put("confirm_password", new String[] { "Motomoto" });
	}

	@Test
	public void checkLoginCorrect() {

		RegistrationMessagesBean regMessagesBean = new RegistrationMessagesBean();
		new RegistrationService().checkLoginCorrect("Motomoto", regMessagesBean);

		Assert.assertTrue(regMessagesBean.getMap().isEmpty());
	}

	@Test
	public void checkLoginWrong() {

		RegistrationMessagesBean regMessagesBean = new RegistrationMessagesBean();
		new RegistrationService().checkLoginCorrect("12", regMessagesBean);

		Map<String, String> map = regMessagesBean.getMap();
		Assert.assertTrue(!map.isEmpty());
	}

	@Test
	public void checkLoginNullValue() {

		RegistrationMessagesBean regMessagesBean = new RegistrationMessagesBean();
		new RegistrationService().checkLoginCorrect(null, regMessagesBean);

		Map<String, String> map = regMessagesBean.getMap();
		Assert.assertTrue(!map.isEmpty());
	}

	@Test
	public void checkEmailNullValue() {

		RegistrationMessagesBean regMessagesBean = new RegistrationMessagesBean();
		new RegistrationService().checkEmailCorrect(null, regMessagesBean);

		Map<String, String> map = regMessagesBean.getMap();
		Assert.assertTrue(!map.isEmpty());
	}

	@Test
	public void checkEmailCorrect() {

		RegistrationMessagesBean regMessagesBean = new RegistrationMessagesBean();
		new RegistrationService().checkEmailCorrect("blabla@mai.ru", regMessagesBean);

		Map<String, String> map = regMessagesBean.getMap();
		Assert.assertTrue(map.isEmpty());
	}

	@Test
	public void checkEmailWrong() {

		RegistrationMessagesBean regMessagesBean = new RegistrationMessagesBean();
		new RegistrationService().checkEmailCorrect("123-mai.ru", regMessagesBean);

		Map<String, String> map = regMessagesBean.getMap();
		Assert.assertTrue(!map.isEmpty());
	}

	@Test
	public void checkNullPasswordsEquals() {

		RegistrationMessagesBean regMessagesBean = new RegistrationMessagesBean();
		new RegistrationService().checkPasswordEquals(null, "", regMessagesBean);
		new RegistrationService().checkPasswordEquals("", null, regMessagesBean);
		new RegistrationService().checkPasswordEquals(null, null, regMessagesBean);

		Map<String, String> map = regMessagesBean.getMap();
		Assert.assertTrue(!map.isEmpty());
	}

	@Test
	public void checkCorrectPasswordsEquals() {
		RegistrationMessagesBean regMessagesBean = new RegistrationMessagesBean();
		boolean checkPasswordEquals = new RegistrationService().checkPasswordEquals("bla", "bla", regMessagesBean);

		Map<String, String> map = regMessagesBean.getMap();
		Assert.assertTrue(map.isEmpty());
		Assert.assertTrue(checkPasswordEquals);
	}

	@Test
	public void checkWrongPasswordsEquals() {
		RegistrationMessagesBean regMessagesBean = new RegistrationMessagesBean();
		boolean checkPasswordEquals = new RegistrationService().checkPasswordEquals("213", "bla", regMessagesBean);

		Map<String, String> map = regMessagesBean.getMap();
		Assert.assertTrue(!map.isEmpty());
		Assert.assertTrue(!checkPasswordEquals);
	}

}
