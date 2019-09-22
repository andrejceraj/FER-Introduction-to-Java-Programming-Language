<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Glasanje</title>
</head>
<body style='background-color: #<%= session.getAttribute("pickedBgCol") %>;'>
	<h1>Glasaj za omiljeni band</h1>
	<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na
		link kako biste glasali!</p>
	<ol>
		<%
			try {
				@SuppressWarnings("unchecked")
				List<String> bands = (List<String>) request.getAttribute("bandsForVoting");
				for (String line : bands) {
					String[] words = line.split("\\t");
					out.println("<li><a href=\"glasanje-glasaj?id=" + words[0] + "\">" + words[1] + "</a></li>");
				}
			} catch (Exception e) {
				request.getRequestDispatcher("/WEB-INF/pages/glasanjeError.jsp").forward(request, response);
			}
		%>
	</ol>
</body>
</html>