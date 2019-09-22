package coloring.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import marcupic.opjj.statespace.coloring.Picture;

/**
 * Class for coloring a picture.
 * 
 * @author Andrej Ceraj
 *
 */
public class Coloring implements Consumer<Pixel>, Function<Pixel, List<Pixel>>, Predicate<Pixel>, Supplier<Pixel> {
	/**
	 * Pixel the user clicked on
	 */
	private Pixel reference;
	/**
	 * Picture to color
	 */
	private Picture picture;
	/**
	 * New color
	 */
	private int fillColor;
	/**
	 * Color of the pixel the user clicked on
	 */
	private int refColor;

	/**
	 * Creates an instance of {@code Coloring} with the given reference
	 * {@link Pixel}, picture and new color
	 * 
	 * @param reference
	 * @param picture
	 * @param fillColor
	 */
	public Coloring(Pixel reference, Picture picture, int fillColor) {
		this.reference = reference;
		this.picture = picture;
		this.fillColor = fillColor;
		refColor = picture.getPixelColor(reference.getX(), reference.getY());
	}

	@Override
	public void accept(Pixel state) {
		picture.setPixelColor(state.getX(), state.getY(), fillColor);
	}

	@Override
	public List<Pixel> apply(Pixel state) {
		List<Pixel> states = new ArrayList<Pixel>();
		if (state.getX() > 0) {
			states.add(new Pixel(state.getX() - 1, state.getY()));
		}
		if (state.getY() > 0) {
			states.add(new Pixel(state.getX(), state.getY() - 1));
		}
		if (state.getX() < picture.getWidth() - 1) {
			states.add(new Pixel(state.getX() + 1, state.getY()));
		}
		if (state.getX() < picture.getHeight() - 1) {
			states.add(new Pixel(state.getX(), state.getY() + 1));
		}
		return states;
	}

	@Override
	public boolean test(Pixel state) {
		if (picture.getPixelColor(state.getX(), state.getY()) == refColor) {
			return true;
		}
		return false;
	}

	@Override
	public Pixel get() {
		return reference;
	}

}
