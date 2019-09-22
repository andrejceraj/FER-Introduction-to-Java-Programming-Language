package hr.fer.zemris.math;

import java.util.Arrays;

/**
 * Representation of complex polynomial given as zn*z^n+...+z2*z^2+z1*z+z0.
 * 
 * @author Andrej Ceraj
 *
 */
public class ComplexPolynomial {
	/**
	 * Array of complex numbers as factors in order from z0 to zn.
	 */
	private Complex[] factors;

	/**
	 * Creates an instance of {@code ComplexPolynomial} with the given factors.
	 * 
	 * @param factors array of complex numbers
	 */
	public ComplexPolynomial(Complex... factors) {
		this.factors = factors;
	}

	/**
	 * @return complex polynomial order
	 */
	public short order() {
		return (short) (factors.length - 1);
	}

	/**
	 * Returns the result of multiplication of this and other complex polynomial.
	 * 
	 * @param p other complex polynomial
	 * @return result complex polynomial
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		int newOrder = order() + p.order();
		Complex[] newFactors = new Complex[newOrder + 1];
		for (int i = 0; i <= newOrder; i++) {
			newFactors[i] = Complex.ZERO;

			for (int j = 0; j <= i; j++) {
				if (j >= factors.length || i - j > p.order()) {
					continue;
				}
				newFactors[i] = newFactors[i].add(factors[j].multiply(p.getFactors()[i - j]));
			}
		}
		return new ComplexPolynomial(newFactors);
	}

	/**
	 * @return derived complex polynomial
	 */
	public ComplexPolynomial derive() {
		Complex[] newFactors = new Complex[order()];
		for (int i = 0; i < order(); i++) {
			newFactors[i] = factors[i + 1].multiply(new Complex(i + 1, 0));
		}
		return new ComplexPolynomial(newFactors);
	}

	/**
	 * Returns value of {@link ComplexPolynomial} when applying the given value as
	 * z.
	 * 
	 * @param z complex number
	 * @return result complex number
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ZERO;
		for (int i = 0; i <= order(); i++) {
			result = result.add(factors[i].multiply(z.power(i)));
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = order(); i >= 0; i--) {
			if (i != 0) {
				builder.append("(").append(factors[i].toString()).append(")z^").append(i).append("+");
			} else {
				builder.append("(").append(factors[i].toString()).append(")");
			}
		}
		return builder.toString();
	}

	/**
	 * @return complex polynomial factors
	 */
	public Complex[] getFactors() {
		return factors;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(factors);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ComplexPolynomial))
			return false;
		ComplexPolynomial other = (ComplexPolynomial) obj;
		return Arrays.equals(factors, other.factors);
	}

}
