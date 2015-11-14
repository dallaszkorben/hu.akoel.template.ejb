package hu.akoel.template.ejb.test.exception;

public class TestCompareJSonToResultNotEqualException extends TestException{

	private static final long serialVersionUID = 4235544691814711019L;

	public TestCompareJSonToResultNotEqualException( String key, Object searchValue, Object foundValue  ){
		super( "The <" + searchValue  + "> value was expected of the \"" + key + "\" attribute but <" + (null == foundValue ? "null" :foundValue) + "> was found");
	}
}
