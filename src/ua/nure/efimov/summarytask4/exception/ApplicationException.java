package ua.nure.efimov.summarytask4.exception;

/**
 * An exception that provides information on an application error.
 * 
 * The class {@code ApplicationException} and its subclasses are a form of
 * {@code Exception} that indicates conditions that a reasonable application
 * might want to catch.
 * 
 * @author Alexandr Efimov
 *
 */
public abstract class ApplicationException extends Exception {

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -6683226250370859684L;

	/**
	 * Constructs a new Application Exception with the specified cause and a
	 * detail message. It's general exception of application.
	 *
	 * @param cause
	 *            the cause (which is saved for later retrieval by the
	 *            {@link #getCause()} method).
	 */
	public ApplicationException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new Application Exception with the specified detail message
	 * and cause.
	 * <p>
	 * Note that the detail message associated with {@code cause} is <i>not</i>
	 * automatically incorporated in this exception's detail message.
	 *
	 * @param message
	 *            the detail message (which is saved for later retrieval by the
	 *            {@link #getMessage()} method).
	 * @param cause
	 *            the cause (which is saved for later retrieval by the
	 *            {@link #getCause()} method). (A <tt>null</tt> value is
	 *            permitted, and indicates that the cause is nonexistent or
	 *            unknown.)
	 */
	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new Application Exception with the specified detail message.
	 * The cause is not initialized, and may subsequently be initialized by a
	 * call to {@link #initCause}.
	 *
	 * @param message
	 *            the detail message. The detail message is saved for later
	 *            retrieval by the {@link #getMessage()} method.
	 */
	public ApplicationException(String message) {
		super(message);
	}

	/**
	 * Constructs a new Application Exception with {@code null} as its detail
	 * message. The cause is not initialized, and may subsequently be
	 * initialized by a call to {@link #initCause}.
	 */
	public ApplicationException() {
	}
}
