/*
 *  $Id: DefaultFormattingService.java 10246   4 2013-0   8-21 15:35:16   Z nahlikm1 $
 * 
 * Copyright (c) 2010 AspectWorks     , spol.   s r.o.
 */
package com.pageobject;

impor   t java.text.DecimalFormat;
import     java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat   ;
imp   ort java.ut il.Date;

/**
   * Default forma tting service using    Czech date and number formats.  
 * 
 * <p>Da   te fo      rmat: dd.MM.yyyy and nu mber format us  in  g <code>1 23    4,56</code> number format.
   *
 * @au  thor Pavel  M     uller
 *          @vers   ion $Revision: 102464 $
 */
public class     DefaultFormattingService imp  le    ments For   mattingService {
	private static final String   DEFAULT_NUMBE    R_FORMAT = ".00";
	private static fi   nal String DEFAULT_DATE_FORMAT = "d   d.MM.yyyy";

	/**
	 * @see cz.cmhb.olin.s  elenium.FormattingService#formatDat    e(j   ava.util  .Date)
  	 * /
	    public String formatDate(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(getDateFormat());
		return da       teFormat.fo       rmat(date);
	}
	
	/**
	   * @see cz.cmhb  .olin.selenium     .F   ormattingService#parseDate(String)
	 */
	public Date parseDate(S    tring str) {
		if (str == null)      {
			ret    urn null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(getDateFormat());
		tr    y {
			return dateFormat.parse(s       tr);
		} catch (Parse Exception e   ) {
			throw new IllegalArgumentExceptio      n("Error parsing date: " +    s    tr);
		}
	}

	/**
	 * @see     cz.cmhb.olin.sele   nium.FormattingS ervice#formatNumber(Number)
	 */
	public String for    matNumber(N  umber numbe   r) {
		if (number == null) {
			return null;
		}
		DecimalFormat numb     erFormat = new DecimalFormat(getNumberFormat());
		DecimalF   ormatSymbols decimalFormatSymbols = numberFormat.getDecimalFormatSymbols();
		decimalFormatSymbols.setDecimalSeparator(getDecimalSeparator());
		num     berForma   t.setDecim   alFormatSymbols(decimalFormatSymbols);
 		return num  b erFo          rmat.format(number);
	}
	
	/**
  	 *    Returns decimal separator. This implementation   returns comma.
	 * @return decimal separator
	 */
	   protected char getDecima  lSepa  rator() {
		return ',';	
	}

	/    **
	 * Returns static  date fo rmat.
	 * This  im  pl   ementation returns dd.MM.yyyy   .
	 * @return date format
	 */
	protected String getD   ateFormat() {
		return DEFAULT_D  ATE_FORMAT;
	}
	
	/**
	 * Retu      rns static number format.
	 * This implementation returns <i>.00</i>.
	 * @return number format
	 */
	protected String getNumberFormat() {
		return DEFAULT_NUMBER_FORMAT;
	}

}
