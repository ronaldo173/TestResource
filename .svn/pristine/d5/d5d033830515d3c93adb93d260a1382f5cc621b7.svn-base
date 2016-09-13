package ua.nure.efimov.summarytask4.command;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.efimov.summarytask4.exception.ApplicationException;

/**
 * Main interface for the Command pattern implementation.
 * 
 * @author Alexandr Efimov
 *
 */
public abstract class Command implements Serializable {
	private static final long serialVersionUID = -4883735692992398669L;

	/**
	 * Execution method for command.
	 * 
	 * @param request
	 *            is {@link HttpServletRequest}
	 * @param response
	 *            is {@link HttpServletResponse}
	 * @return EntryPath -> path to go once the command is executed and it's
	 *         boolean var that shows way of going.
	 * @throws IOException
	 *             if I/O exception of some sort has occurred
	 * @throws ServletException
	 *             if general servlet exception occurred
	 * @throws ApplicationException
	 *             if app exception occurred
	 */
	public abstract EntryPath execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ApplicationException;

	@Override
	public final String toString() {
		return getClass().getSimpleName();
	}
}
