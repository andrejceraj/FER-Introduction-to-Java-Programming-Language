package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

/**
 * Drawing in LSystems is represented by turtle that moves, rotates and draws on
 * the screen. This class represents a turtle state meaning it keeps track of
 * turtle's position, orientation, drawing color and length of one turtle step.
 * 
 * @author Andrej Ceraj
 *
 */
public class TurtleState {
	/**
	 * Representation of turtle's position on the screen.
	 */
	Vector2D position;
	/**
	 * Representation of turtle's orientation.
	 */
	Vector2D orientation;
	/**
	 * Color turtle uses for drawing on the screen.
	 */
	Color color;
	/**
	 * Length turtle moves one the screen with one step.
	 */
	double shift;

	/**
	 * Creates an instance of {@code TurtleState}.
	 * 
	 * @param position
	 * @param orientation
	 * @param color
	 * @param shift
	 */
	public TurtleState(Vector2D position, Vector2D orientation, Color color, double shift) {
		super();
		this.position = position;
		this.orientation = orientation;
		this.color = color;
		this.shift = shift;
	}

	/**
	 * @return Copy of this {@code TurtleState}.
	 */
	public TurtleState copy() {
		return new TurtleState(this.position, this.orientation, this.color, this.shift);
	}

	/**
	 * @return current position
	 */
	public Vector2D getPosition() {
		return position;
	}

	/**
	 * Sets current position.
	 * 
	 * @param position
	 */
	public void setPosition(Vector2D position) {
		this.position = position;
	}

	/**
	 * @return current orientation
	 */
	public Vector2D getOrientation() {
		return orientation;
	}

	/**
	 * Sets current orientation.
	 * 
	 * @param orientation
	 */
	public void setOrientation(Vector2D orientation) {
		this.orientation = orientation;
	}

	/**
	 * @return current drawing color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets current drawing color.
	 * 
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @return length of one turtle step
	 */
	public double getShift() {
		return shift;
	}

	/**
	 * Sets the length of one turtle step.
	 * 
	 * @param shift
	 */
	public void setShift(double shift) {
		this.shift = shift;
	}

}
