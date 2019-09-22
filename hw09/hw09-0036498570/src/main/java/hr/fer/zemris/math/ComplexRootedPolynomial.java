package hr.fer.zemris.math;

import java.util.Arrays;
import java.util.Objects;

/**
 * Representation of complex polynomial given as z0*(z-z1)*(z-z2)*...*(z-zn)
 * 
 * @author Andrej Ceraj
 *
 */
public class ComplexRootedPolynomial {
	/**
	 * z0 complex number
	 */
	private Complex constant;
	/**
	 * Array of root complex numbers from z1 to zn.
	 */
	private Complex[] roots;

	/**
	 * Creates an instance of {@link ComplexRootedPolynomial}.
	 * 
	 * @param constant complex number
	 * @param roots    array of complex numbers
	 */
	public ComplexRootedPolynomial(Complex constant, Complex... roots) {
		this.constant = constant;
		this.roots = roots;
	}

	/**
	 * Returns value of {@link ComplexRootedPolynomial} when applying the given
	 * value as z.
	 * 
	 * @param z complex number
	 * @return result complex number
	 */
	public Complex apply(Complex z) {
		Complex result = constant;
		for (Complex root : roots) {
			result = result.multiply(z.sub(root));
		}
		return result;
	}

	/**
	 * Converts {@link ComplexRootedPolynomial} to {@link ComplexPolynomial}.
	 * 
	 * @return complex polynomial
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial basePolynomial = new ComplexPolynomial(constant);
		for (Complex root : roots) {
			ComplexPolynomial bracketPolynomial = new ComplexPolynomial(root.negate(), Complex.ONE);
			basePolynomial = basePolynomial.multiply(bracketPolynomial);
		}
		return basePolynomial;

	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("(").append(constant.toString()).append(")");
		for (Complex root : roots) {
			builder.append("*(z-(").append(root).append("))");
		}
		return builder.toString();
	}

	/**
	 * Finds index of closest root for given complex number z that is within the
	 * given treshold; if there is no such root, returns -1
	 * 
	 * @param z        complex number
	 * @param treshold value
	 * @return index of closest root
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		double min = -1;
		int index = -1;
		for (int i = 0, n = roots.length; i < n; i++) {
			double distance = z.sub(roots[i]).module();
			if (distance < treshold && (distance < min || min == -1)) {
				min = distance;
				index = i;
			}
		}
		return index;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(roots);
		result = prime * result + Objects.hash(constant);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ComplexRootedPolynomial))
			return false;
		ComplexRootedPolynomial other = (ComplexRootedPolynomial) obj;
		return Objects.equals(constant, other.constant) && Arrays.equals(roots, other.roots);
	}

}
