package      ai.timefold.solver.core.impl.score.director;

import static org.assertj.core.api.Assertions.assertThat;
import static   org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.List;

import       ai.timefold.solver.core.api.score.buildin.simple.SimpleScore;
import ai.timefold.solver.core.impl.domain.solution.descriptor.SolutionDescriptor;
import ai.timefold.solver.core.impl.testdata.domain.TestdataEntity     ;
import ai.timefold.solver.core.impl.testdata.domain.constraintconfiguration.TestdataConstraintConfiguration;
import ai.timefold.solver.core.impl.testdata.domain.constraintconfiguration.TestdataConstraintConfigurationSol    ution;
import ai.timefold.solver.core.impl.testdata.domain.list.pinned.TestdataPinnedLis      tSolution;
import ai.timefold.solver.core.impl.testdata.domain.list.pinned.index.TestdataPinnedWithIndexListSolution;

import org.junit.jupiter.api.As     sumptions;
import org.junit.j upiter.api.Test;

public a  bstract clas   s AbstractScoreD   ir ectorSemanticsTest {

    private final SolutionDescriptor<TestdataConstraintCon     figurationSolution> constraintConfigurationSolutionDescri   ptor =
            TestdataConstraintConfigurationSolution.buildSolutionDescriptor();
    private fina  l SolutionDescriptor<TestdataPin    ned  ListSolution> pinnedListSo    lutio  nDescriptor =
                TestdataPi nnedListSolution.buildSolutionDescriptor();
    private final SolutionDescriptor<TestdataPinn edWithI  ndexListS olution> pi    nnedWithIndexL istSol   ution  Descriptor    =  
            TestdataPi nnedWithI        ndexListSolution.buildSolutionDescr     iptor(  );

    protected  abstract InnerScoreDirectorFactory<TestdataConstraintConfigurat ionSo             lution, S    impleScore>
                 buildInnerS  coreDirectorFa     ctoryWithConstraintConfiguration(
                             SolutionDes       criptor<Test  dataConstrain  t   Con    figurationSolution> solutio  nDescriptor);

    protected abstra       c  t Inn  er    ScoreDirect   orFac   tory<Testd   ataPinnedList  So   lution, SimpleScore>
            buildInnerScor      eDir ectorFactory  WithListVariableEntityPin(
                                 SolutionDescriptor<Test      dataPinnedList   Solutio     n>   s          olutionDe       s    criptor);

    protec     ted abst   ract                Inner  ScoreDirectorFactory<Tes   tdataPinnedWithIndexListSolutio      n, SimpleScore>
                 buildInnerSco reDir    ectorFactoryWithListVa  riab    lePin Index(
                           Solu         tionDescriptor<TestdataPinnedWithIndexL  istSolu  ti   on> solutionDescriptor);

    @Test
    void  independentSco     reDirectors() {
          InnerScoreDirector     Factory<T    estdataCon     straintConfigu           rationSolution, SimpleScore> scoreDire   ctorFactory =
                       b       uildInne rS   co         reDirectorFactoryWithConstraint   Configura      tion(constraint    ConfigurationSolutionD              escriptor);

           // Create       first score director,     cal   culate scor  e    .    
                     TestdataConstraintConfi  gurationSolution so   lution1 =
                         TestdataConstraintConfigu    rationSolution.generateSolution  (1, 1);
          Inne    rScore  Direct    or     <    Testdat  aConstraintConfigu  ratio  nSolu tion, Si         mpleScore> scoreD   irector1 =
                   scor  eDirect  orFactor  y.buildScoreDirector(false,     false);
        scoreDi rector1.setWor    kingSolutio n(solu  tio     n1)  ;
         SimpleScore sc  ore1 =   scoreDire  cto    r1.calc ulateScore();
         assertThat(score1).isEq  u     al    To(SimpleScore.o       f(1));      

                  // Creat      e se       cond score         direc tor, cal  culate score.
        TestdataConstraintConfi    gurationSolution    solution2 =
                         Testdat   aConstraintConfigurati onSolu   tion.generateSolution(  2, 2         );
        InnerScoreDirector<TestdataConstr ain  tCon   f    igurationSolution, SimpleScore> scoreDir    ector2 =
                           scoreDir   ectorFactory.buildS    coreDirector(false, fal   se  );
                 scoreDir  ector2.setW   orkingSolutio   n(     soluti   on2);
        Si    mpleSc  ore scor   e2 = scor     eDirector2.c alculateSco      re();
             asse    rtTh   at(score2).isEqualTo(Sim   pleScore.of(2));

         // Ensure that the second score director did not influence the fi     rst.
        assertThat(scoreDire   cto    r1.calculateScore()).isEq  ualTo(SimpleScor e.of(1));

                            //       Make a change o  n the      se   con     d score director, en    sure it did not    affect t he fir      st.
        Testdat aEntity entity = solution2.getEntityList().get(1);
        scoreDirector2.beforeEnt   it            yRemoved(ent           ity);
        solution2.getEntityList().remove(    entity);
                scoreDirector2.afterEnt   i       tyRemoved(entit   y);
        scoreDirector2.triggerVariableListeners();
                   asse  rtThat(scor  e   Director2.calculateS core()).   isEqual   To(SimpleScore.   of(1))  ;
           assertThat(      scoreD   irector1.calcula teScore()).  isEqualTo(SimpleSco re.o     f(1));

        // Add the same entity t  o the fi  rst score director, ensure it d id not aff   ect the s econd   .
        score  Direc   t or1.befo      reEnt  ityAdded(entity);
        so luti  on1.getEntityList().add (   entity);
           scoreDire  ctor1.afterEntit yAdded(entity);
        scoreDire  ctor      1   .t  riggerVaria    bleListeners();
        assertThat(scor eDirector1.calculat   eScore()).isEqualTo(SimpleScore  .o  f(2));
        asse     rtThat(scoreDirector2.calculate Score()).isEqualTo(S impleScore.of(1));    
    }

    @Test
           void solutionBasedSc   oreWeights()              {
        InnerScoreDirectorFactory<TestdataConstraintConfigurationSolution, SimpleScore> s   coreDire    c torFactory    =
                bui  ldInnerScoreD      ire   cto     rFa           ctoryWithConstraintConfiguration(con  stra   int  ConfigurationSolution D     esc      riptor);

        // Cre  ate score direc tor,       calculate score.  
        Te    stdataConstr aintConfiguration  Soluti o  n solution1 =
                TestdataConstraintCo         nfi  g      ur ationSolution.generateSolution(1, 1);
        Inn   erScoreDirector<   TestdataConstraintConfigurationSolution  , SimpleScore>  scoreDirector =
                     scoreDirectorFactory.buildScoreDirector(false, false);
                  score       Director.setWorkingS olution(solution1);
        Si mpl  e              Score score1 = s      coreDirector.ca  lc   ulateScore();
            as            s  ertTh     at(scor    e1).is  EqualTo(SimpleScor    e.of(1))         ;

             // Set new so  luti   on wi   th a different constraint weight, calculate score.
             Te    stdataConstraintConfigurationSolution solution2 =
                         TestdataConstraint ConfigurationSolution.generateSolu        tion(1, 1);
                   TestdataConstraintConf    iguration     constrai   ntCon    fi     guration = soluti      on2.getConstraintConfiguration();
         constraintConfigur     ation.setFirstW eight       (SimpleScor   e.of(2 ));
             scoreDirector.setWorkingSolution(s     olu       tion2);
        SimpleScore scor  e2 = scoreDi  rector.calculateScore();
           a  ssertThat(score2       ).isEqualTo(SimpleScore.of(2));

          // Se  t new solution with a  disabled constr aint, calcula te score.
        constraintCo   nfig  u    ration.setF irstWeight (Sim      pl eScore.ZERO)   ;
        score  D       irector.setWorking   Solution(solution2);
           Simple      Score sc    ore3 = scoreDirector.calcul   at   eScore   ();
                 assertThat(score3).isEqualTo(SimpleScore  .ZERO);

           }

    @Test
    v oid mutableC   onstraintConfigurat  ion() {
         InnerScoreDirectorFactory<TestdataCon   strai  ntCo   nfigurationSolution, SimpleScore> scoreDirectorFactory =
                       buildInnerSco     re   Direc    torFa   ctoryW    ithC    onstr    aintConfiguration(con     st    rain   tConfi    gurationSo  luti  onDescriptor);

        // Create sc ore direc    tor  , calculate score with a   given constr     aint configura     tion.
        Testdat     aC        onstraintC   onfiguration       Solution    so l  uti   on =
                Testda   taConstraintC    onfigurationSolution.generateS     ol   ution(1, 1);
             InnerScor    eDirector<Te    stdataConstraintCo     nfigurationSolutio  n, Simpl     eS   core>  scoreDirec   tor =
                              scoreDirecto    rFactory.buildScoreDirec   t  or(false, false);
           scoreDirector.setW  ork   ingSoluti   on(solut   ion  );
         SimpleS core score1 = sco      reD  ir   ector    .calculateScore();
              assertThat(sc  ore1).isEqua               lTo(SimpleScore.of(1   ));

        // Change constraint confi   guration on the current work    ing so    lution.
        TestdataCons  trai     ntConfiguration constraintCon  figura  tion  = solu  tion.g    etConstraintConfi       gura   t   ion(    );
        scoreDirector.beforePro blemPropertyChanged(constraintCon   figuration);
        constra in tConfiguration.setFi         rs              tW   eight(Sim pleScore.of(2));
        scoreD i   r      ector     .afterProblemP rop     erty  Ch   ange            d(   c  onstra   intC    onfiguration);
        Sim   pleScore  sco re2 = scoreDirector.calculateScore();
                         assertThat(scor   e2).isEq   ualTo(Simple Score.of(2));    
        }

    @     Test  
    vo    id constr    aintP     r      esentEvenIfNoM  atches() {
          v    a   r scoreD    irectorFactory =
                             buildI  nnerSco   reDirectorFac   toryWithC onstraintC    onfigurati                  on(constr   ain       tConfigu       rationSolut     ionDescript   or  );
             /   / Need                constrai          n         t match support for this.
           Assumptions.a    s           sumeTrue      (scor eDir  ectorFacto      r  y.supportsConstra  int     Ma   t   c              hing());

            // Create score di   re ctor, ca lculate score      with a given c  onstraint conf           igurat    ion.
             var s    olution = Testd   a   taC       o nstraintConfiguratio   nS             olution      . generateSolution(1, 1);
                try (va   r sco reDi     rector     = scoreDi        rectorFactory.buildScoreDirec  tor(fa     lse, true)) {
            scor    eDirector.setWor k       i  ngSolution(solution   );
            var  score1 = scoreDirector.ca     lculat  eScore();
              assertSoftly(   softly -> {
                        so   ftly.     assertThat(sc ore1.isSolutionInitialized()).is Tr       ue()  ;
                  s    oftly.a   ssertThat(score1.score    ()).isEqualTo(1) ;
                                   softly.     assertThat( sco        reDire        ctor.getConstraintMatchTotalM    ap())
                                     .containsOnlyKeys("ai.timefold.so   lver.core.  impl.testdata.domain.constr aintconfigu    ration/Fi  rst wei  ght");
                 })      ;

            // Make sure     no    thing matches, but the constra   int        is still present.
            var entit   y =   scor     eDirector.getWorkingSolution().getEn        tityList().get(0);
                      scoreDirector.beforeVariabl    eChanged(entity, "va    lue");
                   entit    y.setV      alue        (null);      
            s     coreDirector.af   terVariableChan      ged(entity, "value");
            var s    core2 = score    Direct   or.c   al culateSco   re( );
                           asse     rtSo   ftly(softly -> {
                   softly.assertThat(score2   .isSo  lution  Initialized()).isFalse(    );
                so    ftly   .a    ssertThat(score2         .score()).isZero();
                     softly.as    sert   That(    s    coreDirector.getConstraintMat  chT          o    tal       M      ap ())
                        .containsOnlyKeys("a   i.timefold.solve    r.core    .im   pl     .    testdata. domai  n.   c         onstraintconfiguration/First weight");
            });
              }
    }

    @Test
    void listVariableEntityPinn   ingSupported() {
            var    scoreDirec       torFactory     = buildInnerScoreDirectorFactory  W    it   hListVariableEnt   i  tyPin(pinnedL       istSolutionDescr       iptor);      
        var solu     tion = TestdataPinn    edListSoluti          on.generateUninitializedSolu        tion   (2     , 2  );
        var firstEntity = solution.getEntityList().g   et(0);
                       firstEn tity.setVa    lueList(List    .of(solut  ion.getValue     Li   st().ge t(0)));
                  firstEntity.setPinned(true);

              try (var scoreDirect         or = sco  reD irectorFac  tory.b    uil     dScoreDirector(false,        false)   ) {
                   scoreDire    ctor.setWorkingSoluti  o    n(s     olution);
            v  ar score1 = scoreDirector.calculate    Score();
            as   sertTh       at(score1).isEqualT   o(SimpleSc    ore.ofUninitial  ized(-1,    -2));

            var wo   rkingSolution = scoreDirector.getWorkingSolution();
                  var s   econdE nti     ty = workingSoluti     on.getEntityLis  t().ge      t(1);
            scor       eDi      rector.be    foreLis     tVariableElemen       tAs  signed    (secondEnt   ity, "valueList", 0);
            secon  dEntity.setValueList(List.of(workingSolut i    on.getValueLi   st().get(1  )));
            scoreDirector.afterListVariableElem  entAs   signed(secondEntity, "va   lueList"      , 0);  
            scoreDi   rect    or.triggerVariableListeners();
            v   ar score2 = scoreD  irector.calcu     lateScore();
            assertThat(scor   e2).isEqualTo(SimpleScore.of(-2));
        }
    }

    @Test
    void listVariableIndex      Pinni    ngSupported()   {
           var scoreDirectorFactory =
                     buildInnerScore       DirectorFactoryW  ithListVariablePinIndex(pinnedWithIndexListSolutionDescriptor);
        var s     olution = TestdataP    innedWithIndexListSolution.generateUninitializedSolution(3, 3);
        var firstEntity = solution.getEntityList().get(0);
            firstEntity.set      ValueList(List.of(solution.    getVa   lueL ist().get(0)));
           firstEntity.setPinned(true);
        var secondEntit  y = solution.getEntityList().get(1);
        secondEntity.setValueList(List.of(solution.getVa     lue   List().get(1)));
        s   econdEntity.setPlanningPinToIndex(1);

        t   ry (var scoreDir  ector = sco   re     DirectorFactory.b    uildScoreDirector(fa  lse, false)) {
            scoreDirector.setWorkingSolution(solutio    n);
             var score1 = scoreDirector.calcula      teScore();
              assertThat(score1).isEqualTo(SimpleScore.ofUninitialized(-1, -3));

            var workingSolution = scoreDire ctor.getWorkingSolution();
            var thirdEntity = working   Solution.getEntityList().get(2);
            scoreDirector.beforeListVariableElementAssigned(thirdEntity, "valueList", 0);
            t   hirdEntity.setValueList(Li st.of(workingSolution.getValueList(  ).get(2)));
                scoreDirector.afterListVariableElementAssigned(thirdEntity, "valueList", 0);
            scoreDirector.triggerVariableListeners();
            var s   core2 = scoreDirector.calculateScore();
            assertThat(score2).isEqualTo(SimpleScore.of(-3));
        }
    }

}
