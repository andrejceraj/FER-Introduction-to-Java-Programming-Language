package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.Poll;

/**
 * Servlet displays all possible polls the user can vote in.
 * 
 * @author Andrej Ceraj
 *
 */
@WebServlet(name = "index", urlPatterns = { "/servleti/index.html" })
public class IndexServlet extends HttpServlet {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = -5337188458108535318L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().write("<html><body><h2>Select poll</h2><ol>");

		for (Poll poll : DAOProvider.getDao().getPolls()) {
			StringBuilder sb = new StringBuilder();
			sb.append("<li><a href=\"glasanje?pollID=").append(poll.getId()).append("\" >");
			sb.append(poll.getTitle()).append("</li>");
			resp.getWriter().write(sb.toString());
		}
		resp.getWriter().write("</ol></body></html>");
		resp.getWriter().flush();
	}
}
