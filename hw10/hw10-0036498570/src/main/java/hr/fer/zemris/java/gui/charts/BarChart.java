package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Bar chart information
 * 
 * @author Andrej Ceraj
 *
 */
public class BarChart {

	/**
	 * Chart values
	 */
	private List<XYValue> values;
	/**
	 * Chart x-axis description
	 */
	private String xDescription;
	/**
	 * Chart y-axis description
	 */
	private String yDescription;
	/**
	 * Minimal value shown on y-axis
	 */
	private int minY;
	/**
	 * Maximal value shown on y-axis
	 */
	private int maxY;
	/**
	 * Difference between two y-axis value
	 */
	private int space;

	/**
	 * Creates an instance of {@link BarChart}
	 * 
	 * @param values       chart values
	 * @param xDescription x-axis description
	 * @param yDescription y-axis description
	 * @param minY         minimal value shown on y-axis
	 * @param maxY         maximal value shown on y-axis
	 * @param space        difference between two y-axis value
	 */
	public BarChart(List<XYValue> values, String xDescription, String yDescription, int minY, int maxY, int space) {
		super();
		if (minY < 0 || maxY <= minY) {
			throw new IllegalArgumentException();
		}

		this.values = values;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		this.minY = minY;
		this.maxY = maxY;
		this.space = space;

		while ((this.maxY - this.minY) % this.space != 0) {
			this.minY++;
		}
		this.values.forEach(v -> {
			if (v.getY() < this.minY) {
				throw new IllegalArgumentException();
			}
		});
	}

	/**
	 * @return chart values
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * @return x-axis description
	 */
	public String getXDescription() {
		return xDescription;
	}

	/**
	 * @return y-axis description
	 */
	public String getYDescription() {
		return yDescription;
	}

	/**
	 * @return minimal value shown on y-axis
	 */
	public int getMinY() {
		return minY;
	}

	/**
	 * @return maximal value shown on y-axis
	 */
	public int getMaxY() {
		return maxY;
	}

	/**
	 * @return difference between two y-axis value
	 */
	public int getSpace() {
		return space;
	}

}