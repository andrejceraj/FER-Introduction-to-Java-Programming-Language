package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representation of complex number
 * 
 * @author Andrej Ceraj
 *
 */
public class Complex {
	/**
	 * Complex number with real component equal to 0 and imaginary component equal
	 * to 0.
	 */
	public static final Complex ZERO = new Complex(0, 0);
	/**
	 * Complex number with real component equal to 1 and imaginary component equal
	 * to 0.
	 */
	public static final Complex ONE = new Complex(1, 0);
	/**
	 * Complex number with real component equal to -1 and imaginary component equal
	 * to 0.
	 */
	public static final Complex ONE_NEG = new Complex(-1, 0);
	/**
	 * Complex number with real component equal to 0 and imaginary component equal
	 * to 1.
	 */
	public static final Complex IM = new Complex(0, 1);
	/**
	 * Complex number with real component equal to 0 and imaginary component equal
	 * to -1.
	 */
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Real component of complex number
	 */
	private double real;
	/**
	 * Imaginary component of complex number
	 */
	private double imaginary;

	/**
	 * Creates an instance of Complex number with real and imaginary componen equal
	 * to 0.
	 */
	public Complex() {
		this(0, 0);
	}

	/**
	 * Creates an instance of Complex number with real and imaginary componen equal
	 * the given values
	 * 
	 * @param re value of real component
	 * @param im value of imaginary component
	 */
	public Complex(double re, double im) {
		this.real = re;
		this.imaginary = im;
	}

	/**
	 * @return module of this complex number
	 */
	public double module() {
		return Math.sqrt(real * real + imaginary * imaginary);
	}

	/**
	 * Returns new vector as multiplication of this and other complex number.
	 * 
	 * @param c other complex number
	 * @return multiplication result
	 * @throws NullPointerException if the other complex number is null.
	 */
	public Complex multiply(Complex c) {
		Objects.requireNonNull(c);
		return new Complex(real * c.getReal() - imaginary * c.getImaginary(),
				real * c.getImaginary() + imaginary * c.getReal());
	}

	/**
	 * Returns new vector as division of other complex number from this one.
	 * 
	 * @param c other complex number
	 * @return division result
	 * @throws NullPointerException if the other complex number is null.
	 */
	public Complex divide(Complex c) {
		Objects.requireNonNull(c);
		return fromMagnitudeAndAngle(this.module() / c.module(), this.getAngle() - c.getAngle());
	}

	/**
	 * Returns new vector as sum of this and other complex number.
	 * 
	 * @param c other complex number
	 * @return sum result
	 * @throws NullPointerException if the other complex number is null.
	 */
	public Complex add(Complex c) {
		Objects.requireNonNull(c);
		return new Complex(real + c.getReal(), imaginary + c.getImaginary());
	}

	/**
	 * Returns new vector as subtraction of other complex number from this one.
	 * 
	 * @param c other complex number
	 * @return subtraction result
	 * @throws NullPointerException if the other complex number is null.
	 */
	public Complex sub(Complex c) {
		Objects.requireNonNull(c);
		return new Complex(real - c.getReal(), imaginary - c.getImaginary());
	}

	/**
	 * @return negative of this complex number
	 */
	public Complex negate() {
		return new Complex(-real, -imaginary);
	}

	/**
	 * Returns n-th power of this complex number.
	 * 
	 * @param n value
	 * @return n-th power of this complex number.
	 * @throws IllegalArgumentException if the value of n is less than 0.
	 */
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException();
		}
		return fromMagnitudeAndAngle(Math.pow(module(), n), getAngle() * n);
	}

	/**
	 * Returns n-th roots of this complex number.
	 * 
	 * @param n value
	 * @return list of roots
	 * @throws IllegalArgumentException if the value of n is less than 1.
	 */
	public List<Complex> root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		List<Complex> complexNumbers = new ArrayList<Complex>(n);
		for (int i = 0; i < n; i++) {
			complexNumbers.add(
					fromMagnitudeAndAngle(Math.pow(this.module(), 1.0 / n), this.getAngle() / n + 2 * Math.PI * i / n));
		}
		return complexNumbers;
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		builder.append(real);
		if (imaginary >= 0) {
			builder.append("+i").append(imaginary);
		} else {
			builder.append("-i").append(Math.abs(imaginary));
		}
		return builder.toString();
	}

	/**
	 * Creates a complex number from the given magnitude and angle
	 * 
	 * @param magnitude
	 * @param angle
	 * @return complex number
	 * @throws IllegalArgumentException if the magnitude is less than 0.
	 */
	public static Complex fromMagnitudeAndAngle(double magnitude, double angle) {
		if (magnitude < 0) {
			throw new IllegalArgumentException();
		}
		return new Complex(magnitude * Math.cos(angle % (2 * Math.PI)), magnitude * Math.sin(angle % (2 * Math.PI)));
	}

	/**
	 * @return angle between positive x-axis (real-axis) and the complex number
	 */
	public double getAngle() {
		double angle = Math.atan2(getImaginary(), getReal());
		return angle > -1e-8 ? angle : angle + 2 * Math.PI;
	}

	/**
	 * @return real component of the complex number
	 */
	public double getReal() {
		return real;
	}

	/**
	 * @return imaginary component of the complex number
	 */
	public double getImaginary() {
		return imaginary;
	}

	@Override
	public int hashCode() {
		return Objects.hash(imaginary, real);
	}

	@Override
	public boolean equals(Object obj) {
		double delta = 1e-8;
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Complex))
			return false;
		Complex other = (Complex) obj;
		return Math.abs(real - other.getReal()) < delta && Math.abs(imaginary - other.getImaginary()) < delta;
	}

}
