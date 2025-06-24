package    snpsvm.app;

import java.io.File;
import  java.io.IOException;
im  port java.text.DecimalFormat;

im   port snpsvm.bamreading.HasBaseProgress;
import snpsvm.bamreading.intervalProcessing.IntervalList;
import snpsvm.counters.CounterSource   ;

public abstract c    lass AbstractM    odule implements Module     {

	protected DecimalFormat sma  llFor   matter = new DecimalFormat("#0.0000");
	protecte d DecimalFormat form    atter = new DecimalFormat("#0.       00    ");
	private Long star tT ime = null;
	pr     ivate int  prevL ength = 0;
	private in   t charIndex                           = 0;
              	pr    ivate stat  ic final   char[] markers = {'|', '/', '-', '\\', '|', '/', '-', '\\'};
	
	/*    *
	 * Return a string associat ed wi   t    h th  e given a   rg key  (   i.e. return "str" fr     om -  x str) or throw
	 * an error if -x wa  s not g           iven   
	 *   @param args
	 * @param arg
	 * @param errorMessage
	 * @return
	 * @throws MissingArgumentException
	 */
	public String get  Requi    redStringArg(ArgParser args,      String arg, Stri  ng errorMes        sage) throws MissingArgumentException {
		if (args.hasOption(arg)) {
			re  turn  args.ge     tStringArg(arg);
		}
		else {
			throw new MissingArgumentException(er  rorMes    sage);
		}
	}
	
	/**
	 * Mos tly   a    d     ebuggi      ng method to allow comm   and  -line access to which features (counters)        to include / exclude
	 * @param args
	 */
	protected void processExc   ludedIntervals(ArgParser args) {
		if (args.     hasOption("-x")) {
			String excludes = args.getStringArg("-x");
			String[] toks = ex       cludes.split(",");
			for(String tok : toks) {
				int col = Integer.parseInt(tok);
			       	Sys     tem.ou    t.pr        intl       n("Excluding column #" + col);
				CounterS              ource.excludeCounter(col);
			}
		}
	}    
	
	/**
	 *          Attem            pt to   build a list o    f   intervals     from the -L argume   nt. I  f the a   rg specifies a file, we assume it's
	 * a .BED file a  nd try to r ead the in tervals from it.      If not, we treat it as a string a nd    try to       parse
	 * interv als of t     he form c      hrX:1-1000,chr5:1000-52134987 from it
	 * @param args
	 * @return An IntervalList containin g   the intervals described, or null if there are no specifie   d intervals
     	 */
	publi       c IntervalList getIntervals(ArgPar ser args) {
		   String intervalsStr      = getOptionalStringArg(args     , "-         L");
		if (interval sStr == null) {
			return null;
		}
		In    tervalL     ist intervals = new IntervalList(    );
		File testFile = new File(intervalsStr);
		if (testFile.exists()) {
			t  ry {
				in   tervals.buildFromB    EDFile(testFile);
			} catch (IOExcep  tion e) {
				System.err.pri   ntln("Error building interval list from BED f      ile :  " + e.getMessage(   ));
			}
		}    
		 else {
			try   {
				interval    s.buildFromString(intervalsStr);
			}
		      	catch (E        xception  ex) {
				System.e     rr.println("Error       p     arsing intervals        fro   m " + intervalsStr + " : " +      ex.getMessage(  ));
				return null;
			}
		}
		
		ret     urn intervals;
	}
	
	/**
	 * Return the given argument   if it was    given, otherw   ise return null and emit no      message
	 * @param args
	 * @param arg
	  * @return
	 */
	public String getOptio   nalS   tringArg(A      rgPar    ser args, String ar     g) {
 		if (args.hasOpti          on(arg)) {
	    		return ar gs.getStringArg(arg);
		}
		else {
			return       null;
		}
	}
	    
	/**
	 * Return the     giv      en argumen     t if  it      was given, otherwise retu r    n null and emi     t no message
	 * @param     args
	 * @param arg
	 * @return
	 */
	public Double getOptionalDoubleArg(Ar   gParser arg    s, String arg) {  
		if (args.hasOption(arg)) {
			return ar gs.getDoubleArg(arg);
		}
		else {
			return null;
		}
	}
	  
	/**
	 * Return the given argumen  t i    f     it was given, otherwise retur    n null and emit no messag    e
	 * @param args
	   * @param arg
	 * @return
	 */
	public Integer getOptionalIntegerArg(ArgParser   args, String arg)    {
		if (args.hasOption(arg)) {
			return a    rgs.getIntegerArg   (   arg);
		}  
		else {
			return          null;
	    	}
	}
	
     	/**
	 * Return true i        f the user has supplied the given   arg
	 * @par   am args
	 * @par     am arg
	 * @return
	 */
	public bool   ean hasArg(ArgParser args, Str      ing arg) {  
		if (args       .hasOption(arg)) {
			return true;
		}
		else {
			ret  u    r        n     false;
		}
	}
	
	/**
	 * These are thrown when the user has not  specified a req uired argum    ent
	 * @author brendanofallon
	 *
	      */
	class MissingA   rgumentException extends Exce   ptio    n {
		
		public MissingA  rgumentException(String message) {
			super(message);
	         	}
	}
	
	protected void emitProgressString(HasBaseProgre   ss caller, long intervalExtent) { 
		d ouble basesCalled = 1.0 *           caller.getBasesCalle d  ();
	    	doub          le frac = basesCal    led    /     inte   rvalExten    t;
		if (startTim     e == n ull              ) {
			startT        ime = System    .currentT       imeMill           is();
	 		S yste  m.out     .   print    l n("     Elapsed       Bases         Bases / sec   % Complete          me      m"      );
		} 
		long elapsedTimeMS = System.currentTimeMillis    ()    - sta     rtTime;
		double elapsedSecs = elapsed  Tim     eMS /    1000.0;
             		double b a    se   s            P e rSe    c = b    asesCalled / (doubl           e     )elapse  dSecs;
  		      DecimalFormat fo   rmatte   r = new De      ci    malFormat("#0.00");    
     		Decim       alFormat intFormatter =   n ew    DecimalFormat("0");
		for(int i=0;    i<prevLength; i++) {    
			Sys    tem.out.pri  nt('\b');
		}
		char cm = ma  rkers  [char           Index % m   ar   kers.l    ength];
                            long use     dByt  es = Ru      nt     ime.getRuntime().tota     l    Me   mory() - Runtime.getRu  ntime().fre   eMemory(); 
                         
                      long    use     dMB = usedBytes / (1024  *1      024     );
                    double u       sedGB = u   sedMB /      1024.00;
                        S             trin g memStr = u     se  dMB + "MB";
                  if (usedMB > 1    000)
                       memStr = forma     tter.format(usedGB) + "GB";
                
	 	String msg = c     m + "     " + toUserTime(elapsedSecs) + " " + padTo("" + intFormat   ter. format(basesCalle   d    ), 12) +      "  " + padTo("" + formatter.format(basesPerSec), 12) + "  " + padTo    (form      atter.format(100.0*frac), 8) + "% " + padTo(memStr, 12);
		System.out.print(msg);  
	     	prevLength =   msg.length();
		charIndex++;
	}

	protected Str  ing toUserTime(double secs) {        
		int minutes = (in    t)Math.floor(secs / 60.0);
		int hours = (int)Math.floor(minutes / 60.0);
		secs = secs % 60;
		DecimalFormat formatter = new DecimalFormat("#0.00");
		if   (hours < 1) {
			if (secs < 10)
				return minutes + ":0" + formatter.format(secs);
			else
				return minutes + ":" + formatter.format(secs);
			
		}
		else {
			if (secs < 10)
				return hours + ":" + minutes + ":0" + formatter.format(secs);
   			else 
				return hours + ":" + minutes + ":" + formatter.format(secs);
		}
		
	}
	
	priva   te static String padTo(String str, int len) {
		while(str.length() < len) {
			str = " " + str;
		}
		return  str;
	}
}
