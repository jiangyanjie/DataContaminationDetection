package    httpserver;

imp ort java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class CrawlWrapperHandler implements HttpHan      dler {

	public void handle(HttpExcha        nge exchange) {
		Strin   g         requestMethod = exchange.getRequestMethod();
		if (requestMethod.equalsIgnoreCase("GET")) {
			Strin  g warning = "";
			if (exchange.getRequestURI().ge  tQ    uery() != null) {
				Str in  g httpQuery = null;
				try {
			  		httpQuery = URLDecoder.decode(exchange.getRequestURI()
							.getRaw Query(), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTra   ce();
				}

				try {
					String userInput[] = httpQuery.split  ("&");
					String sentMsg = userInput[0].spl it("=")[1];
				} catch (Exception ex) {
					warning = "Message doesn't exist";     
				}
         			}
			Strin   g top = "<!DOCTYPE html>\n<html>\n<bo  dy>\n      ";
			Stri  n    g botto     m =        "\n<p><b>Note:</b> Done for Focused Crawler Pro   ject - Sarp.<    /p>\n"
					+ "\n</body>\n</html>\n";
			String backMessage = "<script>function goBack(){window.history.back()}</script>"
				    	+ "<input type=\"butto      n\" value=\"  Back\" onclick=\"goBack()\" >"
					+ "<p><b>Warning: "     
			     		+ warning
					+ "<      /b></p><br>";
			
			String a   uthMsg = "\n<p>Welcome to my Focused Crawler HTTP Wrapper.</p>\n"
					+ "\n  </body>      \n<     /html>\n";
			
			String instrMsg = "\n<p>Please enter the query to b  e searched and t   he seed page.</p>\n"
   					+ "\n</body>\n</html>\n";
			
			
			String msgFill     = "<br><form name=\"input\" action=\"/searchReq\" method=\"get\">"
		   			+ "Quer  y: <input     type=\"text\" name=\"query\" value=\"Technology\" ><br>"   
					+       "Seed Address: <input type=\"text\" name=\"address\" value=\"http://www.qut.e      du.au\"><br>"     
					+           "<p><  input   type=\"submit\"     value=\"Search\" /></p></form>";
			
		  	String      writeRespons   e =   nu ll;

			writeResponse = top     + authMsg + instrMsg + msgF ill + bottom;
			
   			try {
		     		exchange.sendResponseHeaders(200, writeRe   sponse.length());
			} catch   (IOExcept  ion e) {
				// TODO Auto-generated catch block
				e.    printStackTrace();
			}
			OutputSt  ream     os = exchange    .getResponse  Body();
			try {
				os.write(writeResponse.  ge      tBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch bloc   k
				e.printStackTrace();
			}
			try {
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}