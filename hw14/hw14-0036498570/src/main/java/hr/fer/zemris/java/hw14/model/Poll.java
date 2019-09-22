package hr.fer.zemris.java.hw14.model;

import java.util.Objects;

/**
 * Model of a poll
 * 
 * @author Andrej Ceraj
 *
 */
public class Poll {
	/**
	 * Poll id
	 */
	private long id;
	/**
	 * Poll title
	 */
	private String title;
	/**
	 * Poll message
	 */
	private String message;

	/**
	 * Constructor
	 */
	public Poll() {
	}

	/**
	 * Constructor with title and message.
	 * 
	 * @param title
	 * @param message
	 */
	public Poll(String title, String message) {
		this.title = title;
		this.message = message;
	}

	/**
	 * Sets id to the given value
	 * 
	 * @param id value
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return poll id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return poll title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets title to the given value
	 * 
	 * @param title value
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return poll message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets message to the given value
	 * 
	 * @param message value
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Poll))
			return false;
		Poll other = (Poll) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Poll id = " + id;
	}

}
