package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that handles the logout process, invalidating everything from the
 * current session.
 * 
 * @author Andrej Ceraj
 *
 */
@WebServlet(name = "logout", urlPatterns = { "/servleti/logout" })
public class LogoutServlet extends HttpServlet {
	/**
	 * Defaultly generated serial version id
	 */
	private static final long serialVersionUID = -1770316178727453773L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().invalidate();
		resp.sendRedirect("main");
	}

}
