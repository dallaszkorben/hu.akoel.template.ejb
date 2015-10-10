package hu.akoel.template.ejb.test;

import java.util.ArrayList;

public class ExpectedExceptionObject {

	Class<?> expectedClass;
	ArrayList<String> partialMessage = new ArrayList<>();
	private String exactMessage = null;
	
	public ExpectedExceptionObject( Class<?> expectedClass ){
		this.expectedClass = expectedClass;
	}
	
	public ExpectedExceptionObject setExactMessage( String exactMessage ){
		this.exactMessage = exactMessage;
		return this;
	}
	
	public ExpectedExceptionObject addPartialMessage( String partialMessage ){
		this.exactMessage = null;
		this.partialMessage.add( partialMessage );
		return this;
	}

	public ArrayList<String> getPartialMessage() {
		return partialMessage;
	}

	public String getExactMessage() {
		return exactMessage;
	}

	public Class<?> getExpectedClass() {
		return expectedClass;
	}
	
	
	
}
