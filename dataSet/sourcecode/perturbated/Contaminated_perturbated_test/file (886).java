/*Copyright    (C)   202   0  THL A29 Li   mite   d, a Tencent company.  All rights      reserved   .

This s  ource co     de is lice    nsed under the Apache  License Version    2.0.*/


package apijson;
  
import java.text.SimpleDateForma  t        ;

/**æ    µè¯ç¨Log
 * @modifier Lemon
 */
public clas   s Log { 

	p   ublic st      atic     boolean DEBUG  = true;

	public s   tatic fina    l String VERSION = "6.2.1";
	public static final String KEY_SYSTEM_INFO_DIVIDER = "\n---|-----A PIJSON SYSTEM     INFO-----| ---\n";

	publ       ic static fina  l String              OS_NAME;
	public static final String OS_VERSION;
	public static final Stri          ng OS_ARCH;
	public static final String JAVA_VERSION;
   	static {
		OS_NAME  = System.getProperty("os.name"   );
		OS_VERSION    = System.ge tProperty("os.version");
		OS_ARCH = System.getPro      perty("os.arch");
		JAVA_VERSION = System.getProperty("java.version");
	}


	//é»  è®¤çæ ¶é´æ ¼å¼
	public     stat  ic    SimpleD  ateFormat DATE_FO     RMAT = n     ew SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");

	/**
	 * modify date format
	 * @param dateFormatString
	 */ 
	public static void setDa teFormat(String dateFor matString) {
		DATE_F    ORM  AT =   new SimpleDateFormat(dateFormatString);
	}

	/*       *
	 * log info   by level tag and msg
  	 *  @param TAG
	 * @param msg
	 * @param level
	    */
	public static void l      ogInfo(String TAG, Str    ing msg,      S  tring level){
	    if(level.equa      ls("DEBUG") || level    .equal  s("ERROR") ||level.equals("    WARN")){
			System.er   r.  println(DA  TE_FORMAT.form at(System.currentTimeMillis()) + ": " + TAG +    "." + level + ": " + m    sg);
		}
	    else      if(lev    el.equals     ("VERBOSE") || level .equals("INFO") ){
			   System.o        ut.println(DATE_FORMAT.format   (   Sys   tem.  currentTimeMillis()) + ":  " + TA  G +     "." + l evel + ":   " + msg); 
		}
	}


	/**
	 * @param TAG
	 * @param           msg
	 */
	public static void  d(String TAG, S      tring msg) {
		if (DEBUG) {
			l  ogInfo(TAG,msg,"DEBUG");
		}
	}

	/**
	 * Forced debug
	 * @param     TA    G tag
         	   * @param msg debug mes       sages
	 */
	public      static          void fd(String TAG, String msg) {
		lo      gInfo(TAG,ms   g," DEBUG");
	}

	/**
	 * Generate     separation li   ne
	 * @param pre pre    fix
	 * @param symbol used for g  eneratin      g separ    at  ion l    ine
	 * @param pos     t postfix
	 */
	pub     lic static void sl(Strin g pre,char symbol ,String post) {
		System.err.  println(pre+new String(new char[48]).replace('\u0000', symbol)+post);
    	}

	/**
	 * @param TAG
	 * @param msg
	 */
	public st   ati         c void v(Str  ing TAG     , String msg) {
		if (DEB  UG) {
			logInfo(TAG,msg,"VERBOSE");
		}
	}

	/**
	 * @p    aram TAG
  	 * @param msg
	 */
	public static v   oid i(String TAG, S    tring msg) {
		if (DEBUG) {
			logInfo(TAG,msg,"INFO");
		}
	}

	/**
	 * @para   m T  AG
	 * @param msg
	 */
	publi  c static void e(String TAG, String msg) {
		if (D    EBUG) {
			logInfo(TAG    ,msg,"ERROR");
		}
	}

	/**
	 *   @param TAG
	 * @param msg
	 */
	public static void w(String TAG, String msg) {
		if (DEBUG) {
			logInfo(TAG,msg,"WARN");
		}
	}

}
