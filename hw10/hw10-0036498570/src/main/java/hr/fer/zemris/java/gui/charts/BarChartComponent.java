package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JComponent;

/**
 * Component graphically showing bar chart information
 * 
 * @author Andrej Ceraj
 *
 */
public class BarChartComponent extends JComponent {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = 5972216090961235477L;

	/**
	 * Space between frame and description
	 */
	private static final int FRAME_DESCRIPTION_SPACE = 4;
	/**
	 * Space between description and axis numbers
	 */
	private static final int DESCRIPTION_NUMBERS_SPACE = 16;
	/**
	 * Space between axis numbers and axis
	 */
	private static final int NUMBER_AXIS_SPACE = 8;
	/**
	 * Space between top and right frame is height * SCALED_SPACE or width *
	 * SCALED_SPACE
	 */
	private static final double SCALED_SPACE = 0.02;
	/**
	 * Space between bars and coordinate net
	 */
	private static final int BARS_SPACE = 10;

	/**
	 * Bar chart information
	 */
	private BarChart barChart;

	/**
	 * Creates an instance of {@link BarChartComponent}
	 * 
	 * @param barChart bar chart
	 */
	public BarChartComponent(BarChart barChart) {
		super();
		this.barChart = barChart;
	}

	@Override
	public void paint(Graphics graphics) {
		Graphics2D graphics2D = (Graphics2D) graphics;
		int height = getHeight();
		int width = getWidth();

		graphics2D.setColor(Color.LIGHT_GRAY);
		graphics2D.fillRect(0, 0, width, height);
		graphics2D.setColor(Color.BLACK);

		int xDescriptionLength = graphics2D.getFontMetrics().stringWidth(barChart.getXDescription());
		int yDescriptionLength = graphics2D.getFontMetrics().stringWidth(barChart.getYDescription());
		int fontHeigth = graphics2D.getFontMetrics().getHeight();

		int xDescriptionX = width / 2 - xDescriptionLength / 2;
		int xDescriptionY = height - FRAME_DESCRIPTION_SPACE;

		int yDescriptionX = FRAME_DESCRIPTION_SPACE + fontHeigth;
		int yDescriptionY = height / 2 + yDescriptionLength / 2;

		graphics2D.drawString(barChart.getXDescription(), xDescriptionX, xDescriptionY);

		AffineTransform defaultAT = graphics2D.getTransform();
		AffineTransform rotated90AT = new AffineTransform();
		rotated90AT.rotate(-Math.PI / 2);
		graphics2D.setTransform(rotated90AT);
		graphics2D.drawString(barChart.getYDescription(), -yDescriptionY, yDescriptionX);
		graphics2D.setTransform(defaultAT);

		int graphOriginX = yDescriptionX + DESCRIPTION_NUMBERS_SPACE
				+ graphics2D.getFontMetrics().stringWidth(Integer.toString(barChart.getMaxY())) + NUMBER_AXIS_SPACE;
		int graphOriginY = xDescriptionY - 2 * graphics2D.getFontMetrics().getHeight() - DESCRIPTION_NUMBERS_SPACE
				- NUMBER_AXIS_SPACE;

		graphics2D.drawLine(graphOriginX, graphOriginY, (int) (width * (1 - SCALED_SPACE)), graphOriginY);
		graphics2D.drawLine(graphOriginX, graphOriginY, graphOriginX, (int) (height * SCALED_SPACE));

		int numberOfNumbers = (barChart.getMaxY() - barChart.getMinY()) / barChart.getSpace();
		numberOfNumbers = numberOfNumbers > barChart.getMaxY() ? barChart.getMaxY() : numberOfNumbers;
		numberOfNumbers = numberOfNumbers == 0 ? 1 : numberOfNumbers;
		int distanceBetweenNumbers = (graphOriginY - (int) (height * SCALED_SPACE)) / numberOfNumbers;

		drawByYaxis(graphics2D, width, fontHeigth, graphOriginY, numberOfNumbers, distanceBetweenNumbers);
		drawByXaxis(graphics2D, width, height, fontHeigth, graphOriginX, graphOriginY, numberOfNumbers,
				distanceBetweenNumbers);

		drawAxisArrows(graphics2D, width, height, graphOriginX, graphOriginY);

	}

	/**
	 * Draws lines and numbers along y-axis
	 * 
	 * @param graphics2D             component {@link Graphics2D} object
	 * @param width                  component width
	 * @param fontHeigth             font height
	 * @param graphOriginY           y-coordinate of graph origin
	 * @param numberOfNumbers        number of values shown on y-axis
	 * @param distanceBetweenNumbers distance between numbers
	 */
	private void drawByYaxis(Graphics2D graphics2D, int width, int fontHeigth, int graphOriginY, int numberOfNumbers,
			int distanceBetweenNumbers) {

		int maxYLength = graphics2D.getFontMetrics().stringWidth(Integer.toString(barChart.getMaxY()));

		for (int i = 0; i <= numberOfNumbers; i++) {
			int numberX = FRAME_DESCRIPTION_SPACE + fontHeigth + DESCRIPTION_NUMBERS_SPACE;
			int numberY = graphOriginY + fontHeigth / 2 - i * distanceBetweenNumbers;
			String numberString = Integer.toString(barChart.getMinY() + barChart.getSpace() * i);
			int numberWidth = graphics2D.getFontMetrics().stringWidth((numberString));

			graphics2D.setColor(Color.BLACK);
			graphics2D.drawString(numberString, numberX + maxYLength - numberWidth, numberY);

			graphics2D.setColor(Color.DARK_GRAY);
			graphics2D.drawLine(
					numberX + NUMBER_AXIS_SPACE / 2
							+ graphics2D.getFontMetrics().stringWidth(Integer.toString(barChart.getMaxY())),
					graphOriginY - i * distanceBetweenNumbers, (int) (width * (1 - SCALED_SPACE)),
					graphOriginY - i * distanceBetweenNumbers);
		}
		graphics2D.setColor(Color.BLACK);
	}

	/**
	 * Draws lines and numbers along x-axis
	 * 
	 * @param graphics2D             component {@link Graphics2D} object
	 * @param width                  component width
	 * @param height                 component height
	 * @param fontHeigth             font height
	 * @param graphOriginX           y-coordinate of graph origin
	 * @param graphOriginY           x-coordinate of graph origin
	 * @param numberOfNumbers        number of values shown on y-axis
	 * @param distanceBetweenNumbers distance between numbers
	 */
	private void drawByXaxis(Graphics2D graphics2D, int width, int height, int fontHeigth, int graphOriginX,
			int graphOriginY, int numberOfNumbers, int distanceBetweenNumbers) {

		List<XYValue> xyValues = barChart.getValues();
		int barWidth = ((int) (width * (1 - SCALED_SPACE)) - graphOriginX) / barChart.getValues().size();
		for (int i = 0; i < xyValues.size(); i++) {

			String xValue = Integer.toString(xyValues.get(i).getX());
			int numberX = graphOriginX + barWidth * i + barWidth / 2 - graphics2D.getFontMetrics().stringWidth(xValue);
			int numberY = graphOriginY + NUMBER_AXIS_SPACE + fontHeigth;

			graphics2D.setColor(Color.BLACK);
			graphics2D.drawString(xValue, numberX, numberY);

			graphics2D.setColor(Color.MAGENTA);
			graphics2D.fillRect(graphOriginX + barWidth * i + BARS_SPACE,
					graphOriginY - numberOfNumbers * (xyValues.get(i).getY() - barChart.getMinY())
							* distanceBetweenNumbers / (barChart.getMaxY() - barChart.getMinY()),
					barWidth - 2 * BARS_SPACE, numberOfNumbers * (xyValues.get(i).getY() - barChart.getMinY())
							* distanceBetweenNumbers / (barChart.getMaxY() - barChart.getMinY()));

			graphics2D.setColor(Color.DARK_GRAY);
			graphics2D.drawLine(graphOriginX + barWidth * i, graphOriginY + NUMBER_AXIS_SPACE / 2,
					graphOriginX + barWidth * i, (int) (height * SCALED_SPACE));
		}
	}

	/**
	 * Draws axis arrows
	 * 
	 * @param graphics2D   component {@link Graphics2D} object
	 * @param width        component width
	 * @param height       component height
	 * @param graphOriginX y-coordinate of graph origin
	 * @param graphOriginY x-coordinate of graph origin
	 */
	private void drawAxisArrows(Graphics2D graphics2D, int width, int height, int graphOriginX, int graphOriginY) {
		int[] xPoints = new int[3];
		int[] yPoints = new int[3];
		xPoints[0] = (int) (width * (1 - SCALED_SPACE)) + 5;
		yPoints[0] = graphOriginY;
		xPoints[1] = xPoints[0] - 15;
		yPoints[1] = yPoints[0] + 4;
		xPoints[2] = xPoints[1];
		yPoints[2] = yPoints[0] - 4;
		graphics2D.fillPolygon(xPoints, yPoints, 3);

		xPoints[0] = graphOriginX;
		yPoints[0] = (int) (height * SCALED_SPACE) - 5;
		xPoints[1] = xPoints[0] - 4;
		yPoints[1] = yPoints[0] + 15;
		xPoints[2] = xPoints[0] + 4;
		yPoints[2] = yPoints[1];
		graphics2D.fillPolygon(xPoints, yPoints, 3);
	}
}