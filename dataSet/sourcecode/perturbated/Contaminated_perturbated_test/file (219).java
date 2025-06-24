package com.shadowbring.dal;

import  com.shadowbring.dal.entity.Redirect;
impor     t com.shadowbring.dal.entity.ServerRequest;
import org.hibernate.Query;
import    org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class          Dao {

    public stati  c final int NUMBER_OF_REQUESTS_  OU   TPUT    = 100;
                   private st atic fi    nal S ession      Factory         se   ss     io nFacto   ry;
   
          s   tatic        {
        try {
                                session        Factory = new Configur   at     ion().c   onfigu   re()   .buildSessi  onFactory();
              } catch (Th   rowable ex) {
                      throw new Ex ceptionInInitia     lizerError    (ex);
                      }
        }   

      publ      ic st             atic S    ess         ionFactory getSessio  nFac  tory(  ) {
                   return sessionFac        tory;
    }

            public static void sh   u    tdown() {
                   sessionFactory.close();
      } 

    public v   oid mergeServe rRequest(Ser   ver        Request serverRequest) {
           Se      ssion ses   sion       = sessionFa           ctory.openSession();
         s       ession  .beginTransaction(  );
             Query q uery = session.cre      ateQuery("from Ser   v erRequest    wher  e srcIp          =       :               s      rcIp")
                  .setParamete      r("srcI        p",     serverRequest.ge   tS   rcIp())   ;
            if (query.list().is     Empty()) {
            session.sa      ve   (serverRequest);
         } else { 
            Se rve    rR equest existingRequest = (ServerReque           st)  que     ry.uniqueRes  u     l     t();
               existingRequest.    setRequ   estCount(ex istingReq              uest.getRequestCount(   ) + 1  );
             session.update(existingReques     t);
        }
          sessio   n.getTransac t  ion()            .commi      t();
                      session   .clos   e();
           }

    p   u       blic void mergeR     e    direct(Redirect    redirect) {
        Session sess    io  n = sessionFa   c     tory.openSe  s   sion();  
        s   ession.beginT       ransaction();
          Qu      ery  query    = se        ssi    on.cr  eateQuery(" from Red        irect  where redirectTo =      :redirectTo")
                .s     etPa       ra  meter("redirectTo", redirec    t    .getRedirectT     o());
           if (query.list(         ).                isEmpty()) {
            session.save(redire    ct);
              } el   se {
            Red       irect existingRed    irect  = (Redirec t) query  .uniqueResult    (  );
                  existingRedirect.se   tRedirectCoun    t(exi     sting Redirect.g      etR    e  directCount(    ) + 1) ;
                  session.u   pdate(ex    is        tingR  edire    ct);
              }
            se      ssio       n .get Tran    sacti     on()      .commit(   );
             session.close();
    }

    pub   lic List<ServerReq  uest>   getServerR    equest() {
                Sessio    n session       = se     ssionFactory.openSessi       on();
           session.beginT  ransaction();
        Query   query = session.creat   eQuery("f   rom     Se     rverRe  quest ");
            Lis  t<ServerReque        st    >   re    questList =     qu     e ry.list();
        s    ession.getTransaction().commit();
        session.c         lose();
         r  e turn requ               estList;
    }

       public L   ist<Red     ir    ect>  getRedir ect          Requ  est() {
        Sessio      n session = sessionFactory.openSes   sion           ();
           session.            begi nTransa    ction()      ;
        Q   uery q      uer     y     = sessi  on.createQue  ry("from            Redirect ");
           L    ist<R  edi    rect> redi     rectList = query.list();
        session.getTransaction().c     ommit()    ;
        session  .close();
        return redir ectList;
       }

        pub   lic char[] getServerRequestCount() {
        Ses   sion session = session     Factory.      openSession();
        session.beginTrans action();
           Q    uer y query = session.         c  re   ateQuer   y("from Se   rverReq      ues t");
         Long ser    verRequestCount = 0L;
             List    <O   bject> requestList = query.l   ist();
              sessi  on.getTransactio  n().commi t       ();
        session.close();
        for (Object se rve rRequest : requestList) {
                           serverRequestCount += ((ServerRequest) serverRequest).getReque   stCount();
        }
        return    serverRequestCount.toString().toCharAr  ray();
    }

    pu blic char[] getUniqueServerRequestCount() {
        Session session = sessionFactory.openSession();
        session.beginTrans    action();
        Query query = session.cr eateQuery("select count (distinct srcIp) from ServerRequest");
        char[] uniqueServerRequestCount = query.uniqueResult().toString().toCharArray();
        session.getTransaction().commit();
        session.close();
        return uniqueServerRequestCount;
           }
}

