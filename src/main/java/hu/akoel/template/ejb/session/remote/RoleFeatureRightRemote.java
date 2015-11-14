package hu.akoel.template.ejb.session.remote;

import hu.akoel.template.ejb.entities.RoleFeatureRight;
import hu.akoel.template.ejb.enums.FeatureRight;
import hu.akoel.template.ejb.exceptions.EJBeanException;

import javax.ejb.Remote;

@Remote
public interface RoleFeatureRightRemote {
	
	public RoleFeatureRight doCapture(Integer roleId, FeatureRight featureRight, Integer captureUserId ) throws EJBeanException;

}
