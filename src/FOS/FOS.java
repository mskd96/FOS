package FOS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*; 
import java.net.*; 


/**
 * Servlet implementation class FOS
 */
public class FOS extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 
	Connection conn1 =null;
	Statement st =null;
	public void init() throws ServletException {
      //Open the connection here
	
	String dbURL2 = "jdbc:postgresql://localhost/cs387";
    String user = "sgondala";
    String pass = "x";

    try {
		Class.forName("org.postgresql.Driver");
	
		conn1 = DriverManager.getConnection(dbURL2, user, pass);
		st = conn1.createStatement();
		System.out.println("init"+conn1);
    	} catch (Exception e) {
		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    }

    public void destroy() {
     //Close the connection here
    	try{
    		conn1.close();
    		System.out.println("close");
    	}catch(Exception e)
    	{
    		System.out.println(e);
    	}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String num=request.getParameter("from");
		
		if(request.getParameter("from").equals("3"))
		{
			//System.out.println(request.getParameter("SellerData"));
			String UserData = request.getParameter("UserData");
			String UserDataArray[] = UserData.split("@"); 
			//System.out.println(s);
			String SellerData = request.getParameter("SellerData");
			String SellerDataArray[] = SellerData.split("@"); 
			String q1 = "Select * from menu natural join item where sid = '" + SellerDataArray[0] + "'";
			try {
				String ItemData = "";
				ResultSet rs = st.executeQuery(q1);
				while(rs.next())
				{
					ItemData += rs.getString(1) + "@" + rs.getString(2) + "@"  + rs.getString(3) + "@" +
							rs.getString(4) + "@" + rs.getString(5) + "@"  + rs.getString(6) + "@" +
							rs.getString(7);
					ItemData += "//";
					//System.out.println();
				}
				//request.setAttribute("SellerData", SellerDataNew);
				request.setAttribute("ItemData",ItemData);
				request.setAttribute("SellerData",SellerData);
				request.setAttribute("UserData",UserData);
				System.out.println("User Data 1 : " + UserData);
				//request.setAttribute("SellerCuisine",SellerCuisine);
				//System.out.println(ItemData);
				RequestDispatcher reqDispatcher = getServletConfig().getServletContext().
						getRequestDispatcher("/UserOrder.jsp");
				try {
					reqDispatcher.forward(request,response);
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		if(num.equals("4"))
		{
			String uid = request.getParameter("uid");
			toUser(uid,request,response,null);
		}
		
		if(num.equals("8")){
			String name=request.getParameter("name");
			String sid=request.getParameter("sid");
			if(name.equals("Menu")){
				String Output="";
				String sql="SELECT name,isveg,cuisine,cost,exptime,menu.iid from item join menu on item.iid=menu.iid where menu.sid='"+sid+"'";
				//String sql="Select * from menu where sid='"+sid+"'";
				ResultSet rs1;
				try{
					rs1 = st.executeQuery(sql);
      				 while(rs1.next()){
      					 
      					 	String ItemName = rs1.getString(1);
						 	String isveg = rs1.getString(2);
						 	if(isveg.equals("1")){isveg="Vegetarian";}
						 	else{isveg="Non-Vegetarian";}
						 	String cuisine = rs1.getString(3);
						 	cuisine=getCuisineName(cuisine);
						 	String cost = rs1.getString(4);
						 	String exptime = rs1.getString(5);
						 	String iid=rs1.getString(6);
						 	Output += ItemName + "@" + isveg + "@" + cuisine + "@" + cost + "@" + exptime + "@" + iid + "//";
					}
					rs1.close();
				}
				catch(SQLException e){e.printStackTrace();}
				request.setAttribute("MyMenu", Output);
				request.setAttribute("MySID", sid);
				RequestDispatcher reqDispatcher = getServletConfig().getServletContext().getRequestDispatcher("/Menu.jsp");
				try {
					reqDispatcher.forward(request,response);
				} catch (ServletException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			
			if(name.equals("History")){
				String sql="SELECT a.name,f.name,e.quantity,c.timestamp,g.cost,g.exptime from users as a,userorder as b,orders as c, sellerorder as d,itemorder as e,item as f, menu as g where d.sid='"+sid+"' and d.oid=c.oid and d.oid=b.oid and d.oid=e.oid and a.uid=b.uid and e.iid=f.iid and f.iid=g.iid and g.sid='"+sid+"' and c.deliverystatus='Delivered'";
				ResultSet rs1;
				String Output="";
				try{
					rs1 = st.executeQuery(sql);
      				 while(rs1.next()){
						 	String UserName = rs1.getString(1);
						 	String ItemName = rs1.getString(2);
						 	String Quantity = rs1.getString(3);
						 	String TimeStamp = rs1.getString(4);
						 	String Cost = rs1.getString(5);
						 	String ExpTime = rs1.getString(6);
						 	Output +=  UserName + "@" + ItemName + "@" + Quantity +"@" + TimeStamp +"@" + Cost +"@" + ExpTime +  "//";
					}
					rs1.close();
				}
				catch(SQLException e){e.printStackTrace();}
				request.setAttribute("MyHistory", Output);
				request.setAttribute("MySID", sid);
				RequestDispatcher reqDispatcher = getServletConfig().getServletContext().getRequestDispatcher("/History.jsp");
				try {
					reqDispatcher.forward(request,response);
				} catch (ServletException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			
			if(name.equals("Stats")){
				String sql="select item.name,sum(CAST(quantity as int)) from sellerorder natural join itemorder join item on item.iid=itemorder.iid where sid = '"+sid+"' group by item.iid,item.name order by Sum desc limit 10";
				String sql2="select users.name,count(*) from sellerorder natural join userorder join users on users.uid=userorder.uid where sid='"+sid+"' group by users.uid order by count desc limit 10";
				String sql3="select a.date,count(oid) from (select date(timestamp),oid from orders natural join sellerorder where sid='"+sid+"' order by timestamp) as a group by a.date order by a.date DESC limit 10";
				String MostItems="",MostUsers="",DateWise="";
				ResultSet rs1,rs2,rs3;
				try{
					rs1 = st.executeQuery(sql);
      				 while(rs1.next()){
						 	String ItemName = rs1.getString(1);
						 	String Quantity = rs1.getString(2);
						 	MostItems +=  ItemName + "@" + Quantity +"//";
					}
					rs1.close();
					rs2 = st.executeQuery(sql2);
     				 while(rs2.next()){
						 	String UserName = rs2.getString(1);
						 	String Orders = rs2.getString(2);
						 	MostUsers +=  UserName + "@" + Orders +"//";
					}
					rs2.close();
					rs3 = st.executeQuery(sql3);
     				 while(rs3.next()){
						 	String date = rs3.getString(1);
						 	String Quantity = rs3.getString(2);
						 	DateWise +=  date + "@" + Quantity +"//";
					}
					rs3.close();
				}
				catch(SQLException e){e.printStackTrace();}
				request.setAttribute("MostItems", MostItems);
				request.setAttribute("MostUsers", MostUsers);
				request.setAttribute("DateWise", DateWise);
				request.setAttribute("MySID", sid);
				RequestDispatcher reqDispatcher = getServletConfig().getServletContext().getRequestDispatcher("/Stats.jsp");
				try {
					reqDispatcher.forward(request,response);
				} catch (ServletException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			
			
		}
		
		if(num.equals("13"))
		{
			String UserID = request.getParameter("UserData");
			String SellerID = request.getParameter("SellerData");
			String ItemData = request.getParameter("ItemData");
			if(ItemData == null){System.out.println("IS NULL");}
			int amount = 0;
			boolean ordered=false;
			for(String s: ItemData.split("//"))
			{
				if(s.equals(""))continue;
				ordered=true;
				float c = Float.parseFloat(s.split("@")[1]);
				int cost = (int) c;
				c = Float.parseFloat(s.split("@")[2]);
				int quantity = (int) c;
				amount += quantity*cost;
			}
			System.out.println("Amount is "+amount);
			System.out.println("User ID is "+UserID);
			System.out.println("Seller data is "+SellerID);
			String UserData=getUserData(UserID);
			int walletAmount=Integer.parseInt(UserData.split("@")[4]);
			
			if(amount>walletAmount){
					response.sendRedirect("/FOS/UserRedirection.jsp?name=Not Sufficient Amount&uid="+UserID);
			}
			
			else if(ordered){
				String sql="select count(*) from orders";
				int OrderId=0;
				String OrderIdString="";
				ResultSet rs;
				try {
					rs=st.executeQuery(sql);
					while(rs.next()){
						OrderIdString=rs.getString(1);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				OrderId=Integer.parseInt(OrderIdString);
				OrderId++;
				OrderIdString=Integer.toString(OrderId);
				Date dnow=new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("y-M-d HH:mm:ss");
				String DateFinal=dateFormat.format(dnow);
				
				sql="insert into orders values(?,CAST (? as TIMESTAMP),?)";
				PreparedStatement pStmt,pStmt2,pStmt3,pStmt4;
				try {
					pStmt = conn1.prepareStatement(sql);
		        	pStmt.setString(1,OrderIdString);
		        	pStmt.setString(2,DateFinal);
		        	String NotDelivered="NotDelivered";
		        	pStmt.setString(3,NotDelivered);
		        	System.out.println(pStmt);
		        	pStmt.executeUpdate();
		        	
		        	sql="insert into sellerorder values(?,?)";
		        	pStmt2 = conn1.prepareStatement(sql);
		        	pStmt2.setString(1,SellerID);
		        	pStmt2.setString(2,OrderIdString);
		        	pStmt2.executeUpdate();
		        	
		        	sql="insert into userorder values(?,?)";
		        	pStmt3 = conn1.prepareStatement(sql);
		        	pStmt3.setString(1,UserID);
		        	pStmt3.setString(2,OrderIdString);
		        	pStmt3.executeUpdate();	

					for(String s: ItemData.split("//")){
						if(s.equals(""))continue;
						String[] ArrayItem=s.split("@");
						sql="insert into itemorder values(?,?,?)";
						pStmt4 = conn1.prepareStatement(sql);
			        	pStmt4.setString(1,ArrayItem[0]);
			        	pStmt4.setString(2,OrderIdString);
			        	pStmt4.setString(3,ArrayItem[2]);
			        	pStmt4.executeUpdate();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		// Should insert for each item
				
				String sqltemp="UPDATE seller set wallet=wallet+"+amount+" where sid='"+SellerID+"'";
		        try {
					st.executeUpdate(sqltemp);
				} catch (SQLException e) {
					e.printStackTrace();
			        System.out.println("********************3**************");
				}
		        
		        sqltemp="UPDATE users set wallet=wallet-"+amount+" where uid='"+UserID+"'";
	        	try {
					st.executeUpdate(sqltemp);
				} catch (SQLException e) {
					System.out.println("****************6************");
					e.printStackTrace();
				}
		        
	        	toUser(UserID,request,response,null);
			}
			
			else{
				toUser(UserID,request,response,null);
			}
			
		}
		
		if(num.equals("25"))
		{
			String UserId = request.getParameter("UserId");
			String SellerId = request.getParameter("SellerId");
			System.out.println("User Id : "+UserId);
			System.out.println("Seller Id : "+SellerId);
			String All = request.getQueryString();
			String SelectedItems[] = request.getParameterValues("itemid");
			
			//String Quantity[] = request.getParameterValues("Quantity");
			//System.out.println(All);
			int index = 0;
			int i=0;
			ArrayList Quantity = new ArrayList();
			while(index<All.length())
			{
				int temp = All.indexOf("itemid",index);
				if(temp==-1) break;
				int quantity = Character.getNumericValue(All.charAt(temp-2));
				System.out.println("quantity " + quantity);
				Quantity.add(quantity);
				index = temp + 6;
				//int iid = Character.getNumericValue(All.charAt(temp+7));
				String iid= SelectedItems[i];
				System.out.println("item id " + iid);
				//System.out.println("index : "+index);
				i++;
			}
			String qUser = "select * from users where uid = '" + UserId + "'";
			String qSeller = "select * from seller where sid = '" + SellerId + "'";
			ResultSet rs;
			String UserData = "";
			String SellerData = "";
			String ItemData = "";
			try {
				rs = st.executeQuery(qUser);
				rs.next();
				UserData = rs.getString("uid") + "@" + rs.getString("name") + "@" + rs.getString("wallet");
				rs= st.executeQuery(qSeller);
				rs.next();
				SellerData = rs.getString(1) + "@" + rs.getString(2) + "@" + rs.getString(3);
				//Not adding cuisine details to seller data which was there in the original call
				//System.out.println(All);
				for(int j=0; j<i; j++)
				{
					if(Quantity.get(j).toString().equals("0")) continue;
					String ItemId = SelectedItems[j];
					//System.out.println(Quantity.get(j).toString());
					String q1 = "Select * from menu natural join item where sid = '" + SellerId + "' and "
							+ "iid = '" + ItemId + "'";
					rs = st.executeQuery(q1);
					rs.next();
					ItemData += rs.getString(1) + "@" + rs.getString(2) + "@"  + rs.getString(3) + "@" +
							rs.getString(4) + "@" + rs.getString(5) + "@"  + rs.getString(6) + "@" +
							rs.getString(7) + "@" + Quantity.get(j).toString();
					ItemData += "//";
				}
				//System.out.println(UserData);
				//System.out.println(SellerData);
				//System.out.println(ItemData);
				request.setAttribute("ItemData",ItemData);
				request.setAttribute("SellerData",SellerData);
				request.setAttribute("UserData",UserData);
				RequestDispatcher reqDispatcher = getServletConfig().getServletContext().
						getRequestDispatcher("/OrderConf.jsp");
				try {
					reqDispatcher.forward(request,response);
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(num.equals("36")){
			String uid=request.getParameter("uid");
			toUser(uid,request,response,null);
		}
		
		
		if(num.equals("63")){
			String uid=request.getParameter("uid");
			String sql="SELECT d.name,f.name,e.quantity,b.timestamp,g.cost,b.deliverystatus,g.exptime from userorder as a, orders as b,sellerorder as c,seller as d, itemorder as e,item as f, menu as g where a.uid='"+uid+"' and a.oid=c.oid and a.oid=e.oid and a.oid=b.oid and c.sid=d.sid and e.iid=f.iid and f.iid=g.iid and d.sid=g.sid;";
			ResultSet rs1;
			String Pending="";
			String History="";
			try{
				rs1 = st.executeQuery(sql);
  				 while(rs1.next()){
					 	String SName = rs1.getString(1);
					 	String ItemName = rs1.getString(2);
					 	String Quantity = rs1.getString(3);
					 	String TimeStamp = rs1.getString(4);
					 	String Cost = rs1.getString(5);
					 	String DeliveryStatus = rs1.getString(6);
					 	String Exptime = rs1.getString(7);
					 	if(DeliveryStatus.equals("NotDelivered")){
					 		Pending += SName + "@" + ItemName + "@" + Quantity + "@" + TimeStamp + "@" + Cost + "@" + Exptime + "//";
					 	}
					 	else{
					 		History += SName + "@" + ItemName + "@" + Quantity + "@" + TimeStamp + "@" + Cost + "@" + Exptime +  "//";
					 	}
					 	
				}
				rs1.close();
			}
			catch(SQLException e){e.printStackTrace();}
			request.setAttribute("History", History);
			request.setAttribute("Pending", Pending);
			request.setAttribute("MyUID", uid);
			RequestDispatcher reqDispatcher = getServletConfig().getServletContext().getRequestDispatcher("/HistoryUser.jsp");
			try {
				reqDispatcher.forward(request,response);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		if(num.equals("88")){
			String sid=request.getParameter("sid");
			toSeller(sid,request,response);
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("*******************CAME HERE************");
		String num=request.getParameter("from");
		
		if(num.equals("2")){
			//System.out.println("*******************CAME HERE************");
			String lor=request.getParameter("submitvalue");
			if(lor.equals("login")){
				String UserId= request.getParameter("Username");
		        String PassWd= request.getParameter("Password");
		        String sor= request.getParameter("SellerOrUser");
		        boolean retVal=AuthenticateUser(UserId,PassWd,sor);
		        System.out.println(retVal);
		        System.out.println("SOR IS "+sor);
		        if((retVal)&&(sor.equals("User"))){System.out.println("???????????????????????");toUser(UserId,request,response,null);}
		        else if((retVal)&&(sor.equals("Seller"))){toSeller(UserId,request,response);}
		        else {response.sendRedirect("/FOS/Home.jsp?ErrorMsg=Username or Password is invalid!!");}
		    }
			else if(lor.equals("signup")){
				String UserId= request.getParameter("Username");
		        String PassWd= request.getParameter("Password");
		        String Address= request.getParameter("Address");
		        String Name= request.getParameter("Name");
		        System.out.println(UserId);
		        if(Name == null || Name.isEmpty()){
		        	System.out.println("Came here");
		        	response.sendRedirect("/FOS/Home.jsp?RegistrationError=Name should not be empty.");
		        	System.out.println("Came here too");
		        }
		        else if(Address == null || Address.isEmpty()){
		        	System.out.println("Came here");
		        	response.sendRedirect("/FOS/Home.jsp?RegistrationError=Address should not be empty.");
		        	System.out.println("Came here too");
		        }
		        else{
		        String sor= request.getParameter("SellerOrUser");
		        String sql="";
		        if(sor.equals("Seller")){sql="insert into seller values(?,?,?,?,?)";}
		        else if(sor.equals("User")){sql="insert into users values(?,?,?,?,?)";}
		        try{
		        	PreparedStatement pStmt=conn1.prepareStatement(sql);
		        	pStmt.setString(1,UserId);
		        	pStmt.setString(2,Name);
		        	pStmt.setString(3,Address);
		        	pStmt.setString(4,PassWd);
		        	pStmt.setInt(5,0);
		        	pStmt.executeUpdate();
		        	if(sor.equals("Seller")){toSeller(UserId,request,response);}
		        	else{toUser(UserId,request,response,null);}
		        	//response.sendRedirect("/FOS/Temp.jsp?name=Raccha");
		        }
		        catch(SQLException e){
		        	String ErrorState=e.getSQLState();
		        	System.out.println("Caught SQLException " + e.getErrorCode() + "/" + e.getSQLState() + " " +   
                            e.getMessage() ) ;
		        	if(ErrorState.equals("22001")){response.sendRedirect("/FOS/Home.jsp?RegistrationError=Username should not exceed 5 digits.");}
		        	if(ErrorState.equals("23505")){response.sendRedirect("/FOS/Home.jsp?RegistrationError=Select another user Id.");}
		        	if(ErrorState.equals("23514")){response.sendRedirect("/FOS/Home.jsp?RegistrationError=Password length must be>=6 digits.");}
		        }
		        }
			}
		}
		
		if(num.equals("5")){
			String Name = request.getParameter("Name");
			String Password = request.getParameter("Password");
			String Confirmation = request.getParameter("Confirmation");
			String Address = request.getParameter("Address");
			String UserID = request.getParameter("uid");
			boolean sentConf=false;
			
			if(!Password.equals(Confirmation)){
				response.sendRedirect("/FOS/EditDetails.jsp?ErrorMsg=Passwords don't match&uid="+UserID);
				sentConf=true;
			}
			
			if(!sentConf){
			if(Password!=""){
				String qtemp = "update users set password = '" + Password + "' where uid = '"+UserID+"'";
				try {
					st.executeUpdate(qtemp);
				} catch (SQLException e) {
					String ErrorState=e.getSQLState();
		        	System.out.println("Caught SQLException " + e.getErrorCode() + "/" + e.getSQLState() + " " +   
                            e.getMessage() ) ;
		        	if(ErrorState.equals("23514")){response.sendRedirect("/FOS/EditDetails.jsp?ErrorMsg=Passwords length must be > 6&uid="+UserID);}
		        	sentConf=true;
				}
			}
			}
			
			if(Name!=""){
				String qtemp = "update users set name = '" + Name + "' where uid = '"+UserID+"'";
				try {
					st.executeUpdate(qtemp);
				} catch (SQLException e) {
					String ErrorState=e.getSQLState();
		        	System.out.println("Caught SQLException " + e.getErrorCode() + "/" + e.getSQLState() + " " +   
                            e.getMessage() ) ;
		        	if(ErrorState.equals("23514")){response.sendRedirect("/FOS/EditDetails.jsp?ErrorMsg=Here Error&uid="+UserID);}
		        	sentConf=true;

				}
			}
			
			if(Address!=""){
				String qtemp = "update users set address = '" + Address + "' where uid = '"+UserID+"'";
				try {
					st.executeUpdate(qtemp);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(!sentConf){
				toUser(UserID, request, response, null);	
			}
			
		}
		
		if(num.equals("6"))
		{
			String UserID = request.getParameter("UserID");
			String UserData=getUserData(UserID);
			String[] parts=UserData.split("@");
			String Amount = request.getParameter("Amount");			
			String Passkey = request.getParameter("Passkey");
			boolean nullvals = false;
			boolean auth = false;
			int ammo = 0;
			String uid="";
			if(Amount.equals("") || Passkey.equals("")){
				nullvals = true;
			}
			
			else{
				uid = UserID;
				ammo = Integer.parseInt(Amount);
				auth = AuthenticateUser(uid,Passkey,"User");
			}
			if(auth&&(!(nullvals))){
				ammo = ammo + Integer.parseInt(parts[4]);
				String qtemp = "update users set wallet = " + ammo + " where uid = '"+uid+"'";
				boolean sent=false;
				try {
					st.executeQuery(qtemp);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				toUser(uid,request,response,null);
			}
			else{
				response.sendRedirect("/FOS/Wallet.jsp?ErrorMsg=Incorrect Credentials&UserData="+parts[0]+"@"+parts[1]+"@"+parts[4]);
			}
		}
		
		if(num.equals("8")){
			String sid=request.getParameter("SidPassing");
			System.out.println("SID is "+sid);
			String boolCheck=request.getParameter("boolCheck");
			System.out.println("boolCheck is "+boolCheck);
			String[] values={};
			if(boolCheck.equals("1")){values= request.getParameterValues("OrderIds");}
	        if(values!=null){
			System.out.println("VAlues length is "+values.length);
	        int amount=0;
	        for(int i=0;i<values.length;i++){
	        	String sqltemp="SELECT itemorder.quantity,menu.cost from itemorder,menu where itemorder.oid='"+values[i]+"' and itemorder.iid=menu.iid and menu.sid='"+sid+"'";	
	        try{
	        	ResultSet rs;
	        	rs = st.executeQuery(sqltemp);
	        	String found="";
	        	if(conn1!=null){
	        		while(rs.next()){
	        			String quantityS=rs.getString(1);
	        			int quantity=Integer.parseInt(quantityS);
	        			String costS=rs.getString(2);
	        			float cost=Float.parseFloat(costS);
	        			int costi=(int) cost;
	        			amount += quantity*costi;
	        		}
	        		rs.close();
	        	}
	        }
	        catch(SQLException ex){
		    	ex.printStackTrace();
		        System.out.println("*************2***************************");
		    }
	        }
	        
	        /*
	        String sqltemp="UPDATE seller set wallet=wallet+"+amount+" where sid='"+sid+"'";
	        try {
				st.executeUpdate(sqltemp);
			} catch (SQLException e) {
				e.printStackTrace();
		        System.out.println("********************3**************");
			}
			*/
	        
	        String sqltemp="";
	        for(int i=0;i<values.length;i++){
	        	sqltemp="update orders set DeliveryStatus='Delivered' where oid='"+values[i]+"'";
				try {
					st.executeUpdate(sqltemp);
				} catch (SQLException e) {
			        System.out.println("****************4************");
					e.printStackTrace();
				}
	        }
	        
	        
	        for(int i=0;i<values.length;i++){
	        	sqltemp="SELECT userorder.uid, itemorder.quantity, menu.cost from userorder, itemorder, menu where userorder.oid='"+values[i]+"' and itemorder.oid='"+values[i]+"' and itemorder.iid=menu.iid and menu.sid='"+sid+"'";
	        	ResultSet rs;
	        	int cost=0;
	        	String uid="";
	        	try {
	        		rs=st.executeQuery(sqltemp);
	        		while(rs.next()){
	        		uid=rs.getString(1);	
		        	String quantityS=rs.getString(2);
	    			int quantity=Integer.parseInt(quantityS);
	    			String costS=rs.getString(3);
	    			float costtemp=Float.parseFloat(costS);
	    			int costfinal=(int) costtemp;
	    			cost += costfinal*quantity;
				}
				} catch (SQLException e) {
					System.out.println("****************5************");
					e.printStackTrace();
				}
	        	
	        	/*
	        	sqltemp="UPDATE users set wallet=wallet-"+cost+" where uid='"+uid+"'";
	        	try {
					st.executeUpdate(sqltemp);
				} catch (SQLException e) {
					System.out.println("****************6************");
					e.printStackTrace();
				}
	        	*/
	        }
	        }
	        toSeller(sid,request,response);
		} 
		
		if(num.equals("12")){
			String sid=request.getParameter("sid");
			String ItemName=request.getParameter("ItemName");
			ItemName=ItemName.toLowerCase();
			System.out.println("ItemName is "+ItemName);
			String Cost=request.getParameter("Cost");
			String Minutes=request.getParameter("Minutes");
			boolean sent=false;
			if((ItemName.equals(""))||(Minutes.equals(""))||(Cost.equals(""))){
				response.sendRedirect("/FOS/InsertItems.jsp?ErrorMsg=Values can't be empty&sid="+sid);
				sent=true;
			}
			String sql="select iid from item where name='"+ItemName+"'";
	        ResultSet rs;
	        String iid="";
			try {
				rs=st.executeQuery(sql);
				while(rs.next()){
					iid=rs.getString(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("iid is "+iid);
			if(iid.equals("")){
				if(!sent){response.sendRedirect("/FOS/SomeMoreInfo.jsp?sid="+sid);}	
			}
			else{
			try{
				
				String sql2="insert into menu values(?,?,?,?)";
	        	PreparedStatement pStmt=conn1.prepareStatement(sql2);
	        	pStmt.setString(1,sid);
	        	pStmt.setString(2,iid);
	        	int costInt=Integer.parseInt(Cost);
	        	pStmt.setInt(3,costInt);
	        	pStmt.setString(4,Minutes);
	        	System.out.println("Sid is "+sid+" iid is "+iid+" cost is "+Cost+"");
	        	pStmt.executeUpdate();
	        	toSeller(sid,request,response);
	        }
	        catch(SQLException e){
	        	String ErrorState=e.getSQLState();
	        	System.out.println("Caught SQLException " + e.getErrorCode() + "/" + e.getSQLState() + " " +   
                        e.getMessage() ) ;
	        	if(!sent){
	        	if(ErrorState.equals("23505")){response.sendRedirect("/FOS/InsertItems.jsp?ErrorMsg=Item already present in your database&sid="+sid);}
	        	else{response.sendRedirect("/FOS/Temp.jsp?name=SqlError, Restart server");} // Should Wirte code for different kinds of errors
	        	}
	        	}
			}
		}
		
		else if(num.equals("14")){
			String sql1="select count(*) from item";
			String count="";
			ResultSet rs1;
			try {
				rs1=st.executeQuery(sql1);
				while(rs1.next()){
					count=rs1.getString(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int iidnew=Integer.parseInt(count);
			iidnew++;
			String iidNewString=Integer.toString(iidnew);
			String sid=request.getParameter("sid");
			String itemName=request.getParameter("ItemName");
			itemName=itemName.toLowerCase();
			String isVeg=request.getParameter("IsVeg");
			if(isVeg.equals("Vegetarian")){isVeg="1";}
			else{isVeg="2";}
			int isVegInt=Integer.parseInt(isVeg);
			String cuisine=request.getParameter("Cuisine");
			if(cuisine.equals("North-Indian")){cuisine="1";}
			else if(cuisine.equals("Chinese")){cuisine="2";}
			else if(cuisine.equals("South-Indian")){cuisine="3";}
			int CuisineInt=Integer.parseInt(cuisine);
			String cost=request.getParameter("Cost");
			int costInt=Integer.parseInt(cost);
			String exptime=request.getParameter("Minutes");
			String sql="insert into item values(?,?,?,?)";

			try {
	        	PreparedStatement pStmt=conn1.prepareStatement(sql);
				pStmt = conn1.prepareStatement(sql);
	        	pStmt.setString(1,iidNewString);
	        	pStmt.setString(2,itemName);
	        	pStmt.setInt(3,isVegInt);
	        	pStmt.setInt(4,CuisineInt);
	        	pStmt.executeUpdate();
	        	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			sql="insert into menu values (?,?,?,?)";
			try {
	        	PreparedStatement pStmt=conn1.prepareStatement(sql);
				pStmt = conn1.prepareStatement(sql);
	        	pStmt.setString(1,sid);
	        	pStmt.setString(2,iidNewString);
	        	pStmt.setInt(3,costInt);
	        	pStmt.setString(4,exptime);
	        	
	        	pStmt.executeUpdate();
	        	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			toSeller(sid,request,response);
		}
		
		
		else if(num.equals("3"))
		{
			String SelectedCuisine = "";
			String uid= request.getParameter("uid");
			for(Integer i=1; i<=3; i++)
			{
				System.out.println(i);
				String temp = request.getParameter(i.toString());
				System.out.println(temp);
				if(temp!=null && temp.equals("abc"))
				{
					SelectedCuisine = SelectedCuisine + getCuisineName(i.toString()) + " ";
				}
			}
			System.out.println("SelectedCuisine : " +  SelectedCuisine);
			if(SelectedCuisine.equals("")) SelectedCuisine = null;
			toUser(uid, request, response, SelectedCuisine);
		}
		
		else if(num.equals("4"))
		{
			System.out.println("uid");
			String UserId = request.getParameter("UserId");
			//String UserDataArray[] = UserData.split(" "); 
			//System.out.println(s);
			String SellerId = request.getParameter("SellerId");
			//String SellerDataArray[] = SellerData.split(" "); 
			System.out.println("User Id : "+UserId);
			System.out.println("Seller Id : "+SellerId);
			String q1 = "Select * from menu natural join item where sid = '" + SellerId + "'";
			String SelectedCuisine = "";
			for(Integer i=1; i<=3; i++)
			{
				System.out.println(i);
				String temp = request.getParameter(i.toString());
				System.out.println(temp);
				if(temp!=null && temp.equals("abc"))
				{
					SelectedCuisine = SelectedCuisine + getCuisineName(i.toString()) + " ";
				}
			}
			System.out.println("SelectedCuisine : " + SelectedCuisine);
			boolean Veg = false;
			boolean NonVeg = false;
			for(Integer i=4; i<=5; i++)
			{
				System.out.println(i);
				String temp = request.getParameter(i.toString());
				System.out.println(temp);
				if(temp!=null && temp.equals("abc"))
				{
					if(i==4){
						Veg = true;
					}
					if(i==5){
						NonVeg = true;
					}
				}
			}
			System.out.println("Veg" + Veg + "Nonveg" + NonVeg);
			boolean constraint2 = true;
			if(Veg == NonVeg){
				constraint2 = false;
			}
			try {
				String UserData = "";
				String SellerData = "";
				String qUser = "select * from users where uid = '" + UserId + "'";
				String qSeller = "select * from seller where sid = '" + SellerId + "'";
				ResultSet rs= st.executeQuery(qUser);
				rs.next();
				UserData = rs.getString("uid") + "@" + rs.getString("name") + "@" + rs.getString("wallet");//Change delimiter
				rs= st.executeQuery(qSeller);
				rs.next();
				SellerData = rs.getString(1) + "@" + rs.getString(2) + "@" + rs.getString(3);//Change delimiter
				//Not adding cuisine details to seller data which was there in the original call
				String ItemData = "";
				System.out.println("UserData " + UserData);
				System.out.println("SellerData " + SellerData );
				rs = st.executeQuery(q1);
				while(rs.next())
				{
					
					boolean temp = SelectedCuisine.contains(getCuisineName(rs.getString("cuisine")));
					if(SelectedCuisine.equals("")) temp = true;
					System.out.println(getCuisineName(rs.getString("cuisine")) + temp);
					boolean finalbool;
					if(!constraint2) finalbool = temp;
					else 
					{
						if(Veg) finalbool = (rs.getString("isveg").equals("1")) && temp;
						else finalbool = (rs.getString("isveg").equals("2")) && temp;
					}
					System.out.println(finalbool + " constraint(veg) " + constraint2 +
							" isveg " + rs.getString("isveg").equals("1") +" temp " + temp);
					if(finalbool)
					{
						ItemData += rs.getString(1) + "@" + rs.getString(2) + "@"  + rs.getString(3) + "@" +
								rs.getString(4) + "@" + rs.getString(5) + "@"  + rs.getString(6) + "@" +
								rs.getString(7);
						ItemData += "//";
					}
					//System.out.println();
				}
				System.out.println("ItemData : " + ItemData);
				//request.setAttribute("SellerData", SellerDataNew);
				if(ItemData.equals("")) System.out.println("check");
				request.setAttribute("ItemData",ItemData);
				request.setAttribute("SellerData",SellerData);
				request.setAttribute("UserData",UserData);
				//request.setAttribute("SellerCuisine",SellerCuisine);
				//System.out.println(ItemData);
				RequestDispatcher reqDispatcher = getServletConfig().getServletContext().
						getRequestDispatcher("/UserOrder.jsp");
				try {
					reqDispatcher.forward(request,response);
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(num.equals("9")){
			//response.sendRedirect("/FOS/Temp.jsp?name=BaneAyindhi");
			String sid=request.getParameter("SidPassing");
			String boolCheck=request.getParameter("boolCheck");
			System.out.println("boolCheck is "+boolCheck);
			String[] values={};
			System.out.println("BoolCheck is "+boolCheck);
			String QueryString=request.getQueryString();
			System.out.println("Query String is "+QueryString);
			if(boolCheck.equals("1")){values= request.getParameterValues("DeletedItems");}
	        if(values != null){
	
			System.out.println("VAlues length is "+values.length);
			for(int i=0;i<values.length;i++){
				String sql="delete from menu where sid='"+sid+"' and iid='"+values[i]+"'";
				try {
					st.executeUpdate(sql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					
					e.printStackTrace();
				}
				
			}
	        }
			System.out.println("YOO!! Came here");
			toSeller(sid,request,response);
			
		}
		
		}
		
		boolean AuthenticateUser(String UserName, String PassWd, String sor){
			String sql="";
			boolean ret=false;
			if(sor.equals("Seller")){
	        	sql="Select * from seller where sid='"+UserName+"' and password='"+PassWd+"'";
	        }
	        else if(sor.equals("User")){
	        	sql="Select * from users where uid='"+UserName+"' and password='"+PassWd+"'";
	        }
	        try{
	        	ResultSet rs;
	        	rs = st.executeQuery(sql);
	        	String found="";
	        	if(conn1!=null){
	        		while(rs.next()){
	        			found=rs.getString("name");
	        		}
	        		rs.close();
	        		if(found.equals("")){
	        			ret=false;
	        		}
	        		else{
	        			ret=true;
	        		}
	        	}
	        }
	        catch(SQLException ex){
		    	ex.printStackTrace();
		        System.out.println("HERE IS THE ERROR2");
		    }
	        return ret;
		}
		
		String getCuisine(String sid){
			String Cuisine="";
			String qCuisine = "select distinct cuisine from menu natural join item where sid ='"+sid+"'";
			try {
				ResultSet rs1 = st.executeQuery(qCuisine);
				
				while(rs1.next()){
					Cuisine = Cuisine+getCuisineName(rs1.getString(1))+" ";
				}
				rs1.close();
				//System.out.println(Cuisine);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return Cuisine;
			
		}
		
		String getCuisineName(String n){
			String deepu="";
			if(n.equals("1")){
				deepu =  "North-Indian";
			}
			if(n.equals("2")){
				deepu =  "Chinese";
			}
			if(n.equals("3")){
				deepu =  "South-Indian";
			}
			return deepu;
		}
		
		void toUser(String uid, HttpServletRequest request, HttpServletResponse response, String SelectedCuisines)
		{	
			
			String q1 = "Select * from seller";
			String qname = "select * from users where uid = '"+uid+"'";
			String q2 = "select distinct cuisine from item";
			ResultSet rs;
			String Uname="";
			String Uwallet = "";
			String UserData="";
			//uid = uid + " deepak";
			try {
				 /*rs = st.executeQuery(q2);
				 rs.next();
				 String AllCuisine = "";
				 while(rs.next())
				 {
					 AllCuisine = AllCuisine + rs.getString(1);
				 }
				 System.out.println(AllCuisine);
				 */
				 rs = st.executeQuery(qname);
				 rs.next();
				 Uname = rs.getString(2);
				 Uwallet = rs.getString(5);
				 UserData = uid + "@" + Uname + "@" + Uwallet;
				 rs = st.executeQuery(q1);
				 String SellerData ="";
				 while(rs.next())
					{
					 	String id = rs.getString(1);
					 	String name = rs.getString(2);
					 	String address = rs.getString(3);
					 	SellerData = SellerData + id + "@" + name + "@" + address +  "//";
					 	//AllSellerCuisine = AllSellerCuisine + SellerCuisine + "//";
					}
				 rs.close();
				 String SellerCuisine = "";//getCuisine(id);//" " + SellerCuisine +
				 String SellerDataNew = "";
				 for(String s:SellerData.split("//"))
				 {
					 if(SelectedCuisines == null)
					 {
						 System.out.println("Nothing Selected");
						 SellerCuisine = getCuisine(s.split("@")[0]);
						 //System.out.println(SellerCuisine + s.split(" ")[0]);
						 SellerDataNew = SellerDataNew + s + "@" + SellerCuisine + "//"; 
					 }
					 else
					 {
						 System.out.println("Selected Only");
						 SellerCuisine = getCuisine(s.split("@")[0]);
						 boolean IsThere = false;
						 for(String sel:SellerCuisine.split(" "))
						 {
							 System.out.println(sel);
							 if(SelectedCuisines.contains(sel)) IsThere = true;
						 }
						 System.out.println("IsThere" + IsThere);
						 if(IsThere) SellerDataNew = SellerDataNew + s + "@" + SellerCuisine + "//";
					 }
				 }
				 //request.setAttribute("CuisineData", AllCuisine);
				 request.setAttribute("SellerData", SellerDataNew);
				 request.setAttribute("UserData",UserData);
				 //request.setAttribute("SellerCuisine",SellerCuisine);
					RequestDispatcher reqDispatcher = getServletConfig().getServletContext().getRequestDispatcher("/User.jsp");
					try {
						reqDispatcher.forward(request,response);
					} catch (ServletException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//
			//System.out.println("linkin");
			
			
			
			System.out.println("to user done");
		}
		
		void toSeller(String UserName, HttpServletRequest request, HttpServletResponse response){
			System.out.println("INSIDER SELLER");
			String sql="Select * from seller where sid='"+UserName+"'";
			String SellerData ="";
			ResultSet rs;
			try{
				rs = st.executeQuery(sql);
				 while(rs.next()){
					 	String SellerID = rs.getString(1);
					 	String Name = rs.getString(2);
					 	String Wallet = rs.getString(5);
					 	SellerData = SellerID + "@" + Name + "@" + Wallet + "//";
				}
				rs.close();
			}
			catch(SQLException e){e.printStackTrace();}
			//System.out.println("Seller data is "+SellerData);
			sql="Select a.name, a.address, e.name, d.quantity, f.oid from users as a, userorder as b, orders as c,itemorder as d,item as e, sellerorder as f where f.sid='"+UserName+"' and f.oid=c.oid and f.oid=b.oid and f.oid=d.oid and b.uid=a.uid and d.iid=e.iid and c.DeliveryStatus='NotDelivered'";
			String PersonalData=UserName;
			String OrderData="";
			ResultSet rs1;
			try{
				rs1 = st.executeQuery(sql);
				System.out.println("before LOOP");
				 while(rs1.next()){
					 	String UName = rs1.getString(1);
					 	String UserAddress = rs1.getString(2);
					 	String ItemName= rs1.getString(3);
					 	String Quantity = rs1.getString(4);
					 	String OrderId = rs1.getString(5);
					 	OrderData += UName + "@" + UserAddress + "@" + ItemName + "@" + Quantity + "@" + OrderId + "//";
					 	System.out.println("PRINTING IN LOOP");
				}
				System.out.println("Outside LOOP");
				rs1.close();
			}
			catch(SQLException e){e.printStackTrace();}
			System.out.println("POSITION 2");
			System.out.println("MyData is "+SellerData);
			System.out.println("SIDPassing is "+PersonalData);
			System.out.println(OrderData);
			String testing="";
			request.setAttribute("testing", testing);
			request.setAttribute("MyData", SellerData);
			request.setAttribute("OrderData", OrderData);
			request.setAttribute("SIDPassing",PersonalData);
			RequestDispatcher reqDispatcher = getServletConfig().getServletContext().getRequestDispatcher("/Seller.jsp");
			try {
				reqDispatcher.forward(request,response);
			} catch (ServletException e) {
				System.out.println("POSITION 3");
				//e.printStackTrace();
			} catch (IOException e) {
				System.out.println("POSITION 4");
				//e.printStackTrace();
			}
		}
		
		String getUserData(String uid){
			String sql="select * from users where uid='"+uid+"'";
			ResultSet rs;
			String out="";
			try {
				rs=st.executeQuery(sql);
				while(rs.next()){
					out=rs.getString(1)+"@"+rs.getString(2)+"@"+rs.getString(3)+"@"+rs.getString(4)+"@"+rs.getString(5);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return out;
			
		}
		
		String getSellerData(String sid){
			String sql="select * from user where sid='"+sid+"'";
			ResultSet rs;
			String out="";
			try {
				rs=st.executeQuery(sql);
				while(rs.next()){
					out=rs.getString(1)+"@"+rs.getString(2)+"@"+rs.getString(3)+"@"+rs.getString(4)+"@"+rs.getString(5);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return out;			
		}
		
		String getItemData(String iid){
			String sql="select * from item where iid='"+iid+"'";
			ResultSet rs;
			String out="";
			try {
				rs=st.executeQuery(sql);
				while(rs.next()){
					out=rs.getString(1)+"@"+rs.getString(2)+"@"+rs.getString(3)+"@"+rs.getString(4);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return out;			
		}
		
		
}
