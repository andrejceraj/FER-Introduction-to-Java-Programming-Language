package hr.fer.zemris.java.hw17.jvdraw.tools.impl;

import java.awt.Graphics2D;

import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.visitors.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.models.DrawingModel;

/**
 * Filled circle drawing tool
 * 
 * @author Andrej Ceraj
 *
 */
public class FilledCircleTool extends CircleTool {
	/**
	 * Circle background color provider
	 */
	private IColorProvider bgColorProvider;

	/**
	 * Constructor
	 * 
	 * @param drawingModel    model
	 * @param canvas          canvas
	 * @param foregroundColor foreground color provider
	 * @param backgroundColor background color provider
	 */
	public FilledCircleTool(DrawingModel drawingModel, JDrawingCanvas canvas, JColorArea foregroundColor,
			JColorArea backgroundColor) {
		super(drawingModel, canvas, foregroundColor);
		this.bgColorProvider = backgroundColor;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (drawing == false) {
			startingPoint = e.getPoint();
			currentPoint = e.getPoint();
			drawing = true;
		} else {
			drawing = false;
			FilledCircle filledCircle = new FilledCircle(startingPoint, calculateRadius(startingPoint, currentPoint),
					fgColorProvider.getCurrentColor(), bgColorProvider.getCurrentColor());
			drawingModel.add(filledCircle);
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
		GeometricalObject tempObject = new FilledCircle(startingPoint, calculateRadius(startingPoint, currentPoint),
				fgColorProvider.getCurrentColor(), bgColorProvider.getCurrentColor());
		tempObject.accept(new GeometricalObjectPainter(g2d));
	}

}
