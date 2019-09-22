package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that redirects /"index" URLs to the main application screen.
 * 
 * @author Andrej Ceraj
 *
 */
@WebServlet(name = "redirect", urlPatterns = { "/index", "/index.jsp", "/index.html" })
public class RedirectServlet extends HttpServlet {
	/**
	 * Defaultly generated serial version id
	 */
	private static final long serialVersionUID = -6686469504981578436L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("servleti/main");
	}

}
