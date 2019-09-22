package hr.fer.zemris.java.hw17.jvdraw.geometricalObjects;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.editors.CircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.visitors.GeometricalObjectVisitor;

/**
 * Representation of empty circle shape
 * 
 * @author Andrej Ceraj
 *
 */
public class Circle extends GeometricalObject {
	/**
	 * Circle center point
	 */
	private Point center;
	/**
	 * Circle radius
	 */
	private int radius;
	/**
	 * Circle foreground color
	 */
	private Color fgColor;

	/**
	 * Constructor
	 * 
	 * @param center  point
	 * @param radius  value
	 * @param fgColor color
	 */
	public Circle(Point center, int radius, Color fgColor) {
		super();
		this.center = center;
		this.radius = radius;
		this.fgColor = fgColor;
	}

	/**
	 * @return circle foreground color
	 */
	public Color getFgColor() {
		return fgColor;
	}

	/**
	 * Sets circle foreground color
	 * 
	 * @param fgColor color
	 */
	public void setFgColor(Color fgColor) {
		this.fgColor = fgColor;
		notifyListeners();
	}

	/**
	 * @return circle center point
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * Sets circle center point
	 * 
	 * @param center point
	 */
	public void setCenter(Point center) {
		this.center = center;
		notifyListeners();
	}

	/**
	 * @return circle radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * Sets circle radius
	 * 
	 * @param radius value
	 */
	public void setRadius(int radius) {
		this.radius = radius;
		notifyListeners();
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new CircleEditor(this);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Circle (").append(getCenter().x).append(",").append(getCenter().y).append("), ").append(getRadius());
		return sb.toString();
	}

}
