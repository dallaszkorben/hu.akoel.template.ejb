package hu.akoel.template.ejb.exceptions;

public class EJBNoResultFindByIdException extends EJBeanException{

	private static final long serialVersionUID = 292846098327690759L;

	public EJBNoResultFindByIdException( Class<?> clazz, Integer id ){
		super( "No found element by Id: em.find(" + clazz.getCanonicalName() + ".class, " + id + ") has no result." );
	}
}
