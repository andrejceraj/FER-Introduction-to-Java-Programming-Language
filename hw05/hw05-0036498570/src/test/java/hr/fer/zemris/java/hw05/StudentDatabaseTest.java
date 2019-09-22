package hr.fer.zemris.java.hw05;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.IFilter;
import hr.fer.zemris.java.hw05.db.StudentDatabase;
import hr.fer.zemris.java.hw05.db.StudentRecord;

class StudentDatabaseTest {

	@Test
	void test() {
		StudentDatabase database = new StudentDatabase(getData());
		StudentRecord record = database.forJMBAG("0000000002");
		List<StudentRecord> recordsTrue = database.filter(new TrueFilter());
		List<StudentRecord> recordsFalse = database.filter(new FalseFilter());
		
		assertEquals("0000000002", record.getJmbag());
		assertEquals("Bakamović", record.getLastName());
		assertEquals("Petra", record.getFirstName());
		assertEquals(5, record.getGrade());

		assertEquals(63, recordsTrue.size());
		assertEquals(0, recordsFalse.size());
	}

	public static String[] getData(){
		try{
			List<String> lines = Files.readAllLines(Paths.get("src/main/resources/database.txt"), StandardCharsets.UTF_8);
			String[] data = new String[lines.size()];
			int i = 0;
			for (String line : lines) {
				data[i++] = line;
			}
			return data;
		}catch (Exception e) {
			return null;
		}
		
	}

	private class TrueFilter implements IFilter {
		@Override
		public boolean accepts(StudentRecord studentRecord) {
			return true;
		}
	}

	private class FalseFilter implements IFilter {
		@Override
		public boolean accepts(StudentRecord studentRecord) {
			return false;
		}
	}

}
