package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Representation of parser node
 * 
 * @author Andrej Ceraj
 *
 */
public class Node {
	/**
	 * Child nodes of this node.
	 */
	protected ArrayIndexedCollection childNodes;

	/**
	 * Creates a new Node with no child nodes
	 */
	public Node() {
		childNodes = new ArrayIndexedCollection();
	}

	/**
	 * Makes the given node a child of this node.
	 * 
	 * @param child New child node
	 */
	public void addChildNode(Node child) {
		childNodes.add(child);
	}

	/**
	 * @return Number of child nodes.
	 */
	public int numberOfChildren() {
		return childNodes.size();
	}

	/**
	 * Gets child node of this node stored to the given index
	 * 
	 * @param index Index of child node
	 * @return Child node stored to the given index
	 */
	public Node getNode(int index) {
		return (Node) childNodes.get(index);
	}

	@Override
	public int hashCode() {
		return Objects.hash(childNodes);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Node))
			return false;
		Node other = (Node) obj;
		return Objects.equals(childNodes, other.childNodes);
	}

}
