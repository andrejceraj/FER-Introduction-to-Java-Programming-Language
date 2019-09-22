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
			String error = (String) request.getSession().getAttribute("loginError");
			if(error != null) out.print(error);
			request.getSession().setAttribute("loginError", null);
		}catch(Exception e){
			//ignorable
		}
		%>
		
		</font>
	</p>
	<form action="main" method="post" >

		<div class="container">			
			<label for="nick"><b>Nick</b></label>
			<input type="text" value="<%
				String n = (String) request.getSession().getAttribute("nick"); 
				if(n != null) out.print(n);
			%>" placeholder="Enter nick" name="nick" required><br/>
			<% request.getSession().setAttribute("nick", null); %>
			
			<label for="password"><b>Password</b></label>
			<input type="password" placeholder="Enter password" name="password" required><br/>

			<button type="submit">Login</button>
		</div>
	</form>

</body>
</html>