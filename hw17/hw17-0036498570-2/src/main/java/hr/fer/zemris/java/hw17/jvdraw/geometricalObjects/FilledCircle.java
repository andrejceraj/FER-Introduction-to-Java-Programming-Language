package hr.fer.zemris.java.hw17.jvdraw.geometricalObjects;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.editors.FilledCircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.visitors.GeometricalObjectVisitor;

/**
 * Representation of filled circle shape
 * 
 * @author Andrej Ceraj
 *
 */
public class FilledCircle extends Circle {
	/**
	 * Circle background color
	 */
	private Color bgColor;

	/**
	 * Constructor
	 * 
	 * @param center  point
	 * @param radius  value
	 * @param fgColor background color
	 * @param bgColor foreground color
	 */
	public FilledCircle(Point center, int radius, Color fgColor, Color bgColor) {
		super(center, radius, fgColor);
		this.bgColor = bgColor;
	}

	/**
	 * @return circle backbround color
	 */
	public Color getBgColor() {
		return bgColor;
	}

	/**
	 * Sets curcle background color
	 * 
	 * @param bgColor color
	 */
	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
		notifyListeners();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.Circle#accept(hr.fer.zemris
	 * .java.hw17.jvdraw.geometricalObjects.visitors.GeometricalObjectVisitor)
	 */
	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledCircleEditor(this);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Filled circle (").append(getCenter().x).append(",").append(getCenter().y).append("), ");
		sb.append(getRadius()).append(", #").append(Integer.toHexString(bgColor.getRGB()).substring(2).toUpperCase());
		return sb.toString();
	}

}
