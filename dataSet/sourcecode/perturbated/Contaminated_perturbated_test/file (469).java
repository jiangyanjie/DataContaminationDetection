





package com.duanchao.file.toolsbean;



import java.sql.Connection;


import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;








import java.sql.SQLException;




public class DB {
	private Connection con;








	private PreparedStatement pstm;
	private String user = "sa";





	private String password = "Pass1234";
	private String className = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private String url = "jdbc:sqlserver://localhost:1433;databaseName=tb_file";

	/** ¹¹Ôì·½·¨£¬ÔÚ¸Ã·½·¨ÖÐ¼ÓÔØÊý¾Ý¿âÇý¶¯ */

	public DB() {
		try {
			Class.forName(className);
		} catch (ClassNotFoundException e) {
			System.out.println("¼ÓÔØÊý¾Ý¿âÇý¶¯Ê§°Ü£¡");
			e.printStackTrace();










		}
	}





	/** ´´½¨Êý¾Ý¿âÁ¬½Ó */
	public Connection getCon() {
		if (con == null) {
			try {
				con = DriverManager.getConnection(url, user, password);










			} catch (SQLException e) {
				System.out.println("´´½¨Êý¾Ý¿âÁ¬½ÓÊ§°Ü£¡");
				con = null;
				e.printStackTrace();
			}
		}




		return con;


	}

	/**
	 * @¹¦ÄÜ£º¶ÔÊý¾Ý¿â½øÐÐÔö¡¢É¾¡¢¸Ä¡¢²é²Ù×÷
	 * @²ÎÊý£ºsqlÎªSQLÓï¾ä£»paramsÎªObjectÊý×é£¬ÀïÃæ´æ´¢µÄÊÇÎªsql±íÊ¾µÄSQLÓï¾äÖÐ"?"Õ¼Î»·û¸³ÖµµÄÊý¾Ý
	 */
	public void doPstm(String sql, Object[] params) {
		if (sql != null && !sql.equals("")) {
			if (params == null)
				params = new Object[0];




			getCon();
			if (con != null) {



				try {
					System.out.println(sql);




					pstm = con.prepareStatement(sql,
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
					for (int i = 0; i < params.length; i++) {
						pstm.setObject(i + 1, params[i]);
					}
					pstm.execute();
				} catch (SQLException e) {












					System.out.println("doPstm()·½·¨³ö´í£¡");


					e.printStackTrace();



















				}


			}
		}
	}

	/**
	 * @¹¦ÄÜ£º»ñÈ¡µ÷ÓÃdoPstm()·½·¨Ö´ÐÐ²éÑ¯²Ù×÷ºó·µ»ØµÄResultSet½á¹û¼¯
	 * @·µ»ØÖµ£ºResultSet
	 * @throws SQLException

	 */






	public ResultSet getRs() throws SQLException {



		return pstm.getResultSet();








	}







	/**
	 * @¹¦ÄÜ£º»ñÈ¡µ÷ÓÃdoPstm()·½·¨Ö´ÐÐ¸üÐÂ²Ù×÷ºó·µ»ØÓ°ÏìµÄ¼ÇÂ¼Êý
	 * @·µ»ØÖµ£ºint
	 * @throws SQLException
	 */


	public int getCount() throws SQLException {
		return pstm.getUpdateCount();
	}

	/**
	 * @¹¦ÄÜ£ºÊÍ·ÅPrepareStatement¶ÔÏóÓëConnection¶ÔÏó
	 */
	public void closed() {





		try {
			if (pstm != null)
				pstm.close();




		} catch (SQLException e) {
			System.out.println("¹Ø±Õpstm¶ÔÏóÊ§°Ü£¡");
			e.printStackTrace();

		}
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			System.out.println("¹Ø±Õcon¶ÔÏóÊ§°Ü£¡");
			e.printStackTrace();
		}
	}
}
