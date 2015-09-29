package hu.akoel.template.ejb.session.stateless;

import java.util.Calendar;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import hu.akoel.template.ejb.entities.Role;
import hu.akoel.template.ejb.entities.User;
import hu.akoel.template.ejb.enums.FeatureRight;
import hu.akoel.template.ejb.enums.Status;
import hu.akoel.template.ejb.exceptions.EJBeanException;
import hu.akoel.template.ejb.exceptions.EJBPersistenceException;
import hu.akoel.template.ejb.services.DateService;
import hu.akoel.template.ejb.services.FeatureRightService;
import hu.akoel.template.ejb.services.LoggerService;
import hu.akoel.template.ejb.session.remote.RoleRemote;

@Stateless(name="RoleSession", mappedName="session/RoleSession")
@LocalBean
public class RoleSession implements RoleRemote{

	@PersistenceContext(unitName="dev")
	private EntityManager em;
	
	@Resource
	private SessionContext ctx;
	
	/**
	 * Captures a new Role
	 * Condition: FeatureRight.ROLE_CAPTURE 
	 */
	@Override
	public Role doCapture(String name, Integer captureUserId) throws EJBeanException {

		User statusBy;		
		
		FeatureRight necessaryFeatureRight = FeatureRight.ROLE_CAPTURE;
		
		//If exception occurs it going to be thrown
		statusBy = FeatureRightService.getAuthorized(em, captureUserId, necessaryFeatureRight);
		
		Role role = new Role();
		role.setName(name);
		
		role.setStatusAt(DateService.getCalendar());
		role.setStatusBy(statusBy);
		role.setStatus(Status.CAPTURED);
		
		LoggerService.finest( "Role start to persist: " + role.toString()  );
		
		//Try to capture
		try{
			em.persist( role );
		}catch(Exception e){
			EJBPersistenceException persistenceException = new EJBPersistenceException(role, e);
			LoggerService.severe( persistenceException.getLocalizedMessage());
			e.printStackTrace();
			throw persistenceException;
		}
		
		return role;
	}
}
