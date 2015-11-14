package hu.akoel.template.ejb.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.FileSystemResourceAccessor;

public class BaseDataSet {

	public void dbConnect(String db_connect_string, String db_userid, String db_password) {
		Liquibase liquibase = null;
		Connection conn = null;
		String baseDataSetXml = "basedataset.xml";
		Database database = null;
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(db_connect_string, db_userid, db_password);
			database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(conn));
			System.out.println("connected");
			Statement statement = conn.createStatement();
			String queryString = "select * from sysobjects where type='u'";
			ResultSet rs = statement.executeQuery(queryString);
			while (rs.next()) {
				System.out.println(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {					
			liquibase = new Liquibase(baseDataSetXml, new FileSystemResourceAccessor(), database);
			
		} catch (LiquibaseException e) {
			e.printStackTrace();
			throw new Error(e);
		}
	}

	public static void main(String[] args) {
		BaseDataSet connServer = new BaseDataSet();
		connServer.dbConnect("jdbc:sqlserver://<hostname>", "<user>", "<password>");
	}
}
