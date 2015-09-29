package hu.akoel.template.ejb.entities;

import hu.akoel.template.ejb.enums.Status;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class HistoryMappedSuperclass implements Serializable{
	
	private static final long serialVersionUID = -7475491103263566166L;
	
	private Status status;
	private Calendar statusAt;
	private User statusBy;

	@Column(nullable=false)
	@Enumerated(EnumType.ORDINAL)
	public Status getStatus(){
		return status;
	}
	public void setStatus(Status status){
		this.status = status;
	}
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="statusat", nullable=false)
	public Calendar getStatusAt(){
		return statusAt;
	}
	public void setStatusAt(Calendar statusAt){
		this.statusAt = statusAt;
	}
	
	@ManyToOne(optional=true)
	@JoinColumn(name="statusby_id", nullable=true)
	public User getStatusBy(){
		return statusBy;
	}
	public void setStatusBy(User statusBy){
		this.statusBy = statusBy;
	}
	
	public abstract boolean equals( final Object otherObject );
	
	public abstract String toString();
}
