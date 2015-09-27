package hu.akoel.template.ejb.test.exception;

public class TestDBCompareXMLFormatException extends TestException{

	private static final long serialVersionUID = 4855544690814711019L;

	public TestDBCompareXMLFormatException( String message ){
		super( "CompareSet XML file problem: " + message);
	}
}
