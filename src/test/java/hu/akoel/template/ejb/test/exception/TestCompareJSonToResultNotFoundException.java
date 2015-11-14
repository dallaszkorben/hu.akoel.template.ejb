package hu.akoel.template.ejb.test.exception;

public class TestCompareJSonToResultNotFoundException extends TestException{

	private static final long serialVersionUID = 4235544691814711019L;

	public TestCompareJSonToResultNotFoundException( String pathToSearch  ){
		super( pathToSearch + " was not found.");
	}
}
