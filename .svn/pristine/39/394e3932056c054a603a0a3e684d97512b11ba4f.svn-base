package ua.nure.efimov.summarytask4.db.dao;

import ua.nure.efimov.summarytask4.exception.TechnicalException;

/**
 * Exception class for work with persist layer. Used for removing dependency on
 * storage(for example DB storage -> SQLException, it'll be easy with this
 * exception to change storage to smth not connected to sql). Has super
 * exception - > Technical exception.
 * 
 * @author Alexandr Efimov
 */
public class PersistException extends TechnicalException {

	/**
	 * Serial id.
	 */
	private static final long serialVersionUID = 3762126383972368773L;

	/**
	 * Constructs a new exception with {@code null} as its detail message.
	 */
	public PersistException() {
	}

	/**
	 * Constructs a new exception with the specified detail message. The cause
	 * is not initialized, and may subsequently be initialized by a call to
	 * {@link #initCause}.
	 *
	 * @param message
	 *            the detail message.
	 */
	public PersistException(String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * <p>
	 * Note that the detail message associated with {@code cause} is <i>not</i>
	 * automatically incorporated in this exception's detail message.
	 *
	 * @param message
	 *            the detail message.
	 * @param cause
	 *            the cause (which is saved for later retrieval by the
	 *            {@link #getCause()} method). (A <tt>null</tt> value is
	 *            permitted, and indicates that the cause is nonexistent or
	 *            unknown.)
	 */
	public PersistException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new exception with the specified cause and a detail message
	 * of <tt>(cause==null ? null : cause.toString())</tt> (which typically
	 * contains the class and detail message of <tt>cause</tt>).
	 *
	 * @param cause
	 *            the cause (which is saved for later retrieval by the
	 *            {@link #getCause()} method). (A <tt>null</tt> value is
	 *            permitted, and indicates that the cause is nonexistent or
	 *            unknown.)
	 */
	public PersistException(Throwable cause) {
		super(cause);
	}

}