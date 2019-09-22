package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.util.Utils;
import hr.fer.zemris.java.hw15.web.forms.RegistrationForm;

/**
 * Servlet used for handling the user registration.
 * 
 * @author Andrej Ceraj
 *
 */
@WebServlet(name = "register", urlPatterns = { "/servleti/register" })
public class RegisterServlet extends HttpServlet {
	/**
	 * Defaultly generated serial version id
	 */
	private static final long serialVersionUID = -4603177849580274634L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String firstName = req.getParameter("firstName").trim();
		String lastName = req.getParameter("lastName").trim();
		String email = req.getParameter("email").trim();
		String nick = req.getParameter("nick").trim();
		String password = req.getParameter("password");

		RegistrationForm form = new RegistrationForm(firstName, lastName, email, nick, password);
		form.validate();
		if (form.hasErrors()) {
			req.setAttribute("form", form);
			req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
			return;
		}

		String passwordHash = Utils.encryptPassword(password);
		BlogUser newUser = new BlogUser(firstName, lastName, nick, email, passwordHash);
		DAOProvider.getDAO().createUser(newUser);
		req.getSession().setAttribute("current.user.id", newUser.getId());
		req.getSession().setAttribute("current.user.fn", firstName);
		req.getSession().setAttribute("current.user.ln", lastName);
		req.getSession().setAttribute("current.user.nick", nick);
		resp.sendRedirect("main");

	}

}
