<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Seller Menu</title>
</head>
<body>

<form action="FOS" method=post>

<table CELLPADDING="3" CELLSPACING="1">
<%String S=request.getAttribute("MyMenu").toString(); 
String sid=request.getAttribute("MySID").toString();
String boolCheck="0";
String InsertString="/FOS/InsertItems.jsp?sid="+sid;
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
<input type="hidden" name="boolCheck" value=<%out.println(boolCheck); %>>
<input type="hidden" name="SidPassing" value = <%out.println(sid);%>>
<input type="submit" name="submitvalue" value="Delete Items">
<input type="hidden" name="from" value = "9">
<a href=<%out.println(InsertString); %>> Click here to Insert Items</a>
</body>
</html>