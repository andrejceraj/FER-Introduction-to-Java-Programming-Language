package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program that calculates factorial of a given input. It continues the process
 * until the user types in 'kraj'
 * 
 * @author Andrej Ceraj
 *
 */
public class Factorial {

	/**
	 * Method which is called when the program starts
	 * 
	 * @param args parameters are not used
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String input;
		while (true) {
			System.out.print("Unesite broj > ");
			input = sc.next();
			if (input.equals("kraj")) {
				break;
			}
			try {
				Integer number = Integer.parseInt(input);
				if (number <= 20 && number >= 3) {
					long result = factorial(number);
					System.out.println(input + "! = " + result);
				} else {
					System.out.println("'" + number + "' nije broj u dozvoljenom rasponu.");
				}
			} catch (NumberFormatException e) {
				System.out.println("'" + input + "' nije cijeli broj.");
			}
		}
		System.out.println("Doviđenja.");
		sc.close();
	}

	/**
	 * Method calculates factorial of a given number
	 * 
	 * @param number argument of x! function
	 * @return result of x! function
	 */
	public static long factorial(Integer number) {
		if (number < 0) {
			throw  new IllegalArgumentException();
		}
		long result = 1;
		for (int i = 2; i <= number; i++) {
			result = result * i;
		}
		return result;
	}
}
