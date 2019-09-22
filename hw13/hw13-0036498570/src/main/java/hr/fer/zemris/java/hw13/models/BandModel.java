package hr.fer.zemris.java.hw13.models;

import java.util.Objects;

/**
 * Model representing informations about a band.
 * 
 * @author Andrej Ceraj
 *
 */
public class BandModel {
	/**
	 * Band ID
	 */
	private int id;
	/**
	 * Band name
	 */
	private String name;
	/**
	 * Link to a song this band released
	 */
	private String songLink;

	/**
	 * Constructor
	 * 
	 * @param id       band ID
	 * @param name     band name
	 * @param songLink link to a song this band released
	 */
	public BandModel(int id, String name, String songLink) {
		this.id = id;
		this.name = name;
		this.songLink = songLink;
	}

	/**
	 * @return band ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return band name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return link to a song this band released
	 */
	public String getSongLink() {
		return songLink;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BandModel))
			return false;
		BandModel other = (BandModel) obj;
		return id == other.id;
	}

}
