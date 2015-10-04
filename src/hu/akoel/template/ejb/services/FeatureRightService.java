package hu.akoel.template.ejb.services;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import hu.akoel.template.ejb.entities.Role;
import hu.akoel.template.ejb.entities.User;
import hu.akoel.template.ejb.enums.FeatureRight;
import hu.akoel.template.ejb.exceptions.EJBNotFeatureRightException;
import hu.akoel.template.ejb.exceptions.EJBGeneralException;
import hu.akoel.template.ejb.exceptions.EJBeanException;
import hu.akoel.template.ejb.exceptions.EJBNoResultFindByIdException;

public class FeatureRightService {

	public static User getAuthorized( EntityManager em, int userId, FeatureRight necessaryFeatureRight ) throws EJBeanException{
		
		User user = null;
		try{
			user = em.find( User.class, userId );
		}catch (Exception e){
			e.printStackTrace();
			EJBGeneralException generalException = new EJBGeneralException(e);						
			LoggerService.severe(generalException.getLocalizedMessage());
			throw generalException;
		}
		if( null == user ){
			EJBNoResultFindByIdException e = new EJBNoResultFindByIdException( User.class, userId );			
			LoggerService.severe( e.getLocalizedMessage() );
			return null;
		}
		
		Role role = user.getRole();
		try{
			//TODO handle the deleted
			Query q = em.createQuery("SELECT rfr FROM RoleFeatureRight rfr WHERE rfr.role= :role AND rfr.featureRight= :featureRight");			
			q.setParameter("role", role);
			q.setParameter("featureRight", necessaryFeatureRight);
			
			//We do not care about the specific result
			q.getSingleResult();
		
		//No Feature Right
		}catch(NoResultException nre ){
			nre.printStackTrace();
			EJBNotFeatureRightException fre = new EJBNotFeatureRightException(userId, necessaryFeatureRight );
			throw fre;	
			
		//Other problem
		}catch(Exception e){
			e.printStackTrace();
			EJBGeneralException queryException = new EJBGeneralException(e);						
			LoggerService.severe(queryException.getLocalizedMessage());
			throw queryException;
		}
		
		return user;
	}
}
