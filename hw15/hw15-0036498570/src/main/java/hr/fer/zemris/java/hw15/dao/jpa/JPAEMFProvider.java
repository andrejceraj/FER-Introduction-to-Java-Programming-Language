package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Java Persistence API Entity Manager Factory Provider. Singleton used to
 * provide entity manager factory.
 * 
 * @author Andrej Ceraj
 *
 */
public class JPAEMFProvider {

	/**
	 * Entity manager factory
	 */
	public static EntityManagerFactory emf;

	/**
	 * @return entity manager factory
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * Sets entity manager factory to given value
	 * 
	 * @param emf entity manager factory
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}