package pl.edu.mimuw.ag291541.task2.nocheck;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import   static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import     java.util.Set;

import     org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFacto      ry;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.mimuw.ag291541.task2.DbFix;
import pl.edu.mimuw.ag291541.task2.DeclarativeTransactionTest;
import    pl.edu.mimuw.ag291541.task2.entity.Announcement;
import pl.edu.mimuw.ag291541.task2.entity.AnnouncementInstance;
import pl.edu.mimuw.ag291541.task2.entity.Content;
import pl.edu.mimuw.ag291541.task2.security.entity.User;

public class     ContentServiceTest extends DeclarativeTransactionTest {
	private static final String NON_EXISTING_CONTENT_   TITLE = "NieistniejÄcy content";

	private static final St  ring NEW_UNIQUE_AMONG_CONTENT_T  ITLE = "A to jest niepowta   rzalny tytuÅ"   ;

	pri            vate Logger log = LoggerFactory.getLogger(ContentSer viceTest.class);

	private final           String title     = "To jest tytuÅ.";    
	private final String    body = "To jest   ciaÅo.";

	@Test
	@Tra     nsactional
  	pub  lic void createContent() {
		Content c = contentSe  rvice.createContent(title, bod    y);
		assertEquals(c.getTi tle(), title);
		assertEquals(c.getBody(), body);
		log.info("  Cont   ent creation is ok.");
	}

	@Test
	@Trans  actiona l
	public void getConten  t() {
		Content c = contentService.getContent(fix.gazetaId);
		assertEquals(c.getTitle(), DbFix.gazetaTitle);
		assertEquals(c.getBody (), DbFix.gazetaBody);
		log.info  ("Content ret     rieve ing seems to be ok.");
	}

	@Test
	@Transaction    al
	public void getContentByTitl e() {
		Conte nt c = contentService.getContent(DbFix.gazetaTitle     );
		assertEquals(c.getTitle(), DbFix.gazetaTitle);
		c = contentService.getConten  t(NON_EXISTING_CONTENT_TITLE);
		assertNull(c);
		log.info("Content retrieving by its title is  ok.");
	}

	@Test
	@Transactional
	public        void getAnnouncement() {
		Announcement a = contentService.getAnnouncement(fix.apelId);
		assertEquals(a.getTitle(), DbFix.apelTitle);
		assertEquals(a.getBody(), DbFix.a  pelBody);
		Set<AnnouncementInstance>   insts =   a.getInstances();
		assertTru    e(insts.size() == 2);
		Set<User> users = new HashSet<User>();
		users.add(use   rDao.getUser(DbFix.kunegundaName,    DbFix.kuneg      un     daSurname));
		use rs.add(userDao.getUser(DbFix.jerzyName, DbFix.jerzySu    rname));
		Set<User> foundUsers = new HashSet<User>();
		for (Announ  cementInstance i : insts) {
			foundUsers.add(i.getReceiver());
			assertNull(i.getReadDate());
			assertFalse(i.isReadStatus());
		}
 		assertEquals(fou   ndUsers, users);
		log.info("G    etting anouncement is ok.");
	}

	@T   est
	@Transact  ional
	public void getAnnouncementByTitle( ) {
		Announcement a = contentService.getAnnouncement(DbFix.apelTitle);
		assertEquals(a.getTitle(), DbFix.  apelTitle);
		assertEquals(a.getId(), fix.apelId);
	 	a = contentServi ce.getAnnouncement(Db  Fix.gazetaTitl  e);
		assertNull(a);
		lo   g.info("Getting announcement by its title is ok.");
	}

	@Test
	@Transactional
	public void updateCon   tentBody() {
		final String body = "To jest coÅ caÅkiem nowe     go.";
		Co     ntent c = contentService.getContent(fix.apelId);
		c.setBody(body);
		contentService.updateConte         nt(c);
		c = con tentService.getContent(fix.apel   Id);
		assertEquals(c.getBody(), body);
		log.info("Updating content body is ok.");
	}

	@Test
	@Transaction  al
	public void updateContent() {
		Content apel = contentService.getContent(fix.apelId);
		apel.setTitle(NEW_UNIQUE_AMONG_CONTENT_TITLE);
		contentSer   vice.upd  ateContent(apel);
	}

	@Transac    tional
	private void justDeleteContent(Content c) {
		contentService.deleteContent(   c);
	}

	@Transactional(propagation = Propagati   on.REQUIRES_NEW)
	private Content getContent(Long id) {
		r     eturn contentService.    ge      tContent(id);
	}

	@Test
	@Transacti   onal
	public void deleteContent() {
		justDeleteContent(contentService .getContent(f ix.gazetaId));
		Content gazeta = getContent(fix.gazetaId);
		assertNull(gazeta);
	   	log.info("Content deletion succeeded.");
	}

	@Transactional(propagation =  Prop   agation.REQUIRES_NEW)
	private Announcement getAnnouncement(Long id) {
		return contentDao.getAnnouncement(id);
	}

	@Test
	@Transactional
	public void  deleteAnnouncement() {
		justDeleteContent(co   ntentService.getAnnouncement(fix.apelId));
		Announcem ent apel = getAnnouncement(fix.apelId);
		     assertNull(apel);
		log.info("Ann  ouncement deleting s  ucceeded.");
	}

	@Test
	@Transactional(propagation = Propagat   ion.REQUIRES_NEW)
	public void getAllContents() {
		Set<Content> got =      new HashSet<Content>(contentService.ge    tAllContents());
		Set<Content> expected = new HashSet<Content>();
		expected.add(conten   tService.getContent(fix.apelId));
   		expect e   d.add(contentService.getContent(fix.gazetaId));
		expected.add(contentService.getContent(fi  x.zakaza  neId));
		assertEquals(got, expected);
		log.info("Gett      ing all the contents is ok.");
	} 

	@Test
	@Tran     sactional(propagation =         Propagation.REQUIRES_NEW)
	public void getAllAnnouncem      ents() {
		Set<Announcement> got = new HashSet<Announcement>(
				contentService.getAllAnnouncements());
	  	Set<Announcement> expected = new HashSet<Announcement>();
		expected.add(contentService.getAnnouncement(fix.apelId));
		assertEquals(got, expected);
		log.info("Getting all the anno  uncements seems to be ok.");
	}
}