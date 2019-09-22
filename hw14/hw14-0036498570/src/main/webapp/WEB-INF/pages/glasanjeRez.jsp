<%@page import="hr.fer.zemris.java.hw14.model.PollOption"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
	<table border="1" <%= "cellspacing=\"0\"" %> class="rez">
		<thead>
			<tr>
				<th>Item</th>
				<th>Votes</th>
			</tr>
		</thead>
		<tbody>
			<%
				try {
					@SuppressWarnings("unchecked")
					Map<PollOption, Integer> resultsMap = (Map<PollOption, Integer>) request.getSession().getAttribute("resultsMap");
					for (Entry<PollOption, Integer> entry : resultsMap.entrySet()) {
						out.println("<tr><td>" + entry.getKey().getOptionTitle() + "</td><td>" + entry.getValue() + "</td></tr>");
					}

				} catch (Exception e) {
					request.getRequestDispatcher("/WEB-INF/pages/glasanjeError.jsp").forward(request, response);
				}
			%>
		</tbody>
	</table>

	<h2>Grafički prikaz rezultata</h2>
	<img alt="Pie-chart" src="glasanje-grafika" width="1000" height="450" />

	<h2>Rezultati u XLS formatu</h2>
	<p>
		Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a>
	</p>

	<h2>Razno</h2>
	<p>Primjeri pjesama pobjedničkih bendova:</p>
	<ul>
		<%
			try {
				@SuppressWarnings("unchecked")
				Map<PollOption, Integer> resultsMap = (Map<PollOption, Integer>) request.getSession().getAttribute("resultsMap");
				int maxVotes = -1;
				for (Entry<PollOption, Integer> entry : resultsMap.entrySet()) {
					if (entry.getValue() >= maxVotes) {
						maxVotes = entry.getValue();
						out.println("<li><a href=\"" + entry.getKey().getOptionLink() + "\" target=\"_blank\">"
								+ entry.getKey().getOptionTitle() + "</a></li>");
					} else {
						break;
					}
				}

			} catch (Exception e) {
				System.out.println("error u listanju pjesama");
				request.getRequestDispatcher("/WEB-INF/pages/glasanjeError.jsp").forward(request, response);
			}
		%>
	</ul>
	
	<p>
		<a href="index.jsp" >Home page</a>
	</p>


</body>
</html>