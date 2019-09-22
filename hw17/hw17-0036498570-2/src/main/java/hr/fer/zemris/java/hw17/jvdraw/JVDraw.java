package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.hw17.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.components.JColorStatusBar;
import hr.fer.zemris.java.hw17.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.models.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.models.DrawingObjectListModel;
import hr.fer.zemris.java.hw17.jvdraw.models.impl.DrawingModelImpl;
import hr.fer.zemris.java.hw17.jvdraw.tools.Tool;
import hr.fer.zemris.java.hw17.jvdraw.tools.impl.CircleTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.impl.FilledCircleTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.impl.LineTool;

/**
 * Graphical user interface application for vector graphics. Application
 * supports drawing lines, circles and filled circles with desired colors.
 * Application contains list of drawn objects that can be additionally modified.
 * Application can open .jvd file with listed objects as text, and can also save
 * drawn objects into a file like that. It also supports exporting of the drawn
 * image as .jpg, .png and .gif file types
 * 
 * @author Andrej Ceraj
 *
 */
public class JVDraw extends JFrame {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = 3325805258629854408L;
	/**
	 * Drawing tool
	 */
	private Tool currentTool;
	/**
	 * Drawing model
	 */
	private DrawingModel drawingModel;
	/**
	 * Drawing canvas
	 */
	private JDrawingCanvas canvas;
	/**
	 * Foreground color chooser/provider
	 */
	private JColorArea foregroundColor;
	/**
	 * Background color chooser/provider
	 */
	private JColorArea backgroundColor;
	/**
	 * Current drawing model path
	 */
	private Path currentPath;

	/**
	 * Constructor
	 */
	public JVDraw() {
		foregroundColor = new JColorArea(Color.RED);
		backgroundColor = new JColorArea(Color.BLUE);
		drawingModel = new DrawingModelImpl();
		canvas = new JDrawingCanvas(currentTool, drawingModel);
		drawingModel.addDrawingModelListener(canvas);
		initGUI();

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				exitAction.actionPerformed(null);
			}
		});
	}

	/**
	 * Initializes graphical user interface
	 */
	private void initGUI() {
		setSize(1200, 700);
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		createActions();
		createMenus();
		cp.add(createToolbar(foregroundColor, backgroundColor), BorderLayout.NORTH);
		cp.add(new JColorStatusBar(foregroundColor, backgroundColor), BorderLayout.SOUTH);

		cp.add(canvas, BorderLayout.CENTER);
		cp.add(createList(), BorderLayout.EAST);
	}

	/**
	 * Creates {@link JList} connected to the {@link DrawingModel} showing the
	 * model's content and enabling its edition.
	 * 
	 * @return scroll pane
	 */
	private Component createList() {
		DrawingObjectListModel list = new DrawingObjectListModel(drawingModel);
		drawingModel.addDrawingModelListener(list);
		JList<GeometricalObject> jList = new JList<GeometricalObject>(list);
		jList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				@SuppressWarnings("unchecked")
				JList<GeometricalObject> l = (JList<GeometricalObject>) e.getSource();
				if (e.getClickCount() == 2) {
					GeometricalObject clickedObject = list.getElementAt(l.locationToIndex(e.getPoint()));
					GeometricalObjectEditor editor = clickedObject.createGeometricalObjectEditor();
					while (true) {
						if (JOptionPane.showConfirmDialog(editor, editor, "Edit",
								JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
							try {
								editor.checkEditing();
								editor.acceptEditing();
								break;
							} catch (Exception ex) {
								JOptionPane.showMessageDialog(null, "Input values are invalid, please try again");
							}
						} else {
							break;
						}
					}
				}
			}
		});
		jList.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					int index = jList.getSelectedIndex();

					drawingModel.remove(drawingModel.getObject(index));
				} else if (e.getKeyCode() == KeyEvent.VK_PLUS) {
					int index = jList.getSelectedIndex();
					if (index == -1) {
						return;
					}
					drawingModel.changeOrder(drawingModel.getObject(index), index - 1);
					jList.setSelectedIndex(index - 1);
				} else if (e.getKeyCode() == KeyEvent.VK_MINUS) {
					int index = jList.getSelectedIndex();
					if (index == -1) {
						return;
					}
					drawingModel.changeOrder(drawingModel.getObject(index), index + 1);
					jList.setSelectedIndex(index + 1);
				}
			}
		});
		return new JScrollPane(jList);
	}

	/**
	 * Creates menus and its items, and attaches it to the {@link JVDraw}
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");
		file.add(saveAction);
		file.add(saveAsAction);
		file.add(openAction);
		file.add(exportAction);

		menuBar.add(file);
		setJMenuBar(menuBar);
	}

	/**
	 * Creates {@link JVDraw} toolbar containing foreground and background chooser,
	 * and drawing tool.
	 * 
	 * @param foregroundColor foreground color chooser
	 * @param backgroundColor background color chooser
	 * @return toolbar
	 */
	private Component createToolbar(JColorArea foregroundColor, JColorArea backgroundColor) {
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(true);
		toolbar.add(foregroundColor);
		toolbar.addSeparator();
		toolbar.add(backgroundColor);
		toolbar.addSeparator();

		ButtonGroup buttonGroup = new ButtonGroup();

		JToggleButton line = new JToggleButton(setLineTool);
		JToggleButton circle = new JToggleButton(setCircleTool);
		JToggleButton filledCcircle = new JToggleButton(setFilledCircleTool);
		buttonGroup.add(line);
		buttonGroup.add(circle);
		buttonGroup.add(filledCcircle);
		toolbar.add(line);
		toolbar.add(circle);
		toolbar.add(filledCcircle);

		return toolbar;
	}

	/**
	 * Associates actions with their names, shortcuts and descriptions.
	 */
	private void createActions() {
		setLineTool.putValue(Action.NAME, "Line");
		setCircleTool.putValue(Action.NAME, "Circle");
		setFilledCircleTool.putValue(Action.NAME, "Filled Circle");

		saveAction.putValue(Action.NAME, "Save");
		saveAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveAction.putValue(Action.SHORT_DESCRIPTION, "Saves current document");

		saveAsAction.putValue(Action.NAME, "Save As");
		saveAsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift S"));
		saveAsAction.putValue(Action.SHORT_DESCRIPTION, "Saves current document as...");

		openAction.putValue(Action.NAME, "Open");
		openAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openAction.putValue(Action.SHORT_DESCRIPTION, "Opens selected document");

		exportAction.putValue(Action.NAME, "Export");
		exportAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		exportAction.putValue(Action.SHORT_DESCRIPTION, "Exports drawing into picture");
	}

	/**
	 * Action that sets current tool to {@link LineTool}.
	 */
	private final Action setLineTool = new AbstractAction() {
		/**
		 * Defaultly generated serial version ID
		 */
		private static final long serialVersionUID = 9152644422233607283L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (currentTool instanceof LineTool) {
				return;
			}
			currentTool = new LineTool(drawingModel, canvas, foregroundColor);
			canvas.setTool(currentTool);
		}
	};

	/**
	 * Action that sets current tool to {@link CircleTool}.
	 */
	private final Action setCircleTool = new AbstractAction() {
		/**
		 * Defaultly generated serial version ID
		 */
		private static final long serialVersionUID = 8423451021877667503L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (currentTool instanceof CircleTool && !(currentTool instanceof FilledCircleTool)) {
				return;
			}
			currentTool = new CircleTool(drawingModel, canvas, foregroundColor);
			canvas.setTool(currentTool);
		}
	};

	/**
	 * Action that sets current tool to {@link FilledCircleTool}.
	 */
	private final Action setFilledCircleTool = new AbstractAction() {
		/**
		 * Defaultly generated serial version ID
		 */
		private static final long serialVersionUID = 3715382565443345795L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (currentTool instanceof FilledCircleTool) {
				return;
			}
			currentTool = new FilledCircleTool(drawingModel, canvas, foregroundColor, backgroundColor);
			canvas.setTool(currentTool);
		}
	};

	/**
	 * Action that saves current drawing model into current path. If current path is
	 * null, then {@link SaveAsAction} is called.
	 */
	private final Action saveAction = new AbstractAction() {
		/**
		 * Defaultly generated serial version ID
		 */
		private static final long serialVersionUID = -83481833466420513L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (currentPath != null) {
				try {
					JVDrawUtil.save(drawingModel, currentPath);
					drawingModel.clearModifiedFlag();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Could not save file", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				saveAsAction.actionPerformed(e);
				drawingModel.clearModifiedFlag();
			}

		}
	};

	/**
	 * Action that saves current drawing model into path selected by
	 * {@link JFileChooser}.
	 */
	private final Action saveAsAction = new AbstractAction() {
		/**
		 * Defaultly generated serial version ID
		 */
		private static final long serialVersionUID = -5646512628170369692L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Save As");

			if (fileChooser.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}

			Path filePath = fileChooser.getSelectedFile().toPath();
			if (!filePath.toString().endsWith(".jvd")) {
				JOptionPane.showMessageDialog(null, "Invalid file extension", "Error", JOptionPane.ERROR_MESSAGE);
			}
			if (Files.exists(filePath)) {
				if (JOptionPane.showConfirmDialog(null, "File already exist, do you want to overwrite?", "Overwrite?",
						JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
					return;
				}
			}
			try {
				JVDrawUtil.save(drawingModel, filePath);
				drawingModel.clearModifiedFlag();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Could not save file", "Error", JOptionPane.ERROR_MESSAGE);
			}
			currentPath = filePath;
		}
	};

	/**
	 * Action that opens file chosen by {@link JFileChooser} and gets drawing model
	 * from it.
	 */
	private final Action openAction = new AbstractAction() {
		/**
		 * Defaultly generated serial version ID
		 */
		private static final long serialVersionUID = -7262011589990823939L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Open");

			if (fileChooser.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}

			Path filePath = fileChooser.getSelectedFile().toPath();
			if (!filePath.toString().endsWith(".jvd")) {
				JOptionPane.showMessageDialog(null, "Invalid file extension", "Error", JOptionPane.ERROR_MESSAGE);
			}
			try {
				JVDrawUtil.open(drawingModel, filePath);
				currentPath = filePath;
				drawingModel.clearModifiedFlag();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Could not open file", "Error", JOptionPane.ERROR_MESSAGE);
			}
			repaint();
		}
	};

	/**
	 * Action that exports image drawn on drawing canvas to either .jpg, .png or
	 * .gif file type, whatever the user selects.
	 */
	private final Action exportAction = new AbstractAction() {
		/**
		 * Defaultly generated serial version ID
		 */
		private static final long serialVersionUID = 2346681008402985791L;

		@Override
		public void actionPerformed(ActionEvent e) {
			String type = null;
			ExportPanel exportPanel = new ExportPanel();
			if (JOptionPane.showConfirmDialog(exportPanel, exportPanel, "Export type",
					JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
				try {
					type = exportPanel.getExportType();
				} catch (Exception ex) {
					exportAction.actionPerformed(e);
				}
			}

			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Export *." + type);
			if (fileChooser.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}

			Path filePath = fileChooser.getSelectedFile().toPath();
			String pathString = filePath.toString();
			if (!filePath.toString().endsWith("." + type)) {
				pathString += "." + type;
			}
			filePath = Paths.get(pathString);
			if (Files.exists(filePath)) {
				if (JOptionPane.showConfirmDialog(null, "File already exist, do you want to overwrite?", "Overwrite?",
						JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
					return;
				}
			}
			try {
				JVDrawUtil.export(drawingModel, canvas, filePath, type);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Could not export file", "Error", JOptionPane.ERROR_MESSAGE);
			}
			currentPath = filePath;
		}
	};

	/**
	 * Action that is called when user wants to exit the application. If the
	 * document has been modified since last saving, the user is asked if it would
	 * like to save current modifications.
	 */
	private final Action exitAction = new AbstractAction() {
		/**
		 * Defaultly generated serial version ID
		 */
		private static final long serialVersionUID = 70178771341626582L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (drawingModel.isModified()) {
				switch (JOptionPane.showConfirmDialog(JVDraw.this, "Do you want to save files before exiting?")) {
				case JOptionPane.YES_OPTION:
					saveAction.actionPerformed(e);
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
	 * Method called when the program is started.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JVDraw().setVisible(true);
		});
	}

	/**
	 * Panel that pops up when the user is expected to select export type
	 * 
	 * @author Andrej Ceraj
	 *
	 */
	private class ExportPanel extends JPanel {
		/**
		 * Defaultly generated serial version ID
		 */
		private static final long serialVersionUID = 4756537681443542919L;
		/**
		 * Combo box containing export types
		 */
		private JComboBox<String> comboBox;

		/**
		 * Constructor
		 */
		public ExportPanel() {
			setLayout(new FlowLayout());
			add(new JLabel("Select export format"));
			comboBox = new JComboBox<String>();
			comboBox.addItem("jpg");
			comboBox.addItem("png");
			comboBox.addItem("gif");
			add(comboBox);
		}

		/**
		 * @return selected export type
		 */
		public String getExportType() {
			return (String) comboBox.getSelectedItem();
		}
	}

}
