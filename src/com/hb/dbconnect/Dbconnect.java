package com.hb.dbconnect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Dbconnect {

public static boolean getConnection() throws SQLException     // static method
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbooking", "root", "ravali");
			
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return true;
	}
}
