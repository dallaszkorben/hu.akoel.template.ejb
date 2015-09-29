package hu.akoel.template.ejb.test.testcases.role;

import java.util.GregorianCalendar;

import javax.naming.NamingException;

import hu.akoel.template.ejb.entities.Role;
import hu.akoel.template.ejb.entities.User;
import hu.akoel.template.ejb.enums.Status;
import hu.akoel.template.ejb.exceptions.EJBNotFeatureRightException;
import hu.akoel.template.ejb.services.InitialContextService;
import hu.akoel.template.ejb.test.TestController;
import hu.akoel.template.ejb.test.annotation.InputSet;
import hu.akoel.template.ejb.test.exception.TestException;

import org.junit.Before;
import org.junit.Test;

//import static org.powermock.api.mockito.PowerMockito.mockStatic;   
//import static org.powermock.api.mockito.PowerMockito.when;  

//@RunWith(PowerMockRunner.class)
//@PrepareForTest({DateService.class})
//@PowerMockIgnore({"org.apache.http.conn.ssl.*", "javax.net.ssl.*"})
public class TestRole extends TestController{
	//int mockYear = 2013;
	//int mockMonth = Calendar.MARCH;
	//int mockDay = 5;

	//Calendar mockedDate = new GregorianCalendar(mockYear, mockMonth, mockDay, 0, 0, 0);

	@Before
	public void setup(){		
	
		//DateService dateService = Mockito.mock( DateService.class );
		//Mockito.when(dateService.getCalendar() ).thenReturn( mockedDate );

	}
	
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
		
		User user = new User();
		user.setId(1);
//		user.setName( "adminuser" );
//		user.setEmail("adminuser@somewhe.re");
//		user.setFirstname("admin");
//		user.setSurname("user");
//		user.setPassword("***");
//		user.setStatus(Status.CAPTURED);
//		user.setStatusAt(new GregorianCalendar(2001,0,1));
		
		Role role = new Role();
		role.setName( newRoleName);
		role.setStatus( Status.CAPTURED );
		role.setStatusBy( user );
		role.setStatusAt(new GregorianCalendar(2001,0,1));
		
		Object[] parameterList = new Object[]{newRoleName, captureUserId};
		doSession( InitialContextService.getRoleSession(), "doCapture", parameterList, "test/testdata/role/testCompareSetRole_Added.xml", role, EJBNotFeatureRightException.class );		
		
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
