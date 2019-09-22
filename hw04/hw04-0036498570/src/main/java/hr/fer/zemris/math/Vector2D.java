package hr.fer.zemris.math;

import java.util.Objects;

/**
 * Representation of two dimensional vector defined with length on x-axis and
 * y-axis.
 * 
 * @author Andrej Ceraj
 *
 */
public class Vector2D {
	/**
	 * x-axis length
	 */
	private double x;
	/**
	 * y-axis length
	 */
	private double y;

	/**
	 * Creates an instance of {@link Vector2D}.
	 * 
	 * @throws NullPointerException Exception is thrown if any of parameters are
	 *                              null.
	 * @param x x-axis length
	 * @param y y-axis length
	 */
	public Vector2D(double x, double y) {
		Objects.requireNonNull(x);
		Objects.requireNonNull(y);
		this.x = x;
		this.y = y;
	}

	/**
	 * @return x-axis length
	 */
	public double getX() {
		return x;
	}

	/**
	 * @return y-axis length
	 */
	public double getY() {
		return y;
	}

	/**
	 * Translates this vector by the given vector.
	 * 
	 * @throws NullPointerException Exception is thrown if the given vector is null.
	 * @param offset Vector translator
	 */
	public void translate(Vector2D offset) {
		Objects.requireNonNull(offset);
		x = x + offset.getX();
		y = y + offset.getY();
	}

	/**
	 * Creates new vector that is result of translation of this vector by the given
	 * vector.
	 * 
	 * @throws NullPointerException Exception is thrown if the given vector is null.
	 * @param offset Vector translator
	 * @return Result vector
	 */
	public Vector2D translated(Vector2D offset) {
		Objects.requireNonNull(offset);
		Vector2D newVector = this.copy();
		newVector.translate(offset);
		return newVector;
	}

	/**
	 * Rotates this vector counterclockwise by the given angle.
	 * 
	 * @param angle Angle by which this vector should be rotated.
	 */
	public void rotate(double angle) {
		double thisLength = getLength();
		double thisAngle = getAngle();
		x = thisLength * Math.cos(thisAngle + angle);
		y = thisLength * Math.sin(thisAngle + angle);
	}

	/**
	 * Creates new vector that is result of rotating this vector counterclockwise by
	 * the given angle.
	 * 
	 * @param angle Angle by which this vector should be rotated.
	 * @return Result vector
	 */
	public Vector2D rotated(double angle) {
		Vector2D newVector = this.copy();
		newVector.rotate(angle);
		return newVector;
	}

	/**
	 * Scales this vector by the given value by multiplying x and y-axis length with
	 * the given value.
	 * 
	 * @param scaler Value by which this vector should be scaled
	 */
	public void scale(double scaler) {
		x = x * scaler;
		y = y * scaler;

	}

	/**
	 * Creates new vector that is result of scaling this vector by the given value
	 * by multiplying x and y-axis length with the given value.
	 * 
	 * @param scaler Value by which this vector should be scaled
	 * @return Result vector
	 */
	public Vector2D scaled(double scaler) {
		Vector2D newVector = this.copy();
		newVector.scale(scaler);
		return newVector;
	}

	/**
	 * Creates a copy of this vector.
	 * 
	 * @return New vector that is same as this one.
	 */
	public Vector2D copy() {
		return new Vector2D(this.x, this.y);
	}

	/**
	 * @return Vector absolute length
	 */
	private double getLength() {
		return Math.sqrt(x * x + y * y);
	}

	/**
	 * @return Angle between positive x-axis and this vector orientation
	 */
	private double getAngle() {
		return Math.atan2(y, x);
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Vector2D))
			return false;
		Vector2D other = (Vector2D) obj;
		return Math.abs(x - other.x) < 1e-8 && Math.abs(y - other.y) < 1e-8;
	}
}
