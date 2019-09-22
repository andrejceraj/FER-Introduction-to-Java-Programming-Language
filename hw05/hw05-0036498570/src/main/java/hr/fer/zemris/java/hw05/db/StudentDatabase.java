package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Representation of database containing {@link StudentRecord}s.
 * 
 * @author Andrej Ceraj
 *
 */
public class StudentDatabase {
	/**
	 * Array of {@code StudentRecord}.
	 */
	private StudentRecord[] list;
	/**
	 * Map containing {@code JMBAG} - {@code StudentRecord} pairs.
	 */
	private Map<String, StudentRecord> database;

	/**
	 * Creates an instance of {@code StudentDatabase}.
	 * 
	 * @param data Student records.
	 */
	public StudentDatabase(String[] data) {
		list = new StudentRecord[data.length];
		database = new HashMap<String, StudentRecord>();
		fillWithData(data);
	}

	/**
	 * Returns {@code StudentRecord} associated with the given {@code JMBAG}.
	 * 
	 * @param jmbag
	 * @return Student record
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return database.get(jmbag);
	}

	/**
	 * Returns list of {@code StudentRecord}s that pass the given filter.
	 * 
	 * @param filter
	 * @return List of student records.
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> tempList = new ArrayList<StudentRecord>();
		for (StudentRecord record : list) {
			if (filter.accepts(record)) {
				tempList.add(record);
			}
		}
		return tempList;
	}

	/**
	 * Extracts {@code StudentRecord}s from the given data and then stores them into
	 * the {@code StudentRecord}s list and {@code JMBAG} - {@code StudentRecord}
	 * HashMap.
	 * 
	 * @param data
	 */
	private void fillWithData(String[] data) {
		for (int i = 0; i < data.length; i++) {
			String[] words = data[i].split("\\s+");
			String jmbag, firstName, lastName;
			int grade;
			if (words.length == 4) {
				jmbag = words[0];
				lastName = words[1];
				firstName = words[2];
				grade = Integer.parseInt(words[3]);
			} else if (words.length == 5) {
				jmbag = words[0];
				lastName = words[1] + " " + words[2];
				firstName = words[3];
				grade = Integer.parseInt(words[4]);
			} else {
				throw new IllegalArgumentException("Line:" + data[i] + " is invalid.");
			}
			StudentRecord record = new StudentRecord(jmbag, lastName, firstName, grade);
			list[i] = record;
			if (database.containsKey(jmbag)) {
				throw new IllegalArgumentException("There is more than one student with jmbag: " + jmbag);
			}
			database.put(jmbag, record);
		}
	}
}
