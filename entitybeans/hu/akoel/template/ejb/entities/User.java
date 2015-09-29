package hu.akoel.template.ejb.entities;

import hu.akoel.template.ejb.services.DateService;
import hu.akoel.template.ejb.services.LocalizeService;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User extends HistoryMappedSuperclass implements EntityObject{

	private static final long serialVersionUID = -8033967557177671858L;
	
	private Integer id;
	private String name;
	private String password;
	private String email;
	private String surname;
	private String firstname;
	private Role role;
		
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="user_id_seq_gen")
	@SequenceGenerator(allocationSize=1,initialValue=1,name="user_id_seq_gen", sequenceName="user_id_seq")
	@Column(name="id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(nullable=false, unique=true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(nullable=false, unique=false)	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(nullable=false, unique=true)	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	@ManyToOne(optional=false)
	@JoinColumn(name="role_id", nullable=false)
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	public int hashCode(){
		final int prime = 31;
		int hash = 3;
		hash = hash * prime + this.getName().hashCode();
		return hash;
	}
	
	@Override
	public boolean equals( final Object user ) {		
        if (user instanceof User) {
            final User other = (User) user;
            return 
            		other == this ||
            		other.id.equals( this.id ) || 
            		other.getName().equals(this.getName()) || 
            		other.getEmail().equals(this.getEmail());        
        }	 
        return false;
	}
	
	@Override
	public boolean equalsByThisNotNullFields(Object otherObject) {
		if( null != otherObject && otherObject instanceof User ){
			User other = (User)otherObject;
			if( 
					( null == this.id || this.id.equals(other.id) ) && 
					( null == this.getName() || this.getName().equals( other.getName() ) ) &&
					( null == this.getEmail() || this.getEmail().equals( other.getEmail() ) ) &&
					( null == this.getPassword() || this.getPassword().equals( other.getPassword() ) ) &&
					( null == this.getFirstname() || this.getFirstname().equals( other.getFirstname() ) ) &&
					( null == this.getSurname() || this.getSurname().equals( other.getSurname() ) )&&
					( null == this.getRole() || this.getRole().equals( other.getRole() ) )
			){
				return true;
			}			
		}
		return false;
	}
	
	@Override
	public String toString(){
		StringBuffer out = new StringBuffer();
		out.append("User = id: " + this.getId() );
		out.append(", Name: " + this.getName() );
		out.append(", email: " + this.getEmail() );
		out.append(", First name: " + this.getFirstname() );
		out.append(", Surname: " + this.getSurname() );
		out.append(", Role: " + LocalizeService.getDefaultLocalized( this.getRole().getName() ));
		out.append(" - St: " + this.getStatus().getLocalized() );
		out.append(", Ch: " + DateService.getTimeByDefaultFormatter( this.getStatusAt().getTime() ) );
		out.append(", By: " + (this.getStatusBy() != null ? this.getStatusBy().getName() : "" ));

		
		return out.toString();
	}

}
