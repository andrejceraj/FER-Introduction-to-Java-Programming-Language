package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Exception which is thrown if {@code SimpleLexer} fails to process given text.
 * 
 * @author Andrej Ceraj
 *
 */
public class SimpleLexerException extends RuntimeException {
	private static final long serialVersionUID = -4128125475455364223L;

	/**
	 * Creates an instance of {@code SimpleLexerException}
	 */
	public SimpleLexerException() {
		super();
	}

	/**
	 * Creates an instance of {@code SimpleLexerException} with a given message.
	 * 
	 * @param message Exception message
	 */
	public SimpleLexerException(String message) {
		super(message);
	}
}
