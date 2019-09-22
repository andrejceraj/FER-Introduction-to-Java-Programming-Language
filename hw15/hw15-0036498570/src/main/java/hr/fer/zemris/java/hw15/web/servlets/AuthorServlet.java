package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.web.forms.CommentForm;

/**
 * Servlet used to handle all requests starting with "/servleti/author/*". The
 * servlet manages viewing blog entries, creating new blog entry, editing blog
 * entry, and posting comments on blog entries.
 * 
 * @author Andrej Ceraj
 *
 */
@WebServlet(name = "author", urlPatterns = { "/servleti/author/*" })
public class AuthorServlet extends HttpServlet {
	/**
	 * Defaultly generated serial version id
	 */
	private static final long serialVersionUID = 1338459959525200638L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		System.out.println();

		String[] segments = req.getPathInfo().split("/");
		String nick = segments[1];
		BlogUser viewedUser = DAOProvider.getDAO().getUserByNick(nick);
		req.setAttribute("viewedUser", viewedUser);

		if (segments.length == 2) {
			req.getRequestDispatcher("/WEB-INF/pages/showUser.jsp").forward(req, resp);
			return;
		}

		String parameter = segments[2].toLowerCase();
		if (parameter.equals("new")) {
			if (!authorityValidated(req, nick)) {
				req.getRequestDispatcher("/WEB-INF/pages/invalidPath.jsp").forward(req, resp);
				return;
			}

			req.getRequestDispatcher("/WEB-INF/pages/newEntry.jsp").forward(req, resp);
		} else if (parameter.equals("edit")) {
			if (!authorityValidated(req, nick)) {
				req.getRequestDispatcher("/WEB-INF/pages/invalidPath.jsp").forward(req, resp);
				return;
			}

			try {
				long entryId = Long.parseLong(segments[3]);
				BlogEntry editingEntry = DAOProvider.getDAO().getBlogEntry(entryId);
				req.setAttribute("editingEntry", editingEntry);
				req.getRequestDispatcher("/WEB-INF/pages/editEntry.jsp").forward(req, resp);
			} catch (Exception e) {
				req.getRequestDispatcher("/WEB-INF/pages/invalidPath.jsp").forward(req, resp);
			}

		} else {
			try {
				long entryId = Long.parseLong(parameter);
				BlogEntry entry = DAOProvider.getDAO().getBlogEntry(entryId);
				req.setAttribute("blogEntry", entry);
				req.getRequestDispatcher("/WEB-INF/pages/showEntry.jsp").forward(req, resp);
			} catch (Exception e) {
				req.getRequestDispatcher("/WEB-INF/pages/invalidPath.jsp").forward(req, resp);
			}

		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] segments = req.getPathInfo().split("/");
		System.out.println(req.getPathInfo());
		if (segments.length < 3 && segments.length > 4) {
			req.getRequestDispatcher("/WEB-INF/pages/invalidPath.jsp").forward(req, resp);
			return;
		}
		String nick = segments[1];
		String option = segments[2].toLowerCase();
		BlogUser viewedUser = DAOProvider.getDAO().getUserByNick(nick);
		req.setAttribute("viewedUser", viewedUser);

		if (option.equals("new")) {
			if (!authorityValidated(req, nick)) {
				req.getRequestDispatcher("/WEB-INF/pages/invalidPath.jsp").forward(req, resp);
				return;
			}

			BlogEntry newEntry = new BlogEntry();
			newEntry.setTitle(req.getParameter("title"));
			newEntry.setText(req.getParameter("text"));
			Date currentDateTime = new Date(System.currentTimeMillis());
			newEntry.setCreatedAt(currentDateTime);
			newEntry.setLastModifiedAt(currentDateTime);
			newEntry.setCreator(DAOProvider.getDAO().getUserByNick(nick));
			DAOProvider.getDAO().createEntry(newEntry);
			resp.sendRedirect("../" + nick);
		} else if (option.equals("edit")) {
			if (!authorityValidated(req, nick)) {
				req.getRequestDispatcher("/WEB-INF/pages/invalidPath.jsp").forward(req, resp);
				return;
			}

			try {
				long entryId = Long.parseLong(req.getParameter("entryId"));
				BlogEntry entry = DAOProvider.getDAO().getBlogEntry(entryId);
				if (entry == null) {
					req.getRequestDispatcher("/WEB-INF/pages/invalidPath.jsp").forward(req, resp);
				}
				entry.setTitle(req.getParameter("title"));
				entry.setText(req.getParameter("text"));
				entry.setLastModifiedAt(new Date(System.currentTimeMillis()));
				DAOProvider.getDAO().updateEntry(entry);
				resp.sendRedirect("../" + nick);
			} catch (Exception e) {
				req.getRequestDispatcher("/WEB-INF/pages/invalidPath.jsp").forward(req, resp);
			}
		} else {
			if (!segments[3].equals("comment")) {
				req.getRequestDispatcher("/WEB-INF/pages/invalidPath.jsp").forward(req, resp);
				return;
			}

			String content = req.getParameter("comment").trim();
			String email = null;
			String currentUserNick = (String) req.getSession().getAttribute("current.user.nick");
			if (currentUserNick == null) {
				email = req.getParameter("email");
			} else {
				email = DAOProvider.getDAO().getUserByNick(currentUserNick).getEmail();
			}
			CommentForm form = new CommentForm(email, content);
			form.validate();
			if (form.hasErrors()) {
				try {
					long entryId = Long.parseLong(segments[2]);
					req.setAttribute("form", form);
					BlogEntry entry = DAOProvider.getDAO().getBlogEntry(entryId);
					req.setAttribute("blogEntry", entry);
					req.getRequestDispatcher("/WEB-INF/pages/showEntry.jsp").forward(req, resp);
					return;
				} catch (Exception e) {
					req.getRequestDispatcher("/WEB-INF/pages/invalidPath.jsp").forward(req, resp);
					return;
				}
			}

			BlogComment comment = new BlogComment();
			comment.setMessage(content);
			comment.setPostedOn(new Date(System.currentTimeMillis()));
			BlogEntry entry = DAOProvider.getDAO().getBlogEntry(Long.parseLong(segments[2]));
			comment.setBlogEntry(entry);

			if (currentUserNick == null) {
				comment.setUsersEMail(email);
			} else {
				comment.setUsersEMail(DAOProvider.getDAO().getUserByNick(currentUserNick).getEmail());
			}
			DAOProvider.getDAO().createComment(comment);
			resp.sendRedirect(
					req.getServletContext().getContextPath() + "/servleti/author/" + nick + "/" + segments[2]);
		}
	}

	/**
	 * Checks if the user is currently viewing its own account.
	 * 
	 * @param req  servlet request
	 * @param nick nickname
	 * @return true if the user is currently viewing its own account (entries) ;
	 *         false otherwise
	 */
	private boolean authorityValidated(HttpServletRequest req, String nick) {
		return nick.equals(req.getSession().getAttribute("current.user.nick"));
	}

}
