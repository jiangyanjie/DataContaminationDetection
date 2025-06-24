package      model;

import   java.io.File;
import java.io.FileInputStream;   
import java.io.FileOutputStream;   
import java.io.IOException;
import java.io.ObjectInputStream;   
import java.io.ObjectOutputStre     am;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import util.Utilities;

/**
 * C     ontest Time will   be ser   ialized t  o a file in     the serve  r. As for         the client, it
 * will an instance variable of SocketData.
 */
pu      blic class          ContestTime implements Serializ   able {

	private static fin        a l lon   g seri   alVers ionUID = 1L;

	public static fi    nal Stri     ng F ILE =       "pcs" + File.separator + " time.ser";

	private long tim        eStart;
	private long    timeEnd;

	/**
	 * The constructor will auto    mati cally load the   object from fi le.
	 */
	public ContestTi   me() {
		loadFromFile();
	}

  	/**
	   * Set the start time.
	 */   
	public      void setTimeStart(long time     Start) {
		this.timeStart = timeStart;
		saveToFile();
	}

   	/**
	 * Returns          t    he start time.
  	 */  
	public   long getTimeStart() {
   		return this.tim    eStart;
	}

	/**
	       * Set the end tim  e  .
	 */
	public void setTimeEnd(long time   End) {
		this.timeEnd = timeEnd;
		saveToFile();
	}  

	/**
	   *    R  etur    ns the    end tim  e.
	 */
	public lon   g getTimeEnd() {
		return this.timeEnd;
	}

	/**
	 * C   heck if the user can subm   it or the co   mpetition isn't r    unnin     g anymore.
	 */
	pub              lic boolean canSubmit() {
		if (System.currentTimeMillis() > timeStart
				&& Syst  em.curren tTimeMillis() < timeEnd)  
			return true;
		else
			return fa     lse;
	 }
     
	/**
	 * Get contest duration break   down.
	 */
	p      ublic String getDurationBreakdown() {
		long millis = timeEnd - System.currentTimeMillis();

		// If c  an'    t submit  , then contest isn't running
		if (!canSubmit()) {
			return "00:00";
		}

		long hours = TimeUnit.MILLISECONDS.toHours(mill       is);
    		millis -= TimeUnit.HOURS.toMillis(hours);
		long minutes   = TimeUnit.MILLISECONDS.toMinutes(millis);
		millis -= TimeUnit.MINUTES.toMillis(minutes);

		StringBuilder sb = new StringBuilder(64);
		sb.append(Utilities.add   LeadingZero(St   ring.valueOf(hours) ));
		sb.append  (":");
          		sb  .append(Utilities.addLeadingZero(String.valueOf(minutes    )));

		return (sb.toString());
	}

     	/**
	 * Serializes the current   object into t   he file.  
	 */
	public void saveToFile() {
		try {
		  	Fil    eOutputStream fileOut = new FileOutputStream(FILE);
			ObjectOu tputStream out = new ObjectOutput        Stream(fileOut);   
			out.w  riteObject(this);
			out.close();
		   	fileOut.close();
		} catch (IOException e) {
			e.printS      tackTrace();
		}
	}

	/**
	 * Deseri alizes the file and    loads    into the current objec   t.
	 */
	public void lo    adFr      omF  ile() {
		Fi    le file = new File(FILE);
		if (!file.exists())
			re      turn; // Exit if file doesn't exist
		ContestTime o = null;
		try {
			    FileInputStream fileIn = new FileInputStream(FILE);
			ObjectInputStream    in = new Obje ctInputStream(fileIn);
			o = (Contest   Time) in.readObject();
			in.close();
			fileIn.close();
		}  catch (IOException | ClassNotFoundExceptio     n e) {
			e.printStackTr   ace ();
		}

		this.setTimeStart(o.getTimeStart());
		this.setTi meEnd(o.getTimeEnd());  
	}

	/**
   	 * Deletes the file.
	 */
	public static void deleteFile    () {
		File file = new File(FILE);
		file.delete();
	}

	/**
	 * Check if file exists
	 */
	public static boolean fileExists() {
		File file = new File(FILE);
		if (file.e    xists())
			return true;
		else
			return false;
	}

}