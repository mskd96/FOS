<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="EditDetails.css">
<title>Seller Menu</title>
</head>
<body>

<form action="FOS" method=post>

<!--  <div style="width:50%; margin:5% auto;text-align:center;">  -->
<center style="margin-top:5%;">
<h2> My Menu </h2>
<%
String S=request.getAttribute("MyMenu").toString(); 
String sid=request.getAttribute("MySID").toString();
String boolCheck="0";
String InsertString="/FOS/InsertItems.jsp?sid="+sid;
%>
<br>
<h4> If you want to insert new items, <a href=<%out.println(InsertString); %>> Click here</a> </h4> 
<h4> If you want to delete items, select checkboxes and click delete items</h4> <br>
<table CELLPADDING="3" CELLSPACING="1" border="1" style="text-align:center;">
<tr>
<td> Checkbox </td>
<td> Item Name </td>
<td> Veg/Non-Veg </td>
<td> Cuisine </td>
<td> Cost </td>
<td> Approx Delivery Time </td>
</tr>
<%
String[] SingleLine=S.split("//");
for(String line: SingleLine){
	String[] parts=line.split("@");  //Parts[5] is iid
	if(parts.length>4){
		boolCheck="1";
		%>
		<tr>
		<td> <input type="checkbox" name="DeletedItems" value= <%out.println(parts[5]); %>> </td>
		<td> <%out.println(parts[0]); %></td>
		<td> <%out.println(parts[1]); %></td>
		<td> <%out.println(parts[2]); %></td>
		<td> <%out.println(parts[3]); %></td>
		<td> <%out.println(parts[4]); %></td>
		</tr>
		<%
	}
}
%>

</table>
<br>
<br>
<input type="hidden" name="boolCheck" value=<%out.println(boolCheck); %>>
<input type="hidden" name="SidPassing" value = <%out.println(sid);%>>
<input type="submit" name="submitvalue" value="Delete Items" class="action">
<input type="hidden" name="from" value = "9">
<br>
<br>
<%String output="FOS?from=88&sid="+sid;%>
<a href=<%out.println(output); %>><h3>Home</h3></a>

</center>
 <!--  <div style="width:50%; margin:5% auto;text-align:center;"> -->
</body>
</html>