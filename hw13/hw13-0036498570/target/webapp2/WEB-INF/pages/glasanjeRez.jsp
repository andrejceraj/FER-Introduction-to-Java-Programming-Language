<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<html>
<head>
<meta charset="UTF-8">
<style type="text/css">
table.rez td {
	text-align: center;
}
</style>
</head>
<body>
	<h1>Rezultati glasanja</h1>
	<p>Ovo su rezultati glasanja.</p>
	<table border="1" cellspacing="0" class="rez">
		<thead>
			<tr>
				<th>Bend</th>
				<th>Broj glasova</th>
			</tr>
		</thead>
		<tbody>
			<%
				try {
					Map<String, Integer> resultsMap = (Map<String, Integer>) request.getAttribute("resultsMap");
					for (Entry<String, Integer> entry : resultsMap.entrySet()) {
						out.println("<tr><td>" + entry.getKey() + "</td><td>" + entry.getValue() + "</td></tr>");
					}

				} catch (Exception e) {
					request.getRequestDispatcher("/WEB-INF/pages/glasanjeError.jsp").forward(request, response);
				}
			%>
		</tbody>
	</table>
</body>
</html>