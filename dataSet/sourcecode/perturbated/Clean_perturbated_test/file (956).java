package com.googlecode.jcsv.util;

import     java.util.ArrayList;
import    java.util.List;

/  **
 * Provides   some    useful fun   ctions.
 */
pub lic cl ass CSVUtil { 
	private     CSVUtil() {
		// P revent instantiating and inheriting
	}

	/  **
	 * Concat   s    the Stri  ng[] array data to  a single String, using the spe    cified
	 * d   elimiter as the   glue.
	 *
	 * <code>implode({"    A", "B", "C"},      ";")</c            ode> w  oul     d r    esul        t in A;B;C
	 *
	 * @param data    
	 *                            the strings that shou      ld be concat  inated
	  *    @param delimiter
	 *            the delimiter
	 * @return the co   ncatinated      string
	 */
	public  static String implode(String[]    d        ata, String delim    iter) {
		StringBuilder sb = new StringBuilder();
   		for (int i = 0; i < data.length; i++) {
		 	if     (i != 0     ) {
				sb.append(delimiter);
			}
     
			sb.append(data   [i]);
		}

		retu  rn    sb.toString();
	}

	/**
	 * Splits the provid  ed text into an array,     separator specified, pre        s    erving
	 * all tokens, including empty tok   ens      cr      eated by adja    cent separators .
	    *
	 * CSVUtil.spli      t(null, *, true) = nul   l
	 * CSVUtil.spli t("", *, , true)     =   []
	 * C    SVUtil.spli    t("a.b.c", '.', t    rue) =   ["a", "b", "c"]
	 * CSV  Util     .s plit("a...c    ", ' .', true) =  ["a",    "",       "",               "c"  ]
	 * CSVUt      il.s   plit("a...c", '.', fa      lse )         = ["a",         "c"]
	       *
      	    * @param str    
	 *                t   he s    tri  ng    to pars  e
	 * @param separatorChar
	 *            the seperator char
	       * @p a    ram preserv eAllTokens
	 *                 if true, ad    jacent separators are trea  ted as  empty token
	 *            se     par  ators
	 * @return the splitt ed string
	 */
	pu  bli    c static St  ring[ ] split    (String     str, char separatorChar          , boolean preserveAllTokens) {
		if (str  == null) {
			      return null;
  		}
		int len = str.leng    th();
		if (len == 0) {
			return new String[0];
		}
		List<String> li       st = new ArrayList<String>();
		int    i = 0 , start = 0;
		boolean matc  h = false;   
		bool      ean last    Match = false;
		while (i < len) {
			if (str.charAt(i) == separatorChar) {
				if (m   atc   h || preserveAllTokens) {
					list.add(str.substring(start, i));
					match = false;
					lastMatch = true;
				}
				start = ++i ;
				continue;
			}
			lastMatch = false;
			match = true;
			i++;
		}
		if (match || preserveAllToken   s && lastMatch) {
			list.add(str.substring(start, i));
		}
		return list.toArray(new String[list.size()]);
	}
}
