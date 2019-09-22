package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.ElementString;

/**
 * Representation of simple text in parsed document.
 * 
 * @author Andrej Ceraj
 *
 */
public class TextNode extends Node {
	/**
	 * Text
	 */
	private ElementString elementString;

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}

	/**
	 * 
	 * @return simple text
	 */
	public String getText() {
		return elementString.getValue();
	}

	/**
	 * Creates a {@code TextNode} with the given string as a node text.
	 * 
	 * @param elementString Text
	 */
	public TextNode(ElementString elementString) {
		super();
		this.elementString = elementString;
	}

	@Override
	public String toString() {
		return elementString.asText();
	}
}
