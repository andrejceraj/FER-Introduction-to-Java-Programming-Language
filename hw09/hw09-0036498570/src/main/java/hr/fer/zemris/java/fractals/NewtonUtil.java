package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.math.Complex;

/**
 * Utility class for {@link Newton}.
 * 
 * @author Andrej Ceraj
 *
 */
public abstract class NewtonUtil {
	/**
	 * Scans the user input, parses it into complex numbers and when word "done" is
	 * typed in it returns an array of scanned complex numbers.
	 * 
	 * @return array of complex numbers.
	 * @throws IllegalArgumentException if the input is invalid
	 */
	public static Complex[] getInput() {
		List<Complex> comlexNumbers = new ArrayList<Complex>();
		Scanner scanner = new java.util.Scanner(System.in);
		int rootCounter = 1;

		while (true) {
			System.out.print("Root " + rootCounter + "> ");
			String input = scanner.nextLine().strip();
			if (input.equals("done")) {
				break;
			}
			try {
				comlexNumbers.add(parseInput(input));
				rootCounter++;
			} catch (IllegalArgumentException exception) {
				System.out.println("Invalid input");
			}
		}
		scanner.close();

		Complex[] roots = new Complex[comlexNumbers.size()];
		return comlexNumbers.toArray(roots);
	}

	/**
	 * Parses string into complex number
	 * 
	 * @param string
	 * @return complex number
	 */
	private static Complex parseInput(String string) {
		string = string.replaceAll("\\s+", "");
		int index = 0;
		char[] input = string.toCharArray();
		boolean hasReal = false;
		boolean hasImaginary = false;

		while (index < input.length) {
			if (Character.isDigit(input[index]) && !hasImaginary) {
				hasReal = true;
			} else if (input[index] == 'i') {
				hasImaginary = true;
			}
			index++;
		}

		double real = 0;
		double imaginary = 0;
		if (hasReal && hasImaginary) {
			real = Double.parseDouble(string.substring(0, string.indexOf('i') - 1));
			imaginary = parseImaginary(string.substring(string.indexOf('i') - 1, string.length()));
		} else if (!hasReal && hasImaginary) {
			imaginary = parseImaginary(string);
		} else if (hasReal && !hasImaginary) {
			real = Double.parseDouble(string);
		} else {
			throw new IllegalArgumentException();
		}

		return new Complex(real, imaginary);
	}

	/**
	 * Gets value from imaginary part of a complex number string.
	 * 
	 * @param string imaginary part of a complex number
	 * @return imaginary value
	 */
	private static Double parseImaginary(String string) {
		if (string.endsWith("i")) {
			string = string.replace("i", "1");
		}
		return Double.parseDouble(string.replaceAll("i", ""));
	}
}
