package hr.fer.zemris.java.hw07.demo4;

import java.util.Objects;

/**
 * Representation of student course record. It contains sdudent's jmbag,
 * surname, name, mid term exam score, final exam score, laboratory exercises
 * score and final grade.
 * 
 * @author Andrej Ceraj
 *
 */
public class StudentRecord {

	/**
	 * Unique student's number
	 */
	private String jmbag;
	/**
	 * Student's last name
	 */
	private String surname;
	/**
	 * Student's first name
	 */
	private String name;
	/**
	 * Student's mid term exam score
	 */
	private double miScore;
	/**
	 * Student's final exam score
	 */
	private double ziScore;
	/**
	 * Student's laboratory exercises score
	 */
	private double labScore;
	/**
	 * Student's final grade
	 */
	private int grade;

	/**
	 * Creates an instance of {@code StudentRecord} with the given values.
	 * 
	 * @param jmbag
	 * @param surname
	 * @param name
	 * @param miScore
	 * @param ziScore
	 * @param labScore
	 * @param grade
	 */
	public StudentRecord(String jmbag, String surname, String name, double miScore, double ziScore, double labScore,
			int grade) {
		Objects.requireNonNull(jmbag);
		Objects.requireNonNull(surname);
		Objects.requireNonNull(name);
		if (grade < 1 || grade > 5) {
			throw new IllegalArgumentException();
		}
		this.jmbag = jmbag;
		this.surname = surname;
		this.name = name;
		this.miScore = miScore;
		this.ziScore = ziScore;
		this.labScore = labScore;
		this.grade = grade;
	}

	/**
	 * @return jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * @return last name
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @return first name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return mid term exam score
	 */
	public double getMiScore() {
		return miScore;
	}

	/**
	 * @return final exam score
	 */
	public double getZiScore() {
		return ziScore;
	}

	/**
	 * @return laboratory exercises score
	 */
	public double getLabScore() {
		return labScore;
	}

	/**
	 * @return final grade
	 */
	public int getGrade() {
		return grade;
	}

	@Override
	public String toString() {
		String result = jmbag + "\t" + surname + "\t" + name + "\t" + miScore + "\t" + ziScore + "\t" + labScore + "\t"
				+ grade;
		return result;
	}

}
