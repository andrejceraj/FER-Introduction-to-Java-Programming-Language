package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet constructs two arrays - one containing sin(x) values, one containing
 * cos(x) values, where x is each value from interval [a,b], and "a" and "b" are
 * variables given through the URL parameters
 * 
 * @author Andrej Ceraj
 *
 */
@WebServlet(name = "trigonometric", urlPatterns = { "/trigonometric" })
public class TrigonometricServlet extends HttpServlet {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = 5056935352048242462L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int a;
		try {
			a = Integer.parseInt(req.getParameter("a"));
		} catch (Exception e) {
			a = 0;
		}
		int b;
		try {
			b = Integer.parseInt(req.getParameter("b"));
		} catch (Exception e) {
			b = 360;
		}

		if (b < a) {
			a = a + b;
			b = a - b;
			a = a - b;
		}
		b = b > a + 720 ? a + 720 : b;

		DecimalFormat df = new DecimalFormat("#.######");

		String[] sinTable = new String[b - a + 1];
		String[] cosTable = new String[b - a + 1];

		for (int i = 0; i <= b - a; i++) {
			sinTable[i] = df.format(Math.sin((a + i) * Math.PI / 180));
			cosTable[i] = df.format(Math.cos((a + i) * Math.PI / 180));
		}

		req.getSession().setAttribute("a", a);
		req.getSession().setAttribute("sinTable", sinTable);
		req.getSession().setAttribute("cosTable", cosTable);

		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}

}
