package hu.akoel.template.ejb.services;

import hu.akoel.template.ejb.session.remote.RoleFeatureRightRemote;
import hu.akoel.template.ejb.session.remote.RoleRemote;
import hu.akoel.template.ejb.session.remote.ServiceRemote;
import hu.akoel.template.ejb.session.remote.UserRemote;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class InitialContextService {

	private static InitialContext ctx = null;
	private static String host = "akoel-T400";
	private static String port = "3700";

	private static InitialContext getInitialContext() throws NamingException {

		if( null == ctx ){
			System.setProperty("org.omg.CORBA.ORBInitialHost", host);
			System.setProperty("org.omg.CORBA.ORBInitialPort", port);

			Properties props = new Properties();

			props.setProperty("java.naming.factory.initial","com.sun.enterprise.naming.SerialInitContextFactory");
			props.setProperty("java.naming.factory.url.pkgs","com.sun.enterprise.naming");
			props.setProperty("java.naming.factory.state","com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
			
			props.setProperty("org.omg.CORBA.ORBInitialHost", host);
			props.setProperty("org.omg.CORBA.ORBInitialPort", port);

			ctx = new InitialContext(props);

		}
		//Clear the cache for TEST
		ServiceRemote serviceRemote = (ServiceRemote) ctx.lookup("java:global/templatejee/ServiceSession!hu.akoel.template.ejb.session.remote.ServiceRemote");
		serviceRemote.clearCache();
		
		return ctx;	
	}

	public static DataSource getDatabase() throws NamingException{
		DataSource ds = (DataSource)getInitialContext().lookup("jdbc/templatedb");
		return ds;
	}
	
	public static RoleRemote getRoleSession() throws NamingException{
		return (RoleRemote) getInitialContext().lookup("java:global/templatejee/RoleSession!hu.akoel.template.ejb.session.remote.RoleRemote");
	}
	
	public static UserRemote getUserSession() throws NamingException{
		return (UserRemote) getInitialContext().lookup("java:global/templatejee/UserSession!hu.akoel.template.ejb.session.remote.UserRemote");
	}
	
	public static RoleFeatureRightRemote getRoleFeatureRightSession() throws NamingException{
		return (RoleFeatureRightRemote) getInitialContext().lookup("java:global/templatejee/RoleFeatureRightSession!hu.akoel.template.ejb.session.remote.RoleFeatureRightRemote");
	}

}
