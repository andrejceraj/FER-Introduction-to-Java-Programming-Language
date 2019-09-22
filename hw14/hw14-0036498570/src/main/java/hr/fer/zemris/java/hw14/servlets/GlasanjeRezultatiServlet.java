package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Servlet prepares sorted map with poll options and its number of votes and
 * stores it into session attributes.
 * 
 * @author Andrej Ceraj
 *
 */
@WebServlet(name = "glasanje-rezultati", urlPatterns = { "/servleti/glasanje-rezultati" })
public class GlasanjeRezultatiServlet extends HttpServlet {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = 282417096502872018L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Poll poll = (Poll) req.getSession().getAttribute("currentPoll");
			if (poll == null) {
				System.out.println("nema polla");
				req.getRequestDispatcher("/WEB-INF/pages/glasanjeError.jsp").forward(req, resp);
				return;
			}
			List<PollOption> options = DAOProvider.getDao().getOptionsForPolId(poll.getId());
			Map<PollOption, Integer> resultsMap = generateResultsMap(options);
			req.getSession().setAttribute("resultsMap", resultsMap);
			req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
		} catch (IOException e) {
			resp.setStatus(404);
			req.getRequestDispatcher("/WEB-INF/pages/glasanjeError.jsp").forward(req, resp);
			;
		}

	}

	/**
	 * Creates a map associating poll options with its number of votes. Map is then
	 * sorted descending by number of votes values.
	 * 
	 * @param options list of poll options
	 * @return results map
	 */
	private Map<PollOption, Integer> generateResultsMap(List<PollOption> options) {
		Map<PollOption, Integer> resultsMap = new HashMap<PollOption, Integer>();

		for (PollOption option : options) {
			resultsMap.put(option, option.getVotesCount());
		}
		Map<PollOption, Integer> sortedResultsMap = resultsMap.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
		return sortedResultsMap;
	}
}
