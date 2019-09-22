<%@page import="java.awt.Color"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Set color</title>
</head>
<body style='background-color: #<%= session.getAttribute("pickedBgCol") %>;'>
	<h1>Background color chooser</h1>
	<a href="setColor?color=white" >WHITE</a><br>
	<a href="setColor?color=red" >RED</a><br>
	<a href=setColor?color=green >GREEN</a><br>
	<a href="setColor?color=cyan" >CYAN</a><br>

</body>
</html>