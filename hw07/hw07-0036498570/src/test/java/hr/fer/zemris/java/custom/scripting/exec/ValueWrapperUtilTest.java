package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

class ValueWrapperUtilTest {

	@Test
	void testValidValueClass() {
		assertTrue(ValueWrapperUtil.validValueClass(new String()));
		assertTrue(ValueWrapperUtil.validValueClass(new String("test")));

		Integer i1 = null;
		Integer i2 = 12;
		Double d1 = null;
		Double d2 = 12.334;
		
		assertTrue(ValueWrapperUtil.validValueClass(Integer.valueOf(42)));
		assertTrue(ValueWrapperUtil.validValueClass(i1));
		assertTrue(ValueWrapperUtil.validValueClass(i2));
		assertTrue(ValueWrapperUtil.validValueClass(d1));
		assertTrue(ValueWrapperUtil.validValueClass(d2));
	}
	
	@Test
	void testInvalidValueClass() {
		assertFalse(ValueWrapperUtil.validValueClass(new ValueWrapper(123)));
		assertFalse(ValueWrapperUtil.validValueClass(new ArrayList<Integer>()));
		assertFalse(ValueWrapperUtil.validValueClass(new HashMap<String, Integer>()));
		assertFalse(ValueWrapperUtil.validValueClass(new ObjectMultistack()));
	}
	
	@Test
	void testProcessedObjectClass() {
		assertEquals(Integer.class, ValueWrapperUtil.processObject(null).getClass());
		assertEquals(Integer.class, ValueWrapperUtil.processObject(Integer.valueOf(42)).getClass());
		assertEquals(Integer.class, ValueWrapperUtil.processObject(new String("17")).getClass());
		assertEquals(Integer.class, ValueWrapperUtil.processObject((int) 15).getClass());
		
		Double d = 42.42;
		assertEquals(Double.class, ValueWrapperUtil.processObject(d).getClass());
		assertEquals(Double.class, ValueWrapperUtil.processObject((double) 12.23).getClass());
		assertEquals(Double.class, ValueWrapperUtil.processObject(new String("14.44")).getClass());
		assertEquals(Double.class, ValueWrapperUtil.processObject(new String("1.34E-2")).getClass());
		assertEquals(Double.class, ValueWrapperUtil.processObject(new String("1.34e2")).getClass());
	}
	
	@Test
	void testProcessedObjectValue() {
		assertEquals(0, ValueWrapperUtil.processObject(null));
		assertEquals(42, ValueWrapperUtil.processObject(Integer.valueOf(42)));
		assertEquals(17, ValueWrapperUtil.processObject(new String("17")));
		assertEquals(15, ValueWrapperUtil.processObject((int) 15));
		
		Double d = 42.42;
		assertEquals(42.42, ValueWrapperUtil.processObject(d));
		assertEquals(12.23, ValueWrapperUtil.processObject((double) 12.23));
		assertEquals(14.44, ValueWrapperUtil.processObject(new String("14.44")));
		assertEquals(0.0134, ValueWrapperUtil.processObject(new String("1.34E-2")));
		assertEquals(134.5, ValueWrapperUtil.processObject(new String("1.345e2")));
	}
	
	@Test
	void testAlteringOriginals() {
		String s1 = "12";
		Object o1 = s1;
		Object o2 = ValueWrapperUtil.processObject(o1);

		assertEquals("12", s1);
		assertEquals(String.class, s1.getClass());
		assertEquals("12", o1);

		assertEquals(12, o2);
		assertEquals(Integer.class, o2.getClass());
	}

}
