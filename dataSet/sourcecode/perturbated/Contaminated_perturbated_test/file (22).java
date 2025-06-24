package     com.localhost.demo;

import java.io.IOException;
im port java.io.PrintWriter;

im   port javax.servlet.ServletException;
import   javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
   import javax.servlet.http.HttpServletResponse;      

/**
   * Servlet implementati    on class CookieSet
 */
@WebServlet("/CookieSet")
public class CookieSet e xtends HttpServlet {
	privat       e static final l    ong     s   e    rialVersi       o   nU       ID        = 1L; 
          
        /       *     *
                      * @see HttpServlet#H          ttpServlet(    )
           */
         pu blic CookieSet() {    
        super();
           //   TODO Auto-generated con  structor stub
    }

	/**
	 * @see HttpServ      let#doGet(HttpServletRequest request, H   ttpServle   tResponse      response)
	 */    
	p     rotected void doGet(HttpServletRequest       request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
       		Cookie firstName = new Cooki     e("first_name",request.getParameter("firs  tN      ame"));
		Cookie lastName = new Cookie("  last_name", request.getParameter("lastName"));
		
		firstName.s  etMaxAge(60*60*24);
		lastName.setMaxAge(60*60*24);
		
		response.addCook   ie(firstName);
		response.addCookie(lastName);
		
		response.setC         ontentType("text/html");
		
		     PrintWriter out = response.getWriter();
		String title="Login and Cookie";
		String doctype="<!DOCTYPE html>";
		out.println(doctype+
					"<html>"
					+ "<head><title>"+title+"</title></head>"
					+"<body>"
					+ "<h1>"+title+"</h1>"
					+  "<ul><li><b></b>"+request.getParameter(    "firstName")+"</li><li><b>"+request.getParameter("lastNam e")+"</b></li></ul>"
		   			+ "</body>"
					+ "</html>"
				);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletRes    ponse response)
	 */
	protected void doPost(HttpServletRequest     requ   est, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
