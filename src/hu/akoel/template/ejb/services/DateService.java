package hu.akoel.template.ejb.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateService {

	private static SimpleDateFormat defaultDateformatter = new SimpleDateFormat ("yyyy-MM-dd");
	private static SimpleDateFormat dateformatter = new SimpleDateFormat ("yyyy-MM-dd");

	public static String getTimeByFormatter( Date date ){
		return dateformatter.format( date );
	}

	//TODO get value from cookie
	public static String getTimeByFormatter(){
		return getTimeByFormatter( getTime() );
	}

	public static String getTimeByDefaultFormatter( Date date ){
		return defaultDateformatter.format( date );
	}

	public static String getTimeByDefaultFormatter(){
		return getTimeByDefaultFormatter( getTime() );
	}
	
	private static Date getTime(){
		return Calendar.getInstance().getTime();
	}
	
	
}
