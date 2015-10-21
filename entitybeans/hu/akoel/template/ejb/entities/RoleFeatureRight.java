package hu.akoel.template.ejb.entities;

import hu.akoel.template.ejb.enums.FeatureRight;
import hu.akoel.template.ejb.services.JsonService;

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

//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Entity
@Table(name="rolefeatureright")
@Index(columnNames={"role", "featureRight"})
public class RoleFeatureRight extends HistoryMappedSuperclass<RoleFeatureRight> implements EntityObject{

	private static final long serialVersionUID = 9220363710862227424L;
	
	private Integer id;
	private Role role;
	private FeatureRight featureRight;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="rolefeatureright_id_seq_gen")
	@SequenceGenerator(allocationSize=1,initialValue=1,name="rolefeatureright_id_seq_gen", sequenceName="rolefeatureright_id_seq")
	@Column(name="id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	@Enumerated(EnumType.STRING)
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
		hash = hash * prime + this.getId().hashCode();
		return hash;
	}
	
	public boolean equals( final Object user ) {		
        if (user instanceof User) {
            final RoleFeatureRight other = (RoleFeatureRight) user;
            return 
            		other == this || 
            		other.getId().equals( this.getId() );        
        }	 
        return false;
	}
	
/*	@Override
	public boolean equalsByThisNotNullFields(Object otherObject) {
		if( null != otherObject && otherObject instanceof RoleFeatureRight ){
			RoleFeatureRight other = (RoleFeatureRight)otherObject;
			if( 
					( null == this.id || this.id.equals(other.id) ) && 
					( null == this.getRole() || this.getRole().equals( other.getRole() ) ) &&
					( null == this.getFeatureRight() || this.getFeatureRight().equals( other.getFeatureRight() ) ) 
			){
				return true;
			}			
		}
		return false;
	}
*/
	
	public String toString(){
		//StringBuffer out = new StringBuffer();
		//out.append("RoleFeatureRight = id: " + this.getId() );
		//out.append(", Role: " + this.getRole().getName() );
		//out.append(", FeatureRight: " + this.getFeatureRight().getLocalized() );
		//out.append(", Ch: " + DateService.getTimeByDefaultFormatter( this.getCapturedAt().getTime() ) );
		//out.append(", By: " + (this.getCapturedBy() == null ? "": this.getCapturedBy().getName()));
		//return out.toString();
		
		return JsonService.getJsonStringFromJavaObject(this);
	}

}
