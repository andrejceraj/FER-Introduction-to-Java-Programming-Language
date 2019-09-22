package hr.fer.zemris.java.hw07.observer2.integerStorageObservers;

import java.util.Objects;

import hr.fer.zemris.java.hw07.observer2.IntegerStorageChange;
import hr.fer.zemris.java.hw07.observer2.IntegerStorageObserver;

/**
 * Observer for class {@link IntegerStorage}. Calculates and prints double of
 * storage value for n times, where n given upon initializing this observer.
 * 
 * @author Andrej Ceraj
 *
 */
public class DoubleValue implements IntegerStorageObserver {
	/**
	 * Counter
	 */
	private int counter = 0;
	/**
	 * Number of times to calculate and print double value when the
	 * {@link IntegerStorage} changes.
	 */
	private int n;

	/**
	 * Creates an instance of {@code DoubleValue} observer.
	 * 
	 * @param n Number of times to calculate and print double value when the
	 *          {@link IntegerStorage} changes.
	 * @throws IllegalArgumentException if n is less than 1.
	 */
	public DoubleValue(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		this.n = n;
	}

	@Override
	public void valueChanged(IntegerStorageChange change) {
		counter++;
		System.out.println("Double value: " + change.getNewValue() * 2);
		if (counter == n) {
			change.removeObserver(this);
		}

	}

	@Override
	public int hashCode() {
		return Objects.hash(counter, n);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DoubleValue))
			return false;
		DoubleValue other = (DoubleValue) obj;
		return counter == other.counter && n == other.n;
	}

}
