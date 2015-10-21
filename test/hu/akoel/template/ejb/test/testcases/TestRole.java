package hu.akoel.template.ejb.test.testcases;

import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.List;

import javax.naming.NamingException;

import hu.akoel.template.ejb.entities.Role;
import hu.akoel.template.ejb.entities.User;
import hu.akoel.template.ejb.enums.FeatureRight;
import hu.akoel.template.ejb.exceptions.EJBDeletedException;
import hu.akoel.template.ejb.exceptions.EJBNoFeatureRightException;
import hu.akoel.template.ejb.exceptions.EJBNoResultFindByIdException;
import hu.akoel.template.ejb.exceptions.EJBeanException;
import hu.akoel.template.ejb.services.InitialContextService;
import hu.akoel.template.ejb.test.ExpectedExceptionObject;
import hu.akoel.template.ejb.test.TestController;
import hu.akoel.template.ejb.test.annotation.TestDetails;
import hu.akoel.template.ejb.test.annotation.TestInputSet;
import hu.akoel.template.ejb.test.exception.TestException;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

public class TestRole extends TestController{

	@Before
	public void setup(){		

	}
	
	//
	// --- CAPTURE ---
	//
	
	@Test
	@TestDetails( 
			testCase="Capture a Role",
			testCondition="User has ROLE_CAPTURE right",
			expectedResult="New Role captured"
	)
	@TestInputSet(value={"test/testdata/testInputSet_MinimalAdmin.xml"})
	public void testRole_Capture() throws TestException, NamingException, EJBeanException, IOException, JSONException{
		String newRoleName = "role_new";
		Integer captureUserId = 1;			//admin user
		
		//Expected returned Entity from the Session Method
		User user = new User();
		user.setId(1);		
		Role expectedResultRole = new Role();
		expectedResultRole.setName( newRoleName);
		expectedResultRole.setOperationBy( user );
		expectedResultRole.setOperationAt(new GregorianCalendar(2001,0,1));
		
		//Parameters for the doCapture Session Method
		Object[] parameterList = new Object[]{newRoleName, captureUserId};
		
		//Invokes the doCapture Session Method
		initializeSession( InitialContextService.getRoleSession(), "doCapture", parameterList).
			setExpectedXMLDBSet("test/testdata/role/testCompareRole_Capture.xml").
			setExpectedJsonObject("test/testdata/role/testCompareRole_Capture.json").
			<Role>doSession();
	}
	
	@Test
	@TestDetails( 
			testCase="Can not Capture a Role",
			testCondition="User has NO ROLE_CAPTURE",
			expectedResult="No New Role captured"
	)
	@TestInputSet(value={"test/testdata/testInputSet_MinimalAdmin.xml"})
	public void testRole_CaptureWithNoRight() throws NamingException, TestException, IOException, JSONException{
		String newRoleName = "role_new";
		Integer captureUserId = 2;			//visitor user
		
		//Parameters for the doCapture Session Method
		Object[] parameterList = new Object[]{newRoleName, captureUserId};
		
		//Invokes the doCapture Session Method
		initializeSession( InitialContextService.getRoleSession(), "doCapture", parameterList ).
			setExpectedXMLDBSet( "test/testdata/role/testCompareRole_CaptureWithNoRight.xml" ).
			setExpectedException( new ExpectedExceptionObject(EJBNoFeatureRightException.class).setExactMessage("The User id=" + captureUserId + " has no '" + FeatureRight.ROLE_CAPTURE.getLocalized() + "' Feature Right.") ).
		<Role>doSession();		
	}	

	//
	// --- UPDATE ---
	//
	
	@Test
	@TestDetails( 
			testCase="Update a Role",
			testCondition="User has ROLE_UPDATE right",
			expectedResult="The Role is Updated"
	)
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
			setExpectedJsonObject("test/testdata/role/testCompareRole_Update.json").
		<Role>doSession();
	}

	@Test
	@TestDetails( 
			testCase="Can not Update a Role",
			testCondition="User has NO ROLE_UPDATE right",
			expectedResult="EJBNoFeatureRightException thrown"
	)
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
	
	@Test
	@TestDetails( 
			testCase="Can not Update a Role",
			testCondition="The Role is already deleted",
			expectedResult="EJBNDeletedException thrown"
	)
	@TestInputSet(value={"test/testdata/testInputSet_MinimalAdmin.xml", "test/testdata/role/testInputRole_UpdateDeleted.xml"})
	public void testRole_UpdateDeletedRole() throws TestException, NamingException, EJBeanException, IOException, JSONException{
		String newRoleName = "updated_role_name";
		Integer updateUserId = 1;			//admin user
		Integer roleId = 2;					//visitor role to update
	
		//Parameters for the doCapture Session Method
		Object[] parameterList = new Object[]{roleId, newRoleName, updateUserId};

		//Invokes the doUpdate Session Method
		initializeSession( InitialContextService.getRoleSession(), "doUpdate", parameterList ).
			setExpectedException( new ExpectedExceptionObject(EJBDeletedException.class).setExactMessage("The 'Role' element with name: deleted_role_visitor id: " + roleId + " is already deleted.") ).
		<Role>doSession();
	}
	
	@Test
	@TestDetails( 
			testCase="Can not Update a none existed Role",
			testCondition="The Role with the specified ID is not existed",
			expectedResult="EJBNDeletedException thrown"
	)
	@TestInputSet(value={"test/testdata/testInputSet_MinimalAdmin.xml"})
	public void testRole_UpdateNoneExistedRole() throws TestException, NamingException, EJBeanException, IOException, JSONException{
		String newRoleName = "updated_role_name";
		Integer updateUserId = 1;			//admin user
		Integer roleId = 3;					//none existed role to update
	
		//Parameters for the doCapture Session Method
		Object[] parameterList = new Object[]{roleId, newRoleName, updateUserId};

		//Invokes the doUpdate Session Method
		initializeSession( InitialContextService.getRoleSession(), "doUpdate", parameterList ).
			setExpectedException( new ExpectedExceptionObject(EJBNoResultFindByIdException.class).setExactMessage( "No found element by Id: em.find(" + Role.class.getCanonicalName() + ".class, " + roleId + ") has no result.") ).
		<Role>doSession();
	}
	//
	// --- DELETE --
	//
	
	@Test
	@TestDetails( 
			testCase="Delete a Role",
			testCondition="User has ROLE_DELETE right",
			expectedResult="The Active field of the Role is false"
	)
	@TestInputSet(value={"test/testdata/testInputSet_MinimalAdmin.xml"})
	public void testRole_Delete() throws TestException, NamingException, EJBeanException, IOException, JSONException{
		Integer deleteUserId = 1;			//admin user
		Integer roleId = 2;					//visitor role to delete
	
		//Parameters for the doDelete Session Method
		Object[] parameterList = new Object[]{roleId, deleteUserId};

		//Invokes the doDelete Session Method
		initializeSession( InitialContextService.getRoleSession(), "doDelete", parameterList ).
			setExpectedXMLDBSet( "test/testdata/role/testCompareRole_Delete.xml" ).
			setExpectedJsonObject("test/testdata/role/testCompareRole_Delete.json").
		<Role>doSession();
	}

	@Test
	@TestDetails( 
			testCase="Delete a Role",
			testCondition="User has NO ROLE_DELETE right",
			expectedResult="The Active field of the Role is still true"
	)
	@TestInputSet(value={"test/testdata/testInputSet_MinimalAdmin.xml"})
	public void testRole_DeleteWithNoRight() throws TestException, NamingException, EJBeanException, IOException, JSONException{
		Integer deleteUserId = 2;			//visitor user
		Integer roleId = 1;					//admin role to delete
	
		//Parameters for the doDlete Session Method
		Object[] parameterList = new Object[]{roleId, deleteUserId};

		//Invokes the doDelete Session Method
		initializeSession( InitialContextService.getRoleSession(), "doDelete", parameterList ).
			setExpectedXMLDBSet( "test/testdata/role/testCompareRole_DeleteWithNoRight.xml" ).
			setExpectedException( new ExpectedExceptionObject(EJBNoFeatureRightException.class).setExactMessage("The User id=" + deleteUserId + " has no '" + FeatureRight.ROLE_DELETE.getLocalized() + "' Feature Right.") ).
		<Role>doSession();
	}
	
	
	//
	// --- GETHISTORY ---
	//	

	@Test
	@TestDetails( 
			testCase="Get the history of the specific Role",
			testCondition="User has ROLE_READ right",
			expectedResult="You get back the History of the Role"
	)
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

	@Test
	@TestDetails( 
			testCase="Can not get the history of the specific Role",
			testCondition="User has NO ROLE_READ right",
			expectedResult="EJBNoFeatureRightException"
	)
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

