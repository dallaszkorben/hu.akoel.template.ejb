package hu.akoel.template.ejb.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateService {

	private static SimpleDateFormat defaultDateformatter = new SimpleDateFormat ("yyyy-MM-dd");
	private static SimpleDateFormat dateformatter = new SimpleDateFormat ("yyyy-MM-dd");

	private static DateService instance = null;
	
	private DateService(){}
	
	public static DateService getInstance(){
		if( null == instance ){
			instance = new DateService();
		}
		return instance;
	}

	public String getTimeByFormatter( Date date ){
		return dateformatter.format( date );
	}

	//TODO get value from cookie
	public String getTimeByFormatter(){
		return getTimeByFormatter( getCalendar().getTime() );
	}

	public String getTimeByDefaultFormatter( Date date ){
		return defaultDateformatter.format( date );
	}

	public String getTimeByDefaultFormatter(){
		return getTimeByDefaultFormatter( getCalendar().getTime() );
	}

	public Calendar getCalendar(){
		return GregorianCalendar.getInstance();
	}
	
	
	
}
