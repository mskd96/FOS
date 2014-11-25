<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="EditDetails.css">
<title>Insert Items</title>
</head>
<body>
<% String sid = request.getParameter("sid") ;%>

<center>
<br>
<br>
<h2>Insert values to be added</h2>
</center>

<form id = "msform"  action="FOS" method="post">
<fieldset>
<input type="text" name="ItemName" placeholder="ItemName" class="name" size="20px" > <br>
<input type="text" name="Cost" placeholder="Cost" class="name" size="20px" > <br>
<input type="text" name="Minutes" placeholder="Delivery Time (Mins)" class="name" size="20px" > <br>
<input type="hidden" name="from" value = "12">
<input type="hidden" name="sid" value=<%out.println(sid); %>>
<% 
String error=request.getParameter("ErrorMsg");
if( error != null){out.println(error);}
%>
<br>
<input type="submit" name="Add" value="Add" class="action-button">
</form>
<%String output="FOS?from=88&sid="+sid;%>
<a href=<%out.println(output); %>><h3>Home</h3></a>
</body>
</html>