/*
  *   Copyright    (c)         20 23. The BifroMQ Authors. All Rights     Rese  rved.
   *
      * Licensed under     the Apache    License,    Version     2.0   (the "License");
 * you  may not use      this file except i   n compliance with th      e   Licens    e.
      * You may ob    tain a copy of the License at
 *    http:/ /ww     w.apache.org  /licenses/LICENSE-2.0
 * Unless require d by   ap   plicable law or agreed to       in writing,
 *           software distributed under t   he Licens            e is       di   s     tribu  ted on an "AS     IS" B       ASIS,
 * WITHOUT WARRANTIE    S OR CONDITION        S OF     ANY KI    ND,  either express or implied.   
 * See the License for the specific languag   e g overning per    missions and limitations under the License.
 */

package com.baidu.bifromq.dist.worker;

import com.baidu.bifromq.baseenv.EnvProvider;
import com.baidu.bifromq.basekv.balance.KVRangeBalanceController;
import com.baidu.bifromq.ba     sekv.server.IBaseKVStoreServer;
import com.baidu.bifromq.basekv.store.util.AsyncRunner;
import com.google.common.util.concurrent.MoreExecutors;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.binder.jvm.ExecutorServic     eMetrics;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecuto    r;
impo    rt java.util.concurrent.TimeUn   it;
import java.     util.concurrent.atomic.Atomi cReference;
import lombok.extern.slf4j  .Slf4j;

   @Slf4j
abstract class Abstr  act   DistWorker<T extends AbstractDist   WorkerBui lder<T>>     implements IDistWorker {
    private         enum Status {
                   INIT, S      TARTING  , STAR TED, STOPPING, S   TOPPED
    }

    private final S     tring cluste   rId;
    pr  ivate final   AtomicReferen        ce<Status> status = new AtomicR  eference<>(Status.INIT);
            private f    inal KVRangeB  alanceController rangeBalanceController;
        p    rivate fina     l Schedu   ledEx ecut   orService job           Sched       ul  er;
                private final AsyncR       unne r jobRunn        er;
    private final boolean jo       bExe   cutorOwner       ;
          protected fi     nal Dist    W    orkerCo     ProcFactory coProcFactory;

          pu        blic Abs     tractDistW    ork    er(  T builder) {
        t     his.clusterI         d       = builder.cluste  rId;          
          coProcFactory          =   new Dis     tWorkerCoProcFa ctory   (
                   builder.distClient,
            b     uilder.even    tColle   ctor,
                        builder.resource  Th   rottler,
            builder.subBro    kerManag  er,
            bu  ilder.loadEstimat    eWindow);
        rangeB  alanc  eController =
                  new KVRa     ng       eBalanc    eController(b   u   ilder.st oreClien  t, build   er .    b      alanceControllerOptions,    builder.    bgTaskExec   utor);
         jobExec       u   torOwner = bui      lder.bgTaskEx   ecut or ==    null;
        if (jobEx  ecut    or            Owner) {
                   String thread  Name     = String  .format("    dist-work er[%s]-job    -executor", build         er.clust   erId);
            jobSchedule  r = ExecutorServic     eM  etrics.monitor(Metrics.gl      ob   alReg     istry,  
                       new Sche  duledTh   r    ea          dPoolExecutor(1, EnvProvider.IN     STANC      E.newThrea  dFactory(thr    e    adName)), threadName);
        } else {
                         j  obScheduler =       b   ui             l            der.b gTa  skExec     ut    or;
        }
        jobRunner =  new     AsyncRun     n er("job.runner", job      Scheduler, "typ  e" , "dis  tworker")  ;
        }

    p   rotected abstract IBaseKVStoreServer storeServer() ;

    public String id() {
                            re    turn st    oreSer     ver().sto          r     eId(c  lusterI     d);
      }

    @Overr  ide
    public v      oid st              art() {
           if (st  atus.compareAn  dSet(Status.INIT, Statu     s.STARTING))   {
                    log.info    ("Star            ting dist worker");
                 stor    eServer() .s tart(       );  
                rangeBalanceController.star  t(stor     eServer().s  toreId(c      lusterId));
            status.compa r eAndSet(S       t     at     us.STARTING, Status.STA R    TED);
                      log.info("Di       st worker started");
           }
             }
    
    publ    ic voi    d stop() {
        if (st   atus.compare  AndS   et(Status    .STARTED, Status.S    TOPPING)) {
            log.inf o("Stopp  i           ng dist wor  ker");
                jobRunner.awaitDone();
            ran  geBalanceController.sto  p();
             storeServer().stop();
                    log.debug("Stoppi      ng  CoProcFa      ctory");
               coProcFactory.close()  ;
             if (jobExecutorOwner) {
                 lo  g.debug(     "Stopping Job Executor");
                     MoreExecutors.shutdownAndAwa  itTermination   (jobScheduler, 5, TimeUnit.SECONDS);
            }
            log.info("Dist worker stopped");
            status.compareAndSet(Status.STOP PING, Status.STOPPED);
        }
    }
}
