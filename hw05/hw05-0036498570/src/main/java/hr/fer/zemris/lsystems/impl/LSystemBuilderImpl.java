package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

/**
 * Implementation of {@code LSystemBuilder}. The implementation is used to set
 * all the parameters of the LSystem and when done, the method {@code build()}
 * should be called which returns an instance of {@code LSystem} with all the
 * given attributes.
 * 
 * @author Andrej Ceraj
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {
	private static final double DEFAULT_UNIT_LENGTH = 0.1;
	private static final double DEFAULT_UNIT_LENGTH_DEGREE_SCALER = 1;
	private static final Vector2D DEFAULT_ORIGIN = new Vector2D(0, 0);
	private static final double DEFAULT_ANGLE = 0;
	private static final String DEFAULT_AXIOM = "";
	/**
	 * Character - production dictionary.
	 */
	private Dictionary<Character, String> productions;
	/**
	 * Character - action dictionary.
	 */
	private Dictionary<Character, String> actions;

	/**
	 * Length on one turtle step.
	 */
	private double unitLength = DEFAULT_UNIT_LENGTH;
	/**
	 * Factor by which {@code unitLength} is scaled when increasing the
	 * {@code LSystem} depth.
	 */
	private double unitLengthDegreeScaler = DEFAULT_UNIT_LENGTH_DEGREE_SCALER;
	/**
	 * Starting turtle position.
	 */
	private Vector2D origin = DEFAULT_ORIGIN;
	/**
	 * Starting turtle orientation.
	 */
	private double angle = DEFAULT_ANGLE;
	/**
	 * String in which each character represents a single command stored in
	 * {@code actions} dictionary.
	 */
	private String axiom = DEFAULT_AXIOM;

	/**
	 * Creates an instance of {@code LSystemBuilderImpl}.
	 */
	public LSystemBuilderImpl() {
		super();
		productions = new Dictionary<Character, String>();
		actions = new Dictionary<Character, String>();
	}

	/**
	 * Creates {@code LSystem} with stored attributes.
	 */
	@Override
	public LSystem build() {
		return new CustomLSystem();
	}

	/**
	 * Parses the given data and stores the attributes.
	 * 
	 * @param data Strings containing attributes
	 */
	@Override
	public LSystemBuilder configureFromText(String[] data) {
		for (String line : data) {
			parseLine(line);
		}
		return this;
	}

	/**
	 * Associates the given character with the given command.
	 * 
	 * @param key   character
	 * @param value command
	 */
	@Override
	public LSystemBuilder registerCommand(char key, String value) {
		actions.put(key, value);
		return this;
	}

	/**
	 * Associates the given character with the given production.
	 * 
	 * @param key   character
	 * @param value production
	 */
	@Override
	public LSystemBuilder registerProduction(char key, String value) {
		productions.put(key, value);
		return this;
	}

	/**
	 * Sets turtle orientation to the given angle.
	 * 
	 * @param angle
	 */
	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		return this;
	}

	/**
	 * Sets starting axiom to the given string.
	 * 
	 * @param axiom
	 */
	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}

	/**
	 * Sets starting turtle position to the given position.
	 * 
	 * @throws IllegalArgumentException Exception is thrown if either of the given
	 *                                  coordinates are less than 0.
	 * @param x coordinate
	 * @param y coordinate
	 */
	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		if (x < 0 || y < 0) {
			throw new IllegalArgumentException();
		}
		origin = new Vector2D(x, y);
		return this;
	}

	/**
	 * Sets the length of one turtle step to the given value.
	 * 
	 * @throws IllegalArgumentException Exception is thrown if the given value is
	 *                                  less than 0.
	 * @param unitLength New length
	 */
	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		if (unitLength < 0) {
			throw new IllegalArgumentException();
		}
		this.unitLength = unitLength;
		return this;
	}

	/**
	 * Sets the degree scaling factor to the given value.
	 * 
	 * @throws IllegalArgumentException Exception is thrown if the given value is
	 *                                  less than 0.
	 * @param unitLength New scaling factor
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		if (unitLengthDegreeScaler < 0) {
			throw new IllegalArgumentException();
		}
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}

	/**
	 * Parses the line and stores the extracted attributes.
	 * 
	 * @throws IllegalArgumentException Exception is thrown if the keyword is not
	 *                                  recognized.
	 * @param line
	 */
	private void parseLine(String line) {
		String[] words = line.split("\\s+");
		switch (words[0]) {
		case "origin":
			parseOrigin(words);
			break;
		case "angle":
			parseAngle(words);
			break;
		case "unitLength":
			parseUnitLength(words);
			break;
		case "unitLengthDegreeScaler":
			parseUnitLengthDegreeScaler(words);
			break;
		case "command":
			parseCommand(words);
			break;
		case "axiom":
			parseAxiom(words);
			break;
		case "production":
			parseProduction(words);
			break;
		case "":
			break;
		default:
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Extracts origin from the given line separated into words
	 * 
	 * @throws IllegalArgumentException Exception is thrown if the line is invalid
	 *                                  format.
	 * @param words
	 */
	private void parseOrigin(String[] words) {
		if (words.length != 3) {
			throw new IllegalArgumentException();
		}
		setOrigin(Double.parseDouble(words[1]), Double.parseDouble(words[2]));
	}

	/**
	 * Extracts angle from the given line separated into words
	 * 
	 * @throws IllegalArgumentException Exception is thrown if the line is invalid
	 *                                  format.
	 * @param words
	 */
	private void parseAngle(String[] words) {
		if (words.length != 2) {
			throw new IllegalArgumentException();
		}
		setAngle(Double.parseDouble(words[1]));
	}

	/**
	 * Extracts {@code unitLength} from the given line separated into words
	 * 
	 * @throws IllegalArgumentException Exception is thrown if the line is invalid
	 *                                  format.
	 * @param words
	 */
	private void parseUnitLength(String[] words) {
		if (words.length != 2) {
			throw new IllegalArgumentException();
		}
		setUnitLength(Double.parseDouble(words[1]));
	}

	/**
	 * Extracts {@code unitLengthDegreeScaler} from the given line separated into
	 * words
	 * 
	 * @throws IllegalArgumentException Exception is thrown if the line is invalid
	 *                                  format.
	 * @param words
	 */
	private void parseUnitLengthDegreeScaler(String[] words) {
		if (words.length == 2) {
			setUnitLengthDegreeScaler(Double.parseDouble(words[1]));
			return;
		}
		String devidedNumber = "";
		for (int i = 1; i < words.length; i++) {
			devidedNumber += words[i];
		}
		int indexOfDevide = devidedNumber.indexOf('/');
		setUnitLengthDegreeScaler(Double.parseDouble(devidedNumber.substring(0, indexOfDevide))
				/ Double.parseDouble(devidedNumber.substring(indexOfDevide + 1, devidedNumber.length())));
	}

	/**
	 * Extracts command associated with a character from the given line separated
	 * into words
	 * 
	 * @throws IllegalArgumentException Exception is thrown if the line is invalid
	 *                                  format.
	 * @param words
	 */
	private void parseCommand(String[] words) {
		if (words.length != 3 && words.length != 4) {
			throw new IllegalArgumentException();
		}
		if (words[1].length() != 1) {
			throw new IllegalArgumentException();
		}
		Character key = words[1].charAt(0);
		String value = words[2] + (words.length == 4 ? " " + words[3] : "");
		registerCommand(key, value);
	}

	/**
	 * Extracts axiom from the given line separated into words
	 * 
	 * @throws IllegalArgumentException Exception is thrown if the line is invalid
	 *                                  format.
	 * @param words
	 */
	private void parseAxiom(String[] words) {
		if (words.length != 2) {
			throw new IllegalArgumentException();
		}
		setAxiom(words[1]);
	}

	/**
	 * Extracts production associated with a character from the given line separated
	 * into words
	 * 
	 * @throws IllegalArgumentException Exception is thrown if the line is invalid
	 *                                  format.
	 * @param words
	 */
	private void parseProduction(String[] words) {
		if (words.length != 3) {
			throw new IllegalArgumentException();
		}
		if (words[1].length() != 1) {
			throw new IllegalArgumentException();
		}
		Character key = words[1].charAt(0);
		String value = words[2];
		registerProduction(key, value);
	}

	/**
	 * @return Vector with length equals to 1 and orientation equals to the stored
	 *         angle.
	 */
	private Vector2D singleLengthVectorFromAngle() {
		return new Vector2D(Math.cos(Math.toRadians(angle)), Math.sin(Math.toRadians(angle)));
	}

	/**
	 * Representation of LSystem.
	 * 
	 * @author Andrej Ceraj
	 *
	 */
	class CustomLSystem implements LSystem {

		/**
		 * Draws a fractal with the given depth on the screen.
		 * 
		 * @param depth
		 * @param painter
		 */
		@Override
		public void draw(int depth, Painter painter) {
			Context context = new Context();
			TurtleState state = new TurtleState(origin, singleLengthVectorFromAngle(), Color.black,
					unitLength * Math.pow(unitLengthDegreeScaler, depth));
			context.pushState(state);

			String string = generate(depth);
			for (char c : string.toCharArray()) {
				String actionString = actions.get(c);
				if (actionString != null) {
					doAction(context, painter, actionString);
				}
			}
		}

		/**
		 * Generates string as list of commands from the axiom, productions dictionary
		 * and depth.
		 * 
		 * @param depth
		 */
		@Override
		public String generate(int depth) {
			StringBuilder builder = new StringBuilder(axiom);
			for (int i = 0; i < depth; i++) {
				String tempString = builder.toString();
				builder = new StringBuilder();
				for (char c : tempString.toCharArray()) {
					String productionString = productions.get(c);
					if (productionString != null) {
						builder.append(productionString);
					} else {
						builder.append(c);
					}
				}
			}
			return builder.toString();
		}

		/**
		 * Does a certain action based on the command string.
		 * 
		 * @throws IllegalArgumentException Exception is thrown if the command string is
		 *                                  not recognized.
		 * @throws NumberFormatException    Exception is thrown if certain part of the
		 *                                  string is not parsable into double.
		 * @param context
		 * @param painter
		 * @param command
		 */
		private void doAction(Context context, Painter painter, String command)
				throws IllegalArgumentException, NumberFormatException {
			String[] splitCommand = command.split(" ");
			switch (splitCommand[0]) {
			case "draw":
				double drawStep = Double.parseDouble(splitCommand[1]);
				Command drawCommand = new DrawCommand(drawStep);
				drawCommand.execute(context, painter);
				break;
			case "skip":
				double skipStep = Double.parseDouble(splitCommand[1]);
				Command skipCommand = new SkipCommand(skipStep);
				skipCommand.execute(context, painter);
				break;
			case "scale":
				double scale = Double.parseDouble(splitCommand[1]);
				Command scaleCommand = new ScaleCommand(scale);
				scaleCommand.execute(context, painter);
				break;
			case "rotate":
				double angle = Double.parseDouble(splitCommand[1]);
				Command rotateCommand = new RotateCommand(angle);
				rotateCommand.execute(context, painter);
				break;
			case "push":
				Command pushCommand = new PushCommand();
				pushCommand.execute(context, painter);
				break;
			case "pop":
				Command popCommand = new PopCommand();
				popCommand.execute(context, painter);
				break;
			case "color":
				Color newColor = new Color(Integer.valueOf(splitCommand[1].substring(0, 2), 16),
						Integer.valueOf(splitCommand[1].substring(2, 4), 16),
						Integer.valueOf(splitCommand[1].substring(4, 6), 16));
				Command colorCommand = new ColorCommand(newColor);
				colorCommand.execute(context, painter);
				break;
			default:
				throw new IllegalArgumentException();
			}
		}
	}

}
