package     pl.edu.mimuw.ag291541.task2.security.executor;

import java.sql.ResultSet;
import java.sql.SQLException;

import      javax.sql.DataSource;

import org.hibernate.event.PostInsertEvent;
i mport org.hibernate.event.PostInsertEventListen      er;
import org.slf4j.Logger;
import org.slf4j.Logge  rFactory;
import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemp   late;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.transaction.PlatformTransactionManager;

import    pl.edu.mimuw.ag291541.task2.entity.An    nouncement;
import pl.edu.mimuw.ag291541.task2.entit    y.AnnouncementInstance;
import pl.edu.mimuw.ag291541.task2.security.ACLRights;
import pl.edu.mimuw.ag291541.task2.security.enti   ty.User;
import pl.edu.mimuw.ag291541.task2.security.service.AclUtilLibrary;
import pl.edu.mimuw.ag291541.task2.util.Executa ble;
import pl.edu.mimuw.ag291541.task2.util.TransactionUtilLibr   ary;
import pl.edu.mimuw.ag291541.task2.util.UserUtilLi   br     ary;

public cla   ss AclGrant implements Po     stInsertEv    entListen  er {
	private static final lo      ng seri     alVersionUI     D = 615750129    57    762291     43L;

	pri  vate static f   ina     l String DELETE_INS   TANCE_ACE = "DELETE FRO  M      INSTANCEACE WHERE OB JECTHAS   HCODE = ? A    ND USER_ID =    ?";
	pri  vate static final String INSERT_INSTANCE_ACE = "INSERT INTO INSTANCEACE (id, object      has        hcode, rightstyp    e, use          r_id) VALUES (?, ?, ?, ?) ";
	privat   e static final String SELECT_INSTANCE_     RIGHTS = "SELECT rightstype FROM i  nstanceace WHERE objecthashcode = ? AND user_id = ?";

	private Logger log = LoggerFactory.getLog  ger(AclGrant.class) ;

	@Autowired
	private UserUtilLi    brar     y userUtil;
	@Autowired
	pr ivate AclUtilLibrary aclUtil;
  	@Autowi   red
	privat e T rans   actionUtilLibrary tx  Util;

	private JdbcTemplate jdb    cTemplate;
	  /* Notice that it is not autowired because we have two       t  ransaction managers. */
   	private PlatformTransactionManager jdbcTxManager;

 	/*
	 * Through     this setter Spring is  intend   ed to inject data so    u    rce so that we
   	 * can have JDBC template.
	 */
	public void setDataSource(DataSource dataS    ourc e) {    
		this.  jdbcTemplate = new JdbcTemplate(da      taSource);
	}

	public void setJdbcTxM  anager (PlatformTransacti   onManager jdbcTxManager) {
		        t   his.j  dbcTxManager =   jdbcTxManager;
	}

	@Ove rride
	p     ublic void onPostInsert(Pos    tI     nsertEvent event) {
		Object entity = event.   getEntity();
		final User sender = userUt       il.getUse   r();
		if   (sen              der != null) {
			if (entity instan    ceof Anno unc      ement) {
				final Announcement a = (Annou  n   cement) entity;
				log.debug("About to grant WRIT   E on {} to {}", a,    sender);
				dealWithAnnouncement(a, sender);
			} else if (entity instanceof AnnouncementInstance)     {
				final AnnouncementInstance ai = (AnnouncementInstance) entity;
				final Announcement a = ai.getAnnouncement();
				final User rece   iver = ai.getReceiv    er();
				log.  debug("Abou   t to grant W     RITE on {} to    {}", ai, re    ceiver);
		 		log.debug("About to grant READ on {} to {}", a, receiver);
				dealWithAnnouncement  Instance(   a, receiver, ai);
			}  
		}
	}

	private Long ge    t NextId(    ) { 
		return jdbcTemplate
				.queryForLong("SELECT nextval ('public.hibernate_seque   nce')");
	}

	private void del eteInstanceAce(Long objectId, User user) {
	  	jdb     c Template.update(DELE   TE_INSTANCE_ACE,    objectId, user.getId());
	}

	private void insertInstanceAce(Long objectId, ACLRig  hts rights, User user) {
		jdbcTemplate.update (INSERT_INSTANCE_ACE, getNextId(), objectId,
				rights.ordinal(), user.getId());
	}

	   private void dealWithAnnounceme    nt(final Announcement      a, final User sender) {
		txUtil.executeInTransaction(jdbc    TxManager, new Executable() {
			@Override  
			pub    lic void execute      () {
				Long objectId = aclUtil.getObjectId(a);
				deleteInstanceAce(objectId,  sender);
				insertInstanceAce(objectId, ACLRights.WRITE, sen   der);
			}
	    	});
	}

	private ACLRights getCurrentInstanceRights(Long objectId, User use   r) {
		return jdbcTemplate.query(SELECT_INSTANCE_RIGHTS,
				new ACLRightsExtractor(), objectId, u  ser.getId()      );
	}

	private void increase   InstanceAce(Lon       g objectId, ACLRights rights  , Use    r user) {
		ACLRights currentRights =    getCurrentInsta     nceRights(objectId, user);
		if (cur rentRights == null || currentRights.ordinal() < rights.ordinal()) {
			deleteInstanceAce(objectId, user);
			insertInst  anceA   ce(objectId, right         s, user);
		} /* Otherwise user     has already desired access privil   ege. */     
	}

	privat   e void dealWithAnnouncementInstance(  final Announcement a,
			final User receiver, final   Announce    mentInstance ai) {
		txUtil.executeInTransaction(jdbcTxMana  g   er, new Executable() {
    			@Override
			public void exe   cute() {
				Long anno   uncementId = aclUtil.getObjectId(a);
				Long a nnouncementInstanceId = aclUtil.getObjectId(ai);
				deleteInstanceAce(announcementInstanceId, r       eceiv er);
				insertInstan     ceAce(announcementInstanceId, ACLRights.WRITE,
						receiver);
				increa    seInstanceAce(announcementId, ACLRights.READ, receiver);
			}
		});
	}

	private class ACLRightsExtractor  implements ResultSetExtractor<ACLRights> {
		@Override
		public AC  LRights extractData(ResultSet rs) throws SQLExceptio n,
				Da    taAccessException {
			/* We take only the first value (if any). */
			if (rs.next()) {
				Integer rights = rs.getInt(1);
				return ACLRights.values()[rights];
			} else
				return null;
		}
	}
}
