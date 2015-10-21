package hu.akoel.template.ejb.entities;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class HistoryMappedSuperclass<T> implements Serializable, EntityObject{
	
	private static final long serialVersionUID = -7475211103263566166L;
	
	private Calendar operationAt;
	private User operationBy;
	private T original;
	private Boolean active;
	
	@JoinColumn(name="original_id", nullable=true)
	public T getOriginal(){
		return original;
	}
	public void setOriginal( T original ){
		this.original = original;
	}
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="operationat", nullable=false)
	public Calendar getOperationAt(){
		return operationAt;
	}
	public void setOperationAt(Calendar operationAt){
		this.operationAt = operationAt;
	}
	
	@ManyToOne(optional=true)
	@JoinColumn(name="operationby_id", nullable=true)
	//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
	public User getOperationBy(){
		return operationBy;
	}
	public void setOperationBy(User operationBy){
		this.operationBy = operationBy;
	}
	
	@Column(name="active", nullable=true)
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	public abstract boolean equals( final Object otherObject );
	
	public abstract String toString();

}
