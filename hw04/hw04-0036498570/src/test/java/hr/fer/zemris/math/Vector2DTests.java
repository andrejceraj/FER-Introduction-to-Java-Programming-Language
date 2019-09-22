package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Vector2DTests {

	@Test
	void testConstructor() {
		Vector2D vector = new Vector2D(1.2, 3.4);
		assertNotNull(vector);
	}

	@Test
	void testGetters() {
		Vector2D vector = new Vector2D(1.2, 3.4);
		assertEquals(1.2, vector.getX());
		assertEquals(3.4, vector.getY());
	}

	@Test
	void testTranslate() {
		Vector2D vector = new Vector2D(1.2, 3.4);
		vector.translate(new Vector2D(2.0, 4.0));
		assertEquals(3.2, vector.getX());
		assertEquals(7.4, vector.getY());
	}

	@Test
	void testTranslated() {
		Vector2D vector = new Vector2D(1.2, 3.4);
		Vector2D vector2 = vector.translated(new Vector2D(2.0, 4.0));
		assertEquals(3.2, vector2.getX());
		assertEquals(7.4, vector2.getY());
	}

	@Test
	void testScale() {
		Vector2D vector = new Vector2D(1.2, 3.4);
		vector.scale(2.5);
		assertEquals(3.0, vector.getX());
		assertEquals(8.5, vector.getY());
	}

	@Test
	void testScaled() {
		Vector2D vector = new Vector2D(1.2, 3.4);
		Vector2D vector2 = vector.scaled(2.5);
		assertEquals(3.0, vector2.getX());
		assertEquals(8.5, vector2.getY());
	}

	@Test
	void testRotate() {
		Vector2D vector = new Vector2D(1.2, 3.4);
		vector.rotate(Math.PI / 2);
		assertEquals(-3.4, vector.getX(), 1e-8);
		assertEquals(1.2, vector.getY(), 1e-8);
	}

	@Test
	void testRotated() {
		Vector2D vector = new Vector2D(1.2, 3.4);
		Vector2D vector2 = vector.rotated(-Math.PI / 2);
		assertEquals(3.4, vector2.getX(), 1e-8);
		assertEquals(-1.2, vector2.getY(), 1e-8);
	}

	@Test
	void testCopy() {
		Vector2D vector = new Vector2D(1.2, 3.4);
		Vector2D vector2 = vector.copy();
		assertEquals(vector, vector2);
	}
}
