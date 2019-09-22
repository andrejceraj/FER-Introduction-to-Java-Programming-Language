package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Servlet creates Excel (.xls) file with the following form: the file contains
 * n sheets, and in each sheet values are iterating from a to b, where "a", "b"
 * and "n" are variables parsed from URL parameters. Each sheet contains two
 * colomns, first one is the value, and the second one is value powered to sheet
 * number.
 * 
 * @author Andrej Ceraj
 *
 */
@WebServlet(name = "powers", urlPatterns = { "/powers" })
public class PowersServlet extends HttpServlet {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = -5255859601861513032L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int a;
		int b;
		int n;
		try {
			a = Integer.parseInt(req.getParameter("a"));
			b = Integer.parseInt(req.getParameter("b"));
			n = Integer.parseInt(req.getParameter("n"));

			if (a < -100 || a > 100 || b < -100 || b > 100 || n < 1 || n > 5) {
				resp.setStatus(422);
				sendError(req, resp);
				return;
			}
		} catch (Exception e) {
			resp.setStatus(400);
			sendError(req, resp);
			return;
		}

		HSSFWorkbook workbook = createWorkbook(a, b, n);
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=\"powers.xls\"");
		OutputStream os = resp.getOutputStream();
		workbook.write(os);

	}

	/**
	 * Creates the form of the Excel (.xls) file and fills its content.
	 * 
	 * @param a variable a
	 * @param b variable b
	 * @param n variable n
	 * @return
	 */
	private HSSFWorkbook createWorkbook(int a, int b, int n) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		for (int i = 1; i <= n; i++) {
			HSSFSheet sheet = workbook.createSheet("powerOf" + i);
			HSSFRow headRow = sheet.createRow(0);
			headRow.createCell(0).setCellValue("Value");
			headRow.createCell(1).setCellValue("value^" + i);
			for (int j = 0; j <= b - a; j++) {
				HSSFRow row = sheet.createRow(j + 1);
				row.createCell(0).setCellValue(a + j);
				row.createCell(1).setCellValue(Math.pow(a + j, i));
			}
		}

		return workbook;
	}

	/**
	 * Sends error if saying how parameters should be given in URL.
	 * 
	 * @param req  request
	 * @param resp response
	 * @throws ServletException if the request could not be handled
	 * @throws IOException      if an input or output error is detected when the
	 *                          servlet handles the GET request
	 */
	private void sendError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/powerError.jsp").forward(req, resp);
	}

}
