package hr.fer.zemris.java.gui.layouts;

/**
 * Representation of {@link CalcLayout} component position
 * 
 * @author Andrej Ceraj
 *
 */
public class RCPosition {
	/**
	 * Row
	 */
	private int row;
	/**
	 * Column
	 */
	private int column;

	/**
	 * Creates an instance of {@link RCPosition}
	 * 
	 * @param row
	 * @param column
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * @return row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @return column
	 */
	public int getColumn() {
		return column;
	}

}
