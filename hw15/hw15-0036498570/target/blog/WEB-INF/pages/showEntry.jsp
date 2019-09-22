<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<body>
	<c:choose>
		<c:when test="${blogEntry==null}">
      		Nema unosa!
    	</c:when>
		<c:otherwise>
		
			<c:choose>
				<c:when test="${sessionScope['current.user.id'] == null}">
	     			<h2>Not logged in</h2>
	     		</c:when>
	     		<c:otherwise>
	     			<h2>${sessionScope['current.user.fn']} ${sessionScope['current.user.ln']} </h2>
	     			<p><a href="../../logout" >Logout</a></p>
	     		</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${sessionScope['current.user.nick'] == viewedUser.nick}">
					<a href="edit/${blogEntry.id}">Edit entry</a>
	     		</c:when>
			</c:choose>
			<h1>
				<c:out value="${blogEntry.title}" />
			</h1>
			<p>
				<c:out value="${blogEntry.text}" />
			</p>
			<c:if test="${!blogEntry.comments.isEmpty()}">
				<ul>
					<c:forEach var="e" items="${blogEntry.comments}">
						<li><div style="font-weight: bold">
								[Korisnik=
								<c:out value="${e.usersEMail}" />
								]
								<c:out value="${e.postedOn}" />
							</div>
							<div style="padding-left: 10px;">
								<c:out value="${e.message}" />
							</div></li>
					</c:forEach>
				</ul>
			</c:if>
			<form method="post" action="${blogEntry.id}/comment" >
  				<textarea placeholder="Comment..." name="message" rows="3" cols="100" required ></textarea><br/>
  				<button type="submit" >Comment</button>
			</form>
		</c:otherwise>
	</c:choose>
	<p><a href="<%= request.getServletContext().getContextPath()%>/servleti/main" >Homepage</a></p>

</body>
</html>
