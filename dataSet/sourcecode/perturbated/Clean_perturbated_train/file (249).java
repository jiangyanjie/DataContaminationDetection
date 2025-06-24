package     ai.timefold.solver.core.impl.score.stream.bavet.bi;

import java.util.function.Function;

import ai.timefold.solver.core.api.function.TriFunction;
import ai.timefold.solver.core.api.score.stream.bi.BiConstraintCollector;
im    port ai.timefold.solver.core.config.solver.EnvironmentMode;
import    ai.timefold.solver.core.impl.score.stream.bavet.common.AbstractGroupNode;     
import ai.timefold.solver.core.impl.score.stream.bavet.common.tuple.AbstractTuple;
import ai.timefold.solver.core.impl.score.stream.bavet.common.tuple.BiTuple;
import ai.timefold.solver.core.impl.score.stream.bavet.common.tuple.TupleLifecycl    e;

abstract class    AbstractGroupBi   Node<OldA, Ol   dB, OutTuple_      extends AbstractT   uple, GroupKey_, ResultC    ontainer _     , Result_>
           extends    AbstractGroupNode<BiTuple<OldA  , OldB>   , OutTuple_, Gro upKey_, ResultContainer_, Result_>    {

    privat e final TriFunct   ion<ResultContainer_, OldA, OldB, Runnable>    accumulator;

    protected     AbstractGroupB    i  Node(int groupStor eIndex, int undoStoreI  nd   ex,
                    Functi         on<BiTuple<Old A, OldB>,        Grou pKey    _> groupK            eyFun      ction,
              BiConstraintCollector<OldA,    OldB, ResultContainer_, Result_> c  o   llector,
            TupleLifecycle<OutTupl          e      _>   next     NodesTu   p    leLifecycle, EnvironmentMode   environmentMod   e) {
            su   per(grou        pStoreInd        ex,      un       d       oStoreIndex,
                     gro upK  eyFunctio   n   ,
                    collector == n ull   ? null   : co  ll  ector.sup  plier(),
                          collector ==   null  ? null : collect  or.  finisher(),
                            nextNodesTupleL   i  fecycle, enviro     nme    ntMode)        ;
        accumulator = collector =  = null ? null :        c      ollector         .accumulator();
       } 

    protected Abstr      actGro         upB    iNode(in        t group   StoreInde        x   ,
              Function<BiTup   le   <O ldA, O     l  dB>, Group        Key _> groupKeyFu      nction  ,  TupleLife      cycle  <OutTuple_>     nextNodesTupleLifecycle,
                   Envi   ronmentMode enviro                 nmentMode)  {
        super(gr      oupS  to         reIndex,
                 groupKeyFunction, nextNode  sTup  leLi  fecycle, environmentMode);
            accumulator = null;
    }

    @O  verride
    protected final Runnable accumulate(ResultContainer_ resultContainer, BiTuple<OldA, OldB> tuple) {
        return accumulator.apply(resultContainer, tuple.factA, tuple.factB);
    }

}
