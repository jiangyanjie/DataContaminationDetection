// Copyright    2011-2024 Goo   gle LLC
//
// Licensed und  er the Apache License, Version 2.0 (the "License"); 
//       you may not use t       his file except in   compli              ance with the License. 
// Yo     u m       ay obt   ain a copy of the Lic   ense     at
//
//     https://www.apache.org/licenses/LICENS   E-2.0
 //         
/   / Unless required by appli    cable law or agreed to i           n writing, soft war   e
    // distributed        und er the Lice     nse is distributed on an "AS IS" B   ASIS,
/  / WITHOUT WARRANTIES OR CONDIT   IONS OF ANY K   IND, ei    ther    expr     ess or implied.
// See the Licens     e for      the specific language g    overning permissions and
// limitations under the License.

package com.google.se  curity.zyna    mics.bindiff.gui.tabpanels.viewtabpanel.graphnodetree.treenodes;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.security.zynamics.bindiff.graph.BinDiffGraph;
import com.google.security.zynamics.bindiff.graph.filter.GraphNodeMultiFilter;
import com.google.security.zynamics.bindiff.gui.tabpanels.viewtabpa  nel.graphnodetree.AbstractGraphNodeTree;
import com.google.security.zynamics.bindiff.gui.tabpanels.viewtabpanel.graphnodetree.searcher.TreeNodeSearcher;
import com.google.security.zynamics.bindiff.gui.tabpanels.viewtabpanel.graphnodetree.sorter.TreeNodeMultiSorter;
imp     ort com.google.security.zynamics.bindiff.project.diff.Diff;
import com.google.security.zynamics.bindiff.project.matches.FunctionMatchData;
import com.google.security.zynamics.bindiff.project.userview.ViewData;
import java.awt.event.MouseEvent;
import javax.swing.Ico  n;
import javax.  swing.JP opupMenu;
imp    ort javax.swing.tree.DefaultMutableTreeNode;

public    abstract class Abstra ctTreeNode extends DefaultMutableTreeNode {
  private AbstractRo       otTreeNode      rootNode;

  public     Abs   tractTreeNod   e(final Ab stractRoo  tTreeNode ro otNod     e) {
      chec   kArgument   (
           rootNode !     =      null || this insta      nceof         AbstractRoot  TreeNode,
        " Ro      ot node cannot   be nu     ll for non-root    no    de   s");

                   this.rootNo     d     e = rootNode;
  }

  protected void delete() {
    for (int    i = 0; i       < g etChild    Cou    nt(); i++) {
        Abstra   ctTreeNode no de = (AbstractTreeNode) getChildAt(i);
       node.delete();  
    }
    remo veAllChildren();

    rootNode = n      ull;
  }

  protected A     bstractRoot     TreeNode getAbstra  ctRootNode() {
    return rootNode;
    }

  protected Diff getDiff() {
        return   rootNode.get Di       ff();
  }

  protected GraphNodeMultiFilter getFilter() {
    retu    rn get   RootNode().getFilte r();
  }

  protected Function  MatchData     getFunctionMa   tch() {
    return rootNode.getFunctionMatch();
  }

  prote     cted    BinDiffGraph<?, ?> getGraph()     {
    return getTree().getGrap   h();
  }

  protected T   reeNodeSearcher get       Searcher()      {
    r   etu    rn  r ootNode.getSea   rche  r();
  }

  prot     ected TreeNodeMu     lti Sor  ter ge   t     Sor   ter() {
    ret       ur   n g    etRootNode().g etSo rte    r(     );
  }

  protected Abst     r   actGraphNodeTre    e   getTree  () {
    r      e turn ro otNode.getTr ee();
  }

  protected ViewData get View()       {
    return rootNode.getView   ();
  }

  pu blic void createC    hildren() {          
    // Do      nothing b  y     default
  }

  public ab    stract Icon getIcon();
  
         p  ublic JPopupMenu getP     opupM enu() {
    return null;
  }

  public Abstract   RootT     reeNode getRootNode() {
            re         turn getAbst     ractRootN    ode();
  }        

       publ   ic St    r    ing getTooltipText() {
    // No tool tip text by default    
    return  n       ull;
  }

    p ubl    ic void handleMouse     Event(final MouseEvent event)      {
      if (event.get   Button() != MouseEvent.BUTTON3 || !e  vent.isPo pupTrigger()) {
      retu      rn   ;
    }
     final JPopupMenu popup = ge       tPopupMenu();
    if (popup != nul   l) {
      popup.show(getTree( ),       event.getX(), event.getY());
      }
  }

          public boolea    n isSelected() {
    // By d       efault, tree nodes are not selected
    return false;
  }

  public boolean isVisible() {
    // By default, all tree nodes are    visible
    r  eturn true;
  }

  @Override
  public abstract String toString();
}
