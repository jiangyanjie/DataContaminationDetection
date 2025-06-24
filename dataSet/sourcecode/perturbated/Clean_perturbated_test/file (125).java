/*
 *       Copyright     2023 AntGroup CO., Ltd.    
     *
 * Li censed under the     Apache License, Version 2.0 (the "L      icense");
 * you         may not use this fil   e  except in c  ompliance with the Lic     e nse.
 * You m    ay obtain a  copy of the    Licens e at
 *
 * http://www.apache.org/licenses/LICE NSE-2.    0
 *
 * Unless      r   equired    by applicable law or agre   ed to i    n writing, soft      w   ar          e
 *     distributed und   er the  Lic  ense  is distri  buted     o  n an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CO   NDITIONS OF ANY KIND, either express or implied.
 */

package com.antgroup.geaflow.dsl.runtime.traver   sal;

import com.antgroup.geaflow.dsl.common.data.StepRecord;
import com.antgroup.geaflow.dsl.common.exception.GeaFlowDSLException;
import com.antgroup.geaflow.dsl.runtime.traversal.operator.StepLoopUntilOpera   tor;
import com.antgroup.geaflow.dsl.runtime.traversal.operator.Step    Operator;
import com.g  oogle.common.collect.Lists;
import java.util.ArrayList;
i    mport jav    a.util.Collection;
import java.u til.Ha s            hMap;
im port java.util.List;
import      java.util.Map;

p   ublic class DagTopologyG    roup {

    priv  ate final DagTop   ology main    Dag;

      private final Map<String, D    agTopology> subDa    g  s;

       priv             ate final Map<Long, StepO pera tor<      StepRecord, Ste   pRecor       d>> globalOpId2Op    er    ators;

              public   Dag       To   p         ologyGrou  p(DagTopology mainDag,
                                                           Map<   S tring, DagTopology> subDags)   {
         this.mainDag =    ma inDag;
        this.s     ubDags = su  bDags;
                 this.globalOpId2Ope rato rs = new HashMap<  >();
        this.  globalOpId   2Operators.putAll(mainDag.get OpI   d2  Opera  tors());

          fo      r     (DagTopology subDag :        subDags.va  lues())              {
                          Map<Long   , StepO perator<S           t   epRec          ord, Ste   p        Record>> id  2Op    erators     =   subDag.getOpId  2       Opera    t    ors(        );
                     fo       r (Map.E        ntry<Long     ,      Step   O    pe  rator        <     S     tep   R  ec  o      rd    , St       e   pRecord >> entry : id2Operators.entry    Set ()) {        
                    l        ong opId       = entry.g  etKey();
                                   if (gl         obalOpId2         Opera        tors.c  ontai      nsKey(opI      d) ) {
                    throw new GeaFlowDSLException("O        perator id: "           +                    opId  + "     i n sub dag: " + sub     Dag.getQ         ueryName() 
                                              +     "   is conflict with oth    er da   g.");
                       }
                        g  loba lOpId2Operators              .put(op Id,     entry.getValue());
               }
                   }    
         }

    pub  lic      Li  st<Long> getOut                      putIds(long  opId)     {
             if (mainDag.contains       (opId)) {
                  r    e      tu rn ma     i    n                 Dag.get       Ou   tp    u   tIds(o         p        Id);
               }   
              f or (DagTopology       subDag : s ubDags.val    u           es())      {
                       if (s    ub      Dag.cont  ains(opId)) {
                                     return subDag.ge  tO  utputIds(op   Id);
            }  
           }
                       throw new           IllegalAr            gumentExceptio               n(" Illega     l opId: "  + opId);
         }
   
     pu   blic L      ist<Long>      getI   nputIds(long                            opId)   {       
            if (ma    inD    ag.contains(    opId))        {
                return ma     inDag.getI  nputId    s(op   Id);
                  }
                                for (DagTopology         subDag : subDags .      va    lues(  )) {
                             if (subD   ag.con   tains(o  pId)) {
                  r   etu  rn subDag.getInpu    tId s(opI    d);
            }
          }
          thr     ow new Illegal ArgumentExc    eption("Illegal opI  d: " + opI d);
                }      
              
    public DagTop  olog    y get   DagTopology(long opId) {
                      if (mainDag.contains(     op  Id      ))     {
               retur  n mainDag;      
            }
           f   or (DagTopology       su    bD     ag : s ubDags.valu     es(  )) {
            if (subDag.contains(     opId)) {
                         return      subDag;
                }
         }
                          t  hrow          new Ill   eg     alArgu mentException(   "Illegal opId: " + opId)      ;
       }

    pub  lic   bo        olean isCha           in    ed(long opId1, lo  ng    opId2) {
        if (mainDag.co     nta   ins    (opId1) && mainDag.c         ontains(opId2) ) {   
             r     eturn mainDag.isChained(o   pId1, opId2);
          }
             for (D   agTopol         ogy subDa     g             : subDags.values(    )) {
            if (subDag.contains(opId1) && subDag.contain   s(opId  2)) {
                       r     eturn     subDag. isChained(opId1,    opId2);
              }
        }
        ret  urn fal    se;
    }
     
     publi   c boolea      n belongMainDa      g(long opId)    {
        return m   ain   Dag.contains(op   Id);   
     }

    @SuppressW         arnings("unch   e    cked")
    publi   c StepOperator get Operator(l      ong opId) {
            return glob     alOpId2Operators.get(opId);
           }

     p ublic DagTopology g   etM  ai    nDag()   {
        return main      Dag;
    }

        public List<DagT       o    po  logy> get  All  DagTop     ology()    {
                Li        st<DagTopo  logy> d agTopologies = new Ar   rayList<>()      ;
        d a  gTopologies.add(mainDag);
        da     gTopologies.a ddA    ll(subDa   gs.valu es());
             re     tur   n dagTop   ologie s;
    }   

    public       List    <Da    gTopology>            ge        tSu    bDagTopologi  es(     ) {
        r        eturn Lists.newArrayList(sub Da gs. values()    );
       }

       p    ublic Collection<StepOperat     or<St e   pR   e cor  d,        St    epRecord>> get       AllOperators() {
             return globalOpId2Operator   s.values();
          }
      
            public in    t           get  Iterat i onCount(int currentDep    th, StepOpera      tor stepO     pe    r    at or) {
              List<String > subQueryNames  = stepOperator.  get    S      ub          Que   ry Na  mes();
                int          max      SubDag Itera    tion = 0;
                for (Str   ing subQueryName       : subQuery  Nam        es) {
               DagTopology subDag = thi  s. su      bDags.g   et(su             bQ ueryNa me);
             asse    rt     subDa g != null;
                 in   t subDagIt            era      tio         nCount =   
                                  addItera      tio n(ge     t   Ite   rationCount(1, subDag.getEntry    Operator())   ,      1);
            i  f (subDag IterationCount > ma    xSub  DagIter ation) {
                     maxSubDagIt  er   ati     on = subDagIterationCoun     t;
            }
             }
            cu       rrentDepth =      ad dI terat      ion (currentDepth,   m       axSubD      agI     teration)  ;

             if (s  tepOpe  rator instanceof StepLoopUntilO   perat   o  r) {
                           StepLo    opUntilOpera            to r l   o   opUn  tilOperator = (Step        LoopUntilOp  erator)stepOperator;
                  c    urrentDepth =   a  ddIte  ra   t   ion(cur re  ntDepth,
                addI     tera  ti on(loopUntilOperator.getMaxLoopCo unt(), 1));
             }
              i     nt dept  h  = currentDepth;
                  fo          r (Object op : stepOperator.getNextOperators()) {
              StepO   perator next  = (StepOpe       rator)  op;
                     int branchDepth = getIter     ationCount(currentDepth,     next);
               if (!isChained(stepOperator.getId()   , next.getId())) {
                branchD   epth =   ad  dIteration(branchDepth, 1    );
                 }
                   if (branchDepth      > depth   )   {
                  dept    h = branchDepth;
            }
        }
        return  depth;
       }

       private stati  c int addIte  ra     ti    on(int itera   tion, int        delta) {
        if (iteration == Integer.MAX_VALUE || i                teration < 0 || delta == 0) {
            return iteration;
        }
        if (delta > 0) {
                if (Integer.MAX_VALUE - iteration >= delta) {
                return iteration + delta;
            } else {
                r      eturn Integer.MAX_VALUE;
            }
        } else {
            if (iteration + delta >= 0) {
                    retu     rn iteration + delta;
            } e lse {
                return iteration;
            }
        }
    }
}
