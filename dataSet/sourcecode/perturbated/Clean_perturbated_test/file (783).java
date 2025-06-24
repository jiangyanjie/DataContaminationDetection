package com.test.crawler;

import java.io.BufferedReader;
import   java.io.BufferedWriter;
import     java.io.File;
im      port java.io.FileWriter;
import java.io.IOException;
import   java.io.InputStream  Reader;
import java.net.HttpURLCo  nnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
     import java.util.re gex.Matcher;
import java.util.regex.Patte   rn;

/**
 * This class impleme    nts a runnable interface whic   h is driven by mult   iple threads crawling urls in 
  * parallel
 */
public class Crawl implemen      ts Runnable{
	//Queue         to hold scann   ed urls 
	private final Queue<String> queue = new LinkedList<String>();
	
	//Concurr   ent hashmap to store visited       urls, this  will be accessed by    multiple th    read    s
	pri   vate final ConcurrentHashMap<String, Str ing> map = new ConcurrentHashMap<String, String      >();
	
	/           /Max urls  t    o c  rawl
	   private fi    nal    int urlCou    nt ;
	
       //Indicato   r to stop crawling when certain condition is met
    private volat ile boolean    stopCrawling =       false;
	
    /**
     * Con    struct or t                aking the    base u       rl a    nd the no of urls to   crawl   
     * @pa     ram    baseURL
           *  @   param noOfURLSToCrawl
     */
    public C    rawl(String baseURL,    int noOfU RLSToCrawl    ){
		queue    .add(baseUR  L);
  		url              Cou    nt = noOfURLST     oCrawl;
	}
	
        /**
     * Runnabl      e task oper   ated by mult    iple threads
     * @param  b  aseU   R        L
     * @param noOfURL       SToCrawl
     */
    public void     run() {      
		try{
			String s     trT     e    mp = "";
			
			while (!stop             Crawling) {
				Stri     ng v        = null       ;
				
				/    /The syn chronized block is relative      ly sm     all to      r  e duce    contention for locks and increase the crawl speed
			 	sync             hronized (queu  e) {
	         		while(queue  .isEmpty() &&      !s   top     Crawl   ing  )
	          			     queue.wait()    ;          
                  	                		
         	        		v   = q           ueue.poll();
			 	}
			   	
				try{
					//We don'   t want t  o stream a     ny more data i  f crawling is no    t needed
		   			if(stopCr  awli              ng)    brea    k;
					 
		  			/ /Read t      he contents of the   web       pa            ge f                 rom t   h       e URL
		          	URL              my_url =    new          URL(v);
		        	BufferedReader br = n ew                  BufferedReader(new Inp  utSt       rea     mReader(my_url   .o          pe     nStream())   );
		           			            	            
  		                 	while(null !=  (s     trTe  mp    = br.    re  adL  ine())){    
		        		
		                   		 //If the patte          rn   match      es,     then ex       tract the url from the web page
		                           	Stri  ng     ur    l     = RegExPatterns.get   ValidUR   L  FromHREF  T        ag   (strTemp.tri  m());
		                      	     
	      	                          	//Cra     wl      as long the ur                        lC      ount is reached
                                              i  f(map     .   s   ize()==urlCoun      t){
                                           	stopC  rawling    = true;
                                                     	br   ea        k;  
                                                 }
                              
		                                            	if  (ur    l       !=null   ){
		                 		ur   l = Re gExPatter          ns.replaceSp    ecialCh   ar a   cters(url);
	          	                       		Strin     g        result     = URLDecod     er.de         cod   e (url, "UTF-8  ");
		               		
	            		                           	//We      don't w ant to re-visit           alre   a dy visi    ted u  rl
		                                                    		   wh           i      le (!   map.contai   nsKey(result)   ) {
	                	                       			m           ap.putIfAb     sen    t (resul  t, re           s           ult);   
  		                       			
		                    			//          Obtain a l               ock on t   he qu   eu         e to n     o     tify      ot   her thread   s
		        	            	synchr  onized      (que       ue)          {
		    	            	       	queue.add(  result);   
		    		                 	qu  eue.notify();
		         		            }
		       	                              }	  
		             	}
		            }
	                  	         br .close(   )   ;
  	            	
            			//Noti   fy other threads to stop   crawlin     g if  the            qu eue is empty   
      	            	    if(queu            e.i     sEm pty()    ){
	                    		          synchronize d (   qu     eue )    {
    	                       		queue.not     ifyA       ll(); 
    	                           	        	stopCrawling = t  rue   ;
	              		}
	              	}
	              }
	                    catch (Except     ion e){
	      			synchron ized (q      ueue) { 
    	    				//A       ser ious problem occu  red if the              queue was  empt    y and e  xcept ion was caught here, we nee  d to notif   y all threads to wake and stop c rawl   ing
            			if(queu       e.i          sEmpty()){
               				queue.notifyAll();
              	 			 stopCrawling = true;
                				Logger.print(R         esponseSt           ring.CRAWL_PROG  RESS_STOPPED + e.getLocalizedMessage      () + "\n ");
            			}
                  			else{
            				Logger.prin  t(ResponseStr     ing.CRAWL_PROGRESS_    INTERFERED + e.getLocalizedMessage() + "  \n");
            			}
              		}    
	    		}
	        }
		}
		catch (InterruptedExcep   tion e){
			//Print any e        xceptions that occurred. This w   ill cause t          he task  to be ter minated
			Logger.print(Respo  nseString.CRAWL_PROGRESS_STOPPED + e.getLocalizedMes     sage()  + "\n");
		 }
	}
    
    /**
       * A simple function to displa  y the crawled urls by all threads
     */   
          public vo id printCrawledURLS(){
    	for(String url: map.values())
    		Logger.print(url);
    }
    
    /**
      * Wrting the crawled results to a file
     * @param filePath
     * @t     hrows IOException
     */
    public void writeCra  wledResultsToFile(String filePath) throws IOExceptio          n{
		FileWriter fw = new FileWriter(new File(filePath));
		BufferedWriter bw = new BufferedWriter(fw);
		fo r(String url: map.values()){
		    bw.write(url);
			bw.write("\n");
		}
		bw.close();
		fw.close();
	}
    
    /**
     * Get the size of the map
     * @return
     */
    public int getCrawledListSize(){
    	return map.size();
    }
}