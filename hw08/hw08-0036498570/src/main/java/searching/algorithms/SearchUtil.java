package searching.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Abstract class containing search simple and advanced Breadth-First Search
 * (BFS) algorithms.
 * 
 * @author Andrej Ceraj
 *
 */
public abstract class SearchUtil {
	/**
	 * Implementation of simple Breadth-First Search (BFS) algorithm that does not
	 * keep track of visited states.
	 * 
	 * @param s0         starting state
	 * @param succ       function
	 * @param acceptable predicate
	 */
	public static <S> Node<S> bfs(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal) {
		List<Node<S>> queue = new LinkedList<Node<S>>();
		S start = s0.get();
		queue.add(new Node<S>(null, start, 0));
		while (!queue.isEmpty()) {
			Node<S> node = queue.remove(0);
			if (goal.test(node.getState())) {
				return node;
			}
			List<Transition<S>> transitions = succ.apply(node.getState());
			transitions.forEach(t -> queue.add(new Node<S>(node, t.getState(), node.getCost() + t.getCost())));
			System.out.println("Currently processed: \n" + node.getState());
		}
		return null;
	}

	/**
	 * Implementation of advanced Breadth-First Search (BFS) algorithm that kepps
	 * track of visited states.
	 * 
	 * @param s0         starting state
	 * @param succ       function
	 * @param acceptable predicate
	 */
	public static <S> Node<S> bfsv(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal) {
		List<Node<S>> queue = new LinkedList<Node<S>>();
		Set<S> visited = new HashSet<S>();

		S start = s0.get();
		queue.add(new Node<S>(null, start, 0));
		while (!queue.isEmpty()) {
			Node<S> node = queue.remove(0);
			if (visited.contains(node.getState())) {
				continue;
			}
			if (goal.test(node.getState())) {
				return node;
			}
			visited.add(node.getState());
			List<Transition<S>> transitions = succ.apply(node.getState());
			transitions.forEach(t -> queue.add(new Node<S>(node, t.getState(), node.getCost() + t.getCost())));
		}
		return null;
	}
}
