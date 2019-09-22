package hr.fer.zemris.java.webserver;

/**
 * Defines class that can process a request and create adequate response
 * 
 * @author Andrej Ceraj
 *
 */
public interface IWebWorker {
	/**
	 * Creates adequate response based on the {@link RequestContext}
	 * 
	 * @param context request context
	 * @throws Exception if it is unable to create response
	 */
	public void processRequest(RequestContext context) throws Exception;
}