<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="EditDetails.css">
<title>User Reditection</title>
</head>
<body>

<h1><%=request.getParameter("name") %></h1>
<%
String uid=request.getParameter("uid");
String output="FOS?from=36&uid="+uid;%>
<a href=<%out.println(output); %>><h3>Click here to go to home</h3></a>

</body>
</html>
