package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class SumWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		int a;
		int b;
		String image1 = "image1.jpg";
		String image2 = "image2.jpg";
		try {
			a = Integer.parseInt(context.getParameter("a"));
		} catch (NumberFormatException e) {
			a = 1;
		}
		try {
			b = Integer.parseInt(context.getParameter("b"));
		} catch (NumberFormatException e) {
			b = 2;
		}
		String result = new String(Integer.toString(a + b));
		context.setTemporaryParameter("varA", Integer.toString(a));
		context.setTemporaryParameter("varB", Integer.toString(b));
		context.setTemporaryParameter("imgName", (a + b) % 2 == 0 ? image2 : image1);
		context.setTemporaryParameter("zbroj", result);

		context.getDispatcher().dispatchRequest("/private/pages/calc.smscr");
	}

}
