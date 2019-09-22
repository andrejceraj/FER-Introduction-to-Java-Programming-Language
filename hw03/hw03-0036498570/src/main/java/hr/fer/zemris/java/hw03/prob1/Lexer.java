package hr.fer.zemris.java.hw03.prob1;

/**
 * Lexer is used to process given text. It turns parts of the text to tokens.
 * Lexer can work in two {@link LexerState}s and processes the text based on the
 * current state.
 * 
 * @author Andrej Ceraj
 *
 */
public class Lexer {
	private static final int INITIAL_INDEX = 0;
	private static final char[] BLANKS = { '\r', '\n', '\t', ' ' };

	/**
	 * Entry text
	 */
	private char[] data;
	/**
	 * Current part of the text converted to token
	 */
	private Token token;
	/**
	 * Index of first unprocessed text symbol
	 */
	private int currentIndex;
	/**
	 * State Lexer is in
	 */
	private LexerState lexerState;

	/**
	 * Creates an instance of {@code Lexer}.
	 * 
	 * @throws NullPointerException Exception is thrown if the given text is null.
	 * @param text Text to be processed
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new NullPointerException();
		}
		this.data = text.toCharArray();
		currentIndex = INITIAL_INDEX;
		token = null;
		lexerState = LexerState.BASIC;
	}

	/**
	 * 
	 * Sets {@code Lexer} to new state
	 * 
	 * @throws NullPointerException Exception is thrown if the given state is null.
	 * @param state New state
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new NullPointerException();
		}
		this.lexerState = state;
	}

	/**
	 * Returns current {@link Token}. Multiple call on this method returns the same
	 * token until the {@code nextToken()} is called.
	 * 
	 * @return Current token
	 */
	public Token getToken() {
		return this.token;
	}

	/**
	 * Convert part of the text to new token and returns the token.
	 * 
	 * @throws LexerException Exception is thrown if the {@code Lexer} is unable to
	 *                        convert text to new token.
	 * @return New token
	 */
	public Token nextToken() {
		if (token != null && token.getType() == TokenType.EOF) {
			throw new LexerException();
		}

		if (lexerState == LexerState.BASIC) {
			return basicNextToken();
		} else if (lexerState == LexerState.EXTENDED) {
			return extendedNextToken();
		}
		return token;
	}

	/**
	 * Returns next token while working in BASIC state.
	 * 
	 * @return Next token
	 */
	private Token basicNextToken() {
		getRidOfSpaces();

		if (currentIndex == data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}
		if (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
			token = processWord();
		} else if (Character.isDigit(data[currentIndex])) {
			token = processNumber();
		} else {
			token = processSymbol();
		}
		return token;
	}

	/**
	 * Returns next token while working in EXTENDED state.
	 * 
	 * @return Next token
	 */
	private Token extendedNextToken() {
		getRidOfSpaces();

		if (currentIndex == data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}
		if (data[currentIndex] == '#') {
			token = new Token(TokenType.SYMBOL, (Object) data[currentIndex++]);
			return token;
		}

		String newWord = "";
		while (!nextCharIsBlank() && data[currentIndex] != '#') {
			newWord = newWord + data[currentIndex];
			currentIndex++;
		}
		token = new Token(TokenType.WORD, (Object) newWord);
		return token;
	}

	/**
	 * Processes part of text that can be understood as word.
	 * 
	 * @throws LexerException Exception is thrown if the {@code Lexer} is unable to
	 *                        convert text to new word token.
	 * @return New token with type WORD
	 */
	private Token processWord() {
		String newWord = "";
		while (currentIndex != data.length && (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\')) {
			if (data[currentIndex] == '\\'
					&& (currentIndex + 1 == data.length || Character.isLetter(data[currentIndex + 1]))) {
				throw new LexerException();
			}
			newWord = newWord + (data[currentIndex] == '\\' ? data[++currentIndex] : data[currentIndex]);
			currentIndex++;
		}
		return new Token(TokenType.WORD, (Object) newWord);
	}

	/**
	 * Processes part of the text that can be understood as number.
	 * 
	 * @throws LexerException Exception is thrown if the {@code Lexer} is unable to
	 *                        convert text to new number token.
	 * @return New token with type NUMBER
	 */
	private Token processNumber() {
		String newNumber = "";
		while (currentIndex != data.length && Character.isDigit(data[currentIndex])) {
			newNumber = newNumber + data[currentIndex];
			currentIndex++;
		}
		try {
			return new Token(TokenType.NUMBER, (Object) Long.parseLong(newNumber));
		} catch (NumberFormatException exception) {
			throw new LexerException();
		}
	}

	/**
	 * Checks if the next character of the text is a blank.
	 * 
	 * @return True if the next character of the text is blank; false otherwise.
	 */
	private boolean nextCharIsBlank() {
		if (currentIndex == data.length) {
			return false;
		}
		for (char blank : BLANKS) {
			if (data[currentIndex] == blank) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Processes part of the text that can be understood as symbol.
	 * 
	 * @return New token with type SYMBOL
	 */
	private Token processSymbol() {
		return new Token(TokenType.SYMBOL, (Object) data[currentIndex++]);
	}

	/**
	 * Method skips spaces and blank characters until the next non-blank character.
	 */
	private void getRidOfSpaces() {
		while (nextCharIsBlank()) {
			currentIndex++;
		}
	}

}
