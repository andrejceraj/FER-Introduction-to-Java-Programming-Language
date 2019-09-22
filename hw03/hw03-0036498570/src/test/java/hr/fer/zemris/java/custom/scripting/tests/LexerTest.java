package hr.fer.zemris.java.custom.scripting.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.lexer.*;

class LexerTest {

	@Test
	void testConstructor() {
		Lexer lexer1 = new Lexer("asdf asdf");
		Lexer lexer2 = new Lexer("asdf asdf");
		assertEquals(lexer1, lexer2);
		;
	}

	@Test
	void testConstructorNullArgument() {
		assertThrows(NullPointerException.class, () -> new Lexer(null));
	}

	@Test
	void testLexText() {
		Lexer lexer = new Lexer("This is some text");
		assertEquals("This is some text", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.TEXT, lexer.getToken().getType());
	}

	@Test
	void testLexComplexText() {
		Lexer lexer = new Lexer("This is some \"complex\" text with \\{$ escapes");
		assertEquals("This is some \"complex\" text with {$ escapes", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.TEXT, lexer.getToken().getType());
	}

	@Test
	void testLexOpenTag() {
		Lexer lexer = new Lexer("Opening {$ the tag");
		assertEquals("Opening ", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.TEXT, lexer.getToken().getType());
		assertEquals("{$", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.OPEN_TAG, lexer.getToken().getType());
	}

	@Test
	void testLexName() {
		Lexer lexer = new Lexer("Opening {$= i $} the tag");
		assertEquals("Opening ", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.TEXT, lexer.getToken().getType());
		assertEquals("{$", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.OPEN_TAG, lexer.getToken().getType());

		lexer.setState(LexerState.TAG);
		assertEquals("=", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.NAME, lexer.getToken().getType());
	}

	@Test
	void testLexName2() {
		Lexer lexer = new Lexer("Opening {$=           name $} the tag");
		assertEquals("Opening ", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.TEXT, lexer.getToken().getType());
		assertEquals("{$", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.OPEN_TAG, lexer.getToken().getType());

		lexer.setState(LexerState.TAG);
		assertEquals("=", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.NAME, lexer.getToken().getType());
		assertEquals("name", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.NAME, lexer.getToken().getType());
	}

	@Test
	void testLexName3() {
		Lexer lexer = new Lexer("Opening {$= name_th3_b345t $} the tag");
		assertEquals("Opening ", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.TEXT, lexer.getToken().getType());
		assertEquals("{$", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.OPEN_TAG, lexer.getToken().getType());

		lexer.setState(LexerState.TAG);
		assertEquals("=", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.NAME, lexer.getToken().getType());
		assertEquals("name_th3_b345t", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.NAME, lexer.getToken().getType());
	}

	@Test
	void testLexNumber() {
		Lexer lexer = new Lexer("Opening {$ 12 $} the tag");
		assertEquals("Opening ", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.TEXT, lexer.getToken().getType());
		assertEquals("{$", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.OPEN_TAG, lexer.getToken().getType());

		lexer.setState(LexerState.TAG);
		assertEquals(12, lexer.nextToken().getValue());
		assertEquals(TokenType.INTEGER, lexer.getToken().getType());
	}

	@Test
	void testLexNegativeNumber() {
		Lexer lexer = new Lexer("Opening {$ -12 $} the tag");
		assertEquals("Opening ", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.TEXT, lexer.getToken().getType());
		assertEquals("{$", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.OPEN_TAG, lexer.getToken().getType());

		lexer.setState(LexerState.TAG);
		assertEquals(-12, lexer.nextToken().getValue());
		assertEquals(TokenType.INTEGER, lexer.getToken().getType());
	}

	@Test
	void testLexDecimalNumber() {
		Lexer lexer = new Lexer("Opening {$ 12.234 $} the tag");
		assertEquals("Opening ", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.TEXT, lexer.getToken().getType());
		assertEquals("{$", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.OPEN_TAG, lexer.getToken().getType());

		lexer.setState(LexerState.TAG);
		assertEquals(12.234, lexer.nextToken().getValue());
		assertEquals(TokenType.DOUBLE, lexer.getToken().getType());
	}

	@Test
	void testLexNegativeDecimalNumber() {
		Lexer lexer = new Lexer("Opening {$ -12.234 $} the tag");
		assertEquals("Opening ", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.TEXT, lexer.getToken().getType());
		assertEquals("{$", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.OPEN_TAG, lexer.getToken().getType());

		lexer.setState(LexerState.TAG);
		assertEquals(-12.234, lexer.nextToken().getValue());
		assertEquals(TokenType.DOUBLE, lexer.getToken().getType());
	}

	@Test
	void testLexString() {
		Lexer lexer = new Lexer("Opening {$ \"string\" $} the tag");
		assertEquals("Opening ", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.TEXT, lexer.getToken().getType());
		assertEquals("{$", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.OPEN_TAG, lexer.getToken().getType());

		lexer.setState(LexerState.TAG);
		assertEquals("\"string\"", lexer.nextToken().getValue());
		assertEquals(TokenType.STRING, lexer.getToken().getType());
	}

	@Test
	void testLexStringInsideString() {
		Lexer lexer = new Lexer("Opening {$ \"string with \\\"the\\\" string\" $} the tag");
		assertEquals("Opening ", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.TEXT, lexer.getToken().getType());
		assertEquals("{$", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.OPEN_TAG, lexer.getToken().getType());

		lexer.setState(LexerState.TAG);
		assertEquals("\"string with \\\"the\\\" string\"", lexer.nextToken().getValue());
		assertEquals(TokenType.STRING, lexer.getToken().getType());
	}

	@Test
	void testLexFunction() {
		Lexer lexer = new Lexer("Opening {$ @function $} the tag");
		assertEquals("Opening ", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.TEXT, lexer.getToken().getType());
		assertEquals("{$", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.OPEN_TAG, lexer.getToken().getType());

		lexer.setState(LexerState.TAG);
		assertEquals("function", lexer.nextToken().getValue());
		assertEquals(TokenType.FUNCTION, lexer.getToken().getType());
	}

	@Test
	void testLexOperators() {
		Lexer lexer = new Lexer("Opening {$ + - * / ^ $} the tag");
		assertEquals("Opening ", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.TEXT, lexer.getToken().getType());
		assertEquals("{$", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.OPEN_TAG, lexer.getToken().getType());

		lexer.setState(LexerState.TAG);
		assertEquals('+', lexer.nextToken().getValue());
		assertEquals(TokenType.OPERATOR, lexer.getToken().getType());
		assertEquals('-', lexer.nextToken().getValue());
		assertEquals(TokenType.OPERATOR, lexer.getToken().getType());
		assertEquals('*', lexer.nextToken().getValue());
		assertEquals(TokenType.OPERATOR, lexer.getToken().getType());
		assertEquals('/', lexer.nextToken().getValue());
		assertEquals(TokenType.OPERATOR, lexer.getToken().getType());
		assertEquals('^', lexer.nextToken().getValue());
		assertEquals(TokenType.OPERATOR, lexer.getToken().getType());
	}
	
	@Test
	void testLexMix() {
		Lexer lexer = new Lexer("Opening {$ i+ \"str\"10 ^ $} the tag");
		assertEquals("Opening ", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.TEXT, lexer.getToken().getType());
		assertEquals("{$", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.OPEN_TAG, lexer.getToken().getType());

		lexer.setState(LexerState.TAG);
		assertEquals("i", lexer.nextToken().getValue());
		assertEquals(TokenType.NAME, lexer.getToken().getType());
		assertEquals('+', lexer.nextToken().getValue());
		assertEquals(TokenType.OPERATOR, lexer.getToken().getType());
		assertEquals("\"str\"", lexer.nextToken().getValue());
		assertEquals(TokenType.STRING, lexer.getToken().getType());
		assertEquals(10, lexer.nextToken().getValue());
		assertEquals(TokenType.INTEGER, lexer.getToken().getType());
		assertEquals('^', lexer.nextToken().getValue());
		assertEquals(TokenType.OPERATOR, lexer.getToken().getType());
	}

	@Test
	void testLexCloseTag() {
		Lexer lexer = new Lexer("Opening {$ @function $} the tag");
		assertEquals("Opening ", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.TEXT, lexer.getToken().getType());
		assertEquals("{$", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.OPEN_TAG, lexer.getToken().getType());

		lexer.setState(LexerState.TAG);
		assertEquals("function", lexer.nextToken().getValue());
		assertEquals(TokenType.FUNCTION, lexer.getToken().getType());
		assertEquals("$}", lexer.nextToken().getValue());
		assertEquals(TokenType.CLOSE_TAG, lexer.getToken().getType());
	}

	@Test
	void testLexTextAfterTags() {
		Lexer lexer = new Lexer("Opening {$ @function $} the tag");
		assertEquals("Opening ", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.TEXT, lexer.getToken().getType());
		assertEquals("{$", lexer.nextToken().getValue().toString());
		assertEquals(TokenType.OPEN_TAG, lexer.getToken().getType());

		lexer.setState(LexerState.TAG);
		assertEquals("function", lexer.nextToken().getValue());
		assertEquals(TokenType.FUNCTION, lexer.getToken().getType());
		assertEquals("$}", lexer.nextToken().getValue());
		assertEquals(TokenType.CLOSE_TAG, lexer.getToken().getType());

		lexer.setState(LexerState.TEXT);
		assertEquals(" the tag", lexer.nextToken().getValue());
		assertEquals(TokenType.TEXT, lexer.getToken().getType());
		assertEquals(null, lexer.nextToken().getValue());
		assertEquals(TokenType.EOF, lexer.getToken().getType());
	}

	@Test
	void testMixedText() {
		String text = "This is text {$FOR i 1.2 2.3 \"string\" $} more text";
		String[] data = { "This is text ", "{$", "FOR", "i", "1.2", "2.3", "\"string\"", "$}", " more text" };
		TokenType[] type = { TokenType.TEXT, TokenType.OPEN_TAG, TokenType.NAME, TokenType.NAME, TokenType.DOUBLE,
				TokenType.DOUBLE, TokenType.STRING, TokenType.CLOSE_TAG, TokenType.TEXT };

		Lexer lexer = new Lexer(text);
		for (int i = 0; i < 8; i++) {
			Token currentToken = lexer.nextToken();
			assertEquals(data[i].toString(), currentToken.getValue().toString());
			assertEquals(type[i], currentToken.getType());

			if (currentToken.getType() == TokenType.OPEN_TAG) {
				lexer.setState(LexerState.TAG);
			} else if (currentToken.getType() == TokenType.CLOSE_TAG) {
				lexer.setState(LexerState.TEXT);
			}
		}
	}

}
