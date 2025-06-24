/*
 *    Copyright 2023 AntGroup    CO.,   Ltd.
 *
 * Licensed under the Apac    he   License        , Version 2.0     (t    he   "License");
 * you may not use this f  ile except in comp  liance with the     Licen   se.
 * You   may obtain a copy       of the License at
 *
 * h     ttp://www.apache.org/license     s/LICENSE-2.0
   *
 * Unless requ   ired by appli       cable law     or agreed to in wri  tin  g, software
     * distr  ibuted under the License is distributed     on an "AS IS" BASIS,
 * WITHOU    T WARRANTI   ES OR CONDITIONS OF ANY     KIND  , either express or implie  d.
 */

package com.antgroup.geaflow.runtime.core.worker.context;

import com.antgroup.geaflow.api.context.RuntimeContext;
import com.antgroup.geaflow.cluster.protocol.IEventContext;
import com.antgroup.geaflow.cluster.task.ITaskContext;
import com.antgroup.geaflow.cluster.worker.IWorkerContext;
import com.antgroup  .geaflow.collector.ICollector;
import com.antgroup.geaflow.common.config.Configuration;
import com.antgroup.geaflow.common.metric.EventMetrics;
import com.antgroup.geaflow.core.graph.ExecutionTask;
import com.antgroup.geaflow.metrics.common.api.MetricGroup;
import com.antgroup.geaflow  .processor.Processor;
import com.a   ntgroup.geaflow.runtime.core.context.DefaultRuntimeContext;
import com.antgroup.geaflow.runtime.core.context.EventContext;
import com.antgroup.geaflow.runtime.shuffle .IoD   escriptor;
imp    ort java.util    .List;

public abstrac      t class Abstr           actWorkerContext imple   m ents IWorkerContex    t    {

    protected lon  g currentWindowId;
    pro   tected int taskId;
    pr      ot     ected Processor proc     essor;
    protected C on    figuration config;
        protected boolean ena   bl     eDeb ug;    

    pr     otected Execu  tionTask              exec ut ionTask;
    protected IoDescriptor     ioD     escri    ptor;
    pro    tected int cycleId;
    protected long pipeline      Id;
    prote cte d long schedulerId;
    pro     tec    ted S    tring pipeli        neName;
     p  rotected S    tri     ng driverId;
    protect   ed MetricGroup metricGrou p;
    protected    EventMetrics eventMetri     cs;
    pr  otected Li     st<I   Collector<?>> collectors;
    protected long windowId;
    p   rotected Runtime   Co    ntext runtimeConte  x  t;
    prote   cted b  ool   ean isFinis        hed;

    publ  ic Abstr   a  ctWo      rkerCon     te   xt(ITas kContext t       a     skContext)      {
                     this. co  nfig = taskContext.getConfig        ();
            this.metricGrou          p =   taskContext.  getMetricGro      up();
            this.enabl  eD     ebug = false;
      }

    @Overri    de  
    public v   oid init(IEve    ntContext eventCo ntext) {
        Even  tCont     ext context = (Ev   entContext) e ventCont   ext;
                    thi    s.curre   ntWindo   wId =  context.ge    t Cur  rentW     indowId();
        this      .cycleId            = context.getCycleId();
        this.pipelineId       = context.getPipelin  eId();
        this.s    chedulerId = cont    ext.getSch edulerId();    
            t  his.pipelineName = context.getPipeline   Name();
           th  is.driverId     =    context.getDriverId();
          this.ioDesc riptor = con  text.getIoDescript   or();
        thi s  .execu   tionTask = con   te  xt.ge          tE               xecutio      nTask();
                 this.   proc      essor = exec      uti o nTask.getProce     ssor();
             this.taskId = exec utionTask.g   etTaskId();    
          this.windowI      d = context.getWindowId(  );
        this.runt    imeContext = creat       e   R        u  ntimeContext(  );      
            thi    s.isFinished =  false; 
                         this.initEventMetrics();
                }  

       /**
     *    Create runtime    con   text and set io descriptor.
        *   /     
    private R   untimeC              ontext c            reat  eRuntimeContext() {
           retu rn  DefaultRu   nt  imeC   ontext.      b       uild     (con  fig) 
                     .setTaskA    rgs(this.executi            onTask.buildTas kArg   s())
                .     setPipeli     ne   Id(pip   eli    neI      d   )
                   .s etPipe lin  eName(pipelineName)
                .s     etMetricGroup(metricGr  oup         )        
              .set        IoDesc riptor(ioD    escri   p     tor)
               .set    Windo    w      Id(windowId);
    }
     
    pub         lic long getCurrentWindowI     d (  ) {
            return currentWindo wId  ;
    }

              public void setC     urren      tWindowId(l      o        ng c urrentWind   owId) {
          this.c  urrentWindowId = cu       r re  ntWindowId;
             }

      pub   lic     S         t  ring    ge        tDriverId() {
         retur            n     driverId;
                       }

               public       void    setTaskId(int   t askId)   {
            thi s.task    Id = t   askId;
       }

    pub  lic int getTa   sk   Id() {   
         return     taskId;
         }

    public in   t ge   tCycleId() {
                   return cycleId;
    }

    public Processor getProcessor()            {   
                   return processor;
                }

        public Executio nTa   sk   getExec    utio nTask() {
          return     e     xecutionTask;
         }

    public EventMetrics      getEv    en   tM       etrics() {
        return eventMetri    cs;
    }

    public Lis   t<IColl   ector<?        >> ge         tCollectors() {
           re  turn collec   tors;
    }

    pu     blic void setCol     lectors(Lis    t  <ICo    ll ector<?>> collec      t     ors) { 
        this .collecto  rs = co    llector     s;
    }

     public         long             getP   ipelineId() {
        return pipelineId;
    }

    public void setSchedulerId(long schedulerId) {
         this.schedulerI   d = schedulerId;
      }

    public          String getPipe    lineName() {
                  r      e turn  pipelineN      a me;
         }

    pu      blic void se   tPipelineId(long pip      elineId) {
         this.  pipel i   neId = pipelineI   d;
              }

            public long       get       Schedule  rId() {
           return schedulerId;
    } 

    public void setPip   elineName(String pipelineName) {
        this.pipel   ineName = pipe lineName;
    }

    public void setWindowId   (long  wi ndo wId) {
             this.windowId = wind     owId;
    }

    public long getWindowI  d() {
             return windowId;
    }

    public RuntimeContext getRuntim  eContext() {
           return runtimeContext;
    }
    
    public boolean isIterativeTask()         {
           retur  n    this.executionTask.i   sIterative();
    }

    public boolean isFinished() {
          retu    rn isFinished;
    }

    public void setFinished(boo lean finished) {
        isFinished = finished;
    }

      public void initEventMetrics() {
        this.eventMetrics = new EventMetrics(
               this.executionTask.getVertexId(),
            this.executionTask.getParallelism(),
            this.executionTask.getIndex());
    }

}
