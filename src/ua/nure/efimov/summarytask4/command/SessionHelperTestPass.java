package ua.nure.efimov.summarytask4.command;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

public class SessionHelperTestPass {

	/**
	 * Storage with attr. names in session connected to passing the test.
	 */
	private static Set<String> attrNamesInSessionSet = new HashSet<>();

	public static void addAtrNameToSessionSet(String testStartedAttrName) {
		attrNamesInSessionSet.add(testStartedAttrName);
	}

	public static void removeAllAttributesFromSessionSet(HttpSession session) {
		for (String attrName : attrNamesInSessionSet) {
			session.removeAttribute(attrName);
		}

	}

	/**
	 * Check if test already started.
	 * 
	 * If yes -> set with names of attributes in session not empty.
	 * 
	 * @return true if test started
	 */
	public static boolean isTestStarted() {
		return !attrNamesInSessionSet.isEmpty();
	}

}
