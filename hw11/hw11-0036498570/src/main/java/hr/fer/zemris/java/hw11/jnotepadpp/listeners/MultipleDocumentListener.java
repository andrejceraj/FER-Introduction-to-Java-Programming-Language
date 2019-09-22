package hr.fer.zemris.java.hw11.jnotepadpp.listeners;

import hr.fer.zemris.java.hw11.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentModel;

/**
 * Listener for {@link MultipleDocumentModel}
 * 
 * @author Andrej Ceraj
 *
 */
public interface MultipleDocumentListener {

	/**
	 * Method that should be called when currently selected document in
	 * {@link MultipleDocumentListener} has been changed
	 * 
	 * @param previousModel
	 * @param currentModel
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

	/**
	 * Method that should be called when document in
	 * {@link MultipleDocumentListener} has been added
	 * 
	 * @param model document
	 */
	void documentAdded(SingleDocumentModel model);

	/**
	 * Method that should be called when document in
	 * {@link MultipleDocumentListener} has been removed
	 * 
	 * @param model document
	 */
	void documentRemoved(SingleDocumentModel model);
}