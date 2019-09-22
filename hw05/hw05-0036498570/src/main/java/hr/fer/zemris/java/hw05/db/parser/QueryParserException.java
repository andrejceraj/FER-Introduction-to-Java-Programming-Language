package hr.fer.zemris.java.hw05.db.parser;

/**
 * Exception that should be thrown when an error occurs while parsing the text.
 * 
 * @author Andrej Ceraj
 *
 */
public class QueryParserException extends RuntimeException {
	private static final long serialVersionUID = 4352533213107154256L;

	/**
	 * Creates an instance of {@code QueryParserException}.
	 */
	public QueryParserException() {
		super();
	}

	/**
	 * Creates an instance of {@code QueryParserException} with the given message.
	 * 
	 * @param message Exception message
	 */
	public QueryParserException(String message) {
		super(message);
	}
}
