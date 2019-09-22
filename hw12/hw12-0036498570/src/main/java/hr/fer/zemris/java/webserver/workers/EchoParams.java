package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {

		String header = "<html>\n<head>\n" + "<title>Tablica parametara</title>\n" + "</head>\n" + "<body>\n"
				+ "<table>\n" + "<thead>\n" + "<tr><th>Ime parametra</th><th>Vrijednost</th></tr>\n" + "</thead>"
				+ "<tbody>";
		context.write(header);

		StringBuilder sb = new StringBuilder();
		for (String name : context.getParameterNames()) {
			sb.append("<tr>");
			sb.append("<td>").append(name).append("</td>");
			sb.append("<td>").append(context.getParameter(name)).append("</td>");
			sb.append("</tr>");
		}

		context.write(sb.toString());
		context.write("</tbody>\n" + "</table>\n" + "</body>\n" + "</html>");

	}

}

/*
 * 
 * <html> <head> <title>Tablica Fibonaccijevih brojeva</title> </head> <body>
 * <h1>Fibonaccijevi brojevi</h1> <p>U nastavku je prikazana tablica prvih 10
 * Fibonaccijevih brojeva.</p>
 * 
 * <table> <thead> <tr><th>Redni broj</th><th>Fibonaccijev broj</th></tr>
 * </thead> <tbody> <tr><td>1</td><td>0</td></tr> <tr><td>2</td><td>1</td></tr>
 * <tr><td>3</td><td>1</td></tr> <tr><td>4</td><td>2</td></tr>
 * <tr><td>5</td><td>3</td></tr> <tr><td>6</td><td>5</td></tr>
 * <tr><td>7</td><td>8</td></tr> <tr><td>8</td><td>13</td></tr>
 * <tr><td>9</td><td>21</td></tr> <tr><td>10</td><td>34</td></tr> </tbody>
 * </table> </body> </html>
 * 
 */
