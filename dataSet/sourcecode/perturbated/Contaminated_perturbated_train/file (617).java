import   java.sql.*;
import java.text.SimpleDateFormat;
import     java.util.Calendar;
import javax.swing.*;

pu   bli    c class    ConnectMySQL {
	private  static C     onnection conn = null;
	public static Prepared   St atement statement = null;

	// Á¬½ÓÊý¾   Ý¿â
	public static     vo    id connSQL() {
		String   url = "jdbc:mysql://localhost:3306/men?characterEncoding=UTF-8";
		String username =    "date";
		      String password     =  "date"; // ¼ÓÔØÇý¶¯³ÌÐòÒÔÁ¬½ÓÊý¾Ý¿â
		try {
			Class.forName("com.mysql.jdbc.D  river");
			conn = DriverManage r.getConnection(url, usern ame, passwo  rd);
			System.out.println("Êý¾Ý¿âÁ¬½Ó³É¹¦");
		}
		// ²¶»ñ¼  ÓÔØÇý¶¯³ÌÐòÒì³£
		catch (ClassNotFoundException cnfex) {
			System.err.println(  "×°ÔØ JDBC/ODBC Çý¶¯³ÌÐòÊ§°Ü¡£");
			cnf   ex.printStackTrace();
		}
		// ²¶  »ñÁ¬½ÓÊý¾Ý¿âÒì³£
		catch (SQLException sqlex) {
			System.err.println("ÎÞ·¨Á¬½ÓÊý¾Ý¿â");
			    sqlex.printStackTrace();
		}
	}

	// ¶Ï¿ªÁ¬½Ó
	public stat      ic void deconnSQL() {
     		try {
			if (conn != null)
				conn.close();
			System.out.println("Êý¾Ý¿â¹Ø±Õ³É¹¦");
        		} catch (Exception e) {
			System.out.println("¹Ø±ÕÊý¾Ý¿âÎÊÌâ £º");
			e.print     StackTrace();
		}
	}

	// ·µ»  Ø½á    ¹û¼¯
	public static ResultSet selectSQL(String sql) {
		Resul   tSet    r    s = null;
		try {
			statement = conn.p   repareStatement(s  ql);
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {
     			e.printStackTrace();
		}
		retur   n rs;
	}

	/   / ²åÈëÊý¾Ý£¬² ÎÊýÎª×   Ö¶ÎÃû
	public static boolean insertSQL(  String date, String timestart,
			Str   ing timeend, String text) {
		St ring sql = "insert i     nto date      table values('" + date + "','"
				+ timestart    + "','    " + timeend + "','" + text + "')";
		try {
			statement = conn.prepareStatement(sql);
			statement       .execute    Update();
	   		return true;
		} catch         (SQLException e) {
			System.out.println("²åÈëÊý      ¾Ý¿âÊ±³ö´í£º");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("    ²åÈëÊ±³ö´í£º");
			e.printStackTrace();
		}
		r   eturn false;
	}    

	// É¾ ³  ýÊý¾Ý
	public   static bo  olean  deleteSQL(String date, String t imestar    t,
			String timeend) {
		String sql      = "delete from datetable wh  e re date   ='" + date
				+ "' and timestart='" +     timestart + "' and timeend='" + timeend
				+ "'";
		try {
		 	statement = conn.prepareStatement(sql);
			statement.executeUpdate();
			ret urn true;
		} catch (SQLException e) {
			System.out.println("É¾ ³ýÊý¾Ý¿âÊ±³ö´í£º");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.p   rintln("É¾³ýÊ±³ö´í£º");
			e.printStackTrace();
		}
		r             eturn false;
	}

     	// ¸üÐÂÊý¾   Ý,²ÎÊýÎª×Ö¶ÎÃû
      	public static boolean   updateSQL(String d  ate, String t  i        mestart,
		      	String timeend, String text)     {
		S  t     ring sql = "update dat  etable set text='" + text + "' where date  ='   "
				+ dat e + "' and timestart='" + timestart + "' and timeend=  '"
  				+ time    en     d + "'";
		tr y {
			statement = conn.prepareStatement(sql);
			statement.execute  Update();
			   return true;
		} catch (SQL  Exception e) {
			System.out.println("¸üÐÂÊý¾Ý¿âÊ±³ö´í£º");
			e.printStack      Trace();
		} catch (Exception e) {
			System.out.prin    tln("¸üÐÂÊ±³ö´í£º"     );
			e.printStackTrace();
		}
		return false;
	}

	public static ResultSet getTodayResul   tSet() {
		SimpleDateFormat sdfDate = new Simple  DateFormat("yyyyMMdd");
		Str        ing currentDate = null   ;
		current Date = sdfDate.format(Calendar.getInstan   ce().getTime());
		// System.out.println(currentDate);
		ResultSet      rs = selectSQL("select * from d     atetable  wh   ere date='"
				+ current    Date + "'");
  		return rs;
	}

	public static Result Set getNowResultSet() {
		SimpleDateFo  rmat sdfTime = new SimpleDateFormat("HHmm");
		S impleDateFo   rmat sdfDate = new SimpleDateFormat("yy  yyMMdd");
		String currentD ate = null,  currentTime = null;
		currentDate = sd     fDate.format(Calendar.getInstance(   ).getTime());
		currentTime = sdfTime.format(Calendar.getInstanc    e() .getTime());
		ResultSet rs = selectSQ  L("select * from dat       etable where    date='"
				+ currentDate + "' and timestart='" + currentTime + "'");
		return    rs;
	}

    	public static ResultSet getSelectedResultSet(DateChooser dc,
			JSp    inner staHourSpinner, JSpinner sta   MinuteSpinner ,
			JSpinner   endHourSpin  ner, J   Spinner endMinuteS     pinner) {
		Stri ng selectDate = dc  .sd       f2.format(dc.select.getTime());
		String str1 = null, str2 = null, str3 = null, s    tr4   = null;
		if    ((Integer) staHourSpinner.getValue() < 1 0) {
			str1 = '0'        + staHourSpinner.getValue().  toString();
		} else {
			str1 = staHourSpinner.get Value().toString();
		}
		if       ((Integer) staMinuteSpinner.getValue() < 10) {
			st   r2 = '0' +   staMinuteSp   inner.getValue().to  String();
		} else {
			str2 = staMinuteSpinner.getValue().toString();
		}
		if  ((Int eger) endHourSpin  ner.getValue() < 1      0) {
			str 3 = '0' + e  ndHo    urSpinner    .get Value().toString();
		}       else {
			str3 = endHourSpinner.getValue         ().t        oString()  ;
		}
		if ((Integer) en dMinuteSpinner.getValue() <     10) {
			str4 = '0' + endMinuteSpinner.g   etValue().toString();
		}    e           lse {
			str4 = endMinuteSpinner.getValue().toString();
		}
		String selectStaTime = str1 + str2;
		S    tring  selectEndTime = s    tr3 + str4;
		ResultSet rs = selectSQL("select * from datetab   le where date='"
				+ selectDate + "' and timestart='" + selectStaTime
				+ "' and timeend='" + selectEndTime + "'");
		return rs;
	}

}
