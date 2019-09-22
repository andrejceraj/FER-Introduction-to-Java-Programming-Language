package hr.fer.zemris.java.hw16;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public abstract class GalleryUtil {
	public static void createThumbnailImage(String webInfPath, String imageName) {
		Path imagePath = Paths.get(webInfPath, "slike", imageName);
		try {
			BufferedImage image = ImageIO.read(imagePath.toFile());

			java.awt.Image scaledImage = image.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
			BufferedImage resizedImage = new BufferedImage(150, 150, BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D g2d = resizedImage.createGraphics();
			g2d.drawImage(scaledImage, 0, 0, null);
			g2d.dispose();

			File destination = Paths.get(webInfPath, "thumbnails", imageName).toFile();
			ImageIO.write(resizedImage, "jpg", destination);
		} catch (IOException e) {
			System.out.println(imageName);
			e.printStackTrace();
		}
	}

}
