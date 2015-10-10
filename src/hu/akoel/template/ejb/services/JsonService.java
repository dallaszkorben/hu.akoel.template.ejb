package hu.akoel.template.ejb.services;

/*import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
*/
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonService {

	public static String getJsonStringFromJavaObject( Object object ){
		String requestBean = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			requestBean = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		return requestBean; 
	}
	
/*	public static JSONObject getJsonObjectFromJavaObject( Object object){
		try {
			return new JSONObject( getJsonStringFromJavaObject(object) );
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}		
	}
	
	public static JSONArray getJsonArrayFromJavaArray( Object object){
		try {
			return new JSONArray( getJsonStringFromJavaObject(object) );
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}		
	}
*/	
}
