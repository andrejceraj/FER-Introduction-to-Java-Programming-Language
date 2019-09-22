package hr.fer.zemris.java.hw17.jvdraw.tools.impl;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.Line;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.visitors.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.models.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.tools.Tool;

/**
 * Line drawing tool
 * 
 * @author Andrej Ceraj
 *
 */
public class LineTool implements Tool {
	/**
	 * Flag indicating if the tool is currently drawing
	 */
	private boolean drawing = false;
	/**
	 * First-click location
	 */
	private Point startingPoint;
	/**
	 * Current mouse location
	 */
	private Point currentPoint;
	/**
	 * Model to which new drawn {@link GeometricalObject} should be added
	 */
	private DrawingModel drawingModel;
	/**
	 * Canvas to which this tool should draw.
	 */
	private JDrawingCanvas canvas;
	/**
	 * Line color provider
	 */
	private IColorProvider colorProvider;

	/**
	 * Constructor
	 * 
	 * @param drawingModel  model
	 * @param canvas        canvas
	 * @param colorProvider color provide
	 */
	public LineTool(DrawingModel drawingModel, JDrawingCanvas canvas, IColorProvider colorProvider) {
		this.drawingModel = drawingModel;
		this.canvas = canvas;
		this.colorProvider = colorProvider;
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (drawing == false) {
			startingPoint = e.getPoint();
			currentPoint = e.getPoint();
			drawing = true;
		} else {
			drawing = false;
			Line line = new Line(startingPoint, currentPoint, colorProvider.getCurrentColor());
			drawingModel.add(line);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (!drawing) {
			return;
		}
		currentPoint = e.getPoint();
		canvas.repaint();
		paint((Graphics2D) canvas.getGraphics());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (!drawing) {
			return;
		}
	}

	@Override
	public void paint(Graphics2D g2d) {
		if (startingPoint == null || currentPoint == null) {
			return;
		}
		if (!drawing) {
			return;
		}
		GeometricalObject tempObject = new Line(startingPoint, currentPoint, colorProvider.getCurrentColor());
		tempObject.accept(new GeometricalObjectPainter(g2d));
	}

}
