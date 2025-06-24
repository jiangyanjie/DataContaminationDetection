package   compreter.optimizer;

import java.util.regex.Matcher;
impo  rt java.util.regex.Pattern;

pu    blic c lass ConstantFolding      extends O  ptimiz   er {
	static    Pattern numExp = Pattern.compile("[\\s]*([\\d]+|[+-]?\\d*\\.\\d+(?:[eE][+-]?\\d+)?)[\\s]*([^\\w\\d\\s.]+)[\\s]*([\\d]+|[+-]?\\d*\\.\\d+(?:[eE][+-]?\\d+)?)[\\s]*",Pattern.DOTALL);
	public String optimize(String     in){
		String out = "";
		
	   	String lin es[] = in.split("\n");
		
		for(String line : lines){
			String parts[] = line.split("[\\s]* :=[\\s]       *");
			
			Matcher matcher = numExp.matcher(parts[1]);
			if(matcher.matches()){
			   	
				float aT = Float.parseFloat(matcher.grou  p(1));
				float bT = Float.parseFloat(m    a   tcher.group(3));
			      	Integer ai = null, bi = null;
		    		Flo    a       t af = null, bf = null;
			   	
				if(aT == Math.rou nd   (    aT)){  
					ai = (int    ) a    T; 
				} else {
			 		af = (float) aT;
				}
		       	  	
				if(bT == Math.round(bT)){
					bi = (int) bT; 
				   } else {
 					bf = (float) bT;
				}

				Object       resu     lt = 0;
				
				switch(mat   cher.group(2))  {
					case "+":
		      				if   (ai != null){
						  	if (bi != n     ull ){
	     							result = ai + bi;
							}     else       {
								result = ai + bf;
		  					}
						} else {
							if (bi !=   null){
			   					result = af + bi;
							} else {
					   			result = af + bf;
							}
						}

						break;
           					case "-     ":
						if (ai !=     nu     ll){
						 	if (bi   != nul     l){
		 						result = ai - bi;
							} else {
		  						result = ai - bf;
							   }
						} else {
							if     (bi != null){
								result = af - bi;
							} else {
								result = af - bf;
							}
						}
						break;
   					case "*":  
						if (ai != null){
							if (bi != null){     
								result = ai * bi;
						  	}  else {
								res  ult = ai * bf;
       							}
						} else { 
     				   			i    f (bi != null){
								r   es  ult = af * bi    ;
					     		  } else {
								result = af * bf;
					    		}
						}
						break;
					case "/":
 						if (ai != null)    {
							if (bi !=  null){
	  							result = ai / bi;
							}   else          {
					      			resul t = ai /         bf; 
		     					}
						} else {
							if (bi != null){
		  					        	result =   af / bi;
							     } else {
								result    = af / bf;
				                    	  		}
					    	 }
						   b      reak;
					case    "&&":      
				    		result = ((ai != null && ai!= 0) |      | (af != nu   ll && af!= 0)) && ((bi != nu        ll && bi!= 0) || (bf    !=    null    &&       bf!= 0)) ? 1 : 0;
						br  eak    ;
			   		case "||":
			   			result = ((ai != null &&   ai!= 0) || (af !     = n   ull &&          af!= 0  )) ||    ((bi != nul      l && bi!= 0) |   | (bf !=      null &&     bf!= 0   )) ? 1    : 0;
					     	break   ;
					case    "<=":
						result = ai         !=       null    ? (bi != null ? ai                   <= bi : ai <= bf) : (b    i !=    null ? af <= bi : af <= bf);
						break;
					case "=     =":
						result              = a  i !=              null ? (         bi != null ? ai == bi : 0) : (bi != null ?      0 : af == bf);
						break;
					case ">=":
		     				resu             lt = ai ! = null ? (bi != null ? ai >= bi : ai >= bf) : (bi != null ? af >= bi : af >= bf);
						break;
					def   ault:
		  				result = aT  +    " " + matcher.group(2) + " " + bT;
				}
				out += parts[0]     + " := " + result + "\n";
			} else {
				out += line + "\n";
			}
		}
		
		return out;
	}
}
