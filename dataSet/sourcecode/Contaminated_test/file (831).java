import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class delProduct extends HttpServlet {  // JDK 6 and above only

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
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
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/cse135", "root", "");

			stmt = conn.createStatement();
			if(request.getParameter("delp")==null) {
				String sql = "update products set name='" + request.getParameter("newn")
						+ "', sku='" + request.getParameter("newsku") 
						+ "', category=" + "(SELECT ID FROM `categories` WHERE NAME = '" + request.getParameter("category") + "')"
						+ ", price='" + request.getParameter("newp")
						+ "' where name='" + request.getParameter("orig") + "';";
				stmt.executeUpdate(sql);  // Send the query to the server

				out.println("<html><head><title>Query Response</title></head><body>");
				out.println("<h3>Product " + request.getParameter("orig") + " Edited.</h3>");
				out.println("<p>You query is: " + sql + "</p>"); // Echo for debugging
			}

			if(request.getParameter("delp")!=null) {
				String sql = "delete from products where name = '" + request.getParameter("delp") + "'";
				stmt.executeUpdate(sql);  // Send the query to the server

				out.println("<html><head><title>Query Response</title></head><body>");
				out.println("<h3>Product " + request.getParameter("delp") + " Deleted.</h3>");
				out.println("<p>You query is: " + sql + "</p>"); // Echo for debugging
			}

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