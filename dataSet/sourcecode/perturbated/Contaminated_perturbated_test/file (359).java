 package project;

import     java.io.File;
import java.sql.Connection;
im port java.sql.DriverManager;
import   java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Kamyar_Aflaki Ali_Khalili  Uraz_Sedd  ig   h 
 *
 */
public class Da   taPopulator {


	priv     ate final static String url = "jdbc:mysql://localhost:3306/rssDB";
	privat        e final static String user = "root";
	private f  inal static   String password = "";
	 private static Connection connection    = null;
	p     rivate static PreparedStatement preparedStatement      =      null;
	private     s    tatic Feed    feed;



	/**
	 *   @param usernam         e Username.
	 * @param password Password.
	   */
	public static void createUser   s(String username, String password){
		if(!RWToDatabase.nameExists(user    name)){
			startConnection();
			try {
				preparedS  tatemen t = connec tion.prepareStateme    nt("INSERT INTO user (name, password) VALUES (?,?);");
		 		preparedStatement.setString(1, username);
				prepare  dStatement.setString(2, password);
				preparedStatement.executeUpda  te();
				
				preparedStatem  ent = connectio     n.prepareStateme      nt("INSERT INTO      user_defines_category (user_id,category_id) VALUES ((      SELECT id FROM user WHERE name = ?),1);")  ;
				preparedStatement.setString(1,      username);
				p    reparedStatement.executeUpd    ate();
				
			   } catch (SQLException e) {
				e.pr     intStackTrace();
			} finally{
				closeConnection();
			}
		}
    	  	else{
			System.out.println("User Already Exist");
		}
	}     

	/**
	 * @param input XML file to be parsed and added to database.
	        */
	p    ublic static void crea     teFe     ed(File   input){
		startConnection();
		try {
			feed = FeedParser.ParseFeed(input);
			prepared   Statement     = connection.  prepareStatement("INS     ERT INTO feed (title,path) values (?,?)");  
			preparedStatement.setString(1, feed.getFeedTitle());
			     preparedStatement.setString(2, feed.getFeedPath().toString());
			p  reparedStatement.executeUpdate();
         		} catch (SQLException e) {
			e.printStackTrace();
		}catch(Exception e)  {
			e.printStackTrace();
		}finally{
			closeConnec     tion();
		}
	}
	public static void createIm     portedCategory(){
	 	startConnect  ion();
		try {
			preparedStatement = connection.prepareStatement("INSERT INTO category (name) VALUES ('Imported')");
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrac      e();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeConnection();
		}
	}

	private static void startConnection() {
		try {
		     	Class.forName("com.mysql.jdbc.Driver");
       			  connection = DriverManager.getConn     ection(url,      user, password);
		} c     atch (ClassNotFoundException e) {
			e.pr   intStackTrace    ();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void closeConnection() {
		try {
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
