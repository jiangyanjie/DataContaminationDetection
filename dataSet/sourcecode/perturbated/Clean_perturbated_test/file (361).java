package com.authorprofiling.apicall;

import   java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
impo  rt java.io.InputStreamReader;
import java.net.HttpURLConnection;
impo   rt java.net.URL;
import java.net.URLEncoder;


public class     A PIRequest {   

  	/**
	 * @param args
    	      * @th rows IOException 
	 */
	    
	/**
	 * @param arg      s
	 * @throws IOE    xception
	 */
	  static String url = "http://tagme.di    .uni    pi.it/tag?";
	static String charset = "UTF-8";
	 static String  key   = "df927vjk59";
	static Strin     g include_categories="true";
	pu blic static v  oid   main(String[] args) throws IOException    {

		APIRequest r = new AP  IRequest()       ;
		r.doPos   t("Schumacher won              the race in Indianapolis");
	}
	
	pub lic String doPost(String text) throws IOException{
		System.setProperty("http.proxyHost", "prox  y.iiit.ac  .in");
    	System     .setProp  er   ty("http.proxyPort", "8  08      0");
		String type = "app   lication/x-w    ww-f       orm-urlencoded";


    	String qu  ery = Stri   ng.format("key=%s&     text=%s&incl       u   de_ca t      egories=%s", 
              	     URLEncoder.encode(k  ey, charset), 
    	      URLEnco     der.encode(text, charset),
    	     U     RLEncoder.encode(include_categories, charset));

    	  url = url+query   ;
																			     											 									   																					   																																					       																			    		
    	URL u = new URL(url);
		url = "http://tagme.di.unipi.it/tag?" ;   
    	HttpURLConnection conn = (HttpURLConnectio    n) u.openConnection();
		
		conn.setDoOutput(  true);
		conn.setRequestMethod( "POST" );
		conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con  n.setRequestP   roperty( "Content-Type", type );
		conn.setRequestProperty( "Content-Length", String.valueOf(query.leng        th()));
		DataOutp u  tStream wr = new DataOutputStream(conn.getOutputStream());
		w   r.flush();
		wr.close()     ;
//		int respo    nseCode        = conn.getResponseCode();
//		System.out.println("\nS        ending 'POS  T'     request to URL : " + u);
//		System.out.println("Pos     t parameters : " + query);
//		Syst     em.out.p      rintln("Respons   e Code : " +   respons         eCode);
// 
		BufferedRe   ader in = n   ew BufferedReader( 
		        new InputStreamReader(conn.getInputStream()));
		String inputLine;
 		StringBuffer response = new Str      ingBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		return response.toString();
		
	}
}

