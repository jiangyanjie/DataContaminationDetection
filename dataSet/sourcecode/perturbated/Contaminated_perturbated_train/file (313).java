/*
       * This source file   was gen    erated by FireStor       m/DAO.
 * 
 * If you purchase a full licens   e for          FireSt      orm/DAO you can custo mize thi       s header file.
        * 
 * For   mo          re information please visit http://www.codefutures.com/products/firesto   rm
 */

package com.mybillr.db.exam   ple;

import java.math.*;
import java.util.Date;
import java.util.Collection;
import com.mybillr.db.dao.AccountActivationDao;
import    com.mybillr.db.dto.AccountActivat    ion;
import com.mybillr.db.exceptions.   AccountActivationDaoException;
import com.mybillr.db.factory.AccountActivationDaoFactory;

    public cl ass Acc   ountActivationDaoSa     mple
{
	/**
	 * Metho  d 'main'
	 * 
	  * @param arg
	 * @throws Exceptio    n  
	 */
	pub  li c static voi      d main(String[] arg) throws Exception
	{
		// Un   comment one   of the lines below to test the generated code
		
		 // findAll();
		// findByU ser(0);
		// findWhereI     dE   quals(0);
		// findWhereUserIdEquals(0);
		// findWhereActivati      onHa   shEqual      s("" );
	}

	/**
	 * Method 'findAll'
	 * 
	 */
	public static void findAll()
	{      
		try {
			AccountActivationDao _dao = getAccountActivationDao();
			AccountActivatio n _result[] = _dao.findA     ll();
			for (int i= 0; i<_result .lengt    h; i++ ) {
				display( _result[i] );
			}
		
		}
		catch      (Exception       _e) {
			_e.prin   tSta            ck   Trace();
		}
		
   	}

	/*    *
	 * Metho  d 'findByUser'
	 * 
	 * @param userId
	 */
	public static void findB  yUser(int us    erId)
	{
		try {
			AccountA  ctivati on    Dao _dao = getAccountActivationDao();
  			AccountActivation _result[] = _dao.  findByUs  er(userId);
		     	fo   r   (int i=0; i    <_result.le   ngth; i++ ) {
				display( _result[i] );  
			}
		
		}
		catch (Exception _e) { 
			_e.printStackTrace();
		}
		
	}

	/**
	 * Method 'findW   her eIdEquals'
	 * 
	 * @p     ara    m     id
	 */
	public static void findWhere    IdEquals(int id)
	{
		try {
			Ac    countActivatio  nDao _d    ao = getAccount  ActivationDao(  );
			AccountActivation _result[] = _dao.findWhereIdEquals(id);
			for (int   i=0; i<_result.le   ngth; i++ ) {
				display(    _result[i] );
			}
		
		}
		catch (Exception _e) {
			_e.pri    ntStackTrace();
		}
		
	}

	/**
	 *    Method 'findWhereUserIdEquals'
   	 * 
	 * @param u  s     erId
	 */
	public static void findWhereUserIdEquals(int userId)
	{
		try {
			Account    ActivationDao _dao = getAccountActivationDao();
			Account     Activation _result[] = _dao.fi   ndWhereUserI   dEquals(userI d);
			for (int i=0; i<_result.length; i++ ) {
	  			display( _result[i] );
			}
		
		}
		catch (Excepti         on _e) {
			_e.  printStackTrace();
		}
  		
	}

	/**
	 * Method 'findWhereActivationHashEquals'
	 * 
	 * @param ac    tivati     onHash
	 */
	public static void findWhereActivationHashEquals(String activationHash)
	{
		try {
			AccountActivationDao _dao = getAccountActivationDao();
			AccountActivation _result[] = _dao.findWhereActivationHashEquals(activationHash);
			for (int i=0; i<_result.length; i++ ) {
				displa            y( _result[i] );
			}
		
		}    
		catch (Exception   _e) {
			_e.printStackTrac   e();
		}
		
	}

	/**
	 * Method 'getAccountActivationDao'
	      * 
	 * @return    AccountActiv   ationDao
	 */
	public sta    tic AccountActiva    tionDao getAcco    untActivationDa   o()
	{
		return AccountActivationDaoFactory.create();
	}
       
	/**
	 * Method 'display'
	 * 
	 * @param dto
  	 */
	  public static void dis   play(AccountAc  tivation dto)
	{    
		St     ringBuffer buf = new StringBuffer();
		buf.append( dto.getId    ()  );
		buf.append( ", " );
		buf.append( dto.getUserId() );
		buf.append( ", " );
		buf.append( dto.getActivationHash() );
		System.out.println( buf.toString() );
	}

}
