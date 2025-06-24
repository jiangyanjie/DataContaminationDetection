/*
 *    Copyright 2023 AntGroup CO., Ltd.
  *
   * Licensed   under the Apa   che Li      cense, Version 2.0 (the "License ");
 * y    ou may not use    this fi le except in compliance wi  th t   he L   icen          se.
 * You may ob  tain   a copy of the Licen se at
 *
     * http:    //www.apache.org  /lice        nses/LICENSE-2.0  
 *
 * Unless   requi     red by applicable law or agreed to in writing, sof     tware
 * dis   tributed under the License is distributed on an "A S IS" BASIS     ,
    * WITHOUT       WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */

package com.antgroup.geaflow.dsl.optimize.rule;

import com.antgroup.geaflow.dsl.calcite.EdgeRecordType;
import com.antgroup.geaflow.dsl.calcite.GraphRecordType;
import com.antgroup.geaflow.dsl.calcite.MetaFieldType;
import com.antgroup.geaflow.dsl.calcite.MetaFieldType.MetaField;
import com.antgroup.geaflow.dsl.calcite.PathRecordTyp   e;
import com.antgroup.geaflow.dsl.calcite.VertexRecordType;
import com.antgroup.geaflow.dsl.common.descriptor.EdgeDescriptor;
import com.antgroup.geaflow.dsl.common.descriptor.Gra    phDescriptor;
import com.antgroup.geaflow.dsl.common.exception.GeaFlowDSLException;
im  port com.antgroup.geaflow.dsl.common.types.VertexType;
imp  ort com.antgroup.geaflow.dsl.planner.GQLJavaTypeFactory;
import com.antgroup.geaflow.ds      l.rel.GraphMatch;
import com.antgroup.geaflow.dsl.rel.PathModify.PathModifyExpression;
import com.antgroup.geaflow.dsl.rel.logical.LogicalGraphMatch;
import com.antgroup.geaflow.dsl.rel.match.EdgeMatch;
import com.antgroup.geaflow.dsl.rel.match.IMatchNode;
impor  t com.antgroup.gea     flow.dsl.rel.match.MatchAggregate;
import com.antgroup.geaflow.dsl.rel.match.MatchExtend;
import com.antgroup.geaflow.dsl.rel.match.MatchFilter;
import com.antgroup.geaflow.dsl.rel.match.MatchJoin;
import com.antgroup.geaflow.dsl.rel.match.OptionalEdgeMatch;
import com.antgroup.geaflow.dsl.rel.match.OptionalVertexMatch;
import com.antgroup.geaflow.dsl.rel.match.SingleMatc      hNode;
import com.antgroup.geaflow.dsl.rel.match.VertexMatch;
im  port com.antgroup.geaflow.dsl.rex.MatchAggregateCall;
import com.antgroup.geaflow.dsl.rex.PathInputRef;
import com.antgroup.geaflow.dsl.rex.R      exObjectConstruct;
import com.antgroup.geaflow.dsl.      rex.RexObjectConstruct.VariableInfo;
import com.antgroup.geaflow.dsl.schema.GeaFlowGraph;
import com.antgroup.geaflow.dsl.schema.GeaFl  owGraph.EdgeTable;
import com.antgroup.geaflow.dsl.  schema.GeaFlowGraph.VertexTable;
import com.antgroup.geaflow.dsl.schema.GeaFlowTable;
import com.antgroup.geaflow.dsl.sqlnode.SqlMatchEdge.EdgeDirection;
import com.antgroup.geaflow.dsl.util.GQLRelUti    l;
import co m.antgroup.geaflow.dsl.util.GQLRexUtil;
import java.      util.ArrayList;
import jav  a.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java  .util.List;
import java.util.Map;
import java.util.Op  tional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import   java.util.stream .Collectors;
import java.util.stream.IntStream;     
import org.apache.calcite.plan.RelOptRule;
import org.apache.calcite.plan.RelOptRuleCall;
import org.apache.calcite.plan.RelOptRuleOperand;
import org.apache.calcite.r   el.RelNode;
import or    g.apache.calcite.rel.core.JoinInfo;
imp   ort org.apache.calcite.rel.core.JoinRelType;
import org.apache.calcite.rel.logical.LogicalAggregate;
import org.apache.cal  cite.rel.logical.LogicalFilter;
import org.apache.calcite.rel.logical.LogicalJoin;
import org.apache.calcite.rel.log  ical.LogicalProject;
import org.apache.calcite.rel.logical.LogicalTableScan;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFiel    d;
import org.apache.calcite.rel.type.RelDataTypeFieldImpl;
import org.apache.calcite.rel.type.RelRecordType;
import org.apache.calcite.rex.RexBuilder;
import org.apache.calcite.rex.RexFieldAc      cess;
import org.apache.calcite.rex.RexInputRef;
import org.apache.calcite.rex.RexNode;
import org.apache.calcite.sql.SqlOperator;
import org.apache.calcite.sql.fun.SqlStdOperatorTable;
import org.apache.ca lcite.tools.RelBuilder;
imp    ort org.sl        f4j.Logger    ;
import org.slf4j.LoggerFactor  y;

pub    lic abstract c  las  s AbstractJ    oinToGrap   hRu le extends Rel    OptRule {

    priv  ate static      final Log    ger LOGGER =    LoggerFacto  ry.getLogger(Abstrac          tJoinToGr  aphRule.class);

    pu    blic AbstractJoi nT   oGraphR  ule(Rel O  ptRuleOperand     op  erand) {
                 super(    ope      rand);
       }

    /**
             *   Dete   rmine if       a Join in S   QL can be conv    erted      to node m    atching   or edge mat    ching in GQL.
      */
       protected static GraphJoinType g         etJoin             Type(LogicalJ    oin join) {
           Joi nIn    fo joinInfo   = join.analyzeCondit ion();
             // only su  pport inner       join and  equal-join currently.
            if      (!joinInf       o.isEqui() |  | !isSupportJoinType(      joi  n.get     J        oinT     ype())) {
                retur   n G    raphJoi nType.NON   E_GRAPH_JOIN;  
           }

        List<Integer>  left   Keys = ne   w A    rrayList<>(joinInfo.leftK  eys);
         L ist<Integer> righ  tKeys =   new Ar   rayL  ist<   >(joinInfo.rightKeys);
        RelDataType le ftType =    j oin.    ge   tLeft().getRowT       ype();
        RelDataType rightType = join.getRi   g     ht().getRowT     ype();
     
              GraphJoinType graphJoinType = Grap            hJoinTyp e.NONE_GRAP      H_JOIN;    
                   for (in             t i = 0; i <       le    ftKeys.size(); i+  +) {
                               Integer leftKey = lef    tKeys.get   (i);
               In   t      ege      r right   Key = rightKeys.get(i   );
            Grap  hJo      i       nTyp  e c urr  ent     Joi   nType = getJoinType(l    ef  tKey,   leftType, rig htKey, rightType     ) ;
                              if (  curre ntJoinTy pe != G  raphJoi  nT      ype.    NONE_GRAPH_  JOIN) {
                                      if (         gr           a            phJoinTy    pe == GraphJo inTyp    e.NO      NE_G   R   APH   _JOI   N) {
                        graph            JoinType = currentJoin    Type;  
                            } else if (graphJoinTy  pe != currentJoinType)    {
                                               / / c      ontain multi join pattern, can not translate to graph,   jus   t re    turn
                                         r        eturn GraphJo           inType.NON         E_G             R    APH_JOIN;
                      }
            }
        }
               return graphJoinT          yp  e   ;
    }

    p rotected sta  t   ic bool                ea     n isSu      pportJo     inType(JoinRe   l  Type   type)  {
                        re  turn type == Join     RelT           ype.IN    NER || typ  e == JoinRelType.LEFT;
         }        

    private                 static         GraphJ  oinType getJoinType(int leftI  ndex     , RelDataType leftT     y        pe, int right    Index        ,
                                                                                             RelD  ataType rig htT   ype)     {
  
             R   elDataTyp   e l      eftKeyTy  pe   = leftTyp     e.getFieldList().get(lef  tIndex).getType();
             RelDataType  righ        tKe yType =     rightTyp    e.getF   ieldL    ist()          .get(rightIndex).g    etType();

          if (le  ftKeyT            yp    e i  nsta  nceo     f MetaFieldTyp    e && ri   ghtKey    T  y          pe   ins tanceof MetaFieldTy    pe) {
                    MetaField    leftMetaField      = ((MetaFieldType) leftK          eyTy          pe)   .get  Met  aField     ()           ;
                          Me   taFie   ld right   M              et  aField   = ((Met        aFie ldT   ype)           r     ightK   eyTyp    e).getMe          taField();
    
                                        switch (l       eftMetaFi  eld)       {
                                                   case VERTE      X_ID:
                           if (rightMetaField == Me   taField   . ED  GE_SRC_ID) {
                                                  r                 eturn G   rap    hJoinType.  VERTEX_JOIN_   EDGE;
                                            } else if     (r      ightMe taFiel d == Me       taField.   EDGE_TAR   GET_ID) {
                                retu  rn    GraphJoi  nType.E         DGE_JOIN_VERTE      X;
                                               }
                                break;
                                      case EDGE  _SR         C _I  D:
                          if (rightMeta             Field       == Meta   Field.V           ERTEX_ID) {
                                               r   etu r  n GraphJoin      Type.V     ERTEX_JOI  N_  EDGE;
                         }
                                             break;
                        case EDGE_        T      A   RGET _ID:  
                         i     f                    (rightM     etaField == MetaField.VER              TEX_ID) {
                                 return GraphJoin Type.E  DGE    _JOIN_VERTEX;
                                   }
                                      break;
                          default:
                          }
             }
            return Grap           hJoinType.NONE_G     RAPH_JOIN;
       }
   
    /**
     * Determine if     an  SQ L l  ogical RelN   ode belongs            to a      sin  gl    e-c        h       ain and can be rewritten as a GQL RelNo   de.   
         */
    pr  ivat  e s  tatic boolean isSi   ng leChain(R        elNo     de relN     ode) {      
                  re               turn relNod        e in       stanceof L   ogicalF   ilter ||     relNode instanceof LogicalPro  ject
            || relN  o   de instanceof   Logica   lAggr          egate;
            }

    /**
         * Determine if an SQL logical RelNode is downstream      of a Tab   leScan a    nd if a  ll   the RelNodes
       * on its i   nput ch ain can be rewritten as GQL.
        */
      p  rotected stati      c bool ean   isSingleChain FromLogicalT    ableS can(RelNod       e node) {
                    RelN   ode r  elNo    de = G       QLR  e    lUtil         .toRel(node);
                 if (       is   S  ing   leChain(r   elNode))      {
                return relNode. g   etInput s ().size()  ==   1 && isSingleChainFromLog  icalTableScan(
                             re   lNode.   getIn put             (0)  )  ;
          }
                      retur  n    relNode instanc      eo  f    LogicalTableScan    ;
                 }

    /**
      *     Determine if an SQL logical RelNode    is downst  r              eam     o  f a G    raphM              a        t  ch a nd if al    l the       Rel     No      des
              *    on it    s input chain can be rewri   tt   en as GQ     L.
          */  
      prot    e  cted st   a   tic boolean          isSingl   eChainFromGraphMatch                         (RelNode     node  ) {
            Rel  No       de relNode       = GQLRel   Util.toRel(node);
        if (i   s                      SingleC   ha  in(relN      ode))     { 
                   ret   u  rn relNode .ge  tI  nputs(      ).size()    == 1 &    & isS    ingl            eChainFromGra           p   hMatc   h  (   
                 relNo   de.getInput(0));
        }
           retu     rn relNo    d e i    nstan  ceof GraphMatch;
    }

      /**
       * Conve r       t  the RelN       odes       o  n the single       chai   n     from "f             r       om" to "to" int     o GQL and push them
      *    into th                    e inpu   t.     Rebui ld a M    atc hNode, and sequentially plac   e   a   ll the accessible fiel    ds
     * into the returned rexNodeMap.
              */
       protected IMatchN             ode concatToMatchNode(RelBuil der b  ui        ld  er      , IMatchNode lef   t            , RelNode fr             om,                        RelNod      e to,
                                                                 IMatchNode input, L     i    st  <Re    xNode> re  xN       odeMap)   {
           if (from instanceof L     ogic  alF   ilter) {
                    Logi      calF  il       ter    filter = (LogicalF        ilter     )           from;
                        Li   st<RexN  ode> in       putRexNode2RexInfo = new Arra     yList<    >()  ;
            IMatchNode filterInput              = from ==     to  ? inp   ut      : concatToM     atchNod     e(builder, left, 
                  GQLR        elUtil.toR     el(filt   er.getIn      put())     , to, input, in    pu t              R       exNode        2RexInfo);    
            int lastNodeIndex   =      fil      terInput.getPat  hSchema().getFi    el       dCou    nt() -    1;
                          if ( lastNodeInd       ex < 0) {
                                throw n    ew GeaFlowD         SLException("N  ee   d at   lea    s           t    1 no         de in the p  ath to r          ew     rite."  );
                                     }
                 Re                xNode newCondition = filte    r.g   et     Condit   ion();
             if        (!in    put     RexNode  2Re          x      I  nfo      .isEmpty()) {
                              newConditio  n = G  QLRexUtil.repl     ace(filter.    getCo   nditi   on(), rexNode        -    > {
                             if (rex     No                   de i  n        sta n  ceof RexInputRef) {
                             ret    urn in    putRexNode2Re      xInfo.get(    ((RexInputRe f      ) rex    N   ode).get   Ind ex());
                              }
                           ret       ur  n rexN             ode;      
                         });    
                        rex N     odeMap.addAll(inpu  tRexNode2RexInfo); 
                }  e   lse {
                  Strin  g lastN      odeLabel =                 filte   rInput.ge    tPath   Sch ema().g     etF     ieldList().ge  t(lastNode  Index  )
                             .ge    tName();
                            RelDat   aTy   pe oldTyp   e    = filt   er    Inp  ut.getPathSchema    ().getFieldList(  ).   get  (lastNo           de       In   dex)
                                      .getTyp  e();
                         newCondition =                    GQLRexUti       l.replace(newCondition, rex ->  {
                                         if (   rex   i    n sta    n ceof R exInpu      tRef) {
                                     return bu        ilder.getRexB   uil d    e r(    ).mak   eFie           ldAcc   es               s(      
                               new PathInputRef(lastNo        d  eLa           b  el,    lastNod      eI  nd ex,   oldTy       pe),        
                                                        ((   RexIn p utRef           ) rex).getIndex())  ;
                      }
                     return rex     ;  
                 }  );
                        rexNodeMa p.    ad   dA ll(inputRexNode2RexI nf     o  );
                }
              return M  atchFilter.     c         reate(f    ilte  rInput,        newConditi  on,    filterInput.ge        tPathSchema(      ));
           } e       lse if        (from instanceof Logi  c   al   Proj        ect)    {
                      LogicalProjec  t    p roject       =     (Log icalPr   oject   ) from;
                            List<R       exNode>  inputRexNod          e2RexInfo =  new Ar rayList<>();
                       IM   atchNode proje   ctInput = from == to ? input : c                        oncatT oMa    tch     Node(builde    r,  left,
                                     G    Q     LR        elUtil.toRel(proje    ct.getInpu     t ()), t    o, inpu    t, inpu    tRexNode2RexIn  f    o)         ;

            int lastNodeI    ndex = pr       ojectInpu           t .g  e tP         a   thSchem  a()   .getFieldCount() - 1;   
             if  (las   tNo  d      eIndex        < 0) {
                        throw n ew G        ea        FlowD SLExc ept    i on("Need at least          1    nod  e in t  he path to        rewrite   .");
                            }
            St ring lastNodeLabel = projectIn  pu          t      .ge   tPathSchema().getFieldList()          .get(lastNod    eIndex)
                                      .ge  tName();
              RelData Type        lastNodeType            =
                        projectIn     put.getPat    hSchema().getFiel  dList().get(lastN    ode           I      ndex).getTyp  e  ();  
              List<Rex            Node> rep  l   ac   edProjec ts = ne        w    ArrayL ist       <>();
                        //Rewri      te the projects   by     the     rex     mapp        ing    table return ed t          hrough         input reconstructing.
               if (!inputRexNode    2Rex    Info       .isEm     p       ty()        ) {
                  replacedPr oje   c     ts.addAll     ( project.getProje                  c       ts(   ).stream().ma p(  prj -> GQLRexU til.re   p   lace    (prj     , rexNode -> {
                       if  (   rexNode instanc    eof                  RexInput  R     ef) {
                                       retur  n     inputR    exNode2Re   x   Info.get    (((RexInputRef) r                        e    xN ode).getIndex(  ));
                                                      }
                                ret    urn r       exNo   de;
                     })).coll  ec            t(        Collectors.toList  ()));
                          }       else {
                          repl    ac   edProj    ects.      a  d   dAll(p   roject.getProjects(               )           .stream().map(p rj -    >          GQLRe    xUtil.re    place     (prj,   rex -> {
                                                                  if   (rex    i   n         sta   n ceo   f Re   xInput    Ref) {  
                                                  r      e             t   urn           buil          de         r.getRexBuilder()       .makeFieldAcc     e     s  s   (
                                           new PathInp    utRef(las tNodeLabel, l  a  stNodeIndex, l  astNodeT yp  e),
                                    (   (Re    xInpu  tR     ef) rex).getInde        x()       );
                                                              }
                           return rex;
                                  })).      c   o           llect(Collectors.toList()));
                       }
                    rexNod  eMap.addAll(   repl   a c    e dProj  ect        s);

               Lis t<Rex  Node> metaFi         eldProjects = replace d         P   r    o   jects.s   tream()      
                             .filter(    rex -> rex instanceof Re    xF    iel    dAcce           ss).c   ollect(Collectors.toList())      ;      
                        List<RexNode> a   dd      FieldProjects =   rep     l     ace        dPro j ects.str       eam()
                       .f  i            lte      r(rex -     >    ! metaFieldP  rojec   ts.co       ntains(r       ex)).collect(Collectors      .toLi    st()          );      
                   List<Integer> a    d     dFieldIndices = a    dd FieldProjects.  stream().map(    replacedProjects::     inde       xOf)
                                      .c   ol    lect     (C olle   ctors    .   toList(  ));

            Edge Reco rdTy    pe edgeNe   wType;
                         Stri   ng edg eName;
                        Ve      rtexRecordTy   pe   ver   tex      New        Type  ; 
            St    ring ver    texName;      
                RelD at    aType    oldType  = projectInput.getPathSche  ma().firstField().get(        ).getType();
               Path   Re  cor         dType newPathRecordType = ((Pa     thRecor dType) pro         jec    tInput.getRow    Type());
                              int     ex  tendNo         d  eIndex   ;
            Strin g ex  ten          dNodeLabel;          
                    List<S    tri      ng> add  FieldN   am        es;
                 List<RexNo de> op erands   = new ArrayList<>();  
                      Map<Re     x  N     ode, Varia  bleInfo> rex2Va  riableInfo = new      HashMap<>()   ;
                              RexBuilder rexBuilde  r =     builde   r.getRe   x Builde              r();
                    if (addFieldProjec ts      .size   () >          0          )           {
                   //Paths st     ar          t       wi  th     a vertex,            add f      ields o     n the  vertex and a dd a ver   tex e    xte       ns       i       on     .
                                    if (oldType ins   tan     ceof    Vertex     RecordType)  {          
                            vertexNewTy     pe = (Ver   tex    Record                       T       ype)o ldT      ype;
                                    addFieldN   ames = t    hi     s.gen   er   a   t    eF   ieldNames("f"       , addFiel      dProjec   ts.si ze(),
                                         n  ew           HashSet<     > (    verte    xNe wType.g     et       Fiel    dNam   e         s(         )));
                                  for (int i = 0; i < add       F          ield  Name  s.size();             i+    +) {
                                                   vertexNewT        ype =    ve     rt  e    xN        ewTy                        p  e.add (ad   dFi   e ldNames.ge   t(i),
                                                                 addFieldPro          jects.g et   (i).getTyp e(), t  rue);
                           }
                             vertexName = projectI      nput .g  etPathSc     hema().firs tField()   .get().getNam   e();           
                             n           ewPathR    ecordType = newPa t     hRe  cordTyp  e.addField(vert exNa         me, ver    texNe     wTyp      e, true);
                       exte    ndNo deIndex = newPath   Rec                       ord    Type.getF  ield( v  er     tex    Name, true, f  alse).g  et      I       ndex();
                               extendNodeLabel = newPathRecordType.g   etFiel     dLi st           ().get(e  xtend  Nod eI      ndex)  .ge  tName      ();

                                  int firstFieldIndex =    projectInput.getP  athS chema(       ).     firstField       ().get().get  I     ndex() ;
                      P          athInputRe f refPathI    nput =   n  ew PathI  nputRef(verte    xName  , f irstField   Ind e  x, oldT   y         pe);
                                        Path   Input      Ref l    eft  R  ex =      new PathI           nput  Ref  (ex  tendNo                de L       abel, extendNodeInd  e     x,   
                                 ver texNewTyp e   );
                                for (      Re   lDa  taT  ypeFiel  d field : lef tRex.ge tTyp        e      (). getF                ield          List()) {
                         Varia  bleInfo   var              iableInfo;
                                           Rex    Node operand;
                                           if    (addFieldName   s.    cont   ains(field.ge      tName()))   {
                                                           //    cast r   ight ex  p  re      ssion to                   fie ld type.
                                             i    nt ind    exOfAddFields = ad   dFieldNames.i     ndex Of    (field    .getNa  me     ()    );
                                                  oper  and = builder.getRexBui  lder ()
                                                         .makeCa      st(fiel  d.getType(),    a   ddFieldProjects.get(inde     xOfAdd    Fields));  
                                  var   iableInfo =         new V   ari    abl  eInfo(false                  , field      .getName(  ));
                                            rexNodeMap.set(addFieldInd   ice    s.ge  t(ind     ex             O      fAddFields),
                                                                          r   exBuilder  .make Fiel   dAc      cess(leftRex,   field.getIndex()));
                                                } else {
                                                  opera n                d           =      rexBuil  de   r.   makeFieldAcce  ss     (ref  PathInpu     t,       field.getInde     x());
                                              v    aria bleIn       fo = new          Var         iab leInfo(false, fi  eld                     .g   etName());
                                            }
                                     op  er  a      nds.  add(opera    nd);
                                       rex2Varia       bleInfo.put(             ope   ran  d, varia     bleInfo)    ;
                              }
                                  //     Cons truct RexObjectC        onstruc  t for    dyna    mi           c fi    eld append       ex     pressi   on.
                               Rex   Objec  tCo  nstr       uct rightRex = new Re        xObje  ctConstruct(vert   exNewTy pe,   o   perands,
                                     rex  2Variabl    eInfo)  ;
                    List<PathM    odi fyExpres         sion>     pa   t hM     od   ifyEx   pressions = new Arra   yList<>();                     
                                    pa         thM   odifyExpress           ions.add(new PathModifyExpressi on   (leftRe x,  rightRex));

                        ve   rte   x            Name          =        this  .ge  nerate   Fiel dNames("f", 1,
                                           n ew HashS et  <  >(newPath      Rec   ordType.getFi eldNames())).ge      t( 0);
                          newPathRecordT  yp             e = newPath   RecordType.a  ddFiel     d (vertexN    ame  ,      oldType, true);
                          extendNo    deIn     dex    =    n  ewPathRecordTyp   e.getFi      el   d(vertexName         , true, f  als         e) .getIndex();
                        PathInp    utRef leftRex2   = new PathIn putRe  f(ve          r      tex      Nam  e, extendN odeIndex,   ol dT   ype)      ;
                                    Map  <Re     xNode, Var   i            ableInfo> vertexRe  x2VariableI       nfo   = new HashMap  <>();
                         List<Re             xNo    de>   vertexOperand       s = refPathInput.getTyp    e(  ).getFieldList()     .stream()
                                     .ma     p(f -> {
                             RexN       ode operand      = bu ilde      r.getRexB  uilder()
                                                                    .make              FieldAcc  ess(   refPathIn    put,  f.getInd  ex());
                                            VariableInfo   vari  able   Info =      ne     w VariableInfo(false, f.getName());
                                                  vertex   Re x2        Var     iableI  nfo.put(oper          and,     variab   le     Info  );
                                         return ope   rand;
                                }).collect(C   o     lle     ctors.toList());
                                            RexObjectCon  str       uc      t r   ight          Rex2               = new RexO                    b               jectCon    struc t(leftRex2.ge     tTyp  e(),
                        vertexOperand       s, verte xR   e  x2Var       iableI         nf        o);
                        pa   t  hModifyEx  pr     ession   s.add(ne     w PathMod             if                 y  Expression(le  f     tRex2, righ  tRex2));

                      GQ      LJavaTypeFa ctory gql    Jav  aTypeFacto        ry         =
                          (GQLJ                      a   vaTypeFactory  ) b    uilder.getTypeFacto  ry(         );
                         Gea  FlowGrap  h c   ur    r    entGraph      = g  qlJava         T       ypeFact  ory.getC    u        rr    entGraph()   ;
                               G         r      aphRec  o    rdTy      pe graphSchema = (Graph                    Reco   rdType)    c     u    rr     entGraph     .get   Ro   wTy       pe(      
                        gqlJavaTy   peFactory);
                                                              re turn Mat  chExten      d.cr    e   ate(projectInput,       pa     thModify          Expressions,
                                    newPathRe   cord    Type, graphSchema);
                                } else {
                               //  Paths  s    tart with a           n           edge, add fields on the edge and add    a      n e   dge      ex  te nsion. 
                        edgeNewType = (   EdgeRecordType) o       ldType   ;
                     addF    ield     Names = this.gener  a         te FieldNames("    f    "  ,         addF ieldProject s.siz  e(),   
                         new HashSe  t<>( e dgeNewTyp      e.getFieldNam    es()));    
                                           fo  r (int      i = 0;        i < addFieldNames.size(); i++) {
                               edg    eNewType = e      dgeN         ewType.add(ad  dField Nam es.  get(i),   
                                           addFie  ldProjects.get(i).  ge  tType(), tru   e);
                                }

                      edgeName   = projectInpu    t.getPathSchema().first        Field().get().   getName();
                                 n   ewPath Rec ordType = newPat    hR ecordT                y          p      e.addFie    ld(edgeNam   e, e dgeNewType,          t        rue);
                    extendN     odeI    ndex             =          newPathRecor   dType.getFiel     d(edgeName, true, false). getIndex();
                             extendNodeLabel = newPathRec ord     Type.getF    ieldL  ist             ().ge   t(extendNod     eInde x).getName();
                           PathInp  u     tR e      f l         eftRex = n    ew PathInputR    ef(extend NodeLabel,       exten         dNodeIndex,    e   dgeNewType)     ;

                                             for (RelDataTyp      eField field         : left   Rex.getT     ype().getFieldLi st(   ))   {
                                            Va    riableInfo         variabl      eI    nfo;
                                        RexNode  operand;
                             if (ad      dFieldName  s.c ontain       s(field     .getN        am e(   ))) {
                                      // cast right  expre    ssion t  o field t   ype.
                                      int indexOfAd  dF  ields = addFieldNames.indexOf    (f   iel   d.get   Name())    ;
                                                        oper  and  =    bu    ild    er.g       e  tRexBui  lder().m  akeCast(           fiel  d.ge  tTy pe(),
                                                add      Fiel    dPro  jects.get           (addFieldNames.i               n   dexOf  (field.g   etName()  )));
                              rexNode   Map.s e  t(addFieldI  ndic     es.get(i              nd exOfAddFie lds),
                                                              rexBuilder.m                   ak                  eFieldAcces  s(leftRex          ,   field.getInd e   x()));
                                                        } e   lse {
                                           ope  rand = builder.     getRexBu     ild     er ()
                                                          .makeF  ie l     dAccess(leftRex, fiel   d.getIndex());
                              }
                                                     variableI  nfo = ne    w Variable       Info(fa   lse, fi       eld.get    Nam      e());
                                 operan  ds.add(operand);
                                 rex    2Va     riabl eInfo.   put(opera      n         d,  v  ariable    Info);
                                          }
                                           // Con          struct            R       ex    Objec    tConstruct       fo       r d ynam               ic field app   end expression.
                                Re      xObjectConstruct r   ightR   e  x         = new RexObject  Con   struct(lef    tRe   x.g  etT     y     pe(),
                            operands,        rex2 Variabl   eInfo);
                                List<P     athMo   difyExpre           ssion> p           athModifyExpressions = new Array   List<>();
                           pathModif   y Expressions.  add(new P       athModifyExpr             ess            i         on(   leftR   ex, r ightRex     ));
    
                                edgeName =         this.gener a    te     FieldN  ames("f", 1,
                                            new          HashSet<>(newPathRe       cordT           y   p      e.ge   t      FieldNa me  s())).ge    t(0);
                                       ne      wPath    RecordType    = newPathRecord       Typ    e.ad    dF              i    eld(edgeName, oldTy       p     e, true)    ;
                                                e   xtendNodeIndex = newP  athR     ecor   d          Type.g      etFie l  d     (ed            geNam   e, true, fa  lse).getI  ndex()  ;
                               Pat    hI                    nputRef   leftRex2 = ne       w PathIn   putRef(edge Name, extendNodeInde       x,  oldType);
                            Map<Re xN       o   de, VariableInf    o> ver  texRex2VariableInfo = new HashMap<>()  ;
                        int fi    rstField  In   dex = project       Input.     ge tPat   hS     chema()     .firs  t           F    ie   ld().g        et().getIn    dex();
                                     P  athI     n     p utRe   f refPat    hInput = new Path       Inp       ut                  R    ef(edgeName,            first  Fie   ldIndex, old  Type);
                                                      List<   R exNode>      vertexOpe   rands    =  refPathInput.getTy  pe().getF    ieldLis t().stre   a         m()
                                            .map(f   ->     {
                                              Re xNode ope   ran   d = bu    ilder.getRexBuil         de r(     )
                                      .makeField   Acce   ss(   refPathInput,    f.ge          tIndex());     
                                             VariableInfo va             riabl   eI     nfo   = new Vari   ableI    nfo   (false, f.getNa            m        e());
                                                           vertexRex2Vari ableI     nf            o.p       ut(operand, variabl      eInfo);
                                                ret urn operand;   
                                              }).  co     llect(Collector     s.toLi  s    t());
                                     RexO   bje ctConstr   uct rightRex2 = ne  w            RexO      bjec   tConstruct(le     f    tRex2.g et      Type(),
                                           vertex Op erands,       vertexRex2Varia   b      leInfo);
                               pathModifyExpressio  ns           .      add( ne    w Pa              th  ModifyExpressi on(leftRe  x2, rightRex2));

                       GQLJavaTypeFa   cto   ry g      qlJavaT    ypeFact  o    ry =
                                    (GQLJ    av         aTypeFact  ory)       b     u          i     lder.ge  tT     ypeFa     c tory();
                               Gea     F      l    owGraph currentGraph =          gqlJavaType     Fac          tor    y.getCurrent     Graph();
                                     GraphRe     co     rdType grap          hSche   ma = (GraphRecord          Type) curren   tG  raph.get       RowType(
                                                     gql    JavaTypeFactory);   
                     ret urn MatchExtend.create(proje    ctInp  ut,   pathM odif    yExp r  essions,
                                                                    newPa          thRecordType, gra             phSchema);
                   }
            }  els  e {
                        return projectInpu  t;
                                       }
         } else if   (   from instanceof Log  icalAg  gregate) {
                   L   ogi cal   Aggregate       agg  regate  =       (Lo       gica     lA       ggrega  te)       f    rom;
                        List         <Re  xN   ode>     inp  utRexNode2Rex    In         fo       = n ew Ar r      ayL      ist<>();
                IMatchN   ode aggregat  eInp  u   t = from ==        to    ? input : concatToMatchNo       de(builder, left,
                    GQLRelUtil.toRel(agg regate.getIn              put()), to, input,             inp utRex  No              de2Re        xInfo)  ;
                         int la        stNodeInde x    =  agg regateInput    .g  etPathS   ch  e ma  ()    .g   etFiel      dCou    n    t() - 1   ;
                         if (la   stNod     e  Ind    ex <  0    ) {
                throw new  G   e aFlow        DSLException(   "Need at least   1      n          ode  i                n the   pat   h to rewr     ite.") ;
                      }
             //Ma  tchA   ggrega    te needs to ref  ere    nce path         fie        lds, no t using ind  ices for ref   erencing,
              //   but usi  ng Rex   Node i             nstead.
               L ist<MatchAggregateCall> adjustAgg  Ca      lls;
                 Lis       t<Re   xN          ode> adjust    Gro  upList;
                       Set<Stri     ng>     m        atchAggPathLabels =    new Ha               shSet<>();
                           if   (!inputRexNode2Re x      Info.is      E     mpty       ()) {    
                   adjustGroup   L ist =          aggregat   e.getGrou         pS et().asList().stream    ()
                                     .map(inputRe   xN  ode2R    exIn  fo::get).col l     ect(C    ollec tors  .    to  List());
                ad     justAgg   Calls       = aggregate.       get           AggCallLi   st(  ).stream().map(  
                                aggCall    -> {  
                                                               List<RexN   ode> newAr   gList = aggCall.getArgList().           st  ream(   )
                                          .map    (inpu    tRe xN     ode2RexInf        o: :ge t).collect(     C    o llectors.toList());    
                                      r      et     urn new MatchAggregateCall(    aggCall.     getAg      gregation(),     aggCa    ll.isDist  inct( ),
                                      aggC      all   .isApp           roximate(),    ne   wArgLi  st, aggCall       .fi  lterArg   ,
                                                     aggC all.g    etColla     tion        (    ), aggCall.getType()  ,          aggCall.g   etName(               ));
                               }).co   llect  (Collectors.toList()) ;
              } else {
                             St    ri     ng lastNodeLabel = aggrega  te      Inp ut.    get   PathSchema(). getFieldList()
                          .ge t(la   stN   odeIndex   ).getName();   
                         matchAggPathLab  el   s.add(last   NodeLa   bel) ;
                                           R     elDataType old  Type = aggregateInput.ge   tPa th      Schema().getFie    ldL is     t()
                                .g e t(     las     tNodeIndex).getTyp             e()   ;
                         adjus      tG roup          Lis                 t =      agg  regate.getGrou pSet().asLi   st()  .    s    tr    eam   ().map(idx -> builder.getRe     xBuilder    ()
                             .m                ake    Fi        eldAccess(new PathI   npu tRef(  las        tNodeLa  bel,     lastNodeIndex,      oldType),
                                       i   dx  ))    .coll     ect(Collecto         rs.  toL           ist()                );
                           a  djustAggC    alls = aggregat        e.g  etAggCallL i  st()                   .strea   m().map (
                                     ag gC all -> n   ew MatchAgg   re ga   teCall   (a   gg    Call.get  A    g gr    egation(),
                            aggCal     l.isDistinct(   ), a                 gg Call.is          A     pproxim       a     te(),
                           aggC     all.g   e    tA rgL     ist   ().stream()       .   map(id  x     ->   builder.getRexBui    lder().makeFieldAcc es  s(
                                            new   Pat hInput Ref(la       stNodeLabel,       la  s    tNo deInde     x,   ol   dType), idx)).collect(Collectors .toList()), aggCall.fi      lte rArg,  aggCall.getCollat                      ion() ,
                                       aggCall.getT     y       pe(), aggCa      ll    .getName())).   c   o    llect(C  ol   lec           tors.     toList ());      
                                                 }

                  //Get the     pruni     ng   path.
            if (l  eft    !=   n  ull)     {
                                                matchAggPathLabels.addAll(left.g etPa t   hSchema().   getF    i  eldNames(   )  );
                        }
                               for              (RelDataTyp   eField field    : aggregateInput.getPa  t               hSchema()        .getField List()) {
                                         if (fi         eld.getType() instanc      eof   V   er      texRecordTy                 pe && adju     st    Gr    o    upLi    st     .s    tream()  
                                     .anyMa    tch(rexNode -> ((PathInp    utRef) ((RexF ieldAcce    ss)   rexNod   e)
                                             . getR            efere  n   ceExpr()).getLa      be     l().equals(field.      getN                  ame())
                                 &&           rexNode.getT  yp    e(              ) i    nstanceof               MetaFieldType    
                              && ((MetaFieldType) rexNode.ge    tT             ype()).getMetaField()
                                         .equals(M  etaF       ield.VERTEX            _I     D))) {  
                     //The condi            tion       for    pres    e       rving vertex in the after aggregating path i    s   t  hat the   
                                      /     /  ve         r   tex I     D  appears in the G  roup.
                                                  matchAgg    PathLa be  ls  .add(fi       eld.    g   etName());
                            } el   se    if (field.   get                 Type( ) i nstanceof EdgeRecord   Typ     e)  {
                                     //Th  e con        dition for p     reserving   edges in the  af  ter aggregati    ng     path    is      that  
                          //    the       edge  sr           cI d, ta             rg       et    Id,      an    d tim   estamp ap   pear in      the       G           roup.
                                         //to     do: Since the GQL inferr        ed from SQL    does not   ha      ve a Union, we will
                                       // temporarily   not consid   e  r       Labels here.
                                    b                    oole     a   n grou  p     BySrcId = a       djustGr     oupList.stream()      .anyMatc       h(rexNode - >         
                                                         ((PathInputR  ef)   (( RexFie ldAcc    es      s) rexNode                 )    
                                .   g      etReferenceExpr())       .      ge        tL  ab  el().equals(field  .getName())
                                                    && rexNode.ge tType(       ) instanceof        MetaFie      ldType
                                             && ((MetaFieldType)     r     exNode    .getType()).       getMeta   Fiel     d()
                                                        .equa   l   s        (Met     aFie  ld.E  D   GE_SRC_ID)  );
                                  boole               an group   ByT    argetId =       adjus   t   Gr oupList.stre    am           ().anyMa     tch(rexNod          e ->
                                      ((Pat      hInput  Ref) ((Rex  FieldAccess) re  x Nod   e)
                                                                 .g   e tReferenceExpr())  .get  La     bel().equal s(           field.g    e          tName())
                                                                  &&            r  exNode.    getType    () instan         ce     of Me         taFieldTyp      e
                                            && ((MetaFieldType ) re    xNode  .getType      ()).getM        etaField()
                              .   equals(M etaFiel        d.    EDGE_T A RGET            _ID) );
                          boolea    n grou   pByTs       = true;
                      if ((( Edge   R  eco   rdType ) field.get T ype  ()).ge       tT     imes  tampFie ld().isPresent        ()) {           
                                                                    groupByTs = adjustGroupList.st   ream().any   Match(rex Node ->
                                             ((PathInputRef            ) ((R               ex    F   ieldAccess) rex   Node)
                                                                      .getReferenceExpr   ())     .getLab el().   equals(  field.g      et    Name())
                                                                       &   &  rexNode.getType() instanceof M                       etaFi    e  ldType
                                                     && ((              MetaFi eld Type        )         rexN                 od  e.getTyp    e()).        g      etMetaF i  eld()
                                                             .equa   l  s(Me     taF ield.EDGE_TS));
                                       }
                                       if (groupBySrcI       d &&  g        roupByTargetId && gr          oup  ByTs) {
                                      matchAggP      a thLabe    ls.     ad  d   (field.   getNam e());
                                         }
                       }
                            }
                       if (  mat  chAg    gPathLabels.i     sEm    pty()) {   
                           matchAggP                         ath    Labels.a  d  d (aggregateInput.getP   athSchema().firs  tF           i     eldNam    e().g et ());
               }

             Pat     hRec        ordType aggPathType;
                   if (adju       stGroupList  .size() > 0   || aggrega  t   e.getAgg   C  allLi  st     ()       .siz  e() > 0)     {  
                                                      Pat  hRecordType  pat   hType = a    ggregate             Input.getPathSche   ma    (     );
                         Path    Reco   r   dType   prunePathType = new P        at  hR  ecordType(p   athType.getFie     ldList().str         e      am     (       )
                                  .    f  ilter    (f -> matchAggPathLabels  .   c   o   ntains(f.   getNa    m          e()))
                                                        .c oll ect(Col               l      ectors   .to   List()))     ;
                            /   /Prune th       e pa th,    and ad   d the a  gg   rega          t      ed valu e to the be  ginning of the path.
                                 RelDataTyp         e fi  rstNod        e       T  yp     e    = prun ePathTy      pe.firstField(   ).get   (    ).   getType()     ;
                                  Str ing    firs tNodeName = prunePath    Type  .firs          tField().  g       et().getName();
                                 i         n     t offset;
                  if (firs      tNode    Typ          e i  nstan    ce   of Verte   xReco rdType) {
                                Vertex    Rec  ord   Type      verte    xNewType = (                                Ve r   texRe  c   ordType) firstN   ode   Type   ;
                                   List<S             t   ring   > addF ieldNames    =         this.genera  teFie  ldNames("   f"   , adjustGr        o    upList.  s  ize(),
                              new      Ha                             s  hSet<>(vertexNew   Type .getFieldNames()));
                                                             o      ffset = ver t    exNewType.  get   Field Count();
                             for (int i =  0   ; i <       adj   u  stGr  o  up   L     is  t.size    ()  ; i++)   { 
                                  RelD   ata Type     dataType = adjustGrou      pList.get   (i).getType  ();
                                                       vertexN                        ewT    ype = vertexNewTy pe.add(addFie    l    dNam  es.get(i)           ,       d        ata   Type,     true);
                                                  }
                          addF iel       dNames         = genera  teFiel dNam    es (   "agg", aggregate.get   A  ggC allList().size      (),
                                          n ew H              ashSe t<>(vert exNewType  .getFieldNa     mes()));
                               fo   r (int i = 0;  i   < aggregat e.g   et A ggCa     llList ().size(); i++) {
                                           vertexN   e  wType   = v      ertexNewType.add(addField     Names.get(   i),
                                                ag  gregate         .getA ggC allList    (      ).get(i).getT   ype(), true); 
                        }
                                           for (  int i = 0  ; i <      adjustGr   oupL ist      .   size()       + a ggre    gate.getA    ggCallList ().size()   ; i++  )   {
                                             rexNodeMa          p.add(builder.get  Re   xBu         ilder().makeF  ieldAccess(
                                new PathInputRef(f          irs   tNo                          d   eName, 0      , v     ertexN ewTy             pe), offs   et + i));
                                                            }
                                     aggPathTy                 pe = new    Pa thRecordTyp      e(new    A rr   ayL    ist   <>())
                                                      .   add Field      (firstN          o           deN   a me,       vertexNewType ,  true);
                                          a gg       PathType       = aggPat    hTyp     e.concat(pruneP    athType, tru  e);
                 } el  se if (firs     tNod   eType i  nstanceo   f EdgeRe    c     o rdTy   p      e)            {
                                                   EdgeRecordType          edge   NewType = (EdgeRec   or       dTyp    e) first            Nod    eType;
                          List<   St   ring   > addF   ieldNames = this  .    gene r             a    t   eFieldN     ames("f", adjustGrou    pLi    st.size(       ),
                                       new                   Has  h  Set<>(edge  NewType.g     etFiel  dName   s    ()));
                                           o   ffset   = edgeNewTy     pe.ge        tFie  ldC     ount();
                             for (int i     = 0;        i <  adjus    tGr    oupL      ist.size(); i++) {     
                                                       RelDa     taType dat   aType          = adjustGrou   pList         .g   et(i).g    etType      ()  ;
                                                    edge   N     e wType = edgeNewType         .add(        a    ddFiel dNa      mes.g     et(          i), da  taType, tru   e   );  
                          }
                                                                  addFie   ldNames = gen      erate Fi       eldNam          es     (             "agg", aggr    egate   .         get Agg Call    List().size(),
                             ne       w    Ha       s    hS     et     <>(edgeNe w   Type.g   etF  iel   dN  ames()));
                                                 f    or (  int i = 0; i <   aggregate .g  etAggCallLi       st(         ).si ze()  ; i++) {    
                           ed geNewTyp    e = edge  New        Typ      e.add(a       ddFieldNames.        get(i),
                                                           aggr    eg    at  e.g etAggCal                           lL  ist().get( i).g        etType      (), true);
                                                 }
                          for (  int     i = 0; i       < a  d   j  ustGroupList.size()        +   aggregat     e.get          AggCallLi  st().size   (  )  ;  i++) {   
                                     rexNod      eMap.  a  dd(builde            r.      getRexBuild      er   ().makeFieldAccess(
                                                            new Path    In     putRef(f   irstNodeName, 0, e   dgeNewTy  pe), offset          +    i))           ;  
                                     }
                                                   agg Path    Type  = n   ew PathRe  cordT     y         pe           (n   ew A     rrayLi    s    t<>( ))
                                   .addField(firs               tNodeN  am  e       , edgeN ewTyp  e,      true)   ;
                                aggPat    hType = aggPathTyp   e.concat(prun          e   PathType, true);
                  } els e {   
                                                 throw      ne        w GeaFlowDSL   Excepti on("Pat h no  de should be          vertex or     e    dge       .");
                                   }
                                            retu    rn     MatchAggreg  ate.cre at  e(    ag   g       regate    Inp   u     t, aggregate.indicator, a   djustGroupList,
                                                a       djus           tAggCa     l   ls,   aggPathType     );
                   } else {
                                                        r et    urn aggregateInput;
                         }
                 }
             return input;     
      }

          /   * *
            *   Ge    ner ate n     non-repeating field nam   es with the format "[  $           pre   fix   ][$ind           ex_nu    mb     er     ]".
      */   
    prote       cted List<    String> gen        erateFieldName   s(Str   ing    prefix, int            nameC      ount,
                                                                        Col   lection<St ring    >       existsNames) {
                                    if (n  a    m    eCo unt <= 0)   {
               return Collec             tions.emptyLis    t                   ();
              }
                Set<            S   tr   i      ng> exist    s = new H     as               h                 S         et<>(exis      tsNames) ;
                                     List<Str        ing  > validNam    es = new ArrayLi  st<>(nam        eCount);
           int i = 0;
                     while (val   id    N  ames.  size() < nam  eCount) {
                     Strin   g ne    wNa  me = prefix + i;
                    i   f (!exists.contains      (newName)) {
                     validNam  es.add(newN              ame);
                }
                     i++    ;    
                        }
               retur     n    validN          ames           ;
    }

    /**     
              * Re     pla   ce the     r    eferences to p   ath no de   s i  n the lef       tRexNodes with references      to
               * the n        e  wGra        phM   at   ch directly, based on th   e lab e  l of         the reference.
                  */
    p    rotect  ed List<RexNode>  adjustLeftRe    x   Nodes(Lis t<RexNod  e> leftRexN   odes, Gr   aphMatch newGraphMatch,
                                                                                  R      elBuil der  b       uild  er) {
             return l   eftRe x  N         odes.stre   am().map(prj     -> GQ    L      RexUtil.replace(prj, rex   N    ode -> {
             i    f (rex   Node inst  anceof      R   e  xFi    eldAccess
                                 &&   ((RexFie    ldAc   cess)  rexNode   ).getReferenceE   xpr   ()    instanceof PathI np  ut  Ref) {
                   PathI  nputRef pathInputRef =
                                 (PathIn    putRef)             ((RexF   ieldA   ccess) rex  Node).     getR        e f  eren     ceEx   pr()  ;
                                      St    r    ing label =      pat hInp  utR    e  f.ge           tLabel();
                          in   t i    ndex = n      e   wGraphMat    ch  .getRo        wType().getField    (  lab e       l  ,           true,           f   als   e ).  get Index();
                                  RelData        Typ e            t  ype      = newGraphMatch.getRowT  ype        (       )      .getField(lab            e   l ,   t ru       e, false)
                                   .getType();
                     Pa      t   h  I      n      putRef  vert                exRef = new PathInput    Ref(la     b  el,      index, type);
                              return builder.get    Re   xBuilder().makeFieldAc        cess  (vert   exRef, (      (RexFi eldAcces s)     rexNode).get   F  i        e  l    d().getIndex());

                     }
                     return rexN o de;
        })).collect(Collect ors.toList());
        }

    /**
           * Replace the references to pat   h no   des in t  h  e leftRe  xN             o    de s with r  efe      rences to
       *    th             e newGr       aphMat   ch, base    d o     n th e label      o   f the ref    ere    nce. If the label of    the r  e fere     nce
              * exis  ts i  n the  left br   anch, autom      atically ad      d            an of  fset to make i          t a reference    to    t            h  e    sam    e            
            * na  me     No  de    in   the right bra  nch. 
                * /
        protected   List<Re      xNode> adjus             t  Ri        ghtRexNodes     (List<RexNode> rightRexNo d  es,            
                                                                       Gr           aphMatc         h                n       e     wGra  p        hMatc  h, Re              lB             u     ild     er builder,
                                                                                                             IMatch   Node l eftPat  hPattern,
                                                                                                       I  MatchNode rightPat  hPattern    ) {    
                    final IMatch Node   finalLeft          =               leftPa    thPattern;
        fi    nal I MatchNo   de finalRight     = rig       htP   athPattern;
                          ret  urn ri      ghtRexNodes         .  strea      m()      .map(prj ->  GQ  L    RexUt    i    l.repl  ace(             prj,   rexNode -              > {
              if    (   rexNo   de          instanceof R    exFieldA    ccess
                                                                     && (    (Rex       FieldAccess) rexNode).getRefere   nceExpr()      instanceof  Pat   hIn                       pu   tR ef) {
                     Pat  hI    nputRef pat     hInp  utRef    =
                             (Pa         thInputR         e f     ) ((R  exFieldA  ccess)      rexNode).ge       t              Refer    enceE        xp             r ();
                                  Strin          g lab           el = pathInputRef.      g    et  Lab   el();         
                boo l ean          i        sC   onflic  tLa  bel = lef  t PathPattern.getPathSc         hema().g        et Fiel  d Names()
                        .con  ta    i      ns(l        abel);
                           if            (isConfl ictLabel)       {
                                     int ind  ex    = finalRight.getRo             wTyp            e().      getField(label, t      rue, false).ge tIndex();
                              index +=  finalLeft.g  etRowT           ype()     .getF   iel   dCount();
                     labe      l = newGr   aphMatch    .getRowType().getF   ieldLi  st(   )    .get   (i   nd   ex).getName();
                         RelData     Ty  pe type = newGra p     hMatc   h.getRo   wType().g   e   tFie   ld                    List().get(    i ndex)
                              .        getTy   pe();
                           Path   In  putRef               vertexRef        = new PathIn  putRef(label    , index, type);
                                 ret     urn          build          er.getRexB              uilder(     ).ma   keFieldAcces  s(vertexRef,
                                      (    (RexF   i           eldAccess) rexNode          ).getFi      eld().getIn       dex(       ));
                          }               else  {
                      int index       = newGraphMat         ch.g          etRowType   ().getField(label, true, false).ge           tIndex();
                           RelDataType t   ype = newGraphMatch.g     et   Ro        wType().getFieldL         ist().get(in   dex   )  
                                              .g                         etType    ();
                                Path  InputRef vert exRe    f    = ne     w  Pa   t  hInputRef(    lab      el, index, type)        ;     
                          retur    n builde    r.getRexBuilder().m   ake     FieldA ccess(v          er         texRef,
                                  ((RexFi   eldAc      ce       ss) rexN     ode).getFiel      d()  .getInde   x());             
                        }
                 }
                retur   n r   e      xNo   de;
                      }    ))   .collect (Colle   ctors.toList   (             )  );  
    }

           /**
             * Han               dli  ng th      e transform   a  tion      of     SQL             R      elNodes fo   r the         MatchJo        inTable  ToGr  aphM   atc  h
       * and Table Jo          in       MatchTo     Grap   hM   a  tc  h rule   s in to GQL m atch  es.
      */
        pr      otecte   d   Rel     Node pro   cess      Graph  Mat     c    hJoinTa   bl       e     (RelO p  tRule  Cal  l     call, Logical Jo       i n  j        o  in   ,
                                                                                                          Logical  GraphMatch g    raphMa    tch,
                                                                 Log     ica   lProject project,       
                                                                                     LogicalTableScan  tab      leScan,
                                                                                           RelNode leftIn  p      ut, RelNode    left      H ead,
                                                                                                                  Re  lNode     rightInp       ut,  RelNode righ     tHe     ad,
                                                                                               boolea       n           isMatchInLeft        ) {       
               GeaFlowTable       geaf    low        T  able = ta  bleScan.get Table().unwra p  (G   eaFlowTable.c     lass);
             GQL   JavaTypeFactory         typeFactor    y = (GQLJavaTypeF   actory) c    all.bui  l   der().getTyp  eF    actor                      y(         );
             GeaFlowGra  ph cu   rre   ntGraph     =  typ  eFactory.getCu                           rre   n   tGraph(           );
            if   (!c  ur     rentGraph.cont ainTable(   geaflowTable)) {
                   if (ge        aflo   wTable insta            nceof Vert     e        xTable || geaflowTab l   e     in  stanceof EdgeTable) {
                t        hrow new        G              eaFlowDSLExc      eption(    "Unknow n     graph element:       {},  u            se graph pl  e        ase.",
                                                    geaflowTab      le.getN     ame()  );
                }
                retu                 rn null;
        }
           Gr  aphJoi       nT   y  pe gr  aphJoinType = ge tJo  inType(join);
            R elDataTyp   e tableT  ype         =  tableScan.g  etR owT  ype();
                IMatc hNo de     match        Node        = graphMa     tc   h.getPathPat   tern();
          Re lB                 uilder         r e  lB uilder     = call.    b  uilder()     ;
                                  List<RexNode> rexLeftN    odeMap = new         ArrayList<     >( );
          List         <Re  xNo   de> r  exRightNodeMap = new ArrayList<>();
           IMatchNode     co   n  catedLeftMat   ch;
             IMatchNod       e newPathPat   tern;
            b    oolean      isEdgeRev    erse = f   alse;
            switch          (gra         ph    JoinType) {
                  cas     e      EDGE_JOIN     _VER      TEX:
                   case V              ER    TEX_JO     IN_EDGE                    :
                                 if (geaflo    wTabl      e in  stanceof Vertex    T    abl   e) { // g     raph match j   oin     vertex tab   le    
                                    if         (matchNo   d   e    inst                    anceof Singl            eM  atc           hNode    &&    GQLRelUtil.ge    tLatestMatchNode(           
                                          (              Single     Ma       tc        hNode  )      ma         tchNode)   inst  anceof   E     dgeMa    tch  ) {
                                        concat    edLeftMatch       =
                            isMatch      InL    eft ? concatToMatchNode(rel  Bui lder,     null, leftInput,
                                            leftHead  ,       matchN     ode, rexLeftNode       Map)
                                                                       : concatToM   a    tchNo    de( relBu    i     lder, nul   l, r    ightInput,   
                                                                                            rig         h                  tHe  a  d, match Node, rexRigh     tNodeMap);
                             String nodeNam   e =   geafl  owTabl      e.ge  tNa     me();
                                                PathRecordType pathR  ecordType =
                                            co   ncatedL  eft    Ma tc    h.getPat hSch        ema().getFieldNames().co    ntai  n   s(nod   e              Name      )     
                                          ?         concat    edLe   ftMatch.getPa               thSchema(     )
                                             : concatedLeftMa   t        ch.getP    athSchema().ad d Field(nod eNam           e, tabl eType, f  alse)     ;
                              switch (join.getJoinTy       pe     ()) {
                                               c a         se LEFT     :
                                     n  ewPathPattern =  Option  alVertexMatc    h   .create    (concatedLeftMatch.getCluster(),
                                              (  S        ingleMatc     hNo    de) concatedL     e  ftMatch      , nodeName,
                                                          Colle   ctions.s     in gleto  nL  is      t(nodeN           ame), tableTyp                        e, pa    thRe                  cordTy  pe);
                                             break;
                                                    cas      e INNER:
                                                     ne  wPathP     attern      = VertexMatch.create          (concatedLeftMa    t    ch.g etCluster() ,
                                                                    (Singl     eM    atchNode      )  co     nca     tedL  eftMat       ch,   nodeName,
                                                           Collec     tio   ns.si  ngletonLi  st(nod     eName          ), tableTyp   e, pa   th    R      ecordType);
                                          break;
                              case R    I   GHT:
                                                   cas e F    UL  L:
                                                    defau  lt:
                                                 throw new    GeaFlo       wDSL  Ex   ception("    Il legal join type:    {}",  join  .                         g   etJoinType());
                                                         }
                                  n  ewPathPa   ttern =   
                                             isMatchInLeft ? conc atToMa     tchNode(r  elBuilder,         concatedLeftM  atch, rightInput      ,
                                                        ri   ghtHead, newPathPattern, r    ex             Rig   htNodeMap)
                                                                           :     co  nc atToMatchN           od   e   (         relBuilder     , c            on     cated      LeftMatch, lef   tI      nput,
                                                             l    eftH  ead, ne   wPathPattern, r          exLeftNodeMa     p    );
                                                         } e    lse {
                          String no    deName = ge     a    f  low  Table.get     Name();
                                   assert cur      rentGraph.g  etV    ertexTables     ().str eam()
                             .an  yM  atch(t ->      t.getName(). equalsI              gnoreCase(geaflowT       abl          e.g                   etNa     me()));     
                                                   con     ca    tedLeftMatch        =
                                   isMatc hInLeft ? con   catToMat    chN   ode(relBuild         er, null, l          eftInp    ut,            leftHe ad,
                                                matchNode,    rexLeftNodeMap        )   
                                                           :     conca           tToMatch     N     ode(relBuilder, null       , rightInput,     rightHead,
                                                                         matchNode,         rexR   ightN   odeMap);
                                           RelDataT   yp  e     vertexRelTyp    e =    ge  aflowTable.getR       owType(
                                 relBuilder.  getTy       peF  actory()  );
                                            Pat    hRecordT   y   pe rightPath Type = Pat     hRecord Type.EMPTY.       ad dField     (geaflowTa  ble.getName(),
                                                v e    rtexRelTy    pe    ,         fals  e);
                        VertexM     atc  h ri  ghtVertexMatch = V ertexMatch.cr     e     a   t               e(concate dLeftMatch.g etCl   uster(), nul  l,
                                             nodeNam e, Collect   ion   s.singletonList(  ge    a   flowTabl    e.g        e   tName()),
                                  v  ertexRelTyp    e,       rightPa                    th    Typ         e)           ;
                                    I     MatchNode matchJoinRight =
                                            is  MatchInLeft ?               concat  ToMatchNod      e(relBuil   der  , null,    rightInput,
                                       r   igh     t Head,           r     ightVe  rtexMatc         h , rexRightNode    Map)
                                                                                                      :    concatTo  MatchNode      (relBuilder   ,   nu      ll, left Input,  
                                                                       left   Hea  d ,   rightVertexMatch, rexLeftNodeMap);
                                Ma  tchJoi     n mat     chJo  i      n = Mat chJoin.crea  te    (concatedLeftM   atch.g   etCluster(),
                                     conca tedLeft   Match.getTraitSet(), concatedLeftMatch,  matchJo       inRight,
                             relBuilder.getRex    Builder()      .mak        eLiteral(true),     j      o  in.   getJoinType(    ));

                                                          PathInp   u         tRef vertexRef = n ew PathInputRef(nodeNam   e,
                                     mat   chJoin.getRo        wType(     ).getField(              n     odeName, tr            ue, false ).g  etIndex(),
                                matchJoi             n.getRowType().getField(nodeName  , t    rue, false) .getTyp        e       (  ));
                                      Re     xNode op  e        rand1 = r     el     B   u   ilder.getRexBuild  e            r()
                                     .m   akeFieldAccess(vertexRef,  VertexTyp    e.ID_FIE         LD_POSITION);
                                 RelDa taTypeField field =   matchJoin. ge  tRowType().getFieldList().get(
                                      matchJoin.getRowType().   getF    ieldCount() - rightV  e     r   texMatch.getRowT  ype   ()
                                            .getFiel dCoun  t());    
                        vertexRef = new P  athIn  putRef(field.getN          a               me(), field.ge     tIn   dex(),
                                  field       .ge       tType());
                           RexNo de operand2 = r      elBuilder.   g    etRexBu   ilder()
                               . makeFieldAccess(vertexR  ef, VertexT   ype.ID_FIELD_PO    SIT   ION);     
                                        SqlO  perator    equalsOp  era        tor   = S  qlStdOperatorTable.EQUALS               ;
                                       Re xNo      de condit      ion = relBuilder.g     etR     exBuilder()
                                      .makeCall(  equalsOperato   r, opera     nd     1      , oper   and2);
                                   newPa thP         at  tern    =     matchJ      oin.copy(matchJ   oi   n.getTr    aitSet(),    conditio  n,
                                matchJo  in.getLeft(),    ma       tchJoin.getRight(), mat chJoin.getJoinType()       );
                           }
                                } e lse if (geaflowTable instanceof EdgeTable) {
                     if      (matchNode insta nceof Singl    eMatchNode && GQL  RelUt il.getLatestMatchNode (
                                   (Si    ngleMa   tchNo       d e) matchNode) instanceof Verte  xMatch) {    
                         c   oncated L eftMat    ch =
                                            isMatchInLeft    ? concat     ToMatchNode(relBuild   er,   null, le    ftIn      put, l   eftHead,
                                                   matchNod  e, rexLeftNodeMap)
                                                                   :            concatToMatch   Node(relBuilder, nu        l    l,    rightInpu t, rightHea     d,
                                                       matchNo    de,       r  exRightNo             deMap); 
                                     isEd geReverse = graphJoinType.equa   l  s   (GraphJoin  Type.E D      GE_JOIN_    VERTEX); 
                        EdgeDir  ection edgeDirection =    
                                                                i     sEdgeReverse ? EdgeDirection.IN : EdgeDirection.OUT;
                           String edgeName =    geaflowTa   ble.getNam e();
                              Pat hRecor    dType path    Record            Type =
                                                    concatedLeftMatch.getPathSch  em a().getField        Names   ().contains(edgeName)
                                 ? concated  LeftMatch.getPathSchema(    )
                                   : con     catedLeft Match.getPa thS   ch  ema().addFie   ld       (edge  N       ame, ta    bl  eT   ype, false);
                            swit  c  h (jo   in   .get   Jo  inTyp    e()) {  
                                             case LEF     T    :
                                    if (!isM         atchInLeft) {
                                                   LOGGER.wa rn("    Lef      t  table cannot be for  cibly    retained. Use INNER Join i nstead.");
                                        }
                                                 newPathPattern = OptionalEdgeMa  tc   h.crea     te(conca   te     dLeftMatch.get  Cluster(),              
                                                           (SingleMatchNode) concatedLeftM    a     tch, edgeNam            e,
                                             Collections.si  n gletonList(edgeName), edgeDi  re   ction,       tableType,
                                          pathR   ecordT ype     );
                                               break;
                                          ca se            INNER     : 
                                           new     PathPatter n = EdgeMatch.cre    at   e(concatedLeftMatch       .getCl  uster() ,
                                                      (Sing    leM      atchNode) con   c  a   tedLeftMa tch, edgeName,
                                               Coll ections.si     nglet          onList(   edgeName), edge        Direction,   ta bleTyp  e,
                                             pathRecor  dType)     ;
                                          break;        
                                case    RIG        HT:
                                         case   FULL:
                            default   :   
                                    throw new Gea     FlowDSLException("Illegal join type: {}", join      .getJoinT    ype());
                                }
                            ne       w    PathPatte rn =
                                     isMatchInLeft ? c oncatToMatchNo de(relBuilder, con      catedLeftMatch, rightInpu       t,
                                           rightHead, n      ewPathPattern, rexRightNodeMap)
                                                                           : con  catToMatch   Node(r     elBuilder, concat   edLeftMa  tch, leftInput,
                                                           le   ftH     ead, newPathPa      ttern, rexLeftNo   deMap);

                             } else      {
                                        String edgeName = geaflowTable.get        Name();
                                    GraphDescriptor gr aphDes    c  ript or = currentGraph.getDescriptor();
                         Optional<EdgeDescriptor> edgeDesc       = graphDescri      ptor.edges.stream()
                                .f    i  lter(e -> e.type.e  quals(geaflowTable   .getName())  ).findFir   st();
                             Ver     texTable dummyVertex = null;        
                        if (ed     geDes      c.isPr   esent()) {
                                        EdgeDescriptor edgeDescripto      r = edgeDesc.   get();
                                 St                  ring dummyNodeType = edgeDesc     riptor.s   ourceType;
                                   dummy       Vert ex = currentGraph.getVertexTables    ().stre  am( )
                                           .filte   r(v -> v.getName().   equals(dummyNodeType)). findFirst().get();
                        }
                        if (dummyVertex == null)   {
                            return nu ll;
                                       }
                                   String dummyNodeName = dummyVertex.getName();
                                 RelDataT  y  pe dummyVertexRelType = dumm      yVertex.getRowType(
                                relBuilder.getTypeFac tory());
                                    PathRecordType pat   hRecordType =       new Pat   hRecordType(
                                         new ArrayList<>   ()).addFiel    d(dummyNodeName,   dummyVertexRelT  ype, true);
                                      Verte     xMatch du    mmyVertexMatch = VertexMatch.creat e(matchNode.getCluster(),
                              null, dummyNodeNam e, Col   lections.sin gletonL     ist(d u   mmyVe  rtex.getName   ()),
                                                 dummyVertexRelType,   pathRecordType   );
                        RelDataType             edgeRelType = ge  aflowTable.getRowType(
                                  relBuilder.getTypeFactory());       
                             pathReco    rdType = pathRecor        d Type. addFiel  d(edgeNa   me, edgeRelType  , true);
                           EdgeM      atch edgeMatch = EdgeMatc       h.create(matchNode.getCluster(),
                                         dumm  yVertexMa       tch      , edgeName,
                                    Collection s.singl etonL   ist(geaflowTable.getName()), EdgeDirection.     OUT,
                                          edgeRelType, pathRecordType);

                             concatedLeftMatch =
                                isMatchInLeft ? concatToMatchNode(relBuilder, null,  leftInput, leftH     ead    ,
                                             matchNode, rexLe ftNodeMap)
                                                   : concatToMatchNode(relBuilder, null  , rightInpu   t      , rightHead,
                                                                   ma      tchNode, rexRightNo    deMap);
                              IMatchN   ode m  atchJoinRight =
                              isMat  chInLeft ?     concatToMatchNode(relBu  ilder, null         , rightInput,
                                        ri     ghtHead, edgeMat    ch, rexRightNodeMap)
                                                   : concatT oMatchNode(relBuilder, null, leftInput,
                                                     left Head, e dgeMatch, rexLef tNodeMap     )  ;
                            MatchJoin        matchJoin = MatchJoin.create(matchNode.getCluster(),
                              matchN  ode.getTraitS e  t(), concatedLeftMatch, matchJoinRight,
                                         relBuilder.getRexBuilder().makeLiteral(true), join.get      Join  Type());

                           PathInput     Ref vertexRef =  new PathInputRef(dummyNodeNam e,
                               ma             tchJoi     n .getRowType().g  etField(dummyNodeNam  e, true, false).getIndex(),
                             matchJ     oin.getRowT    ype().g etField(dummyNodeName, true,   false).getType(   ));
                                      RexNode operand1 = relBuilder.getRexBuilder()    
                                            .makeFie    ldAccess(vertexRef, V   ertexType.ID_FIELD_POSITION);
                        RelDataTypeFie ld field = matchJoin.getRowT   ype().getFieldList()    .get(
                               matchJoin.get RowType().getFi     el   dCount() - ed  geMatch.getRowType(  )
                                      .getFieldCount());
                          vertexRef = new Pat   hInp   utRef(field.getName(), field.getIndex(),
                             fiel     d.g    etTyp      e());
                           RexNode operand 2 = relBuilder.g   e   tRexBuilder()
                                     .makeFieldAccess(vertexRef, VertexType.ID_F    IELD_POSITION)  ;
                         SqlOperator equalsOperator = SqlStdOp   eratorTable.EQUALS;
                                      RexNode condition = relBu    ilder.getR      exBuilder()
                              .makeC    all(equ als                Operator, operand1, operand2);
                           newPath  Patte  rn = matc    hJoin.copy(matchJoin.getTraitSet(), c        ondition,
                                  matchJoin.getLeft(), matchJoin.getRight(), matchJoin.getJoinType());
                          }
                     } el  se {    
                     retu  rn null  ;
                }
                break;
               default:
                          return null;
            }
        if (newPathPatt          ern == null) {
                return null;
            }
          GraphMatch newGraphMa      tch = graphMatch.copy(ne   wPathPattern);
                 // Add the original Projects from th    e GraphMatc     h branch, filt        ering out fields tha   t
        // no longer exis   t after rebu    ilding GraphMatch inputs.   
            List<Re  xNode> ol    dProj   ects = project.getProjects().stream().filter(prj -> {
            if      (prj instanceof RexFieldAccess
                 && ((RexFieldAccess) prj).   getReferenceExpr() instanceof PathInputRef) {
                P   athInputRef pathInputRe f =
                    (Pat       hInputRef) ((RexFieldAccess) prj).getReferenceExpr();
                String label   = pathInputRef.getLabe   l();
                RelDataTypeField pathField = newGrap    hMatch.getRo  wType().getField(label, true,   false);
                   if (pathField != null) {
                       RexFieldAc  cess fieldAccess = (R   e   xFieldAccess) prj;
                         in t index = fieldAcce         ss.getField().getIndex();
                             return    i     ndex < pathField.getType().getFieldList().size()
                        && fieldAccess.getF     ield().equals(pathField.getType(  ).getFieldList().get(index));
                           }
                  }
            return false;
        }).collect (Collectors.toList());
        List<R    exNode> newProjects = new ArrayList<>();
          if (newPathPattern  instanceof    Mat c       hJoin) {
            newProject s    .       ad       dAll(
                         isMatchInLeft ? adjustLeftRexNodes(o   l  dProjects, newG    ra    phMatch,
                    relBuilder) : adjustRightRexNodes(oldProject   s, newGraphMatch,
                    relB    uilder, (IM        atchNode) newPathPattern.getInput(0)   ,
                         (IMatchNode) newPathPattern.get   Input(1)));
        } else {
            newProjects.addAll(adjustLeft      RexNodes(oldP        roje     cts, newGraphMatch, relBuilder));
            }

        RexBuil    der rexBu ilder =         relBuilder.ge     tRex  Builder();
        //  Add fields of the Table into the projects  .
        String tableName = geaflowTable.getName();
           RelDataTypeField  pathTableField = n    ewPathPattern.getPathSchema()
              .getField(tableName, true, false);
         List<RexNode> tableProjects = new ArrayList<>();
        if (pathTableField !=   null) {
            int baseOffset = newProjects.size();
                 PathInputRef pathTableR   ef = n      ew PathInputRef(tableName, pathTableField.getIndex(),
                pathTableField.getType());
            tableProjects = tableT    ype.getF   ieldLis    t().stream()
                .map(f -> rexBuilder     .m akeFieldAccess(pathTableRef, f.getIndex()))
                .collect(Collectors.toList());
            newProjects.addAll(tablePr ojects);

                //In the case of reverse matching in the IN direction, the positions o  f the source
            // ve  rtex and the des   tination vertex ar    e swappe   d.
              if (   isEdgeReverse) {
                 int edgeSrcIdIndex = tableType.getFieldList().stream().filte   r(
                    f -> f.getTyp   e() inst   anceof MetaFieldType
                                    && ((MetaFieldType) f.getT   ype()).getMe  taField().equals(MetaFi  e ld.EDGE_SRC_ID))
                    .collect(Collectors.toList()).get(0).getIndex();
                int ed geTargetIdIndex = tableType.ge tFieldList().stream().filter(
                           f -> f.getType() instanceof M   et  aFieldType
                                         && ((MetaFieldType) f.getType()).getMetaField()
                                   .equals(MetaField   .EDGE_TARGET_ID)).collect(Collectors.toList()).get(0)
                    .      getIndex();
                Collectio      ns.swap(newProjects, baseOf   fset     + edgeSrcIdInde       x,
                    baseOffset + edge TargetIdIndex  );
            }
        }

        // Add fields newly added in the rebuild of the left branch.
        if (rexLeftNodeMap.size() > 0) {
            List<RexNode>    tmpLeftProjects = new ArrayList<>(rexLeftN  odeMap);
            newProjects.addAl    l(adjus    tLeftRexNodes(tmpLeftProjects, newGraphMatch, r  elBuilder));
        }

        // Add fields newly added in the rebuild of the right branch.
        if (rexRightNodeMap.s ize() > 0) {
            List<RexNode> tmpRightProjects = new ArrayList<>(rexRig     htNodeMap);
                 if (newPathPattern instance       of MatchJoin) {
                    newProjects.addAll(adjustRightRexNodes(tmpRightProjects, newGraphMatch, relBuilder,
                    (IMatchNode)     newPathPattern.getInput(0),
                      (IMatchNode) newPathPattern.getInput(1)));
              } else {
                  newProjects.addAll(adjustLeftR exNodes(tmpRightProjects, newGraphMatch, relBuil  der));
            }
        }

        // Complete the projection from Path t      o Row.
        Lis  t<RelDataTypeField> matchTypeFields = new ArrayList<>();
        List<String> newFieldNames = this.generateFieldNames("f", newProjects.size(    ), new HashSet<>());
             for (int i = 0; i < ne  w    Projects.size(); i++) {
               matchTypeFields.add(
                new RelDataTypeFieldImpl(ne    wFieldNames.get(i), i, newProjects   .get(i).getType()));
        }
        RelNode tail = LogicalProj      ect.create(newGraphMatch, newProjects,
            new RelRecordType(matchTypeFields));

        // Complete the Join projection.
        if (newPathPattern instanceof Mat  chJoin) {
            rexRightNodeMap = adjustRightRexNodes(r  exRightNodeMap, newGraphMatch, relBuilder,
                  (IMatchNode) newPathPattern.getInput(0), (IMatchNode) newPathPattern.getInput(1));
           }
        List<RexNode> joinProjects = new ArrayList<>();
        //  If the left branch undergoes rebuilding, ta ke the reconstructed R ex f  rom
        //  the left branch, otherwise take the original Projects.
        final RelN   ode finalTail = tail;
        int projectFieldCount      = oldProjects.size();
        int joinFieldCount = projectFieldCount + tableProjects.size();
        if (rexLeftNodeMap.size( ) > 0) {
              joinProjects.addAll(
                  IntStream.range(jo inFi  el  dCount, joinFieldC   ount + rexLeftNodeMap.size())
                    .mapToObj(i -> rexBuilder.makeInputRef( finalTail, i))
                    .collect(Collectors.toList()));
        } else {
            if (isMatchInLeft) {
                  joinProjects.addAll(IntStream.range(0, projectFieldCount)
                    .mapToObj(i -> rexBuilder.makeInputRef(finalTail, i))
                    .collect(Collectors.toList()));
            } else {
                joinProjects.addAll(IntStream.range(projectFieldCount,   joinFieldCount)
                     .mapToObj(i -> rexBuilder.makeInputRef(finalTail, i))
                    .collect(Collectors.toList()));
            }
        }
        // If the right branch undergoes rebuilding, take the reconstructed Rex from the right
        // branch, otherwise    tak  e    the origin al Projects.
        if (rexRightNodeMap.size() > 0) {
            joinProjects.addAll(IntStream.range(joinFieldCount + rexLeftNodeMap.size(),
                    joinFieldCount + rexLeftNodeMap.size() + rexRightNodeMap.size())
                .mapToObj(i -> rexBuilder.makeInputRef(finalTail, i)).collect(Collectors.toList()));
        } else {
            if (isMatchInLeft) {
                joinProjects.addAll(IntStream.range(projectFieldCount, joinFieldCount)
                    .mapToObj(i -> rexBuil der.makeInputRef(finalTail, i))
                    .collect  (Collectors.toList()));
            } else {
                joinProjects.addAll(IntStream.range(0     , projectFieldCount)
                    .mapToObj(i -> rexBuilder.makeInputRef(finalTail, i))
                    .collect(Collectors.toList()));
            }
           }
        AtomicInteger offset = new AtomicInte ger();
        // M   ake the project type nullable the same as the output type of the join.
        joinProjects = joinProjects.stream().map(prj -> {
            int i = off    set.getAndIncrement();
            boolean joinFieldNullable = join.getRowType().getFieldList().get(i).getType().isNullable();
            if ((prj.getType().isNullable() && !joinFieldNullable)
                || (!prj.getType().isNullable() && joinFieldNullable)) {
                Rel  DataType type = rexBuilder.getTypeFactory().createTypeWithNullabili    ty(prj.getType(), joinFieldNullable);
                return rexBuilder.makeCast(type, prj);
            }
            return prj;
        }).collect(Collectors.toList());
        tail = LogicalProject.create(tail, joinProjects, join.getRowType());
        return tail;
    }

    public enum GraphJoin    Type {
        /**
         * Vertex join edge src id.
         */
        VERTEX_JOIN_EDGE,
        /**
         * Edge target id join vertex.
         */
        EDGE_JOIN_VERTEX,
        /**
         * None graph match type.
         */
        NONE_GRAPH_JOIN
    }
}
