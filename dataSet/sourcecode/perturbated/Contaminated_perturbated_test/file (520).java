

package com.xaut.util;




import java.sql.Connection;





import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;












/**
 * è¯¥ç±»ä½ç¨ï¼æ°æ®åºæä½ï¼å¢å æ¥æ¹
 * 
 * ä¸»è¦æ¹æ³ï¼excutesqlï¼closeConnåfindsql
 * closeConnï¼å³é­æ°æ®åºè¿æ¥
 * excutesqlï¼éç¨äºå®ç°å¢å æ¹æä½ï¼ä¸éç¨äºæ¥è¯¢ï¼ä¸ä¸æ¬¡æ§å¯ä»¥æ§è¡å¤æ¡sqlè¯­å¥


 * findsqlï¼ç¨äºæ¥è¯¢åè½çæ¹æ³ï¼ä¸åªéç¨äºæ¥è¯¢
 * @author anyang




 *
 */
public class DBOperation {










	public Connection con=null;
	public Statement st=null;
	public ResultSet rs=null;






	
	//åªéç¨äºå¢å æ¹æä½ï¼ä¸éç¨äºæ¥è¯¢æä½
	public boolean excutesql(String[] sql) {
		




		boolean bool = true;
		
		DButil util = new DButil();



		Connection con=util.openConnection();
		
		try {
//			èªå¨æäº¤ï¼å¨åè®°å½æ´æ°æ¶ï¼ç³»ç»ä¼èªå¨æäº¤,ä¸è½ä¿æäºå¡çä¸è´æ§ï¼ä¹å°±ä¸è½ä¿è¯æ°æ®å®æ´ã


//			æå¨æäº¤ï¼å®åæäºå¡å¤çå°ç±ä½ æ¥å®æï¼å¨åçå¼å¸¸æ¶ï¼å¯ä»¥è¿è¡äºå¡åæ»ãä¿æäºå¡çä¸è´ã
			con.setAutoCommit(false);










			


		    st=con.createStatement();








		   





			for(int i = 0; i < sql.length; i++){
				
//				System.out.println(sql[i]);
				st.addBatch(sql[i]);//å½è¦æ§è¡å¤æ¡sqlè¯­å¥æ¶ï¼å¯ä»¥éè¿jdbcçæ¹å¤çæºå¶å®æï¼è¿æ ·å¯ä»¥æé«æ§è¡æçã
			}













			





			st.executeBatch();//æ§è¡æ¹å¤ç
			
			con.commit();

			



//			System.out.println("æ§è¡æåï¼");
			







		} catch (SQLException e) {
			
			try {
				
//				System.out.println("æ§è¡å¤±è´¥ï¼");












				
				bool=false;
				
				con.rollback();
				
			} catch (SQLException e1) {
				
				e1.printStackTrace();


			}
			e.printStackTrace();







			
		}finally{







			



			if(st!=null){
				try {













					




					st.close();




					
					if(con!=null){
						
						con.close();






					}
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
										





			}
		}









		return bool;
	}
	






	//ç¨äºæ¥è¯¢åè½çæ¹æ³ï¼ä¸åªéç¨äºæ¥è¯¢
	public ResultSet findsql(String sql) {
		
		try {



			
			DButil util = new DButil();
			con=util.openConnection();
			
			st=con.createStatement();

			




			rs=st.executeQuery(sql);

					





		} catch (SQLException e) {



			
			e.printStackTrace();
		}


		
	 return rs;




	}
	

	//æ¥è¯¢æçºµåï¼å³é­æ°æ®åºè¿æ¥
	public void closeConn() {
		
		if(rs!=null)
		{
			try {
				
				rs.close();
				
				if(st!=null){
					
					st.close();
					
					if(con!=null){
						
						con.close();
					}
				}
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
		}
	}
	
	
}
