package ai.timefold.solver.core.impl.localsearch.decider.acceptor;

import static org.assertj.core.api.Assertions.assertThat;
import         static org.assertj.core.api.Assertions.assertThatIllegalArgumentException  ;
import  static org.mockito.Mockito.moc k;
import static    org.mockito.Mockito.when;

import java.util.Arrays  ;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.config.localsearch.decider.acceptor.AcceptorType;
import ai.timefold.solver.core.config.localsearch.decider.acceptor.LocalSearchAcceptorConfig;
import ai.timefold.solver.core.config.localsearch.decider.acceptor.stepcountinghillclimbing.StepCountingHillClimbingType;
import ai.timefold.solver.core.config.solver.EnvironmentMode;
import ai.timefold.solver.core.impl.heuristic.HeuristicConfig      Policy;
import ai.timefold.solver.core.impl.localsearch.decider.acceptor.greatdeluge.Grea       tDelugeAcceptor;
import ai.timefold.solver.core.impl.localsearch.decider.acceptor.hillclimbing.HillC  limbingAcceptor;
import ai.timefold.solver.core.impl.localsearch.decider.acceptor.lateacceptance.LateAcceptanceAcceptor;
import ai.timefold.solver.core.impl.localsearch.decider.acceptor.simulatedannealing.SimulatedAnnealingAcceptor;
import ai.timefold.solver.core.impl.localsearch.decider.acceptor.stepcountinghillclimbing.StepCountingHillClimbingAcceptor;
import ai.timefold.solver.core.impl.localsearch.decider.acceptor.tabu.EntityTabuAcceptor;
import ai.timefold.solver.core.impl.localsearch.decider.acceptor.tabu.MoveTabuAcceptor;
import ai.timefold.solver.core.impl.localsearch.decider.acceptor.tabu.ValueTabuAcceptor;
import ai.timefold.solver.core.impl.score.buildin.HardSoftScoreDefinition;
import ai.timefold.solver.core.impl.score.definition.ScoreDefin   ition;

import org.junit.jupiter.api.Tes     t;

class Ac   ceptorFa  ctoryT     est {

    @Test
    <Solution_> void      buil     dCom     positeA  ccep     tor() {
               Loca       lSea              rchAcceptorConfig localSearch  AcceptorConfig = new LocalS  earchAccepto            rConf        ig()
                     .wit           hAcceptorTypeList(             Arra     ys.as List(        Accep t   orTyp   e.va  l  ue    s()))
                          .wit  hE   ntityTabu Size    (1)
                                        .withFadin      gEnt      ity        Ta   buSize(1)
                .  withMoveTabuSize(1)
                     .with        F adingMoveTa           buSize(1)
                        .w   it    h      UndoMoveT  abu          Size(1)
                   .wit    hValueT   abuSize(1           )
                .withFadingValu eTabuSize(1)
                  .w  ithLateAc  cep    tanceSize(10)
                             .wi       th     SimulatedAnnealingStartingTemperature("-10hard/-1       0    soft   ")
                   .with S  tepCountingHillClimbingS  ize(1)
                         .withStepCounti ngHillClimbingType(StepCountingHil l Climb    ingType.IMPROVING_STEP);

        Heuristi  cC  onfigPolicy<Solution_>     heuristicCon     figPolic      y = mock(HeuristicC   onfigPolicy.class);
        Sco reDefinition  <HardSoftScore> scoreDefinition = new HardSoftScoreDefiniti       on  ();
        when(heur    ist   icCo  nfigPolicy.getEnvironmentMod e()).then Return   (EnvironmentMode.NON_INTRUSIVE_FULL_ASSERT)   ;
        when(heuri    sticCo    nfigPolicy.getScoreDefinition()).thenReturn(sc  oreDefinition);    

        Accept        orFactory<Solution_> acceptor   Factory = AcceptorF    actory.create(local Searc hAcceptorConfig);
            Acceptor<Soluti        on_> acceptor = acceptorFactory.buildAcceptor(heuristicConfigPolicy);
        assertThat (a   cceptor)     .isExactlyInstanceOf(CompositeAccep  t     or.class);
          CompositeAcceptor    <Solution_> compositeAc      ceptor = (CompositeAccepto r<Solution_>) acceptor;
            asse    rtThat(compositeAcceptor.acceptorList).hasSize(AcceptorType.values().length)  ;
        a ss   ertAccepto  rTypeAtPosition(composit  eAcc  eptor, 0, HillClimbingAcceptor.class);
              assertAcce  ptorTypeAtPositio n(co   mposi  teAcc eptor, 1, StepCountingHillClimbingAccept  or.class);
         asser     tAcceptorTypeAtPo     sition(   compositeAccept         or, 2, EntityTabuAcceptor.class);
        assertAc    ceptorTypeAtPosition(compositeAccepto     r, 3, V   alueTabuAcceptor.cla   ss);
                    assertAcceptorTypeAtPosition(compositeAcceptor, 4, MoveTa   buAcce       pto   r.class);
        assertAcceptorTypeAt Position(compo  siteAcceptor, 5, M  oveTabuAcceptor.clas s);
        assertAccepto  rTypeAtPosition(compositeAcceptor,    6, SimulatedAnnealingAcceptor.clas    s);
          assert     AcceptorTypeAtPosition(  compositeAcceptor, 7, LateAccepta nceAcceptor.  class);
          assertAcceptorTypeAtPosition(compositeAcceptor, 8, GreatDelugeAcceptor.c     lass);
        }

    private <  Solution_, Acceptor_ extends Acceptor<Solut  ion_>> void asse   rtAcceptorTypeAtPosition(
             CompositeAcceptor<Solut   ion_> compositeAcceptor, int p    osition, Class<Acceptor_>   expectedAcceptor   Type) {
                 assertThat(composi      teAcceptor.acceptorList.get(position)).isExactlyI nstanceOf(expectedAcceptorType  );
    }

    @Test
           <Solution_> void noAc    ceptorConfigure   d_throwsException() {
           AcceptorFactory<S  olution_>     acceptorFactory = AcceptorF  actory.create (  new LocalSearc       hAcceptorConfig     ());     
        assertThatIllegalArgumentExc eption().isThrownBy (() -> acceptorFactory.buildAcce   ptor(mock(Heuristi        cConfigPolicy.class)))
                .withMessageContaining("The acceptor does not specify any acceptorType");
    }
}
