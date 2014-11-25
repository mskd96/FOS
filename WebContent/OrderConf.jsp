<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="EditDetails.css">
<link rel="stylesheet" type="text/css" href="Menu.css">
<title>Order Confirmation page</title>
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

<%
String ItemData = request.getAttribute("ItemData").toString();
String ItemDataArray[] = ItemData.split("//");
String SellerData = request.getAttribute("SellerData").toString();
String SellerDataArray[] = SellerData.split("@");
%>
</div>
<div style="float:right;margin-top:3%;margin-right:10%;text-align:center;">
<h3> Items of <%out.println(SellerDataArray[1]); %></h3>



<TABLE BORDER="1" CELLPADDING="3" CELLSPACING="1">
	<TR>
        <TH>Name</TH>
        <TH>Cost</TH>
        <TH>Expected time</TH>
        <TH>veg or non veg</TH>
        <TH>cuisine</TH>
        <TH>Quantity</TH>
    </TR>

<%
String outString="";
for(String s : ItemDataArray)
{
	if(s.equals("")) continue;
	String ItemArray[] = s.split("@");
	outString+=ItemArray[0]+"@"+ItemArray[2]+"@"+ItemArray[7]+"//";
%>
    <TR>
        <TD><%=ItemArray[4] %></TD>
        <TD><%=ItemArray[2] %></TD>
        <TD><%=ItemArray[3] %></TD>
        <TD>
        <%if(ItemArray[5].equals("1")) out.println("Veg");
        else out.println("Non-Veg");
        %>
        </TD>
        <TD>
		<%if(ItemArray[6].equals("1")) out.println("North Indian");
        else if(ItemArray[6].equals("2")) out.println("Chinese");
        else out.println("South Indian");%>
		</TD>
        <TD><%=ItemArray[7] %></TD>
	</TR>

<%}%>
</TABLE>

<FORM action="FOS">
<input type = "hidden" name = "UserData" value = <% out.println(ArrayUser[0]); %>>
<input type = "hidden" name = "SellerData" value = <% out.println(SellerDataArray[0]); %>>
<input type = "hidden" name = "ItemData" value = <% out.println(outString); %>>
<input type = "hidden" name = "from" value = "13"><br>
<input type="submit" name="submitvalue" value="Submit" class="action1">
</FORM>
<br>
<a href="/FOS/FOS?from=3&UserData=<%= DataUser %>&SellerData=<%=SellerData %>">
 Change the order
 </a>
 </div>
</body>
</html>