package hu.akoel.template.ejb.session.remote;

import hu.akoel.template.ejb.entities.Role;
import hu.akoel.template.ejb.exceptions.EJBeanException;

import javax.ejb.Remote;

@Remote
public interface RoleRemote {
	
	public Role doCapture(String name, Integer captureUserId) throws EJBeanException;

}
