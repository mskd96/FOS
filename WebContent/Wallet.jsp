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

<%String UserData = request.getParameter("UserData").toString();
String ArrayUser[] = UserData.split("@");%>

<center>
<h1>welcome <%out.println(ArrayUser[1]); %></h1>
<h2>your wallet contains <%out.println(ArrayUser[2]); %></h2>

</center>


<FORM id = "msform" action="FOS" method = "post">
<fieldset>
<input type = "hidden" name = "UserID" value =<%out.println(ArrayUser[0]); %> >
<input type = "hidden" name = "from" value = "6">
<input type = "text" name = "Amount" placeholder="Amount" class="name"></br>
<input type = "password" name = "Passkey" placeholder="Password" class="name"></br>
<input type="submit" name="submitvalue" value="Auth" class="action-button"><br>
</FORM>

<% 
String s= request.getParameter("ErrorMsg");
if(s != null) out.println(s);
String output="FOS?from=36&uid="+ArrayUser[0];
%>
<br>
<a href=<%out.println(output);%>>Home</a>
</fieldset>
</body>
</html>