package searching.slagalica;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import searching.algorithms.Transition;

/**
 * Representation of 3x3 Sliding Puzzle
 * 
 * @author Andrej Ceraj
 *
 */
public class Slagalica implements Function<KonfiguracijaSlagalice, List<Transition<KonfiguracijaSlagalice>>>,
		Predicate<KonfiguracijaSlagalice>, Supplier<KonfiguracijaSlagalice> {

	/**
	 * Puzzle configuration
	 */
	private KonfiguracijaSlagalice configuration;

	/**
	 * Creates an instance of {@code Slagalica} with the given
	 * {@link KonfiguracijaSlagalice}.
	 * 
	 * @param configuration puzzle configuration
	 */
	public Slagalica(KonfiguracijaSlagalice configuration) {
		Objects.requireNonNull(configuration);
		this.configuration = configuration;
	}

	@Override
	public List<Transition<KonfiguracijaSlagalice>> apply(KonfiguracijaSlagalice state) {
		List<Transition<KonfiguracijaSlagalice>> states = new ArrayList<Transition<KonfiguracijaSlagalice>>();
		int indexOfSpace = state.indexOfSpace();

		if (!inTopRow(indexOfSpace)) {
			states.add(newTransition(state, indexOfSpace, indexOfSpace - 3));
		}
		if (!inBottomRow(indexOfSpace)) {
			states.add(newTransition(state, indexOfSpace, indexOfSpace + 3));
		}
		if (!inLeftColumn(indexOfSpace)) {
			states.add(newTransition(state, indexOfSpace, indexOfSpace - 1));
		}
		if (!inRightColumn(indexOfSpace)) {
			states.add(newTransition(state, indexOfSpace, indexOfSpace + 1));
		}
		return states;
	}

	@Override
	public boolean test(KonfiguracijaSlagalice state) {
		int[] polje = state.getPolje();
		for (int i = 0; i < polje.length; i++) {
			if (!(i + 1 == polje[i] || (i == polje.length - 1 && polje[i] == 0))) {
				return false;
			}
		}
		return true;
	}

	@Override
	public KonfiguracijaSlagalice get() {
		return configuration;
	}

	/**
	 * Creates a {@link Transition} based on the given state and 2 indexes of a
	 * puzzle content to be replaced.
	 * 
	 * @param state        current state
	 * @param indexOfSpace index of puzzle content empty field
	 * @param indexToMove  index of a piece to put to the empty field
	 * @return new {@code Transition}
	 */
	private Transition<KonfiguracijaSlagalice> newTransition(KonfiguracijaSlagalice state, int indexOfSpace,
			int indexToMove) {
		int[] polje = state.getPolje();
		polje[indexOfSpace] = polje[indexToMove];
		polje[indexToMove] = 0;
		return new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(polje), 1);
	}

	/**
	 * Checks if the puzzle piece with the given index is in the top row of the
	 * sliding puzzle.
	 * 
	 * @param index index of a puzzle piece
	 * @return true if the index is in the top row; false otherwise.
	 */
	private boolean inTopRow(int index) {
		if (index == 0 || index == 1 || index == 2) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if the puzzle piece with the given index is in the bottom row of the
	 * sliding puzzle.
	 * 
	 * @param index index of a puzzle piece
	 * @return true if the index is in the bottom row; false otherwise.
	 */
	private boolean inBottomRow(int index) {
		if (index == 6 || index == 7 || index == 8) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if the puzzle piece with the given index is in the left column of the
	 * sliding puzzle.
	 * 
	 * @param index index of a puzzle piece
	 * @return true if the puzzle piece is in the left column; false otherwise.
	 */
	private boolean inLeftColumn(int index) {
		if (index % 3 == 0) {
			return true;
		}
		return false;

	}

	/**
	 * Checks if the puzzle piece with the given index is in the right column of the
	 * sliding puzzle.
	 * 
	 * @param index index of a puzzle piece
	 * @return true if the puzzle piece is in the right column; false otherwise.
	 */
	private boolean inRightColumn(int index) {
		if (index % 3 == 2) {
			return true;
		}
		return false;
	}

}
