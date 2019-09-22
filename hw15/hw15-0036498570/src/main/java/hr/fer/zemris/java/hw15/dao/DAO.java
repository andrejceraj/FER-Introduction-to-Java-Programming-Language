package hr.fer.zemris.java.hw15.dao;

import java.util.List;

import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Interface towards the Data Access Object layer.
 * 
 * @author Andrej Ceraj
 *
 */
public interface DAO {

	/**
	 * Creates a blog user
	 * 
	 * @param user blog user
	 */
	public void createUser(BlogUser user);

	/**
	 * Gets a user by nick
	 * 
	 * @param nick nick
	 * @return user
	 */
	public BlogUser getUserByNick(String nick);

	/**
	 * Gets user by email
	 * 
	 * @param email email
	 * @return user
	 */
	public BlogUser getUserByEmail(String email);

	/**
	 * Gets all users
	 * 
	 * @return list of users
	 */
	public List<BlogUser> getAllUsers();

	/**
	 * Creates a blog entry
	 * 
	 * @param entry blog entry
	 */
	public void createEntry(BlogEntry entry);

	/**
	 * Gets blog entry by id
	 * 
	 * @param id id
	 * @return blog entry
	 */
	public BlogEntry getBlogEntry(Long id);

	/**
	 * Updates existing blog entry
	 * 
	 * @param entry blog entry
	 */
	public void updateEntry(BlogEntry entry);

	/**
	 * Creates a comment to blog entry
	 * 
	 * @param comment comment
	 */
	public void createComment(BlogComment comment);

}