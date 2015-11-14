package hu.akoel.template.ejb.logger;

import java.io.IOException;
import java.util.logging.FileHandler;

public class LogHtmlHandler extends FileHandler {

	public LogHtmlHandler() throws IOException, SecurityException {
		//glassfish/domains/domains1/
		super( "../logs/hu.akoel.enta.html" );
		this.setFormatter( new LogHtmlFormatter() );
	}
}
