<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="EditDetails.css">
<title>Statistics</title>
</head>
<body>
<center style="margin-top:5%;">
<%String sid=request.getAttribute("MySID").toString(); %>

<h1> Statistics </h1>
<br>
<br>
<h3>Most Items Table</h3>
<table  CELLPADDING="3" CELLSPACING="1" border="1" style="text-align:center;">
<tr>
<td> Item Name </td>
<td> Item Quantity </td>
</tr>
<%String S=request.getAttribute("MostItems").toString(); 
System.out.println("MostItems is "+S);
String[] SingleLine=S.split("//");
for(String line: SingleLine){
	if(line.equals(""))continue;
	String[] parts=line.split("@");
	if(parts.length>1){
	%> 
	<tr> 
		 <td> <%out.println(parts[0]);%> </td>
		 <td> <%out.println(parts[1]);%> </td>
	</tr> 
	<% 
}
}
%>
</table>

<br>
<br>

<h3>Most Users Table</h3>
<table  CELLPADDING="3" CELLSPACING="1" border="1" style="text-align:center;">
<tr>
<td> User Name </td>
<td> No. Of Orders </td>
</tr>
<%S=request.getAttribute("MostUsers").toString(); 
System.out.println("MostUsers is "+S);
SingleLine=S.split("//");
for(String line: SingleLine){
	if(line.equals(""))continue;
	String[] parts=line.split("@");
	if(parts.length>1){
	%> 
	<tr> 
		 <td> <%out.println(parts[0]);%> </td>
		 <td> <%out.println(parts[1]);%> </td>
	</tr> 
	<% 
}
}
%>
</table>

<br>
<br>

<h3>Date Wise Table</h3>
<table  CELLPADDING="3" CELLSPACING="1" border="1" style="text-align:center;">
<tr>
<td> Date </td>
<td> No. Of Orders </td>
</tr>
<%S=request.getAttribute("DateWise").toString(); 
System.out.println("DateWise is "+S);
SingleLine=S.split("//");
for(String line: SingleLine){
	if(line.equals(""))continue;
	String[] parts=line.split("@");
	if(parts.length>1){
	%> 
	<tr> 
		 <td> <%out.println(parts[0]);%> </td>
		 <td> <%out.println(parts[1]);%> </td>
	</tr> 
	<% 
}
}
%>
</table>

<br>
<br>


<%String output="FOS?from=88&sid="+sid;%>
<a href=<%out.println(output); %>><h3>Home</h3></a>

</center>
</body>
</html>