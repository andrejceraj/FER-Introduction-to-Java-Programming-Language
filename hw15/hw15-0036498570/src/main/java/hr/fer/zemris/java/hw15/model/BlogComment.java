package hr.fer.zemris.java.hw15.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Representation of a comment on blog post (entry).
 * 
 * @author Andrej Ceraj
 *
 */
@Entity
@Table(name = "blog_comments")
public class BlogComment {

	/**
	 * Comment id
	 */
	@Id
	@GeneratedValue
	private Long id;
	/**
	 * Mail of a user who posted the comment
	 */
	@Column(length = 100, nullable = false)
	private String usersEMail;
	/**
	 * Comment content
	 */
	@Column(length = 4096, nullable = false)
	private String message;
	/**
	 * Time at which the comment is posted
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date postedOn;

	/**
	 * Entry which is commented
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	private BlogEntry blogEntry;

	/**
	 * @return comment id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets comment id to the given value
	 * 
	 * @param id id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return entry which is comented
	 */
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}

	/**
	 * Sets entry which is commented to the given value
	 * 
	 * @param blogEntry entry
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * @return mail of a user who posted the comment
	 */
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Sets the mail of a user who posted the comment
	 * 
	 * @param usersEMail email
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * @return comment content
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets comment content to the given value
	 * 
	 * @param message comment content
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return time at which the comment is posted
	 */
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Sets the time at which the comment is posted
	 * 
	 * @param postedOn time
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
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
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}