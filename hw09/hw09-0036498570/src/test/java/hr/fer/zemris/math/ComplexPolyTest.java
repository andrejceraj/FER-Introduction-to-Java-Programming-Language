package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComplexPolyTest {
	private static final Complex[] EXAMPLE = new Complex[] { Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG };

	@Test
	void toPolyTest() {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(Complex.ONE, EXAMPLE);

		ComplexPolynomial cp = new ComplexPolynomial(Complex.ONE_NEG, new Complex(), new Complex(), new Complex(),
				Complex.ONE);
		assertEquals(cp, crp.toComplexPolynom());
	}

	@Test
	void testDerive() {
		ComplexPolynomial cp = new ComplexPolynomial(Complex.ONE_NEG, new Complex(), new Complex(), new Complex(),
				Complex.ONE);
		ComplexPolynomial cpDerived = new ComplexPolynomial(new Complex(), new Complex(), new Complex(),
				new Complex(4, 0));
		assertEquals(cpDerived, cp.derive());

	}

}
