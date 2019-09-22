package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Utility class for {@link ValueWrapper}.
 * 
 * @author Andrej Ceraj
 *
 */
public abstract class ValueWrapperUtil {

	/**
	 * Checks if the given object is of valid type for arithmetic operations and
	 * comparing.
	 * 
	 * @param object
	 * @return true if it is valid; false otherwise
	 */
	public static boolean validValueClass(Object object) {
		if (object == null || object instanceof Integer || object instanceof Double || object instanceof String) {
			return true;
		}
		return false;
	}

	/**
	 * Processes the given object into a numeric value and returns the value cast as
	 * object class.
	 * 
	 * @param object
	 * @return numeric value
	 * @throws RuntimeException if the given object is not convertable into numeric
	 *                          value.
	 */
	public static Object processObject(Object object) throws RuntimeException {
		if (object == null) {
			return Integer.valueOf(0);
		} else if (object instanceof Integer) {
			return Integer.valueOf((int) object);
		} else if (object instanceof Double) {
			Double number = (double) object;
			return number;
		} else {
			try {
				object = parseString(object);
				return object;
			} catch (NumberFormatException e) {
				throw new RuntimeException("Conversion of object: " + object.toString() + " failed");
			}
		}
	}

	/**
	 * Checks if both objects are of Integer type.
	 * 
	 * @param o1 first operand
	 * @param o2 second operand
	 * @return true if they are; false otherwise
	 */
	public static boolean resultShouldBeInteger(Object o1, Object o2) {
		if (o1 instanceof Integer && o2 instanceof Integer) {
			return true;
		}
		return false;
	}

	/**
	 * Returns numeric value of String object. Returned value is of type Integer if
	 * possible; return value is of type Double otherwise
	 * 
	 * @param object
	 * @return numeric value
	 * @throws NumberFormatException if String object is not parsable into numeric
	 *                               value.
	 */
	private static Object parseString(Object object) throws NumberFormatException {
		String string = (String) object;
		if (string.indexOf('.') == -1 && string.toUpperCase().indexOf('E') == -1) {
			return Integer.parseInt(string);
		} else {
			return Double.parseDouble(string);
		}
	}
}
