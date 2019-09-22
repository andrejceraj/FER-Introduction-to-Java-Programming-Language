package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import hr.fer.zemris.java.hw05.db.parser.QueryParser;

/**
 * Program that prints all student records that satisfies the query. When the
 * program is started, it loads data from the .txt file and creates
 * {@link StudentDatabase} with the loaded data. It then offers the user to type
 * in a wanted query and if the query is valid, the results will be shown on the
 * screen; if the query is invalid, an appropriate message will be shown
 * instead. User can type in as many queries as he likes until it types in
 * 'exit'. The program will then output 'Goodbye!' and exit.
 * 
 * @author Andrej Ceraj
 *
 */
public class StudentDB {
	/**
	 * Method starts when the program is run.
	 * 
	 * @param args Not used in this program
	 */
	public static void main(String[] args) {
		String[] data = loadData("src/main/resources/database.txt");
		if (data == null) {
			System.out.println("Error occured reading the database.");
			return;
		}

		try (Scanner scanner = new Scanner(System.in)) {
			StudentDatabase database = new StudentDatabase(data);
			while (true) {
				System.out.print(" > ");
				String scannedLine = scanner.nextLine();
				if (scannedLine.equals("exit")) {
					System.out.println("Goodbye!");
					break;
				} else {
					try {
						String pureQuery = getQuery(scannedLine);
						QueryParser parser = new QueryParser(pureQuery);
						if(parser.getQuery().size() == 0) {
							System.out.println("No expression in given query");
							continue;
						}
						if (parser.isDirectQuery()) {
							StudentRecord record = database.forJMBAG(parser.getQueriedJMBAG());
							output(record);
						} else {
							output(database.filter(new QueryFilter(parser.getQuery())));
						}

					} catch (Exception exception) {
						System.out.println(exception.getMessage());
					}
				}
			}
		} catch (Exception exception) {
			System.out.println(exception.getMessage() + "\nexiting...");
			
		}
	}

	/**
	 * Method loads data from the given file path.
	 * 
	 * @param file path
	 * @return loaded data.
	 */
	private static String[] loadData(String file) {
		try {
			List<String> lines = Files.readAllLines(Paths.get(file), StandardCharsets.UTF_8);
			String[] data = new String[lines.size()];
			int i = 0;
			for (String line : lines) {
				data[i++] = line;
			}
			return data;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Method extracts and returns pure query from the given string.
	 * 
	 * @param scannedLine string
	 * @return pure query
	 */
	private static String getQuery(String scannedLine) {
		int indexOfQuery = scannedLine.indexOf("query");
		if (indexOfQuery == -1) {
			throw new NoSuchElementException("There is no keyword \"query\"");
		}
		return scannedLine.substring(indexOfQuery + 5, scannedLine.length());
	}

	/**
	 * Method prints appropriate output format for the given student record.
	 * 
	 * @param record
	 */
	private static void output(StudentRecord record) {
		List<StudentRecord> records = new ArrayList<StudentRecord>();
		if (record != null) {
			records.add(record);
		}
		System.out.println("Using index for record retrieval.");
		output(records);
	}

	/**
	 * Method prints appropriate output format for the given student records.
	 * 
	 * @param records
	 */
	private static void output(List<StudentRecord> records) {
		if (records.size() > 0) {
			printTable(records);
		}
		System.out.println("Records selected: " + records.size());
	}

	/**
	 * Method prints table with student records.
	 * 
	 * @param records
	 */
	private static void printTable(List<StudentRecord> records) {
		int lastNameLength = records.stream().mapToInt(r -> r.getLastName().length()).max().getAsInt();
		int firstNameLength = records.stream().mapToInt(r -> r.getFirstName().length()).max().getAsInt();
		printFrame(lastNameLength, firstNameLength);
		for (StudentRecord record : records) {
			printRecord(record, lastNameLength, firstNameLength);
		}
		printFrame(lastNameLength, firstNameLength);
	}

	/**
	 * Method prints frame which is scaled with the given last name length and first
	 * name length.
	 * 
	 * @param lastNameLength
	 * @param firstNameLength
	 */
	private static void printFrame(int lastNameLength, int firstNameLength) {
		String frame = "+============+" + "=".repeat(lastNameLength + 2) + "+" + "=".repeat(firstNameLength + 2)
				+ "+===+";
		System.out.println(frame);
	}

	/**
	 * Method prints one row containing the given student record. Last name column
	 * is scaled to the given lastNameLength and first name column is scaled to the
	 * given firstNameLength.
	 * 
	 * @param record
	 * @param lastNameLength
	 * @param firstNameLength
	 */
	private static void printRecord(StudentRecord record, int lastNameLength, int firstNameLength) {
		String lastName = String.format("%-" + lastNameLength + "s", record.getLastName());
		String firstName = String.format("%-" + firstNameLength + "s", record.getFirstName());
		System.out.println(
				"| " + record.getJmbag() + " | " + lastName + " | " + firstName + " | " + record.getGrade() + " |");
	}
}
