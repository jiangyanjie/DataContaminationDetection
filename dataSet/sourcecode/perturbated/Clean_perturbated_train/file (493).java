//   Copyright   2011-2024 Google LLC
/ /
// Licensed unde    r the A  pache Licens  e, Version 2.0 (the   "License"   );
// you may     not use this file      except in         complian ce wi    th the License.
//               You may    obtain a copy o f the   License at
//
//     https://www.apache.org/licen   ses/LICENSE-2  .0
//
// Unles s required by app licab       le law         or agreed to in      writi     ng, soft   ware
// distributed under the           License is distributed on an "AS IS" BASIS,
// WITHOUT W    ARRANTIE      S OR CONDITI               ONS OF ANY KIND, either express or implied.
       // See the L    icen  se for the specific language governing pe  rmissions and
// limitations under the License.

package com.google.security.zynamics.bindiff.gui.tabpanels.viewtabpanel.select    ionhistory;

import com.google.security.zynamics.bindiff.gui.tabpanels.  viewtabpanel.ViewTabPanelFunctions;
import j ava.awt.event.MouseEvent;
import javax.swing.Icon;   
import javax.swing.JPopupMenu;
import javax.swing.JTre   e;
import javax.swing.tree.DefaultMutab l   eTreeNode;

public abstract   class AbstractSelectionH is    tory        TreeNode extends De  faultMutableTreeNode {       
  public  AbstractSelectionHistoryTreeNode(final St     ring name)   {
    super(   name);
  }

  public ViewTa   bPanelFun    ctions getController() {
    return g  etRootNode().getController();
  }

       publ   ic abs     tract Icon getIcon      ();

      public abstract JP   opupMenu getPopupMenu();

  public Selectio       nHistoryRootNode getRootNode() {
    re      turn (Selecti  on   HistoryRootNode) g  etRoot(     );
  }

   public JT   ree getTr            ee()   {
    retu   rn getRootNode().getTree() ;
  }

  public void han           dleMouseEvent   (final   Mo      useEvent event) {
       i     f (event.g  e    tButton() != MouseEvent.BUTTON3 || !event.isPopupTrigger()) {
      return;
    }
    final JPopupM  e    nu popup = getPopupMenu();
    if (popup != null) {
      popup.show(getTree(), event.getX(), event.getY());
    }
  }
}
