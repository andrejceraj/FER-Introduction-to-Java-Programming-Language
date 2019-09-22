package hr.fer.zemris.java.hw07.observer2.integerStorageObservers;

import hr.fer.zemris.java.hw07.observer2.IntegerStorageChange;
import hr.fer.zemris.java.hw07.observer2.IntegerStorageObserver;

/**
 * Observer for class {@link IntegerStorage}. Prints squared value of
 * {@code IntegerStorage} stored value.
 * 
 * @author Andrej Ceraj
 *
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorageChange change) {
		int value = change.getNewValue();
		System.out.println("Provided new value " + value + ", square is " + value * value);
	}

}
