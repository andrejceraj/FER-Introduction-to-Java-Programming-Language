package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representation of parser node
 * 
 * @author Andrej Ceraj
 *
 */
public abstract class Node {
	/**
	 * Child nodes of this node.
	 */
	protected List<Node> childNodes;

	/**
	 * Creates a new Node with no child nodes
	 */
	public Node() {
		childNodes = new ArrayList<Node>();
	}

	/**
	 * Calls visit adequate visit method from {@link INodeVisitor}
	 * 
	 * @param visitor visitor
	 */
	public abstract void accept(INodeVisitor visitor);

	public List<Node> getChildNodes() {
		return childNodes;
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
