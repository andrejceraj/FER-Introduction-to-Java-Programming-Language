package coloring.algorithms;

import java.util.Objects;

/**
 * Representation of a pixel in a picture.
 * 
 * @author Andrej Ceraj
 *
 */
public class Pixel {
	/**
	 * x coorditane
	 */
	private int x;
	/**
	 * y coordinate
	 */
	private int y;

	/**
	 * Creates an instance of {@code Pixel} with the given coordinates
	 * 
	 * @param x coordinate
	 * @param y coordinate
	 */
	public Pixel(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Pixel))
			return false;
		Pixel other = (Pixel) obj;
		return x == other.x && y == other.y;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	/**
	 * @return x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return y coordinate
	 */
	public int getY() {
		return y;
	}
}
