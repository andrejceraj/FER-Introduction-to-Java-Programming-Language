package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet redirects "/index.html" to servlet "/servlet/index.html"
 * 
 * @author Andrej Ceraj
 *
 */
@WebServlet(name = "index-redirect", urlPatterns = { "/index.html" })
public class IndexRedirectServlet extends HttpServlet {

	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = 7188421800203398237L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("servleti/index.html");
	}
}