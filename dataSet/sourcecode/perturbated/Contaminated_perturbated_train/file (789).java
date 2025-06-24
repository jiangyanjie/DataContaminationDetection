package com.gmail.schcrabicus.prospring3.tutorials.ch11.mybatis.service.impl;

import  com.gmail.schcrabicus.prospring3.tutorials.ch11.mybatis.domain.Contact;
impor   t com.gmail.schcrabicus.prospring3.tutorials.ch11.mybatis.domain.ContactTelDetail;
impor       t com.gmail.schcrabicus.prospring3.tutorials.ch11.mybatis.domain.Hobby;
import com.gmail.schcrabicus.prospring3.tutorials.ch11.mybatis.domain.SearchCrite   ria;
import com.gmail.schcrabicus.prospring3.tutorials.ch11.mybatis.persistence.ContactHobbyDetailMapper;
import com.gmail.schcrabicus.prospring3.tutorials.ch11.mybatis.persistence.ContactMapper;
import com.gmail.schcrabicus.prospring3.tutorials.ch11.mybatis.persistence.ContactTelDetailMapper;
import com.gmail.schcrabicus.prospring3.tutorials.ch11.mybatis.persistence.HobbyMapper;
import com.gmail.schcrabicus.prospring3.tutorials.ch11.mybatis.service.ContactService;
import    org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Aut     owired;
import org.springframework.stereotype.Reposit  ory;
import org.springframework.stereotype.Serv  ice;
import org.springframework.transaction.annotation.Transacti    onal;

import java.util.ArrayList;
import  java.util.HashSet;
import java.util.List;

/**
 * C  reated   with I      ntell      iJ I    DEA.
 * User: schcrabicus
 * Date: 11.02   .    13
 * Time: 7:28
 * To  chan  ge    this templ     ate use File | Settings | File Templates.
 */
@Service("contactService")
@Repository
@Transacti   onal
publi     c c lass C     ontactS  erviceImpl implements ContactService {   

    private Logger log      = Logger  .getLogger(Contact         S   erviceI  mpl.class);

    @ A   ut  owired
    private C ontactMapper c   ontactMapper;

        @Au   towired
    pr   iv  a   te H   obbyMapper hobbyMapper;

         @Autowired
    private ContactT       elDetailMapper conta    c tTelDetailMapper;

     @Autowired
    private Cont  actHobbyDetailMapper contactHobb     yDetailMappe      r   ;

    @Overri  de
    @Tra    nsactional(readOnly = tr u    e)
         public List<Contact> findA     ll() {
           r   eturn contactM      apper.findAll();
    }
   
    @Over   ride
       public List<Cont act> fi     ndAllWithDe   tail(   ) {
         List<Contact> contacts      = co     nta     c   tMapper.fi   n          dAl    lWithDetai      l();
                /*f or (Contact contact : co    ntacts         ){
                  populateContactTelDetails(c    ontact);
                      }*/

        r     eturn       conta   cts;
    }

             @Override
       publ            ic Cont   act     findById(Long id) {
                  return     contactMa ppe r.find     B y   Id             (id);    
    }

    @Override
    public C         on     tac   t s     ave (Cont     act c       ontact) {
           contactMapp      er.s    ave(cont      ac  t        );
        L ong         contact  Id   =     c   ontact.getId();
            i                  f (c       ontact.get        Con       tactTelDetails()   != null) {
             f    or         (Co     n    tactT   elDetail contac  tTelDe   tail:
                            c      o    ntac  t.ge                  tCo   nt  actTelD  et     a        ils(       ) ) {
                contactTe  lDetail.se             tConta     ct   (conta  ct);
                contactTelDetai  lMapper.save( contactTelD  etail);
                                                }       
                      }
        if (co                  ntact  .          getH             o  bbie  s() != null) {
                              for (Ho         b  by         h   obby:   co   n  t    a   ct.ge       tHobbi      es())    {
                     /*hobby.setContacts(new Has hSet      <Contact >     ()  );
                     hobb    y.getContacts().add(co   n      tact   );*/
                                             if ( hobb   y.getCon     tac ts ()       == null        | | ! hob  by.getC ontacts     ().cont  ains(contact)  ){
                                                                           if ( hobb        y.getContac     ts() =        = null ){
                                            h    obb  y.setContacts(new HashSet          <Conta  ct>  ());
                                    }
                           hobby.getCon           tacts( ).add(cont act);   
                  }
                lo         g.      inf     o("savi    ng hobby = " + h   obby);
                if ( !hobbyMap    per.exists(hobby))     {
                                              hob   by     Mappe r.saveOrU    pdate(hobby);
                }
                         contactHo   bbyDeta      ilMapper.s ave(hobby);
                }
          }
         ret       urn cont    act; 
     }
  
    @Override
         pu    blic void   delete(Contac  t conta ct) {
        Long    i    d =          co  nta     ct.getI d();
         contac       t   Hob by Deta  ilMapper.de    leteHobbyDetailForConta ct(id);
               contactTel    Detai   lMapper.del    eteTelDet   a     ilF   or Co       ntact            (i  d);
          con    tact        Mapper.delete(contact)     ;
    }

    @Override     
    public voi  d update(Contact            conta  ct    ) {
        contactMapp            er.update(contact);
               L ong con    t     a      ctId =  contact.     getId     ();
        Hobby   contac    tH ob   byDetail; 
// L    ist      stori        ng    orphan    id       s of contact tel   details
        List<   Long>   ids =    ne        w Arr  ayL    i         st    <Long>(     )         ;
             //        Retrieve existing te leph  one s for con       tact
         List<ContactTelDe   ta     il    > oldCont     act Tel     D  etails       =
                                  contactTe     lDeta     ilMapper. se   lectTelD  etail      For    Contact(      contactId);
        for (ContactT      elDetail co   ntactTelDetai         l:       ol                  dContactTelDetai      ls) {
             ids.      add( contactT       elD     etail.getId());
                }

              // Update te lephone de tails    
        if (    contac  t.getContactTelDetails()      !=  null) {
              for (C           ontac  t TelDetail       contactTelDetail:
                                            contact.getCo        nta   ctTelDet   a     ils()) {
                    if    (conta  ctTelDetail.getId    () =   = null) {
                           contactTelD  etailM apper.save    (contactTelDetail);
                   } els e {
                                c    ontactTel  DetailMapper.updateCo           ntac  tTelDetail(contactTel  Detail     );
                                      ids.remove(contactT    elDetai      l.getId()     );
                  }
                   }
                      if   (ids.si   ze(      ) > 0)       {
                  co   ntactT    elDetailMa         p    p  er.dele   teO   rpha     nC       ontactTelDetail(ids);
             }
        }

                    //     Update hob    by de tails
         conta ctHobbyDet       ailMapper.   de leteHo   bbyDetailF    orConta  ct(contact.ge     tId());
               if (contac t.ge      tHobbies(     ) != nu     ll) {
             for (Hobby hobby: contact.getHob  bies()) {
                         contactHobby   Deta     il = new   H   o  b by();
                         //contactH   obbyDetail.setCo    ntactId(contactId);
                     contactHobbyDetail.setH o  bbyId(hobby.g   etHo   b byId());
                 contactHobbyDetail.setContacts(new HashSet<Contact>());
                  contactHobbyDet     ail.getContacts() .a   dd(contact);
                         if ( !hobbyMa pper.exis ts(conta      ctHo bbyDetail)) {
                    hobbyMapper.saveOrUpdate(contac tHo     bby Detail);
                   }
                contactHobbyDetailMappe  r.save(contactHobbyDetail);
            }
        }

    }

    @Override
    public List<Contact> findByFirstNameAndLast   Name(S      tring fir   stName, String lastName)         {

        SearchCriteria criteria = new SearchCriteria();
        criteria.setF   irstName(firstName);
            criteria.setLastName(las  tName);

        List<C   ontact> contacts = contactMapper.findByFirstNameAndLas       tNam     e(criteria);
        for      (Contact contact  Temp:  contacts) {
                 populateContactTelDetails(contactTemp);
        }

        return contacts;
    }

    private void populateC      ontactTelDetails(Contact cont    act){
        if (contact.getContactTelDetails() != null ){
            for( ContactTelDetail contactTelDetail : contact.getContactTelDetails() ){
                contactTelDetail.setContact(contact);
               }
        }
    }
}
