package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet sets the attribute for background color to the color parsed from URL
 * parameters. If the color is other than "white", "red", "green", "cyan", then
 * the color is set to white.
 * 
 * @author Andrej Ceraj
 *
 */
@WebServlet(name = "setColor", urlPatterns = { "/setColor" })
public class SetColorServlet extends HttpServlet {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = -8008027763632329419L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String color = (String) req.getParameter("color");
		String colorRGB;
		switch (color.toLowerCase()) {
		case "white":
			colorRGB = "FFFFFF";
			break;
		case "red":
			colorRGB = "FF0000";
			break;
		case "green":
			colorRGB = "00FF00";
			break;
		case "cyan":
			colorRGB = "00FFFF";
			break;

		default:
			colorRGB = "FFFFFF";
			break;
		}
		req.getSession().setAttribute("pickedBgCol", colorRGB);
		req.getRequestDispatcher("/index.jsp").forward(req, resp);

	}
}
