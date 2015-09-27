package hu.akoel.template.ejb.test.testcases.role;

import static org.junit.Assert.fail;

import javax.naming.NamingException;

import hu.akoel.template.ejb.exceptions.EJBNotFeatureRightException;
import hu.akoel.template.ejb.services.InitialContextService;
import hu.akoel.template.ejb.session.remote.RoleRemote;
import hu.akoel.template.ejb.test.TestController;
import hu.akoel.template.ejb.test.annotation.InputSet;
import hu.akoel.template.ejb.test.exception.TestException;
import org.junit.Test;


public class TestRole extends TestController{
	
	/**
	 * Test case:		Capture a Role
	 * Condition:		User has fr_role_capture right
	 * Expected result:	New Role captured
	 * @throws NamingException 
	 * @throws TestException 
	 */
	@Test
	@InputSet(value={"test/testdata/testInputSet_MinimalAdmin.xml"})
	public void testCaptureRole() throws TestException, NamingException{
		String newRoleName = "role_new";
		Integer captureUserId = 1;			//admin user
		
		//Mock the FeatureRightService
		
		Object[] parameterList = new Object[]{newRoleName, captureUserId};
		doSession( InitialContextService.getRoleSession(), "doCapture", parameterList, "test/testdata/role/testCompareSetRole_Added.xml", EJBNotFeatureRightException.class );		
		
	}
	
	/**
	 * Test case:		Can not Capture a Role
	 * Condition:		User has NO fr_role_capture right
	 * Expected result:	No New Role captured
	 * @throws NamingException 
	 * @throws TestException 
	 */
	@Test
	@InputSet(value={"test/testdata/testInputSet_MinimalAdmin.xml"})
	public void testCaptureRole_NoRight() throws NamingException, TestException{
		String newRoleName = "role_new";
		Integer captureUserId = 2;			//visitor user
		
		//Mock the FeatureRightService 
		
		Object[] parameterList = new Object[]{newRoleName, captureUserId};		
		doSession( InitialContextService.getRoleSession(), "doCapture", parameterList, "test/testdata/role/testCompareSetRole_noRight.xml", EJBNotFeatureRightException.class );
		
	}	
	
}
