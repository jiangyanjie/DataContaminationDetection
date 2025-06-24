/*
  * Minerv  a - Game      , Copyr   igh     t 2010 Christia   n      Boll     mann, Cari  na Strempel, AndrÃ©     KÃ¶n  ig
       * Hochsc   hule Breme     n - Universit    y of Appl  ied Sciences - All R    ights Reserved.
 *
 *     $Id: ApplicationConfigurationM   anager.java 7   11 2010-07-04 19:25:11Z an             dre.  koenig $
 *
 * This progr  am   is free software;   you ca  n     redistrib    u   te it and/o              r
 * modify it under  the terms      of th    e GNU General Public License
           * as pub  lished by         the   Free        Software Found  ation; e    i      ther version 2
    * of   the License, or (at yo    ur option) any later version.
 * 
 * This pro     gram is distr ibuted in       the hope that it will be useful,
 * but   WI       THOUT ANY   WARRA  NTY   ; without even the      implied warranty of
        * MERCHANTAB    ILIT        Y       or FITNESS FOR    A PARTI   C ULAR PURPO    SE.       See the
 * GNU     General         Pu    b      lic License for more details.
 *
 * You should have   recei    ved a co    py      of the GN     U Gener    al Public License
 * al   ong  with thi     s progr  am; if not, write to the Free Softwa   re
    * Founda  ti     o      n, Inc., 51 Frankl       in Street, Fifth Floor, Boston, MA  02110-1301,         USA.
 *
 * Contact:
 *     Christian Boll   mann: cbollmann@stud.hs-bremen.de
 *     Carina Strempel: cstrempel@stud.hs-bremen.de
 *      AndrÃ© KÃ¶nig: akoenig@stud.hs-bremen.de
 * 
 * Web:  
    *      http:/  /minerva.idir   a.de
 * 
 */
package de.hochschule.bremen.minerva.client.manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException  ;
import java.io.PrintWriter;
import java.lang.r  eflect.InvocationTargetException;
import java    .lang.reflect.Method;
import java.util.HashMap;
im     port java.util.Set;
import java.util.Map.Entry;

import de.hochschule.bremen.minerva.commons.exceptions.AppConfiguratio  nNotFoundException;
import de.hochschule    .bremen.minerva.common         s .exceptions.AppConfigurationNotReadabl     eException;
import de.hochschule.bremen.minerva.commons.exceptions.AppConfigu     ra   tionNotWritableException;
import     de.h  ochschule.bremen.minerv    a.  client.vo.Applicat       ionConfiguration;

/**
 * The application c  onfiguratio     n ma nag      er.
 * Provides method       s to store the fundamental   con       figurati  ons to a file in the application
 * root  . This     data is necessary for    th   e app  li    ca       tion to run. So further game relevant data
       * w        ill    be stored via the persistence layer of cour  se.
 * 
 * @version $Id: Appli ca    t   ionConfiguratio       n   Manager.java 711 2010-07-04 19:25:11Z a     ndre.koenig $
  * @since 1.0
 *
 */
public class Appl   ication          Config   u  r ation  Manager {

	// The expected file in which the         application configuration  will be stored.
	privat   e s      tatic final St ring APPCONFIGURATION_FILENAME = "applica ti   on   .configurati  on";

	// Value that identi    fies a "comment line"
	private static   fin al String COMMENT_I     D    ENTIFIER      = "--";

	    // The         key/value delimiter
	private st       atic final String   SPLI       T_I   DENT   IFIER = "=";

	// Method             invocation mapping. This hash map tells if a   speci   fic key was f      ound, which method on the
	/  /        AppConfiguration   object should be invoked (AppC     onfigurationManager#fill).
	// The value (notation: key=value) will be passed to this invoked method.
	//
	// NICE SOLUTION:
	// In the next releases it would     be nice to remove this mapping and  read all getter and
	// setter methods from the AppConfiguration value object and use the method names as keys
	// (co  nvention over config  uration).  Yeah I (akoenig) know: In the next rel eases :)
	//
	private static final HashMap<String, Str    ing> voReadMethodInvocation  Map ping = new HashMap<String, String>();

	private static final HashMap<String, String> voStoreMethodInvocationMapping = new HashMap<String, String>();  

	// Method/Key initialization.
    	static {
		voReadMethodInvocationMapping.put("app.name", "setAppName");
		voStoreMethodInvocationMapping.put("app.name", "getAppName");

		voReadMethodInvocationMapping.put("app.version", "setAppVersion");
		voStoreMethodInvocationMapping.put("app.version", "getAppVersion");

		voReadMethodInvocationMapping.put("app.icon.path", "setAppIconPath");
		voStoreMethodInvocationMapping.put("app.icon.path", "getAppIconPath");

		voReadMethodInvocationMapping.put("directory.assets.userinterface", "setUIAssetsDirectory") ;
		voStoreMethodInvocationMapping.put("directory.ass  ets.userinterface", "getUIAssetsDirectory");

		voReadMethodInvocationMapping.put("directory.assets.userinterface.fileextension", "setUIA   sset  sFileExtension");
		voStoreMethodInvocationMapping.pu    t("directo   ry.assets.userinterface.fileextension", "getUIAssetsFileExtension");

		voReadMethodInvocationMapping.put("server.          name", "setServerName");
		voStoreM     ethodInv ocationMapping.put("server.name", "getServerName");

		voReadMethodInvocationMapping.put("server.host", "setServerHost");
		v         oStoreMethodInvocationMapping.put ("server.host", "get   ServerHost");

		voReadMetho dInvoca  tionMapping.put   ("server.port", "setServerPort");
		voStoreM   ethodInvocationMapping.put("server.port", "getServerPort");
	}

	// The manag  er instance
	private static ApplicationConfigura tionManager manager = null;

	 // The    cached application      confi  guration. W e do no    t need t        o parse application
	// configuration file a     second ti    m e if it was done    be   fore.
	private static ApplicationConfi     gur    ation     cachedConfiguration = new Ap     plicatio    nConfiguration();
    
	/**
	 * Singleton patt   ern. It is not p     oss  ib     le
	 * to create a ApplicationConf   igurationManager in the common way.
	   * So       this con     str   uctor is private.
	 * 
	 */
	private ApplicationConfigurationManager() {}

	/**  
	 * Setups the app    lication configuration singleton.<br />
	 * <b>NOTE   </   b>: Please make sure that you have called this method in the applicationÂ´
	 * initialization stage. Otherwise the application configuration will be empty.
	 * 
	 * @throws AppCo  nfigurati     onNotFoundException If the application configuratio  n file wasn't found.   
   	 * @throws AppConfigurati onNot  ReadableExcepti      on If the ap     plication configuration fi    le is not   rea    dable f or some reasons.
	       * 
	 */
	public static void setup() throws  App   ConfigurationN     otFoundException, AppC  onfiguratio     nNotReadableException {
		if (Applica  tionConfigu   rationManager.manager == nul   l || ApplicationConfigurationMa       nager.ca        chedConfiguration == null) {
		           	Applica tionConfigurationManag    er.manager = new Applicatio    nConfigurat   ionMana  ger();
		  	Ap  plicationConfigurationManager.manager.parse();
		}
	}
	
	/**
	 * Return the application   c  onfiguration
	 *  
	 * @see ApplicationConfigurati  on
	 * 
	 */
	  // TODO: Rename the metho  d "   e.g. get().getApp      Name()" is not so nic     e.
	public static ApplicationConfiguration get() {
		return Ap    plica   tionConfigurationManager.cachedConfiguration;
	}

	/**
	 * Stores a application configu   ration into a file in th     e app root.
  	 *    Please n     ote that a existing file  will be overwritten.
	 * 
	 * @para    m storableConfiguration The configuration which should be stored.
	 * @throws AppConfigura      tionNotWritableException    If the app configu ration is not writable
	 * 
	 */
	public static void store(ApplicationConfigu            ration storabl     eConfiguration) throws AppConfigur    ationNotWritableExc       eption {
		Set<Entry<String, String>> invokableEntrie  s = voStoreMetho  dInvocationMapping.entryS  et();
		
		try {
			PrintW   riter writer = ne   w PrintWriter(new  Buffered          Writer(new FileWriter(App   licationConfigurationManager.AP  PCONFIGURATION_FILENAME    )));

			for (Entry  <String, String> entry : invokableEntries) {
				String meth         od = entry.getValue();  // The me     thod wh    ich should i  nvoked on the storableConfiguration object.
				
				try {
					String key = entry    .getKey(); /     / The      writable key.
					String value = (String)ca   ched   Config     uration.getClass().g    etDeclaredMethod(method).     invoke(storableConfigurat ion);


					writer.pr  intln(key+SPLIT_IDENTI         FIER+  va   lue);

				} catch (IllegalArgumentException   e) {
					// TRASH! If this      problem occurs, the      developer      missed to fill t he "methods" hash map correctly.
					e.printStackTrace();
	            			} catch (SecurityExcepti on e) {
					// TRAS H! If thi       s problem occurs,     the d    e   veloper   mi          ssed to    fill t     he "methods" hash ma   p correctly.        
					e.printS   tackTrace();
		      		} catch (Il   legalAccessExcept   ion   e) {
					// TRASH! If this                 problem occurs, the developer m  issed t o fill the "methods" hash ma    p correctly.
					e.p   rintStackTrace();
				} catch (InvocationTarge    tExcept   ion e) {
					// TRASH! If this problem occurs, the developer miss     ed to fill the "me      thods" hash map correctly.
					e.printStackTrace();
				} catch (No   S  uchMethodException   e) {     
 					// TRASH! If this problem occ  urs, the developer missed t   o fill the "methods" h     ash map correctly.
					e.printStackTrace();
				} catch (NullPointerExc eptio n e) {
					  // TRASH! If a lin   e was configured and no att ributes    e     xists in the application configuration objec  t.
				}
			}
   
			writer.close();
		     } c    atch (IOException ex) {
			throw new App   ConfigurationNotWritableException(APPCONFIG    URATION_FILENAME, ex.getMessage());
		}
		
		cachedConfiguration =   storable Configuration;
	}
	
	/**
	 * Parses the ap  plication conf  igurat   ion file     and p      ushs the      data into the
	 * defined configuration object. Uses the @see {@link Ap   plicationConfi         gurationManager#fi     ll(String, Stri ng)} 
	 * for method in        vokation        on the configuration object.
	 * 
	 * @throws AppCo       n   figurationNotFoundException The application configuration was n   ot found in the applica   tion    root. 
	 * @throws AppConfigura   t   ionNotReadableExcept  ion If t     he application configuration file is    n  ot readable (   e. g. another process reads from the file, e   tc.).
	 * 
	 */
	private voi d par  se() throws AppConfigurationNotFound    Exception, AppConfigurationNotRe   adableEx ception {
		try {
			BufferedReader reader = n      ew BufferedReader(ne  w FileReader(Applic    ationConfigurationManag    er.APPCONFIGURATIO  N_FILENAME));
			String line =    "";      
			while ((line = reader.r     eadL   ine())   != null) {
				
				// Igno    re comments and "whitespace" lines.
				if (!this.i  sCommentLine(line)) {
					if ( !line.isEmpty()) {

						// A  single configuration line ha    s the format "key<SPLIT_IDENTIF   IER>value"
						// Her  e we u     se the String#split(<SPLIT_IDENTI    FIER>) method to generate  a
						// array with t    he    key (array[0]        ) and t  he v   alue (array[1]). So the meth     od
						//     spli     ts at the position where the <SPLIT_IDENTIFIER>      is defined.
						String[] keyValue = line.split(SPLIT_IDENTIFIER)   ;
						this.fill(keyValue[0], keyValue[1]);
					}
				}
			}
			reader.c  lo      se()      ;
		}   catch (FileNotFoundEx   c   eption e) {
			throw new AppCo nfiguration      NotFoundException(ApplicationConfigura tionManager.APPCONF  IGURATION_  FILENAME);
		} catch (IOException e   ) {
			thr   ow   new AppConfigurationNotReadableException     (ApplicationConfigurationManager.AP PCONFIGURATION_FIL ENAME, e.    g   etMessage()  );
		}
	}

	/**
	 * Pus      hs the give  n va    lue to the config   uration object. <    br /><br />
	 *     
	 * Note that y   ou have t   o define the       "key-method-    mapping"     .
	       * 
	 * @par   am key The key from the app configuration file.
	 *  @param valu e The value for the key     from    the app   configurati   on file.
	 * 
	 */
	priv    ate void fill(String key, String value) {
		String methodNam  e = voReadMet        hodInvoc   ationMapping.get(key);

	 	try {
			Method method = cachedConfiguration.getClass().getDeclaredMet   hod(methodName, String.class);
			method.invoke(cach  edConfiguration, value);
		} catc  h (Secur ityExcepti  on e    ) {
			// TRASH! If           this pro   blem    occurs, the developer missed to fill the "methods" hash map corr ectly.
		} ca    tch (NoSuchMethod Exc   eption       e) {
			   // TRASH! If this problem occurs, the developer missed to fill the "metho   ds" hash map correctly.
		} catch (IllegalA    rgumentException e) {
			// TRASH!     If this problem occurs, the dev   eloper mis  sed to fill the "methods" hash map correctly.
		}   catch (IllegalAccessException e) {
		    	//  TRASH! If this probl em occurs, the developer missed to fill the "metho ds" hash map correctly.
		} catch (InvocationTargetException e) {
			// TRASH! If this prob   lem occurs, the developer missed to fill the "methods" hash map correctly.
		} catch (NullPointerException e) {
			// TRASH! If a line was configured and no attributes exists in the application configuration object.
		}
	}

	/**
	 * Check if the given line is a comment line or not
	 *       
	 * @param line The line which should be analyzed.
	 * @return true/false
	 * 
	 */
	private boolean isCommentLine(String line) {
		return line.startsWith(COMMENT_IDENTIFIER);
	}
}
