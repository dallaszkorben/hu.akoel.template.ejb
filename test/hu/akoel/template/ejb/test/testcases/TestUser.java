package hu.akoel.template.ejb.test.testcases;

import java.io.IOException;
import java.util.List;

import javax.naming.NamingException;

import hu.akoel.template.ejb.entities.Role;
import hu.akoel.template.ejb.entities.User;
import hu.akoel.template.ejb.enums.FeatureRight;
import hu.akoel.template.ejb.exceptions.EJBNoFeatureRightException;
import hu.akoel.template.ejb.exceptions.EJBeanException;
import hu.akoel.template.ejb.services.InitialContextService;
import hu.akoel.template.ejb.test.ExpectedExceptionObject;
import hu.akoel.template.ejb.test.TestController;
import hu.akoel.template.ejb.test.annotation.TestInputSet;
import hu.akoel.template.ejb.test.exception.TestException;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

public class TestUser extends TestController{

	@Before
	public void setup(){		

	}
	
	/**
	 * Test case:		Capture a User
	 * Condition:		User has USER_CAPTURE right
	 * Expected result:	New user captured
	 * @throws EJBeanException 
	 * @throws JSONException 
	 * @throws IOException 
	 */
	@Test
	@TestInputSet(value={"test/testdata/testInputSet_MinimalAdmin.xml"})
	public void testUser_Capture() throws TestException, NamingException, EJBeanException, IOException, JSONException{
		String newUserName = "newUser";
		String newUserFirstName = "newUserFirstName";
		String newUserSurName = "newUserSurname";
		String newUserEmail = "newEmail@valah.ol";
		String newUserPassword = "xxx";
		Integer newUserRoleId = 1;
		
		Integer captureUserId = 1;			//admin user to capture
		
		//Parameters for the doCapture Session Method
		Object[] parameterList = new Object[]{newUserRoleId, newUserName, newUserPassword, newUserFirstName, newUserSurName, newUserEmail, captureUserId };
		
		//Invokes the doCapture Session Method
		initializeSession( InitialContextService.getUserSession(), "doCapture", parameterList).
			setExpectedXMLDBSet("test/testdata/user/testCompareUser_Capture.xml").
			setExpectedJsonObject("test/testdata/user/testCompareUser_Capture.json").
			<User>doSession();		

	}
	
	/**
	 * Test case:		Can not Capture a User
	 * Condition:		User has NO USER_CAPTURE
	 * Expected result:	EJBNoFeatureRightException, No New Role captured
	 * @throws JSONException 
	 * @throws IOException 
	 */
	@Test
	@TestInputSet(value={"test/testdata/testInputSet_MinimalAdmin.xml"})
	public void testRole_CaptureWithNoRight() throws NamingException, TestException, IOException, JSONException{
		String newUserName = "newUser";
		String newUserFirstName = "newUserFirstName";
		String newUserSurName = "newUserSurname";
		String newUserEmail = "newEmail@valah.ol";
		String newUserPassword = "xxx";
		Integer newUserRoleId = 1;
		
		Integer captureUserId = 2;			//visitor user to capture
		
		//Parameters for the doCapture Session Method
		Object[] parameterList = new Object[]{newUserRoleId, newUserName, newUserPassword, newUserFirstName, newUserSurName, newUserEmail, captureUserId };
		
		//Invokes the doCapture Session Method
		initializeSession( InitialContextService.getUserSession(), "doCapture", parameterList ).
			setExpectedXMLDBSet("test/testdata/user/testCompareUser_Capture.xml").
			setExpectedException( new ExpectedExceptionObject(EJBNoFeatureRightException.class).setExactMessage("The User id=" + captureUserId + " has no '" + FeatureRight.USER_CAPTURE.getLocalized() + "' Feature Right.") ).
		<Role>doSession();		
	}	

	/**
	 * Test case:		Update a Role
	 * Condition:		User has ROLE_UPDATE right
	 * Expected result:	The Role is Updated
	 * @throws TestException
	 * @throws NamingException
	 * @throws EJBeanException
	 * @throws IOException
	 * @throws JSONException
	 */
	@Test
	@TestInputSet(value={"test/testdata/testInputSet_MinimalAdmin.xml"})
	public void testRole_Update() throws TestException, NamingException, EJBeanException, IOException, JSONException{
		String newRoleName = "updated_role_name";
		Integer updateUserId = 1;			//admin user
		Integer roleId = 1;					//admin role to update
	
		//Parameters for the doCapture Session Method
		Object[] parameterList = new Object[]{roleId, newRoleName, updateUserId};

		//Invokes the doUpdate Session Method
		initializeSession( InitialContextService.getRoleSession(), "doUpdate", parameterList ).
			setExpectedXMLDBSet( "test/testdata/role/testCompareRole_Update.xml" ).
			setExpectedJsonObject("test/testdata/role/testCompareRole_DoUpdate.json").
		<Role>doSession();
	}

	/**
	 * Test case:		Can not Update a Role
	 * Condition:		User has NO ROLE_UPDATE right
	 * Expected result:	EJBNoFeatureRightException, No New Role captured
	 * @throws TestException
	 * @throws NamingException
	 * @throws EJBeanException
	 * @throws IOException
	 * @throws JSONException
	 */
	@Test
	@TestInputSet(value={"test/testdata/testInputSet_MinimalAdmin.xml"})
	public void testRole_UpdateWithNoRight() throws TestException, NamingException, EJBeanException, IOException, JSONException{
		String newRoleName = "updated_role_name";
		Integer updateUserId = 2;			//visitor user
		Integer roleId = 1;					//admin role to update
	
		//Parameters for the doCapture Session Method
		Object[] parameterList = new Object[]{roleId, newRoleName, updateUserId};

		//Invokes the doUpdate Session Method
		initializeSession( InitialContextService.getRoleSession(), "doUpdate", parameterList ).
			setExpectedXMLDBSet( "test/testdata/role/testCompareRole_UpdateWithNoRight.xml" ).
			setExpectedException( new ExpectedExceptionObject(EJBNoFeatureRightException.class).setExactMessage("The User id=" + updateUserId + " has no '" + FeatureRight.ROLE_UPDATE.getLocalized() + "' Feature Right.") ).
		<Role>doSession();
	}
	
	/**
	 * Test case:		Get the history of the specific User
	 * Condition:		User has ROLE_READ right
	 * Expected result:	You get back the History of the Role
	 * @throws TestException
	 * @throws NamingException
	 * @throws EJBeanException
	 * @throws JSONException
	 * @throws IOException
	 */
	@Test
	@TestInputSet(value={"test/testdata/testInputSet_MinimalAdmin.xml", "test/testdata/role/testInputRole_GetHistory.xml"})
	public void testRole_GetHistory() throws TestException, NamingException, EJBeanException, JSONException, IOException{
		Integer userId = 1;			//admin user
		Integer roleId = 2;			//visitor role to check

		//Parameters for the doCapture Session Method
		Object[] parameterList = new Object[]{roleId, userId};

		//Invokes the getHistory Session Method
		List<Role> roleList = initializeSession( InitialContextService.getRoleSession(), "getHistory", parameterList ).
				setExpectedJsonArray("test/testdata/role/testCompareRole_GetHistory.json").
				<List<Role>>doSession();
	}

	/**
	 * Test case:		Can not get History of User
	 * Condition:		User has NO ROLE_READ right
	 * Expected result:	EJBNoFeatureRightException
	 * @throws TestException
	 * @throws NamingException
	 * @throws EJBeanException
	 * @throws JSONException
	 * @throws IOException
	 */
	@Test
	@TestInputSet(value={"test/testdata/testInputSet_MinimalAdmin.xml", "test/testdata/role/testInputRole_GetHistory.xml"})
	public void testRole_GetHistoryWithNoRight() throws TestException, NamingException, EJBeanException, JSONException, IOException{
		Integer userId = 2;			//visitor user
		Integer roleId = 2;			//visitor role to check

		//Parameters for the doCapture Session Method
		Object[] parameterList = new Object[]{roleId, userId};

		//Invokes the getHistory Session Method
		List<Role> roleList = initializeSession( InitialContextService.getRoleSession(), "getHistory", parameterList ).
				setExpectedJsonArray("test/testdata/role/testCompareRole_EmptyArray.json").
				setExpectedException( new ExpectedExceptionObject(EJBNoFeatureRightException.class).setExactMessage("The User id=" + userId + " has no '" + FeatureRight.ROLE_READ.getLocalized() + "' Feature Right.") ).
				<List<Role>>doSession();
	}
}

