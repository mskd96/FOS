<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="EditDetails.css">
<link rel="stylesheet" type="text/css" href="Menu.css">
<title>User Page</title>
</head>
<body>
<%String DataUser = request.getAttribute("UserData").toString();
String ArrayUser[] = DataUser.split("@");%>




<div class="menu3">
    <a href="/FOS/Wallet.jsp?UserData=<%out.println(DataUser); %>">Wallet</a>
    <% String edit="/FOS/EditDetails.jsp?uid="+ArrayUser[0]; 
String history="FOS?from=63&uid="+ArrayUser[0]; %>
    <a href=<%out.println(edit); %>>Edit Details</a>
    <a href=<%out.println(history); %>>History</a>
    <a href="Home.jsp">Logout</a>
</div>
<div class="menu3sub"> </div>




<div style="float:left;margin-top:3%;margin-left:10%;">
<h1>Welcome <%out.println(ArrayUser[1]); %></h1>
<h2>Your wallet contains <%out.println(ArrayUser[2]); %></h2>
</br>
</br>
</br>
<%
String DataSell = request.getAttribute("SellerData").toString();
String ArraySell[] = DataSell.split("//");
//out.println(s.split("//")[0] + " dinesh " + s.split(" ")[1]);
%>

<h3>please select your cuisine</h3>
<form action="FOS" method ="post">
  <input type="checkbox" name="1" value="abc"> North Indian<br>
  <input type="checkbox" name="2" value="abc" > Chinese<br>
  <input type="checkbox" name="3" value="abc"> South Indian<br>
  <input type="hidden" name="uid" value=<%out.println(ArrayUser[0]); %>> 
  <input type="hidden" name="from" value = "3">
  <input type="submit" value="Submit" class="action1">
</form>

</div>
<div style="float:right;padding-right:10%;text-align:center;margin-top:3%;">
<table  CELLPADDING="3" CELLSPACING="2" border="3">
<tr>
<td> Seller Name </td>
<td> Seller Address </td>
<td> Cuisine </td>
</tr>
<%
for(String s : ArraySell)
{
	if(s.equals("")) continue;
%>	

<tr>
<td><a href = "/FOS/FOS?from=3&UserData=<%out.println(DataUser); %>&SellerData=<%out.println(s); %>" >
<%out.println(s.split("@")[1]);%></a></td>
<td> <%out.println(s.split("@")[2]);%> </td>
<td> <%out.println(s.split("@")[3] ); %> </td>
</tr>

</div>
<%}%>
</table>
</div>
</body>
</html>