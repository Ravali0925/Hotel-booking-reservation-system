package com.hb.customException;

public class HotelReservationException extends Exception  {
	
	public HotelReservationException(String msg)
	{
		super(msg);
		System.out.println("Try again with your credintials");
	
	}
}
