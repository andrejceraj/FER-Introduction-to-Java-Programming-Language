<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<style>
	label {
		float: left;
		width: 100px;
	}
</style>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="../edit" method="post" >
		<div class="container">
			<label for="title" ><b>Title</b></label>
			<input type="text" value="<c:out value="${ editingEntry.title }" />" name="title" required /><br/>
			
			<label for="text"><b>Text</b></label>
			<textarea name="text" rows="10" cols="150" required><c:out value="${ editingEntry.text }" /></textarea><br/>
			<input hidden="true" name="entryId" value="${editingEntry.id}" />

			<button type="submit">Submit</button>
		</div>
	</form>
	<p><a href="<%= request.getServletContext().getContextPath()%>/servleti/main" >Homepage</a></p>

</body>
</html>