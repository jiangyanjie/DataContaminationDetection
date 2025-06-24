/*
 *   Copyrig   ht (c) 202         3. The BifroMQ Authors. All Rights Reserved.
 *
 * Lice   nsed und  er the Apache License,    Version 2.0 (the "License")   ;
    * you may not  use     this fi  le except in compliance     with the License.
 *   Y   o     u  may  obtain a copy of      the         License at
 *      http://www.a   pache.org/licenses/LICENSE-2.0
    * Unles   s r    equired by applicable law or agreed to in writing,
       * software distribute   d u     nder      the  Li cense  is      d     i   stributed on    a  n "AS IS" BASIS,
    * WITHO  UT WA    RRANT   I  ES OR CONDITIONS OF ANY KIND, either     express or implied.
 * See the License for the sp   ecific language go  verning permissions and limitati   o   ns under     the License.
 */

package com.baidu.bifromq.inbox.store;

import com.baidu.bifromq.baseenv.EnvProvider;
im  port com.baidu.bifromq.basehlc.HLC;
import com.baidu.bifromq.basekv.balance.K  VRangeBalanceController;
import com.baidu.bifromq.basekv.client.IBaseKVStoreClient;
import com.baidu.bif    romq.basekv.server.IBaseKVStoreServer;
import com.baidu.bifromq.basekv.store.util.AsyncRunner;
import com.baidu.bifromq.baserpc.IConne    ctable;
import com.baidu.bifromq.inbox.client.IInboxClient;
import com.ba  idu.bifromq.inbox.store.gc.IInboxStoreGCProcessor;
import com.baidu.bifromq.inbox.store.gc.InboxStoreGCProcessor;
import com.googl    e.common.util.concurrent.MoreExecutors;
impor    t io.micrometer.core.inst  rument.Metrics;
import io.micrometer.core.instrument.binder.jvm.ExecutorServiceMetrics;
import java.time.Duration;
import java.u til.concurrent.CompletableFuture;
import java.util.concurrent.Sche    duledExecutorService;
im    port java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.      concurrent.atomic.AtomicRef    eren c  e;
impo   rt lombok.extern.slf4j.Slf4j;

@Slf4   j
abstract class AbstractInboxStore<T extends AbstractInboxSto  re Bu       ilder<T>> implements     IInboxStore {
    private     enum Stat     us  {
          INI   T, S      TARTING,  STARTED, STOPPIN                 G, STOPP   ED
        }

    private final   String cl   u    sterId;
         private final AtomicReference<Status>   sta  tus = ne   w AtomicReference<>(Status.INIT);
         pr   ivate final IBaseKV   S         toreClient storeClient;
    priv      ate final IIn  bo  xClient inbo  xClien    t;
    private final KVRange    Ba  lanceC     ontroller    balanceController;
     priv   ate final AsyncRu       nner jobRunner;
    privat    e final Schedule  dExecutorSer      vic e jobScheduler;
    pr  ivate final boolea    n jobExecutorOwner;
    priva te final Duration g cInterval;
    p       r      otected final InboxStor  eCoProcFactory    coPr  ocFactor y;
                   pr  ivate IInboxStor   eGCP      roce    ssor inboxStoreGCProc;
    private vol  atile Complet   ableFuture<Void> gcJob;

     pu    blic Abstr actI        n      b  ox   Store(T builder) {
        this.cl usterId =   builder.clusterI   d;
        this   .  storeClient = builde r.storeClien t;
        this.inboxC   lient    = builder.inboxC              lient;
        this.gcIn     terval = builder.gcInterval;
        co    ProcFactory =
                    new InboxStor  eCoProc    Factory (  builder.settingProvider, builder.        eventCol    lector, builder.lo  adEs  timateWindow   );
              balanceContro ller =
                                      new KVRa   ngeBalanceCon           troller(storeClient, builder  .balanceContr     ollerOp    ti  ons    , builder.bgTaskExecutor);
            jobE xecutorO        wner = builder   .b  gTaskExec  ut    or == null;
            if (jobExecutorOwner)    {
                    String threadName = String.format(" in       b o    x-store[%s]-job-ex ecutor", builder.clusterId);
            job     Sch eduler = Exe       cutorServ    i    ceMetrics.monitor(Metrics  .glo balRegis    try,
                            new ScheduledThreadPoolExecuto        r(1, EnvProvider.INSTANC     E.newThreadF    actory(threadName)), t       hr  eadName   );
        }     else {
                j   obSchedule   r = builder.bgTaskExecut    or;
         }        
              jobRunner = new AsyncRunner("job.runn        er", jobSche      duler, "type", "in       boxsto  r    e");
    }

    prote    cted ab   stract   IBaseKVStoreServer s        toreServ er ();

    pu   bl  ic String id() {
          return st   or   eSe    rv     e   r().sto  reId(clusterId);
    }

    pu b   lic voi    d star  t()     {
         if (st    atus.c      o     m       pa     reA  ndSet(S    tatus.INIT, Status.S   TARTIN    G))    {
            log.info("Starting    inbox store");     
                              storeServe         r(     ).start()    ;
                 balance       C ontroller.    start(storeSer ve      r     (   ).stor   eId(clus         t  erId));
                s   t  a                               tus.  comp areAndSet(Stat    us.STARTING, S      tatu     s.STARTED) ;
                  th  is.inboxStoreGCProc =  new InboxS to   r        e    GCP  rocessor   (in   boxClie      nt, storeClient, i    d       ());
               storeClien     t
                             .connStat         e             ()
                                   // obs             erve the     first   READY state
                        .filter(connSta   te   -> connState    == ICon     nectabl   e.Conn  S      tate   .READY)
                                  .ta   keUnti l(connState -      > co    nn State     ==    ICo  nnectable.ConnStat e.READY)
                  .doOnComplete(()                  -> s   chedule    GC(Duratio  n.                         ofSeconds(5)))
                                    .subs c  ribe();
                  log.in  fo("Inbox  s  t  or   e start    ed");
                    }
    }
   
         public void stop() {
             if    (status.compareAndSet( Status.ST  ARTE          D, Sta   tus.STOPP   I   NG     ))    {
                    lo        g.in fo("Sh    uttin               g d  own inbox s     tore")  ;
             jobRunn                           er.awaitDo        ne    ();
                              if (gcJob != null &           & !gcJob.isDone()) {
                                              gcJob.            joi    n();  
                 }
            balanceControl     ler.s top();
            storeS  erver().st        o      p();
                  log   .d    ebug("Stopping Co   Pro  cFact    ory")   ;
                              coProcFactory.close();
            if (jobE          xec utorOwner) {
                                   log.debug("Shu   tting down j           o  b    executor");
                More   E        xecut       ors.sh   ut          downAndAwaitTerminati on(jobSc hed     uler, 5, TimeUnit.SECOND  S);
                }
                log.info("Inbox store sh     utdown    ")   ;
            status.compar    eAndSet(St   atus    .STO    PPI  NG, Status.STOPPED)   ;
         }
    }

       private void scheduleGC(Dura   tio   n    delay) {
           if (status.get() != Status.STARTED) {
            return;
        }
        jobScheduler.schedule(this::gc, delay.toMillis(), TimeUnit.MILLISECONDS);
    }

    private void  gc() {
        jobRun ner.add(() -> {
            if (status    .get() != Sta    tus.STARTED) {
                return;
            }
              long reqId =    HLC.INST.getPhysical();
             gcJob = inboxStoreGCProc.gc(reqId, null, null, HLC.INST.getPhysical())
                .handle((v, e) -> {
                        scheduleGC(gcInterval);
                    return null;
                });
        });
    }
}
