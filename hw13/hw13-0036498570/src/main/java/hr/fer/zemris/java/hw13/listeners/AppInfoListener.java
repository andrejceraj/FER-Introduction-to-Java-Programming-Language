package hr.fer.zemris.java.hw13.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * {@link ServletContextListener} used to store current time in milliseconds to
 * servlet context, so when the mentioned data is reached, it is able to display
 * for how long this server is already running.
 * 
 * @author Andrej Ceraj
 *
 */
public class AppInfoListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		context.setInitParameter("timeStarted", String.valueOf(System.currentTimeMillis()));
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
