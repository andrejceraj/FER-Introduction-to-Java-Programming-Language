<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trigonometric</title>
</head>
<body style='background-color: #<%= session.getAttribute("pickedBgCol") %>;'>
	<table border="1">
		<thead>
			<tr>
				<th>Value</th>
				<th>sin(value)</th>
				<th>cos(value)</th>
			</tr>
		</thead>

		<%
			int a = (int) session.getAttribute("a");
			String[] sinTable = (String[]) session.getAttribute("sinTable");
			String[] cosTable = (String[]) session.getAttribute("cosTable");
			for (int i = 0; i < sinTable.length; i++) {
				out.print("<tr><td>" + (a + i) + "</td><td>" + sinTable[i] + "</td><td>" + cosTable[i] + "</td></tr>");
			}
		%>
	</table>

</body>
</html>