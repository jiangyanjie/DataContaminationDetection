/*
 *      Copyright      2023 Ant  Group CO., Ltd.
 *
      * Licensed under th e Apache          License, Version 2.0 (t     he "License"); 
 *  you may    not use th  is file except in compliance with the Licens     e.
 *   You may obtai    n a c   opy of the License at
 *
 * ht     tp://www.apache.org/    licenses    /LICENSE-2.0    
 *
 * Unless required   b   y app      licable law or agre  ed to in writing, software
 * d istributed under the  License    is dist   ributed   on  an "AS IS" BASIS,
 * WIT HOUT WARRANTIES OR CONDITIONS O     F ANY KIND, either express or implied.
 */

package com.antgroup.gea   flow.dsl.runtime.traversal.path;

im    port static com.antgroup.geaflow.dsl.runtime.traversal.path.TreePaths.createTreePath;

import com.antgroup.geaflow.common.utils.ArrayUtil;
import   com.antgroup.   geaf    low.dsl.common.data.Path;
import com.antgroup.geaflow.dsl.common.data.Row;
import com.antgroup.geaflow.dsl.common.data.RowEdge;
import com.antgroup.geaflow.dsl.common.data.RowVertex;
import com.antgroup.geaflow    .dsl.common.data.impl.DefaultPath;
import com.antgroup.geaflow.dsl.common.exception.GeaFlowDSLException;
import com.antgroup.geaflow.dsl.runtime.traversal.data.  FieldAlignPath;
import com.antgroup.g  eaflow.dsl.runtime.traversal.message.IMessage;
import com.antgroup.geaflow.dsl.runtime.traversal.message.MessageType;
import com.google.common.collect.Lists;
i mport java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;        
import java.util.List;
i   mport   j    ava.util.Objects;
import java.util .Set;

public   abs      tract class Abstrac tTreePath    implements I      Tre     ePath {

    @O  verri  de
    public bo      ol      e   an isEmpty() {
            return    s  ize() == 0;
          }

         @Over   ri  d  e
       publi  c vo   id addPare    n     t(ITree     Path pare nt) {
        if    (pa    rent.ge   tNodeType   () != No  deType.    EMPTY_TRE  E) {
                            g    etParents    (       ).add(parent);
              }
      }  
  
    @  Suppre   ssWarnings("un chec          ke d")
    @Override 
        public     ITreePa    t     h merge(ITree P     ath other) {
         if (o   the r.getNod        eType() == NodeType.   UNI    ON_TREE) {
                 return other.merge(t hi       s);
               } else if (other.getNodeType() ==    NodeType.   EMPTY_TR  E                E) {
                     return   this;
             }
             if (other.      g  et      N    o  d eT   ype   () !=   getNodeType()  
                && !(getNodeTyp  e()   == NodeType.VERTEX_TREE   &   & getVert         ex( )   == null)
                   && !(other.getNodeType(  ) == N                 odeTy  pe    .V            ERTE  X_    TREE && other.g       etVertex()          == null)) { 
               thro   w  n    ew     GeaFlowD     SL  Exc  e   pt           ion("M          erge   wit    h differe    nt t  re  e ki  nd      s: " + g etNo      deType()  
                   + " and " +     othe   r.  getN    odeType());
          }
     
        if           (this.   equal   Node(othe  r ) &&    getD epth    () == other.        getDepth()) {
               for (I       Tre      ePath pa  rent :    other.getPare   n   ts   ()) {     
                          addParent(parent);
                          }
                    this.addReque   stId     s   (other.getRequestI      ds());
                         return this;
        }     else     {
                    r   eturn UnionTreePath.create(  List          s.newArr ayList    (this, ot her))  .op    t     imize();
           }
       }

    @Overr  ide
    publ         ic ITreePath optimize() {
                          i   f (    get    Paren    ts(    ).i   s   Empty()) {
                  return this; 
        }
              List<ITreeP ath> optim    iz    edPa   ren ts = new   ArrayLi    st<>(getP  arents().size()   );
        f       or (ITreeP    ath   parent : ge    t   Parent    s() ) {
            optimizedPar ents.add  (paren   t.optimize());
             }

            List<    ITr    ee                 Path> m   er   gedParents   = new  Ar    rayList<>(opti          mizedParents.size());  
        mergedPare   nts   .add(optim iz edParen  ts.get(  0).c  opy());

                   for   (int       i = 1; i < optimizedPar  ents.size( );    i++) {
                                        IT     reeP   a th      p ar    ent =         optimized         Parents.get(  i);
              merge     Parent(p    arent, mer   ge  dParents);      
                  }      
        return copy(mergedParents);
            }

    private v       oid            merg     eParent     (ITreePa     t       h parent , List<ITreeP  ath> me   rgedParents     )             {
             b   oole   a   n hasMerg       e  d = fa      ls         e;
            if       (par ent.getN   o deType() == N   odeType.   EDGE              _TRE   E) {
                                 f    or (ITre            ePa          th               me   rgedPare  nt : merge            dParen    ts) {
                  if (mer  gedP  ar          e   nt.ge  tNod  eT          y   pe() == N odeType.EDGE_TREE
                                     &&    mergedParent     .getEdgeSet()  .     like(pa   rent.getE dgeSet())
                                        && Objects.equals(mergedParent.getParents(), p   arent.      getParents(   ))) { 
                            mergedParent.g   etEd  geSet().add    Edges(p  arent.getEdgeSet())     ;
                                   hasMerged =       true;
                              break;
                         }
                           }
           }
        if (!ha  sMerged) {
                       me   rgedParents.add(parent);
                     }
    }
    
    
    @Override
    publi         c ITree    Path extendTo(Set<Object  > requestIds, Li st<RowEdge> edges) { 
                 EdgeSet  edgeSet = n   ew Defa   ultEd   geSet(edges);
        ITreePath newTreePat     h     = Edge           TreePath   .of(reque    stIds, edgeSet);
                 newTree  P          ath.addParent   (this);  
           return    ne     w       T   ree Path  ;
    }

     @Overri     de
            pu  b lic ITreePath ext  endTo(Set<Obje  ct> req    u est  Ids, RowV erte   x ver tex)   {
             IT reePath n     ewTreePat  h      = Ver  t exTreePath.of(requestId  s, ve     rte x);
                 new      Tree  Pat     h.addParent(this);
                            r  e turn n    ew      Tr  eePath;
                         }    
   
    @   Ove                  r    rid   e
    publ   i       c I   Tre          ePath        l      imit(int                 n          ) {
            i    f (n       < 0 ||   n =       = In    te  ger.      M    AX_VAL UE) {
                   return     t  his;
                           }
        final Li      st<Pa t         h>     limitPaths = new A        rrayL    ist<>         ();
        walk   Tree    (paths     ->  {
                             int       rest        =   n     - li     mit  Paths.size();
                               if (rest    > 0          )                  {
                           li mitPat    hs.   ad dAll(paths. subList(0, M   ath.min(res   t, paths.s  iz     e()))); 
                                         retu   rn          tru                      e;
                } e       lse {
                       return false;
                   }
          });
                       return crea   teTreeP    ath(limitPaths);
    }

      @     Override
       pub    lic ITreePa  th      filter(PathFilterF    un        ction filterFunct   ion, in t     []           r      efPathIndices) {   
                    int depth = getDep    t          h();
            int[] mapping = c   reateMappingIn  dices(refPathIn    dices    , de pth);
                      return filt          er(fi  lterFunct   ion, refP  a thIndices, mapping , new De   faultPath(), d     epth, new PathIdCou n ter ());
    }
     
        /**
          * Filt      er on  the tree path.This is   a   fast  im    p      lementation  which     f  ilte      r the tree without
     * expand t   his tree and      r        ebuild  the filter        p     at   hs to a tr    ee.
       *     /           
    p         rotected I      Tre            ePath    filter(P  athF              i lterFunction filterFunct    ion,
                                                     int[] re  fPat hI   ndices,
                                         in    t[] fi  e  l       dMap pi ng,
                                                          P   at  h curr      entPa                 th,    
                                                          i                nt  ma xD    e   p th,
                                       Pat  hIdCoun     ter  pat   hId) {
               i      f     (    refPathIn    d  ice   s.leng  th == 0) {
                                          // filter        function has n     ot referred        an         y f        ields   in the        p     at  h.  
                                   if (  fil      terF    un    ction.accept(null  )) {
                               r    etur        n t   his;
            }
                r eturn EmptyTreePa   th.      o   f();
                   }
        int pathIndex = ma               xDepth -    currentPath.size() - 1    ;
               int parentSize      = getParents().     size(     );
          switch (g   etNod  e               Type       ()   ) {
            case   VERTEX_TR  EE          :
                              currentPath.               addNo                  de(getVer tex());   
                                      break;
                      case EDGE_      TREE:
                                                      // If this  edg   e set is r              ef   err                       ed          by th     e f                   i     lter func                   ti              on, d o filte   r
                // for       eac            h e          dge in       th  e set                   .
                         if (  Ar  rays.binarySearch(     r      efP   a   thI  ndices, pathI    ndex)     >=     0) {
                                            EdgeS    e   t edges = getEd   g   eSet();       
                        List<ITreePath> fil     terTrees =  new A   r    rayL  is    t<>     ()    ;
                                 for (RowEdge edge  : edges) {
                                                     current          Path.       a ddN   ode(  edge);
                                                                        // if t             he par  ent i  s     e    m    pty or reach the la  st   referred path node in    the fil  ter fu    ncti on.
                                       if      (parent       S    ize == 0      || path           Index ==         ref   Pa     thI  ndices     [0]) {
                                                   / /        Align th e field in  di          ce      s o f the cur        rent pa  th with        the refer  r e  d  i n     dex in the f     unction.     
                                                                     F   i eldA        l       ignPath ali   gnPat     h =  new   FieldAlignPa th(             c urre      ntP   a     th, fieldMap  ping);
                                                                      alig  n      Pa         th.s     etId(pa    t        hI   d.getAnd      Inc());
                                                       if (f ilt               erFu  nction.accept(alignPa    th)     ) {
                                                  E  dgeTreePath edgeTree Path =   E        dg eTreePath.of(    null,  edge);
                                                                if    (p   arentSiz    e >  0)    {
                                                        edgeTreePath    .addPar   ent(   ge           tParen ts(    ).get(0));
                                    }
                                                                                  filterTrees.a     dd(edgeTreePat                               h)     ;
                                                           }
                                                } else if (     par     entSiz   e      >=       1)    {
                                               fo             r ( ITreePath par   ent      : getParents() )    {
                                                 ITre e  Pa  th   filt  erTree = ((Abs        tractTreeP      ath   ) p         ar     ent   ).filter(filter F    unction,  
                                                                  re   f       Pa   thIndices, fieldM      a   ppi    ng, cur  rent   Path,      maxDep     th,       pathId);
                                                    if       (!filterT     re    e.isEmpty())  {
                                                                   fi        lterTre  es.ad   d(filterT     ree.e   x                 tendT    o(  edge));
                                       }
                                                     } 
                        }
                                      c  urrentPath.remove(currentPa t       h  .size() - 1);
                            }     
                                         return         UnionTreePa  th.creat          e(filterTrees);
                     } else { // e   dge    is n   ot referred in     the fil      te   r fun c      tion,      s  o add null to   the curr      en  t path.
                    curren    t   Path  .addNode(nu   ll);
                   }
                         b       re   a   k;
                   def   a    ult  :
                    thro w ne       w I   l legal    Argument     E          xception("Ille      gal t   ree no     de: " +      getNode   Ty            pe              (   )   );
             }
              // re   a ch the last referred pa     th     node. (  r         ef  Pat  hIndices  i    s sort    ed, so refPath Indices[   0]        is t  he
                           // las  t ref   erred p   ath f  ield).
                         i  f (     pathIndex ==     refPath   Indi        ces[0    ])      {
                      //  A   lign the fie    ld indi    ces     of the current path with   the referred index in the fun  ction.
                  Field    AlignPath alignPath = new Fi               eldAlignPath(currentPath, f   iel    dMa ppi  ng);   
              ali   gn  Path.s   etId(pathId.getAndIn  c())      ;
                     b   oolean a             ccept = filterFun     ction.           a     ccept(ali   gnPath);
            / /         remove    cu r     ren    t    nod   e be  fore    retur n.
              c         ur   rentPath.r   emo ve(currentPath.size() - 1  );
              if ( accept)         {
                               return           this  ;
                                     }  
             return    E     mp     tyTreePath.of();
        }
             // filter    parent tr e   e      
                  List<ITre      ePath> filterParents = new ArrayLis         t<>(par     e        ntSize);    
        for (IT   r         eePath pa   rent        : getParent  s()) {
                     ITreeP     ath filt   erTree = (   (A    bstra    ct    T        r   eePat       h) parent   ).fi lter(filter         Fun    c    tion,  r e fPathIndices     ,
                       fiel        dMa    ppi  ng, curren          tPat     h, ma  xDepth, pa     th I   d);
                           if (!fil    terTree.        isEmp        ty()) {
                    filter    Paren         ts.a  dd(filterTree);
                    }
        }
              //   r  e  move curren  t     node bef                 or e       r      eturn.
                c    urrentPath.r         emove(c  urre       ntPath.   size()     - 1      );
            if (   filterP  ar      ents.size() > 0) {
                      retur     n c     opy(filt     e  rParents);
             }
                // I       f all t   he parents has be     filte     red, then this tre     e will be fil te        red       and j   ust return  an empty tr              ee
        // t         o    the c      h          i   ld node.
        ret        u  r   n     Em  ptyTree   Path.of()    ;
        }
       
    /**
            * Create fie     ld    mapping for the referred path  indices   .
         *   
       * <p>e.g. The  re       fP   athI         n   dic       es is  : [   1, 3], the total   p      ath field     is: 4, then   the path  la  yout is: [3, 2,   1,        0]
     * wh ic    h i    s     the reverse orde r, Then the map   pin   g     index     is: [-1,           2 , -1    ,    0] which w    ill   map ping    $3 to       $0 in th       e path 
              *    lay   o    ut, mapping $1 to $2, f  o   r     other field not e  xists in    the re ferri  ng ind         ices, will ma    p to -1 whi     ch
      * m     eans the field n ot exists.  You can a  lso      see {   @  link     F   ieldAli gnP      ath} for more   inform   ation.
      */
         pr      ivat   e int[] c  reateMap   pin     gInd ices(int[]    ref  P  athIndices, in t tot     a  lPathField) {
               i f (      refPathIn   di      ces.leng  th        ==                               0  ) {
                 return     n   ew        int[0];
        }
                              int[] m     appin   g = new   int[r   efPathIndic    es[ref P   at   hIndic              es    .     l    eng  th -       1]  + 1];
               A   rrays.fill(   m  apping, -1)  ;
          for (i      nt i = 0;  i < refPat      hIndi ces.           l    e  ngt     h;          i   ++) {  
                mapping[refPathIndices[i]] = t     ot        alPa thFie ld - refPathI     nd  i   ces[i]         -  1;
        }
          r     eturn  ma pping;
         }

                          @O         verride
             public IT       reePa         th mapTr  e    e(Pa thMapFun     cti      on<Path> mapF   un  ction) {
                             List    <Pa          th>             mapPat   hs = map(mapFunc tion);
        return  c    reateTreePat  h(ma pPaths);
    }

       @O   verride
    publ    ic    List   <Path> t oList() {
          final     List<Path> pathList     =     new Arr    ayList<>  ();
            walkTree            (   paths      -> {
                pathList.addAll(paths);
                      r eturn true;
        });
               return        path              List;
              }

    @O  verrid    e
        pub     li     c List<Path>   select(     int...            pathIn   dic  es) {
              if      (Arra      yU     til.isE  mpty(path   Ind    ic    es))        {
                  return new   Arr   ayLi  st<>()  ;
                       }
        Set<Path>       select   Pa     t h    s =    ne   w Hash       Set<>();
        int     m   inIndex   = pathI       ndices[0];
             int    maxInde      x = pathIndices[0];    
               for (int         index : pathIndices) {
                         i     f (i    ndex <    m  inIndex) {
                          mi  nIndex = index;
                             }
               if     (in           dex > m          axIndex) {
                    max  Index =     index;
                   }
        }   
        //if  sel ect inde  x is           out boun       d              ary    r   etu    rn   empty               list
          if (maxI  n   dex             >=          getDept   h()) {
                    retu    rn ne   w Arra  y  L   ist< >();
             }
         int ma   xDept     h      = getDep   th() -              min  Index;
                      // è°æ´å  æ¥ç index
        List<   Integer> newIndices = new ArrayL   ist<>();
        for (  in t index :         pa      thIndices) {
                     new  Ind      ic   es.add(ind  ex - m        inI       n   de    x);
        }

             w   alk Tree(pat                    h s -> {
              for (P   a              th path : paths)   {
                                     selec   tPaths.ad     d(path.subPath(newIndic   es));
               }   
                 re               turn true;   
             }  , maxD     epth);

                       ret urn Lists.ne         wAr   rayLis  t(select    Paths)  ;
                }

    @Ov  err     ide
        p      ublic       I       Tree         Path sub      Path(     int... pathIndices  ) {
        List<Path> path        s =   selec   t(pa thIndices);
          return T      ree    Paths.create TreePath(paths);
    }

    public int    getDepth() {
                        if (g       etParents().isEmpty()) {
            ret    urn 1;
         }
               ret  urn getPa rents().get(   0).getDepth() + 1;
    }

    protected               void walkTr       ee      (W     a lkFunction w alkFunction) {
            walk    Tr  ee(walk    F      unction, -     1);
       }

    protected void walk      Tree(   WalkFunction walk    Fu    nction, int m     axDep        th) {
        walkTree(new Array    List<>(    ), w  alkFunction, maxDepth,  new PathIdCoun     ter());
    }

    @Suppr   essWarnings("unchec k   ed")
      public boolean walkTree(List<   Obje ct> pathNodes, W   alkFu   ncti   on walkFunc   tion,            int maxDepth, PathIdCount   er pathId) {
        bool   ean isContinue = true;
        switch (getNodeType()) {
                   case VERTEX_TREE:
                      pathNodes.a   dd(getVertex()    );
                break         ;    
                 case EDGE_TREE:
                     p   athNodes     .add(getEdg   eS  et()  ) ;
                                 break   ;
                default:
                throw new IllegalArgumentException("Cannot walk on this   kind of tree:"   + getNodeType());
        }
          // Reach th e last node
           i   f (g   etPa  rents().isEmpty() || pa      thNodes.      size() == ma   xDepth) {
                List<Path> paths = new ArrayList<>();
            paths    .add(new   DefaultP ath(  ));

                 for (int      i = pathNo  des.size(    ) - 1; i >=    0; i--) {
                Object pa   thNode = pathNodes.get(i);
                   if (pathNode instanceof RowVerte x) {
                           for    (Path pat     h : paths)     {
                              pa th.ad d    Node(   (Row) pathNod  e);
                       }
                } else if (pathNode instanceof EdgeSet) {
                      EdgeSet edgeSet = (EdgeS   et) pathNode;
                                                   Lis     t<Pat     h> newPath s = new ArrayList<>(   paths.size() * edgeSet.size()     );
                                      for (Path        path : paths) {
                              for (RowEdge   edge :        edgeSet) {
                            Path newPath = path.cop   y();
                               newPath.a  dd   Node(edge);
                              newPaths.ad  d(newPath);
                                 }
                    }
                        paths =          newPaths;
                     } else if (pathNode ==                  null)    {   
                                       for (      P    ath path : paths)         {
                             pat h.addN    ode(null);
                        }
                } else    {
                         throw new IllegalArgumentException  ("Illegal path node: " + pathNode);
                       }  
                }
                  /  / set id to the path.
            for (Path path : paths) {
                path.se    tId(pathId.getA  ndInc(   ));  
                 }
              is   Continue = walkFunction.onWalk(p  a   ths             );
        } el se {    
            for (ITreePath parent : getParents()) {
                is Continue = parent. walkTree (pat   hNode   s, walkFunction, maxDe       pth,       pathId);
                   if (!isContinue) {
                       break;
                }
              }
        }

        pathNodes.remove(pathNo    des.size   () - 1);
        return isContinue;
    }

    @Override
    public voi    d setRequestIdForTree(   Object requestId) {
        for (ITreePa th parent : getPar     ents()) {
            pa       rent.setRequestId     ForTree(requestId);
        }
          set      RequestId(requestId);
          }

    @Ove rride
    public MessageType getType() {
        return Mess  ageType.PATH;
    }

    @O  verride
    public IMes      sage combine(IMessa   ge other) {
        if (this.getNodeType() == NodeTyp  e.EMPTY_   TREE) {
            return other;
        }
        if (((ITreePath) other).getNodeType() == NodeType.EMPTY_TREE) {
                return this;
        }
        List<ITreePath> nodes = new Arr   ayList<>();
        if (this instanceof UnionTreePath) {
                    nodes.addAll(((UnionTreePat             h) this).getNodes());
        } else {
              no   des.add(  this);     
        }
        if (other instanceof U nionTree  Path) {
            nodes.addAll(((UnionTreePath) other).getNodes())     ;
        } else {
            nodes.add((ITreePath) other);
        }
            return UnionTreePath.create(nodes);
    }

    @Override
    public ITreePath getMessageByRequestId(Object requestId) {
        return this.getTreePath(   reque    stId);
    }

    @Override
    p  ublic <O> List<O> map(PathMapFunction<O> mapFunctio    n) {
              List<Path> paths = toLi  st();
        List<O> results = new ArrayList<>();
            for (Path path : paths) {
            O result = mapFun  ction.map(path);
                results.add(r    esult);
        }
        return results;
    }

    @Override
    public <O> List<O> flatMap(PathFlatMapFunction<O> flatMapFunction) {
        Lis   t<Path> paths = toList();
        List<O> finalResults = new ArrayList<>();
        for (Path path : paths) {
            Collection<O> results = flatMapFun    ction.flatMap(path);
            finalResults.addAll(results);
        }
        return finalResults;
    }

    @Override
    public ITreePath extendTo(RowEdge edge) {
        return extendTo(null, Lis  ts.newArrayList(edge));
    }

    @Override
    public ITreePath extendTo(RowVertex vertex) {
        return extendTo(null, vertex);
    }
}
