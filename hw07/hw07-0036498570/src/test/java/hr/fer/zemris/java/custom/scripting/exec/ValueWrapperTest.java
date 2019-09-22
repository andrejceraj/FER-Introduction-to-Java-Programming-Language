package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ValueWrapperTest {

	@Test
	void testConstructor() {
		assertEquals(12, new ValueWrapper(12).getValue());
		assertEquals(null, new ValueWrapper(null).getValue());
	}

	@Test
	void testAddInteger() {
		int i = 12;
		double d = 2.5;
		ValueWrapper vw1 = new ValueWrapper(i);

		vw1.add(i);
		assertEquals(24, vw1.getValue());

		vw1.add(d);
		assertEquals(26.5, vw1.getValue());
	}

	@Test
	void testAddDouble() {
		int i = 12;
		double d = 2.5;
		ValueWrapper vw1 = new ValueWrapper(d);

		vw1.add(i);
		assertEquals(14.5, vw1.getValue());

		vw1.add(d);
		assertEquals(17.0, vw1.getValue());
	}

	@Test
	void testSubstractInteger() {
		int i = 12;
		double d = 2.5;
		ValueWrapper vw1 = new ValueWrapper(i);

		vw1.subtract(i);
		assertEquals(0, vw1.getValue());

		vw1.subtract(d);
		assertEquals(-2.5, vw1.getValue());
	}

	@Test
	void testSubstractDouble() {
		int i = 12;
		double d = 2.5;
		ValueWrapper vw1 = new ValueWrapper(d);

		vw1.subtract(i);
		assertEquals(-9.5, vw1.getValue());

		vw1.subtract(d);
		assertEquals(-12.0, vw1.getValue());
	}

	@Test
	void testMultiplyInteger() {
		int i = 12;
		double d = 2.5;
		ValueWrapper vw1 = new ValueWrapper(i);

		vw1.multiply(i);
		assertEquals(144, vw1.getValue());

		vw1.multiply(d);
		assertEquals(360.0, vw1.getValue());
	}

	@Test
	void testMultiplyDouble() {
		int i = 12;
		double d = 2.5;
		ValueWrapper vw1 = new ValueWrapper(d);

		vw1.multiply(i);
		assertEquals(30.0, vw1.getValue());

		vw1.multiply(d);
		assertEquals(75.0, vw1.getValue());
	}

	@Test
	void testDivideInteger() {
		int i = 12;
		double d = 2.5;
		ValueWrapper vw1 = new ValueWrapper(i);

		vw1.divide(i);
		assertEquals(1, vw1.getValue());

		vw1.divide(d);
		assertEquals(0.4, vw1.getValue());
	}

	@Test
	void testDivideDouble() {
		int i = 10;
		double d = 2.5;
		ValueWrapper vw1 = new ValueWrapper(d);

		vw1.divide(i);
		assertEquals(0.25, vw1.getValue());

		vw1.divide(d);
		assertEquals(0.1, vw1.getValue());
	}

	@Test
	void testCompare() {
		int i = 10;
		double d = 12.23;
		String s1 = "15";
		String s2 = "Ankica";
		ValueWrapper vw = new ValueWrapper(12.23);

		assertTrue(vw.numCompare(i) > 0);
		assertTrue(vw.numCompare(d) == 0);
		assertTrue(vw.numCompare(s1) < 0);
		assertThrows(RuntimeException.class, () -> vw.numCompare(s2));
	}

}
