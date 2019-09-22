package hr.fer.zemris.java.hw14.dao;

import java.util.List;

import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Interface towards the Data Access Object layer.
 * 
 * @author Andrej Ceraj
 *
 */
public interface DAO {
	/**
	 * Gets all polls from the database
	 * 
	 * @return all polls
	 */
	public List<Poll> getPolls();

	/**
	 * Gets all poll options with the given poll id.
	 * 
	 * @param pollId poll id
	 * @return poll options
	 */
	public List<PollOption> getOptionsForPolId(long pollId);

	/**
	 * Gets poll by id
	 * 
	 * @param pollId poll id
	 * @return poll
	 */
	public Poll getPollById(long pollId);

	/**
	 * Registers vote for poll option with the given id
	 * 
	 * @param id poll option id
	 */
	public void registerVote(long id);

	/**
	 * Gets poll option with the given id
	 * 
	 * @param id poll option id
	 * @return poll option
	 */
	public PollOption getPollOptionById(long id);

}