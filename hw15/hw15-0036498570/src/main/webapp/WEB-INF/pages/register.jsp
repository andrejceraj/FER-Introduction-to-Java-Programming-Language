<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="register" method="post" >
		<div class="container">
			
			<div>
		 		<div>
		  			<span class="formLabel">First name</span><input type="text" name="firstName" value='<c:out value="${form.firstName}"/>' size="20">
				</div>
				<c:if test="${form.hasErrorOn('firstName')}">
				 	<div class="Error: "><c:out value="${form.getErrorOn('firstName')}"/></div>
				</c:if>
			</div>
			
			<div>
		 		<div>
		  			<span class="formLabel">Last name</span><input type="text" name="lastName" value='<c:out value="${form.lastName}"/>' size="20">
				</div>
				<c:if test="${form.hasErrorOn('lastName')}">
				 	<div class="Error: "><c:out value="${form.getErrorOn('lastName')}"/></div>
				</c:if>
			</div>
			
			<div>
		 		<div>
		  			<span class="formLabel">Email</span><input type="text" name="email" value='<c:out value="${form.email}"/>' size="50">
				</div>
				<c:if test="${form.hasErrorOn('email')}">
				 	<div class="Error: "><c:out value="${form.getErrorOn('email')}"/></div>
				</c:if>
			</div>
			
			<div>
		 		<div>
		  			<span class="formLabel">Nick</span><input type="text" name="nick" value='<c:out value="${form.nick}"/>' size="20">
				</div>
				<c:if test="${form.hasErrorOn('nick')}">
				 	<div class="Error: "><c:out value="${form.getErrorOn('nick')}"/></div>
				</c:if>
			</div>
			
			<div>
		 		<div>
		  			<span class="formLabel">Password</span><input type="password" name="password" value='<c:out value=""/>' size="20">
				</div>
			</div>
			<button type="submit">Register</button>
		</div>
	</form>
	<p><a href="<%= request.getServletContext().getContextPath()%>/servleti/main" >Homepage</a></p>
	
</body>
</body>
</html>