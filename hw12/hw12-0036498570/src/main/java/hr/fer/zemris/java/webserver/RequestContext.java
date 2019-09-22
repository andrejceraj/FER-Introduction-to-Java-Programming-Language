package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Context containing information for generating HTML header and appending
 * further HTML code to it
 * 
 * @author Andrej Ceraj
 *
 */
public class RequestContext {
	/**
	 * Output stream to which data is written
	 */
	private OutputStream outputStream;
	/**
	 * Writing charset
	 */
	private Charset charset;

	/**
	 * Writing encoding
	 */
	private String encoding = "UTF-8";
	/**
	 * Response header status code
	 */
	private int statusCode = 200;
	/**
	 * Response header text
	 */
	private String statusText = "OK";
	/**
	 * Response body mime-type
	 */
	private String mimeType = "text/html";
	/**
	 * Response body content length
	 */
	private Long contentLength = null;

	/**
	 * Context parameters
	 */
	private Map<String, String> parameters;
	/**
	 * Context temporary parameters
	 */
	private Map<String, String> temporaryParameters = new HashMap<String, String>();
	/**
	 * Context persistant parameters
	 */
	private Map<String, String> persistentParameters;
	/**
	 * Context cookies
	 */
	private List<RCCookie> outputCookies;
	/**
	 * Session ID
	 */
	private String SID;

	/**
	 * Flag indicating if the header written to output stream
	 */
	private boolean headerGenerated = false;

	/**
	 * {@link Dispatcher}
	 */
	private IDispatcher dispatcher;

	/**
	 * Constructor
	 * 
	 * @param outputStream         output stream to which data is written
	 * @param parameters           context parameters
	 * @param persistentParameters context persistant parameters
	 * @param outputCookies        context cookies
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
		this.outputStream = outputStream;
		this.parameters = Collections.unmodifiableMap(parameters);
		this.persistentParameters = persistentParameters;
		this.outputCookies = outputCookies;
		charset = Charset.forName(encoding);
	}

	/**
	 * Constructor
	 * 
	 * @param outputStream         output stream to which data is written
	 * @param parameters           context parameters
	 * @param persistentParameters context persistant parameters
	 * @param outputCookies        context cookies
	 * @param temporaryParameters  context temporary parameters
	 * @param dispatcher           dispatcher
	 * @param SID                  session ID
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters, IDispatcher dispatcher, String SID) {
		this(outputStream, parameters, persistentParameters, outputCookies);
		this.temporaryParameters = temporaryParameters;
		this.dispatcher = dispatcher;
		this.SID = SID;
	}

	/**
	 * @return dispatcher
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}

	/**
	 * Method that retrieves value from parameters map (or null if no association
	 * exists)
	 * 
	 * @param name key
	 * @return value
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}

	/**
	 * Method that retrieves names of all parameters in parameters map as read-only
	 * set
	 * 
	 * @return parameters names
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}

	/**
	 * Method that retrieves value from persistent parameters map (or null if no
	 * association exists)
	 * 
	 * @param name key
	 * @return value
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}

	/**
	 * Method that retrieves names of all parameters in persistent parameters map as
	 * read-only set
	 * 
	 * @return persistant parameters names
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}

	/**
	 * Method that stores a value to persistent parameters map
	 * 
	 * @param name  key
	 * @param value value
	 */
	public void setPersistentParameter(String name, String value) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(value);

		persistentParameters.put(name, value);
	}

	/**
	 * Method that removes a value from persistentParameters map
	 * 
	 * @param name key
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}

	/**
	 * Method that retrieves value from temporary parameters map (or null if no
	 * association exists)
	 * 
	 * @param name key
	 * @return value
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}

	/**
	 * Method that retrieves names of all parameters in temporary parameters map as
	 * read-only set
	 * 
	 * @return temporary parameters names
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}

	/**
	 * @return session ID
	 */
	public String getSessionID() {
		return SID;
	}

	/**
	 * Method that stores a value to temporary parameters map
	 * 
	 * @param name  key
	 * @param value value
	 */
	public void setTemporaryParameter(String name, String value) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(value);

		temporaryParameters.put(name, value);
	}

	/**
	 * Method that removes a value from temporary parameters map
	 * 
	 * @param name
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}

	/**
	 * Adds {@link RCCookie} to cookies list
	 * 
	 * @param cookie RCCookie
	 */
	public void addRCCookie(RCCookie cookie) {
		this.outputCookies.add(cookie);
	}

	/**
	 * If the header is not already written the method generates and writes the
	 * header. Method then writes given bytes array to output stream.
	 * 
	 * @param data array of bytes
	 * @return this
	 * @throws IOException if it is unable to write to output stream
	 */
	public RequestContext write(byte[] data) throws IOException {
		if (!headerGenerated) {
			setCharset();
			printHeader();
			headerGenerated = true;
		}

		outputStream.write(data);
		return this;
	}

	/**
	 * If the header is not already written the method generates and writes the
	 * header. Method then writes given amount of bytes from the data starting from
	 * the given offset to output stream.
	 * 
	 * @param data   array of bytes
	 * @param offset offset
	 * @param len    amount of bytes to be written
	 * @return this
	 * @throws IOException if it is unable to write to output stream
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		if (!headerGenerated) {
			setCharset();
			printHeader();
			headerGenerated = true;
		}

		outputStream.write(data, offset, len);
		return this;
	}

	/**
	 * If the header is not already written the method generates and writes the
	 * header. Method then writes given string to output stream.
	 * 
	 * @param text string
	 * @return this
	 * @throws IOException if it is unable to write to output stream
	 */
	public RequestContext write(String text) throws IOException {
		if (!headerGenerated) {
			setCharset();
			printHeader();
			headerGenerated = true;
		}

		outputStream.write(text.getBytes(charset));
		return this;
	}

	/**
	 * Writes header to output stream
	 * 
	 * @throws IOException if it is unable to write to output stream
	 */
	private void printHeader() throws IOException {
		outputStream.write(constructHeader().getBytes(charset));
	}

	/**
	 * Gets charset from the encoding
	 */
	private void setCharset() {
		charset = Charset.forName(encoding);
	}

	/**
	 * Constructs header based on the context information
	 * 
	 * @return header
	 */
	private String constructHeader() {
		StringBuilder sb = new StringBuilder();

		sb.append("HTTP/1.1 ").append(statusCode).append(statusText).append("\r\n");

		sb.append("Content-Type: ").append(mimeType);
		if (mimeType.startsWith("text/")) {
			sb.append("; charset ").append(encoding);
		}
		sb.append("\r\n");

		if (contentLength != null) {
			sb.append("Content-Length").append(contentLength).append("\r\n");
		}

		if (outputCookies != null && !outputCookies.isEmpty()) {
			outputCookies.forEach(c -> appendCookieToStringBuilder(c, sb));
		}

		sb.append("\r\n");

		return sb.toString();
	}

	/**
	 * Appends cookie information to string builder
	 * 
	 * @param cookie cookie
	 * @param sb     string builder
	 */
	private void appendCookieToStringBuilder(RCCookie cookie, StringBuilder sb) {
		sb.append("Set-Cookie: ").append(cookie.name).append("=\"").append(cookie.value).append("\"");
		if (cookie.domain != null) {
			sb.append("; Domain=").append(cookie.domain);
		}
		if (cookie.path != null) {
			sb.append("; Path=").append(cookie.path);
		}
		if (cookie.maxAge != null) {
			sb.append("; Max-Age=").append(cookie.maxAge);
		}

		sb.append("; HttpOnly\r\n");
	}

	/**
	 * Sets encoding to the given value
	 * 
	 * @param encoding value
	 */
	public void setEncoding(String encoding) {
		shouldAlter(this.encoding, encoding);
		this.encoding = encoding;
	}

	/**
	 * Sets status code to the given value
	 * 
	 * @param statusCode value
	 */
	public void setStatusCode(int statusCode) {
		shouldAlter(this.statusCode, statusCode);
		this.statusCode = statusCode;
	}

	/**
	 * Sets status text to the given value
	 * 
	 * @param statusText value
	 */
	public void setStatusText(String statusText) {
		shouldAlter(this.statusText, statusText);
		this.statusText = statusText;
	}

	/**
	 * Sets mime type to the given value
	 * 
	 * @param mimeType value
	 */
	public void setMimeType(String mimeType) {
		shouldAlter(this.mimeType, mimeType);
		this.mimeType = mimeType;
	}

	/**
	 * Sets content length to the given value
	 * 
	 * @param contentLength value
	 */
	public void setContentLength(Long contentLength) {
		shouldAlter(this.contentLength, contentLength);
		this.contentLength = contentLength;
	}

	/**
	 * Throws {@link RuntimeException} if the certain context information should not
	 * be altered
	 * 
	 * @param o1 old information
	 * @param o2 new information
	 */
	private void shouldAlter(Object o1, Object o2) {
		if (headerGenerated && !o1.equals(o2)) {
			throw new RuntimeException();
		}
		return;
	}

	/**
	 * Representation of a web cookie
	 * 
	 * @author Andrej Ceraj
	 *
	 */
	public static class RCCookie {
		/**
		 * Cookie name
		 */
		private String name;
		/**
		 * Cookie value
		 */
		private String value;
		/**
		 * Minimal time cookie should exist
		 */
		private Integer maxAge;
		/**
		 * Cookie domain
		 */
		private String domain;
		/**
		 * Cookie path
		 */
		private String path;

		/**
		 * Constructor
		 * 
		 * @param name   cookie name
		 * @param value  cookie value
		 * @param maxAge minimal time cookie should exist
		 * @param path   cookie path
		 * @param domain cookie domain
		 */
		public RCCookie(String name, String value, Integer maxAge, String path, String domain) {
			this.name = name;
			this.value = value;
			this.maxAge = maxAge;
			this.path = path;
			this.domain = domain;
		}

		/**
		 * @return cookie name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @return cookie value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * @return cookie domain
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * @return cookie path
		 */
		public String getPath() {
			return path;
		}

		/**
		 * @return minimal time cookie should exist
		 */
		public Integer getMaxAge() {
			return maxAge;
		}

	}
}
