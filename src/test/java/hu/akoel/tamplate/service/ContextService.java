package hu.akoel.tamplate.service;

import hu.akoel.template.ejb.session.remote.RoleFeatureRightRemote;
import hu.akoel.template.ejb.session.remote.RoleRemote;
import hu.akoel.template.ejb.session.remote.ServiceRemote;
import hu.akoel.template.ejb.session.remote.UserRemote;

import java.util.Properties;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ContextService {

	public static enum EJBTYPE{
		EMBEDDED,
		SERVER
	}

	private static Context ctx = null;
	private static String host = "akoel-T400";
	private static String port = "3700";
	private static String projectName = "templatejee";
	private EJBTYPE ejbType;
	
	private ContextService(){}
	
	public ContextService( EJBTYPE ejbType 	){
		this.ejbType = ejbType;
	}

	private Context getServerContext() throws NamingException {

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
//		//Clear the cache for TEST
//		ServiceRemote serviceRemote = (ServiceRemote) ctx.lookup("java:global/templatejee/ServiceSession!hu.akoel.template.ejb.session.remote.ServiceRemote");
//		serviceRemote.clearCache();
		
		return ctx;	
	}
	
	private Context getEmbeddedContext() throws NamingException {

		if( null == ctx ){
//			System.setProperty("org.omg.CORBA.ORBInitialHost", host);
//			System.setProperty("org.omg.CORBA.ORBInitialPort", port);

			Properties props = new Properties();

//			props.setProperty("java.naming.factory.initial","com.sun.enterprise.naming.SerialInitContextFactory");
//			props.setProperty("java.naming.factory.url.pkgs","com.sun.enterprise.naming");
//			props.setProperty("java.naming.factory.state","com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
			
//			props.setProperty("org.omg.CORBA.ORBInitialHost", host);
//			props.setProperty("org.omg.CORBA.ORBInitialPort", port);

//			props.setProperty(EJBContainer.MODULES, "session/ServiceSession" );
//			props.setProperty("org.glassfish.ejb.embedded.glassfish.installation.root","./src/main/resources");
			
			EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer(props);
			ctx = container.getContext();
		}
		
		return ctx;	
	}

	public void close() throws NamingException{
		ctx.close();
	}
	
	public void clearCache() throws NamingException{
		
		ServiceRemote instance;
		
		//Clear the cache for TEST		
		if( ejbType.equals( EJBTYPE.SERVER ) ){
			instance = (ServiceRemote) ctx.lookup("java:global/templatejee/ServiceSession!hu.akoel.template.ejb.session.remote.ServiceRemote");
		}else{
			instance = (ServiceRemote)ctx.lookup("java:global/classes/ServiceSession!hu.akoel.template.ejb.session.stateless.ServiceSession");
		}
		instance.clearCache();		
	}
	
	public DataSource getDatabase() throws NamingException{
		Object initialContext;
		if( ejbType.equals( EJBTYPE.SERVER ) ){
			initialContext = getServerContext().lookup("jdbc/templatedb");
		}else{
			initialContext = getEmbeddedContext().lookup("jdbc/templatedb");
		}
		DataSource ds = (DataSource)initialContext;
		return ds;
	}
	
	public RoleRemote getRoleSession() throws NamingException{
		if( ejbType.equals( EJBTYPE.SERVER ) ){
			return (RoleRemote) getServerContext().lookup("java:global/" + projectName + "/RoleSession!hu.akoel.template.ejb.session.remote.RoleRemote");
		}else{
			return (RoleRemote) getEmbeddedContext().lookup("java:global/" + projectName + "/RoleSession!hu.akoel.template.ejb.session.stateless.RoleSession");
		}
	}
	
	public UserRemote getUserSession() throws NamingException{
		if( ejbType.equals( EJBTYPE.SERVER ) ){
			return (UserRemote) getServerContext().lookup("java:global/" + projectName + "/UserSession!hu.akoel.template.ejb.session.remote.UserRemote");
		}else{
			return (UserRemote) getServerContext().lookup("java:global/" + projectName + "/UserSession!hu.akoel.template.ejb.session.stateless.UserSession");
		}
	}
	
	public RoleFeatureRightRemote getRoleFeatureRightSession() throws NamingException{
		if( ejbType.equals( EJBTYPE.SERVER ) ){
			return (RoleFeatureRightRemote) getServerContext().lookup("java:global/" + projectName + "/RoleFeatureRightSession!hu.akoel.template.ejb.session.remote.RoleFeatureRightRemote");
		}else{
			return (RoleFeatureRightRemote) getServerContext().lookup("java:global/" + projectName + "/RoleFeatureRightSession!hu.akoel.template.ejb.session.stateless.RoleFeatureRightRemote");			
		}
	}

}
