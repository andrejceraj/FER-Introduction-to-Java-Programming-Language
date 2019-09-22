package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Representation of a variable
 * 
 * @author cerko
 *
 */
public class ElementVariable extends Element {
	/**
	 * Variable name
	 */
	private String name;

	/**
	 * @return Variable name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Creates a representation of a variable with given name
	 * 
	 * @param name Variable name
	 */
	public ElementVariable(String name) {
		super();
		this.name = name;
	}

	@Override
	public String asText() {
		return name;
	}

}
