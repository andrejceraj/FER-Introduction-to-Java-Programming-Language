<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>webapp2</title>
</head>
<body style='background-color: #<% session.getAttribute("bgCol");%>;'>
	<h1>Home page</h1>
	<p>Izaberi boju:</p>	
	<a href="/asdfasdf" >Background color chooser</a>
</body>
</html>