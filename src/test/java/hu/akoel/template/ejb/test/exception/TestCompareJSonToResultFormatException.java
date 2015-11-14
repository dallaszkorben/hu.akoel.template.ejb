package hu.akoel.template.ejb.test.exception;

public class TestCompareJSonToResultFormatException extends TestException{

	private static final long serialVersionUID = 4855544690814711019L;

	public TestCompareJSonToResultFormatException( String message ){
		super( "Compare JSon file problem: " + message);
	}
}
