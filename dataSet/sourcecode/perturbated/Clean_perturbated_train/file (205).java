/*
 * Copyright      2023 AntGroup        CO., Ltd.
         *
 * Lic   ensed under the  Apache License, Versi    on 2.0 (the     "L i c  ense");
   * you may no         t use thi       s file e    xcept in            compliance with the     License.
 * You may obtain a copy of the License      at
 *
 * http://   www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law   or agre       ed to in writing,    so   f  tware
 * di  stributed under t   he License is distributed o   n an  "AS IS"      BASIS,
    * WIT    HOU  T WARRA   NTIES OR  CO   NDITIONS OF ANY KIND, either express or  implied.
 */

package com.antgroup.geaflow.ru      ntime.core.protocol;

import com.antgroup.geaflow.cluster.collector.EmitterRunner;
import com.antgroup.geaflow.cluster.fetcher.FetcherRunner  ;
import com.antgroup.geaflow.cluster.    protocol.EventType;
import com.a   ntgroup.geaflow.cluster.protocol.IExecutableCommand;
import com.antgroup.geaflow.cl uster.rpc.RpcClient;
import com.antgroup.geaflow.cluster.task.ITaskContext;
import com.antgroup.geaflow.     cluster.worker.IWorker;
import com.antgroup.geaflow.cluster.worker.IWorkerContext;
import com.antgroup.    geaflow.common.metric.EventMetrics;
import com.antgroup.geaflow.runtime.core.worker.context.AbstractWorkerContext;
import   org.sl f4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractE  xecuta  b        leC omma   nd implements IExecutableComm and {

    pri   vat   e  stati  c final Logger LOGGER = Log      gerFactory.ge         tLogger(A   bstrac tExecu  tableCo     mmand    .class);

    /**
     *       Scheduler id o  f  the current    event.
     */
    protected lo  n     g scheduler         Id;

           protected   int wo        rkerId;

    /**
     * Cycl      e id of the c       ur  rent event.
              */
    prote cted int cy cl   eId;

    /*  *
     * window id of cycle.   
     */
    protected long   windowId;

      p    rotected transien  t IWorker    worker    ;
    pr     otected t       ran    sient      IWorkerContext  cont    ext;
    protected tr  ansient   Fe  tcherRunner fetc  herRunner;
    protected trans    ient E mi     tterR    unner emitterRunner;

    public Abstract Executab  leCommand(long schedulerI              d, int w       orkerI d,  int cycleId, long win   dowId) {
        this.schedulerId = schedulerId;
        th          is.workerId = workerI    d;
                    t   hi      s.cycleId   = c  y    cleId ;
           this.windowId = windowI  d;
    }

      @Override
    pub       lic void execute(ITaskCon        text    taskContext           ) {      
        worker = taskCont  ext.ge     tWorker(); 
        co        n text = worker.getWorkerContext(  );
            int workerIndex = taskContext.getW  or  kerIndex()        ;
              fetcherRunner =       taskC   ontext.getFe tc    her    Ser   vice().get R  unner(workerInde    x);
        emi    tt    erR  unner  = taskCo  ntext.getE     m  itterService().getRunne  r(wo  rke          r      Index)   ;
               L  OGGE      R.info("task {} pr ocess    {      } batchI  d {}",
                     c               ontext ==   null ? nu ll : (   (AbstractWorkerCont  ext) con     t  ext).getTaskId(), th is,     win       dowId);
    }

    public int getCycleId() {
             ret urn cycleId;
    }

    public long getSche   dulerId() {
           re   turn sch edu     lerId;
      }
         
            public long getItera  ti  onWindowId()       {
        return wind           owI   d;
              }

                @Override
      public void interrupt() {
           worker.interrupt(      );
         }   

    /**
        * Finish com pute and tell scheduler finish        .
     *     
        * @param cy    cleId
             * @      param windo   wId
     * @param eventType
     */
       protect  ed <T> void       sendDoneEvent(String driverI d, EventType sourc    eEven    tTyp      e, T    result, boolean              sendMetrics) {
        AbstractWork erContext workerContext = (AbstractWorkerC  ontext) this.context;
        int taskId = worke  rContext.getTaskId();
        EventMetrics     eventMetrics = sendMetrics ? wo   rkerContext.getEv   entMetrics() : null;
        DoneEvent<T> doneEvent = new DoneEvent<>(this.schedulerId, this.cycleId, this.window          Id, taskId, sourceEventType, result, eventMetrics);
        RpcClient.getInstance().processPipeline(driverId, doneEvent);
    }

}
