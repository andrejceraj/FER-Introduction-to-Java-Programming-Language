package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Demonstration of {@link StackObject} class. Program gets an argument through
 * a command line. When parsing the argument if an integer occurs it is stored
 * to stack; if an operator occurs, it takes two numbers from the stack, and
 * stores the result to the stack.
 * 
 * @author Andrej Ceraj
 *
 */
public class StackDemo {
	/**
	 * Method called when the program starts.
	 * 
	 * @param args Arguments given through the command line
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.print("Invalid number of arguments");
			return;
		}

		Object[] array = parseInput(args[0]);
		try {
			Integer result = calculate(array);
			System.out.print("Expression evaluates to " + result + ".\n");
		} catch (EmptyStackException emptyStackException) {
			System.out.print("Expression is invalid\n");
		} catch (IllegalArgumentException illegalArgumentException) {
			System.out.print(illegalArgumentException.getMessage() != null ? illegalArgumentException.getMessage()
					: "Expression is invalid\n");
		}
	}

	/**
	 * Method creates an array of numbers and operators from the input format.
	 * 
	 * @param input String in determined format
	 * @return Array of numbers and operators.
	 */
	private static Object[] parseInput(String input) {
		String[] splitInput = input.split(" ");
		Object[] array = new Object[splitInput.length];
		for (int i = 0; i < splitInput.length; i++) {
			try {
				array[i] = (Object) Integer.parseInt(splitInput[i]);
			} catch (IllegalArgumentException exception) {
				array[i] = (Object) splitInput[i];
			}
		}
		return array;
	}

	/**
	 * Method calculates the final result from the array of numbers and operators.
	 * 
	 * @param array Array of numbers and operators
	 * @return Final result
	 */
	private static Integer calculate(Object[] array) {
		ObjectStack objectStack = new ObjectStack();
		for (Object object : array) {
			if (object instanceof Integer) {
				objectStack.push(object);
			} else if (object instanceof String) {
				processStringObject(object, objectStack);
			} else {
				throw new IllegalArgumentException();
			}
		}
		if (objectStack.size() != 1) {
			throw new IllegalArgumentException("More than one object is remained on the stack.");
		}
		return (Integer) objectStack.pop();
	}

	/**
	 * When one of the operators is contained in the given object, the method takes
	 * two numbers from the stack, calculates the result with the given operator and
	 * then stores the result to the stack.
	 * 
	 * @throws IllegalArgumentException Exception is thrown when the object does not
	 *                                  contain an operator.
	 * @param object      Object that should contain an operator
	 * @param objectStack Representation of a stack
	 */
	private static void processStringObject(Object object, ObjectStack objectStack) throws IllegalArgumentException {
		Integer secondOperand = (Integer) objectStack.pop(), firstOperand = (Integer) objectStack.pop();
		switch ((String) object) {
		case "+":
			objectStack.push((Integer) (firstOperand + secondOperand));
			break;
		case "-":
			objectStack.push((Integer) (firstOperand - secondOperand));
			break;
		case "*":
			objectStack.push((Integer) (firstOperand * secondOperand));
			break;
		case "/":
			if (secondOperand == 0) {
				throw new IllegalArgumentException("Cannot devide by 0.\n");
			}
			objectStack.push((Integer) (firstOperand / secondOperand));
			break;
		case "%":
			if (secondOperand == 0) {
				throw new IllegalArgumentException("Cannot modulo by 0.\n");
			}
			objectStack.push((Integer) (firstOperand % secondOperand));
			break;
		default:
			throw new IllegalArgumentException("Exception argument is invalid\n");
		}
	}

}
