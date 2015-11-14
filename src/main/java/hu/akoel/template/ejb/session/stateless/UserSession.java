package hu.akoel.template.ejb.session.stateless;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import hu.akoel.template.ejb.entities.Role;
import hu.akoel.template.ejb.entities.User;
import hu.akoel.template.ejb.enums.FeatureRight;
import hu.akoel.template.ejb.exceptions.EJBPersistenceException;
import hu.akoel.template.ejb.exceptions.EJBeanException;
import hu.akoel.template.ejb.exceptions.EJBNoResultFindByIdException;
import hu.akoel.template.ejb.services.DateService;
import hu.akoel.template.ejb.services.FeatureRightService;
import hu.akoel.template.ejb.services.LoggerService;
import hu.akoel.template.ejb.session.remote.UserRemote;

@Stateless(name="UserSession", mappedName="session/UserSession")
@LocalBean
public class UserSession implements UserRemote{

	@PersistenceContext(unitName="dev")
	private EntityManager em;

	@Resource
	private SessionContext ctx;
	
	/**
	 * Capture a User
	 * Condition: USER_CAPTURE
	 */
	@Override
	public User doCapture(Integer roleId, String name, String password,
			String firstName, String surname, String email, Integer capturedById) throws EJBeanException{

		//If exception occurs, it going to be thrown
		User capturedBy = FeatureRightService.getAuthorized(em, capturedById, FeatureRight.USER_CAPTURE);
		
		//Find the Role
		Role role = em.find( Role.class, roleId);
		if( null == role ){
			EJBNoResultFindByIdException e = new EJBNoResultFindByIdException(Role.class, roleId);
			throw e;
		}
		
		User user = new User();
		user.setRole(role);
		user.setName(name);
		user.setPassword(password);
		user.setFirstname(firstName);
		user.setSurname(surname);
		user.setEmail(email);
		user.setOperationAt(DateService.getInstance().getCalendar());
		user.setOperationBy(capturedBy);

		LoggerService.finest( "User Capture start to persist: " + user.toString()  );
		
		//Try to capture
		try{
			em.persist( user );
		}catch(Exception e){
			EJBPersistenceException persistenceException = new EJBPersistenceException(user, e);
			LoggerService.severe( persistenceException.getLocalizedMessage());
			e.printStackTrace();
			throw persistenceException;
		}
		
		LoggerService.info( "User Capture has persisted: " + user.toString()  );
		
		return user;
	}
}
