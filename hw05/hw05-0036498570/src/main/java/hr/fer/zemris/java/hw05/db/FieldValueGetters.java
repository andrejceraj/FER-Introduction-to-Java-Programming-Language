package hr.fer.zemris.java.hw05.db;

/**
 * Class used to get value of an appropriate field of {@link StudentRecord}.
 * 
 * @author Andrej Ceraj
 *
 */
public class FieldValueGetters {
	/**
	 * Class used to get {@code StudentRecord} JMBAG.
	 */
	public static final IFieldValueGetter JMBAG = new IFieldValueGetter() {

		@Override
		public String get(StudentRecord studentRecord) {
			return studentRecord.getJmbag();
		}
	};
	/**
	 * Class used to get {@code StudentRecord} last name.
	 */
	public static final IFieldValueGetter LAST_NAME = new IFieldValueGetter() {

		@Override
		public String get(StudentRecord studentRecord) {
			return studentRecord.getLastName();
		}
	};
	/**
	 * Class used to get {@code StudentRecord} first name.
	 */
	public static final IFieldValueGetter FIRST_NAME = new IFieldValueGetter() {

		@Override
		public String get(StudentRecord studentRecord) {
			return studentRecord.getFirstName();
		}
	};

}
