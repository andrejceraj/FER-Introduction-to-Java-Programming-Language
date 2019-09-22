<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>webapp2</title>
</head>
<body
	style='background-color: #<%=session.getAttribute("pickedBgCol")%>;'>
	<h1>My cool homepage</h1>
	<a href="colors.jsp">Background color chooser</a><br>
	
	<a href="trigonometric?a=0&b=90">Test trigonometric (a = 0; b = 90)</a>
	<form action="trigonometric" method="GET">
		Početni kut:<br>
		<input type="number" name="a" min="0" max="360" step="1" value="0"><br>
		Završni kut:<br>
		<input type="number" name="b" min="0" max="360" step="1" value="360"><br>
		<input type="submit" value="Tabeliraj"><input type="reset"
			value="Reset">
	</form>
	
	<p>
		<a href="stories/funny.jsp">Funny story</a><br>
	</p>
	
	<p>
		<a href="reportImage">OS usage</a><br>
	</p>
	
	<a href="powers?a=1&b=100&n=3">Powers</a><br>
	<form action="powers" method="GET">
		Starting value:
		<input type="number" name="a" min="-100" max="100" step="1" value="0">
		Ending value:
		<input type="number" name="b" min="-100" max="100" step="1" value="50"><br>
		Number of powers:
		<input type="number" name="n" min="1" max="5" step="1" value="3"><br>
		<input type="submit" value="Generate .xls file">
	</form>
	
	<p>
		<a href="appinfo.jsp">Time running</a><br>
	</p>
	
	<p>
		<a href="glasanje" >Vote for favorite band</a>
	</p>
	
	<p>
		<a href="glasanje-rezultati" >Vote results</a>
	</p>

</body>
</html>