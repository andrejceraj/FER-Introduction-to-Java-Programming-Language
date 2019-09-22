package hr.fer.zemris.java.hw17.jvdraw.geometricalObjects;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.editors.LineEditor;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.visitors.GeometricalObjectVisitor;

/**
 * Representation of Line shape.
 * 
 * @author Andrej Ceraj
 *
 */
public class Line extends GeometricalObject {
	/**
	 * Line starting point
	 */
	private Point startingPoint;
	/**
	 * Line ending point
	 */
	private Point endingPoint;
	/**
	 * Line color
	 */
	private Color color;

	/**
	 * Constructor
	 * 
	 * @param startingPoint line starting point
	 * @param endingPoint   line ending point
	 * @param color         line color
	 */
	public Line(Point startingPoint, Point endingPoint, Color color) {
		super();
		this.startingPoint = startingPoint;
		this.endingPoint = endingPoint;
		this.color = color;
	}

	/**
	 * Constructor with Point(x1, y1) as starting point and Point(x2, y2) as ending
	 * point.
	 * 
	 * @param x1    x1
	 * @param y1    y1
	 * @param x2    x2
	 * @param y2    y2
	 * @param color color
	 */
	public Line(int x1, int y1, int x2, int y2, Color color) {
		super();
		this.startingPoint = new Point(x1, y1);
		this.endingPoint = new Point(x2, y2);
		this.color = color;
	}

	/**
	 * @return line color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets line color
	 * 
	 * @param color color
	 */
	public void setColor(Color color) {
		this.color = color;
		notifyListeners();
	}

	/**
	 * @return line starting point
	 */
	public Point getStartingPoint() {
		return startingPoint;
	}

	/**
	 * Sets line starting point
	 * 
	 * @param startingPoint point
	 */
	public void setStartingPoint(Point startingPoint) {
		this.startingPoint = startingPoint;
		notifyListeners();
	}

	/**
	 * @return line ending point
	 */
	public Point getEndingPoint() {
		return endingPoint;
	}

	/**
	 * Sets line ending point
	 * 
	 * @param endingPoint point
	 */
	public void setEndingPoint(Point endingPoint) {
		this.endingPoint = endingPoint;
		notifyListeners();
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new LineEditor(this);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Line (").append(startingPoint.x).append(",").append(startingPoint.y).append(")-(");
		sb.append(endingPoint.x).append(",").append(endingPoint.y).append(")");
		return sb.toString();
	}

}
