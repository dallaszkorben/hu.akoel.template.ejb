package hu.akoel.template.ejb.session.remote;

import java.util.List;

import hu.akoel.template.ejb.entities.Role;
import hu.akoel.template.ejb.exceptions.EJBeanException;

import javax.ejb.Remote;

@Remote
public interface RoleRemote {
	
	public Role doCapture(String name, Integer capturedById) throws EJBeanException;
	
	public Role doUpdate(Integer roleId, String name, Integer updateById) throws EJBeanException;

	public List<Role> getHistory( Integer roleId, Integer userId )throws EJBeanException ;
}
