package hu.akoel.template.ejb.session.stateless;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import hu.akoel.template.ejb.entities.Role;
import hu.akoel.template.ejb.entities.RoleFeatureRight;
import hu.akoel.template.ejb.entities.User;
import hu.akoel.template.ejb.enums.FeatureRight;
import hu.akoel.template.ejb.exceptions.EJBeanException;
import hu.akoel.template.ejb.exceptions.EJBNoResultFindByIdException;
import hu.akoel.template.ejb.exceptions.EJBPersistenceException;
import hu.akoel.template.ejb.services.DateService;
import hu.akoel.template.ejb.services.FeatureRightService;
import hu.akoel.template.ejb.services.LoggerService;
import hu.akoel.template.ejb.session.remote.RoleFeatureRightRemote;

@Stateless(name="RoleFeatureRightSession", mappedName="session/RoleFeatureRightSession")
@LocalBean
public class RoleFeatureRightSession implements RoleFeatureRightRemote{

	@PersistenceContext(unitName="dev")
	private EntityManager em;
	
	@Resource
	private SessionContext ctx;
	
	/**
	 * Captures a new FeatureRight for the specific Role
	 * Condition: FeatureRight.FEATURERIGHT_CAPTURE
	 */
	@Override
	public RoleFeatureRight doCapture(Integer roleId, FeatureRight featureRight, Integer capturedById) throws EJBeanException {

		//If exception occurs, it going to be thrown
		User capturedBy = FeatureRightService.getAuthorized(em, capturedById, FeatureRight.FEATURERIGHT_CAPTURE);
		
		Role role = em.find( Role.class, roleId);
		if( null == role ){
			EJBNoResultFindByIdException e = new EJBNoResultFindByIdException(Role.class, roleId);
			throw e;
		}
		
		RoleFeatureRight roleFeatureRight = new RoleFeatureRight();

		roleFeatureRight.setRole(role);
		roleFeatureRight.setFeatureRight(featureRight);
		
		roleFeatureRight.setOperationAt(DateService.getInstance().getCalendar());
		roleFeatureRight.setOperationBy(capturedBy);
		
		//Try to capture
		try{
			em.persist( roleFeatureRight );
		}catch(Exception e){
			EJBPersistenceException persistenceException = new EJBPersistenceException(roleFeatureRight, e);
			LoggerService.severe( persistenceException.getLocalizedMessage());
			e.printStackTrace();
			throw persistenceException;
		}
		
		return roleFeatureRight;
	}
}
