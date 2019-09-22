package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.util.Utils;

/**
 * Servlet used for handling login process. When post method is called, the
 * servlet checks if the login is successful.
 * 
 * @author Andrej Ceraj
 *
 */
@WebServlet(name = "login", urlPatterns = { "/servleti/login" })
public class LoginServlet extends HttpServlet {
	/**
	 * Defaultly generated serial version id
	 */
	private static final long serialVersionUID = -5864245757065542144L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nick = req.getParameter("nick");
		String password = req.getParameter("password");
		if (nick == null || password == null || nick.isBlank() || password.isBlank()) {
			failedToLogIn(req, resp, "Nick and password are required");
			return;
		}
		String passwordHash = Utils.encryptPassword(password);
		BlogUser user = DAOProvider.getDAO().getUserByNick(nick);
		if (user == null) {
			req.getSession().setAttribute("nick", nick);
			failedToLogIn(req, resp, "Failed to log in.");
			return;
		}

		if (passwordHash.equals(user.getPasswordHash())) {
			req.getSession().setAttribute("current.user.id", user.getId());
			req.getSession().setAttribute("current.user.fn", user.getFirstName());
			req.getSession().setAttribute("current.user.ln", user.getLastName());
			req.getSession().setAttribute("current.user.nick", user.getNick());
			System.out.println("user logged in");
			resp.sendRedirect("main");
			return;
		}

		req.getSession().setAttribute("nick", nick);
		failedToLogIn(req, resp, "Failed to log in!");

	}

	/**
	 * Redirects failed login to the the main application screen
	 * 
	 * @param req     servlet request
	 * @param resp    servlet response
	 * @param message error message
	 * @throws IOException if an input or output error is detected when the servlet
	 *                     handles the request
	 */
	private void failedToLogIn(HttpServletRequest req, HttpServletResponse resp, String message) throws IOException {
		req.getSession().setAttribute("loginError", message);
		resp.sendRedirect("main");

	}

}
