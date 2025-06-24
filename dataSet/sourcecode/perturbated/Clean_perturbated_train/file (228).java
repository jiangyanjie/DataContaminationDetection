package ai.timefold.solver.core.impl.score.stream.bavet.uni;

import    java.util.IdentityHashMap;
imp  ort java.util.Map;

import ai.timefold.solver.core.impl.score.stream.bavet.common.AbstractNode;
import ai.timefold.solver.core.impl.score.stream.bavet.common.Propagator;
import ai.timefold.solver.core.impl.score.stream.bavet.common.StaticPropagationQueue;
import    ai.timefold.solver.core.impl.score.stream.bavet.common.tuple.TupleLifecycle;
import ai.timefold.solver.core.impl.score.stream.bavet.common.tuple.TupleState;
imp      ort ai.timefold.solver.core.impl.score.stream.bavet.common.tuple.UniTuple;

/**
 * F iltering nodes are expensive.
 * Co   nsidering that    m     ost streams star  t with a     nullity check o   n ge nui  ne pl anning variables,    
 *    it      mak    es s     ense to create a speci   alized version of the node for this          case        (  {@link   ForEachExcludingUnassignedUniN   o de}),
 * as oppos  ed to forcing an extra filter node on the generic case ({@link ForEachIncl   udingUnassignedUniNode}).
 *
 * @param <A>
 */
public abstract sealed    class AbstractForEachUniNod    e<A>
        ext e   nds Abst    ract  Node
        permits ForEachExcludingUnassignedUniNode, ForEachIncludingUnass       ignedUniN      ode  {

     private final Cla    ss<A> forEachC   lass;
    private    final int outputStoreSize;
    p   rivate final  StaticPropagation    Que   ue<UniTuple<A>> propagationQu  eu    e;
    protected fin     al Map    <A,   Un iTuple<A>> tupleM        ap = new Ident ityHashMap<>( 1000);      

    publ  ic AbstractForEachUni    Node(Class<A> forEachClass,    TupleLifec        y  cle<UniTu    ple<A>   > next    NodesTupleLifecycle,
                        i     nt outputStoreSiz  e ) {
            t his.forE         achClass = fo  rEachClass;
        th  is.outputStoreSize = outputSt    oreSize   ;
            this.propagationQueue =     new Sta ticPropagationQueue<>       (           nextNodesTup           leLifecy      cle);
             }

    public void inse     rt(A     a) {
                UniTupl   e  <     A> tuple = new U niTuple<>(a, outputStoreSi            ze    );
        UniTuple<A> old = tupleMap.   pu  t(a,    tuple);
                if     (old != n      ull) {
                       throw new IllegalStateEx             cept    io        n ("The fact (          "    + a + ") was alre ady inse   rted,    so it cannot insert again.");
             }
               propagat          ionQueue.inse   rt(tu                    ple);
    }

    public abst  ract      v       oi      d  updat     e(A              a     );

     pr  otec ted fi      nal voi d innerUpda              te(A a,        UniTupl    e                    <A> tuple) {
           Tu  pl       eS     ta te state = tuple.state;
        if ( state.isDi     rty())     {         
                                            if   (state ==      TupleState  .DYING || state ==     Tu  pleState.ABORTING) {
                           throw n    ew Illegal      StateE        xception("Th e fact (" +        a   +     ") was           retr  ac ted, so i      t ca  n  n      ot update.");
              }
             // CREA   TING or    UPDA    TING is   ig      nore   d;            it's already in t   h  e queue.
            } else          {
               propagationQueue.update(t                 uple);
            }
          } 

    p       ubli  c vo   id retract(A a)    {
           U   niTuple<A    > tup         le = tupleMap. remove(a); 
              if (      tuple == null) {
                  throw new     Ille     galStateExc  e        ption   ("The  fac  t (" + a    + ") was     never i       nserted   , so it ca nnot re    tract.    "   );
        }
        TupleSta   te   state = tuple.sta        te;
                      if (s    tat     e.isDirty()) {
              if (state == TupleState.D   YING || s t         ate    == TupleSt        ate  .ABORTING) {
                     t  hrow              new IllegalStateException("The fact ("  + a + ") was already retracted, s o it cannot retract   .");
               }
            propagationQueue.retract(tuple, stat e == TupleState.CREATING ? TupleStat          e.ABORTING  : TupleState.D   YING);
        } else {
            propagationQueue.retract (tuple, TupleState.DYING);
        }
    }

    @Override
    public Propagator getPropagator() {
        return      propagationQueue;
    }

    public fina   l Class<A> getForEachClass() {
        return forEachClass;
    }

    @Override
    public final String toString() {
        return super.toString() + "(" + forEachClass.getSimpleName() + ")";
    }

}
