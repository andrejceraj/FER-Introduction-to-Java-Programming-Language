package hr.fer.zemris.java.hw03.prob1;

/**
 * Exception which is thrown if {@code Lexer} fails to process given text.
 * 
 * @author Andrej Ceraj
 *
 */
public class LexerException extends RuntimeException {
	private static final long serialVersionUID = -8689371241741643179L;

	/**
	 * Creates an instance of {@code LexerException}
	 */
	public LexerException() {
		super();
	}

	/**
	 * Creates an instance of {@code LexerException} with a given message.
	 * 
	 * @param message Exception message
	 */
	public LexerException(String message) {
		super(message);
	}

}
