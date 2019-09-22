<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	<form action="new" method="post" >
		<div class="container">
			<label for="title" ><b>Title</b></label>
			<input type="text" placeholder="Enter title" name="title" required ><br/>
			
			<label for="text"><b>Text</b></label>
			<textarea name="text" rows="10" cols="150" required></textarea><br/>

			<button type="submit">Submit</button>
		</div>
	</form>
	<p><a href="<%= request.getServletContext().getContextPath()%>/servleti/main" >Homepage</a></p>

</body>
</html>