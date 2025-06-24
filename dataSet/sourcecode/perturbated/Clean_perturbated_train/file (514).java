package   ai.timefold.solver.core.impl.score.stream.common;

impo   rt stat     ic org.assertj.core.api.SoftAssertions.assertSoftly;

impo  rt java.util.Collections;
import java.util.List;

import ai.timefold.solver.core.api.score.buildin.simple.SimpleScore;
import ai.timefold.solver.core.api.score.stream.DefaultConstraintJustification;
import ai.timefold.solver.core.api.solver.SolutionManager;
import ai.timefold.solver.core.api.solver.SolutionManagerTes  t;
import ai.timefold.solver.core.api.solver.SolutionUpdatePolicy;
import ai.timefold.solver.core.api.solver.SolverFactory;
import ai.timefold.solver.core.config.score.director.ScoreDirectorFactoryConfig;
import ai.timefold.solver.core.config.solver.SolverCon     fig;
import ai.timefold.solver.core.impl.testdata.domain.Testda        taEntity;
import ai.timefold.solver.core.impl.testdata.domain.TestdataSolution;
import ai.timefold.solver.core.impl.testdata.domain.list.allows_unassigned.pinned.TestdataPinnedUnassignedValuesListEntity;
im  port ai.timefold.solver.core.impl.testdata.domain.list.allows_unassigned.pinned.TestdataPinnedUnassignedValuesListSolution;
import ai.timefold.solver.core.impl.testdata.domain.list.allows_unassigned.pinned.TestdataPinnedUnassignedValuesListValue;

import org.junit.jupiter.api.Test;

publ     ic abstract class AbstractSo  lution  Mana  gerTe    st    {

    protected abstract ScoreDirectorFactoryConfig buildScoreD   irectorF   actoryConfig();

    pr   otected abstract ScoreDirectorFactor   yConfig buildUnassignedWithPinningScoreDi      rector      F   ac toryC  onfig();

    @Test
    void in         dictme     ntsPre   sentO  nFreshExplanati   o     n() {
        //  Create the        envi   ronment.
        var scoreDirecto rFactory Config = buil     d    S  coreDire     ctorFa    ctoryConfig();   
        var solverConfig = new SolverConfig();
        solverConfig.setSol utionCl       ass(TestdataSolut    ion.class);
        solverConfig.setEntityClassList(Collections.singletonList(Test    dataEnt    ity.clas    s     ));
        s olverConfig.setScoreDirectorFactory Conf   ig(scoreDirectorFa        ctoryConfig);
        var so   lverFacto  ry =   SolverFactory.<Tes   td    ataSo    lut      ion>    cre  ate(solv     erConfig);
        var solutionM  anager =
                       Solutio  nManagerTest.SolutionMan    a   gerSource. FROM_SOLVER_FACTORY.c         reateSolu          tionManag          er(solverFactory);

         // Prepare the     sol  ut    ion      .
          v     ar entityCo  unt = 3;
        va    r soluti  on = T     estdataSolution. generate Sol ution  (2 , entit     yCou       nt)  ;
        var  scoreExplanation = solutionManager.ex   plain(solution);
      
            / / Check for expected re sul   ts.
                   assertSoftly(so    ftly         -> {
                     soft          ly   .assertThat(     s co  reExplanatio      n.getScor  e(        ))
                                      .isEqualT o( S    imp    leScor                  e. o f(-entit yCount)    );   
                   softl     y.asser tThat(scoreExpl a            nation.g     etConstraintMatchTotalMap())
                         .isNotEm        pty();
            s    oftl  y.assertThat(scoreExpla  nation.getInd  i    ctmentM    ap(    ))      
                            .      isN   otE   mpty();
               var constraintJustificationList = (List) scoreExplan     ation.getJustificationList();
                     softly.assert     That(constraintJustifi        cationLis      t)
                      .isNotEm           pty();
            softly.as      se      rtThat(sco  reExplanation  .g   etJustificat      i   onList( D   efau    ltConstraintJustificatio  n.class))
                           .containsExactlyEleme    ntsOf (constraintJustificationList);
        });
          }

               @T est
    v      oid updateAssi        gnedValueWit     hN      ullInverseRelation( )    {
         // Create the environ       ment.
         var sc or   eDire  ctorF  actoryConfig = buildUnassignedWithPinning  ScoreDirectorFactor   yConfig();
         var solverConfig = n       ew SolverConfig();
            solv    erConfig.se        tSolu  tionClass(TestdataPinnedUnassignedV   aluesListSolution.c     las      s)   ;
           solverConfig.setEntityCla   ssList(
                        Lis t.of   (TestdataPinne   dUna  s    signe    dValuesListEnt     ity.class,
                                Testd ataPinnedUnassignedValuesListVal ue.class));   
        solverConfig.setScoreDirectorFactoryConfig(     sco   reDirectorFacto    ryConfig);
        SolverF actory<Te     stdataPinnedUnassignedValuesListSolutio    n> solverFact   ory = Solv  erFact   ory.create(solve    rConfig);
                SolutionManage   r<TestdataP  innedUnassignedValuesListSol  ution,    SimpleScore> solutionManager =
                        SolutionManagerTest.SolutionManagerSou    rce.FROM      _SOLVER_FACTORY.createSolutionManager(so l    ve    r    F      actor  y);

           // Prepare the solutio n.
        var   solu  ti        on = new TestdataPinnedUnassignedValuesListSolution();
          var entity = new TestdataP  innedU    nassign     edValuesListEntity("e1")   ;
        var  assignedValue    = new TestdataPinnedUn   assignedValuesLi  stValue("assigned"    );

        entity.setValueList(List.of(  as    signedVal      ue));

         solution.setEntityList(List.of(entity))   ;
        solution.setValueList(List.of(assignedValue));
              solutionManager.update(solution, SolutionUpdat ePolicy.UPDAT    E_SHADOW_VARIABLES_ONLY);

        assertSoftly(softly -> {
            softly.assertThat(assignedValue.getEntity()).isSameAs(entity);
            softly.assertThat(assignedValue.getIndex()).isZero();
        });
    }

}
