package com.wipro.frs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.wipro.frs.util.DBUtil;
import com.wipro.frs.entity.CredentialsBean;

public class CredentialsDAO {
	
	
      @SuppressWarnings("finally")
	public String createUser(CredentialsBean cb) {
    	  Connection c = DBUtil.getDBConnection("Type4");
  		PreparedStatement pst;
  		
  		String tablename1 = "FRS_TBL_User_Credentials";
  		try{
    	  pst = c.prepareStatement("insert into " + tablename1
					+ " values(?,?,?,?)");
    			pst.setString(1, cb.getId());
			pst.setString(2, cb.getPassword());
			pst.setString(3,"C");
			pst.setInt(4,0);
		    int i= pst.executeUpdate();
		    if(i!=0){
		    return cb.getType();
		    }
  		}catch (Exception e) {
			
			return null;
		} finally {
			 return cb.getType();
		}
		
  		}

       public boolean updateUser(CredentialsBean userbean) {
         if(userbean.getLoginStatus()==0){
        	 userbean.setLoginStatus(1);
         }
    	   return true;
       }

        public boolean deleteUser(ArrayList<String> userId) {
        return false;
          }
        
        public int changeLogin(CredentialsBean credentialsBean,int loginStatus) {
      		Connection connec;
      		connec = DBUtil.getDBConnection("Type4");
           		

      		if (connec != null) {
      			try {			
      				
      						
      				PreparedStatement psmt=connec.prepareStatement("update frs_tbl_user_credentials set loginstatus='"+loginStatus+ "' where userid='"+credentialsBean.getId()+"'");
      			
      								int j=psmt.executeUpdate();
      				
      				
      			} catch (Exception e) {
      				      			}
      			return 1;
      		} else
      			return 0;
      	}
      	
        public ArrayList<CredentialsBean> findAll() {
       return null;
        }

  public CredentialsBean findById(String userId) {
	  CredentialsBean ob = new CredentialsBean();
	  Connection c = DBUtil.getDBConnection("Type4");

	  PreparedStatement psmt;
	  ResultSet rs = null; 
		
	  if(c!=null){
		 
		  try{
			  
			  psmt = c.prepareStatement("select Userid,Password,Usertype,Loginstatus from FRS_TBL_User_Credentials where Userid ="+"?");	
			
			  psmt.setString(1,userId );
			rs=psmt.executeQuery();	
				while(rs.next()){
				  ob.setId(rs.getString(1));
				  ob.setPassword(rs.getString(2));
				  ob.setType(rs.getString(3));
				  ob.setLoginStatus(rs.getInt(4));}
				
				
		  }  
			  catch(Exception e){
					
				}
			
  
  }
	  return ob;                           
  }
  public boolean changePassword(CredentialsBean userbean) 
	{
		PreparedStatement pst;
		Connection conn;
		try {
			conn = DBUtil.getDBConnection("Type4");
		
			pst = conn.prepareStatement("UPDATE frs_tbl_user_credentials SET password =(?) WHERE userid=(?)");
			
			pst.setString(1, userbean.getPassword());
			pst.setString(2, userbean.getId()); 
			pst.executeUpdate();	
		}
		catch (Exception e) {
			
		}
		
			return true;
	}

	public int changeLogout(String userId,int loginStatus) {
		Connection connec;
		connec = DBUtil.getDBConnection("Type4");
	
		

		if (connec != null) {
			try {												
				PreparedStatement psmt=connec.prepareStatement("update frs_tbl_user_credentials set loginstatus='"+loginStatus+ "' where userid='"+userId+"'");
				int j=psmt.executeUpdate();
				
			} catch (Exception e) {
				
			}
			return 1;
		} else
			return 0;
	}
}