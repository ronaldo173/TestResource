package ua.nure.efimov.summarytask4.utils;

public final class CommonUtils {

	/**
	 * Get subpath.
	 * 
	 * Example:
	 * {@code  http://localhost:8080/SummaryTask4/tests/main.jsp is full,
	 * /SummaryTask4 is relative  -----> get /SummaryTask4/tests/main.jsp}
	 * 
	 * 
	 * If 1 of arg {@code null} -> return {@code null}
	 * 
	 * @param fullPath
	 * @param startRelative
	 * @return
	 */
	public static String getRelativePath(String fullPath, String startRelative) {
		if (fullPath == null || startRelative == null || !fullPath.contains(startRelative)) {
			return null;
		}

		String result = null;
		int indexOf = fullPath.indexOf(startRelative);
		result = fullPath.substring(indexOf);
		result = result.replace(startRelative, "");

		return result;
	}

	/**
	 * Util class not for creating. Use {@code static methods.}
	 * 
	 * @throws IllegalAccessException
	 *             if try to create.
	 */
	private CommonUtils() throws IllegalAccessException {
		throw new IllegalAccessException();
	}
}
