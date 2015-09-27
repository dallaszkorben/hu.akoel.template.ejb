package hu.akoel.template.ejb.services;

import hu.akoel.template.ejb.logger.LogFileHandler;
import hu.akoel.template.ejb.logger.LogHtmlHandler;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerService {

	private static Logger logger = null;

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
				logger.addHandler(new LogFileHandler());
			} catch (SecurityException | IOException e1) {
				// TODO lehet, hogy semmit nem kell tennem vele
				e1.printStackTrace();
			}
			// try {
			// logger.addHandler(new LogHtmlHandler());
			// } catch (SecurityException | IOException e) {
			// // TODO lehet, hogy semmit nem kell tennem vele
			// e.printStackTrace();
			// }

			logger.setLevel(Level.ALL);
	
		}
		return logger;
	}
}
