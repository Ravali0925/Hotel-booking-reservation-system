package com.hb.customerlogin;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.hb.colors.ConsoleColors;
public class CustomerLogin
{
	 static String username,password;
	public static void customerLog()
	{
		String option="y";
	     int choice;
	     Scanner din=new Scanner(System.in);
	     while(option.equalsIgnoreCase("y"))
	     {
	    	 System.out.println(ConsoleColors.DARK_BLUE+"1. customer Register");   // Print statement
	    		System.out.println("2.customer Login ");     // print statement
	    		System.out.println("3.Logout");            // print statement
				System.out.println("Select Your Option"+ConsoleColors.RESET);    // print statement
				choice=din.nextInt();
				switch(choice)
				{
				case 1:customerRegister();
					break;
				case 2:
					customerLogin();		
					 break;
				case 3: System.out.println("Customer Logout Success");					
				}			
				System.out.println("Do You Want to Continue Press Y or For Exit Press N");
	     }
	}
static void customerRegister()
{
	String cname,cemail,cmobile,cgender,ccity,cstate,cpaasword;
	Scanner din = new Scanner(System.in);
	
	System.out.println(ConsoleColors.BLUE_BRIGHT+"** Welcome to Happy Hotel Booking **"+ConsoleColors.RESET);
	System.out.println(ConsoleColors.BLUE_BRIGHT+"Enter Customer Name"+ConsoleColors.RESET);
	cname = din.nextLine();
	System.out.println(ConsoleColors.BLUE_BRIGHT+"Enter Customer Email"+ConsoleColors.RESET);
	cemail = din.nextLine();
	System.out.println(ConsoleColors.BLUE_BRIGHT+"Enter Customer Password"+ConsoleColors.RESET);
	cpaasword = din.nextLine();
	System.out.println(ConsoleColors.BLUE_BRIGHT+"Enter Customer Mobile"+ConsoleColors.RESET);
	cmobile = din.nextLine();
	System.out.println(ConsoleColors.BLUE_BRIGHT+"Enter Customer Gender"+ConsoleColors.RESET);
	cgender = din.nextLine();
	System.out.println(ConsoleColors.BLUE_BRIGHT+"Enter Customer City"+ConsoleColors.RESET);
	ccity = din.nextLine();
	System.out.println(ConsoleColors.BLUE_BRIGHT+"Enter Customer State"+ConsoleColors.RESET);
	cstate = din.nextLine();
	try
	   {		   
		  Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbooking","root","ravali");  // getConnection method is a Static method
		   String sql="insert into customer(customer_name,customer_email,password,customer_mobile,customer_gender,customer_city,customer_state)values(?,?,?,?,?,?,?)";
		   PreparedStatement ps=con.prepareStatement(sql);
		   ps.setString(1, cname);
		   ps.setString(2, cemail);
		   ps.setString(3, cpaasword);
		   ps.setString(4, cmobile);
		   ps.setString(5, cgender);
		   ps.setString(6, ccity);
		   ps.setString(7, cstate);		   
		   int i= ps.executeUpdate();
		   if(i>0)
		   {
			   System.out.println(ConsoleColors.BLUE_BRIGHT+"Customer Added successfully"+ConsoleColors.RESET);
			   // Email Sending Customer 
			   String senderEmail = "rangasravani2001@gmail.com";
		        String senderPassword = "zkjy lqgt yqdp ecoz";
		        // Recipient's email address
		        String RecipientEmail=cemail;//change accordingly  
		        // Set up properties for the email server
		        Properties properties = new Properties();
		        properties.put("mail.smtp.auth", "true");
		        properties.put("mail.smtp.starttls.enable", "true");
		        properties.put("mail.smtp.host", "smtp.gmail.com");
		        properties.put("mail.smtp.port", "587");
		        // Get the email session
		        Session session = Session.getInstance(properties, new Authenticator() {
		            @Override
		            protected PasswordAuthentication getPasswordAuthentication() {
		                return new PasswordAuthentication(senderEmail, senderPassword);
		            }
		        });
		        try {
		            // Create a message
		            Message message = new MimeMessage(session);
		            message.setFrom(new InternetAddress(senderEmail));
		            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(RecipientEmail));
		            message.setSubject("Hotel Reservation Confirmation");
		            message.setText("Dear " + cname + ",\n\nYou Successfully Registered\n\n\n  Thank you for choosing our hotel!");
		            // Send the message
		            Transport.send(message);
		            System.out.println("Please Login .... Start Booking your hotel rooms");
		            customerLog();
		        } 
		        catch (MessagingException e) 
		        {
		            e.printStackTrace();		            
		            System.out.println("Error sending confirmation email.");		            
		            return;
		        }
		   }
		   else
		   {
			   System.out.println("Customer not added..");
		   }
	   }
	   catch(SQLException e)
	   {
		   e.printStackTrace();
	   }	
}
static void customerLogin()
{
	Scanner din = new Scanner(System.in);
	
	System.out.println(ConsoleColors.GREEN_BRIGHT+"Enter Your Email"+ConsoleColors.RESET);
	username = din.next();
	System.out.println(ConsoleColors.GREEN_BRIGHT+"Enter Your Password"+ConsoleColors.RESET);
	password = din.next(); 
	int check=0;
	try
	{
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbooking", "root", "ravali");
		String sql="select * from customer";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		while(rs.next())
		{
			if(username.equals(rs.getString(3)) && password.equals(rs.getString(7)))
			{
				check = 1;
			}
		}
		if(check==1)
		{
			System.out.println("Welcome To Customer");
			process();
		}
		else
		{
			System.out.println("Invalid Credentials or Register First");
			customerLog();
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}
static void process()
{
	String option="y";
    int choice;
    Scanner din=new Scanner(System.in);
    try {
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbooking","root","ravali");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    while(option.equalsIgnoreCase("y"))
    {
   	 Scanner sc=new Scanner(System.in); 	
		 System.out.println(ConsoleColors.LIGHT_PURPLE+"1.VIEW ROOMS"+ConsoleColors.RESET);  // Print statement
		 System.out.println(ConsoleColors.LIGHT_PURPLE+"2.BOOK ROOMS"+ConsoleColors.RESET);    // Print statement
		 System.out.println(ConsoleColors.LIGHT_PURPLE+"3.BOOKED ROOMS"+ConsoleColors.RESET);  // Print statement
		 System.out.println(ConsoleColors.LIGHT_PURPLE+"4.CANCEL ROOMS"+ConsoleColors.RESET);  // Print statement
   		System.out.println(ConsoleColors.LIGHT_PURPLE+"5.CheckOut"+ConsoleColors.RESET);     // print statement
  		 System.out.println(ConsoleColors.LIGHT_PURPLE+"6.EXIT"+ConsoleColors.RESET);                // Print statement
			System.out.println(ConsoleColors.LIGHT_PURPLE+"Select Your Option"+ConsoleColors.RESET);
			choice=din.nextInt();
			switch(choice)
			{
			 case 1:
				view_rooms();
					 break;
				 case 2:			
				try {
					book_rooms();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					 break;				 
				 case 3:
				try {
					booked_rooms();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					 break;				 
				 case 4: System.out.println("Enter the Room Number to Delete");
				 int rn=din.nextInt();
					cancelrooms(rn);
					
					 break;				 
				 case 5:
				try {
					CheckOut();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					 break;				 			 
			case 6:
				exit();
				 break; 				 
			 default:
				 System.out.println("Invalid choice. Try again. ");   // Print statement				
			}			
    }	
}
static void view_rooms()
{
	try
   	{
		String ch="yes";
   		String sql="select * from add_rooms where   Availability_status ='"+ ch +"' ";
   		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbooking","root","ravali");
           	Statement st=con.createStatement();
           	ResultSet rs=st.executeQuery(sql);
           	System.out.println("List of Rooms  Available");
           	System.out.println("-------------------------------------------------------------*");
   			System.out.println("| roomnumber | room_type   | room_price  |  Availability_status | ");
   			System.out.println("-------------------------------------------------------------*");
           	while(rs.next())
           	{    
           		int roomnumber=rs.getInt(1);
           		String room_type=rs.getString(2);
           		int room_price=rs.getInt(3);
          		String availability=rs.getString("Availability_status");
           		System.out.printf("| %-11d | %-13s | %-11d | %-18s |\n ",roomnumber,room_type,room_price,availability);
           	}
           		System.out.println("----------------------------------------------------------*");           	
   	}
   	catch(SQLException e)
   	{
   		e.printStackTrace();
   	}
}
static void book_rooms() throws ClassNotFoundException 
{	
	try 
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbooking", "root", "ravali");
		Scanner sc=new Scanner(System.in);		
		System.out.println(ConsoleColors.BLUE+"Enter Reservation Date (dd-MMM-yyyy): Example -7-Jan-2025"+ConsoleColors.RESET);
		String input="";
		input+=sc.nextLine();		
		System.out.println(ConsoleColors.BLUE+"Enter Your  name:"+ConsoleColors.RESET);   // Print statement
		String guestName = sc.next();		
		System.out.println(ConsoleColors.BLUE+"Enter contact Number:"+ConsoleColors.RESET);
		String contactNumber = sc.next();
		System.out.println(ConsoleColors.BLUE+"Enter you are  email"+ConsoleColors.RESET);    
		 String cemail=sc.next();	
		 System.out.println(ConsoleColors.BLUE+"Enter Room number:"+ConsoleColors.RESET);   // Print statement
			int roomNumber = sc.nextInt();					 
		try {	
			Connection con1=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbooking", "root", "ravali");			
                //			Date date1 = (Date) obj.parse(input);			
			String sql = "insert into  book_rooms(guest_name, room_number, contact_number,reservation_dateandtime,customer_email,status) values(?,?,?,?,?,?)";
			PreparedStatement ps=con1.prepareStatement(sql);
			ps.setString(1, guestName);
			ps.setInt(2, roomNumber);
			ps.setString(3, contactNumber);
			ps.setString(4, input);
			ps.setString(5, cemail);
			ps.setString(6, "pending");
			int i=ps.executeUpdate();						
			//try (Statement st = con1.createStatement())
			//{
					
				if (i > 0)
				{		
					System.out.println(ConsoleColors.BLUE+"Reservation successful!..."+ConsoleColors.RESET);
					 // Sender's email address and credentials
					  String senderEmail = "rangasravani2001@gmail.com";
				        String senderPassword = "zkjy lqgt yqdp ecoz";
				        // Recipient's email address
				        String RecipientEmail=cemail;//change accordingly  
				        // Set up properties for the email server
				        Properties properties = new Properties();
				        properties.put("mail.smtp.auth", "true");
				        properties.put("mail.smtp.starttls.enable", "true");
				        properties.put("mail.smtp.host", "smtp.gmail.com");
				        properties.put("mail.smtp.port", "587");
				        // Get the email session
				        Session session = Session.getInstance(properties, new Authenticator() {
				            @Override
				            protected PasswordAuthentication getPasswordAuthentication() {
				                return new PasswordAuthentication(senderEmail, senderPassword);
				            }
				        });
				        try {
				            // Create a message
				            Message message = new MimeMessage(session);
				            message.setFrom(new InternetAddress(senderEmail));
				            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(RecipientEmail));
				            message.setSubject("Hotel Reservation Confirmation");
				            message.setText("Dear " + guestName + ",\n\nYour reservation for Room " + roomNumber +
				                    " on " + input + " has been processing...please wait .Thank you for choosing our hotel!");
				            // Send the message
				            Transport.send(message);
				            System.out.println("Request email sent successfully.");
				        } catch (MessagingException e) 
				        {
				            e.printStackTrace();		            
				            System.out.println("Error sending confirmation email.");
				            System.out.println("Booking failed.Room might be unavailable.");
				            System.out.println("Sorry!!Reservation is not conformed");
				            System.out.println("reservation is not  conformed for room "+roomNumber+".");
				            return;
				        }
					}			
					else 
					{
						System.out.println(ConsoleColors.RED+"Reservation failed..."+ConsoleColors.RESET);    // Print statement
					}
				
			} 
	catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		catch(SQLException e)   // catch is a method,during at run time if we are any issue it will display the error
		{
			e.printStackTrace();
		}
	}
   private static void booked_rooms() throws SQLException   // User-defined Method
{
	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbooking", "root", "ravali");
   String sql = "select * from  book_rooms where customer_email='"+username +"'";
   
	try(Statement st=con.createStatement();			
		 ResultSet rs=st.executeQuery(sql))
	{
		System.out.println("Current bookedrooms : ");    // Print statement
		System.out.println("----------------------------------------------------------------------------------------------");
		System.out.println("|  room_number   | contact_number |  reservation_dateandtime  |  customer_email   |   status   |");
		System.out.println("-----------------------------------------------------------------------------------------------");
		while(rs.next())
		{			
			int room_number=rs.getInt("room_number");
			String contact_number=rs.getString("contact_number");
			String reservation_dateandtime=rs.getString("reservation_dateandtime");
			String customer_email=rs.getString("customer_email");
		    String status=rs.getString("status");		    
			System.out.printf("|  %-12d  |  %-12s |  %-15s|  %-19s|  %-12s |\n ",room_number,contact_number,reservation_dateandtime,customer_email,status);
		}
		System.out.println("-------------------------------------------------------------------------------------------------");
	}
	catch(SQLException e)   // catch is a method,during at run time if we are any issue it will display the error
	{
		e.printStackTrace();
	}
}
 private static void cancelrooms(int room_number)
{
	String sql="delete from book_rooms where room_number=?";
	try(Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbooking", "root", "ravali");
			PreparedStatement preparedStatement=con.prepareStatement(sql))
	{
   preparedStatement.setInt(1,room_number);
    int rowsAffected=preparedStatement.executeUpdate();
    if(rowsAffected>0)
    {
    	System.out.println(ConsoleColors.CYAN_BRIGHT+"Booking canceled successfully."+ConsoleColors.RESET);
    	String check1="Yes";
		  String sql2="Update add_rooms set availability_status='"+check1 +"' where room_number='"+room_number+"'";
		  PreparedStatement ps1=con.prepareStatement(sql2);
		  ps1.executeUpdate();
		  String senderEmail = "rangasravani2001@gmail.com";
	        String senderPassword = "zkjy lqgt yqdp ecoz";
	        // Recipient's email address
	        String RecipientEmail=username;//change accordingly  
	        // Set up properties for the email server
	        Properties properties = new Properties();
	        properties.put("mail.smtp.auth", "true");
	        properties.put("mail.smtp.starttls.enable", "true");
	        properties.put("mail.smtp.host", "smtp.gmail.com");
	        properties.put("mail.smtp.port", "587");
	        // Get the email session
	        Session session = Session.getInstance(properties, new Authenticator() {
	            @Override
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(senderEmail, senderPassword);
	            }
	        });
	        try {
	            // Create a message
	            Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress(senderEmail));
	            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(RecipientEmail));
	            message.setSubject("Hotel Booking Cancle Successfully ");
	            message.setText("Dear Customer   \n\nYour reservation for Room " + room_number +
	                    "on    has been Canceled Successfully .Thank you for choosing our hotel!");
	            // Send the message
	            Transport.send(message);
	            System.out.println("Request email sent successfully.");
	        } catch (MessagingException e) 
	        {
	            e.printStackTrace();		 
    }
    }
    else
    {
		System.out.println(ConsoleColors.RED+"Booking not found or cancellation failed."+ConsoleColors.RESET);	
    }
	}
	catch(SQLException e)
	{
		e.printStackTrace();
	}
	}
static void  CheckOut() throws SQLException
{
	int room_number;
	String customer_email="";
	int amount=0;
	Scanner sc=new Scanner(System.in);
	System.out.println(ConsoleColors.ORANGE+"Enter The roomnumber"+ConsoleColors.RESET);
	room_number=sc.nextInt();
	// 315
	String sql="select * from book_rooms where room_number='"+room_number+"'";
	try(Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbooking", "root", "ravali");	 
			Statement st=con.createStatement();
					ResultSet rs=st.executeQuery(sql))	
	{
		 String CheckInDate="",CheckOutDate="";
		if(rs.next())
		{
			 customer_email=rs.getString("customer_email"); // sravaniranga18@gmail.com
		     CheckInDate=rs.getString("reservation_dateandtime");// 2024-02-23 10:30:00			
       	}
       System.out.println(ConsoleColors.GREEN+"Enter Checkout Date"+ConsoleColors.RESET);       
       SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
     //  String checkOut = "10-Jun-2025";
       CheckOutDate=sc.next();
       try {
		java.util.Date date2 =  formatter.parse(CheckOutDate);
		java.util.Date date1 =  formatter.parse(CheckInDate);
		System.out.println(date1);
		System.out.println(date2);
		long diffInMillies = Math.abs(date2.getTime() - date1.getTime());
	    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
	    System.out.println(diff);
	    String sql1="select * from add_rooms where room_number='"+room_number+"'";
	    Statement st1=con.createStatement();
		ResultSet rs1=st.executeQuery(sql1);
		double price=0;
		if(rs1.next())
		{
			price = rs1.getDouble(3); // 2000
		}
		double totalamount = price * (diff+1); // calculation 
		System.out.println("Dear Customer - Total Amount Has to pay Rs- " + totalamount); // 6000
		System.out.println(ConsoleColors.GREEN+"Please enter your amount"+ConsoleColors.RESET);
		double camount;
		camount = sc.nextDouble(); // 6000
		double finalamount = totalamount - camount; // 6000 - 6000=0    6000-3000=3000
		String status="";
		if(finalamount==0)
		{
			status="Full Paid";	
			 // Sender's email address and credentials
	        String senderEmail = "rangasravani2001@gmail.com";
	        String senderPassword = "zkjy lqgt yqdp ecoz";
	        // Recipient's email address
	        String RecipientEmail=customer_email;//change accordingly  
	        // Set up properties for the email server
	        Properties properties = new Properties();
	        properties.put("mail.smtp.auth", "true");
	        properties.put("mail.smtp.starttls.enable", "true");
	        properties.put("mail.smtp.host", "smtp.gmail.com");
	        properties.put("mail.smtp.port", "587");
	        // Get the email session
	        Session session = Session.getInstance(properties, new Authenticator() {
	            @Override
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(senderEmail, senderPassword);
	            }
	        });
	        try {
	            // Create a message
	            Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress(senderEmail));
	            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(RecipientEmail));
	            message.setSubject("Hotel Reservation Confirmation");
	            message.setText("Dear Customer You are paid full Amount .Thank you for choosing our hotel!");
	            // Send the message
	            Transport.send(message);
	            System.out.println("Request email sent successfully.");
	        } catch (MessagingException e) 
	        {
	            e.printStackTrace();		            
	            System.out.println("Error sending confirmation email.");
	            System.out.println("Booking failed.Room might be unavailable.");
	            System.out.println("Sorry!!Reservation is not conformed");
	            return;
	        }
		}		       
		else if(finalamount>0)
		{
			status="Un Paid";
			System.out.println("Customer Does not pay the Amount");
		}       
		// Insert Query to store data
		String sql3="insert into billing(room_number,customer_email,CheckInDate,CheckOutDate,amount,status)values(?,?,?,?,?,?)";
		PreparedStatement ps1=con.prepareStatement(sql3);
		ps1.setInt(1, room_number);
		ps1.setString(2, customer_email);
		ps1.setString(3, CheckInDate);
		ps1.setString(4, CheckOutDate);
          ps1.setInt(5, amount);
	      ps1.setString(6, status);
		  ps1.executeUpdate();	    
       
}
    catch(Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	}
}
static void exit()
{
	Scanner sc=new Scanner(System.in);
	System.out.println("Do You Want To Continue Press Y Or Exit Press N");
	String choice = sc.next();
}
}
       
       
       
//       CheckOutDate =sc.next();   // 2024-02-25 10:30:00  
//       SimpleDateFormat obj = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");   
//       // In the try block, we will try to find the difference  
//       try {   
//           
//    	   String str_date = "11-June-07";
//    	   DateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
//    	   Date date = formatter.parse(str_date);
//       }
//       catch(Exception ex)
//       {
//    	   ex.printStackTrace();
//       }
           // Calucalte time difference in d
       // 2024-02-25
       // find the difference bitween the dates are 3 
       // find the room price 1000
       // 3* 1000=3000