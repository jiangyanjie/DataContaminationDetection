/**
 *     
 */
pac     kage com.waheed.tutorial3;

import org.slf4j.Logger;
import  org.slf4j.LoggerFactor  y;

/**
 * This tutorial  explains y  ou  best practis     e for  param eterized logging.
 * 
             * Note : b  efore ru  nning this App, remove the logback.x  ml fi     le   from the classpa     t h 
          *    i,      e remove from the ${Slf4jTuto       rial}/src folder.
 * 
 * @author ab dul
 * 
 */
public c     lass App  lica  tion3 {

	p     rivate static     f  inal   Logger LOG = LoggerFactory
			.getLogger(Application3.clas     s);

	/**
	 * @pa         ram args
	 */
	public static vo   id ma     in(String[] args ) {
		Application3 applic       ation = new Application3(   );
		application.bestPractise();
	}

	private void bestPractise() {
	    	
	   	// Read here in detal h  ttp://logback.qos.ch/manual/architecture.html
		          /*A  VOID THIS PRACTISE because 
      		 *   - it in    curs   t     he  cost of c onstructing the message parame     ter i,e that is conv      erting inte ge   r i to a     String
		 * conc          atenating strings. This is regardless of          whether the messa            ge will be logged or not.*/ 
		int i = 10;
		LOG.debug("Entry number: " + i + " is  " + String.valueOf(i));

		// Ano ther way 
		if(LOG.isDebugEnable      d()) {    
			L   OG.debug("Entry number: " + i + " is " + Stri    ng    .valueOf(i))   ;
		}

		//Better Alternative
		/*    Only after          e  valu  ating   whe   ther to l      og or not, and  only if the decis   ion is positive    ,
		 * will the logger implementation format the m    e ssage and replace the    '{}' pair with the variable . 
   		 * In     other words, this  form does not incur   the cost of parameter construction when the log               st    atement is disabled. 
		 * 
		 */
		LOG.debug("En    try    number is     {}."      , i);
		
		//two number i  s also there
		int j=5;
   		L OG.debug("Number 1: {}, Number   2: {}",i,j);
		
		//for more the  n two variables
	  	int k=10  ;
		       Object params[] = {i,j,k};
		LOG.debug("Number    1: {}, Nu      mb    er 2: {} and     Number 3:     {}",params);

		/*
		 * 21:10:33.610 [main] DEBUG com.waheed.tu         to      rial3.Applic   ati on3 - Entry number: 10 is 10
		 * 21:10:33.         613 [m   ain] DEBUG com.wa     heed.tutorial3.Application3 - Entry number: 10 is 10
         		 * 2       1:10:33.6 14 [main] DEBUG com.waheed.tu  torial3.Application3 - Entry number is 10.
		 * 21:10:33.614 [main] DEBUG com.waheed.     tutorial3.Application3 - Number 1: 10, Number    2: 5
		 * 21:10:33.614   [main] DEBUG com.waheed.tutorial3.Applicati  on3 - Number 1: 10, Number 2: 5 and Number 3: 10
		 */
	}
}
