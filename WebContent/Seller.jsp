<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="EditDetails.css">
<link rel="stylesheet" type="text/css" href="Menu.css">
<title>Seller's Home</title>
</head>
<body>



<% 
String sid=request.getAttribute("SIDPassing").toString();
String Data=request.getAttribute("MyData").toString();
String TempArray[] = Data.split("//");
String FinalArray[]=TempArray[0].split("@");
String SellerID = FinalArray[0];
String Orders=request.getAttribute("OrderData").toString();
String SingleOrderArray[] = Orders.split("//");
System.out.println("Length is "+SingleOrderArray.length);
String testing=request.getAttribute("testing").toString();
String MenuRef="/FOS/FOS?name=Menu&from=8&sid="+sid;
String StatsRef="/FOS/FOS?name=Stats&from=8&sid="+sid;
String HistoryRef="/FOS/FOS?name=History&from=8&sid="+sid;
%>

<div class="menu3">
    <a href=<%out.println(MenuRef);%>>Edit Menu</a>
    <a href=<%out.println(StatsRef);%>>Stats</a>
    <a href=<%out.println(HistoryRef);%>>History</a>
    <a href=/FOS/Home.jsp>Logout</a>
</div>
<div class="menu3sub"> </div>

</br>
</br>
</br>



<center>
 <h3> <%out.println("Hello Mr. "+FinalArray[1]+"! You have "+FinalArray[2]+" in your Wallet."); %> </h3>
<form action="FOS" method="post"> 
<br>
<table CELLPADDING="3" CELLSPACING="1" border="1" style="text-align:center;">
<tr>
<td> Checkbox </td>
<td> User Name </td>
<td> User Address </td>
<td> Item Name </td>
<td> Item Quantity </td>
</tr>
<%
String boolCheck="0";
for(String s : SingleOrderArray){
	if(s.equals("")){continue;}
	System.out.println("*******!!!!!!!!!!!!!!!!!!*******");
	String SingleOrderPartsArray[]=s.split("@");
	String OutPut="";
	String mid="";
	if(SingleOrderPartsArray.length>1){
		OutPut=SingleOrderPartsArray[0]+" "+SingleOrderPartsArray[1]+" "+SingleOrderPartsArray[2]+" "+SingleOrderPartsArray[3]+" ";
		mid=SingleOrderPartsArray[4];	
	};
	if(!(mid.equals(""))){
	boolCheck="1";
	%>
	<tr>
	<td> <input type="checkbox" name="OrderIds" value=<%out.println(mid);%> > </td>
	<td> <% out.println(SingleOrderPartsArray[0]); %> </td>
	<td> <% out.println(SingleOrderPartsArray[1]); %> </td>
	<td> <% out.println(SingleOrderPartsArray[2]); %> </td>
	<td> <% out.println(SingleOrderPartsArray[3]); %> </td>
	</tr>
	<%
	}
}
%>
</table>
<input type="hidden" name="boolCheck" value=<%out.println(boolCheck); %>>
<input type="hidden" name="from" value = "8">
<input type="hidden" name="SidPassing" value = <%out.println(sid);%>>
<br>
<input type="submit" name="submitvalue" value="Make Delivered" class="action">

</form>
 </center>
<br>
</body>
</html>