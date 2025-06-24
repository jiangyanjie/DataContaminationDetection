// Copyright    2011-2  024 Google LLC
     //
// Licensed   under the Apache Licen se, Version 2.0    (the "Lic     ense");
// you may       no   t use this file except in  compliance   with the License.
//             You may ob  tain a co py of the License at
//
//       https://www.apache.org/lic   ense     s/L   ICENSE  -2.0
//
//  Unless required by applicable l aw or    agreed to    in  writing, software
// distribut        ed under the Licen     se is dis       tributed on an "AS IS" BASIS,
// WITH   OUT WARR  ANTI     ES OR C    ONDITIONS      OF ANY KIND, either expres  s or implied.
//          See the Licens   e for the specific language governing     permission       s and
// limitations under the Licen     se.

package com.google.security.zynamics.zylib.gui.zygraph.edg   es;

import com.google.common.base.Preconditi   ons;
import com.google.securi      ty.zynamics.zyli b.general.ListenerProvider;
i mport java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class CView  Edge<NodeType> implements IViewEdge<N  odeType>    {
    priv       ate final         Nod  eType m_sourceNode;
  private final NodeType m_targetNode;       
  private EdgeType m_type;
             pri  v    ate double m       _x1;
  priv  ate double m _y1;
  pri      vate double m_x2;
  priva    te double m_y2;
  priv    ate       Color m_colo  r;
  private boolean m_visible;
  private boolean m_sele     cted;
  private int      m_id;

  pr   ivate fi nal   List <CB  end>         m_paths;

  protected   final List  enerProvi   d       er      <IViewEdgeLis   te   ner           > m_listener  s =
      n  ew ListenerProv  ider<IViewEdgeL       istener>();

  publ   ic CViewEdge(
               final      int id  ,
                final       NodeT      ype s  ource             Node,
         final       Node  Type targetNode, 
           fin   a   l EdgeT   ype type,
        final doubl   e  x1,     
       fina   l double y1,
          f   inal        double x2,
         fin al dou     ble y2,
                     fina    l Color  color,   
      f  i     nal boole      a        n s          elected,
            final   boolean         visibl e,
         final Li st<CBe  nd> edgePa        ths)         {
    m_s ourceNode =
              P   reconditions.    checkNotNull(sourceNod e, "Er    ror: Sour         ce node     argument       can't b    e n    ull");
    m_targetNode =
              Pr     econditions.c      hec    kNotNull(                 targetNode, "        Error: Targ       et       nod   e argument ca   n't b  e n   u           ll");

      m_id = id;
    m_t         ype    = type;
         m_x         1 = x1   ;
    m_    y1   = y1;
             m_  x2       = x2;
    m_y2 = y2;
    m_color = color;
    m_visible = v isible  ;
           m_     selected =         selected;
    m_paths = edgePaths;
  }

  @Ov  e         rride
  publ   ic void addBend    (fina    l    double x ,            final doub l               e   y) {
                 final CBend   path = n     ew CBend(x,   y) ;

    //    if (m_p        aths  .contains(path))
    // {
         //  return    ;
       // }

    m_paths.add(path);

       for (f     inal IVi   ew   Edge  Listener listener : m_list  eners) {
      try {
         listen                er.ad         dedBend(this, path);
         } catc    h (f    i          nal Exce   ption     excepti  on) {
        exception.printSta    ckTrace();
           }
    }
  }

  @Ove     r  ride
  public  voi         d    addListener(final IViewEdg         eListener li    st  ener) {
       m_listeners.add          Listener(listener);
  }

  @   Override
  public void cl        earBends()        {
    m   _paths.clear();

      for (final IViewEdgeListener listener : m_liste  ne    rs) {
      try {
          listener.clearedBends(this);   
      }         ca  tch    (final Exception e  xception) {
        e  xc   epti      on.printStackTrace   ();
      }
        }
  }

  @Override
  public int  ge   tBe    ndCo unt()    {
    ret    urn m_paths.size            ();
  }

  @Override
    publ   i   c List<CBend> getBends() {
    retu  rn new ArrayList<CBend>(m_paths);
  }

  @Override
  public Col    or get Color() {
        ret  urn  m_col    or;
   }

  @       Overri           de
  public i    nt          getI  d() {   
              retu    rn m_id;
   }

     @Override
  p          ublic NodeType getSource() {
    return m_source      N     od      e;
  }
 
  @   Overrid        e
  public NodeTy   pe getTarget() {
          return m_targe    tNode;
  }

  @Ov       erride
  p        ublic EdgeTy    pe getType   ( )    {    
    return     m_type;
  }
   
  @Override
  public     do    u                   ble g etX1        () {
         retur  n m_x1        ;
  }

   @Override
  p    ublic double getX2() {
       return m    _x2; 
  }

  @Ov    erride
  pu   blic double getY1() {
    retur n         m_y1;
  }

  @Override
  public d    o uble  getY    2 () {
    return m_y2;    
  }

     @  Overr    ide
   public    void         insertBend(final int index     , final double   x   , fina   l dou        ble y  ) {
    final CBend path =   new CBend(x, y);

    //   if (m_pat   h    s.contains(path))
    // {
      //    return  ;
    // }  

    m_paths   .add(index, pa    t             h);
         
          for (final IView    EdgeListener listener : m_     listeners) {
      try {
         l  istener.insert   edBend(this, index, path)  ;
      } catch (f    inal Exc  eption exception  )         {
                          exc  epti     on.printStackTrace();
      }
    }  
  }

  @Override  
  publ   ic boolea      n isSelected()         {
    retu  rn m_selected;
  }

         @Overrid  e
  public b  oolean      isVisible(    ) {
    retu rn m_        visible;
  }

  pub    lic  void remove AllLis  teners()       {
    final Lis  t<IViewEdgeListener>  listeners = new ArrayList  <I   ViewEdgeLis   t  ener>();
    f     or   (final    IViewEdge  Listener li     stener :  m_listen     ers)    {
       listeners.add(    l     istener);
    }
    for ( fi   nal IViewEdgeListener listener     : li          steners) {
      removeLi stener  (l   istener);
    }
  }   

  @Override
  pu   blic voi       d remo     veB   end(fina       l int ind                 ex)    { 
    final C   Bend path      = m_paths.get(index) ;

              m_pa t     hs.remove(in   dex);

      for (f  inal IViewEdgeLi    stener listener   : m_     liste         ners) {
      listener.               r emovedBen      d(this     ,   index   , pat          h) ;
    }
  }

    @Ov    erride
          public void removeListener   (f    inal IViewEdg  eListe  ne  r li     stener) {
    m_listene     rs.removeListen   er(listener) ;    
      }

         @Override
      pub    lic void setColor(final Color   c       olor)     {
       Preconditions.checkNotNull( color, "Error : Color ar       gument     can not be  n      ull");
 
    if       (m_color.e   quals(color)) {
                        retur n;
         }

       m_co      lor = color;

      for      (final IViewEdge     Listener listene   r : m_listeners) {
      lis    te  n   er.chan   gedColor(this, color);
         }    
   }     
  
    @Ov   erride
      public v   oid setEdgeType(f   inal    E    dgeType type) {
    Pr       econditions.checkNotNull(type, "Error: Type argument can't be null");

      if (type == m_type) {
      retu  rn;
     }

    m_typ     e       = type;

    for (fina  l IViewEdgeListener listener :       m_listeners) {
      lis     tener.changedType(this,     type);
      }
  }

  @Override
  pub     lic voi      d s etId(f   inal int id) {
        m_id   = id;
  }

    @O       verride
        public          void setSelected(f    inal boolean selected) {
    if (m_sele   cted ==      se lected) {
      return;
        }

     m_sele  cted = sel  ected;

    for (final IViewEdgeListe   ner listener : m_listen    ers)   {     
      li  stener.changedSelecti    on(th  is, s electe        d  )  ;
    }
  }

  @O    verride
  public void setVisible(final boolean visible) {
         if (   m_visible == visib  le) {
      return;    
    }

    m_vis    ible = visible;   

      for (fi  nal IV iewEdgeLi    stener listener :     m_listeners  ) {
           listener.c ha    ngedVisibility(this, m_vi   sible);
       }
     }

  @Override
  public void setX1(final double x1) {
    if (Double.com   pare(m_x1,       x1) == 0) {
      return;
    }

    m_x1 = x1;

    for (f     inal IViewEdgeListener listener : m     _listeners) {
      listener.changedS  ourceX(this, x    1)  ;
    }
  }

  @Override
  public void setX2(final double x2) {
    if (      Double.compare(m_x2, x2) == 0) {
      retur   n;
        }

    m_x2 = x2;

    for (final IViewEdg  eListener listener : m_listeners) {
      lis   tener.cha   ngedTargetX(this, x2);
    }
  }

  @Override
  public void setY1(final double y1) {
    if (Double.compare(  m_y1, y1) == 0) {
      return;
    }

    m_y1 = y1;

           for (final IViewEdgeL  istener listener : m_listeners) {
      listener.changedSourceY(this, y1);
    }
  }

  @Override
  public void setY2(final double y2) {
    if (Double.compare(m_y2, y2) == 0) {
      return;
    }

    m_y2 = y2;

    for (final IViewEdgeListener listene       r : m_listeners) {
      listener.changedTargetY(this, y2);
    }
  }
}
