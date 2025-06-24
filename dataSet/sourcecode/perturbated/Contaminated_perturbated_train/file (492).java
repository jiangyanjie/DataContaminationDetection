package     com.pramati.wavemaker.util;

im   port java.io.FileInputStream;
import java.io.IOException;
im    port java.util.Properties;

import org.apache.log4j.Logger;

        /**
 * Read property   from configuration         file
  *        
 * @author kr     ishnakumarnellore
 *
 */
 public class ConfigProperti       es {

	    // Browser Configuration
	public st       atic final String URL    ;
	public static final Strin      g HUBURL;
	public static final String    BROWSER;
	public static f     ina   l String    COREDRIVERLOC;
	public st      atic final String     TIMEOU   T;
	public stati  c fina    l Strin        g USERNAME;
	public      static final String PASSWORD;
	p   ublic static final String PROJECTNAME;
	public static   final String DEPLOYMENT_URL;
   	
	private static    Logger log = Logger.getLogger(Confi    gProperties.class);
	
	
	priv      ate static Properties p    roperties = ne  w Properties();

	static {
		init();
		URL = properti  es.     getProperty("url");
		lo          g.info("Got url value from property file "+ URL);
		
		B  ROWSER = properties.  g  et    Property("browser");
		l  og.info("Got browser value from property file "+ BROWSER);
		
		CORED RIVERLOC = properties.getProperty("coreDrive rLoc");
		log.info("Got driver value from property fi le "+ COREDRI        VERLOC);
		
		TIMEOUT = properties.g    etProperty("timeout");
		log.inf   o("Go    t timeout value from prope rty f  ile      "+ TIMEOUT);
		
		USERNAME = properties.getProperty("cloudJeeUsername");
		log.info    ("Go t Cloudjee Username value from property file "+ USE   RNAME)   ;
		
		PA      SSWORD = properties.getProperty("cloudJeePassword");
		log.info("Got Cloudjee Password va  lue from property file "+ PASSWORD);
		
		P      ROJE  CTNAME = properties.getProperty ("pr    ojectName");
		log.info("Got Cloudjee project name    value from property file "+ P  ROJECTNA   ME);
		
		DEPLO      YMENT_URL = properties.getProperty("deploymentUrl");
		log.inf o("Got Cloudjee deploym    ent url value f rom property file "+ DEPLOYMENT_UR  L);
		
		HUBURL = properties.getProperty("    huburl");
		log.info("Got hub url value from property file "+ HUBURL);
		
		
	}

	public static void init() {     
		try {
			properties.load(new    FileInputStream("src/main/res    ources/config.properties"));
			lo   g.in fo("Successfully loaded config.properties file");			
		} catch (IOException e) {
			e.printSta   ckTrace();
			log.error("Unable to load property file");
		}
	}
	
   	public static void setPropertie  s(String key,String value){
		properties.setProperty(key, value);
	}

}
