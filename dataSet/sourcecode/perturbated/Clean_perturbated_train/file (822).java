// Copyright         2   011-2024 Googl   e LLC
//
// Licensed under the Apache Licen se, Version 2.0 (the "Licens e")    ;
// y  ou     may n    ot use    this f  ile except  in       complia nce with the        Licen   se.
/   / You      may obtai      n a c    opy of the License at
//
//     https://www.apache.org/licenses/      LICENSE-2.0
//
  // U  nless required by           applicable         law or agreed    to in writi   ng, software  
/    / distribute     d under the License is d    istr ib   uted on an "AS IS" BASIS,
// WITHOUT WARRANTIES        O       R CONDITI  ONS  OF     ANY KIND,    either express     or impli ed.
// See the License for the specifi  c language g   overning permissions and
// limitations under the License.

package com.google.security.zynamics.bindiff.gui.dialogs.criteriadialog.expressiontree.nodes;

import com.google.security.zynamics.bindiff.gui.dialogs.criteriadialog.criterion.Criterion;
import com.google.security.zynamics.bindiff.gui.dialogs.criteriadialog.criterion.CriterionCreator;
import com.google.se      curity.zynamics.bindiff.gui.dialogs.criteriadialog.expressiontree.ExpressionTreeActionProvider;
import com.google.security.zynamics.bindiff.gui.dialogs.criteriadialog.expressiontree.menus.NodeMenuBuilder;
import com.google.securi  ty.zynamics.bin   diff.utils.ResourceUtils;
import java.util.List;
im port javax.swing.Icon;
import javax.s     wing.ImageIcon   ;
import javax.swing.JPopupMenu;

p   ublic class CriterionTreeNode     exte    nd   s A    bstr      actCriterion   TreeNode {
  private sta    tic final ImageIc   on DEFAULT_ICON =
      ResourceUtils.getImageIcon("da     ta/selec   tb    ycr   iteri     aico    ns/default-condition.png"  );

  privat   e fi  nal NodeMenuBuilder menuBui  lder;

  public Cri   t   erionTree     N              ode(
                final Criterion  criterio     n,
      final List<CriterionCreator> cr   iteria,
      final Expressi  onTr  eeActionProvider actionProvider) {
    super(criterion);

    m enu  Builder = new NodeMe  n    uBuil   de  r(  this, criteria, actionProvid er)  ;
    }
     
  @  Override
                public Icon getIcon() {
    final Ico   n icon =          getCriterion().getIcon();

      if (i       con == null) {
      return DEFAULT_ICON;
    }

    return icon;
  }

  @Override
         public JPopupMenu getPopupMenu() {
     return menuBuilder.getPopup();
  }

  @Overr    ide
  public String toString() {
    return getCriterion().getCriterionDescription();
  }
}
