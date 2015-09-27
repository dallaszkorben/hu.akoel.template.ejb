package hu.akoel.template.ejb.entities;

import hu.akoel.template.ejb.enums.FeatureRight;
import hu.akoel.template.ejb.enums.Status;
import hu.akoel.template.ejb.services.DateService;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.Index;

@Entity
@Table(name="rolefeatureright")
@Index(columnNames={"role", "featureRight"})
public class RoleFeatureRight extends HistoryMappedSuperclass implements Serializable{

	private static final long serialVersionUID = 9220363710862227424L;
	
	private int id;
	private Role role;
	private FeatureRight featureRight;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="rolefeatureright_id_seq_gen")
	@SequenceGenerator(allocationSize=1,initialValue=1,name="rolefeatureright_id_seq_gen", sequenceName="rolefeatureright_id_seq")
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne(optional=false)
	@JoinColumn(name="role_id", nullable=false)
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}	
	
	@Column(nullable=false)
	@Enumerated(EnumType.ORDINAL)
	public FeatureRight getFeatureRight(){
		return featureRight;
	}
	public void setFeatureRight(FeatureRight featureRight){
		this.featureRight = featureRight;
	}
	
	//---
	
	public int hashCode(){	
		final int prime = 31;
		int hash = 3;
		hash = hash * prime + this.getRole().hashCode();
		hash = hash * prime + this.getFeatureRight().hashCode();
		return hash;
	}
	
	public boolean equals( final Object user ) {		
        if (user instanceof User) {
            final RoleFeatureRight other = (RoleFeatureRight) user;
            return other == this || other.id == this.id || (other.getRole().equals(this.getRole() ) && other.getFeatureRight().equals(this.getFeatureRight() ) );        
        }	 
        return false;
	}
	
	public String toString(){
		StringBuffer out = new StringBuffer();
		out.append("RoleFeatureRight = id: " + this.getId() );
		out.append(", Role: " + this.getRole().getName() );
		out.append(", FeatureRight: " + this.getFeatureRight().getLocalized() );
		out.append(" - St: " + this.getStatus().getLocalized() );
		out.append(", Ch: " + DateService.getTimeByDefaultFormatter( this.getStatusAt().getTime() ) );
		out.append(", By: " + (this.getStatusBy() == null ? "": this.getStatusBy().getName()));
		
		return out.toString();
	}
}
