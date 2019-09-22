package hr.fer.zemris.java.webserver;

/**
 * @author Andrej Ceraj
 *
 */
public interface IDispatcher {
	/**
	 * Generates response HTML from the given path
	 * 
	 * @param urlPath path
	 * @throws Exception if it is unable to create request
	 */
	void dispatchRequest(String urlPath) throws Exception;
}