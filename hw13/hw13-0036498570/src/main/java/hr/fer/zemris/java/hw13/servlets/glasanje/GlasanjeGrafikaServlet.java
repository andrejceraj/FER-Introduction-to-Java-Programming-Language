package hr.fer.zemris.java.hw13.servlets.glasanje;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import hr.fer.zemris.java.hw13.models.BandModel;

/**
 * Servlet generates Pie Chart from voting results.
 * 
 * @author Andrej Ceraj
 *
 */
@WebServlet(name = "glasanje-grafika", urlPatterns = { "/glasanje-grafika" })
public class GlasanjeGrafikaServlet extends HttpServlet {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = -8917390199593837313L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			@SuppressWarnings("unchecked")
			Map<BandModel, Integer> resultsMap = (Map<BandModel, Integer>) req.getServletContext()
					.getAttribute("resultsMap");
			JFreeChart chart = createChart(resultsMap);
			resp.setContentType("image/png");
			OutputStream os = resp.getOutputStream();
			ChartUtils.writeChartAsPNG(os, chart, 1000, 450);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error");

		}
	}

	/**
	 * Creates a Pie Chart from results map
	 * 
	 * @param resultsMap results map
	 * @return pie chart
	 */
	private JFreeChart createChart(Map<BandModel, Integer> resultsMap) {
		JFreeChart chart = ChartFactory.createPieChart3D("Bands", generateDataset(resultsMap), true, true, false);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setForegroundAlpha(0.7f);
		return chart;
	}

	/**
	 * Generates {@link Dataset} for Pie Chart from results map
	 * 
	 * @param resultsMap results map
	 * @return dataset
	 */
	private PieDataset generateDataset(Map<BandModel, Integer> resultsMap) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (Entry<BandModel, Integer> entry : resultsMap.entrySet()) {
			if (entry.getValue() != 0) {
				dataset.setValue(entry.getKey().getName(), entry.getValue());
			}
		}
		return dataset;
	}
}
