package hr.fer.zemris.java.hw11.jnotepadpp.models.impl;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import hr.fer.zemris.java.hw11.jnotepadpp.listeners.SingleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentModel;

/**
 * Default implementation of {@link SingleDocumentModel}
 * 
 * @author Andrej Ceraj
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {
	/**
	 * This document's path
	 */
	private Path path;
	/**
	 * Text area this document contains
	 */
	private JTextArea textArea;
	/**
	 * Flag showing if the document has been modified after saving or loading
	 */
	private boolean isModified;
	/**
	 * List of document listeners
	 */
	private List<SingleDocumentListener> listeners = new ArrayList<SingleDocumentListener>();

	/**
	 * Creates an instance of {@link DefaultSingleDocumentModel}
	 * 
	 * @param path        document path
	 * @param textContent document text content
	 */
	public DefaultSingleDocumentModel(Path path, String textContent) {
		this.path = path;
		this.textArea = new JTextArea(textContent);
		this.isModified = false;
		textArea.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				setModified(true);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				setModified(true);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				setModified(true);
			}
		});
	}

	@Override
	public JTextArea getTextComponent() {
		return textArea;
	}

	@Override
	public Path getFilePath() {
		return path;
	}

	@Override
	public void setFilePath(Path path) {
		Objects.requireNonNull(path);
		this.path = path;
		notifyListenersPathUpdated();
	}

	@Override
	public boolean isModified() {
		return isModified;
	}

	@Override
	public void setModified(boolean modified) {
		if (isModified != modified) {
			isModified = modified;
			notifyListenersModified();
		}
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}

	/**
	 * Notifies listeners for modify status updated
	 */
	private void notifyListenersModified() {
		listeners.forEach(l -> l.documentModifyStatusUpdated(this));
	}

	/**
	 * Notify listeners for file path updated
	 */
	private void notifyListenersPathUpdated() {
		listeners.forEach(l -> l.documentFilePathUpdated(this));
	}

}
