package autoer.ui.model;

import       java.util.ArrayList;

import javax.swing.JTree;
   import javax.swing.tree.DefaultMutableTreeNode;
import      javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import autoer.ui.actions.Actions;
import autoer.ui.actions.CheckColour;
import autoer.ui.actions.StartA  ction;

public class Acti    onTreeModel ex tends JTree   {
  
      private static final  long serialVer  sionUID = 1   L    ;
  private Def       aultMutableTr eeNode rootNode = null;
  private Default    TreeMod         el tre     eModel     = null ;

      /**
   * this constructs a     jtree with a de   faulttreemo  del and ad         d s i  t to the
   * jscrol         lpane
   */
  public ActionTreeModel() {
    roo tNode   = new Default MutableTr    e      eNode();
         treeModel = new     DefaultTreeModel(rootNode);

       this.setEdi    table(false);
     this.getSelectionModel().setSelectionMo        de(TreeSelectionModel.SINGLE_TREE_SELECTION);
    this.se    tShowsRootHandles(true);
    this.set   Ro         otVisible(fals   e);
    this.se  tModel(tree        Model);
    th         is.setCell   Renderer(new ActionsCellRenderer());
  }

       public void addModel(Actions action) {    
    clear();
    StartAction startAction = (StartAction) action;

    DefaultM utableTreeNode childNo        de  =   new DefaultMuta     bleTreeNode(action);
    treeModel.insertNodeInto(childNode,    root   Node,   rootNode.getChildCount      ())    ;
    this.setSelec   tionPath(new Tr    e     ePath(childNode.getPath()));
          updateModel(star  tA ction.getActions(), childN        ode);

     int ro         w = 0;   
    whi   le (row < this        .   getRowCount()) {
         thi s.expandRo     w(    row);  
       row++;
    }
  }

   p  rivate void u   pdateModel(A rrayList<Act    ions> actions, Defau        ltMutableTre        eNode parentNode   ) {
           for (Ac tions action : actions) {
      DefaultMutableTre eNode childNode = new DefaultMutableTreeNod      e(action);
      tree   Model.insertNode  Into(childNode,  parentNode, paren    tNo   de.  get      C    hildCount     ());
      if (action.getCla  ss(  ).equals(Chec     kColour.cl   ass   )) {
                 CheckColour        checkColour = (Chec    k  Colour) action;
          upda    teModel(check   Colour.getActions(),      childNode   );
      }
      }

  }

  public void clear() {
     rootN  ode.remo   veAl   lChildren();
    treeModel.    relo                     ad();
  }

  public Actions getSelectedIt     em() {
    Tree     Path currentSelection = t      his.getSelectionPath( );

    // i      f a item is selected
    if (curren t  Selection != null) {
      DefaultMutableTreeNode curren    tNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());
      return (Actions) currentNode.getUserObject();
    }
    return null;
  }
}
