package hr.fer.zemris.java.gui.charts;

/**
 * Representation of point in coordinate system
 * 
 * @author Andrej Ceraj
 *
 */
public class XYValue {
	/**
	 * x coordinate
	 */
	private int x;
	/**
	 * y coordinate
	 */
	private int y;

	/**
	 * Creates an instance of {@link XYValue}
	 * 
	 * @param x
	 * @param y
	 */
	public XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * @return x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return y coordinate
	 */
	public int getY() {
		return y;
	}

}