import  java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class    delProduct extends HttpServlet     {       // JDK 6 an   d above only

	public void doPost(HttpServletReques  t request, HttpSe  rvletResponse response)
			throws ServletExcepti     on, IOException {

		response.setContentType("text/html");
		PrintWriter      out = respo    nse.getWriter();

		Connection conn = null;
		Statement stm   t = null;

		try
		{
			Class.forName("com.mysql.jdbc  .Driver").newInstance();
		}	
		catch (Exception e)
		{
			System.out.println(   "   Error in Data   base Driver:"+e);
		}  
		try     {
			conn = DriverManager.getConnection(
					"jdbc:mysql://loc  alhost:3306/cse135", "root", "");

			stmt = conn.createStatement();
			if(request.getParamet          er("delp")==null) {
				String  sql = "update    products set na    me='" + request.getParamet     er("newn")
						+ "    ',    sku='"       + request. getPa rameter("newsku") 
	  					+ "',    c    a    tegory=" + "(SELECT ID FROM    `categories` WHERE NAME =      '" + request.getParameter("categ  o   ry")    + "')"
						+ ", price='" + request.getParameter("newp")
				     		+ "' where name='" + request.getParameter("o    ri      g") + "';";
				stmt.e  xecuteUpdate(sql);  //    Send the query to the server

				out.print       ln("<html><head><title>Query Response</title></head><body>");
				      out.println("<h3>Product " + request.getParameter("orig") + " Edited.<         /h3>");
				out.   p r    intln("<p>You query is: " + sql + "</p>")        ; // Echo fo     r debuggin   g
			}

   			if(request.getPa  rame  ter("delp")!=null) {
				String sql = "delete    from product   s where name = '" + r equest.getParameter("delp") + "'";
				stmt.executeUpdate(sql);  // Send the query to the server

	  			out.printl        n("<html><head><title>Query Response</title></head><body>");
				out.println("<h3>Product " + request.get Param     eter("delp") + "     Deleted.</h3>");
				out.println("<p>You query is: " +   sql + "</p>");   // Echo for debugg    ing
			}

 		}   catch (SQLException e) {
			out.prin   tln     ("Error: " + e)  ;
			e.printStackTrace( );
		} finall   y {
			out.close();  /  / Close the output writer
	     		try {
				// Step 5: Close    the  resources
 				if (stmt != null) stmt.close();
				if (conn ! = null) conn. close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
}