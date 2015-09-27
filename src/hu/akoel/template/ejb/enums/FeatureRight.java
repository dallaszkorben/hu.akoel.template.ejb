package hu.akoel.template.ejb.enums;

import hu.akoel.template.ejb.services.LocalizeService;

public enum FeatureRight {
	ROLE_CAPTURE("fr_role_capture"),
	FEATURERIGHT_CAPTURE("fr_featureright_capture"),
	;	
	String code;
	
	private FeatureRight( String code ){
		this.code = code;
	}
	
	public String getLocalized(){
		return LocalizeService.getLocalized(code);
	}
}
