package hr.fer.zemris.java.hw15.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * Representation of a blog user.
 * 
 * @author Andrej Ceraj
 *
 */
@NamedQueries({
		@NamedQuery(name = "BlogUser.upit1", query = "select e from BlogEntry as e where e.creator=:bu and e.createdAt>:when") })

@Entity
@Table(name = "blog_users")
public class BlogUser {

	/**
	 * User id
	 */
	@Id
	@GeneratedValue
	private long id;
	/**
	 * User's first name
	 */
	@Column(name = "FIRSTNAME", length = 20, nullable = false)
	private String firstName;
	/**
	 * User's last name
	 */
	@Column(name = "LASTNAME", length = 20, nullable = false)
	private String lastName;
	/**
	 * User's nickname
	 */
	@Column(name = "NICK", unique = true, length = 20, nullable = false)
	private String nick;
	/**
	 * User's email
	 */
	@Column(name = "EMAIL", length = 50, nullable = false)
	private String email;
	/**
	 * User's account password
	 */
	@Column(name = "PASSWORDHASH", nullable = false)
	private String passwordHash;

	/**
	 * Entries the user has created
	 */
	@OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("createdAt")
	private List<BlogEntry> entries = new ArrayList<BlogEntry>();

	/**
	 * Constructor
	 */
	public BlogUser() {
	}

	/**
	 * Constructor with fields: first name, last name, email, nickname and hashed
	 * password.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param nick
	 * @param email
	 * @param passwordHash
	 */
	public BlogUser(String firstName, String lastName, String nick, String email, String passwordHash) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.nick = nick;
		this.email = email;
		this.passwordHash = passwordHash;
	}

	/**
	 * @return user's id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets user's id to the given value
	 * 
	 * @param id value
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return user's first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets user's first name to the given value
	 * 
	 * @param firstName value
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return user's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets user's last name to the given value
	 * 
	 * @param lastName value
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return user's nickname
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Sets user's nickname to the given value
	 * 
	 * @param nick value
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * @return user's email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets user's email to the given value
	 * 
	 * @param email value
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return user's account hashed password
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Sets user's account hashed password to the given hash
	 * 
	 * @param passwordHash hash
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * @return entries the user has posted
	 */
	public List<BlogEntry> getEntries() {
		return entries;
	}

	/**
	 * Sets the entries the user has posted.
	 * 
	 * @param entries entries
	 */
	public void setEntries(List<BlogEntry> entries) {
		this.entries = entries;
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
		if (!(obj instanceof BlogUser))
			return false;
		BlogUser other = (BlogUser) obj;
		return id == other.id;
	}

}
