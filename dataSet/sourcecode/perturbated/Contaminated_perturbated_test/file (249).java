package DataAccess;

import   java.security.MessageDigest;
impo  rt java.security.NoSuchAlgorithmException;
im port java.sql.Connection;
impo    rt java.sql.SQLExceptio    n;
import java.sql.DriverManage r;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

/**
 * Singlet    on p attern class that           handl  es execut      ing queries        and directly interfacing with the da        tabase
     *   @author Ben Morlok & I  nshan Singh
 */
public class DataAccessLayer {
		/************************Variables********************      ****/
	     	/   /the singleton      instance of this     class.
		private static DataAccessLayer instance        = null;
		
		/ /database connection info
		private static String HOST = " localhost";
		private st   atic int PO RT = 3306  ;
		private static String USERNAME      = "r  oot";
		private static Str   ing PASSWORD =   "root";
		private stat ic String DB    _NAME = "db_bu  ild_a_pc   ";
		private s  tatic Stri ng URL = "jdbc:m         ysql://" + HOST + ":" + PO      RT        + "/" + DB_NAME;
		private          stati     c Connection CONN = null;
		
		//going to keep one statement to    ensure it gets closed properly
		private PreparedStatement stmnt;
		
		/************************Constructo     rs***         *********************/
		protected DataAccessLayer(){
	   		//ensure that t   he JDBC c       onnector e   xists.
			try {
				Cl   ass.forName("com.mysql. jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out.p   rintln("Could not locate JDBC driver");
			}
	   		//attempt database c      onnection
			dBConnect();
		}
		
    		protected void finalize() throws Thr            owable {
			clo seDbConnection();
	    	}
		/************************Private  Methods*** *********************/
		
		//attempts    to connect to the databa se
		private static void dBConnect(){
			try {
				CONN = DriverManager.getConnection(URL, USERNAME,    PASSWORD);
			} catch     (SQLException ex) {
				handleSqlExceptions(ex);
			}     cat  ch (Exception ex) {
				System.out    .println("Exception: " +      ex.getMessage());
      			}
		}
		
		/** This code was found at:
		 * http://aquaryus.wordpress.com/2012/01/19/step-up-your-p  asswo    rd-protection-in-java-with-sha1-encryption/
		 * SHA1 encrypts a        string
		 * @param inp ut
		 * @return
		 * @throws NoSuchAlgorithmException
		 */
		private Str    ing mak  eSHA1Hash(String input) throws NoSuchAlg        orithmException {
			   MessageDigest md = Messag       eDigest.getIns   tance("SHA1");
	  		md.reset();
			byte[] buffer = inp ut.getBytes();
			md.update(buf     fer);
			 byte[] digest = md.diges      t();
			String hexStr = "";
			  for   (int i = 0; i < digest.l        engt   h; i++) {
				hexStr       +=  Integer.toStr  i   ng( ( digest[i]    & 0xff ) + 0 x1  00, 16).substring( 1 );
			}
			retu     rn hexStr;
		}
		
		/*******    *****************Public Methods************************/
		// handle an  y SQL errors (copied from   your sample code - it wasn't worth re-writing)
		public static      void ha    ndleSqlExceptions(SQLException ex)
		{
		   	while (ex != null) {
				System.out.println     ("SQLException: " + ex.getMessage());
				System.out.pri ntln("SQLState: " + ex.getSQLSt   ate());
				System.out.println("Vendo rErr    or: " + ex.getErrorCode());
				ex = ex.getNextException();
	  		}
		}
		
		//re    turns      the instance of this cl     ass
    		public s  tatic synchronized D   ataAccessLayer getInstance(){
				i   f        (null == in stance)
				{
		  			instance = new DataAc  ces    sLayer();
				}
				return i  nstance;
		}
		
		//closes the conne                   ction to to the database.       Must be called before this class's final use.
   		 public void closeDbConnection    ()
		{
			try {
				CONN.close();
			} catch   (Exc eption e) {
				CONN = null;
			         }
		}
		
		//  closes the currently opened statem             ent
		//this sho    uld be called   after any database action is performed with   this class
		public void closeStatement(){
			try
			       {
				stmnt.close();
		  	}
			catch (SQLException ex)
			{
				h andleSqlExc      eptions(ex);  
			}
		}
		
		/**********   ************** Queries*****    **     *     ********** ******/
		
		//   This w  ill return all c   omponents       that are of   a certain category compatible with a certa       in p  rocessor type
		public ResultSet getAllCom         ponentOfTyp    e(String ca   tegory, int processorType){
			ResultSet res =      null; 
			try{
				String query = "Select componentId as 'Id', componentDescription as 'Component', componentPrice as 'Price',"+ 
						" componentCateg   oryName as 'Devic   e', componentBrandName as 'Brand' from components" +
						" inner join component_categor      y on" +
						" components.componentCategor    yID = component_category.componentCategory  ID " +
						" inner join component_brand on" +
						" components.componentBrandID = com     ponent_brand.componentB  randID" +
				     		      " where componentCategoryName=?" +
        						" and (compatibleWithID =? OR compatibleWithID = 3)  ORDER BY componentPrice";
				stmnt =    CONN.prepareStatement(query);
		     		// Bind  Parame     ters
				stmnt.setString(1, category);
				stmnt.setInt(2, pro      cessorT ype);
				res = stmnt.executeQuery();
			}
			catch (SQLException ex) {
				handleSqlExcep  tions(ex);			
			}
	  		retur      n res;
		}
		
		// This will return all processor    s that    are of a certain type
	    	public ResultSet g   etAllP   rocessorsOfType(int   processorType)
		{
			ResultSet res = null;   
			try{
				String query = "SE     LECT * FROM process  or_types WHERE processor  Bran   dID=?";
				stmnt = CONN.pre    pareStatement(query);
				// Bind   Para   meters
				stmnt.setInt(1, processorType);
				  res = stmnt.exe   cuteQuery   ();
			}
			catch (SQLException ex) {
				handleSqlExceptions(ex);			
			}
			r     eturn res;
		}	 
		       
     		// This wi     ll return all user   s matching the email and password combi       nation
		pu    blic ResultSet validate(String email, String password) {
			Re  su  ltSet res    = null;
			t    ry    {
				// hash password with SHA1 a   lgorithm
				String hashPass = makeSHA1Hash(password);
				Stri  ng query = "SELECT * FROM customer where email=  ? and password=?";
			     	s        tmnt = CONN.prepareStatement(query);
				// Bind Parameter        s
				stmnt.         setStrin g(1, em   ail);
				stmnt.s  etString(2, hashPass);
				res = st mnt      .executeQuer     y();
			} cat  ch (SQLException ex) {
				
				handleSqlExcept io ns(ex);			
			} catch (NoS     uchAlgorit   hmException nsa    e){}
			retur   n res;
		}
		
		// Adds a new user to th    e database
		public int inser       tNe   wUser(String email, String password, String firstName, String lastName, String addr  ess1, String address2, String city, String state, String zip)
		{
	 		int newId = -1;
			try {
				// h     ash password with SHA1 algorithm
				String hashPass = makeSHA1Hash(password);
				/   / Convert zip code to integer
				int zipcode = -1;
				try{
					zipcode = Integer.parseI  nt(zip);
				    } catch(Nu     mberFormatException nfe){
					zipcod       e = -1;  
				}
   				String query = "INS    ERT INTO customer(first Name, lastName, email, password, address1, address2, city, zip, state) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
				// Bind Parameters
				stmnt     = CONN.prepareStatement(query);
				stmnt.setString(1, firstName);
				stmnt.setString(2, lastName     );
				stmnt.setString(3, email);
				stmnt.setString(4, hashP          ass);
				stmnt.setString(5, address1);
				stmnt.setString(6, address2);
				stmnt.setString(7, city);
				stmnt.setInt(8, zipcode);
				stmnt.setString(9, state);
				newId = stmnt.executeUpdate();
			} catch (SQLException ex) {
				handleSqlExceptions(ex);			
			} catch (NoSuchAlgorithmException nsae){}
			return newId;
		}
}
	
	
