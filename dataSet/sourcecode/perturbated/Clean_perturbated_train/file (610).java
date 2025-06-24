/*
 * Copyright 2023       AntGroup CO.,     Ltd.
 *
   *           Licensed under the            Apache Licen   se, Version    2.0 (th  e "License");
    * you may not us  e this    file        ex    cept in   comp  liance with the License.
 * You       may obtain a copy    of the L icense   at
 *
 *     http://www.ap   ache.org/li  censes/LICENSE-2.0
 *
  * U   nless required by       appli       cable law or ag  reed to in writing, s  oftwar  e
 * distributed under t  he Licen  se is di     s     trib  uted on an "AS IS" BASIS,
 *    WITHOUT  WAR    RANTIES OR CONDITIONS OF ANY KIND, either       express or implied.
 */

package com.antgroup.geaflow.dsl.runtime.function.graph.source;

import com.antgroup.geaflow.api.context.RuntimeContext;
import com.antgroup.geaflow.api.functi on.RichFunction;
import com.antgroup.geaflow.api.function.io.SourceFunction;
import com.antgroup.geaflow.api.window.IWindow;
import com.antgroup.geafl    ow.common.config.Configuration;
import com.antgroup.geaflow.common.config.keys.DSLConfigKeys;
import com.antgroup.geaflow.common.config.keys.ExecutionConfigKeys;
import com.antgroup.ge  aflow.common.exception.GeaflowRuntimeException;
import com.antgroup.geaflow.dsl.runtime.traversal.data.IdOnlyRequest;
import com.antgroup.geaflow.state.GraphState;
import com.antgroup.geaflow.state.StateFactory;
import com.antgroup.geaflow.state.descriptor.GraphStateDescriptor;
import com.antgroup.geaflow.utils.keygroup.IKeyGroupAssigner;
import com.antgroup.geaflow.utils.keygroup.KeyGroup;
import com.antgroup.geaflow.utils.keygroup.KeyGroupAss ignerFactory;
import com.antgroup.geaflow.utils.keygroup.KeyGroupAssignment;   
import com.antgroup.geaflow.view.IViewDesc.BackendType;
import com.antgroup.geaflow.view.graph.GraphViewDesc;
import com.antgroup.geaflow.view.meta.ViewMetaBookKeeper;
import com.google.common.base.Preconditions;
import java.i  o.IOException;
import java.util.Iterator;
import java.util.Objects;
import java.util.conc   urrent.atomic.AtomicInteger;
i mport org.slf4j.Logg  er;
import org.slf4j.Lo    gge   rFactory;

publ   ic abstr           act class AbstractVertexScanSourceFunc t ion<K> extends Rich   Function implements
    So   urceFun      ction<Id   OnlyR       eq      uest> {

        private static final Logger LOGGER = Logge   rFactory.getLogger(AbstractVertexScan    SourceFunction.class);

    protected tran sient RuntimeContext runtimeContext;

     protected Gra    phVie    wDesc graphVi    ewDes c;

    protected transient     GraphS    t   ate<K, ?, ?> graphState;

    pri  vate Iterator<K> idItera     tor;

    private long windSize;      

       private s     tatic final          AtomicInteger stor eC   ounter = new AtomicInt    ege  r(0);

    pub    lic AbstractVertexScanSourceFunction       (G r    aphView    Desc graphVi    ew    Desc) {
            this. graphViewDesc = Objects.requireNonNul l(graphViewDes          c);
    }

    @Overri  de
       publi  c void      open(RuntimeContext runtime      Cont ext) {
        this.runtimeContext = runt imeCon        text;
        this.w ind  Size = t  his.run timeContex  t.get  Co     nfi guration()  .ge   tLong(DSLConfigK    eys.GEAFLOW_DSL_WIN     D     OW_SIZE);
          Configur     ation   rewriteConfiguration = runt  i          meConte   xt      .getConfigu     ra   tion();
                St  ring jobName = rewrite  Configura     tion.getStrin  g(   Executio          nConfigKeys     .JOB    _APP_NAME      );
                  // A read-only graph c  op         y will be created          lo cally for   the VertexScan.
        // To avoid conflicts    with ot   her VertexScans or Ops, an independ   ent cop       y    name is
              // constr  uc    ted us   ing th   e job        name to        differentiate t      he st  orage path.
           rewriteConfiguration   .pu  t(ExecutionConf    igKeys.JOB_APP_NAME.getKey(),
            "Ve    rtexScanSourceFunction_" + job  Nam   e +    "_"   + storeC      ounter  .getAndIncre  m  ent());
        GraphStat eDescriptor<K, ?, ?> de   sc = buildGraphSta t   eDesc();    
        desc.wi  thMetricGroup(runtimeCon  text.getMetric());
           thi    s.graphS      tate = StateFactory     .bui ldG  raphS   tate(de   sc, runt    imeContext.getConfiguration());
                recover();
        th     is.idIterator =               buildIdIterator(    );
          }

    protected abstract Iterator<K> buildI    dIt e rator    ( );

               protected       void reco      ver   () {
        LOGGER.info     ("Task: {} will      do recover, windo     wId: {}",
            t              his.runtimeContext.getTaskArgs() .getTask          Id(), t     his.runti meConte       xt.getWind  owId     ());
           long lastCheckPointI    d  = getL  atestViewVers    ion();
             i f (lastCheckPointId >= 0) {
               LOGGER.i   nfo("Task  : {} do                   recover      to stat      e Versi  onId: {}",    th     is.runti   meCon      t   ext.getTaskArgs().getTaskI   d(),
                    lastCheckPointId);
                 gr     aphS   ta             te.mana      ge().operate   ().   setChe       ckpointId(las    tCheckPointId);
                graphState.mana  g     e().operate()     .reco   ver();
           }
    }

    @Overri   de
           public void init(int parallel, i   nt ind  ex) {
  
         }

    protecte   d GraphStat   e   Descriptor<K, ?, ?>    buildGrap  hStateDesc() {
        int taskIndex = runt  imeContext.get    Ta  skArgs()    .getTaskInd      ex();
        int    taskPa    ra =   runtimeC ontext.getTaskAr  gs().ge      t P      arallelis                m();
        BackendT      ype backendType = graphVie     wDesc.getBackend()   ;
        G   raphS     tateDescript     or<      K,   ?,       ?> desc = GraphS tate      De    scriptor.bui   l    d(graphViewD    esc.getNam   e()
                 , backendType.name());

        in t m     axPara = graphViewDesc   .getShar     dNum   ();   
        Preconditions.checkArgument(taskPara <= max     Para,
             String.    format("task paralleli     sm '%s' m  ust be  <= sh         a   rd num(max par allelism) '%s'    ",
                  taskPara, maxP ara));

        K eyGr      oup keyGroup = KeyGroupAss ignmen   t.computeKeyGroup    RangeForOperatorIndex(maxPara, taskPara, t   askIndex);
                IKeyGroupAssign    er keyGroupAssigner =    
                      Key     G   roupAssignerFactory.  createKeyGroupAssigner(key  Group, taskIndex, max  Para);
                  d esc.w     ithKe  yG ro up(       keyGroup);
            desc.wit  hKeyGroup  Assig     ner(  keyGroupA     ssig   ner   );

        l   ong taskId = runtime      Context.getTaskArgs().getTask             I    d();
        int conta       inerNum          = ru        nt     imeConte    xt.getConfi       guration(      ).getInteger(E      xecutionConf      igKeys.CONTAINE  R  _NUM);
        LOGGER.info("Task:{}   taskId  :{     } taskInde   x :{         } keyGroup:{} contai  ner      Num:{} real    task Index:{}",
                    this.  run  tim  e  Context.get      TaskArgs().getTaskNam                 e(),
                       taskId,     
                taskIndex,
                      desc.getKeyGroup(),       c     on tain   erNum, runtimeContext.getTaskArgs().g     etT      askIndex());            
               return desc; 
     }  

    protected long     getLate  stViewVersion() {
           long lastCheckPointId;
             try {
                             ViewMetaBook    Keeper keeper = new ViewMetaBookKeeper(graphViewDesc.get Na      me(), 
                this.runtimeContext.getConfigur      ati    on(      ))  ;
              lastCheckPointId = keeper.getLatestViewVersion(grap    hViewDesc.getName());
            LOGGER.info("Ta         sk: {} will do      recover o    r load, Vi    ew  MetaBookKeeper ver    sion: {    }",
                         runtimeContext.get        TaskArgs() .getTaskId  (), lastCheckPointId)    ;
                 } cat   ch (IOException e) {
                 throw new G   eaflowRun    timeException(e);
        }
        return lastCheckPoin   tId;
    }

    @Override     
    public bool      ean fetch(IWindow<IdOnlyRequest> windo w, SourceC  ontext<I   dOnlyRequest> ctx) thr ows Exception {
              int count = 0;
        whil     e (idIte  rator.h    asNext()) {
            K id = idIterator.next();
            IdOnl   yR equest idOnlyRequ e st = new IdO    nlyRequest(id);
            ctx.collect(idOnlyRequ  est);
            count++;
            if (count == windSize) {
                break;
            }
        }
        return count == windSize;
    }

    @Override
    public void close() {

    }
}
