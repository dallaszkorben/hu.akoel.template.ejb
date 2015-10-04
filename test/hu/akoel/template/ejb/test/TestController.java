package hu.akoel.template.ejb.test;

import hu.akoel.template.ejb.entities.EntityObject;
import hu.akoel.template.ejb.exceptions.EJBeanException;
import hu.akoel.template.ejb.services.InitialContextService;
import hu.akoel.template.ejb.services.JsonService;
import hu.akoel.template.ejb.test.annotation.InputSet;
import hu.akoel.template.ejb.test.exception.TestCompareJSonToResultFormatException;
import hu.akoel.template.ejb.test.exception.TestCompareXMLToDBException;
import hu.akoel.template.ejb.test.exception.TestCompareXMLToDBFormatException;
import hu.akoel.template.ejb.test.exception.TestException;
import hu.akoel.template.ejb.test.exception.TestNotExpectedException;
import hu.akoel.template.ejb.test.exception.TestCompareJSonToResultException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.json.Json;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.parsers.ParserConfigurationException;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.FileSystemResourceAccessor;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestController{
	protected ArrayList<Liquibase> liquibaseList = new ArrayList<>();
	private Connection conn = null;
	private Database database;	
	
//	@Rule
//	public TestName testName = new TestName();
	
	@Rule
	public TestWatcher testWatcher = new MyTestWatcher();

	public TestController(){
		//----------------------
		// Gain the database
		//----------------------
		DataSource ds;					
		try {
			ds = InitialContextService.getDatabase();
			conn = ds.getConnection();
			database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(conn));
		} catch (NamingException | DatabaseException | SQLException e) {
			//e.printStackTrace();
			throw new Error(e);				
		}
	}
	
	class MyTestWatcher extends TestWatcher {		

		@Override
		protected void starting(final Description description) {
			
			//--------------------
			// Gain liquidbaseList
			//--------------------
			InputSet dataSetAnnnotation = (InputSet)description.getAnnotation(InputSet.class);
			if( null != dataSetAnnnotation ){
				for( String value: dataSetAnnnotation.value() ){
					//databaseChangeLogXmlList.add( value );
					
					try {					
						Liquibase liquibase = new Liquibase(value, new FileSystemResourceAccessor(), database);
						liquibaseList.add( liquibase );
					} catch (LiquibaseException e) {
						e.printStackTrace();
						throw new Error(e);
					}
				}
			}

			//----------------------
			// Clears all tables
			// Fill out tables
			//----------------------
			for( Liquibase liquibase : liquibaseList ){
				try {					
					liquibase.update("");
				} catch (LiquibaseException e) {
					e.printStackTrace();
					throw new Error(e);
				}
			}
		}

		@Override
		protected void finished(final Description description) {
			super.finished(description);

			try {
				for( Liquibase liquibase: liquibaseList) {
					liquibase.rollback(1000, null);
				}				
			} catch (Exception e) {
				e.printStackTrace();
				throw new Error(e);
			}finally{
				try{
					conn.close();
				}catch(Exception f){}
			}
		}
	};

	protected TestControllerChainParameterHelper initializeSession( Object service, String methodName, Object[] parameterList ){
		return new TestControllerChainParameterHelper( this, service, methodName, parameterList );
	}

	/**
	 * Invokes Session operation
	 * 
	 * @param service
	 * @param methodName
	 * @param parameterList
	 * @param expectedJSONObjectFileName
	 * @param expectedJSONArrayFileName
	 * @param expectedXMLFileName
	 * @param expectedException
	 * @return
	 * @throws TestException
	 */
	//
	@SuppressWarnings("unchecked")
	public <E> E doSession( Object service, String methodName, Object[] parameterList, String expectedJSONObjectFileName, String expectedJSONArrayFileName, String expectedXMLFileName, Class<? extends EJBeanException> expectedException ) throws TestException{

		E actualResult = null;
		
		Class<?>[] parameterClassList = new Class<?>[ parameterList.length ];
		for( int i = 0; i < parameterList.length; i++ ){
			parameterClassList[i] = parameterList[i].getClass();
		}
		
		Method method = null;
		try {
			method = service.getClass().getMethod(methodName, parameterClassList );			
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			throw new Error(e);
		}

		try {
			  actualResult = (E) method.invoke(service, parameterList);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			throw new Error(e);
		} catch(InvocationTargetException e){
			
			Throwable targetException = e.getTargetException();
			
			//There was an exception but it was not the expected !!!!
			if( null == expectedException || !targetException.getClass().equals( expectedException) ){				
				throw new TestNotExpectedException( targetException.getLocalizedMessage() );
			}
		}
		
		//--------------------
		// Compare XML the DB
		//--------------------
		if( null != expectedXMLFileName ){
			try {
				String difference = CompareXMLToDB.getDifference(expectedXMLFileName, conn, database);
				if( null != difference ){
					throw new TestCompareXMLToDBException(expectedXMLFileName, difference );
				}
			} catch (ParserConfigurationException |SAXException |IOException | SQLException e) {				
				//e.printStackTrace();
				throw new TestCompareXMLToDBFormatException(e.getLocalizedMessage());
			}
		}
		
		//---------------------------------
		// Compare JSONObject to the result
		//---------------------------------			
		if( null != expectedJSONObjectFileName ){
			try {
				String difference = CompareJSONToResult.getDifferenceJSONObject(expectedJSONObjectFileName, actualResult);
				if( null != difference ){
					throw new TestCompareJSonToResultException(expectedJSONObjectFileName, difference );
				}
			} catch ( IOException | JSONException e) {				
				//e.printStackTrace();
				throw new TestCompareJSonToResultFormatException(e.getLocalizedMessage());
			}
		
		//---------------------------------
		// Compare JSONArray to the result
		//---------------------------------	
		}else if( null != expectedJSONArrayFileName ){
			try {
				String difference = CompareJSONToResult.getDifferenceJSONArray(expectedJSONArrayFileName, actualResult);
				if( null != difference ){
					throw new TestCompareJSonToResultException(expectedJSONObjectFileName, difference );
				}
			} catch ( IOException | JSONException e) {				
				//e.printStackTrace();
				throw new TestCompareJSonToResultFormatException(e.getLocalizedMessage());
			}
		}


		return actualResult;
	}
}
