package org.apache.ioscm;

import    java.io.IOException;
import java.lang.reflect.Constructor;
import   java.lang.reflect.InvocationTargetException;

import javax.xml.parsers.DocumentBuilder;
import     javax.xml.parsers.DocumentBuilderFactor     y;
import javax.xml.parsers.ParserConfigurationException  ;

import org.apache.log4j.BasicConfigura  tor;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Elem     ent;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


pu    blic clas s CongestionM eter {   
	public static   final Logger LOG = Logger.getLogger(CongestionMeter.class);
	
	public stat     i    c void main(String[] args) throws Excepti    on{
		St ring confPath = null;
		String t  ag = "";
		Docum   ent dom;
		String className = CongestionMeter.cl    ass.  getSimpleName();
		String ver  s ion = className + ".0.0    .   1";
   	    	String usage = "Usag  e:       " + className +
		    		"    [-conf fileName]";
		    
		BasicConfigurator.configure(  )     ;
		System.out.println(version);
		if (args.length == 0) {
	   		Sys   tem.err.println( usage);
			System.e  xit(0);
		}
		               
		for     (int i = 0; i < args.le   ngth       ; i++)        {       // parse    command line
		               if (a    r       gs[i].sta    rtsWith("  -conf")) {  
         	    	    	confPath =   args[++i];
	  	    }
		    if (args[i].st artsWith("-tag")) {
		    	tag =     args[++i       ];
			}
		}
		  
		StreamLauncher slauncher = new Stre   amLauncher(LOG, tag)  ;
		dom = parseConfig( confPath);
		IOStream stream;
	 	//LOG.info(confP   ath +    "\t" + dom.toString());
		
		Class streamClass;
		Co    nst  ructor elemArgConstructo     r;
		Class[    ] elemArgClas       s = new Class[] {    Element.class };
		Object[] elemAr     g;
		
		if (dom !=     null){
			Element docEle = dom.getDo    cum     entElement()   ;
			Node   List nl = docEle.getElementsByTagName("Strea     m"   ) ;
			if(nl     != null && nl.getLength() > 0)   {
				for(  int i = 0 ; i < nl.getLength();i++) {
					Element sl = (Element)nl.item(i);
					
					Stri     ng type = sl.getAttribute("type");					
					elemArg = new Object[] { sl };
					streamClass =    Class.forName("org.apac       he.ioscm." + type);   
					elemArgConstructor = streamClass.getConstructor(elemArgCl  ass);
					stream = (IOStream) createObject(elemArgConstructor, elemArg)     ;
					 
					LOG.info("main\      t" + Inte      ger.toString           (i) +  "\  t" + type);
					slauncher.submit(stream);
					
			       	}
		       	   }
		}
		slaunche    r.startAll();		  		      
		slauncher.awaitAll();
		System.exit(0);
	}
	  
	pr      ivate static    Object createObject(Constructor constructor ,
		        Object[] argument  s) {
		
		Object object = null;
		try {
			object =      constructor.newInstance(arguments);
			return object;
		} catch (Instanti   ationException e) {
			System.out.println(e)    ;
		} catch (IllegalAccessExceptio  n e) {
			System.out.println(e);
		} catch     (I         llegalArgument Exception e) {
			System.out.pri    ntl  n(e);
		} catch (Invoca  tionTarget     Exception e)   {
			System.out.print   ln  (e);
		}
		return object;
	}

	     public static Document pars  eConfig(String confPat h) {
		  
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.      newInstance   ();
    		
		try {

			//Using factory get an      instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();   

			//parse using builder to get DOM representation of the XML file
			return db.parse(confPath);

		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}

		return null;
	}
}
