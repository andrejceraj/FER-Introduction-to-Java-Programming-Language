package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Representation of parsed document stored in {@code DocumentNode} and
 * {@code DocumentNode}'s child nodes.
 * 
 * @author Andrej Ceraj
 *
 */
public class DocumentNode extends Node {
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}

	@Override
	public String toString() {
		String string = "";
		for (Node n : getChildNodes()) {
			string += n.toString();
		}
		return string;
	}
}
