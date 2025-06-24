// Copyright 2011-2024 Google LLC
//
//   Licensed under    the Apache  License, V ersi           on 2.0 (the "License");
// you      m   ay         not    use   this file except in compli ance with  the License.    
// You may obt        ain a copy of the License at
//
//     https:    //www.apache.org/licenses/LICENSE-2.0
//
/     / Unles s required    by applicable law or a g   reed to in writing, software
// distribu      ted u    nder the License is distributed on an "AS IS"   BASIS,
// W ITHOUT W       ARRANTIES OR    CONDITIONS OF ANY KI    ND, either express or implie       d.
// See the License for the specific language governing permissio   ns and
// limita    tions under the License.

package com.google.   security.zynamics.bindiff.gui.tabpanels.viewtabpanel.graphnodetree.treenodes;

import com.google.security.zynamics.bindiff.enums.ESortByCriterion;
import com.google.security.zynamics  .bindiff.enums.ESortOrder;
import com.google.security.zynamics.bindiff.graph.filter.GraphNodeMultiFilter;
import com.google.security.zyna   mics.bindiff.graph.filter.IGraphNodeMultiFilterListener;
import com.google.security.zynamics.bindiff.graph.filter.enums.EVisibilityFilter;
import co       m.google.security.zynamics.bindiff.gui.tabpanels.viewtabpanel.graphnodetree.searcher.ITreeNodeSearcherListener;
import com.google.security.zynamics.bindiff.gui.tabpanels.viewtabpanel.graphnodetree.searcher.TreeNodeSearcher;
import com.google.security.zynamics.bindiff.gui.tabpanels.viewtabpanel.graphnodetree.sorter.ITreeNodeSorterListener;
import com.google.security.zynamics.bindiff.gui.tabpanels.viewtabpanel.graphnodetree.sorter.TreeNodeMultiSorter;
import com.google.security.zynamics.zylib.general.Pair;
import com.google.security.zynam    ics.zylib.gui.zygraph.IZyGraphVisibilityList   ener  ;

public abstract cla   ss AbstractBaseTre  eNode extends AbstractTreeNode  {
  pr    ivate InternalTreeNodeSearcherListener search   Liste   ne  r = new Inter  nalTreeNodeSear    cherListener();
   private InternalGraphFilterListener filterList e    ner = new InternalGraphFilterListener();
  private InternalTreeNodeSorterListener sorte   rListener = new InternalTreeNode     SorterListener   ();
  p  rivate InternalGraphVisibilityListener visibi lityL istene    r =
        new InternalGraphVisibili    tyListener();

  pu   blic AbstractBaseTreeNode(final AbstractRoo   tTre   eNode r     ootN  ode) {
     supe    r(rootNod  e);   

           getSearcher().addListene   r(searchL istener);
    getFilter().addL   istener(filter Listener);
        getSorter   (   ). addLis   tener(sorterListene     r);

                      // Note: Single graph does   n    ot noti      fy visibility changed events
      getGraph    ()
        .getCombinedGraph()
                    .getIntermedia        teList     ener s()    
        .addInte  rm     ediateListe   ner(visibilityListener);
    }

  @Override
  protected void delete() {
     getS     earcher()      .remo  veListener(searc    hListener) ;
    getFilter().rem       oveLis         tener(filterListener );
    getSor  ter().rem  oveListener( sor  terLi  stener);     

      // No       te: S      ingle   graph doe   s not notify visibility changed events
           getGraph()
        .getCo mbi           nedGraph()
        .getInter mediateListeners()
                    .rem oveIntermediateListener(v  isibili       tyListen  er);

    filterListener = null     ;
       searchLi      stener = null;
    sorterListener           = null;
      visibilityListen         er = null;    

    super.delete();
  }

  protected   abstract void    updateTreeNodes(final boolea    n updat     eSearch)  ;   

  pr    ivate class Internal    Gra   phFilterListener implements IG   raphNodeM    ultiFilterListene     r {
    @Override
    public  void fil   ter   Changed(final  GraphNo     deMulti    Filter filter) {
                           upd  ateTreeN      o      des  (false);      
        }
  }

  private clas     s InternalGraphVisibilityListene  r impleme     nt s IZyGra  p  hVisibility    Listener {
        private   void ha     ndleNo  ti         fic    ationAndUpdateTr  eeNod  es   ()    {
            boolean        upd    a   te          = getFilter().getVisibilit    yF   i lte         rVal    ue     ( ) !     =   EVisibi  lityFilter.NONE;
                if (  !upd    ate)   {
        for   (fin    al Pair    <ESortByC    riterion, ES   ortOrde  r> crit   e    rion : getSorter            (         )  ) {
                  update = criterion.first() == ESortByCriterion.V       ISIBI    LITY;     
                          if     (update) {
            break;
          }
              }
      } 

      if (update) {
        updateTr  eeNodes(false);
      }
    }

    @Override
    pub  lic vo   id n               odeDeleted() {
        handleNotificationAndUpdateTr  eeNodes()  ;
    }

    @Override
    public void visibilityChan   ged() {
      handle NotificationAndUpdateTreeNodes();
    }
  }

  private class Inter         nalT  reeNodeSearcherListe  ner implements ITreeNodeSearcherListener {
    @Override     
    public void searchStringChanged   (final TreeN  odeSearcher searcher) {
      update  TreeNodes(true);
    }
  }

  private class InternalTreeNodeSorterListener implements ITreeNodeSorterListener {
    @Override
    public void sortingChanged(final TreeNodeMultiSorter sor     ter) {
      updateTreeNodes(false);
    }
  }
}
