package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Function;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import hr.fer.zemris.java.hw11.jnotepadpp.listeners.MultipleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LJcomps.*;
import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.models.impl.DefaultMultipleDocumentModel;

/**
 * Text editor application with following options: holding multiple tabs, saving
 * files, opening files, creating documents showing document statistics, upper
 * or lower casing selected section, inverting casing of selected section,
 * sorting selected lines ascending or descending, removing repeating lines from
 * the selected section.
 * <p>
 * JNotepad++ is supported in three languages: English, Croatian and German
 * </p>
 * 
 * 
 * @author Andrej Ceraj
 *
 */
public class JNotepadPP extends JFrame {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = 8840011296369844046L;
	/**
	 * Location of red floppy disc icon
	 */
	private static final String MODIFIED_ICON_PATH = "icons/red.png";
	/**
	 * Location of green floppy disc icon
	 */
	private static final String NOT_MODIFIED_ICON_PATH = "icons/green.png";
	/**
	 * JNotepad++ name
	 */
	private static final String J_NOTEPAD_PP = "JNotepad++";

	/**
	 * Reference to custom {@link JTabbedPane}
	 */
	private DefaultMultipleDocumentModel multipleDocumentModel;
	/**
	 * Provider for localization
	 */
	private FormLocalizationProvider fpl;

	/**
	 * Creates an instance of {@link JNotepadPP}
	 */
	public JNotepadPP() {
		setLocation(20, 20);
		setSize(1000, 1000);
		setTitle(J_NOTEPAD_PP);

		fpl = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		try {
			initGUI();
			setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
	}

	/**
	 * GUI initialization of {@link JNotepadPP}
	 * 
	 * @throws IOException if method cannot load icons
	 */
	private void initGUI() throws IOException {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		Icon modifiedIcon = loadIcon(MODIFIED_ICON_PATH);
		Icon notModifiedIcon = loadIcon(NOT_MODIFIED_ICON_PATH);
		multipleDocumentModel = new DefaultMultipleDocumentModel(modifiedIcon, notModifiedIcon);

		cp.add(multipleDocumentModel, BorderLayout.CENTER);

		multipleDocumentModel.addMultipleDocumentListener(new MultipleDocumentListener() {

			@Override
			public void documentRemoved(SingleDocumentModel model) {
			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				if (multipleDocumentModel.getSelectedIndex() == -1) {
					setTitle(J_NOTEPAD_PP);
					return;
				}

				Path path = multipleDocumentModel.getDocument(multipleDocumentModel.getSelectedIndex()).getFilePath();
				setTitle(path == null ? "(unnamed) - " + J_NOTEPAD_PP : path.toString() + " - " + J_NOTEPAD_PP);
			}
		});

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				exitAction.actionPerformed(null);
			}
		});

		createActions();
		createMenus();
		cp.add(createToolBar(), BorderLayout.PAGE_START);
		cp.add(createStatusBar(), BorderLayout.PAGE_END);

	}

	/**
	 * Creates string representing date "yyyy/mm/dd hh:mm:ss"
	 * 
	 * @return date string
	 */
	@SuppressWarnings("deprecation")
	private String getDate() {
		Date date = new Date();
		int year = date.getYear() + 1900;
		int month = date.getMonth() + 1;
		int day = date.getDate();
		int hours = date.getHours();
		int mins = date.getMinutes();
		int secs = date.getSeconds();

		return year + "/" + month + "/" + day + " " + hours + ":" + mins + ":" + secs;
	}

	/**
	 * Creates status bar displaying current document length, caret information and
	 * date.
	 * 
	 * @return status bar
	 */
	private JPanel createStatusBar() {
		JPanel statusBar = new JPanel();
		statusBar.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.LIGHT_GRAY));
		statusBar.setLayout(new GridLayout(1, 3));

		JPanel left = new JPanel(new BorderLayout());
		left.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		JPanel middle = new JPanel(new BorderLayout());
		middle.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		JPanel right = new JPanel(new BorderLayout());
		right.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

		statusBar.add(left);
		statusBar.add(middle);
		statusBar.add(right);

		JLabel length = new JLabel("length :");
		left.add(length, BorderLayout.WEST);
		JLabel caret = new JLabel("Ln:   Col:   Sel:  ");
		middle.add(caret, BorderLayout.WEST);
		JLabel time = new JLabel(getDate());
		right.add(time, BorderLayout.EAST);

		ActionListener updateClockAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				time.setText(getDate());
			}
		};
		Timer t = new Timer(1000, updateClockAction);
		t.start();

		CaretListener statusBarListener = new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				String text = multipleDocumentModel.getCurrentDocument().getTextComponent().getText();
				int dot = e.getDot();
				int mark = e.getMark();
				int selCounter = Math.abs(dot - mark);

				int lnCounter = 1;
				int colCounter = 1;
				for (int i = 0; i < dot; i++) {
					colCounter++;
					if (text.charAt(i) == '\n') {
						lnCounter++;
						colCounter = 1;
					}
				}
				length.setText("length: " + text.length());
				caret.setText("Ln: " + lnCounter + "  Col: " + colCounter + "  Sel: " + selCounter);
			}
		};

		multipleDocumentModel.addMultipleDocumentListener(new MultipleDocumentListener() {
			@Override
			public void documentRemoved(SingleDocumentModel model) {
			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				if (previousModel != null) {
					previousModel.getTextComponent().removeCaretListener(statusBarListener);
				}
				if (currentModel != null) {
					currentModel.getTextComponent().addCaretListener(statusBarListener);
				}
			}
		});

		return statusBar;
	}

	/**
	 * Creates tool bar containing basic text editor actions
	 * 
	 * @return tool bar
	 */
	private JToolBar createToolBar() {
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(true);

		toolBar.add(new LJButton(newDocumentAction, fpl, "new"));
		toolBar.add(new LJButton(openAction, fpl, "open"));
		toolBar.add(new LJButton(saveAction, fpl, "save"));
		toolBar.add(new LJButton(saveAsAction, fpl, "saveAs"));
		toolBar.addSeparator();
		toolBar.add(new LJButton(cutAction, fpl, "cut"));
		toolBar.add(new LJButton(copyAction, fpl, "copy"));
		toolBar.add(new LJButton(pasteAction, fpl, "paste"));
		toolBar.addSeparator();
		toolBar.add(new LJButton(statsAction, fpl, "statistics"));
		toolBar.addSeparator();
		toolBar.add(new LJButton(closeAction, fpl, "close"));
		toolBar.add(new LJButton(exitAction, fpl, "exit"));

		return toolBar;
	}

	/**
	 * Creates menus and attaches them to {@link JNotepadPP}
	 */
	private void createMenus() {
		JMenuBar menu = new JMenuBar();

		JMenu file = new LJMenu(fpl, "file");
		menu.add(file);
		file.add(new LJMenuItem(newDocumentAction, fpl, "new"));
		file.add(new LJMenuItem(openAction, fpl, "open"));
		file.add(new LJMenuItem(saveAction, fpl, "save"));
		file.add(new LJMenuItem(saveAsAction, fpl, "saveAs"));
		file.addSeparator();
		file.add(new LJMenuItem(closeAction, fpl, "close"));
		file.add(new LJMenuItem(exitAction, fpl, "exit"));

		JMenu edit = new LJMenu(fpl, "edit");
		menu.add(edit);
		edit.add(new LJMenuItem(cutAction, fpl, "cut"));
		edit.add(new LJMenuItem(copyAction, fpl, "copy"));
		edit.add(new LJMenuItem(pasteAction, fpl, "paste"));
		edit.addSeparator();
		edit.add(new LJMenuItem(statsAction, fpl, "statistics"));

		JMenu tools = new LJMenu(fpl, "tools");
		menu.add(tools);

		JMenu changeCase = new LJMenu(fpl, "changeCase");
		tools.add(changeCase);
		changeCase.add(new LJMenuItem(toUpperAction, fpl, "toUpper"));
		changeCase.add(new LJMenuItem(toLowerAction, fpl, "toLower"));
		changeCase.add(new LJMenuItem(invertAction, fpl, "invert"));
		changeCase.setEnabled(false);

		JMenu sort = new LJMenu(fpl, "sort");
		tools.add(sort);
		sort.add(new LJMenuItem(ascendingAction, fpl, "ascending"));
		sort.add(new LJMenuItem(descendingAction, fpl, "descending"));
		sort.setEnabled(false);

		JMenuItem unique = new LJMenuItem(uniqueAction, fpl, "unique");
		tools.add(unique);
		unique.setEnabled(false);

		CaretListener setAble = new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent caret) {
				if (caret.getDot() == caret.getMark()) {
					changeCase.setEnabled(false);
					sort.setEnabled(false);
					unique.setEnabled(false);
				} else {
					changeCase.setEnabled(true);
					sort.setEnabled(true);
					unique.setEnabled(true);
				}
			}
		};
		multipleDocumentModel.addMultipleDocumentListener(new MultipleDocumentListener() {

			@Override
			public void documentRemoved(SingleDocumentModel model) {
			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				if (previousModel != null) {
					previousModel.getTextComponent().removeCaretListener(setAble);
				}
				if (currentModel != null) {
					currentModel.getTextComponent().addCaretListener(setAble);
				}
			}
		});

		JMenu languages = new LJMenu(fpl, "language");
		menu.add(languages);
		languages.add(new LJMenuItem(englishAction, fpl, "english"));
		languages.add(new LJMenuItem(germanAction, fpl, "german"));
		languages.add(new LJMenuItem(croatianAction, fpl, "croatian"));

		setJMenuBar(menu);
	}

	/**
	 * Addresses values to every {@link JNotepadPP} action
	 */
	private void createActions() {
		newDocumentAction.putValue(Action.NAME, "New");
		newDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		newDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		newDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Creates a new tab");

		openAction.putValue(Action.NAME, "Open");
		openAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control 0"));
		openAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openAction.putValue(Action.SHORT_DESCRIPTION, "Opens a selected file");

		saveAction.putValue(Action.NAME, "Save");
		saveAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveAction.putValue(Action.SHORT_DESCRIPTION, "Saves current file");

		saveAsAction.putValue(Action.NAME, "Save As");
		saveAsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control D"));
		saveAsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		saveAsAction.putValue(Action.SHORT_DESCRIPTION, "Saves current file at selected path");

		closeAction.putValue(Action.NAME, "Close");
		closeAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		closeAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Q);
		closeAction.putValue(Action.SHORT_DESCRIPTION, "Closes current tab");

		exitAction.putValue(Action.NAME, "Exit");
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		exitAction.putValue(Action.SHORT_DESCRIPTION, "Exits JNotepad++");

		cutAction.putValue(Action.NAME, "Cut");
		cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cutAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		cutAction.putValue(Action.SHORT_DESCRIPTION, "Cuts selected text into clipboard");

		copyAction.putValue(Action.NAME, "Copy");
		copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copyAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copyAction.putValue(Action.SHORT_DESCRIPTION, "Copies selected text into clipboard");

		pasteAction.putValue(Action.NAME, "Paste");
		pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		pasteAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		pasteAction.putValue(Action.SHORT_DESCRIPTION, "Pastes text stored in clipboard");

		statsAction.putValue(Action.NAME, "Statistics");
		statsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
		statsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		statsAction.putValue(Action.SHORT_DESCRIPTION, "Lists document statistics");

		englishAction.putValue(Action.NAME, "English");
		englishAction.putValue(Action.SHORT_DESCRIPTION, "Set language to English");

		germanAction.putValue(Action.NAME, "German");
		germanAction.putValue(Action.SHORT_DESCRIPTION, "Set language to German");

		croatianAction.putValue(Action.NAME, "Croatian");
		croatianAction.putValue(Action.SHORT_DESCRIPTION, "Set language to Croatian");

		toUpperAction.putValue(Action.NAME, "To Uppercase");
		toUpperAction.putValue(Action.SHORT_DESCRIPTION, "Uppercase selected section");

		toLowerAction.putValue(Action.NAME, "To Lowercase");
		toLowerAction.putValue(Action.SHORT_DESCRIPTION, "Lowercase selected section");

		invertAction.putValue(Action.NAME, "Invert");
		invertAction.putValue(Action.SHORT_DESCRIPTION, "Invert selected section");

		ascendingAction.putValue(Action.NAME, "Ascending");
		ascendingAction.putValue(Action.SHORT_DESCRIPTION, "Sort selected section ascending");

		descendingAction.putValue(Action.NAME, "Descending");
		descendingAction.putValue(Action.SHORT_DESCRIPTION, "Sort selected section descending");

		uniqueAction.putValue(Action.NAME, "Unique");
		uniqueAction.putValue(Action.SHORT_DESCRIPTION, "Remove all duplicate lines");

	}

	/**
	 * Loads icon from the string path
	 * 
	 * @param pathString string path
	 * @return image icon
	 * @throws IOException if there is no icon on the given path
	 */
	private Icon loadIcon(String pathString) throws IOException {
		InputStream is = this.getClass().getResourceAsStream(pathString);
		if (is == null) {
			throw new IllegalArgumentException("No icon found on path: " + pathString);
		}
		byte[] bytes = is.readAllBytes();
		is.close();
		return new ImageIcon(bytes);
	}

	/**
	 * Creates new document
	 */
	private final Action newDocumentAction = new AbstractAction() {

		/**
		 * Defaultly generated serial version ID
		 */
		private static final long serialVersionUID = -3660225573128456868L;

		@Override
		public void actionPerformed(ActionEvent e) {
			multipleDocumentModel.createNewDocument();
		}
	};

	/**
	 * Opens a document
	 */
	private final Action openAction = new AbstractAction() {

		/**
		 * Defaultly generated serial version ID
		 */
		private static final long serialVersionUID = -2796634880921047137L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Open");

			if (fileChooser.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}

			Path filePath = fileChooser.getSelectedFile().toPath();
			multipleDocumentModel.loadDocument(filePath);
		}
	};

	/**
	 * Saves a document
	 */
	private final Action saveAction = new AbstractAction() {

		/**
		 * Defaultly generated serial version ID
		 */
		private static final long serialVersionUID = -3330931000709851504L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (multipleDocumentModel.getCurrentDocument() == null) {
				return;
			}
			if (multipleDocumentModel.getCurrentDocument().getFilePath() == null) {
				saveAsAction.actionPerformed(e);
				return;
			}
			multipleDocumentModel.saveDocument(multipleDocumentModel.getCurrentDocument(),
					multipleDocumentModel.getCurrentDocument().getFilePath());
		}
	};

	/**
	 * Saves a document to desired path
	 */
	private final Action saveAsAction = new AbstractAction() {

		/**
		 * Defaultly generated serial version ID
		 */
		private static final long serialVersionUID = 6366520240770468350L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (multipleDocumentModel.getCurrentDocument() == null) {
				return;
			}
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Save As");

			if (fileChooser.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}

			Path filePath = fileChooser.getSelectedFile().toPath();
			multipleDocumentModel.saveDocument(multipleDocumentModel.getCurrentDocument(), filePath);
		}
	};

	/**
	 * Closes current document
	 */
	private final Action closeAction = new AbstractAction() {

		/**
		 * Defaultly generated serial version ID
		 */
		private static final long serialVersionUID = -7990560770170460604L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (multipleDocumentModel.getCurrentDocument() == null) {
				return;
			}
			SingleDocumentModel current = multipleDocumentModel.getCurrentDocument();
			if (current.isModified()) {
				switch (JOptionPane.showConfirmDialog(JNotepadPP.this, "Do you want to save files before closing?")) {
				case JOptionPane.YES_OPTION:
					saveAction.actionPerformed(e);
					break;
				case JOptionPane.NO_OPTION:
					break;
				case JOptionPane.CANCEL_OPTION:
					return;
				}
			}
			multipleDocumentModel.closeDocument(multipleDocumentModel.getCurrentDocument());
		}
	};

	/**
	 * Exits {@link JNotepadPP}
	 */
	private final Action exitAction = new AbstractAction() {

		/**
		 * Defaultly generated serial version ID
		 */
		private static final long serialVersionUID = 70178771341626582L;

		@Override
		public void actionPerformed(ActionEvent e) {
			boolean hasModified = false;
			for (SingleDocumentModel model : multipleDocumentModel) {
				if (model.isModified()) {
					hasModified = true;
					break;
				}
			}

			if (hasModified) {
				switch (JOptionPane.showConfirmDialog(JNotepadPP.this, "Do you want to save files before exiting?")) {
				case JOptionPane.YES_OPTION:
					for (SingleDocumentModel model : multipleDocumentModel) {
						if (model.isModified()) {
							multipleDocumentModel.saveDocument(model, model.getFilePath());
						}
					}
					break;
				case JOptionPane.NO_OPTION:
					break;
				case JOptionPane.CANCEL_OPTION:
					return;
				}
			}

			dispose();
		}
	};

	/**
	 * Cuts selected section of the text
	 */
	private final Action cutAction = new AbstractAction() {

		/**
		 * Defaultly generated serial version ID
		 */
		private static final long serialVersionUID = 7628783364274402725L;

		@Override
		public void actionPerformed(ActionEvent e) {
			multipleDocumentModel.getCurrentDocument().getTextComponent().cut();
		}
	};

	/**
	 * Copies selected section of the text
	 */
	private final Action copyAction = new AbstractAction() {

		/**
		 * Defaultly generated serial version ID
		 */
		private static final long serialVersionUID = 1623178307979843751L;

		@Override
		public void actionPerformed(ActionEvent e) {
			multipleDocumentModel.getCurrentDocument().getTextComponent().copy();
		}
	};

	/**
	 * Pastes text stored in clipboard
	 */
	private final Action pasteAction = new AbstractAction() {

		/**
		 * Defaultly generated serial version ID
		 */
		private static final long serialVersionUID = 1928901350688265998L;

		@Override
		public void actionPerformed(ActionEvent e) {
			multipleDocumentModel.getCurrentDocument().getTextComponent().paste();
		}
	};

	/**
	 * Lists document statistics
	 */
	private final Action statsAction = new AbstractAction() {

		/**
		 * Defaultly generated serial version ID
		 */
		private static final long serialVersionUID = 6919022162383895114L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (multipleDocumentModel.getCurrentDocument() == null) {
				JOptionPane.showMessageDialog(null, "No opened file", "Statistics", JOptionPane.ERROR_MESSAGE);
				return;
			}
			String content = multipleDocumentModel.getCurrentDocument().getTextComponent().getText();
			int charsCount = 0;
			int rowsCount = 1;
			for (char c : content.toCharArray()) {
				if (!Character.isWhitespace(c)) {
					charsCount++;
				}
				if (c == '\n') {
					rowsCount++;
				}
			}
			StringBuilder sb = new StringBuilder();
			sb.append("Your document has ").append(content.length()).append(" characters, ");
			sb.append(charsCount).append(" non-blank characters and ").append(rowsCount).append(" lines.");
			JOptionPane.showMessageDialog(null, sb.toString(), "Statistics", JOptionPane.INFORMATION_MESSAGE);
		}
	};

	/**
	 * Sets {@link JNotepadPP} language to english
	 */
	private final Action englishAction = new LocalizableAction("locale", fpl) {

		/**
		 * Defaultly generated serial version ID
		 */
		private static final long serialVersionUID = -6278190031704100605L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("en");
		}
	};

	/**
	 * Sets {@link JNotepadPP} language to german
	 */
	private final Action germanAction = new LocalizableAction("locale", fpl) {

		/**
		 * Defaultly generated serial version ID
		 */
		private static final long serialVersionUID = -2192575453755819752L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("de");
		}
	};

	/**
	 * Sets {@link JNotepadPP} language to croatian
	 */
	private final Action croatianAction = new LocalizableAction("locale", fpl) {

		/**
		 * Defaultly generated serial version ID
		 */
		private static final long serialVersionUID = -8632949666956604123L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("hr");
		}
	};

	/**
	 * Makes selected section of the text in upper case
	 */
	private final Action toUpperAction = new AbstractAction() {

		/**
		 * Defaultly generated serial version ID
		 */
		private static final long serialVersionUID = -6489276609958382939L;

		@Override
		public void actionPerformed(ActionEvent e) {
			manageTextCasing(c -> Character.toUpperCase(c));
		}
	};

	/**
	 * Makes selected section of the text in lower case
	 */
	private final Action toLowerAction = new AbstractAction() {

		/**
		 * Defaultly generated serial version ID
		 */
		private static final long serialVersionUID = 5568479492837973531L;

		@Override
		public void actionPerformed(ActionEvent e) {
			manageTextCasing(c -> Character.toLowerCase(c));

		}
	};

	/**
	 * Inverts casing of selected section of the text
	 */
	private final Action invertAction = new AbstractAction() {

		/**
		 * Defaultly generated serial version ID
		 */
		private static final long serialVersionUID = -6911397698919512525L;

		@Override
		public void actionPerformed(ActionEvent e) {
			manageTextCasing(c -> {
				if (Character.isLowerCase(c)) {
					return Character.toUpperCase(c);
				} else if (Character.isUpperCase(c)) {
					return Character.toLowerCase(c);
				} else {
					return c;
				}
			});
		}
	};

	/**
	 * Method manage casing based on the function given as argument
	 * 
	 * @param function function
	 */
	private void manageTextCasing(Function<Character, Character> function) {
		JTextArea textArea = multipleDocumentModel.getCurrentDocument().getTextComponent();
		int dot = textArea.getCaret().getDot();
		int mark = textArea.getCaret().getMark();
		String text = textArea.getText();
		char[] charArray = text.toCharArray();
		for (int i = Math.min(dot, mark); i < Math.max(dot, mark); i++) {
			charArray[i] = function.apply(charArray[i]);
		}
		textArea.setText(String.valueOf(charArray));
	}

	/**
	 * Sorts selected lines ascending
	 */
	private final Action ascendingAction = new AbstractAction() {

		/**
		 * Defaultly generated serial version ID
		 */
		private static final long serialVersionUID = -5598165548165510133L;

		@Override
		public void actionPerformed(ActionEvent e) {
			sortText(true);

		}
	};

	/**
	 * Sorts selected lines descending
	 */
	private final Action descendingAction = new AbstractAction() {

		/**
		 * Defaultly generated serial version ID
		 */
		private static final long serialVersionUID = -1247282962968730875L;

		@Override
		public void actionPerformed(ActionEvent e) {
			sortText(false);
		}
	};

	/**
	 * Method finds starting and ending point of text that should be sorted and then
	 * sorts the lines from starting to ending point based on the argument given. If
	 * the argument is true it sorts ascending; otherwise it sorts descending.
	 * 
	 * @param ascending true if method should sort ascending; false otherwise
	 */
	private void sortText(boolean ascending) {
		JTextArea textArea = multipleDocumentModel.getCurrentDocument().getTextComponent();
		int dot = textArea.getCaret().getDot();
		int mark = textArea.getCaret().getMark();

		char[] charArray = textArea.getText().toCharArray();

		int min = Math.min(dot, mark);
		int max = Math.max(dot, mark);
		for (; min >= 0; min--) {
			if (charArray[min] == '\n') {
				break;
			}
		}
		min++;
		for (; max < charArray.length; max++) {
			if (charArray[max] == '\n') {
				break;
			}
		}

		List<String> lines = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		for (int i = min; i < max; i++) {
			if (charArray[i] == '\n') {
				lines.add(sb.toString());
				sb = new StringBuilder();
			} else {
				sb.append(charArray[i]);
			}
		}
		lines.add(sb.toString().strip());

		Collator collator = Collator.getInstance(new Locale(LocalizationProvider.getInstance().getString("locale")));
		Collections.sort(lines, collator);
		if (!ascending) {
			lines = invertLines(lines);
		}

		sb = new StringBuilder();
		sb.append(textArea.getText().substring(0, min));
		for (String line : lines) {
			sb.append(line);
			if (!line.equals(lines.get(lines.size() - 1))) {
				sb.append("\n");
			}
		}
		sb.append(textArea.getText().substring(max, textArea.getText().length()));

		textArea.setText(sb.toString());
	}

	/**
	 * Creates inverted String list from the given list
	 * 
	 * @param lines list of strings
	 * @return inverted list of strings
	 */
	private List<String> invertLines(List<String> lines) {
		List<String> invertedLines = new ArrayList<String>();
		for (int i = lines.size() - 1; i >= 0; i--) {
			invertedLines.add(lines.get(i));
		}
		return invertedLines;
	}

	/**
	 * Removes repeating lines from the selected section of the text
	 */
	private final Action uniqueAction = new AbstractAction() {

		/**
		 * Defaultly generated serial version ID
		 */
		private static final long serialVersionUID = 3625532375254563021L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea textArea = multipleDocumentModel.getCurrentDocument().getTextComponent();
			int dot = textArea.getCaret().getDot();
			int mark = textArea.getCaret().getMark();

			char[] charArray = textArea.getText().toCharArray();

			int min = Math.min(dot, mark);
			int max = Math.max(dot, mark);
			for (; min >= 0; min--) {
				if (charArray[min] == '\n') {
					break;
				}
			}
			min++;
			for (; max < charArray.length; max++) {
				if (charArray[max] == '\n') {
					break;
				}
			}

			Set<String> lines = new LinkedHashSet<String>();
			StringBuilder sb = new StringBuilder();
			for (int i = min; i < max; i++) {
				if (charArray[i] == '\n') {
					lines.add(sb.toString());
					sb = new StringBuilder();
				} else {
					sb.append(charArray[i]);
				}
			}
			lines.add(sb.toString().strip());

			sb = new StringBuilder();
			sb.append(textArea.getText().substring(0, min));
			Iterator<String> iter = lines.iterator();
			while (iter.hasNext()) {
				sb.append(iter.next());
				if (iter.hasNext()) {
					sb.append("\n");
				}
			}
			sb.append(textArea.getText().substring(max, textArea.getText().length()));

			textArea.setText(sb.toString());
		}
	};

	/**
	 * Starts when the program is run
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}

}
