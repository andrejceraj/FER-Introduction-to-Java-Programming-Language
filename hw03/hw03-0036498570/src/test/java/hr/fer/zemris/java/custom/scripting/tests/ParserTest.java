package hr.fer.zemris.java.custom.scripting.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

class ParserTest {

	@Test
	void testConstruction() {
		SmartScriptParser parser = new SmartScriptParser("input");
		assertNotNull(parser);
		assertNotNull(parser.getDocumentNode());
	}

	@Test
	void testSimpleText() {
		SmartScriptParser parser = new SmartScriptParser("simple text");
		Node node = parser.getDocumentNode().getNode(0);
		assertEquals("simple text", ((TextNode) node).getText());
	}

	@Test
	void testEchoNode() {
		SmartScriptParser parser = new SmartScriptParser("simple text {$= i 2 $} continues");
		Node documentNode = parser.getDocumentNode();
		assertTrue(documentNode.getNode(0) instanceof TextNode);
		assertTrue(documentNode.getNode(1) instanceof EchoNode);
		assertTrue(documentNode.getNode(2) instanceof TextNode);
	}

	@Test
	void testForNode() {
		SmartScriptParser parser = new SmartScriptParser("simple text {$ FOR i 1 2 3 $} text {$END$}");
		Node documentNode = parser.getDocumentNode();
		assertTrue(documentNode.getNode(0) instanceof TextNode);
		assertTrue(documentNode.getNode(1) instanceof ForLoopNode);
		assertTrue(documentNode.getNode(1).getNode(0) instanceof TextNode);
	}

	@Test
	void testEchoInsideFor() {
		SmartScriptParser parser = new SmartScriptParser(
				"simple text {$ FOR i 1 2 3 $} text {$= i 2 $} more text {$END$} even more text");
		Node documentNode = parser.getDocumentNode();
		assertTrue(documentNode.getNode(0) instanceof TextNode);
		assertTrue(documentNode.getNode(1) instanceof ForLoopNode);
		assertTrue(documentNode.getNode(1).getNode(0) instanceof TextNode);
		assertTrue(documentNode.getNode(1).getNode(1) instanceof EchoNode);
		assertTrue(documentNode.getNode(1).getNode(2) instanceof TextNode);
		assertTrue(documentNode.getNode(2) instanceof TextNode);
	}
	
	@Test
	void testForExceptions() {
		String string1 = "{$FOR v_a_r_1_j_a_b_l_4 \"string\"$} {$END$}";
		String string2 = "{$FOR 11111 \"string\" varijabla 32 $} {$END$}";
		String string3 = "{$FOR var \"string\" varijabla @funct $} {$END$}";
		String string4 = "{$FOR var \"string\" * varijabla $} {$END$}";
		String string5 = "{$FOR var1 var2 var3 var4 var5 $}";

		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(string1));
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(string2));
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(string3));
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(string4));
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(string5));
	}
	
	@Test
	void testAcceptableFor() {
		String string1 = "{$  FOR var \"string\" 233 20.05 $} {$END$}";
		String string2 = "{$FOR var 233 20.05 $} {$END$}";
		String string3 = "{$FOR var\"string\" 233 20.05 $} {$END$}";
		String string4 = "{$FOR var\"string\" 233var$} {$END$}";
		
		SmartScriptParser parser1 = new SmartScriptParser(string1);
		SmartScriptParser parser2 = new SmartScriptParser(string2);
		SmartScriptParser parser3 = new SmartScriptParser(string3);
		SmartScriptParser parser4 = new SmartScriptParser(string4);

		assertEquals("var", ((ForLoopNode) parser1.getDocumentNode().getNode(0)).getVariable().asText());
		assertEquals("\"string\"", ((ForLoopNode) parser1.getDocumentNode().getNode(0)).getStartExpression().asText());
		assertEquals("233", ((ForLoopNode) parser1.getDocumentNode().getNode(0)).getEndExpression().asText());
		assertEquals("20.05", ((ForLoopNode) parser1.getDocumentNode().getNode(0)).getStepExpression().asText());
		
		assertEquals("var", ((ForLoopNode) parser2.getDocumentNode().getNode(0)).getVariable().asText());
		assertEquals("233", ((ForLoopNode) parser2.getDocumentNode().getNode(0)).getStartExpression().asText());
		assertEquals("20.05", ((ForLoopNode) parser2.getDocumentNode().getNode(0)).getEndExpression().asText());
		assertEquals(null, ((ForLoopNode) parser2.getDocumentNode().getNode(0)).getStepExpression());
		
		assertEquals("var", ((ForLoopNode) parser3.getDocumentNode().getNode(0)).getVariable().asText());
		assertEquals("\"string\"", ((ForLoopNode) parser3.getDocumentNode().getNode(0)).getStartExpression().asText());
		assertEquals("233", ((ForLoopNode) parser3.getDocumentNode().getNode(0)).getEndExpression().asText());
		assertEquals("20.05", ((ForLoopNode) parser3.getDocumentNode().getNode(0)).getStepExpression().asText());
		
		assertEquals("var", ((ForLoopNode) parser4.getDocumentNode().getNode(0)).getVariable().asText());
		assertEquals("\"string\"", ((ForLoopNode) parser4.getDocumentNode().getNode(0)).getStartExpression().asText());
		assertEquals("233", ((ForLoopNode) parser4.getDocumentNode().getNode(0)).getEndExpression().asText());
		assertEquals("var", ((ForLoopNode) parser4.getDocumentNode().getNode(0)).getStepExpression().asText());
	}

}
