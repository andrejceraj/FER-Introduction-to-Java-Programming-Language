package hr.fer.zemris.java.hw11.jnotepadpp.listeners;

import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentModel;

/**
 * Listener for {@link SingleDocumentModel}
 * 
 * @author Andrej Ceraj
 *
 */
public interface SingleDocumentListener {

	/**
	 * Method that should be called when the {@link SingleDocumentModel} has been modified.
	 * 
	 * @param model document
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);

	/**
	 * Method that should be called when the {@link SingleDocumentModel}'s path has been updated.
	 * 
	 * @param model document
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}