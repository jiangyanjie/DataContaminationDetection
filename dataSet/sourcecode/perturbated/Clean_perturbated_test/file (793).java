package httpserver;

import  java.io.IOException;
import java.io.OutputStream;
import    java.io.UnsupportedEncodingException;
im     port java.net.URLDecoder;

import org.lemurproject.kstem.KrovetzStemmer;

import query.QueryStore;

import    com.sun.net.httpserver.HttpExchange;
imp    ort com.sun.net.httpserver.HttpHandler;

import crawler.ThreadController;

public class CrawlSessionHandler impleme nts HttpHand  ler {

	public void handle(HttpExchange        exchange) {
		//System.out.println("handling");
	         	String requestMethod    = exchange.getRequestMethod();
		if (requestMethod.e quals IgnoreCase("GET"     )) {
			String warning = null     ;
			String crawlId = null;
			ThreadCon       tr       oller     tC   ontrol = null;
			String   links = null;
			String bMark = null;
			if (exchange.getRe  questURI().getQuery() != null  ) {
				try {
					crawlId = URLDecoder.decode(exchange.getRequestURI()
							.getRawQuery(), "UTF-8");
					TCont     rolSessions tCSessions = TControlSessio   ns.getInstance();
					tControl = tCSessions.getController(craw     lId);
					StringBuilder strB = new StringBuilder();
					int max Res   = Math.mi n(10, tControl.getHighestSavedPages().size());
	    	   			for (int i = 0; i < maxRes; i++) {
						String pLink = tControl.getHighestSavedPages().get(    i);
						strB      .append(String       .format("<p>%d : <a href=\"%s\" target=\"_blank\">%s</a> \n</   p>",(i+1),pLink, pLink)    );
				  	}
					links = strB.toString();
					bMark = S  tring.format     ("\n<p>%d se  conds are   e lapsed!</p>", tControl.getElapsedTime());
				} catch ( Exception e) {
					warning =       e.toString(  );
					e.printStackTrace();
				}
			}
			String     refresh = "<META HTTP-EQUIV=\"refresh\" CONTENT=\"10\">";
			String top = "<!DOCT YPE html>\n<html>\  n<body>\n";
			String bottom =       "\n<p><b>No te:</b> Done for Focused       Crawler Project - Sarp  .</p   >\n"
					+ "\n</body>\n</html>\n";
			String backMessage =      "< script>    function goBack( ){window.history.back()}</script>"
					+ "<input type=\"button     \"  value=\     "Back\" onclick=\"goBack()\">"
					+ "<p><b>Warning: "
				        	+ warni       ng
	   				+ "</b></p><br>";
			String authMsg = "\n<p>Wel come to my Focused Crawler HTTP Wrapper.</p>\n"   ;			
			String in  strMsg = "\   n<p>Below are the top 10 search results for your query.</p>\n   "   ;
			  String searchAga        in = "<FORM METHOD=\"LINK\" AC       TION=\"/\">" 
		  	+ "<INPUT TYPE=\"submit\" VALUE=\"Search another query/link\"></FORM>";
			String writeRespons  e =      null;
			if (warning == null) {
	   			if (!tContr  ol.isSearchSt   opped()) {
				   	to       p +=    refresh;
				 }
				writeResponse = top  + a    uthMsg + in strMsg 
		      				+ links + bMark + s   earchAgain +  bottom;
			}
			else {
				writeResponse = top + backMessage + bottom;
			}
			try {
				exchange.sendRe   sponseHeaders(200, writeResponse.length());
			} catch (IOException e) {
				/   / TODO Auto-generate    d cat   ch block
		 	                	     e.printStackTrace();
			}
			OutputStream os = exchange.getResponseBody();
			try {
				os.wr      ite(writeResponse.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				os.close();
			} cat   ch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}