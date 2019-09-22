package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * Representation of expression. Expression can have any number of elements in
 * it.
 * 
 * @author Andrej Ceraj
 *
 */
public class EchoNode extends Node {
	/**
	 * Elements in the expression
	 */
	private Element[] elements;
	
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}

	/**
	 * @return Elelemts in the expression
	 */
	public Element[] getElements() {
		return elements;
	}

	/**
	 * Creates new EchoNode with expression containing the given elements.
	 * 
	 * @param elements Elements of the expression
	 */
	public EchoNode(Element[] elements) {
		super();
		this.elements = elements;
	}

	@Override
	public String toString() {
		String string = "{$= ";
		for (int i = 0; i < elements.length; i++) {
			string += elements[i].asText() + " ";
		}
		string += "$}";
		return string;
	}

}
