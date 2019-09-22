<%@page import="java.awt.Color"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Background color chooser</h1>
	<%
		Color color;
	%>
	<a href="setColor" <%color = Color.WHITE;%>>WHITE</a>
	<a href="setColor" <%color = Color.RED;%>>RED</a>
	<a href="setColor" <%color = Color.GREEN;%>>GREEN</a>
	<a href="setColor" <%color = Color.CYAN;%>>CYAN</a>

</body>
</html>