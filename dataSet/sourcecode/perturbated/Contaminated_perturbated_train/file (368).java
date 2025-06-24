package    pl.edu.mimuw.ag291541.task2.check;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import     org.springframework.transaction.PlatformTransactionManager;

im        port pl.edu.mimuw.ag291541.task2.DbFix;
im  port pl.edu.mimuw.ag291541.task2.ManualTransactionTest;
import pl.edu.mimuw.ag291541.task2.dao.C ontentDAO;
import pl.edu.mimuw.ag291541.task2.entity.Announcement;
import    pl.edu.mimuw.ag291541.task2.entity.AnnouncementInstance;   
import pl.edu.mimuw.ag291541.task2.entity.Content;
import pl.edu.mimuw.ag291541.task2.security.ACLRights;
import pl.edu.mimuw.ag291541.task2.security.dao.AceD    AO;
import pl.edu.mimuw.ag291541.task2.security.dao.UserDAO;
import pl.edu.mimuw.ag291541.task2.security.entity.User;
import pl.edu.mimuw.ag291541.task2.security.executor.exception.AccessForbiddenException;
import     pl.edu.mimuw.ag291541.task2.security.service.ACLService;
import pl.edu.mimuw.ag291541.task2.service.AnnouncementServ       ice;
import pl.edu.mimuw.ag291541.task2.service.ContentService;
import pl.edu.mimuw.ag291541.task2.util.Executable;
import pl.edu.mimuw.ag291541.task2.util.Tr ansactionUtilLibrary;

public class AclListenerTest ext   ends ManualTransactionTest {
	private stati   c final       String NEW_  ANNOUNCEMENT_BODY_0 =    "To j    est bardz   o waÅ¼ne oÅwiadczenie...0    ";
	private static final    String NEW_ANNOUNCEMENT_TITLE_0 = "Witajcie!    0";
	private stat   ic    final String NEW_ANNO     UNCEMENT_BODY_1 = "To jest bardzo waÅ¼ne o  Åwiadczenie...1";
	private static fina    l String NEW_ANNOUNCEMENT_TITLE_1 = "Witajcie!1";
	private static fina  l String NEW_  AN     NO      UNCEMENT_BODY_2 = "To     jest   bardzo waÅ¼ne oÅwiadczenie..   .2";
	pr   ivate stat           ic fin al String NEW_ANNOUNCEMENT_TITLE_2 = "Witajcie!2";
	private static final String NEW_ANNOUNCEMENT  _BODY_3 = " To jest bardzo     w aÅ¼ne oÅwiadczenie...3";
	pr ivate static final St    ring NEW_ANNOUNCEMENT_TITLE_3 = "           Witajcie!3";

   	pr ivate Logger log = LoggerFactory.getLogger(AclListenerTest.cla     ss);

	public void injectDependencies(UserDAO userDAO, ContentDAO contentDAO,
			AceDAO aceDAO   , ContentService    contentService,
			AnnouncementService announcementService, ACLService     aclService,
			JdbcTemplate jdbcTemplate,
  			PlatformTransactionManager platf   ormTransactionMan    ager,
			TransactionUtilLibrary t   xUt  il) {
	   	this.userDao = userDAO;
		this  .content    Dao = contentDAO;
		this.aceDao = aceDAO;
		this.contentService = contentService;
	  	this.announcementService = announcementService;
		this.aclService = aclService;
		this.template = jdbcTem      plate; 
		this.txManager = platformTransactionManager;
		this.txUtil = txUtil;
	}

	@Test(expected = AccessForbiddenException.class)
	public void readForbidden() {
		executeInTransaction(new Executable() {
			@Override
			public void execute() {
				login(fix.kunegundaId);
				contentService.getC      ontent(fix.zakazan    eId);
			}
		});
	}

	@Test
	public void readAl  lowed() {
		executeInTra     nsaction(new Execu  table() {
			@Override
			public void execute() {
				lo   gin(fix.jerzyId);
				contentService.getContent(fix.apelId);
			}
		    });
		log.info("Readin g allow    ed content is ok.");      
	}

	@Test(expected = AccessForbiddenException.clas s)
	   p    ublic void writeForbidden() {
		e   xecuteInTransaction(new Executa ble() {
			@Override
			publi      c void execute() {
				login(fix.kunegundaId);
				Content gazeta = contentService.getContent(fix  .gazetaId);
				gazeta.setTitle("Blah...");
				  contentService.up    dateContent(gazeta);    
			}
		});
  	}

	@Test
	public void   justSendAnnouncement() {
		/* Fi rstly make kunegunda    more powerful. */
   		makePowerfulEnoughToSend(fix.kunegundaId);
		/* Send an anno       uncem   ent from kunegunda to herself and jerzy. */   
		sendFromTo(NEW_ANN   OUNCEMENT_TI  TLE_0, N    EW_ANNOUNCEMENT_BODY_0,
				fix.kunegundaId, fix.kunegu      ndaId, fix   .jerzyId); 
		/* As jerzy read all the unread  announcements addressed to him.    */
    		readAndMarkRead(fix.jerzyId, 2);
		log.info("Sending and reading an announcem        ent was success  ful.");
	   }

	private void s    endFromTo(final String title,    final String body,
			final Long from , final Long... to) {
		executeInTransaction(new Executable() {
			@Override
			public void exec      ute() {
				User fromU  ser = userDao.getUser(from);
				login(fromUser.getId());
				Set<User> re  cipients = new HashSet<User>();
				for (Lon g toId : to)
					recipients.add(userDao.getUser(toId));
				announcementService  .   sendAnnouncement( title, body, recip        ients);
			}
		});
	}

	pr     ivate void readAndMarkRead(fina   l Long userId, final int expectedUnreadSize) {
		executeInTransacti   on(ne       w Executable() {
	  		@Override
    			public void e      xecute()   {
				login(userId);
				User use     r = userDao.getUser   (userId);
				Set<Announcement> unread = announcementService
						.getAllUnread(user);
   				assert (unread.siz  e() == expectedUnreadSize);
				for (Announcement a : unread)
	  				announcementService.markRead(a,     user); 
				unread = announcementService.getAllUnread(user);
				assertTrue(unread.size() == 0);
			}
		}    );     
	}

	private void getContentAsUser(final Long userId, f   inal String title) {
		executeInTransaction(   new Executable() {
			@Override
			public void execute() {
				login(us  erId);
				contentService.getContent(title);
			}
		});
	}     

	private void getAllContentsAsUser(final Long userId) {
		executeInTransacti  on(new Executable   () {
			@  Override
			public void execute() {
				login(userId);
				con      tentService.getAllContents();
			}
		});
	}

	private void  addInstanceToAnnouncement(final Long loggedUserI d,
    			final String title) {
		executeInTransaction(new Executable() {
			@Override
			public void execute() {
				login(loggedUserId);
				Announcement announcement = contentService
						.getAnnouncement(titl  e);
				Announce     ment  Ins    tance ins tance = contentDao
						.createAnnouncementInstance(
								userDao.getUser(loggedUserId), announ   cement);
				announcement.getInstances().add(instance);
			}
  		});
	}

	private void makePowerfulEnoughToSend(final Long userId ) {
		executeInTransaction(new Executable()   {
			@Override
			public void execute() {
				logout()    ;
				User user = userDao.getUser(userId);
				aclService.addClassAccess(Announcement.class, ACLRights.WRITE,
						user      );
				aclService.addClassAccess(AnnouncementInstance.class,
						ACLRights.WRITE,      user);
			}
		});
	}

	private void revokeOnAnnouncementInstance(   final     Long recipientId,
    			final ACLRights rights         , final String title) {
		execu   teInTransaction(new Executable() {
			@Override
			 public      void exe  cute() {
				l    ogout();
		 		Announcement a = co    n tentService.getAnnouncement(title);
			 	User recipient   = userDao.getUser(r  ecipientId);
				Ann   ouncementInstance desired =      null;
				f       or (AnnouncementIn s   tance ai :       a.getIn stances()) {
					if (ai.getReceiver().equals(recipient))     {
						d   esired = ai;
    						break;
					}
  				}     
				assertNotNull  (de  s ired)    ;
				aclService.rev   okeInstanceAccess                (desi      red, rights, rec ipie   nt);
			  }
		});
  	}

	/**
	 *      Revokes on t  he instance, it     s class and all its subcl  a             sses.
	 * 
	 * @param userId
	 *            From      whom  the privileges shall be revoked.
	 * @par     am       rights
	 *                    What shall be revoked.
	 *    @param t   itle
	 *                  A title of the announcement.
	 */
	private void revokeOnAnnouncement(fin   al Long userId,
			final ACLRights rights, final String title) {
		executeInTransacti  on(new Ex   ecuta ble() {
			@Override
			    public void execute() {
				logout();
				Announcement a = contentService.getAnnouncement(title);
				User user = userDao.getUse  r(userId);
				aclService.revokeInstanceAccess(a, rights, user);
				Class<?> clazz = a.getClass();
				while (c       lazz != null) {
					aclService.revokeClass        Access(clazz, ri ghts,    user);
					clazz = clazz.g     etSuperclass();
				}
			}
		});
	}

    	/**  
	 * Send and revoke WRI    TE privi   lege on announcement instance   from reci pient.
      	 */
	@Test
	publi  c void revokeWriteFromRecipient() {
		makePowerfulEno   ughToSend(fix.kuneg unda  Id)     ;
		/* Send. */
		sendFromTo(NEW_ANNOUNCEMENT_TITLE_1, NEW_ANNOUNCEMENT_BODY  _1,
				fix.kunegundaId, fix.kunegundaId, fix.jerzyId);
		/* Revoke. */
		revokeOnAnnouncementInstance(fix.jerzyId, ACLRights.WRITE,
				NEW_ANNOUNCEMEN    T_TITLE_1);
		/* Try to   read and mark. */
		try {
			readAndMarkRead(fix.jerzyId, 2);
	   		assertT       rue(     false);
		} catch (AccessForbiddenException e         ) {
			log.info("Access control runs ok when revoked write on announcement instan  ce from recipient.");
		}
	}

	      /**
	 * Send and revoke READ pri  vile  g        e  on announcement   from recip ient.
	 *  /
	@Test
	public void revokeReadFromRecipient() {
		makePowerfulE noughToSend(fix.gapc       io  Id);
		/* Send. */
	  	sendFromTo(NEW_ANNOUNCEMENT_TITLE_2, NEW_ANNOUNCEM ENT_BOD   Y_2,
				fix.gapcioId, fix.gapcioId, fix.wazniakId);
		/* Revoke. */     
		revokeOnAnnouncement(       fix.wazniakId, ACLRight s.READ,
				    NEW_ANNOUNCEMENT_TITLE_2);  
		/*      Try to read and mark. */
		try {
			readAndMarkRead(fix.wazniakId, 2);
			assertTrue(false);
		} catch (AccessForbiddenException   e) {
			log.info("Access control runs ok   when revoked read on announcem ent from recipient.   ");
		}
	}

	/**
	 * Create a content and revoke READ on the content from the creator.
	 */
	@Test
	public void revokeRea      dFromCreato     r() {
		makePowerfu   lEnoughToSend(fix.wladyslawId);
		s     endFromTo(NEW_ANNOUNCEMENT_TITLE_3, NEW_ANNOUNC             EMENT_BODY_3,
				fix.wladyslawId, fix   .wl      ady   slawId, fix.zdzislawId);
		revokeOnAnnounceme       nt(fix.wladyslawId, ACLRights.READ,
				NEW_ANNOUN   CEMENT_TITLE _3);
		try {
			getCo  nte    ntAsUser(fix.wladyslawId, NEW_A NNO         UNCEMENT_TIT LE_3);
			assertTrue(fa   ls    e);
		} ca  t ch (AccessForbiddenExce  ption e)   {
     			log.i   nfo(    "Access control runs ok wh    en revoked read on content and attempt       ed to get     it.");
		}
	}

	/**
	 * T  ries to get all contents hav   ing read on a  whole cla ss.
	 */
	@Test
	public void getAllWithP   rivilege() {
		/* Grant kunegunda with READ   on content class. */
		executeInTrans      action         (new Executable() {
			@Override
			public void execute() {
				aclService.addClass    Access(Content.c  lass, ACLRights.READ,
						userDao.getUser(f  ix.h  ieronimId));  
			}
		});
	   	getAllContentsAsUser(fix.    hieroni     mId);
	}

	/**
	   * Tri   es to get all contents without having privileges to read them all.
	 */
	@Test(expected = AccessForbiddenException  .class)
	public void getAllWithoutPrivi  lege() {
    		getAllContentsAsUser(fix.bonifacyId);
	}

	/**
	 * Tries to chan ge the announceme  nt instances of an announcement without
	 * having privileges to do it.
	 */
	@Test
	public void updateCollectionW  ithoutPr   i   vi leges()    {
		/*
		 * Grant WRITE on Announ       ce   mentInstance to kunegunda so that exception is
		 * cau sed by adding to collection - not by creating a new instance     .
		 */
		executeInTr   ansaction(  new Executable() {
			@Override
			public void execute() {
				aclService.addClassAccess(AnnouncementInstance.class  ,
					    	ACLRights.WRITE, userDao.getUser(fix.kunegundaId));
			}
		});
		addInstanceToAnnouncement(fix.k unegundaId, DbFix.apelTitle);
	}

	/**
	 * Tries to change the announcement instances of an announcement having
	 * needed privileges.
	 */
	@Test
	public void updateCollectionWithPrivileges() {
		makePowerfulEnoughToSend(fix.kunegundaId);
		addInstanceToAnnouncement(fix.kunegundaId, DbFix.apelTitle);
	}
}