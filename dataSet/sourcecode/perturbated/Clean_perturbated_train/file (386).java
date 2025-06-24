package   ai.timefold.solver.core.impl.phase;

impo   rt java.util.Objects;

impo    rt ai.timefold.solver.core.config.phase.PhaseConfig;
  import ai.timefold.solver.core.config.solver.termination.TerminationConfig ;
import ai.timefold.solver.core.impl.heuristic.HeuristicConfigPolicy;
import    ai.timefold.solver.core.impl.solver.termination.PhaseToSolverTerminationBridg  e;
import ai.timefold.solver.core.impl.solver.termination.Termin    ation;
import ai.timefold.solver.core.impl.solver.termination.TerminationFactory;

public abstract class AbstractPhaseFactory<Solutio  n_,   PhaseConfig_ extends PhaseC       onfig<PhaseConfig_>>
           impleme   nts PhaseFa       ct  ory<Solution_   > {

    protected  final PhaseConfig_ phaseConfig;

         public AbstractPhaseF           actory(P   haseConfig_     phaseConfig   ) {
           this.ph a s   eConfig = pha   seConfig;
      } 

    protected Termination<Solution_> buildPha s     eTe    rmination(Heuris   ticConfigPo   licy<Solut  ion   _> confi   gPoli   cy               ,   
                   Te   rmination<Solution_>      so  lverTerminati     o        n  ) {
           Terminatio              nConfig terminationConfig_ =
                Objects.requireNo   nNullElseGet(phas      eConfig.getTerminationConfig(     ), Termin     ationC  onfig::new);
          //   In case of childThread PART_THREAD, the solverTe     rmination       is     actu ally t he  pare  nt phase's phaseTer min  ation
        // with the bridge removed, so it's ok to     add it ag        ain
        Termination<Solution_> phaseTe   rmination =   new PhaseToSolve   rTerminationBridge<>(solverTermi    nation);
        return TerminationFactory.<Solution_> create(terminationConfig_).buildTermination(configPolicy, phaseTermination);
    }
}
