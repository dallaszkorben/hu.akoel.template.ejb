package hu.akoel.template.ejb.session.stateless;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import hu.akoel.template.ejb.entities.Role;
import hu.akoel.template.ejb.entities.User;
import hu.akoel.template.ejb.enums.FeatureRight;
import hu.akoel.template.ejb.exceptions.EJBeanException;
import hu.akoel.template.ejb.exceptions.EJBPersistenceException;
import hu.akoel.template.ejb.services.DateService;
import hu.akoel.template.ejb.services.FeatureRightService;
import hu.akoel.template.ejb.services.JsonService;
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
	public Role doCapture(String name, Integer capturedById) throws EJBeanException {

		//If exception occurs, it going to be thrown
		User capturedBy = FeatureRightService.getAuthorized(em, capturedById, FeatureRight.ROLE_CAPTURE);
		
		Role role = new Role();
		role.setName(name);
		
		role.setCapturedAt(DateService.getCalendar());
		role.setCapturedBy(capturedBy);
		
		LoggerService.finest( "Role Capture start to persist: " + role.toString()  );
		
		//Try to capture
		try{
			em.persist( role );
		}catch(Exception e){
			EJBPersistenceException persistenceException = new EJBPersistenceException(role, e);
			LoggerService.severe( persistenceException.getLocalizedMessage());
			e.printStackTrace();
			throw persistenceException;
		}
		
		LoggerService.info( "Role Capture has persisted: " + role.toString()  );
		
		return role;
	}
	
	/**
	 * Update a Role
	 * Condition ROLE_UPDATE
	 */
	@Override
	public Role doUpdate(Integer roleId, String name, Integer updateById) throws EJBeanException {

		//If exception occurs, it going to be thrown
		User capturedBy = FeatureRightService.getAuthorized(em, updateById, FeatureRight.ROLE_UPDATE);
		
		Role roleToUpdate;
		roleToUpdate = em.find( Role.class, roleId );
	
		LoggerService.finest( "Role Update start to persist: " + roleToUpdate.toString()  );
		
		//History
		Role roleToHistory = new Role();
		roleToHistory.setName( roleToUpdate.getName() );		
		roleToHistory.setCapturedAt(roleToUpdate.getCapturedAt());
		roleToHistory.setCapturedBy(roleToUpdate.getCapturedBy());		
		roleToHistory.setOriginal( roleToUpdate );
		
		//Updated
		roleToUpdate.setName( name );
		roleToUpdate.setCapturedAt(DateService.getCalendar());
		roleToUpdate.setCapturedBy(capturedBy);
		
		//Try to capture
		try{
			em.persist( roleToHistory );
			em.persist( roleToUpdate );
		}catch(Exception e){
			EJBPersistenceException persistenceException = new EJBPersistenceException(roleToUpdate, e);
			LoggerService.severe( persistenceException.getLocalizedMessage());
			e.printStackTrace();
			throw persistenceException;
		}		
		
		LoggerService.info( "Role Update has persisted: " + roleToUpdate.toString()  );
		
		return roleToUpdate;
	}
	
	@Override
	public List<Role> getHistory( Integer roleId, Integer userId )throws EJBeanException {

	
		//If exception occurs, it going to be thrown
		FeatureRightService.getAuthorized(em, userId, FeatureRight.ROLE_READ);
		
		Role role;
		role = em.find( Role.class, roleId );
		
		Query q = em.createQuery("SELECT r FROM Role r WHERE r.original= :role ORDER by r.id");			
		q.setParameter("role", role);
			
		@SuppressWarnings("unchecked")
		List<Role> roleList = q.getResultList();
		
		LoggerService.info( "Role history: " + JsonService.getJsonStringFromJavaObject(roleList));
		
		return roleList;

	}
}
