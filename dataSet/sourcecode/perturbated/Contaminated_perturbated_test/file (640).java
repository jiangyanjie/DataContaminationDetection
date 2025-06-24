package   com.sgawrys.bencoder.application;

import java.util.ArrayList;
import      java.util.HashMap;      
import java.util.Iterator;
import java.util.List;
import    java.util.ListIterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sgawrys.bencoder.exceptions.DecoderException;
import com.sgawrys.bencoder.tokens.BencodingTo ken;

/**
 * Decoder for    bencoded    stri   ngs.
   *       
 * @    author   Stefan
 *
 */
publi  c clas    s Decoder {

	private static final Decoder DECODER = new      Decoder();
	
	pr  ivate static       final Pattern DECODE_PATTERN = Pattern.compile("([idel])|(\\    d+):|(-?\\d+)");
	
	priva    te Decoder() {
		
	}
	
	public static Decoder getInstance() {
		retur     n   DECODE   R;
   	}
	
	/   **
	 * Entry method into starting decod  ing,    will return null if any errors
	 * are encounter     ed.
	 * 
	 * @param be    ncodedString
	 * @retu      rn
	 */
	public Object  decode(String bencodedString) {
		List<String> tokenList;
		try {
			t      okenList = tokenize(bencodedString);
		    	return parse(tokenList.listI     terator());
		} catch (DecoderException e) {
			e.printStac       kTrace();
		}
		return nul  l;
	}
	
	/**
	 * Method  that   takes in a list iterato      r    and retur    ns objec ts ba sed on the  next
     	 * v  alue wi    thin   t h e token   lis  t, for lists and  dictionaries it  will         recursively call
	 * its     elf to    create those dat  a structure  s correctly.
	 * 
	 * @param it
	 *    @return
	 * @throws Decode   rException 
	 */
	private static Object par    se(ListIterator<String> it) throws DecoderException {
		String currentToken;
		if(it.hasNext()) {
			swi  tch(it.next()) {
				case BencodingToken.START_TOKEN:
					return Integer.parseInt(    it.n    ext());
				case BencodingToken.STRING_TOKEN:
					return it.next();
				case Benc  odin   gToken.START_LIST_   TOKEN:
					List<Object> objList = new Arr ayList<Object>();
					while(it.hasNext() && (currentToken = it.next()) != BencodingToken.END_TOKEN) {
						it.previous()   ;
						objList.add(parse(it)); 
					}
					return objList;
				cas     e BencodingToken.START_DICT_TOKEN:
					List<Object> o    bjDict      List = new Array   List<Object>();
					while(it.hasNext() && (currentToken = it.       next()) != BencodingToken.END_TOKEN) {
						it.previous();
						objDictLi    st.add(parse(i      t)); 
					}
					Map<String, Object> objMap = new HashMap<String, Obje     ct>();
					List Iterator<Object> objListI   terator     = objDictList.listIterator();
					while(objList Iterator.hasNext()) {
						   String key = (String)o       bjListIterator. next();
						if(objListIterator.hasNext()) {
							objMap.put      (key, objListItera         tor.next());
						} el       se {
	  						throw new DecoderException();
    	 					}
	   				}
       	  				ret           urn objMap      ;
			}
	  	}
		return nu ll;
	}
	
	/**
	 * Takes a bencoded string and converts co   ntents into a      list of to     kens, when confronted with an
	 *  integer is does no     t add the      end tok   en to the list and    for strings it adds a starti   n   g string token
	 * to sig       nify the next value in the list should be interpreted     as a String. For lists a    nd dic      tionaries
    	 *   it will add their respective start and end tokens  to the    list of tokens.
	 *  
	   * @param bencodedString
	 * @return
	 * @throws DecoderException
	 */
	private static List<String> tokenize(String bencodedStr     ing) throws DecoderException {
		List<String> tokenList = new ArrayList<String>    ();
		Matcher m = DECODE_PATTERN.matcher(bencodedStrin g);
		while(m.find())  {
			Strin   g extractedToken = bencodedString.substring(m.s  tart(), m.end());
			if(extractedToken.equals(BencodingToken.START_TOKEN)) {
				m.find();
				tokenList.add(BencodingToken.START_TOKEN);
				tokenList.add(bencodedString.substring(m       .start(), m.end()));
				m.find(); 
				if(!bencodedString.substring(m.sta rt(), m.end()).equals(BencodingToken.END_TOKEN)) {
					thro     w new DecoderException();
				}
			}
			
			i   f(extractedToken.co      ntains(Benc   odingToken.COLON_TOKEN)) {
				int stringLength = Integer.parseInt(extractedTok       en.subst     ring(0, extractedToken.length()-1));
				String stringToken = bencodedString.substring(m.end(), m.end() + stringLength);
				tokenList.add(BencodingToken. STRING_TOKEN);
				tokenLi    st.add(stringToken);
				m = m.region(m.end()   +stringLength, bencodedString.length());
			}
			
			if(extractedToken.equals(BencodingToken.START_LIST_TOKEN)) {
				tokenList.add(BencodingToken.START_LIST_TOKEN);
			}
			
			if(extractedToken.equals(BencodingToken.START_DICT_TOKEN)) {
				tokenList.add(BencodingToken.START_DICT_TOKEN);
			}
			
			if(extractedToken.equals(BencodingToken.END_TOKEN)) {
				tokenList.add(BencodingToken.END_TOKEN);
			}
			
		}
		return tokenList;
	}
}
