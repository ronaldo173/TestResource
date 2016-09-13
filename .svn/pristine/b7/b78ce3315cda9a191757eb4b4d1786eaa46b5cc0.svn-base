package ua.nure.efimov.summarytask4.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import org.junit.Test;

public class CommonUtilsTest {

	@Test
	public void correctResultTestGetRelativePath() {
		String full = "http://localhost:8080/SummaryTask4/tests/main.jsp";
		String relative = "/SummaryTask4";
		String resultCorrect = "/tests/main.jsp";

		String relativeGet = CommonUtils.getRelativePath(full, relative);

		Assert.assertEquals(resultCorrect, relativeGet);
	}

	@Test
	public void nullArgumentsTestGetRelativePath() {
		Assert.assertNull(CommonUtils.getRelativePath("", null));
		Assert.assertNull(CommonUtils.getRelativePath(null, ""));
		Assert.assertNull(CommonUtils.getRelativePath(null, null));
	}

	@Test
	public void notContainsTestGetRelativePath() {
		String result = CommonUtils.getRelativePath("blaprivet", "gel");
		Assert.assertNull(result);
	}

	@Test(expected = InvocationTargetException.class)
	public void tryToCreateWithConstructor() throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<CommonUtils> claz = CommonUtils.class;
		Constructor<CommonUtils> declaredConstructor = claz.getDeclaredConstructor();
		declaredConstructor.setAccessible(true);
		declaredConstructor.newInstance();
	}
}
