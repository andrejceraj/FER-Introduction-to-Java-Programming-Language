package hr.fer.zemris.java.hw15.dao.jpa;

import java.util.List;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Java persistence API implementation of Data Access Object
 * 
 * @author Andrej Ceraj
 *
 */
public class JPADAOImpl implements DAO {

	@Override
	public void createUser(BlogUser user) {
		try {
			JPAEMProvider.getEntityManager().persist(user);
		} catch (Exception e) {
			throw new DAOException("Unable to create user", e);
		}
	}

	@Override
	public BlogUser getUserByNick(String nick) {
		@SuppressWarnings("unchecked")
		List<BlogUser> users = JPAEMProvider.getEntityManager()
				.createQuery("SELECT u FROM BlogUser as u WHERE u.nick = :nick").setParameter("nick", nick)
				.getResultList();
		if (users.isEmpty()) {
			return null;
		}
		return users.get(0);
	}

	@Override
	public BlogUser getUserByEmail(String email) {
		@SuppressWarnings("unchecked")
		List<BlogUser> users = JPAEMProvider.getEntityManager()
				.createQuery("SELECT u FROM BlogUser as u WHERE u.email = :email").setParameter("email", email)
				.getResultList();
		if (users.isEmpty()) {
			return null;
		}
		return users.get(0);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BlogUser> getAllUsers() {
		return JPAEMProvider.getEntityManager().createQuery("SELECT u FROM BlogUser as u").getResultList();
	}

	@Override
	public void createEntry(BlogEntry entry) {
		try {
			JPAEMProvider.getEntityManager().persist(entry);
		} catch (Exception e) {
			throw new DAOException("Unable to create entry", e);
		}
	}

	@Override
	public BlogEntry getBlogEntry(Long id) {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	@Override
	public void updateEntry(BlogEntry entry) {
		JPAEMProvider.getEntityManager().merge(entry);
	}

	@Override
	public void createComment(BlogComment comment) {
		try {
			JPAEMProvider.getEntityManager().persist(comment);
		} catch (Exception e) {
			throw new DAOException("Unable to create comment", e);
		}
	}

}