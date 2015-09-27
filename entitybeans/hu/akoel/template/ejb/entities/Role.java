package hu.akoel.template.ejb.entities;

import hu.akoel.template.ejb.services.DateService;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;



@Entity
@Table(name="role")
public class Role extends HistoryMappedSuperclass implements Serializable{

	private static final long serialVersionUID = -8046466070317090805L;
	
	private int id;
	private String name;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="role_id_seq_gen")
	@SequenceGenerator(allocationSize=1,initialValue=1,name="role_id_seq_gen", sequenceName="role_id_seq")
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(nullable=false, unique=true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int hashCode(){
		final int prime = 31;
		int hash = 1;
		hash = hash * prime + this.getName().hashCode();
		return hash;
	}
	
	public boolean equals( final Object user ) {		
        if (user instanceof User) {
            final Role other = (Role) user;
            return other.id == this.id || other.getName().equals(this.getName());        
        }	 
        return false;
	}
	
	public String toString(){
		StringBuffer out = new StringBuffer();
		out.append("Role = id: " + this.getId() );
		out.append(", Name: " + this.getName() );
		out.append(" - St: " + this.getStatus().getLocalized() );
		out.append(", Ch: " + DateService.getTimeByDefaultFormatter( this.getStatusAt().getTime() ) );
		out.append(", By: " + (this.getStatusBy() == null ? "": this.getStatusBy().getName()));
		
		return out.toString();
	}
}
