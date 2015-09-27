package hu.akoel.template.ejb.exceptions;

public class EJBeanException extends Exception{

	private static final long serialVersionUID = 9088893298756661312L;

	public EJBeanException( String message ){
		super(message);
	}
}
