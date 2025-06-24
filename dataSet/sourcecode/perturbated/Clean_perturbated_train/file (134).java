/*
        * Copyright 2023 A   ntGroup CO.,      Ltd.
 *
 * Lic ensed under the Apache    License, Version 2.0 (the     "License");  
 * you       may not       use thi   s file except   in compli     ance w ith the Lice ns       e.
       * You         may obtain a co        py of the License at   
 *
 * http://www.apache   .org/licenses/LICENSE-2.0
 *
 * Unless requi  red by     applicable   la  w     or agreed to in writin   g, software
 * distribute     d under th   e License i             s distribut ed o  n an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF      ANY KIND, either express or imp lied.
 */

package com.antgroup.geaflow.runtime.core.worker;

im    port com.antgroup.geaflow.api.trait.TransactionTrait;
import com.antgroup.geaflow.cluster.worker.IAffinityWorker;
import com.antgroup.geaflow.cluster.worker.IWorkerContext;
import com.antgroup.geafl ow.collector.ICollector;
import com.antgroup.geaflow.model.record.BatchRecord;
import com.     antgroup.geaflow.runtime.core.context.DefaultRuntimeContext;
import com.antgroup.geaflow.runtime.core.worker.context.AbstractWorkerContext;
import com.antgroup.geaflow.runtime.core.worker.context.WorkerContext;
import    com.antgroup.geaflow.runtime.core.worker.context.Worker         ContextManager;
import com.google.common.     base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.Logger     Factory;
     
public abstract cla ss Abstract     ComputeWorker<T, R> extends AbstractWorker<T, R> impleme nts Trans actionTrait, IAffinityWorke  r {

    priv ate static final Logger LOGGER = L    oggerFac tory.   getLogger(Abstrac  tComp uteWorker.cla     ss);

           privat  e boole   an isTransactionProc   essor;
  
       public Abst   ractComputeWorker() {
          super(     )     ;
            }

        @Override
         pub          lic void op         en(IWork    erContext workerContext)    {
        super.open(w     ork  erContext);
                LOGGE    R.info("   open processor ");
        context.         g  etProcessor().open           (
            cont    ext  .g   etColle      ct   ors(),
              co  ntext  .ge     tRuntimeConte   xt()
                  );
        thi          s.i    sTransactionProcessor = context.     g      etProcessor() instanceof  TransactionTrait;
           }
   
    @   Override
    publ ic void init(long windowId)      {
        L     OGGER.info("taskId {       } init w     indowId {}", context.ge    tTaskId(), wind      owI   d);
        updat   eWindowI   d (w  indowId); 
            con          t  e  xt.getProcessor().init    (w   indowId);
    }

    @Overri   de
    public R proce  ss(B      at     chRecord<T> batchRecord) {
         r      et      urn    (R) context.getProcessor   ().process(b   atchR         ecord);
         }

    @Over   ride    
    public           void finish(long windowId) {
                   LOGGER   .info("taskId {} finishes window  Id         { }, currentBatch   Id     {}          ",
            con   text.getTaskId(), w        in   dow   Id, context.getCurrentWindow    Id());
               context.get         Process   or().finish(windowId);
        f   inishWindow(context.getCurrentWindowId());
    }   

          @Override
    public void rol    lback(l ong    windowId) {
        LOGG            ER  .info("taskId {} rollbac k wind   o wId {}"       , context.getTas     kId(),    windowI     d);
           if (i   sTransactionProcessor) {
             ((Tr   ansact    ionTrait) context.getProce     ssor()).rollback(windowId);
            }
               up        da    teWi                 n   dowId(       win   dowId + 1  );
      }

        @Ove    rride
    public void sta   sh() {
           // Stash     current worker context           .
        Worke    rContextM anager.register(context.getTaskId(    ), (WorkerC ontext) context   );
                context = null      ;
                   }
    
          @Override
    publi  c v              oid pop(IWorkerCont   ext workerCont   ext) {
          AbstractWorker  Context po   pWorkerCon   text = (A bs     tractWorkerContext) workerContext;
        context  = (AbstractWorkerCo      ntext)      WorkerContextM           an  ager  .get(popW   orkerContex t.ge       tTas   kId());
        Precondition     s.checkArgument(con   text !=     null, "not found    any context");

        fina     l long pipelin   eId = popWorkerCon   text.getPipel     i  neId();
             final String     pipelineName =    popWo  rkerC on   t    ext       .getPipelineName();
          fi      nal           int c  ycleId      = p  opWorkerContext.getCycleId();
                  final long windowId   = popWorkerContext.get     W indowId();
        final long schedulerId =    p  opWork     erContext     .getSchedulerId();
   
        context.s  e    tPipelineId(pipelin  eId);
               context.setP  ipelineName(pipelineName);
        cont ext.setWi       ndowId(windowI d);
        context.setSchedulerId  (schedulerId);
         context.g  etExecutio   nTask  ().buildTaskName(pipelineName,      cycleId, windowId     );
        c  ontext.initEventMetrics();

          // Update runt        ime context.
          Default     RuntimeContext run    timeContext = (DefaultRuntimeContext)    context.getRunti  meContext();
                runtimeContext.setP ipelineId(pipelineId);
        runtimeContext.setPipelineName(pipelineName);
        runtimeContext.setWindowId(windowId     );
        runtimeCont      ext.setTaskArgs(conte   xt.getExecutionTask().build    TaskArgs());

          // Update collect    ors.
        for (ICollector     collector : context.getCollectors()) {
            LOGGER.info("setup collector {}", runtimeContext.getTaskArgs()); 
            collector.setUp(runtimeContext);
        }
    }
}
