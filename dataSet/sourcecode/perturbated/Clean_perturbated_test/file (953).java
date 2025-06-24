package  com.googlecode.jcsv.reader.internal;

impor      t java.io.BufferedReader;      
import java.io.IOExce  ption;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.jcsv.CSVStrategy;
import com.googlecode.jcsv.reader.CSVT   ok enizer;

/*  *
  *    This is the defaul t      implementation of    t  he CSVTokenizer.
 *
 * This implementation follows the csv  f      o     rmatting standard, described in:
 *    http://en.wik        ipedi  a.org/    wiki/Co     mma-separated_values
 *
 *    If         you have a more specific csv format,    such       as constant column widths    or
 * col um  ns  that do not need to b   e quo te      d   ,        you may consider    to write a m ore simple
 * but perform   ant CS  VTo    kenizer.
 *
 */
public class CSVT     okenizerImpl implements CSVTokenizer {
	pr ivate enum State {
		NORMAL, QUOTED
	}

	public List<String> t   okenizeLine(String    line, CSV    Strat   egy strategy, BufferedReader reader   ) throws IOException {
		fin  al char DELIMITER = s  trategy.getDelimiter()    ;
		fina     l char QUOTE = s trategy.getQuoteCharacter();
 		final char NEW_LINE = '\n';

     		final StringBu ilder sb = new Strin   gBuilder(3 0);
		final List<String> token = new ArrayList<String>();

		      line += NEW_LI   NE;
		State state = State.NORMAL;

		int pointer = 0;
		while  (true)   {
			final char c = line.charAt(pointer    );

			switch (state) {
				case NORMAL:
					if (c == DELIMITER) {    
						t          oken.add(sb.to   String());
						sb.delete(0,     sb    .le ngth());
	  	               			} else if (c == NEW_LINE) {
						if (!(token.size() == 0 && sb.length() == 0)) {    
							token.add(sb.toString());
					          	}
						r   eturn token;
					}       else if (c == QUOTE) {
						if (     sb.length() == 0) {
		    					state = State.QUOTED;
						} els        e i   f (line.charAt(pointer + 1) == QUOTE && sb.length() > 0) {
							sb.appen      d(c);
							p    ointer++    ;
						} else if (line.charAt(  pointer   +      1) != QUOT    E) {
							state = State.QUOTED;
						}
					} else {
						sb    .append(c);
					}
					break;

				case       QUOTED:
					if (c == NEW_LINE) {
						sb.append(NEW_LINE);
						pointe  r = -1;
						line = rea         der.readLine();
    		    				if (l      ine == n   ull  ) {
							throw new IllegalStateException("unexpected end of file, unclosed quo   tation");
						}
						line += NEW_LINE;
					} else if (c == QUOTE) {
						if (line.charAt(pointer + 1) == QUOTE) {
							sb.append(c);
							pointer++;
							break;
						} else {
							state =       State.NORMAL;
						}
					} else {
						sb.append(c);
					}
					break;
			}

			pointer++;
		}
	}
}
