package hr.fer.zemris.java.webserver.workers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class BgColorWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String bgcolor = context.getParameter("bgcolor");

		String html = "<!DOCTYPE html>\n"
						+ "<html>\n"
							+ "<body>\n"
								+ "<a href=\"/index2.html\">Home</a><br>";
		context.write(html);

		if (bgcolor != null) {
			Pattern colorPattern = Pattern.compile("[0-9a-fA-F]{6}");
			Matcher m = colorPattern.matcher(bgcolor);
			if (m.matches()) {
				context.setPersistentParameter("bgcolor", bgcolor);
				context.write("<p>Color is updated.");
			}
		}
		context.write("</body>"
				+ "</html>");
	}

}
