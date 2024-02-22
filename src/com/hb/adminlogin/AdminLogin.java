package com.hb.adminlogin;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.hb.colors.ConsoleColors;
import com.hb.customException.HotelReservationException;
public class AdminLogin
{
private static ResultSet sc;
public void AdminLogin()
{
	Scanner din = new Scanner(System.in);
	String username,password;
	System.out.println(ConsoleColors.LIGHT_BLUE+"Enter Your UserName"+ConsoleColors.RESET);  // print statement
	username = din.next();
	System.out.println(ConsoleColors.LIGHT_BLUE+"Enter Your Password"+ConsoleColors.RESET); // print statement
	password = din.next(); 
	try
	{
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbooking", "root", "ravali");
		String sql="select * from admin";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		while(rs.next())
		{
			if(username.equals(rs.getString(2)) && password.equals(rs.getString(3)))
			{
				System.out.println(ConsoleColors.TEAL+"Welcome To admin"+ConsoleColors.RESET); // print statement
				process();
			}
			else
			{
				 new HotelReservationException(ConsoleColors.RED+"Sorry!!!Enter Invalid Credentails"+ConsoleColors.RESET);
			}
		}				
						System.out.println("Do You want to Continue Press Yes or No ");			
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}	
}
 static void process() throws SQLException, AddressException, MessagingException
{
	String option="y";
     int choice;
     Scanner din=new Scanner(System.in);
     Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbooking","root","ravali");
     while(option.equalsIgnoreCase("y"))
     {
    	 Scanner sc=new Scanner(System.in);
    	 System.out.println(ConsoleColors.PURPLE+"1.ADD ROOMS");          // print statement
		 System.out.println("2.CONFIRM RESERVATIONS");  // Print statement
		 System.out.println("3.VIEW RESERVATIONS");  // print statement
		 System.out.println("4.GET ROOM NUMBER");    // Print statement
		 System.out.println("5.UPDATE RESERVATION");  // Print statement
		 System.out.println("6.DELETE RESERVATION");  // Print statement
    	 System.out.println("7.VIEW CUSTOMER BILLING");  // print statement
   		 System.out.println("8.EXIT");                // Print statement
		 System.out.println("Select Your Option"+ConsoleColors.RESET);   // print statement
			choice=din.nextInt();
			switch(choice)
			{
			 case 1:
					add_rooms();
					 break;
				 case 2:
					 confirmReservation(con);
					 break;	
				 case 3: 
					 viewReservations();
				     break;
				 case 4:
					 getRoomNumber(con, sc);
					 break;				 
				 case 5:
					 updateReservation(con, sc);
					 break;				 
				 case 6:
					 deleteReservation(con, sc);
					 break;				 			
			    case 7:
				try {
					Billing();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 break;
			case 8:  try {
					exit();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				 
			 default:
				 System.out.println("Invalid choice. Try again. ");   // Print statement
				 break;
			}
     
			 System.out.println("Do You want to continue press Y otherwise N");
		     option=din.next();
		     if(option.equalsIgnoreCase("n"))
		     {
		    	 System.out.println("Thank You");
		    	 break;
		     }
     }
       
}
static void add_rooms()
{
	 int roomnumber;
	   String roomtype;
	   int roomprice;	   
	   String availability;
	   Scanner sc=new Scanner(System.in);
	   System.out.println(ConsoleColors.CYAN_BRIGHT+"Enter room number"+ConsoleColors.RESET);   // print statement
	   roomnumber=sc.nextInt();
	   System.out.println(ConsoleColors.CYAN_BRIGHT+"Enter room type"+ConsoleColors.RESET);    // print statement
	   roomtype=sc.next();
	   System.out.println(ConsoleColors.CYAN_BRIGHT+"Enter roomprice"+ConsoleColors.RESET);    // print statement
	   roomprice=sc.nextInt();
	   System.out.println(ConsoleColors.CYAN_BRIGHT+"Enter availability"+ConsoleColors.RESET);  // print statement
	   availability=sc.next();
	   try
	   {
		   Scanner din=new Scanner(System.in);
		  Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbooking","root","ravali");  // getConnection method is a Static method
		   String sql="insert into add_rooms(room_number,room_type,room_price,Availability_status)values(?,?,?,?)";
		   PreparedStatement ps=con.prepareStatement(sql);
		   ps.setInt(1, roomnumber);
		   ps.setString(2, roomtype);
		   ps.setInt(3, roomprice);
		   ps.setString(4, availability);
		   int i= ps.executeUpdate();
		   if(i>0)
		   {
			   System.out.println(ConsoleColors.GREEN+"Rooms Added successfully"+ConsoleColors.RESET);  // print statement 
		   }
		   else
		   {
			   System.out.println("Rooms not added.."); // print statement
		   }
	   }
	   catch(SQLException e)
	   {
		   e.printStackTrace();
	   }
	   }		
private static void confirmReservation(Connection con) throws SQLException, AddressException, MessagingException {
	
	String sql12= "select * from  book_rooms";
	try(Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(sql12))
	{
		System.out.println("Current Reservations : ");    // Print statement
		System.out.println("--------------------------------------------------------------------------------------------------------------------*");
		System.out.println("|  Guest_Name    | Room_Number     | Contact_Number   | Reservation_Date     |  customer_email          |  Status         |");
		System.out.println("--------------------------------------------------------------------------------------------------------------------*");
			Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbooking","root","ravali");
			Statement st1 = con1.createStatement();	
			while(rs.next())
			{						
			String guestname= rs.getString(1);
			int roomNumber= rs.getInt(2);			
			String contactNumber=rs.getString(3);
			String reservationDateTime= rs.getString(4);
			String  customeremail=rs.getString(5);	
			String Status=rs.getString(6);
			System.out.printf("| %-14s | %-15d | %-13s | %-20s | %-20s   |   %-20s| \n ",guestname,roomNumber,contactNumber,reservationDateTime,customeremail,Status);
		}
		System.out.println("----------------------------------------------------------------------------------------------------------------------*");
		confirmReservation();
	}	
}
 static void confirmReservation() throws SQLException, AddressException, MessagingException
{
  System.out.println(ConsoleColors.TEAL+"Enter the Roomnumber for confirmation"+ConsoleColors.RESET);	
  Scanner sc=new Scanner(System.in);
  System.out.println(ConsoleColors.TEAL+"Enter the roomnumber"+ConsoleColors.RESET);
  int roomNumber=sc.nextInt(); 
  System.out.println(ConsoleColors.TEAL+"Enter the Customer Email"+ConsoleColors.RESET);
  String cemail=sc.next();
  String check="confirm";
  Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbooking","root","ravali");
  String sql="Update book_rooms set status='"+check+"' where room_number='"+roomNumber+"'";
  String sql1="select * from book_rooms where room_number='"+roomNumber+"'";
//  PreparedStatement ps=con.prepareStatement(sql);
 // ps.setString(roomNumber, sql1);
  Statement st=con.createStatement();
	ResultSet rs=st.executeQuery(sql1);
	try
	  {
		  PreparedStatement ps=con.prepareStatement(sql);
		  int i=ps.executeUpdate();
		  if(i>0)
		  {	
			  String check1="No";
			  String sql2="Update add_rooms set availability_status='"+check1 +"' where room_number='"+roomNumber+"'";
			  PreparedStatement ps1=con.prepareStatement(sql2);
			  ps1.executeUpdate();
			  System.out.println(ConsoleColors.TEAL+"Room Confirmed"+ConsoleColors.RESET);
			  // send email to customer your room is booked throw email 
				 // Sender's email address and credentials
			  String host="smtp.gmail.com";   // Types of gmail sending hostname []
			  final String user="rangasravani2001@gmail.com";//change accordingly
			  final String password="qkhz mljm bfts cjmp";//change accordingly  			    
			  String RecipientEmail=cemail;			  
			   //Get the session object  
			   Properties props = new Properties();  
			   props.put("mail.smtp.host",host);  
			   props.put("mail.smtp.auth", "true"); 
			   props.put("mail.smtp.port", "587");
			   props.put("mail.smtp.starttls.enable", "true");
			   Session session = Session.getDefaultInstance(props,  
			    new javax.mail.Authenticator() {  
			      protected PasswordAuthentication getPasswordAuthentication() {  
			    return new PasswordAuthentication(user,password);  
			      }  
			    }); 
			   MimeMessage message = new MimeMessage(session);  
			     message.setFrom(new InternetAddress(user));   
			     message.addRecipient(Message.RecipientType.TO,new InternetAddress(RecipientEmail));  
			     message.setSubject(ConsoleColors.BLUE+"Hotel Reservation Confirmed Sucessfully"+ConsoleColors.RESET);  
			     message.setText("Dear Customer   \n\n Your Room is Confirmed \n\n Enjoy Your Day in our Hotel..    \n\n\n\n\n\n Thanks You for Choosing our Hotel");  		       
			    //send the message  
			     Transport.send(message);  			  
			     System.out.println(ConsoleColors.BLUE+"message sent successfully..."+ConsoleColors.RESET);
		  }
			else 
			{				 
		  System.out.println("Room Not Confirmed");
			}	
  }
 catch(SQLException e)
 {
	 e.printStackTrace();
}
}
 private static void viewReservations() {
		 try
		 {	 
		 Scanner sc=new Scanner(System.in); 
			Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbooking","root","ravali"); 		  
			   Statement st=con1.createStatement();
			   String sql1="select * from reservations";
			  ResultSet rs= st.executeQuery(sql1);
			  System.out.println("reservation id"+"\t"+"customername"+"\t"+"roomnumber"+"\t"+"contact number"+"\t"+"customer email"+"\t\t\t"+"status");
			  while(rs.next())
			  {
				  System.out.println(rs.getInt("rid")+"\t\t"+rs.getString("cname")+"\t\t"+rs.getInt("room_number")+"\t\t"+rs.getString("contact_number")+"\t"+rs.getString("customer_email")+"\t"+rs.getString("status"));				  
			  }
		 }
		 catch(Exception e)
		 {
		 e.printStackTrace();	 
		 }
	 }		 
private static void getRoomNumber(Connection con,Scanner sc)   // User-defined Method
	{
		try
		{
			System.out.println(ConsoleColors.BROWN+"Enter Reservation Id : "+ConsoleColors.RESET);    // Print statement
			int reservationId=sc.nextInt();
			System.out.println(ConsoleColors.BROWN+"Enter Customer Name :"+ConsoleColors.RESET);    // Print statement
			String guestName=sc.next();
			String sql="select * from reservations where rid ='"+reservationId+"' and cname='"+guestName+"'";   // sql query is a object
			try(Statement st=con.createStatement();
					ResultSet rs=st.executeQuery(sql))
			{
				if(rs.next())
				{
					int roomNumber=rs.getInt("room_number");
					System.out.println(ConsoleColors.BROWN+"Room number for Reservation ID "+ reservationId + "and Guest" + guestName+ " is : "+roomNumber+ConsoleColors.RESET);  // print statement					
				}
				else
				{
					System.out.println(ConsoleColors.RED+"Reservation Not Found for the given ID and guest name "+ConsoleColors.RESET);    // Print statement
				}
			}
		}
		catch(SQLException e)   // catch is a method,during at run time if we are any issue it will display the error
		{
			e.printStackTrace();
		}
	}
   private static void updateReservation(Connection con,Scanner sc)   // User-defined Method
   {
   	try
   	{
   		System.out.println(ConsoleColors.DARK_BLUE+"Enter Reservation ID Update"+ConsoleColors.RESET);   // Print statement
   		int reservationId11=sc.nextInt();
   		System.out.println(ConsoleColors.DARK_BLUE+"Enter New Room Number : "+ConsoleColors.RESET);   // Print statement
   		int newRoomNumber=sc.nextInt();
   		Statement st=con.createStatement();
		String sql="update reservations set room_number="+newRoomNumber+"where rid="+reservationId11+"";
   		int i=st.executeUpdate(sql);
   		if(i>0)
   		{
				System.out.println(ConsoleColors.DARK_BLUE+"Reservation Updated Successfully!"+ConsoleColors.RESET);   // Print statement

   		}
   		else
			{
				System.out.println(ConsoleColors.DARK_BLUE+"Reservation Updated Failed "+ConsoleColors.RESET);   // Print statement
			}
   	}
   		
   	catch(SQLException e)    // catch is a method,during at run time if we are any issue it will display the error
   	{
   		e.printStackTrace();
   	}
   }
   private static void deleteReservation(Connection con,Scanner sc)   // User-defined Method
   {
   	try
   	{
   		System.out.println("Enter Reservation ID to Delete : ");   // Print statement
   		int reservationId=sc.nextInt();
   		if(!reservationExists(con,reservationId))
   		{
   			System.out.println("Reservation Not Found for the given ID ");   // Print statement
   			return;
   		}
   		String sql="delete from reservations where reservation_id="+reservationId;
   		try(Statement st=con.createStatement())
   		{
   			int affectedRows=st.executeUpdate(sql);
   			if(affectedRows>0)
   			{
   				System.out.println("Reservation Deleted Successfully!");   // Print statement
   			}
   			else
   			{
   				System.out.println("Reservation Deleted Failed");   // Print statement
   			}
   		}
   	}
   	catch(SQLException e)   // catch is a method,during at run time if we are any issue it will display the error
   	{
   		e.printStackTrace();
   	}
   }
   static void Billing() throws SQLException, ParseException
   {	   
   	try
   	{   
   		Scanner sc=new Scanner(System.in);
   		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbooking","root","ravali");  		
   		String sql="select * from billing";
   		Statement st=con.createStatement(); 
        ResultSet rs=st.executeQuery(sql);
        if(rs.next())
        {       	
           	System.out.println("Bid:"+rs.getInt(1)); 
           	System.out.println("room_number:"+rs.getInt(2)); 
           	System.out.println("customer_email:"+rs.getString(3)); 
           	System.out.println("CheckInDate:"+rs.getString(4)); 
           	System.out.println("CheckOutDate:"+rs.getString(5)); 
           	System.out.println("amount:"+rs.getInt(6)); 
           	System.out.println("status:"+rs.getString(7)); 
           	System.out.println("*****************");
        } 
        else
        {
        	System.out.println("Billing Not Found");
        }      
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   	}
   }
   private static boolean reservationExists(Connection con,int reservationId)   // User-defined Method
   {
   	try
   	{
   		String sql="select reservation_id from reservations where reservation_id="+reservationId;   // sql query is a object
   		try(Statement st=con.createStatement();
   				ResultSet rs=st.executeQuery(sql))
   		{
   			return rs.next();
   		}
   	}
   	catch(SQLException e)   // catch is a method,during at run time if we are any issue it will display the error
   	{
   		e.printStackTrace();
   		return false;
   	}   	
   }  
   public static void exit() throws InterruptedException    // User-defined Method
   {
   	System.out.println("Exiting System");   // Print statement
   	int i=5;
   	while(i!=0)
   	{
   		System.out.print(".");
   		Thread.sleep(450);
   		i--;
   	}
   	System.out.println();
   	System.out.println("ThankYou For Using Reservation System!!! ");    // Print statement
   }
   }