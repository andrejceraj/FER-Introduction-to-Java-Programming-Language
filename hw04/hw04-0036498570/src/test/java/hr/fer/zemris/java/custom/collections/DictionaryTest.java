package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DictionaryTest {
	
	@Test
	void testConstructor() {
		Dictionary<Integer, String> dictionary = new Dictionary<Integer, String>();
		assertNotNull(dictionary);
	}
	
	@Test
	void testIsEmpty() {
		Dictionary<Integer, String> dictionary = new Dictionary<Integer, String>();
		assertTrue(dictionary.isEmpty());
		
		dictionary.put(Integer.valueOf(42), "meaning of life");
		assertFalse(dictionary.isEmpty());
	}
	
	@Test
	void testSize() {
		Dictionary<String, Double> dictionary = new Dictionary<String, Double>();
		assertEquals(0, dictionary.size());
		dictionary.put("meaning of life", Double.valueOf(42.0));
		assertEquals(1, dictionary.size());
		dictionary.put("string 1", Double.valueOf(43.44));
		assertEquals(2, dictionary.size());
		dictionary.put("string 2", Double.valueOf(45.234));
		assertEquals(3, dictionary.size());
		dictionary.put("string 3", Double.valueOf(2.01));
		assertEquals(4, dictionary.size());
	}
	
	@Test
	void testClear() {
		Dictionary<String, Double> dictionary = new Dictionary<String, Double>();
		dictionary.put("meaning of life", Double.valueOf(42.0));
		dictionary.put("string 1", Double.valueOf(43.44));
		dictionary.put("string 2", Double.valueOf(45.234));
		dictionary.put("string 3", Double.valueOf(2.01));
		assertEquals(4, dictionary.size());
		assertFalse(dictionary.isEmpty());
		dictionary.clear();
		assertTrue(dictionary.isEmpty());
	}
	
	@Test
	void testPutIntString() {
		Dictionary<Integer, String> dictionary = new Dictionary<Integer, String>();
		dictionary.put(42, "meaning of life");
		assertEquals(1, dictionary.size());
		dictionary.put(4242, "meaning of life");
		assertEquals(2, dictionary.size());
		dictionary.put(42, "meaning of life");
		dictionary.put(42, "string");
		dictionary.put(42, "string2");
		assertEquals(2, dictionary.size());
	}
	
	@Test
	void testGet() {
		Dictionary<Integer, String> dictionary = new Dictionary<Integer, String>();
		dictionary.put(42, "meaning of life");
		assertEquals("meaning of life", dictionary.get(42));
		dictionary.put(4242, "meaning of life");
		assertEquals("meaning of life", dictionary.get(4242));
		dictionary.put(42, "meaning of life");
		assertEquals("meaning of life", dictionary.get(42));
		dictionary.put(42, "string");
		assertEquals("string", dictionary.get(42));
		dictionary.put(42, "string2");
		assertEquals("string2", dictionary.get(42));
	}
	
	@Test
	void testPutAndGet() {
		Dictionary<String, Integer> dictionary = new Dictionary<String, Integer>();
		dictionary.put("meaning of life", 42);
		assertEquals(42, dictionary.get("meaning of life"));
		dictionary.put("meaning of life", 4242);
		assertEquals(4242, dictionary.get("meaning of life"));

		dictionary.put("string1", 1);
		assertEquals(1, dictionary.get("string1"));
		dictionary.put("string2", 2);
		assertEquals(2, dictionary.get("string2"));
		dictionary.put("string1", 1111);
		assertEquals(1111, dictionary.get("string1"));
		dictionary.put("string3", 3);
		assertEquals(3, dictionary.get("string3"));
	}

}
