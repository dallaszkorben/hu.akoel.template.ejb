package hu.akoel.template.ejb.entities;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="jsonId")
@MappedSuperclass
public abstract class HistoryMappedSuperclass<T> implements Serializable, EntityObject{
	
	private static final long serialVersionUID = -7475211103263566166L;
	
	private Calendar capturedAt;
	private User capturedBy;
	private T original;
	
	@JoinColumn(name="original_id", nullable=true)
	public T getOriginal(){
		return original;
	}
	public void setOriginal( T original ){
		this.original = original;
	}
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="capturedat", nullable=false)
	public Calendar getCapturedAt(){
		return capturedAt;
	}
	public void setCapturedAt(Calendar capturedAt){
		this.capturedAt = capturedAt;
	}
	
	@ManyToOne(optional=true)
	@JoinColumn(name="capturedby_id", nullable=true)
	//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
	public User getCapturedBy(){
		return capturedBy;
	}
	public void setCapturedBy(User capturedBy){
		this.capturedBy = capturedBy;
	}
	
	public abstract boolean equals( final Object otherObject );
	
	public abstract String toString();
}
