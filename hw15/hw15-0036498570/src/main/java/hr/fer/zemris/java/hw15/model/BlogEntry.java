package hr.fer.zemris.java.hw15.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Representation of a blog post (entry)
 * 
 * @author Andrej Ceraj
 *
 */
@NamedQueries({
		@NamedQuery(name = "BlogEntry.upit1", query = "select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when") })
@Entity
@Table(name = "blog_entries")
@Cacheable(true)
public class BlogEntry {

	/**
	 * Entry id
	 */
	@Id
	@GeneratedValue
	private Long id;
	/**
	 * Time at which the entry is created
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date createdAt;
	/**
	 * Time at which the entry is last modified
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	private Date lastModifiedAt;
	/**
	 * Entry title
	 */
	@Column(length = 200, nullable = false)
	private String title;
	/**
	 * Entry content
	 */
	@Column(length = 4096, nullable = false)
	private String text;

	/**
	 * Comments on this entry
	 */
	@OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("postedOn")
	private List<BlogComment> comments = new ArrayList<>();
	/**
	 * User who created the entry
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	private BlogUser creator;

	/**
	 * @return user who created the entry
	 */
	public BlogUser getCreator() {
		return creator;
	}

	/**
	 * Sets user who created the entry
	 * 
	 * @param creator user
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
	}

	/**
	 * @return entry id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets id of the entry
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return comments on this entry
	 */
	public List<BlogComment> getComments() {
		return comments;
	}

	/**
	 * Sets the comments on this entry
	 * 
	 * @param comments comments
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * @return time the entry is created
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Sets time the entry is created
	 * 
	 * @param createdAt
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return time the entry is last modified
	 */
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Sets time the entry is created
	 * 
	 * @param lastModifiedAt
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * @return entry title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets entry title to the given title
	 * 
	 * @param title title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return entry content
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets entry content to the given content
	 * 
	 * @param text content
	 */
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}