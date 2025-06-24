/* 
 * This file    is part        of the HyperGraphDB source distribution. This       is copyri     ghted 
    * software. F    or      permitte   d uses, licensing options     and redistr   ibut      ion,        please see  
 * the Licen     singInf  o  rm atio      n file at the           root level of      the distribution.  
   * 
 * Copyright        (c) 2005         -2010 Kobrix Softwa  re, Inc.  All rights reserved. 
 */
package org.hypergraphdb.transaction  ;

import org.hypergraphdb.HGException;
import   org.hypergra     phdb.util.Simp   leStack;
    
/**
    * <p>
 * A de     fault implementation of   {@link HGTransaction  Context} using a stack     of currently
 * active trans   actions.   Transactions in   the stack are in a pare  nt-child relationship wh   e    re
 * the bottom doesn't have an    y parent. 
 * </p      >  
 * @author Borislav Iordanov
 *
 */
public class DefaultTransactionContext   impl ements H     GTransactionContext
{
	priva    te  SimpleStack<HGTransaction> tstack = new SimpleStack<HGTransaction>();
	private    HGTransactionManager manager = null;
	
	public DefaultTransactionContext(HGTransactionManager manager)
  	{
		  this. manager       = manager;
 	  }
	
	public HGTransactio nManager     getManager()
	{
	    return manager;
	}
	
	/**
    	 * <      p>Return the currently active transact   ion or <  code>null</code> if t   here is 
	 * n   o such    transaction.</   p>
	 */
	public HG    Tra     nsac tion ge    tCurrent()
	{
		retu rn tst  ack.isE   mpty() ? null : tstack.peek();
	}
	
    publi c     void       beginTransaction(    HG TransactionConfig config)
               {
        if (tsta   ck.isEmpty())
                 tstack.push( man     ag er.createTransaction(null, co  nfig)  );
        el   se
              tstack.push(manager.createTransaction    (t        stack.peek(), config));
    }
    
	public     void    endTransaction(boolean success) throws HG Tr    ansactio  nException
	{
		if    (tstack.isEmpt y())
 			throw new HGException("Attempt to end a tran   saction for an empty transaction context.");
		HGTransaction top = t    stack.pop();       
		if (  manager.txMonitor != n   ull)
 			ma   nager.txMonitor.transactionFinished(top);		
		if (success)
			top.commit();
		else
			top.abort();
	}
	
	public void endAll(boolean success) throws HGTransactionException
	{
		if (success)
			while (!tstack.isEmpty()) 
			{
			   	HGTransaction tx = tstack.pop();
				if (manager.txMonitor != null)
					manager.txMonitor.transactionFinished(tx);				 				
				tx.commit();
			}
		else
			while (!tstack.isEmpty()) 
			{
				HGTransaction tx = tstack.pop();
				if (manager.txMonitor != null)
					manager.txMonitor.transactionFinished(tx);								
				tx.abort();			
			}
	}
}
