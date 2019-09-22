package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * Representation of For loop expression. For loop expression has 3 or 4
 * elements in it. First must be variable, and others can be either variable,
 * number or string.
 * 
 * @author Andrej Ceraj
 *
 */
public class ForLoopNode extends Node {
	/**
	 *  First element - variable
	 */
	private ElementVariable variable;
	/**
	 * Second element of For loop (start expression)
	 */
	private Element startExpression;
	/**
	 * Third element of For loop (end expression)
	 */
	private Element endExpression;
	/**
	 * Fourth element of For loop (step expression)
	 */
	private Element stepExpression = null;
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}

	/**
	 * @return First element - variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * @return Second element of For loop (start expression)
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * @return Third element of For loop (end expression)
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * @return Fourth element of For loop (step expression)
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

	/**
	 * Creates {@code ForLoopNode} with given elements.
	 * 
	 * @param variable Variable
	 * @param startExpression Start expression
	 * @param endExpression End expression
	 * @param stepExpression Step expression
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		super();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	@Override
	public String toString() {
		String string = "{$FOR ";
		string += variable.asText() + " " + startExpression.asText() + " " + endExpression.asText() + " ";
		if (stepExpression != null) {
			string += stepExpression.asText() + " ";
		}
		string += "$}";
		for (int i = 0; i < childNodes.size(); i++) {
			string += childNodes.get(i).toString();
		}
		string += "{$END$}";
		return string;
	}

}
