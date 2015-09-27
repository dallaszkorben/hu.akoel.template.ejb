package hu.akoel.template.ejb.test;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import liquibase.database.Database;

public class CompareXMLToDB {
	
	public static String getDifference( String expectedSetXML, Connection conn, Database dbToCompare) throws ParserConfigurationException, SAXException, IOException, SQLException{
				
		File xmlFile = new File(expectedSetXML);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlFile);
				
		doc.getDocumentElement().normalize();

		NodeList dataSetList = doc.getElementsByTagName("dataSet");
		
		//Through the elements <dataSet> Generally only ONE
		for (int i = 0; i < dataSetList.getLength(); i++) {
						
			Node dataSetNode = dataSetList.item(i);
			
			NodeList tableNodeList = dataSetNode.getChildNodes();
			
			for( int j = 0; j < tableNodeList.getLength(); j++){
			
				Node tableNode = tableNodeList.item( j );
			
				if (tableNode.getNodeType() == Node.ELEMENT_NODE) {
				
					Element tableElement = (Element) tableNode;
					StringBuilder tableRow = new StringBuilder("<");
				
					String tableName = tableElement.getNodeName();
					tableRow.append( tableName );
					tableRow.append( " ");
					
					NamedNodeMap attributeMap = tableElement.getAttributes();
					int numAttrs = attributeMap.getLength();					
					StringBuilder queryString = new StringBuilder("SELECT count(*) FROM ");
					queryString.append( tableName );
					queryString.append( " WHERE " );
					
					for (int k = 0; k < numAttrs; k++) {
						Attr attr = (Attr) attributeMap.item(k);
						
						if( k != 0 ){
							queryString.append( " AND ");
						}
						
						String fieldName = attr.getNodeName();
						String fieldValue = attr.getNodeValue();
						
						tableRow.append( fieldName );
						tableRow.append( "='");
						tableRow.append( fieldValue );
						tableRow.append( "' ");
												
						queryString.append( fieldName );
						queryString.append( "='");
						queryString.append( fieldValue );
						queryString.append( "'");
						
					}					
					
					tableRow.append( "/>");
					
					Statement stm = conn.createStatement();
					ResultSet rs = stm.executeQuery( queryString.toString() );
					rs.next();
					int rows = rs.getInt( 1 );
					if( 1 != rows ){
						return tableRow.toString();
					}
				}				
			}			
		}
	
		return null;
	}
}
