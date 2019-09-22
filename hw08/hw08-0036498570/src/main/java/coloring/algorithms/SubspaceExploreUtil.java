package coloring.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Abstract class containing exploring algorithms.
 * 
 * @author Andrej Ceraj
 *
 */
public abstract class SubspaceExploreUtil {
	/**
	 * Implementation of Breadth-First Search (BFS) algorithm that does not keep
	 * track of visited states.
	 * 
	 * @param s0         starting state
	 * @param process    consumer
	 * @param succ       function
	 * @param acceptable predicate
	 */
	public static <S> void bfs(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {
		List<S> queue = new LinkedList<S>();
		queue.add(s0.get());
		while (!queue.isEmpty()) {
			S currentState = queue.remove(0);
			if (!acceptable.test(currentState)) {
				continue;
			}
			process.accept(currentState);
			queue.addAll(succ.apply(currentState));
		}
	}

	/**
	 * Implementation of Depth-First Search (DFS) algorithm that does not keep track
	 * of visited states.
	 * 
	 * @param s0         starting state
	 * @param process    consumer
	 * @param succ       function
	 * @param acceptable predicate
	 */
	public static <S> void dfs(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {
		List<S> queue = new LinkedList<S>();
		queue.add(s0.get());
		while (!queue.isEmpty()) {
			S currentState = queue.remove(0);
			if (!acceptable.test(currentState)) {
				continue;
			}
			process.accept(currentState);
			queue.addAll(0, succ.apply(currentState));
		}
	}

	/**
	 * Implementation of Breadth-First Search (BFS) algorithm that keeps track of
	 * visited states.
	 * 
	 * @param s0         starting state
	 * @param process    consumer
	 * @param succ       function
	 * @param acceptable predicate
	 */
	public static <S> void bfsv(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {
		List<S> queue = new LinkedList<S>();
		Set<S> visited = new HashSet<S>();
		queue.add(s0.get());
		while (!queue.isEmpty()) {
			S currentState = queue.remove(0);
			if (!acceptable.test(currentState)) {
				continue;
			}
			process.accept(currentState);
			List<S> childStates = succ.apply(currentState);
			childStates.removeAll(visited);
			queue.addAll(childStates);
			visited.addAll(childStates);
		}
	}

	/**
	 * Implementation of Depth-First Search (DFS) algorithm that keeps track of
	 * visited states.
	 * 
	 * @param s0         starting state
	 * @param process    consumer
	 * @param succ       function
	 * @param acceptable predicate
	 */
	public static <S> void dfsv(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {
		List<S> queue = new LinkedList<S>();
		Set<S> visited = new HashSet<S>();
		queue.add(s0.get());
		while (!queue.isEmpty()) {
			S currentState = queue.remove(0);
			if (!acceptable.test(currentState)) {
				continue;
			}
			process.accept(currentState);
			List<S> childStates = succ.apply(currentState);
			childStates.removeAll(visited);
			queue.addAll(0, childStates);
			visited.addAll(childStates);
		}
	}
}
