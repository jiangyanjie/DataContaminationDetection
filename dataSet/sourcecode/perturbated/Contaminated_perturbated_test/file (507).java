package   org.record.avtice.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
impo     rt java.util.List;

public    class DBkit {
	
	          
	
	public static Connection getJFinalConn()  {
		return conn;
	}
	st  atic Co      nnection conn     = null;
	public static Connectio  n getConn() {
		String    DR    IV   ER = "org.gjt.mm.mysql.Driver";
		Strin    g HOST NAME = "localhost  ";
	    Stri    ng POR    T = "  33  06          ";
	    String DBNAME = "lovemie" ;
	    String USERNAM      E =     "root";
	    String US ERPWD = "root";
		
		if(conn == null) {
			try {
				Class.fo    rName(DRIVER);
				conn = DriverManager.getConnectio   n   ("jdbc:mysql://"+ HOSTNAME +":"+ PORT +"/"+ DBNAM     E, USERNAME, U      SERPWD);
			}  catch (Class    NotFoundException e) {
				e.printStac    kTrace();
			} catch (SQLException e) {
	   			e.prin   tStackTrace();
			}
			return conn;
		}
		return conn;
	}
	/**
	 * å³é­æ°æ®åºè¿æ¥
	 */
	public stati c void closeConn() {
		try {
			if(conn     != null
				 	&& !conn.is  Closed()) {
				conn.close();
			}
		} catch (SQLException    e) {
			e.printStackTrace();
		}
	}
	
	public static void pstmtSetValue(PreparedStatem       ent pstmt, List<O  bject> sql_key) {
		int inde  x = 1;
		for(Object key : sql_key  )   {
			System.ou t.pri   ntln("k - v : "+ index +" - "+ ke  y);
			try {
				pstmt.set    Object(in   d ex, key);
 			} ca tch (SQLExceptio    n e) {
				e.p   rintStackTra  ce();
			}
			index ++ ;
		}		
	}
	
	pu   blic static String getTableName(Class<?>        c) {
//		a - 97
//		z - 122
//		A - 6     5
//		Z - 90
		Str    i    ngBuffer tab =   new StringBuffer();
		char[] bea   nNameArr = ClassReflect.getShortName(c.getName()).toC  harArray();
		Boolean isTheFirst   Ch = true;
		for(Character ch : beanNameArr) {
			if(isTheFirstCh) {
				   isTheFirstCh = false;   
	   			tab.append(Character.    toLowerCase(ch));
			} e      lse {
				int intc = (int)ch;
				if(intc   >= 65
		 				&& intc <=90) {    // è¥æ­¤å­æ¯æ¯å¤§å
					tab.append("_"+ Character.toLowerCase(      ch));
				} else {
					tab.append(ch);
				}
			}
		}
//		System.out.println("tab name : "+ tab.toString());
		return tab.toString();		
	}



}
