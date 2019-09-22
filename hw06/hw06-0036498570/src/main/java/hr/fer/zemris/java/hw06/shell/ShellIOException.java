package hr.fer.zemris.java.hw06.shell;

/**
 * Exception thrown when communication with user is broken in {@link MyShell}.
 * 
 * @author Andrej Ceraj
 *
 */
public class ShellIOException extends RuntimeException {
	private static final long serialVersionUID = 3844731367581627444L;

	/**
	 * Creates an instance of ShellIOException
	 */
	public ShellIOException() {
		super();
	}

	/**
	 * Creates an instance of ShellIOException with certain message
	 * 
	 * @param message
	 */
	public ShellIOException(String message) {
		super(message);
	}

}
