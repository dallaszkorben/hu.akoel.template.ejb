package hu.akoel.template.ejb.test;

import java.io.IOException;

import org.json.JSONException;

import hu.akoel.template.ejb.test.exception.TestException;

public class TestControllerChainParameterHelper {

	private TestController testController;
	
	private Object service;
	private String methodName;
	private Object[] parameterList;
	
	private String expectedJSONObjectFileName = null;
	private String expectedJSONArrayFileName = null;
	private String expectedXMLDBSet = null;
	private ExpectedExceptionObject expectedException = null;
	
	public TestControllerChainParameterHelper( TestController testController, Object service, String methodName, Object[] parameterList ){
		this.testController = testController;
		this.service = service;
		this.methodName = methodName;
		this.parameterList = parameterList;		
	}
	
	public TestControllerChainParameterHelper setExpectedException( ExpectedExceptionObject expectedException ){
		this.expectedException = expectedException;
		return this;
	}
	
	public TestControllerChainParameterHelper setExpectedJsonObject( String expectedJsonObject ){
		this.expectedJSONObjectFileName = expectedJsonObject;
		this.expectedJSONArrayFileName = null;
		return this;
	}

	public TestControllerChainParameterHelper setExpectedJsonArray( String expectedJsonArray ){
		this.expectedJSONArrayFileName = expectedJsonArray;
		this.expectedJSONObjectFileName = null;
		return this;
	}
	
	public TestControllerChainParameterHelper setExpectedXMLDBSet( String expectedXMLDBSet ){
		this.expectedXMLDBSet = expectedXMLDBSet;
		return this;
	}
	
	public <E> E doSession() throws TestException, IOException, JSONException{
		return testController.doSession(service, methodName, parameterList, expectedJSONObjectFileName, expectedJSONArrayFileName, expectedXMLDBSet, expectedException);
	}
}
