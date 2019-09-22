package searching.slagalica;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Configuration of 3x3 Sliding Puzzle.
 * 
 * @author Andrej Ceraj
 *
 */
public class KonfiguracijaSlagalice {
	/**
	 * Puzzle content.
	 */
	private int[] polje;

	/**
	 * Creates an instance of {@code KonfiguracijaSlagalice} with the given puzzle
	 * content.
	 * 
	 * @param polje
	 */
	public KonfiguracijaSlagalice(int[] polje) {
		if (!checkValidity(polje)) {
			throw new IllegalArgumentException();
		}
		this.polje = polje;
	}

	/**
	 * @return puzzle content
	 */
	public int[] getPolje() {
		return Arrays.copyOf(polje, polje.length);
	}

	/**
	 * @return index of puzzle field with no element in it
	 */
	public int indexOfSpace() {
		for (int i = 0; i < polje.length; i++) {
			if (polje[i] == 0) {
				return i;
			}
		}
		throw new NoSuchElementException();
	}

	/**
	 * Method checks if the puzzle content is valid meaning if it has 9 elements,
	 * all digits from 0 to 8 appearing only once each.
	 * 
	 * @param polje puzzle content
	 * @return true if the puzzle content is valid; false otherwise
	 */
	private boolean checkValidity(int[] polje) {
		if (polje.length != 9) {
			return false;
		}
		int[] counter = new int[9];
		for (int i : polje) {
			if (i < 0 || i > 8) {
				return false;
			}
			counter[i]++;
			if (counter[i] >= 2) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < polje.length; i++) {
			if (polje[i] == 0) {
				builder.append("* ");
			} else {
				builder.append(polje[i] + " ");
			}
			if (i % 3 == 2 && i != polje.length - 1) {
				builder.append("\n");
			}
		}
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(polje);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof KonfiguracijaSlagalice))
			return false;
		KonfiguracijaSlagalice other = (KonfiguracijaSlagalice) obj;
		return Arrays.equals(polje, other.polje);
	}

}
