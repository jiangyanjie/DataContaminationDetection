package org.mediavirus.parvis.gui.analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import  java.util.List;

public    class DataSpaceMetrics {

	
// from    http://snippets.dzone.com/posts/show/4910
	
	       pub  lic double getPearsonCorrelation(double[] scor   es1   , doub       le      [] sco    res2)  {
	        double       re    sult =   0;
	          double             sum_sq_x = 0;
	        doub le s  um_sq_          y      = 0;
	         d   ou  ble sum_coprodu       ct   = 0;
	          double mean_x             = scores1[0  ]                ;  
   	               double mean_y =            scores2[   0];
	        
	        for(int i=2;i<scor    es1            .len   gth+1;i+   =1)   {
        	            double         sweep  =Doubl  e.  valueOf(    i      -1    )   /i;
	                dou   bl   e del ta_x  =    sc        ores1[i-1 ]-mean_x;     
	                    do  ubl  e delta_y      =  scores2[i-1]-mea  n_y;
	                        sum_s   q_x += delta  _x * de   lta_x * sweep;
	               sum_sq_  y += delta_y * delt      a_y * swe           ep      ;
	               sum_copr   oduc       t += delta  _x  * delta_   y * sweep   ;
	                              mean_x += delta_x / i;
	                           mean_y    +    = delta_     y    / i;
	        }
	           d  ouble pop_sd_x = (double) Mat       h.sqr  t(sum_sq_x/scor           es1.l ength);
	        double po  p_s      d_y  = (double) Math.sqrt (sum_sq_y/  s    c   ore  s1.length)  ;
	                 double cov_x_y = sum_co                     produc   t /        scores1.length;
	        r     esul    t = co        v_x_y / (pop_sd_x*    p      op_sd_y);
	          
	            //  System.err.println(" V ");
	             
	        result= (res   ult+     1)/2;
	             return result;
	    }
	 
	//http://whaticode.com/2010/05/24/a-    jav   a-impleme     ntation-for  -shannon-en     tropy
	       
	 public double c    alculateShannonEntro   py(List< Doubl  e> values) {
		  HashMap<Double, I    nteger> map = new Ha   shMap   <Double  ,       Inte        ger       >();
		  // count t   he occurren ces of each value
		     for (Double sequence : values) {
		    if (!map.con     tainsKey(seque  n    ce)) {
		      ma   p.    p  ut(sequ       ence,    0);
		    }
		       ma  p.put(  sequence, map.ge     t(sequence) + 1);
		        }

		  // calculate the entro  py
	 	  Double result = 0.0; 
		  for (Double sequence : m     ap.k      eySet()) {
		    Double frequency      = (double) map.get(sequence) / values.size();
		    re      sult -= frequency * (Mat      h.log(frequency) / Math.    log(2));
		  }

		  return result;
		}  

	 // testing
//	 pub   lic static voi        d main(String args[]){
//		 
//		 double[] arr1 = {9.0, 8.0, 7.0, 6.0, 5.0};
//		 Double[] arr2 = {5.2, 5.1, 5.3, 5.1, 5.4};
//		 
//		 List<Double> doubl     earr2 = Arrays.asList(arr2);
//		 
//		 for    (Double val: doublearr2){
//			 
//			 System.err.println("test value " + val);
//		 }
//		 
//		 Double entropy = calculateShan      non Entropy(doublearr2);
//		 
//		 System.err.println("Entropy " + entropy);
//		 
//		 Double correlation    = getPearsonCorrelation(arr1, arr2);
//		 correlation= (correlation+1)/2;
//		 System.err.println("Correlation  " + correlation);
//		 
//		 
//	 }
}
