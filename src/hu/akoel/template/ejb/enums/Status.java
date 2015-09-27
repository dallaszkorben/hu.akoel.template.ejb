package hu.akoel.template.ejb.enums;

import hu.akoel.template.ejb.services.LocalizeService;

public enum Status {
	CAPTURED("status_captured"),
	MODIFIED("status_modified"),
	DELETED("status_deleted");
	
	String code;
	
	private Status( String code ){
		this.code = code;
	}
	
	public String getLocalized(){
		return LocalizeService.getLocalized(code);
	}
}
