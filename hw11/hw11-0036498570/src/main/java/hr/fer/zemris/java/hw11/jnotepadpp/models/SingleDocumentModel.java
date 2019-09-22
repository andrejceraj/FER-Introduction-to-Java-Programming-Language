package hr.fer.zemris.java.hw11.jnotepadpp.models;

import java.nio.file.Path;

import javax.swing.JTextArea;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.listeners.SingleDocumentListener;

/**
 * Model of single document in {@link JNotepadPP}
 * 
 * @author Andrej Ceraj
 *
 */
public interface SingleDocumentModel {
	/**
	 * @return document text component
	 */
	JTextArea getTextComponent();

	/**
	 * @return document path
	 */
	Path getFilePath();

	/**
	 * Sets document path to the given path
	 * 
	 * @param path
	 */
	void setFilePath(Path path);

	/**
	 * @return true if the document has been modified; false otherwise
	 */
	boolean isModified();

	/**
	 * Sets modification flag to the given value
	 * 
	 * @param modified flag
	 */
	void setModified(boolean modified);

	/**
	 * Adds listener to list of listeners
	 * 
	 * @param l listener
	 */
	void addSingleDocumentListener(SingleDocumentListener l);

	/**
	 * Removes listener from the list of listeners
	 * 
	 * @param l listener
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}