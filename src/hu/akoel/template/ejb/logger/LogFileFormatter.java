package hu.akoel.template.ejb.logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class LogFileFormatter extends Formatter {

	public String format(LogRecord rec) {

		StringBuffer buf = new StringBuffer(1000);

		buf.append(rec.getLevel());

		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy.MM.dd-hh:mm:ss");
		buf.append(" " + dateFormatter.format(Calendar.getInstance().getTime()));

		buf.append(" " + formatMessage(rec));

		//Because not the actual method calls the log but the log calls the log
		StackTraceElement myCaller = Thread.currentThread().getStackTrace()[9];
		
		buf.append( " (" + myCaller.getClassName() );
		//buf.append( " " + rec.getSourceClassName() );
		
		buf.append( " " + myCaller.getMethodName() + ")");
		//buf.append( " " + rec.getSourceMethodName() );
		
		buf.append( "\n" );

		return buf.toString();
	}

	public String getHead(Handler h) {
		return "\nThe server restarted\nvvvvvvvvvvvvvvvvvvvv\n";
	}

	public String getTail(Handler h) {
		return "^^^^^^^^^^^^^^^^^^^^\nThe server stopped\n\n";
	}

}

