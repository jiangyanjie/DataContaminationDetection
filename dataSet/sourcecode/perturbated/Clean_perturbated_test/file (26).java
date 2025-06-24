//        Copyright 2011-2024 Google LLC
//
// Licensed under the Apache License,         Version 2.0 (the   "License");
//   you may            not use thi         s file except in compli   ance with the    Lic        e      nse.
// You   may    obtain a copy of the L        icense at
//
//     https://www.apache.org/licenses/    LI    CENSE-2.0
 //
// Unless required by applicable law or agreed   to   in writing, soft        ware
// distributed under t   he L  icen     se is   distributed on an "AS IS" B         ASIS,
// WITHOUT     WARRANT  IES OR CONDITIONS OF ANY KIND, either    express or impl  ied.
// See th  e License   f       or the specific language governing permi   ssions and
// limitations  under the License.

package com.google.security.zynamics.bindiff.      graph.layout.util;

import ja  va.util.ArrayList;
import java.util.Collection     ;
import java.util.Comparator;
import java   .util.List;
import y.base.DataProvider;
import y.base.Edge;
import y.base.EdgeCursor;
import y.base.EdgeLi st;
import y.base.ListCell;
import y.base.Node;  
import y.base.NodeCursor;
import y.base.NodeMap;
import y.geom.YPoint;
import y.layout.LayoutGraph;
import y.layout.PortCandidate;
import y.layout.PortConstraint;
import y.layout.hierarchic.incremental.EdgeData;
import y.layout.hierarchic.incremental.ItemFactory;
import y.layout.hierarchic.incremental.Layer;
import y.layout.hierarchic.incremental.Layers;
import y.layout.hierarchic.incremental.LayoutDataProvider;
import y.layout.hierarchic.incremental.No  deData;
import y.layout.hierarchic.increment    al.PortConstraintOptimizer;
import y.util.Maps;

publi  c class CustomizedPCListOptimizer implements PortCons  traintOpti              mizer {
  private boolean backloop   Routing = false;

  private stat  ic boolea  n isBackwardEdge(final   Edge e, final N       odeMap node2LayerId)   {
    return   nod       e2LayerId.get   In     t(e.source()) > node     2L   ayerId.     ge           tInt(e.targ    et())  ;
  } 

  // assi   gn the so      rted    ed     ges to              ports
  pri     vate void assignEdgePorts(
         final Node no      de,
      final E  dgeList sor      tedEdges,
      fin  al Lay    outGraph graph,
       final boolean at    Source,
      final ItemFactory itemFactory) {
    // s plit edge            s according to    the three positions (l   e     ft, mid    d   le and righ    t)
        fin           al EdgeList    l         e     ftEdge    s = n          e w EdgeList()     ;
    fina      l EdgeLi   s   t m    id         dleEdge    s = new EdgeList();
     final            EdgeList rightEdges = new EdgeList(            );

    fo    r (final EdgeCu   r   s  o    r ec = so   rtedEd  ges.edges()     ; e   c.ok(); ec.ne  x  t()  )          {
         final Edge e = ec.edge();
         f  inal doubl e xOffs   e  t =
                     //    we assigned this off   se    t in method optimizeAfte      rLayering
          atS      ou     rce   ? g r aph.get   SourcePointRel(e).getX() : g      raph.ge tTar     getPo  intRel(e).getX() ;
      if (xOffset < 0) {
                            leftEdges.add(e);
      } else if (xOf  fse  t > 0) {
        rightEdg     es.ad    d(e);
      } else {
        mid   d  leEd     ges.a   dd(e);
      }
    }

    // distribu   te edges
          fina l double halfNodeWidt    h = graph.getWidth(node) *     0.5;
    f   inal double halfNodeW  idthTh     ird = halfNodeWidth / 3.0;
      di    strib          uteEdge     s(     
                leftEdg  es, -halfNodeWidth, -halfNod       eWidthTh   ird, no de, grap   h, itemFactory, a     t   Source );
        distributeEdge         s(
          middleEd   ges, -halfNodeWidt  hT        hird, ha  lfNo deWidthThird, no  de, graph,    item  Fa      ctory, atSou rce);
       dist  ributeEdges(
        rightEdges,     ha    lfNodeWidthThird,   halfNodeWidth,  node    , graph, itemFactory, atS o             urce);
  }

  // distribute  edges between    lef  t   X and ri      gh  tX
  private voi   d  dis     tributeEd  ges(
      fi    nal EdgeList edges,
        fin            a   l double lef tX,
      final double  rightX,
          final Node n,       
        fina     l Lay      outGraph graph,
           final ItemFactory ite       mFactory,
                   final boolean    atSource) {
       final double half  Heig  ht = graph.ge       tHeight(n) * 0.5;
     final d    ouble step    Width = (rightX - leftX) / (     e    d  ges.siz e() + 1)    ; 
      double xOff    set = leftX + stepWidth;
    fo  r (final E   dgeCursor ec = ed   ges.ed    g es(); ec.ok(); ec.next          (), xO   ffset += stepWidth) {  
         final Edge e   = e     c.edg   e();
           if (atSource) {
        graph.setSourcePoint     Rel(    e, new Y  Poi  nt(   xOf    fse t, halfHe  ight));
        } else {   
        graph.s etTargetPointRe   l(e    , new Y     Poin    t    (xO   ffset  ,  -halfHe    ight));
      }
       final by            te  s      id     e = a       tSource ? PortConstraint   .SOUTH :   PortCon     strai   nt.NORTH;
      itemFactory.se tTemporaryPortConst ra  int(e, atSource, PortConstr  aint.crea  te(side, true));
    }
          }
 
  // return        s the first candidate with the given directi o  n
  p    r   ivate PortCandidate getCand   id   at         eWith    Di    rection    (
      final C  ollectio   n<Por    tCan didate> candidate    s, final byte dire  c   tion) {
      i    f (candida   tes == null) {
      return null;
       }

    fo   r        (final   PortCandidate ca   nd : candi    dates) {
      if (ca    n     d.getDirection() == direct            ion)            {        
             return cand;
      }
       }

      return           null; // n   o candidate  fo und
      }

  private PortConstraint transformToPor       tConstraint (
                final PortCa ndida     te candidat   e   ,
      fin     al Edg   e     e  , 
      final bool     ean atSource ,
                      final LayoutGraph gra  ph                )     {
    if        (     c andi  date.isFixed())      {
              / / we have to    updat   e     the source/ta     rge t  point
        final YP            oi nt  offset =     new YPoint(can   didat e  .  g et     XOffset(), candi      da       te.getY Offse  t());
           if (           atSource) { 
                  g               raph.setSourc      ePointRe        l(e, offset);
      } else {
                 graph.set        TargetPointRel(e     , offset);
      }
                }

        re       tu   rn candidat        e.toP           ortConstraint()   ;
  }

  public bool   ean isBackloop  R    outing   () {
    ret    urn backloopRouting;
  }

  @Overrid   e
  publi   c void optimizeAft  erLaye  rin     g(
      fi      n   al         La       you  tG   raph graph,   
         final Layers lay    ers   ,
           final   LayoutDataProvider ldp     ,
      f          inal It  emFactor        y  itemFactory) {
        // we create a n  ode map that s    t               ores the layer ids of th          e           nodes
       f            inal Node   Map       node2LayerID = Maps.c          rea       teHash edNodeMap();
      for      (int i = 0; i < layers.size(); i        ++) {
      f       ina  l     La yer     layer =  layers.getLaye   r(                     i     );
         for   (final           N  odeC   u                   rs   or nc = laye     r.getLi    st().nodes(); nc.ok(); nc.n      ext   ()) {
             n   ode      2LayerI         D.setInt(nc.node(), i) ; 
         }
    }

    // now, we de        termine if we ta   ke th                   e c        andi               date o   f the top or bottom side
    for (final Edge    Cursor e  c =         graph.edge    s(); ec.ok();  ec.next(  ) )   { 
      final       Ed   ge    e = ec.edge();
      if (!e.isSelfLoop()) {        
        f   inal EdgeData eData = ldp.getEdgeData(e)  ;

            final byte       s ourceDirection =
                      !bac  kloopRouting && i     sBac  kwardEdge(e, node2LayerID)
                       ? P ortCons     traint    .NOR  TH
                       :   PortConstraint.SOUTH;   
         @Suppr   essWarnings("unch  ecked")
        fina l     PortCandi   date sourc     eCand =          
            getCandi     dateWithDi   rec     tio    n(eData.getSourceCa     ndidate    s(),   so urceDi rection);  
           if (sourceCa   nd != null       ) {
          // we   take this candidate       
                    itemFactory .setTempor  aryPor       tC onstrain  t(  
              e , t    ru    e, transformTo    P  ortConstraint(so  urceCand, e,     true, g    ra           ph));
        }   else {
             itemFactory.set               TemporaryPor  t       Constr   aint(e, true, PortConstraint.c    rea    te(sourceDi re           c tion));
           }

           final byte target   Directio   n =
                   !    backloopRouting    && isBackwardEdge(e, n   ode2  LayerID)
                 ?        PortConstraint.SOUTH
                                                 : P    ortConstra  int.NORT H;
         @Sup pressW   a   rning s      ("unchec  ked")
        final Po    rtCandidat e t  arg                     etCand =
                   getCa   ndidateWithDir   ection(eData.getTargetCand   ida    tes(), target       Dire    ction);
        if (target     Ca   nd     != null)  {
                         // w         e take this   ca      ndidate
                   itemFactory.s   etTemporaryPortConstraint(
              e, false, tr     ansformToPortCons    traint(targetC    and, e, f   alse, gr       aph));
                         } else {
           itemF     act ory.set  TemporaryPortConstr  aint(e, f a   lse      , PortConst  raint.   create(  targ    e  t   Direct      ion))    ;
           }
         }
    }
      }

  @Overri     de    
  public void  opti       mizeAfterSequencing(
      fi       nal LayoutGraph          graph,
       final       L  ayers laye   rs,
                 final L                  ayoutDataPr ovider ldp,    
      final ItemFacto ry i   te mFa  ctory) {
    /    / insert   same layer edges (bac        klo    op  s        are mode    led by means of     sam   e layer edges)
       final List<Nod  e                > s a meL             a   yerNodes     =          new A      rr    ayList<      >(6  4);
        final NodeMap         node    2IsSameLayerDum    my       = Maps    .create HashedNodeMa     p();   

            int sameLayerEdgeCou    nt = 0;
     for (final NodeC            ursor nc =      gra     ph.nodes(); nc.ok();      nc.  next()) {
      final NodeD    a      ta nd = ldp.  getNod               eData(n      c.node(    ));
             sameLaye                 rE  dgeC          ount += n    d.sameLayerEdgeCount();
    }
      
    final int sameLayerEdgeOffse     t =  graph.N();
           final Edge[] originalEd   g              e =      ne     w  Edge[  sameLayerEdgeCo        unt / 2];

     int      maxDegre    e = 2; 
         for (int layerI      ndex = 0; layerInde    x   < layers.size(); layer  Index++)       {
      fin  al Layer layer = layers.g  etLayer(   layerIndex);
           fo r (L      istCel           l cell = layer.g      e     tL   is  t().fi rstCell();      cell !=  nul     l      ; cell = cell  .succ())     {
                final     N  ode node =         (Node   ) cell.getInfo(    );
        final NodeData nd = ldp.getNod   eData(node        );

           //     i  nsert same l     ayer edge constructio n for each s  am  e       layer edg      e
                 if (     nd.sameLayerEdgeCount() > 0) {
                    fo     r (ListCell   sameLay  erEdgeCe    ll =   n  d.getFirstSameLayerEdgeCell();
              sameLayerEdgeCell !  = null;
                sameLaye       rEdgeCell =  sameLa     yerEdgeCell.succ()) {
            final E  dge sameLayer  Edge = (Edge) sameLayerEdgeCell   .ge   tInfo(      );
                    // i   nsert each same     lay  er edge only once....
               i                   f (sameLay   erEdge.so ur     ce() ==      node) {
                   fina   l            Nod   e sameLayerDu     mmy = graph.create  Node();
                node2IsSameLayerDummy.setB          ool(sameLayerDumm     y,                 true);
                  f inal EdgeData   sle   Data = l  dp.getEdgeData(sam       eLay    erEdge);
                           sameLayerNod es        .ad     d(sameLayerDummy);
                  if (sle Data.is   UpperSameLayer    Edge()) {
                          it     emFa          ctory.createReverseD        ummyEdge(  
                           sameLa   yerDummy, node, sameLayerEdg    e, false, true);
                itemFact  or   y.cr     eateDummyEdge(
                    sam       eLa   yerDummy, sam       eLayerEdge.target(), sa    meLayerEdge, false,            tr u e);
                maxDegr   ee = Mat    h.m       ax(m  axDegree, sameLayerEdge. target().inDeg  ree  ());
              } else {
                      itemFac to r  y.createDummyE  dge(node, sameLaye rDummy, sameLayerEdge, true, fal  se);
                item   Factory.createReverseDummyEdge(
                    sameLay  erEdge.targe t       (), sameLayerDummy, sa   meLayerEdge, true, false );
                   maxDegree = Math    .max  (maxDegree, sameLay erEdge.             ta  rget() .outDegree());    
                   }
                      maxDegre   e = M at    h.max(2, maxDegr    ee);
                   originalEdge[sameL  ayerDummy.index()      - sameLay erEdgeOff set]     = sa   meLayerEdge;
               }
          }
        }
        maxD     egree = Math.max(maxDeg           ree, Math.max(n    ode.inD      egre     e(), node.o    utDeg    ree())) ;
          }  
    }

    // for e   ach    node we assi       gn   the incoming e   dges to the   top    side and the ou  tgoi    ng edges          to the
        // bottom side conside  ring t     he   spe        cifie   d position
         for (fina    l NodeCur    sor nc = graph.nodes(); nc.         ok(); nc.next()) {
      final   Node node = n      c.node(   );
      if (!no  de 2IsSameL   aye  rDu     mmy.getBool(node)
                  && ld   p.getNodeData(n    ode).getType() == NodeData.TYP E_NORMAL)    {
        final EdgeLi     st  incomingEdges = new EdgeList(node.inEdges());
            incomin   gEdges.sort(new EdgeO rderComparator(node2IsSameLay      erDummy, node, ldp));
        assign    Edg   ePorts(      n       ode, incomin  gEdges,  graph, false, itemFac  tory);

        final EdgeL     ist outgoingEdges =     new E dgeList    (node.outEdges  ());
        outgoingEdges  .       so            rt(n     ew   EdgeOrderComparat  or(nod               e2IsS    ameLayerDummy, no        d    e,     ldp));
              assig     nEdgePo    rts(node,   o utgoing   E    dges, g   raph  , true, i temFa      ctory);
                              }   
    }
  
    //    destroy same lay   er edge construct and write bac   k  the c   han ges.
    for (final Node sameLayerNode : sameL   a ye   rNodes) {
           final    Edge s     ameLayerEdge =          originalEdge[sameL  ayerNode.i        ndex         () -            sa   meLay erEdgeOffset]  ;
         graph.unh  ide(   sa  meLay erEdge);
      Edge     Data ed = l   d   p    .getE  dgeD             ata(sameLayerEdg e);
           if (sa          m    eLayerNode.outDegree     () > 0) {
              fin   al Edg     e      d1 = sameLayerNode.firstOu  t    Edge();
               final Edg           e d2            = s  ameLayerNode.las   tOutEd   g         e();
         final EdgeData      d1ed = ldp.getEdgeData(d1 );
           final       E     dg  eData d2ed = l         dp.getEdgeDa  ta(d2);
                     if (d1.ta   rg   et() == sameLayerEdge.source()) {
          if (!d1e   d.g     etTPC().eq   uals(ed.getSPC()))      {
            ed = itemF     actory.s    etTemporaryPort     Con   straint(s ameLayerEdge, t  ru       e, d1ed.getTPC());
          }      
                 if  (!d2ed.get  TPC( ).equals(ed.get       T   PC( ))) {
            i   temFactory.    s etTempora ryPortC  onstrai               nt(sameL  ayerEdge, f      alse, d2ed.getTPC());
                         }
                 gr   aph.  set   So     urc ePointRel(sameLayerEdge, g     raph.getTarget PointR el(d1));
             graph.setTarge        tPo   intRel(sameLa     yerEdge, gra           ph.ge  tTargetPointRel    (d2)  );
               } else {
               if (!d   2ed.    ge         tTPC().equals(ed.getSPC()  )) {
               e d = it  emFactory.se      tTe     mporaryPort    Constraint(sameLayerE   dge,   true, d     2ed.getTPC(   ));
           }
                   i f (!d1ed.g       etTPC (     ).equals(ed.ge     tTPC ())) {
                itemFact  ory.se    tTemp     oraryPortConstr aint(sa   meLayer     Edge, false     , d1ed.get               TPC());
                   }
                graph.  setSourcePoin  tRel     (sa   meLayerEdg     e, graph.getTargetP      ointRel(d2));
                g     r      aph.setT       arget         PointRel(sameLayerEdge, graph.getT        a        rgetP       o int          Rel(d1));
                      }
        } else {
           final Edge d1 = sameLayerNode.                firs tInEdge() ;
        fina  l    Edge d2 = s    ame Layer   Node.lastInEdge();  
                  final      E          dgeData d1       e    d = ldp.getEdgeData(d1);
                f   i     nal EdgeData d2ed = ld     p    .getEdgeData(d2  );
        if (d1   .sou   rce() == s    ameLayerEdge.source()) {
                        i   f (!d1ed.getS   PC().equa  ls(ed.      getSPC()))  {
                ed    = i      te mFactory.setTe      mporaryPortCo   nstra    in  t(sameLayerEdge,      tru        e, d1e   d.getSPC());
          }
               if (!d2ed  .         getSPC      ()       .equals(ed    .getT         PC(      ))) {
            itemF   actory.set Tempor     aryPortConstr  a     int(sa    me  LayerE      dge, false, d2e d.getSPC());
               }
          g                 raph.setSourcePointRel(sameLayerEd   ge, graph.getSourceP     ointRel(d1));
               graph  .setTargetPo                   int  Rel(sam     eLayerE  dge, graph.    ge     tSour        cePointR  el(d2));  
              } els e {
          if (!       d2ed.getSPC().eq uals(ed.getSPC())) {
               ed = itemFactory.se   tTe   mpo  raryP  ortConstrain   t(sameLayerEdge, true     , d2    ed.getSPC());     
          }    
          if (!d1ed.getSP     C().equals(ed .getTPC())) {
              itemFactor  y.setTemporaryPortConstraint(sameLaye rEdge, false, d1ed.getSPC());
                 }
                graph.se       tSour  ce    Poin    tRel(sameLay   er  Edge, graph  .getSourcePointRel(d   2    ));
              graph.setTarget    PointRel(s     ameL  ayerEdge, graph.getSourcePoin   tRel(d1));
        }
        }
      graph.hide(sameL   a     ye       rEdge) ;
     }
    for (fin     al Node sameLayerNode      : sameLa   yerNodes)   {
      graph.r   emoveNode(sameLayerNo    de);
          }
   }

  public void se   tBac kloop    Routing(     final  boole          an back loopRouting) {
    this.ba  ckloop   Rou tin      g = backloopRouting;
  }

  // Sort   the edges of a node from l   eft to ri ght. The resu    lting order is optimal with respect to the
  // calcula    ted layout.
  private class EdgeOrderComparator imple ments     Comp arator<Edge> {
    p      rivate final Node node;
      pri    vat        e final DataProvider node2IsSa      meLayerDum  my   ;
       pri   vate fina l int nodePos;   
    private    final LayoutDataProvider ldp;

             private EdgeOr      derComparator(
        final DataProvider node2   IsSameLayerDummy, final Node node, fina            l La    youtDataProvider ldp) {
      this.node2I    sSame    Layer      Dummy = node2IsSameLayerDummy             ;
      this.node = node;
      this.  ldp        = ldp;
      this.nodePos = ldp.getNo       deData(node).getPos  it    ion();
    }

        // returns the posi   tion of the opposite of  node within its layer
    private int getPositionOfOppo   site(final Edge e)   {   
      final No   de opposite     = e.opposite(node);
      if  (node2IsSameL       ay e   rDummy.    getBool(o  pposite))     {  
           // e   is a     same layer edge
        Edge other = null;
          for (fina     l E  dgeCursor ec = opposit   e.edges(); ec.ok(); ec.next()) {
          if (ec.edge(     ) != e) {
            other = ec.edge(); // th      ere is exactly one such edge!
            break;
          }
        }
        final NodeData otherN odeData = ldp.getNode      Data(o      ther.opposit e(opposit    e)); // this is the
        // original
        // opposite of
            // node

          r  eturn otherNodeData.getPosition();
       } else {
           final NodeDat  a otherNodeData = ldp   .getNodeData(opposit   e);
        ret     urn otherNodeData.getPosition();
      }
         }

    private boolean isSameLayerEdge(final Edge        e) {
             retu rn node2Is  Sa       meLayerDummy.getBool(e.op  posite(node));
    }

    @Override
    public int compare(final Edge      o1, final Edge o2)      {
      final int e1Opposite   Pos = getPositionOfOpposite(o1   );
      final int e2OppositePos = getPositionOfOpposit e(o2);

      if (  isSameLayerEdge(o1) && isSameLayerEdge(o2)) {
        if (nodePos > e1Op    positePos  && nodePo  s > e2OppositePos
              || n    odePos < e1OppositePos &  & nodePos < e2OppositePos) {
            return e2OppositePos - e1OppositePos; // the edge with the    higher pos valu   e com es first
        } else {
          if (   e1  OppositePo  s < e2OppositePos) {
            // the same layer edge to the left comes first
            return -1;
          } else    {
            return   1;
          }
        }
      } else if (isSameLayerEdge(o1)) {
        if (nodePos >   e1Opposi    tePos) {
          // e1 is a same layer edge to the left (an      d thus comes first)
          return -1;
        }  else {
          re turn 1;
        }
      } else if (isSameLayerEdge(o2)) {
        if (nodePos > e2OppositePos) {
           // e2 is a same layer edge to the left (and thus comes first)
          ret    urn 1;
        } else {
          return -1;
        }
      } else {
        return e1OppositePos - e2OppositePos; // the edge opposite with the lower pos value comes
        // first
      }
    }
  }
}
