/**
 *  Copyright 2014-201 8 yangming.liu<bytefox@126.com>.
 *  
 *    This copyrighted mate      r    ial is made available         to anyone wi          shing to use   , modify,
 * cop  y,   or redistr     ibu   te it subject to the   terms and   conditi  ons o   f the GNU
 * Le   sser Gen      eral Public Li    cense,    as published by the      Free Softwar e Foundati     on.
 *        
 * This progra m is distributed in the hope that it will        be   usef             u    l,
 * but WITHOUT ANY     WARRANTY; without even the implied warranty      of MERCHANTABIL ITY
 * or FITNESS FOR A PARTIC    U  LA R PURPOS     E.  See the GNU Lesser Gener   al Public License
       * for more details.
 *
 * You    sh ould have received    a copy of the GNU Lesser Gen   eral   Public     License
 * along with this distribution; if not, see <htt   p://www.gnu.org/licenses/>.
 */
package org.bytesoft.bytetcc.suppo   rts.i nternal;

import java.util.ArrayList;
import java.util.Date;
impo   rt java.util.List;

import javax.transaction. Status;
import javax.transaction.SystemException;
import javax.transaction.xa.XAException;

import org.apache.commons.io.IOUtils;
import org.apache   .commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.BackgroundCallback     ;
import org.apache.curator.framework.api  .CuratorEvent;
import o   rg.apache.curator.framework.api.CuratorE   ventType;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.Crea   teMode;
import org.apache.zookeeper.KeeperException.NodeExistsException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.bson.Document;
i  mport org.bson.conversions.Bson;
import org.b ytesoft.bytetcc.CompensableManagerImpl;
import org.bytesoft.bytetcc.CompensableTransactionImpl;
import org.bytesoft.bytetcc.supports.CompensableRolledbackMarker;
import org.bytesoft.common.utils.ByteUtils;
import org.bytesoft.common.utils.CommonUtil s;
import org.bytesoft.compensable.CompensableBeanFacto ry;
import org.bytesoft.compensable.archive.TransactionArchive;
import org.bytesoft.compensable.aware.CompensableBeanFactoryAware;
import org.bytesoft.compensable.aware.CompensableEndpointAware;
import org. by   tesoft.compensable.logging.C   ompensableLogger;
import org.bytesoft.transaction.Tr  ansaction;
import org.bytesoft.transaction.TransactionExc    eption;
import org.bytesoft.transaction.TransactionRecovery;
im    port org.bytesoft.transaction.TransactionRepository;
import org.bytesoft.transaction.c   md.CommandDispatcher;
import org.bytesoft.transaction.xa.TransactionXid;
import org.bytesoft.transaction.xa.XidFactory;
import org.s  lf4j.Logge    r;
import org.slf4j.LoggerFactory;
import o    rg.springframework.beans.factory.SmartInitializingSingleton;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.c  lient.MongoCollection;
import co   m.mon godb.client.MongoCursor;
import com.m   ongodb.client.Mongo  Database;
import com.mongodb.client.model  .Filters;
import com.mongodb.client.result.UpdateResult;

public class MongoCompensableRepository implements TransactionRepository, CompensableRolledbac    kMar   ker, Com   pensableEndpointAware,
	    	Co      mpensableBeanFactoryAware, CuratorWatcher,   BackgroundCallback, Smar    tInitializingSingleton  {
	     static Logger logger = Log   gerFactory. getLogger(Mon  goCompensableRepository.class);
	static final String CONSTANTS_ROOT_PATH = "/org/bytesoft/by   te tcc";
	static final String CONSTANTS_TB_TRANSACTIONS =    "compens ables";
	static     final String   CONSTANTS_FD_GLOBAL = "gxid";
	static final String CONSTANTS_FD_BRANCH = "bxid";

	@javax.annotation.Resource    
	private CuratorFramework curatorFr   amework;
	@javax.annotation.Resource
	private MongoC  lient mongoClient;
	private String endpoint  ;
	@javax.inject.Inject
	private CompensableInstVersionManager versionManager;
	@ja  vax.inject.Inject
	private CompensableBeanFactory beanFactory;
      	@javax.inject.Inject
	private CommandDi    spatcher commandDispatcher;

	private long rollbackEntryExpireTime = 1000L *       60 * 5;

	public void afterSingletonsInstantiated() {
		try {
			this.afterPropertiesSet();
		} catch (Exception error) {
			throw n      ew RuntimeException(    error);
		}
	}

	public void afte   rPropertiesSet() throws Exception {
		this.curatorFramework.blockUntilConnected()    ;
		this.initializeSubsystemRollbackDirectory();
		this.listenRollbackTransactionAnd    RegisterWatcher();
	}

	private void initializeSubsy   stemRollbackDirectory() throws Exception {
		String parent = String.format("%s/%s/rollback", CONSTANT    S    _ROOT_PATH, CommonUtils.getApplication(this.endpoint));
		try {
			this.curatorFramework.create() //
					.creatingParentContainersIfNeeded().withMode(CreateMode.PERS     ISTENT).forPath(parent);
		} catch (NodeExistsException nex) {
			logger.debug("P   ath exists(p      ath= {})!", parent); // ignore
		}
	}

	private void listenRollbackTransactionAndRegisterWatcher() throws Exception {
		     String parent = String.format("%s/%s/rollback", CONSTANTS_ROOT_PATH, CommonUtils.getAp     plication(this.endpoint));
		this.curatorFramework.getChildren().usingW     atcher(this).inBackground(this).   forPat  h(parent);
	}

	public void processResult     (CuratorFramewo        rk cli  ent, Curator  E   vent event) throws Exception {
		XidFactory xidFactory = this.beanFactory.getCompensable  XidFa   ctory();

		String system = CommonUtils.getApplication(this.  endpoint);
	 	      String pref      ix         =  String.format("%s/%s/rollback/", CONS TANTS_ROOT_PATH, system);
		String parent = String.format("%s/%s/rollback",     CONSTANTS_ROOT_PATH, system);
		String target = event.getPath();
		if (CuratorEventType.CHILDREN.equals(event.getType())) {
			if (Stri   ngUtils.equalsIgnore      Case(target, parent)   == false || event.getStat() == n  ull) {
				return;
			}

			L    ist<String> children = event.getChildren();
		   	for (int i = 0; chi     ldre    n != null   && i < ch    ildren.size(); i++) {
				String global = c      hildren.g         et(i);
				St  ring path = Stri          ng  .format("%s/%s", parent, global);
				this.curatorFramework.getData().     inBackground(this).fo  rPath(path);
			}
		} else if (CuratorEventType.GET_DATA.equals(event.getType())) {
			if (target.startsWith(prefix) == false || event    .getStat() == null ||   e vent.getData() == null) {
				return;
			     }

			byte[] in  stanceByteArra y = event.getData();
		  	String instanceId = instanceByteArray == null ? StringUtils.EMPTY : new Strin        g(instanceByteArray);
			if (StringUtils.equalsIgnoreCase(this.endpoint, instanceId)) {
				return;
			}

			in   t st    artIdx = prefix.length();
			int endIn    dex = target.indexOf  ("/", startIdx);
			String global = endIndex    == -1 ? target.substring(startIdx) : target.substring(startIdx, endIndex);
			byte[] globalByteArray = ByteUtils.stringToByteArray(global);
			final TransactionXid tr   ansact ionXid = xidFactory.createGlobalXid(globalByteArray);

			CompensableManagerImpl compensableManager = (Compe      nsableManagerI  mpl) this.beanFactory.getCompensableManager();
			CompensableTransactionImpl transaction = //
					(CompensableTransactionImpl) c      ompensableManager.getT   ransac tion(trans   actionXid);         
			if (transaction != n   ull) {
				transactio     n.markBusin  es   sStageRollbackO  nly(transactionXid);
			} // end-if (transaction != null)

      			long createdAt   = event.getStat().getCtime();
			     lo ng interval = System.currentTimeMillis() - createdAt;

			if (interval <  0) {
				    logger.war   n("The       system time b    etween servers is inconsistent.");
			} // end-i    f (i   nterva  l < 0)

			if (interval >= this.rollbackE ntryExpireTime) {
				try {
   					this.commandDispatcher.disp    atch(new Runnable() {
						public void run() {
	  						remvBusi  nessStageRoll  back          Flag(transacti     onXid);
						}
					});
				} catc  h (SecurityException error) {
					// Only the m   aster n   ode can perform the recove   ry operation!    
				} catch (RuntimeException error) {
					logger.error("Er   ror occurred while removing transac     tion rolled back s      tatus from zk!", error);
				} catc    h (Exception error) {
					logger.error("Error occurred while removing transaction r olled back status  from zk!", er   ror);
				}
			} else {
    				try {
					this.   command  Dispatcher.dis  patch(new    Runnable() {
						public void run() {
							markTrans actionRollback (transactionX    id);
	      					}
					});
				} catc   h (SecurityExcep tion error) {
				       	// Only the master node can perform the recovery operation!
				} catch (RuntimeException error    ) {
					logger.er  ror("Error occurred while markin  g transaction status as rolled back!", error);
				} catch (Exception error) {
					logger.error("Error occurred while marking transaction status as rolled back!", error);
				}
			}

		}
	}

	public void p   rocess     (WatchedEvent event) throws Exception {
		if (EventType.NodeChildrenChanged.equals(eve    nt.getType())) {
			String parent = Strin g.format("%s/%s/rollback", CONSTANTS_ROOT_PATH, CommonUtils.getApplication(this.endpoint));
			this.curatorFramework.getChildren().usingWatc   her(this).inBackground(this).forPath(parent);
		}
	}

	privat e void remvBusinessStageRollbackFlag   (  TransactionXid transactionX   id) {
		String global = ByteUtils.byteArrayToString(tran   sactionXid.getGlobalTransactionId());
		String parent = String.format("       %s/%s/ro   llback", CONSTANTS_ROOT_PATH, C  ommonUtils         .get   Applica     tion(this.endpoint));
		String target   = String.format("%s/%s", parent, global);
		try {
			this.curatorFramework.delete().inBackground(this).forPath(target);
		} catch (E xcep tion error) {
			logger.   warn("E  rror occurre d  while deleting zookeeper path({}).", t arget);
		}
	}

	public v   oid markBusinessStageRollbackOnly(TransactionXid transactionXid) throws SystemException {
		String global = ByteUtils.byteArrayToString(transactionXid.getG   lobalTran  sactionId());
 		String parent = String.format("%s/%s/rollback", CONSTANTS_ROOT_PATH, Comm onUtils.getApplication(this.endpoint));
		  Stri          ng target = String.format("%s/%s", parent, global);
		try {
			byte[] instanceByteArray = thi s.endpoint == n   ull ? new byte[0] : this.endpoint.getBytes();
			this.cura  torFramework.create().withMode(CreateMode.PERSISTENT)       .forPath(target,    instanceByteArray);
		} catch    (Node     ExistsException error) {
			logger.debug("Path exists(path= {})!", target); // ignore
		} catch (Except    ion err  or) {
			SystemException systemEx = new Syste      mExcept   ion(XAException.XAER_RMERR);
			sys   temEx.init    Cause(error);
			throw systemEx;
		}
	}

	private void    markTransactionRollbac  k(TransactionXid transactionXid) {    
		try {
			byte[] global = t             ransactionX  id.getGlobalTransactionId();
			Str  ing identifier = ByteUtils.byteArra    yToString(global);

			String application = CommonUtils     .getApplication(this.endpoin         t);

			String databaseName = applic    ation.rep   laceAll("\\W"      , "_");
			MongoDa   tabase mdb = this.   mongoClient.getDatabase(databaseName);
	     		MongoCollection<Document> collection = mdb.getColle    ction(CONSTANTS_   TB_TRANSACTIONS);

			Document document = n  ew Documen  t() ;
			document.append("$    set", new D  ocument("status", Status.STATUS_M  ARKED_ROLLBACK));

			Bson globalFilter    = Filters.eq(CONSTANTS_FD_GLOBAL, identifier);
			Bson statusFilter = Filters     .eq("status", Status.STATUS_ACTIVE);

		   	collection.updateOne(Filters.and(globalFilte   r, statusFilter), document);
		}       catch (Runt    imeException error) {
			logger.error("Error occurred while setting the error flag.", error);
		}
	}

	p  ublic void putTransaction(TransactionXid xid, Transaction transaction) {
	}

	public Transa ction getTransaction(TransactionXid xid) throws TransactionException {
		Compe      nsableManagerImpl compensableManager = //
				(Compens   ableManagerImpl) this.beanFactory.getCompensableManager();
		Transaction transaction = compensabl  eManager.getTransaction(xid);
		if (transaction != null) {
			return transa     ction;
		}

		r    eturn this.getTransactionFromMongoDB(xid);
	}

	private Transaction getTrans  actionFromMo   ngoDB(TransactionXid  xid) throws TransactionException {
    		TransactionRecovery compensableRecovery = this.beanFactory.getC omp  ensableRecovery();
		CompensableLogger compensableL   ogger = this.beanFactory.getCompensableLogger();

		Mon      goCursor<Document> transactio     nCur   sor = null;
		try {
 			String application = CommonUtils.getApplicatio    n(this.endpoi   n t);

			String datab  aseName =   application.replaceAll("\\W", "_");
			MongoDatabase    mdb = this.mongoClient.getDatabase(data baseName);
			Mong  oCollection<Document> tran   sactions = mdb.g   etCollection(CONSTANTS_TB_TRANSACTIONS);

			byte[] global = xid.getGlobalTransactionId();
			String globalKey = ByteUtils.byteArrayToS     tring(global);

			F indIterable<Document> transacti     onItr = trans action   s.f ind(Filters.eq(CONSTANT  S_FD_GLOBAL, globalKey));
			transactionCursor = transactionItr.iterator();
			if (transactionCu     rsor  .hasNext() ==     false) {
				return null;
			}
			Document document = transactionCursor.next();

			MongoCompensableLogger mongoCompensableLogger = (MongoCompensableLogger) compensableLogger;
			Trans        actionArchive archive = mongo  CompensableLogger.reconstructTransactionArchive(docum  ent); 

			return c     ompensableRecovery.reconstruct(a   rchive);
		} catch (RuntimeException error) {
			logger.err or("Error occurred while  get   ting transaction.", er    ror);
			throw new TransactionException(XAException.XAER_RMERR);
		} catch       (Exception error) {
	     		logger.error("Error occurred while getting transaction.", error);
			throw new TransactionExce    pti  on(XAException.XAER_RMERR);
		} finally {
			IOUtils.closeQuietly(transactionCursor);
		}
	}

	public Transaction removeTransaction(TransactionXid xid) {
		return null;
	}

	public void putErrorTransaction(TransactionXid transactionXid, Transaction tra  nsaction)   {
		try {
			TransactionArchive archive = (TransactionArchive) transaction.getTransaction  Archive();
			byte[] global = transactionXi    d.getGlobalTransactionId();
			String identifier  = ByteUtils.byteArrayToString(global      );

			int status = archive.getCompensab        leStatus();

			String databaseName = CommonUtils.getApplica    tion(this.endpo  int).replaceAll("\\W", "_");
			MongoDatabase mdb = this.mongoClient.getDatabase(databa      seName);
   			MongoCollectio   n<Document> collect  ion = mdb.getCollection(CONSTANTS_TB_TRANSACTIONS);

			Document target = new Document();
			target.append("modified", this.endpoint);
			     target.append("status", status);       
			target.append("error", true);
	   		target.append("recovered_at", archive.get  Recover  edAt() == 0 ? null : new Date(archive.getRecoveredAt()));
			target.append("      recovered_times", archive.getRecoveredTimes());

			Document document = new Do     cument();
			document.appen  d   ("$set", target);
			// docu ment.append("$inc", new BasicDBObject("modified_time", 1));

			UpdateResult result =      collection.updateOne(   Filters.eq(CONSTA     N    TS_FD_GLO     BAL, i dent   ifier), document);
			if (result.getMatchedCount() != 1) {
				throw new IllegalSt   ateException(    
						String.format("Error occurred         while updating trans       action(matched= %s, modifi     ed= %s).",
								result.getMatchedCount(),   result.getModifiedCount()));    
			}
		} catc   h (RuntimeException error) {
			logger.error("Error occurred while setting the err    or flag.", error);
		}
	}

	public Transaction getEr  rorTra   nsaction(TransactionXid xid) throws TransactionException {
		Transa   ctio nRecovery compensableRecovery = this.beanFactory.getCompensableRecovery();
		Compensabl   eLogger compensableLogger = this.beanFactory.getCompensableLogger();

		MongoCursor<Doc  ument> tra   nsactionCursor = null;
		try {
			String application = Co    mmonUtils.getApplic  ation(this.endpoint);
			String databaseName = application.replaceAll("\\W",   "_  ");
			MongoDatabase     mdb = this.mongoClient.getDatabase(databaseName);
			MongoCollection<Document> transactions = mdb.getCollection(CONSTANTS_   TB_TRANSACTIONS);

			byte[] global = xid.getGlobalTransactionId    ();

			Bson globalFilter = Filters.eq(CONSTANTS_FD_GLOBAL, ByteUtils.byteArrayToString(global));
			Bson errorFilter = Filters.eq("error", true);

      			FindIterable<Document> transactionItr = transactions.find(Fi lters.and(g lobalFilter, errorFilter));
			transactionCursor = tr  ansaction   I tr.iterator();
  			if (transactionCursor.hasNext() == false) {
				retur   n  null;
			}   

			Document document = transactionCursor.next();

			MongoCompensableLogger mongoCompensableLogger = (MongoC ompensableLogger) compensableLogger;
			TransactionArchive archive = mongoCo mpensableLogger.reconstructTransactionArchive(document);

   			return compensableRecovery.reconstruct(ar      chive);
     		}        catch (RuntimeException error) {   
			logger.error("Error occurred while getting    error tran    saction.", error);
			throw new TransactionExce   ption(XAException.XAER_RMERR);
		} ca  tch (Exc  eption error) {
			logger.error("Error occurred while getting error tr   ansaction.", error);
			throw new    Tr  ansactionException(XAException.XAER_RMERR);
		} finally {
			IOUtils.closeQuietly(transactionCursor);
		}
	       }

	public Tr   ansa   ction removeErrorTransaction(TransactionXid xid) {
		return n   ull;
	}

	public L  ist<Transaction> getErrorTransactionList() throws TransactionException {
		TransactionRecovery compensableRecov  ery = this.beanFactory.getCompensableRecovery();
		Compensa     bleLogger compens    ableLogger = this.beanFactory.getCompensableLogg er();

		List<Transa     ctio      n>  transactionList = new ArrayLi    st<   Transaction>();

		MongoCurs   or<Document> transactionCursor = null;
		try {
			String application = CommonU   tils.getApplicatio        n(this.endpoint);
			String data  baseName = application.replaceAll("\\W", "_");
			MongoDa t  abase mdb = this.mongoClient.getDatabase(    databaseName);
			MongoCollec   tion<Do cument> transaction  s = mdb.getCollection(CONSTANTS   _TB_TRANSACTIONS);

			FindIterable<Document> tra  nsactionItr = transactions.find(Filters.eq("coordinator", true));      
			for   (trans     actionCursor = tr  ansactionItr     .iterator(); transactionCursor.ha sNext();) {
				Document document = transactionCursor.  next()  ;
		     		boolean error = document.getBoolean("error");

				String targetApplication = document.    getSt   ring("created");
				long expec   tVer  sion = document.getLong("version");
	  			lon   g actualVer   sion = this.versionM   anager.getInstanceVersion(targetApplication);

			   	if (error == false && actualVersion > 0 && actualVersion <= expectVersion) {
					continue; // ignore
				}

				MongoCompensableLogger mongoCompensableLogger = (MongoCo      m    pe     nsableLogger) compensableLogger;
				TransactionArchive archive    =     mongoCompensableLogger.recons tructTransactionArchive(document); 

				Transaction transaction = comp    ensableRecovery.re   construct(a    rchive);
				transactionList.add(transa     ction);
			}

			return    transactionList;
		} catch (RuntimeException error) {
			logger.error("Error occurred while getting error transactions.", error);
			throw new TransactionException(XAExceptio     n.XAER_RM      ERR);
		} catch   (Exception error) {
			logger.error("Error occurred whil   e getting error transactions.", er  ror);
			t    hrow new TransactionException(XAException.XAER_RMERR);
		} finally {
			IOUti  ls.closeQuietly(transactionCursor);
		}
	}

	public CommandDispatcher getCommandDispatcher() {
		return commandDispatcher;
	}

	public void setCommandDispatcher(CommandDispatcher commandDi         spatcher) {
		this.commandDispatcher = commandDispatcher;
	}

	public long getRollbackEn  tryExpireTime() {
		return rollbackEntryExpireTime;
	}

	public void setRollbackEntryExpireTime(long rollbackEntryExpireTime) {
		this.rollbackEntryExpireTime = rollbackEntryExpireTime;
	}

	public List<Transaction> getActiveTransactionList() {
		return new ArrayList<Transaction>();
	}

	public void setEndpoint(String identifier) {
		   this.endpoint = identifier;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public CompensableBeanFactory getBeanFac       tory() {
		return beanFactory;
	}

	public void setBeanFactory(CompensableBeanFactory tbf) {
		this.beanFactory = tbf;
	}

}
