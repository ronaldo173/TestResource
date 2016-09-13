package ua.nure.efimov.summarytask4.utils;

import org.apache.log4j.Logger;

public final class LogUtils {

	/**
	 * Add to log debug message if debug enabled.
	 * 
	 * @param logger
	 *            is logger for making log
	 * @param message
	 *            is message for logging.
	 */
	public static void logDebug(Logger logger, String message) {
		if (logger.isDebugEnabled()) {
			logger.debug(message);
		}
	}

	/**
	 * Util class not for creating. Use {@code static methods.}
	 * 
	 * @throws IllegalAccessException
	 *             if try to create.
	 */
	private LogUtils() throws IllegalAccessException {
		throw new IllegalAccessException();
	}
}
