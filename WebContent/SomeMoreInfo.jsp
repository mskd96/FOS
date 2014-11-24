<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<% String sid = request.getParameter("sid") ;%>
<h2>Apparently, there is no item in database with that name. Add some more information.</h2>

<form action="FOS" method="post">
ItemName <input type="text" name="ItemName" size="20px" > <br>
Cost <input type="text" name="Cost" size="20px" > <br>
Minutes <input type="text" name="Minutes" size="20px" > <br>
Veg/Non-Veg- 
<input type="radio" name="IsVeg" value="Vegetarian">Vegetarian
<input type="radio" name="IsVeg" value="Non-Vegetarian">Non-Vegetarian

<br>
Cuisine- 
<input type="radio" name="Cuisine" value="North-Indian">North-Indian
<input type="radio" name="Cuisine" value="Chinese">Chinese
<input type="radio" name="Cuisine" value="South-Indian">South-Indian
<br>
<input type="hidden" name="from" value = "14">
<input type="hidden" name="sid" value=<%out.println(sid); %>>
<input type="submit" name="Add" value="Add">
</form>

</body>
</html>