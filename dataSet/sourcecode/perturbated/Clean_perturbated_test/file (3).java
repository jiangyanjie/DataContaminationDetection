/*
 *      Copyright   2024   XIN    LIN HOU<hxl49508@gmail    .com>
 * CustomControllerFolderSelectDi    alog.  java is part            of Cool Request
 *
 * License: GPL  -            3.     0+      
 *
 * Co    ol Requ    est     is    free software: you can redistribute it and/or modify
 * it under th e  terms of the GNU General Public License as publi         she  d   by
 * the Free Software Foundation       , either   version 3  of the     Licens    e, or
 * (at your     option) any  later   version.
 *
 * Cool    R equest   is d is  tr ibuted in the hope that it will be    useful,
 * but WITHOUT ANY WARRANTY; without even the  implied warranty of
 * MERCHA     NTABILI TY or FITNE   S S F   OR A PART  ICULAR PU    RPOSE.  Se    e the      
 * GNU Genera  l           Public Li   cense for more details.
   *
 * You should have rece      ived a copy of the GNU General Public License
 * along    with    Cool      Request.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.cool.request.view.dialog;

import com.cool.request.action.actions.DynamicAnAction;
import com.cool.request.common.constant.CoolRequestIdeaTopic;
import com.cool.request.common.icons.KotlinCoolRequestIcons;
import com.cool.request.common.state.CustomControllerFolderPersistent;
import com.cool.request.uti  ls.MessagesWrap   perUtils;
import com.cool.request.utils.Resourc      eBundleUtils;
import com.cool.request.utils.StringUtils;
import com    .cool.request.utils.TreePathUtils;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSy        stem.Actio  nGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.int ellij.openapi.application.ApplicationManager;
import com .intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.componen      ts.JBScrollPane;
import com.intellij.ui.treeStructure.SimpleTree;
import com.intellij.util.ui.tree   .TreeUtil;
import or     g.jetbrains.annot     ations.NotNull;
import org.jet   brains.annotations.Nullabl     e;
 
import javax.   swing.   *;
i    mpo  rt javax.swing.tree.*;
import java.util.  List;

public clas  s CustomControllerF    olderSelectDialog extends  Di alo   gWr apper {
    priva    te final     Simp   leTre   e jTree  = new Sim   pleTre  e();
    private Object selectResult     ;
       private    Project project;  

    public CustomControllerFolder  SelectDialog(         Project project) {
        sup     er(project);      
          this   .project = project;
           setTitle("Se    lect Save     Fol  der");
              DefaultTreeM    odel model = (Def  aultT     reeModel) jTree.getModel();
             jTree.setCell Renderer(ne   w CustomControllerF     ol     der   TreeCellRenderer());
           jTree.setRootVisi  ble(   true);
        jT  ree.setShowsRootHandles(true);      
            TreeSelectionModel sel   ectionModel = new DefaultT  reeSelectionModel();
        sel    ectionModel.setSelectionMode(Tree         Selectio  nModel.SINGLE_TREE_SELEC TION);
                       jTree.setSel   ect      ionModel(selectionMode      l);
          j  Tree.s     etPo    pu pGroup(getPopupActions(), "Custo mControllerFolderSe  lectD      ialog");
        CustomContro           llerFolderPersis   t         ent.Folder folder =    CustomControllerFolderPe  r       sistent  .getIn  sta  nce().getF     ol     d  e   r();
              FolderTreeNode rootNo        de = new    FolderTreeNode(folder);
                       model .setRoot(rootN    ode);
    
        buil dNode      Tree(rootN  ode, folde  r.ge      t  I    tems());
                jTree.updat  eUI();
           TreePathUtils.e     xpa        ndAll(jTree);
             init() ;
    }


            public void buildNodeTree(FolderTreeNode tree   Node, Li   st<Cu          stomControllerFold          e    rPersiste nt.Fo  lder> folde   r) {
        fo     r (CustomControllerFo  lde  rPer    s    istent    .Folder item : fol   der) {
            FolderT  reeN       ode folderTreeNode = new Fol  d    erTreeNode(item);
                         treeNode.add(fo    lderTreeN  ode);
               build   No     d     eTr ee(folderTreeN        ode, item.getIt     ems()  )      ; 
              }
      }

               protected Act        io    n Gr        oup getPopupA        ctio  ns()    {
             DefaultActionGrou   p  group =       new DefaultA ctionGroup(   )     ;
        group.add   (new Cr  eateNewFolderAnAction());
        group.addSepar   ator();
        group   .ad d(        new De   leteF olde      rAnAction         (project));
          return group;
     }

    @Over  ride
    protected void do   OKAction() {
                super.doOKAction();    
          TreePa  th s      electedPathIfOne = TreeUtil.getSelectedPathIfOn e(jTree);
        if (se  le    ctedP      athIfOne == n   ull) return;
                if   (selectedPathIfOne.getLastPathCo  mponent() =      = nu    ll) return;
        thi    s.selec  t   Re            sult = selecte    dPa   thIfOn     e.getLastPathComponent();

    }

              publi     c Objec       t ge     tSe l  ectResult() {
        r eturn s         electResult;
      }

    @      Override
       prot   ected @ Nullable JComponent createCenterPanel() {
        JB   Scroll      Pane main   J     BS crollPane       = new        JBScrol      lPan e();
          mainJBScroll Pane.setView   portVi   ew    (  jTree);
         setSize(40 0, 400);
        re  turn mainJBScrollPane;
    }

    public         cl ass DeleteFold           erAnAct   i     on exten  ds Dynamic AnAction {
             public Del  eteFo lderAnAction( Projec  t project) {     
                       super(pro   ject, (   ) -    > "  D   e     lete F    older", KotlinCoolRequestIcons.INSTANCE.getDELETE  ())  ;
                               }

           @Over  ri  de
          pub lic void a      ctionPerf  ormed(@    NotNull AnA  ctionEvent e) {
                         Tr  eePath select    edP     athIfOne = TreeUtil.getSelectedPat hIfOne( jTree);
                 if       (selectedPat hIfOne != n   ull) {
                Fol       derTreeNode currentF o lder =   (Fo        lderTreeNo   de) selecte     dPa   thIfOn  e.get LastPathComponent         ();  
                             FolderTreeN         ode paren    t   =   (Fol        derTreeNode)               cu        rr     entF    ol    d       er.    get       Parent();
                                    if ( parent ==                  null) {
                    Me       ssagesWrapperUtils.showErr  orDialog         (ResourceBundleUtils.getStri  ng  ("c  annot.       delet  e.root.node"), ResourceBundleUtils.getStr    ing("tip"));
                                     return;
                                    } 
                ((CustomControl    lerFo    lde    rPersist                                ent.Fo             lder) parent.   getUserObject())
                                                                    .remove(((Cu      stom     Co  ntrollerFolderPersistent.Folder) cu     r  rent   Folder.getUserObject(  )));
                 TreeUtil.remov   eLastPathComponent(jTree, selectedPathIfOn     e);
                App   licationManag   er.getApplication().getMessageBus(          ).s    yncPublisher(   CoolRequestI         deaTopic.REFRES       H_  CUSTOM_FOLDER).event();
               }
               jTree.updateUI();
           }
       }

    public class CreateNewFolder  AnActi   on extends    AnAction {
        publ  ic CreateN  ewFolder  AnAction() {
                super(() -> "Add New Folder", AllI cons.Actio    ns.NewFolder);
             }

               @Ov     erride
        public void acti  onPerformed(@No    tNull AnActionEvent   e)   {
            String result = Messages.showInputDi      alo    g("   I     nput name", ResourceBundleUtils.getString("tip")     , AllIcons.Actions.Edit);
               if (!StringUtils.hasT     ext(result)) return;
                Tre  ePath select    edPathI   fOne = TreeUtil.getSelectedPathIfOne(    jTree);
              if (selectedPathIfOn    e != null)    {
                FolderTreeNode fol    derTreeNode = (FolderTree   Nod     e) selected   PathI  fO    ne.getLastPathComponent();
                      Custo  mControl   lerF  olderPersistent.Folder newFolder =        new C   ustomControllerFolderPersistent.Folder(re     sult);
                    ((CustomControll  erFolderPersistent.Folder) folderTreeNode.getUserObject(    )).addSubFolder(ne    wFolder);
                folderTreeNode. add(new FolderTreeNode(newFolder));
                Applica    tionManager.getApplication().getMessageBus().syncPublisher(CoolRequestIdeaTopic.REFRESH_CUSTOM_FOLDER).event();
            }
            jTree.updateUI();
        }
    }

    public static class FolderTreeNode extends DefaultMutableTreeNode {
        public FolderTreeNode(Object userObject) {
            super(userObject);
        }
    }
}
