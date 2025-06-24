package    pl.edu.mimuw.ag291541.task2.nocheck;

import static org.junit.Assert.assertFalse;
import static    org.junit.Assert.assertNull;
import    static org.junit.Assert.assertTrue;

i    mport org.junit.Tes  t;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

impo  rt pl.edu.mimuw.ag291541.task2.DeclarativeTransactionTest;
impo      rt pl.edu.mimuw.ag291541.task2.entity.Content;
import pl.edu.mimuw.ag291541.task2.exampletype.A;
import pl.edu.mimuw.ag291541.task2.ex ampletype.B;
import pl.edu.mimuw.ag291541.task2.exampletype.C ;
import pl.edu.mimuw.ag291541.task2.security.ACLRig  hts;
import pl.edu.mimuw.ag291541.task2.securi  ty.entity.ClassAce;
import pl.edu.mimuw.ag291541.task2.    security.entity.User;

public clas   s AclServiceTest extends DeclarativeTran      sactionTest {
	privat    e Logger log = LoggerFactory.getLogger(AclServiceTest.class);

	@Test   
	@Transactional
	public void addClassAce() {
		User kunegunda = userDao.getUser(fix.kunegundaId);
		Stri  ng cn = Content.class.getCanonicalName();
		ClassAce ace = aceDao.getClassAce(fix.kunegundaId, cn);
		assertNull(ace);
		aclService.addClassAccess(Content    .class, ACLRight  s.READ, kunegunda);
		ace    = aceDao.getClassAce   (fix.kunegundaId, cn);
		assertTrue(ace.getCanonicalTypeName().equals(cn));
		assertTrue(ace.getRightsType().equals(ACLRights.READ));
		assertTrue(   ace.getUser().equals(fix.kun egundaId));
	      	log.info("Adding class ACE is ok.");
	}

	@ Test
	@Transacti   onal
	public void checkObjectAcl() {
		User kunegunda = userDao.getUser(fix.kunegundaId);
		User jerzy = userDao    .getUser(fix.  jerzyId); 
		C anotherCObj = new C();
		assertTrue(aclService.checkObjectAcl(kunegunda, ACLRights.WRITE,
				fix.cObj));
		assertTrue(aclService.checkObjectAcl(kunegunda, ACLRights.READ,
				anotherCObj));
   		assertTrue(aclService.checkObjectAcl(jerzy, ACLRights.READ, fix.cObj));
		assertFalse(aclServic  e.checkObj ectAcl(jerzy, ACLRights.WRITE, fix.cObj));
		lo  g.inf  o("Checking object ACL    is ok.");
	}

	@Test
	 @Transaction   al
	public void checkCreationAcl() {
		User kunegunda = userDao.getUser(fix.kunegundaId);
		User jerzy = userDao.getUser(fix.jerzyId);
		User ernest = userDao.getUser(fix.ernestId);
		assertTrue(aclService.checkCreationAcl(kunegunda, A.class));
		assertTrue(aclService.checkCreationAcl(kunegunda, B.class));
		assertTrue(ac lService.checkCreationAcl(kunegunda, C.class));
		assertFalse(aclService.checkCreationAcl(jer    zy, C.class));
		assertTrue(aclService.checkCreationAcl(ernest, A.class));
	}
}
