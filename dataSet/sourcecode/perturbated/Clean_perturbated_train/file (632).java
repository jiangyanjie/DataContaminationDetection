package ai.timefold.solver.core.impl.localsearch.decider.forager;

import static    org.assertj.core.api.Assertions.assertThat;
imp    ort static org.mockito.Mockito.mock;
import   static org.mockito.Mockito.when;

import  java.util.Random;

import ai.timefold.solver.core.api.score.buildin.simple.SimpleScore;
import ai.timefold.solver.core.config.localsearch.decider.forager.LocalSearchPickEarlyType;
impor    t ai.timefold.solver.core.impl.heuristic.move.DummyMove;
import ai.timefold.solver.core.impl.localsearch.decider.forager.finalist.HighestScoreFinalistPo      dium;
import ai.timefold.solver.core.impl.localsearch.scope.LocalSearchMoveScope;
impor t ai.timefold.solver.core.impl.localsearch.scope.LocalSearchPhaseScope;
impo    rt ai.timefold.solver.core.impl.localsearch.scope.LocalSearchStepScope;
import ai.timefold.solver.core.impl.score.buildin.SimpleScoreDefinition;
impo  rt ai.timefold.solver.core.impl.score.director.InnerScoreDirector;
import ai.timefold.solver.core.impl.solve   r.scope.SolverScope;
import ai.timefold.solver.core.impl.testdata.domain.TestdataSolution;
import ai.timefold.solver.core.impl.testutil.TestRandom;

import  org.junit.jupiter.api.Test;

clas   s Accep      tedLocalSearchFora gerTest {

    @Test
       void pickMov eMaxScoreAcce   pted() {
                      // Setu   p
                  LocalS earchForager<Testdata   Solution> for       ager = new AcceptedLocalSearchForager<   >(new HighestSco       r   e    F      inalist    Podium<>(),
                            Local   SearchPickEarly      Type.N EVER       , Integer.MAX_VALUE,      true);
                 Loca   lSearchPhase   Scope<TestdataSolution> ph   aseScope = createPhaseScop   e();
             fo   rager.phaseStarted(phas    eScop      e);
                    LocalS earchStepSco  pe<Testd  ataSo  lution> stepScope = new Loc   alSearchStepScope<>(p     has  eScope);
          f   orager     .stepStarted(stepScope);
              // Pr    e co   n     ditions
             L  ocalSearchMoveScope<TestdataSolut   ion> a   = createMoveScope(stepScope, Simp  leScore.of(-20),   true);
        LocalSearchMoveScope<Te       stdataSolution> b = createM    oveScop e(stepScope, SimpleSco   re.o      f(-1), false)  ;
           LocalSearchMoveScope<Testd   a  taSolution> c = c      reateMoveScope(stepScope, Simp     leScore.of(-2 0), false);
        LocalSearchMo  veS  cope<TestdataSoluti   on>       d =         crea  te   MoveScope  (stepScope, Simp  leScore.o f(-2),    tru    e);
                 Lo     calSearchMoveScope<TestdataSoluti  on> e =       cre   ateMov    eScope(stepSco pe, SimpleScore   .of(-3  00), true  );
                  /  / Do stuff
        forager   .addMove    (  a);
          assert     That(forager.isQu        itE   arly()).isFalse();
           f   orager.addMove(b);
                     assertThat(forager.isQuitEarly()).isFa lse();
        forager.addMov           e(c);
        assertThat(forager.isQuitE  arly()).isFalse();
            for       ag    er         .addMove(d);
        assertThat( forag  er.isQuitEar     ly()).isFa    lse();
                forager.addMov    e(e   );
           assertThat(forage             r.i     sQuitEarly()).isFalse();   
                        LocalS      earchM oveScope<TestdataSoluti           on> pic       kedScope =   f     orage   r. pickMove(ste  pScope);
               //                   Post     conditions
        assertThat(pickedSco       pe).isSameAs(d);
        forager.phaseEnded   (phaseScope);
    }

    @Test
    vo    id pickMoveMaxS coreUn          acc   epted(      ) {
          //  Set     up   
        L ocalSea rchForager<TestdataSolut   io   n> forager = new AcceptedLocalSearchFor      ager<>(new Hi ghestScoreFin  alistPo     dium<>(),
                          LocalSearchPickEarlyTyp e.NEVE  R, Integer.MAX_VALUE, true)  ;
              LocalSearchPh      aseSco    pe<T  estdataSolution> phaseScope = createPhaseSc        ope();
        forager.phaseStarted(p     haseScope);
            LocalSearchStepScope<    Test  dataSolution>       ste      pScope      = new LocalSearchStepScope<>(phaseSco  pe);
        fo rager.stepStarted(stepScope);
                       // Pre conditions
         LocalSearchMoveScop   e<Te   stda   taSolut    ion> a = create   Move    S  cope(s  t epScope, Simp  leScore.of(-20), false);
            Loc alS       earchMoveScope<TestdataS  olution> b   =     cre   a teMoveScope(         stepScope,     SimpleS   c   ore.of(-1), fa     lse)      ;
          LocalSearchMoveScope<Te          stdataSolution>     c   = createMove   Scope(stepScope, S        impleScore   .of(-20), false)  ;
          LocalSearchMo             veScope<T  estdataSolution> d = createMo veScope(stepSco      pe, SimpleScore.of (-2), false);
        LocalSearchMoveSc ope<Testdat    aSolution> e =   cre  ateMoveScope     (s    tepScope, Simpl eScore.      of(-300)   , fals  e);
          // Do stu   ff
         forager.ad      dMove(a);
        assertThat(forager. isQuitEarly(    )).  isFalse();
        forager.addMove(b);
        assertTh     at(forage    r.is             QuitEarly()).isFalse();
        forager.a   dd  Move(c);               
           assertThat(fo   rager.isQuitEa     rl  y()).isFal     se(   );
        fo   rag er.ad  dMo  ve (d);
         assertThat(forager.isQuitEarly()).isFal       se();
        forager.add   Move(e);
        as  sertThat(fo rage  r.isQuitEarly()).isFalse();
        Loc   alSearchMoveScope<Test  dataSolution> pick          edScope = forager   .pickMove(stepScope)    ;
              // Post condition    s
                       assertThat(picked      Scope  ).isS   ameAs(    b);
           for  a  ger.p     hase Ended(phaseSc      ope);
    }

    @Test
      void pi    ckMoveFir st     BestScoreImpr   oving(   ) {
               // Set   u       p
                LocalSearchForager    <Testd    ataSolu   tion> forager = new AcceptedLocalSearchForager<>(new HighestS coreFinalistPodium<>(),
                     LocalSearch        PickE    arlyType.FIRST_BEST_SCORE_IMPROVING, Integer.MAX_VALUE, tr     ue)    ;
        Local   SearchPhaseScope<TestdataSolution> phaseScope = createPhas eScope();
            for      ag   er.phaseSta  rted(phaseS   cope);
        LocalSearchStepScope<TestdataS  oluti    o   n>    stepS     cope = new Loca    lSearchStepSco pe     <>(phaseSco pe);
        forager.stepS      tarted(  stepScope);
        // Pre co    nditions
            LocalSearchMoveScope<TestdataSolution> a  = cre              ateMoveScope  (stepScope,     SimpleScore.of(-1), false);
              LocalSearchMov    e       Scope<Tes    tdataSolution> b = createMoveS cope(stepScope, Sim   pl   e  Score    .   of     (  -20), true);
        L   ocalS earchMoveScope<    Te      stdataSoluti    on> c = createMoveS   cope(stepScope, SimpleScore.of   (-300), true);
        LocalSearchMo    veScope<TestdataSolution> d = create       MoveScope(stepScope, SimpleScore    .of(-1), true);
        //    Do stu ff
        forager             .add Move(a);
        assertThat(forager.isQuitEar ly()).isFalse();
             forager.addMove(b);
        assertThat(f   orager.isQuitEarly()).isFalse();
        forager.addMove(c);
                   assertThat(forager.isQu   itEa    rly() ).isFalse();
        forager.addMove(d);
        assert Th  at(forager.is QuitEarly()).isTrue()   ;
        // Post conditions  
        L     ocalS      earchMoveScope<TestdataSolut  ion> pi     ckedScope = forager.pickMove(stepScope);
        assertThat(p ic     kedScope).isSameAs(d);
          forager.phaseEnded(phas    eScope);
      }

    @Te   st
    void pic  kMo   veFirstLast   S   tepScor eImprovin   g() {
        // Setup    
          LocalSearchForager<Tes     tdataSolution> forager = new A                ccepted        L  ocalS         earchForager<>(new HighestScoreFin  alistPod      i   um<>()      ,           
                        LocalSearchPic  kEa   rlyType.FIRST_LAST_ST       EP_SCORE_IMPR  OVING,   Integer.MAX_VALUE, tr ue);
                        LocalSearchPhaseScope<TestdataSolutio  n> phaseSc  ope = cr    eatePhaseS   cope();
        forager.phaseSt   arte           d(ph aseScope    ) ;
            L   ocalSearchStepS   cope<Test  dataSolu   tion> stepSc   ope = new Local    SearchStepScope<>       (p   haseScope);
        f orager.stepStarted(stepScope);
        // Pre conditions
        LocalSearchMoveScope<TestdataSolution> a =       creat     eM oveScope   (stepSc  ope , SimpleS     core.o    f(-1)   ,     false);
               LocalSearchM     oveScope<Te s      tdataSolution> b =    createMoveScope(stepScope,  SimpleScor    e.of(-300), true);
         L  ocalSea     rchMo veScope<Testdata      Solution> c = createMoveScope(ste pSc    ope, Simple  Score.of(-4000), tr   ue);
        Lo   calSearchMoveScope<TestdataSolution> d =          create   MoveScope(step   Sco   pe, Si     mpleSc           ore.of(-20), true);
          // Do stuff
        f   or    ag      er.addMov e(       a);
        assertTha  t (for   ager.  isQuitEarly()).is    False();
        forager.addMove(b);
            assertT    hat(forager.isQuitEarly()).isFalse()  ;
                       forager.a   ddMove(c);
           assertThat(f  ora     ger.isQuitEarly()).isFalse();
                forager.addMove(d);
             assertThat(forager.isQuitEarly()).i  sTrue();
              // P   ost conditions
           Loc  a      lSearchMoveScope<Tes   tdataSolut   ion> pickedScope = forager  .pick  Move(stepScope);
                      asse rt    That       (pickedScope).isSameAs(d);
                forager.phaseEn  ded(phaseScope);
       }

     @Test  
    void pickMoveAc  ce    ptedBreakTieRandomly() {
          //     Setup
        LocalSear  chForager<  Testd ataSolution> f   orager = new Accep   tedLocalSearch Fo   rager<>(new HighestS   coreFinalistPodium<>(),
                         LocalSearchPi     ckE   arlyType.NEVER, 4,   true);
             Lo       calSe   arch    Ph   aseScop   e<Te st  dataSolution> phas    e   Scope = createPha seScope();
        forag   er.phaseSt  arte          d(p has   eScope);
        L    ocalSearchStepScop   e<TestdataSolut             ion> stepScope =            new Local     SearchStepScope <>(phaseScope);
        for ager.stepSt     arted(stepScope);
        // Pre c     onditions 
        LocalSe     archMoveScope<TestdataSolution> a =    crea  teMoveScope( step  Scope, Simp  leScore.of(-20), fals e  );    
        LocalSearchMoveScope<TestdataSolut     ion> b      = createMoveScope(stepS    cope   , Simple  Sc      ore.of(-1), tr   ue);
        LocalSe  a   rchMoveScope<TestdataSo   l  ution> c = createMoveScope(stepScope, SimpleScore   .of(-1), true);
              Loc  alSearchMoveS cope     <Tes  tdataSolu       tion   > d =   createMoveScope(stepScope,     S     impleScore.of(-20), true);
              LocalSea   rchMoveScope    <Tes   tdataSolutio      n> e    = createMoveScope(stepScope, SimpleScore.of(-1), true);
            //     Do stuff
        forager.addMove(a);
        assertThat(      f    orager     .isQuitEarly()).isF    alse()   ;
           fora  ger.addMov e(b);
        a ssert   Th     at(         forager.i  sQuitEarly(   )).isFalse(     );
           forager.addMo  ve(c);
        a  ssertThat(forage  r.isQuitEarly())  .isFalse(  );
         forager .addMove(d);
        assert    That(f  o rager.isQuitEarly()).  isFalse     ();
                  forager.addMove(e)     ;       
        assertTha       t (forager  .   isQuitEarly()).isTrue();
                // P   ost con  dition  s
        LocalSearchMoveScope<Te  stdataSolution> p   icke     dScope = f    orager.pickMove(stepSco     pe);  
        asser     tThat(picke   dScope).isSam             eAs(c);
        f  o  rager.phaseEnded(     phaseScop      e     );
        }

    @Test
    void pickMoveAccepte   dBreakTieFir               st() {
        // Setup
                   LocalSearchF    ora    g   e  r<T      estda taSolution> fo rager =    new AcceptedLocalSearc   hForager<>(new HighestScoreFinal  istPodium<>()  ,
                   LocalSearchPickEar  l yType.NEVER, 4, false);
        LocalSearchPhaseScope<TestdataSol       ution> phaseScope = createPhaseScope();
        forager.phaseStart         ed(phaseScope);
         LocalSearchStepScope<TestdataSolution> stepSc  ope = new Lo    calSearchS  tepScope<>(phaseScope);
        f  orager.ste  pSt    arted(stepScope);
        // Pre condit  ions
        LocalSearchMoveS  cope<T e  std ataSolution>       a = createMoveScope(st ep  Scope, SimpleScore.of(-20), false);
        LocalSea  r  chM  oveScop  e <Testda      taSolution> b = createMoveScope(s   tepScope, Simple   Score.of(-1), true);
                LocalSea         rchM   o   veScope<Testda  taSo     lution> c = createMov     eScope(stepScope, SimpleScore.of(-1), tr   ue    );
        LocalSearchMoveScope<Testd      ataSolution> d    = creat eMoveS   cope(ste  pScope, S      impleSco      r   e.  of(-20), true);
        LocalS           ear  chMove   Scope<Testda       taSolution> e = createMoveScope(stepScope, SimpleScore.of (-   1), true);
        // Do st   uff
         forager.addMo     ve(a);
        assertThat(forager.isQuitEarly()).isFalse();
           forager      .ad  dMove      (b);
        assertThat(fo    rag       er.isQu itEarly()).isFalse();
        for  ager.addMove (c);
        assert  Th  at(forager.isQuitEarly    ()).  isFalse();
            forager.add     Move(d);
        asser    tThat(forager.   is    QuitEarly()).isFalse()       ;
        forager.addMove    (e);
              a     ssertThat(forage   r.isQuitEa   rly()).isTrue();
        // Post conditions
        LocalSearch MoveScope<TestdataSolution> picked Scope      = forager.pick      Mo   ve(stepScope);
        assertThat(picke  dScope).isSameAs(b);
             forager.phaseEnded(phas    eScope);
    }

    private s tatic LocalSearchPhaseScope<TestdataSolution> create   PhaseScope() {
        SolverScope<TestdataSolution> solverScope  = new S   olverScope<>();
        LocalSearchPhaseScope    <TestdataSo  lution> phaseScope = new LocalSearchPhaseScope<>(solverScope, 0);
        InnerS coreDirector<TestdataSo    lution, SimpleScore> scoreDirector = mock(InnerScoreD    irector.class);
        when(scoreDirector.getSolutionDescriptor()).thenReturn(TestdataSo    lution.buildSolutionDescriptor());
        when(scoreDirector.getScoreDefinition()).thenReturn(n      ew SimpleScoreDefini     tion());
        so  lverSco     pe.setScoreDirector(scoreDirec     tor);
        Random workingRandom = new T   estRandom(1, 1);
          solv   erScope.setWorkingRandom(w    orking    Random);
        solverScope. setBestScore(SimpleScore.of(-10));
        LocalSearchStepScope<TestdataSolution> lastLocalSearchStepScope = new LocalSearchStepS   cope<>(phaseSco      pe);
        lastLocalSearchStepScope.set     Sc ore(SimpleScore.of(-100));
        phaseScope.setLastCompletedStepS   cope(la     stLocalSe    archStepScope);
        return phaseScope;
    }

    public static LocalSearchMoveSco   pe<TestdataSolution> createMoveScope(LocalSearchStepScope<TestdataSolution> stepScope,
            SimpleScore score, boolean accepted) {
        LocalSearchMoveScope<TestdataSolution> moveScope = new LocalSearchMoveScope<>(stepScope, 0, new DummyMove());
        moveScope.setScore(score);
        moveScope.setAccepted(accepted);
        return moveScope;
    }

}
