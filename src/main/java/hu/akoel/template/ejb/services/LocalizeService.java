package hu.akoel.template.ejb.services;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LocalizeService {

	private static Locale defaultLocale = new Locale("en", "US");
	
	public static String getLocalized( String code ){
		try{
			//TODO get defaultLocale value from cookie
			return ResourceBundle.getBundle("locale.Locale", defaultLocale ).getString( code );
		}catch( MissingResourceException e ){
			return code;
		}
	}
	
	public static String getDefaultLocalized( String code ){
		try{
			return ResourceBundle.getBundle("hu.akoel.template.ejb.locale", defaultLocale ).getString( code );
		}catch( MissingResourceException e ){
			return code;
		}
	}

}
