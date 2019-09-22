<%@page import="java.util.Date"%>
<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%!private String getTimeRunning(ServletContext context) {
		long milis = System.currentTimeMillis() - Long.parseLong(context.getInitParameter("timeStarted"));
		long seconds = (milis / 1000) % 60;
		long minutes = (milis / 1000 / 60) % 60;
		long hours = (milis / 1000 / 60 / 60) % 24;
		long days = (milis / 1000 / 60 / 60 / 24);
		return days + " days " + hours + " hours " + minutes + " minutes " + seconds + " seconds " + (milis % 1000)
				+ " milis.";
	}%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Time running</title>
</head>
<body
	style='background-color: #<%=session.getAttribute("pickedBgCol")%>;'>

	<h4>
		Time this server is running is: 
		<%=getTimeRunning(request.getServletContext())%></h4>
</body>
</html>