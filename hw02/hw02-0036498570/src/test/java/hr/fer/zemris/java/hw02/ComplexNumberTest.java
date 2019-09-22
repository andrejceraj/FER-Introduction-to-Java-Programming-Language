package hr.fer.zemris.java.hw02;

import static org.junit.jupiter.api.Assertions.*;
import static hr.fer.zemris.java.hw02.ComplexNumber.*;

import org.junit.jupiter.api.Test;

class ComplexNumberTest {
	private static final double SQRT_OF_2 = Math.sqrt(2);

	@Test
	void testGetReal() {
		assertEquals(3.3, new ComplexNumber(3.3, 1.2).getReal());
		assertEquals(0, new ComplexNumber(0, 3.3).getReal());
		assertEquals(-1.2, new ComplexNumber(-1.2, 0).getReal());
	}

	@Test
	void testGetImaginary() {
		assertEquals(1.2, new ComplexNumber(3.3, 1.2).getImaginary());
		assertEquals(0, new ComplexNumber(-1.2, 0).getImaginary());
		assertEquals(-3.3, new ComplexNumber(-1.2, -3.3).getImaginary());
	}

	@Test
	void testGetMagnitude() {
		assertEquals(5, new ComplexNumber(3, 4).getMagnitude());
		assertEquals(5, new ComplexNumber(-3, 4).getMagnitude());
		assertEquals(5, new ComplexNumber(3, -4).getMagnitude());
		assertEquals(5, new ComplexNumber(-3, -4).getMagnitude());
	}

	@Test
	void testGetAngle() {
		assertEquals(Math.PI / 4, new ComplexNumber(1, 1).getAngle());
		assertEquals(3 * Math.PI / 4, new ComplexNumber(-1, 1).getAngle());
		assertEquals(5 * Math.PI / 4, new ComplexNumber(-1, -1).getAngle());
		assertEquals(7 * Math.PI / 4, new ComplexNumber(1, -1).getAngle());
		assertEquals(0, new ComplexNumber(1, 0).getAngle());
		assertEquals(Math.PI, new ComplexNumber(-1, 0).getAngle());
		assertEquals(Math.PI / 2, new ComplexNumber(0, 1).getAngle());
		assertEquals(3 * Math.PI / 2, new ComplexNumber(0, -1).getAngle());
		assertEquals(0, new ComplexNumber(0, 0).getAngle());
	}

	@Test
	void testFromReal() {
		assertEquals(new ComplexNumber(3.3, 0), fromReal(3.3));
		assertEquals(new ComplexNumber(0, 0), fromReal(0));
		assertEquals(new ComplexNumber(-3.3, 0), fromReal(-3.3));
	}

	@Test
	void testFromImaginary() {
		assertEquals(new ComplexNumber(0, 1.2), fromImaginary(1.2));
		assertEquals(new ComplexNumber(0, 0), fromImaginary(0));
		assertEquals(new ComplexNumber(0, -1.2), fromImaginary(-1.2));
	}

	@Test
	void testFromMagnitudeAndAngle() {
		assertEquals(new ComplexNumber(SQRT_OF_2, SQRT_OF_2), fromMagnitudeAndAngle(2, Math.PI / 4));
		assertEquals(new ComplexNumber(-SQRT_OF_2, SQRT_OF_2), fromMagnitudeAndAngle(2, 3 * Math.PI / 4));
		assertEquals(new ComplexNumber(-SQRT_OF_2, -SQRT_OF_2), fromMagnitudeAndAngle(2, 5 * Math.PI / 4));
		assertEquals(new ComplexNumber(SQRT_OF_2, -SQRT_OF_2), fromMagnitudeAndAngle(2, 7 * Math.PI / 4));

		assertEquals(new ComplexNumber(2, 0), fromMagnitudeAndAngle(2, 0));
		assertEquals(new ComplexNumber(0, 2), fromMagnitudeAndAngle(2, Math.PI / 2));
		assertEquals(new ComplexNumber(-2, 0), fromMagnitudeAndAngle(2, Math.PI));
		assertEquals(new ComplexNumber(0, -2), fromMagnitudeAndAngle(2, 3 * Math.PI / 2));
	}

	@Test
	void testParse() {
		assertEquals(new ComplexNumber(0, 1), parse("i"));
		assertEquals(new ComplexNumber(0, -1), parse("-i"));
		assertEquals(new ComplexNumber(1, 0), parse("1"));
		assertEquals(new ComplexNumber(-1, 0), parse("-1"));
		assertEquals(new ComplexNumber(-2.3, 1.7), parse("-2.3+1.7i"));
		assertEquals(new ComplexNumber(-2.3, -1.7), parse("-2.3-1.7i"));
		assertEquals(new ComplexNumber(2.3, 1.7), parse("2.3+1.7i"));
		assertEquals(new ComplexNumber(2.3, -1.7), parse("+2.3-1.7i"));
	}
	
	@Test
	void testParseException() {
		assertThrows(IllegalArgumentException.class, () -> {parse("string");});
		assertThrows(IllegalArgumentException.class, () -> {parse("2+2");});
		assertThrows(IllegalArgumentException.class, () -> {parse("2+j");});
		assertThrows(IllegalArgumentException.class, () -> {parse("i+2");});
		assertThrows(IllegalArgumentException.class, () -> {parse("-i-2");});
		assertThrows(IllegalArgumentException.class, () -> {parse("-2-string");});
	}

	@Test
	void testAdd() {
		assertEquals(new ComplexNumber(1.7, 5.2), new ComplexNumber(1.2, 3.3).add(new ComplexNumber(0.5, 1.9)));
		assertEquals(new ComplexNumber(0.7, 5.2), new ComplexNumber(1.2, 3.3).add(new ComplexNumber(-0.5, 1.9)));
		assertEquals(new ComplexNumber(-0.7, 5.2), new ComplexNumber(-1.2, 3.3).add(new ComplexNumber(0.5, 1.9)));
		assertEquals(new ComplexNumber(0.7, -1.4), new ComplexNumber(1.2, -3.3).add(new ComplexNumber(-0.5, 1.9)));
	}

	@Test
	void testSub() {
		assertEquals(new ComplexNumber(0.7, 1.4), new ComplexNumber(1.2, 3.3).sub(new ComplexNumber(0.5, 1.9)));
		assertEquals(new ComplexNumber(1.7, 1.4), new ComplexNumber(1.2, 3.3).sub(new ComplexNumber(-0.5, 1.9)));
		assertEquals(new ComplexNumber(-1.7, 1.4), new ComplexNumber(-1.2, 3.3).sub(new ComplexNumber(0.5, 1.9)));
		assertEquals(new ComplexNumber(1.7, -5.2), new ComplexNumber(1.2, -3.3).sub(new ComplexNumber(-0.5, 1.9)));
	}

	@Test
	void testMul() {
		assertEquals(new ComplexNumber(0, 8), new ComplexNumber(2, 2).mul(new ComplexNumber(2, 2)));
		assertEquals(new ComplexNumber(-8, 0), new ComplexNumber(-2, 2).mul(new ComplexNumber(2, 2)));
		assertEquals(new ComplexNumber(0, -8), new ComplexNumber(-2, -2).mul(new ComplexNumber(2, 2)));
		assertEquals(new ComplexNumber(8, 0), new ComplexNumber(2, -2).mul(new ComplexNumber(2, 2)));
	}

	@Test
	void testDiv() {
		assertEquals(new ComplexNumber(2, 2), new ComplexNumber(0, 8).div(new ComplexNumber(2, 2)));
		assertEquals(new ComplexNumber(2, 2), new ComplexNumber(-8, 0).div(new ComplexNumber(-2, 2)));
		assertEquals(new ComplexNumber(2, 2), new ComplexNumber(0, -8).div(new ComplexNumber(-2, -2)));
		assertEquals(new ComplexNumber(2, 2), new ComplexNumber(8, 0).div(new ComplexNumber(2, -2)));
		assertEquals(new ComplexNumber(2, 2), new ComplexNumber(0, 8).div(new ComplexNumber(2, 2)));
		assertEquals(new ComplexNumber(-2, 2), new ComplexNumber(-8, 0).div(new ComplexNumber(2, 2)));
		assertEquals(new ComplexNumber(-2, -2), new ComplexNumber(0, -8).div(new ComplexNumber(2, 2)));
		assertEquals(new ComplexNumber(2, -2), new ComplexNumber(8, 0).div(new ComplexNumber(2, 2)));
	}

	@Test
	void testPower() {
		assertEquals(new ComplexNumber(1, 0), new ComplexNumber(2, 2).power(0));
		assertEquals(new ComplexNumber(0, 8), new ComplexNumber(2, 2).power(2));
		assertEquals(new ComplexNumber(0, -8), new ComplexNumber(2, -2).power(2));
		assertEquals(new ComplexNumber(-16, -16), new ComplexNumber(2, -2).power(3));
		assertEquals(new ComplexNumber(1202.93728, -456.47253), new ComplexNumber(-3.2, 2.7).power(5));
	}
	
	@Test
	void testPowerException() {
		assertThrows(IllegalArgumentException.class, () -> {new ComplexNumber(2, 2).power(-2);});
	}
	
	@Test
	void testRoot() {
		assertEquals(new ComplexNumber(2, 2), new ComplexNumber(0, 8).root(2)[0]);
		assertEquals(new ComplexNumber(2, -2), new ComplexNumber(0, -8).root(2)[1]);
		assertEquals(new ComplexNumber(2, -2), new ComplexNumber(-16, -16).root(3)[2]);
		assertEquals(new ComplexNumber(-3.2, 2.7), new ComplexNumber(1202.93728, -456.47253).root(5)[1]);
	}
	
	@Test
	void testRootException() {
		assertThrows(IllegalArgumentException.class, () -> {new ComplexNumber(2, 2).root(0);});
		assertThrows(IllegalArgumentException.class, () -> {new ComplexNumber(2, 2).root(-3);});
	}

	@Test
	void testToString() {
		assertEquals("i", new ComplexNumber(0, 1).toString());
		assertEquals("-i", new ComplexNumber(0, -1).toString());
		assertEquals("1.0", new ComplexNumber(1, 0).toString());
		assertEquals("-1.0", new ComplexNumber(-1, 0).toString());
		assertEquals("2.3+1.7i", new ComplexNumber(2.3, 1.7).toString());
		assertEquals("2.3-1.7i", new ComplexNumber(2.3, -1.7).toString());
		assertEquals("-2.3-i", new ComplexNumber(-2.3, -1).toString());
		assertEquals("-2.3-1.7i", new ComplexNumber(-2.3, -1.7).toString());
	}

}
