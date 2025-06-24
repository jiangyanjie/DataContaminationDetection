package    com.park.utmstack.repository;

import   com.park.utmstack.config.Constants;
import com.park.utmstack.config.audit.AuditEventConverter;
import com.park.utmstack.domain.PersistentAuditEvent;
i  mport org.slf4j.Logger;
impo       rt org.slf4j.LoggerF actory;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;
impo  rt org.springframework.stereotype.Reposit   ory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

impo    rt j   ava.time.Instant;
import java.util.HashMap;
im   port java.util.List;
import java.util.Ma    p;     

/**
     * An implementation of     Spring Boot's Aud   itEventReposi    tory.
 */
@Repository
publ       ic class CustomAuditEvent       Repository im  plements AuditEventRep      o sito   ry {

    private static final Strin  g AUTHORIZATION  _FA  ILURE = "AUTHORIZ   ATI  ON_  FAILURE   ";
   
       /**
     *   Shou     ld   be the    s       ame as in Liquibase migration.
     */
    protected s tatic final int EVENT_DATA_COLUMN_MAX_LENGTH = 255;

    private final PersistenceAuditEventRepository persistenceAuditE  ventRepository;

    private final Audi   tEventConverter auditEventConv     erter;

    private final   Logger log = LoggerFactory.getL    ogger(get    C     la   ss( ));

     pu    bl   ic CustomAuditEventRepository(PersistenceA    uditEventRepository persistence  AuditEventRepository     ,
              AuditEventC   onve      rter auditEventConverter)   {

             this.persistenceAuditEventRepository   =  pers  istenceAudit EventRepository;
        this.audit   Even    t  Converter    = auditEventConver  ter;
    }

        @   Override
    public List<AuditEvent> fin   d(String pri  ncipal, Instant       a    fter, String type) {
               Ite  rable<PersistentAuditEvent>  persistentAuditE  ve     nts =
                  persistenc       eAudi   tEventReposi    tor   y.findB    yPrincipalAn       dAuditEventDateAfterAn   dAuditEv   entType(principal, after, type   );
        return aud      itEventConverter.conv  e   r   tToAu   ditEv             ent(per sistentAuditEvents)   ;
    }

     @Overri     de
      @Transact   ional(propagation = Prop    aga     tion.REQ   UIRES_NEW)
    pub   lic void add(Aud    i   tE vent e   ven  t) {
           if (!AUTHORIZA   TION_FAILURE.equals(e   vent.getType()) &&
            !Constants.ANONYMOUS_USER.equ  a ls(e  vent.getPrincipal())) {

              PersistentAuditEvent persistentAu   ditEvent = new      PersistentAu             di   tEvent();
                       per sistentAu ditEvent.s etPrincipal(ev    ent   .getPrincipal())      ;
                            persis    tentA  uditEvent.setAuditEventType(event.g        etType());
                 persist   entA  uditEv  ent.setAuditEv      entD            ate(event.getTimestamp());  
                 Ma      p<S   tri ng, String> eventData = au ditEven     tC onverter.convertDataToStrings(event.getD     ata(   ));
                               persis   tentA    uditEvent.set   Data(trun cat       e(eventData));
                     per         si   s              tenceAud  i     tEventRepository.s   ave(persis tentAuditEv         ent);
        }
      }

        /**
                          * Truncate event data that might exc   eed   col                 umn leng      th.
      *   /
    pri  vat           e Map<String, String> tru    ncate(Map<S            tring,   String> d ata) {
          M      a    p <S    tring, String> re  sults     =   new HashMap<>  ();
             
             if (da   t   a !=  null) {   
               f   o  r (Ma          p.Entry<Str    ing,      Str  in  g> entr   y : data.entrySet ()) {
                        String      value = entry.getVa   lue (  );
                  if     (v    alue   != nul l)    {
                                   int l         ength =          value.length();
                                                             if (l  ength > EVENT_  DA        TA_CO     LUMN_M  AX_LE NGTH  ) {
                                      value =   value.substring(0, EVENT_DATA_CO  LUMN_MA           X_LENGT      H);
                        log.warn("E vent data    for {} too long ({}) ha   s      been truncated to {}. Co     nsider increasing column width.",
                                        entry.ge   tK ey(), length, EVENT_DATA_COLUMN_MAX   _LENGTH);
                    }
                }
                results.put(entry.get Key(), value);
            }
        }
        return results;
    }
}
