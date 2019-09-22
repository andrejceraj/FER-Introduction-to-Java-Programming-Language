package hr.fer.zemris.java.hw05;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.IFieldValueGetter;
import hr.fer.zemris.java.hw05.db.StudentRecord;

class FieldValueGettersTest {
	
	private StudentRecord getExampleRecord() {
		return new StudentRecord("0123456789", "Ivanić", "Ivan", 3);
	}

	@Test
	void testJMBAGGetter() {
		IFieldValueGetter getter = FieldValueGetters.JMBAG;
		assertEquals("0123456789", getter.get(getExampleRecord()));
	}
	
	@Test
	void testLastNameGetter() {
		IFieldValueGetter getter = FieldValueGetters.LAST_NAME;
		assertEquals("Ivanić", getter.get(getExampleRecord()));
	}
	
	@Test
	void testFirstNameGetter() {
		IFieldValueGetter getter = FieldValueGetters.FIRST_NAME;
		assertEquals("Ivan", getter.get(getExampleRecord()));
	}

}
