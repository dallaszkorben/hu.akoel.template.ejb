package hu.akoel.template.ejb.test.testcases;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.naming.NamingException;

import hu.akoel.template.ejb.entities.Role;
import hu.akoel.template.ejb.entities.User;
import hu.akoel.template.ejb.exceptions.EJBNotFeatureRightException;
import hu.akoel.template.ejb.exceptions.EJBeanException;
import hu.akoel.template.ejb.services.InitialContextService;
import hu.akoel.template.ejb.services.JsonService;
import hu.akoel.template.ejb.test.TestController;
import hu.akoel.template.ejb.test.annotation.InputSet;
import hu.akoel.template.ejb.test.exception.TestException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestRole extends TestController{

	@Before
	public void setup(){		

	}
	
	/**
	 * Test case:		Capture a Role
	 * Condition:		User has ROLE_CAPTURE right
	 * Expected result:	New Role captured
	 * @throws EJBeanException 
	 */
	@Test
	@InputSet(value={"test/testdata/testInputSet_MinimalAdmin.xml"})
	public void testRole_Capture() throws TestException, NamingException, EJBeanException{
		String newRoleName = "role_new";
		Integer captureUserId = 1;			//admin user
		
		//Mock the FeatureRightService
		
		//Expected returned Entity from the Session Method
		User user = new User();
		user.setId(1);		
		Role expectedResultRole = new Role();
		expectedResultRole.setName( newRoleName);
		expectedResultRole.setCapturedBy( user );
		expectedResultRole.setCapturedAt(new GregorianCalendar(2001,0,1));
		
//TODO not in the parameter
		
		//Parameters for the doCapture Session Method
		Object[] parameterList = new Object[]{newRoleName, captureUserId};
		
		//Invokes the doCapture Session Method
		initializeSession( InitialContextService.getRoleSession(), "doCapture", parameterList).
			setExpectedXMLDBSet("test/testdata/role/testCompareSetRole_Capture.xml").
			setExpectedException( EJBNotFeatureRightException.class ).
		doSession();		

	}
	
	/**
	 * Test case:		Can not Capture a Role
	 * Condition:		User has NO fr_role_capture right
	 * Expected result:	No New Role captured
	 */
	@Test
	@InputSet(value={"test/testdata/testInputSet_MinimalAdmin.xml"})
	public void testRole_CaptureWithNoRight() throws NamingException, TestException{
		String newRoleName = "role_new";
		Integer captureUserId = 2;			//visitor user
		
		//Mock the FeatureRightService 
		
		//Parameters for the doCapture Session Method
		Object[] parameterList = new Object[]{newRoleName, captureUserId};
		
		//Invokes the doCapture Session Method
		initializeSession( InitialContextService.getRoleSession(), "doCapture", parameterList ).
			setExpectedXMLDBSet( "test/testdata/role/testCompareSetRole_CaptureWithNoRight.xml" ).
			setExpectedException( EJBNotFeatureRightException.class ).
		<Role>doSession();
		
	}	

	@Test
	@InputSet(value={"test/testdata/testInputSet_MinimalAdmin.xml"})
	public void testRole_Update() throws TestException, NamingException, EJBeanException{
		String newRoleName = "updated_role_name";
		Integer updateUserId = 1;			//admin user
		Integer roleId = 1;					//admin role
	
		//Parameters for the doCapture Session Method
		Object[] parameterList = new Object[]{roleId, newRoleName, updateUserId};

		//Invokes the doUpdate Session Method
		initializeSession( InitialContextService.getRoleSession(), "doUpdate", parameterList ).
			setExpectedXMLDBSet( "test/testdata/role/testCompareSetRole_Update.xml" ).
		<Role>doSession();
	}

	@Test
	@InputSet(value={"test/testdata/testInputSet_MinimalAdmin.xml", "test/testdata/role/testInputSetRole_GetHistory.xml"})
	public void testRole_GetHistory() throws TestException, NamingException, EJBeanException, JSONException{
		Integer userId = 1;			//admin user
		Integer roleId = 2;			//visitor role
		
/*		ArrayList<ArrayList<User>> al = new ArrayList<>();
		ArrayList<User> al2 = new ArrayList<>();
		al2.add(new User());
		al2.add(new User());
		al.add(al2);
		String s = JsonService.getJsonStringFromJavaObject(al);
		System.err.println(s);
*/		
/*		JSONArray jArray = new JSONArray();
		
		JSONObject jObj = new JSONObject();		
		jObj.put( "id", 2 );
		jObj.put("name", "latest_role_visitor");
		jObj.put("original", JSONObject.NULL);
		jObj.put( "capturedby", JSONObject.NULL );		
		jArray.put(jObj);

		jObj = new JSONObject();		
		jObj.put( "id", 2 );
		jObj.put("name", "latest_role_visitor");
		jObj.put("original", JSONObject.NULL);
		jObj.put( "capturedby", JSONObject.NULL );		
		jArray.put(jObj);
*/
		//Parameters for the doCapture Session Method
		Object[] parameterList = new Object[]{roleId, userId};

		//Invokes the getHistory Session Method
		List<Role> roleList = initializeSession( InitialContextService.getRoleSession(), "getHistory", parameterList ).
				setExpectedJsonArray("test/testdata/role/testCompareSetRole_GetHistory.json").
				<List<Role>>doSession();
		System.err.println(roleList);
		
	}

}

