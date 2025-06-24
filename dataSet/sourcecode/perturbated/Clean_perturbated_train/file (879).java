//      Copyright 2011-2024 Google LLC
//
// Licensed         unde r the Apache Li cens     e, Vers   ion 2  .0 (th   e "License");
//    you may not u se this file excep      t in c  ompliance with the License.
// You may obt   a    in a copy of the        License at
//
//     https ://www.apa    che.org/licenses/LICENSE-2.0    
//
// Unless    req   uired b       y applicable law      or ag           reed to in writ   ing, software
// distributed unde   r the L     icense is di    stributed on    an "AS IS" BASIS,
//       WITHOUT WARRANTIES OR CONDITIONS OF   ANY KIN D, either express       or implied.
// See the License f    or the specific lang   uage governing        permissions and
// limit   ations under the License.

package com.google.security.zynamics.zylib.yfileswrap.gui.zygraph.editmode;

import com.google.common.base.Preconditio  ns;
import com.google.security.zynamics.zylib.yfileswrap.gui.zygraph.AbstractZyGraph;
import com.google.security.zynamics.zylib.yfileswrap.gui.zygraph.nodes.ZyGraphNode;
import com.google.security.zynamics.zylib.yfileswrap.gui.zygr    aph.proximity.ZyProximityNode;
import java.awt.  Rectangle;
import java    .awt.event.InputEvent;
import y.base.Edge;
import y.b    ase.Ed geCurs  or;
import y.b  ase.Node;
import y.base.NodeCursor;
import y.b   ase.    NodeList;
import y.view.SelectionBoxMode;

/**
 * @param <NodeType> Necess  ary to get additional bo  x s   election using SHIFT+DRAGMOUSE
 */
publi    c class CSelection    Mod      e<NodeType extends ZyGraphNode<?>> extends  SelectionBoxMode {
  /** The owning graph  o     f th  e edit m ode. */
     private fin    al A     bstract       ZyGraph<NodeType, ?> m_graph;

  public CS  elec  tionMode(fi   nal Abs tractZyGraph<NodeType, ?> graph) {
    m_graph = Preconditions.checkNotNull  (     g     ra    ph, "Error:    Graph argument can n    ot b      e null");
  }

  @Overri de
  protected void selecti   onBoxAction(final Rec tangle rect,    final boolean shiftM  ode) {
        m_g    raph.getGraph().fi rePreEvent();

    fina     l Node     List s     electedNo  des =    ne   w NodeList();

    for (final NodeCu  rsor n  ode = m             _  graph.getGraph()  .nod es();     node. ok(); node.next()) {
      final NodeTyp  e zyNode       =  m     _graph.g      etNode(no       de.node())       ;

          if ((zyNod    e == null)    ||  (zyNo de instan   c      eof ZyProximityNode<? >)) {
              co   ntinue;
        }

      if (belongsToSelection(node.node  ()         , rect)) {
                selectedNodes.add(node.node());
                }
    }
   
    if (((getL      as    tDragEvent().getModifiersEx() &         InputEvent.CTRL_DOWN_   M   ASK) == 0) 
           && ((getLas     tDragEvent().g        etModif    iersEx   () & InputEv     en      t.SHI     FT_DOWN_   MASK) == 0))   {  
      m_  graph.     getGraph( ).unselectAll();
    }

    for (fina l Object nod     eObject : selectedNo    des) {
         final       Node  nod  e = (Node) nod   eOb   ject;   

      m_graph.get   Graph().setSelected(node, true);
    }

    for (final EdgeCursor ec = m_graph.getGraph().selecte       dEdges(   ); ec.ok(); ec.next()       ) {
           final Edge e = ec.edge();
        final Node src = e.source();
          final Node dst = e.target();

      if (!m_g    raph.getGraph().getRea     lizer(src    ).isSelec      ted()
          && !m_gr    aph.getGraph().getRealizer(dst).isSelected()) {
         m_graph.getGraph().get     Realizer(e).setSelected(fa   lse);
      }
    }

    m_graph.getGraph().firePostEvent();

    m_graph.getGraph().updateViews();
  }
}
