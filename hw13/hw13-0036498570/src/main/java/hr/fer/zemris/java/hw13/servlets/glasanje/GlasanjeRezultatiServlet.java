package hr.fer.zemris.java.hw13.servlets.glasanje;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw13.models.BandModel;
import hr.fer.zemris.java.hw13.utils.GlasanjeUtils;

/**
 * Servlet prepares sorted map with bands and its number of votes and stores it
 * into context attributes.
 * 
 * @author Andrej Ceraj
 *
 */
@WebServlet(name = "glasanje-rezultati", urlPatterns = { "/glasanje-rezultati" })
public class GlasanjeRezultatiServlet extends HttpServlet {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = 282417096502872018L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileNameResults = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		String fileNameBands = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");

		if (!Files.exists(Paths.get(fileNameResults))) {
			GlasanjeUtils.createResultsFile(fileNameResults, fileNameBands);
		}

		try {
			List<String> results = GlasanjeUtils.readFileIntoList(fileNameResults);
			List<String> bands = GlasanjeUtils.readFileIntoList(fileNameBands);
			Map<BandModel, Integer> resultsMap = generateResultsMap(results, bands);
			req.getServletContext().setAttribute("resultsMap", resultsMap);
			req.setAttribute("resultsMap", resultsMap);
			req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
		} catch (IOException e) {
			resp.setStatus(404);
			req.getRequestDispatcher("/WEB-INF/pages/glasanjeError.jsp");
		}

	}

	/**
	 * Creates a map associating band with its number of votes. Map is then sorted
	 * descending by number of votes values.
	 * 
	 * @param results list of results file lines
	 * @param bands   list of bands file lines
	 * @return sorted results map
	 */
	private Map<BandModel, Integer> generateResultsMap(List<String> results, List<String> bands) {
		Map<BandModel, Integer> resultsMap = new HashMap<BandModel, Integer>();
		Iterator<String> iterResults = results.iterator();
		Iterator<String> iterBands = bands.iterator();

		while (iterBands.hasNext() && iterResults.hasNext()) {
			String[] band = iterBands.next().split("\\t");
			String[] result = iterResults.next().split("\\t");

			BandModel bandModel = new BandModel(Integer.parseInt(band[0]), band[1], band[2]);

			resultsMap.put(bandModel, Integer.parseInt(result[1]));
		}

		Map<BandModel, Integer> sortedResultsMap = resultsMap.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

		return sortedResultsMap;
	}
}
