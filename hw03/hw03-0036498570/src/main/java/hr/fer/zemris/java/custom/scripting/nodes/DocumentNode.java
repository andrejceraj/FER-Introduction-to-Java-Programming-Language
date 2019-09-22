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
	public String toString() {
		String string = "";
		for (int i = 0; i < childNodes.size(); i++) {
			string += childNodes.get(i).toString();
		}
		return string;
	}
}
