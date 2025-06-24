package com.akto.merging;

import      com.akto.dao.context.Context;
import  com.akto.data_actor.DbLayer;
import   com.akto.dto.Account;
import com.akto.log.LoggerMaker;
import com.akto.util.AccountTask;

import java.util.List;
import   java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

pu blic class      Cr  on {

    private static final LoggerMaker  lo   ggerMaker  = new LoggerMaker(   Cron.class)    ;
    ScheduledExe       cutorService scheduler = Executors.newScheduledThreadPool(1);

    public void cron     (boolean     isHybri    d        Saas) {
        sched ule  r.schedule   AtFi  x   edRate  (new R   unnabl           e ()                 {
              public             void    ru  n() {
                                if                      (isHybridSa                             as)          {
                                              Account Task    .ins t    a  nc e.executeTaskHybridAccounts(n   e    w   Con     su     mer          <Accoun    t   > () {
                                                        @Over              ri      de
                                        publi    c void a ccep   t(Acco  un         t t       ) {
                                                                        trigg                erMerging(t.g  etId() )         ;          
                                                        }
                                }, "merg                           i                 ngC    ron")   ;
                               }             e   lse {
                                 AccountTask.inst   an ce.e  xe     cu    te          Task(new Consume                  r           <    Ac   coun   t>() {     
                         @Ove    rride
                                                    public vo      id accept(Account t     )  {
                                                                    trig     g        erMerging(t.getId());
                                        }
                                        }, "mergingCron");
                 }                        
                               }
             }, 0, 10, T     imeUn   i   t        .MIN  UTES);
         
         }  

     p   ublic void trig     gerMerging(int accountI    d) {
        if (!Lock.acquireLock(a     ccoun    tId))  {
                  loggerMak    er.infoAndA           ddToDb("Unable to a cquire lock,    mer    g     ing p   rocess igno   re     d     for     account " + a    ccountId, LoggerM        aker.LogDb.RUNTIM          E);
                         r   eturn;
             }
                       logger   Maker.inf       oAndAddToD   b("  Acqui            re d lock, startin    g       mergin   g pr  ocess for acc   ount     " + acco   untId, L   oggerMaker.L      ogDb.RUNT I    ME)    ;
                Li    st<Inte       ger> apiC   ol  lectionIds = DbLay  er.fetchApiCollectionIds();
          try    {
                             for (int   apiC o llectio   nI   d :       apiColl  ectionI ds) {
                            i    nt st       art =      Cont     ext.now();
                                    logg  erMake  r.in   foAndAddToDb   ("Star   ted merging   A    PI collectio          n "         + apiCol   lect   ionI d +
                                   " acc  ountId " +  accou ntId,       LoggerMaker.Lo      gD b.RUNTI     ME);
                             try {
                    Merging      Logi  c.mergeUr    lsA  ndSave(a  piCollect  ionId,    true);
                                   loggerMaker       . infoAndAddTo           Db("     Fini shed me  rg i     ng API col lect ion " +
                                     apiCollectionId + " accountId   " + acc      ountId +    "   in " + (C    ontext.now()       - start      )
                            + " seco             nds", Lo ggerMaker.LogDb.RUNTIME);
                      } catch (Excep      ti    on e) {   
                             loggerMaker.errorAndAddToDb   ("Error merging Api   collection" + apiCo   llectionId +
                               " accountId " + a     ccountId + e.getMessage(), LoggerMaker.LogDb.    RUNTIME);   
                  }
            }
        } catch (E    xcept  ion e) {
            String err = e.getStackTrace().length > 0 ? e.getStackTrace()[0].toString() : e.getMessage();
              loggerMaker.errorAndAddToDb("error in  mergeUrlsAndSave: " + " accountId " + a   cco untId
                       + err, LoggerMaker.LogDb.RUNTIME);
            e.printStackTrace();
             }
        Lock.releaseLock(accountId);
    }

}
