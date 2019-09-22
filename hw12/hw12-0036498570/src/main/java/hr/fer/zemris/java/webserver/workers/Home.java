package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class Home implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String bgValue = context.getPersistentParameter("bgcolor");
		bgValue = bgValue == null ? "7F7F7F" : bgValue;
		context.setTemporaryParameter("background", bgValue);
		
		context.getDispatcher().dispatchRequest("/private/pages/home.smscr");
	}

}
