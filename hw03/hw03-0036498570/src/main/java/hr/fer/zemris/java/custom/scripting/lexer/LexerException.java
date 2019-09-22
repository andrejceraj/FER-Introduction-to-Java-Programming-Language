package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Exception which is thrown if {@code Lexers} fails to process given text.
 * 
 * @author Andrej Ceraj
 *
 */
public class LexerException extends RuntimeException{
	private static final long serialVersionUID = 7900591097543619015L;

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
	public LexerException(String string) {
		super(string);
	}

}
