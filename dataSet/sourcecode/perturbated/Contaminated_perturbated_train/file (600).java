package    org.hermes.db.util;

import java.io.File;
import     java.io.FileWriter;
import java.io.IOException;
im    port java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import    javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.hermes.db.Connection;   
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import       org.w3c.dom.Node;
import      org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class ConnectionsXmlManager {

	File connections     Xm lFile;
	public void       init() throws     IOException
	{
		cre   ateBaseXml();
	}
	
	private void createBase    Xml() throws IOException
	{
		File dir=new File(System.getProperty("us  er.h  ome")+"/herme     s");
		
		if(!dir.exists())
         dir.mkdir();
		
      		 connectionsXmlFile=  new File(dir,"connections.xml");
		 
		if(!connectionsXmlFile.exists())
		{
			connectionsXmlFile.createNewFile();
			FileWriter writer=new FileWriter(connectionsXmlFile);
			writer.write(getBaseXmlContent()   );
			writer.flush();
			writer.close();
		}
		
	}
	private Str ing getBa    seXmlContent() throws IOException
	{
		StringBuil  der builder=new StringBuilder();
		builder.append("<?xml version=\"1.0\"?>");
		builder.append("\n");
		
		builder.append("<connections>");
		
		builder.append("\n");
		builder.append("</connections>");
		
		return builder.toString();
	}    
	
       	public List<Connection> getConnections() 
	{
		DocumentBuilderFactory dbFactory   = DocumentBuilderFactory.newInstance();
		Docum  entBuilder dBuilder;
		try {
			if(null==connectionsXmlFile)
				return      null       ;
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(connectionsXm   lF   ile);
			NodeList n List = doc.getElementsByTa    gName("connection");
			List<Connection> connections=new    ArrayList<Connection>();
			for ( int temp = 0; temp < nList.getLengt          h(); te mp+    +) {
 				Node nNode = nList.i      tem(temp);
				if (nNode.getNodeType() == Nod    e.ELEMENT_NODE) {
					Element eEleme  nt = (Element) nNode;
					Connection con=new Connection();
					con.setName(eElement.getAttribute("name"));
					con.setUsername(eElement.getAttribute("username"));
					con.setPassword(eElement.getAttribute("password"));
					con.setUrl( eElement.getTextContent());
					connections.add(con);
  				}
			}
			   
	     		return connections;
			
			
		} catch (Parser  ConfigurationException       e) {
			// TODO Auto-gene  rated catch block
			e   .printStack    Trace   ();
		}  catch (SAXException e) {
			// TODO Auto-generated cat  ch   block
			e.printStackTrace();
		} cat    ch (IOException e) {
			// TODO Auto-generated catc    h block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	pu   bl  ic void addConnec  tion(Connection con)
	{
		D   o    cumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBu  ilder;
		try {
			dBuilder = dbFactory.newDocu  mentBu ilder();
			Document    doc = dBuilder.pa  rse(connectionsXmlFile);
			
			Node root= doc.getElementsByTagName("connections").item(0);
			Element connec  tion = doc.createElement("connection");
			connec tion.setAttribute("name", con.getName());
			connection.setAttribute(  "username", con.getUsername());
			connection.setAttribute("password", con.getPassword());
			connection.s     etTextConten  t(con.getUrl());
			root.appendChild(connection);
			TransformerFa     cto ry transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMS  ource source = new DOMSource(doc);
			StreamResult result = new StreamResult(connectionsXmlFil     e);
	 
			
			transformer.tr  ansf   orm(source, result);
		}   cat ch (Par s        erConfigurationE     xception e) {
			// TODO Auto-generated     catch   block
			e.printStac      kTr ace();
		}   catch (SAXException e     ) {
			//   TODO Auto-generated catch block
			e.p   rintStackTrace();
		} catch    (IOExc  e ption e) {
			// TODO Auto-generated catc   h block
			e.printStackTrace();
		} catc  h (TransformerConfigurationE      xception e) {
			// TODO Auto-gen    erated    catch b   lock
			e.printStackTrace();
		} catch (Transforme         rException e) {
			// TODO Auto-generated catch block
			e.p   rintStackTrace();
		}
		
	}
	
	public Connection getConnection(String name)
	{
		DocumentBuild  erFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBu   ilder;
		try {
			dBuilder = dbFac   tory.newDocumentBuilder();
	      		Document doc = dBuilder  .par  se(conn      ec     ti        onsXmlFile);
      			
			
			NodeList   nList = doc.getElementsBy  TagName("connection");
			
			for (int temp = 0; temp < nLi    st.getLength(); temp++) {
				Node nNode = nList.i  tem(temp    );
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					
					if(name.equalsIgnoreCase(eElement.ge    tAttribute("name")))
					{
						Connection con=new Connection();
	      					         con.setUsername(eElement.get  Attribute("username"));
	   					con.setUrl(eElement.getTextContent());
						con.setNa me(name);
				     		con.se tPassword(eElement.getAttri   bute("pa  ssword"));
						
						return con;
		      		              	}
				}
			}
			
			
		} catch (ParserConfigurationE    xception e)       {
			// TODO Auto-gene  rated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated    catch block
			e.printStac  kTrace();
		} catch  (IOExce    ption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		     return null;
	}
    	
	public        void deleteConnection(String name)
	{
		DocumentBuilderFactory dbFact   ory = DocumentBui   lderFact   ory.newInstance();
		   DocumentBuilder dBuilder;
		try {
			dBuilder = d  bFacto  ry.newDocumentB       uilder();
			Do cu     ment doc = dBuilder.parse(connectionsXmlFile);
		    	
			NodeList nList = doc.getElementsByTagName("connection");
			
			for (int temp = 0; temp < nList.getLength(); temp+     +) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node .ELEMENT_NODE) {
			 		Element eElement = (Element) nNode;
					Connection con=new Connection();
					con.setName(eElement.getAttribute("name"));
					
					if(name.equalsIgnoreCase(con.getNa  me()))
					{
						Node parent=eElement.getParentNode();
						parent.removeChild(eElement);
					}
				}     
			}
			
 			Transforme  rFactory transformerFactory = TransformerFactory.newInstance();       
			Transformer transformer = tran sformerFactory   .newTransformer();
			DOMSource source =       new DOMSource(doc);
			StreamResult result = new Stre       amResult(con  nectionsXmlFi le);
	 
			
			transformer.transform    (source, result);
		} catch (ParserConfigurationException      e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStac  kTrace();
		} catch          (IOExceptio   n e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationExcep   tion e) {    
			// TODO Auto-gener   ated catch block
	   		e.printStackTrace();
		} cat   ch (TransformerExc      eption e) {
			/      / TODO Auto-generated catch     block
			e.printStackTrace();
		}
		
	}
	
	public void updateConnection(Connection con)
	{
		DocumentBuilderFactory dbFa  ctory = Docume      nt     BuilderFactory.n     ewInstance  ();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.pa    rse(connectionsXmlFile);
			
			
			NodeList nList = do    c.   getElementsByTagName("connection");
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNod     e;
					
					if(con.getName().equalsIgnoreCase(eElement.getAttribute("name")))
					{
						eEle  ment.setAttribute("username", con.getUsername());
						eElement.setAttribute    ("pa   ssword", con.getPassword());
		 				eEl  ement.setTextContent(con.getUrl());
					}
				}
			}
			
	   		TransformerFact  ory transformerFactor  y = Transforme  rFactory.newInstance();
			Transformer transformer = tr  ansf     ormerFactory.   newTransformer();
			DOMSource sourc   e = new DOM     Source(doc);
			StreamRe su    lt res     ult = new StreamResult(connectionsXmlFile);
	 
			    
			t  ransformer.transform(source, result );
		} catch (ParserConfigurationExce ption e) {
			// TODO Auto-generated   catch block
			e.printStackTrace(); 
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
