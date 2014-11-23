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
<% 
String sid=request.getAttribute("SIDPassing").toString();
String Data=request.getAttribute("MyData").toString();
String TempArray[] = Data.split("//");
String FinalArray[]=TempArray[0].split("\\s+");
String SellerID = FinalArray[0];
out.println("Hello Mr. "+FinalArray[1]+"! You have "+FinalArray[2]+" in your Wallet.");
String Orders=request.getAttribute("OrderData").toString();
String SingleOrderArray[] = Orders.split("//");
System.out.println("Length is "+SingleOrderArray.length);
String testing=request.getAttribute("testing").toString();
String MenuRef="/FOS/FOS?name=Menu&from=8&sid="+sid;
String StatsRef="/FOS/FOS?name=Stats&from=8&sid="+sid;
String HistoryRef="/FOS/FOS?name=History&from=8&sid="+sid;
%>
<a href=<%out.println(MenuRef);%>>Click here to change menu</a>
<br>
<a href=<%out.println(StatsRef);%>>Click here to view Stats</a>
<br>
<a href=<%out.println(HistoryRef);%>>Click here to view History</a>
<br>
<form action="FOS" method="post"> 
<%
for(String s : SingleOrderArray){
	String SingleOrderPartsArray[]=s.split("\\s+");
	String OutPut="";
	String mid="";
	if(SingleOrderPartsArray.length>1){
		OutPut=SingleOrderPartsArray[0]+" "+SingleOrderPartsArray[1]+" "+SingleOrderPartsArray[2]+" "+SingleOrderPartsArray[3]+" ";
		mid=SingleOrderPartsArray[4];	
	};
	if(!(mid.equals(""))){
	%>
	<input type="checkbox" name="OrderIds" value=<%out.println(mid);%> > <% out.println(OutPut); %> <br> 
	<%
	}
}
%>
<input type="hidden" name="from" value = "8">
<input type="hidden" name="SidPassing" value = <%out.println(sid);%>>
<input type="submit" name="submitvalue" value="Make Delivered">
</form>
<br>
</body>
</html>