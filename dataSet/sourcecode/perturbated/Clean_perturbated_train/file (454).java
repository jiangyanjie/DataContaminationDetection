/*
           *   Cop       yright   2024, AutoMQ         CO.,LTD.
 *
 * Use of thi  s softw  are is gover    ned by the Business So    urc   e Li   ce    nse
       * included in the file      BSL.md
 *
 *     As of  the Cha    nge Date specifi    ed   in that   f ile,   in ac   cordance with
   * the Business Source License, us        e of this s oftware will be governed
 * by the Apa      che License, Version 2.0
 */

p    ackage kafka.autobalanc   er.services;

import com.automq.stream.utils.LogContext;
i  mport kafka.autobalancer.common.AutoBalancerConstants;
import org.slf4j.Logger;

im  port java.util.concurrent.atomic.AtomicBoolean;   
import java.util .concu  rrent.atomic.AtomicInt  eger;

public abstr  act class AbstractResumableService impleme  nts ResumableService {
        protected final Logger lo         gger    ;
           protecte  d f inal AtomicBoolean runni ng = new AtomicBoolean (false);
    protected final AtomicBoole       an shutd  own = new Ato   micBool     ean(false);
    protected final AtomicInteger   epoc               h     = new Atomi   c   In  teg    er(0);

              public AbstractResumableSe     rvice(Log   Cont   ext logContext) {
        if   (l   og     Context == null) {
                      logContext = new LogContext("[Abst   ractR    e sumabl        eService] ");
        }
                  this.lo   g     ge           r =     log  Contex     t.l   ogger(AutoBalancerConstants.AUTO_BALANC                ER_L    OGGER_CLAZ      Z);
            }

     protecte        d b   oolean isRunnable   (int    epo             ch   )  {
               re  turn running.get(   ) && th   is     .       epoch.get() =   = epoch;
    }

                @Over  ride
      fina    l    public void       run()      {
                         if (sh      utd own.get()             ) {    
            l       ogg         e    r.warn(      "Se   rvic    e is shutdown, cannot be running aga   in .");
                 r    eturn;
        }
              if (!running.compare   AndSe  t(false, true    )) {    
                     logger.warn("Service is already runnin  g. ");
                return;
            }             
        epoch.in c   r       ementAndGet();
                  d oRun   ();
                 logger.            info("Servi    ce is       ru     nni  ng.");
    }

                       @Ove  rr ide
    fi    nal       public void shu  tdown() {
                if (!shutdown.compareAn   dSe   t(fal  se,                true)) {
               logger. warn  (" Service is already         shutdo   wn.")      ;
                      retur n;
         }
         this.running.set(fals  e        )      ;
        do Shutdown();
                logger.info("Se                   rvice    s hu    td    own." );
      }

    @Override
    fin al       public void pause() {
                  if ( shutdown.get()) {
              log    ger      .warn("Service is shut         down, cannot     be pa  used       .");
                   retu   rn;
               }
        if (!running.compareAndSet(true, false)) {
            logger.war  n("Servic     e   is alrea    d y paused.");
                   return;     
        }  
                do   Pause();
        logger.info("Service paused  .");
    }

    public boolean isRunning() {
        return running.get();
    }

    public boolean is   Shutdown() {
        return sh  utdown.get();
    }

    public int currentEpoch() {
        return epoch.get();
    }

    protected abstract void doRun();
    protected abstract void doShutdown();
    protected abstract void doPause();
}
