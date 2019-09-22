package hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.visitors;

import java.awt.Point;
import java.awt.Rectangle;

import hr.fer.zemris.java.hw17.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.Line;
import hr.fer.zemris.java.hw17.jvdraw.models.DrawingModel;

/**
 * Implementation of visitor used for calculating {@link JDrawingCanvas} minimal
 * bounding box containing all {@link GeometricalObject}s from
 * {@link DrawingModel}.
 * 
 * @author Andrej Ceraj
 *
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {
	/**
	 * Minimal x-axis point
	 */
	private int minX = -1;
	/**
	 * Minimal y-axis point
	 */
	private int minY = -1;
	/**
	 * Maximal x-axis point
	 */
	private int maxX = -1;
	/**
	 * Maximal y-axis point
	 */
	private int maxY = -1;

	/**
	 * Constructor
	 */
	public GeometricalObjectBBCalculator() {
	}

	@Override
	public void visit(Line line) {
		checkPoints(line.getStartingPoint(), line.getEndingPoint());
	}

	@Override
	public void visit(Circle circle) {
		visitCircle(circle);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		visitCircle(filledCircle);
	}

	/**
	 * Method checks and updates bounding box for the given circle.
	 * 
	 * @param circle circle
	 */
	private void visitCircle(Circle circle) {
		Point leftPoint = new Point(circle.getCenter().x - circle.getRadius(), circle.getCenter().y);
		Point rightPoint = new Point(circle.getCenter().x + circle.getRadius(), circle.getCenter().y);
		Point upperPoint = new Point(circle.getCenter().x, circle.getCenter().y - circle.getRadius());
		Point lowerPoint = new Point(circle.getCenter().x, circle.getCenter().y + circle.getRadius());
		checkPoints(leftPoint, rightPoint, upperPoint, lowerPoint);
	}

	/**
	 * Updated bounding box {@link Point}s if needed.
	 * 
	 * @param points points
	 */
	private void checkPoints(Point... points) {
		for (Point p : points) {
			if (minX == -1 || p.x < minX) {
				minX = p.x < 0 ? 0 : p.x;
			}
			if (minY == -1 || p.y < minY) {
				minY = p.y < 0 ? 0 : p.y;
			}
			if (maxX == -1 || p.x > maxX) {
				maxX = p.x;
			}
			if (maxY == -1 || p.y > maxY) {
				maxY = p.y;
			}
		}
	}

	/**
	 * @return bounding box
	 */
	public Rectangle getBoundingBox() {
		return new Rectangle(minX, minY, maxX - minX + 1, maxY - minY + 1);
	}

}
