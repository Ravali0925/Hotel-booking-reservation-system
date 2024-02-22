package com.hb.admin;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import com.hb.adminlogin.AdminLogin;
import com.hb.colors.ConsoleColors;
import com.hb.customerlogin.CustomerLogin;
import com.hb.dbconnect.Dbconnect;
public class Admin {
	public static void main(String[] args) throws SQLException {
		int choice;
		boolean connection = Dbconnect.getConnection();
		if(connection==true)

		{	
		System.out.println(ConsoleColors.PURPLE_BOLD+"****WELCOME TO TAJ BHANJARA HOTEL****"+ConsoleColors.RESET);
		Scanner din = new Scanner(System.in);
		System.out.println(ConsoleColors.ROSY_PINK+"1.Admin Login ");
		System.out.println("2.Customer Login"+ConsoleColors.RESET);
		System.out.println(ConsoleColors.ORANGE+"Please Enter Your choice"+ConsoleColors.RESET);
		choice=din.nextInt();
		switch(choice)
		{
		case 1: System.out.println(ConsoleColors.BROWN+"Admin Here"+ConsoleColors.RESET);
				AdminLogin al = new AdminLogin();
				al.AdminLogin();
		break;
		case 2: System.out.println(ConsoleColors.BROWN+"Customer Login or Register Here"+ConsoleColors.RESET);
				CustomerLogin cl = new CustomerLogin();
				cl.customerLog();
		break;
		}
		}
}
}
