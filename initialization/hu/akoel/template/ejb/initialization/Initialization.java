package hu.akoel.template.ejb.initialization;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import hu.akoel.template.ejb.entities.Role;
import hu.akoel.template.ejb.entities.RoleFeatureRight;
import hu.akoel.template.ejb.entities.User;
import hu.akoel.template.ejb.enums.FeatureRight;
import hu.akoel.template.ejb.exceptions.EJBeanException;
import hu.akoel.template.ejb.exceptions.EJBNoResultFindByIdException;
import hu.akoel.template.ejb.services.InitialContextService;
import hu.akoel.template.ejb.session.remote.RoleFeatureRightRemote;
import hu.akoel.template.ejb.session.remote.RoleRemote;
import hu.akoel.template.ejb.session.remote.UserRemote;

import javax.naming.NamingException;
import javax.sql.DataSource;

public class Initialization {
	public Integer roleAdminId;
	public Integer roleGuestId;
	public Integer roleVisitorId;
	
	public static void main(String[] args) throws NamingException, EJBeanException, SQLException{
		
		Initialization init = new Initialization();
		
		init.clearDB();
		
		init.fillOutRole();
		init.fillOutUser();
		init.fillOutRoleFeatureElement();
	}
	
	private void clearDB() throws NamingException, SQLException{
		DataSource ds = InitialContextService.getDatabase();
		Connection conn = ds.getConnection();
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery("SELECT * FROM Role");
		while(rs.next()){
			String name = rs.getString("name");
			System.out.println(name);
		}
		
		//Clear DB
		//Suspend constraints
		//stm.executeUpdate("SET CONSTRAINTS ALL DEFERRED");		
		stm.executeUpdate("TRUNCATE " + 
				"Users, " +
				"RoleFeatureElement, " +		
				"Role " +
				"CASCADE");
		
		//Enable constraints
		//rs = stm.executeQuery("ALTER TABLE ? CHECK CONSTRAINT ALL");
		
		
		
		conn.close();
	}
	
	private void fillOutRole() throws NamingException{
		String roleNameAdmin = "role_admin";
		String roleNameGuest = "role_guest";
		String roleNameVisitor = "role_visitor";
		
		Role roleAdmin;
		Role roleGuest;
		Role roleVisitor;
		
		RoleRemote roleSession = InitialContextService.getRoleSession();
		
/*		roleAdmin = roleSession.doCapture(roleNameAdmin, null);	
		roleGuest = roleSession.doCapture(roleNameGuest, null);
		roleVisitor = roleSession.doCapture(roleNameVisitor, null);
		
		roleAdminId = roleAdmin.getId();
		roleGuestId = roleGuest.getId();
		roleVisitorId = roleVisitor.getId();
		
		System.err.println(roleAdmin.toString());
		System.err.println(roleGuest.toString());
		System.err.println(roleVisitor.toString());
*/		
	}
	
	private void fillOutUser() throws NamingException, EJBeanException{
		User statusBy = null;
		
		UserRemote userSession = InitialContextService.getUserSession();
		
		//Administrator
		String adminName = "akoel";
		String adminPassword = "blabla";
		String adminFirstName = "";
		String adminSurname = "";
		String adminEmail = "akoel@somewhe.re";			
		User userAdmin = userSession.doCapture(roleAdminId, adminName, adminPassword, adminFirstName, adminSurname, adminEmail, statusBy);
		
		//Visitor
		String visitorName = "visitor";
		String visitorPassword = "";
		String visitorFirstName = "";
		String visitorSurname = "";
		String visitorEmail = "";		
		User userVisitor = userSession.doCapture(roleVisitorId, visitorName, visitorPassword, visitorFirstName, visitorSurname, visitorEmail, statusBy);

		System.err.println(userAdmin.toString());
		System.err.println(userVisitor.toString());
	}
	
	public void fillOutRoleFeatureElement() throws NamingException, EJBNoResultFindByIdException{
		User statusBy = null;
		
		RoleFeatureRightRemote roleFeatureElementSession = InitialContextService.getRoleFeatureRightSession();
		
		RoleFeatureRight roleFeatureElement;
//		roleFeatureElement = roleFeatureElementSession.doCapture(roleAdminId, FeatureRight.ROLE_CAPTURE, statusBy);
//		roleFeatureElement = roleFeatureElementSession.doCapture(roleAdminId, FeatureRight.FE_2, statusBy);
		
//		roleFeatureElement = roleFeatureElementSession.doCapture(roleVisitorId, FeatureRight.FE_2, statusBy);
				
	}
	

}
