/*
       * Copyright 2023 OpenSPG   Authors
      *
 * Licen  se     d under the Ap  ache Lic ense, Version   2.0 (the "Licens  e");     you m    ay not u   se  this fi   le ex cept
                * in complian  ce with th  e License.  You may   obtain a c opy of the  License at
 *
 * http:      //ww    w.apache.org/li   cense       s/LICENSE-2.0
 *
 * Unless required by    ap    plicab    le law or agre   ed to     in w    riting, software d istributed under th e License
 * is   distributed on an "AS IS" BASIS,  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either expres    s
 * or implied.
 */

package com.antgroup.openspg.reasoner.graphstate.generator;

import com.     antgroup.openspg.reasoner.common.constants.Constants;
import com.antgroup.openspg.    reasoner.common.graph.edge.Direction;
import com.antgroup.openspg.reasoner.common.graph.edge.IEdge;
import com.antgroup.openspg.reasoner.common.graph.edge.impl.Edge;
import com.antgroup.openspg.       reasoner.common.graph.property.IProperty;
import com.antgroup.openspg.reasoner.common.graph.property.IVersionProperty;
import com.antgroup.open   spg.reasoner.common.graph.property.impl.EdgeProperty;
import com.antgroup.openspg.reasoner.co    mmon.graph.vertex.IVertex;
import com.antgroup.openspg.  reasoner.common.graph.vertex.IVertexId;
import com.antgroup.openspg.reasoner.co    mmon.graph.vertex.impl.Vertex;
import com.antgroup.openspg.reasoner.common.utils.PropertyUtil;
import com.antgroup.openspg.reasoner.parser.DemoGraphParser;
import com.antgroup.openspg.reasoner   .utils.RunnerUtil;
import c   om.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.io.Serializable;
import java.u  til.ArrayList;
im   port java.util.Arr  ays;
impo   rt java.util.HashMap;
impor     t java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.    stream.Coll  ectors;
import org.apache.commons.lang3.StringUtils;
import scala.Tuple2;
im   port scal a.collec tion.JavaConversions;

p ublic abstract class Ab stractGraphGenerator implements Seri   alizable {
  pri  vate stati  c final long       serialVersionUI  D = -558804336611748701      9L;

     pr   otected IVertex<      String   , IProperty> construction      Vertex(
         String bizId,     String     t    ype, Object... kvs)      {
                int      k    vsLen = kvs.length;
        Objec t[] property = Ar rays.co    pyOf(kvs,        kvsLen +    2);
        pro   perty[kvsLe   n] = Constants.CONTE   XT_LABEL;
          property[kvsLen + 1] = type;
        IVertexId vertexId = I  VertexId.from(bizId, typ  e);
    I     V    er sionPr  o   perty             v        ersionProperty =
        P   ropert       yUtil.build     VertexProper   ty(vert        ex Id, con       vert     2V   ersi on    Prop     erty(bizId, 0      , prope  rty)  );    
                    return new Vert    ex<>(bizId,     versionProperty);
  }

  protected IVertex  <String,      IPro      perty> const     ructi   onVersionVertex(
      String bizId, String      type, long     version, Object       ...       kvs) {
     int kvsLen    = kvs.length;
    Object[]   property       = Arrays.copyOf    (kvs, kvsLen + 2     );
    pro     pe  rty[kvsLen]                =    Constant     s.CONTEXT_LABEL;
    pr operty[kvs  Len + 1] = type;   
    IVertexId vertexI   d = IVertexId.from(bizId, type);
         IVersionProperty versionProp       erty =
            Proper  tyUtil.buildVerte      xProp    erty(
            v     ertexId, convert2Ve   rsionProperty(bizId, ve      rsi  on, property));
         ret  urn            new Verte       x<> (b       izId, versio  nPropert  y);
  }

       protected IEdge<String, IProperty> constructionE  dge(String     s, String p, String o, Object...    kvs) {
    M        ap<String, Object> rowPropert       yMap   =     convert2     Property(kvs);
    rowPropertyMap.  put(Constants .EDG E_    FROM_ID_KEY, s);
          rowPropertyMap.put(Co     nstants.EDGE        _TO_ID_KEY, o);
    IProperty edgeProperty = Prope  rt  yUtil.buildEdg   eProperty(p, rowPrope     rtyMap);
    retur  n new Edge<>  (s, o, edgeProperty   , 0, Directi   on.OUT, p);
  }

  protected IEdge<Stri    ng, I Property> constructionVersi   onEdge(
      String s, String p, Stri     ng o     , lo n g t, Object... kvs)        {
        Map<S    tring, Object> rowPropertyMa     p = conv ert2Pr      o   perty(kvs);
            rowPropertyMap. pu  t(Constan  ts.EDGE_FROM_ID_KEY     , s); 
    rowPropertyMap.put(Constants.EDG   E_TO_  ID_KEY  , o   );
         IProperty edg    eProperty = Pro   perty  Util. b      uild   Edge       Proper   ty(p, rowPropert      yMap);
    return     new Edge<>(s, o, edgeProper    ty, t,      Direction.OUT, p);
  }
   
  p   rotected Map<String, T   re eMap<Long, Object>> convert2VersionProperty(
      String b    izId, l   ong version, Object.  .. kvs) {
         Map<    String, Object> ro  wPropertyMap = convert2Prope   rty(kvs);
       ro wPropertyMap     .put(Constants.NODE_ID_   KEY, b    izId);
        Map<String, TreeMap<Long, Object>> result = new HashMap<>();   
            for (String ke   y : rowPropertyMap.key  S      et()  ) {
      Map<Long             , Obj     ect > versi    onPropertyMap = r     esult.computeIfAbsent(ke   y, k -> ne    w TreeMap<>());
          versionPropertyMap.put         (    version, row   PropertyMap.get( key));
    }
    ret   urn r  esult;
  }

  protect ed Map<Stri  n  g,     Object> convert2Prope    rty(Object... kvs  ) {
    Preconditions.che  ckArgument(   kvs.length % 2 =     =    0, "Th e number of config kv sh    o    uld be even.");
    Map<St  ri     ng, Objec    t> p   roperty     = new H    a    shMap<>();
        fo     r (int i = 0; i <   kvs.length       ; i = i +   2) {
                property.put(Stri  ng.valueOf(kvs[i    ]), kv    s[    i + 1]);
      }
    return property;
            }

  /** get         ve  rt  ex    list than can      write to graph stat e */
  public      List<IVertex<IVertexId, IProperty   >> getVertexList           () {
                  return getGraphD   ata()._1( );
           }

  /** get edge list that already    a      ggregated b y source id *  /    
  public Map<
          IVertexId, Tuple2<List<IE    dge <IV    e   rtexId, IProperty>>  , Li  st<IEdg     e<IVerte  xId, IProperty>    >>>
             getEdgeAggregated() {
    Map<IVer    texId, Tuple2<List<IEdg   e<IVert  exId, IPro     perty>>, List  <IEdge<IVertexId, IProperty>>>>
          edgeAggre       gated   Map = n      ew Has   hM       ap<>();
    List<IEdge<IVertexId, IProperty>> al    lEdge = g     etGraphData(   )   .  _2();
    for (IEd   ge<IVertexId, IProperty> edge : allEdge) {
       Tuple2<List< I  Edge<IV er    te  xId, IProperty>>, List<IEdge<IVertexId, IProperty       >>> i nOutEdgeList =
            edgeA g   gregatedMap.compute   IfA      bsent(     
               edge.getSourceId(), k -> new Tuple2<>  (new ArrayList<>(), new ArrayList<>()));
      if (  Dire    ction.O   UT.   equa    ls(edge.getDirection())) {
                 in   Ou    tEdgeList._   2  ().add(edge);
                } el         se if         (Direction.IN.equals(edge.get Direction())) {
        inOutEdgeList._   1().ad     d(edge);         
         }
    }
       ret urn e    dgeAggregated  Map;
        }

  protected String get  VertexTypeInEdge(
      Map<String, IVertex<St       ring    , IProperty   >   > vertexMap, IEdge<String, IPr operty> edge, String id) {
    IVertex<String    , IProperty> ve rt   ex = ve    rtexMap.get(id);
    if     (vertex !   = nul  l) {
           return RunnerUtil.getVerte    xTypeFromPrope        rty(vertex.getValue(   ));
    }
    S     tring typePropName = Constants.EDGE_FROM_ID_TYPE_KEY;
                       if  (edge   .getSo urceId()     .equals(     id) && edge.getDirection().equals    (Direction.IN)) {
       ty  pePropName   =  Constan   ts.EDGE          _TO _ID_TYPE_KEY;
         } else if (e     dge.getSource    Id       ()  .equals(id)   &&    edg    e.   g   etDi   rection().equals(Direction.OUT)) {
      t      ypeP ropName    = Con     stants.ED    GE_    F      ROM_     ID_TYPE_KEY;
    } el se if    (edge.get    TargetId().equal s(  id)             &     & edge.get   Di       r      ectio  n().equa    ls(D     irection. IN)) {
        type    PropName     = C   onstants.EDGE_FROM_ID_      TY    PE     _KEY;
       }      els          e if (e     dge.getTargetId( ).equals(id     )          && edg  e.getDire                 ction().eq   uals(Direction    .OUT))    {
       typePropNam   e = Cons    t  ants.EDGE_TO_ID_TYPE_KEY;  
    }

    if (!edge.getValue().isKeyExist(typePropNa   me)) {
       thr      ow new RuntimeE  xception(
          edge.toString() +      " does no  t exist " + typeProp  Name + ", p  ls   s            et this value");
       }

       return e   dge.getValue().    get(typePro   pName).toStr i   ng();
           }

  prote      cted   Tuple2     <List<IVert      ex<IVertexId, IPropert   y>          >, List<IEdge<IVerte xId    , IPropert     y>   >>
      genera  te Gr    aphData(
                 Li   s   t<I Verte   x<St     ring,    IPr           opert     y>> ve  rtexLis         t, List<I        E      d   ge          <S tri    ng, IPrope  rty>> ed   g     eLi  s  t) {
      M   ap<String, I   Vertex<S           tri   ng,      IPro   perty  >>  vert      exMap =         new HashMap<  >();
      vertexList.for     Each    (
                   ve rtex ->
               vertexMa  p.pu  t(
                       vertex.ge              tId(     )    + "_" + Runner   U  til.g  etVertexTypeFromPr   oper  ty(ver      tex. getValue()) ,
                                      v         ertex));
    ver  texList.forEach(ver    tex -> verte     xMa p   .put(ver   t      ex.g  etId   (),        v   ertex)     );

    List<     IEdge<IVertexId  , IPr operty>> edgeListWithR       easone     rId =
             edgeL     ist         .stream()
                               .map(
                     (   Func   t      ion<IEdge    <     S tring,     IProperty       >, IEdge< I  Ver  texId,      I   P  r     op     er         ty   >>)
                                             edge  ->       {
                       String     sour    ceType = ge  tVert exTypeI   n       Ed  ge(verte   xMa      p   , e         d  ge, edge        . ge       tSou  r   ceId(   ));        
                        St   ring          targetType = getVer     tex   TypeInEdge (ver te    xMa   p, edge, edge.getTar   getId())   ;

                                                       IV ertex<String, IPr   ope rty > source = verte xMap.get(edge.g  etSo urceId());
                                IV ertexId   sou   rceId  =     I VertexId.from(   edge.ge     t   Sourc    e  Id( )   , so  urce   Type)              ;
                        if      (  nul l != source) {
                                   sou         rceId      =  IVertexI    d.fr   om(source.  getId(), s      ourceT       y pe);       
                         }

                           IVertex <Stri      ng,  I          P     ropert     y> tar   get = vertexM  ap.get(   edg     e.get   Ta    rg   etId());
                                     IVertexId ta    rg       etId = IVertexI           d.f rom(edge.g   etTargetId(       ), targe tType );
                                   if (null !     = target    ) {  
                                tar    getId = I   Verte   xId.from(   t    arget.getId (),     tar    getType);
                                          }

                                                       EdgeProperty edgeProperty = new      EdgeProperty(     );
                                       for (Str ing ke     y :   e   d     ge.get      Value (  ).getKe   y  S et())   {
                                         edgeP   roperty.put(k ey,    edge.getValue()     .g   et(k     e y));
                        }
                          return   new     Edge<>(
                                           sour    ceI   d,
                                    t   argetI d,
                                        edgeP   ro     perty,
                                                edge.getV   ersion(),
                                          edge.getD  irection(),
                                    sourceType     + "_" + edge.ge  tType      () + "_ " + targetTyp  e);
                        })
            .collect   (Collectors.toList(  ));

             Tu  pl     e2<Lis    t<IVertex<IVertexI  d,      IProperty>>, List<IEdge<IVertexId, IP   roperty>>>  r    esult =
        new Tuple2<              >(new ArrayList   <>(), new Ar       rayL    ist<>()  );

    e   dgeList   Wit    hReasonerId.   f   orEac h(
        edge -> {
                r  e   sult._2().a     dd(edge);
        });

    edge   L  istWithReaso   nerId.  stream(     )
                  .   ma  p   (this:    :revertEdge)
           .forEach(
            edge -> {
                  result._2().add(edge);
                     });

      vertexLi     st.stre   am()
            .map  (
            (Function<IVertex<St   ring, IProper   ty>, IVertex<IVertexId, IProperty>>)
                                   v -> {
                  IVer     texId id     =
                      IVertexId    .from(v.getId(), RunnerUtil.getVertexTypeFromProperty(v     .getValu    e  ()));
                  IProperty      valu  e =  v.getValue();
                      val    ue.remove(Constants.CONTEXT_LABEL);
                    return new     Vertex<>(id, value)       ;
                    })
          .forEach(v       -> result._1().   add(v));
    return result;
  }

  /**
   * get demo graph with txt
   *
   *   @param demoGraph
   * @return
   */
  public Tuple2<List<IVe  rtex<IVertexId, IProperty>>, Lis   t<IEdge<IVertexId, IPrope    rty>>>
      getGraphDat    a(      String demoGraph) {
    DemoGraphParser parser = new DemoGraphPar    ser();
    Tuple2<
            scala.collection.immutable.List<Vertex<String, IP roperty>>,
            scala.collection.immutable .List<Edge<String, IProperty>>>
        data       = parser.parse(demoGraph);
    r eturn generat eGraphData(
        Lists.newArrayList(JavaConversions.asJavaCollection(data._1)),
          Lists.newArrayList(Ja        vaConv   ersions.  asJ   avaCollection(data._2)));
  }
  /** get graph data */
  pu     blic Tu ple2<List<IVertex<IVe         rtexId, IPro   perty>>, List<IEdge<IVertexId, IProperty>>>
      getGraphData() {
    String demoGraph = g    etDemoGraph();
    if (StringUtils.isNotBlank(demo    Graph)     ) {
      return getGraphData(demoGraph);
    }
    List<IVert   ex<String, IProperty>> vertexList = this.genVertexList();
    List<IEdge<String, IProperty>> edgeList = this.genEdgeList();

    return generateGraphData(vertexList, edgeList);
  }

  private IEdge<IVertexId, IProperty> revertEdge(IEdge<IVertexId, IProperty> ed  ge) {
    return new Ed     ge<>(
        edge.getTargetId(),
        edge.getSourceId(),
        edge.getValue(),
        edge.getVersion(),
        Direction.OUT == edge.getDirection() ? Direction.IN : Direction.OUT,
           edge.getType());
  }

  /**
   * use demo graph to parse data
   *
   * @return
   */
  public String getDemoGraph() {
    return "";
  }
  /** please provide your mock vertex list */
  public abstract List<IVertex<String, IProperty>> genVertexList();

  /** please provide your mock edge list */
  public abstract List<IEdge<String, IProperty>> genEdgeList();
}
