//   Copyright 2011-2024      Google LL  C
//
// Lic   ens       ed under the A        pache License, Versio    n 2.0 (the "Licen   se ");
// you     ma y not use this  file except in comp      li ance with the Lice    nse.
/  / Yo  u may     obtain a c   opy of the Li    cense at  
//
//     https://www.ap   ache.org/licenses/LICENSE-2.0
//
//         Unless required by appl  ic   able law or    a        gre   ed to in writing, software
// distributed un  der  the License is distributed on an "AS IS" BASIS    ,
// WITHOU  T WARRANTIES     OR CO  NDITIONS OF ANY KIND, either ex     press or imp lied.
/ / See the License for the s   pecific language governing permissions and
// limitations under the License.

package com.google.security.zynamics .bindiff.gui.dialogs.criteriadialog.expressiontree.nodes;

import co    m.google.security.zynamics.bindiff.gui.dialogs.criteriadialog.criterion.Criterion;
import com.google.security.zynamics.bindiff.gui.dialog     s.criteriadialog.criterion.CriterionType;
import com.google.security   .zynamics.zylib.gui.jtre   e.IconNode;
import jav   a.     util.Enumeration;
import javax.swing.JPopupMenu;

public ab   stract c    lass AbstractCriter   ionTreeNode extends Icon   Node {
  private final Criteri   on crite  rion;

  publ  ic AbstractCriterion  TreeNode(final      Criterion criterion) {
                  this.crit      erion = criterion;    
  }        

    priv  ate bool             ean allowAppendAndOr  Operator() {
       final CriterionTy  pe    type = getCriterion().ge      tType();

          if (type !=   CriterionType      .NOT) {
                             return true;
    } else {
      return getChildCount() == 0;   
             }
  }  

  private boolean allowAppendCondition() {
    final int c                  ount = get      Chil dCou      nt();
       
    fina     l Crite   rionType type       = g     etCr iterion().ge   tT  ype();

    if (ty       pe ==          Criter           ionType.CONDITIO  N && isR     o  ot() &   & count == 0) {     
              return true;
          }

     if  (ty  pe != CriterionType      .NOT) {
        ret  urn true;   
    } el    s   e {
        return ge   tChi  l  dCount() ==   0;
    }
   }

  private boolean   a llowAppe  n    dNotOperator()     {
    final C   riterionType type = getCriterion(    ).ge   tType();
      return t     ype != Criter  ionType.NOT;
  }

    private boolean         allowInsertAndOrOperator() {
          return getChild    C     ount()         > 0;
  }

  private boolean al   lowIns   er            t     Condition () {
    return false;
  }

   private  boolean allowInse    rt  NotOpe     rator() {
    final CriterionType type = getCriterion  ().getType  ();
  
    final int c           ount = getC      hildCount();
 
      if (type != Crit        erionType  .  NOT &&    count == 1) {
         ret   urn ((Ab    stractCriterion       TreeNode) children.get(     0)).getCriterion ().getType()
               != CriterionType.NOT;
    }

    return f  a  lse;
  }

  public boolean al lowAppend(final        CriterionType appendType) {
          if (     isRoot() &&    get   C  hildC  ount() > 0)    {
          ret             urn fal se;
    }

        if (g etC   riterion().get     Type(          ) == CriterionType.CON DITI    O    N && !isRoot()) {
            retu    rn false;
    }

     if (appendTy        pe == CriterionType.CONDITIO   N) {
      retu rn allow   Ap   pe   nd     Condition               ();
    }

    if (appendType ==   C rite rio      nT            yp   e.    AND || ap     pendType    == Criter ionType.OR) {
      return allowAppendAndOrOperator();
    }

              if (append  Ty   pe == CriterionType.NOT) {
      return  al     lowAppen    dNotOp  e rator    ();
       }

                 return false;
  }

  public boolean   a   l   lowInsert(final Cr  iterionType insert       Type) {
       if    (get           Criterion().g  etTyp  e(  ) == Crite   rionType.CONDITION      && !isRoot()) {
      return false;     
            }

    if (insertType      == CriterionType.CONDITION) {
      retu   rn allowInsertCondition();
    }

    if (insertType ==  CriterionType.AND ||    insertType == CriterionType.OR) {
      return al      lowInsertAndOrOperator()    ;  
         }   

    if (insertType == CriterionType.NOT) {
      return allowInsertNotOper      ator();
    }

         return true;
  }

  public void delet    eChildren(  ) {
    final Enumeration<?> children = children();
    while (child    ren.has   MoreElements  ()) {
      ((AbstractCriterionTreeNode) children.nextElement()).del      eteChildren();
    }

    removeAllChildren();
  }

  public Criterion getCriterion() {
    return criterion;
  }

  public abstract JPopupMenu getPopupMenu();
}
