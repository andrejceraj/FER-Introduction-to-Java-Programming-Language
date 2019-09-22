package hr.fer.zemris.java.hw15.dao;

import hr.fer.zemris.java.hw15.dao.jpa.JPADAOImpl;

/**
 * Singleton class which is used to get Data Access Object
 * 
 * @author Andrej Ceraj
 *
 */
public class DAOProvider {

	/**
	 * Data access object 
	 */
	private static DAO dao = new JPADAOImpl();

	/**
	 * DAO getter
	 * 
	 * @return data access object
	 */
	public static DAO getDAO() {
		return dao;
	}

}