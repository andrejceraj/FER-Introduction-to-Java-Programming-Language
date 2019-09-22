package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SimpleHashtableTest {

	@Test
	void testConstruct() {
		assertThrows(IllegalArgumentException.class, () -> new SimpleHashtable<Integer, Integer>(Integer.MAX_VALUE));
		assertThrows(IllegalArgumentException.class, () -> new SimpleHashtable<Integer, Integer>(0));
	}

	@Test
	void testPutAndSize() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>();
		assertEquals(0, table.size());

		table.put("ivan", 2);
		table.put("marko", 3);
		table.put("domagoj", 4);
		table.put("jasmina", 5);
		table.put("kresimir", 6);
		assertEquals(5, table.size());

		table.put("ivan", 4);
		assertEquals(5, table.size());
	}

	@Test
	void testGet() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>();

		table.put("ivan", 2);
		table.put("marko", 3);
		table.put("domagoj", 4);
		table.put("jasmina", 5);
		table.put("kresimir", 6);

		assertEquals(2, table.get("ivan"));
		assertEquals(3, table.get("marko"));
		assertEquals(4, table.get("domagoj"));
		assertEquals(5, table.get("jasmina"));
		assertEquals(6, table.get("kresimir"));

		table.put("ivan", 7);
		assertEquals(7, table.get("ivan"));
	}

	@Test
	void testContains() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>(50);

		table.put("ivan", 2);
		assertTrue(table.containsKey("ivan"));
		assertTrue(table.containsValue(2));
		table.put("marko", 3);
		assertTrue(table.containsKey("marko"));
		assertTrue(table.containsValue(3));
		table.put("domagoj", 4);
		assertTrue(table.containsKey("domagoj"));
		assertTrue(table.containsValue(4));
		table.put("jasmina", 5);
		assertTrue(table.containsKey("jasmina"));
		assertTrue(table.containsValue(5));
		table.put("kresimir", 6);
		assertTrue(table.containsKey("kresimir"));
		assertTrue(table.containsValue(6));

		assertFalse(table.containsKey("mirko"));
		assertFalse(table.containsValue(7));

		table.put("ivan", 7);
		assertTrue(table.containsValue(7));
	}

	@Test
	void testReallocation() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>(2);

		table.put("zeljko", 1);
		assertTrue(table.containsValue(1));
		assertTrue(table.containsKey("zeljko"));
		table.put("ivan", 2);
		assertTrue(table.containsValue(2));
		assertTrue(table.containsKey("ivan"));
		table.put("marko", 3);
		assertTrue(table.containsValue(3));
		assertTrue(table.containsKey("marko"));
		table.put("domagoj", 4);
		assertTrue(table.containsValue(4));
		assertTrue(table.containsKey("domagoj"));
		table.put("jasmina", 5);
		assertTrue(table.containsValue(5));
		assertTrue(table.containsKey("jasmina"));
		table.put("kresimir", 6);
		assertTrue(table.containsValue(6));
		assertTrue(table.containsKey("kresimir"));
		table.put("zeljko2", 11);
		assertTrue(table.containsValue(11));
		assertTrue(table.containsKey("zeljko2"));
		table.put("ivan2", 22);
		assertTrue(table.containsValue(22));
		assertTrue(table.containsKey("ivan2"));
		table.put("marko2", 33);
		assertTrue(table.containsValue(33));
		assertTrue(table.containsKey("marko2"));
		table.put("domagoj2", 44);
		assertTrue(table.containsValue(44));
		assertTrue(table.containsKey("domagoj2"));
		table.put("jasmina2", 55);
		assertTrue(table.containsValue(55));
		assertTrue(table.containsKey("jasmina2"));
		table.put("kresimir2", 66);
		assertTrue(table.containsValue(66));
		assertTrue(table.containsKey("kresimir2"));
	}

	@Test
	void testRemove() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>();

		table.put("ivan", 2);
		table.put("marko", 3);
		table.put("domagoj", 4);
		table.put("jasmina", 5);
		table.put("kresimir", 6);

		assertTrue(table.containsValue(2));
		assertTrue(table.containsKey("ivan"));
		assertEquals(2, table.get("ivan"));
		assertEquals(3, table.get("marko"));
		assertEquals(4, table.get("domagoj"));
		assertEquals(5, table.get("jasmina"));
		assertEquals(6, table.get("kresimir"));

		table.remove("ivan");
		table.remove("marko");
		table.remove("domagoj");
		table.remove("jasmina");
		table.remove("kresimir");

		assertFalse(table.containsValue(2));
		assertFalse(table.containsKey("ivan"));
		assertEquals(null, table.get("ivan"));
		assertEquals(null, table.get("marko"));
		assertEquals(null, table.get("domagoj"));
		assertEquals(null, table.get("jasmina"));
		assertEquals(null, table.get("kresimir"));
	}

	@Test
	void testIsEmpty() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>();
		assertTrue(table.isEmpty());
		table.put("ivan", 2);
		assertFalse(table.isEmpty());
	}
	
	@Test
	void testClear() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<String, Integer>();

		table.put("ivan", 2);
		table.put("marko", 3);
		table.put("domagoj", 4);
		table.put("jasmina", 5);
		table.put("kresimir", 6);
		
		assertFalse(table.isEmpty());
		table.clear();
		assertTrue(table.isEmpty());
		
	}
}
