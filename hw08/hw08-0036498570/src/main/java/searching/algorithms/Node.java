package searching.algorithms;

import java.util.Objects;

/**
 * Algorithm search node. The node contains reference to the previous (parent)
 * node, current state and cost of all transitions from the starting state until
 * the current state.
 * 
 * @author Andrej Ceraj
 *
 * @param <S> State
 */
public class Node<S> {
	/**
	 * Previous (parent) node
	 */
	private Node<S> parent;
	/**
	 * Current state
	 */
	private S state;
	/**
	 * Cost of transitions from the starting state until the current state
	 */
	private double cost;

	/**
	 * Creates an instance of a {@code Node} with the given previous (parent) node,
	 * current state and cost of all transitions from the starting state until the
	 * current state.
	 * 
	 * @param parent previous (parent) node
	 * @param state  current state
	 * @param cost   cost of all transitions from the starting state until the
	 *               current state
	 */
	public Node(Node<S> parent, S state, double cost) {
		Objects.requireNonNull(state);
		this.parent = parent;
		this.state = state;
		this.cost = cost;
	}

	/**
	 * @return current state
	 */
	public S getState() {
		return state;
	}

	/**
	 * @return cost of all transitions from the starting state until the current
	 *         state
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * @return previous (parent) node
	 */
	public Node<S> getParent() {
		return parent;
	}

}
