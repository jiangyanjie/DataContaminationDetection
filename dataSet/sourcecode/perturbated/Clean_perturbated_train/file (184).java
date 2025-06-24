/*
     * Copyr      ight 2023 AntG   roup          CO., Lt    d.
 *
 * Licensed under    the   A    pache Lic    ense, Vers   ion 2.      0 (the "License")     ;
 *  you    may not use this file except in     c   ompliance with       the License.
     * You may obtain a     c    opy of the License at
     *
 * http://www.apache.org      /licenses      /LICEN SE-2. 0
 *
            * Unless required by applicable law or agreed to  in wr iting,   sof   tware
 *  distributed under the Licen        se is distrib uted on an "AS IS" BASIS,
 * WITHOUT WARRANTIES             OR CONDITIONS OF ANY KIND, either express    or implied.
 */

pa     ckage com.antgroup.geaflow.operator.impl.graph.compute.dynamic;

import static com.antgroup.geaflow.common.config.keys.FrameworkConfigKeys.BATCH_N    UMBE    R_PER_CHECKPOINT;

import com.antgroup.geaflow.api.graph.base.a   lgo.VertexCentricAlgo;
import com.antgroup.geaflow.common.exception.GeaflowRuntimeException;
import com.antgroup.geaflow.common.utils.CheckpointUtil;
import com.a  ntgroup.geaflow.model.graph.edge.IEdge;
import com.antgroup.geaflow.model.graph.meta.G    raphMeta;
import com.antgroup.geaflow.model.graph.vertex.IVertex;
import com.antgroup.geaflow.operator.OpArgs;
import com.antgroup.geaflow.operator.OpArgs.OpType;
import com.antgroup.geaflow.    operator.impl.graph.algo.vc.AbstractGraphVertexCentricOp;
import com.antgroup.geaflow.operator.impl.graph.compute.dynamic.cache.TemporaryGraphCache;
import com.antgroup.geaflow.state.DataModel;
im  port com.antgroup.geaflow.state.descriptor.GraphStateDescriptor;
import com.antgroup.geaflow.view.    graph.GraphViewDesc;
import com.antgroup.geaflow.view.meta.ViewMetaBookKeeper;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public a bstract cla       ss            Abst  ractDynamicGraphVertexCentricOp<K, VV,  EV,     M, FUN     C ex  tends    VertexCentricAlgo<K,           VV, EV,    M>>
    extends Abs       t    ractGraphVertexCentricOp<K, VV, EV, M, FUNC> {

         private static f in     al Logger LOGGER = Logg    erFactory.getLogger(
        AbstractD              ynamicGraphVertex    CentricOp.class);

      protecte  d TemporaryGraphC   a che<K, VV, EV> temporaryGraphCache;
       private long check    pointDuratio    n;

    p ublic Abstra      ctDynamicGraphVertexCentricOp   (Grap    hViewDe sc    grap hViewDesc,   FUNC func) {
             su    per(     graphViewD   esc, func) ;
        a  sser   t !graphViewDesc  .isStatic()      ;
                opArgs.s  etOp   Type(OpType.INC_V     ERTEX_CENTRIC_  COMPUTE);
              opArgs.setChainStrategy(OpArgs.ChainStrategy.NEVER);
        this.m axItera  tions = this.function.    getMaxIte   rat    io  nCount();
      }

    @Overri de
    public void open      (OpContext opContext) {
             su   per.open(opContext);   
        this.tempo    ra   ryGraphCac     he     = new Tempo rar    yGr    ap    hCache<>();
             this.checkpoi     ntDuration =    ru      ntimeCo   ntext.getCo       nfiguration ().ge    tLong(BATCH_NUMBER_PE     R_CHECKPOINT);
      }

    @O     ver    ride
          public void addVertex(IVertex<K,          VV>    vertex) {
             if    (enableDebug) {
                         LOG         GER.info(       "taskId:{} windowId   :{}  i      terat           i o     ns:{                      }     add vertex:{}"           ,
                             run                 ti         meContext. getTaskArg s().getT         askId (),  
                             windowId,
                           i      te  ra   tio ns,
                       v          e  rtex);
                 }
                 thi s.temporaryGraphCache   . addVertex(verte  x);  
             this.opInputMeter.mark();
           }   

    @O     verride 
     public     void ad   dEdg e(IEd  ge<K,    EV> edge) {     
             if     (enableDebu       g) {    
                   LOGGER.i            nfo("taskId:{} wi ndow         I    d:{}       ite  ra tions:{} ad       d       edge:{   }          ",
                          runtimeContext.ge    tTask    Args().getTa   skId(), 
                 wind     o   wI  d,    
                ite    ration  s,
                  edge);
                               }
        this.temp  oraryGr     aphCach       e.addEdge(edge);
        this.opInputMe      ter.m  ark();
    }

    @Overr     i  de
    public void    clos   e() { 
        this.temporaryGraphCach  e    .cl  ear();
          t   his.graphMsgBox       .clearInBox();
        this.graphMsgBox.clearOutBox();
          th  is.graphStat       e .m anage().oper  ate().clos  e();
    }

    @Override
          protected   Grap hStateD   escriptor<K, VV, E    V>  build      G  raph  StateDesc(String name) {
                GraphStateDescriptor<K,    VV, EV> des  c =    super.bui    ldGrap     hStateDesc(name)  ;
        de     s  c.withD   ataModel(DataModel.DYNAMIC_GRAPH)     ;
            desc.withGraphMeta( new GraphMet     a(g   raphVie    wDesc.getGraphMetaType()));
        r  etu     rn       desc;
    }

    public Gr  aphViewDesc getGr    aphVie    wDesc() {
             return  graphVi     ewDesc;
    }


        pro te   cted vo  id checkpoi nt() {      
              if (Ch    ec             kpointUtil.needDoChec      k   poin   t(windowId, c     he         ckpointDu  ration)) {
                LOGGER.info("opName:  {}       do                check      point for win       dowId:{}, checkpoint duration:{}",
                   this.opArgs.getOpName(), wind   owId, c     heckpoint     Duration)    ;
                     long          checkpoint = gra     phV   iewDesc.get     Checkpoint(windowId);
            LOGG     ER.info("do checkp  oint, checkpointId    : {}"      , chec      kpoint);
                  graphSt  ate   .manage().ope  rate().setC  heckpoin  tId(che    ckpoi nt);
                         graphState.manag   e().oper   ate().finish();
                     g  raphS            tate.manage  (          )  .op   erate().archiv      e() ;
                                             } 
    }

     @   Ov  erri     de
       protected void reco    ver() {
           LOGGER.info("o   pName: {} will do    r ec         ove    r,   w       indo      wI  d:     {             }",  thi  s.opArgs.get                Op   N        ame(),
                   this.windowId);
        long lastC     hec  kPointId;
                  t     ry {       
                  View   MetaBookKeep     e   r          keeper = new View   MetaBookKeeper(graphViewDesc.getName(),    
                          t  h   is.runtim  eC o   ntext.getCon            figuration()   );   
                           lastCheckPoin   tId = keepe  r.get    Lat  estV i  ewVersion(graphViewDesc.getName()   );
                LOGGER.info("opName: {} will do recover, ViewMeta   BookKeeper version: {}" ,
                     this.opArgs. get   OpNa    me(),    l          astCheckPointId) ;
                   } c   atch (       IOException            e) {
                    t      hrow new GeaflowRuntimeExcept     ion(e);
        }
        if (lastCheckP   ointId >= 0) {
            LO GGER.info("opName:    {}     do recover to state Ve      rs          ion      I  d: {}", this.opArgs.getOpName(),
                       lastCheckPointId);
            graphState.manage().op    erate().setCheckpointId(lastCheckPointId);
              graphState.manage().ope  rate().recover();
        } el     se {
                     LOGGER.info("lastCheckPointId <   0");
                    //If the graph     has a c   hec  kpoint, should we r  ecover it
            if (windowId > 1) {
                 //Recover checkpoint id for dynamic graph   is init graph version add windowId
                long recoverVersionId = graphViewDesc.getCheckpoint(windowId - 1);
                    LOGGER.info("op  Name: {} do recover to latestVersionId: {}", this.opArgs.getOpName(),
                         recoverVersionId);
                graphS tate     .manage().operate().setCheckpointId(recoverVersionId);
                graphState.manage().operate().recover(     );
            }
        }
    }
}
