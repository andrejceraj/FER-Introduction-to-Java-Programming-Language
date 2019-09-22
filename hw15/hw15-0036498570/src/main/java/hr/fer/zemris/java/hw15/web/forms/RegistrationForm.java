package hr.fer.zemris.java.hw15.web.forms;

import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Representation of a form that should be filled when a user wants to register.
 * 
 * @author Andrej Ceraj
 *
 */
public class RegistrationForm {
	/**
	 * Inserted first name
	 */
	private String firstName;
	/**
	 * Inserted last name
	 */
	private String lastName;
	/**
	 * Inserted email
	 */
	private String email;
	/**
	 * Inserted nick name
	 */
	private String nick;
	/**
	 * Inserted password
	 */
	private String password;

	/**
	 * Map containing errors
	 */
	private Map<String, String> errorMap = new HashMap<String, String>();

	/**
	 * Constructor
	 */
	public RegistrationForm() {
	}

	/**
	 * Constructor using fields: first name, last name, email, nick, password.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param nick
	 * @param password
	 */
	public RegistrationForm(String firstName, String lastName, String email, String nick, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.nick = nick;
		this.password = password;
	}

	/**
	 * @return inserted password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets inserted password to the given value
	 * 
	 * @param password value
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return inserted password
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets inserted first name to the given value
	 * 
	 * @param firstName value
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return inserted password
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets inserted last name to the given value
	 * 
	 * @param lastName value
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return inserted password
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets inserted emailto the given value
	 * 
	 * @param email value
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return inserted password
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Sets inserted nick to the given value
	 * 
	 * @param nick value
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Validates the {@link RegistrationForm} filling error map if there are any
	 * errors in the form.
	 */
	public void validate() {
		if (firstName == null || firstName.isBlank()) {
			errorMap.put("firstName", "First name is required");
		} else if (firstName.length() > 20) {
			errorMap.put("firstName", "First name cannot be longer than 20 characters.");
		}
		if (lastName == null || lastName.isBlank()) {
			errorMap.put("lastName", "Last name is required");
		} else if (lastName.length() > 20) {
			errorMap.put("lastName", "Last name cannot be longer than 20 characters.");
		}
		if (email == null || email.isBlank()) {
			errorMap.put("email", "Email is required");
		} else if (email.length() > 50) {
			errorMap.put("email", "Email cannot be longer than 50 characters.");
		} else if (!email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
			errorMap.put("email", "Email does not match required form");
		}
		if (nick == null || nick.isBlank()) {
			errorMap.put("nick", "Nick is required");
		} else if (nick.length() > 20) {
			errorMap.put("nick", "Nick cannot be longer than 20 characters.");
		}
		if (password == null || password.isBlank()) {
			errorMap.put("password", "Password is required");
		}
		BlogUser userExist = DAOProvider.getDAO().getUserByNick(nick);
		if (userExist != null) {
			errorMap.put("nick", "User with provided nick already exist");
		}
	}

	/**
	 * @return true if error map contains errors; false otherwise.
	 */
	public boolean hasErrors() {
		if (errorMap.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if error map contains error for the given key
	 * 
	 * @param string key
	 * @return true if error map contains error for the given key; false otherwise
	 */
	public boolean hasErrorOn(String string) {
		if (errorMap.containsKey(string)) {
			return true;
		}
		return false;
	}

	/**
	 * Gets error for the given key
	 * 
	 * @param string key
	 * @return error
	 */
	public String getErrorOn(String string) {
		return errorMap.get(string);
	}
}
