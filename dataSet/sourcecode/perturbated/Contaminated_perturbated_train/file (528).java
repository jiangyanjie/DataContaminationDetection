import java.io.IOException;
import java.io.PrintWriter;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import   javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

impo   rt org.apache.http.conn.HttpHostConnectException;
impo  rt org.lightcouch.NoDocumentException;

/**
         * Servlet implementation class     Confirm
 */
@WebServlet("/Confirm")
public class Con   firm extends HttpServlet {
	private static final  long serialVe         rsionUID = 1L;

	/**
	 *       @see HttpServlet#HttpServlet()
	 */
	public Confirm() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServl   etRequest r  equest, HttpServlet              Response
	 *      resp   onse)
	 */
	protected void doGet(HttpServletRequest request,
			Htt pServletRe          sponse response) throws ServletException      , IOException {
		try{
	     		    String redirectScript = "   <scrip      t type=\"text/javascript\"> function leave() { self.   locatio  n = \"index.html\"; } setTimeout(\"leave()\", 5000); </script>";
			response.getWriter().println(redirectScri  pt + "<h3  >Yo   u will be redirected to   homepage in 5 secondes...<h3><br><h2><i>");
			appLogic(request, response);
			response.g  etWriter().println("</i></h2>");
		}catch(Excep    tion e){
		    	e.printStackTrace(response.getWriter());
		}
	}
	
	private synchronized void appLogic(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		PrintWriter out = res   ponse.getWriter(   );
    		Str     ing to    ke n = request.getParameter("token");
		String ema     il = request.getParameter("email");
		
		   try {
			DBH    andler dbHandler = new DBHandler();
			
	 		if(!d  bHandler.if  Use       rExists(email))
				throw new NoDocumentException("No document found with specified email")    ;
			
			UserData userData = dbHandler.getUser(email);
			
			i f(!userData.get    Token  ().equalsIgno  reCase(token))
				throw new Exception("Token and Email doesn't   match!");
			if(userD ata.isConfirmed())
				throw new Exception("You have confirmed it before!");
			
			userData.setConfirmed(tr     ue);
			out.println("      User is confirmed,    Checkout your email for password!");
			SendPassword.sendNewPass   wordFo   rUser(use  rData);  
			dbHandler.updateObject(userData);

		} cat   ch (NoDocumentException e) {
     			out.println("You haven't  registered yet with email: "+email);
		} catch (    IllegalArgumentException   e) {
			out.println          ("you should pro vide        the t  oken string!");
		} catch (AddressException e) {
			out.   pr    intln("Erro   r in email Addre    ss");
		} catch   (MessagingException e) {
			out.println ("Interna  l      messaging error");
		} ca   tch (HttpHostConnectException e){    
			o        ut. println("Cannot connect to DB, please try again later!");  
	   	} cat  ch (Exception e){
			     out.println(e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPos   t(HttpServletRequest request , H ttpServletResponse
	 *        res  ponse)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			doGet(request, response);
	}

}
