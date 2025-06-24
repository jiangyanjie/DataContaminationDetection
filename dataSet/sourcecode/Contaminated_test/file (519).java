package DB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import AppCore.Msg;
import DB.DBHandler;


public class DBMsgToolbox extends DBToolbox {
	
	public DBMsgToolbox ()
	{
		super();
		_dbName = "myam-db";
		_dbHandler = new DBHandler(_dbName);
	}
	
	public ArrayList<Msg> getMessages(int srcUserId, int dstUserId)
	{
		Connection conn 					= getConn();
		CallableStatement cs 			= null;
		ResultSet rs 							= null;
		ArrayList<Msg> messages 	= null;
		
		
		try {
			cs = conn.prepareCall("{CALL getMessages(?,?)}");
			cs.setInt("pSrcUserId", srcUserId);
			cs.setInt("pDstUserId", dstUserId);
			rs = cs.executeQuery();		
		
			messages = new ArrayList<Msg>();
		
			while(rs.next())
			{
				messages.add(new Msg(rs.getInt("id"),rs.getInt("srcUserId"),rs.getInt("dstUserId"),rs.getString("content"),rs.getTimestamp("sentDate"),rs.getBoolean("isDelivered")));
			}
		} catch (SQLException e) {
			System.out.println("Error in getMessages:" +e.getMessage());
		} finally {
			closeConn(conn);	
		}
		
		
		return messages;
	}
	
	public int sendMessage(int srcUserId, int dstUserId, String content)
	{
		Connection conn 			= getConn();
		CallableStatement cs 	= null;
		ResultSet rs 					= null;
		int id							= 0;
		
		try {
			cs = conn.prepareCall("{CALL sendMessage(?,?,?,?)}");
			cs.setInt("pSrcUserId", srcUserId);
			cs.setInt("pDstUserId", dstUserId);
			cs.setInt("pContentTypeId", 1);
			cs.setString("pContent", content);			
			
			rs = cs.executeQuery();
			while(rs.next())
			{
				id = rs.getInt("id");
			}
			
		} catch (SQLException e) {
			System.out.println("Error in sendMessage:" +e.getMessage());
		} finally {
			closeConn(conn);	
		}
		
		return id;
	}
	
	public int getNonDeliveredMessageCount(int srcUserId, int dstUserId)
	{
		Connection conn 			= getConn();
		CallableStatement cs 	= null;
		ResultSet rs 					= null;
		int count 						= 0;
		
		try {
			cs = conn.prepareCall("{CALL getNonDeliveredMessageCount(?,?)}");
			cs.setInt("pSrcUserId", srcUserId);
			cs.setInt("pDstUserId", dstUserId);		
			
			rs = cs.executeQuery();
			while(rs.next())
			{
				count = rs.getInt("count");
			}
			
		} catch (SQLException e) {
			System.out.println("Error in getNonDeliveredMessageCount:" +e.getMessage());
		} finally {
			closeConn(conn);	
		}
		
		return count;
	}
	
	public Boolean setMessageDelivered(int msgId)
	{
		Connection conn 			= getConn();
		CallableStatement cs 	= null;
		Boolean rs 					= false;
		
		try {
			cs = conn.prepareCall("{CALL setMessageDelivered(?)}");
			cs.setInt("pMsgId", msgId);			
			
			rs = cs.execute();
			
			
		} catch (SQLException e) {
			System.out.println("Error in setMessageDelivered:" +e.getMessage());
		} finally {
			closeConn(conn);	
		}
		
		return rs;
	}

	

}
