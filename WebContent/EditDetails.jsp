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
<% String uid= request.getParameter("uid");
String output="FOS?from=36&uid="+uid;%>
<FORM id="msform" action="FOS" method = "post">
<fieldset>
<input type = "hidden" name = "from" value = "5">
<input type = "hidden" name = "uid" value = <%out.println(uid); %>>
<input type = "text" name = "Name" class = "name" placeholder = "New Name"></br>
<input type = "text" class = "name" name = "Address" placeholder="New Address"></br>
<input class = "name" type = "password" name = "Password" placeholder="New Password"></br>
<input class = "name" type = "password" name = "Confirmation" placeholder="Confirm Password"></br>
<input type="submit" name="Change" value="Change" class="action-button"><br>


</FORM>
<% String ErrorMsg= request.getParameter("ErrorMsg");
if(ErrorMsg!=null){out.println(ErrorMsg);}
String output1="FOS?from=36&uid="+uid;
%>
</br>
<a href=<%out.println(output);%>>Home</a></fieldset>
</fieldset>
</body>
</html>