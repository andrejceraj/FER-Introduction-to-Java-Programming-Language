package hr.fer.zemris.java.hw13.servlets.glasanje;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw13.utils.GlasanjeUtils;

/**
 * Servlet preparing bands to be voted for into {@link List} and setting it into
 * request attributes.
 * 
 * @author Andrej Ceraj
 *
 */
@WebServlet(name = "glasanje", urlPatterns = { "/glasanje" })
public class GlasanjeServlet extends HttpServlet {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = 931402056533826761L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		try {
			List<String> bands = GlasanjeUtils.readFileIntoList(fileName);
			req.setAttribute("bandsForVoting", bands);
			req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
		} catch (IOException e) {
			resp.setStatus(404);
			req.getRequestDispatcher("/WEB-INF/pages/glasnjeError.jsp").forward(req, resp);
		}

	}
}
