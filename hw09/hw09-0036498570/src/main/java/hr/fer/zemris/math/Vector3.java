package hr.fer.zemris.math;

import java.util.Objects;

/**
 * Representation of 3-dimensional vector
 * 
 * @author Andrej Ceraj
 *
 */
public class Vector3 {
	/**
	 * x-axis length
	 */
	private double x;
	/**
	 * y-axis length
	 */
	private double y;
	/**
	 * z-axis length
	 */
	private double z;

	/**
	 * Creates an instance of {@code Vector3} with given dimensions.
	 * 
	 * @param x axis length
	 * @param y axis length
	 * @param z axis length
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * @return vector length
	 */
	public double norm() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * @return vector scaled so its length is equal to 1.
	 */
	public Vector3 normalized() {
		double norm = norm();
		return new Vector3(x / norm, y / norm, z / norm);
	}

	/**
	 * Returns new vector as sum of this and the other {@code Vector3}.
	 * 
	 * @param other Vector
	 * @return vector sum
	 * @throws NullPointerException if the other vector is null.
	 */
	public Vector3 add(Vector3 other) {
		Objects.requireNonNull(other);
		return new Vector3(x + other.getX(), y + other.getY(), z + other.getZ());
	}

	/**
	 * Returns new vector as subtraction of other vector from this vector.
	 * 
	 * @param other Vector
	 * @return subtraction vector
	 * @throws NullPointerException if the other vector is null.
	 */
	public Vector3 sub(Vector3 other) {
		Objects.requireNonNull(other);
		return new Vector3(x - other.getX(), y - other.getY(), z - other.getZ());
	}

	/**
	 * Returns new vector as scalar product of this and the other {@code Vector3}.
	 * 
	 * @param other Vector
	 * @return scalar product
	 * @throws NullPointerException if the other vector is null.
	 */
	public double dot(Vector3 other) {
		Objects.requireNonNull(other);
		return x * other.getX() + y * other.getY() + z * other.getZ();
	}

	/**
	 * Returns new vector as vector product of this and the other {@code Vector3}.
	 * 
	 * @param other Vector
	 * @return vector product
	 * @throws NullPointerException if the other vector is null.
	 */
	public Vector3 cross(Vector3 other) {
		Objects.requireNonNull(other);
		return new Vector3(y * other.getZ() - z * other.getY(), z * other.getX() - x * other.getZ(),
				x * other.getY() - y * other.getX());

	}

	/**
	 * Returns new vector as this vector scaled with the given value.
	 * 
	 * @param s value
	 * @return scaled vector
	 */
	public Vector3 scale(double s) {
		return new Vector3(x * s, y * s, z * s);
	}

	/**
	 * Returns value of cos(fi), where fi is angle between this and the other
	 * vector.
	 * 
	 * @param other vector
	 * @return value of cos(fi)
	 */
	public double cosAngle(Vector3 other) {
		Objects.requireNonNull(other);
		return this.dot(other) / (this.norm() * other.norm());
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
	 * @return z-axis length
	 */
	public double getZ() {
		return z;
	}

	/**
	 * @return x,y and z-axis lengths as array
	 */
	public double[] toArray() {
		return new double[] { x, y, z };
	}

	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}

}
