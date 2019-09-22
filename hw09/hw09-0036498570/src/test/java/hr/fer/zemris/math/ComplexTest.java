package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;
import static hr.fer.zemris.math.Complex.*;

import org.junit.jupiter.api.Test;

class ComplexTest {
	private static final double SQRT_OF_2 = Math.sqrt(2);

	@Test
	void testGetReal() {
		assertEquals(3.3, new Complex(3.3, 1.2).getReal());
		assertEquals(0, new Complex(0, 3.3).getReal());
		assertEquals(-1.2, new Complex(-1.2, 0).getReal());
	}

	@Test
	void testGetImaginary() {
		assertEquals(1.2, new Complex(3.3, 1.2).getImaginary());
		assertEquals(0, new Complex(-1.2, 0).getImaginary());
		assertEquals(-3.3, new Complex(-1.2, -3.3).getImaginary());
	}

	@Test
	void testmodule() {
		assertEquals(5, new Complex(3, 4).module());
		assertEquals(5, new Complex(-3, 4).module());
		assertEquals(5, new Complex(3, -4).module());
		assertEquals(5, new Complex(-3, -4).module());
	}

	@Test
	void testGetAngle() {
		assertEquals(Math.PI / 4, new Complex(1, 1).getAngle());
		assertEquals(3 * Math.PI / 4, new Complex(-1, 1).getAngle());
		assertEquals(5 * Math.PI / 4, new Complex(-1, -1).getAngle());
		assertEquals(7 * Math.PI / 4, new Complex(1, -1).getAngle());
		assertEquals(0, new Complex(1, 0).getAngle());
		assertEquals(Math.PI, new Complex(-1, 0).getAngle());
		assertEquals(Math.PI / 2, new Complex(0, 1).getAngle());
		assertEquals(3 * Math.PI / 2, new Complex(0, -1).getAngle());
		assertEquals(0, new Complex(0, 0).getAngle());
	}

	@Test
	void testFromMagnitudeAndAngle() {
		assertEquals(new Complex(SQRT_OF_2, SQRT_OF_2), fromMagnitudeAndAngle(2, Math.PI / 4));
		assertEquals(new Complex(-SQRT_OF_2, SQRT_OF_2), fromMagnitudeAndAngle(2, 3 * Math.PI / 4));
		assertEquals(new Complex(-SQRT_OF_2, -SQRT_OF_2), fromMagnitudeAndAngle(2, 5 * Math.PI / 4));
		assertEquals(new Complex(SQRT_OF_2, -SQRT_OF_2), fromMagnitudeAndAngle(2, 7 * Math.PI / 4));

		assertEquals(new Complex(2, 0), fromMagnitudeAndAngle(2, 0));
		assertEquals(new Complex(0, 2), fromMagnitudeAndAngle(2, Math.PI / 2));
		assertEquals(new Complex(-2, 0), fromMagnitudeAndAngle(2, Math.PI));
		assertEquals(new Complex(0, -2), fromMagnitudeAndAngle(2, 3 * Math.PI / 2));
	}

	@Test
	void testAdd() {
		assertEquals(new Complex(1.7, 5.2), new Complex(1.2, 3.3).add(new Complex(0.5, 1.9)));
		assertEquals(new Complex(0.7, 5.2), new Complex(1.2, 3.3).add(new Complex(-0.5, 1.9)));
		assertEquals(new Complex(-0.7, 5.2), new Complex(-1.2, 3.3).add(new Complex(0.5, 1.9)));
		assertEquals(new Complex(0.7, -1.4), new Complex(1.2, -3.3).add(new Complex(-0.5, 1.9)));
	}

	@Test
	void testSub() {
		assertEquals(new Complex(0.7, 1.4), new Complex(1.2, 3.3).sub(new Complex(0.5, 1.9)));
		assertEquals(new Complex(1.7, 1.4), new Complex(1.2, 3.3).sub(new Complex(-0.5, 1.9)));
		assertEquals(new Complex(-1.7, 1.4), new Complex(-1.2, 3.3).sub(new Complex(0.5, 1.9)));
		assertEquals(new Complex(1.7, -5.2), new Complex(1.2, -3.3).sub(new Complex(-0.5, 1.9)));
	}

	@Test
	void testmultiply() {
		assertEquals(new Complex(0, 8), new Complex(2, 2).multiply(new Complex(2, 2)));
		assertEquals(new Complex(-8, 0), new Complex(-2, 2).multiply(new Complex(2, 2)));
		assertEquals(new Complex(0, -8), new Complex(-2, -2).multiply(new Complex(2, 2)));
		assertEquals(new Complex(8, 0), new Complex(2, -2).multiply(new Complex(2, 2)));
	}

	@Test
	void testdivide() {
		assertEquals(new Complex(2, 2), new Complex(0, 8).divide(new Complex(2, 2)));
		assertEquals(new Complex(2, 2), new Complex(-8, 0).divide(new Complex(-2, 2)));
		assertEquals(new Complex(2, 2), new Complex(0, -8).divide(new Complex(-2, -2)));
		assertEquals(new Complex(2, 2), new Complex(8, 0).divide(new Complex(2, -2)));
		assertEquals(new Complex(2, 2), new Complex(0, 8).divide(new Complex(2, 2)));
		assertEquals(new Complex(-2, 2), new Complex(-8, 0).divide(new Complex(2, 2)));
		assertEquals(new Complex(-2, -2), new Complex(0, -8).divide(new Complex(2, 2)));
		assertEquals(new Complex(2, -2), new Complex(8, 0).divide(new Complex(2, 2)));
	}

	@Test
	void testPower() {
		assertEquals(new Complex(1, 0), new Complex(2, 2).power(0));
		assertEquals(new Complex(0, 8), new Complex(2, 2).power(2));
		assertEquals(new Complex(0, -8), new Complex(2, -2).power(2));
		assertEquals(new Complex(-16, -16), new Complex(2, -2).power(3));
		assertEquals(new Complex(1202.93728, -456.47253), new Complex(-3.2, 2.7).power(5));
	}

	@Test
	void testPowerException() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Complex(2, 2).power(-2);
		});
	}

	@Test
	void testRoot() {
		assertEquals(new Complex(2, 2), new Complex(0, 8).root(2).get(0));
		assertEquals(new Complex(2, -2), new Complex(0, -8).root(2).get(1));
		assertEquals(new Complex(2, -2), new Complex(-16, -16).root(3).get(2));
		assertEquals(new Complex(-3.2, 2.7), new Complex(1202.93728, -456.47253).root(5).get(1));
	}

	@Test
	void testRootException() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Complex(2, 2).root(0);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			new Complex(2, 2).root(-3);
		});
	}

	@Test
	void testToString() {
		assertEquals("i", new Complex(0, 1).toString());
		assertEquals("-i", new Complex(0, -1).toString());
		assertEquals("1.0", new Complex(1, 0).toString());
		assertEquals("-1.0", new Complex(-1, 0).toString());
		assertEquals("2.3+1.7i", new Complex(2.3, 1.7).toString());
		assertEquals("2.3-1.7i", new Complex(2.3, -1.7).toString());
		assertEquals("-2.3-i", new Complex(-2.3, -1).toString());
		assertEquals("-2.3-1.7i", new Complex(-2.3, -1.7).toString());
	}

}
