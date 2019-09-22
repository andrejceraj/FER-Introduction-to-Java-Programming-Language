package hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.visitors;

import java.awt.BasicStroke;
import java.awt.Graphics2D;

import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.Line;

/**
 * Implementation of painter visitor for {@link GeometricalObject}s. This
 * visitor draws objects on given {@link Graphics2D}.
 * 
 * @author Andrej Ceraj
 *
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {
	/**
	 * Graphics object
	 */
	private Graphics2D g;

	/**
	 * Constructor
	 * 
	 * @param g graphics object
	 */
	public GeometricalObjectPainter(Graphics2D g) {
		this.g = g;
		g.setStroke(new BasicStroke(2));
	}

	@Override
	public void visit(Line line) {
		int x1 = line.getStartingPoint().x;
		int y1 = line.getStartingPoint().y;
		int x2 = line.getEndingPoint().x;
		int y2 = line.getEndingPoint().y;
		g.setColor(line.getColor());
		g.drawLine(x1, y1, x2, y2);
	}

	@Override
	public void visit(Circle circle) {
		drawCircle(circle);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		drawCircle(filledCircle);
	}

	/**
	 * Method for drawing {@link Circle} and {@link FilledCircle}.
	 * 
	 * @param circle any type of circle
	 */
	private void drawCircle(Circle circle) {
		int r = circle.getRadius();
		int x = circle.getCenter().x;
		int y = circle.getCenter().y;

		if (circle instanceof FilledCircle) {
			g.setColor(((FilledCircle) circle).getBgColor());
			g.fillOval(x - r, y - r, r * 2, r * 2);
		}

		g.setColor(circle.getFgColor());
		g.drawOval(x - r, y - r, r * 2, r * 2);

	}

}
