package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Servlet preparing poll options to be voted for into {@link List} and setting it into
 * request attributes.
 * 
 * @author Andrej Ceraj
 *
 */
@WebServlet(name = "glasanje", urlPatterns = { "/servleti/glasanje" })
public class GlasanjeServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1886806633106426412L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long pollId;
		try {
			pollId = Long.parseLong(req.getParameter("pollID"));
		} catch (Exception e) {
			req.getRequestDispatcher("/WEB-INF/pages/glasanje-error.jsp").forward(req, resp);
			return;
		}

		Poll poll = DAOProvider.getDao().getPollById(pollId);
		req.getSession().setAttribute("currentPoll", poll);

		List<String> optionsList = new ArrayList<String>();
		for (PollOption option : DAOProvider.getDao().getOptionsForPolId(pollId)) {
			optionsList.add(option.getId() + "\t" + option.getOptionTitle() + "\t" + option.getOptionLink());
		}
		req.setAttribute("votingOptionList", optionsList);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
		;
	}
}
