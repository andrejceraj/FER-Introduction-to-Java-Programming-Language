package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;
import static hr.fer.zemris.java.hw01.Rectangle.*;

import org.junit.jupiter.api.Test;

class RectangleTest {

	@Test
	void testAreaWithIllegalArguments() {
		boolean thrown = false;
		try {
			double result = area(-2.1, -3);
			fail();
		} catch (IllegalArgumentException exception) {
			thrown = true;
		}
		assertTrue(thrown);
	}

	@Test
	void calculateRectangleArea() {
		double result1 = area(1, 1), result2 = area(1.2, 4.9), result3 = area(17.5, 0.1);
		assertTrue(Math.abs(1 - result1) < 1e-8);
		assertTrue(Math.abs(5.88 - result2) < 1e-8);
		assertTrue(Math.abs(1.75 - result3) < 1e-8);
	}

	@Test
	void testPerimeterWithIllegalArguments() {
		boolean thrown = false;
		try {
			double result = area(0, 0);
			fail();
		} catch (IllegalArgumentException exception) {
			thrown = true;
		}
		assertTrue(thrown);
	}

	@Test
	void calculateRectanglePerimeter() {
		double result1 = perimeter(1, 1), result2 = perimeter(1.2, 4.9), 
				result3 = perimeter(17.5, 0.1);
		assertTrue(Math.abs(4 - result1) < 1e-8);
		assertTrue(Math.abs(12.2 - result2) < 1e-8);
		assertTrue(Math.abs(35.2 - result3) < 1e-8);
	}

}
