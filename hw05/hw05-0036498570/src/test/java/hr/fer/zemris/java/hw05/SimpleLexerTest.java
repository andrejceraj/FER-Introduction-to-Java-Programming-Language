package hr.fer.zemris.java.hw05;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.lexer.SimpleLexer;
import hr.fer.zemris.java.hw05.db.lexer.SimpleLexerException;
import hr.fer.zemris.java.hw05.db.lexer.SimpleToken;
import hr.fer.zemris.java.hw05.db.lexer.SimpleTokenType;

class SimpleLexerTest {

	@Test
	void testLexer() {
		SimpleLexer lexer = new SimpleLexer("this query is good<=\"sdf\"");
		SimpleToken token = lexer.nextToken();
		assertEquals("is", token.getValue());
		
		token = lexer.nextToken();
		assertEquals("good", token.getValue());
		
		token = lexer.nextToken();
		assertEquals("<=", token.getValue());
		
		token = lexer.nextToken();
		assertEquals("sdf", token.getValue());
		
		token = lexer.nextToken();
		assertEquals(SimpleTokenType.EOF, token.getType());
		
		assertThrows(SimpleLexerException.class, () -> lexer.nextToken());
		
		
	}

}
