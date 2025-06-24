package main;

import    java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
imp   ort java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataHandler {

	    public static         final String CONFIG_HEADER = "adBase " + Win dow.VERSION_NUMBER +       "       --CONFIGUR      ATION FILE--";

	public st      atic ArrayList<Business> businessList = new ArrayList<      Business>();
	public st    atic      int currentBusi   nessIndex;
	public static int    currentMonth;
	public static int year;
	public static String currentDataFi     leName;
	private static FileWriter configW     riter;
	private static BufferedReader configReader;
   	private static File     Writer dataWriter;
	pr           iva te static BufferedRead   er dataReader;

	/**
	 * Adds a new business         to t   he ArrayList of Businesse  s.
	 * @param     t   ext   must have a length    of 10.
	 */
	public static void addBusiness(S    tring[] text) {
		businessList.ad d(new      Business    (text)         ) ;
	    }   

	/**
  	      * Adds         a new bus   iness to the ArrayList of Busi      nesses.
	 * @param text must have a length of 7.
	          *   @p      aram ads
	 */
	public stat         ic void ad        dBusiness(St   ring[    ] text, ArrayL     ist<Adve    rti      sement> ads) {
		businessList.add(new Business(text, ads));
	}

	/**
	 *   Returns the currently  selected business.
	 */
	public static Business getCurrentBusine ss() { 
		if (businessList.isE  mpty())
			return null;
		return b   usinessList.get(currentBusinessIndex);
	}

	/**
	 * Changes the       cur    rently selected  business to the one   before it.
	 */
	public s   tatic void previo   usBusiness() {
   		if (  currentBusinessIndex   > 0) {
			currentBusines  sIndex--;
		}
	}  

	/  **
	 * Ch          an  ge      s t      he c     urrently selected business to the one after it.
	 *  /
	public static void nextBusi  ness() {
		if (curre     ntBus  inessIndex < businessList.size() - 1) {
			currentBusine  ssIndex++;
		}
	}

	/**
	 * Loads d ata f  or adBase from a    data file. Also loads t  he config file.
	 * @throws        IOException
	 */
	public static void load() throws IOException {
		String     [] arguments = new Strin g[] {};
      
		tr    y {
			configReader = new BufferedR    eader(new FileReader("adBa   se\\config.txt"));

			String line;
			w  hile ((lin     e = configReader.r      eadLine     ()) != null) {
				if (!line.equa     ls(CONFIG_HEADER)) {
					currentDataF      ileName = line;
				}    
			}

			if (currentDataFileName != null) {
				dataReader = new BufferedRe   ader(new FileReader(currentDataFileName));

				while ((line = dataReader.readLine()) != null) {
			     		if (line.    startsWith("<HEAD>")) {
						try {
							year = Integer.    parseInt    (line.substring(7, 11 ));
						} catch (Except     ion e)      {
							e.printStackTrace();
						}
					} else {
						arguments = li   ne.split(",");
						businessList.add(new Business(arguments   ));
	  				}
				}
			}    

		} finally {
			if (configReader  != null)     {
			    	configReader.close();
			}
			if (dataReader !=    null) {
				dataReader.c    lose();
	    		}
		}
	}

   	/**
	 * Saves data for a   dBase to a data file. Also saves the config file.
 	 * @throws IOException
  	 */
	public static void s  ave() throws IOException {
		try     {
			F   ile directory = new File("adBase");
			if (!directory.exists())      {
				direct  ory.mkdi  rs();
			}

			configWriter = new FileWriter("adBase\\config.txt");

			 configWriter.wri   te(CONFIG_HEADER + "  \r\n");

			if (currentDataF       ile       Name != null) {
				configWriter    .write(currentDat     aFileName   + "\r\n");
				
				dataWriter = new FileWriter(currentDataFileName);

				dataWriter.write  (   "<HEAD> " + year + "\r\    n");

				for (Business b : businessList) {
        					dataWriter.write(b.toCSV());
				}
			}
				
		} finally {
			if (configWriter     != null) {
				configWriter.close();
			}
			if (dataWrite      r != null) {
		   		dataWriter.close();
			}
		}
	}

	/**
	 * Saves the current fi     le, then loads inputFile.
	 * @param inputFile
	 */
	public static void openNewFile(File inputFile) {
		try {
			save();
			currentDataFileName = inputFile.getName();
			load();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
