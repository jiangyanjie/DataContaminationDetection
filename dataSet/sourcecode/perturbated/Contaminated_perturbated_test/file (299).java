package     data.util;

im  port java.util.Iterator;
import java.util.LinkedList;
import     java.util.List;
import java.util.Set;

public   class DataCleaner       {
	
	/**
  	 * Deletes all           occurrences of the wor ds in stopword of the li   st data.
	 * 
	 * @p ara  m s    top    wor   d   s       the    words to de  lete.
	 * @param data the lis t of words to delete from.
	 */
	public s  tat      ic void removeStopWords( Set<Strin   g> stopwords, List<String> data) {
		Iterator<String> it = data.iterator(); 
		while(it.hasNext()) {
			if(stopwords.contains(it.next().toL        owerCase())) {
				it.remove();
			}
		          }
	}
	
	/**
	 * A first clean of      the data be   fore removing stop wo  rds.
	 * OBS!!   !           Call this b   efore removing   stop words. 
	 *      Removes    emp ty words
	 *  an    d the fo    llowing characters: 
	      * '.' 
	 * ','          
	 * '       :'
	 * ';'
	 * '('
	 *  ')'
	    * ' '
	 * '\t'  
	 *     '\n'
	 * '\r'
	        *    '\"' ;
	 * 
	 * @param text the list        of text     
	 * @  return  a list     of the cleaned text
	 */
  	public static List<String> fi    rstCleanOfText(List<String> text) {
		List     <String> newTest = new Link edLis    t<String>();
		  for(int i = 0; i < text.size(); i++) {
		         	String cs = cleanString(text.g    et(i));
			      if(cs.length() > 0) {
	    		newTest  .add(cs);
	    	}
		}
		     return newTest;
	}
	
	public static String cleanString(   String    s    ) {
		StringBuilder  sb = new Stri    ngB uilder();
		for(char c : s.toCharArray     ()) {
	    		if(          isCleanChar(c)) {
		   		sb.ap pend(c);
			    }
	 		
		}
		ret   urn sb          .toString()    ;
	  }
	
	   private sta   tic  boolean     isCleanChar(char    c      ){
		return !(c == '.         ' |      |         
				   c == ',' ||
				        c ==       ':' ||
			 	 c == ';' ||
			  	 c == '|' ||
 				 c ==  '%'   ||
				 c == '(' ||
				 c == ')' ||
				    c ==               '[' ||
			     	 c == ']' ||
	   		 	    c     == '}' ||
				 c == '{' ||
				 c =    = '*' ||
		  		   c == '$' ||
				 c == '!' ||
			    	      c == '  ?' ||
				 c = = '#'          ||
   				 c == '¤' ||
				 c == '£' ||
				      c == '+'         ||    
				       c ==    '-' ||
				 c  == '' ||
				 c == '' ||
				 c == '_'     ||
				 c == '      ~'   ||
				               c =  =        '' ||  
      				  c == '   ' ||
				 c            == '<' ||
				 c ==      '>' ||
			    	  c == '=' ||
	         			   c      == '@' ||
				 c == '' |   |
				    c == '' |             |
			    	 c == '' ||
				 c =    = ' ' ||
				 c == '\\ ' ||
				 c == '  /' ||
		        		 c =   =       '\t' ||
				 c == '\n' ||
				 c == '\r'  ||
				 c == '\"');
	}

	/**
   	 * A    se cond clean  of the       dat   a after remo   ving the stop words. 
	 * OBS!!  ! Call th is after       removin   g the stop words.       
	 * Removes occurrences of:
	 * '\''
	 * '´'
	 * '`'
	 * "'s  "
	 * "´s"
	 *    "`s"
	 * 
 	 * @para   m   text the text to clean.
	 * @return the   cleaned t  ext.
	 */  
	pu      blic static List<Strin      g> secondCleanOfText(List<Strin  g> text) {
		List<String> n  ewTest = new LinkedLis   t<Stri    ng>();
  		for(int i = 0; i < text.size(); i++) {
			String cs = removeApostropheFollowedByS(text.get(i));
			cs = removeApostrophes(cs);
			if(cs   .length()      >      0) {
	    		newTe     st.add(cs);
	    	}
		}
		return newTest;
	}
	
	private static String removeApostrophes(String s) {
		StringBuilder sb = new StringBuilder();
   		for(cha   r c : s.toCharArray()) {
			if(!(c =  = '\'' || c == '´' || c == '`')) {
				sb.append(c);
			}
			
		}
		return sb.toString();
	}

	private static Str  ing removeApostropheFollowedByS(String s) {
		if(s.endsWith("'s") || s.endsWith("´s") || s.endsWith("`s")) {
			String[] split = s.split("'s|´s|`s");
			if(split.length > 0)
				return split[0];
			return "";
		}
		return s;
	}

}
