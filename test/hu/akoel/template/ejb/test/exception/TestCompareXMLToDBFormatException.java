package hu.akoel.template.ejb.test.exception;

public class TestCompareXMLToDBFormatException extends TestException{

	private static final long serialVersionUID = 4855544690814711019L;

	public TestCompareXMLToDBFormatException( String message ){
		super( "CompareSet XML file problem: " + message);
	}
}
