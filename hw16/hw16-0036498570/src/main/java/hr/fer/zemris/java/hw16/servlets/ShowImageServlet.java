package hr.fer.zemris.java.hw16.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "showImage", urlPatterns = { "/showImage" })
public class ShowImageServlet extends HttpServlet {
	private static final long serialVersionUID = 3598242205153379127L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String folder = req.getParameter("folder");
		String imageName = req.getParameter("name");
		Path imagePath = Paths.get(req.getServletContext().getRealPath("/WEB-INF"), folder, imageName);
		BufferedImage image = ImageIO.read(imagePath.toFile());
		ImageIO.write(image, "jpg", resp.getOutputStream());
	}

}
