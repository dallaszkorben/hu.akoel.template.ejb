package hu.akoel.template.ejb.logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class LogHtmlFormatter extends Formatter{
	public String format(LogRecord rec) {
		StringBuffer buf = new StringBuffer(1000);
		
		// Bold any levels >= WARNING
		buf.append("<tr>");
		
		buf.append("<td>");
		if (rec.getLevel().intValue() >= Level.WARNING.intValue()) {
			buf.append("<b>");
			buf.append(rec.getLevel());
			buf.append("</b>");
		} else {
			buf.append(rec.getLevel()+"TTTTT");
		}		
		buf.append("</td>");
				
		buf.append("<td nowrap>");
		buf.append(calcDate(rec.getMillis()));
		buf.append("</td>");

		buf.append("<td>");
		buf.append(rec.getSourceMethodName());
		buf.append("</td>");
		
		buf.append("<td>");
		buf.append(formatMessage(rec));
		buf.append("</td>");
		
		buf.append("<td>");
		buf.append(rec.getSourceClassName());
		buf.append("</td>");

		buf.append("</tr>\n");
		
		return buf.toString();
	}

	private String calcDate(long millisecs) {
		SimpleDateFormat date_format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
		Date resultdate = new Date(millisecs);
		return date_format.format(resultdate);
	}

	// This method is called just after the handler using this
	// formatter is created
	public String getHead(Handler h) {
		return "<HTML>\n<HEAD>\n" + (new Date()) + "\n</HEAD>\n<BODY>\n<PRE>\n"
				+ "<table width=\"100%\" border>\n  " + "<tr>" 
				+ "<th>Level</th>"
				+ "<th>Time</th>" 
				+ "<th>Log Message</th>" 
				+ "<th>Class</th>"
				+ "<th>Method</th>"
				+ "</tr>\n";
	}

	// This method is called just after the handler using this
	// formatter is closed
	public String getTail(Handler h) {
		return "</table>\n  </PRE></BODY>\n</HTML>\n";
	}
}