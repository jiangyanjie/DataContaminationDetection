package DB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import DB.DBHandler;
import AppCore.User;

/**
 * AppCore DBUserToolbox
 */
public class DBUserToolbox extends DBToolbox {
	
	public DBUserToolbox ()
	{
		super();
		_dbName 		= "myam-db";
		_dbHandler 	= new DBHandler(_dbName);
	}

	public String userExists(String email, String phone, String login)
	{
		
		Connection conn 				= getConn();
		CallableStatement cs 		= null;
		ResultSet rs 						= null;
		String exists						= "";
		try {
			
			cs = conn.prepareCall("{CALL userExists(?,?,?)}");
			cs.setString("pEmail", email);
			cs.setString("pPhone", phone);
			cs.setString("pLogin", login);
			rs = cs.executeQuery();		
			
			while(rs.next())
			{
				if(rs.getBoolean("loginExists"))
				{
					exists+="loginExists,";
				}
				if(rs.getBoolean("phoneExists"))
				{
					exists+="phoneExists,";
				}
				if(rs.getBoolean("emailExists"))
				{
					exists+="emailExists,";
				}
			}
		} catch (SQLException e) {
			System.out.println("Error in userExists:" +e.getMessage());
		} finally {		
			closeConn(conn);
		}
		
		return exists;
	}
	
	public ArrayList<User> getUsers()
	{
		
		Connection conn 				= getConn();
		CallableStatement cs 		= null;
		ResultSet rs 						= null;
		ArrayList<User> users 	= null;
		
		try {
			
			cs = conn.prepareCall("{CALL getUsers()}");
			rs = cs.executeQuery();
			
			users = new ArrayList<User>();
			
			while(rs.next())
			{
				users.add(new User(rs.getInt("id"),rs.getString("login"),rs.getString("email"),rs.getString("phone"),rs.getString("firstName"),rs.getString("lastName"),rs.getTimestamp("lastLoginDate")));
			}
		} catch (SQLException e) {
			System.out.println("Error in getUsers:" +e.getMessage());
		} finally {		
			closeConn(conn);
		}
		
		return users;
	}
	
	public Boolean hasApprovedContact(int srcUserId, int dstUserId)
	{
		Connection conn 			= getConn();
		CallableStatement cs 	= null;
		ResultSet rs 					= null;
		Boolean result 				= false;
		
		try {
			cs = conn.prepareCall("{CALL hasApprovedContact(?,?)}");
			cs.setInt("pSrcUserId", srcUserId);
			cs.setInt("pDstUserId", dstUserId);
			
			rs = cs.executeQuery();
			while(rs.next())
			{
				result = rs.getBoolean("hasApprovedContact");
			}
			
		} catch (SQLException e) {
			System.out.println("Error in hasApprovedContact:" +e.getMessage());
		} finally {		
			closeConn(conn);
		}
		
		return result;
	}
	
	public ArrayList<User> getContacts(int id)
	{
		
		Connection conn 				= getConn();
		CallableStatement cs 		= null;
		ResultSet rs 						= null;
		ArrayList<User> users 	= null;
		
		try {
			cs 		= conn.prepareCall("{CALL getContacts(?)}");
			cs.setInt("pUserId", id);
			rs 		= cs.executeQuery();
			
			users = new ArrayList<User>();
			
			while(rs.next())
			{
				
				User user = new User(rs.getInt("id"),rs.getString("login"),rs.getString("email"),rs.getString("phone"),rs.getString("firstName"),rs.getString("lastName"),rs.getTimestamp("lastLoginDate"));
					user.setApprovalStatus(rs.getInt("approvalStatus"));
				users.add(user);
					
			}
		} catch (SQLException e) {
			System.out.println("Error in getContacts:" +e.getMessage());
		} finally {		
			closeConn(conn);
		}
		
		return users;
	}
	
	public ArrayList<User> getContactRequests(int id)
	{
		
		Connection conn 				= getConn();
		CallableStatement cs 		= null;
		ResultSet rs 						= null;
		ArrayList<User> users 	= null;
		
		try {
			cs 		= conn.prepareCall("{CALL getContactRequests(?)}");
			cs.setInt("pUserId", id);
			rs 		= cs.executeQuery();
			
			users = new ArrayList<User>();
			
			while(rs.next())
			{
				users.add(new User(rs.getInt("id"),rs.getString("login"),rs.getString("email"),rs.getString("phone"),rs.getString("firstName"),rs.getString("lastName"),rs.getTimestamp("lastLoginDate")));
			}
		} catch (SQLException e) {
			System.out.println("Error in getContactRequests:" +e.getMessage());
		} finally {		
			closeConn(conn);
		}
		
		return users;
	}
	
	public int getContactRequestsCount(int id)
	{
		
		Connection conn 			= getConn();
		CallableStatement cs 	= null;
		ResultSet rs 					= null;
		int count			 			= 0;
		
		try {
			cs 		= conn.prepareCall("{CALL getContactRequestsCount(?)}");
			cs.setInt("pUserId", id);
			rs 		= cs.executeQuery();
			
			while(rs.next())
			{
				count = rs.getInt("contactRequestsCount");
			}
		} catch (SQLException e) {
			System.out.println("Error in getContactRequestsCount:" +e.getMessage());
		} finally {		
			closeConn(conn);
		}
		
		return count;
	}
	
	public ArrayList<User> findContacts(int userId, String searchString)
	{
		
		Connection conn 				= getConn();
		CallableStatement cs 		= null;
		ResultSet rs 						= null;
		ArrayList<User> users 	= null;
		
		try {
			cs 		= conn.prepareCall("{CALL findContacts(?,?)}");
			cs.setInt("pUserId", userId);
			cs.setString("pSearchString", searchString);
			
			rs 		= cs.executeQuery();
			
			users = new ArrayList<User>();
			
			while(rs.next())
			{
				users.add(new User(rs.getInt("id"),rs.getString("login"),rs.getString("email"),rs.getString("phone"),rs.getString("firstName"),rs.getString("lastName"),rs.getTimestamp("lastLoginDate")));
			}
		} catch (SQLException e) {
			System.out.println("Error in getContacts:" +e.getMessage());
		} finally {		
			closeConn(conn);
		}
		return users;
	}

	public Boolean addUser(String firstName, String lastName, String email, String phone, String login, String password)
	{
		
		Connection conn 			= getConn();
		CallableStatement cs 	= null;
		Boolean rs					= false;
		
		try {
			
			cs = conn.prepareCall("{CALL addUser(?,?,?,?,?,?)}");
			cs.setString("pFirstName", firstName);
			cs.setString("pLastName", lastName);
			cs.setString("pEmail", email);
			cs.setString("pPhone", phone);
			cs.setString("pLogin", login);
			cs.setString("pPassword", password);
			rs = cs.execute();			
			
		} catch (SQLException e) {
			System.out.println("Error in addUser:" +e.getMessage());
		} finally {		
			closeConn(conn);
		}
		
		return rs;
	}
	
	public Boolean addContact(int srcUserId, int dstUserId)
	{
		
		Connection conn 			= getConn();
		CallableStatement cs 	= null;
		Boolean rs					= false;
		try {
			
			cs = conn.prepareCall("{CALL addContact(?,?)}");
			cs.setInt("pSrcUserId", srcUserId);
			cs.setInt("pDstUserId", dstUserId);
			rs = cs.execute();			
			
		} catch (SQLException e) {
			System.out.println("Error in addContact:" +e.getMessage());
		} finally {		
			closeConn(conn);
		}
		return rs;
	}
	
	public Boolean deleteContact(int srcUserId, int dstUserId)
	{
		
		Connection conn 			= getConn();
		CallableStatement cs 	= null;
		Boolean rs					= false;
		try {
			
			cs = conn.prepareCall("{CALL deleteContact(?,?)}");
			cs.setInt("pSrcUserId", srcUserId);
			cs.setInt("pDstUserId", dstUserId);
			rs = cs.execute();			
			
		} catch (SQLException e) {
			System.out.println("Error in deleteContact:" +e.getMessage());
		} finally {		
			closeConn(conn);
		}
		return rs;
	}
	
	public String getName(Integer id)
	{
		Connection conn 			= getConn();
		CallableStatement cs 	= null;
		ResultSet rs 					= null;
		String name 					= "";
		
		try {
			
			cs = conn.prepareCall("{CALL getName(?)}");
			cs.setInt("pUserId", id);		
			rs = cs.executeQuery();	
			
			while(rs.next())
			{
				name = rs.getString("name");
			}
		} catch (SQLException e) {
			System.out.println("Error in getName:" +e.getMessage());
		} finally {		
			closeConn(conn);
		}		
		return name;
	}
	
	public String getLogin(Integer id)
	{
		Connection conn 			= getConn();
		CallableStatement cs 	= null;
		ResultSet rs 					= null;
		String login 					= "";
		
		try {
			
			cs = conn.prepareCall("{CALL getLogin(?)}");
			cs.setInt("pUserId", id);		
			rs = cs.executeQuery();	
			
			while(rs.next())
			{
				login = rs.getString("login");
			}
		} catch (SQLException e) {
			System.out.println("Error in getLogin:" +e.getMessage());
		} finally {
			closeConn(conn);
		}		
		return login;
	}
	
	public Timestamp getLastLogin(Integer id)
	{
		
		Connection conn 			= getConn();
		CallableStatement cs 	= null;
		ResultSet rs 					= null;
		Timestamp lastLogin 	= null;
		
		try {
			
			cs = conn.prepareCall("{CALL getLastLogin(?)}");
			cs.setInt("pUserId", id);		
			rs = cs.executeQuery();
			
			while(rs.next())
			{
				lastLogin = rs.getTimestamp("lastLoginDate");
			}
		} catch (SQLException e) {
			System.out.println("Error in getLastLogin:" +e.getMessage());
		} finally {		
			closeConn(conn);
		}

		return lastLogin;
	}
	
	
	
	public Boolean updateUserLastLogin(Integer id)
	{
		
		Connection conn 			= getConn();
		CallableStatement cs 	= null;
		Boolean rs					= false;
		try {
			
			cs = conn.prepareCall("{CALL updateUserLastLogin(?)}");
			cs.setInt("pUserId", id);
			rs = cs.execute();
			
		} catch (SQLException e) {
			System.out.println("Error in updateUserLastLogin:" +e.getMessage());
		} finally {
			closeConn(conn);
		}
		return rs;
	}
	
	public boolean checkCredentials(String login, String password)
	{
		boolean match 			= false;
		
		Connection conn 			= getConn();
		CallableStatement cs 	= null;
		ResultSet rs 					= null;
		
		try {
			
			cs = conn.prepareCall("{CALL checkCredentials(?,?)}");
			cs.setString("pLogin", login);
			cs.setString("pPassword", password);
			rs = cs.executeQuery();
			
			while(rs.next())
			{
				match = rs.getBoolean("valid");
			}
		} catch (SQLException e) {
			System.out.println("Error in checkCredentials:" +e.getMessage());
		} finally {		
			closeConn(conn);
		}
		
		return match;
	}
	
	public int getIdFromLogin(String login)
	{
		
		Connection conn 			= getConn();
		CallableStatement cs 	= null;
		ResultSet rs 					= null;
		int id	 						= 0;
		
		try {
			
			cs = conn.prepareCall("{CALL getIdFromLogin(?)}");
			cs.setString("pLogin", login);
			rs = cs.executeQuery();
			
			
			while(rs.next())
			{
				id = rs.getInt("id");
			}
		} catch (SQLException e) {
			System.out.println("Error in getIdFromLogin:" +e.getMessage());
		} finally {		
			closeConn(conn);
		}
		
		return id;
	}
	
	public User getUser(int id)
	{
		
		
		Connection conn 			= getConn();
		CallableStatement cs 	= null;
		ResultSet rs 					= null;
		User user 					= null;
		
		try {
			
			cs = conn.prepareCall("{CALL getUser(?)}");
			cs.setInt("pUserId", id);
			rs = cs.executeQuery();
			
			while(rs.next())
			{
				user = new User(rs.getInt("id"),rs.getString("login"),rs.getString("password"),rs.getString("email"),rs.getString("phone"),rs.getString("firstName"),rs.getString("lastName"),rs.getTimestamp("lastLoginDate"));;
			}
		} catch (SQLException e) {
			System.out.println("Error in getUser:" +e.getMessage());
		} finally {		
			closeConn(conn);
		}
		
		return user;
	}
	
	public User getUser(String login)
	{
		return getUser(getIdFromLogin(login));
	}

}
