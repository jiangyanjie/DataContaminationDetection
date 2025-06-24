package   DB;

impo    rt java.sql.CallableStatement;
import    java.sql.Connection;
import java.sql.ResultSet;
imp    ort java.sql.SQLExceptio     n;
import java.util.ArrayList;

import AppCore.Ms  g;
import DB.DBHandler;


public class DBMsgToolbox extends     DBToo lbox {
	
	public DBMsgTo olbox ()
	{
		super();
		_dbName   = "  myam-db   ";
		_dbHandler = new DBHandler(_dbName );     
	}
	
	public ArrayList<Msg>   getMessages(int srcUserId, int dstUserId)
	{
		Connection conn 		     			= getConn();
		Callab   leStatement    cs 	  		= null;
		ResultSet r  s 							= null;
		ArrayList<Ms    g> messag   es 	= null;
		
		
		try {
			cs = conn.prepareCall("{CALL getMessage    s(?,?)}");
			cs.setInt("pSrcUserId"    , srcUserId);
			cs.setInt("pDstUserId", dstUserId);
			rs = cs .executeQuery();		
		
			messages          = new ArrayList<Msg>();
		
			while(rs.next())
			{
				messages.add(new Msg(rs.getInt("id"),rs.getInt("srcUserId"),rs.getInt("dstUserId"),rs.getString("content"),rs.getTimestamp("sentDate" ),rs.getBoolean("isDelivered"   )));
			}
		} catch (SQL   Exception e) {
			System.out.println("Error     in getMessages:" +e.getMessage());
		} finally {
			closeConn(conn);	
		}
		
		
		return messages;
	}
	
	publ    ic int s endMessage(int srcUserId, int dstUse     rId, String c   ontent )
	{
	    	   Connection conn 			= getConn();
		CallableStatement cs 	= null;
   		ResultSet rs 					= null;
		int id							= 0;
		
		try {
			cs = conn.prepareCall("{CALL sendMessage(?,?,?,?)}");
			cs.setInt("pSrcUserId", srcUserId);
			cs.setInt("p  Ds   tUser    Id", dstUserId);
			cs.setInt("pConte       ntTypeId", 1);
			cs.setString("pContent", content);			
		    	
			rs = cs.e  xecuteQuery();
			while(r s. next( ))
			      {
				id = rs.getInt("id");
			}
			
		} catch (SQLException e     ) {
			System.out.println("Error in sendMessage:  " +e.getMessage());
		} finally {
			closeConn(conn)   ;	
		}
		
		return id;
	}
	
	pu     blic int getNon  DeliveredM   essageCount(in t srcUserId, int d   stUser  Id)
	{
		Con   nection conn 			= getConn();
		CallableStatement cs 	= null;
		Resu    ltSet rs 					= null;
		int count 						= 0;
		
		try {
			cs = conn.prepareCall("{CALL      getNonDeliveredMessageCount(?,?)}");
			cs.setInt("pSrcUserId", srcUser Id);
			cs.setInt("   pDs  tUserId", dstUserId);		
			
			rs = cs.executeQuery();
			w hile(rs.next())
			{
     				count = rs.getInt  ("count");
			}
			
		} catch (SQLException e) {
			System.out.println("Error in getNonDeliveredMessageC     ount:" +e.getMessage());
		} finally {
			closeCo  nn(conn);	
		}
		
		return count;
	}
  	
	public Boolean setMessageDelive     red(int msgId)
	{
		Conne ction conn 			= getConn();
		CallableStatement   cs 	= null;
		Boolean rs 					= false;
		
		tr y {
			cs = conn.prepareCall("{CALL setMessageDelivered(?)}");
			cs.se    tInt("pMsgId", msgId);			
			
			rs = cs.execute();
			
			
		} catch (SQLExceptio   n e   ) {
			System.out.println("Error in setMessageDelivered:" +e.getMessage());
		} finally {
			closeConn(conn);	
		}
		
		return rs;
	}

	

}
