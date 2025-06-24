import java.io.*;
import java.sql.*;
import     javax.servlet.*;
im   port javax.servlet.http.*;
/*         
 * NEED   TO CHECK IF       USER ALREADY EXISTS
    *      PERFORM     OT     HER TESTIN      G AS   WELL
 */
pu        blic class cre ateUser extends HttpServlet {  //       JDK 6 and above only

	public void doPost(HttpServletRequest request, HttpServletRespon  se re     sponse)
			throws ServletExcept   ion, IOExc   eption {
		// Set the MI   ME type for the respo   nse message
		re sponse.setContentType(  "text/html ");
		// Get a output writer to write the  re  sponse message into the net    work socket
		Print      Writer out = response.getWrit   er();

		Connection conn =  null;
		Statement stmt = null;

		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}	
		catch (          Exc           eption e)
		{
			System.out.     println(       "Error in Data base Driver:"+e);
		}
		try {
			// Step 1: Allocate a database Connection object
			c          onn = DriverManager.getConnection(
					  "jd   bc:mysql://localhost:3306/135", "root", "");     // <== Check!
			// database-URL(hostna   me, port, def    ault database), username, passw    ord

		 	     // Step 2: Allocate a   Statement object within the Conn   ection
			stmt =   conn.c  reateStatement();

 	  		// Step 3: Execute a S  QL SELEC     T query
			String sql = "i    n     sert in to        users (name, role, age, state) "
					+  "values('" + request.getPa      rameter      ("name") + "','"
					+ request.getPara     meter("      role") + "','"     + request.   getParameter("age")
					+ "','" + request.getParameter("state") + "')";

   			// Print an HTM  L page    as the output of the query
			out.p       rintln("<html><head>  <title>Query Response</title></hea  d><b ody>");
			out.printl   n("<h3>User Created.</h3>");
  			out.println("<p>Yo  u query  is: " + sql + "</p>"); // Echo for debugging
			stmt.executeUpdate(sql);  // Sen  d the   query to the      s      erver

		} c      atch (SQLException e) {
            			out.println("Error: " + e);
 		  	e.printStackTrac    e();
		} fina  l      ly {
			out.close();  // Close t   he output writer
			try {
				// Step 5: Close the resources
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
			} catch (SQLException    ex) {
				ex.printStackTrace();
			}
		}
	}
}