package hr.fer.zemris.java.hw16.rest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import hr.fer.zemris.java.hw16.GalleryUtil;
import hr.fer.zemris.java.hw16.Image;
import hr.fer.zemris.java.hw16.ImageDB;

@Path("/images")
public class ImagesREST {

	@Context
	private ServletContext servletContext;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllImages() {
		return prepareImages(ImageDB.getImages());
	}

	@GET
	@Path("/{tag}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getImagesForTag(@PathParam("tag") String tag) {
		return prepareImages(ImageDB.getImagesWithTag(tag));
	}

	@GET
	@Path("/getImage/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getImage(@PathParam("name") String imageName) {
		for (Image image : ImageDB.getImages()) {
			if (image.getName().equals(imageName)) {
				Gson gson = new Gson();
				String jsonText = gson.toJson(image);
				return Response.status(Status.OK).entity(jsonText).build();
			}
		}
		return Response.status(Status.NOT_FOUND).build();
	}

	private Response prepareImages(List<Image> images) {
		List<Image> preparedImages = new ArrayList<Image>();
		for (Image img : images) {
			java.nio.file.Path thumbnailsPath = Paths.get(servletContext.getRealPath("/WEB-INF/thumbnails"));
			if (!Files.exists(thumbnailsPath) || !Files.isDirectory(thumbnailsPath)) {
				try {
					Files.createDirectories(thumbnailsPath);
				} catch (IOException e) {
					e.printStackTrace();
					continue;
				}
			}
			if (!Files.exists(Paths.get(thumbnailsPath.toString(), img.getName()))) {
				GalleryUtil.createThumbnailImage(servletContext.getRealPath("/WEB-INF"), img.getName());
			}
			preparedImages.add(img);
		}

		Gson gson = new Gson();
		String jsonText = gson.toJson(images);

		return Response.status(Status.OK).entity(jsonText).build();
	}

}
