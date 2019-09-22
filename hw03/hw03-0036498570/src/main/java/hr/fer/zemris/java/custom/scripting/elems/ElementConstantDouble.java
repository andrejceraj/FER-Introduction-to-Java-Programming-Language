package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Representation of value of type {@code double}.
 * 
 * @author Andrej Ceraj
 *
 */
public class ElementConstantDouble extends Element {
	/**
	 * Element value
	 */
	private double value;

	/**
	 * @return Element value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Creates an instance of {@code ElementConstantDouble} with given value
	 * 
	 * @param value new Element value
	 */
	public ElementConstantDouble(double value) {
		super();
		this.value = value;
	}

	@Override
	public String asText() {
		return Double.toString(value);
	}
}
