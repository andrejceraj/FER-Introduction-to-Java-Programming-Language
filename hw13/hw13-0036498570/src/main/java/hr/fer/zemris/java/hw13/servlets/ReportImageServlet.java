package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.chart.ChartUtils;

/**
 * Servlet generates OS usage Pie Chart with example data values.
 * 
 * @author Andrej Ceraj
 *
 */
@WebServlet(name = "reportImage", urlPatterns = { "/reportImage" })
public class ReportImageServlet extends HttpServlet {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = 4055883957912530528L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JFreeChart chart = getChart();

		resp.setContentType("image/png");
		OutputStream os = resp.getOutputStream();
		ChartUtils.writeChartAsPNG(os, chart, 1000, 450);
	}

	/**
	 * Creates a OS usage Pie Chart.
	 * 
	 * @return
	 */
	private JFreeChart getChart() {
		JFreeChart chart = ChartFactory.createPieChart3D("OS usage", getDataset(), true, true, false);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setForegroundAlpha(0.7f);
		return chart;
	}

	/**
	 * Creates and returns {@link Dataset} filled with example OS usage data values.
	 * 
	 * @return dataset
	 */
	private PieDataset getDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Linux", 55);
		dataset.setValue("Mac", 11);
		dataset.setValue("Windows", 25);
		dataset.setValue("Android", 9);
		return dataset;
	}

}
