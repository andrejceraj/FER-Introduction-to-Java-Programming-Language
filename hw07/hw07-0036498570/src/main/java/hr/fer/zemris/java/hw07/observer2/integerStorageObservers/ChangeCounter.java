package hr.fer.zemris.java.hw07.observer2.integerStorageObservers;

import java.util.Objects;

import hr.fer.zemris.java.hw07.observer2.IntegerStorageChange;
import hr.fer.zemris.java.hw07.observer2.IntegerStorageObserver;

/**
 * Observer for {@link IntegerStorage}. Counts how many times has the
 * {@code IntegerStorage} changed.
 * 
 * @author Andrej Ceraj
 *
 */
public class ChangeCounter implements IntegerStorageObserver {
	/**
	 * Counter
	 */
	private int counter = 0;

	@Override
	public void valueChanged(IntegerStorageChange change) {
		counter++;
		System.out.println("Numbers of value changes since tracking: " + counter);
	}

	/**
	 * @return counter
	 */
	public int getCounter() {
		return counter;
	}

	@Override
	public int hashCode() {
		return Objects.hash(counter);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ChangeCounter))
			return false;
		ChangeCounter other = (ChangeCounter) obj;
		return counter == other.counter;
	}

}
