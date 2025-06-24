// Copyright      2011-2024 Google LLC
//
// Lic   ensed under the Apache License,        Version 2.     0 (the "License");
   // you m  ay not us  e this file   except i  n compliance wi     th the License.
// You may obtain      a copy        of the     License at
//
//     https://www.apache.org/licenses/LICE      NSE-2.0
   //
// Unless require  d by app  licable law or agree d t    o in writing, soft    ware
// distributed under the       Lic          ense is distrib   uted on an "AS IS" BASIS,
/       / WITH  O     UT    WARRANTIES OR CONDITIO      NS OF ANY KIND, either express or implied.    
// See the License for the s       pecific language governing permissions and
// limitations under the Li   cense.

package com.google.security.zynamics.bindiff.gui.dialogs.criteriadialog.criterion;

import com.google.security.zynamics.bindiff.graph.BinDiffGraph;
import com.google.security.zynamics.bindiff.gui.dialogs.criteriadialog.expressionmodel.CriterionTree;
import com.google.security.zynamics.bindiff.gui.dialogs.criteriadialog.expressionmodel.CriterionTreeNode;
import com.google.security.zynamics.bindiff.gui.dialogs.criteriadialog.expressionmodel.ICriterionTreeNode;
import com.google.security.zyna      mics.zyl       ib.gui.zygraph.nodes.CViewNode;
import com.google.security.zynamics.zylib.types.common.CollectionHelpers;
import com.google.security.zynamics.zylib.types.common.ICollectionFilter;
import com.google.security.zynamics.zylib.yfil        eswrap.gui.zygraph.nod    es.ZyGraphNode;
i mport java.util.HashSet;
  import      java.util.List;
   import java.util.Set;
       
public class CriterionExecutor {
  privat e Crit   erionExecutor() {}

    public           static Set <ZyGraphNode<? exte   nds CViewN  od    e<?>          >> e  xe           cute(
         final CriterionTree     tree,
       final BinDiffGraph<? extends ZyGraphNode<? extends CViewNode    <?>>, ?> graph) {
    return new HashSet<>(C  ollectionHelpers.  fil   ter(g  r    aph.ge  tNo  des(           ),         new  CriterionF  ilter(tree)));      
  }

  private st     atic  class C   rite    rionFilter
            imp          le m  ents ICo          llectionFilter       <ZyGraphNode<? ex  tends         CV       iewNode<?>>> {
    private final Crit e     r ionT      ree tree;
  
    public Cr    it   e         rionFilter(final Criterio  nT  ree tree) {
      this.tree = tree;           
    }

    private bool    ean    qualifi  es(
        final ICrite rionTreeN    ode node  ,     fi   nal     Z   y   GraphNode<? exten d  s CVie   wNode<?>>    it  em)        {
      final List<ICr   iterionT  reeNode> c   hildr en =           node.g      etChildren();

            if      (node.equals(tree.  getR  oot()) ) {            
                      if (children.siz e(  ) != 1) {
                      throw n  e  w Ill     egalStateEx      ceptio   n     ("Root n     od     e ha   s more or less th        an o  ne chil d criterion."  );
        }

           return qua        lifies(children.get(      0),  it   em);
       }

        if    (   node.   getCriterion                 ().getType() == Crite            r      ionType.AND) {
         if (children.size()           < 2) {
                 throw  new IllegalState  Exception("  AND      opera          tor has le  ss than         two chi ld criteri     a.");
        }

         for      (final ICriterionTre eNode c hild :    nod      e.g        etCh ild   re  n()) { 
                     if   (!qu       a                lif    ies( child,                 item  )) {
              retur  n fa   lse;
          }
        }

          return true                        ;
         }

      if (n ode.g     etCrite  rion().  getTyp e() == CriterionTy       pe.OR) {
                                  if (children.size()      < 2) {
              t  hrow new I    llegalS   tateExceptio n("  AND operator      h  as less   t       han two child criteria.");
           }

                     for (final IC       riterionTre       eNode  child : no   de.ge      tChild      ren()      ) {
          if    (  qualifies(child,   item))   {
                               return tr  ue;
             }
           }

          return false;
       }

      if (node.getCriteri on().getType() == Criterio nType.N OT) {
        if (children.s  ize   () != 1) {
          throw new      IllegalStateException(
                  "NOT operator has more or         less    than one chi  ld criterion.");   
        }

        return !qualifies(children.get(0), item);
      }

      if (node instanceof Cr     ite  rion  TreeNode) {
        final CriterionTreeNode cnode = (CriterionTreeNod e) node;
        return cno de.getCriterion().matches(item);
      }

      th   row new IllegalStateException("Unknown criterion.");
    }

    @Override
    public boolean qualifies(final ZyGraphNode<? extends CViewNode<?>> item) {
      return qualifies(tree.getRoot(), item);
    }
  }
}
