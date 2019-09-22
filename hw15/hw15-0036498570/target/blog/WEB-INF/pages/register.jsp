<%@page import="java.util.Map"%>
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
	<p>
		<font color="red">
		<%
		try{
			@SuppressWarnings("unchecked")
			Map<String,String> errorMap = (Map<String,String>) request.getSession().getAttribute("errorMap");
			for(String error : errorMap.values()){
				out.print(error + "<br/>");
			}
			request.getSession().setAttribute("errorMap", null);
		}catch(Exception e){
			//ignorable
		}
		%>
		
		</font>
	</p>
	<form action="register" method="post" >

		<div class="container">
			<label for="firstName"><b>First name</b></label>
			<input type="text" placeholder="Enter first name" name="firstName" required><br/>
			
			<label for="lastName"><b>Last name</b></label>
			<input type="text" placeholder="Enter last name" name="lastName" required><br/>
			
			<label for="email"><b>Email</b></label>
			<input type="text" placeholder="Enter email" name="email" required><br/>
			
			<label for="nick"><b>Nick</b></label>
			<input type="text" placeholder="Enter nick" name="nick" required><br/>
			
			<label for="password"><b>Password</b></label>
			<input type="password" placeholder="Enter password" name="password" required><br/>

			<button type="submit">Register</button>
		</div>
	</form>
	<p><a href="<%= request.getServletContext().getContextPath()%>/servleti/main" >Homepage</a></p>
	
</body>
</body>
</html>