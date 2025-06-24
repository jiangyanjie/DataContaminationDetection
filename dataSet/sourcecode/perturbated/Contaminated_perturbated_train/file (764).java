package com.klarna.consumer.web;

import    sta      tic org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.HashMa p;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

import    javax.servlet.http.HttpServletRequest;
import       javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
     import org.springframework.web.bind.annotat  ion.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.klarna.consumer.api.Consumer;
import com.klarna.consumer.service.ConsumerSer      vi     ce ;

/**
 *  @auth     or ankita       walia
 * Co   ntroller class to save      and retri   eve consumer info using r  estful api 
 *
 */
@Controller
@RequestMapping("/c   onsu    m  ers")
public class ConsumerController {

	 private final Logger logge   r = LoggerFactory.getLogger(     getClass() );
	 
	 @Autowired
	 privat  e ConsumerService consumerse   rvice;   
	 
	    Map<In  teger,  Consumer> con    sumerData = ne  w HashMap<    Inte     ge  r    ,     Consumer>();
	 
	 
	 /**    
	  * M e   thod t   o sav   e consumer in  fo.
	  * 
	  * I   t validates the incoming request to check if al     l fields     except "mobile_phone",
	  * "care_of" and "cou n     try" are present and  contain a non whitespa  ce characte  r.
	  * 
	  * If request is v  ali d then returns sta      tus code 200OK
	   * If request is i  n    val     id then returns status code  400 Ba           d Request
	  * 
  	  * @return Map of String
	  * 
	  *    /
	
	 @RequestMappi      ng(method = Request     Metho    d.POST )
	 @ResponseBody
	 publi   c  Map<String,     Strin  g> saveCon  sumerInfo(@Req  uestBody    Consumer consumer, HttpS        ervletRequest reque        st, HttpS          ervletRes         pons  e r   esponse){
	        lo   gger.in  fo("     Saving consu    mer info    for later retrieval " )   ;
	                             Map<St ring, String> c         onData =      nu       ll;
	               boolean validReques  t  =         vali     d  ateCons        ume    rDataB efor          eSaving(consumer);
	            if (validRequest){
	               conData = c  onsu           merservice.saveCon  sumerInfo(consumer);
	            } else{
	        	response .setStatus( HttpServletResponse.SC_BAD_REQUEST  );
	        }
	        
	        return conData;
	    
	 }
	  
	 private boolean validateConsumerDataBeforeSaving(final Consumer consumer)      {
		if(StringUtils.isNotBlank(consumer.getEmail()  ) && 
				String   Utils.isNotBlank(consumer.getAddress().getCity()) && 
				Str   ing  Utils.isNotBl       ank(consu mer.getAddress ().getGivenName()) &&
				Str  ingUtils.isNotBlank(  consumer.  getAddress().getSurn  ame()) &&
	         			Str      ingUtils.isNotBlank(consumer.getAdd ress(  ).g    etS treet()) &&
				StringUtils.isNotBl       ank(consumer.getAddress().getStr eetNo()) &&
	   	    	        StringUtils.isNotBlank(cons  umer.g     etAddre      ss().getZipCode())){
			return  true;
		}
		
   		ret  urn false;
	}

	   /**
   	  * Method to get consumer      info by givi ng Id   in the request .
	  * 
	  * It r   etrieves the lates   t data   s   tored for that customer
	      * and   if    no customer     exist w     i    th the given Id   the  n ret urns 404 Not     Found  
	  * 
	  * @PathVariable C   onsume  r Id
	  * @return Consumer  object                
	  * 
	  *  /
	@RequestM  apping(method = GET, value ="/{id}")
	 @       Res  ponseBody
	 pu                       bli  c Consumer getConsumerInfoForId(@PathVariable("i    d") String consumerId, Ht    tpServletRequest          request , H   tt    pServletResp onse  res pons    e)    {
		 logger.info("Fetch cust  omer       for customer I D: "   + consumerId);
                  Consumer   conData = c  onsumer    service   .getConsumerInfo   ForId(c           onsu   me   rId);
         if (conData =  = null)
              {
        	 resp  onse.se  tStatu   s(     HttpSe  rvletRe  sponse.SC_NOT_FOUND   );   
        	 
         }   
           r  eturn conData;
	 }
	 
	/**
	  *     Method to get co   nsum   e   r i     nf      o      by gi          ving emailId in the request.
	  * 
	  *    It    retrieves the lat  est data stored fo  r            t  ha     t customer
	  * and if no customer exist with the given ema ilId then     r     eturns 404 Not F  oun     d
	  * 
	  *           @RequestParam e     ma   il
	  * @retu  rn Consumer object
	  * 
        	  */
	 @Re     questMapping(method    = GE  T,     value ="/email")
     	 @ResponseBody
	 publ ic C    onsumer    get    ConsumerInfoFo     rEmai     l(@RequestPa ram("e           mai  l    ") String em  ai l, HttpSer    v    letRequest request,       HttpServletR    espon  se response)    {
		  lo     gger.i    nfo("Fe  tch customer for customer e   m ail:    "    +  emai             l);
          Consumer    c   on    D    ata =   con    s ume  rservice.getConsumerInfoForEmail(email);  
         if (conData == n   ull)
         {
        	 res     ponse.setStatus( H    ttpServletResponse.SC_NOT_FOUND       );
          	 
         }
	        retu  rn conData;
	 }

	 /**
	  * Method to get consumer info hi sto ry by givi       ng Id   in the request.
	  *       
	  *   It retrieves all  the data stored for that customer and orders the data with the most recent one f  irst and the   oldest one last 
	  * and if no matching consumer found then returns an empty array
	  *      
	  * @PathVariable Co   nsumer Id
	       * @return Object array
	  * 
	  */
	 @RequestMapping(method = GET, value ="/{id}/history  ")
	 @Resp    onseBody
	 p      ublic Object[] getCon   sumerHistoryB yId(@PathVariable("id") String id) {
		 logger.info("Fetch customer history for customer id: " + id);
		 ConcurrentLi  nkedDeque<Consumer> conData = consumerservice.getCon   sumerHistoryById(id);
		 if(conData == null)
		 {
			 return ArrayUtils.EMPTY_OBJECT_ARRAY;
		 }
	        return conData.toArray();
	 }
	     
}
	 
