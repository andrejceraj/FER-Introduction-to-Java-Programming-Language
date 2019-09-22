package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class used for construction of HTML from SmartScripts
 * 
 * @author Andrej Ceraj
 *
 */
public class SmartScriptEngine {
	/**
	 * SmartScrtipt document
	 */
	private DocumentNode documentNode;
	/**
	 * Context
	 */
	private RequestContext requestContext;
	/**
	 * Representation of multiple stacks
	 */
	private ObjectMultistack multistack = new ObjectMultistack();
	/**
	 * {@link SmartScriptEngine} node visitor
	 */
	private INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.toString());
			} catch (IOException e) {
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			multistack.push(node.getVariable().getName(), new ValueWrapper(getElementValue(node.getStartExpression())));

			Object endExpression = getElementValue(node.getEndExpression());

			while (true) {
				if (multistack.peek(node.getVariable().getName()).numCompare(endExpression) > 0) {
					break;
				}

				for (Node n : node.getChildNodes()) {
					n.accept(this);
				}

				ValueWrapper temp = multistack.pop(node.getVariable().getName());
				if (node.getStepExpression() == null) {
					temp.add(Integer.valueOf(1));
				} else {
					temp.add(getElementValue(node.getStepExpression()));
				}
				multistack.push(node.getVariable().getName(), temp);
			}

			multistack.pop(node.getVariable().getName());
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<Object> tempStack = new Stack<Object>();
			for (Element e : node.getElements()) {
				if (e instanceof ElementVariable) {
					Object value = multistack.peek(((ElementVariable) e).getName()).getValue();
					tempStack.push(value);
				} else if (e instanceof ElementString) {
					tempStack.push(((ElementString) e).getValue());
				} else if (e instanceof ElementConstantInteger) {
					tempStack.push(((ElementConstantInteger) e).getValue());
				} else if (e instanceof ElementConstantDouble) {
					tempStack.push(((ElementConstantDouble) e).getValue());
				} else if (e instanceof ElementOperator) {
					doOperation(((ElementOperator) e).getSymbol(), tempStack);
				} else if (e instanceof ElementFunction) {
					// do function
					doFunction(((ElementFunction) e).getName(), tempStack);
				}
			}

			for (Object o : tempStack) {
				try {
					requestContext.write(o.toString());
				} catch (IOException e1) {
				}
			}
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (Node n : node.getChildNodes()) {
				n.accept(this);
			}
		}

	};

	/**
	 * Constructor
	 * 
	 * @param documentNode   document
	 * @param requestContext context
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	/**
	 * Gets value stored in element based on element type
	 * 
	 * @param e element
	 * @return value wrapped in object
	 */
	private Object getElementValue(Element e) {
		if (e instanceof ElementString) {
			return ((ElementString) e).getValue();
		} else if (e instanceof ElementConstantInteger) {
			return ((ElementConstantInteger) e).getValue();
		} else if (e instanceof ElementConstantDouble) {
			return ((ElementConstantDouble) e).getValue();
		} else if (e instanceof ElementOperator) {
			return ((ElementOperator) e).getSymbol();
		} else {
			throw new IllegalArgumentException("Cannot extract value from the element");
		}
	}

	/**
	 * Does certain operation between top two elements from the stack
	 * 
	 * @param symbol    operator
	 * @param tempStack stack
	 */
	private void doOperation(String symbol, Stack<Object> tempStack) {
		ValueWrapper o1 = new ValueWrapper(tempStack.pop());
		ValueWrapper o2;
		try {
			o2 = new ValueWrapper(Integer.parseInt(tempStack.pop().toString()));
		} catch (Exception e) {
			o2 = new ValueWrapper(Double.parseDouble(tempStack.pop().toString()));
		}
		switch (symbol) {
		case "+":
			o1.add(o2.getValue());
			break;
		case "-":
			o1.subtract(o2.getValue());
			break;
		case "*":
			o1.multiply(o2.getValue());
			break;
		case "/":
			o1.divide(o2.getValue());
			break;
		default:
			throw new IllegalArgumentException("Unknown operator");
		}

		tempStack.push(o1.getValue());
	}

	/**
	 * Does certain function using operands from the stack
	 * 
	 * @param functionName function
	 * @param tempStack    stack
	 */
	private void doFunction(String functionName, Stack<Object> tempStack) {
		switch (functionName) {
		case "sin":
			sin(tempStack);
			break;
		case "decfmt":
			decfmt(tempStack);
			break;
		case "dup":
			dup(tempStack);
			break;
		case "swap":
			swap(tempStack);
			break;
		case "setMimeType":
			setMimeType(tempStack);
			break;
		case "paramGet":
			paramGet(tempStack);
			break;
		case "pparamGet":
			pparamGet(tempStack);
			break;
		case "pparamSet":
			pparamSet(tempStack);
			break;
		case "pparamDel":
			pparamDel(tempStack);
			break;
		case "tparamGet":
			tparamGet(tempStack);
			break;
		case "tparamSet":
			tparamSet(tempStack);
			break;
		case "tparamDel":
			tparamDel(tempStack);
			break;

		default:
			throw new IllegalArgumentException("Unknown function");
		}
	}

	/**
	 * Pops the key from the top of the temporary stack and deletes entry stored in
	 * context's temporary parameters map under the popped key.
	 * 
	 * @param tempStack stack
	 */
	private void tparamDel(Stack<Object> tempStack) {
		String name = tempStack.pop().toString();
		requestContext.removeTemporaryParameter(name);
	}

	/**
	 * Pops the key and the value and stores them into context's temporary
	 * parameters map
	 * 
	 * @param tempStack stack
	 */
	private void tparamSet(Stack<Object> tempStack) {
		String name = tempStack.pop().toString();
		Object dv = tempStack.pop();
		requestContext.setTemporaryParameter(name, dv.toString());
	}

	/**
	 * Pops value and key from the temporary stack and if context does not contain
	 * the key in temporary parameters map, it pushes popped value to the stack;
	 * otherwise, the context contained value is pushed to stack
	 * 
	 * @param tempStack stack
	 */
	private void tparamGet(Stack<Object> tempStack) {
		Object dv = tempStack.pop();
		String name = tempStack.pop().toString();
		Object parameter = requestContext.getTemporaryParameter(name);
		tempStack.push(parameter == null ? dv.toString() : parameter.toString());
	}

	/**
	 * Pops the key from the top of the temporary stack and deletes entry stored in
	 * context's persistent parameters map under the popped key.
	 * 
	 * @param tempStack stack
	 */
	private void pparamDel(Stack<Object> tempStack) {
		String name = tempStack.pop().toString();
		requestContext.removePersistentParameter(name);
	}

	/**
	 * Pops the key and the value and stores them into context's persistent
	 * parameters map
	 * 
	 * @param tempStack stack
	 */
	private void pparamSet(Stack<Object> tempStack) {
		String name = tempStack.pop().toString();
		Object dv = tempStack.pop();
		requestContext.setPersistentParameter(name, dv.toString());
	}

	/**
	 * Pops value and key from the temporary stack and if context does not contain
	 * the key in persistent parameters map, it pushes popped value to the stack;
	 * otherwise, the context contained value is pushed to stack
	 * 
	 * @param tempStack stack
	 */
	private void pparamGet(Stack<Object> tempStack) {
		Object dv = tempStack.pop();
		String name = tempStack.pop().toString();
		Object parameter = requestContext.getPersistentParameter(name);
		tempStack.push(parameter == null ? dv : parameter);
	}

	/**
	 * Pops value and key from the temporary stack and if context does not contain
	 * the key in parameters map, it pushes popped value to the stack; otherwise,
	 * the context contained value is pushed to stack
	 * 
	 * @param tempStack stack
	 */
	private void paramGet(Stack<Object> tempStack) {
		Object dv = tempStack.pop();
		String name = tempStack.pop().toString();
		Object parameter = requestContext.getParameter(name);
		tempStack.push(parameter == null ? dv : parameter);
	}

	/**
	 * Sets context's mime type to popped value.
	 * 
	 * @param tempStack stack
	 */
	private void setMimeType(Stack<Object> tempStack) {
		String type = tempStack.pop().toString();
		requestContext.setMimeType(type);
	}

	/**
	 * Swaps top two elements on the stack
	 * 
	 * @param tempStack stack
	 */
	private void swap(Stack<Object> tempStack) {
		Object a = tempStack.pop();
		Object b = tempStack.pop();
		tempStack.push(a);
		tempStack.push(b);
	}

	/**
	 * Duplicates top element on the stack
	 * 
	 * @param tempStack stack
	 */
	private void dup(Stack<Object> tempStack) {
		tempStack.push(tempStack.peek());
	}

	/**
	 * Pops {@link DecimalFormat} and a value from the stack and then pushed
	 * formated value back to the stack
	 * 
	 * @param tempStack stack
	 */
	private void decfmt(Stack<Object> tempStack) {
		DecimalFormat f = new DecimalFormat(tempStack.pop().toString());
		double x = getDoubleValueFromObject(tempStack.pop());
		tempStack.push(f.format(x));
	}

	/**
	 * Calculates sin of popped value and pushed the result to the stack
	 * 
	 * @param tempStack stack
	 */
	private void sin(Stack<Object> tempStack) {
		double x = getDoubleValueFromObject(tempStack.pop());
		x = Math.sin(x * Math.PI / 180);
		tempStack.push(x);
	}

	/**
	 * Gets double value from the object
	 * 
	 * @param object object
	 * @return value
	 */
	private double getDoubleValueFromObject(Object object) {
		return Double.parseDouble(object.toString());
	}

	/**
	 * Converts .smscr script into HTML
	 */
	public void execute() {
		documentNode.accept(visitor);
	}

}