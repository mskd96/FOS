<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="EditDetails.css">
<title>Some More Info</title>
</head>
<body>
<% String sid = request.getParameter("sid") ;%>

<br>
<br>
<center>
<h2>Apparently, there is no item in database with that name. Add some more information.</h2>
</center>

<form id = "msform" action="FOS" method="post">
<fieldset>
<input type="text" name="ItemName" placeholder="ItemName" class="name" size="20px" > <br>
<input type="text" name="Cost" placeholder="Cost" class="name" size="20px" > <br>
<input type="text" name="Minutes" placeholder="Minutes" class="name" size="20px" > 
Vegetarian<input type="radio" name="IsVeg" value="Vegetarian">
Non-Vegetarian<input type="radio" name="IsVeg" value="Non-Vegetarian">


North-Indian<input type="radio" name="Cuisine" value="North-Indian">
Chinese<input type="radio" name="Cuisine" value="Chinese">
South-Indian<input type="radio" name="Cuisine" value="South-Indian">
<br>
<input type="hidden" name="from" value = "14">
<input type="hidden" name="sid" value=<%out.println(sid); %>>
<input type="submit" name="Add" value="Add" class="action-button">
</form>
<%String output="FOS?from=88&sid="+sid;%>
<a href=<%out.println(output); %>><h3>Home</h3></a>
</body>
</html>