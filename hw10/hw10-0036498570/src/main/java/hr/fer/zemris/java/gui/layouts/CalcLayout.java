package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.Rectangle;

/**
 * Representation of layout used in simple calculator built in this homework
 * 
 * @author Andrej Ceraj
 *
 */
public class CalcLayout implements LayoutManager2 {
	/**
	 * Number of rows in layout
	 */
	private static final int ROWS = 5;
	/**
	 * Number of columns in layout
	 */
	private static final int COLUMNS = 7;

	/**
	 * Space between adjacent components
	 */
	private int space;

	/**
	 * Containing components
	 */
	Component[][] components;

	/**
	 * Creates an instance of {@link CalcLayout}
	 */
	public CalcLayout() {
		this(0);
	}

	/**
	 * Creates an instance of {@link CalcLayout} with the given space between
	 * components
	 * 
	 * @param space
	 */
	public CalcLayout(int space) {
		this.space = space;
		components = new Component[ROWS][COLUMNS];
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		RCPosition position = parseConstraint(constraints);
		if (!checkPositionValidity(position)) {
			throw new CalcLayoutException("Invalid component position");
		}
		if (components[position.getRow() - 1][position.getColumn() - 1] != null) {
			throw new CalcLayoutException("Position already occupied");
		}
		components[position.getRow() - 1][position.getColumn() - 1] = comp;
	}

	/**
	 * Checks if the given position is available for adding a component in it
	 * 
	 * @param position
	 * @return true if it is; false otherwise
	 */
	private static boolean checkPositionValidity(RCPosition position) {
		if (position.getRow() < 1 || position.getRow() > ROWS) {
			return false;
		}
		if (position.getColumn() < 1 || position.getColumn() > COLUMNS) {
			return false;
		}
		if (position.getRow() == 1 && (position.getColumn() >= 2 && position.getColumn() <= 5)) {
			return false;
		}
		return true;
	}

	/**
	 * Parses given object into {@link RCPosition}
	 * 
	 * @param constraint object
	 * @return RCPosition
	 */
	private static RCPosition parseConstraint(Object constraint) {
		if (constraint instanceof RCPosition) {
			return (RCPosition) constraint;
		} else {
			String[] parts = ((String) constraint).replaceAll("\\s+", "").split(",");
			return new RCPosition(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
		}
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				if (components[i][j] == (comp)) {
					components[i][j] = null;
				}
			}
		}
	}

	@Override
	public void layoutContainer(Container parent) {
		Dimension parentSize = parent.getSize();
		Insets insets = parent.getInsets();
		int parentWidth = parentSize.width - insets.left - insets.right;
		int parentHeight = parentSize.height - insets.top - insets.bottom;
		double componentWidth = (double) (parentWidth - (COLUMNS - 1) * space) / (double) COLUMNS;
		double componentHeight = (double) (parentHeight - (ROWS - 1) * space) / (double) ROWS;

		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				if (components[i][j] == null) {
					continue;
				}
				double x = insets.left + j * (componentWidth + space);
				double y = insets.top + i * (componentHeight + space);

				Rectangle rectangle = null;
				if (i == 0 && j == 0) {
					rectangle = new Rectangle((int) Math.round(x), (int) Math.round(y),
							(int) Math.round(componentWidth * 5 + space * 4), (int) Math.round(componentHeight));

				} else {
					rectangle = new Rectangle((int) Math.round(x), (int) Math.round(y),
							(int) Math.round(componentWidth), (int) Math.round(componentHeight));
				}
				components[i][j].setBounds(rectangle);
			}
		}

	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return layoutSize(parent, "pref");
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return layoutSize(parent, "min");
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return layoutSize(target, "max");
	}

	/**
	 * Calculates layout size based on the given container and option
	 * 
	 * @param comp   container
	 * @param option "max", "min", or "pref"
	 * @return layout size
	 */
	private Dimension layoutSize(Container comp, String option) {
		double maxComponentHeight = 0;
		double maxComponentWidth = 0;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				if (components[i][j] == null) {
					continue;
				}
				Dimension componentSize = null;
				switch (option) {
				case "pref":
					componentSize = components[i][j].getPreferredSize();
					break;
				case "min":
					componentSize = components[i][j].getMinimumSize();
					break;
				case "max":
					componentSize = components[i][j].getMaximumSize();
					break;
				}
				if (componentSize == null) {
					continue;
				}

				if (i == 0 && j == 0) {
					double normalizedSize = (componentSize.getWidth() - 4.0 * space) / 5;
					maxComponentWidth = maxComponentWidth > normalizedSize ? maxComponentWidth : normalizedSize;
					j = 4;
				} else {
					maxComponentWidth = maxComponentWidth > componentSize.getWidth() ? maxComponentWidth
							: componentSize.getWidth();
				}

				maxComponentHeight = maxComponentHeight > componentSize.getHeight() ? maxComponentHeight
						: componentSize.getHeight();
			}
		}

		Insets insets = comp.getInsets();
		int layoutWidth = COLUMNS * (int) maxComponentWidth + space * (COLUMNS - 1) + insets.left + insets.right;
		int layoutHeight = ROWS * (int) maxComponentHeight + space * (ROWS - 1) + insets.top + insets.bottom;
		return new Dimension(layoutWidth, layoutHeight);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}
}