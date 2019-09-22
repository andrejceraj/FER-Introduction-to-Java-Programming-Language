package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Representation of value of type {@code int}.
 * 
 * @author Andrej Ceraj
 *
 */
public class ElementConstantInteger extends Element {
	/**
	 * Element value
	 */
	private int value;

	/**
	 * @return Element value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Creates an instance of {@code ElementConstantDouble} with given value
	 * 
	 * @param value new Element value
	 */
	public ElementConstantInteger(int value) {
		super();
		this.value = value;
	}

	@Override
	public String asText() {
		return Integer.toString(value);
	}

}
