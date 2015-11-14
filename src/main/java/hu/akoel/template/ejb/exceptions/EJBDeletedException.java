package hu.akoel.template.ejb.exceptions;

public class EJBDeletedException extends EJBeanException{

	private static final long serialVersionUID = 542846098371690759L;
	
	private Integer id;
	private String name;
	
	public EJBDeletedException( Class<?> clazz, Integer id, String name ){
		super( "The '" + clazz.getSimpleName() + "' element with name: " + name + " id: " + id + " is already deleted." );
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}	
}
