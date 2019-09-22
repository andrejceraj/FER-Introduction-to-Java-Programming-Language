package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;

import static hr.fer.zemris.java.hw01.Factorial.*;

import org.junit.jupiter.api.Test;

class FactorialTest {
	@Test 
	void negativeArgument(){
		boolean thrown = false;
		try {
			long result = factorial(-3);
			fail();
		} catch(IllegalArgumentException exception) {
			thrown = true;
		}
		assertTrue(thrown);
	}
	
	@Test
	void edgeCaseZero() {
		long result = factorial(0);
		assertEquals(result, 1);
	}
	
	@Test
	void edgeCaseOne() {
		long result = factorial(1);
		assertEquals(result, 1);
	}
	
	@Test
	void factorialTest() {
		long result1 = factorial(3), result2 = factorial(4), result3 = factorial(7), result4 = factorial(12);
		assertEquals(result1, 6);
		assertEquals(result2, 24);
		assertEquals(result3, 5040);
		assertEquals(result4, 479001600);
	}

}
