package ua.nure.efimov.summarytask4.command;

/**
 * It's entry for Command pattern. The path - path for forward/redirect. The
 * {@link isRediect} is flag that shows what method should be used. If true -
 * redirect, else -> forward.
 * 
 * @author Alexandr Efimov
 *
 */
public class EntryPath {
	/**
	 * Path.
	 */
	String path;
	/**
	 * True -> use Redirect, else - forward.
	 */
	boolean isRediect;

	public EntryPath(String path, boolean isRediect) {
		super();
		this.path = path;
		this.isRediect = isRediect;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the isRediect
	 */
	public boolean isRediect() {
		return isRediect;
	}

	/**
	 * @param isRediect
	 *            the isRediect to set
	 */
	public void setRediect(boolean isRediect) {
		this.isRediect = isRediect;
	}

	@Override
	public String toString() {
		return "Entry [path=" + path + ", isRediect=" + isRediect + "]";
	}
}
