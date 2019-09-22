package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class ArrayIndexedCollectionTest {

	public static ArrayIndexedCollection initializeArrayIndexedCollection1() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(12);
		fillArray(array, array.getCapacity() - 2);
		return array;
	}

	public static ArrayIndexedCollection initializeArrayIndexedCollection2() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(20);
		fillArray(array, array.getCapacity());
		return array;
	}

	private static void fillArray(ArrayIndexedCollection array, int index) {
		for (int i = 0; i < index; i++) {
			array.add(i);
		}
	}

	@Test
	void testIllegalArgumentInConstructor() {
		assertThrows(IllegalArgumentException.class, () -> {
			new ArrayIndexedCollection(-2);
		});
	}

	@Test
	void testDefaultConstructor() {
		ArrayIndexedCollection arrayDefaultLength = new ArrayIndexedCollection();
		assertEquals(16, arrayDefaultLength.getCapacity());
	}

	@Test
	void testConstructorWithCapacity() {
		ArrayIndexedCollection arrayCapacity4 = new ArrayIndexedCollection(4);
		ArrayIndexedCollection arrayCapacity20 = new ArrayIndexedCollection(20);
		assertEquals(4, arrayCapacity4.getCapacity());
		assertEquals(20, arrayCapacity20.getCapacity());
	}

	@Test
	void testConstructorWithCollection() {
		ArrayIndexedCollection arrayInitialized = initializeArrayIndexedCollection2();
		ArrayIndexedCollection arrayFromInitialized = new ArrayIndexedCollection(arrayInitialized);
		assertEquals(arrayInitialized.getCapacity(), arrayFromInitialized.getCapacity());
		assertEquals(arrayInitialized, arrayFromInitialized);

		ArrayIndexedCollection arrayLength20 = new ArrayIndexedCollection(20);
		ArrayIndexedCollection arrayinitializedWithOtherCollection = new ArrayIndexedCollection(arrayLength20);
		assertEquals(16, arrayinitializedWithOtherCollection.getCapacity());
	}

	@Test
	void testConstructorWithCollectionAndCapacity() {
		ArrayIndexedCollection arrayLength20 = new ArrayIndexedCollection(20);
		ArrayIndexedCollection arrayinitializedWithOtherCollectionAndCapacity = new ArrayIndexedCollection(
				arrayLength20, 12);
		assertEquals(12, arrayinitializedWithOtherCollectionAndCapacity.getCapacity());
	}

	@Test
	void testSize() {
		ArrayIndexedCollection array1 = initializeArrayIndexedCollection1(),
				array2 = initializeArrayIndexedCollection2();
		assertEquals(10, array1.size());
		assertEquals(20, array2.size());
	}

	@Test
	void testGetMethod() {
		ArrayIndexedCollection array = initializeArrayIndexedCollection1();
		assertEquals(array.getElements()[3], array.get(3));
		assertEquals(array.getElements()[10], array.get(10));
	}

	@Test
	void testGetMethodWhenOutOfBounds() {
		ArrayIndexedCollection array = initializeArrayIndexedCollection1();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			array.get(23);
		});
	}

	@Test
	void testAddMethod() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add(23);
		array.add("string");
		assertEquals(23, array.get(0));
		assertEquals("string", array.get(1));
	}

	@Test
	void testAddNullValue() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		assertThrows(NullPointerException.class, () -> {
			array.add(null);
		});
	}

	@Test
	void testInsert() {
		ArrayIndexedCollection array1 = initializeArrayIndexedCollection1();
		int size = array1.size();
		Object objectOnIndex9 = array1.get(9);
		array1.insert("string", 3);
		assertEquals(size + 1, array1.size());
		assertEquals("string", array1.get(3));
		assertEquals(objectOnIndex9, array1.get(10));
	}

	@Test
	void expandArrayWhenInserting() {
		ArrayIndexedCollection array1 = initializeArrayIndexedCollection2();
		int capacity = array1.getCapacity();
		array1.insert("string", 3);
		assertEquals(capacity + 1, array1.getCapacity());
	}

	@Test
	void testIndexOf() {
		ArrayIndexedCollection array1 = initializeArrayIndexedCollection1();
		assertEquals(9, array1.indexOf(9));
		assertEquals(-1, array1.indexOf(1000));
	}

	@Test
	void testContains() {
		ArrayIndexedCollection array1 = initializeArrayIndexedCollection1();
		assertTrue(array1.contains(2));
		assertFalse(array1.contains("string"));
	}

	@Test
	void testRemove() {
		ArrayIndexedCollection array1 = initializeArrayIndexedCollection1();
		assertTrue(array1.remove((Object) 3));
		assertFalse(array1.contains(3));
		assertEquals(4, array1.get(3));
	}

	@Test
	void testRemoveOnIndex() {
		ArrayIndexedCollection array1 = initializeArrayIndexedCollection1();
		assertTrue(array1.remove((int) 3));
		assertFalse(array1.contains(3));
		assertEquals(4, array1.get(3));

	}

	@Test
	void testRemoveWithIndexOutOfBounds() {
		ArrayIndexedCollection array1 = initializeArrayIndexedCollection1();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			array1.remove((int) 30);
		});
	}

	@Test
	void testToArray() {
		Object[] objects = new Object[10];
		for (int i = 0; i < 10; i++) {
			objects[i] = i;
		}
		ArrayIndexedCollection array1 = initializeArrayIndexedCollection1();
		assertTrue(Arrays.deepEquals(objects, array1.toArray()));
	}

	@Test
	void testsforEach() {
		class TestProcessor extends Processor {
			int counter = 0;

			public void process(Object value) {
				assertEquals(counter, value);
				counter++;
			}
		}
		ArrayIndexedCollection array1 = initializeArrayIndexedCollection1();
		array1.forEach(new TestProcessor());
	}

	@Test
	void testForEachWithNull() {
		ArrayIndexedCollection array = initializeArrayIndexedCollection2();
		assertThrows(NullPointerException.class, () -> {
			array.forEach(null);
		});
	}

	@Test
	void testClear() {
		ArrayIndexedCollection array1 = initializeArrayIndexedCollection1();
		array1.clear();
		for (int i = 0; i < array1.getCapacity(); i++) {
			assertEquals(null, array1.get(i));
		}
	}
}
