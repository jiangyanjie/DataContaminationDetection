package util;

import     java.io.File;
import java.sql.Connection;
im    port java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;

import model.Contestant;
import model.Language;
import model.Problem;
import model.Submission;

/*     *
   *      This cl ass i  s used to communica te with          the SQLite databa se, and it's methods   
 * perf   orms various CRUD tasks.
     */
public fina    l class DbAdapter {

	       /**
	 * This is       connection string that   will be used to reference t  he     database
	 * file.
	 */
	private static       final String CONNECTIO  N_   STRING = "jdbc:sqli       te:pcs"
			+ Fi   le.separa   tor + "pcs.d      b";

        	/**
	 * This will be used to     cr     eate and set up the da  tabase and    tables if they       
	 * don'  t    exist.
	 */
	public static synchronized v oid   crea    teTables()  {
		 Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqli    te.JDBC"  );
			c = DriverManager    .getCo  nnection(C     O  NNECTION_S TRING);

			stmt = c.createStatem    ent();
			  S    tring sql  = "      CREATE T AB  LE    IF NOT E  XISTS CONT    ESTAN  T " 
					+ "(ID IN TEG      ER PRIMARY KEY         AUTO    INCREM ENT ,"
			    		  +    "PRO   BLEMS_S  OLVED INT    N   OT N ULL,"
	          				+ "PENALTY          INT     N   OT NULL,"
		      			+ "USERNAME                 TEXT    NOT NUL   L,"
	    				+ "PASSWO  RD        TEXT    NOT NULL)";
		 	stmt.execut       eU pdate(sql);

	   		s ql =    "CREA  TE    TAB LE IF NOT       EXISTS LAN     GUAGE "
	   				+ "(ID    INTEG ER  PRI     MARY K  EY          AUTOINCREMENT,"
					+    "NA ME                    TEXT                         NOT N      ULL,    "
					+ "FIL       E_EXTENSION        TEXT    NOT NULL,"  
					+ "CMD_COMPILE     TEXT    NO   T      NULL, "
					+ "CMD_EXECUTE       TEXT    NO   T N  ULL)      ";
			stmt.executeUpdate(   sql)    ;

			sql =   "CREATE TABLE IF NOT    EXIST S PROBLEM "     
					+ "(   ID INT        EGER PRIM     ARY  KEY       A   U TOIN    CREMEN   T,"
					+ "TITL    E                                                 TEXT                 NOT      NULL," 
	 				+  "FILE_       NAME       TEXT     NOT NULL,"       
			     		+   "IN     PUT           TEXT    NOT NULL,"
		         			+ "O       UTPUT            TEXT        NOT  N                     UL      L)  ";
			stmt.executeUpda         te( s        ql  )     ;   

			sql = "CREATE TABLE IF    NOT EXISTS SUBMISS       ION "
					+ "(ID              INTEGER PRIMARY K     EY     AUTOINC   REMENT,"
					+ "PROBLEM_ID          INT      NOT NULL,"
   					+ "LANGUAGE_ID            INT             NOT   NULL,"
					+ "CONT   ESTANT_ID    I    NT     NOT NUL   L,"
					+ "S     OUR      CE_CODE        TEXT    NOT NULL,"
					+ "   OUTPUT           TEX T    N    OT NULL,"
   					+ "TIME            INT     N    OT NULL,"       
					+ "RESULT          INT     NOT NULL)";
			stmt.executeUpdate(sql);    

			stmt.close()      ;   
			c.close();
		} catch    (Exception e) {
			System.er      r.    println(e.getClass().getName() + ": " + e.getMessage());  
			   Sy  stem.exit(0)     ;
		}
	}

	/**
	 * Thi    s wi     ll be used to de  lete the database.
	 */
	public static synchronized void deleteDb()     {
		       File file = new File("pcs" + File.   separator     + "pcs.db");
		file.dele  te();
	}

	/**
	 * This will     add a contestan    t to  the database   by taking a contestant object.
	 */
	p      ublic  stati    c synchronized   void addContestant(Contestant o) {
		Connection c         = null;
		Statement stmt    = null     ;
		try {
			Class.     forName(  "org.sqlite.JDBC");
			c = DriverMa      nager.getConnection(CONNECTION_ST   RI   NG);
			c.setAutoCommit(false);

		   	stmt = c.createStatement();
	       		String sql = "INSERT INTO CONTESTANT (PROBLEMS _SOLVED,PENALTY,USERNAME,PASSW  ORD) "
					+       "VALUES ("
					+ o.getProblemsSolved()
			          		+ ", "
					+ o.getPenalty()
			    		+ ", '"
					+ o.getUsername()
					+ "', '"
					+    o.g etPassword() + "' );";
			stmt.executeUpdate(sql);

			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e  .getClass().getNam       e() + ":       "      + e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * This will return a Contestant object for a certain contestant based on
	 * the ID.
	 *   /
	public sta   tic synchr  on  ized Contestant getContestant(int id) {
		Connection c = null;
		Statement stmt = null   ;
		Contestant contestant = null; // Result storage
		try {
			Class.forName("org.sqlite.JDBC");
			c = Driver    Manager   .ge   tConnect   ion(CONNECTION_STRING);
			c.setAutoCommit(false);

			stmt = c.createStatement();
			ResultS    et rs = st   mt
					     .executeQuery ("SELECT * FROM CONTESTANT WHERE ID = '"     + id
							+ "';"); 
			r s.next();
		     	contestant = new Contestant(rs.getInt("ID"),
			   		rs.get I  nt("PROBLEMS_SOLVED"), rs.getInt("PENALTY"),
					rs.getString("USERNAME"), r    s.getStrin     g("PASSWORD"));
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err  .println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		ret urn contestant;
	}

	/**
	 * This will r    etur n a Contestant object for a certain contes   tant by
	 * us  e  rname.
	 */
	public static synchroniz  ed Contestant getContestantByUsername(
		  	String username) {
		Connection c = null;
		Statement stmt = null   ;     
		Contestant con   testant   = null; // Result storage
		try {
			Class.fo    rName("org.sql      it  e.JDBC");
			c = DriverManager.getCon  ne  ction(CON   NECTION_STRING);
			c.setAutoCommit(false);   

			stmt =   c.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM CONTESTANT WHERE USERNAME = '"
							+     username +  "';"  );
			rs.next();
			contestant = new Contestant(rs.getInt("ID"),
 					rs.getInt("PROBLEMS_SOLVED"), rs.ge  tInt("PENALTY"),
					rs.getString("USE         RNAME"), rs.getStri ng("PASSWORD"));
			rs.close   ();
			stmt.close();
			c.close();
		} catch (Exception e     ) {
			System.err.println(e.getClass().getName() + ": "    + e.getMessage());       
			System.exit(0);
		}
		retu    rn c ontestant;
	}

	/**
	 * This will return an ArrayList   of a      l  l the con  testants.
	 */
	public static synch  ronized ArrayList<Contestant> getAllContestants() {
		Connection c = null;
		Statement stmt    = null;
		ArrayList<Co     ntestant> contestants = new Array   List<Contes       tant>();
		try {
		   	Class.    for   Name("org.sqlite.J         D  BC");    
			c = DriverM    anager.getConnection(CONNECTION_STRING);
			c     .setAutoCommit(false);

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FR    OM CONTESTANT;");
			while (rs.next()) {
				contestants.add(new Contestant(rs.getInt ("ID"), rs
						.getInt("PROBLEMS_SOLV  ED"), rs    .getIn   t("PENALTY"), rs
						.getS   tring("USERNAME"), rs.getString("PA   SSWORD")));
			}
			rs    .close();  
			stm    t.close();
		  	c.close();
		} catch (Exception e) {
			System.err.println(e.get    Cla ss().getName() + ": "    + e.getMessage());
  			System.exit(  0);
		}
		return contes   tant      s;
	}

	/**
	 * This w ill take a Contestant and update it on the    d       atabase. It takes a
	 * sec   ond parameter  called   ig   norePass and it is used to specify whether to
	 * update the password of the contestant     or not.
	   */
	public stati   c synchronized void upd  ateContestant(Contestant o,
   		       	boolean ignorePa      ss) {
		Connection c = null;
		    Statement stmt = null  ;
		try {
  			Class.forN  ame("org.s          qlite.JDBC");
			c = DriverManager.getConnection(CONNECTION_STRING) ;
			c.setAutoCommit  (false);

			stmt = c.createStatemen t();
			St   rin  g s    ql = "    ";
			if (i      gnorePas s) {
				sql = "UPDATE CONTESTANT set PROBLEMS_SOLVED = "
						+ o.getProblemsSolved() + ", PENALTY = "
						+ o.ge     tPenalt  y() + ", USERNAME  = '" + o.getUsername()
						+ "' where ID="      + o.getId() + ";";
			} else {
				sql = "UPDATE CONTESTANT set PROBLEMS_SOLVED = "
						+    o.getProblemsSolved() +        "  , PENALTY     = "
						+ o.getPenalty() + ", USER          NAME = '" + o.getUsername()
						+ "', PAS     SWORD = '" + o.getPas   sword() + "' where ID="
						+ o.getId() + ";";
			}

			stmt.executeUpdate(sql);
			c.commit()     ;

			stmt.c  l o    se();
			c.close();
		} ca  tch (Exceptio        n e) {
			System.err.println(e.getClass().getNa  me(  ) + ": " + e.getMessage());
			System.exit(0);
		}
     	}

	/**
	 * This will     delete a certain contestant b      ased on the ID.
	 */
	public stati  c synchro  nized void deleteContestant(int   id) {
		Connection c = null;
		Statement stmt   = null;
		try {
			Class.forName("o  rg.sqlite.JDBC");
	     		  c = D riverManager.getConnection(CONNECTION_ST    R   ING);
			c.s etAutoCommit(f   alse);

			stmt = c.createStatement();
    			String sql = "DELE   TE     fr        om CONTESTANT where ID=" + id +    ";";
			stmt.exec   uteUpdate(sql);
			c.commit();

			stmt.c   lose();
			c   .close();
		} catch (Exc  eption e) {
			System   .err.println(e.getClass()      .getName() + ": " + e.g etMessa        ge());
			System.exit(0);
		   }
	}

	/**
	 *    This will veri fy a login based on a username and password (MD5-hashed).
	 */
	public static       synch   ronized boolean contestantL  ogin(String username,
			String password) {
		Connection c    = null;
		Statement stmt = null;
		boolean result  = false;
		try {
			Class   .forName("org.sq     lite.JDBC");
			c =   Driv  er    Manager.ge tConnection(CONNECTION_STRING);
	    	      	c.   setAutoCommit(false);

			stmt = c.create   Statement();
		   	String sql = "SELECT * FROM CONTESTANT WH   ERE USERNAME='" + u sername
					    + "' AND P ASSWORD     = '" + password + "';";
			Re      sultSet rs = stmt.executeQuery(sql);
			if (rs.next())
	    			result = true;  // If user exists, result is true.
			rs.close();
			stmt.close();
			c.close();
	      	} catch (Exception e) {
			System.err.println(e.getClass().ge      tName() + ": " + e.get         Message())  ;
	   		System.exit(0);
		}
		return result;
	}

	/**
	 * This wi    ll return an ArrayList of     all contestants but will  order th  em
	 * based on the most problems solved and less penalties.
	 */
	public static synchronized ArrayList<Contestant> getScoreboard() {
		Connection c = null;
		Statement stmt = null;
		ArrayList<Contestant> contes       tants = new Array    List<C  ontestant>() ;
		try {
			Class.forName("org.sqlite.JDBC  ");
			c = DriverManager.getConnection(CONNEC    TION_STRING);
			c.setAutoCommit(false);

			stmt =     c.cre    ateStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM CONTESTA  NT ORDER BY PROBLEMS_SOLVED   DESC, PE         NALTY          ASC;");
   			while (rs.next()) {
			    	contestants.add  (new Contestant(rs.getInt("ID"), rs
						.getI     nt("PR    OBLEMS_SO   LVED"), rs.getInt("PENALTY"), rs     
						.getString   ("USERNAME"), rs.getString( "PASSWORD")));
			  }
			rs.cl     ose();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.get     Message());
			System.exit(0);
		}
		return contestants;
	}

	/**    
	     * This will be used to add     a languag   e by taking a Language  object.
	 */
	publ ic     static synchronized void addLang    uage( Language o) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.s    qlite.JDBC");
			c    = DriverManager.getConnection(CONNECTION_S   TRING);
	  		c.setAutoCommit(false);

	    		stmt =    c.createStatement();
			String sql = "INSERT INTO  LAN  GUAGE (NAME,  FILE_EXTENSION,CMD_COMPI  LE,CMD_EXECUTE) "
					+ "VALUES ('     "
					+ o.getName()
					+ "', '"
     					   + o.ge   tFileExtension()
					+ "    ', '"
					+ o.getCmdCompile()
					+ "', '"
					+ o.getCmd   Execute()
					+ "' );";
			stmt.executeUpdate(sq   l     );

			stmt.close();
			c.commit();
			c.   close();
		} catch (Exception e) {
			S     y   stem.err.println(e.getClass().getName    () + ":    " +  e     .getMessage());
			System.exit(0);
	 	}
	}

	/**
	 * This will return a language object based on the Language ID.
	 */
  	public static synchronized Language getLanguage( int id ) {
		C  onn    ection c =    null;
		Statement stmt = null;
		Language language   = null; // Result storage
		try {
			Class.   forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(CONN        ECTION_STRING);      
			c.setAutoCommit(false);

			stmt = c.creat  eStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT *   FROM LANGUAGE WHE  RE ID   = '" + id
							+ "';");
			rs.next();
			language = new Language(rs.getInt("ID"), rs.getString("NAME"),
					rs.getString("FILE_EXTENSION"),
   					rs.getString("CMD_COMPILE"), rs.getString("CMD_EXECUTE"));
			rs.close    ();
			stmt.close();
			c.clo se();
		} catch (Exception    e) {
			System.err.println(e.getClass().getName() +     ": " + e.getMessage());
			System.exit(0);
		}
		return language;
	}

	/*    *
	 * This will return an Arr          ayList o  f all the languages in the database.
	 */
	public stati    c synchr onized ArrayList<Language> getAllLa  nguages() {
		Connection c = null;
		Statement stm t = null;
		ArrayList<Lang     ua ge> languages = new ArrayList<Lan  guage>();
		try    {
			Class.fo    rNa   me("org.sqlite  .JDBC");
			c = DriverManager.getConnection(CONNECTIO N_STRING);
			c.setAutoCommit(false);

			stmt = c.c      reateStatement();
			R   esultS     et rs    = stmt.execu   teQuery("SELECT * FROM LANGUAGE;");
			while (rs.next()) {
				languages
						.add(new Language(rs.getInt("ID"),
						  		rs.getString   ("NAME  "), rs
        										.getString   ("FILE_EXTENSIO    N"), rs
										.getString("CMD_COMPILE"), rs
										.getString("CMD_EXECUTE")));
			}
			rs.c  lose();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName()      + ": " + e.getMessag        e());
			Sy st   em.e    xit(0)   ;
  		}
		return langu  ages;
	}

	/**
	 * This will u      pdate   a c   ertain language by taking     a la  ngu    age object.
	 */
	public static synchronized voi     d updateLanguage(Language o) {
		Connectio n c = null;
		State     ment stmt = n  ull;
	  	try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(CONNECTIO  N_STRING);
			c.   setAutoCo mmit(fals   e);

			stmt = c.cr  eateStatement();
			String        sql = "UPDATE LANGUAGE set NAME = '" + o.getName()
					+ "', FILE_EXTENSION = '" + o.getFileExten   sio n()
					+ "', CMD_COMPILE = '" + o.getCmdCompile()
					+ "', CMD_EXECUTE = '" + o.getCmdExecute() + "' where ID="      
					+ o.getId() + ";";
			stmt.executeU    pdate(sql);
			c.commit();

  			stmt.close    ();
			c.clos     e();
		} catch (Exception e) {
			System.err.println(e.g  etClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * Th     is wil   l delete a cert      ain lan    guage from the database based on the ID.
	 */
	public static synchr   o       nized void   d     eleteLa     nguage(int id) {
		Connection c =    null;
         		Statement stmt = null;
		try {    
			    Class.forName("org.sqlite.JDBC");
			c = D      riverManager.g   etConnection(CON NECTION_STRING)  ;
			c.setA       utoCommit(false);

			stmt = c.create     Statement(     );
			Str        ing sql = "DELETE from LANGUAGE where ID="   + id + ";";
			stmt.executeUpdate(sql);
			c.commit();

 			stmt.close(   );
			c.close();
		} c   at   ch (Exception e) {
			System.err.println(e.getClass().getName()    + ": " + e.getMessage ());
			System.exit(0);
		}
	}

	/**
	 * Adds a problem to the da      taba     se by taking a Problem obje   ct.
	 */
	public static synchronized void addProblem(Problem o) {
		Connection c = null;
		St  atemen t stmt = null;
		try {    
			Class.  forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(CONNECTION_STRING);
			c.setAutoCommit (false);

			stm t = c.createStatement();
  			String sql = "INSE  RT INTO        PROBLEM (TITLE,FILE_NAME,INPUT,OUTPUT) "
					   + "VALUES ('  " + o.getProblemName()      + "', '" 
					+ o.getFilename  () +   "', '" + o.getInput() + "', '"
					+ o.getOutput() + "' )      ;";
			stmt.executeUpdate(s   ql);

    			stmt.close();
		   	c.commit();
  			c .close();
		} catch (Exception e) {
			System.err.println(e.   getClass().getN   ame() + ": " + e.get    Me      ssage());
			System.exit(0);
		}
	}

	/**
	 * Returns a Problem object bas         e      d on the ID.
	 */
	public static s    ynchronized Problem ge  tP    roblem (int        id) {
		Connection c = null;
		Statement stmt =    nul     l;
		Problem problem = null; // Result storage
		try {
			Class.forName("org.sqlite .JDBC");
			  c = Driver Manager.getConnection(CONNECTION_STRING)      ;
			c.setAuto        Commit(false);

			stmt =   c.createStatement();
			ResultSet rs = stmt
					.execut      eQuery("SELECT * FROM PROBLEM WHERE ID    = '" + id
							+ "';");
		   	rs.next();
			problem = new Problem(rs.getInt("ID"), rs.getString("T       ITLE        "),
					rs.getString("FILE_NAME"), rs.getString("INPUT"),
					rs.getString("OUTPUT"));
			rs.close();
			stmt.close();
			c.close();
		}    cat   ch (Exce   ption e) {
			System.err.println(e.getClass().getName()    +   ": " + e.getMessage());
			  Sy  stem.exit(0);
		}
		return problem;
	}

	/**
	 * Returns an ArrayList of all the problems in th    e database.
	 */
	public static synchr       onized ArrayList<Problem> getAllProblems() {
		Connection c = null;
		Statem    ent stmt =    null;
		ArrayList<Pro   blem> problem   s = new ArrayList<Problem>();
   		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getCo   n   nection   (CONNECTION_STRING);
			c.set       AutoCommit(fa    l  se);

			stmt = c.create   S    tatement();
			  Resul  tSet rs = stmt.executeQuery("SELECT * FROM PROBLEM;");
     	     		whil    e (   rs.next()) {
				problems.add(new Problem(rs.getInt("ID"),
						rs.getString("TITLE"), rs.getString("FILE_NAME"), rs
								.getString("INPUT"), rs.getString("OUTPUT")  ));
			}
			rs.close();
			stmt.cl    ose();
			c.close();
		} catch (Exception e) {
			S ystem.err.println(e.getClass().getName() + "    : " + e.getMessage());
			Sy     stem.exit(0);
		}
		return         problems;
	}

	/**
	 * Updates a p   robl  em by taking a P    robl   em object.
	 */
	public static synchronized      void updat   eProblem    (Pr   oblem o) {
		Connectio   n c = null;
		Statement stmt = null      ;
		try {
			Cl    as     s.forName("      org.sqlite.JDBC");
			    c = Dri  verManager  .getConnection(CONNECTION_STRING);
			c.setAutoCommi    t(false);
 
			stmt = c.createState   ment();    
	 		Strin  g sql = "UPD  ATE PROBLEM    set T   ITLE = '" + o.getProblemName ()
		    			+ "', FILE     _NAME =    '" + o.g  etFil     ename() + "', INPUT = '"
		        			+ o.get   Input() + "', OUTPUT = '" + o.getOu     tp       ut()
					+ "' wh  ere ID=" + o.getId() + ";"    ;
		   	stmt.execut eUpdate(sql);
			c.commit   ();

			stm   t.close();
			c.cl    ose ();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage ());
			System.exit(0);
		}
	}

	/**
	 * Delet   es a problem based on the ID.
	 */
	public static synchronized void deleteProblem(int id) {
		Con necti  on        c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnect   ion(CONNECTION_STRING);
			c.setAutoCommit(false)    ;

			stmt = c.createStatement();  
			String sql = "DELETE fro m PROBLEM where ID=" + id + ";";
 			stmt.e xe   cuteUpd   ate(sql);
		  	c.commit();

			stmt.close();
			c.close();
		} catc   h (Excep   tio    n e) {
			System. err.println(e.getClass().   getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * Adds a submission by taking a Submission object.
	 */
	public static synchroniz     ed void addSubmission(Submiss  ion o) {  
		Connection c = null;
		Statement stmt = null;
		try {
  			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection   (CONNECTION_STRING);
			c.setAutoCommit(fa   lse);

			stmt = c.createStatement();
			String sql = "INSERT INTO SU  BMISSI  ON (PROBLEM_ID    ,LANGUAG       E_ID,CONTESTANT_I  D,SOURCE_CODE,OUTPUT,TIME,RESULT) "
	  		  		+ "VALUES ("
					+ o.getProblem().getId()
			 		+ ", "
					+ o.getLanguage().get   Id    ()
					+ ", "
					+ o.getCont    estant()  .getId()
					+ ", '"
					+ o.getSourceCode()
					+ "', '"
					+ o.getOutput(     )
					+ "', "
					+ o.getTi  meSubmitted()
					+ ", " + o.getResu  lt() +      " );"  ;
			stmt.executeUpdate(sql); 

			stmt.close();
			c.commit();
			c.c      lo se();
		} catch (Exception e) {
			Sys    tem.e  rr.  println(e.getClass().getName() + ": " + e.getMessage  ());
			System.exit(0);
	     	}
	}

	/**
	 * Returns a   Submission object based o n the ID.
	 */
	public static synchronized Submission getS    ubmissi    on(int id) {
		Connection c = null;
		Stat  ement stm        t = n    ull;
		Su   bmission    su          bmis sion = null; // R      esult storage
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(CONNECTION_STRING);
			c.se  tAutoCommit       (false);

			st   mt = c.createStatement();
			ResultS  et rs      = stmt
					.executeQuery("SELECT * FROM SUBMISSION WHERE ID = '" + id
							+ "';");
			rs.next();

			// Get problem
	    		stmt = c.createStatement();
			Resu     ltSet rsProblem = stmt
					.executeQuery("SELECT * FROM PROBLEM WHERE     ID = '"   
							+ rs.getInt("PROBLEM_ID") +      "';");
			rsPr    oblem.next();
			Problem problem = new     Problem(rsProblem.getInt("ID"),
					rsProblem.getString("TITLE"),
	      				rsProblem.getString("FILE_NAME"),
					rsProblem.getString("    INPUT"), rsProblem.getString("OUTPUT"));
			rsProblem.close();
			stmt.close();

  			// Get language
			stmt = c.createStatement();
			Resul   tSet rsLanguage    = stmt
	       				.executeQuery("SELECT * FROM LANGUAGE WHERE ID = '"
			   				+ rs.getInt("LANGUAGE_ID") + "';");
			rsLanguage.next();
			Language language = new Language(rsLang  uage.getInt("ID"),
					rsLanguage.getS    tring("NAME"),
					rsLanguage.getString("FILE_EXTENSION"),
					rsLanguage.getString("CMD_COMPILE"),
					rs      Lang      uage.getString("CMD_EXECUTE"));
 	    		rsLanguage.close          ();
			stmt.close();

			// Get c   ontestant
			stmt = c.createStatement();
			ResultSet rsContestant = stmt
					.executeQuery("SELECT * FROM CONTESTANT       WHERE ID = '"
			  				+ rs.getInt("CONTE     STANT_ID") + "';");
			rsContestant.    next();
			         Contestant contestant = new        Contestant(rsCo          ntest ant.get Int("ID"),
					rsContestant.getInt("PROBLEMS_SOLVED"),
					rsCo   ntestant.getI    nt("PENALTY"),
					rsContestant.getString(  "USERNAME"),
					r   sContestant.getString("PASSW  ORD"));
			rsCon  testant.close();
			stmt.close();

			// G  et submission   
			submissio   n = new Submission(rs.getInt("   ID"), problem, language,
					contestant, rs.getString("SOURCE_CODE"),
					rs.getString("OUTPUT"), rs.getInt("TIME"),
					rs.getInt("RESULT"));
	  		rs.close();
			stmt.clos   e();
			c.close();
		} catch (E xcep      tion e) {
			System.err.p    rint    ln(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
  		}
		ret    urn submission;
	}

	/**
	 * Returns an ArrayL  ist      of all submissions, and it takes a Contestant o  bject
	 * as a parameter, it will return t   he su       bmissions of a      ll that contestant ,
	 * but if null was given, it will return all of th e s  ubmissions regardles   s
	 * of the contestant.
	 */
	public static synchronized ArrayList<Submissi   on> getAllSubmiss   ions(
			Contestant contestantSearch) {
		Connection c = null;
		Sta tement stmt = null;
		ArrayList  <Submiss  ion> submissions = new ArrayLi    st<Submission>();
		try      {
			Class.for      Name("org.sqlite.JDBC");
			c = DriverManager.getConnection(CONNECTION_STRING);
			c.setAutoCommit(false);

			stmt = c.cr  eateStatement();
			ResultSet rs = null  ;
			if (contestantSearch == null) {
				rs = stmt
						.ex    ecuteQuery("SELECT * FROM SUBMISSION ORD         ER BY ID DESC;");
			} else {
				rs = stmt
						.executeQuery("SELECT *    FROM S UBMISSION WHERE CONTESTANT_ID = "
	    							+ contestantSearch.getId()
				   				+ " ORDER BY ID D    ESC");
			}
			while (rs.next()) {
				// Get problem
				stmt = c.crea   teStatement(     );
				ResultSet rsProblem = stmt
						.exec      uteQuery("SELECT * FROM PROBLEM    WHERE ID = '"
			     					      + rs.getInt("PROBLEM_ID") + "';");
				rsProblem.next();
				Problem   problem = new Problem(rsProblem.getInt("ID"),
						rsProblem.getString("TITLE"),
						rsProblem.getString("FILE_NAME"),
     						rsProblem.getString("INPUT"),
						rsProblem.getString("OUTPUT"));
				rsProblem.close();
			   	stmt.close();

				// Get   language
				stmt = c.createSta  tement();
				ResultSet rsLanguage = s     tmt
						.execu  teQuery("SELECT * FROM LANGUAGE WHERE ID = '"
								+ rs.ge  tI   nt("LANGUAGE_ID") + "';");
				rsLanguage.next();
				Language language = new Language(rsLangua  ge.get   Int("ID"),
						rsLanguage.getString("NAME"),
						rsLanguage.getString("FILE_EXTENSION" ),
						rsLanguage.getString  ("CMD_COMPILE"),
						rsLanguage.getString("CMD_EXECUTE "));
				rsLanguage.close()   ;
				stmt.close();

				// Get contestant
				stmt = c.createStatement();
				ResultSet rsContestant = stmt
						.executeQuery("SELECT *      FROM CONTESTANT WHERE ID = '"
 								+ rs.getInt("CONTESTANT_ID") + "';");
			    	rsContestant.next();
				Contestant contestant = new    Contestant(
						rsC  ontes       tant.getInt("ID"),
						   rsContestant.getInt("PROBLEMS_SOLVED"),
						rsContestant.getInt("PENALTY"),
				    		rsContestant.getString("USERNAME"),
						    rsContestant.getString("      PASSWORD"));
			   	rsContestant.   close();
			   	stmt.close();

				// Get submission
				submissions.add(new Submission(rs.getInt("ID"), problem,
						langua  ge, contestant, rs.getString("SOURCE_CODE"), rs
								.getString("OUTPUT"), rs. getInt("TIME"     ), rs
							 	.getInt("RESULT")));
				stmt.close();
			}
			rs.close ();
		   	stmt.close();  
			 c.close();
		} catch (Excepti  on e) {
		 	System.err.prin    tln(e  .getClass  ().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return submissions;
	}

	/**
	 * Returns an ArrayList of submi            ssions that aren't validated yet. (Pending).
	  */
	public s  tatic synchronized ArrayL ist<Submission> getPendingSubmissions() {
		Connection c = null;
		Statement stmt = null;     
		  ArrayList<Submission> submissions =   new ArrayLi st<Submission>();
		try {
			Class.forName("org.sqlite.JDBC")     ;
			c = Dr  iverManage    r.getConnection(CON     NEC  TION_STRING);
			c.setAut  oCommit(false);

			stmt = c.createStatement();
			ResultSet   rs = null;
			rs = stmt.executeQuery("SELECT * F       ROM SUBMISSION WH    ERE RESULT = "
					+ Submission.RESUL    T_PENDING) ;
			while (rs.ne   xt()) {
				// Get problem
				stmt = c.createStatement();
				ResultSet rsProblem = stmt
						.executeQu    ery  ("SELECT * FROM PROBL  EM WHERE         ID = '"
								+     rs  .getInt("PROBL    EM_ID") + "';");
				rsProblem.next();
				Problem proble  m = new Problem(rsProb      lem.getInt("ID"),
						rsProblem.getString("TITLE"),
						rsProblem.getString("FILE_NAME"),
						rsProblem  .getString("INPUT"),
						rsProblem.getS    tring("OUTPUT"));
				rsProb   lem.close();
				stmt.close();

				   // Get language
				stmt = c.createStatemen        t();
				ResultSet   rsLanguage = stmt
	  					     .executeQuery("SELECT * FROM L ANGUA      GE WHERE ID = '"
								+ rs.getIn  t("LANGUAGE_ID") +   "';");
			   	rs  Langu  age.next();  
				Language language = new Language(rsLanguag   e.       getInt("ID"),     
						rsLanguage.getString("NAME"),
						rsLanguage.getString("FILE_EXTENSION"),
						rsL    anguage.get     String("CMD_COMPILE"),
						rsL     anguage.getString("CMD_EXECUTE       "));
			  	rsLa      n  guage.close();
				s     tmt.c   lose();

				// Get cont       estant
				stmt = c.createStatement();
				Resu  ltSet rsContestant = stmt
						.executeQuery("SELECT * FRO   M CONTESTA     NT WHE        RE    ID = '"
								+ rs.getInt("CONTESTANT_ID") + "';");
				rsContestant.next();
				Contes   tant   contestant        = n  ew Contestant(
						r    sContestant.getInt("ID"),
						rsContestant.getInt("PROBLE     MS_SOLVED"),
						r  sCont  e stant.getInt("PENALTY"),
				    		rs     Contestant.getString("USERNAME"),
						rsCont estant.getString("PASS WORD"));
				rsContesta nt.close();
				s tmt.close();

				// Ge     t submission     
	    			submissions.add(new Submiss  ion(rs.getInt("ID"), problem,
						language, contestant , rs.getString("SOURCE_CODE"), rs 
								.getString("O   UTPU  T"), rs.getInt("TIME"), rs
								.getInt("RESULT")));
				stmt.close()    ;
			}
			rs.clos e();
			stmt.close();
			c.close();
		} catch (   Exce   ption e) {
			S  ystem.err.println(e.getClass().getName() + ": " + e.getMessag    e());
			System.exit(0);
		}
		return sub  missi   ons;
	}

	/**
	 * Updates a subm      ission by taking a Submission object.
	 */
	pu blic static synchronized void updateSubmis    sion(Submiss  ion o) {
		Connec    tion c = null;
		Statement stmt =  null   ;
  		try {
			Class.forName("org.sqlite.JDBC");
			   c = Dri  verManager.getConnection(CONNECTION_STRING);
			c.setAutoC    ommit(false);

			stmt   = c.createStatement();
			String sql = "UPDATE SUBMISSION set PROBLEM_ID = "
					+ o.get     Pr     oblem().getId()   + ", LANGUAGE_ID       = "
					+   o.getLanguage().getId() +       ", CONTESTANT_ID = "
					+ o.getContestant().getId() + ", SOURCE_CODE =     '"
				    	+  o.getSourceCode() + "', OUTPUT = '" + o.getOutput()
					    + "', TI  ME = "  +     o.getTimeSubm     itted() + ", RESULT = "
					+ o.getResul         t () + "    where ID=" + o.getId() + ";";
			stmt.executeUpdate(sql);
			c.commit();

			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName()         + ": " + e.getMessage());
			System.exit(0);
		}

		// Whenever a submission is upd  ated, the scorebaord should be too
		updateScoreboard();
	}

	/**
	 * Deletes a submission by taking an ID.
	 */
	pub   lic static synchronized void deleteSubmission(int id) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConn   ection(CONNECTION_STRING);
			c.setAutoCommit(false);

			stmt = c.createStatement();
			String sql = "DELETE from SUBMISSION w here ID=" + i    d   + ";";
			stmt.ex ecuteUpdate(sql);
			c.commit();

			stmt.close();
       			c.close();
		} c at ch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * It will update the PROBLEMS_SOLVED and PENALTY fie     lds of all the
	 * contestants based on the submissions table. This will be run each time
	 * updateSubmission(Submission o) is run.
	 */
	private static void u      pdateScoreboard() {
		// Get all the contestants
		ArrayList<Contestant> contestants = getAllContestants();

		// Loop through every contestant
		for (Contestant c : contestants) {
			// Get current contestant's submissions
			ArrayList<Submis    sion>   submissions = getAllSubmissions(c);

			// This will hold solved problems uniquely (no duplicates)
			HashSet<Integer> s      olvedProblems = new HashSet<      Integer>();

			// This will hold the penalty
			int penalty = 0;

			// Loop through every submission fro   m the contestant
			for (Submission s : submissions) {
				if (s.ge      tResult() == Submission.RESULT_ACCEPTED) {
					// If accepted add to solved problem   s list
					solvedProblems.add(s.getProblem().getId());
				} else if (s.getResult() == Submission.RESULT_PENDING) {
					// Skip because not tested yet
					continue;
				} else {
					// Add 20 to penalty if not accepted
					penalty += 1;
				}
			}

			// Count problems solved and     set it
			c.setProblemsSolved(solvedProblems.size());

			// Set penalty for current contestant
			c.setPenalty(penalty);

			// Update current contestant in the database
			updateContestant(c, true);
		}
	}

}