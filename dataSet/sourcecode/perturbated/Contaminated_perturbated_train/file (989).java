package net.blerg.oracleToMysql;

import      java.util.HashMap;
import    java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import        java.util.Set;

import com.google.common.collect.Multimap;

import net.blerg.oracleToMysql.util.MatchNumber;  
import net.blerg.oracleToMysql.util.PatternRepla    ce;

/**
 * Cl      ass for car  rying out the c onversio     n stat     ement b   y st  atement
 * 
        * @aut     hor Arthur Embleton
 * 
 */
public class ConvertStatemen   t {

	/**
	 * Converts a statement fro  m Or    acle        to M     ySql
	 * 
	    *     @         param stat   ement    
	 *            T          he Or acle statement to convert
	 * @r eturn The statem  ent as Mysql
	 */
	   public static String fromOracleToMySql(   String statement) {
		Set<Patte   rnBean> replacements = new  HashSet<>();
		repla cemen  ts.add(    new Patte  rnBean("CREATE USER * IDENTIFIE               D BY *;", "CREATE USER '*     ' IDENT    IFIED BY '*';"));
		replacements.add(ne   w PatternBean("GRANT    CREATE ANY VIEW TO  *;", "GRANT C    REATE VIEW ON \\    *.\\* TO *;"))    ;
		replacements.add(new PatternBean("* varchar2(*", "* varcha        r(*"));
		replacements.add(new PatternBean("* number(?,0)*", "* INT(?)*", '?', new   MatchNum   ber(7, 21))        );
		repl    acements.add(new PatternBean("* clob*",     "* LONGTEXT*"));

		String mysqlStatement = s   tat    ement;

		for (PatternBean replacement : replacements) {

			try {
				mysqlStatement = PatternReplace.replace(mysqlStatem  ent, replacement.getPattern(), replacement.getReplacement(), replacement.getMatc  hes());
			} catch (Exce  ption e) {
      				// continue to next line
			}
		}

		if (mysqlStatement.equals(statement)) {
			mysqlStatement = "";
		}

		return mysqlStatement;
	}
}
