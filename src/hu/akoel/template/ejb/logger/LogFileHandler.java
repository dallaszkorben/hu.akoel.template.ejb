package hu.akoel.template.ejb.logger;

import java.io.IOException;
import java.util.logging.FileHandler;

public class LogFileHandler extends FileHandler{

	public LogFileHandler( String filePath ) throws IOException, SecurityException {
		//glassfish/domains/domains1/
		super( filePath );
		this.setFormatter( new LogFileFormatter() );		
	}

}