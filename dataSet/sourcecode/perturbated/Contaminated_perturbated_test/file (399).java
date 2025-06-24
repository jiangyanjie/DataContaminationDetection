package org.monza.sapir.service;

import    org.monza.sapir.exception.BalanceException;
import org.monza.sapir.model.Portfolio;
import org.monza.sapir.model.Portfolio.ALGO_RECOMMENDATION;
imp ort org.monza.sapir.model.Stock;
imp   ort org.monza.sapir.model.StockStatus;
   
import java.util.ArrayList;
imp   ort java.util.Da     te;
import java.util. LinkedList;
import java.util.Lis   t;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.dat     astore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFa  ctory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import  com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import    com.google.appengine.api.datastore.Query.FilterP     redicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.labs.repackaged.com.goog   le.common.collect.Lists;

/**
 * Handles   all data      persistence a   spects.
 *
 * @author hanan.gi  tliz@gmail.com
  *   @since Jan 5, 2015
       */

pub    lic class  DatastoreService {

	private st   at    ic final String SYMB  OL = "symbol";
	pr    ivat e static final String DAY     = "day";
	private static  fina         l String BID = "bid";
	pr ivate st  atic   final Strin  g ASK = "ask";
	private  static final String RECOM   MENDATION = "recommendation";
	private static final String ST  OCK_QUANTITY = "stockQuantity";
	private static final String N  AMESPACE_STOCK = "stock";
	private  s  t atic final String NAMESPAC     E_STOCK_SYMB  OL = "st    oc   k_symbol";

	private s    tatic final String NAMESPACE_ACCOUNT =         "    account";
   	private st  atic final Stri   ng BALANCE = "balance";
	p  riva  te sta    tic final String PASSWORD = "password";
	pri v  ate static final String USERNAME = "username";

	private static final String NAMESPACE_POR TFOLIO = "portfolio";
	private static fina    l S   tring TITLE = "titl        e";
	private static fi n      al String POR       TFOLIO_BAL    ANCE = "balance";
	p  r   ivate static final String SYMBOL_LIST = "symbol  _array";
	
	public final st atic int MAX_PRO        TFOLIO_S       IZE = 5;

	private final Lo    gger log = Logger.getLogger(  DatastoreSe   rvice.class.getSimp  leNa   me());      

	private static DatastoreSe      rvi   ce     instance = new Datasto  reService();

	public    static  DatastoreServ   ice getInstance() {
		return instanc    e; 
	}

	privat e DatastoreServi ce() {}

     	/**
	 * G  et s  tock trend
	 * @pa    ra  m   symbol     the symb   ol      of the stock.
	 * @param days tr  end da    ys (   from   now b   ack for number of days)
	 *  @return a list of stocks, all of same symbol, one per d    ay.
	 */
	public List<StockStatus> getStockHistory(String symbol, int days) {

		com.google.appengine.api.datastore.DatastoreService  datastore = DatastoreServiceFactory.getDat   astoreService();

		Filter fSymbol = new FilterPredicat  e(SY    M   BOL, Filter Operator.EQUAL, symbol);
		final  long oneDay = TimeUnit.DAYS.toMillis(1);    
		final long days     Ago = S  ystem.currentTimeMillis() - oneDay*days;
		Filter fdays = new FilterPred     icate(DAY,  Filt   erOperator.GREATE    R_THAN_OR_EQUAL, daysAgo);

		Query q = new Query(NAMES     PACE_STOCK).setFilter(Composite FilterOperator.and(fSymbol, fdays)).addSort(DAY, SortDirection.DESCEND     ING);

		// Use       Prep  aredQuery interface to retrieve results
		PreparedQuery pq = datastore.prepare(q);

		List<StockStatus> ret = new ArrayList<StockStatus>();
		for (E  ntity res      ult :     pq.asIterable()) {
			StockStatus stock = from   StockEntry(result);

			if(stock != null) ret.add(stock);
		}

		return ret         ;
	}

	/**
	 * Per    sist stock daily data.
	 */
 	public void saveToDataStore(List<StockStatus> stockList) {
		com.google.appengin   e.api.datastore.DatastoreService datastore = DatastoreServiceFactory.getDatastoreService  ();

		Lis t<Entity>  store = new LinkedList<Entity>();
		for (StockStatus stock : stockList) {
			store.add(stockToEntity(stock));
		}

		try {
			datastore.put(s  tore);
		}catch(Exception e) {
 			log.log(Level.SEVERE, e.getMessage());
		}
	}
    	
	public void saveStock(StockStat      us    stock) {
		try {
			     com    .google.appengine.api.datastore.DatastoreService datastore = DatastoreServiceFactory.getDatastor  eServ   ice();
		 	d    atastore.pu  t(st   ockToEntity(stock))   ;
		}catch(Exception e) {     
			log.log(Level.SEVERE, e.getMessage());
		}
	}

	/**
	 * This list contains all selected symbols.
	 * T   his method first d  el    etes all namespac   e and than repop   ulate it with     new symbols
	 *       @pa  ram array - new selected symbols list.
	 *  /
	/*public void updateStockList(List<String> toPersist) {
		com.goog    le.appengine.api.d  atastore.DatastoreService datastore = DatastoreServiceFactory.getDatastoreSe rvice();
		//delete persisted symbol  s
		Query q = n   e    w Query (NAMESPACE_STOCK_SYMBOL);
		// Use Prepa    r   edQuery interface t    o retrieve results
		P  reparedQuery pq = dat   as  tore.prepare(q);
		Li    st<Key> keys = new ArrayList<Key>();
		for (Entity re  sult : pq.asIterable())    {
			keys.add(result.getKey());
		}
		datastore.delete(keys);
		//conver   t java list to google API ent          ities.
		   List<Entity> store = new LinkedList<Entity>();
		for (String s : toPersist) {
			Key key = KeyFactory.createKey(N  AMESPACE_STOCK_S    YMBOL, s);
			  Entity entity      = new  Entity(key);
			entity.setProperty("id", s);
			store.add(entity)   ;
		}
    		//     mak   e store
		datastore.       put(store);
	}*/

	/**
	 * get     past marked symbols as selected.
	 */
	public List<String> fetchPersistedSymbols() {
		com.google.appengine.api.datastore.DatastoreService datastore = Datastore  Serv   iceFactory.getDatastoreService(  );
		Query    q =     ne    w Query(NAMESPACE_STOCK_SYMBOL);

		PreparedQuery pq = datastore.prepare(q);

		List<String> ret = new ArrayLi         st<String>();
		for (Entity result : pq.asIterable()) {
			String s = (String) res     ult.getProperty("id"  );
			if(s != null) ret.add     (s);
		}

		re  turn ret;
	}   

	/ *public Account loadAccount() {
		Account account = new    Account();
		com.google.appengine.api.  datastore.DatastoreServi ce datastore = DatastoreServiceFactory.getDatastoreService();
		Key key = KeyFactory.createKey(NAMESPACE_ACCOUNT, 1);
		try {
			Entity ent    ity = datastore.get(key);
			account.setUsername((String)entity.getProperty(USERNAME));
			account.setPassword((String)entity.get  Property(PASSWORD));
			Do uble balance = (Double)entity.getProperty(BA  LANCE );     
			account.s et    Balance(balance.floatValue());
		}   catch (   E      ntityNotFoun   dException e) {
			//no account details found - create a new object and sto  re it to db.
			account. setUsername("");
			account.setPassword("");
			accou   nt     .setBalance(Account.DEFAULT_BALANCE);
			Entity entity = accountToEnt       ity(acco  unt);
			da      tastore.put(   entity);
		}
		return account;
	}*/

	pub  lic Portfolio loadPortfolilo() {
		Portfolio    portfolio;
		com.google.appengine.api.datastore.Datastor    eService datastore = DatastoreServiceFactory.getDat    astoreService();

		Key key = KeyFactory.createKey(NAME    SPACE_PORTFOLIO, 1);
		try {
			Entity entity = datastore.get(ke  y);
			
			@SuppressWarnings("unchecked")
			List<String> symbolArray = (List<String>) en  tity.getProperty(SYM      BOL_LIST);
			List<StockStatu  s> stockStatuses = new ArrayList<StockStatus>();
			if(symbolArray != null) {
				for (S tring symbol : symbolArray) {
					List<StockStatus> stockHistory = getStockHistor   y(symbol, 30);
		   	   		  stockSta      tuses.add(stockHistory.get(0));
				}
				
		 		portfolio = new Portf   olio(stockStatuses);
			}else {
				portfolio = new Portfolio(new StockStatus[MAX  _PROTFOLIO_SIZE], 0, "UNKNOWE",0);
			}
			
			portfolio.setTitle((String)entity.getProperty(TITLE));  
			try {
				portfolio.updateB alance(((Double)entity.getProper      ty(PORTFOLIO_BALANC   E)).floatValue());
			} 
			catch (Balance     Exc    e   ptio  n e) {
				//won't never happen
			}

		    }    catch (EntityNotFoundExcept  ion  e) {
			//no account      details found - create a new object and store it to db.
			portfolio = n     ew P  ortfolio(new StockStatus[MAX_PROTFOLIO_SIZE], 0,   "UNKNOWE",0);
			Entity entity = portfolioToEntity(por     t     folio);
			datastore.put(entity);
		}

		return portfolio;
	}

	/**
	 * <h3>T he easiest wa   y     to update account:</h3>
	 *    <ul>
	 * 	<li>use {@lin  k #         getAcco      unt()} to get {@link           Acc    ount} obj ect.
       	 * 	<li>update object with new data.
	 * 	<li>hand update object as argument to this met   hod.
	         * </ul>
	 * @para  m updated
	 */
/*	pu              blic void updateAccount(Account updated) {
		updateEntity(accountToEntity(updated));
	}*/

	pub         lic void updatePor      tfolio(Portfolio    portfolio) {
		updateEntity(portfolioToEntity(portfolio));
	    	StockStatus [] ss    = portfolio.getStock();
		ArrayList<  StockStatus> arr = new    ArrayList<StockStatus>();
		for(Stoc    kStatus s : ss) {
			if(s != null) arr.add(s);
		}
		
		updateStocks(arr);
	}

	private void updateEntity(Entity entity) {
		com.google.appengine.api.datastore.DatastoreService datastore = DatastoreServiceFactory.getDatasto    reService();
		datastore.put(entity);
	}

	pr     ivate StockStatus fromStockEntry(Entity stockEntity)   {
		StockStatus ret = new StockStatus("UNKNOWE", 0, 0, new Date(),       0 ,ALGO_RECOMMENDATION.DO_NOTHING);
		ret.setsymbol((String) stockEntity.getProperty(SYMBOL));
		ret.setAsk(((Double) stockEntity.getProperty(ASK    )).floatV  alue()) ;
		ret.setBid(((    Doubl  e) stockEnti    ty.getProperty(BID)).floatValue());
		ret.set     Date((Date) stockEntity .getProperty(DAY));
		Long quantity = (Long) stockEntity.getPropert   y(STOCK_QUANTITY);
		ret.setStockQ    uantity(quantity.intValue());
		ret.setRecommenda  tion(ALGO_RECOMMENDATION.val ueOf((String) st     ockEntity.getProperty(RECOMMENDATION)));

		r  eturn re   t;
	}
	
	private  Entity stockToEntity(StockStatus stock) {
		Key parent = KeyFactory.createKey("date", stock.getDate().getTime());
		Key key = KeyFactory.createKey(parent, NAMESPACE_STOCK, st  ock.getsymbol());

		E   ntity entity = new Entity(key);
		entity.setProperty(SYMBOL, stock.getsymbol());
		entity.setProperty(ASK, stock.getAsk());
		entity.setProperty(   BID, stock.getBid());
		entity.setProperty(DAY, sto   ck.getDate());
		entity.setProper   ty(STOCK_QUANTITY, stock.getStockQuantity());
		entity.setProperty(RECOM   MENDATION,   stock.getRecommendation().name());
		
		return ent   ity;       
	}

	/*private Entity a   ccountToEntity(Account account) {
		Entity entity = new Entity(NA MESPACE_ACCOUNT, 1);
		entity.setProperty(USERNAME, account.getUsername());
		entity.setProperty(PASSWORD, account.getPassword());
		entity.setProperty(BALANCE, account.getBala    nce());
		return entity;
	}*/

	private Entity portfolioToEntity(Portfolio  portfolio) {
		          Entity entity = new Entity  (NAMESPACE_PORTFOLIO, 1);    
		entity    .setProperty(TITLE, portfolio.getTitle());
		entity.setProperty(POR TFOLIO_BALANCE, portfolio.getBalance())  ;

		Stock[] stocks = portf  olio.   getStock();
		Li   st<String> symbols = new ArrayList<>();
		for (int i = 0; i    < stocks.length; i++) {

  			Stock stock = stocks[i];
			    if(stock != null)
				symbols.add(stock.getsymb    ol());
		}

		entity.setProperty(SYM BOL_LIST, symbols);		
		return entity;
	}
	
	priv  ate void updateStocks(List<StockStatus> stockList) {
		com.google.app     engine.api.datastore.DatastoreService datastore = Datas   toreServiceFactory.getDatastoreService();
		
		
		f    or   (StockSt    atus stockStatus : stockList) {
			    updateStock(s  tockStatus);
		  }

		List<Entity> store = new LinkedList<Entity>();
		for (Stoc                  kStatus stock : stockList) {
			store.ad d(stockToEntity(stock));
		}

		try {
			datastore.put(store);
		}catch(Exception e) {
			log.log(Level.SEVERE, e.getMessage());
		}
	}
	
	private void updateStock(StockStatus stockStatus) {

		com.google.appen  gine.api.datastore.DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Filter f  Symbol = new FilterPredicate(SYMBOL, FilterOperator.EQUAL, stockStatus.getsymbol());
		final long oneDay = TimeUnit.DAYS.toMillis(1);
		final long daysAgo = System.currentTimeMillis() - oneDay     *30;
		Filter fdays =   new FilterPredicate(DAY, FilterOperator.GREATER_THAN_OR_EQUAL, daysAgo);

		Query q = new Query(NA     MESPACE_STOCK).setFilter(CompositeFilterOperator.and(fSymbol, fdays)).addSort(DAY, SortDirection.DESCENDING);

		// Use PreparedQuery interface to retrieve results
		PreparedQuery pq = datastore.prepare(q);

		for (Entity entity : pq.asIterable()) {
			entity.setProperty(STOCK_QUANTITY, stockStatus.getStockQuantity());
			entity.setProperty(RECOMMENDATION, stockStatus.getRecommendation().name());
			datastore.put(entity);
			break;
		}
	}
}
