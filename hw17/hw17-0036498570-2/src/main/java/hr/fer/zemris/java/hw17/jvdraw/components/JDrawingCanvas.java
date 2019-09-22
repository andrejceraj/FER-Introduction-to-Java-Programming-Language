package hr.fer.zemris.java.hw17.jvdraw.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.visitors.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.visitors.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw17.jvdraw.models.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.tools.Tool;

/**
 * Component that draws all {@link GeometricalObject}s from {@link DrawingModel}
 * and on user's action it uses {@link Tool} to draw new objects which are then
 * also added to {@code DrawingModel}.
 * 
 * @author Andrej Ceraj
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = -203733367390631676L;
	/**
	 * Drawing tool
	 */
	private Tool tool;
	/**
	 * Drawing model
	 */
	private DrawingModel drawingModel;
	/**
	 * Painter
	 */
	private GeometricalObjectVisitor paintVisitor;

	/**
	 * Constructor
	 * 
	 * @param tool         drawing tool
	 * @param drawingModel document model
	 */
	public JDrawingCanvas(Tool tool, DrawingModel drawingModel) {
		this.tool = tool;
		this.drawingModel = drawingModel;
		addMouseListeners();
		setIgnoreRepaint(false);
	}

	/**
	 * Adds {@link MouseListener} and {@link MouseMotionListener} needed for
	 * dynamically drawing of {@link GeometricalObject}s.
	 */
	private void addMouseListeners() {
		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				if (tool != null) {
					tool.mouseReleased(e);
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (tool != null) {
					tool.mousePressed(e);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (tool != null) {
					tool.mouseClicked(e);
				}
			}
		});
		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {

				if (tool != null) {
					tool.mouseMoved(e);
				}
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				if (tool != null) {
					tool.mouseDragged(e);
				}
			}
		});
	}

	/**
	 * Sets drawing tool.
	 * 
	 * @param tool drawing tool
	 */
	public void setTool(Tool tool) {
		this.tool = tool;
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		paintVisitor = new GeometricalObjectPainter(g2d);

		for (int i = 0; i < drawingModel.getSize(); i++) {
			GeometricalObject object = drawingModel.getObject(i);
			object.accept(paintVisitor);
		}
		if (tool != null) {
			tool.paint(g2d);
		}
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
	}

}
