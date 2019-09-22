package hr.fer.zemris.java.hw16;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ImageDB implements ServletContextListener {
	private static List<Image> images = new ArrayList<Image>();
	private static List<String> tags = new ArrayList<String>();
	private static Path pathToImages = Paths.get("./src/main/webapp/WEB-INF/slike");

	public static List<Image> getImages() {
		return images;
	}

	public static List<String> getTags() {
		return tags;
	}

	public static Path getPathToImages() {
		return pathToImages;
	}

	public static List<Image> getImagesWithTag(String tag) {
		List<Image> imagesWithTag = new ArrayList<Image>();
		for (Image img : images) {
			if (img.getTags().contains(tag)) {
				imagesWithTag.add(img);
			}
		}
		return imagesWithTag;
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Path path = Paths.get(sce.getServletContext().getRealPath("WEB-INF/opisnik.txt"));
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(path)));
			String line = null;
			while (true) {
				if ((line = reader.readLine()) == null) {
					break;
				}
				Image img = new Image();
				img.setName(line.trim());
				line = reader.readLine();
				img.setDescription(line.trim());
				line = reader.readLine();
				for (String tag : line.split(",")) {
					String trimmedTag = tag.trim();
					img.addTag(trimmedTag);
					if (!tags.contains(trimmedTag)) {
						tags.add(trimmedTag);
					}
				}
				images.add(img);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}
