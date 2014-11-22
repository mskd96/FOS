<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<a href="/FOS/Home.html">Logout</a>
<br>
<a href="/FOS/Menu.jsp">Click here to change menu</a>
<br>
<a href="/FOS/HistorySeller.jsp">Click here to view your history</a>
<br>
<a href="/FOS/Stats.jsp">Click here to view your Stats</a>
<br>
<% 
String Data=request.getAttribute("MyData").toString();
String TempArray[] = Data.split("//");
String FinalArray[]=TempArray[0].split("\\s+");

out.println("Hello Mr. "+FinalArray[0]+"! You have "+FinalArray[1]+" in your Wallet.");
%>
<br>
</body>
</html>