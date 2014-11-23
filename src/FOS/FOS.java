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
		if(num.equals("8")){
			String name=request.getParameter("name");
			String sid=request.getParameter("sid");
			if(name.equals("Menu")){
				String Output="";
				String sql="Select * from menu where sid='"+sid+"'";
				ResultSet rs1;
				try{
					rs1 = st.executeQuery(sql);
      				 while(rs1.next()){
						 	String iid = rs1.getString(2);
						 	String cost = rs1.getString(3);
						 	String exptime = rs1.getString(4);
						 	Output += iid + "|||" + cost + "|||" + exptime +  "//";
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
				String sql="Select  f.oid, a.name, e.name, d.quantity from users as a, userorder as b, orders as c,itemorder as d,item as e, sellerorder as f where f.sid='"+sid+"' and f.oid=c.oid and f.oid=b.oid and f.oid=d.oid and b.uid=a.uid and d.iid=e.iid";
				ResultSet rs1;
				String Output="";
				try{
					rs1 = st.executeQuery(sql);
      				 while(rs1.next()){
						 	String oid = rs1.getString(1);
						 	String UserName = rs1.getString(2);
						 	String ItemName = rs1.getString(3);
						 	String ItemQuantity = rs1.getString(4);
						 	Output += oid + "|||" + UserName + "|||" + ItemName + "|||" + ItemQuantity +  "//";
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
			
			
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("*******************CAME HERE************");
		String num=request.getParameter("from");
		if(num.equals("2")){
			System.out.println("*******************CAME HERE************");
			String lor=request.getParameter("submitvalue");
			if(lor.equals("login")){
				String UserId= request.getParameter("Username");
		        String PassWd= request.getParameter("Password");
		        String sor= request.getParameter("SellerOrUser");
		        boolean retVal=AuthenticateUser(UserId,PassWd,sor);
		        if((retVal)&&(sor.equals("User"))){toUser(UserId,request,response);}
		        if((retVal)&&(sor.equals("Seller"))){toSeller(UserId,request,response);}
		        else {response.sendRedirect("/FOS/Temp.jsp?name=Ettindhi!!");}
		    }
			else if(lor.equals("signup")){
				String UserId= request.getParameter("Username");
		        String PassWd= request.getParameter("Password");
		        String Address= request.getParameter("Address");
		        String Name= request.getParameter("Name");
		        String sor= request.getParameter("SellerOrUser");
		        String sql="";
		        if(sor.equals("Seller")){sql="insert into seller values(?,?,?,?)";}
		        else if(sor.equals("User")){sql="insert into users values(?,?,?,?)";}
		        try{
		        	PreparedStatement pStmt=conn1.prepareStatement(sql);
		        	pStmt.setString(1,UserId);
		        	pStmt.setString(2,Name);
		        	pStmt.setString(3,Address);
		        	pStmt.setString(4,PassWd);
		        	pStmt.executeUpdate();
		        	response.sendRedirect("/FOS/Temp.jsp?name=Raccha");
		        }
		        catch(SQLException e){
		        	String ErrorState=e.getSQLState();
		        	System.out.println("Caught SQLException " + e.getErrorCode() + "/" + e.getSQLState() + " " +   
                            e.getMessage() ) ;
		        	if(ErrorState.equals("23505")){response.sendRedirect("/FOS/Temp.jsp?name=SelectAnotherUserId");}
		        	if(ErrorState.equals("23514")){response.sendRedirect("/FOS/Temp.jsp?name=Password Length Must be>=4");}
		        }
			}
		}
		
		if(num.equals("8")){
			
			System.out.println("CAME HERE");
			//response.sendRedirect("/FOS/Temp.jsp?name=Working");
			String sid=request.getParameter("SidPassing");
	        String[] values= request.getParameterValues("OrderIds");
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
	        
	        String sqltemp="UPDATE seller set wallet=wallet+"+amount+" where sid='"+sid+"'";
	        try {
				st.executeUpdate(sqltemp);
			} catch (SQLException e) {
				e.printStackTrace();
		        System.out.println("********************3**************");
			}
	        
	        
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
	        	sqltemp="UPDATE users set wallet=wallet-"+cost+" where uid='"+uid+"'";
	        	try {
					st.executeUpdate(sqltemp);
				} catch (SQLException e) {
					System.out.println("****************6************");
					e.printStackTrace();
				}
	        }
	        toSeller(sid,request,response);
		} 
		
		if(num.equals("12")){
			String sid=request.getParameter("sid");
			String ItemID=request.getParameter("ItemId");
			String Cost=request.getParameter("Cost");
			String Hours=request.getParameter("Hours");
			String Minutes=request.getParameter("Minutes");
			String Seconds=request.getParameter("Seconds");
			String Interval=Hours+":"+Minutes+":"+Seconds;
			String sql="insert into menu values(?,?,?,?)";
	        try{
	        	PreparedStatement pStmt=conn1.prepareStatement(sql);
	        	pStmt.setString(1,sid);
	        	pStmt.setString(2,ItemID);
	        	pStmt.setString(3,Cost);
	        	pStmt.setString(4,Interval);
	        	pStmt.executeUpdate();
	        	response.sendRedirect("/FOS/Temp.jsp?name=Successful");
	        }
	        catch(SQLException e){
	        	response.sendRedirect("/FOS/Temp.jsp?name=Dobbindhi"); // Should Wirte code for different kinds of errors
	        }

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
		
		void toUser(String uid, HttpServletRequest request, HttpServletResponse response)
		{
			String q1 = "Select * from seller";
			ResultSet rs;
			try {
				 rs = st.executeQuery(q1);
				 String SellerData ="";
				 while(rs.next())
					{	String id = rs.getString(1);
					 	String name = rs.getString(2);
					 	String address = rs.getString(3);
					 	SellerData = SellerData + name + " " + address + "//";
					}
				 request.setAttribute("data", SellerData);
				 request.setAttribute("id",uid);	
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
					 	SellerData = SellerID + " " + Name + " " + Wallet + "//";
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
					 	OrderData += UName + " " + UserAddress + " " + ItemName + " " + Quantity + " " + OrderId + "//";
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
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
