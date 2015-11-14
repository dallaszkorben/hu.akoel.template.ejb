package hu.akoel.template.ejb.exceptions;

public class EJBPersistenceException extends EJBeanException{

	private static final long serialVersionUID = 292846098327690759L;

	public EJBPersistenceException( Object persistObject, Exception e ){
		super( "Persistence failed:\nem.persistence(" + persistObject.getClass().getCanonicalName() + ") resulted with error.\n" + e.getMessage() );
	}
}
