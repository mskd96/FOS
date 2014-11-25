<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="EditDetails.css">
<link rel="stylesheet" type="text/css" href="Menu.css">
<title>Order Page</title>
</head>
<body>

<%String UserData = request.getAttribute("UserData").toString();
String ArrayUser[] = UserData.split("@");%>

<div class="menu3">
    <a href="/FOS/Wallet.jsp?UserData=<%out.println(UserData); %>">Wallet</a>
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
<%
String ItemData = request.getAttribute("ItemData").toString();
String ItemDataArray[] = ItemData.split("//");
String SellerData = request.getAttribute("SellerData").toString();
String SellerDataArray[] = SellerData.split("@");
%>
</br>
</br>
</br>
<h3>Please select your cuisine and order</h3>
<form action="FOS" method ="post">
  <input type="checkbox" name="1" value="abc"> North Indian<br>
  <input type="checkbox" name="2" value="abc" > Chinese<br>
  <input type="checkbox" name="3" value="abc"> South Indian<br>
  <input type="checkbox" name="4" value="abc"> Veg<br>
  <input type="checkbox" name="5" value="abc"> Non Veg<br>
  <input type = "hidden" name = "UserId" value = <% out.println(ArrayUser[0]); %>>
  <input type = "hidden" name = "SellerId" value = <% out.println(SellerDataArray[0]); %>>
  <input type="hidden" name="from" value = "4">
  <input type="submit" value="Submit" class="action1">
</form>

<a href = "/FOS/FOS?from=4&uid=<%out.println(ArrayUser[0]); %>"> Back to your Homepage</a>
</div>


<div style="float:right;padding-right:10%;margin-top:3%;margin-bottom:5%;">
<h3> Items of <%out.println(SellerDataArray[1]); %></h3>
<div style="width:100%;border:1px dotted black;text-align:center;">
<form action="FOS">
<input type="hidden" name="from" value = "25">
<input type = "hidden" name = "UserId" value = <% out.println(ArrayUser[0]); %>>
<input type = "hidden" name = "SellerId" value = <% out.println(SellerDataArray[0]); %>>  
<TABLE BORDER="1" CELLPADDING="3" CELLSPACING="1">
	<TR>
        <TH>Name</TH>
        <TH>Cost</TH>
        <TH>Expected time</TH>
        <TH>veg or non veg</TH>
        <TH>cuisine</TH>
    </TR>

<%
for(String s : ItemDataArray)
{
	if(s.equals("")) continue;
	String ItemArray[] = s.split("@");
	
%>
    <TR>
        <TD><%=ItemArray[4] %></TD>
        <TD><%=ItemArray[2] %></TD>
        <TD><%=ItemArray[3] %></TD>
        <TD>
        <%if(ItemArray[5].equals("1")) out.println("Veg");
        else out.println("Non-Veg");
        %></TD>
        <TD>
        <%if(ItemArray[6].equals("1")) out.println("North Indian");
        else if(ItemArray[6].equals("2")) out.println("Chinese");
        else out.println("South Indian");%></TD>
        <TD><select name="Quantity">
  <option value="0">0</option>      
  <option value="1">1</option>
  <option value="2">2</option>
  <option value="3">3</option>
  <option value="4">4</option></select>
  <input type="checkbox" name = "itemid" value = <%out.println(ItemArray[0]); %>>
</TD>
    </TR>

</a>
</div>

<%}%>
</TABLE>
<input type="submit" name="submitvalue" value="Submit" class="action1">

</form>
</div>


</body>
</html>