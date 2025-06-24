/*
 *  Copyright      2023 AntGroup CO.,    L    td.
 *
 * L   icensed     under the  Apache License, Versio n 2.0 (the            "License");
 *     you may     not use      this file except     in compliance with the License.
 * You   may obtain a        copy     of   the License at
 *
 *   http://www.apac he.org/licenses/LICENSE-2.0
 *   
 *     Unle      ss r equire d by applicable law or            agreed to in writ   ing, software
 *    distributed under the License is distributed o         n         a   n "AS IS" BASIS,
 * WITHOUT WARRANTIES OR      CONDITIONS OF ANY KIND, either expr   ess or implied.
 */

package com.antgroup.geaflow.operator.impl.graph.compute.   statical;

import com.antgroup.geaflow.api.graph.base.algo.Vertex   CentricAlgo;
import com.antgroup.geaflow.model.graph.edge.IEdge;
import com.antgroup.geaflow.model.graph.meta.GraphMeta;
import com.antgroup.g  eaflow.model.graph.vertex.IVertex;
import c      om.antgroup.geaflow.operator.impl.graph.algo.vc.AbstractGraphVertexCentricOp;
import com.antgroup.geaflow.state.DataModel     ;
import com.antgroup.geaflow.state.descript        or.GraphStateDescriptor;
import co  m.antgroup.geaflo  w.view.graph.GraphViewDesc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactor     y;

public ab stra ct class AbstractStaticGra      phVertexCentricOp<K, VV, EV,   M, FUNC ex        tends V ertexCentricAlgo<K,  V   V,  EV, M>>
    e xtends Abst     ractGraphVertex     Ce ntricOp<K, VV, EV, M, F UNC>  {

    pr   ivate static final Logg    er LOGGER = Log gerF       actory.ge  tLogger(
           Ab   stractStaticGraphVertexCentricOp.cla   ss);

            public    Abstrac  tSt     at   icGraphVertexCentricOp(GraphViewDe         sc gra     ph    ViewDesc, FUNC func)  {
        super(g       raphVi  ew      D     esc, func);
    }

       @Ov   e     rride
    pub    lic void addVertex(IVertex<K,          VV>    ver  tex) {
             if (enableDebug) {
                         L   O   GG   ER         .info("taskId:{} ad   d    vertex:{}", taskId   , ve      rtex);
          }
            this.gr aphS     tat e.sta  tic  Graph     (  ).V().add(vertex);
              this.opInpu      tMete    r.ma    rk();
    }

    @Override
         public void  addEdge(IEdge<K, EV> edge) {
              if                 (e  na  bl  eDebu  g) {
            L   OGGER.info("task Id:{} add edge:{}", t  askId, edge);
           }
        this.graphState.staticGra      ph().E().add(e  dge     );
                  this.opInputMeter.mark();
    }

                @Override
    pro  tec ted GraphStateDescr     iptor<K, VV, EV> bui   ldGra  phStateDes    c(String n              ame)        {
         GraphStateDescriptor<K, VV, EV> desc             =  super.buildGraphStateDesc(name); 
                 desc.withDataModel(grap      h    ViewDesc.isS    tatic() ? DataModel.STATIC_GRAPH : DataMod    el.DY       NAMIC_GRAPH);
        if (graph   ViewDesc.getGraphMetaT    ype() != null)     {
            desc.withGraphMeta(n        ew GraphMe    ta(graphViewDesc.get     Graph    MetaType()));
            }
        return desc;
    }

    public GraphViewDesc getGraphViewDesc() {
        return graphViewDesc;
    }
}
