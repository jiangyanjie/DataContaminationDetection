package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Handles the db connection. It creates a connection to the databasename
 * given to the constructor, or creates a connection to a default database
 * (test.db) if none is given.
 * @author larba462
 *
 */
public class DatabaseObject {
	
	
	
	/**
	 * Class variables
	 */
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	/******************* CONSTRUCTORS *******************/
	
	/**
	 * Constructor creates the connection by calling openConnection
	 * And initiates the statement handler
	 * @param dbName
	 */
//	public DatabaseObject(String dbName){
//		
//		openConnection(dbName);
//		createStmt();
//	}
//	
	
	
	/**
	 * Constructor creates the connection by calling openConnection
	 * Since no parameter is given test.db is used as a default database
	 * And initiates the statement handler
	 */
	public DatabaseObject(){
		openConnection("Battleships.db");

		createStmt();
	}
	
	
	
	
	/******************* PRIVATE METHODS *******************/
	
	
	
	
	/**
	 * Opens a connection to the given dbName
	 * Saves the connection in the conn variable
	 * @param dbName
	 */
	private void openConnection(String dbName){
		Connection conn = null;
		try{
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbName);
		}catch(SQLException e){
			handleError(e);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		this.conn = conn;
	}
	
	
	
	/**
	 * Instantiates the statement
	 */
	private void createStmt(){
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			handleError(e);
		}
	}
	
	

	
	
	/******************* PROTECTED METHODS *******************/
	
	
	/******************* PUBLIC METHODS *******************/
	/**
	 * Writes to the given sql statement to the database
	 * If the table does not exist the table is created
	 * @param sql
	 */
	public void write(String sql){
		try{
			stmt.executeUpdate(sql);
		}catch (SQLException e){
			handleError(e);
		}
	}
	
	
	
	/**
	 * Runs the given sql then saves and returns
	 * the given ResultSet
	 * @return sql
	 */
	public ResultSet read(String sql){
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			handleError(e);
		}
		return rs;
	}
	
	
	
	/**
	 * Checks if the table exists
	 * @return boolean
	 */
	public boolean tableExists(String tableName){
		
		String sql = "SELECT count(*) FROM sqlite_master WHERE"
				+ "	type='table' AND"
				+ " name='" + tableName + "'";
		rs = read(sql);
		try{
			//Returns true if the count of matched tables are one or more
			return rs.getInt("count(*)") > 0 ? true : false;
		}catch(SQLException e){
			handleError(e);
		}
		return false; //This will never happen
	}
	
	
	/**
	 * Creates a table of given name and array of columns
	 * the given columns are suposed to be given in the following
	 * format:
	 * [columnNames][columnType]
	 * 
	 * Example:
	 * [ example ],[varchar(255)]
	 * 
	 */
	public void createTable(String tableName, String[][] columns){
		if(!tableExists(tableName)){
			String sql = "CREATE TABLE " + tableName + "(";
			for(int i = 0; i < columns.length; i++){
				sql +=  columns[i][0] + 
						" " + columns[i][1];
				sql += i == columns.length -1 ? "" : ", ";
			}
			sql += ")";
			System.out.println(sql);
			write(sql);
		}
		else{
			//If the table allready exists, shut down
			System.out.println("Table allready exists");
			System.exit(0);
		}
	}
	
	/**
	 * Closes the connection
	 */
	public void closeConnection(){
		try{
			conn.close();
		}catch(SQLException e){
			handleError(e);
		}
		
	}
	
	
	
	/**
	 * Handles the given SQLException
	 * @param e: SQLException
	 * @see SQLException
	 */
	public void handleError(SQLException e){
		System.err.println(e.getClass().getName() + ": " + e.getMessage());
		System.exit(0);
	}
	
	
	public int countRows(String tableName){
		int returnValue = 0;
		String sql = "SELECT count(*) FROM "
				+ tableName;
		ResultSet rs = read(sql);
		try {
			returnValue = rs.getInt("count(*)");
		} catch (SQLException e) {
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
