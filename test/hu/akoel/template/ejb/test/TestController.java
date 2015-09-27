package hu.akoel.template.ejb.test;

import hu.akoel.template.ejb.exceptions.EJBeanException;
import hu.akoel.template.ejb.services.InitialContextService;
import hu.akoel.template.ejb.test.annotation.InputSet;
import hu.akoel.template.ejb.test.exception.TestDBCompareException;
import hu.akoel.template.ejb.test.exception.TestDBCompareXMLFormatException;
import hu.akoel.template.ejb.test.exception.TestException;
import hu.akoel.template.ejb.test.exception.TestNotExpectedException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

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

import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.xml.sax.SAXException;

public class TestController {
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
			e.printStackTrace();
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

	public void doSession( Object obj, String methodName, Object[] parameterList, String compareSet) throws TestException{
		doSession(obj, methodName, parameterList, compareSet, null);
	}
	
	public void doSession( Object obj, String methodName, Object[] parameterList) throws TestException{
		doSession(obj, methodName, parameterList, null, null);
	}
	
	public void doSession( Object obj, String methodName, Object[] parameterList, Class<? extends EJBeanException> expectedException ) throws TestException{
		doSession(obj, methodName, parameterList, null, expectedException);
	}
	
	public void doSession( Object obj, String methodName, Object[] parameterList, String compareSet, Class<? extends EJBeanException> expectedException ) throws TestException{

//		Class<?>[] parameterClassList = Arrays.copyOf(parameterList, parameterList.length, Class[].class );

		Class<?>[] parameterClassList = new Class<?>[ parameterList.length ];
		for( int i = 0; i < parameterList.length; i++ ){
			parameterClassList[i] = parameterList[i].getClass();
		}
		
		Method method = null;
		try {
			method = obj.getClass().getMethod(methodName, parameterClassList );			
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			throw new Error(e);
		}

		try {
			  method.invoke(obj, parameterList);
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
		// Compare the DB
		//--------------------
		if( null != compareSet ){
			try {
				String difference = CompareXMLToDB.getDifference(compareSet, conn, database);
				if( null != difference ){
					throw new TestDBCompareException(difference );
				}
			} catch (ParserConfigurationException |SAXException |IOException | SQLException e) {				
				//e.printStackTrace();
				throw new TestDBCompareXMLFormatException(e.getLocalizedMessage());
			}
		}
	}


}
