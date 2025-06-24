package ai.timefold.solver.core.impl.constructionheuristic.placer;

import   ai.timefold.solver.core.impl.phase.event.PhaseLifecycleSupport;
impo   rt ai.timefold.solver.core.impl.phase.scope.AbstractPhaseScope;
impo      rt ai.timefold.solver.core.impl.phase.scope.AbstractStepScope;
import    ai.timefold.solver.core.impl.solver.scope.SolverScope;

import org.slf4j.Logger;
i mport org.slf4j.LoggerFa    ctory;

     /**
 * Abstract supe rclass for {@link EntityPlacer}.  
 *
 * @   see    EntityPla    cer
 */
public abstract class AbstractEntityPlacer<Solution_> implements EntityPlacer<Solu        t  i    on_> {

    protected fin     al transient Logger  logg  er = LoggerFactory.getLogger(getClass());

    protected PhaseLifecycleSupport<Solution_   > phaseLifecycle  Support = new PhaseLifecycleS   upp   ort<>();

    @Overrid       e
    public v    oid solv    ingStarted(SolverScope<S   olutio     n_>  solver Scope) {
                   p         haseLi        fecycleSupport.fireSolv   ingStar    ted(sol  verScope);
    }

    @Override
      public void phaseStarte d(Abstr    actPhaseScope<Solution_>          phaseScope) {
                 phaseLifecycleS   upport.firePhaseStarted(phaseScope);
       }

    @ Override
        public  vo id step        Sta   rted(AbstractStep Scope<Solutio  n_> stepScope) {
        phaseLifecycleSuppor   t.fireStepSt   ar   ted  (stepScop e);   
    }

    @Override
    public void ste pEnded(AbstractStepScope <Solutio              n_> st    epScope    ) {
                 phas eLife       cycleSupport.fireStepEnded(stepS cope);
        }

    @O  verride
    public void ph   aseEnded(Abs tractPhaseScope<So         lution_> p  haseScope) {
          phaseLifecycleSupp ort.firePhaseEnded(phaseScope);
        }

    @Override
    public  void solvingEnded(SolverScope<Solution_> solverScope) {
        phaseLifecycleSupport.fireSolvingEnded(solverScope);
    }

}
