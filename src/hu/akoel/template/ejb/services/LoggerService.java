package hu.akoel.template.ejb.services;

import hu.akoel.template.ejb.logger.LogFileHandler;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoggerService {

	private static Logger logger = null;

	public static String getJsonStringFromJavaObject(Object object) {
		String requestBean = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			requestBean = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		return requestBean;
	}
	
	public static void finest( String message ){
		getInstance().finest( message );
	}
	
	public static void finer( String message ){
		getInstance().finer( message );
	}
	
	public static void fine( String message ){
		getInstance().fine( message );
	}
	
	public static void config( String message ){
		getInstance().config( message );
	}
	
	public static void info( String message ){
		getInstance().info( message );
	}
	
	public static void warning( String message ){
		getInstance().warning( message );
	}
	
	public static void severe( String message ){
		getInstance().severe( message );
	}	
	
	private static Logger getInstance() {

		if (null == logger) {
			//logger = Logger.getLogger("hu.akoel.enta");
			logger = Logger.getLogger( "blabla" );

			try {
				logger.addHandler(new LogFileHandler( "../logs/hu.akoel.enta.log" ) );
			} catch (SecurityException | IOException e1) {
				// TODO lehet, hogy semmit nem kell tennem vele
				e1.printStackTrace();
			}

			logger.setLevel(Level.ALL);
	
		}
		return logger;
	}
}
