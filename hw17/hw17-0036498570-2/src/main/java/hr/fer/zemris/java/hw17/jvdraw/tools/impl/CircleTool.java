package hr.fer.zemris.java.hw17.jvdraw.tools.impl;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.visitors.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.models.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.tools.Tool;

/**
 * Circle drawing tool
 * 
 * @author Andrej Ceraj
 *
 */
public class CircleTool implements Tool {
	/**
	 * Flag indicating if the tool is currently drawing
	 */
	protected boolean drawing = false;
	/**
	 * First-click location
	 */
	protected Point startingPoint;
	/**
	 * Current mouse location
	 */
	protected Point currentPoint;
	/**
	 * Model to which new drawn {@link GeometricalObject} should be added
	 */
	protected DrawingModel drawingModel;
	/**
	 * Canvas to which this tool should draw.
	 */
	protected JDrawingCanvas canvas;
	/**
	 * Circle foreground color provider
	 */
	protected IColorProvider fgColorProvider;

	/**
	 * Constructor
	 * 
	 * @param drawingModel  model
	 * @param canvas        canvas
	 * @param colorProvider foreground color provider
	 */
	public CircleTool(DrawingModel drawingModel, JDrawingCanvas canvas, JColorArea colorProvider) {
		this.drawingModel = drawingModel;
		this.canvas = canvas;
		this.fgColorProvider = colorProvider;
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
			Circle circle = new Circle(startingPoint, calculateRadius(startingPoint, currentPoint),
					fgColorProvider.getCurrentColor());
			drawingModel.add(circle);
		}
	}

	/**
	 * @param point1
	 * @param point2
	 * @return
	 */
	protected int calculateRadius(Point point1, Point point2) {
		return (int) Math.round(point1.distance(point2));
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
		g2d.setColor(fgColorProvider.getCurrentColor());
		GeometricalObject tempObject = new Circle(startingPoint, calculateRadius(startingPoint, currentPoint),
				fgColorProvider.getCurrentColor());
		tempObject.accept(new GeometricalObjectPainter(g2d));
	}
}
