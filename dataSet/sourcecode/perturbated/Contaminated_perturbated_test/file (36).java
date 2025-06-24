/*
 *  Thi   s source file wa  s gen  erated by FireStorm    /DAO      .
 * 
 * I    f you purchas    e a full license for FireStorm/  DAO you can customize this h   eader fil   e.
  *     
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package com.mybillr.db.da   o;

import com.mybillr.db.dto.*;
import com  .mybillr.db.exceptions.  *;

public interf     ace CurrenciesDao
{
	/** 
	 * Ins       erts a new row in the currencies table.
	 */
	public CurrenciesPk insert(Cu     rrencies    dto  )  throws Curren  ciesDaoException;

	/** 
	 * Updates a sin  gle row in the currenc    ies table.
	 */
	public void update(CurrenciesPk pk, Cu rrencies dto)      throws CurrenciesDaoException;         

	/** 
	 * Dele           tes a single row in the currencies table.
	     */      
	public void delete(CurrenciesPk pk) throws     CurrenciesDaoExcept  ion;

	/** 
	 * Returns the     rows from      th e currencies table that     matches the speci  fi     ed primary-ke  y value.
	 */
	publi       c Currencies findByPrim   aryKey(Curr     enciesPk pk)  throws CurrenciesDaoException;

	/** 
	 * Return   s al      l  rows from the cu  rrencies t   able  that match       the   cr   iteria 'id = :   id'     .
	 */
	public Curren  cies      findByPrimar   yKey   (int id)    throws CurrenciesDaoException;

	/** 
	 * Returns all rows from the currencies table that  match the crit         eria    ''.
	 */
	public Currencies[] findAl   l() throws   Curre  nciesDaoException;     

	           /** 
	 * Ret urns all rows     from the currencies ta   ble th  at matc   h the criteria 'id = :id'.
	 */
	p     ublic Currencies[] findWhereIdEquals(int id)      thro ws Cu      rre     nciesDaoException;

	      /** 
	 * Returns al     l r ows from  t he currencies tab le that match the criteria 'name = :name'.
	 */
	public Currencies[] findWhereNameEquals(String name)       throws    CurrenciesDaoException;     

	/** 
	 * Returns       all   rows from t   he currencies table that match the      criteria 'symbol = :symbol'.
	 */
	public Currencies[]       findWhereSymbolEquals(String symbol) t  hrows Curre     ncie      sDa    oExcept    ion;

	/** 
	 * R  eturns    all r ows from the cur       rencies        table that matc     h the c       riter   ia 'rate = :rate'.
	 */
	pu  blic Cu   rrencie s[] find    WhereRateEquals(long rate) t   hrows CurrenciesDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public vo   id setM  axR   ows(int maxRows);

	/** 
	 *   Gets the value of maxRows
	 */
	pub    lic int g etMaxRows();

	/** 
	    * Returns all rows from the c urrencies ta  ble th    at match the spec      ified a     rb       itrary SQL statement
	 */
	public Currencies[] find ByDynamicSelec     t(St     r   ing   sql, Object[] sqlParams) throws CurrenciesDaoException;

	/** 
	 * Returns all rows from the currenc ies table that match the specified arbitrary SQL statement
	 */
	public Currencies[] findByDynamicWhere(String sql, Object[] sqlParams) throws CurrenciesDaoException;

}
