package ua.nure.efimov.summarytask4.utils;

import static org.mockito.Mockito.mock;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.mockito.Mockito;

public class LogUtilsTest {

	@Test(expected = InvocationTargetException.class)
	public void tryToCreateWithConstructor() throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<LogUtils> claz = LogUtils.class;
		Constructor<LogUtils> declaredConstructor = claz.getDeclaredConstructor();
		declaredConstructor.setAccessible(true);
		declaredConstructor.newInstance();
	}

	public static Logger mockLogger(boolean debug) {
		Logger logger = mock(Logger.class);
		if (debug) {
			logger.setLevel(Level.DEBUG);
		} else {
			logger.setLevel(Level.INFO);
		}
		return logger;
	}

	@Test
	public void testLoggerDebugEnabled() {
		Logger mockLogger = mockLogger(true);
		LogUtils.logDebug(mockLogger, "testDebug");
	}

	@Test
	public void testLoggerDebugDisabled() {
		Logger mockLogger = mockLogger(false);
		Mockito.when(mockLogger.isDebugEnabled()).thenReturn(true);

		LogUtils.logDebug(mockLogger, "testDebug");
	}

}
