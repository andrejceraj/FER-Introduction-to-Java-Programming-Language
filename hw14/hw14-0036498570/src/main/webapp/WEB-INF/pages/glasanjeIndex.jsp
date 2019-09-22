<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Glasanje</title>
</head>
<body>
	<h1>${ currentPoll.title }</h1>
	<p>${ currentPoll.message }</p>
	<ol>
		<%
			try {
				@SuppressWarnings("unchecked")
				List<String> optionList = (List<String>) request.getAttribute("votingOptionList");
				for (String line : optionList) {
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