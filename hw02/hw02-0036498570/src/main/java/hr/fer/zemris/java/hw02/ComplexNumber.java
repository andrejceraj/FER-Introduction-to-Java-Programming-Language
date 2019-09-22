package hr.fer.zemris.java.hw02;

import java.util.Objects;

/**
 * Representation of a complex number.
 * 
 * @author Andrej Ceraj
 *
 */
public class ComplexNumber {
	/**
	 * Value of the real part of the @{link ComplexNumber}.
	 */
	private double real;
	/**
	 * Value of the imaginary part of the {@link ComplexNumber}.
	 */
	private double imaginary;

	/**
	 * Creates a @{link ComplexNumber} with given real and imaginary part.
	 * 
	 * @param real      Value of real part of @{link ComplexNumber}
	 * @param imaginary Value of imaginary part of @{link ComplexNumber}
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * @return Real part of @{link ComplexNumber}.
	 */
	public double getReal() {
		return real;
	}

	/**
	 * @param real New real part of @{link ComplexNumber}.
	 */
	private void setReal(double real) {
		this.real = real;
	}

	/**
	 * @return Imaginary part of @{link ComplexNumber}.
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * @param imaginary New imaginary part of @{link ComplexNumber}
	 */
	private void setImaginary(double imaginary) {
		this.imaginary = imaginary;
	}

	/**
	 * Creates a @{link ComplexNumber} with real part value of the given number, and
	 * imaginary part value of 0.
	 * 
	 * @param real Real part of the new complex number
	 * @return New complex number
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * Creates a @{link ComplexNumber} with imaginary part value of the given number, and
	 * real part value of 0.
	 * 
	 * @param imaginary Imaginary part of the new complex number
	 * @return New complex number
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Creates a @{link ComplexNumber} from the given magnitude and angle.
	 * 
	 * @param magnitude Magnitude of a complex number
	 * @param angle     Angle of a complex number
	 * @return New complex number
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * Math.cos(angle % (2 * Math.PI)),
				magnitude * Math.sin(angle % (2 * Math.PI)));
	}

	/**
	 * Creates a @{link ComplexNumber} from the given string.
	 * 
	 * @param string String to parse to complex number
	 * @return New complex number
	 * @throws IllegalArgumentException Exception is thrown if it is not possible to
	 *                                  parse the given string to a complex number.
	 */
	public static ComplexNumber parse(String string) throws IllegalArgumentException {
		double real = 0, imaginary = 0;

		if ((!string.contains("+") || string.lastIndexOf('+') == 0)
				&& (!string.contains("-") || string.lastIndexOf('-') == 0)) {
			if (string.contains("i")) {
				imaginary = parseImaginary(string.substring(0, string.length() - 1));
			} else {
				real = Double.parseDouble(string);
			}
		} else if (string.contains("+") && string.lastIndexOf('+') != 0) {
			if (string.lastIndexOf('i') != string.length() - 1) {
				throw new IllegalArgumentException();
			}
			int indexOfPlus = string.lastIndexOf('+');
			real = Double.parseDouble(string.substring(0, indexOfPlus));
			imaginary = parseImaginary(string.substring(indexOfPlus, string.length() - 1));
		} else {
			if (string.lastIndexOf('i') != string.length() - 1) {
				throw new IllegalArgumentException();
			}
			int indexOfMinus = string.lastIndexOf('-');
			real = Double.parseDouble(string.substring(0, indexOfMinus));
			imaginary = parseImaginary(string.substring(indexOfMinus, string.length() - 1));
		}
		return new ComplexNumber(real, imaginary);
	}

	/**
	 * @return Magnitude of {@link ComplexNumber}
	 */
	public double getMagnitude() {
		return Math.sqrt(getReal() * getReal() + getImaginary() * getImaginary());
	}

	/**
	 * @return Angle of @{link ComplexNumber}
	 */
	public double getAngle() {
		if (getImaginary() == 0 && getReal() == 0) {
			return 0;
		}
		double angle = Math.atan(getImaginary() / getReal());
		if (getReal() >= 0 && getImaginary() >= 0) {
			return angle;
		} else if (getReal() < 0 && getImaginary() >= 0) {
			return Math.PI + angle;
		} else if (getReal() < 0 && getImaginary() < 0) {
			return Math.PI + angle;
		} else {
			return 2 * Math.PI + angle;
		}
	}

	/**
	 * Method adds given complex number to this @{link ComplexNumber}.
	 * 
	 * @param complexNumber Complex number to add to this one
	 * @return Result of the addition
	 */
	public ComplexNumber add(ComplexNumber complexNumber) {
		setReal(getReal() + complexNumber.getReal());
		setImaginary(getImaginary() + complexNumber.getImaginary());
		return this;
	}

	/**
	 * Method subtracts given complex number from this @{link ComplexNumber}.
	 * 
	 * @param complexNumber Complex number to subtract from this one
	 * @return Result of the subtraction
	 */
	public ComplexNumber sub(ComplexNumber complexNumber) {
		setReal(getReal() - complexNumber.getReal());
		setImaginary(getImaginary() - complexNumber.getImaginary());
		return this;
	}

	/**
	 * Method multiplies given complex number with this @{link ComplexNumber}.
	 * 
	 * @param complexNumber Complex number to multiply this one
	 * @return Result of multiplication
	 */
	public ComplexNumber mul(ComplexNumber complexNumber) {
		return fromMagnitudeAndAngle(this.getMagnitude() * complexNumber.getMagnitude(),
				this.getAngle() + complexNumber.getAngle());
	}

	/**
	 * Method divides this @{link ComplexNumber} by the given complex number.
	 * 
	 * @param complexNumber Complex number to divide this one by.
	 * @return Result of division
	 */
	public ComplexNumber div(ComplexNumber complexNumber) {
		return fromMagnitudeAndAngle(this.getMagnitude() / complexNumber.getMagnitude(),
				this.getAngle() - complexNumber.getAngle());
	}

	/**
	 * Method powers @{link ComplexNumber} by the given value.
	 * 
	 * @param n Number to power the complex number
	 * @return Result of the powering
	 * @throws IllegalArgumentException Exception is thrown if the value of number
	 *                                  to power the complex number is lesser than
	 *                                  0.
	 */
	public ComplexNumber power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException();
		}
		return fromMagnitudeAndAngle(Math.pow(this.getMagnitude(), n), this.getAngle() * n);
	}

	/**
	 * Method calculates the root of @{link ComplexNumber}.
	 * 
	 * @param n Number of roots to be be calculated
	 * @return Result roots.
	 * @throws IllegalArgumentException Exception is thrown if the value of number
	 *                                  of roots is lesser than 1.
	 */
	public ComplexNumber[] root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		ComplexNumber[] complexNumbers = new ComplexNumber[n];
		for (int i = 0; i < n; i++) {
			complexNumbers[i] = fromMagnitudeAndAngle(Math.pow(this.getMagnitude(), 1.0 / n),
					this.getAngle() / n + 2 * Math.PI * i / n);
		}
		return complexNumbers;
	}

	/**
	 * Method parses the string representation of an imaginary number and returns
	 * only the value of it.
	 * 
	 * @param string String from which the value is parsed
	 * @return Value of an imaginary number
	 * @throws IllegalArgumentException Exception is thrown if it is not possible to parse the given string.
	 */
	private static double parseImaginary(String string) throws IllegalArgumentException {
		if (string.length() == 0) {
			return 1;
		} else if (string.equals("+")) {
			return 1;
		} else if (string.equals("-")) {
			return -1;
		}
		return Double.parseDouble(string);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String string = "";
		if (Math.abs(getReal()) > 1e-8) {
			string = new String(Double.toString(getReal()));
		}
		if (Math.abs(getImaginary()) > 1e-8) {
			if (Math.abs(getImaginary()) - 1 < 1e-8) {
				if (getImaginary() > 0) {
					string = String.valueOf(string + (string.equals("") ? "i" : "+i"));
				} else {
					string = String.valueOf(string + "-i");
				}
			} else {
				if (getImaginary() > 0) {
					string = String.valueOf(string + (string.equals("") ? Double.toString(getImaginary()) + "i"
							: "+" + Double.toString(getImaginary()) + "i"));
				} else {
					string = String.valueOf(string + Double.toString(getImaginary()) + "i");
				}
			}
		}
		return string;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(imaginary, real);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ComplexNumber))
			return false;
		ComplexNumber other = (ComplexNumber) obj;
		return Math.abs(real - other.real) < 1e-8 && Math.abs(imaginary - other.imaginary) < 1e-8;
	}

}
