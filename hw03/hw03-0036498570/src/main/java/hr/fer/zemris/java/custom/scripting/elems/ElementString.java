package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Representation of a string
 * 
 * @author Andrej Ceraj
 *
 */
public class ElementString extends Element {
	/**
	 * String
	 */
	private String value;

	/**
	 * @return String
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Creates a representation of a string with given value
	 * 
	 * @param value String to store in the element
	 */
	public ElementString(String value) {
		super();
		this.value = value;
	}

	@Override
	public String asText() {
		return value;
	}

}
