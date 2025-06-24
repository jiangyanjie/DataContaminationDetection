import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* 
 * NEED TO CHECK IF USER ALREADY EXISTS
 * PERFORM OTHER TESTING AS WELL
 */
public class createUser extends HttpServlet {  // JDK 6 and above only

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Set the MIME type for the response message
		response.setContentType("text/html");
		// Get a output writer to write the response message into the network socket
		PrintWriter out = response.getWriter();

		Connection conn = null;
		Statement stmt = null;

		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}	
		catch (Exception e)
		{
			System.out.println("Error in Data base Driver:"+e);
		}
		try {
			// Step 1: Allocate a database Connection object
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/135", "root", ""); // <== Check!
			// database-URL(hostname, port, default database), username, password

			// Step 2: Allocate a Statement object within the Connection
			stmt = conn.createStatement();

			// Step 3: Execute a SQL SELECT query
			String sql = "insert into users (name, role, age, state) "
					+ "values('" + request.getParameter("name") + "','"
					+ request.getParameter("role") + "','" + request.getParameter("age")
					+ "','" + request.getParameter("state") + "')";

			// Print an HTML page as the output of the query
			out.println("<html><head><title>Query Response</title></head><body>");
			out.println("<h3>User Created.</h3>");
			out.println("<p>You query is: " + sql + "</p>"); // Echo for debugging
			stmt.executeUpdate(sql);  // Send the query to the server

		} catch (SQLException e) {
			out.println("Error: " + e);
			e.printStackTrace();
		} finally {
			out.close();  // Close the output writer
			try {
				// Step 5: Close the resources
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
}