package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

class ObjectMultistackTest {

	@Test
	void testConstructor() {
		assertNotNull(new ObjectMultistack());
	}
	
	@Test
	void testPushAndPeekAndPop() {
		ValueWrapper vw = new ValueWrapper(12.23);
		ObjectMultistack stack = new ObjectMultistack();
		
		stack.push("test", vw);
		assertEquals(vw, stack.peek("test"));
		assertEquals(vw, stack.pop("test"));
	}
	
	@Test
	void testIsEmpty() {
		ObjectMultistack stack = new ObjectMultistack();
		
		assertTrue(stack.isEmpty("test"));
		stack.push("test", new ValueWrapper("test"));
		assertFalse(stack.isEmpty("test"));
	}
	
	@Test
	void testMultiplePushAndPop() {
		ValueWrapper vw1 = new ValueWrapper(12.23);
		ValueWrapper vw2 = new ValueWrapper("string");
		ValueWrapper vw3 = new ValueWrapper(42);
		ObjectMultistack stack = new ObjectMultistack();
		
		stack.push("test", vw1);
		stack.push("test", vw2);
		stack.push("test", vw3);

		assertEquals(vw3, stack.pop("test"));
		assertEquals(vw2, stack.pop("test"));
		assertEquals(vw1, stack.pop("test"));
	}
	
	@Test
	void testMultiplePeek() {
		ValueWrapper vw1 = new ValueWrapper(12.23);
		ValueWrapper vw2 = new ValueWrapper("string");
		ValueWrapper vw3 = new ValueWrapper(42);
		ObjectMultistack stack = new ObjectMultistack();
		
		stack.push("test", vw1);
		stack.push("test", vw2);
		stack.push("test", vw3);

		assertEquals(vw3, stack.peek("test"));
		assertEquals(vw3, stack.peek("test"));
		assertEquals(vw3, stack.peek("test"));
	}
	
	@Test
	void testPopWhenEmpty() {
		ObjectMultistack stack = new ObjectMultistack();
		
		assertTrue(stack.isEmpty("test"));
		assertThrows(NoSuchElementException.class, () -> stack.pop("test"));
	}
	
	@Test
	void testPeekWhenEmpty() {
		ObjectMultistack stack = new ObjectMultistack();
		
		assertTrue(stack.isEmpty("test"));
		assertThrows(NoSuchElementException.class, () -> stack.peek("test"));
	}

}
