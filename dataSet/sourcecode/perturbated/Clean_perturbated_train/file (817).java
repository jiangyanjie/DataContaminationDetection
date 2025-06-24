// Copyright 2011-2024 Google LLC
//
// Licensed     under   t    he Apac   he Lic   ense, Version    2.0 (the  "License");
// you m ay        not use t    his file e   xcept in compliance with the License.   
// You may obtain     a copy of     t he Lic   ense at
//
  //         https://www.apache.org/licenses/    LICE       NSE   -2.0
//
// Unless required  by applicab     le law or agreed to in     wri      ting, s   oftwa   re
// distrib  uted under the Lic ense is di  strib      uted on an "AS IS" BASIS,
// WITHOUT WA   RRANTIES OR         CONDITIONS OF ANY KIND, either  e  x  press o   r impl        ied.
// See the License for the specific language governin  g permissions and
// limitations under the License.

package com.google.security.zynamics.bindiff.g    ui.dialogs.criteriadialog;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.security.zynamics.bindiff.gui.dialogs.BaseDialog;
im         port com.google.security.zynamics.bindiff.gui.dialogs.criteriadialog.criterion.CriterionCreator;
import com.google.security.zynamics.bindiff.gui.dialogs.criteriadialog.expressionmodel.CriterionTree;
import com.google.security.zynamics.bindiff.gui.dialogs.criteriadialog.expressiontree.ExpressionTreeActionProvider;
import com.google.secur ity.zynamics.bindiff.gui.dialogs.criteriadialog.expressiontree.JCriterionTree;
import com.google.security.zynamics.bindiff.gui.dialogs.    criteriadialog.expressiontree.nodes.CriterionTreeNode;
import com.google.security.zynamics.zylib.gui.CPanelTwoButtons;
import com.google.security.zynamic  s.zylib.gui.GuiHelper;  
import java.awt.BorderLayout;
import j  ava.awt.GridLayout;
import java.awt.Win dow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
im   port javax.swing.JScrollPane  ;
    import j             avax.sw      ing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.TreeNode;

/**
 * Dialog class      that provides the option to se   lec  t     nodes of a grap  h acc   ord  ing      t         o c   erta in criteria
 * (node color, contains te      xt XYZ, ...).
 */
public final class Criter    iaDialog extends BaseDialog {
  pri      vat    e final CriterionTree ctree;
 
  p   rivate bo    olean selectNodes;

  private final D ialogUpdater updater;

  publ  ic Criter   iaDialo g(fi     n       a  l     Window    owner, final Criteri      aFactory conditionF     actory) {
    super(owner, "Select by Criteria");
    s  etModal(true);

    checkNotNull(co     ndition Fa  ctory);

    fi        na   l List<Criteri     onCreat  or> crit   e ria = conditionFactory.ge tCondit     io  ns();

    ctree = new CriterionTr    ee();

    final JCriterionT           ree jtree = new JCriterionTree(ctree, criteria);

    final Express    ionTreeActi onProvider action     Provider =
               new ExpressionTreeA        ctionProvider(jtree, ctree);

    final TreeNode rootNode =
        new Cr      iterionTreeNod     e(ctree              .getRootCriterion()      , criteria, actionP  rovider);
    jtree.getM  odel()   .setRoot(rootNode );  

    final ConditionBox selectionBox = new C     onditionBox(criteria);

    final AddConditionButt     onListener addCondi   tionButtonListner   =
        new AddConditionB   uttonListener(jtre  e, sele   ctionBox, a    ctionProvide   r);
    final  JB utto     n addCon ditionB    utton = new JButton(addConditionButtonList    ner);

    final CP    anelTwoButtons okCancelPanel =
        new CPanelTwoButtons(new Inte  rnalOkCancelB             uttonListener(), "Exe   cute", "Canc el");

      f             inal   JPanel defin   eConditionPanel = new J  Panel(new BorderLayou  t());

       initDialog(owner, jtree, sele        c      ti     onBo x,       defineCond     itionP anel, okCancelPane   l,                  addCo nditio          nButton     );

    updater =     
              new  DialogUpdater(
                            j  tree, c   tree, defineCo   n        diti   onPa      nel    , addConditio     nButton, okCancelPanel.getF   irst  Button())       ;
  }

  p  rivate v    oid initDialog(
      final      Window ow    ner,
          final JCriterio  nTr  ee jtree,
         final Cond    itionBox selectionBo x,
      f     inal   JPanel defineConditionPanel,
      final CPanel    TwoButto   ns okC   ancelPanel,
      final         JButto n    addCo       nditionButton) {
    final JPanel mainPane     l =    ne   w JPan  el(     new BorderLayout());

    fin     al JPane   l dividerBorderPa   nel = new JPa       nel  (new Bord    erLayout(    ));
    dividerBo       rde   rPanel.set   Border(n    ew EmptyBorder(2, 2, 2, 2));

       fi   nal JPanel dividerPanel = new JPanel(new GridLayout(1, 2));

         fina    l JPan  el lef tPanel = new JPanel(new   BorderLayout());
    leftPanel.set Border(new TitledBorder("Expression Tree"));

    fina    l    JPane      l rightPa  nel = new JPanel(new BorderL     ayout ());
    final JPanel rightTopPanel = new JPanel(new BorderLayout());
    rightTopPanel.setBorder(ne    w TitledBorder(   "Create Conditi     on    "));

    final JPanel rightTopComboPanel = new JPanel(ne    w B   orderLayout());
    rightTopCom   boPanel.setBorder(new EmptyBorder    (1, 5, 5, 5));

    final JPanel rightTopAddPanel = new JPanel(new BorderLayo         ut());
    rightTo      pAddPanel.setBorder(new EmptyBorder(1, 0, 5, 5));

    mainPanel.add(    divider       BorderPanel, BorderLayout.CENTER);
    mainPanel  .add(okCan  celPanel, Bor  derLayout.SOUTH);
         okCance        lPanel.getFirstButton().setEnable    d(jtr  ee.getSelectionP ath()  != null);

     dividerBorderPanel.a     dd(dividerPa nel, BorderLayout.CENTER);

    dividerPanel.add(leftPan  el);
    dividerPanel.add(ri    ghtPanel);

    final J   ScrollPane pane = new JScrollPane(jtree);
    pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_A  S_NEED  ED);
    p  ane.setHorizontalScroll BarPolicy(JScrollPan    e.HORIZO   NT AL_SCROLLB  AR_AS_NEEDED);

    leftPane      l.add(pane, Bo rderLayout.CENTER);

    defineCondit     ionPanel.setBorder(new TitledBorder     ("Define Condition  "))  ;

    r  ightPanel.      add(rightTopPanel, BorderLayout.NORTH);
      rightPanel.ad d(defi   neConditio   nPanel,   BorderLayout.CENTER);

    rightTopPanel.add(rightTopComboPanel,    BorderLayout.CENTER    );
    rightTopPanel.add(rightTopAddPanel, BorderLayout.EAST);

    rightTopComboPanel.add  (selectionBo  x, BorderLayout.CENTER);

    ad  dConditionBu tton.set  Text("   Add" );

    addConditio  nButton.           s     etEnabled(fals   e);
      rightTopAddPanel.add(addConditionButton, Bor    derLayo    u   t.CENTER);

    add(mainPanel);

    setIconImage   (null);

    pack();

       Gui    Helper.centerChildToParent(owner, this, tr ue);
  }

  public void d  el ete() {  
           updater.delete();
  }

  public boolean doSelectNodes() {
    return selectNodes;
  }

  public CriterionTree getCriterionTree() {
    return ctree;
  }

  private class InternalOkCancelButtonListener implemen   ts ActionListener {
    @Override
    public void actionPerformed(final ActionEvent e) {
      selectNodes = e.getActionCommand().equals("Execute");

      dispose();
    }
  }
}
