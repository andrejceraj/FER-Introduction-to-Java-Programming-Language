package hr.fer.zemris.java.hw14.model;

import java.util.Objects;

/**
 * Model of poll option.
 * 
 * @author Andrej Ceraj
 *
 */
public class PollOption {
	/**
	 * Poll option id
	 */
	private long id;
	/**
	 * Poll option title
	 */
	private String optionTitle;
	/**
	 * Poll option link
	 */
	private String optionLink;
	/**
	 * Poll id
	 */
	private long pollId;
	/**
	 * Poll option votes count
	 */
	private int votesCount;

	/**
	 * Constructor
	 */
	public PollOption() {
	}

	/**
	 * Constructor with option title, option link and poll id
	 * 
	 * @param optionTitle option title
	 * @param optionLink  option link
	 * @param pollId      poll id
	 */
	public PollOption(String optionTitle, String optionLink, long pollId) {
		this.optionTitle = optionTitle;
		this.optionLink = optionLink;
		this.pollId = pollId;
	}

	/**
	 * Sets votes count to the given value
	 * 
	 * @param votesCount value
	 */
	public void setVotesCount(int votesCount) {
		this.votesCount = votesCount;
	}

	/**
	 * @return votes count
	 */
	public int getVotesCount() {
		return votesCount;
	}

	/**
	 * @return poll option id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets poll option id count to the given value
	 * 
	 * @param id value
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return poll option title
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * Sets poll option title count to the given value
	 * 
	 * @param optionTitle value
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * @return poll option link
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * Sets poll option link count to the given value
	 * 
	 * @param optionLink value
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	/**
	 * @return poll id
	 */
	public long getPollId() {
		return pollId;
	}

	/**
	 * Sets poll id count to the given value
	 * 
	 * @param pollId value
	 */
	public void setPollId(long pollId) {
		this.pollId = pollId;
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
		if (!(obj instanceof PollOption))
			return false;
		PollOption other = (PollOption) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "PollOption id = " + id;
	}

}
