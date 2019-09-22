package hr.fer.zemris.java.hw05.db.lexer;

/**
 * String with certain {@link SimpleTokenType}.
 * 
 * @author Andrej Ceraj
 *
 */
public class SimpleToken {
	/**
	 * Simple token type
	 */
	private SimpleTokenType type;
	/**
	 * Simple token value.
	 */
	private String value;

	/**
	 * Creates an instatnce of {@code SimpleToken}.
	 * 
	 * @param type
	 * @param value
	 */
	public SimpleToken(SimpleTokenType type, String value) {
		super();
		this.type = type;
		this.value = value;
	}

	/**
	 * @return Simple token type.
	 */
	public SimpleTokenType getType() {
		return type;
	}

	/**
	 * @return Simple token value
	 */
	public String getValue() {
		return value;
	}

}
