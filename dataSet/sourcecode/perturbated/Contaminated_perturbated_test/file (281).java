package com.unc.cs.graderprogramplugin.com.sql;

import    java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import   java.util.ArrayList;
impo   rt java.util.Properties;

/**
   * @author Andr    ew Vit kus
 *
 */
public cla    ss DatabaseReader implements IDatabaseReader        {
	  private Connection connection;
	
	@Override
	public void connect(String u   sername, St ring passwo    rd, String server) throw     s  SQLException, InstantiationException, IllegalAcces   sException, ClassNotFoundException {
		DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());
		connection = Driver   Manager.getConnection(ser     ver, username, password);
	}

	@Override
	public String[]   readCourseList() thro  ws    SQLExcepti    on {
		ArrayList<String> courseList =   new ArrayList<String>();
		PreparedStatement pstmt = null;
	 	try {
			pstmt        =   connec tion.prepareSta  tement("SELECT * FROM c     ourse WHER E term_id IN (SELECT id FR OM term WHERE current =      TRUE)");
			ResultSet result     s = pstmt.executeQuery();
			while(results.next()   ) {
				c  ourseList.add(results.getStr  ing("name") + "-" + results.getString("se  ction"));
			}
			retur    n courseList.toArray(new String[courseList.size()]);
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}
	}
	
   	@Overrid  e
	public String[] readAssignmentTypes() throws SQLExcepti  on {
		ArrayList<String> typ  es = new ArrayList<String>();
		PreparedState  me nt pstmt = null;
  		try {
			pstmt = connection  .prepareStatement("SELECT name FR  OM assignment_type");
			ResultSet results = pstmt.executeQuery();
			while(results.next()) {
				types.add(resu lts.getString("name"));
			}
			return typ    es.     toArray(new String[types.siz  e()]);
		} f inally {
			if (p   stmt != null) {
				pstmt.close();
			}
		}
	}
	
	@Override
    	public ResultSet rea    dAssignments(String     course, String section) throws SQLExc       eptio   n {
		PreparedS      tatement pstmt = null;
		  try {
			pstmt = connection.prep   areState   ment("SELE   CT * FROM assign    ment_cata  l    og WHERE c ourse.id IN (SELECT id FR OM    course WHERE name = ? AND section = ? AND term_id IN (SE   LECT id FROM term WHERE    current = TRUE))");
			ResultSet resul      ts = pstmt.executeQuery  ();
			 ret    urn  results;
		} finally {
			if (pstmt != null) {
				ps   tmt.close();
			}
		}
	}

	 @Override
	public void disconnec   t() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}

}
