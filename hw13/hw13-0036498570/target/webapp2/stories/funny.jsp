<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true" %>
<%
	String[] colors = { "FFFFFF", "FF00FF", "00FF00", "808080", "F1C40F", "#D68910", "E74C3C" };
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Punny</title>
</head>
<body
	style='background-color: #<%=session.getAttribute("pickedBgCol")%>;'>

	<p>
		<font
			color="#<%Random rand = new Random();
			out.print(colors[rand.nextInt(colors.length)]);%>">
			Bila dva slapa jedan se slijeva drugi zdesna.<br> Dođu Angelina
			Jolie i Brad Pitt u pekaru i kaže Angelina: "Može meni burek, a Bredu
			Pittu".
		</font>
	</p>
</body>
</html>