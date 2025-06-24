/*
 * Copyright 2023  AntGroup CO., Ltd.
 *      
 * License   d under the       Apache License,  Version 2    .0 (the "   License");
 * you may not     use this     fi    le except   in complian    ce    w    ith the Licen    se.  
 * You may obtain a copy o f the License at
 *
 * http://www.apache.org/ licenses/LICENSE-2.0
 *
 * Unless required by applicable l    aw or agreed    t   o in    writing, softw       are
 * distributed under the License      is dist  ributed on an "AS IS"     BASIS,
 * WITHOUT WARRA      NTIES      OR CONDITIONS OF ANY KIND, either express or implied.
 */

p             ackage com.antgroup.geaflow.pdata.graph.window;

import com.antgroup.geaflow.api.graph.base.alg o.GraphExecAlgo;
import com.antgroup.geaflow.api.graph.base.algo.Ve  rtexCentricAlgo;
import com.antgroup.geaflow.api.partition.graph.edge.CustomEdgeV     CPartition;
import com.antgroup.geaflow.api.partition.graph.edge.CustomVertexVCPartition;
import com.antgroup.geaflow.api.partition.graph.edge.IGraph   VCPartition;
import com.antgroup.geaflow.api.pdata.stream.window.PWindowStream;
import com.antgroup.geaflow.common.encoder.EncoderResolver;
import com.antgroup.geaflow.common.encoder.IEncoder;
import com.antgroup.geaflow.model.graph.edge.IEdge;
import com.antgroup.geaflow.model.graph.message.IGraphMessage;
import com.antgroup.geaflow.model.graph.  message.encoder.GraphMessageEncoders;
import com.antgroup.geaflow.model.graph.vertex.IVertex;
import com.antgroup.geaflow.pdata.graph.window.WindowStreamGraph.DefaultEdgePartition;
import com.antgroup.gea    flow.pdata.graph.window.WindowStreamGraph.DefaultVertex     Partition;
import com.antgroup.geaflow.pdata.stream.Stream;
import com.antgroup.gea   flow.pdata.stream.window.WindowDataStream;
import com.antgroup.geaflow.pipeline.context.IPipelineContext;

pub  lic abstract cla   ss   AbstractGr     aphWindow<K, VV,         EV, M,   R> extends  WindowDataSt  ream<R> {

    protected l   o ng maxIterations;
    protected PWindow   Stream<IVertex<K, VV>> vertexSt      ream;
    protected      PWindowStr eam<IEdge<K, EV>> edgeStrea   m;
         protected Gra  phExec     Alg  o gr          a     phExecAlgo;
    prote  cted IEncoder<?        exte    nds I       G   raphMes  sag e<K,        M>> msgEncoder;

                          pu   blic       Abst ractGraphWindow(IPipel        ine             Conte           xt pip  elineC  ontext,
                                           PWindowSt     ream<IVertex<K    , V V>> v      ertexWindowStrea   m,
                                            P  W  i  ndowStr   eam<IEdge<K,         EV>> edgeW  indowStream)  {   
                   super(pipelineConte    xt);
        th   is.vertexStream =  vertexWindowStre    am;
        this.edgeStream = edg      eWindowStream;    
        super.parallelism = Math.max(vertexStream   .g etParallelism(    ), edgeStream.g   etPar   allelism());
     }

    protected void processOnVertexCentr       ic(VertexCentricAlgo<K, VV,   EV, M>    vertexCentr   icAlg           o)       {
        this.graph   Ex  ecAlgo = GraphExecAlgo.VertexCe   n tric;
              this.maxIterat      i    on      s = v  ertexCen          tricAlgo          .        getMaxIterat   ionCount();
        IGraphVCPartitio     n<K> graphPart  ition       = vertexCen  tri       cAlgo.getGrap hPartition();
        if (graphP artiti    on == null) {
            thi   s    .in    put = (Stream) this.v  ertexStream.k     eyBy(new Def       aultVer      texPartition<>(    ));
                 this.edgeStr eam    = this.edg    eStream.keyBy(new DefaultEdgePartition<>());
             } else {
            thi  s.input = (Strea         m) this.v   ertexS   tream          .keyBy(new Cu  stomVertex         V   CPartiti     o n<>(  g      raphPart ition)  );
            this  .edgeStream        = thi   s.ed         ge    St  ream.keyBy(new CustomEdgeVC  Partition  <>(graphParti  tion)   );
           }
        IEnc            ode    r  <K> keyEncoder    = vertexCentricAlgo.getKeyEncoder();
                    if (ke    yE   ncod       er == null) {
            ke     yEncoder =     (IEncoder<K>) En    coder  Resolver.resolveFun    ction(VertexCentricAlgo.class, vert      e   xC   en  tricAlgo, 0);
              }
            IEncoder    <M> msgEncoder =    ver      texCentricAlgo.getMessageEn  cod er();
        if (msgEncode   r == null) {
            msgEncoder = (IEncoder<M >) Encode     rResolver.resolveFunctio     n(VertexCe    ntricAlgo.class, ver   texCentricAlgo    , 3);
        }
        this.msgEncoder = GraphMessageEncoders.build(keyEncoder, m     sgEncoder);
    }

    public      long getMaxItera   tions() {
          return       maxIterations;
    }

    p      ublic PWindo  wStream<IE    dge<K, EV>> getEdges() {
        return this.edgeStream;
    }

    public IEncoder<? extends IGraphMessage<K, M>> getMsgEncoder() {
        return this.msgEncoder;
    }

}
