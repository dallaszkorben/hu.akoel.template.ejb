package hu.akoel.template.ejb.exceptions;

public class EJBGeneralException extends EJBeanException{

	private static final long serialVersionUID = 292846098327690759L;

	public EJBGeneralException( Exception e ){
		super( "An error occur: " + e.getMessage() );
	}
}
