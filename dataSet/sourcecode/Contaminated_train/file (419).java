package autoer.ui.model;

import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import autoer.ui.actions.Actions;
import autoer.ui.actions.CheckColour;
import autoer.ui.actions.StartAction;

public class ActionTreeModel extends JTree {

  private static final long serialVersionUID = 1L;
  private DefaultMutableTreeNode rootNode = null;
  private DefaultTreeModel treeModel = null;

  /**
   * this constructs a jtree with a defaulttreemodel and adds it to the
   * jscrollpane
   */
  public ActionTreeModel() {
    rootNode = new DefaultMutableTreeNode();
    treeModel = new DefaultTreeModel(rootNode);

    this.setEditable(false);
    this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    this.setShowsRootHandles(true);
    this.setRootVisible(false);
    this.setModel(treeModel);
    this.setCellRenderer(new ActionsCellRenderer());
  }

  public void addModel(Actions action) {
    clear();
    StartAction startAction = (StartAction) action;

    DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(action);
    treeModel.insertNodeInto(childNode, rootNode, rootNode.getChildCount());
    this.setSelectionPath(new TreePath(childNode.getPath()));
    updateModel(startAction.getActions(), childNode);

    int row = 0;
    while (row < this.getRowCount()) {
      this.expandRow(row);
      row++;
    }
  }

  private void updateModel(ArrayList<Actions> actions, DefaultMutableTreeNode parentNode) {
    for (Actions action : actions) {
      DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(action);
      treeModel.insertNodeInto(childNode, parentNode, parentNode.getChildCount());
      if (action.getClass().equals(CheckColour.class)) {
        CheckColour checkColour = (CheckColour) action;
        updateModel(checkColour.getActions(), childNode);
      }
    }

  }

  public void clear() {
    rootNode.removeAllChildren();
    treeModel.reload();
  }

  public Actions getSelectedItem() {
    TreePath currentSelection = this.getSelectionPath();

    // if a item is selected
    if (currentSelection != null) {
      DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());
      return (Actions) currentNode.getUserObject();
    }
    return null;
  }
}
