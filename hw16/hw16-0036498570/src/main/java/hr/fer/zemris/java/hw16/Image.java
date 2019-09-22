package hr.fer.zemris.java.hw16;

import java.util.ArrayList;
import java.util.List;

public class Image {
	private String name;
	private String description;
	private List<String> tags;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public void addTag(String tag) {
		if (tags == null) {
			tags = new ArrayList<String>();
		}
		tags.add(tag);
	}

	public boolean containsTag(String tag) {
		if (tags.contains(tag)) {
			return true;
		}
		return false;
	}

}
