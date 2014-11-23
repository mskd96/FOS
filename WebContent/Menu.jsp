<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<table>
<%String S=request.getAttribute("MyMenu").toString(); 
String sid=request.getAttribute("MySID").toString();
String InsertString="/FOS/InsertItems.jsp?sid="+sid;
String[] SingleLine=S.split("//");
for(String line: SingleLine){
	%> <tr> <%out.println(line);%> </tr> <% 
}
%>

<a href=<%out.println(InsertString); %>> Click here </a>
</table>
</body>
</html>