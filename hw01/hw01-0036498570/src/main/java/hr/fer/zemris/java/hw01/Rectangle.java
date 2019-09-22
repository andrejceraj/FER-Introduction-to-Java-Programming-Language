package hr.fer.zemris.java.hw01;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Program that calculates area and perimeter of a rectangle with given width
 * and height
 * 
 * @author Andrej Ceraj
 *
 */
public class Rectangle {

	/**
	 * Method which is called when the program starts
	 * 
	 * @param args arguments from the command line
	 */
	public static void main(String[] args) {
		if (args.length == 2) {
			try {
				double width = Double.parseDouble(args[0]), height = Double.parseDouble(args[1]);
				print(width, height);
			} catch (NumberFormatException e) {
				System.out.println("Uneseni argumenti se ne mogu protumačiti kao brojevi.");
			}
		} else if (args.length != 0 && args.length != 2) {
			System.out.println("Unijeli ste pogrešan broj argumenata.");
		} else {
			Scanner scanner = new Scanner(System.in);
			double width = getDimensionValue(scanner, "širinu"), height = getDimensionValue(scanner, "visinu");
			print(width, height);
		}
	}

	/**
	 * Calculates area of a rectangle with given width and height
	 * 
	 * @param width  width of the rectangle
	 * @param height height of the rectangle
	 * @return area of the rectangle
	 */
	public static double area(double width, double height) {
		if(width <= 0 || height <=0) {
			throw new IllegalArgumentException();
		}
		return width * height;
	}

	/**
	 * Calculates perimeter of a rectangle with given width and height
	 * 
	 * @param width  width of the rectangle
	 * @param height height of the rectangle
	 * @return perimeter of the rectangle
	 */
	public static double perimeter(double width, double height) {
		if(width <= 0 || height <=0) {
			throw new IllegalArgumentException();
		}
		return 2 * (width + height);
	}

	/**
	 * Method that handles user input. The method checks if the input is positive
	 * decimal number, if not the adequate message is displayed.
	 * 
	 * @param scanner   User input scanner
	 * @param dimension dimension that user should enter, width or height
	 * @return positive decimal number
	 */
	public static double getDimensionValue(Scanner scanner, String dimension) {
		while (true) {
			System.out.print("Unesite " + dimension + " > ");
			String input = scanner.next();
			try {
				double value = Double.parseDouble(input);
				if (value < 0) {
					System.out.println("Unijeli ste negativnu vrijednost.");
				} else if (value == 0) {
					System.out.println("Unijeli ste vrijednost 0");
				} else {
					return value;
				}
			} catch (NumberFormatException e) {
				System.out.println("'" + input + "' se ne može protumačiti kao broj.");
			}
		}
	}

	/**
	 * Prints width, height, area and perimeter of a rectangle with numbers rounded
	 * to 8 decimal spaces
	 * 
	 * @param width  width of the rectangle
	 * @param height height of the rectangle
	 */
	public static void print(double width, double height) {
		DecimalFormat decimalFormat = new DecimalFormat("0.00000000");
		String area = decimalFormat.format(area(width, height)),
				perimeter = decimalFormat.format(perimeter(width, height));
		System.out.println("Pravokutnik širine " + width + " i visine " + height + " ima površinu "
				+ Double.parseDouble(area) + " te opseg " + Double.parseDouble(perimeter));
	}
}
