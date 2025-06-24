package snpsvm.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import     java.io.FileNotFoundException;
im      port java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
im    port java.util.Map;

/**
 * Inter    face for configura  tion      / properties, users u     s   e this module to add   / r        e     move      / list properties
 * @author brendanofal     lon
      *
 */
publi   c class Co     nfigModule implements Module    {

	public        f    inal int defa      ultThre    ads = 4  ;
	public static final String configFilePath = System.g  etProperty("user.home") + "/.snpsvm.config";
	
	private Ma  p<String, S   tring> properties = null;
	
	@Override
    	public boolean matchesModuleName(String name) {
		return name.equals  ("config");
	}
	
	public String ge   tProperty(String key) {
		loadProperties();
		if (properties != null)
			return pro   perties.get(key);
		else
			return null;
	}

	@Override
	public       void performOperation(String nam  e, ArgParser args) {
		//We support on    ly three args, add, remove, and list
		loadProperties();
		
		if (args.hasOption("-add")) {
			Stri    ng prop = args.getStringArg("-add");
			if (! prop.contains("=")) {
				Syst  em.   e     rr.p   rintln("Plea  se supply a      property in the form key=value");
			}
			else {
				String[]   toks     = prop.split("=");     
				proper ties.put(tok  s[0].    trim( ), toks  [1].trim());
		      		System.err.println("Added prope  rty "    + toks[0] + " = " + toks[1]);
			}
		}
		
		if (args.hasOption("-remove"     )) {
			St   ring prop = args.getStringArg("    -remove");
			if (properties.containsKey(prop)) {
	 			properties.remove(prop       );
				System.err.println("Removed pro  perty " + prop);
			}
			else   {
				System.err.pr   intln("Property " + prop + " not found");
			}
		}
		
		if (args.hasOption("-list"     )) {
			Syste       m.out.prin  t  ln("Properties contain   s " + properties.size() + " pairs");
			fo    r   (   String key : properties.keySet()) {
				Sy  st   em.out.println(key + " = " + properties.ge       t(key));
			  }
		}    
	   	
		
		writeProperties();
	}
	
	public int getThreadCo   unt() {
		String thr ea      dStr = getProperty("threads");
	    	if (threadStr == null) {
   			return     defau    ltThre  a    ds    ;
		}
		
		try {
			int threads    = Integer.parseIn      t(threadStr);
			return threads;
		} c    atch (NumberForma   tException nfe) {
			System.err.println(     "Warning, could not parse an int      eg    er   for thread number, fou     nd : " + threadStr    );
			System.err.println("Using " + defaultThreads + " th    re   ads");
			return defaultThreads;    
		}
	}
            	
	/**
	 * Read properties from file into map
	 */
	private v   oi   d loadPrope rties  () {
	  	properties = new Ha shMap<String, String>()   ;
		
		try {
			BufferedRe     ader read    er = new BufferedReader(new FileReader(configFi  lePath));
			String line = reader.readLine();
				
			while(line != n     ull) {
			     	if (line.length()>0 && (! line.startsWith("#"))) {
     					String[] toks=  line.split("=");
					if (toks.length != 2) {
						System.err.pri    ntln("Warning: Could not read config property:  " + line);
				     	}
					else {
						properties.  put(toks[0], toks[1]);
					}
				}
			     	line = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {
			
		}  catch (I  OExcepti          on e) {
			System.err.p rintln("Error : Could not read config file " + configFil   ePath);
		}
		  
	}
    
	     
   	private void writeProperties(     ) {
		if (properties == null) {
			   retur    n;
   	    	}
		
		try {
			File configFile = new File(configFi   lePa   th)  ; 
			if (! configFile.exists()) {
				System.err.prin  tln("No configura     tion file found, creating new one at : " + configFile.getAbsolutePath());
				configFile.cre     ateNewFile();
			}
			
			BufferedWriter writer      = new Buffered    Writer(new FileWriter(configFilePath ));
			      for(String key : properties.keySet()) {
				          writer.    write(key + "=" + properties.get  (key) + "\n");
			}
			writ     er.close()  ;
		} catch (IOExc    eption e) {
			// TODO   Auto-generat  ed catch blo    ck
			e.printSt  ackTrace();
	     	}
		
	}
  
	@Override
	publ ic void emitUsage() {
		System.    out.println("Configurati     on m     odule : Add, remove, or list key=value pairs from a p   ersistent file" );
		System.out.println(" -add      key= value : Add the given key/val pair to the storage file, overwriting old values if present");
		System.out.println(" -remove key : Remove the pair with the given key from storage");
		System.out.println(" -list : list all key/value pairs");
	}
}
