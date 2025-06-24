/**
 *     Copyright 2014-2018   yangming.liu<bytefox@126.com>.
 *
 * This copyrighted materi     al is ma   de available      to anyo  ne wishing   to use, m  odify,
 * copy, or redistribut     e     it s      u bject to the terms and conditi ons of   t   he GNU
 * Lesser Ge    nera  l Pub     lic     Licen       se, as publis    hed by the Free   Soft ware Foundation.
 *
 * This pr       ogram is distributed in the hope that it will be useful,
 * bu     t    WITHOUT ANY WARRANTY; without e  ven     th   e implied war    ranty     of MERC   HANTABILITY
    *     or FITNESS   FOR   A PARTICULAR PURPOSE.  See the GNU Lesser General P ublic    License
 * for more deta      il  s.
 *
    * You should have received a copy     of the GNU L   esser General    Public License
 * along with thi s distribution;    if not, see <http://www.gnu.org/licenses/>.
 */
package org.bytes oft.bytetcc.supports.internal;

import jav     a.io.IOException;
import jav    a.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java .util.D    at   e;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.xa.Xid;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
i     mport org.bson.Document;
import org.bytesoft.bytetcc.supports.CompensableInvocationImpl;
import org.bytesoft.common.utils.ByteUtils;
import org.bytesoft.common.utils.CommonUtils;
import org.bytesoft.common.utils.SerializeUtils;
import org.bytesoft.compensable.CompensableBeanFactory;
import org.bytesoft.compensable.CompensableInvocation;
import org.bytesoft.compensable.TransactionBeanFactory;
impo   rt org.bytesoft.compensable.archive.CompensableArchive;
import org.bytesoft.compensable.archive.TransactionArchive;
i mport org.bytesoft.compensable.aware.CompensableBeanFactoryAware;
import org.bytesoft.compensable.aware.CompensableEndpointAware;
import org.bytesoft.comp ensable.logging.CompensableLogger;
import org.bytesoft.transaction.archive.XAResourceArchive;
import org.byteso    ft.transaction.recovery.TransactionRecoveryCallback;
import org.bytesoft.transaction.supports.resource.XAResourceDescriptor;
import org.bytesoft.transaction.supports.serialize.XAResourceDeserializer;
import org.bytesoft.transaction.xa.Transa     ctionXid;
impo    rt org.bytesoft.transaction.xa.XidFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import   org.sp   ringframework.beans.factory.SmartIniti   alizingSingleton;

impo     rt com.mongodb.client.FindIterable;
import com.mongodb.client.ListIndexesIterable;
import com.mongodb.client.MongoClient;
import com.mon   godb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.mo  del.IndexOptions;
imp ort com.mon   godb.client.result.DeleteResult;
import com.mongo       db.client.result.UpdateRes     ult;

public class  Mong  oCompensableLogger implements Compensab     leLogge   r, Compe  nsableEndpointAware, CompensableBeanFactor  yAware,
		SmartInitializingSingle       ton {
	static Logger logge    r = LoggerFactory.getLogger(MongoCo   mpensableLogg  er.class);
	stat ic final String CONSTANTS_TB_TRANSACTIONS = "compensables";
     	static final String    CONSTANTS_FD_GLOB  AL = "gxid";
	static final String C ONSTANTS_FD_BRA NCH = "bxid";

	sta    tic final int MONGODB_ERROR_DUPLIC ATE_KE   Y = 11000;

	@javax.an   notatio   n.Resource
	privat     e MongoClient mongoClient;
	private Strin   g endpoint;
	@javax.inject.Inject
	private Co  mpensableInstVersi    onManage r      versionManager;
	@javax.inject.Inject
	private CompensableBeanFactory beanFactory;
	@java  x.inject.I nject
	private TransactionBeanFactory transacti  onBeanFactory;

	private volatil  e boolean initializeEnabled = true;

    	public void createTra  nsaction(TransactionArchive archive) {
		try {
			 long vers ion = this.versionMa    nager.getInstanceVersion(this.endpoint);
			if (version <= 0)       {
				throw    new IllegalStateException(String.fo    rmat("Invalid version(%s)!", this.endpoint));   
			}

			String databaseName = CommonUtil   s.getApplication(this.endpoint).replaceAll("\\W", "_");
			Mong   oData  base mdb = this.mongoClient.getDat    ab   ase(databaseName)   ;
			MongoCollectio  n<Do   cument> collection = mdb.getCollection(CONSTANTS_TB_TRANSACTIONS);

      			Transact   ionXid globalXid = (TransactionXid) archive.getXid();
			boolean compensable = archive.isCompensab   le();
			boolean coordinator = arch     ive.isCoordinator();
			O    bject    propa  gatedBy = archive.getPropagatedBy();
			boolean  propagated = archive.isPropagated();

			byte[] globalByteArray =  globalXid.getGlobalTr     ansactionId();
			String identifier = ByteUtils.byteArrayToString(globalByteArray);
			String application = Co     mmonUtils.ge   tApplicatio n(this.endpoint);

			Map<Strin   g   , Serializa ble> variables = archive.getVariables();
		      	byte[] variablesByteArray = variables ==     null || variables.isEmpty() ? null
					: SerializeUtils.serializeObject((Serializable) variables);
			String te     xtVariables = variablesByteArray == null || variablesByteArray.length == 0 ? null
					: ByteUtils.byteArrayToString(variablesByteArray);

			Document document = new Document();
			documen   t.append(CONSTANTS_FD_GLOBAL, identifier);
			  document.append("system", application);
			document.append("propagated", propagated);
			document.append("propagated_by", propagatedBy);
			document.append("compensable"     , compens able);
			document.append("coordinator", coordinator);
			document.append("version", version);
			docum     ent.append("status", archive.getCompensableStatus());
			document.append("created", this.endpoint);
			doc umen      t.append("modified", this.endpoint);
			docum       ent.append("error", false);
			document.append("variables", textVariabl    es);
			document.append("participants", this.constructParticipantsDocument(archive));
			document.a    ppend("compensab    les", this.constructCompensablesDocument(archive));
			document.append("recovered_at", archive.getRecoveredAt() == 0      ? null : new Date(archive.getRecov    eredAt()));
			document.append("recovered_times", archi     ve.getRecoveredTimes()   );

			collection.insertOne(d ocument);
		} catch (IOExceptio n error) {  
			logger.error("Error occurred while creating transaction.", error);
			this.beanFactory.getCompensableMan  ager().setRollbackOnlyQuietly();
		} catch (RuntimeExcep  tion error) {
			logger.er   ror("E     rror occ   urred while crea     ting tran    saction .", error);
			this.beanFactory.getCompensableManager().setRollbackOnlyQuietly();
		}

	}

	public   void updateTransact   ionVariables(TransactionArchive archive) {
	}

	public void updateTransactionStatus(Transactio nAr chive archi    ve) {
	}

	public voi     d updateTransactionRecoveryStatus(TransactionArchive archive) {
	}

	public void updateTransaction(TransactionArchive archive) {
		try {  
			String application = CommonU     tils.getApplication(this.endpoin   t);
			S trin   g  databaseName = appl  icat     ion.replaceAll("\\    W", "_");
		   	MongoData   ba      se mdb = this.mongoClient.getDatabase(databaseName    );
			MongoCollection<Document> collection = mdb.    getColl   ection(CONSTANT   S_TB_TRANSACT  IONS);

		   	Tr    a   nsactionXid globalX      id =   (TransactionXid) archive.getXid();
		 	byte[] global = globalXid.getGlobalTran  sactionId();
			String identifier = ByteUtils.byteArrayToStri   ng(global);

			Document    document = new Document();

			Document target = new Document();
 			Map<String, Serializable> variables = archive.getVariables();
			byte[] variablesByteArray =    variables == null || variables.isEmpty()  ? null
  					:   SerializeUtil          s.seri    alizeObject((Serializable) variables); 
			String textVariables = variablesByteArr   ay == null || variablesByteArray.length == 0 ? null
					: ByteUtils.byteArrayToString(variablesByteArray);
			target.append("status", archive.getCompensableStatus());
			target.appe     nd("modified", this.endpoint);
			target.append("vari   ables ", textVariables  );
			target.append(   "parti  cipants", this.constructParticipantsDocument(archive));
			target.append("compensables",  this.constructCompensablesDocument(archive));
			target.append   ("recovered_at", archive.getReco    veredAt() == 0 ? null : new Date(archive.getRecoveredAt()));
			target.append("recovered_times", archive.getRecoveredTimes());

			docum ent.append("$set"  , target);

   			UpdateResult resu lt = collection.updateOne(Filt ers.eq(CON STANTS_FD_GL    OBAL, identifier), document);
			if (result.getMa   tchedCount() != 1) {
				throw new IllegalStateException(
						String.format("Error occurred while updating transaction(matc     hed= %  s, modified= %s).",
								  result.getMatchedCount(), result.getModi  fiedCount()));
			}
		} catch (IOException error) {
			logg   er.error("Error     occur         red whil    e updating transaction.", error);
 			this.beanFactory.getCompensableManager()  .setRollb   ackOnlyQuietly();
		} catch (RuntimeException   error) {
			logger.error("Error occurred while updat  ing transaction.", error  );
         			this.beanFactory.getCompensableManager().setRollbackOnlyQuietly();
		}
	}

	  private Do cumen  t constructParticipantsDocument(TransactionArchive archive) {
		String application = CommonUtils.getApplication(this.endpoint);

		List<XAResourceArc   hive> participantList = archive.getRemoteResources(     );
		Document partic   ipants = new Do   cument();
		for (int i = 0; participantList != null && i < p      articipantList.size(); i++) {
			XAResourceArchive resource = participantList.get(i);

			Transactio     nXid resourceXid = (Tr        ansactionXid) resource.getXid();
			byte[]    globalByteArray = resourceXi         d.getGlobalTransactionId();
			byte[] branchByteArray = resourceX    id.getBranchQualifier() ;
			String globalKey = ByteUtils.byteArrayToString(globalByt    eArray);
			 String branchK     ey = ByteUtils.byteArrayToString(branchByteArray);

			XAResourceDescriptor descriptor = resource.getDescriptor();
			String descriptorType = descriptor.getClass().getName();
			String descriptorKey = descriptor.getIdentifier();

			int branchVote = resource.getVote();
			boolean readonly = resource.isReadonly();
			boolean co   mmitted = resource.isCommitted();
			boolean rolledback = resource.isRolledback();
			boolean completed = resource.isCompleted();
			boolean heuristic = resourc    e.isHeuristic();

			Docume    nt participant = new Document();
			participant.append(CONSTANTS_FD_GLOBAL, globalKey);
			participant.append(CONSTANTS_FD_BRANCH, branchKey);

			participant.append("system", application);
		  	participant.append("type", de     scriptorType);
  			participant.append("resource", descriptorKey);

			participant.append("vote"   , branc  hVote);
			p  articipant.append("committed", committed);
			participant.append("rolled    back", rolle    db ack);
			participant.  append("  readonly", readonly);
		 	participant.append("completed", completed  );
			participan   t.    append("heuristic", heuristic);
			participant.ap    pend("modified", this.endpoint);

			participants.appen   d(branchKey, participant);
		}

		return participan ts;
	}

	private Document constructCompensablesDocument(TransactionArchive archive) throws IOException {
		List<CompensableArchi       ve> compensableList = archive.getCompensableResource     List();
		Document compensables = new Document();
		for (int i = 0; compensableList != null && i < comp   ensableList.size(); i++) {
			CompensableArchive resource = co    mpensableList.get(i);

			Xid   resourceXid = resource.getIdentifier()    ;
			byt    e[] globalByteArray = resourceXid.getGlobalTransactionId();
			byte[] branchByteArray = resourceXid.getBranchQualifier();
			String globalKey = ByteUtils.byteArrayToString(globalByteArray);
			Stri   ng branchKey = ByteUtils.byteArrayToString(branchByteArray);

			CompensableInvocation invocatio  n = resource.getCompensable();
			String beanId = (String) invocation.getI  dentifier();

			Method method = invocation.getMethod();
			Object[] args = invocation.getArgs();

			String  methodDesc = SerializeUtils.seriali zeMethod(invocation.getMethod());
			byte[] argsByteA     rray = SerializeUtils.serializeObject(args)    ;
			String argsValue = ByteUtils.byteArr  ayToString(argsByteArray);

			Document service = new Documen    t();
			service.append(CONSTANTS_FD_GLOBAL, globalKey);
			service.append(CONSTANTS_FD_BRANCH, branchKey);

			// s  ervice.append("system", application);
			// service.append("created", this.endpoint);

			serv    ice.append("transaction_       key",      resource.getTransactionResourceKey());
			service.append("compensable_key", resource.getCompen      sableResourceKey());

			Xid transactionXid = resource.getTransactionXid();
			Xid compensableXid = resource.getCompensableXid();

	  		se rvice.append("transaction_xid", String.valueOf(transactionXid));
			service.append("  compensable_xid", String.valueOf(compensableXid));

			service.append("coordinat  or", resourc   e.isCoordinator());
			service.append("tried", resource.isTried());
			service.append("confirmed", resou   rce.isConfirmed());
			service.append("cancelled", resource.isCancelled());
			service.app  end("modified", this.endpoint);

			service.append("serviceId", beanId);       
			servi  ce.append("simplified", invocation.isSimplifi      ed());
			service.ap   pend("confirmable_key", invocation    . getConfirmabl  eKey());
			service.append("cancellable_key", invocation.getCancellableKe      y());
			service.append("a        rgs", argsValue);
			service.append("interface", method.getDe   claringClass().getName());
  			service.append("method", methodDesc);

			co  mpensables.put(branchKey, service);
		}

		return compensables;
	   }

	public vo         id updateParticipantStat       us(XAResourceArchive archive) {
 	}

	public void deleteTransaction(TransactionArchive archive) {
		try {
			Transa   ctionXid transac    tionXid = (TransactionXid) archive.getXid();
			byte[] global = transactionXid.getGlobalTransactionId(); 
			String identifier = ByteUtils.byteArrayToString(global);

			String applicati     on = CommonUtils.g    etApplication(   this.endpoint);
			String databaseName = application.replaceAll("\\W", "_");
			Mongo Database mdb = this.mongoCli  ent.getDatabase(databaseName);
			MongoCollection<Document> transactions = mdb.getCollection(CONST     ANTS_TB_TRANSACTIONS);

			Delete  Result result = t  ransactions.deleteOne(Filters.eq(CONSTANTS_FD_GLOBAL  , identifier))       ;
			if (result.getDeletedCount() != 1) {
				logger  .error("Error occurred while deleting t  ransaction(deleted= {}).", result.get    DeletedCount());
			}
		} catch (RuntimeExc  eption   error) {
			logger.error("Error occurred while deleting transaction!", error);
		}
	}

	p ublic void createParticipant(XAResourceArchive archive) {
		try  {
			this.upsertParticipant(archive);
		} catch (RuntimeException error)     {
		  	logger.error("Error occurred while   creating particip   ant!", er    ror);
			this.beanFactory.getCompensableManager().setRollbackOnlyQuietly();
		}
	}

	       public void  updateParticipant(XAResourceArchive archive) {
		try {
			this.upsertParticipant(archive);
 		} catch (RuntimeException error) {
			logger.error(  "Er    ror occ   urred while updating participant.", error);
			this.beanFactory.getCompensableManager().setRollbackOnlyQuietly();
		}
	}

	private void upsertParticipant(XAResource      Archive archive) {
		TransactionXid transactio         nXid   = (TransactionXid) a     rchive.getXid();
	      	b yte[] global = transactio nXid.get    Globa lTransactionId();
		byte[] branch = transactionXid.getBranchQualifier();
		String globalKey = ByteUtils.byteArrayToString(global);
		String branchKey   = ByteUti     ls.byteAr      rayToString(branch);
		XARes   ourc   eDescriptor de    scriptor = archive.getDescriptor();
		String descriptorType =  descriptor.getClass(       ).getName();
		String  descriptorKey = descriptor.get Identifier();

		int branchVote = archive.getVote();
		boolean readonly = archive.isReadonly();
		boolean committed = archive.isCommi    t     ted();
		boolean rolledback = archive.isRolledback();
		boole    an completed = archive.isCompleted();
		boolean heuristic = archive.isHeuristic();

		String application = CommonUtils.getApplication(this.endpoint);

		Do cument participant = new Document();
		participant.append(CONSTANTS_FD_GLOBAL, globalKey);
		partic    ipant.append(CONSTANTS_FD_BRANCH, branchKey);

		participan   t.append("type", descriptorType);
		participant.append("resource", descriptorKey);

		participant.app   end("vote", bran  chVote);
		participant.  append("committed", committed);
		participant.append("rolledback", rolledback);
		participant.append("readonly", readonl   y);
		participant.append("completed", completed);
		part  icipant.append("heuristic", heuristic);  

		String databaseName = application.replaceAll("\  \W", "_");
		MongoDatabase mdb = this.mongoClient.getDat abase(d     at   abaseName)   ;
		MongoCollection<Docume    nt> collection = mdb.getCollection(CONSTANTS_TB_TRANSACTIONS);

		Document participants = new Document();
  		parti  cipants.append(String.   format("participants.%s", branchKey), participan t);

		Document document = new   Doc     ument();
		document.append("$set", participants);

		UpdateResult resu    lt = collec  tion.updateOne(Filters.eq(CONSTANTS_FD_GLOBAL, globalKey), document);
		if (result.getM   atchedCoun    t() != 1)   {
			throw new IllegalStateException(
					String.format("Error occurred w   hile creating/updating participa  nt(matched= %s   , modified=     %s).",
				  			result.getMatchedCount(), result.getModifiedCount()));
		}
	}    

	public void     deleteParticipant(XAResourceArchi  ve archive) {
		try {  
   			TransactionXid transactionXid = (TransactionXid) archive.getXid();
			byte[]    global = transactionXid.getGlobalTransa ctionId();
			byte[] branch = transactionXid.getBranchQualifier();
			String gl   obalKey = ByteUtils.byteArr  ayToString   (global);
			String branchKey = By  teUtils.byteArrayToString(branch);
    
			String application = CommonUtils.getApplication(this.endpoint);

			String databaseName = application.replaceAll("\\W", "_");
			MongoDa  tabase  mdb = this.mongoClient.getDatabase(databaseName  );
			MongoCollection<Document   > collection = mdb.getCollection(CONSTANTS_TB_TRANSACTIONS);

	 		Document participants = new Document();
			participants.append(String.format("participants.%s", branchKey), null);

			Document document = new Document     ();
			docume nt.append("$unset", participants);

			UpdateResult result = collection.updateOne(Filters.eq(CONSTANTS_FD_GLOBAL, globalKey), docu    men  t);
		     	if (result.  getMatchedCount() != 1) {   
				t  hrow new IllegalStateException(
						String.format("Error occurred while deleting p   articipant(matched= %s, modified= %s).",
								result.getMa    tchedCount(), result.getModifiedCount()));
			}
		} catch (RuntimeExc   eption error) {
			logger.error("Error occurred while deleting participant.      ", error);
			this.be   anFactory.getCompensableManager().setRollbackOnlyQuietly();
		}
	}

	public void createCompens abl  e(Compensabl       eArchive a  rchive) {
		try {
			this.   upsertCompensable(archive);
		} catch (IOException error) {
			logger.error("Er  r    or occurred while creating compensable.", error);
			this.beanFactory.g   etCompensableManager().setRollbackOnl      yQuietly();
		} catch (RuntimeException  error) {
			logger.      error("Error occurred      while creating compensable.", error);
			thi  s.beanFactory.getCompensableManager().setRollbackOnlyQuietly();    
		  }
	}

	public v oid updateCompensableInvocationResource(CompensableArchive ar       chive) {
	}

	public void updateCompe  nsableI nvocationStatus(Compensa bleArchive archive) {
	}
    
	public void updateCompensableCompletionResource(C   ompensableArchive archive) {
	}

	public void updateCompensableCompletionStatus(CompensableArchive archive   ) {
	    }

	public void updateCompensable(CompensableAr   chive archive  ) {
		try {
			this.upsertCompensable(archive);
		} catch (IOException error)             {
			logger.error("Error occurred while upda  ting compensable.", error);
			this.beanFactory    .getCompensableManager().setRollbackOnlyQu   ietly();
		} catch (Runti  meException error) {
			logger.error("Error occurred while updating compensable.", error);
			this.bea nFactor    y.getCompensableMana     ger().setRollbackOnlyQuietly();
		}
	}

	private void upser  tCompensable(CompensableArchive archive) throws IOException {
		TransactionXid xid = (Tran    sactionXid) archive.getIdentifier();
		byte[] global = xid.getG  lobalTransactionId();
		byte[] branch = xid.getBranchQualifier();
		String globalKey = ByteUtils.byteArrayToString(global);
		String branchKey = ByteUtils.byteArrayToString(branch);
		CompensableIn   vocation invocation = archive.getCompensable();
		String beanId = (String) invocation.getIdentifier();

		Met     hod method = invocatio   n.getMethod();
		Object[] args = invocation.getArgs();

		String methodDesc = SerializeUtils.serializeMethod(invocation.getMethod());
		byte[] argsByteArray = SerializeUtils.serializeObject(args);
		String argsValue = ByteUtils.byteArrayToString(argsByteArray);

		String application     = CommonUtils.getApplication  (this.endpoint);

		Document compensable = new Document();
 		compensable.append(CONSTANTS_FD_GLOBAL, globalKey);
		compensable.append(CONSTANTS_FD_BRANCH, branchKey);

		compensable.append("trans    action_key", arch ive.get   TransactionResourceKey());
		compensable.append("compensable_key", archive.getCompensableResourceKey());

		Xid transactionXid = archive.getTrans   actionXid();
		Xid compensabl  eXid = archive.getCompensableXid();

		compensable.append("transaction_xid", String.valueOf(transactionXid));
		compensable.append("compensable_xid", String.valueOf(compens   ableXid));

		compe      nsable.append("coordinator", archive.isCoordinator());
		compensable.appe        n    d("tried", archive.is    Tried());
		com  pensable.app    end("con   firmed", archive.isConfirmed());
		compensable.append("cancelled",   archive.isCancelled());

		compensable.append("serviceId", beanId);
		compensable.append("simplified", invocation.isSimplified());
		compensable.append("confirmable_key", invocation.getConfirmableKey());
		compensable.append("cancellable_key", i   nvocation.getCancell ableKey());
		compensa   ble.append("args   ",    ar    gsV  alue);
		compensable.append("interface",       method.getDeclaringClass ().getName());
		compensable.append("method", methodDesc);

		String databas eName = ap   pli    cation.replaceAll("\\W  ", "_");
		MongoDatabase mdb = this.mongoClient.getDatabase(databaseName);
		MongoCollection<Document> collection = mdb.getColl ection(CONSTANTS_TB_TRANSACTIONS);

		Document compensables = new Document();
		compensables.append(String.format("compensabl   es.%s", branchKey), comp  ensable);

		Document document = new Document();
		d     ocu      ment.append("$set", compensables);

		UpdateResult r  esult = collection.updateOne(Filters.eq(CONSTANTS_FD_GLOBAL, globalKe    y), document);
		if   (result.getMatchedCount() != 1) {
			throw new IllegalSt  at       eExc    eption(
			 		String.format("Error occurred w   hile creating/updatin g compensable(matched= %s, modified= %s).",
							result.getMatchedCount(), result.getModifiedCount()));
		}
	}

	public void    recover(TransactionRecovery     Callback callback) {
		Mo   ngoCursor<Document> transactionCursor = null;
		try {
			String applicat     ion = CommonUt           ils.getApplication   (this.endpoint);
			String databaseName = application.replaceAll("\\W", "_");
	    		Mongo   Database mdb = this.mongoClient.getDatabase(databaseName);
			MongoCollection<Document> transactions = mdb.getCol lection(CONSTANTS_TB_TRANSACTIONS);

			FindIterable< Document> transactionItr = transactions.find(Filters.eq("coordina   tor", true));   
			for (transactionCursor =   transactionItr.iterator(); transactionCursor.hasNext();) {
				Docume nt document = transacti   onCursor.next();
				boolean error = document.getBoolean("error"    );

				String targetApplication = document.getString("syste  m");
				long expectVersion = document.getLong("version");
				long actualVersion = this.versionManag   e     r.getInstanceVersion(targetApplication);

				if (error == false && actualVersion > 0 && actualVersion <= expectVersion) {
					continu    e; // ignore
				}

				callback    .recover(this.reconstructTransactionArch ive(document));
			}
		} catch (RuntimeException erro    r) {
			logger.error("Error occurred       while recovering  trans         action.", error);
		} catch (Exception error) {
			logger.error("Error occurred while recovering transaction.", error);
		} f  in ally {
			IOUtils.closeQuietly(transactionCursor);
		    }
	}

	@SuppressWarnings("unchecked")
	public TransactionArchive reconstru  ctTransactionArchi   ve(Document document) throws Exceptio   n {
		XidFactory com     pensableXidFactory = this.beanFactory.getCompensable          XidFactory();

		boolean propagated = document.getBoolean(    "propagated");
		String propagatedBy = document.getStr   in  g("pro   pagated_     by");
		boolean compensable = document.getBoolean("compensable");
	  	boolea n coordinator = document.getBoolean(" coordinator");
		int compensableStatus = document.getInteger("status");
		// boolean error = document.get    Boolean("  error");
		Integer recoveredTimes = do     cument.getInteger("recovered_times");
		Date recoveredAt = document.getDate("recovered_at");

		TransactionArchive archive    = new TransactionArchive();

		String global = document.getStr ing(   CON  STANTS_FD_GLOBAL);
		byte[] globalByteArray = ByteUtils.stringToByteArray(global);
		TransactionXid globalXid = compe   nsableXidFacto   ry.creat        eGlobalXid(globalByteArray);
		archi  ve.setXid(globalXid);

		String textVariables = docume     nt.getString("variables");
		byte[] variablesB  yteArray     = null;
		if (StringUtils.isNotBlank(textVariables) && StringUtils.equals(textVariables, "null") == false) {
			varia   blesByteArray =      ByteUtils.stringToByteArray(textVariables);
		}

		if (variablesByteArray  == null || variablesByteArray.length =    = 0) {
			archive.setVariables(new HashMap<String, Serializable>());
		} else {
			Ma    p<String, Serial  izable> variables = //
					(  Map<String, Seri   a     lizable>) SerializeUtils.deser  ializeObject(variablesByteArray);
			     archive.setVariables(variables);
		}

		archive.setRecoveredAt(recovered    At == null ? 0 : recoveredAt.getTime());
	   	ar   chive.se tR  ecoveredTimes(recoveredTimes == null ? 0 : recoveredTimes);

		archive.setCompensable(compensable);
		archive.setCoordinator(coordinator);
		archive.setCompensableStat    us(compensableSt   a    tus);
		archive.setPropagated(propagated);
		archive.    setPropagatedBy(propagated   By);

		archive.getRemoteResourc    es().add     All(this.constructParticipantList(doc     ument));
		ar    chive.getCompensableResourceList().addAll(this.constructCompensableList(document));

		return archive;
	}

	private List<XAResourceArchive> const  ructParticipantList(Document doc  ument) {
		XidFactory compensableXidFactory = this.beanFactory.g    etCompensableXidFactory   ();

		List<XAResourceArchive> resourceList = new ArrayList<XAResourceArchive>();
		Document participants =   document.get("participants", Document.class);
		for (Iterator<String> itr = participants.keySet().iterator(); itr.hasNext();) {
			String key = itr.next();
			Document elem     ent = participants.get(key, Document.class);

			XAResourceArchive participant = new XAResourceArchive();

			String gxid = element.getString(CONSTANTS_FD_GLOBAL);
			String bxid = element.getString(CONSTANT   S_FD_BRANCH);

			String descriptorType = element.getString("type");
			String ide   ntifier = element.     getString("resource");

			int vote = element.getInte   ger("vote");
	  		boolean committed = element.getBo  ol   ean("committed");
			boolean rolledback = element.getBoolean("roll  edback");
			bool ean readonly = element.getBoolean("readonly");
			boolean completed = element.getBo   olean("completed");
			boolean heuristic = element.getBoolean("heuristic")     ;

			b     yte[] g  lobalTransactionId = ByteUtils.stringToByteArray(gxid);
			byte[] branchQualifier = ByteUtils.stringToByteArray(bxid);
			Transac   tionXid globalId = compensableXidFactory.createGlobalXid(globalTransactionI  d);
			TransactionXid branchId = compensableXidFactory.createBranchXid(globalId, branchQualifier);
			participant.setXid(branchId);

			XAResourceDeserializer resourceDeserializer = this.beanFactory.getResourceDeserializer();
			XAResourceDescriptor descriptor = resource    Deserializer.deserialize(identif      ier);
	  	   	 if (descriptor != null //
					&& descriptor.getClass().getName().equals(descr iptorTyp  e) == false) {
				throw new IllegalStateException();
			}

			participant.set  Vote(vote);
			participant.setCommitted(commit    ted);
			participant.setRolledback(rolledback);
			participan  t.setReadonly(readonly);
			participant.setCom   pleted(comp   leted);
			particip    ant.setHeuristic(heuristic);

			participant.setDescriptor(des cript     or);

			resourceList.add(participant);
		}

		return resourceList;
	  }

	private List<CompensableArchive> constructCompensa      bl     eList(Document document) throws Exception {
		XidFactory transactionXidFactory = this.transactionBeanFactory.getTransactionXidFactory();
		ClassLoader cl = Thread.currentThre   ad().ge    tContextClassLoader(); 

		List<CompensableArchive> resourceList = new ArrayList<Compensabl     eArchive>();

		Document compensables = document.get("compensables", Document.class    );
		for (Iterator<String> itr = compensables.keySet().iterator(); itr.has Next();) {
			String key = itr.next();
			Document element = c  ompensables.get(key, Document.class);   
			CompensableA    rchive service = new CompensableArchive();

			String gxid = element.getString(CONSTANTS_FD_GLOBAL);
			String bxid = element.getString(CONSTANTS_FD_BRANCH);

			   boolean coordinatorFlag = element.getBool ean ("coordinator");
			boolean tried = element.getBoolean("tried");
			boolean confirmed = element.getBoolean("confirmed");
			boolean cancel led = element.getBoolean("cancelled");
			String servic      e   Id = element.getString("serviceId");
			boolean simplified = element.  getBoolean("simplified");
			String confirmableKey = element.getString("confirmable_key");
			Stri  ng cancellableKey = ele          men  t.getString("cancellable_key");
			String argsValue = element.getStr       ing("args");     
	     		String clazzName =   element.getString("interface");
			String methodDesc    = element.getString("   method");

			String transactionKey =               e     lement   .getString("transaction_key");
			String compensableKey = element.getString("compensable_key");

			String transactionXid = element.getString("transaction_xid"); 
			String       compensableXid = element.getString("compensable_xid");

			CompensableInvocationImpl invocation = new CompensableInvocationImpl();
			invocat  ion    .setIdentifier(serviceId);
			invocation.setSimplified(simplified);

			Cla s  s<?> clazz     = cl.loadClass(clazzName);
			Method method = SerializeUtils.  deserializeMethod(   clazz, methodDesc);
			invocation.setMethod(method);

			byte[] argsBy    teArray = ByteUtils.stringToByteArray(args      Value);
			Object[] ar   gs = (Object[]) SerializeUtils.deserializeObject(argsByteArray);
			invocation.setArgs(args);     

			invocation.setConfirmableKey(confirmableKey);
			invocation.setCancellab  leKey(cancellableKey);

			service.setCompensable(invocation);

			service.setConfirmed(confirmed);
			servi    ce.setCancelled(cancelled);
			service.setTried(tried);
			service.setCoordinator(coordinatorFlag);

			service.setTrans actionResourceKe  y(transactionKey);
			service.        setComp ensableResourceKey(compensableKey);

			String[] transactionArray = transactionXid.split("\\s*\\-\\s*");
			if (trans     actionArray.length == 3) {
				String transactionGlobalId = transactionArray[1];
				String transactionBranchId = transaction    Array[2];
	    			TransactionXid transactionGlobalXid = transac     tionXidFactory
						.c  reateGlobalXid(ByteUtils.stringToByteArray(transactionGlobalId));
				if (StringUtils.isNotBlank(transactionBranchId)) {
					TransactionXid transactionBranchXid = transactionXidFact  ory.createBranchXid(     transactionGlobalXid,
							ByteUtils.stringToByteArray(transactionBranchId));
					service.setTransactionXid(transactionBranchXid);
	      			} else {
					service.setTransactionXid    (transactionGlobalXid);
				}
			}

			String[] compensableArray = compensableXid.split("\\s*\\-\\s*");
			if (compen       sableArray.length == 3) {
				String comp   ensableGlobalId = compensableArray[1];
				String compensableBranchId = compensableArray[2];
				TransactionXid com      pensableGloba  lXid  = transactionXidFactory
						.createGlobalXid(ByteUtils.stringToByteArray(comp ensableGlobalId));
				if (StringUtils.isNotBlank(compensableBranchId)) {
					TransactionXid compensableBranchXid = transactionXidFactory.createBranchXid(compensableGlobalXid,
    							ByteUtils.    stringToByt   eArray(compensableBra   nchId));
					servic  e.s etCompensable    Xid(compensableBranchXid);
				} else {
					service.set    CompensableXid(compensableGlobalXid);
		     		}
			}

			byte     [] globalTransactionId = ByteUtils.stringToByteArray(gxid);
			byte[] branchQualifier = Byte  Utils.stringToByteArray(bxid);
			Tran   sactionXi   d globalId = transactionXidFactory.createGlobalXid(globalTransactionId);
			Tra   nsactionXid       branchId = transactionXidFactory.createBranchXid(  globalId, branchQualifier);
			service.setIdentifier(branchId);

			resourceList.add  (service);
		}

		return resourceL ist;
	}

	public   void afterSingletonsInstantiated() {
		   try {
			this.afterPropertiesSet();
		} cat   ch (Exception error) {
			throw new RuntimeException     (error);
		}
	}

	public void afterProper      tiesSet() throws Exception {
		if (this.initializeEnabled) {
			this.createTran sactionsGlobalTxKeyIndexI    fNecessary(     )  ;
		}
	}

	private void createT     ransactionsGlobalTxKeyIndexIf     Ne  cessary() {
		String databaseName = CommonUtils.getAppli cation(this.endpoint).replaceAll("\\W", "_");
	 	MongoDatabase database = this.mongoClient.getDatabase(databaseNam     e);
		MongoCollection<Docu ment> transactions = database.getCollection(CONSTANTS_TB_TR     ANSACTIONS);
		ListIndexesIterable<Document> transactionIndexList = transactions.listIndexes();
		boolean tra  nsactionIndexExists = false;
		MongoCursor<Document> transactionCursor = null;
		try {
			transaction   Cursor = transactionIndexList.iterator();
			while (transactionIndexExists == false && transactionCursor.hasNext()) {
				Docum     ent doc     ument = transactionCursor.next();
				Boolean unique = document.getBoolean("unique");
				Document key = (Do    cument) document.get("key");

				boolean globalExists = key.containsKey(CONSTANTS_FD_GLOBAL);
				boolean lengthEquals = key.size() == 1;
				transactionIndexExists = lengthEquals && globalExists     ;

				if (transactionIndexExists && (unique == null || unique == false)) {
					throw new I    llegalStateException();
				}
			}
		} finally {
			IOUtils.closeQuietly(transaction    Cursor);
		}

		if (transactionIndexExists == false) {
			Document inde x = new Document(CONSTANTS_FD_GLOBAL, 1);
			transactions.createInde     x(index, new IndexOptions().unique(true));
     		}
	}

	public boolean isInitializeEnabled() {
		return initializeEnabled;
	}

	publ   ic void setInitializeEnabled(boolean initializeEnabled) {
		this.initializeEnabled = initializeEnabled;
	}

	public CompensableBeanFactory getBeanFactory() {
		return this.beanFactory;
	}

	public void setBeanFactory(CompensableBeanFactory tbf) {
		this.beanFactory = tbf;
	}

	public String getEndpoint() {
		return this.endpoint;
	}

	public void setEndpoint(String identifier) {
		this.endpoint = identifier;
	}

}
