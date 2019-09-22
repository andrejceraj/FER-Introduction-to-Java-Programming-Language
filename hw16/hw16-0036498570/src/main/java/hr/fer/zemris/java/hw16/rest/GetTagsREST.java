package hr.fer.zemris.java.hw16.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import hr.fer.zemris.java.hw16.ImageDB;

@Path("/tags")
public class GetTagsREST {
	@GET
	@Produces("application/json")
	public Response getTags() {
		List<String> tags = ImageDB.getTags();
		tags.sort((a, b) -> a.compareTo(b));

		Gson gson = new Gson();
		String jsonText = gson.toJson(tags);

		return Response.status(Status.OK).entity(jsonText).build();
	}
}
