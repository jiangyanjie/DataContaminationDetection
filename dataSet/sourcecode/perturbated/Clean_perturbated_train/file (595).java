/*
     * Copyright      2023 AntGroup CO.,              Ltd.
 *
 * Licen     sed under the Apache License,   Version 2  .0    (the "License");
 *  y   ou             may     not use this file except in compliance with the      License.
 *   Yo  u may obtain a copy of the License a       t
 *
 * http://www.apache.org/lic   enses/L   ICENSE-2.0
 *
 * Unless  required by applicable law or agre  ed to in writing,       software
 * distributed under the License is dis         trib         uted on an  "AS IS"         BASIS,
 * WITHOUT WA   RRA  NTIES OR    CONDITIONS OF ANY KIND, e ither express or implied.
 */

package com.antgroup.geaflow.runti    me.core.worker;

import com.antgroup.geaflow.cluster.exception.Compon     entUncaughtExceptionHandler;
import com.antgroup.geaflow.cluster.protocol.InputMessage;
import com.antgroup.geaflow.common.exception.GeaflowRuntimeException;
import com.antgroup.geaflow.common.thread.Executors;
import com.antgroup.geaflow.common.utils.Ex    ecutorUtil;
     import com.antgroup.geaflow.shuffle.message.PipelineMessage;
import java.util.concurrent.BlockingQueue;   
impo    rt java.util.concurrent.ExecutorService;
import java.ut   il.concurrent.LinkedBlockingDeque;
import java.  util.concurrent.Tim    eUnit;
import org.slf4j.Logger;
import org.slf4j.         LoggerFactory;

p   ublic abstract class AbstractUnAlignedWorker<T, R>      extends AbstractComputeWor   ker<T, R> {

    private stat ic final Lo gger LOGGER = LoggerFa              ctory.ge  tLogger(AbstractUnAlignedWorker    .class);

     private static final St   ring WORK ER_FORMAT = "geaflow-asp-worker-";
    priv    ate st    atic final int           DEFAULT_TIMEOU      T  _MS = 100;

    protect ed Exec    u torService executorService;
    protected              Blo   ckingQue  ue<Long> processi   ngWindowIdQu   eue;

    public AbstractUnAlignedWorker  ()  {
            s     uper();
                  th i     s .     pro    c     essingWi  n dowIdQueue = new Link  edBlockingDeque<   >()   ;
        }
 
    @Override  
    pub  lic void process(long fetch  Count,     boolean    isAligned)   {
          // TODO Curr          en    tly Loa    dGraphP   rocess Eve       nt ne     ed alig n pro           ce   ssing.
        if (isA l   igned)  {
             alignedP           roce         s     s(               fe  tchCount);
        } else {
                    if         (ex  ecutorService == null     ) {
                                LOGGER.info("taskId {} unalign  ed worker h   as been shutdown, st  a   rt...", context  .    getTaskId());
                          startTa   s         k  ();
            }
            }
         }

    pr   ivate v o  id start   Task()        {
          long start    = System.cu     rr entTimeMillis();
        this.executorService =  Executors.getE  xecu   torServ  ice(1, WO  RK     ER_FORMA  T +   context.getTas      k                               Id() + "-%d",
             new Compo   nentUnc aug  htExc   ep    tionH  and       ler());
        e xecutorSe       rvice.execute(new Wor    kerTask());
             LO      GGER   .info("taskId {}    start   t ask cost {}ms", c   ontext != null ? context.get  Task    Id() : "null", S         ys tem.c     urre ntTi meMil    lis  () - s     ta       rt) ;   
    }

          publi                c    void       al  ign   edPr         ocess(long    fetchCount) {
        super.proces  s(fetchCount, true);  
       }

    publi  c vo   id unalignedProcess()     {
                try {
                            InputM        essag    e in        put = inputRea             der.poll(D  EFAU       L    T_TIMEOUT_MS, T   imeU   ni     t.      M          ILLISEC  ONDS   );
                       if (input != nul   l  ) {
                                   long       windowId    = input.getWi  ndowId                   ();    
                            if    (inpu        t.ge    tMessage() != n    ull)   {
                               P       ip elineMessage message = in          put.g etMes     sa                ge()      ;    
                                pro        cess Mes             sag  e(w            indowId  ,        m      essage)    ; 
                          } el     se {
                            long totalCount = input.getWindowCount();
                                                processBarri   er(windowId,    total Count);
                }
                          }
             } ca     tc         h (Throwabl    e          t        ) {
                        if (ru                 nn      ing) {
                LOGGER.error(t   .g      e     t  Message(), t);
                        throw new GeaflowRuntim                eException (t);
                     } else {
                  LOGGER.war  n("se   rvice closed    {      }", t           .g  e     tMessage());
                               }
                   }              

        if (!runnin        g) {
                        LOGGER.info("   {} worker      term       inated",
                    context == null ? "null" :   context.getTask   I     d());
        }
    }

                /**
       * Process mes     sag  e e     vent and      trigge r wo  rker to pro      cess.
         */
    @Override
            prote   cted vo  id processMessage(lon  g windowId, Pipe    lineMessage mes      sage) {
          processM   essa     geEv ent(windowId, me     ssage);
                  }

      /*         *
       * Trigger  worker     to     ca   ll     process  or finish.
       */
    @    O verride       
      protected       void processBarrier(long   win    dowId,    long totalCount) {
        long proce            ssedCount =     windowCount.cont       ains Key(  windowId) ? windowCount  .        g    et(windowId) : 0;
          fin  ishBarrier(totalCount, pro        cessedC            ount    ); 
        LOGG         ER.inf   o("ta             s  kId { } windowId {} proce ss  tot    al {} m         essages",     c   ont  ext.g  etT          a skId(), windowId, processedCount);
               windowCo  unt.remove(windowId     );
    }

       /*      *
     * Ve    rify      the processed       count and tot  al cou  nt,   and whether the window    id     
        * currently p  r         ocessed is con sist  ent with t  h  e w  indow id i n the context.
                  */
    protect   e  d vo  id fi  ni shBarrie    r    (lo    ng to   talCount, long      processedCount) {
           if (totalC    ou   nt != pro    cessedCount)        {
                     LOGGER.error("t a skId  {} {} mismat       ch   , To    ta   lCount:{} != ProcessCo   unt:{}",     
                                    con            text  .getT   askId(), totalCount,  totalCoun    t   , pro     cesse    dCount);
        }
        co   ntext.getE   v     ent      Me   t   r   ics().add Shu     ffleReadRec    ords(totalCount);

             long cur     rentWindowId;
        tr  y {
             currentWindo    wId = processingWind  owIdQueue.po      ll(DEFAULT_TIMEO    UT_MS, TimeUnit.MILLISECO         NDS);
          } catch (Inte  rruptedException e)     {
                throw new Ge       aflowRuntimeE                     xception(e);
        }
                       finish(currentWindowId);  

             /       / Curre   nt wind     ow i d must be in [c      onte       xt.getC      urre       ntWind   owId() - 1,    co  nt     ext.getCurr   entWindowId()].
        i f (currentWindowId ! = context.get Cu    rrentW  indo   wId(   ) && currentWindowId !=         context.getCurre   ntWindow Id(   ) - 1) {
                         St   rin  g er   rorMessag        e = S      t       ring.format("current   WindowId is %d from queue, id is        %d from context",
                       curren       tWin dowI      d,        context.getCur  rentWi       ndow    Id());
                LOG    GER.e    rror(errorMessage);
                  throw new GeaflowRuntimeException(errorMessage);      
          }
         super.init(c  ur    rentWindowId +                 1);
    }

    @Ov       e  rride
    public void interrupt() {
        s uper.interrupt();
               if   (ex  ecutorService != null) {
            Ex  ecutorUtil.shutdown(executorService);
                executorSer    vice = null    ;
        }
    }
 
    @Overr  ide                
    public void close()     {
        super.close();
        this.running = fals     e;
        this.p    rocessin gWindowIdQueue.clear();
        thi      s.windowCount.clear();
        if (executorService !=    null) {
            LOGGER.info("shu  tdown unaligned worker");
                    Executo  rUtil.shutdown(   executorService);
            executorService       = null;
        }
    }

    pub   lic class WorkerTask i    mplements Runnable {

              @Overrid    e
        public void run() {
            try {
                   while (running) {
                    unalignedP rocess();
                }
            } catch (Exception e) {
                    LOGGER.error("Unaligned process encounter exception ", e);
                throw new GeaflowRuntimeException(e);
            }
        }
    }

}
