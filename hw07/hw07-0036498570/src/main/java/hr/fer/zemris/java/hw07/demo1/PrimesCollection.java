package hr.fer.zemris.java.hw07.demo1;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class storing only a count of prime numbers to be generated while iterating
 * through the {@code PrimesCollection}.
 * 
 * @author Andrej Ceraj
 *
 */
public class PrimesCollection implements Iterable<Integer> {
	/**
	 * Prime numbers count.
	 */
	private int primesCount;

	/**
	 * Creates an instance of {@code PrimesCollection}.
	 * 
	 * @param count
	 */
	public PrimesCollection(int count) {
		primesCount = count;
	}

	/**
	 * @return prime numbers count
	 */
	public int getPrimesCount() {
		return primesCount;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new PrimeIterator();
	}

	/**
	 * An iterator for class {@link PrimesCollection}.
	 * 
	 * @author Andrej Ceraj
	 *
	 */
	private class PrimeIterator implements Iterator<Integer> {
		/**
		 * Current prime number in iterator
		 */
		private int currentPrime = 1;
		/**
		 * Next prime number in iterator
		 */
		private int nextPrime = 2;
		/**
		 * Counter of times iterated
		 */
		private int counter = 0;
		/**
		 * True if the collection has next element; false otherwise
		 */
		private boolean hasNext = true;

		@Override
		public boolean hasNext() {
			return hasNext;
		}

		@Override
		public Integer next() {
			if (!hasNext) {
				throw new NoSuchElementException();
			}
			counter++;
			currentPrime = nextPrime;
			for (int i = nextPrime + 1; hasNext; i++) {
				if (i < 0 || counter >= primesCount) {
					hasNext = false;
					break;
				}
				if (isPrime(i)) {
					nextPrime = i;
					break;
				}
			}
			return currentPrime;
		}

		/**
		 * Checks if the given number is prime
		 * 
		 * @param number
		 * @return true if the number is prime; false otherwise.
		 */
		private boolean isPrime(int number) {
			for (int i = 2; i * i <= number; i++) {
				if (number % i == 0) {
					return false;
				}
			}
			return true;
		}
	}

}
