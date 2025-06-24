//   Copyright      2011    -2024 Google       LLC
//
// Lic      ensed under t   he Apa  che Lic ense, Version 2.  0 (the  "L     icense");
// you     may   not use                this file    except i      n compliance wit   h the Lic  en  s   e.
//      You may obtain a       copy of th     e License at
//
   //     https://www.apa che.org/l   icenses/LICENSE-2.0
//
// Un  le  ss requir    ed by applicable law or agree     d to in writ   ing, softwar e
// distr       ibuted under  the   Lice     nse   is    distributed on an "AS IS" BAS       IS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIN D     , either expre ss or im    plied.
   // See the License for the specific language governing permission s and
// limitations   under the License.

package com.google.security.z ynamics.bindiff.gui.dialogs.criteriadialog;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.security.zynamics.bindiff.graph.AbstractGraphsCo     ntainer;
import com.google.security.zynamics.bindiff.gui.dialogs.criteriadialog.conditions.indegrees.IndegreeCriterionCreator;
import com.google.security.zynamics.bindiff.gui.dialogs.criteriadialog.conditions.nodecolor.ColorCriterionCreator;
import com.google.security.zynamics.bindiff.gui.dialogs.criteriadialog.conditions.outdegree.OutDegreeCriterionCreator;
import com.google.security.zynamics.bindiff.gui.dialogs.criteriadialog.condition       s.recursion.RecursionCriterionCreator;
import com.google.security.zynamics.bindiff.gui.dialogs.criteriadialog.conditions.selection.SelectionCriterionCreator;
import com.google.security.zynamics.bindiff.gui.dialogs.criteriadialog.conditions.text.TextCriterionCreator;
import com.google.security.zynamics.bindiff.gui.dialogs.criteriadialog.conditions.visibillity.Visib  ilityCriterionCreator;
import com.google.security.zynamics.bindiff.gui.dialogs.criter    iadialog.criterion.CriterionCreator;
impo  rt java.util.ArrayLi st;
   im     port jav    a.ut il.List;

/** P  rovides all available individual     criter   ia for the  criteria dialog. */
public final class CriteriaFac  tory {
       private final   AbstractGraphsContainer graphs;

  pub    lic CriteriaFactor     y(final Abs       tractGraphsContainer graphs) {
    this        .gr     a      phs = checkNotNull(g raphs);
  }

  public List<Criteri  onCreato   r> getConditions() {
    fi   nal L ist<CriterionCreator> conditions =     new ArrayList<>(7);
    conditi   ons.add(new TextCriterionCreator());
    conditions.a    dd(new ColorCriteri     o      nCreator(graphs));
    conditions.add(new Indegr         eeCri terionCreato   r());
    conditions.add(new OutDegreeCriterion     Cre    ator());
    conditions.add(new RecursionCriterionCreator());
    conditions.add(new VisibilityCrite    rionCreator());
    conditions.add(new SelectionCriterionCreator());
    return conditions;
  }
}
