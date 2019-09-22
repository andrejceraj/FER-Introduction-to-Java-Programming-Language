package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * <p>
 * Program when run starts server. Server properties are read from file given as
 * program argument. Server handles clients with {@link ClientWorker}s.
 * </p>
 * <p>
 * Web server's home page is at /index2.html from which other available links
 * are accessible.
 * </p>
 *
 * @author Andrej Ceraj
 *
 */
public class SmartHttpServer {
	/**
	 * Server IP address
	 */
	@SuppressWarnings("unused")
	private String address;
	/**
	 * Server domain name
	 */
	private String domainName;
	/**
	 * Port to which server listens to
	 */
	private int port;
	/**
	 * Number of threads used for handling clients
	 */
	private int workerThreads;
	/**
	 * Session timeout in seconds
	 */
	private int sessionTimeout;
	/**
	 * Mime types stored from mime.properties
	 */
	private Map<String, String> mimeTypes = new HashMap<String, String>();
	/**
	 * Thread holding the server
	 */
	private ServerThread serverThread;
	/**
	 * Thread pool
	 */
	private ExecutorService threadPool;
	/**
	 * Path to server root document
	 */
	private Path documentRoot;

	/**
	 * Flag indicating if the server is running
	 */
	private AtomicBoolean isRunning = new AtomicBoolean(false);

	/**
	 * Map associating workers url with worker classes
	 */
	private Map<String, IWebWorker> workersMap;

	/**
	 * Map associating session IDs with {@link SessionMapEntry}
	 */
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();
	/**
	 * Random generator
	 */
	private Random sessionRandom = new Random();

	/**
	 * Cookie cleaner
	 */
	private CookieCleaner cleaner = new CookieCleaner();

	/**
	 * Method starts when the program is run. Starts the {@link SmartHttpServer}
	 * 
	 * @param args path to configuration file
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Only path to server properties is expected.");
			return;
		}

		SmartHttpServer server = new SmartHttpServer(args[0]);
		server.start();
	}

	/**
	 * Constructor
	 * 
	 * @param configFileName path to configuration file
	 */
	public SmartHttpServer(String configFileName) {
		Properties properties = getProperties(configFileName);

		address = properties.getProperty("server.address");
		domainName = properties.getProperty("sever.domainName");
		port = Integer.parseInt(properties.getProperty("server.port"));
		workerThreads = Integer.parseInt(properties.getProperty("server.workerThreads"));
		sessionTimeout = Integer.parseInt(properties.getProperty("session.timeout"));
		documentRoot = Paths.get(properties.getProperty("server.documentRoot"));
		serverThread = new ServerThread();

//		String mimeConfigPath = properties.getProperty("server.mimeConfig");
		getMimeTypes(properties.getProperty("server.mimeConfig"));
		getWorkers(properties.getProperty("server.workers"));

	}

	/**
	 * Starts the server if its not already started
	 */
	protected synchronized void start() {
		if (isRunning.get()) {
			return;
		}
		threadPool = Executors.newFixedThreadPool(workerThreads);
		isRunning.set(true);
		serverThread.start();
		cleaner.start();

	}

	/**
	 * Stops the server if it is running
	 */
	protected synchronized void stop() {
		if (!isRunning.get()) {
			return;
		}
		isRunning.set(false);
		threadPool.shutdown();
	}

	/**
	 * Gets workers from the given workers configuration file
	 * 
	 * @param workersConfigPath workers configuration file
	 */
	private void getWorkers(String workersConfigPath) {
		workersMap = new HashMap<String, IWebWorker>();
		try (InputStream input = Files.newInputStream(Paths.get(workersConfigPath))) {
			Properties properties = new Properties();
			properties.load(input);

			properties.forEach((k, v) -> {
				if (workersMap.containsKey(k)) {
					throw new RuntimeException("More than 1 item has same path in: " + workersConfigPath);
				}
				String path = k.toString();
				String fqcn = v.toString();
				try {
					Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
					@SuppressWarnings("deprecation")
					Object newObject = referenceToClass.newInstance();
					IWebWorker iww = (IWebWorker) newObject;
					workersMap.put(path, iww);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		} catch (IOException exception) {
			throw new IllegalArgumentException("Unable to read properties from: " + workersConfigPath);
		}

	}

	/**
	 * Gets workers from the given mime configuration file
	 * 
	 * @param mimeConfigPath mime configuration file
	 */
	private void getMimeTypes(String mimeConfigPath) {
		try (InputStream input = Files.newInputStream(Paths.get(mimeConfigPath).toAbsolutePath())) {
			Properties properties = new Properties();
			properties.load(input);

			properties.forEach((k, v) -> mimeTypes.put(k.toString(), v.toString()));
		} catch (IOException exception) {
			exception.printStackTrace();
			throw new IllegalArgumentException("Unable to read properties from: " + mimeConfigPath);
		}
	}

	/**
	 * Gets properties from the given configuration file
	 * 
	 * @param configFileName configuration file
	 * @return properties
	 */
	private Properties getProperties(String configFileName) {
		try (InputStream input = Files.newInputStream(Paths.get(configFileName))) {
			Properties properties = new Properties();
			properties.load(input);

			return properties;
		} catch (IOException exception) {
			throw new IllegalArgumentException("Unable to read properties from: " + configFileName);
		}
	}

	/**
	 * Thread holding the server running. While the thread is running it accepts
	 * clients and creates {@link ClientWorker} to handle the client's requests
	 * 
	 * @author Andrej Ceraj
	 *
	 */
	protected class ServerThread extends Thread {

		@Override
		public void run() {
			try {
				@SuppressWarnings("resource")
				ServerSocket serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress((InetAddress) null, port));
				while (isRunning.get()) {
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * Thread used to clean expired cookies
	 * 
	 * @author Andrej Ceraj
	 *
	 */
	class CookieCleaner extends Thread {

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(5 * 60 * 1000);
					List<String> keysToRemove = new ArrayList<String>();
					for (String sidKey : sessions.keySet()) {
						SessionMapEntry entry = sessions.get(sidKey);
						if (entry.validUntil < System.currentTimeMillis() / 1000) {
							keysToRemove.add(sidKey);
						}
					}
					for (String key : keysToRemove) {
						sessions.remove(key);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Worker used to handle client's request. Based on the request, the worker does
	 * certain actions which results in returning the adequate response
	 * 
	 * @author Andrej Ceraj
	 *
	 */
	private class ClientWorker implements Runnable, IDispatcher {
		/**
		 * Client socket
		 */
		private Socket csocket;
		/**
		 * Stream from which data is read
		 */
		private PushbackInputStream istream;
		/**
		 * Stream to which data is written
		 */
		private OutputStream ostream;
		/**
		 * HTTP version
		 */
		private String version;
		/**
		 * HTTP method
		 */
		private String method;
		/**
		 * Client host name
		 */
		private String host;
		/**
		 * Map of parameters
		 */
		private Map<String, String> params = new HashMap<String, String>();
		/**
		 * Map of temporary parameters
		 */
		private Map<String, String> tempParams = new HashMap<String, String>();
		/**
		 * Map of persistent parameters
		 */
		private Map<String, String> permPrams = new HashMap<String, String>();
		/**
		 * List of cookies
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		/**
		 * Session ID
		 */
		private String SID;

		/**
		 * {@link RequestContext}
		 */
		private RequestContext context;

		/**
		 * Constructor
		 * 
		 * @param csocket client socket
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
			this.host = domainName;
		}

		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();
				List<String> request = readRequest(istream);
				if (request == null) {
					return;
				}
				if (request.size() < 1) {
					sendError(400, "Invalid request");
					return;
				}

				String firstLine = request.get(0);
				method = firstLine.split(" ")[0];
				String requestedPath = firstLine.split(" ")[1];
				version = firstLine.split(" ")[2].toUpperCase();
				if (!method.equals("GET") || (!version.equals("HTTP/1.0") && !version.equals("HTTP/1.1"))) {
					sendError(400, "Invalid header");
					return;
				}
				for (String line : request) {
					if (line.startsWith("Host")) {
						host = line.split(":")[1].trim();
					}
				}

				checkSession(request);

				String path;
				String paramString = null;
				int indexOfQ = requestedPath.indexOf('?');
				if (indexOfQ != -1) {
					path = requestedPath.substring(0, indexOfQ);
					paramString = requestedPath.substring(indexOfQ + 1);
				} else {
					path = requestedPath;
				}
				if (paramString != null) {
					parseParameters(paramString);
				}

				internalDispatchRequest(path, true);

			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					csocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}

		/**
		 * Based on the given URL path, the method does certain actions and as a result
		 * the adequate response is generated and returned to the client
		 * 
		 * @param urlPath    URL path
		 * @param directCall true if the client directly called the URL; false otherwise
		 */
		public void internalDispatchRequest(String urlPath, boolean directCall) {

			if (directCall && urlPath.startsWith("/private")) {
				try {
					sendError(404, "Not found");
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}

			if (urlPath.startsWith("/ext")) {
				String fqcn = "hr.fer.zemris.java.webserver.workers." + urlPath.substring(5);
				createContext();
				try {
					Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
					@SuppressWarnings("deprecation")
					Object newObject = referenceToClass.newInstance();
					IWebWorker iww = (IWebWorker) newObject;
					iww.processRequest(context);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return;
			}

			if (workersMap.containsKey(urlPath)) {
				createContext();
				try {
					workersMap.get(urlPath).processRequest(context);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return;
			}

			Path requestedFilePath = documentRoot.resolve(urlPath.substring(1));
			try {
				if (!Files.exists(requestedFilePath) || !Files.isReadable(requestedFilePath)) {
					sendError(404, "Not found");
					return;
				}

				String fileName = requestedFilePath.toFile().getName();
				String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
				String mimetype = mimeTypes.get(extension) == null ? "application/octet-stream"
						: mimeTypes.get(extension);

				createContext();

				if (extension.equals("smscr")) {
					String script = new String(Files.readAllBytes(requestedFilePath));
					SmartScriptParser parser = new SmartScriptParser(script);
					SmartScriptEngine engine = new SmartScriptEngine(parser.getDocumentNode(), context);
					engine.execute();
				} else {
					context.setMimeType(mimetype);
					context.setStatusCode(200);
					context.setContentLength(Files.size(requestedFilePath));
					InputStream fileStream = Files.newInputStream(requestedFilePath);
					byte[] buffer = new byte[1024];
					while (true) {
						int r = fileStream.read(buffer);
						if (r < 1)
							break;
						context.write(buffer, 0, r);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Checks for a valid cookie with session ID equals to this session ID. If valid
		 * cookie is found, its lifespan is expanded and map associated with the cookie
		 * session ID is loaded into client worker persistent parameters map. If the
		 * valid cookie is not found, new cookie is created
		 * 
		 * @param request request lines
		 */
		private void checkSession(List<String> request) {
			String sidCandidate = null;
			first: for (String line : request) {
				if (!line.startsWith("Cookie:")) {
					continue;
				}
				String[] variables = line.split(":")[1].strip().split(";");
				for (String variable : variables) {
					String name = variable.split("=")[0];
					String value = variable.split("=")[1];
					if (name.equals("sid")) {
						sidCandidate = value.substring(1, value.length() - 1);
						break first;
					}
				}
			}

			if (sidCandidate != null && sessions.containsKey(sidCandidate)) {
				SessionMapEntry entry = sessions.get(sidCandidate);
				if (host.equals(entry.host)) {
					if (entry.validUntil >= System.currentTimeMillis() / 1000) {
						entry.validUntil = System.currentTimeMillis() / 1000 + sessionTimeout;
						permPrams = entry.map;
						return;
					} else {
						sessions.remove(sidCandidate);
					}
				}
			}

			String sid = generateSID();
			SessionMapEntry newEntry = new SessionMapEntry(sid, host,
					System.currentTimeMillis() / 1000 + sessionTimeout);
			sessions.put(sid, newEntry);
			outputCookies.add(new RCCookie("sid", newEntry.sid, null, "/", host));
			permPrams = newEntry.map;

		}

		/**
		 * Generates 20 uppercase characters
		 * 
		 * @return generated session ID
		 */
		private String generateSID() {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < 20; i++) {
				char c = (char) (Math.abs(sessionRandom.nextInt() % 26) + 65);
				sb.append(c);
			}
			return sb.toString();
		}

		/**
		 * Parses parameters and adds them to parameters map
		 * 
		 * @param paramString string containing all parameters
		 */
		private void parseParameters(String paramString) {
			for (String param : paramString.split("&")) {
				String[] word = param.split("=");
				if (word.length == 2) {
					params.put(word[0], word[1]);
				}
			}
		}

		/**
		 * Creates a list of strings and reads client request into it
		 * 
		 * @param is input stream
		 * @return list of strings
		 * @throws IOException if it is unable to read from the input stream
		 */
		private List<String> readRequest(InputStream is) throws IOException {

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			l: while (true) {
				int b = is.read();
				if (b == -1)
					return null;
				if (b != 13) {
					bos.write(b);
				}
				switch (state) {
				case 0:
					if (b == 13) {
						state = 1;
					} else if (b == 10)
						state = 4;
					break;
				case 1:
					if (b == 10) {
						state = 2;
					} else
						state = 0;
					break;
				case 2:
					if (b == 13) {
						state = 3;
					} else
						state = 0;
					break;
				case 3:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				case 4:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				}
			}
			return Arrays.asList(bos.toString().split("\n"));
		}

		/**
		 * Sends error with the given status code and text
		 * 
		 * @param statusCode status code
		 * @param statusText status text
		 * @throws IOException if it is unable to write to output stream
		 */
		private void sendError(int statusCode, String statusText) throws IOException {
			ostream.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" + "Server: SmartHttpServer\r\n"
					+ "Content-Type: text/plain;charset=UTF-8\r\n" + "Content-Length: 0\r\n" + "Connection: close\r\n"
					+ "\r\n").getBytes(StandardCharsets.US_ASCII));
			ostream.flush();

		}

		/**
		 * Creates context if it is not already created
		 */
		private void createContext() {
			if (context == null) {
				context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this, SID);
			}
		}

	}

	/**
	 * Entry associated with session ID in sessions map. Entry holds information
	 * about session ID, host, how long is the entry valid and a map of parameters
	 * 
	 * @author Andrej Ceraj
	 *
	 */
	private static class SessionMapEntry {
		/**
		 * Session ID
		 */
		String sid;
		/**
		 * {@link ClientWorker} host
		 */
		String host;
		/**
		 * Time until the entry is valid
		 */
		long validUntil;
		/**
		 * Map of parameters
		 */
		Map<String, String> map;

		/**
		 * Constructor
		 * 
		 * @param sid        session ID
		 * @param host       Client's host
		 * @param validUntil time until the entry is valid
		 */
		public SessionMapEntry(String sid, String host, long validUntil) {
			this.sid = sid;
			this.host = host;
			this.validUntil = validUntil;
			map = new ConcurrentHashMap<String, String>();
		}
	}

}
