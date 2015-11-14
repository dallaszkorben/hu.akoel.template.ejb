package hu.akoel.template.ejb.test.exception;

public class TestNoExceptionException extends TestException{

	private static final long serialVersionUID = 4855544690814711019L;

	public TestNoExceptionException( String message ){
		super( "There was no exception but there was an expected: " + message);
	}
}
