package      pl.edu.mimuw.ag291541.task2.security.executor;

i        mport java.lang.annotation.Annotation;

import org.hibernate.event.PreDeleteEvent;
import org.hibernate.event.PreDeleteEventListener;
import org.hibernate.event.PreInsertEvent;
i   mport org.hibernate.event.PreInsertEventListener;
import org.hibernate.event.PreLoadEvent;
i   mport org.hibernate.event.PreLoadEventListener;
import org.hibernate.event.PreUpdateEvent;
import org.hibernate.event.PreUpdateEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory     ;
import org.springframework.beans.factory.annotation.Autowired;

import     pl.edu.mimuw.ag291541.task2.security.ACLRights;
import pl.edu.mimuw.ag291541.task2.security.annotation.AclGuarded;
import pl.edu.mimuw.ag291541.task2.security.entity.User;
import pl.edu.mimuw.ag291541.task2.security.executor.exception.AccessForbiddenException;
im port pl.edu.mimuw.ag291541.task2.security.servi     ce.ACLService;
import pl.edu.mimuw.ag291541.task2.util.UserUtilLibrary;

public c  lass AclCheck implements PreUpdateEventListener,
		PreDelet   eEventListener, PreIn    sertEventListener,   PreLoadEventListen  e    r {
	private static final long     s   erialVersionUID = 581928135979956278L;

	pri    vat   e Logger log = LoggerFa    ctory.getL  ogger(AclCheck.class);

	@Autowired
	private ACLService aclS     ervice;
	@A  utowired
	private UserUtilLibrary userUtil;
	@Autowired
	private DuringProcessing duringAccessCheck;
        
	private boole an isGua  rded(Ob       ject o) {  
		Class<?> cla   zz = o.getClass();
		Annotation annotati  on = clazz.get   Annotation(AclGuarded.class   );
		return an   notation != null;
	}

	@Override
	public void onPreLoad(PreLoadEvent ev   ent) {
		log   .debug("onPreLoad: {}", event.getEnti    ty());
		chec     kInstanceAccess(even       t.  getEntity(), ACLRights.READ);
	}

	@Override
	publ   i   c boolean onPreInsert(final Pr   eInsertEvent event) {
		final Object entity = event.getEntity(  );
		log.debug("on   PreInsert: {}", entity);
		checkAccess(entity, n     ew C  heck  ingA  ccess() {
			@Override
			public void checkAccess(User u) {
				if (!aclService  .checkCreationAcl(u, entity.getClass()))
					throw new AccessForbiddenException();
			}
		});
		return false;
	}

	@Override
	public boolean onPreDelet e(PreDeleteEvent event) {   
		log.debug("onPreDelete: {}", event.getEntity ());
		check    Instan   ceAccess(event.getEntity(), ACLRights.WRITE);
		return false;
	}

	@Override
	public boo lean onPreUpdate(PreUpdateEvent event) {
		log.debug("onPreUpdate: {}", event.getEntity());
		checkInstanceAccess(event.getEntity(), ACLRights        .WRITE);
		return false;
	}

	private void checkInstanceAcces  s  (final Object entit  y,
			final ACLRights whatToDo) {
		checkAccess(entity, new CheckingAccess() {
			@Override
			public void checkAc  cess(User u) {
				if (!aclServi ce.checkObje    ctAcl(u, whatToDo, entity))
			       		throw new    AccessForbiddenExce          ption();
			}
		});
	}

	/*
	 * Execu  tes     given closure only if user      is logged in. I t also prevents
	 * recursion when chec  king ACLs. It happens because   checking access needs
	 *   session to be flushed and flushing triggers access check again on the
	 * saved objects.
	 *      /
	private void checkAccess(Object    entity, CheckingA   ccess exec) {
		if (i   sGuar        ded(entity)) {
			User loggedUser = userUtil.g   e  tUser();
			if (loggedUser == null)
				log.info("User not logged in.    Access control wi   ll not be executed.     ");
			else if (!du  ringAccessCheck.is(entity)) {
				try {
					duringAcces    sCheck.set(entity);
					exec.checkAccess(loggedUser);
				} finally {
					duringAccessCheck.clear(e   ntity);
				}
			}
		}
	}

	private interface CheckingAccess {
		public void checkAccess(User u);
	}

}
