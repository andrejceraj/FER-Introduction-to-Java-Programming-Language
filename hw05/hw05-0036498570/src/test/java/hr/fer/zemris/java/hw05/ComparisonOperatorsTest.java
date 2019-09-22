package hr.fer.zemris.java.hw05;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.IComparisonOperator;

class ComparisonOperatorsTest {

	@Test
	void testLess() {
		IComparisonOperator operator = ComparisonOperators.LESS;
		assertTrue(operator.satisfied("A", "B"));
		assertFalse(operator.satisfied("A", "A"));
		assertFalse(operator.satisfied("B", "A"));
	}

	@Test
	void testLessOrEquals() {
		IComparisonOperator operator = ComparisonOperators.LESS_OR_EQUALS;
		assertTrue(operator.satisfied("A", "B"));
		assertTrue(operator.satisfied("A", "A"));
		assertFalse(operator.satisfied("B", "A"));
	}

	@Test
	void testGreater() {
		IComparisonOperator operator = ComparisonOperators.GREATER;
		assertFalse(operator.satisfied("A", "B"));
		assertFalse(operator.satisfied("A", "A"));
		assertTrue(operator.satisfied("B", "A"));
	}

	@Test
	void testGreaterOrEquals() {
		IComparisonOperator operator = ComparisonOperators.GREATER_OR_EQUALS;
		assertFalse(operator.satisfied("A", "B"));
		assertTrue(operator.satisfied("A", "A"));
		assertTrue(operator.satisfied("B", "A"));
	}

	@Test
	void testEquals() {
		IComparisonOperator operator = ComparisonOperators.EQUALS;
		assertFalse(operator.satisfied("A", "B"));
		assertTrue(operator.satisfied("A", "A"));
		assertFalse(operator.satisfied("B", "A"));
	}

	@Test
	void testNotEquals() {
		IComparisonOperator operator = ComparisonOperators.NOT_EQUALS;
		assertTrue(operator.satisfied("A", "B"));
		assertFalse(operator.satisfied("A", "A"));
		assertTrue(operator.satisfied("B", "A"));
	}

	@Test
	void testLike() {
		IComparisonOperator operator = ComparisonOperators.LIKE;
		assertFalse(operator.satisfied("A", "B"));
		assertTrue(operator.satisfied("A", "A"));
		assertFalse(operator.satisfied("B", "A"));

		assertTrue(operator.satisfied("AAA", "A*A"));
		assertTrue(operator.satisfied("ABBBA", "A*A"));
		assertTrue(operator.satisfied("AAAA", "AA*AA"));

		assertTrue(operator.satisfied("AAA", "A*"));
		assertTrue(operator.satisfied("ABBBA", "AB*"));
		assertTrue(operator.satisfied("AAAA", "AAAA*"));

		assertTrue(operator.satisfied("AAA", "*A"));
		assertTrue(operator.satisfied("ABBBA", "*BA"));
		assertTrue(operator.satisfied("AAAA", "*AAAA"));

		assertFalse(operator.satisfied("AAA", "AA*AA"));
		assertFalse(operator.satisfied("AAA", "A*AAA"));
		assertFalse(operator.satisfied("AAA", "AAA*A"));

		assertFalse(operator.satisfied("Zagreb", "Aba*"));
	}

}
