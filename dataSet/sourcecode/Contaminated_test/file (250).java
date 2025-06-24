package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@Deprecated
public class DataAccessObject {
	private  PreparedStatement verifyUserLogin,verifyTransaction,setCRNStatus,getCRNStatus;
									 
							 
	private  Connection conn;
	private  ResultSet rs;
	private  DataBaseConnectionProperties dbcp;
	
	/***Below constructor will take dabaseloc and driver name from
	DataBaseConnectionProperties  ****/
	 

public DataAccessObject() //default constructor
{	//getting database parameters from DataBaseConnectionProperties
	dbcp=DataBaseConnectionProperties.getInstance();
	String driverLoc=dbcp.getDatabaseLoc();
	String driver=dbcp.getDriver();
	try {
		Class.forName(driver);
		conn=DriverManager.getConnection(driverLoc,"root","root");
		
		verifyUserLogin=conn.prepareStatement("Select * from user where UserID=? and LoginPassword=?"); 				
		verifyTransaction=conn.prepareStatement("Select * from accountinfo where AccountNumber=? and TransactionPassword=?");
		setCRNStatus=conn.prepareStatement("update user set CRNStatus=? where UserID=?");
		getCRNStatus=conn.prepareStatement("Select CRNStatus from user where UserID=? and CRNStatus=?");
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public  boolean authenticateUser(String userID,String loginpassword)
{
	try {
		verifyUserLogin.setString(1, userID);
		verifyUserLogin.setString(2, loginpassword);
		rs=verifyUserLogin.executeQuery();
		if(rs.next())
		return true;
		else
		return false;
		
		} 
		catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
		}
	}//checkUserLogin ends
public  boolean authenticateTransaction(long accountNum,String transactionPassword)
{
	try {
		verifyTransaction.setLong(1, accountNum);
		verifyTransaction.setString(2, transactionPassword);
		rs=verifyTransaction.executeQuery();
		if(rs.next())
		{System.out.println("Transaction authentication successful");return true;}
		else
		{
			System.out.println("Transaction authentication unsuccessful");
			return false;
		}}//try ends 
		catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
		}
		finally
		{
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
}//check Transaction ends


//to lock a user!
public void lockUser(String userID) {
	
	try {
		setCRNStatus.setString(1,"LOCKED");
		setCRNStatus.setString(2,userID);
		setCRNStatus.executeUpdate();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public boolean isUserLocked(String userID)
{
	try {
		getCRNStatus.setString(1,userID);
		getCRNStatus.setString(2,"LOCKED");
		rs=getCRNStatus.executeQuery();
		if(rs.next())
		{
		rs.close();
		return true;
		}
		else
			return false;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
}


}//class DataAccessObject ends.

