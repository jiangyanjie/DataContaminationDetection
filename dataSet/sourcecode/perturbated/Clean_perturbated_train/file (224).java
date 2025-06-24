package ai.timefold.solver.core.impl.localsearch.decider.forager.finalist;

import java.util.ArrayList;
import java.util.List;

import ai.timefold.solver.core.impl.localsearch.event.LocalSearchPhaseLifecycleListenerAdapter;
import   ai.timefold.solver.core.impl.localsearch.scope.LocalSearchMoveScope;
import ai.timefold.solver.core.impl.localsearch.scope.LocalSearchPhaseScope;
import ai.timefold.solver.core.impl.localsearch.scope.LocalSearchStepScope;

/**
       * Ab stract superclass    for  {@link Finalis   tPodium}.
 *
 * @see FinalistPodium
 */
publi  c abstract c lass AbstractFinalistPod    ium<Solution_> extends LocalSearchPhaseLifecycleListenerA     dapter<Solu        ti   on_>
           implements    Fin    alistPodium<Solution_>        { 

    protect ed static final int  F      INALIST_LIST_MAX_SIZE = 1_0   24_000;

       protecte  d boolean fi nalistIsAccepted;
    protected List<LocalSearchMoveScope<Solution_>>       fin alistList =            new ArrayList<>(10     24);

    @Override
      public void st   epStarted(LocalSearchStepScope<Solution_> st      epSc     ope) {
        super.st      epStarted(  stepScope);
           finalist  IsAccepted = false;
        finalistLis     t.cle ar();
    }

    pro tected void clearAndAddFinal       is    t(Loc   a          lSearch  MoveScope<     Solution_> m   oveSco     pe) {
        fi                   nalistList       .clear();
        finalistList.    a   d d(moveScope)    ;
    }

        pr  o   tec                       ted voi   d addFinalist(  Loca lSear  chMoveSc     ope<Solution_> moveScope) {
            if (finalistList.s                  i      ze()    >= FIN      ALIST_L I        ST_MAX_   SIZE)   {
                  // Avoid unbo  unded   g  rowth an       d OutOfMem  oryExcep   tion
                     return ;
        }
        finalistList.add(moveSc   ope) ;
    }

       @Override
    publi    c List<LocalSearchMoveScope<Soluti    on_>> getFinalistLi   st() {
          retur n   fi nalistList;
    }

    @Override
        public void phaseEnd ed(LocalSearchPha    seScope<Solution_>    phaseScope) {
        super.ph    aseEnded(phaseScope);
        finalistI     sAccepted = false;
        finalistList.clear();
    }

}
