package hu.akoel.template.ejb.exceptions;

import hu.akoel.template.ejb.enums.FeatureRight;

public class EJBNotFeatureRightException extends EJBeanException{

	private static final long serialVersionUID = 292846098377690759L;

	public EJBNotFeatureRightException( Integer userId, FeatureRight featureRight ){
		super( "The User id=" + userId + " has no '" +  featureRight.getLocalized() + "' Feature Right.");
	}
}
