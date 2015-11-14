package hu.akoel.template.ejb.session.remote;

import hu.akoel.template.ejb.entities.User;
import hu.akoel.template.ejb.exceptions.EJBeanException;

import javax.ejb.Remote;

@Remote
public interface UserRemote {

	public User doCapture(Integer roleId, String name, String password,
			String firstName, String surname, String email, Integer capturedById) throws EJBeanException;

}
