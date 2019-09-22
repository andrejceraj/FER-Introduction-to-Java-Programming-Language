package hr.fer.zemris.java.gui.prim;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PrimListModelTest {

	@Test
	void testPrimGenerator() {
		PrimListModel model = new PrimListModel();
		int[] primNumbers = new int[] { 1, 2, 3, 5, 7, 11, 13, 17, 19, 23 };
		for (int i = 0; i < primNumbers.length; i++) {
			model.next();
			assertEquals(primNumbers[i], model.getElementAt(i));
		}
	}

}
