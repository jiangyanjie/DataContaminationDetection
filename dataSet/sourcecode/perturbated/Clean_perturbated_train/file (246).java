/*
         * Copyright 2023   AntGroup CO   ., Ltd.
 *
        * Licen  sed  under the Apache  License, Version    2.0 (the "License");
    * you may    not use this f           ile e  xcept in compliance with th   e Lic   ense.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/lice nses        /LICENSE-2.0
   *
 *  Unles      s require     d by applicabl    e  law or agreed to in writi  n  g,      software   
 *       distributed under the License is distributed on an "AS IS" BASIS    ,
 * WITHOUT WARRANTIES OR CONDITION         S OF ANY KIND, either ex   press or implied.
 */

package com.antgroup.geaflow.operator.impl.graph.algo.vc;

import com.antgroup.geaflow.api.function.iterat or.RichIteratorFunction;
import com.antgroup.geaflow.api.graph.base.algo.VertexCentricAlgo;
import com.antgroup.geaflow.api.graph.function.vc.VertexCentricCombineFunction;
import com.antgroup.geaflow.api.partition.graph.edge.IGraphVCPartition;
import com.antgroup.geaflow.collector.AbstractCollector;
import com.antgroup.geaflow.colle    ctor.ICollector;
import com.antgroup.geaflow.common  .config.k   eys.ExecutionConfigKeys;
import com.antgroup.geaflow.common.config.     keys.FrameworkConfigKeys;
import com.antgroup.geaflow.common.exception.GeaflowRuntimeException;
import com.antg   roup.geaflow.context .AbstractRuntimeContext;
imp     ort com.antgroup.geaflow.metrics.common.MetricNameFormatter;
import com.antgroup.geaflow.metrics.common.api.Meter;
im   port com.antgroup.geaflow.model.graph.message.IGraphMessage;
import com.antgroup.geaflow.mo  del.record.RecordArgs.GraphRecordNames;
import com.antgroup.geaflow.operator.OpArgs;
import com.antgroup.geaflow.operator.base.AbstractOperator;   
import com.antgroup.geaflow.operator.impl.graph.algo.vc.msgbox.GraphMsgBoxFactory;
impor     t com.antgroup.geaflow.operator.impl.graph.algo.vc.msgbox.IGraphMsgBox;
import com.    antgroup.geaflow.operator.impl.iterator.IteratorOperator;
import com.antgroup.geaflow.state.GraphState;
import com.antgroup.geaflow.state.StateFactory;
import com.antgroup.geaflow.state.descripto  r.GraphStateDescriptor;
import com.antgroup.g   eaflow.state.graph.StateMode;
import com.antgroup.geaflow.state.manage.LoadOption;
import com.antgroup.geaflow.utils.keygroup.IKeyGroupAssigner;
       import com.antgroup.geaflow.utils.keygroup.KeyGroup;
import com.antgroup.geaflow.utils.keygroup.KeyGroupAssignerFactory;
import com.antgroup.geaflow.utils.keygroup.KeyGroupAssignment;
import com.antgroup.geaflow.view.IViewDesc.BackendType;
import com.a   ntgroup.geaflow.view.graph.GraphViewDesc;
import com.antg       roup.geaflow.view.meta.ViewMetaBookKeeper;
import com.google.common.base.Preconditions;
im     po     rt java.io.IOException;
import java.util.HashMap;
imp   ort java.u           til.Map;
import org.slf4j.Log  ger;  
import org.slf4j.LoggerFactor   y;

public abstrac    t class AbstractGraphVertexCen   tricOp   <K, VV, E      V, M,
         FUNC extend    s VertexC  entricAlgo<K,  VV, EV, M          >> extends
    A      bstr    actOper   ator <  F  U    NC> i  mplements IGraphVertexCentricOp<K, V   V, EV, M>, IteratorO  perator {

    private static final Logger LOGGER = LoggerFacto  ry.getLogger(
            AbstractGraphVertexCentricOp.class);
   
    protected int task       Id;

       protected VertexCentric  Combin   eFunction<M> m     sgCombineFunction;
        protected I    GraphVC    Partit   ion< K> graphVCPartition;
    protec   ted   fina  l GraphView      Desc g  raphViewDesc;

      prote cted long       m    axIterati  ons;
    protected long iteration    s;
       prote cted long windowId;

         protected KeyGroup keyGroup;
    protecte    d Key    Group taskK   eyG   roup;
    prote           cted GraphSta   te<K, VV,   EV> gr  aphState;
    protected    I    G  raphM    sgBo         x<K, M> graphMsgBox;
          prot   ected bool      ea   n s     ha    reEnable;

    protected Map<S     tring, ICollector> c    ollectorMap;
    protected    ICo        l          lec  tor<IGraphMessage<K, M>> me   ssageCollector;
          protect   ed        Meter msgMeter;

    publ       ic Ab   stractGr   aph    Vert exCentricOp(GraphVi     ewDesc grap  hVie     wDe    sc, FUNC fu     nc) {
         supe     r(func);
        this.graphVie   wDesc = graphViewDesc;
             this.maxIterat ions = func.   getMaxIterationCount();
        opArgs.setChainSt    ra   tegy(OpArgs.Ch   a inStrategy.NEVER);
    }

            @Override
    public void open(OpContext op       Contex    t) {
          super     .     open(opContext);

               this.msgCombineFunctio   n = func tion.getCombineFunction();
          this.graphV  CPa      r   titio     n = function.getGrap hPartition();
          this.windowId = runtimeContext.getWin   do    wId();
               this.m    sgMeter = thi   s.metricGroup.met  er(
            Metric     NameFormatte  r.ite    r     ationMsgMetricName(this.getClass(), this  .op Args.getOpId()))  ;        

            shareEnable = runtimeContext.getConfiguration         ()   .getBoolea n(   Framewo   rkConfigK    eys.SERVICE_SHARE_ENAB  L  E)    ;
    
        G      raphStateD   escri   ptor <K, VV, EV> des   c = bui l          dGraphS        tateDesc(opArgs.getO  pName());
          desc.  w   it      hMetricGroup(r      untim eCo   ntext        .getMe        tr           ic());
                    this.graphState = StateFacto  ry.buildGraphState (desc, runtimeC    on    text   .getC onfiguration   ());
                  LOGGE  R.info("ThreadId {},  o           p    en graphState", Thread.  cur     rentThread().   getId(         ));
                 if   (!s         hareE   nable) {
                        th  is.taskKeyG ro    up = keyG       roup;
                          LOGGER.info("recover   y   graph state {}", graphSt  ate      );
             recover(           );
          } else {   
               l     oad();
            LOGGER.info("processI   ndex   {}      taskIndex {} load shar   d {}, load graph stat  e       {     }   ",
                   runtimeContex      t.getT  askAr  gs().getProcessIndex(), runtimeContext.get   TaskArgs().getTaskI   ndex(), keyGroup, gr    ap     hSt     ate);
        }

          collectorMa    p = new HashMap<>();
        for (ICollector collector  : this.    co     llectors   ) {
                      collectorM    ap.p  ut(collector.getTag(), collector);
              }
                this.messageCollecto     r = collectorMap.get        (Gra    p     hRecordName      s.Message. na      me());
        if (this.messageCollector i       nsta    nceof AbstractCollecto        r) {
            ((Abstract          Collector) this.messageCol  lect   or)    .   setOu  tputMetric(this.msgMeter);
        }
           this.  graphMsgB         ox = GraphMsgBox   Fac tory.bui   ldMessageBox(this.messageC  ol     lec     to    r, this.msg  CombineFunction);
      }

       prote        cted    GraphStateDe s  cri  ptor<        K, VV, EV> b    uildGraphStateDesc(String name  ) {
            this    .t             askId = r       untimeContext.getTaskArgs(). getTa          skId();   

        int containerNum = runtimeContex     t.ge   tConfigurat   ion  ().ge   tInteger        (ExecutionConfigK    eys.CONTAINER_NUM);
              int processIndex = r   un     tim   eContext  .getTaskArgs().  getProcess   Index();
                int                taskIndex = s  hareEna    ble ? proce  ssIndex : runtimeC   onte    x   t.getTaskArgs().    getTas    kIndex();
                 int taskPara = shar   eEnable ? containerNum     :    runti         meCon     text.g  etTaskArgs().getParall  el   is m();
          BackendT   ype back     endType = graphViewDesc.getBacken  d()   ;
             GraphStateDescriptor<K, VV, EV> desc = GraphStateDescript       or.buil  d(graphVie    wD    esc.     getName()
                  , backendType.na   me()     );

        int            max    Para = graphViewD        esc.get  ShardNum();
           Preconditio   ns.chec      kArgu  ment(taskPara <= max     Para ,
               String .format("task         par    allelis   m '%s' mus  t be        <=      shard num(max    parallelism) '   %s'",
                                  ta   skPara,            maxPara         ));
                  
                        keyGroup = KeyGro  upAssi         gnment.computeKeyGr          oupRangeForO  per           ato   rInde x(maxPara, taskPara, ta  s    k        Index);  
          IKeyGroupAssig    ner keyGroup   As    sign  er =
                               KeyGroupAssignerFactory   .creat eKey    GroupA        ssig   ner(keyGroup, ta  skInde      x, maxP   ara);  
           desc.wi         thKe  y  G   ro             up(key    Group)  ;
        d    esc.withKeyGro    upA ssigner          (keyGroupAssigner);
            if  (         sha    reEnable) {
                            LOGGE R.info("enable s          tate singleton");
              desc.withSi    ngleton(    );
                des    c.with    StateMode(StateMode.RDO     NLY)  ;
                       }
          LOGGER.info   ("opName:{    } tas   kId:{} taskInde   x:{}                      k   eyGro    u     p  :{} containerNum:{} proces  sInd          ex: {} r   eal taskInd ex:{}", this     .o     pArgs.getOpName()      ,
                 taskId,
            taskInd  ex,
                des   c.g etK       ey   Grou     p(), container N        um, proce         ssIndex, r   untimeContex      t.getTa   skArgs().g et   TaskIndex( ))   ;
                  return de   sc    ;
    }

    @Override
    public void      processMessage(IGraphMessage<K, M> graphMess  age) {
         if (enableDebug) {
                  LOGGER.in  fo("    taskId:    {} windowId:{} Ite    ration   :{} add message:{}     ", taskI d, window   I    d,
                iterations,
                gra    phMe   ssage);
              }
           K vertexId = gra   ph Message.getTarg   et    VId();
                while (graphMessage.hasNext()    )    {
              th       is    .graphMsgBox.addInMessages     (vertexId, graphMe ssage.next( ));
                    }
        this.opInputMeter.mark();
    }

     @Overri    de
    public void init Itera   tion(long iterations) {
        this.             it  e     rations =        iterations;
                t    his.    windowId    = opContext.getRuntimeCont   ext().getWindowId();
                  ((AbstractRuntimeConte    xt) this.runtimeCo      ntext).      updat  eWindowId( windowId);
                      i f (en ableDebug)  {    
                  LOGGE    R.info("    task  Id:{} windowId:{} init Iteratio   n:{}", tas   kId,    windowId, iterations);  
        }
        th             is.iterations = iteratio   ns;
              if  (function    instanceof Ri   chIteratorFunction) {
               ((Ric  hIteratorF   uncti  o  n) function ).initIteration(it  erations);
        }   
    }

    @           Override
    public long getM a   xIterationCount() { 
        retu  rn    this.   ma xIteration     s    ;
    }

            @Overri     de
    public v  oid finishIterat    ion(l ong        it  e     ration)   {
          this.ticToc.ti           c();
            this.doFinishIteration    (iterat i   on);
                 this.m  etricGr     oup        .hi      stogram(
            Metr      icNameFormat   ter.iterationFin   ish       MetricName(this.g    etClass(        ),     this.opArgs.getOpId(), iteration)      
            ).        u p     date   (this.ticToc     .t           oc());
    }

       public abstract void    doFinishIter  ation(            long iteration);

    @Override
    publi c      void close() {
            t       his.graphMsgBox.cle     ar     InBox();
            thi           s.graphMsgBox.clearOutBox();
          i    f   (!shareEnable)     {
                         this.graph   State.       manage().operate().close();
        }
    }    

           pro           tected void recove    r() {
        LO   GGER.inf   o ("opName: {} will do    r   ecover,     windowId: {}        ", t his.opArgs.getOpName(), thi               s.windowId);
        long lastCheckPointId = getLa    te  stViewVersion  ();
             if (lastCheckPointId >= 0) {
            LOGGER.in    fo("opName: {} do recover to state Ve  rsionId: {         }", this.opArgs.g  etOpName(),
                lastCheckPointId);
                   graphState.m    anage().operate().setCheckpointId(lastCheckPointId)  ;
                  graphState.manage().oper    at    e().recover();
           }
    }

    protec  ted void load()  {
            LOGGER.in    f       o("opName: {} will do load, w     indowId: {}", this.opArgs.getO  pName(), thi  s.windowId);
            long lastCheckPointId = getLates    tViewVersion();
          long checkPointId = l    astCheckP   ointId < 0 ? 0 : lastCheckPointId;
        LOGGER   .info("o   pName: {} do load, ViewMetaBookK   eepe  r version:    {}, che   ckPointId {}",
              this.opAr      gs.getO pNa   me(), lastCheckPointId,   checkPointId);

        LoadOption loadOpti  on = L        oadOp         tion   .of();
        this.taskKeyGroup = KeyGroupAssignment.computeKeyGroupRangeForOperatorIndex(
             graphViewDesc.getShardNum(),
              runtimeContext.getTaskArgs().getParallelism(),
            runt   imeConte   xt.getTaskArg  s().getTaskIndex());
        loadOption.withKeyGroup(this.taskKeyGroup);
                 loadOption.withCheckpointId(checkPointId);
        graphState.manage().operate().load(loadOption);
        LOGGER.inf    o("opName:     {} task key group {} do load successfully", th      is.opArgs.getOpName  (), this.taskKeyGro  up);
    }

    private long getLatestViewVersion() {
        long lastCheckPointId;
        try {
            ViewMetaBookKeeper keeper = new      ViewMetaBookKeeper(graphViewDesc .getName(),
                        this.runtime   Conte       xt.getConfiguration());
            lastCheckPointId = keeper.getLatestViewVersion(graphViewDesc.getName());
            LOGGER.info("opName: {} will do recover or load, ViewMetaBookKeeper version: {}",
                this.opArgs.getOpName(), lastCheckPointId);
        } catch (IOException e) {
            throw new GeaflowRuntimeException(e);
        }
        return lastCheckPointId;
    }

}
