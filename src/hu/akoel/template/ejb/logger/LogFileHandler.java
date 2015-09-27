package hu.akoel.template.ejb.logger;

import java.io.IOException;
import java.util.logging.FileHandler;

public class LogFileHandler extends FileHandler{

	public LogFileHandler() throws IOException, SecurityException {
		//glassfish/domains/domains1/
		super( "../logs/hu.akoel.enta.log" );
		this.setFormatter( new LogFileFormatter() );		
	}

}