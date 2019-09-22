package hr.fer.zemris.java.gui.charts;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Program demonstrating {@link BarChartComponent}
 * 
 * @author Andrej Ceraj
 *
 */
public class BarChartDemo extends JFrame {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = 5151434034409506855L;

	/**
	 * Method starts when the program is run
	 * 
	 * @param args path to bar chart information
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			throw new IllegalArgumentException("1 argument must be provided.");
		}
		BarChart barChart;
		try {
			barChart = getBarChartFromInput(readLines(args[0]));
		} catch (Exception e) {
			System.out.println("Unable to create BarChart\n" + e.getMessage());
			return;
		}

		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame();
			frame.setLocation(20, 20);
			frame.setSize(500, 500);
			frame.setTitle("Bar chart");
			frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			frame.setVisible(true);

			BarChartComponent component = new BarChartComponent(barChart);
			frame.add(component);
			component.paint(frame.getGraphics());

		});

	}

	/**
	 * Creates {@link BarChart} from list of lines
	 * 
	 * @param lines bar chart information
	 * @return bar chart
	 */
	private static BarChart getBarChartFromInput(List<String> lines) {
		List<XYValue> values = new ArrayList<>();
		for (String value : lines.get(2).split("\\s+")) {
			String[] xy = value.split(",");
			values.add(new XYValue(Integer.parseInt(xy[0]), Integer.parseInt(xy[1])));
		}

		return new BarChart(values, lines.get(0), lines.get(1), Integer.parseInt(lines.get(3)),
				Integer.parseInt(lines.get(4)), Integer.parseInt(lines.get(5)));
	}

	/**
	 * Reads lines from the given path
	 * 
	 * @param string path
	 * @return Read lines
	 */
	private static List<String> readLines(String string) {
		try {
			BufferedReader fileReader = Files.newBufferedReader(Paths.get(string));
			String line;
			List<String> lines = new ArrayList<String>();
			while ((line = fileReader.readLine()) != null) {
				lines.add(line);
				if (lines.size() == 6) {
					break;
				}
			}
			if (lines.size() != 6) {
				throw new IllegalArgumentException("File must have 6 lines.");
			}
			return lines;
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to read file: " + string);
		}

	}
}