<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="EditDetails.css">
<title>Insert title here</title>
</head>
<body>

<center style="margin-top:5%;">
<h2>History of all deliveries</h2>
<br>
<table  CELLPADDING="3" CELLSPACING="1" border="1" style="text-align:center;">
<tr>
<td> Customer Name </td>
<td> Item Name </td>
<td> Quantity </td>
<td> Date & Time of Delivery </td>
<td> Cost of each item </td>
<td> Approx delivery time </td>
</tr>
<%String S=request.getAttribute("MyHistory").toString(); 
String sid=request.getAttribute("MySID").toString();
String[] SingleLine=S.split("//");
for(String line: SingleLine){
	if(line.equals(""))continue;
	String[] parts=line.split("@");
	if(parts.length>3){
	%> 
	<tr> 
		 <td> <%out.println(parts[0]);%> </td>
		 <td> <%out.println(parts[1]);%> </td>
		 <td> <%out.println(parts[2]);%> </td>
		 <td> <%out.println(parts[3]);%> </td>
		 <td> <%out.println(parts[4]);%> </td>
		 <td> <%out.println(parts[5]);%> </td>
	</tr> 
	<% 
}
}
%>
</table>
<br>
<br>
<br>
<%String output="FOS?from=88&sid="+sid;%>
<a href=<%out.println(output); %>><h3>Home</h3></a>
</center>
</body>
</html>