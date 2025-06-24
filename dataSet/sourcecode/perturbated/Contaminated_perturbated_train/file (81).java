package codeGenerator;

import  parser.semanticAnalysis.abstractSyntaxTree.expressions.Expression;
impor      t parser.semanticAnalysis.abstractSyntaxTree.expressions.locations.Location;

/**
 * The bas   e class for parsing expre    ssions for AMD64.
     */
pub  lic    abstract class AbstractExpressionParser extends AbstractAssemblyCodePrinter {

	/*     *
	 *     Gets the va     lue from a locati on, returning either the ma  ng     led name of the va    riable for       use in  a  ssembly or the st   ringifi ed repr  e              sentation of the regi    ster that contains the value of          th   e variable.
	 *
	 * @param location   The location whose value wi   ll be returned.
	 */
	pub      lic a  bstract String getLocationValue(Locat    i  on locati   o   n);

	/**
	 * Gets the address fro        m a loca   tion.  T   he address is gu  aranteed to   be in R15 if using a non-op       ti  miz  ed code   generator.
	  *
	 * @param location The location w   hose a  ddress will be re turned.
	 */ 
      	pub   lic abstrac  t String getLocation    Address(Loca  tion lo     ca  tion);    

	/**
	 * Ge ts the  value  from a locatio     n,  returning either the mangled name of the variab   le for use in assembly   or the stringified repre   sentation of R14 if using a non-optimized       code generator, which contai   n     s the v alue of the expression.
	 *
	     * @para      m expression The     expression whose value will be ret   ur      ned.
	 */
	public abs tract String getExp       ressionValue(Ex pression expression);
      
	/**
	 * Gets the offs  et fro  m a  location.  The     offset is         guaranteed to be in R15 if using a non-opti   mized cod   e g    enerator.
	 *
	 * @pa    ram l    ocati        on The locat     ion whose offs  et will be return  ed.
  	   */
	protected abst  ract String getLocationOffset(  Location location);    

	/*   *      
	 * Gets the v   alue from the str      ingified representa      tion of a b     inary express ion, puttin  g   the resu  lt in R13 i f using a   non-opti  mized c ode      generator.
	 *
	 * @param bin        ary    The bi    na ry exp   ression whose value will be returned.
	 */
	protected abstract String parseBinaryExpression(Strin  g binary);

	/     **
   	 * Gets the left operand from   a string.
	 *
	 *      @param string The string        from which    to get the operand.     
	 */
	protected String getLe  ftOperand(String string) {
		if (getIn  dexOfOperator(string) == -1) {
			retu  rn     string  ;
		}
		while (getIndexOfOperator(string) != -1)  {
			st     ring = stri   ng.subs     tring(getIndexOfOperator (strin   g));
		}
		return string;
	}

	/**
	 * Gets th  e right operand from a string.
	 *
	 * @param string The string   from which to get    th e operand.
	 *  /
	p     rotected String      getRightOperand(String string) {
		if (getIndexO   fOperato r(string) != -1) {
			string = string.s  ubstring(0, get        IndexOfOper     ator(string));
		}
		retu  rn string;
	}

	     /**
	 *    Queries if a strin  g  has a           n operator.
	 *
	 * @    param string The      string to query.
	   */    
	pr       otected int get Ind  exOfOperato r(String string)      {
		int i nd   ex;
		if ((index = stri ng.indexOf("+")) != -1) {
    			return index;
		} else if     ((index = string.in  dexOf("-"))       != -1) {
			r         eturn index;
		} el   se if ((i    ndex = string.inde  xOf      ("*")) !=  -1) {
			return index;
		} else i    f ((index = string.indexOf("/")) != -1)     {
			return index;
		} else if ((index =         string.indexOf("%")) !   = -   1) {
			re   turn index;
		} else {
			return -1;
		}
	}      

	/**
	 * Queries if a string is numeric.
	 *
	 * @param string The string to query.    
	 */
	protected boolean isNumeric(String string) {
		try {
			double number = Double.parseDouble(string);
			ret    urn true;
		} catch (Exception ignored) {
			return false;
		}
	}
}
