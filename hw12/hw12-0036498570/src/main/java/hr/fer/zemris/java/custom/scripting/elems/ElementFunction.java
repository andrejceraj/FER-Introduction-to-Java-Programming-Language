package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Representation of a function.
 * 
 * @author Andrej Ceraj
 *
 */
public class ElementFunction extends Element {
	/**
	 * Name of the function
	 */
	private String name;

	/**
	 * @return Function name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Creates a representation of function with given function name
	 * 
	 * @param name Function name
	 */
	public ElementFunction(String name) {
		super();
		this.name = name;
	}

	@Override
	public String asText() {
		return "@" + name;
	}

}
