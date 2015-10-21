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
import hu.akoel.template.ejb.exceptions.EJBDeletedException;
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

		LoggerService.finest( "Role Capture started: name: " + name  );

		//If exception occurs, it going to be thrown
		User capturedBy = FeatureRightService.getAuthorized(em, capturedById, FeatureRight.ROLE_CAPTURE);
		
		Role role = new Role();
		role.setName(name);
		
		role.setOperationAt(DateService.getCalendar());
		role.setOperationBy(capturedBy);
		role.setActive( true );
		
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

		LoggerService.finest( "Role Update started: id:" + roleId + " name: " + name  );
		
		//If exception occurs, it going to be thrown
		User capturedBy = FeatureRightService.getAuthorized(em, updateById, FeatureRight.ROLE_UPDATE);
		
		Role roleToUpdate;
		roleToUpdate = em.find( Role.class, roleId );
		
		//If the Role is not active
		if( !roleToUpdate.getActive() ){
			EJBDeletedException e = new EJBDeletedException( Role.class, roleId, roleToUpdate.getName() );			
			LoggerService.severe( e.getLocalizedMessage() );
			return null;
		}
		
		//History
		Role roleToHistory = new Role();
		roleToHistory.setName( roleToUpdate.getName() );		
		roleToHistory.setOperationAt(roleToUpdate.getOperationAt());
		roleToHistory.setOperationBy(roleToUpdate.getOperationBy());		
		roleToHistory.setOriginal( roleToUpdate );
		
		//Updated
		roleToUpdate.setName( name );
		roleToUpdate.setOperationAt(DateService.getCalendar());
		roleToUpdate.setOperationBy(capturedBy);
		
		LoggerService.finest( "Role Update start to persist: " + roleToUpdate.toString()  );
		
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
	public Role doDelete(Integer roleId, Integer deleteById) throws EJBeanException {

		LoggerService.finest( "Role Delete started: id:" + roleId );
		
		//If exception occurs, it going to be thrown
		User capturedBy = FeatureRightService.getAuthorized(em, deleteById, FeatureRight.ROLE_DELETE);
		
		Role roleToDelete;
		roleToDelete = em.find( Role.class, roleId );
		
		//If the Role is not active
		if( !roleToDelete.getActive() ){
			EJBDeletedException e = new EJBDeletedException( Role.class, roleId, roleToDelete.getName() );			
			LoggerService.severe( e.getLocalizedMessage() );
			return null;
		}
		
		//History
		Role roleToHistory = new Role();
		roleToHistory.setName( roleToDelete.getName() );		
		roleToHistory.setOperationAt(roleToDelete.getOperationAt());
		roleToHistory.setOperationBy(roleToDelete.getOperationBy());		
		roleToHistory.setOriginal( roleToDelete );
		
		//Deleted
		roleToDelete.setOperationAt(DateService.getCalendar());
		roleToDelete.setOperationBy(capturedBy);
		roleToDelete.setActive( false );
		
		LoggerService.finest( "Role Delete start to persist: " + roleToDelete.toString()  );
		
		//Try to capture
		try{
			em.persist( roleToHistory );
			em.persist( roleToDelete );
		}catch(Exception e){
			EJBPersistenceException persistenceException = new EJBPersistenceException(roleToDelete, e);
			LoggerService.severe( persistenceException.getLocalizedMessage());
			e.printStackTrace();
			throw persistenceException;
		}		
		
		LoggerService.info( "Role Delete has persisted: " + roleToDelete.toString()  );
		
		return roleToDelete;
	}
	
	@Override
	public List<Role> getHistory( Integer roleId, Integer userId )throws EJBeanException {

		LoggerService.finest( "Role History started: id:" + roleId );

		//If exception occurs, it going to be thrown
		FeatureRightService.getAuthorized(em, userId, FeatureRight.ROLE_READ);
		
		Role role;
		role = em.find( Role.class, roleId );
		
		//If the Role is already deleted
		if( !role.getActive() ){
			EJBDeletedException e = new EJBDeletedException( Role.class, roleId, role.getName() );			
			LoggerService.severe( e.getLocalizedMessage() );
			return null;
		}
		
		Query q = em.createQuery("SELECT r FROM Role r WHERE r.id = :roleId OR r.original= :role ORDER BY r.operationAt DESC");			
		q.setParameter("roleId", role.getId());
		q.setParameter("role", role);
			
		@SuppressWarnings("unchecked")
		List<Role> roleList = q.getResultList();
		
		LoggerService.info( "Role history: " + JsonService.getJsonStringFromJavaObject(roleList));
		
		return roleList;

	}

}
