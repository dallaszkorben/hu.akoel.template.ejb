package hu.akoel.template.ejb.test.exception;

public class TestNotExpectedException extends TestException{

	private static final long serialVersionUID = 4855544690814711019L;

	public TestNotExpectedException( String message ){
		super( "Not expected exception occured: " + message);
	}
}
