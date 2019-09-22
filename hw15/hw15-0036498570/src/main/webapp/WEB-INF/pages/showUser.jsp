<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:choose>
		<c:when test="${viewedUser==null}">
      		User does not exist!
    	</c:when>
		<c:otherwise>
		
			<c:choose>
				<c:when test="${sessionScope['current.user.id'] == null}">
	     			<h2>Not logged in</h2>
	     		</c:when>
	     		<c:otherwise>
	     			<h2>${sessionScope['current.user.fn']} ${sessionScope['current.user.ln']} </h2>
	     			<p>
						<a href="../logout">Logout</a>
					</p>
	     		</c:otherwise>
			</c:choose>
			
			
			<h1>
				<c:out value="${viewedUser.nick}" />
			</h1>
			<c:if test="${!viewedUser.entries.isEmpty()}">
				<ul>
					<c:forEach var="e" items="${viewedUser.entries}">
						<li><div style="font-weight: bold">
								<a href="${viewedUser.nick}/${e.id}"><c:out value="${e.title}"/>
								</a><c:out value="${e.createdAt}"/>
							</div></li>
					</c:forEach>
				</ul>
			</c:if>
			
			<c:choose>
				<c:when test="${sessionScope['current.user.nick'] == viewedUser.nick}">
	     			<a href="${viewedUser.nick}/new">New entry</a>
	     		</c:when>
			</c:choose>
		</c:otherwise>
	</c:choose>
	<p><a href="<%= request.getServletContext().getContextPath()%>/servleti/main" >Homepage</a></p>
</body>
</html>