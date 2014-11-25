<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css" href="Login.css">

<link href='http://fonts.googleapis.com/css?family=Oswald' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
<HTML>
   <HEAD>
      <TITLE>Food Panda</TITLE>
 <script src="login.js"></script>     
   </HEAD>
   <body>
   <div style="width:60%; margin:0 auto;text-align:center;">
   <h1 style="font-size:50px;">NOW ORDER FOOD AT YOUR DOORSTEPS!!!!</h1>
   </div>
<div class = "wrapper" style="float:left;margin-left:10%;margin-top:3%;">

<h1>LOGIN</h1>
<form action="FOS" class="form" method="post">
<input type="radio" name="SellerOrUser" value="Seller" checked>Seller
<input type="radio" name="SellerOrUser" value="User">User<br/>

<input type="text" class = "name" name="Username" placeholder = "UserName" >
<p class="name-help">Please enter your UserName</p>
<input type="password" name="Password" class = "name" placeholder = "Password">
<p class="name-help">Please enter your Password.</p>
<input type="hidden" name="from" value = "2">
<input type="submit" class = "submit" name="submitvalue" value="login" >
</form>
<h1>
<% 
   String s = request.getParameter("ErrorMsg");
   if(s != null)out.println(s);
   %>
   </h1>
</div>
<div class = "wrapper" style="float: right;margin-right:10%;margin-top:3%;">
<h1>SIGN UP</h1>
<form class = "from" action="FOS" method="post">
<input type="radio" name="SellerOrUser" value="Seller" checked>Seller
<input type="radio" name="SellerOrUser" value="User">User

<input type="text" name="Username" class = "name" placeholder="Username">

<input type="text" name="Address" class = "name" placeholder="Address">

<input type="text" name="Name" class = "name" placeholder = "Name">

<input type="password" name="Password" class = "name" placeholder = "Password">
<input type="hidden" name="from" value = "2">
<input type="submit" name="submitvalue" value="signup" class = "submit">
</form>
<h1>
<% 
   String re = request.getParameter("RegistrationError");
   if(re != null)out.println(re);
   %>
</h1>
</div>
   </BODY>
</HTML>