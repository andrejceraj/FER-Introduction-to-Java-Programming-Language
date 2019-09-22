package hr.fer.zemris.java.hw11.jnotepadpp.models.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.zemris.java.hw11.jnotepadpp.listeners.MultipleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.listeners.SingleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentModel;

/**
 * Default implementation of {@link MultipleDocumentModel}
 * 
 * @author Andrej Ceraj
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = 7569223275234579117L;

	/**
	 * Name of unnamed documents
	 */
	private static final String UNNAMED = "(unnamed)";

	/**
	 * List of {@link SingleDocumentModel}s
	 */
	private List<SingleDocumentModel> documents = new ArrayList<SingleDocumentModel>();
	/**
	 * Currently selected document
	 */
	private SingleDocumentModel currentDocument;
	/**
	 * List of listeners
	 */
	private List<MultipleDocumentListener> listeners = new ArrayList<MultipleDocumentListener>();

	/**
	 * Icon showing on tabs which documents have been modified
	 */
	private Icon modifiedIcon;
	/**
	 * Icon showing on tabs which documents have not been modified
	 */
	private Icon notModifiedIcon;

	/**
	 * Creates an instance of {@link DefaultMultipleDocumentModel}
	 * 
	 * @param modifiedIcon    icon
	 * @param notModifiedIcon icon
	 */
	public DefaultMultipleDocumentModel(Icon modifiedIcon, Icon notModifiedIcon) {
		this.modifiedIcon = modifiedIcon;
		this.notModifiedIcon = notModifiedIcon;
		this.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				SingleDocumentModel previousModel = currentDocument;
				int index = getSelectedIndex();
				currentDocument = index == -1 ? null : documents.get(index);
				notifyListenersCurrentDocumentChanged(previousModel, currentDocument);
			}
		});
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		addTabWithModificationListener(new DefaultSingleDocumentModel(null, ""));

		return currentDocument;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		Objects.requireNonNull(path);
		for (int i = 0; i < documents.size(); i++) {
			if (documents.get(i).getFilePath() != null && documents.get(i).getFilePath().equals(path)) {
				setTab(i);
				return currentDocument;
			}
		}

		String content;
		try {
			content = new String(Files.readAllBytes(path));
		} catch (IOException e) {
			errorBox("Could not load file", "Error");
			return currentDocument;
		}

		addTabWithModificationListener(new DefaultSingleDocumentModel(path, content));

		return currentDocument;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		if (model == null) {
			errorBox("There is no tab to save.", "Error");
			return;
		}

		Path savePath;
		if (newPath == null) {
			savePath = model.getFilePath();
		} else {
			for(SingleDocumentModel doc : documents) {
				if(doc.getFilePath().equals(newPath)) {
					errorBox("You can't save file to selected path as document with"
							+ "selected path is currently opened.", "Error");
					return;
				}
			}
			savePath = newPath;
		}
		try {
			Files.writeString(savePath, model.getTextComponent().getText());
			model.setFilePath(savePath);
			model.setModified(false);
			notifyListenersCurrentDocumentChanged(null, currentDocument);

			infoBox("File saved successfuly", "Info");
		} catch (IOException e) {
			errorBox("Unable to save document.", "Error");
		}

	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		int index = documents.indexOf(model);
		this.remove(index);
		documents.remove(model);
		if (currentDocument == model) {
			this.setSelectedIndex(documents.isEmpty() ? -1 : 0);
			currentDocument = documents.isEmpty() ? null : documents.get(0);
			notifyListenersCurrentDocumentChanged(model, currentDocument);
		}
		notifyListenersDocumentRemoved(model);
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return documents.get(index);
	}

	/**
	 * Sets currently selected tab to the tab with in the given index
	 * 
	 * @param newIndex tab index
	 */
	private void setTab(int newIndex) {
		SingleDocumentModel previous = currentDocument;
		currentDocument = documents.get(newIndex);
		this.setSelectedIndex(newIndex);
		notifyListenersCurrentDocumentChanged(previous, currentDocument);
	}

	/**
	 * Adds a tab with modification listener and addresses
	 * {@link SingleDocumentModel} to it.
	 * 
	 * @param newModel document
	 */
	private void addTabWithModificationListener(SingleDocumentModel newModel) {
		SingleDocumentModel previous = currentDocument;
		currentDocument = newModel;
		documents.add(currentDocument);

		Path path = currentDocument.getFilePath();
		String title = path == null ? UNNAMED : path.getFileName().toString();
		this.addTab(title, getAppropriateIcon(currentDocument), new JScrollPane(currentDocument.getTextComponent()));

		currentDocument.addSingleDocumentListener(new SingleDocumentListener() {

			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				setIconAt(documents.indexOf(model), getAppropriateIcon(model));
			}

			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				setTitleAt(documents.indexOf(model), model.getFilePath().getFileName().toString());

			}
		});

		this.setSelectedIndex(documents.size() - 1);
		setToolTipTextAt(documents.size() - 1, path == null ? UNNAMED : path.toString());
		notifyListenersDocumentAdded(currentDocument);
		notifyListenersCurrentDocumentChanged(previous, currentDocument);
	}

	/**
	 * Notifies all listeners for current document changed
	 * 
	 * @param previousModel document
	 * @param currentModel  document
	 */
	private void notifyListenersCurrentDocumentChanged(SingleDocumentModel previousModel,
			SingleDocumentModel currentModel) {
		listeners.forEach(l -> l.currentDocumentChanged(previousModel, currentModel));
	}

	/**
	 * Notifies all listeners for document added
	 * 
	 * @param model document
	 */
	private void notifyListenersDocumentAdded(SingleDocumentModel model) {
		listeners.forEach(l -> l.documentAdded(model));
	}

	/**
	 * Notifies all listeners for document removed
	 * 
	 * @param model document
	 */
	private void notifyListenersDocumentRemoved(SingleDocumentModel model) {
		listeners.forEach(l -> l.documentRemoved(model));
	}

	/**
	 * Shows information message
	 * 
	 * @param message
	 * @param title
	 */
	private static void infoBox(String message, String title) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Shows error message
	 * 
	 * @param message
	 * @param title
	 */
	private static void errorBox(String message, String title) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Selects appropriate icon based on modification flag
	 * 
	 * @param model document
	 * @return icon
	 */
	private Icon getAppropriateIcon(SingleDocumentModel model) {
		return model.isModified() ? modifiedIcon : notModifiedIcon;
	}

}
