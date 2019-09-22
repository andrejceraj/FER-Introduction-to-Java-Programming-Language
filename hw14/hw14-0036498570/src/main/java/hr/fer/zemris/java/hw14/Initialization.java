package hr.fer.zemris.java.hw14;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;
import hr.fer.zemris.java.hw14.model.examples.PollOptions;
import hr.fer.zemris.java.hw14.model.examples.Polls;

/**
 * Upon starting server, this {@link ServletContextListener} initializes servlet
 * context. It then create and fill database tables if missing.
 * <p>
 * Upon shuting server down, the listener destroys servlet context.
 * </p>
 * 
 * @author Andrej Ceraj
 *
 */
@WebListener
public class Initialization implements ServletContextListener {

	/**
	 * SQL command for creating table "Polls"
	 */
	private static final String CREATE_POLLS_TABLE = "CREATE TABLE Polls\n"
			+ "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n" + "title VARCHAR(150) NOT NULL,\n"
			+ "message CLOB(2048) NOT NULL\n" + ")";

	/**
	 * SQL command for creating table "PollOptions"
	 */
	private static final String CREATE_POLLOPTIONS_TABLE = "CREATE TABLE PollOptions\n"
			+ "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n" + "optionTitle VARCHAR(100) NOT NULL,\n"
			+ "optionLink VARCHAR(150) NOT NULL,\n" + "pollID BIGINT,\n" + "votesCount BIGINT,\n"
			+ "FOREIGN KEY (pollID) REFERENCES Polls(id)\n" + ")";

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		String propertiesFileName = sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties");

		Properties properties = new Properties();
		try (InputStream input = Files.newInputStream(Paths.get(propertiesFileName))) {
			properties.load(input);
		} catch (IOException exception) {
			throw new IllegalArgumentException("Unable to read properties from: " + propertiesFileName);
		}

		String host = properties.getProperty("host");
		String port = properties.getProperty("port");
		String name = properties.getProperty("name");
		String user = properties.getProperty("user");
		String password = properties.getProperty("password");
		if (host == null || port == null || name == null || user == null || password == null) {
			throw new RuntimeException("Some properties are missing");
		}

		String connectionURL = "jdbc:derby://" + host + ":" + port + "/" + name + ";user=" + user + ";password="
				+ password;

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}
		cpds.setJdbcUrl(connectionURL);
		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);

		try {
			int ct = createTable(cpds, CREATE_POLLS_TABLE);
			if (ct == 1) {
				System.out.println("Table Polls created");
			} else if (ct == 0) {
				System.out.println("Table Polls already exist");
			} else {
				throw new SQLException();
			}
			ct = createTable(cpds, CREATE_POLLOPTIONS_TABLE);
			if (ct == 1) {
				System.out.println("Table PollOptions created");
			} else if (ct == 0) {
				System.out.println("Table PollOptions already exist");
			} else {
				throw new SQLException();
			}

			try {
				fillPollsIfEmpty(cpds);
			} catch (SQLException e) {
				System.out.println("Unable to fill polls");
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println("Unable to get connection!");
			e.printStackTrace();
		}

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Creates table with the given sql command.
	 * 
	 * @param cpds combo polled data source
	 * @param sql  command
	 * @return 1 if table is created, 0 if table already exist, -1 if error occured
	 * @throws SQLException if unable to get connection.
	 */
	private int createTable(ComboPooledDataSource cpds, String sql) throws SQLException {
		Connection con = cpds.getConnection();
		PreparedStatement pst = null;

		// Create table if not exist
		int tableCreated = -1;
		try {
			pst = con.prepareStatement(sql);
			pst.executeUpdate();
			tableCreated = 1;
		} catch (SQLException exception) {
			if (exception.getSQLState().equals("X0Y32")) {
				tableCreated = 0;
			}
		} finally {
			try {
				if (pst != null) {
					pst.close();
				}
			} catch (Exception ignorable) {
			}
		}
		return tableCreated;
	}

	/**
	 * Fills "Polls" table and "PollOption" table if empty.
	 * 
	 * @param cpds combo polled data source
	 * @throws SQLException if unable to get connection
	 */
	private void fillPollsIfEmpty(ComboPooledDataSource cpds) throws SQLException {
		Connection con = cpds.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("SELECT * FROM POLLS");
			ResultSet resultSet = pst.executeQuery();
			if (!resultSet.next()) {
				fill(con, pst, Polls.getBandPoll(), PollOptions.getBandPollOptions());
				fill(con, pst, Polls.getMoviePoll(), PollOptions.getMoviePollOptions());
			}
		} finally {
			try {
				if (pst != null) {
					pst.close();
				}
			} catch (Exception ignorable) {
			}
		}

	}

	/**
	 * Inserts given {@link Poll} into "Polls" table and inserts given
	 * {@link PollOption}s into "PollOptions" table.
	 * 
	 * @param con         connection
	 * @param pst         prepared statement
	 * @param poll        poll
	 * @param pollOptions poll options
	 * @throws SQLException if unable to insert rows
	 */
	private void fill(Connection con, PreparedStatement pst, Poll poll, List<PollOption> pollOptions)
			throws SQLException {
		pst = con.prepareStatement(
				"INSERT INTO Polls (title, message) VALUES ('" + poll.getTitle() + "', '" + poll.getMessage() + "')",
				PreparedStatement.RETURN_GENERATED_KEYS);

		if (pst.executeUpdate() == 0) {
			throw new SQLException("Insertion failed");
		}
		ResultSet generatedKeys = pst.getGeneratedKeys();
		generatedKeys.next();
		long pollId = generatedKeys.getLong(1);
		for (PollOption option : pollOptions) {
			option.setPollId(pollId);

			pst = con.prepareStatement("INSERT INTO PollOptions (optionTitle, optionLink, pollId, votesCount) VALUES"
					+ " ('" + option.getOptionTitle() + "', '" + option.getOptionLink() + "', " + option.getPollId()
					+ ", 0)");
			pst.executeUpdate();
		}
	}

}