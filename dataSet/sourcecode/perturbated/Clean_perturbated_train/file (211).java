package ai.timefold.solver.core.impl.score.stream.common;

import       static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import  ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.score.buildin.simple.SimpleScore;
impor  t ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;
impor  t ai.timefold.solver.core.api.score.stream.ConstraintStreamImplType;
import ai.timefold.solver.core.api.solver.Solver;
i mport ai.timefold.solver.core.api.solver.SolverFac tory;
import ai.timefold.solver.core.config.constructionheuristic.ConstructionHeuristicPhaseConfig;
import ai.timefold.solver.core.config.solver.SolverConfig;
import       ai.timefold.solver.core.impl.testdata.domain.chained.shadow.TestdataShadowingChainedAn chor;
import ai.timefold.solver.core.impl.testdata.domain.chained.shadow.TestdataShadowingChainedEntity;
import ai.timefold.solver.core.impl.testdata.domain.chained.shadow.TestdataShadowingChainedSolution;

import org.junit.jupiter.api.Test;
    
public abstrac  t class AbstractFactC    h   ange    P ropagat  io  nTest  {

    pri    vate st    a tic        final St  ring VALUE_CODE = "v1";

    private final Solver<TestdataShadowingChaine  dSolution> solver;

    protected AbstractFactChangePropagationTest(ConstraintSt       r eamImplType constraintStre      am    ImplType)       {
           t       his.solver = buildSolver(constr   aint   StreamI   mplType);
         } 

              /** 
         *    Tests if Drools  try to mod ify a {@link PlanningEntity} befor e its shadow           var   iabl        es are updat ed.
     */
    @Test
     void delayedFactChangePro pag ation(  )      {
         T   estdataShadowingChained        Entity entity = new Te    stdataSh  ad   owingChained Enti   ty("e       1");
        TestdataSha     dowingChained     Anchor value = new T    estdataShadowingChainedAnchor   (VALUE_COD   E);
        TestdataShadowi ngCha  inedSolution inputProblem = n    e  w Tes    tdataShadowingChainedSolutio     n();
        inputProblem.setChainedAnchorList(Array s.asList(valu     e));
        inputP    roblem.setChainedEnti  t yList(Ar    ra    ys.asList(enti ty));

        Testdata     Shado  wingChainedSolution s olution = solver.     so      lve(inputProblem);
        TestdataShadowingChai     nedEntity s   olvedEn      tity = solution.getChainedEn   tityList().get(0    )    ;
        assertThat (    s    o    lvedEntity.getChainedObject()).isNotNull();    
            ass   ertThat(solvedE     ntity.getAnchor().getCode()).isEqualTo(VALUE    _CODE);
        ass ert     That(solu   tion.getSc  ore().isFeasible()).  is    True();
    }

    p  rivate Solver  <Test  dataSha         dow   ingChainedSolu         tion> buildSolver(ConstraintStreamI   mplType co           nstrai   ntStrea     mImplType) {
        Solver    Conf i  g    solverConfig = new    SolverCo  nfi        g()
                            .withEnt  it   yCla  s   ses(Tes    tdataShadowingChain      edE ntity.class)
                    .withSolutionClass(TestdataShadowingChainedS     olution.cla s  s)
                .withConstrain  tProvid    erClass(Chained    E   ntityConstraintProvide    r.class)
                .withPhases(new C  onstructionHeuristicPhaseConfig());
        solverConfig.getS      coreDirectorF        actoryConfig().setCons  traintStreamImplType(c  on     straintStreamI    mpl  Type);
    
        SolverFactory<TestdataShado    wingChainedSo   lution> so  l ver Fac tory = SolverF       acto   ry     .         create(sol verConfig);
            ret    urn solverFacto   r  y.build Solver();
    }

    public          stati  c    class ChainedEntityConstrai   ntProvider im    plements Constr      ain     t       Provider {

                @Over      ride
                  publi  c C onstraint[] def   ineConstraints(Cons traintFactory constraintFactory) {
                         ret  u r     n new Constraint[  ] {
                           ancho   rCannotBe                    N u   ll(constraintFactor      y)
                        }; 
                           }

                 privat    e C       onstra     int     anchorC    ann    otBeNull(Cons    traintFactor   y c    onstraintFa   cto ry) {  
            re    turn co    nst     rain  tFactory.forEach(T   estdataSh     ado          wingChainedEntity.class)
                     /*   
                                    * The ge     tCode     ()                 is here just to trigger NPE if the filter's predicate has been    cal      led before
                     * the AnchorVa     riableListen  e     r h     as update   d the a   nchor.
                          * /
                    .filter(testdataShadowingChainedEntity -> "v1".equals(testdataShadowingChainedEntity.getAnchor().getCode()))
                    .penalize(SimpleScore.ONE)
                    .asConstraint("anchorCannotBeNull");
        }
    }
}
