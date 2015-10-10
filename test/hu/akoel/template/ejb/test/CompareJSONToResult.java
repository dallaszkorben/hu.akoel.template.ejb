package hu.akoel.template.ejb.test;

import hu.akoel.template.ejb.services.JsonService;
import hu.akoel.template.ejb.test.exception.TestCompareJSonToResultFormatException;
import hu.akoel.template.ejb.test.exception.TestCompareJSonToResultNotEqualException;
import hu.akoel.template.ejb.test.exception.TestCompareJSonToResultNotFoundException;
import hu.akoel.template.ejb.test.exception.TestException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

public class CompareJSONToResult {
	
	public static String doCompareAsJSONObject( String expectedJSonObjectFileName, Object actualResult) throws IOException, JSONException, TestException {
				
		String jsonArrayResult = JsonService.getJsonStringFromJavaObject( actualResult );
		
		String expectedJSonObjectString = new String(Files.readAllBytes(Paths.get(expectedJSonObjectFileName)));
		
		JSONObject expectedJSONObject = new JSONObject(expectedJSonObjectString);

		getKeyValue(expectedJSONObject, jsonArrayResult, "$" );
		
		//JSONObject jSonObject = JsonService.getJsonFromObject(expectedJSonResult);
		//Iterator<?> keys = expectedJSONObject.keys();
		//while( keys.hasNext() ){
		//	String key = (String)keys.next();		    
		//	if ( expectedJSONObject.get(key) instanceof JSONObject ) {
		//		System.err.println(key + ": " + expectedJSONObject.get(key) );					
		//	}			
		//}		
		return null;
	}
	
	public static String doCompareAsJSONArray( String expectedJSonArrayFileName, Object actualResult) throws IOException, JSONException, TestException {
		
		String jsonArrayResult = JsonService.getJsonStringFromJavaObject( actualResult );
		
		String expectedJSonArrayString = new String(Files.readAllBytes(Paths.get(expectedJSonArrayFileName)));
		
		JSONArray expectedJSonArray = new JSONArray( expectedJSonArrayString );
		
		getKeyValue( expectedJSonArray, jsonArrayResult, "$" );
						
		return null;
	}
	
	private static void getKeyValue( JSONArray jSonArrayExpected, String jsonArrayResult, String depth ) throws TestException, JSONException{
		
		//Go through the expected elements
		for( int i = 0; i < jSonArrayExpected.length(); i++ ){

			Object o = null;
			try {
				
				//Get the next expected element
				o = jSonArrayExpected.get(i);
			} catch (JSONException e) {
// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//If the next element is a JSON Object
			if( o instanceof JSONObject ){
				
				//Then invoke this method again
				getKeyValue( (JSONObject)o, jsonArrayResult, depth + ".[" + i + "]" );	
			
			//If the next element is a JSON Array
			}else if( o instanceof JSONArray ){
				
				//Then invoke the array method
				getKeyValue( (JSONArray)o, jsonArrayResult, depth );	
				
			//If the next element something else
			}else{

				throw new TestCompareJSonToResultFormatException( "Found this '" + o + "' element in JSONArray." );
			}		
		}		
	}
	
	private static void getKeyValue(JSONObject jSonObjectExpected, String jsonArrayResult, String depth ) throws TestException, JSONException{
		
		//Go through the JSON Object
		Iterator<?> keys = jSonObjectExpected.keys();
		while( keys.hasNext() ){
		
			//The next KEY in the JSON Object
			String key = (String)keys.next();
			Object searchValue = null;
			
			try {
				
				//The VALUE of the next JSON Object
				searchValue = jSonObjectExpected.get(key);
				
			} catch (JSONException e) {

				throw e;
			}
			
			if ( searchValue instanceof JSONObject ) {

				getKeyValue( (JSONObject)searchValue, jsonArrayResult, depth + "." + key );
				
			}else if( searchValue instanceof JSONArray ){
				
				getKeyValue( (JSONArray)searchValue, jsonArrayResult, depth + "." + key );
				
			}else{
				
				String pathToSearch = depth + "." + key;
				Object foundValue = null;
				try{
					
					//Problem: result is null: 1. not found 2. found but the value is null
					foundValue = JsonPath.read(jsonArrayResult, pathToSearch);
					
				//Path was not found
				//But somehow it does not work. 
				}catch( PathNotFoundException e ){
					
					throw new TestCompareJSonToResultNotFoundException(pathToSearch );
				}
				
				//Found the element but the values are different
				//Problem: It can not tell that the key was not found or it was but the values are different
				if( 
						( searchValue.equals(JSONObject.NULL ) && foundValue != null ) ||
						( !searchValue.equals(JSONObject.NULL) && foundValue == null ) || 
						( !searchValue.equals(JSONObject.NULL) && foundValue != null && !foundValue.equals(searchValue) ) ){
					
						throw new TestCompareJSonToResultNotEqualException( pathToSearch, searchValue, foundValue );
				
				}
			}		
		}		
	}
}
