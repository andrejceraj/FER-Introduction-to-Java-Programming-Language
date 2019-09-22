<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<style>
	label {
		float: left;
		width: 100px;
	}
</style>
<meta charset="UTF-8">
<title>Homepage</title>
</head>
<body>
	<c:choose>
		<c:when test="${sessionScope['current.user.id'] == null}">
     		<h2>Not logged in</h2>
			<p><font color="red">
			<%
	 		try {
	 			String error = (String) request.getSession().getAttribute("loginError");
	 			if (error != null) out.print(error);
	 			request.getSession().setAttribute("loginError", null);
	 		} catch (Exception e) {
	 			//ignorable
	 		}
	 		%></font>
			</p>
			<form action="login" method="post">
				<div class="container">
					<label for="nick"><b>Nick</b></label><input type="text" value="<% 
						String n = (String) request.getSession().getAttribute("nick");
						if (n != null) out.print(n);%>"
					placeholder="Enter nick" name="nick" required><br />
			 	<%
					request.getSession().setAttribute("nick", null);
				%>
				<label for="password"><b>Password</b></label> <input type="password" placeholder="Enter password" name="password" required><br />
				<button type="submit">Login</button>
				</div>
			</form>
			</c:when>
		<c:otherwise>
			<h2>${sessionScope['current.user.fn']} ${sessionScope['current.user.ln']} </h2>
			<a href="logout">Logout</a></br>
		</c:otherwise>
	</c:choose>

	<a href="register">Register</a>

	<h2>List of users</h2>
		<ul>
		<%
			@SuppressWarnings("unchecked")
				List<String> usersNicks = (List<String>) request.getAttribute("usersNicks");
				for (String nick : usersNicks) {
					out.println("<li><a href=\"author/" + nick + "\">" + nick + "</a></li>");
				}
		%>
		</ul>

</body>
</html>