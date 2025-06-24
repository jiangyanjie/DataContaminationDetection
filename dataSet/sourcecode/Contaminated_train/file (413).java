package autoer.ui.model;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import autoer.ui.actions.Actions;
import autoer.ui.actions.CheckColour;
import autoer.ui.actions.StartAction;

public class ActionsCellRenderer implements TreeCellRenderer {

  private DefaultTreeCellRenderer defaultRenderer = new DefaultTreeCellRenderer();

  private Color backgroundSelectionColor;

  private Color backgroundNonSelectionColor;

  private JPanel renderer;
  private JLabel label = new JLabel();

  public ActionsCellRenderer() {
    renderer = new JPanel(new GridLayout(0, 1));
    backgroundSelectionColor = defaultRenderer.getBackgroundSelectionColor();
    backgroundNonSelectionColor = defaultRenderer
        .getBackgroundNonSelectionColor();
    renderer.add(label);
  }

  public Component getTreeCellRendererComponent(JTree tree, Object value,
      boolean selected, boolean expanded, boolean leaf, int row,
      boolean hasFocus) {

    Component returnValue = null;
    if ((value != null) && (value instanceof DefaultMutableTreeNode)) {
      Object userObject = ((DefaultMutableTreeNode) value).getUserObject();
      if (userObject instanceof Actions) {
        Actions action = (Actions) userObject;
        label.setText(action.getDescription());

        if (selected) {
          renderer.setBackground(backgroundSelectionColor);
        } else {
          renderer.setBackground(backgroundNonSelectionColor);
        }

        if (action.getClass().equals(StartAction.class)) {
          label.setForeground(Color.RED);
        } else if (action.getClass().equals(CheckColour.class)) {
          label.setForeground(Color.BLUE);
        } else {
          label.setForeground(Color.BLACK);
        }

        renderer.setEnabled(tree.isEnabled());
        returnValue = renderer;
      }
    }
    if (returnValue == null) {
      returnValue = defaultRenderer.getTreeCellRendererComponent(tree, value,
          selected, expanded, leaf, row, hasFocus);
    }
    return returnValue;

  }
}
