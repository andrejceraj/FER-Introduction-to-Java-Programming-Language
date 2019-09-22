package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Main application screen servlet. It is used to prepare list of users for
 * viewing their blogs.
 * 
 * @author Andrej Ceraj
 *
 */
@WebServlet(name = "main", urlPatterns = { "/servleti/main" })
public class MainServlet extends HttpServlet {
	/**
	 * Defaultly generated serial version id
	 */
	private static final long serialVersionUID = 8067748696702751032L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<BlogUser> users = DAOProvider.getDAO().getAllUsers();
		List<String> usersNicks = users.stream().map(u -> u.getNick()).collect(Collectors.toList());
		req.setAttribute("usersNicks", usersNicks);
		req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
	}

}
