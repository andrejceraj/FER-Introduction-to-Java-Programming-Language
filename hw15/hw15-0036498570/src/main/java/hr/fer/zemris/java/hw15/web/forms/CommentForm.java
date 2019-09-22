package hr.fer.zemris.java.hw15.web.forms;

import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Representation of a form that should be filled when user posts a comment
 * 
 * @author Andrej Ceraj
 *
 */
public class CommentForm {
	/**
	 * Inserted email
	 */
	private String email;
	/**
	 * Inserted comment
	 */
	private String comment;
	/**
	 * Map containing errors
	 */
	private Map<String, String> errorMap = new HashMap<String, String>();

	/**
	 * Constructor
	 */
	public CommentForm() {
	}

	/**
	 * Constructor with fields: email and comment.
	 * 
	 * @param email   inserted email
	 * @param comment inserted comment
	 */
	public CommentForm(String email, String comment) {
		this.email = email;
		this.comment = comment;
	}

	/**
	 * @return inserted email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets inserted email to the given value
	 * 
	 * @param email value
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return inserted comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Sets inserted comment to the given value
	 * 
	 * @param comment value
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Validates the {@link CommentForm} filling error map if the email provided
	 * does not exist or the comment is blank.
	 */
	public void validate() {
		if (email == null || email.isBlank()) {
			errorMap.put("email", "Email is required");
		} else {
			BlogUser userExist = DAOProvider.getDAO().getUserByEmail(email);
			if (userExist == null) {
				errorMap.put("email", "User with provided email does not exist.");
			}
		}
		if (comment == null || comment.isBlank()) {
			errorMap.put("comment", "Comment cannot be empty");
		}
	}

	/**
	 * @return true if form is containing errors; false otherwise
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
