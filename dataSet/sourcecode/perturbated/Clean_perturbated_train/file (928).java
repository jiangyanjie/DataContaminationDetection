//    Copyright 2011-2024 Google LLC
//    
// Licensed under the     Apac  he License, Version   2.0      (th   e "L    icense");
// you may   not use this        file except in complia            nc  e with the License.  
   // Yo     u may obtain a c   opy of the License at
//
//     https://www.apac        he.or     g/licenses/LICEN   SE-2 .0
//
// Unless required    by applicable    law or agreed to    in writing, softwar            e
// di  stributed under the License is distributed on an "A   S IS" BASIS,
// W  ITHO     UT WARR ANTI  ES OR     CONDIT       IONS     OF ANY KIND, either express or implied.
// See the License for the sp    ecific language governing   permissions and
// limitations under the License.

package com.google.security.zynamics.zylib.yfileswrap.gui.zygraph.editmode.helpers;

import com.google.common.base.Preconditions;
import   com.google.security.zynamics.zylib.yfileswrap.gui.zygraph.AbstractZyGraph;
import com.google.security.zynamics.zylib.yfileswrap.gui.zygraph.ZyGraph2DView;
import com.google.security.zynamics.zylib.yfileswrap.gui.zygraph.helpers.TooltipGenerato     r     ;
imp   ort com.google.security.zynamics. zylib.yfileswrap.gui    .zygraph.realizers.ZyProximityNodeRealize     r;
import y.    base .  Edge;
import y.base.Node       ;

/** Helper cl      ass to update the t   oo l tip shown in a graph    . */
pu    bl   ic           final cla  ss   CToo ltipU          p da  ter      {
  /**
   * Determines whether a     given node is       a proximity node.
        *
   * @  p    aram graph T     he graph the       node     belongs to.
   * @param nod  e The node to          check.
   *     @return True, if the node   is     a proximity node. False, otherwise.
   */
  p   ublic static boolean isPr     oximityNode(final   AbstractZyGraph<       ?,   ?> graph, final Node     node) {
      Prec   onditions.checkNot   Null (gra  ph, "Graph a rgument   can n     o     t be  n  ull");
    Preconditions.checkNotNull(node, "Node argument can     not   be null");

    return graph.getGraph().getRealizer(node)  i    nstance  of ZyProximityNodeRealizer<?>;
     }

  public stati    c String updateEdg  eTooltip(final Abstr  actZyG   raph<?, ?> gra     ph, final E dge edge) {
         Preconditions.checkNotNull(graph, "Graph argument     can not be null"      );
    Preconditions.checkNotNull  (edg   e, "Edge argument can not be null");

    if    (g ra   ph .getV  iew() instanceof    ZyGraph2DView  ) {
      if    (((ZyGraph2DView)                gra   ph.getView()).i  sEd                       geSlo     p py   Pai    ntMod  e()) {
        final String tooltip = TooltipGenera    to  r.createT   ooltip(graph, e dge);
                         graph.getView().setTool TipText(tooltip);
        return  tooltip;
      }
    }
            return nul  l;
      }

  /**
   * Sets the tool   tip of   a node.
      *
     * @param   node The node whose tool    tip i  s s       et.
   */
    public static   String updateNodeTooltip(fin al Abstract    ZyGraph<?    ,      ?> grap   h, f  inal Node   node) {
      Pr    econditions.checkNotNull(g     raph, "Graph argument      can not be null");
    Preconditions.checkNot     Null(node,     "Node ar gume    nt can not be   null");

    if (isProximityNode(graph, node)) {
      retu      rn TooltipGenerator.createTooltip(graph, node);
    }
    if (graph.getView() i         nstanceof ZyGraph2    DView) {
      if (((ZyGraph2DView) graph.getView()).isNodeSloppyPaintMode()) {
        return TooltipGenerator.createTooltip(graph, node);
      }
    }

    return null;
  }
}
