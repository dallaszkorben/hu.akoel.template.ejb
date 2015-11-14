package hu.akoel.template.ejb.jsonformatters;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JsonCalendarFormatter extends JsonSerializer<Calendar> {
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	 
	@Override
	public void serialize (Calendar value, JsonGenerator gen, SerializerProvider arg2) throws IOException, JsonProcessingException {
		gen.writeString(formatter.format(value.getTime()));
	}
}