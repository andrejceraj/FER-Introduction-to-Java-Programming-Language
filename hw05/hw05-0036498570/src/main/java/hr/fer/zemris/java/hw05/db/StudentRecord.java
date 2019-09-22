package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * Representation of a student's record. It contains student's JMBAG, last name,
 * first name and final grade.
 * 
 * @author Andrej Ceraj
 *
 */
public class StudentRecord {
	/**
	 * Student's jmbag
	 */
	private String jmbag;
	/**
	 * Student's last name
	 */
	private String lastName;
	/**
	 * Student's first name
	 */
	private String firstName;
	/**
	 * Student's grade
	 */
	private int grade;

	/**
	 * Creates an instance of {@code StudentRecord} with given values.
	 * 
	 * @param jmbag
	 * @param lastName
	 * @param firstName
	 * @param grade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int grade) {
		if (grade < 1 || grade > 5) {
			throw new IllegalArgumentException(
					"Invalid grade: \"" + grade + "\" for student: " + jmbag + " " + lastName + " " + firstName);
		}
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.grade = grade;
	}

	@Override
	public int hashCode() {
		return Objects.hash(jmbag);
	}

	/**
	 * @return student's jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * @return student's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return student's first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return student's final grade
	 */
	public int getGrade() {
		return grade;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof StudentRecord))
			return false;
		StudentRecord other = (StudentRecord) obj;
		return Objects.equals(jmbag, other.jmbag);
	}

	@Override
	public String toString() {
		return jmbag + "\t" + lastName + "\t" + firstName + "\t" + grade;
	}

}
