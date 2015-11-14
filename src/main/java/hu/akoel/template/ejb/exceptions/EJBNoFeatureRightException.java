package hu.akoel.template.ejb.exceptions;

import hu.akoel.template.ejb.enums.FeatureRight;

public class EJBNoFeatureRightException extends EJBeanException{

	private static final long serialVersionUID = 292846098377690759L;
	
	private Integer userId;
	private FeatureRight featureRight;
	
	public EJBNoFeatureRightException( Integer userId, FeatureRight featureRight ){
		super( "The User id=" + userId + " has no '" +  featureRight.getLocalized() + "' Feature Right.");
		this.userId = userId;
		this.featureRight = featureRight;
	}
	
	public Integer getUserId() {
		return userId;
	}

	public FeatureRight getFeatureRight() {
		return featureRight;
	}
}
