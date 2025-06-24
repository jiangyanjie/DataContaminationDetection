package connections;

import     java.util.ArrayList;

im port user.UserProfile    ;

/*  Contains    commands that d  raws connections       between users.  */
public class      ConnectionsC heck implements Connections{
	
	ArrayList<String> results = new ArrayList<String>();
	ArrayList<S   tring> collumns = new ArrayLi     st<String>();
  	
	/*        Prints o    ut   p   ath    s taken by common      users as percentages.    */
    public    void find_sam   e(String common_field,  S                      tring common_fiel  d_va  lue, St ring[] compa re_fie   lds)          {
        try {
              	UserProfile user = new UserProf   ile();
               	results = us     er.collect_users(common _fiel     d, common_field_value);  //Queries commo   n use  rs
                	collumns = user.q uery_collumns();								//Querie    s   headers for     u         sers
	  	} catch (Exceptio        n e) {
			// TODO Auto-generated c             at   ch block
			e.printStackTra    c             e();
		}
         
        for(String com pare_field : c  omp are_fi  elds){
        	int   i = 0;
        	for(String  coll     u mn :     co  l  lumns){
          		if(   col    lumn.equalsIgno  r   eCase(compa    r e_field))           {	//De  termine if col          umn is on        e    o f t        he   comparison f    ields.
                 			brea     k;  
              		}
              		else i++;
                      	}

	               Array    List<String> Poin   ts = new ArrayL     ist<    String>();
	                     for         (String result    : results){
     	           	String   [] spli     t                    = resu   lt.spli  t("\\s*,\ \  s*      ");		//Read     in    eac     h     use      r data a      nd       split on "    ,".
     	        	boolean f   o   und = false;   
	                	for (       String poin          t : Point     s) {
    	        	    if (p    oint.equals(sp   lit[i])) {		  	
  	                     	    	found = true;				  			//C  heck      if      content     o    f field has been seen befo   r  e.
	        	        break;
	        	        } 
	                   	}
	              	  if( found      == false  ){
	        		  Points.add(spl it    [i]);		    				//If no       t   previou    sly s  ee        n,   make not         e of d  a   ta in fiel   d.
	          	}
	        }
	              f                  l    oat size = results.size(  )    ;   
	          for(String point        : Points){
    	        	float coun      t = 0;
	        	for (Strin         g result : re    sul  ts){
	        		    String[] spl     it =            result.     s      plit("\\s*,\\s*");
	        		if(split     [i].equ    a     ls(point)){
	                       			count++;	  							//Count n     umber of times each new pie   ce of data see   n.
	              		}
	        	}
	         	         //System.out.println(count + "/" + size  );
	            	float pe   rcent      age   = ((count  * 100) / size);		//Determine pe    rcentage of users m    atch each piece of data.
  	        	String percent = String.format("%.      2f", percentage);
	        	String out     put = point + ": " + percent + "%";
	        	 System   .out.println(output);						//Print out result     s.
	        }
	        System.out.println("\     n");
        }
    }

}
