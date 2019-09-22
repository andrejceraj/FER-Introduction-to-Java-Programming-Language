package hr.fer.zemris.java.hw05.db.lexer;

/**
 * SimpleLexer is used to process given text. It turns parts of the text to
 * tokens.
 * 
 * @author Andrej Ceraj
 *
 */
public class SimpleLexer {
	/**
	 * String of characters that can be part of an operator.
	 */
	private static final String OPERATORS = "!=<>";
	/**
	 * Entry text
	 */
	private char[] data;
	/**
	 * Current part of the text converted to token
	 */
	private SimpleToken token;
	/**
	 * Index of first unprocessed text symbol
	 */
	private int currentIndex;

	/**
	 * Creates an instance of {@code SimpleLexer}.
	 * 
	 * @throws NullPointerException Exception is thrown if the given text is null.
	 * @param text Text to be processed
	 */
	public SimpleLexer(String data) {
		if (data == null) {
			throw new NullPointerException();
		}
		this.data = data.toCharArray();
		token = null;
		currentIndex = 0;
	}

	/**
	 * Returns current {@link SimpleToken}. Multiple call on this method returns the
	 * same token until the {@code nextToken()} is called.
	 * 
	 * @return Current token
	 */
	public SimpleToken getToken() {
		return token;
	}

	/**
	 * Convert part of the text to new token and returns the token.
	 * 
	 * @throws SimpleLexerException Exception is thrown if the {@code SimpleLexer}
	 *                              is unable to convert text to new token.
	 * @return New token
	 */
	public SimpleToken nextToken() {
		if (token != null && token.getType() == SimpleTokenType.EOF) {
			throw new SimpleLexerException();
		}
		if (currentIndex >= data.length) {
			token = new SimpleToken(SimpleTokenType.EOF, null);
		} else {
			processQuery();
		}

		return token;
	}

	/**
	 * Processes text until token can be created.
	 * 
	 * @throws SimpleLexerException Exception is thrown if {@code SimpleLexer} is
	 *                              unable to tokenize the text.
	 */
	private void processQuery() {
		skipBlanks();
		if (currentIndex >= data.length) {
			token = new SimpleToken(SimpleTokenType.EOF, null);
		} else if (data[currentIndex] == '"') {
			processStringLiteral();
		} else if (Character.isLetter(data[currentIndex])) {
			processName();
		} else if (OPERATORS.indexOf(data[currentIndex]) != -1) {
			processOperator();
		} else {
			throw new SimpleLexerException("Unknown token type");
		}
	}

	/**
	 * Turns part of the text into {@code STRING_LITERAL} type token.
	 * 
	 * @throws SimpleLexerException Exception is thrown if {@code SimpleLexer} is
	 *                              unable to tokenize the text into StringLiteral.
	 */
	private void processStringLiteral() {
		StringBuilder builder = new StringBuilder();
		boolean finished = false;
		currentIndex++;
		while (currentIndex < data.length) {
			if (data[currentIndex] == '\"') {
				finished = true;
				break;
			}
			builder.append(data[currentIndex]);
			currentIndex++;
		}
		if (!finished) {
			throw new SimpleLexerException("String literal is unfinished. Missing (\")");
		}
		currentIndex++;
		token = new SimpleToken(SimpleTokenType.STRING_LITERAL, builder.toString());
	}

	/**
	 * Turns part of the text into {@code NAME} type token.
	 * 
	 */
	private void processName() {
		StringBuilder builder = new StringBuilder();
		while (currentIndex < data.length && Character.isLetter(data[currentIndex])) {
			builder.append(data[currentIndex]);
			currentIndex++;

		}
		token = new SimpleToken(SimpleTokenType.NAME, builder.toString());
	}

	/**
	 * Turns part of the text into {@code OPERATOR} type token.
	 * 
	 */
	private void processOperator() {
		String operator = new String();
		operator += data[currentIndex];
		if (currentIndex < data.length - 1 && OPERATORS.indexOf(data[currentIndex + 1]) != -1) {
			operator += data[currentIndex + 1];
			currentIndex++;
		}
		currentIndex++;
		token = new SimpleToken(SimpleTokenType.OPERATOR, operator);

	}

	/**
	 * {@code SimpleLexer} sets the {@code currentIndex} to first non-whitespace
	 * character.
	 */
	private void skipBlanks() {
		while (currentIndex < data.length && Character.isWhitespace(data[currentIndex])) {

			currentIndex++;
		}
	}

}
