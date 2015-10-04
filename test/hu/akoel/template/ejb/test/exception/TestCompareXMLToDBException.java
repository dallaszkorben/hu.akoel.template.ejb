package hu.akoel.template.ejb.test.exception;

public class TestCompareXMLToDBException extends TestException{

	private static final long serialVersionUID = 4855544690814711019L;

	public TestCompareXMLToDBException( String dbSetName, String message ){
		super( "The DB comparation to " + dbSetName + " has failed: " + message);
	}
}
