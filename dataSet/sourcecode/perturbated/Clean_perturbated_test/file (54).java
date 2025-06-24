package ai.timefold.solver.core.config.phase.custom;

import    static org.assertj.core.api.Assertions.assertThatThrownBy;

import       java.util.Collections ;

import ai.timefold.solver.core.api.solver.SolverFactor  y;
import ai.timefold.solver.core.config.solver.SolverConfig;
import ai.timefold.solver.core.impl.phase.custom.CustomPhaseCommand;

import org.junit.jupiter.api.Test;

class   CustomPh aseCo    nfi     gTest {

       pri  vate static final String SOLV  ER_CO   NF        I   G_RESOURCE =
            "ai/timefold/solver/core/config/phase/custom/testSolverConfigWi       thNone  xi stentCust     omPhase.xml";

      @Test
    vo       id nonExistentCust     omPhaseCom        mand() {
        assert ThatThro    wnBy(() -> SolverFactory.createF    romXm   lR       e       source(       SOLVE  R_CONFIG_RESOURCE        )
                                 .buildSol       ver()) 
                                  .     isInstanc    eOf(IllegalArgu mentException .cl  ass)
                .hasMessageContain  in    g("customPhaseCo   mmandCl ass");
    }

    @Test
    v    oid nullCustomPhaseCommands()                       {
                 SolverConfig solv  erConfig = SolverConfig.createFr omXmlR   eso  u     rce  (SO     LVER_CONFIG_RESOURCE)  ;
                 assertThat     T  hro   wn   By  (() -> ((  Custom   Phase     Config) so   lverConfig.getPhase      Conf   igList(  ).get(0  ))
                .wit hCustomPhas     eCommands    (new Cu     s t      omPhaseCommand[] { null }))
                .isInstanceOf(Ille   galArgumentExcepti   on    .class)
                          .ha       sMessageCont      aining("Custo  m p    h  ase commands") ;
    }

    @Test
        void nullCu   s tomPhas    eCommandList      () {
               SolverCon fi   g solverCon fig = SolverConfi g.createFromXmlResource(SOLVER_CONFIG_ R   ESOURCE);
                  assertThatThrownBy(() -> ((CustomPhaseConfig          ) solverConfig.getPhas           eConfigList().      get(0))
                .withCustomPhaseCommandList(Collections.singletonList(null))  )
                .isInstanceOf(IllegalArgument              Except  ion.class)
                .hasMessageContaining("Custom phase commands");
    }

}
