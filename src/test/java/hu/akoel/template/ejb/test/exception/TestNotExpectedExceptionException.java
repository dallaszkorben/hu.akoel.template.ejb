package hu.akoel.template.ejb.test.exception;

public class TestNotExpectedExceptionException extends TestException{

	private static final long serialVersionUID = 4855914690814711019L;

	public TestNotExpectedExceptionException( String expectedException, String receivedException ){
		super( "Not expected exception occured. \nExpected exception: " + expectedException + "\nReceived exception: " + receivedException );
	}
}
