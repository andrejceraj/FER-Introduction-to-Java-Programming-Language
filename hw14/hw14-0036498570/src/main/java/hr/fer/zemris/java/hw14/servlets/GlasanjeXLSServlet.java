package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Servlet generates Excel (.xls) file containing the voting results
 * 
 * @author Andrej Ceraj
 *
 */
@WebServlet(name = "glasanje-xls", urlPatterns = { "/servleti/glasanje-xls" })
public class GlasanjeXLSServlet extends HttpServlet {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = -1653530627026078349L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			@SuppressWarnings("unchecked")
			Map<PollOption, Integer> resultsMap = (Map<PollOption, Integer>) req.getSession().getAttribute("resultsMap");
			HSSFWorkbook workbook = createWorkbook(resultsMap);
			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-Disposition", "attachment; filename=\"glasanje-rezultati.xls\"");
			OutputStream os = resp.getOutputStream();
			workbook.write(os);
		} catch (Exception e) {
		}

	}

	/**
	 * Creates the form of the Excel (.xls) file and fills its content.
	 * 
	 * @param resultsMap
	 * @return
	 */
	private HSSFWorkbook createWorkbook(Map<PollOption, Integer> resultsMap) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Rezultati glasanja");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("Bend");
		headRow.createCell(1).setCellValue("Broj glasova");
		int rowNo = 1;
		for (Entry<PollOption, Integer> entry : resultsMap.entrySet()) {
			HSSFRow row = sheet.createRow(rowNo);
			row.createCell(0).setCellValue(entry.getKey().getOptionTitle());
			row.createCell(1).setCellValue(entry.getValue());
			rowNo++;
		}

		return workbook;
	}

}
