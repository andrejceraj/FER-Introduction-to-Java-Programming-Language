package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Representation of an operator
 * 
 * @author Andrej Ceraj
 *
 */
public class ElementOperator extends Element {
	/**
	 * The operator
	 */
	private String symbol;

	/**
	 * @return The operator
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Creates a representation of an operator
	 * 
	 * @param symbol Operator
	 */
	public ElementOperator(String symbol) {
		super();
		this.symbol = symbol;
	}

	@Override
	public String asText() {
		return symbol;
	}
}
