package hu.akoel.template.ejb.test.exception;

public class TestDBCompareException extends TestException{

	private static final long serialVersionUID = 4855544690814711019L;

	public TestDBCompareException( String message ){
		super( "The DB comparation has failed: " + message);
	}
}
