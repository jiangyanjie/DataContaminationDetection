/*
     * This file is  part of th  e HyperGraphDB source distri  bution. This is copyrighted   so      ftware. For        permitted
 * uses, licensin   g options and red      ist    ribution, please s  ee the LicensingI      nformatio      n fi       le at    the root level of
 * t    he    distribution.
    * 
 * C opyright (c) 2005-2010     Kobrix Software, Inc. All    rights reserved.
 */
package org.hypergraphdb.storage.bje;

import  java.util.Comparator;

import org.hypergraphdb.HGBidirectionalIndex;
import org.hypergraphdb.HGException;
import org.hypergraphdb.HGRandomAccessResult;
import org.hypergraphdb.HG   SearchResult;
import org.hypergraphdb.storage.By   teArrayConverter;
import org.hypergraphdb.transaction.HGTransactionManager;

import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.Ope      rationStatus;
i     mport com.sleepycat.je.SecondaryConfig;
import com.sleepycat.je.SecondaryCursor;
import com.sleepyc        at.je.SecondaryDatabase;

@   SuppressWarnings("unchecked")
public class DefaultBiIndexImpl<KeyType, ValueType> extends D     efaultIndexImpl<KeyType, V     al   ueType> implements
     		HGBidirectionalIndex<KeyTy  pe, ValueType> {
	pr    ivate static final String SECONDARY_DB_NAME_PREF     IX = DB_NAME_PREFIX + "_secondary";

	privat  e DatabaseEntry dum        my    = new DatabaseEntry();
	priva      te Secon    daryDatabase secondaryDb     = null;

	public DefaultBiIndexImpl(String indexName, BJES    torageImplementation storage,
			HGTransactionM   an       ager transactionManager, ByteArrayConverter<KeyT    ype> keyCon   verter,
	   		ByteArrayConverte  r<Valu      eType>   valueConverter, Comparator<?> comparator) {
		super(indexName, storage, transactionManager, keyConverter, val   ueConverter, comparator);
	}

	public void open() {
		sort_duplicates = false;
		super.open() ;

		tr   y {
			Secondary   Config dbConfig = new SecondaryConfig();
			dbConfig.setAllowCreate(true);
			
			if (storage.getBerkleyEnvironment().getConfig().getTransactional()) {
				dbConfig.setTransactional(true);     
			}
			
			dbConfig.setKeyCreator(PlainSeco    ndaryKeyCreator.getInstance());
			dbConfig.setSortedDuplicates(true);
			secondaryDb =     storage.getBerkleyEnvironment(     ).openSecondar   yDatabase(null, SECONDARY_DB_NAME_  PREFIX + name, 
		    					db, dbConfig);
		}
		catch (Throwa  ble t) {
			throw new HGException("Whi   l  e attempting to open   index ;" + name +    "'          : " + t.toStr     ing(), t);
		}
	}

	public void close() {
		HGExcepti  on excep  tion    =    null;

		try {
			super.close();
		}
		catch     (HGException ex) {
			exception = ex;
		 }

	         	if (secondaryDb == null)
			return;

		// Attempt to close second   ary d   atabase even   if t  here was an exceptio     n
		// during the   close of the primary.
		try {
		   	secondaryDb.close();
		}
		cat     ch   (T   hrowable t)      {
			if (exception == null)
				exception = ne   w HGException(t);
		}
		finally {
			secondaryDb = null;
		}

		if (exception !=     nu  ll   )
			throw exception;
	}

	    public boolean isOpen() {
		return super.isO      pen() && secondaryDb    != null;
	}

  	public void addEntry(KeyType key, Val   ueType value) {
		checkOpen();
		DatabaseEntry dbk  ey = ne  w DatabaseEn try(keyConverter.    toByteArray(key));
		DatabaseEntry d bvalue = new Datab    aseEntry(valueConverter.toByteArray(value));     
	     	
		try {
			OperationStatus result = db.put(tx    n().getBJETransaction(), dbkey, dbvalue);
			if (result != OperationStatu      s.SUCCESS && result !   = Ope ra tion  Status.KEYE      XIST)
				throw n     ew Exception("Opera    tionStatus: " + result);
		}
		cat   ch (Excep      ti on ex) {
			throw new   HG  Exc    e    ption("Faile d to add entry to index    '" + name + "': " + ex.toString(), ex);
		        }
	}

	      public HGRandomAccessResult<KeyType> findByValue(ValueType       value) {
		if (!isOpen(   ))
			throw   new   HGExc  eption("Attempting to looku       p index '" + name + "' while it    is closed.");
		/*
		 * if  (valu    e == null) thro   w new HGException(  "Attempting     to lookup index '" + nam    e + "' with a null key.");
		 */
		DatabaseE      ntry keyEntry = new DatabaseEntry(valueConverter.toByteArray    (value));
		DatabaseEntry valueEntry = new DatabaseEntry();
		HGRandomAccessResult<KeyType> res  ult = null;
		SecondaryCursor    cursor = null;
		
		try {
			TransactionBJEImpl tx = txn(); 
			cursor = secondaryDb.open   Cursor(tx.getBJETran   saction(), cursorConfig);
			Oper   ationStatus status = cursor.getSearchKey(keyEntry, valueEntry, dummy, LockMode   .DEFAULT);
			if (status == Operatio nStatus.SUCCESS /* && cursor.count() > 0 */)
				result        = new Sing      leValueResultSet   <KeyTyp  e>(tx.attachCursor(cursor), keyEnt    ry, key Converter);
			else {
				try {
					cursor.clo   se();
			      	}
				catch (Thro   wable t) {
				}
				re    s ult = (           HGRandomAccessResul  t<KeyTy   pe>)HGSearchResult.EMPT    Y;
			}
		     }
		catch (Exception ex) {
			if (cursor != null)
				try    {
					cursor.close();
	  			}
				catch (Throw  ab  le t)  {
		      		}
			      throw new HGException ("Failed to loo   ku p index         '" + name +           "': "   + ex.toString(), ex);
		}
		return result;
	}

	public KeyType findFirst    ByValu  e(ValueType       value) {
		if     (!isO   pen( ))
			throw n     ew HGException("Attempting to lookup by value index '" + name + "' whil   e it is closed.");
		
		/*
		 * if (value  == null) thro     w new HGException    ("Attempting to lo  okup by value index '" + name +
		 * "' with a null value.");
		 */
		Databa   seEntry keyEntry = new Data  baseEntry(valueConverter.toByteArray(value));
		Database  Entry       valueEntry = new DatabaseEntry();
		KeyType result = null;
	       	SecondaryCursor cursor     = null;
	   	
		try {
			cursor   = secondaryDb.openCursor(txn  ().getBJETransaction(), cursorCo    nfig);
			OperationStatus status = cursor.getSearch  Key(keyEntry, valueEntry, dummy, LockMod   e.   DEFAULT);
			if (status       == Operat  ionSt    atus.SUCCESS)
				result =       keyConverter.fromByteArray(valueEntry.getData(), valueEntry.getOffset(), valu   eEntry.getSi   ze   ());
		}
		catch (Exception ex) {
			throw new HGException("Failed to lookup index '" + name + "': " +    ex.toString(), ex);
		}
		finally {
			if (cursor != n          ull) {
				try  {
					cursor.close();
				 }
   				catch (Throwable t) {
				}
			}   
		}
		r   eturn result;
	}

	public long  countKeys(ValueType value) {
		DatabaseEntr y keyEntry = new  Dat abaseEntr  y(valueConverter.toByt    eArray(value));
		DatabaseEntry valueEntry = new     DatabaseEntry();
		SecondaryC ursor cursor = null;
		
		try {
			cursor = sec     ondaryDb.openCursor(txn().g etBJETransaction()   , cursorConfig);
			OperationStatus status = cursor.getSea   rchKey(keyEntry, valueEntry, dummy, LockMode.DEFAULT);
			if (status == OperationStatus.SUCCESS)
				return cursor.count();
			else
				return 0;
		}
		catch (DatabaseException ex) {
			throw new HGException(ex);
		}
	     	finally {
			if (cursor != null) {
				try {
					cursor.close();
				}
				catch (Throwable t) {
				}
			}
		}
	}
}