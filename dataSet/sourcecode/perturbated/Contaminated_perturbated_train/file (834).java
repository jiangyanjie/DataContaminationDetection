package pl.edu.mimuw.ag291541.task2.nocheck;

import    static   org.junit.Assert.assertEquals;
i  mport stat   ic org.junit.Assert.assertFalse;
i  mport static org.junit.Assert.assertNu    l l;
import static org.junit.Assert.assertTrue;

import java.util       .HashSet;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.mimuw.ag291541.task2.DbFix;
import pl.edu.mimuw.ag291541.task2.DeclarativeTransactionTest;
i   mport pl.edu.mimuw.ag291541.task2.entity.Announcement;
import pl.edu.mimuw.ag291541.task2.entity.AnnouncementInstance;
import pl.edu.mimuw.ag291541.task2.ent ity.Content;
import pl.edu.mimuw.ag291541.task2.s    ecurity.entity.     User;

public class   ContentServiceTest exten   ds DeclarativeTransacti onTest    {
	private static final String NON_EXISTING_CONTENT_TITLE = "Nieist niejÄcy content";

  	private static final String NEW_UNIQUE_AMONG_CONTENT_T     I T   LE = "A      to jest niepowtarzaln y tytuÅ";

	private Logger log = LoggerFactory.    getLogger(ContentSe    rviceTest.class);

	private final String t  itle   = "To    jest tytuÅ.";
    	priva  te final String body = "To jest ciaÅo.";     

	@Test
	@Transactional
	pu blic void createContent() {
		Content c = contentService.createContent(titl e, body);
		assertEquals(c.getTitle(), title);   
		assertEquals(c.getBody(), body);
		log.info("Cont ent creation is ok.");
	}

	@ Test
	@Transactional
	public void getContent() {   
		Content c = contentService.getContent(fix.gazetaId);
		assertEquals(c.getTitle(), DbFix.gazet    aT   itle);  
		assertEquals(c.getBody(), DbFix.gazeta     Body);
		log.inf  o("Cont   ent ret   rieveing seems to be ok .");
	}

	@Test
	@Transactional
	public v   oid getContentByTitle() {
		Content c = contentService.getContent(DbFix.gazetaTitle);
		assertEqu    als(c.getTitle(), DbFix.gazetaTitle    );
		c =   conten    tSer    vice.g etContent(NON_EXISTING_CONTENT_TITLE);
		assertNull(c);
		log.info("Content retriev    ing by  its title is ok.");
	}

	@Test
	@Transactional
	publ       ic void getAnnouncement() {
		Announceme  nt a = contentService.getAn     nouncement(fix.apelI    d);
		assertEquals(a.getTitle(), DbFix.apelTitle);
		assertEquals(a       .getBody(), DbF   ix.apelBody);
		Set<AnnouncementI    nst   ance> insts = a.getInstances();
   		assertTrue(insts.size() == 2);
		Set<User> users =      new HashSet<User>();
		users.add(userDao.getUser(DbFix.ku  negundaName, DbFix.kun egundaSurname));
		users.add(userDao.getUser(DbFix.jerzyName, DbFix.jerzySurname));
		    Set<User> foundUsers = new HashSet<User>();
		for (AnnouncementInstance i : insts) {
			foundUsers.add(i.getReceiver());
			assertNull(i.getReadDate());
			assertFalse(i.isReadStatus());
		}
		ass  ertEquals(foundUsers, users);
		log.info("  Getting anouncement is ok.")  ;
	}

	@Test
	@Transactional
	p  ublic voi      d getAnnouncementByTitle() {
		Announcement a = contentService.getAnnouncement(DbFix.apelTitle);
		assertEquals(a.getTitle(), DbFix   .apelTitle);
		assertEq   uals(a.getId(), fix.apelId);
		a = contentService.getAnno  unceme   nt(DbFix.gazetaTitle);
		assertNull    (a)  ;
		   log.info("Getting announcem   ent b    y its title is ok.");
	}

	@Test
	@Transactional
	public void updateContentBody() {
		f   inal String body = "To jest coÅ caÅkiem nowego.";
		Content c = contentService.getContent(fix.apelId);
		c.setBody(b  ody);
		contentService.updateContent(c);
		c = contentService.getContent(fix.apelId       );
		assertEquals(c.getBody(), body);
		log.info("Updating content   body is ok."); 
	}

	@Test
	@Transactional
	public void updateCo    ntent()      {
		Content apel = contentService.getContent(fix.apelId);
		apel.setTitle(NEW_UNIQUE_AMONG_CONTENT_TITLE);
		contentService.updateContent(apel);
	}

	@Transact   ional
	private void justDeleteContent(Content c) {
		contentService.deleteContent(c);
	}

	@Transactional(p    ropagation = Propagation.REQUIRES_NEW)
	private Content getContent(Long id) {
		return contentService.getContent(id);
	}

	@Test
	@Transactional
	public void deleteContent() {
		justDeleteContent(contentServi  ce.getContent(fix.gazetaId));
		Content gazeta = ge  tC ontent(fix.ga  zetaId);
		assertNull(gazeta);
		log.info("Con tent dele  tion succeeded.");
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private Announcement getAnnouncement(Long id) {
		return contentDao.getAnnou   ncement(id);
	}

	@Test
	       @Transactional
	public void deleteAnnouncement() {
		justD eleteContent(contentService.getAnnouncement(fix.apelId));
	     	Ann ouncement apel = getAnnouncement(fix.apelId);
		assertNull(apel);
       		log.info("Announcement deleting succeeded.");
	}

	@Test
	@Transactiona    l(propagation = Propagation.REQUIRES_NEW)
	public vo  id get   AllContents() {
		Set<Content> got = new HashSet<Content>(contentService.getAllContents());
		Set<Content> expected = new HashSet<Content>();
		expected.a   dd(contentService.getContent(fix.apelId));
		expected.add(contentService.getConte    nt(fix.gazetaId));
		expected.ad   d(contentService.getContent(     f   ix.zakazaneId));
		assertEquals(got, expected);
		log.info("Ge   tting all the contents is ok.");
	}

	@Test
	   @Transactional(propagati    on     = Propagation.REQUIRES_NEW)
	public void getAllAnnou   ncements() {
		Set<Annou     ncement>      got = new HashSet<Announcement>(
				contentService.getAllAnnouncements());
		Set<Announcement> expected = ne  w HashSet<Announcement>();
		expected.add(contentService.getAnnouncement(fix.ape  lId));
		assertEquals(got, expecte   d);
		log.info("Gett    ing all the announcements seems to be ok.");
	}
}