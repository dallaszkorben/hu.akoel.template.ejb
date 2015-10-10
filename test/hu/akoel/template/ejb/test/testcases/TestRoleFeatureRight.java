package hu.akoel.template.ejb.test.testcases;

import java.util.GregorianCalendar;

import javax.naming.NamingException;

import hu.akoel.template.ejb.entities.Role;
import hu.akoel.template.ejb.entities.User;
import hu.akoel.template.ejb.enums.Checked;
import hu.akoel.template.ejb.exceptions.EJBNoFeatureRightException;
import hu.akoel.template.ejb.services.InitialContextService;
import hu.akoel.template.ejb.test.TestController;
import hu.akoel.template.ejb.test.annotation.InputSet;
import hu.akoel.template.ejb.test.exception.TestException;

import org.junit.Before;
import org.junit.Test;

public class TestRoleFeatureRight extends TestController{

	@Before
	public void setup(){		

	}
	
	/**
	 * Test case:		Capture a RoleFeatureRight for visitor role
	 * Condition:		User has fr_rolefeatureright_capture right
	 * Expected result:	New RoleFeatureRight captured
	 */
	@Test
	@InputSet(value={"test/testdata/testInputSet_MinimalAdmin.xml"})
	public void testCaptureRole() throws TestException, NamingException{
		Integer captureUserId = 1;			//admin user
		
		//Expected returned Entity from the Session Method
		User user = new User();
		user.setId(1);		
		Role role = new Role();
		role.setName( newRoleName);
		role.setStatus( Checked.CAPTURED );
		role.setCapturedBy( user );
		role.setCapturedAt(new GregorianCalendar(2001,0,1));
		
		//Parameters for the doCapture Session Method
		Object[] parameterList = new Object[]{newRoleName, captureUserId};
		
		//Invokes the doCapture Session Method
		doSession( InitialContextService.getRoleFeatureRightSession(), "doCapture", parameterList, "test/testdata/role/testCompareSetRole_Added.xml", role, EJBNoFeatureRightException.class );		
		
	}
	
	/**
	 * Test case:		Can not Capture a Role
	 * Condition:		User has NO fr_role_capture right
	 * Expected result:	No New Role captured
	 */
	@Test
	@InputSet(value={"test/testdata/testInputSet_MinimalAdmin.xml"})
	public void testCaptureRole_NoRight() throws NamingException, TestException{
		String newRoleName = "role_new";
		Integer captureUserId = 2;			//visitor user
		
		//Mock the FeatureRightService 
		
		//Parameters for the doCapture Session Method
		Object[] parameterList = new Object[]{newRoleName, captureUserId};
		
		//Invokes the doCapture Session Method
		doSession( InitialContextService.getRoleSession(), "doCapture", parameterList, "test/testdata/role/testCompareSetRole_noRight.xml", EJBNoFeatureRightException.class );
		
	}	
}
