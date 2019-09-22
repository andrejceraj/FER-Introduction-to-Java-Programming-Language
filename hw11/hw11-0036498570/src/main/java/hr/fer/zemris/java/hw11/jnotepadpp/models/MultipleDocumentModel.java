package hr.fer.zemris.java.hw11.jnotepadpp.models;

import java.nio.file.Path;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.listeners.MultipleDocumentListener;

/**
 * Model of multiple documents in {@link JNotepadPP}
 * 
 * @author Andrej Ceraj
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	/**
	 * Creates new {@link SingleDocumentModel} and returns it.
	 * 
	 * @return new document
	 */
	SingleDocumentModel createNewDocument();

	/**
	 * Returns document in currently selected tab
	 * 
	 * @return document
	 */
	SingleDocumentModel getCurrentDocument();

	/**
	 * Loads document from the given path and sets it as currently selected document
	 * 
	 * @param path document path
	 * @return loaded document
	 */
	SingleDocumentModel loadDocument(Path path);

	/**
	 * Saves the given {@link SingleDocumentModel} to the given path
	 * 
	 * @param model   document
	 * @param newPath path
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);

	/**
	 * Closes currently selected {@link SingleDocumentModel} and its tab
	 * 
	 * @param model document
	 */
	void closeDocument(SingleDocumentModel model);

	/**
	 * Adds listener to the list of listeners
	 * 
	 * @param l listener
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Removes a listener from the list of listeners
	 * 
	 * @param l listener
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Returns number of documents in {@link MultipleDocumentListener}
	 * 
	 * @return
	 */
	int getNumberOfDocuments();

	/**
	 * Gets a {@link SingleDocumentModel} from the {@link MultipleDocumentListener}
	 * by the given index
	 * 
	 * @param index index of document
	 * @return document
	 */
	SingleDocumentModel getDocument(int index);
}
