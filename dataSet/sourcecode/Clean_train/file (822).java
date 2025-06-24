// Copyright 2011-2024 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.security.zynamics.bindiff.gui.dialogs.criteriadialog.expressiontree.nodes;

import com.google.security.zynamics.bindiff.gui.dialogs.criteriadialog.criterion.Criterion;
import com.google.security.zynamics.bindiff.gui.dialogs.criteriadialog.criterion.CriterionCreator;
import com.google.security.zynamics.bindiff.gui.dialogs.criteriadialog.expressiontree.ExpressionTreeActionProvider;
import com.google.security.zynamics.bindiff.gui.dialogs.criteriadialog.expressiontree.menus.NodeMenuBuilder;
import com.google.security.zynamics.bindiff.utils.ResourceUtils;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;

public class CriterionTreeNode extends AbstractCriterionTreeNode {
  private static final ImageIcon DEFAULT_ICON =
      ResourceUtils.getImageIcon("data/selectbycriteriaicons/default-condition.png");

  private final NodeMenuBuilder menuBuilder;

  public CriterionTreeNode(
      final Criterion criterion,
      final List<CriterionCreator> criteria,
      final ExpressionTreeActionProvider actionProvider) {
    super(criterion);

    menuBuilder = new NodeMenuBuilder(this, criteria, actionProvider);
  }

  @Override
  public Icon getIcon() {
    final Icon icon = getCriterion().getIcon();

    if (icon == null) {
      return DEFAULT_ICON;
    }

    return icon;
  }

  @Override
  public JPopupMenu getPopupMenu() {
    return menuBuilder.getPopup();
  }

  @Override
  public String toString() {
    return getCriterion().getCriterionDescription();
  }
}
