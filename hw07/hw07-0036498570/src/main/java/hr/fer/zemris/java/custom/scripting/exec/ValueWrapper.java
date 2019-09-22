package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Wrapper of an object. The wrapper supports addition, subtraction,
 * multiplication, division and comparison if the stored object and the given
 * second operand are parsable into numeric value.
 * 
 * @author Andrej Ceraj
 *
 */
public class ValueWrapper {
	/**
	 * Keyword for addition
	 */
	private static final String ADD = "add";
	/**
	 * Keyword for subtraction
	 */
	private static final String SUBSTRACT = "substract";
	/**
	 * Keyword for multiplication
	 */
	private static final String MULTIPLY = "multiply";
	/**
	 * Keyword for division
	 */
	private static final String DIVIDE = "dIvide";

	/**
	 * Value
	 */
	private Object value;

	/**
	 * Creates an instance of {@code ValueWrapper}.
	 * 
	 * @param value
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}

	/**
	 * Adds the given value to the stored value.
	 * 
	 * @param incValue
	 */
	public void add(Object incValue) {
		calculate(incValue, ADD);
	}

	/**
	 * Subtracts the given value from the stored value.
	 * 
	 * @param decValue
	 */
	public void subtract(Object decValue) {
		calculate(decValue, SUBSTRACT);
	}

	/**
	 * Multiplies the stored value by the given value.
	 * 
	 * @param mulValue
	 */
	public void multiply(Object mulValue) {
		calculate(mulValue, MULTIPLY);
	}

	/**
	 * Divides the stored value by the given value.
	 * 
	 * @param divValue
	 */
	public void divide(Object divValue) {
		calculate(divValue, DIVIDE);
	}

	/**
	 * Compares the stored value with the given value.
	 * 
	 * @param withValue
	 * @return integer greater than 0 if the stored value is greater; integer lesser
	 *         than 0 is the stored value is lesser; 0 if the stored value and the
	 *         given value are equal.
	 */
	public int numCompare(Object withValue) {
		if (!ValueWrapperUtil.validValueClass(value) || !ValueWrapperUtil.validValueClass(withValue)) {
			throw new RuntimeException("Both values must be of type Integer, Double or String");
		}
		Object temp1 = ValueWrapperUtil.processObject(value);
		Object temp2 = ValueWrapperUtil.processObject(withValue);
		return temp1.toString().compareTo(temp2.toString());
	}

	/**
	 * Sets the stored value to the given value.
	 * 
	 * @param value
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * @return stored value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Method does the operation on the stored value.
	 * 
	 * @param operand
	 * @param operation
	 */
	private void calculate(Object operand, String operation) {
		if (!ValueWrapperUtil.validValueClass(value) || !ValueWrapperUtil.validValueClass(operand)) {
			throw new RuntimeException("Both values must be of type Integer, Double or String");
		}
		value = ValueWrapperUtil.processObject(value);
		Object tempObject = ValueWrapperUtil.processObject(operand);
		switch (operation) {
		case ADD:
			if (ValueWrapperUtil.resultShouldBeInteger(value, tempObject)) {
				value = Integer.valueOf((int) value + (int) tempObject);
			} else {
				value = Double.valueOf(value.toString()) + Double.valueOf(tempObject.toString());
			}
			break;
		case SUBSTRACT:
			if (ValueWrapperUtil.resultShouldBeInteger(value, tempObject)) {
				value = Integer.valueOf((int) value - (int) tempObject);
			} else {
				value = Double.valueOf(value.toString()) - Double.valueOf(tempObject.toString());
			}
			break;
		case MULTIPLY:
			if (ValueWrapperUtil.resultShouldBeInteger(value, tempObject)) {
				value = Integer.valueOf((int) value * (int) tempObject);
			} else {
				value = Double.valueOf(value.toString()) * Double.valueOf(tempObject.toString());
			}
			break;
		case DIVIDE:
			if (ValueWrapperUtil.resultShouldBeInteger(value, tempObject)) {
				value = Integer.valueOf((int) value / (int) tempObject);
			} else {
				value = Double.valueOf(value.toString()) / Double.valueOf(tempObject.toString());
			}
			break;
		}
	}

}
