import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class AdminLogin
{
void AdminLogin()
{
	Scanner din = new Scanner(System.in);
	String username,password;
	System.out.println("Enter Your UserName");
	username = din.next();
	System.out.println("Enter Your Password");
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
				System.out.println("Welcome To admin");
				process();
			}
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}	
}
 static void process() throws SQLException
{
	String option="y";
     int choice;
     Scanner din=new Scanner(System.in);
     Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbooking","root","ravali");
     while(option.equalsIgnoreCase("y"))
     {
    	 Scanner sc=new Scanner(System.in);
    	 System.out.println("1.ADD ROOMS");
		 System.out.println("2.VIEW RESERVATIONS");  // Print statement
		 System.out.println("3.GET ROOM NUMBER");    // Print statement
		 System.out.println("4.UPDATE RESERVATION");  // Print statement
		 System.out.println("5.DELETE RESERVATION");  // Print statement
    //	 System.out.println("6.View Customer Application"); // Print statement
    		System.out.println("6.View Customer History");     // print statement
   		 System.out.println("7.EXIT");                // Print statement
			System.out.println("Select Your Option");
			choice=din.nextInt();
			switch(choice)
			{
			 case 1:
					add_rooms();
					 break;
				 case 2:
					 viewReservations(con);
					 break;				 
				 case 3:
					 getRoomNumber(con, sc);
					 break;				 
				 case 4:
					 updateReservation(con, sc);
					 break;				 
				 case 5:
					 deleteReservation(con, sc);
					 break;				 			 
			 //case 6:customer_application();
				 // break;
			case 6:
				Customer_History();
				 break;
			case 7:  try {
					exit();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				 
			 default:
				 System.out.println("Invalid choice. Try again. ");   // Print statement				
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
	   System.out.println("Enter room number");
	   roomnumber=sc.nextInt();
	   System.out.println("Enter room type");
	   roomtype=sc.next();
	   System.out.println("Enter roomprice");
	   roomprice=sc.nextInt();
	   System.out.println("Enter availability");
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
			   System.out.println("Rooms Added successfully");
		   }
		   else
		   {
			   System.out.println("Rooms not added..");
		   }
	   }
	   catch(SQLException e)
	   {
		   e.printStackTrace();
	   }
	   }		
	private static void viewReservations(Connection con) throws SQLException   // User-defined Method
	{
		int reservationid;
		String guestname;
		int roomNumber;
		String contactNumber;
		String reservationDateTime;
		String customeremail;
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter reservation id");
		reservationid=sc.nextInt();
		System.out.println("Enter guestname");
		guestname=sc.next();
		System.out.println("Enter room number");
		roomNumber=sc.nextInt();
		System.out.println("Enter contact number");
		contactNumber=sc.next();
		System.out.println("Enter reservationDateTime");
		reservationDateTime=sc.next();
		System.out.println("Enter customeremail");
		customeremail=sc.next();
		String sql12= "insert into  reservations(reservation_id,guest_name, room_number, contact_number,reservation_dateandtime,customer_email)" +
		           "values('"+ reservationid+"', "+ guestname + "'," + roomNumber + ",' " +contactNumber +"','"+reservationDateTime+"','"+customeremail+"')";
		try(Statement st=con.createStatement();
				ResultSet rs=st.executeQuery(sql12))
		{
			System.out.println("Current Reservations : ");    // Print statement
			System.out.println("-----------------------------------------------------------------------------------------------------------------------*");
			System.out.println("| Reservation_id | Guest_Name      | Room_Number   | Contact_Number    | Reservation_Date         | customer_email           |");
			System.out.println("-----------------------------------------------------------------------------------------------------------------------*");
				Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbooking","root","ravali");
				Statement st1 = con.createStatement();				
				rs.getInt(1);
				rs.getString(2);
				rs.getInt(3);
				
				rs.getString(4);
				rs.getTimestamp(5).toString();
				rs.getString(6);
				System.out.printf("| %-14d | %-15s | %-13d | %-20s | %-19s  | %-20s |\n ",reservationid,guestname,roomNumber,contactNumber,reservationDateTime,customeremail);
			}
			System.out.println("----------------------------------------------------------------------------------------------------------------------*");
		}	
	private static void getRoomNumber(Connection con,Scanner sc)   // User-defined Method
	{
		try
		{
			System.out.println("Enter Reservation ID : ");    // Print statement
			int reservationId=sc.nextInt();
			System.out.println("Enter Guest Name : ");    // Print statement
			String guestName=sc.next();
			String sql="select room_number from reservations where reservation_Id ='"+reservationId+"' and guest_name='"+guestName+"'";   // sql query is a object
			try(Statement st=con.createStatement();
					ResultSet rs=st.executeQuery(sql))
			{
				if(rs.next())
				{
					int roomNumber=rs.getInt("room_number");
					System.out.println("Room number for Reservation ID "+ reservationId + "and Guest" + guestName+ " is : "+roomNumber);
					
				}
				else
				{
					System.out.println("Reservation Not Found for the given ID and guest name ");    // Print statement
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
   		System.out.println("Enter Reservation ID Update");   // Print statement
   		int reservationId=sc.nextInt();
   		sc.nextLine();
   		if(!reservationExists(con,reservationId))
   		{
   			System.out.println("Reservation Not Found for the given ID ");   // Print statement
   			return;
   		}
   		System.out.println("Enter Guest id"); // print statement
   		int Guestid=sc.nextInt();
   		System.out.println("Enter New Guest Name : ");   // Print statement
   		String newGuestName=sc.next();
   		System.out.println("Enter New Room Number : ");   // Print statement
   		int newRoomNumber=sc.nextInt();
   		System.out.println("Enter New Contact Number : ");   // Print statement
   		String newContactNumber=sc.next();
   		String sql="update reservations set guest_name='"+newGuestName+"',"+"room_number="+newRoomNumber+","+"contact_number='"+newContactNumber+"'"+"where reservation_id="+reservationId;
   		try(Statement st=con.createStatement())
   		{
   			int affectedRows=st.executeUpdate(sql);
   			if(affectedRows>0)
   			{
   				System.out.println("Reservation Updated Successfully!");   // Print statement
   			}
   			else
   			{
   				System.out.println("Reservation Updated Failed ");   // Print statement
   			}
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
   static void Customer_History()
   {
   	try
   	{
   		String sql="select * from customer_history";
   		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbooking","root","ravali");
           	Statement st=con.createStatement();
           	ResultSet rs=st.executeQuery(sql);
           	System.out.println("List of Customer History");
           	System.out.println("--------------------------------------------------------------*");
   			System.out.println("| customer id | roomnumber   | checkInDate  |  checkOutDate       | ");
   			System.out.println("--------------------------------------------------------------*");
           	while(rs.next())
           	{    
           		int customerid=rs.getInt("customerid");
           		int roomnumber=rs.getInt("roomnumber");
           		Date checkInDate=rs.getDate("checkInDate");
           		Date checkOutDate=rs.getDate("checkOutDate");
           		System.out.printf("| %-11d | %-13d | %-11s | %-18s |\n ",customerid,roomnumber,checkInDate,checkOutDate);
           	}
           		System.out.println("-----------------------------------------------------*");           	
   	}
   	catch(SQLException e)
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
//   static void customer_application()
//{	
//	int cid;
//	String cname;
//	String cemail;
//	String cmobile;
//	String status;
//	Scanner din=new Scanner(System.in);
//	try
//	{
//		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbooking","root","1234");
//		String sql="select *  from customer_application";
//		System.out.println("List of Customer Application");
//		Statement st=con.createStatement();
//		ResultSet rs=st.executeQuery(sql);
//		System.out.println("Cid\tCname\tCemail\tCmobile\tStatus");
//		while(rs.next())
//		{
//			System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5)+"\t");
//		System.out.println("Hello Admin... Please Select Any Customer to Approve");
//			System.out.println("Enter Customer Id");
//			cid=din.nextInt();// for example 2
//			String sql1="select * from customer_application where cid='"+cid+"'";
//			Statement st1=con.createStatement();
//			ResultSet rs1=st1.executeQuery(sql1);
//	}	
//	}
//    	catch(SQLException e)    // catch is a method,during at run time if we are any issue it will display the error
//	{
//		e.printStackTrace();
//	}
 //}
