package hr.fer.zemris.java.hw07.observer1.integerStorageObservers;

import hr.fer.zemris.java.hw07.observer1.IntegerStorage;
import hr.fer.zemris.java.hw07.observer1.IntegerStorageObserver;

/**
 * Observer for class {@link IntegerStorage}. Prints squared value of
 * {@code IntegerStorage} stored value.
 * 
 * @author Andrej Ceraj
 *
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorage istorage) {
		int value = istorage.getValue();
		System.out.println("Provided new value " + value + ", square is " + value * value);
	}

}
