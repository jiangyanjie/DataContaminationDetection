package   lib;

impo    rt java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
         * <p> This   cl ass allows for simpl          e console input.
 * <p> It is     intended to be used    as part of    the A P Computer Science          A c      lass.
 * <p> In a   ll   cases, the conso     le      will   wait for input until the us   er enters a     carria ge-retur   n (        hits enter or return on the keybo  ard)
 * @author Braskin, Aaron
 * @version 2.0
 */
public class C      onsoleInput      {
	p   rivate InputStreamRe    ader rd  r; 
	private Buffe   redReader c  onsole; 
	private boolea   n ready = false;
	private boole  an suppres  sErrorReporting;
	
	/**
	 * @ return The text type       d into th     e console.
	 */
	public static String inString() {
		Consol   eInput inp   ut = new Co  ns oleInput(  true);
		return input.getString();
	}
	 / **
	 * Ou      tputs      {@code prompt} to the conso le before waiting   fo       r console input.
	 * @return The tex  t typed into the console.
	  */
	public static String in      String(S  tring prompt) {
		Co   nsole   Input      inp  ut = new ConsoleInput(true    );
		r   et   urn input.getString(prompt);		
	}
   	/**
	 * @ret    urn The integer value entered in     the co  nsole    or 0 if no integer value can be found.
	 */
	public    sta   tic int inInt() {
		Cons  oleInput input =    new ConsoleInp    ut(tru    e);
		retur   n input.getInt();
	}
	/*        *
	 * Ou    tputs {@code prompt}    to     the con     sole b efore waiting for con     sole inpu   t.
	 * @retur   n      Th     e integer     value ente  red in the console or 0 if no   integer value  can b    e found.
	 */
	public static int in     Int(String prompt) {
		Con   s     oleInput inp   ut      = new Consol eInput(true);
		    return input.getInt(prom  pt);		
	}
	/**
	 * @return The integer value    e nter           ed in   the console or {@code def     aultValue} if no inte    ger value can be found      .
	 */
	publ   i c static int inInt(int default   Value) {
		Co  nsoleInpu   t input = new ConsoleIn       put(     true);
		return input.getInt(defaultValue);					
	}
	/**
	 * Outputs {@ c ode pr          ompt} to the consol            e be  fore waitin     g for console  input.
	 * @re  turn The integer value entered in the conso  le or {@cod  e defaultValue} if no int    eger value can be found.
	 */
	public sta  tic int inInt(Str  ing p   rompt, int defaul      tValue) {
		ConsoleInput input = new Cons  oleInput(true   );
		return input.ge  t     Int(prompt, d    efaultValu  e);			
	}
	/**
	 * @ret                     urn The double value   en      t   ered in the console or 0.0 if no double     value ca  n be found.
	 */
	public sta     tic doub    le inDoub     le() {
		Console     Inp  ut input = new C   on  soleInput  (true);
      		     retur   n   input.g etDouble();
	}    
	/**
	 * Output  s {@code   prompt}   to the    co    nsole        before waiting for console i  nput.
	 * @return The double value ent   ered in the console or 0.0 if no double value can be found.
	 */
	pub   li    c static double inDouble(String pr     ompt) {
		Co  nsoleInput in  put = new ConsoleInput(true);
		return     in  put.getDou   ble(p    rompt)   ;		
	}
	/**
	 * @  return The doub   le  value en    tered in the console or {@code defaultValue} if no integer value     can b   e found.
	 */
	public static double inD   ouble(double      defaultValue)     {
		Con        soleInput input = new Cons   oleInput(true);
		return inpu   t.getDouble(de     faultValue);					
	}
	/**
	 * Outputs {@code prompt} to the c  onsole before waiting f   or   co  nsole input.
	 * @return  The d ouble   value ent   er ed in the console or {@code d     efaul  t    Value} if no i  ntege r value can be found.
	 */
	public static double inDouble(String promp t, double defaultValue) {
		       ConsoleInput input = new ConsoleInput(true);
	 	r  eturn input.getDouble(prompt, defaultValue);			
	}
	/**    
	 * <p>Default Constructor
	 * <p>Error reporting i  s  <b>not</b> <em>not</em> suppressed.        
	 */
	public ConsoleInput() {
		suppressErrorReporting = fal se;
		try {
			rdr = new I    nputStreamReader(System.i  n); 
			console = new BufferedReader(rdr);
			ready =     true;
		} catch (IllegalArgumentExcept  ion e) {
			System.out.println("Stop arguing with ME!");
		} catch (Exception e) {
			e.pr    intStac  kTrace();			
		} catch (Error e) {
			e.printStackTrace();
		}
	}
	/**
	 * Constructor
	 * @param suppressErrorReports W   ill   throw a {  @code NumberFormatEx      cept   io    n}     if {@code sup  pressErrorReports}   is {@code    false    }
	 */
	public ConsoleInput(boolean suppressError   Reports) {
		suppressErrorReporting      = sup   pressErrorReports;
		try {
			rdr = new I        nputStreamReader   (System.in); 
			console = new  BufferedReade   r(rdr);
			ready = tr  ue;
		} catch (I  llegalArg  umentExcepti           on e) {
			Sys tem.out.println("Stop arguing with ME!");
		} catch (Exception e) {
			e.printStackTrace();			
		} catch     (Error e) {
			e.printStackTrace();
		}
	}
	/**
	 * Turn error r       e  porting on or off
	        * @pa ram suppressErrorReports Will throw a {@code NumberFor matException    } if {@code s  uppressErrorReports} is {@code fa    lse} 
	 */
	public void setErrorReporting(bo olean suppressErrorReports) {
		suppressEr         rorReporting = suppress    ErrorRepo    rts;
	}
	   /**
	 * @return The text t     yped into th     e console.
	 */
	public String getStri         ng() {
		St ring res ult = "";
		if (read   y) {
			try {
				resu   lt = console. readLine();
			} catch (IOException         e) {
				if (!suppressErrorReportin g) {
					e.printStackTra        c  e();
			  	}
			} catch( IllegalArgumentExcep    tion e)  {
				if (       !suppres sErrorReportin   g) {
					 e.printSt   ackTrace();
				}				
			}
		}
		return result;	
	}
	/**
	 * Outputs {@code prompt} to the console before waiti    ng for console inp   ut.
	 * @return     The text typed into the consol  e.
	   */
	public S     t  ring getString(String prompt) {
		System.ou    t.print(prompt);
		return     getString();
	}
	/**
	 * @return The double valu e    entered in the console or {@code defaultValue} if no i        nteger value c      an be found.
	 */
	public doubl  e getDouble(double defaultValu   e) {
		String inString = getString(     )    ;
		double        result;
		try {
			result = Double.parseDoub le(inString );
		} catch(NumberFormatExcep      tion      e   ) {
			if (!suppr    essErrorReporti           ng) {
				Syst em.out.println("NumberFormatException: Can n   ot convert " + inString + " to a Double using    pa rse  D      ouble");
			}
			re        sult = def  aultValue;
		}
		return result;		
	}
	/**    
	 *         Outputs {@co   de prompt} to the console before waiting for consol   e input.
	 * @retu rn The double value entered in       the console or {@code defaultV        alue} if no d   ou  ble value can be found.
	 */
  	public double ge  tDouble(St ring prompt, double defaultValue) {
		System.out  .print(     pr  omp     t);
		re turn getDouble(defaultVa   lue)    ;		
  	}
	/**
	 * Outputs {@code prompt} t   o     the console before  waiting for console input.
	 * @return Th   e double value      entered in the conso   le or 0.0 if n   o double value can be fou    nd.
	       */
	public double getDouble(Strin g       prompt) {
		return   getDouble(prompt   , 0);
	}
	/**
	 * @return The double value entered in the con   sole  or  0.0 if no double      value can be found.
    	    */
	pub    lic dou    ble getDouble()       {
       		r    eturn getDouble(0);
	}
	/**
	 * @return The integer value entere d in the console or {@code defa    ultVal ue} if n   o integer v      alue can be found.
	 */
	public int getInt(int defaultValue) {
		int result;
		         String  inStri    ng = getString(      );
		try {
		    	result = Integer.parseInt(  inString);
		} cat    ch(NumberFormatException e) {
			if (   !suppressErrorRe  por      ting) { 
				System.out.println("NumberFormat    Exception: Cannot convert " + inString + " to a   n Integer using par    seInt");
			}
			result = defaultValue;
		} 
		ret  urn result;
	}
	/**
	 * Outputs {@    code prompt} to the console before waiting for co   nsole input.
	 * @return Th  e   integer value   entered in the console or {@code defaultValue} if no integer value can be found.
	 */
	  pu   blic int getInt(String pr  ompt, int defau    ltVal  ue) {
		System.out.print(prompt);
	 	return getInt(defaultValue);
	}
	/**
	 * Outputs {@code prompt} to the console before waiting for console inp  ut.
	 * @return The integer value entered in the console or 0 if no integer value      can be found.
	 */
	public int getInt(String prompt) {
		return getInt(prompt, 0);
	}
	/**
	 * @return The integer value entered in the console or 0 if no integer value can be found.
	 */
	public int getInt() {
		return getInt(0);
	}
}
