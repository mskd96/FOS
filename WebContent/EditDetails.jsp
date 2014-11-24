<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<% String uid= request.getParameter("uid"); %>

<FORM action="FOS" method = "post">
<input type = "hidden" name = "from" value = "5">
<input type = "hidden" name = "uid" value = <%out.println(uid); %>>
Use this form to change your details. If you do not want to change an attribute, leave the corresponding field unchanged.<br>
New Name:<input type = "text" name = "Name"></br>
New Address:<input type = "text" name = "Address"></br>
New Password:<input type = "password" name = "Password"></br>
Confirm Password:<input type = "password" name = "Confirmation"></br>
<input type="submit" name="Change" value="Change">
</FORM> 

<% String ErrorMsg= request.getParameter("ErrorMsg");
if(ErrorMsg!=null){out.println(ErrorMsg);}
String output="FOS?from=36&uid="+uid;
%>
<a href=<%out.println(output);%>>Home</a>

</body>
</html>