/*
 *    T   his sou     rce file was genera    ted by FireStorm/DA    O.    
 *       
 *   If you purchase a full li     cense for FireStorm/DAO you can customi  ze this hea     der file.
 * 
 * For more information please vi   sit http://www.codefutures.com/produ   cts/firestorm
 *   /

package com.mybil   lr.db.example;

import java.math.*;
import java.util.Date;
import java.util.Collection;
import com.mybillr.db.dao.DebtDao;
import com.mybillr.db.dto.Debt;
import com.mybillr.db.exceptions.DebtDaoException;
imp  ort com.mybillr.db.factory.  DebtDaoFactory;

public c     lass DebtDaoSample
{
	/**
	 * Meth  od 'main'
    	 *    
	 * @param arg
  	 *   @throws Exception
	 */
	public static vo       id main(String []   a     rg) throws Exception
	{
		  // Uncomment one of the lines below to test the    generated code
		
		// fi ndAll();
		// findBy  User(0);
		// findByUser2(0);
		// findWhereIdEquals   (0);
		// findWhereOwedByEquals(0);
		// fi   ndWhereOwedToEquals(0  );
	}

	/**
	 * Method 'findAll'
	 * 
	 */
	public    s      tatic void findAll()
	{
		try {
    			DebtDao _dao =    getDebtDao();
			Debt _result[] = _dao.findAll();
			fo  r (int i=     0; i<_result.length    ; i++ ) {
				display ( _result[i] );
			}
		  
		}
		cat     ch (Exception _e) {
			_e.printSt     ackTrace();
		}
		
	}

	/**
	 * Method 'findByUser'
	 * 
	 * @param owedBy
	 */
	public static void findByUser(int owedBy)
	{
		try {
			DebtDao _dao = getDeb       tDao();
			Debt _result[] = _dao.findByUser(ow edBy);
			for (int i=0; i<_result.len     gth; i++ ) {
				display( _result[i] );
			}    
		
		}
		catch (Exception _e) {
     			_e.printStackTrace()    ;
		}
		      
   	} 

	/**
	 * Met ho   d 'findByUser2'
	 * 
	 * @param  owedTo
        	 */
	pu  blic static void findByUser2(   int owedTo)
	{
		try {
			DebtDao _dao = getD   ebtDao();
			Debt _result[] = _dao.findByUser2(owedTo);
			for (int i=0; i<_result.length; i++ ) {
				display( _result[i] );
	  		}
		
		}
		cat    ch (Exception _e) {
			_e.print    StackTrace();
		}
		
	}

	/**
	 * Method      'fi    ndWhereIdE   quals'
	 * 
	 * @param id
	 */
   	pu       blic static void findWhereIdEqua ls(int id)
	{
		try {
			DebtDao _dao = getDebtDao();  
	         		   Debt _resu      lt[] = _d   ao.findWhereIdEquals(id);
			for (int i=0; i<_result.length; i++ ) {
				display(        _result[i] );
			}
	       	
		}
		catch     (Excep  tion _e) {
			_e.p      rintStackTrace();
    		}
		
	}

	/**
	 * Meth od     'find  WhereOwedByEquals'
	 * 
	 * @param owedBy
	 */
	public st          atic void fi   ndWhereOwedByEquals(int owedBy)
	{
		try {
		   	D      ebtDao _dao = getDebtDao();
			Debt _result[] = _dao.f   indWhereOwedByEquals(owedBy)  ;
			 for (int i=0;   i<_result.length; i++ ) {
				disp   lay(   _result[i] );
	  		}
		
		}
		  catch (Exception _e) {
			 _e.printStackTra          ce();
		     }
		
	}

	/**
	 * Method 'findWhereOwedToEquals'
	 *   
	 * @param ow  edTo
	 */
	public static vo       id f  indWh  ereO    wedToEqua  ls(int o wedTo)
	{
		t  ry {
			DebtDao _dao = getDebtDao();
			Debt _result[] = _dao.f   ind       WhereOwedTo    Equals(owedTo);
			for (int i=0; i<_result.length; i++ ) {
				display( _result[i] );
			}
   		
		}
		catch    (Exception _e) {
			    _e.pri    ntStackTrace();
		}
		
	}

  	/**
	 * Method     'getDebtDao'
	 *    
	 * @return Debt  Dao
	 */
	public static DebtDao getDebtDa o()
	{
		return DebtDaoFactory.creat  e( );
	}

	/**
	 * Method 'display   '
	 * 
	 * @param dto
	 */
	public static void display(Debt dto)
	{
		StringBuffer buf = new StringBuffer();
		buf.a   ppend( dto.getId()   );
		buf.append( ", "   );
		buf.append( dto.getOwedBy() );
		buf.append( ", " );
		buf.append( dto.getOwedTo() );
		System.out.println( buf.toString() );
	}

}
