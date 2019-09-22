package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.lexer.*;
import hr.fer.zemris.java.custom.scripting.stack.*;

/**
 * <p>
 * Representation of a parser for simple language. Accepted input is simple text
 * and three types of expression. Expression begins with "{$" and ends with $}.
 * First type of expression is "for" loop expression and it is takes 3 or 4
 * arguments in it. First argument must be a variable, and other 2 or 3
 * arguments must be either variable, integer number, decimal number or string.
 * For every "for" loop expression there must be exactly one "end" expression
 * (second type of expression. An ordinary expression (third type) starts with
 * symbol '=' and can take any number of arguments. Arguments in the ordinary
 * expression can be variables, strings, integer numbers, decimal numbers,
 * functions and operators.
 * </p>
 * <p>
 * Parser uses {@link Lexer} to tokenize the input, processes tokens one by one
 * and builds a node tree. Node tree root is {@code DocumentNode} to which child
 * nodes are added. When {@code ForLoopNode} is added to the tree, further nodes
 * added to the tree are added as the {@code ForLoopNode}'s children until the
 * "end" expression occurs. {@link ObjectStack} is used for proper building of
 * the tree.
 * </p>
 * 
 * @author Andrej Ceraj
 *
 */
public class SmartScriptParser {
	private static final int NUMBER_OF_FOR_LOOP_ARGUMENTS = 4;
	private static final String FOR_LOOP = "FOR";
	private static final String END = "END";
	private static final String ECHO_TAG = "=";

	/**
	 * {@link Lexer} used while parsing.
	 */
	private Lexer lexer; // lexer.getToken() gets first unprocessed token.
	/**
	 * {@link ObjectStack} used to building proper tree
	 */
	private ObjectStack stack;
	/**
	 * Tree containing of nodes to parse input text into.
	 */
	private DocumentNode tree;

	/**
	 * Creates an instance of {@code SmartScriptParser} containing the {@code Lexer}
	 * used for parsing, tree to which input text is parsed into and
	 * {@code ObjectStack} used for easier parsing.
	 * 
	 * @param input
	 */
	public SmartScriptParser(String input) {
		this.lexer = new Lexer(input);
		lexer.nextToken();
		this.stack = new ObjectStack();
		try {
			buildTree();
		} catch (Exception e) {
			throw new SmartScriptParserException();
		}
	}

	/**
	 * @return Tree to which input text is parsed into.
	 */
	public DocumentNode getDocumentNode() {
		return tree;
	}

	/**
	 * Method builds a tree using {@code Lexer} and {@code ObjectStack}.
	 */
	private void buildTree() {
		this.tree = new DocumentNode();
		stack.push(tree);
		parseInput();
		if (stack.size() != 1) {
			throw new SmartScriptParserException();
		}
	}

	/**
	 * Method parses tokenized input text until the end. The method uses
	 * {@code Lexer} to get tokens and then processes them
	 * 
	 * @throws SmartScriptParserException Exception is thrown if it is not possible
	 *                                    to parse input text
	 */
	private void parseInput() {
		while (lexer.getToken().getType() != TokenType.EOF) {
			if (stack.isEmpty()) {
				throw new SmartScriptParserException("Too much END words");
			}
			if (lexer.getToken().getType() == TokenType.TEXT) {
				processTextToken();
			} else if (lexer.getToken().getType() == TokenType.OPEN_TAG) {
				lexer.setState(LexerState.TAG);
				processTag();
				lexer.setState(LexerState.TEXT);
			} else {
				throw new SmartScriptParserException();
			}
			lexer.nextToken();
		}
	}

	/**
	 * Method stores value of text token into {@code ElementString} which is then
	 * stored to {@code TextNode} and then added into the node tree.
	 */
	private void processTextToken() {
		ElementString elementString = new ElementString((String) lexer.getToken().getValue());
		Node parentNode = (Node) stack.peak();
		parentNode.addChildNode(new TextNode(elementString));
	}

	/**
	 * Method processes an expression weather it is {@code FOR} loop expression,
	 * {@code END} expression, or an ordinary expression tag.
	 * 
	 * @throws SmartScriptParserException Exception is thrown if it is not possible
	 *                                    to parse the expression
	 */
	private void processTag() {
		lexer.nextToken();
		if (lexer.getToken().getType() == TokenType.NAME) {
			if (lexer.getToken().getValue().toString().toUpperCase().equals(FOR_LOOP)) {
				processForLoop();
			} else if (lexer.getToken().getValue().toString().equals(ECHO_TAG)) {
				processEcho();
			} else if (lexer.getToken().getValue().toString().toUpperCase().equals(END)) {
				processEnd();
			} else {
				throw new SmartScriptParserException();
			}
		} else {
			throw new SmartScriptParserException();
		}
	}

	/**
	 * Method parses the {@code FOR} loop expression. It checks if it has 3 or 4
	 * arguments and it the first one is variable and others are either variable,
	 * number or string. The method then creates the {@code ForLoopNode} and adds it
	 * into the node tree.
	 * 
	 * @throws SmartScriptParserException Exception is thrown if the {@code FOR}
	 *                                    loop expression is falsely implemented.
	 */
	private void processForLoop() {
		Token currentToken = lexer.nextToken(); // current token should be variable
		Element[] forLoopElements = new Element[NUMBER_OF_FOR_LOOP_ARGUMENTS];
		for (int i = 0; i < NUMBER_OF_FOR_LOOP_ARGUMENTS + 1; i++) {
			if (i == 0 && currentToken.getType() == TokenType.NAME) {
				forLoopElements[i] = constructForElement(currentToken);
			} else if (i == 1 || i == 2 && (currentToken.getType() == TokenType.NAME
					|| currentToken.getType() == TokenType.INTEGER || currentToken.getType() == TokenType.DOUBLE
					|| currentToken.getType() == TokenType.STRING)) {
				forLoopElements[i] = constructForElement(currentToken);
			} else if (i == 3 && (currentToken.getType() == TokenType.NAME
					|| currentToken.getType() == TokenType.INTEGER || currentToken.getType() == TokenType.DOUBLE
					|| currentToken.getType() == TokenType.STRING || currentToken.getType() == TokenType.CLOSE_TAG)) {
				forLoopElements[i] = constructForElement(currentToken);
			} else if (i == 4 && currentToken.getType() == TokenType.CLOSE_TAG) {
				continue;
			} else {
				throw new SmartScriptParserException();
			}

			if (i < 3 || currentToken.getType() != TokenType.CLOSE_TAG) {
				currentToken = lexer.nextToken();
			}
		}

		ForLoopNode forLoopNode = new ForLoopNode((ElementVariable) forLoopElements[0], forLoopElements[1],
				forLoopElements[2], forLoopElements[3]);
		Node parentNode = (Node) stack.peak();
		parentNode.addChildNode(forLoopNode);
		stack.push(forLoopNode);
	}

	/**
	 * Method processes an {@code END} expression. If there is nothing to end in the
	 * input text then the exception is thrown.
	 * 
	 * @throws SmartScriptParserException Exception is thrown if there is no loops
	 *                                    or expressions to end in the input text.
	 */
	private void processEnd() {
		if (stack.isEmpty()) {
			throw new SmartScriptParserException();
		}
		lexer.nextToken();
		stack.pop();
	}

	/**
	 * Method parses an ordinary expression creating an array of elements in which
	 * tokens are stored into. The array is then stored to {@code EchoNode} and
	 * added to the node tree.
	 */
	private void processEcho() {
		Token currentToken = lexer.nextToken();
		ArrayIndexedCollection elements = new ArrayIndexedCollection();
		while (currentToken.getType() != TokenType.CLOSE_TAG) {
			elements.add(constructEchoElement(currentToken));
			currentToken = lexer.nextToken();
		}
		Element[] echoElements = new Element[elements.toArray().length];
		for (int i = 0; i < echoElements.length; i++) {
			echoElements[i] = (Element) elements.toArray()[i];
		}
		EchoNode echoNode = new EchoNode(echoElements);
		Node parentNode = (Node) stack.peak();
		parentNode.addChildNode(echoNode);
	}

	/**
	 * Method constructs the right {@code Element} that should be stored to
	 * {@code ForLoopNode} based on token type.
	 * 
	 * @throws SmartScriptParserException Exception is thrown if the token type does
	 *                                    not match the ones able to be stored into
	 *                                    {@code ForLoopNode}.
	 * @param token Token which value is to be stored into element.
	 * @return Element to be stored to {@code ForLoopNode}
	 */
	private Element constructForElement(Token token) {
		Element element = null;
		if (token.getType() == TokenType.NAME) {
			element = new ElementVariable((String) token.getValue());
		} else if (token.getType() == TokenType.INTEGER) {
			element = new ElementConstantInteger((int) token.getValue());
		} else if (token.getType() == TokenType.DOUBLE) {
			element = new ElementConstantDouble((double) token.getValue());
		} else if (token.getType() == TokenType.STRING) {
			element = new ElementString((String) token.getValue());
		} else if (token.getType() == TokenType.CLOSE_TAG) {
			element = null;
		} else {
			throw new SmartScriptParserException();
		}
		return element;
	}

	/**
	 * Method constructs the right {@code Element} that should be stored to
	 * {@code EchoNode} based on token type.
	 * 
	 * @throws SmartScriptParserException Exception is thrown if the token type does
	 *                                    not match the ones able to be stored into
	 *                                    {@code EchoNode}.
	 * @param token Token which value is to be stored into element.
	 * @return Element to be stored to {@code ForLoopNode}
	 */
	private Element constructEchoElement(Token token) {
		Element element;
		if (token.getType() == TokenType.NAME) {
			element = new ElementVariable((String) token.getValue());
		} else if (token.getType() == TokenType.INTEGER) {
			element = new ElementConstantInteger((int) token.getValue());
		} else if (token.getType() == TokenType.DOUBLE) {
			element = new ElementConstantDouble((double) token.getValue());
		} else if (token.getType() == TokenType.STRING) {
			element = new ElementString((String) token.getValue());
		} else if (token.getType() == TokenType.OPERATOR) {
			element = new ElementOperator(token.getValue().toString());
		} else if (token.getType() == TokenType.FUNCTION) {
			element = new ElementFunction((String) token.getValue());
		} else {
			throw new SmartScriptParserException();
		}
		return element;
	}

}
