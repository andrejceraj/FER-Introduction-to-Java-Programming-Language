package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class LinkedListIndexedCollectionTest {

	public static LinkedListIndexedCollection initializeStringArray() {
		LinkedListIndexedCollection array = new LinkedListIndexedCollection();
		fillArray(array, 10, "string");
		return array;
	}

	public static LinkedListIndexedCollection initializeIntegerArray() {
		LinkedListIndexedCollection array = new LinkedListIndexedCollection();
		fillArray(array, 10, "integer");
		return array;
	}

	private static void fillArray(LinkedListIndexedCollection array, int size, String typeToFill) {
		for (int i = 0; i < size; i++) {
			if (typeToFill.equals("string")) {
				array.add(Integer.toString(i));
			} else if (typeToFill.equals("integer")) {
				array.add(i);
			}
		}
	}

	@Test
	void testDefaultConstructor() {
		LinkedListIndexedCollection array = new LinkedListIndexedCollection();
		assertEquals(null, array.getFirst());
		assertEquals(null, array.getLast());
		assertEquals(0, array.size());
	}

	@Test
	void testConstructorWithCollection() {
		LinkedListIndexedCollection array1 = initializeStringArray();
		LinkedListIndexedCollection array2 = new LinkedListIndexedCollection(array1);
		assertEquals("2", array2.get(2));
		assertEquals("9", array2.get(9));
		assertEquals(10, array2.size());
	}

	@Test
	void testAddMethod() {
		LinkedListIndexedCollection array = new LinkedListIndexedCollection();
		array.add("string1");
		array.add("string2");
		array.add(3);
		assertEquals("string1", array.getFirst().getValue());
		assertEquals(3, array.getLast().getValue());
		assertEquals("string2", array.get(1));
	}

	@Test
	void testAddNullValue() {
		LinkedListIndexedCollection array = new LinkedListIndexedCollection();
		assertThrows(NullPointerException.class, () -> {
			array.add(null);
		});
	}

	@Test
	void testGet() {
		LinkedListIndexedCollection array = initializeStringArray();
		assertEquals("2", array.get(2));
		assertEquals("7", array.get(7));
	}

	@Test
	void testGetIndexOutOfBounds() {
		LinkedListIndexedCollection array = initializeStringArray();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			array.get(100);
		});
	}

	@Test
	void testInsert() {
		LinkedListIndexedCollection filledList = initializeStringArray();
		LinkedListIndexedCollection emptyList = new LinkedListIndexedCollection();
		filledList.insert("string0", 0);
		filledList.insert("string5", 5);
		filledList.insert("stringMax", filledList.size());
		emptyList.insert("string0", 0);
		assertEquals("string0", filledList.get(0));
		assertEquals("string5", filledList.get(5));
		assertEquals("stringMax", filledList.get(filledList.size() - 1));
		assertEquals("string0", emptyList.get(0));
	}

	@Test
	void testInsertingNullValue() {
		LinkedListIndexedCollection array = initializeStringArray();
		assertThrows(NullPointerException.class, () -> {
			array.insert(null, 3);
		});
	}

	@Test
	void tesetInsertWithIllegalArgument() {
		LinkedListIndexedCollection array = initializeStringArray();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			array.insert("string", 100);
		});
	}

	@Test
	void testIndexOf() {
		LinkedListIndexedCollection array = initializeStringArray();
		assertEquals(3, array.indexOf("3"));
		assertEquals(-1, array.indexOf(100));
		assertEquals(-1, array.indexOf(null));
	}

	@Test
	void testContains() {
		LinkedListIndexedCollection array = initializeStringArray();
		assertTrue(array.contains("2"));
		assertFalse(array.contains("štefica"));
		assertFalse(array.contains(null));
	}

	@Test
	void testRemoveByObject() {
		LinkedListIndexedCollection array = initializeStringArray();
		assertEquals("4", array.get(4));
		assertTrue(array.remove("4"));
		assertFalse(array.contains("4"));
		assertEquals("5", array.get(4));

		assertFalse(array.remove(null));
		assertFalse(array.remove("štefica"));
	}

	@Test
	void testRemoveByIndex() {
		LinkedListIndexedCollection array = initializeStringArray();
		assertEquals("4", array.get(4));
		assertTrue(array.remove(4));
		assertFalse(array.contains("4"));
		assertEquals("5", array.get(4));
	}

	@Test
	void testRemoveWithIndexOutOfBounds() {
		LinkedListIndexedCollection array = initializeStringArray();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			array.remove(100);
		});
	}

	@Test
	void testToArray() {
		Object[] objects = new Object[10];
		for (int i = 0; i < 10; i++) {
			objects[i] = Integer.toString(i);
		}
		LinkedListIndexedCollection array = initializeStringArray();
		assertTrue(Arrays.deepEquals(objects, array.toArray()));
	}

	@Test
	void testForEach() {
		class TestProcessor extends Processor {
			int counter = 0;

			public void process(Object value) {
				assertEquals(Integer.toString(counter), value);
				counter++;
			}
		}
		LinkedListIndexedCollection array = initializeStringArray();
		array.forEach(new TestProcessor());
	}

	@Test
	void testForEachWithNull() {
		LinkedListIndexedCollection array = initializeStringArray();
		assertThrows(NullPointerException.class, () -> {
			array.forEach(null);
		});
	}

	@Test
	void testClear() {
		LinkedListIndexedCollection array = initializeStringArray();
		array.clear();
		assertEquals(null, array.getFirst());
		assertEquals(null, array.getLast());
		assertEquals(0, array.size());
	}
	
	@Test
	void crossoverConstructors() {
		ArrayIndexedCollection arrayIndexedCollection = ArrayIndexedCollectionTest.initializeArrayIndexedCollection2();
		LinkedListIndexedCollection arrayLinkedListCollection = initializeStringArray();
		ArrayIndexedCollection newIndexedArray = new ArrayIndexedCollection(arrayLinkedListCollection);
		LinkedListIndexedCollection newLinkedListArray = new LinkedListIndexedCollection(arrayIndexedCollection);
		
		assertTrue(Arrays.deepEquals(arrayIndexedCollection.toArray(), newLinkedListArray.toArray()));
		assertTrue(Arrays.deepEquals(arrayLinkedListCollection.toArray(), newIndexedArray.toArray()));
	}
}
