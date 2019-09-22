package hr.fer.zemris.java.hw07.demo4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Demonstration program of task 4 of 7th homework of Basics of Java Programming
 * Language course.
 * 
 * @author Andrej Ceraj
 *
 */
public class StudentsDemo {

	/**
	 * Method runs when the program is started.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		Path path = Paths.get("./src/main/resources/studenti.txt");
		List<String> lines;
		List<StudentRecord> records = null;
		try {
			lines = Files.readAllLines(path);
			records = convert(lines);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return;
		}

		// 1. zadatak
		long broj = vratiBodovaViseOd25(records);
		System.out.println("Zadatak 1\n=========");
		System.out.println(broj);

		// 2. zadatak
		long broj5 = vratiBrojOdlikasa(records);
		System.out.println("\nZadatak 2\n=========");
		System.out.println(broj5);

		// 3. zadatak
		List<StudentRecord> odlikasi = vratiListuOdlikasa(records);
		System.out.println("\nZadatak 3\n=========");
		for (StudentRecord record : odlikasi) {
			System.out.println(record);
		}

		// 4. zadatak
		List<StudentRecord> odlikasiSortirano = vratiSortiranuListuOdlikasa(records);
		System.out.println("\nZadatak 4\n=========");
		for (StudentRecord record : odlikasiSortirano) {
			System.out.println(record);
		}

		// 5. zadatak
		List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);
		System.out.println("\nZadatak 5\n=========");
		for (String jmbag : nepolozeniJMBAGovi) {
			System.out.println(jmbag);
		}

		// 6. zadatak
		Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);
		System.out.println("\nZadatak 6\n=========");
		for (Map.Entry<Integer, List<StudentRecord>> entry : mapaPoOcjenama.entrySet()) {
			System.out.println("Ocjena: " + entry.getKey());
			for (StudentRecord record : entry.getValue()) {
				System.out.println("\t" + record);
			}
		}

		// 7. zadatak
		Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);
		System.out.println("\nZadatak 7\n=========");
		for (Map.Entry<Integer, Integer> entry : mapaPoOcjenama2.entrySet()) {
			System.out.println("Ocjena: " + entry.getKey() + " - " + entry.getValue());
		}

		// 8. zadatak
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);
		System.out.println("\nZadatak 8\n=========");
		for (Map.Entry<Boolean, List<StudentRecord>> entry : prolazNeprolaz.entrySet()) {
			System.out.println("Prolaz = " + entry.getKey());
			for (StudentRecord record : entry.getValue()) {
				System.out.println("\t" + record);
			}
		}

	}

	/**
	 * Returns a number of {@code StudentRecord}s whose total score is greater than
	 * 25.
	 * 
	 * @param records
	 * @return number of records
	 */
	private static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records.stream().mapToDouble(r -> r.getMiScore() + r.getZiScore() + r.getLabScore()).filter(r -> r > 25)
				.count();
	}

	/**
	 * Returns a number of {@code StudentRecord}s whose grade is equal to 5.
	 * 
	 * @param records
	 * @return number of records
	 */
	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(r -> r.getGrade() == 5).count();
	}

	/**
	 * Returns a list of {@code StudentRecord}s whose grade is equal to 5
	 * 
	 * @param records
	 * @return list of {@code StudentRecord}s
	 */
	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(r -> r.getGrade() == 5).collect(Collectors.toList());
	}

	/**
	 * Returns a list of {@code StudentRecord}s whose grade is equal to 5, sorted by
	 * the total score sum.
	 * 
	 * @param records
	 * @return sorted list of {@code StudentRecord}s
	 */
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(r -> r.getGrade() == 5)
				.sorted((r1, r2) -> Double.valueOf(r2.getMiScore() + r2.getZiScore() + r2.getLabScore())
						.compareTo(Double.valueOf(r1.getMiScore() + r1.getZiScore() + r1.getLabScore())))
				.collect(Collectors.toList());
	}

	/**
	 * Returns a list of JMBAGs of {@code StudentRecord}s whose grade is equal to 1.
	 * 
	 * @param records
	 * @return list of JMBAGs
	 */
	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		return records.stream().filter(r -> r.getGrade() == 1).map(r -> r.getJmbag())
				.sorted((j1, j2) -> j1.compareTo(j2)).collect(Collectors.toList());
	}

	/**
	 * Returns a map that associates each grade with list of students that got that
	 * grade
	 * 
	 * @param records
	 * @return grade - list of {@code StudentRecord}s map
	 */
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		return records.stream().collect(Collectors.groupingBy(r -> r.getGrade()));
	}

	/**
	 * Returns a map that associates each grade with a number of students that got
	 * that grade
	 * 
	 * @param records
	 * @return grade - number of students map
	 */
	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		return records.stream().collect(Collectors.toMap(r -> r.getGrade(), r -> 1, (r1, r2) -> r1 + 1));
	}

	/**
	 * Returns a map associating true value with {@code StudentRecord}s that has
	 * grade greater than 1; and false value with {@code StudentRecord}s that has
	 * grade equal to 1.
	 * 
	 * @param records
	 * @return passage/failure map
	 */
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		return records.stream().collect(Collectors.partitioningBy(r -> r.getGrade() != 1));
	}

	/**
	 * Method converts a list of Strings containing students records into list of
	 * {@link StudentRecord} objects.
	 * 
	 * @param lines
	 * @return list of {@code StudentRecord}s
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> list = new ArrayList<StudentRecord>();
		for (String line : lines) {
			String[] words = line.split("\t");
			if (words.length != 7) {
				continue;
			}
			String jmbag = words[0];
			String surname = words[1];
			String name = words[2];
			double miScore = Double.parseDouble(words[3]);
			double ziScore = Double.parseDouble(words[4]);
			double labScore = Double.parseDouble(words[5]);
			int grade = Integer.parseInt(words[6]);
			StudentRecord record = new StudentRecord(jmbag, surname, name, miScore, ziScore, labScore, grade);
			list.add(record);
		}
		return list;
	}
}
