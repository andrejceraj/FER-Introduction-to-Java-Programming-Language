package searching.algorithms;

/**
 * Representation of state transition. This class contains a reference to the
 * new state, and the cost of the transition into the new state.
 * 
 * @author Andrej Ceraj
 *
 * @param <S> State
 */
public class Transition<S> {
	/**
	 * New state (state after the transition has been done)
	 */
	private S state;
	/**
	 * Transition cost
	 */
	private double cost;

	/**
	 * Creates an instance of a class {@code Transition} with the given new state
	 * and transition cost
	 * 
	 * @param state new state
	 * @param cost  transition cost
	 */
	public Transition(S state, double cost) {
		super();
		this.state = state;
		this.cost = cost;
	}

	/**
	 * @return state
	 */
	public S getState() {
		return state;
	}

	/**
	 * @return transition cost
	 */
	public double getCost() {
		return cost;
	}

}
