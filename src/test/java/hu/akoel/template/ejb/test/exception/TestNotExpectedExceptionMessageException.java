package hu.akoel.template.ejb.test.exception;

public class TestNotExpectedExceptionMessageException extends TestException{

	private static final long serialVersionUID = 4855544690814711019L;

	public TestNotExpectedExceptionMessageException( String expectedMessage, String receivedMessage ){
		super( "Not expected exception message occured. \nExpectedMessage: " + expectedMessage + "\nReceivedMessage: " + receivedMessage );

	}
}
