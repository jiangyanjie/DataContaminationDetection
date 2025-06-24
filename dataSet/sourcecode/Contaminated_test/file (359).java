 package project;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Kamyar_Aflaki Ali_Khalili Uraz_Seddigh 
 *
 */
public class DataPopulator {


	private final static String url = "jdbc:mysql://localhost:3306/rssDB";
	private final static String user = "root";
	private final static String password = "";
	private static Connection connection = null;
	private static PreparedStatement preparedStatement = null;
	private static Feed feed;



	/**
	 * @param username Username.
	 * @param password Password.
	 */
	public static void createUsers(String username, String password){
		if(!RWToDatabase.nameExists(username)){
			startConnection();
			try {
				preparedStatement = connection.prepareStatement("INSERT INTO user (name, password) VALUES (?,?);");
				preparedStatement.setString(1, username);
				preparedStatement.setString(2, password);
				preparedStatement.executeUpdate();
				
				preparedStatement = connection.prepareStatement("INSERT INTO user_defines_category (user_id,category_id) VALUES ((SELECT id FROM user WHERE name = ?),1);");
				preparedStatement.setString(1, username);
				preparedStatement.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
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
	public static void createFeed(File input){
		startConnection();
		try {
			feed = FeedParser.ParseFeed(input);
			preparedStatement = connection.prepareStatement("INSERT INTO feed (title,path) values (?,?)");
			preparedStatement.setString(1, feed.getFeedTitle());
			preparedStatement.setString(2, feed.getFeedPath().toString());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeConnection();
		}
	}
	public static void createImportedCategory(){
		startConnection();
		try {
			preparedStatement = connection.prepareStatement("INSERT INTO category (name) VALUES ('Imported')");
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeConnection();
		}
	}

	private static void startConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
