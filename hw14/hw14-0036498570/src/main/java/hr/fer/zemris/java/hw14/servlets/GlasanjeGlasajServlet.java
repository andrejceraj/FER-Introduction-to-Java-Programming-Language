package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used for registering votes. Through URL the servlet gets parameter
 * with band ID. It then updates database by incrementing
 * number of votes for band with the given ID. It then redirects to "/servleti/glasanje-rezultati"
 * 
 * @author Andrej Ceraj
 *
 */
import hr.fer.zemris.java.hw14.dao.DAOProvider;

@WebServlet(name = "glasanje-glasaj", urlPatterns = { "/servleti/glasanje-glasaj" })
public class GlasanjeGlasajServlet extends HttpServlet {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = 6088801003461120273L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long id;
		try {
			id = Long.parseLong(req.getParameter("id"));
		} catch (Exception e) {
			req.getRequestDispatcher("/WEB-INF/pages/glasanje-error.jsp").forward(req, resp);
			return;
		}
		DAOProvider.getDao().registerVote(id);
		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati");
	}
}
