package hr.fer.zemris.java.hw13.servlets.glasanje;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw13.utils.GlasanjeUtils;

/**
 * Servlet used for registering votes. Through URL the servlet gets parameter
 * with band ID. It then updates "glasanje-rezultati.txt" file to increment
 * number of votes for band with the given ID.
 * 
 * @author Andrej Ceraj
 *
 */
@WebServlet(name = "glasanje-glasaj", urlPatterns = { "/glasanje-glasaj" })
public class GlasanjeGlasajServlet extends HttpServlet {
	/**
	 * Defaultly generated serial version ID
	 */
	private static final long serialVersionUID = 6088801003461120273L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileNameResults = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");

		if (!Files.exists(Paths.get(fileNameResults))) {
			String fileNameBands = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
			GlasanjeUtils.createResultsFile(fileNameResults, fileNameBands);
		}

		int bandId = Integer.parseInt(req.getParameter("id"));
		registerVote(fileNameResults, bandId);
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}

	/**
	 * Method opens a file, loads its content into {@link StringBuffer} with changed
	 * value for the given band ID and then overwrites the file with new content.
	 * 
	 * @param fileName full file path
	 * @param bandId   band ID
	 * @throws IOException if it is unable to read or write into the given file.
	 */
	private void registerVote(String fileName, int bandId) throws IOException {
		Path path = Paths.get(fileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(path)));
		StringBuffer sb = new StringBuffer();
		String line;
		while ((line = reader.readLine()) != null && !line.equals("\\n")) {
			String[] words = line.split("\\t");
			if (Integer.parseInt(words[0]) == bandId) {
				sb.append(words[0]).append('\t').append(Integer.parseInt(words[1]) + 1).append('\n');
			} else {
				sb.append(line).append('\n');
			}
		}
		OutputStream os = Files.newOutputStream(path);
		os.write(sb.toString().getBytes());
		os.flush();
	}
}
