package  Database;
import java.sql.Connection;
import java.sql.DriverManager;
impor    t java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
     * Ha   ndl   es the db  connectio         n. It creates a connection          to the           database  name
 *   given to th  e constructor, or crea       tes a  connec tion to a de   fault database
 * (te    st.db) if none is give    n.
     * @author la       rba46    2
 *       
 */
public class DatabaseObject {
	    
	
	
	/**
	 * Class      variables
	 */
	private C onnection conn = nu     ll;
         	private S         tatement stmt = null;
	privat        e ResultSet rs = null;
	
	/******************* CONS TRUCTORS ************   *******/
	
	    /**
  	 * Constructor creates the connection by calling openConnection
	 *     And initiate     s the statement handler
	 *  @param db  Name
	 */
//	public Da      tabaseObject(String dbName){
//		
//		openConnection(dbName);
//		createStmt();
//	}
//	    
	
	
	  /**
	 * Constructor creates the connection by c    al  li      ng o    p    enConnection
	 * Since no parameter is  given test.db is used as a de fault database
	 * And initiates the statemen     t handler
	 */
	public DatabaseObject(){
  		openConnection("Battles       hips.db");

		crea      teStmt();
	}  
	
	
	
	
     	/******************* PRIV  ATE METHODS ************* ******/
	
	
	
	
	/**
	 * Opens a connection to     the given dbName
	  * Saves the connection in the conn variable
	 * @param dbName
	 */
	private void openConnection(String dbName){
		C onnection conn = null;
		try{
			  Class.forName("org.sqlite.JDBC");   
			conn =    DriverManager.getConnection("j dbc:sqlite:" + dbName);
		}catch(SQLException e){
			handleError(e)   ;
		}catch(Clas       sNotFoundExce    ption e){
			e.printStackTrace();
		}
		this.con n = conn;
	}
	
	
	
	/**
	   *  Instantiates the s  tatement
	 */
	private void createStmt(){
		try {
			stmt = conn.createStatement();
		} catch (SQLException e   ) {
		    	ha  nd   leE  rror(e);
		}
	}
	
	      

	
	
	/**    ***************** PROTE  CTED METHODS ************* ******/
	
	
	/******************* PUBLIC METHODS *        ******************/
	/**
	       * Writes to the given sql statemen   t     t    o      the database
	 * If t          he table does not exist the table    is cre  ated
	 * @param            sql
	 */
	public           v oid write(String sql)  {
		try{
			stmt.ex   ecuteUpdate(sql);
		}catch (S    QL   Excep   tio  n   e){
			handleError(e);
		}
	}
	
	
	
	/**
	 * Ru  ns the given sql then saves and   returns
	 * the given R  esult  Set
	      *  @return  sql
	 */
	public ResultS  et read(Strin  g sql){
		try {
			rs = stm t.executeQuery(sql);
		} catch (SQLException e) {
			handleError(e)    ;
		}      
	       	return rs;
	}
	
	
	
	/**
	 * Checks if the tab le exists
	 * @return boolean
	 */
	public b   oolean tableExists(String tableName){
		
	   	String sql = "SELECT       count(*) FROM sqlite_master WHERE"
				+ "	type='table'   AND"
				+  " name='      " + ta     bleName + "'";
		rs = read(sql);
		try          {
			//Retur  ns tr ue if the count of matched tables are   one or more
			ret urn rs.ge tInt("coun  t(*)"  ) > 0 ? true : fals           e;
		}catch(SQLExceptio          n e){
		    	    h   andleError(e           );
		}
		return false; //This     wil     l   n eve  r happen
  	}
	
	
	/**
	 * C      reates a table of given nam e and  array of col     umns
	 * the given columns are sup     o  sed    to be given in the following
	 * format:
	       * [colu     mn  Names][ columnType]
	 * 
	 * Example:
	 * [ example ],[varchar(255)]
	 * 
	 */
	public void createT   abl  e(String tableName, String[][] columns){
		i          f   (!tableExists(tableName)){
			String sql = "CREATE TABLE " + ta  bleName + "(";
			for(int i = 0;    i < co   l      umns.leng   th; i++){
				sql +=  columns[i][0] + 
						" "       + columns[i][1];
				sql += i == columns.length -1 ? "" : ", ";
			}
			sql += ")";
			System.out.println(sql) ;    
			write(sql);
		}
		else{
			//If the table allready exists, shu         t down
			Sy   stem.out.prin        tln("Table allready exists");
			System.exit(0);
		}
	}
	
	/**
	 * Closes the connection
	 */
	   public void closeConn  ection(){
		try{
			conn.close();
		}catch(SQLExce              ption e){
			handle    Error(e)     ;
		}
		
	}
	
	       
	
	/**
	 * Handles the given SQLException
	 * @param e: SQLException
	 * @see SQLE x  ception
	 */
	publi    c void handleError(SQLException e){
		System.err.println(e.getC lass().getName() + ": " + e.getMessage());
		System.exit(0);
	}
	
	
	public int countRows(String ta bleName){
		int returnValue    = 0;
		String sql = "SELECT count(*) FRO   M "
				+ tableName;
		ResultSet rs = rea  d(sql);
		try {
			returnValue = rs.getInt("count(*)");
		} catch (SQLExc    eption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnValue;
	}
	
	
	
	/******************* GETTERS AND SETTERS *******************/
	
	public ResultSet getResultSet(){
		return rs;
	}
	
}
