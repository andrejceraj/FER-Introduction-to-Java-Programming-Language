package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.hw17.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.Line;
import hr.fer.zemris.java.hw17.jvdraw.geometricalObjects.visitors.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw17.jvdraw.models.DrawingModel;

/**
 * Utility class for {@link JVDraw}. This class contains public methods save,
 * open and export, and other private helper methods.
 * 
 * @author Andrej Ceraj
 *
 */
public abstract class JVDrawUtil {

	/**
	 * Saves drawing model to the given path
	 * 
	 * @param drawingModel model
	 * @param path         path
	 * @throws IOException if it is unable to write to the given path
	 */
	public static void save(DrawingModel drawingModel, Path path) throws IOException {
		OutputStream os = Files.newOutputStream(path);
		for (int i = 0; i < drawingModel.getSize(); i++) {
			GeometricalObject object = drawingModel.getObject(i);
			String line = null;
			if (object instanceof Line) {
				line = lineToString((Line) object);
			} else if (object instanceof FilledCircle) {
				line = filledCircleToString((FilledCircle) object);
			} else if (object instanceof Circle) {
				line = circleToString((Circle) object);
			}
			os.write(line.getBytes());
			os.write("\n".getBytes());
		}
		os.flush();
	}

	/**
	 * Opens drawing model from the given path
	 * 
	 * @param drawingModel model
	 * @param path         path
	 * @throws IOException if it is unable to read from the given path
	 */
	public static void open(DrawingModel drawingModel, Path path) throws IOException {
		InputStream is = Files.newInputStream(path);
		String text = new String(is.readAllBytes());
		String[] lines = text.trim().split("\\n");
		drawingModel.clear();
		for (String line : lines) {
			if (line.isBlank()) {
				continue;
			}
			GeometricalObject object = parseObject(line);
			drawingModel.add(object);
		}
	}

	/**
	 * Exports drawing model painted on canvas into given .jpg, .png or .gif type
	 * file.
	 * 
	 * @param drawingModel model
	 * @param canvas       canvas
	 * @param path         path
	 * @param type         type
	 * @throws IOException if it is unable to write to the given path
	 */
	public static void export(DrawingModel drawingModel, JDrawingCanvas canvas, Path path, String type)
			throws IOException {
		GeometricalObjectBBCalculator bbCalc = new GeometricalObjectBBCalculator();

		for (int i = 0; i < drawingModel.getSize(); i++) {
			drawingModel.getObject(i).accept(bbCalc);
		}

		Rectangle bounds = bbCalc.getBoundingBox();
		int imageWidth = bounds.width > canvas.getWidth() - bounds.x ? canvas.getWidth() - bounds.x : bounds.width;
		int imageHeight = bounds.height > canvas.getHeight() - bounds.y ? canvas.getHeight() - bounds.y : bounds.height;
		BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d = (Graphics2D) image.getGraphics();
		g2d.translate(-bounds.x, -bounds.y);
		canvas.paint(g2d);
		ImageIO.write(image, type, path.toFile());

	}

	/**
	 * Creates {@link GeometricalObject} from a string
	 * 
	 * @param line string
	 * @return geometric object
	 */
	private static GeometricalObject parseObject(String line) {
		String[] words = line.split(" ");
		if (words[0].equals("LINE")) {
			return parseLine(words);
		} else if (words[0].equals("CIRCLE")) {
			return parseCircle(words);
		} else if (words[0].equals("FCIRCLE")) {
			return parseFilledCircle(words);
		}
		return null;
	}

	/**
	 * Creates {@link FilledCircle} from line words
	 * 
	 * @param words strings
	 * @return filled circle
	 */
	private static GeometricalObject parseFilledCircle(String[] words) {
		Point center = new Point(Integer.parseInt(words[1]), Integer.parseInt(words[2]));
		int radius = Integer.parseInt(words[3]);
		Color fgColor = new Color(Integer.parseInt(words[4]), Integer.parseInt(words[5]), Integer.parseInt(words[6]));
		Color bgColor = new Color(Integer.parseInt(words[7]), Integer.parseInt(words[8]), Integer.parseInt(words[9]));
		return new FilledCircle(center, radius, fgColor, bgColor);
	}

	/**
	 * Creates {@link Circle} from line words
	 * 
	 * @param words strings
	 * @return circle
	 */
	private static GeometricalObject parseCircle(String[] words) {
		Point center = new Point(Integer.parseInt(words[1]), Integer.parseInt(words[2]));
		int radius = Integer.parseInt(words[3]);
		Color fgColor = new Color(Integer.parseInt(words[4]), Integer.parseInt(words[5]), Integer.parseInt(words[6]));
		return new Circle(center, radius, fgColor);
	}

	/**
	 * Creates {@link Line} from line words
	 * 
	 * @param words strings
	 * @return line
	 */
	private static GeometricalObject parseLine(String[] words) {
		Point start = new Point(Integer.parseInt(words[1]), Integer.parseInt(words[2]));
		Point end = new Point(Integer.parseInt(words[3]), Integer.parseInt(words[4]));
		Color color = new Color(Integer.parseInt(words[5]), Integer.parseInt(words[6]), Integer.parseInt(words[7]));
		return new Line(start, end, color);
	}

	/**
	 * Converts line to string for saving it into to .jvd file
	 * 
	 * @param line line
	 * @return string representing the line
	 */
	private static String lineToString(Line line) {
		return "LINE " + pointToString(line.getStartingPoint()) + " " + pointToString(line.getEndingPoint()) + " "
				+ colorToString(line.getColor());
	}

	/**
	 * Converts circle to string for saving it into to .jvd file
	 * 
	 * @param circle circle
	 * @return string representing the circle
	 */
	private static String circleToString(Circle circle) {
		return "CIRCLE " + pointToString(circle.getCenter()) + " " + circle.getRadius() + " "
				+ colorToString(circle.getFgColor());
	}

	/**
	 * Converts filled circle to string for saving it into to .jvd file
	 * 
	 * @param filledCircle filled circle
	 * @return string representing the filled circle
	 */
	private static String filledCircleToString(FilledCircle filledCircle) {
		return "FCIRCLE " + pointToString(filledCircle.getCenter()) + " " + filledCircle.getRadius() + " "
				+ colorToString(filledCircle.getFgColor()) + " " + colorToString(filledCircle.getBgColor());

	}

	/**
	 * Converts point to string
	 * 
	 * @param p point
	 * @return string
	 */
	private static String pointToString(Point p) {
		return p.x + " " + p.y;
	}

	/**
	 * Converts color to string
	 * 
	 * @param c color
	 * @return string
	 */
	private static String colorToString(Color c) {
		return c.getRed() + " " + c.getGreen() + " " + c.getBlue();
	}

}
