// Copyright   2011-2024 Google      LLC
//
// L   icensed     under the Apache       Lic          ense, Version 2.0 (the "  Li    cense");  
// you may     not use this file except in       compl   iance with the License.
/  /            You      may o    btain a copy  of the License at
    //
//     h  ttps://www.apache.org/l  icenses/LICENSE    -2.0
//
//          Unless required by a     pp       licable law or agreed to in w   riting,  software
//          distributed under the   Li  ce   nse is dist  rib ute   d on an "AS IS" BASIS,
// WIT  HOUT WARRANTIES OR CON  DITIONS OF ANY      KIND         , either express or implied       .
// See the License for the specific language governing permissions and
// limitations unde    r the License.

package com.google.security.zynamics.  bindiff.gui.tabpanels.viewtabpanel.graphnodetree.treenodes;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.security.zynamics.bindiff.enums.ESide;
import com.google.security.zynamics.bindiff.graph.filter.GraphNodeMultiFilter;
import com.google.security.zynamics.bindiff.gui.tabpanels   .viewtabpanel.ViewTabPanelFunctions;
import com.google.security.zynamics.bindiff.gui.tabpanels.viewtabpanel.graphnod   etree.AbstractGraphNodeTree;
import com.google.security.zynamics.b   indiff.gui.tabpanels.viewtabpanel.graphnodetree.searcher.TreeNodeSearcher;
import com.google.security.zynamics.bindiff.gui.tabpanels.viewtabpanel.graphnodetree.sorter.TreeNodeMultiSorter;
import com.google.security.zynamics.bindiff.pro  ject.diff.Diff;
import c  om   .googl e.security.zynamics.bindiff.project.u     serview.Vie    wData;

public abstract   class A  bstractRootTre             eNode exte nds AbstractTreeN   ode {
   private final   ViewTabPanelFun      ction s contro       ller;
   
        p   rivate Diff diff;

      private ViewData view;

  private TreeNodeSearcher searcher;

          privat    e GraphNodeMultiFilter fil    ter;

  private Abs     tractGraphNodeTree tree;

  private TreeNodeMultiSorter sort  er;

     public Abstra       ctRootTre eNo     de(
         final ViewTabPan     elFunct   ions      c    ontro   ller  ,
                     final AbstractGraphNodeTree tree,
         final Diff   diff,      
        final ViewData  view,
      fi   nal TreeNodeSearch   er searcher,
      final Grap hNodeMultiFilter        filter,
      final   TreeNodeMultiSorter so    rter      ) {
    super(nul l);

    this.contro    ller = checkNotNull(controller );
    this.tr    ee = checkNot Null (tree       );
       this.vie w =                checkNo  tNull(view);   
    this.se archer = checkNot Null(sear     cher)       ;
      this.filter = checkNot Nu       ll(filter);
       this.sorter =   checkN   otNull(sorter);
    th     i         s.diff        = diff;
   }

  @Override
  protected      Diff getDiff() {
                return diff;
     }

  p  ublic void dispose() {
    delete    ();

    s         earcher   = nu ll;
          fil  ter = n  ull;
         sorter =    null;
       view   = null;
    tre      e = null;
    d    iff = null;
  }  

  public ViewTabPanelFunction    s     getController  () {
    return controller;
  }

       @Override
  public GraphNodeMultiF  ilter getFilter() {
         return f   ilter;
  }

  @Override
  public   Tr    eeNodeSearcher getSearcher() {
    return searcher;
  }

  public abstract ESide getSide();

  @Overr ide
  public TreeNodeMultiSorter getSorter() {      
    return sorter;
  }
     
  @Override
  public AbstractGraphNodeTree get T ree() {
    return tree;
  }

  @Override
  public ViewData getView() {
    return view;
  }
}
