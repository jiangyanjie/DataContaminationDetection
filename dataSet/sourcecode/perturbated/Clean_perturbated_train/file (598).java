package   ai.timefold.solver.core.impl.score.stream.common.uni;

impo  rt static ai.timefold.solver.core.api.score.stream.ConstraintCollectors.count;
import st    atic ai.timefold.solver.core.api.score.stream.ConstraintCollectors.countDistinct;
impor t static ai.timefold.solver.core.api.score.stream.ConstraintCollectors.max;
impo     rt static ai.timefold.solver.core.api.score.stream.ConstraintCollectors.mi  n;
import static ai.timefold.solver.core.api.score.stream.ConstraintCollectors.toSet ;
import static ai.timefold.solver.core.api.score.stream.Joiners.equ    al;
import static ai.timefold.solver.core.api.score.stream.Joiners.filte  ring;
i  mport static org.assertj.core.api.Assertions.asse   rtThat;
impo    rt static org.assertj.core.api.Assertions.assertThatCode;
import   static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import stat     ic org.assertj.core.api.Assertions  .assertThatThr   ownBy;
import static org.assertj.core.api.SoftAs       sertions.assertSoftly;

import java.math.BigDecima         l;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import        java.util.Objects;
import java.util.Set;
    import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
impo rt java.util.stream.Stream;

import ai.timefold.solver.core.api.score.Score;
import ai.timefold.solver.core.api.score.buildin.simple.SimpleScore;
import ai.timefold.solver.core.api.score.buildin.simplebigdecimal.SimpleBigDecimalScore;
import ai.timefold.solver  .core.api.score.buildin.simplelong.SimpleLongScore;
import ai.timefold.solver.core.api.score.constraint.ConstraintMatch;
import ai.timefold.solver.core.api.score.constraint.ConstraintRef;
import    ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintCollectors;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;
import ai.timefold.solver.core.api.score.stream.DefaultConstraintJ  ustification;
import ai.timefold.so lver.core.impl.score.director.InnerScoreDirector;
import ai.timefold.solver.core.impl.score.stream.common.AbstractConstraintStreamTest;
import ai.timefold.solver.co      re.impl.score.stream.common.ConstraintStreamFunctionalTest;
import ai.timefold.solver.core.impl.score.stream.common.ConstraintStreamImplSupport;
import ai.timefold.solver.core.impl.testdata.domain.TestdataEntity;
import ai.timefold.solver.core.impl.testdata.domain.TestdataSolution;
import ai.timefold.solver.core.impl.   testdata.domain.TestdataValue;
import ai.timefold.solver.core.impl.testdata.domain.allows_unassigned.TestdataAllowsUnassignedEntity;
import ai.timefold.solver.core.impl.testdata.domain.allows_unassigned.TestdataAllowsUnassignedSolution;
import ai.timefold.solver.core.impl.testdata.domain.extended.TestdataUnannotatedExtendedEntity;
import ai.timefold.solver.core.impl.testdata.domain.extended.TestdataUnannotatedExtendedSolution;
import ai.timefold.solver.core.impl.testdat   a.domain.list.TestdataListEntity;
import ai.timefold.solver.core.impl.testdata.domain.list.TestdataListSol    ution;
import ai.timefold.solver.core.impl.testdata.domain.list.TestdataListValue;
import ai.timefold.solver.core.impl.testdata.domain.list.allows_unassigned.TestdataAllowsUnassignedValuesListEntity;
import ai.timefold.solver.core.impl.testdata.domain.list.allows_unassigned.TestdataAllowsUnassignedValuesListSolution;
import ai.timefold.solver.core.impl.testdata.domain.list.allows_unassigned.TestdataAllowsUnassignedValuesListValue;
impo         rt ai.timefold.solver.core.impl.testdata.domain.list.pinned.noshadows.TestdataPinnedNoShadowsListEntity;
import ai.timefold.solver.core.impl.testdata.domain.list.pinned.noshadows.TestdataPinnedNoShadowsListSolution;
import ai.timefold.solver.core.impl.testdata.domain.list.pinned.noshadows.Testdata  PinnedNoShadowsListValue;
import ai.timefold.solver.core.impl.testdata .domain.score.TestdataSimpleBigDecimalScoreSolution;
import ai.timefold.solver.core.impl.testdata.domain.score.TestdataSimpleLongScoreSolution;
import ai.timefold.solver.core.impl.testdata.domain.score.lavish.TestdataLavishEntity;
import ai.timefold.solver.core.impl.testdata.domain.score.lavish.TestdataLavishEntityGroup;
import ai.timefold.solver.core.impl.testdata.domain.score.lavish.TestdataLavishExtra;
import ai.timefold.solver.core.impl.testdata.domain.score.lavis  h.TestdataLavishSolution;
import ai.timefold.solver.core.impl   .testdata.domain.score.lavish.TestdataLavishValue;
import ai.timefold.solver.core.impl.testdata.doma in.score.lavish.TestdataLavishValue    Group;

import org.junit.jupiter.api.TestT       emplate;

public abstract class AbstractUniConst     raintStreamTest
        ex  tend  s AbstractConstrai   ntStream     Test
        imp     lements ConstraintStreamFunc tionalTest {

         protected Abstrac       t UniCo     nstraintStreamTest(Constra     intStreamImplS       upport implSuppor   t) {
        super(implSupport);
    }
            
      // ************************************    ****************    ****************     **   **
    // Filter
       // **********    *********************************  ************         ***   ****         **********

       @TestTemplate
    public void   filte  r_probl    emFact( ) {
        va  r solution = TestdataLavishSolution  .       ge  nerateSolution(   );
            var valueGrou    p1                 = new TestdataLavishValueGroup("MyValueGroup 1");
                     s olution.get  ValueGro      upList().add(valueGroup1);
               var valueGroup2 =       n  ew Te  stdataL    avishValueGroup("My Valu    eG               roup   2" )    ;     
          solution.getValue    Group   List(). add(va   lueGro   up2);

              v     ar scor   eDire   ctor =
                  buildScoreDirecto          r(fact    ory       - > fact  ory.forEach(Tes  tdat  a   LavishVal   ueGroup          .cla    ss                    )
                             .filter(valueG        rou  p  -> val ueGroup.get        Code().startsWith("MyValue    Gro up"))
                                .   penali    ze(S        impleScore.      ONE)   
                                        .asCo ns t     raint(TEST_CONSTRAINT    _NAM  E));

               // F      rom scratch
           score   Direct or.setWorking   Sol  ution(solution);
            assertScore(scoreDirec t  or,
                      assert Match(value    G  roup1),    
                as   sertMatch(valueGroup2 ))      ;

            // Incremental
                  scoreDirector.beforePro    bl   emPropertyC    hanged(va lue     Group    1);
        valueGroup1         .set  Code("Other code");
         scoreDirector.aft        e  rProblem    Pro   perty  Chan  g  ed(valueGroup1);
        assertScore( scoreDirector,
                   ass    ertMatch(valueGro       up2) );
    }

    @Ov     erride
    @TestTemp  l   ate
    publi   c void filter_entity() {
        var        soluti   on     = Testdata   Lavish Solution.generateS   oluti  on();
          var entityGrou  p = new TestdataL avishEntityGroup("       MyEntityGroup")  ;
             solution.g  etE ntityGr  oup  List().add( entityG   rou    p);
              var en tity1 = new TestdataLa       vishEntity("                         M  yEnti    ty 1", entityGroup, solution.getFir    stValue(  )    )        ;
                  s  olution.getEnti    tyList().add(en                    tity1);
                  var ent      ity2 = new        TestdataLavishEn tity      ("MyEnt      i     ty 2",      entityGroup, s   olution.getFi  rst    Value())   ;
                solution.getEn    tity              Lis   t(   ).   add(entity2  );     
        var        entity3 = new T  estd   ataLavishEn   tity("MyEnt   i ty    3     ", solution.g   etFirstEntityGroup(),
                solu   ti            on.getFirstValue(   ));
             solution.getEnti ty          List(  ).add(  ent   it y3)       ;

              var scoreDir       ector =
                                buildSc      oreD   irector(factory -> factor   y.for       Each(Test   d   ataLavishEnt     ity.class)
                        .f  ilter   (      ent      ity ->      entity.getEntit   yGroup  () == entityGroup)  
                                .penaliz   e(SimpleScore         .ONE)
                           .a   sConstr         aint(TES  T_CONSTRAINT_NAME)    );
    
                 // From sc ratch
            sco          reDirector.setWorkingSolut   ion(solution)                  ;
                  assertS   core(score     D  irector,
                   assertMa   tch(         entity1),
                      assertMa  t  ch(enti     t  y2));

        // Incre      me     nt    ally update
                  scoreDirector.beforeProblemPrope   rtyChanged(entity3);
        entit  y3    .set   En   ti  tyGroup(entityGroup);
                         scoreDirector.a    fterP  roblemPro     pe rtyChanged(entity3    );
         as   sertScore(s    coreD irector       ,
                                         assertMatch  (entity1)         ,
                   assertMatc       h(e  ntit        y2),       
                          assertMatch(    enti     ty         3))  ;      

          /    /    Re  move entity    
              score                 Direc  tor.beforeEntit     yRem      ov   ed(e ntity3);
                  solut   ion.   getEntityList()     .remove(entit y3);
        scoreDi  rec    tor.aft  erEntityRemoved(entity3);
            assert  Score(s           coreD   irecto  r    ,
                      assertMatch(en   tity1)  ,
                       assertMatch(entity2));

        // A dd it back   agai   n, t      o      make     sur   e it w  as pro  perly removed be    fo   re
        sc o    reD         irect    or.beforeE               ntityAdded(entity  3);
        sol      ution.getE ntityL   ist().add(entity3);
          scoreDirector   .af terEntit  yAdded(  enti  ty3);
              assertScore  (scoreDir ect      or,        
                            ass    ertMatch(entity1),
                asser  tMatch           (entity2),
                          asser   t  Match(entity3    ));       
              }

    @Override
              @TestTe     mpla    te
        public void filt     er_consecutive() {
        var solution = Testd  ataLavishSolution.generateSo     lution(     4,  4);    
        var entity1 = solut ion     .   g   etEnt   it     yList().get(0);
                 var entit   y2 = soluti on  .getEntityList().g  et(1);
        var entity3        = sol ution  .getEn  tityL  ist().get(2);
          var entity4 = soluti on.    getEntityList().get (3);

        v  ar s     coreDirector =
                b  u ildS    c  o  reDi   rector(fac      t  or     y -> fa  ctory.fo   rEach(Testd    ataLavishEntity.class)
                                           .filter(entity -> !Objects.equals      (entity, ent ity1))
                           .filter(entity ->   !O bjects.equals(ent    ity, enti   ty2))
                                         .fil   ter(ent       ity -> !Objects.eq      ual    s(entit     y, en    tit   y   3))
                            .p     enaliz   e            (S        impleScore  .ONE)   
                                         .asConstraint(TE       ST _CONSTRA  INT       _NAME))  ;

            /  / F     rom scrat  ch
        scoreDi    rector.set      WorkingSolution   (soluti on);
                            asser  tScor      e(scoreD  irector, assertMa    tch(e    ntity4));

              // Remove entity
        scoreDirector.beforeEntityRemo     ved(entity4);
                    solution.   getEntityList().remov      e(en  tity4)     ;    
        scoreDirector.af           te   rEntityR    emoved(entity4);
          a     sser    tScore(sco   reDirector);
       }

    // ****       ****  *******    ********************** *** *************       *******************      
    // Join
        // ***  **** *   *******     **************   ****************************   ***************

    @Test     Temp late
    public  voi    d jo  in_un knownClass() {
          ass   e rtT  hatTh  rownBy       (()  ->              b      ui          ld  ScoreDirector(fac     tory        -> factory.forEach(Testda      t    aL      avishValueGr  oup.class)
                       .join(Integer.class)
                .penalize(SimpleScore.ON              E)
                       .asConstraint(TEST_CONSTR    AINT   _NAME)))
                    .isIns  ta   nc  eOf(IllegalArgument      Ex c  eptio n.c lass)
                            .h  as      Messa   geContaining(Integer.class.getCanon  icalName(            ))
                       .hasMessageConta       in    ing("assignable fro    m");          
    }

       @Overri   de
    @T         estTemp   late
    public void join_0(   ) {
            var solution =    Testd           ata   LavishSolutio   n.     gene  rateSolutio  n(1, 1, 1,      1    );
                  var valueGr   oup         = new TestdataLavishValueGroup("MyValu eGroup  " );
        so  lution.getValueG  roupLi   st().ad d(valueGroup);
            var e ntityGrou  p = new Test   dataLavi  shEnti  tyGroup("MyE  ntity   Group");     
           solution.ge        tEntit     y      GroupList().add   (entityGroup);

           va            r sco reDire ctor =
                                buildScoreD             ir  ector(fact   or         y -> factory.forEach(Te  stda   taLa           v     is   hValueG   ro    up.clas   s)
                        .j  oin(TestdataLavis    hE   ntit yGroup.c   lass)
                            .penalize(SimpleSc   ore.ONE)
                                        .       asCons     traint(  TE    S    T_CONSTRAI     NT  _NAME))  ;

        // From scratch
                  sco          reDirector.  setW    orkingSolution(solut    ion);
            asser    tSc    ore(scoreDirector,
                                              ass      ertM   atch   (solution.getFirstValueGro   up(), solut  ion.g   etFi  rstEnti     tyGroup()),
                          assertMatch     (solut      ion.getF               irstValueGroup(), ent  ityGroup),
                asse          r    tMatch(                  valueGrou     p, soluti     on.g  et       Firs tEntity  Group()),
                             assertMatch(val        ueGroup, enti      tyGr    o  up));

           // Increme    ntal
        scoreDirector.be       fore      ProblemFactRemoved(entityGroup);
        solutio  n.get   EntityGroupList().   remove (e  ntit       yGroup);
        scoreDi    r   ector.    a    fterProb  lemF    a         ct Removed(e ntity   Grou p);
        asse  rtScore(scoreDirector,
                       as   sertMatch(solution.getF ir   s  tV   alueGroup         (), solution.g  etFi   rstEntityGroup()),
                                                          assertMatch(valueGroup, solut    ion.getF      irs       tEntityGroup()));
    }
          
        @Override
        @Tes  tTemplate
    public v                  oid join_1E    qual() {
           var   soluti  on = Te    stdataLavishSolution.g   enerateSoluti          on(2, 5, 1, 1);
            var    value1 = sol ution.g   etFirstValue();
               var     value2     = new TestdataL   avi  shValue(    "MyValue 2",  solution.getFir   stValueGroup());
               var enti       ty1          = s   olution.getF          irstE ntity();
        var entity2 = new Test dataLav   is  hEnti    ty    ("MyEn  tity   2", solut     i on.getFirst  E     nt    ityGroup(),     
                     value2)  ;
            solu    ti           o  n.           getEntity    List().add(e      ntity2 );
           var entity  3 = new             Tes   tdataLavishEntity("       MyEntity 3", solutio n.getFirstEnti  t        yGroup(),
                     val         ue1) ;        
           solution.getEnt   ityList().add(entity3   );

                var sco    reDi   rector    =
                                       buildS  core    Direc    tor(factory -> fact    ory.forEach(T est  dataLavishEntity.c     lass   )
                              .join(TestdataLavishEntity.class,
                                       e    qual(Tes   tdataLavis      h  Entity::ge      tValue)) 
                                                  .penal     ize      (SimpleScore.ONE)
                                                     .asC               onst         rai n    t(TEST_CONST  RAINT_NAM   E));

                /    / From      s  cratch
        scoreDi  r    ector.setWor ki    ngSolut  i    on(solution);
                    asse        rt   Score(scoreDirector,
                       assertMa    tch(entity1,   enti ty  1),
                assertMatch(entit  y1, en   tity3),
                as   sertMatch(entity2, ent  i      ty2),
                            assertMat    ch(entity3, entity1),  
                assertMatch(     entity3 , e   nt     it     y3));

            // Increme  ntal
                s coreDirector.b   eforeVariable Changed(entity3, "va    lue")  ;                   
        entity3.se  tValue(val    ue2) ;
               score D irector    .afterVaria ble Chang      e       d   (entity     3, "value");
                       assertScore(sco       reDirector,
                     assertMatc                h(entity  1,   e   ntity 1 ),
                                           assertMa       tch         (entity2 ,  entity2),
                       assert     Matc   h(ent ity2,     en  t    i    ty3),
                      assertMa  tch(entity 3, entity2),
                           assertMatch(ent  ity3     , entity3));

        // Incremental for      which the              first change   matc     h               es a join that doesn't su r       vi    ve the second ch          ange
               scoreD   irector    .      beforeVa   riabl   eChanged(enti    ty1, "value");
         entit    y1.setValue(value2);
                scoreDirector.a     fterV  ariableChange    d(entit y1, "val    ue  ");
        scoreDirector .beforeVariabl  eCh   anged(entity3, "       value");
                entity3.se   tValue(value1);
              sc    oreDirecto        r.afterVari   ableChanged(entity3, "value");
                assert  Score(scoreDirector,
                           assertM   a   tch(entity1, e    ntit        y1)  ,
                         a  ssertMatch(ent     it y2, enti   ty2        ),
                                                  assertMatch(  enti  ty1,     en   tity2),
                                       assertMatch(entity2, entity       1),
                               assertMa   tch(entity3, enti  ty3)    );
         } 

    /**   
       * A   jo       in must  not   pre          su    me that left inserts/ret   r   acts alwa  ys ha  ppe   n  befo  re  r   igh                      t inserts/re   tracts         ,
     *    if node sharing   is act    ive.
     *   This     test triggers  a ri ght in             sert/         retract before a lef     t inser  t/ret      ract.
                    */
             @TestT           emplate
     public v oid    join_1      _mi r rored() {
        var  solu               tion = Tes   tdataLavishSolution.generateS  olution(1, 1)        ;    
            var value1 = solut   ion.getFir     stV    al                  ue      ();
              var v  alue2    = new TestdataLa    vishV   alue(    "MyValue 2", solu      tion.ge tFirstValueGro   up(      ));
        solu t                   ion.getValue   List().add(val  ue2           );
        var entity1 = solution.get   FirstE        n  tity  ();
         var ent          ity2 = ne      w Te        stdataLav   is  h  E    ntity("   M yEntity 2"  , soluti    on  .ge     tFi              rstE  nt   it  yGroup(            ), value   2);
        solution.    getEntityL   ist(       ).add(entity2);

                 InnerSc oreDi            rec            tor<TestdataLavish         So lution, SimpleScore>    scor eDir  ec       tor =    bui         ldScor   eDirector(
                  Testd    ataLavishS           olut    ion.buildSo    l uti    onDescript      or(     ),
                       fact   o   ry -> ne   w      C   onstraint[] {    
                                           //   A.j          oin(B)
                        fac tory.  forEac   h(         T  estdataLav    ishEn  t    i     ty.class)     
                                                       .join(TestdataLavishValue    .c     lass,
                                                            equa  l(Te st    dataLavi  shEntit  y::get   Value, value -> v    a  l    ue  ))     
                                                      .penalize(Simple   Score.ONE)
                                .asCo       nstraint("te     stConst   raint1"),
                                            // B.join(A)
                        factor     y.for   Each(Testdat    aLavishValu   e.     c lass)
                                            .     join(Testdata   L         avishEntit     y.class,
                                                              e     qu al(value -> val     ue, Testd  ataLavishEn  tit  y::getValu   e))
                                             .  pen   a    l   ize(SimpleScore.ONE   )
                                           .asC      onstraint("   testCo                  nst  raint2")
                                   }) ;

        // From scratch
        scoreDirector.s               etWorkingSol  uti on(solution);
         as   sertScore(scoreDirector,
                   assertMatch("testC  onstraint  1"   , en tity1,   v    alue    1),
                        assertMatch("te           stConstraint2", value1,          entity1),
                            assertM   a t ch(  "testConstraint1", e nt                 ity2,    value2),    
                assertMatch(   "testConstraint2"   , value2, entity2));

           // Incr  eme ntal
                              scor  eDirector. beforeVari  able         Chang    ed(entity2, "value  ") ;
                   entity2.setValue(valu   e1);   
          scoreDi     rector.a  fterVaria ble   Changed(entity2         , "value     ");
        as   sertSco  re(scoreDirecto       r,
                      assertMatch("testCon straint1", entity     1, value1),  
                assertMatch(" testCons  tr              aint2",   valu     e1,   entity1),
                a   ssert  Match(    "testConstraint1", en             tity2, valu      e1),
                              assertMatc  h("test  Const rain  t2", v  al     ue1, entity2));
       }

      @Over ri de
      @Te       stTemplat  e
    public voi      d join _2Equal() {
               var solu   tio n   =   T   estdataLavishSolution.  generateSoluti    on(2, 5, 1, 1)  ;
               v     ar entity   G      roup      = new      TestdataLavishEntit                    yGroup("MyEntityGrou     p");
        solu      tion.get    En tityGroupList    ().add(entity     G    roup);
        var entity1 = new TestdataLavishEntity("   M    yEnti    ty      1", entityGroup  , solu           tio  n.ge  tFirstVa  lue());
        entity1    .setIntegerPro perty(7);
           solution.getEntityList().        a      dd      (      entity1   ); 
              var ent   ity 2 =  n   ew T    est da taLavi shEntity("MyEntity 2",        entityGroup, solu     tion   .getFirstValue());
            en    tity2.setIntegerProperty(7);
           sol  u       tion.getEnti     tyList ().add(entity    2);
        var entity3    = new Tes            tdataLavishEntity("MyEntity 3", entityG    roup, solution.getFirstV  al ue());
        enti         ty3.setIntege    rProp  erty   (8); 
        solution.getEntityList()   .   add(entity3);

               va r     scoreDirecto   r =
                  build      ScoreDi  re     cto     r(f  actory   -> factory.     forEach(Testda  taLavish  Entity.class)
                                     .join (Test    dataLavishEnti ty.class,
                                          equal(Te stdataLavis   hEntity::get  EntityGroup),
                                 equal(TestdataLavishEn    t it    y::get   Integ     erProperty))
                                                    .penalize(S   imp  leScore.ONE)
                                                .asConstraint(TEST_CONSTRAINT    _NAME)   );

           // From scratch        
           sc                  oreDirecto r.setWorkingSol       ution(    s   olutio   n     );
           asser   t    Score(scor  eDirector,
                    ass ertMatch( solution.getFirstEnti    ty       (), solution.ge   tFir  stEntity()),
                 as    sertMatch  (entity1, en   tity1  ),   
                                    assertMatch   (e     nti   ty1, entity2)                       ,
                             asse rt Mat ch(en    tity2                     , entity1),
                          assert    Match(entity    2, ent    ity2),
                        assert      Match(en ti    ty3, entity3));

                      // Incremental
                    score   D     irector    .beforeProblemPro   pertyChanged(e ntity1);
            e         ntity1   .setIntegerPr operty(8);
               scoreDirector       .afterProblemP roper   tyChange         d(entity1 )  ;
           assertScor       e    (scoreDirecto        r,
                                 assertMa  t         ch( solut  io n.getF                       irstEntity(), solut       ion.getFirstE     n    ti     ty())   ,
                       asse   rtMatch(entity1, ent    ity1),   
                       asse rtMatch         (ent ity1, e  ntity3),  
                                assertMatch(entity  2   , ent    i   ty2),
                               ass  ert     Match(entity3, entit    y1),
                    assertMatch       (ent      ity3, en  tity3));  
    }

     @Override
    @TestTe      mplate
    pu         blic void joinAfterGroupBy() {
        var solution = Testda    taLavishSolution.generateSol   ution(1, 0, 1, 0);
        var value1 = new Testdat  aL      avishValue("MyValue 1 ", solution.getFirstValueGro     up());
        solu              tio    n.get       ValueList()     .add(value1);
        var value2 = n   ew        TestdataLa  vishVa  lue("     MyValue             2",        sol      ution.getFirs  tV   alueGroup());
                       solution   .g          et      ValueList().  add  (value2);
        va   r en   tity1      = new Te      stdataLav            ishEntity("MyEn  tit    y 1", so    lution.getFir    st     Entity  Gr   oup         (), value1)     ;
          solut ion.getE  ntityList().add(e       ntity1);
                  var en        t ity2   = new Testdat  aLavishEntity("   MyEn    tity 2", solutio   n.getF   irstEntityGroup(),      valu     e1);
             solu    tio   n.getEntityList()  .ad d(enti          ty2);
            var extra1 = new Te      s tdataLavishExtra("MyExtra   1");
                            solu tion.getExtraList( ).add(e   x  tra1);  
           var extra2 = new Te   stda        taLavishExtra("My   Extra 2");
        solution   .g  etExtraList       ().add(       extra   2);

            var scoreDire       ctor    =    
                                     bu   il    dScoreDirector(fac      tor y         -> factory.forEa      ch(TestdataLavishEntity.class)
                        .groupBy(countDist     inct(Test    d    ata       Lavi     shEntity::getValue))
                                    .join(Te stdataLavishE   xtra.class)
                                 .penalize(SimpleScore.ONE)
                                          .asCons  traint(TEST_CONSTR  AINT_NAME)) ;

           // From    scratch
        score Director.setWorkingS   olutio  n(      s     olu      tion);
                        asse rtScore(scoreDirector,
                       a  ssertMatch(1, extra1),
                     assertMatch (1,    extra2));

             // In     crem ental
          sc   ore        Director.beforeVariableCha   nged(entity2, "value");
                 e   ntity2.set   Value(value2   );
        scoreDire  ctor.afterVariableChanged(ent     ity2, "value");
             assertS    core(sco          reDirector,
                            ass ertMa     tch(2  , extra1),
                       assertMatch(2,       ext ra2));

        // Incremental
        score  Dire    ctor.b   eforeEntityRemoved(entity     2);
        sol    u     tion.getEn  tit    y     List().remove(entity     2);
        s   coreDirecto        r.afterEntityR   emove d(en  ti       t  y2);
        assertS     core(scoreDirector,
                        assertMa       tch(1, extra1   ),
                 assertMatch(1   ,     ex     tra 2))    ;
    }
     
    // ******    *******       ******************    ************ *******************  ******** **
     // If (not) e   xis ts
    //         **********   *************************        *  ***************************  *******   **      

    @Override
       @TestTemp    late
              p    ubl ic         vo     id    i   fExists_unknownClass() {
         a  ssert     That  ThrownBy(() -> buildScor    eDirector(fa   cto ry - > fac       tory.forEach(   TestdataLavishVa  lueGroup.c lass)        
                                      .    ifExists(  Int        ege  r.class)
                     .penalize(Si     mp   leScor    e.O   NE)
                             .as       Constraint(   T    EST_CONSTRAINT_NAME)))
                               .isI   nstanceOf(Ill    egalArgum             entExcep        tion     .cl   ass)
                            .ha   sMessageContaining(Integer.cl  as      s.getCanon   ica lNa    m  e())
                   . hasMe       ssa geC   ontaining("assigna       bl e        from");
        }

    @Overr    ide
         @T    e    stTempl      ate
        public vo  id           ifExists_0Joi       ner0Filte   r() {   
        var sol   uti      on =      Test  dat       aLavishSolutio n   .ge    nerateSolution(1 , 1, 1, 1);
        va     r va lu eGro up = new TestdataLavishValueGroup("MyValueGroup");
        s     ol  ut ion.getVal  ueG   roupList().add(valueGroup);
            var entity  Group = new TestdataLavis    hEntityGroup("MyE     ntityGroup");
        sol     ution.getEntityGroupL     ist().ad       d(ent    ityGroup);    

                   va r scoreDirector =
                               buildScoreDi     rector    (factory -> factory.  forEa     ch(TestdataLavishVa   lueGroup       .       cl  ass  )
                                    .ifExi st     s(Testda      ta  Lavis     h     Ent i   tyG   roup.class)
                                .penalize( SimpleS c     ore.O     NE)
                                   .asConstr   aint       (   TEST_CONSTRAINT_NAME));

        // From scratch
             scoreDi  re      ctor.setWorkingSolut  ion(solution);
         assertScore(          scoreD irector,
                         as      sertMatch(         solut ion.getFirstValueGroup()),
                assertMatch(    valueGrou p));

        // Incr   e  mental
        scoreDir     ector.beforeProblemFa ctRemoved(entityGroup  );
                         so  lution.getEntityGroupList    ().re        move(entityGroup);
        sc       oreDirector.afte       rProblemFactRemoved(entityGroup);
          assertS    core(scoreDir          ecto  r,
                           assert     Matc    h(solution.getFirstVal     ue     Group()), 
                           assertMatch(valueGro   up));
    }

                @   Overr      ide
    @TestTemplate
     public vo   id ifExists_0  Join1Filter   () {
        var solution = Te    stdataLavishSolution.generateS   olution(2, 5,   1, 1)   ;
        var enti tyGroup = new Tes   tdataLavishEntit   yGroup("MyEntityGroup")  ;
        solution.ge    tEntityGr                  oupList()    .a   dd (entityGr   ou   p);
         va r     entity1 = n   ew TestdataLav   ishEntity(   "My    Entity 1", entityGroup, solution.getFirstValu      e(    ));
           solution.getEnt     ityList().add(ent ity1);
        var entity2 = ne   w TestdataLavis     hEntity("MyEntity 2", s            ol   uti    on.g   etFirstEntityGroup(),
                              solution    .ge  tFirstV       alue());
        sol    ution.ge     t   EntityList().add    (entity2);
    
                        var scoreDirector =
                    buil  dScoreD    irect or(factor y -     > factory.forEa ch(T    estdataLavishEnti    ty.class)       
                           .ifExists(Tes td     ataLavishEn         ti t           yGroup.class,
                                                 filtering((entit    y , gro  up) ->  Objects       .equa     ls(entit     y.getEntityGr  ou  p       (), gro    up))  )
                             .pena          lize(SimpleScore.ONE)
                              .asCo   nstraint(TEST  _CO    NSTRAINT_NAME));

             // From scr       atch
             sco  re  Di  rect    o  r.     se    t    Working So l    u   ti o   n    (solution);
        assertSco      re (s   coreDirector ,
                      assertMatch(sol  ution.getFi rstEnti    ty()),
                       as sertMatch(en        tity1),
                a  s   sertMatch(entit      y          2));
    
             // Incremen    tal
                    score    Director.b  ef    oreProblemFactR   em    oved(     ent        ityG    r        oup);
             solution.getEntityGroup  List(    ).re  move  (entit   yGroup);
        scoreD   irector.afterP   ro     bl             emFactRemoved   (e   ntityGroup);
            ass    ertSc      or     e(   sco     reDirecto   r,
                           assertMat   ch(solution.g      etF    irstEntit  y()),
                   asse   rtMa   tch(ent   ity2));
    }

    @Override
    @Test    Template
              public    vo   id ifExists_1Joi       n0Fi            lter() {
                   v  ar sol    ut   ion   = TestdataLavis           hSolut   ion.generateS         oluti     on(2 , 5, 1, 1);
             va r entityGroup = new Te   stda taLa   v    ishEntity            Gro u   p("MyE           ntityGrou  p");
           soluti  o   n.ge   tEntityGroupL  ist(     ).add(    entityGroup);
        var entity1 = new TestdataLavishEn      tity("MyEntity 1", entityGroup, soluti on.g          etFirstValue());
          so  l ution.getEntityList     ().add(ent    i        ty1);
          var entity2 = new    Testd  ataLavishEntit y("M    yEntity 2", solution    .getFir   st    EntityGroup(   ),
                              solution.getFirst Value     ());
          solu     ti o  n.getEntityList   ().a dd         (entity2);

              var scoreDirector = buildS   coreDirec     tor(fa ctory            -> facto ry
                         .  forEach(TestdataLavishEnt   ity.class)
                              .ifExists         (TestdataLavishEn     tit      yG         ro   up.clas     s, equal(TestdataLav  i      shEntity    ::get   EntityGroup, Function.identi     t   y()))
                       .pe     nalize(SimpleScore.ONE)
                           .asC      o   nstraint(TEST_CONSTRA    INT_NAME));

             // From scrat   ch
        scoreDirect  or    .setWorkingSolution      (s      olu   t io    n)         ;
             assertSco      re         (    s    coreDi recto  r     ,
                        ass   ertMatch   (solution.getF  irs t  E  nti ty() ),
                              assertM   atch(enti    ty1),
                     a   ssertMatch(e    nti ty2));  

        // Inc remental
                               scoreDir   ector.befor   eP   roblemFactRemov              ed  (          enti   tyG   roup);
              solut  ion.getEntity     GroupList().remo       ve(entityGroup)  ;
        sc     oreDirector      .a    f    terProblemFactRemo             ve     d(     entityGroup);
            asse   rtScore(scoreD    irect        or,
                assertMatch(solu  tion.getFirstEntity()),
                      assertMa    tch(e        n       tity2));
    }

    @       Over     ride
               @TestTemplate
       public v  oid     i   fExi    sts_1Join1Filter() {
           v   ar solution =    Tes   tdata   Lavish So     lution.generate   Soluti on(2, 5,   1, 1)         ;
        v    ar entity   Gro  up = ne     w  TestdataLavishE               nti    ty  Gr  oup("MyEntityGro     up     ");
          solution.getEnt     i    tyGroupList().add(e          ntityGro     up   );
        var  entity1  = new TestdataLavishEntity(   "      M   yEntity 1", ent   ityGro    up, solut   ion.getFir stValue());
            so   l     ution.getEnt     ityLis  t().add(entity1);
        var entity      2      = new TestdataLavishEnti ty("           MyEnti   ty 2", s olution     . getFirstEntityGr   oup(),
                 solution   .getFirstValue());
                        solut   i    on .get Entity    List().add(entity2) ;
     
        var sco            reDirecto r =  
                buil   dScoreDirector(factory -> factory   .forEach(TestdataLavis hEntity     .cl ass)
                        .ifEx ists(   TestdataLavi    sh  EntityG  roup.class,
                                                  eq       ual(  TestdataLavishE ntity::getEnti    tyGroup,       Function.identit    y         ()),
                                   filter    ing   ((ent i     t  y, group ) -> entity.getC  ode().conta ins("MyEntity")
                                                  || gr    oup.getCode().con   t   ains("M   yEntity")))
                                     .             penalize(Si   mpleScore.ONE)
                                      .asConstrain         t(TES    T_CONSTRAINT_NAME));

                 // From s            cratch
             s    coreDirector.s    etWorkingSol     ution(solut   i     on );
             a      s     sertScore(   scoreDirecto  r,
                                asser    tMatch(enti         ty1)   ,       
                     as    sertMatch (     entity2));

        /  /    Incremental  
                scoreDirector.beforeProblem   FactRemov ed(entityGroup)   ;
            solution .getEntityGroup    List().rem             ove(entityGroup)       ;
            scor  eDirector.afterProblemFactRemoved(e      n  tityGroup    );
        assertScor    e(scoreDirector,   
                                        assertMa tch(entity2));
    }

    @Te     stTemplate
    public void ifExistsOthe  r_1Join0       Fil t    er() {
        var sol      utio         n = Test dataL   av   ishSolution.generateS olution(2, 5, 1, 1);
         var entityGroup = new     Te s    tdataLavishEntit   y   Group("M   yEn   tit     yG  roup");
                      solution.ge   tEnt ityG  r        oupList().add( entityGroup);
              va    r entity1 = new     TestdataLavish  Enti   ty("MyEn     tity                1", entityG  roup,    solu    ti   on.getFirstValue());
        solution.getEntityLi  st().add(enti   ty1); 
               v   ar entity2 = new TestdataLavishEnt   ity(     "    MyEntit    y 2", sol  ution.getFirs   tEntityGroup(),   
                         solution.getFirst       Value());
           solu    tion.getEntityList().add(e    ntity2) ;  

                              var scor e D  ire           ctor =
                      buildScoreDirector(fac     tory -         > factory .forEach(TestdataLavishEntity.class)
                                    .ifExistsOt     her(Te      stdataLavi                shEntity.class, equal(Testdata   LavishEn      tity::get     En   tityGr     oup))
                            .pena      lize(SimpleScore.ON    E)             
                                               .asC      ons  traint(T   EST_CONSTRAINT_NAME));    

        // Fro  m     scratch
               scoreDirect  o r.setWorkingSol           ution(solution);
        assertScore(score    Director,
                            as    sert    Match(solution.get                        First      Enti  ty()      ),
                 assertMat   ch(entity2));  

            // Incremental
        scor   eDirecto   r.beforeProbl   emPropertyChan            ged    (entity2);
        e   ntity2.se  tE   ntityG  roup(entit     yGroup);
                 s      cor  eDirector        .afte rP    roblemPr  opertyChanged(entity2);
        assertScore(s  coreDirector,
                            assert  Matc      h(e     ntit   y1),
                             asse    rt    M         atch(ent         ity  2));
    }

    @Override
    @TestTemplate
    pu         blic v   oid   ifExistsD    oesNotIncludeUn   assigned   () {
        var           soluti  o    n = Testda     taLavishSoluti     on.generateSolution(2,          5, 1, 1);
             va       r entit       yGro   up = new       Te   stdataLavishEnt  ityGroup("MyEntityGroup      ");
                    solutio n.g   etEntityGroup     List()  .a          dd(entit yGroup);
        var entity1 = new Testdat          aLavishEntity("MyEntit              y 1",       entityGroup, sol  ution.getFirstVa   lue());
              solution. getEn     tityList().add(entity1);
                   var entit   y2 = new TestdataLavishEntit   y("     My  En ti       t y 2",   solution.ge   tFirstEntityGroup(),
                           solution    .getFirstValue(   ));
        solution.getEntityList().add(ent  ity2);
            var entity3 = new TestdataLavishEn          tity("Entity         with null var  "   , sol  u  ti     on.getFirstEnt          ityGroup(   ), null);
        so luti     on      .getEn  tityList()     .ad     d(entity3    );

              // both forE     ach   () and ifExists            () will ski            p       ent      ity3,        as it    is not initialized   .  
        var   scoreDirector       =
                     buildScoreDi   rector(factory    -> factory.forE    ach(TestdataLavi shE     nt   ity.c       lass)
                                      .ifExist  sOther(TestdataLavish E        ntity.class, equal(Testda   ta LavishEntity        ::ge   tEntityGroup))
                              .penalize(SimpleScore.ONE)
                                   .asCo  nstraint(T      EST_CON    STRAI  NT_NAME))     ; 

        //  From s    c  ratch
                sco reDirector.setWork    i     n   gSolut ion(solu     tion);
           assertScore(scoreDirector,
                    assert  Match(soluti   on.getFirstEn     ti  ty())         ,
                     assertMatch(entity2));

                       // Incremental    
            score    Director.b    eforeProblemProperty   Changed(entity2);
        ent   ity  2.setE   ntityGroup(entityGroup);
         score Director.afterProblemProperty C   hanged(en   t                  ity2);
             assert Score(scor   eDirector,
                assert    M atch(      entity1)      ,
                    assertMa        tch(    entity2));
    }       

         @Override
    @Te  stTemplate
    @Deprecated(       forRemoval = t         rue)
    public    void i         f Exi       s    ts  IncludesNull   V ar   sW i thFrom() {
                var       s  olution =      T   e   stdataLavishSol   ution.genera     te   Solution(2, 5  , 1, 1);
                 var enti      tyGrou     p = new TestdataLavishEntityGroup("MyEnti       tyGroup");
        solution       .getEntityGroupList().add(ent   ity    G   roup);
        var e   ntity         1 = new Te  stdata    Lavis              hEn  tity("My  Enti  ty 1     ",      entity    Grou      p,    solut ion.getFirstValue());
          s      olution.ge   t  En    tityLis    t(    ).add(enti      ty1);
            var entity2 = new     Testd    ataL avishEntity("MyEnt     ity 2", solution    .getF  irst                      Entit    yGroup(),
                           soluti        on.getFirstV   alue());
        solution   .getEntityL ist().add(enti  ty2);
        var ent ity3 = new TestdataLavi   sh    Entity("Entity   with null var",   solution.getFirstEntityGr     oup(), null);     
                  so    lution.getEntityList().add(entit    y3);

         /    / from() will skip ent     ity3  , as it i        s not        initializ     ed.
              // i      fEx ists     ()     will still cat  ch it, as it ignores    tha   t check.
          var sco  reDirector =
                    buildScoreDir  ector (factory ->     factory.from(T  estdataLavishEntity.class)
                          .ifExistsOther  (TestdataLa  vishEntity.    class, equal (TestdataLavis   hEntity  ::get Entit    y Group))
                                   .penal  ize   (SimpleScore    .ONE)
                                 .      as  Const        rai   nt(TEST_CO    NSTRAINT_NAME));

        //     From scratch
             scoreDirector.setWo    rkingSolution(solution);
                 as   ser       tScore(scoreDirect     o   r,
                assertMatch(solu       tion.getFirst    Enti  ty()),  
                              ass    ertMatch(entity2          ));

             // Incremental
            sco   reDirector.beforeProblem  PropertyChanged     (entity2);
          entity2.setEntity Grou    p(entityGroup);
                scoreDirector.afterProblemProper  tyChanged(entity2);
        assertScor            e(scoreDirector,
                                  a    ssertMat ch(solution.g    etFir                       stEnti    ty()),
                        asse    rtM        atch(ent   ity 1),
                     assertMatch(entity2 ));
    }

        @Overrid   e
    @TestTemplate
    public void i   fN    otExists _          unkno     w  nClass() {
        asse    rtThatThr     ownBy(   () -> buildScoreDirector(factory -> fa   ctory     .forEach(Te  stdataLavi    shValueGroup.c    lass)
                        .ifN  otExist      s(Integer.class      )
                   .penal i   ze(SimpleScore.ONE)
                            .asConstraint(T      EST_CONSTRAI      N    T_NAME)))
                    .isIns tanceOf(I    llegalA      rg   u     me  ntException.cla  ss)
                   .hasMessageConta   ining(Int      eger .cl   ass.getCanonic        alName())
                         .has M    essa geCon      taining("as  s    ig nable from");
          }

                  @Override
    @TestTempl        ate
         publi      c void    ifNotExists_0Joiner0Filter()   {
          var sol     ution = TestdataLavishSolution.g       enerateSol utio    n(1, 1, 1, 1);
        var valueGroup = new Te     stdataLavishValueGroup("   MyValueG       roup");
            solution.g   etVal ueGroupList().  ad    d(valueGro     up);
             var entityGroup = new TestdataLavishEntityGro up("MyEn   tityGr     oup");
        s     olution.getEntityG    roupLis      t().add(e       nt   ityGroup);
  
            var  scoreDirector     =
                          b  uildScoreDi rector(factory -> fa      ctor  y   .for   Each(Tes  tdataLavishValueGrou    p.cla    ss)
                                   .ifNotExists (Te   stdat    aLavi       shEntityGr   oup.class)
                                  .   penalize(SimpleScore.O NE)
                                       .       asConstraint(        T      EST_CONSTRAINT_NAM E     ));

             // F      rom scratch
        scor               eDirector.se      tWorkingSolution(solut  ion)    ;
        asser  tScore(s     coreDir    ector);

             //    Increm   ental
        scoreDirector. beforeProblemFactR emoved(     ent        ity   Group)    ;
          solution .getEntityGroupL     i   st().   remo ve(entit  yGroup);
            s                        coreDirector.   afterProblemFactRemoved(e  ntityGroup);
                 as            sertScore(sc     oreDi    rector);
    }

    @Overr ide
               @Tes       tTemplate
    pu   blic void ifNotExist s_0Jo   i     n1Filter() {
                                  va   r   soluti         on = T    estd    a  taLa    vis           hSoluti     on.      generateSolution(2, 5, 1, 1);
           var entityG   ro    up = new TestdataLavishEntit   yG   roup   ("    MyEnt    i   tyGroup");
         so  lution.getEntityGroupLi   st().add(entityGroup);
              var enti                  ty1 = new TestdataLavish       Entity("MyEntit  y          1", en  tit yGr  o  up, solution.        getFir    stValue(  ));
              s olution.getEnt  ityLis t  (     ).add(entit    y1)     ;
           v     ar entity2 = new Testdata La    vishEntity("MyEntity 2"   ,     soluti  on.ge  tFirstEntityGroup (),
                         s               olut    ion.getFirstValue());
         solu    tion.ge  tEntityList().add(e   nti ty2);

                   va       r scoreDirector =
                   buildSc       or  eD  irector(factory -> fa ctory.forEach   (Testda   taLavishEntity.class)
                            .ifNotExis   ts(Tes               tdat   a            Lavi   shEntit   yGroup.class,
                                                    filt  ering((en     tity, gro  up) -> Objec   ts.equals(entity.getEntityG   roup()  , gro    u    p)))  
                                            .penalize(Simpl      eSco     re.ONE)
                           .asConstraint(T      EST_CONSTRAINT_N  AME));

           // Fr         o   m scratch  
           s  c   oreDir     e    ctor.         se tW    ork            ingSolution(solution);
                  a     ssertScore(scoreDirector);   
     
        //    Incremental
         scoreDire             ctor.beforeProblemFactRemoved(entityGroup);
          sol   uti     on.getEn    t    i      ty   Gr   oup    List().r   emove(entity Gro  up)  ;
             scor    eDirect        or.af     terProblem      FactRemov   ed(entityGroup);
        assertScore  ( sco  reDirector,
                            asser   tMatch(ent   it      y    1)        ) ;
        }

    @Ove    rride
    @TestTemplate
      public  void if     NotExists_1        Join0Filter() {   
              var  s   olution =       T  estd    ataLavishSo       lution.generate    Solution(2, 5, 1    , 1);
               v   ar entityGroup      = new   Test    d    ataLavi    shEntityGr        oup(  "    MyEntityGroup");
              solution.getEntityGrou   pList().add(  entityGroup);
                v   ar entity1     = new Testdat     aLavishEntity("MyEntity 1", en    tityGroup  , solution.ge  tFirstValue()       );
            sol  uti      on.getEntityLi  st()  .a dd(entity1);
        var entity2 = ne    w T      est   dataLavi     shE    ntity("     MyEnti   ty 2",    solution        .ge     tFirstE  ntityG r  oup(),
                      solutio   n.getFirstValue(    ));
           solution.getE        ntityList().add(   e    ntity2);

              var scoreDi rector =    
                            buildScoreDi       rect or(factor        y ->    factory.forEach (Tes    tdataLavis    hEn  tity.class)
                                              .ifNo tEx            ists(T      estd       at       aLavishEnti  tyGroup.class,
                                equ          al   (Testd    ataL avishEnti        t     y      ::getEnt  ity      Group, Function.identity()))
                         .p  en   ali z e(SimpleScore.ONE)
                                     .asConstrain t (TEST_C  O  NSTRA INT_NA  ME));

        // From scratc            h
         scoreDirect or.   setW orkin     g Solution(sol     ution);
        assertScore(scoreDirector);

         // Inc     r          ementa   l
                        scoreDi   rector.be    forePro blemF   actR emoved(en    ti        t        yGroup);
        so       lution.getEntityGroupList().  r  emove(en   tityGroup);
             sco    reDirector.afterProblem      F  act  Rem  oved(entityGr oup);
        a    ssertS core(score D  irector,
                       assertMatch(entity1));
        }

    @Overrid       e
    @Te        stTemplate 
            public v    oid ifNotExists_1Join  1Filter() {
                var   soluti           on             = TestdataLavishSolution.generateS olution(2, 5, 1, 1);
         var e      ntityGroup =      n       ew  Testd     ataL     avishEnti                tyGroup("MyEntit      yGroup   ");
            sol     ution.ge     tEntityGroupList().add(entityGroup);
          var    e   n       tity    1 = new T   estdat    aLavishEnti      ty("MyEn      tity 1        " , entityGroup        , solution.getFir             stValu     e())    ;
           solu     tion.ge tEntityLi st().add(entit       y1);
        var entity2 =     new T     estdataLavish    Entit   y("MyEn tity 2", so  lution.get              First    E   ntityGr    oup(),
                      soluti   on.getF  ir    stValue());
                         solution. getEntityList().add (en   tity2);

         var scor     eDirec  tor             =
                b  uildS   cor   eDirector(factory -> factor    y.forEach(Te      stdataLa  vishEntity.cl  ass)
                           .ifNo   tExists(Testdata  Lavis hEntityGroup.cla        s s,
                                       equal(Testdata    L   avishE  ntity:    :getEntityGroup, F  unction.ide  nti     ty(   )     ),
                                                        fi   lteri    ng((entit  y , group) -> entity.    getCode().conta       in      s("MyEntity")
                                                            ||  group.getCode().contai ns    ("My  Entity")))
                              .penalize(SimpleScor   e.ONE)
                        .a  sCon   stra       int(   TES  T_CONSTRAINT_NAME));

        // From scrat  ch
                 sc      or    eDi     rector.setW orkingS  olution(solution);
             asse       rt  Sco  re(scoreDirector,
                  assertMatch(sol ut       ion.get   FirstEntity(  )));

            //   I   ncr        emental
           scoreDirec   t      or. befo   reProblemFactR  emoved(en    tityG   roup);
            solutio  n.ge tEntityGro     upList().      remove(ent  ityGroup);
            scoreDirector.  a   fterProblem     FactRemoved(entityG   roup   );
              as      sertScore(sco   reDi   rector ,
                 asse    rtMat  ch(solut   ion.getFirstEntity()      )   ,
                    as ser      tMa    tch(ent           ity1));
    }

      @Override
            @TestTempla  te
      public void i    fNotExistsDoesNo          tInclude     Una  ssigned( ) {
        var    solution = TestdataL     avishSolution. ge    nerateSo lu tion(2  ,             5, 1, 1);
             var   entityGroup =     new T    estdataLavishEntit    yGroup(         "MyE    ntityGroup");
        s      o    l  ution.getEnt    ityGrou pList().add(entityGr      oup);
                      v ar entity1 = new Test               dataL avishEnti      ty(   "MyEntit    y 1", entityGroup, solution.getFirstValue());
                      solution.get  Entity     List().add      (entity    1);
                var entity2 = new Testdat            aLavishEntity("Entity wit  h null var", solu  tion.getFirstEntity    Gro      up(),   null);
               s   ol      ution.getEntityLi   st().add(entity2);

        var scoreDirector =
                bu        ildScoreDire          ct      or( factory ->   factory.forEach(T    estd   at  aL   avishEntit    y.    class)
                              .ifNotExists    Other(TestdataLavishE  ntity.class, eq    ual(Tes   td ataL avi    shEnt  ity:   :getEntit   yGrou                  p)     )
                                    .pe          n       alize(       Simp  l   eScore.ONE)
                                     .as   Cons       t   raint     (TEST_CO    NSTR        AINT_NAME    ));

        // From scratch
                    sc           or   eDi   re ctor.se    tWo         rkingSolutio     n(soluti     on);
        asse   rtScore(scoreDirecto  r,
                  ass  ertMatch(solution.ge   tFirstEnt   ity()),
                           assertMa       tch(entity1));

          // Incremental
          s      coreDirector.beforeProb         lemPropertyC    h anged(    entity2   );
               entity2.setEntity      Group(entityGroup);      
                   scoreDirector.after  P   roblemPropert       yChanged(en  t   ity2)   ;
         ass  ertSco     re(sco        reDir     ector,
                assert         Match(solution.getFirs  t Entity()  ),   
                   as  sertMa       tch(entity1));
    }

    @ Ov  erride
    @Tes              tTempl     ate
       @Deprecated(for       Remov        al = true)    
    public     void ifNotExistsInc        ludesNul    lVarsWithFro        m   () {
                    var solutio n = Test   dataLavis    hSolution.generateSolution(2,   5, 1, 1);
                var ent i      tyGroup =   new Testdat   aLavishEntityGrou   p    ("        My    En  ti         ty  G     roup");    
        s  olution.getEntityGroupList().    a dd(entityGrou   p);
           var entity1 = n  ew Te stdataLavishEnt              ity("M       yE            ntity 1"   ,   enti    t yGrou p, solution.g          etFirst   Value());
              solutio     n.g  etEntityLis      t().add(  en      tity1);         
        var entity2 =    new    TestdataLavis   hEntity("Entity with null va  r", s    olution.getFirstEntityGroup(), null);
        solution.getE     ntityList(). add(ent ity2);   

        va  r   s     cor   eDi  re ctor =
                      buildScor          eDire   ctor(fa    ctory -> factory.from(Testd        a ta   Lavi   sh   Enti  ty.class)
                                        .ifNotExist  sOther(Testd   ataLavis     hE      ntity.c     l   ass, eq ual(Testda taLavishE               ntity::getEnt i  ty          Grou    p))
                                       .p   ena   lize(      Simple    Sco    re.ON         E)
                                .as          Constrai  nt(TEST_C      ONSTRAINT_NAM   E));

              // Fro           m scratch
        scoreDirector.s         et    WorkingSoluti     on(solu     ti   on);
        assertScore(sco     reDi recto  r,
                     assertMatch(entity1));    

        /              / Incr  ement  al
        s  co   r   eDirector.b    e   forePro blemPro pertyCha  nged(entity2);
        enti  ty2.setEnti     tyGroup(enti   tyGro    up);
             scoreDi   rector.        afte   rProblemPropertyCha ng    ed(ent   ity2);
        assertScore(sco    reDirector,
                   a  ssertMatch          (       solut  ion.getFirstEn   tity     ()))    ;
    }

         @TestTemplat    e
    pu       blic void ifNotExis   tsO         ther_1     Join0Filter  ()   {
                 var solution = Testd      ataLav      ishSol    ution.ge  nerateSoluti on(2, 5, 1 , 1);
            var entityGr  oup = new Testdata   La vishEntityGr     oup("MyEntity      G  roup    ");
          sol ution.   getEntityGroupList().add(    ent       it y     Group);
           var entity1           = new Te stdataLavishEnt i     ty("MyE  ntity 1", entity    G   roup, solu   tio   n      .getFirstVal   ue())    ;
                           solution.getEntityL  i    st().add(entit       y1);  
         va  r entity2 = new            T   estdataLavishEntity("MyEntity 2", solution.getFir stEnti tyGroup    (),
                s olutio   n.    getFirst  Value());
             solutio n.getEntityList().add(e     n   tity2);

          var s       coreDirector =
                      build   ScoreDirector(factory -> fact    ory.forEa       ch(TestdataLavishEnt          ity.class)   
                                     .if      No  tE xi      stsOther(TestdataLa  vishEntity     .class,   equal(T     estdataLavis   hEntity::getEnt  ityGroup))
                                           .pe nal     ize(SimpleSc ore.ONE)
                              .a    sConstraint(TEST_CONST         RA    INT_   NAME));
      
             //            From scratc   h
                       scoreDir ector.setWorkingSolution(solution);
                   assertScore(scoreDirector,
                  a      ssertMa    tch( e  ntity1));

        // Incre    ment  al
         s   coreDirector.beforeProblemProper    ty Chan      ged(en   tity  2);
          entity2.set    EntityGroup(entit      yGroup);
          scor     eDirect    or.aft   erProblemPropertyCha      nge d(entity  2); 
          assertS    cor  e(scoreDirect     or,
                assertMatc            h(solu    tion.getFirstEntity()));
      }

       @Overrid    e
           @TestTemplate     
      p  ublic v  o   id ifExists       AfterGrou   pBy() {
                         var sol   ut   ion =     T  e    stdataLavishSolution.gene         r     ateSo  lution(1, 0, 1,   0);  
        var value  1 = new TestdataLavishV alu      e("MyValue 1", solution.getFirst       ValueGroup()  );
                 s     olution     .          getValueList().add(val       ue1)      ;
        var      v    alue2 = new    Testdat            aLavi  shV    alue("MyValue 2 ", solution.getF   irst    Valu eGro up());
         solution.getV     alueList().add(value2);
                 v   ar     entity1 = new   TestdataLa   vishEntity("M  yEn  tity          1", so lution.getFirstEntityGroup(), value1);
        solution.getEntityL    ist().add(entity1                     )  ;
         var      entity2 = new     TestdataLa   v   i  shEntity("MyEntity 2", so  lution.getFi rstEnt  ityGroup(), value1)    ;
           solution.getEntit   y     List()   .ad             d(e             nt  ity2);
          var     e      xt r      a1       = new Testdat   aLavish Ext     r   a   ("MyExtra 1");  
        solution.getExtraLis        t().add(extra1);
          var ext  ra       2 = new TestdataLavishExtra("MyExtra   2");
           solution .getEx     traList().add(extr    a2);

                 var scoreDirector =
                       buildScore D     irector(fa     ctory -    >   factory.forE    ach(Test     dataLavishE     ntity.class)
                           .gr         ou     pBy(countDi   sti    nct(Te        stdataL     avishEntity::g     etValue))
                                           .ifExist s(Testdat   aLavishEx     tr a.   class)
                                         .pe  nalize     (S impleS      c ore.ONE)
                                   .asConstraint(TES   T_C ONSTR           AINT_NAME))             ; 

                   // Fr om scra    tch
         scoreDirector.setWorkingSol           utio n(solution);
               assertScor   e(scoreD        irector,
                  a ssertMa                  tch(1 )   );

        / / Inc     remental
          scoreDirector.bef    oreV  ariableChanged(entity2,    "va  lue     ")   ;
                entity2.setValue(value2);
                 scoreDir  ector.afterVariableC   hanged(en       tity2, "value"        )  ;          
         a      ssertScore(scoreDire ctor,     
                     a sser        tMatch(2));

        // Incrementa       l
                   scoreD       irector         .beforeEntityR  emoved(e   nt ity2);
        solution.getEntity  List().remove(entity2  );
        sc       o re   D irect     or           .afterEntityRemoved(   entity2);
           assertScore        (s  coreDire                 ctor,
                     asser       tMatch(     1));
       }    

       // **********    **  *****  *  *******************************    ************   ***********
                /     / For Each
       //  **      ******   **************          **    *      ******  *                 **          *******  **              **  *  **    *   ****   *******************

    @    Te     stTempla  te
       public void forEach_unkn   o    wnC            las   s()       {
        asser  tT    hatT   hrow                  nBy(() -> buildS  coreDirect or(factory -> fact   ory.for    Each( I nteger.class)
                       .penalize(S   impleScor  e.ONE   )
                        .asCons  traint      (TES        T_CONSTR   AIN         T     _NAME))         )
                 .isInstanceOf(IllegalArgumentException. class)
                          .hasMessa  geCon       tai    ning (Intege r.cla       s    s.getCanon   icalName())
                .hasMessage    Co   nta       i    ning("as  sig  nable from"); 
        }     
                 
      @Tes tT  em     p    l         at     e
            public vo id forEach_po     lymorphis   m()        {
               TestdataSolut   ion solution =     new Te     stdataUnannotatedExtendedSolution();
             var       v1 = n    ew Testdata     Value("v1");
           var           v2 = new Testdat   aValue("v2");
        solution.setValueList(List.of(v1, v2));
                    var cat     = new Testdat   aUn annotatedExtendedE         ntity ("Cat", v1);
         v  ar anima   l = new Te  s  tdataEntit   y("  Animal", v     1           );
              var dog = new     TestdataUnannot  atedE   xtendedE    ntity("Dog", v1);
        s   olution.setE   n   t         ityList(  List.o    f(cat , animal, dog));

          InnerScoreDirector<Tes      tda ta  Sol     ution, SimpleS     core> sco         reDi    re   ctor = buildScor       eDirect  o                      r(
                             TestdataSolutio n.bu       ildSoluti  onDesc      riptor( ),
                      factory -> new C   o       nstraint[  ] {
                            f  actor  y.forEach(Testd   at    aE          n    tity.class)
                                                       .pen  al    ize(SimpleScore.ONE)
                                                  .asConstraint("superclas  sCo      nstraint"),
                                 factory.forEach(TestdataUnannotate      dEx       tendedE      ntity   .c    lass)
                                                        .penalize(SimpleScore  .ONE)
                                                   .asConstraint("su   bclassConstraint")
                               });

            // From sc  r    atch
            scoreD   irect  or.setWorki           ngSo     lution(solutio         n);
        as             sertS    c   ore(scoreDirecto  r   ,   
                              assertMatch("  superclassC     o     nstr  aint",      cat),   
                   assertMatch("supercla           ssCo ns traint"        ,     animal        )  ,
                    assertMatch("supercla   ssC   onstraint ", dog)       ,
                   assertMat  ch("s      ub  cl  assC      onstraint", ca  t),
                   ass     e   rtMatc  h("s      u      bclassConstraint", d   og));
    }

    @TestTemplate
            public         void for     Each          _b  asicV    arUnin     i   tial ized() {
        var   solution   = new Te   stdataAllow sUnassig nedSolutio n();
          var v   1 = ne w    Testdata      Value  (    "   v1");
                    var v2 =    new T   est d   ataValue("v2")         ;
        solution.   setValueList   (List.of(v 1, v2));
                var e1 =    new Testd  ataAllo   wsUna  ssi   gnedE    nti     ty("  e1", v1);
           var e2 = n  e  w TestdataAl   lowsUnassi gnedEntity(   "e2",  n    ull);
         soluti   on.setEntityList(List.   of     (e1, e2));

           InnerScoreDir         ector<TestdataAllowsUna      ssignedSolution, S  impleScore> scoreDirect   or   =    buildSc         oreDi    rector  (
                TestdataAllowsUnassignedSolution.bu    ildSolutionDescript or(),
                   factory -> new Constraint[    ] {
                               fact   ory.forEa  ch  (Te  stdataA      llowsU   nassignedEn         tity.cla        ss)
                                       .penali    z   e(Si  m pleScore.ONE)
                                                             .asCons traint(TEST_CO NSTR AINT_N  AME)
                     });  

             // From      s        cratch
         scoreDirecto  r.    setWorking  Solution   (     solution) ;
        assertScore(scoreDirector,
                   assertMatch(e1));
 
            // Increment     al
         s    coreD  irect  or.before       V   ariableC             hanged(e2, "val  ue");
             e2.se    tVa lue(v2);
        scoreDirector.afterVariableChang ed  (     e2, "value  ");
            scoreDi        r  ector.  setWorki         ngSolution(solu     tion);
        as   sertScore(scoreDire     ctor,
                           assert     Match(e1  ),
                   assertM     atch(e2));

        sc         oreDirector.beforeVariableChanged(e1, "value");           
        e  1    .setVa     lue(null);
                   scoreDirec  tor.aft  erVariableChange    d(e1, "value"   );
           scoreDirector.  s   etWo  rkingSol       u     tion(soluti         on);
        as     sertScore(scor    eDirector,
                    assertMatch(e2))     ;
      }

    @TestTempla   te
       public vo  id  forEa ch_list    Va  rNot      AllowsUnassignedVal   ues()    {
        var s    olut    ion =     new    Tes tdataL      i   s   tSolu tion();
        var     v1 = new Tes  tdataLi stValue(   "  v1");
               var   v2 =   n   ew     Testdata      ListVa  lue        ("v2");
        solut      ion.setValueList(List.of(v1, v2));
        var  e1 = new TestdataListEntity("   e    1"   , v1);
          v1.s      etE  nti ty(e1);
                    v1.setInd       ex          (0);
           var e2 = ne     w Testda t  aListEntity("       e2");
        solution.set En       tit    yList(List.of(e1, e2));

             I    nn  erScoreDirector<Tes           tdataListSolut    ion  , Simple    Score> scoreDirector    = b        uildScoreDirector(
                             Testd     ataListSolution.bui   ldSolutionDescr   i pto   r(),
                    factory ->    new Co     nst   rai  nt[] {         
                            fact      ory.forE       ach(Tes  tdataListV  alu   e.class)
                                                       .penalize(           SimpleScore   .ONE)
                                         .as C     onstraint(TE S    T_CONSTRAINT_N   AME)
                    });  

           // v2 is not   assigned, so i t shou  ld not be matched
        scoreDirector.setWo    r  kingSolution(s olution);
        asser     tScore(scoreDirector,
                                 as sertMatch(v1));

        // Increm  ental
        sc  oreDirec  tor.beforeListVaria      bleChan  ge    d(e    2, "valueList", 0,    1);
               scoreDi rector.before   ListVariab    l      eElementAssig  ne  d(e    2, "valueList" , v2);
        e2.getValueL   ist().a    dd(v2);
             scoreDirector   .afterL  istVariableElementA  s  signed(e2            , "valueList", v2);
             scoreDirector.afterListVar      iableCha        nged(e2,   "valueL    ist", 0, 1);
           assertScore(score    Director,
                assertMatch(  v1),
                   assertM atch                 (v2));

           scoreDir       ector.beforeListV    ariableChanged(e1,   "valueList", 0, 0);
          score  Director.be   foreListVariableElemen        tUnassigned(e1, "valueLi         st         ", v1);
                     e1.g     et    ValueList()   .clear  ();
         scor         eDirector.  afterList Var  iableElement      Unassign    ed(e1, "valueL ist",     v1)     ;
           scoreDi  rector.af           terList    Var   iableChanged(e1, "valueList  ",    0, 0);                        
        as  sertScore(sc oreDirecto  r,
                    assertM   atc     h(v2))       ;
    }

    @TestTemplate
    p  u   blic void for  Eac   h_listV     ar      NotAl l   owsUna   ssigned  Values_no Inver  seVar()  {
           v  ar solu    tion = new TestdataPinnedN o   Shad   ows         ListSoluti     on();
                    var     v    1   = new  Te     stdataPinnedNoShadowsListValue("v1");
               var v2 = new TestdataPinnedN      oShado   wsListValue("v2");     
             solution.setValueList(      List.of(v1, v2));
                   va   r e   1 = new TestdataPinned NoS   hadowsListEn                 ti      ty("e1   ", v1);
             v1.setIndex(0);
                                   var e2 =       new TestdataPinnedNoShadowsListEnti   ty("e2");
                  solution.setEnti     tyLis      t(List.of(e   1    ,   e2));

        I      nnerScoreDi       rector<TestdataPinnedN  oShadowsLi   stSolution, SimpleScore> scoreDirector       = bui ldSco    reDirector(
                      T estdataPinnedN  o ShadowsLis           tSo  luti    on.buildSoluti   onD     esc  riptor(),
                                   factory  -       >      ne      w Co        nstrain     t[] {   
                                    fa   ctory.fo  rEach(T   estdataPinnedNoShadowsListValue. cl ass)
                                                        .penaliz        e(SimpleScore.ONE       ) 
                                    .asConstraint(TE ST_CONSTRAINT_NAME  )
                      });

            //   v2 is     not assign   e d, so it should not be matc   hed
        scoreDirect      or.setWorkingSolution(solution);
         ass    ert  S  cor e(s           coreD     irector,
                     as     sertMatc       h(v  1));

         // Inc rementa     l
                  sc  ore Dir       ector.beforeLis   tVariableChan     ge  d(e2, "valueLis  t", 0, 1);       
              scoreD    irect  or.b  efo reListVariableElementAssigned(e2, "valueLis       t", v2)  ;
        e2.get    ValueL     ist().add(v     2);
        score    Director.afterL    ist    Variable         ElementAssigne   d(e2, "v    a  lueLi      st", v2);    
           scoreDirec  tor.af  terListVariableC    hanged(e2, "val    ueList", 0, 1);
            asser    tScore(sc     oreDir ector,
                               ass     ertMatc     h(v1),
                      asse    rtMatch(        v2));      

               scoreDirect  or.beforeLis  tVariabl eChanged(    e1,       "val    u e    List", 0, 0);
           sc o   reDirecto r.beforeListVariableElementUnassign  ed(e1,  "valueList", v1);
             e1  .getValue     List().c lear();
           scoreDirector.afterListVaria    bleElementUnassigned          (e1, "valueL     ist", v   1);
        score   Director.af terListVariabl  eChanged(e1, "valueList", 0, 0);
            assertScore(s   coreDirector,
                            assertMatch(v2)   );
         }

    @TestTe mplate
    pub    lic void forEach_list       VarA      llowsUnassigned   Val      ues        () {
                      var solution = ne     w TestdataAll o      wsUnassig nedValuesListSolution();
                var v1 = new Testdata    Al   lowsUnas signedValuesLi  stValu e("v1")   ;
               var v2 = new T   estd      ata  A         llowsU       nassign   edValues   List      Va       lue("v2");
          s  olution.    setValueL      ist(List.of(v      1 , v 2));
                   v    ar     e1    = new Tes   tdataAllowsUnassi          gne      dVa   lue          sLis   tEnt ity("e1", v1);
              v1.se  tEntity(e1)   ;    
        v1    .s   etIn  dex        (0  );
        v   ar e2      = new Testd                  ataAllowsUnas  si   gnedValuesLi     stEntity("e2"   );
        soluti           on.   setEnti tyList      (List.of(e1, e2)) ;

           Inn    erScoreD  irecto     r<TestdataAllowsU   nass       igned    ValuesLis tSolution,    Simpl    eSco  re     > scoreDirector = bui        ldScoreDirector(
                            TestdataAllowsUnassignedValue    s        ListS         olution.buildSolutionDescriptor(),
                                fa  ctory ->      new Const  raint[] {
                             factory.forEa   ch(Tes   t       data  All     ows                UnassignedValuesListValue.clas     s   )  
                                .penalize(Si          mpleScore.ONE)
                                         .as Co     nstra i nt(TEST_CONSTRAINT_NAME)
                        });

               //    v2 is n                          ot assigned, so   it   should  not be ma    tched
          score      Direct    or   .setWorkingSoluti  on(so      l  uti   on   );
          a  ssertScore   ( scoreD    irector,
                                 assertMatch(v1));

              // Incr      emental
               sc    oreDi   rector.beforeListVariableChanged(e2, "valueList", 0,  1)       ;
                  sc     or eDir  ecto   r.beforeListVariableElem    entA  ssigned(e2, "valueList", v2);
          e   2.  getVa     lueList()    .add  (v2);
                  score  Director.     afterList VariableEl   emen    tA ssigned(e2   , "valu eLi st  ", v2);
        scoreDirector.afterListV              ariab   leChanged(e2, "valueList       ", 0, 1);
                   assertScore     (scoreDirector,
                a     s   s   ertM atch(v1),    
                a s   sertMatch(v2));

                  s    co       reDirect    or.    beforeListVariabl   eCh  ang    ed       (e1, "   value             List", 0, 0);
        scoreDir          ect            or.beforeListVariab       le  ElementUnassigne d(   e1, "valueList", v   1);
             e      1.getV             alu       eList().clear();
          scoreD    irector.afterLi                 stVariableElementUnas  si    gn ed(e1, "valueList  ",     v1);
               scoreDirector.after     ListVariabl  eC hanged(e     1, "valueList",   0, 0);
         assertS co    r   e(scor        eDirect   or,      
                     assertM   atch(v2    ));
    }   

    @TestTemplate
     public void forEa      chIncludingUnass           ig     ned_basicVarUnin     itialized() {
                 va   r       sol    utio     n = ne   w Tes tdataAll  owsUn     assignedSolu    tion() ;
            var   v1 =   new TestdataV alue("v1");
        va r v   2 = new TestdataValue("   v2 "    );    
               solu   tion  .s    etValueList(Li       st.of   (v1, v2))       ;    
        var e   1          = new TestdataA    llowsUnassignedEntity("e1", v1);
            var e2 = new    Tes       tdataAllowsUnassignedEntity("  e  2", nul     l)  ;
              solution.   se     t    EntityLi st     (List.    of(e1,     e2));

        InnerScoreDirector<TestdataAllowsUnassignedSolution, SimpleScore> scoreDirector = bu  ildScoreDire   cto     r(
                      TestdataA           llowsUnassign edS          olu  t  ion.buildSolutionDescriptor(),
                                 fa     ctory  -> ne w Constra int[ ] {
                                factory.forEachIncludingUnass    i   gned  (TestdataAllowsUna   ssignedE         ntity.class)
                                                    .       pe nalize(SimpleScore.ONE)
                                 .  as    C  onstraint(TEST_CONS   T  R   AIN  T_NAME)
                });

                       //           From scratch
        sco r          eD            irector    .setWorkingSolution      (solution);
        assertScore(scoreDirector,
                assertMatch(e1),
                        a    ssertMatch(e  2 ));
    }
    
           @TestTempl    at   e
    public vo     id forE   achInclud    in   gUnassi   gned_listVarNotAllowsUnassignedValues() {
        var solution = new  TestdataListSoluti on();
           var v1 =   new Testd  ataListValue("v1");
           var v2    =  new T  estdat    aL   i  stVa      lue(      "v2");
                     solution.se   t                  Val  u     eList(List.of    (v1, v2));
            va      r e1 = new TestdataListEntity("e1", v1  );     
                   var  e2 =        new Testd   ataListEntity("e2");
                     solutio    n.setEntityList(List.of(e1    , e2));

                        InnerScoreDi  rector<TestdataL  istS     ol    ution, SimpleScore> scoreDir           ector = buildS             co     re   Director(
                                       Testda    taListSo                      lution.buildSolutio nDescri      ptor(),
                fa    ctory ->  new Con   stra  int[]   {
                                                   factor  y.forEachIncludingUnassigned(Test dataListValue.class)
                                    .penalize(Simpl  eScore.ONE)
                                      .asConstraint(TEST_CONSTRAINT_NAME)
                  })     ;

        //       Even tho   u gh only      o      ne   value         is assigned , both       are matched.
              scoreD irect  or.setW  orkingSolutio n(solution);
           asse      rtScor   e(s           coreD     irect or,
                               a  ssertMa   tch(v1),
                           assertMatch(   v2))   ;

    }

    @TestTemplate
    public v   oid forEa   chInclud   ingUna   ssigne   d_          listVarAl   lo     wsUnassignedValue       s()     {
        var solution = new Testd  ataAllowsUnassi    gnedValu  esListSolution();    
           var v1 = new Te stdataAllowsUnassig     nedValuesListV     alue("v    1");
            v ar v2    = n     ew Testda               taAllowsUnassignedVa  luesListValu       e("     v2");
         s olut     ion .setValueList(List.of(v1, v2   )); 
        var          e1 = new Testd       at     aAllowsUnassignedValuesListEntity    ("e1", v1);
         var e2       = new        Te st     d  ataAllowsUnassignedValuesLi         stE     ntity("e2");
            v1.se   tEntity(e   1);
        v1.setIndex(0);
        solu        tion.setEntityList(List.     of(e1, e2));      

          In     ner     Sc   oreD   ire   ctor<Testda  taAllo      wsUn           ass ignedValue sList  Solution      , SimpleScore  > scoreDi      rector = buil    dScoreDire  cto    r     (
                              TestdataAllowsUnassig     nedValues     ListSolu   tion.buildSolution       Descriptor(),
                               factory  ->      ne   w Co nstrai nt     [] {
                                        fa     ctory.forE    achIncluding  U n            assigned(   Test   d  ataAllowsUnassignedVa    luesL     i      stValue.class    )
                                             .penal    ize(SimpleScore.ONE)
                                          .asConstrain   t(TEST   _CO    NSTRAINT_NAME)
                   });

          /    / Even      though only one value is assi    gned, both  are matche     d.
        sc   or e    Direct          or    .   setWor   king        Solut   i on(solution);
        assertScore(sc     o reDir              ector,
                                           assertMatch(v1),
                   as  sertMatch     (   v2      ));

       }

    @TestTemp   late
    public      vo   id      forEachUniquePair_0()     {
        var so    lutio  n =                       Te stdataLavis hSol                utio   n.generateSolution(2,  5, 1, 0);
                      var entityB = new TestdataLavishEntity("B",     s    olution.ge   tFirstEntityGroup(),
                     solution.  getFirstValu  e());
        solution.getEnti    tyList().ad       d(entityB);
        var entityA = new T   est      da     taLavishEntity("A", solu   tion.getF irstEntityGr   oup(),
                      solution.ge   tFirstValue());
               so       lution.getEn  tityList().add(entityA);  
                     var en    ti    tyC = new Testda    taLa vishE   ntity("    C"       , solution.getFir        st    Entit       yGroup(),
                     solution.getFirstValue())      ;
          sol    ution.  get                 EntityList().add(ent  i   ty     C);

             var scoreDire    ctor =
                    buildSco      reDire ct     or(f              actor  y -              > fac tory.forEachUniquePair(TestdataLav  ishEntity.c  lass)
                                             .penalize(Simpl    eScore.ON     E)   
                             .a   sConstraint(TEST_CONS   TR AINT   _NAME));
   
        //   From sc            ra         tch
        scoreDirector.setWorki            ngS      olutio       n    (solu  tion);
              assert    Score(scoreDirecto      r,
                       as   sertMatch(entityA,          entityB),
                               a  ssertMatc  h(e        ntityA, entityC),
                  assertMatch(entityB,         entit       yC));     
       }
   
    @Te      stTe    mplate
    public      vo  id forEachUniqu   ePair_1E   quals() {   
        var solu  tion =   T          es       tdataL   avishSolution   .generateSolution(2, 5, 1, 0);
            v  ar entity                 B = new Test   dataLavishEntity("B", s   olution.         getF  ir stEntityGrou p(),
                           s olution.ge    tFirstValue     ());
        ent     i tyB.s    etIntegerP   r  op        erty(     2);
                   so  luti   on.getEntityList().add(entityB     );
        va    r entityA =  new TestdataLa    vish    Entity("A",      s   olutio  n.getFir    stE   ntityGroup      (),     
                               solution.ge  tFirstValue());      
          entityA.setIntegerP      rope  rty(2);
        s  olution. getEntityLis   t().add   (entityA);
            var    entity     C =      new Tes    tdataLavishEntity("C", so      luti    on.getFirstEntityGroup(),
                 solution.getFirstV   alue()  );
        entit  yC .se            tInt    egerPropert              y(10    );
            solution.g       etEnt  i    tyLi    st().add(entityC);
 
        var          score    Director =      buildScoreDirector(factory -> factory
                         .forEachUniquePair(TestdataLavi  shE         ntity.class,        equal(     Te stdataLavishEntity::getInt egerP   roperty))
                  .penal  ize(SimpleScore.     ONE)
                         .asC    onstraint(TEST_CONSTRAINT_NAME));

             // From sc   ratch
        scoreDi     rector.setWork       ingSolution(so    lution);
               assertScore(   scoreDirector,
                a  ssertMa tch     (entityA,    entityB));

        // I     ncremental
          scoreDirector.b  eforeProbl emProp  ertyCh    anged(entityB      )   ;
        ent    it     yB.setIntege  rPro     perty(   10);
        sc     o reDirector.    a  fterProblemPropertyChanged   ( entit     yB            );
         asser    tS   core(scor eDir   ector,
                                asse         rtMa        tch(entityB, e ntityC  ));
    }

    //                          ***   ***********    *********    ****       *    ********************    ********     ********    *****     ***
    // Group by
    // *********   ***** ***  **** *** *******   ***********  *****      ********  *****************

     @T  estT    emplate
    p   ubl   ic void gr    o  upB y      _1Ma     pping0Col  lect_filtered() {
        var solution =          TestdataLavi    shS     olution.g    ene   rateSolution(2, 5, 1,  7 );
           var entity  Group1 = new Test     dataLavi    shEnt ityGro      up("MyEntityGroup");  
                 solu       tio          n.g  etEntityGroup      Lis   t(        ).   add(e     ntityGroup1);
            var entity1   =        new Test    da      ta     LavishEntity("  MyEntity        1", entityGrou   p1,    solution.getFirstValue(  ));
        soluti   o  n.get   E n        tityLis    t().     add(enti  ty1);
             var e  nt    ity2 = new Testdata    LavishEntity(   "My    Entity             2",    entityGroup1, so   lution.getF   irstValue());   
              solu tion.getEn    tityLis t().add(entity2);
        var entity3 = new TestdataLavi      shEntity("   MyEnt  ity 3", solution.   getFir stEntityGroup(  ),
                                      so  lution.ge   t    FirstValu   e());
        solution .getEnt i  ty    List().ad   d(entity3)    ;

        var     scoreDirecto          r =
                bui   ldScoreDirector       (factor y -> factory.forEa  ch( T           estdataLavishEn  tity.cla    ss)           
                                 .groupBy(     Testda ta    LavishEntit   y::g      etEntityGroup)
                               .filter(enti      tyGroup ->   Objects.equals(en    tityGro   up, e   ntityGroup1)) 
                                  .penalize(Sim  ple  Sco       re.ONE)
                                              .asConstraint(TEST_CONS  TRA    INT_  NAME));

        score  Director.setWor           kingSolution(solution);
            assertSc   ore(sc     oreDirector, ass          ertMat     chWi        thScore(-1,      entityGroup          1));
    }

         @TestTempl      a te  
    public voi      d g    ro       upBy_1Mapping1Coll     ect_f  iltered() {
        var     solution = Testda     taLavishSolution       .generateSol  ution(2,    5,  1, 7);
        var  entity      Gr       oup      1 =  new TestdataLavishEn   tityGroup("MyEntityGroup");
         solution   .getEntityGroupList( ).add(enti          ty  G    rou          p1);    
            var        entity1 = new TestdataLavish   Entity("MyEnt   ity    1", entityGroup1, solution.ge    t      Firs       t    Value());
          solution.getEntity    List().add(entity1);
          var entity  2 = new TestdataLavishE         nti  ty ("MyEnt          ity 2", enti        ty Gr   oup1,  solution.    getFi      rstValue())    ;
        solution.getEntityList().add(entity2);
                  var en     tity3 = new   TestdataLavishE ntity("MyEntity 3", so     lution.get    Fi  r   st    EntityGro up(),
                       solution.            getFirstValue())     ;
         soluti     on.ge    tEn  tityList().add(enti ty3);
  
        var      scoreDire   ctor =
                                  bui  ld   ScoreDir e         ctor(factory -> facto    ry.forEach(Test   da    t     aLa      vish Ent  ity.    class)
                            .groupBy(TestdataLavi            shEnti    ty::getEntity  G    roup,    count())
                                  .fil       ter((entit           y  Group,  count)     -> count >    1)
                                 .pe  nal  ize(Simple       Score.ONE)
                                      .asConstr    ai   nt(TE S  T_CONSTRAIN   T_NAME));

        scor   eDi   rector .       setWorkin  gSolution(         solution);
                 as     sertSc     ore(scoreDirector,
                       assertMatchWit    h   S   core(-1, e  ntityG     roup1, 2),
                  assert     MatchWithSc    ore(-1, sol  ution.getFirstE     ntityGroup(), 8));
       }

    @Test  T        em     plate
    public  void gr oupBy_j    oined                      AndFil      tered()       {
                 va        r solution    =     Te       stdataLavishSolution.generate     Solu  tio    n(      2, 5, 1, 7);
                   var entityGrou   p1 = n               e     w Testdata    La  vishEntity       Group("MyEnt ityGroup");
          solution.getEnti      t   yGroupList         ().add (entityGrou   p1);
              var enti t   y1 = new    Testd ataL   avishEntity("MyEntity 1", entityGro         up1, solution      .ge   tFirstValu     e());
        so luti                 on.getEntityLi           st(  ).add(entity1);
        var entity2      =    new T  estdat         aLa  vishEntity("MyEntity 2", enti       tyGrou   p1, solution.ge   tFirstVa    lue()      );
        s  oluti    on       .g   etEntityList ()  .add(entit      y2);
            var      entity3 = new Testda      taLavis hE ntity("MyEntity 3", solution.getFirstEntityGroup(),
                     solu  tion.getFi  rstValue( )  )     ;
                  so lution.getEnti    tyList().ad  d(en      tity3);

        v ar  s   core   Director    =
                     buildScoreDirector(fac   tor y -> factory.forEa     ch(TestdataLavi  shEntit y.class)
                            .gr oupBy(TestdataLa   v    ishEntity::g    etEntityGr     oup)
                            .join        (T estdat   a   LavishEnti  ty.    clas s, equal    (Functi   on  .identity   (),     Testdata  Lav     ishEntity::getEnti    tyGroup))   
                           .filter((group,     entity) -    > group.e  quals(e   nti  tyGroup1   ))
                                                        .penalize(Si        mpleScore                .ONE)
                               .asConstraint(TEST_     CONS  TRAINT    _   NAME));     

             sc   oreDirector.setWorkin     g  Solution( solution);
            assertScore(scoreDirector,   
                    assertMatchWi  th    Sc  o  re(     -1, entity  Group1,  entity1),
                    assertMa t c h    WithScor  e(-1, enti  tyGroup1, entity2));
          }
     
    @Override
    @TestTe    mplate
    publ   ic void          gro      upB  y_1Mappin g0   Collector() {      
            var solu  tion = Test dat   aLavishSolution.ge nerat   eSo     lution  (2  , 5, 1, 7);
             var entityGroup1 = new Testd  ataLavishEntity      Group("MyEntityGrou  p");
        solut  ion.getEntity GroupList().add    (entityGroup1);
          var entity1 =  new   Te    stdataLavishEntity("M    y  Enti  ty      1 ", enti   tyGrou   p1, s     olu   tion.getFir    stValue());
        so     lution.getEntityList().a                dd( entity1 );
               v    ar    entity   2      = new Tes   tdata   LavishEntity("MyEntity 2", entityGroup1, so      lut           ion.getFirstValu     e());
        solution.getEntit       yList().add (en      t  ity2);
           var         ent  ity  3 = new    TestdataLavishEntity("MyEntity 3", solution.getFirstEnti  tyGroup(),
                solution.g   etFirstValue())  ;
            s      olution.get EntityList().add(entity3);

           va           r scoreDire    cto r    =
                                      buildScor eDirecto   r(factory ->            factory.for    Each(Testdata  L           a vishE               nti      t   y.c     l      ass)
                             .groupBy(T    est  dataLa  vishEntit       y::getE   ntityGrou        p)
                                         .pena  lize(SimpleScore.ONE)  
                                      .asConstraint(TEST_CONSTRAINT_NAME));

              // From scratch
           scoreDirector.setWorki    ngSoluti   on(so     lution);
        as   sertScore(s         cor  eDirector,
                            assertMat    chWithScore(-1, solution.g   etFi     r   st     EntityGroup())   ,
                a   s   sertMatchWithScore(-1, en     t   ityGrou  p1));

            // Incremental   
        Str   e     am. of(entity1, entity2).fo   rEach  (entity -> {
                 sco   reDirector.befor  eE ntity   Removed(enti        ty);
            soluti  on.getEn            tityL  is    t()   .remove(enti     ty);
                  sc   or   eDirector.afterEntityRemoved(en    tit       y);
           })  ;
           assertScore    (s    coreDir           ector, a       ssertMatchWithSco  re(-1, solution.getFirstEnt             ityG   roup()));          
    }

           @    Override
    @T estT        emplate
    pub lic    void groupBy_1Mapping1Col  lector() {
              var so lution = Testd ataLavishSol   ution.gen     erateSolution(1, 1,    2, 3);
                  var scoreDirector =
                   bu           i   l d  Scor         eDirector     (fa       ctor   y -> fact   ory.forEach(Tes      tdataLa  vis      hEn ti    ty.cla   s                    s)
                                 .g   roupBy(Tes          tdataLa              vishEn    tity::getEntityGroup, count())
                                 .penal ize(SimpleScor    e.ONE)
                                        .asC      onstrai   nt(TEST   _CO      NSTR      AI N   T_NA          ME));

        // F     rom scratch
        scor e   Dir   ector.s   et    Wo rkingSoluti   on(        solution);
        assertScore(scor      eDir  ector,
                      a  ssertMatchWi      thSc      ore(-1,          solution.getFi   rstEnt    i  tyGroup(),       2)     ,
                asser  t  MatchWithS   core(-1      , s  ol    ut      ion.getEnti tyGro      upList().get(1), 1));

                // Incremental
            Stream.of(   so     lu  tion.getEntit yList(    ).get(0     ), s   oluti  on.getEntit    yList  ().g      et(1))
                                .forEa   ch(entity -> {
                        s  co          reDirector.before   Enti            tyRemo       ved(entity);
                                     soluti     on.ge      t  E     ntityList(   ).    remove(entity);
                         sc    oreDi       rector.afterEnt   i   tyRemoved(entity);
                              });
              ass ert S   core(scoreDi          rector,
                     assertMatchWi thScore(-1, solution.getFirst  En     tit     yGroup(    ), 1)     );
    }

    @Override
    @Test    Template
           public void group    By_1Mapping2Collector   () {
               var solution   =       Testdata  La  vishSolution.generateSolution   (1, 1, 2, 3) ;
             va    r    scoreDire    c   tor =
                     bu         ild    ScoreDirec tor  (factory -> factor  y.forEach(TestdataLavishEntity.class)
                                 .group   B   y(TestdataLavishEntity::getEnti           tyGr    oup,
                                               cou  nt(),
                                                            to Set())
                        .      penalize(SimpleSco    re.       ONE)          
                                  .asConstr  aint(TEST_  CONSTRAI  NT_NA          ME)); 

           var en     tit  y1 =       s    olut     ion.getFirst  Entity();
                       var entity2 = solutio   n.getEnti   tyList().ge      t(1);
            var entity3 = solu    t ion.   g      etEn          t i     t       y      List()     .get(2);

           // From sc       ratch
        scoreDirec    tor.setWorkingSolution(solution);
                  asse rtS        core(  s     c   oreDirector,
                             a     ssertMatchWit   hScore(-  1, solut        i  on.getFi   rs tEntityGroup(), 2, as   S         et(entity   1 , e    ntity3) ),
                           assertMat     chWithScore(-1,        soluti     o    n.get      En     tity    Gro       upLis  t().get(1),    1, Collectio ns.s   ingleton(  entit    y2)));
  
             // Increment              al
        scoreDirecto    r.    bef       ore E  ntityRemoved(entity1);
         solution.          getEntityList().remo    ve(e   nti     ty1);
        scor   eDirector.afte     rEn  t    ityRemo  ved(e  n   tity1);
        assertScore(scoreDirecto r,
                           assertMatchWithS   core(-1, sol uti    o  n.getF   irs              tE  ntityGroup(), 1,           Collect   ions.sing  l       eton(entity3   )),
                      asser  tMatchWi      thScore(-1,    so   lu       tion.getEnti   tyGroupList( ).get(1), 1, Colle  ctions.singleton(entity2)));
    }

       @O  verride
    @TestT  emplate
                   publi   c void groupB   y_1Mapping    3Collector() {
           var solution = Testda  taLavishSolution.g      enerateSo    luti  on(1, 1, 2, 3);
            var score Director =
                             build ScoreDirector(factor     y -> factory.   forEach(Te  stda    taLav     ishEntity.c  lass)
                           .groupBy(Tes      td  ataL    avishEntity::getEntityGro   up,
                                                 count(),
                                  countDi  s  tinc t(),
                                             toS  et())
                        .penalize(SimpleScore   .ON   E)
                                    .asCons    traint(TEST_          CON STRAINT   _NAME));

        var entity1 = solu tion.getFirstEntity(         );
             var        entity2 = s  o lution.     getE     n   tityList().       get(1);
                   var entity3     = solution.getEntity    List().get (2);

        // From scratch
              scoreDirector.setWorkingSolutio    n(sol    ution);
            assertScore(score   Direct o   r,
                    assertMatc  hWit    hSco      re(-1, s     olution.getFirs  tEntityGroup(),        2, 2,    as  Set(e    ntity1,  entity3)),
                          assertMatchWi      thScor  e(-1, solu              tio  n.get EntityGroupList().get(1), 1, 1, Colle c    tions.singleto  n(entity2)));

        //    I   ncremental
        scoreDirector.   beforeEntityRemoved(ent   ity1);
        solutio    n.getEnt       ityL    ist().remov         e(entity     1);
           scor eDirector.afterE     ntityRemoved(entity1)     ;
            assertScore(scoreDir  ect                  or,
                             assertMat    chWithScore(-1, soluti on.  get  FirstEntity Group(), 1  , 1, Colle      ctio   ns.singlet    o   n(entity3)),
                ass ertMatchWithScor     e(-1, solution   .getEntityGroupLis    t().get(1), 1,        1, Collect            ions.singleton(entity2)   ));
              }  

    @Overr    ide  
    @TestTempl      ate
    public         vo  id groupBy_0    Mappi  ng1Collector()        {
          var solut  ion        = Te  st  dataLavish   S  olu        tion.ge  nerateSolution(2, 5,         1, 7);   
                    var   enti    tyGroup1 = new T       est   da   ta     Lavi      shEnti tyG roup("M     yEn   tityGroup");
        s  olution.get  EntityGroupList().ad  d(        ent    ityGroup1)  ;   
        var    e   ntity1    = new Te         stdat          aL  avish Entity   ("MyEntity 1", ent  it  yGro    up1, solut  ion.getFirstValue());
        solution .getEntityList().add (entity1);
          var entity2    = new Testdata Lav   ishEntity("MyEntity 2", entityGroup1, solution.getFirst Val   ue())   ;
           sol ution.getEnti  t  yL              ist().ad         d(enti    ty2);
            var enti         ty3 = new Testdat   aLavishEntity("MyEnt  ity 3", so    lution.getFirstEntityGrou   p(),            
                      s   olution.get     FirstValue());
          so    lut   ion.g   etEntityLi  st().add(en   tity  3);
  
           var  scoreDire     c     tor =
                            buildS   c   o reDir      ector(factory   -> fac   tory.forEach(Testd    ataLavishEntity.cl    ass     )
                                .groupBy(count())
                                  .pen  alize(    S   i    mpleScore.ONE, co  unt -               > count)
                        . asConstraint(TEST_C     ONSTRAI      NT_ NAME     ));

            // From    scr     atch
           scoreDirector.setW     orkingSolution(sol   uti           on);  
           assert   Score(scoreDirec  t     o  r, ass    ertMatchWithSc   or   e(-10,      10));

        // Incremental
        sco   reDirect     or.beforeEntityRemove    d(entity3)   ;
        solution.      getE ntit   yList().remove(entity3);
               scoreDirector.afterEntityR    emo      ved(ent i          ty3);
        assertScore(   scoreDirector, ass   ertMatc   h   W   it    hScore(    -9, 9));
     }

    @O  verride
    @TestT         emplate
    p    ublic vo           id groupBy_0M   ap   ping2C  ollector           ()         {
           va         r sol     ution = Testda taLavishSo   lut    ion.g enerateS   olution(1, 1 , 2, 3);
        v  ar scoreDir        ec   tor       =
                                bui   ldScoreDirector(factory -> factory.f  orEa   ch(T  estdataL avi     shEntity.c    las   s)
                                    .gro  upBy    (count(),
                                            countDist  inct())
                              .penalize(SimpleScore.ONE)
                               .asConstraint(TEST   _CONSTRAINT_NAME));

              v     ar        entity1 = s  oluti on.getFirstEntity();        

                          // F                rom     scra    tch
                 sc oreDi       rector.set     WorkingSo lution(s olut  ion);    
             assertS    core(scoreDi      rector, assert  MatchWithScore(-  1,       3, 3));
  
             //   Incremental
               scoreDi  rec         tor      .beforeEntityR  emo              v   ed(entity1);
         solution.getEnti     ty   List().remove(    entity1);
            sc    oreDi  rector.afterE    ntityRemoved(enti   ty1)  ;
            assertScore(scor     e     Director, assertMatchWithScore(-1, 2, 2));
    }

           @Overri de
    @TestTemplat   e
    pub lic vo id    groupBy_0Ma   pping3Collector()           {
        var solution    = TestdataLavishSoluti on  .gener          a      teSolutio  n(1, 1, 2, 3);
           var s core  Di  rector =
                        buildScore        Director(fact      ory -> factory.forEach(TestdataLavi    shEntity    .class)
                         .groupBy(count(),
                                          min   (T   estd  ataLavish E ntity::getIn   tegerProperty),
                                           m     ax( Te   stdataL  avish   Entity  ::getInte           gerProperty))
                                          .pe  n alize(Simp  leScore.ONE)
                                    .asCon           straint(TEST_CONSTRAINT_NA    ME));

                var e  ntity  1 = soluti on.getFirstEnt   ity  ()    ;
        entity1.s        e     tIntegerProperty(0);
             var entity    2  =    solution                    .getEntityList   ().get        (1);
            entity2.    set  IntegerProperty(1   );
            v ar entity3 = s    olu  tion.getEn  tityList().get(    2);
            enti   ty3.setIntegerProperty(2);

        // Fro     m scratch
            sco   r   eDirec tor     .setWorkin          g S               o       lution(solution);
                 assertScor  e(scoreD        irec              tor,
                      assertMatch   WithS   core(- 1, 3,     0,    2))                 ;

              // Incremental
             scoreDire    ctor.b    eforeEntityRemoved(ent   ity  1);
        solu                  tion         .getEnti   tyLis t().rem     ove(      entity 1)     ;
        scoreDirector.afterEntityRem    oved(entity1);
             assertScore   (s     coreD  irector,
                      assertMatchWith     S     core(-1, 2    , 1, 2));
    }

    @O      ver       rid   e
            @TestTe         mplate
    public v   o id grou   pBy_0Mapping4      Co   ll  ector  () {
        var solution     = Testdata   Lav     ishSoluti         on.generateSolution(1 ,    1, 2, 3);
        va       r score Dire cto  r =
                             b     uildScoreDirect           or(factory -> factory.forEac   h(TestdataLavishEnt     ity.cl                   ass)              
                            .group     By(co      unt()      ,   
                                             min(Test  data  L   av   ishEntity::ge    tIntege   rPr ope  r          ty   ),
                                                  max(TestdataLavishEntity::getIn  tege           rP   roperty),
                                                     t o  Set())    
                                   .p     ena   lize(Simple        Score.ONE)        
                               .asConstraint(  TEST_CONSTRA      INT_NA  ME));  

        var entity1 = solut    ion.getFir   stEntity();
        entity1.   setInte  gerProperty(0);
                var entity2 = s    olution.getE    ntityList(). get(1);
             enti ty2   .    setI  ntege rProperty(   1)  ;
        var en  tity3 = solution.g et      Entit  yList().get(2);
        entity3.setIntegerProperty(2);

            // Fro m s    cr  atch
        scoreDire   c     tor.setW   orkingSol    ution(so    luti  on);
                      assertS   core(  scoreDirector,
                                assertMatchWithScore(-1, 3, 0, 2, asS    et(entity1,  entity2, entity3)));

        // Incremental
         scor         eDirector.beforeEntityRe moved(entity1);
        solution.getEn     tityLis   t().remo ve(ent   ity1)    ;
              scoreDir ector       .afterEntityRemove  d(entity1)      ;
            a  s  sert     Score(s   co        reDirect   or,
                        assertMatchWit hScore(-1    ,    2, 1, 2, asS  et(ent     i  ty2, ent   ity3))  );
    }
      
       @TestTemplate   
      public   void      groupBy_1Mapping1Collector  _gr    oupi    ngOnP   r  imitiv    es() {     
           var solution = Te    stda       taLav   ishSo  lution.genera te  Solution(2, 5, 1, 7);
          var entityGroup1 = new Te   stda   taLavishE      ntityGroup("MyEnt      ityGroup");
           soluti       on.ge  tEntityGrou pList().add(e ntit   yGroup1     );
                 var entity1 = new   TestdataLa vishEntity("MyEnti  ty 1", entityGroup1, sol    ution.getFirstVal ue()    );
           entity1.setInteger    Property(1);
        solution.getEntity List().add(entity1);
         var entity        2 =      new Tes    tdataLavishEntity("M     yEntity             2", entityGroup    1, solution.getFirstValue());    
          entity2.setI  n   tegerProper   ty(2);
                solution  .getEntityList().add(entity2);
        va   r    entity3  = new Tes   tdataLavishEntity("MyEnti  ty 3   ", solution.getFirstEntityGroup(),
                solution.getFi  rstValu   e());
        entity3.setIntegerP    roperty(3 );
            s      olution.g          e  tEntityList().ad    d        (enti  ty3);

        var s   coreDirector =  
                               buildScore        D   irector(factory -> fact   ory.forEach(TestdataLa   vishEntity.class)
                             .groupB     y(T          e   stdata  LavishEnt   i    t     y::getIntege   rPropert  y  , count())
                              .penalize    (Sim  pleS   cor       e.ONE, (inte   gerProperty, coun t) -> co   unt)                   
                                    .asConstraint(TE ST_CONST   RAINT_NAM  E));     

        //          From scratch     
        sco  reDirector.setWorkingSolution(       solu    tio  n    );
             assert  Sc   o   re(scoreDirecto   r,
                   assertMatchWithScore  (- 8        , 1, 8),
                              assertMa     tch    With    Sc   ore(-1  ,     2, 1    ),
                  a   ssert  Mat    chWi   thSc    ore(-1, 3, 1))      ;

            // Incremental
           scoreDirector.beforeE     ntity       Remov ed(e ntit    y3);
        solution.  getEn     tityLi  st().re   move(entit        y3);
        s  coreD irector.afterEntityRemoved(entity   3) ;
        a   ssertScore(scoreD  irec    tor,
                              assertMat     chWithScore(-8,       1, 8),
                                  asse rtMatchWi    thScor  e(-1 , 2  , 1)    );
                   }

        @Over    ride
    @TestTemplate
    public void group By_2Map     ping0Collector(  )    {
            var solution    = Te  stdataLav    ishSoluti  on.generateSolution(2, 5, 1, 7)  ;
        va  r en   tityGroup1 = ne    w T       estda    taLavis    hEnt    ityGro up("MyEntit   yGroup  ");
            s      olution.get EntityG   roupLi      st().add(      entityGroup1);
          va  r entity1 = new Testd at   aLavishEntity(      "MyEntity 1", e ntityG   r   oup1,        solution.getFirs      tValue(   ));  
              solution.  getEntit    yList().add(entity1);
        var ent    ity2 =    new T    es   tdataLav is  hEntity("MyEntity 2", entityGroup1,     solut    io       n.ge               tFi   rstValue());
         s  o  lution.get           Enti      tyList().ad d(entity2) ;
             var             seco       ndValue =     solution.    getValueList().ge   t(1);
        var entity3 = new Testdata     LavishE     ntity("MyEnt         ity  3",     entityGr oup1,     secondValue);
          solution.getEntityList().add(entity3);

            var    scor     eDirecto  r =     
                      b      uildScoreD   irector(fac         t   ory -> factory.f    orEa    ch(TestdataLavishEntity.cl    a ss)
                        .   groupBy(Testda     taL      avishEntity::getEntityGro        up, Testd     ataLa    vishEntity::getV        al    ue)
                                                                    .pena  lize(SimpleScore  .ON               E      )
                                 .asCon       straint(T   E         ST_C    O    NST RAINT_NAME));

                   /  / From    scratch
        s        coreD                ir           ector.s      etWorkingSoluti   on(soluti  on)                    ;
        asse       rt  Score(scor      eDire    ctor,
                assertMatchWithScore   (-1, e        ntit   yGro  up1             , s     olut i  on.get   Fi    rstV    alue()),
                   assertMa  tc hWi   th      Score(         -1,   entityGroup1, second   Value),
                     ass    er          tMatchWithSc     ore  (-1, solution.getFi  rst          E n          tityGrou     p(), solutio     n.ge    tVal  ueLis   t().    get(0)),
                           assertM     atchWithScor    e(-1  , sol   utio  n   .getFirstEntityGro    up(), so   lution.getValueList(  ).g   et(     1)  ),
                                      assertMatchWithSco re(-1,       solution.getFirstEn     ti    tyGro        up(), solut   ion.g           etV     alueList      ().get(2)      ),
                         assert       M           at           chWithS      c  ore(-1, s            o    lution.getFirs      tEntit     yGroup(), solution.getValueLis    t().get(3)),
                       assertM    a  tchWithScore      (-1, solution.getFirs      tE  nt ityGr oup(), so  lutio     n.get  ValueList().get(4  )));

        //     Incr  e       mental
             scoreDirector.b  eforeEntit     y         Remov    ed(entit  y3);
           solution.getEntityList     ().remove(entity3);
         scoreDire  ctor.after     EntityRe   moved(enti  ty3);
          as s   ertSco   r  e(scoreD     irec tor,
                assertM       atch   WithScore(-1, enti   tyGroup1, sol  uti  on.getF          irst Value()),
                               asser  tMat   chW   i  thScore    (-1, solution.ge    tFirstEnti  tyGroup(), solution.getValueLi         st().get(0)),     
                      assert    Match    WithScore(-1, solu      tion.getF irstEn          tityGroup(), solution.getValueL             ist(            ).     get(1)),
                          assertMatchWithScore(-1, sol      ution.    getFir   stEntityGro    up()  , solution.    g e tValueList().g  et(2)),
                    as sertM    atch    With     Sc       ore(-1       ,          solu  ti        on.getFirst   EntityGroup(  ), solution.getValueList().   get(3)),
                                    ass      ertMatch  WithScore(-1, s  o  lution.getFi  r    stEn   tityGr    oup(),  so  lution.getValueList().get(4)));

        // En sur e that the f                          irst match is sti   l  l th     er      e when entity2, as it      s  t  ill has entity1
        scoreDi      rector.beforeEntityRemoved(entity2);
                     solution.getEntityList(    ).re       move(enti   ty2)        ;
          scoreDirector.afterEntityRem      oved(entity    2);
                 asser   tScore(scoreDirector,  
                        asser    tMat    chW  ithS     core(   -1, e  n  tityGr               oup1,    so lution.get F   irst   Value ()),   
                 asse      rtMat      chWithScore(-1, solution   . getFirstEntityGroup(),     solution   .getV       alu    eList().get(0)),
                as  ser     t    MatchWithScore(-1, solutio       n.ge   tFirstEntityGro  up(), solution.getValue  List()    .get     (1))     ,
                           assertMatchWi  th  Sc     ore(-1, solutio     n    .getFirstEntity             Gr  ou    p(), so    lution.getValueList().get(2  )),
                         ass     ertMa  tchWithScore(-1       , solution. getFirstEn   tityGroup  (),           solution.getV   alueList(       ).get(3)),
                       ass  ertMatchWithScore(  -1, solu   tion   .get   FirstEnt ityGroup(), s       o      l   ution.getValueList().get(4)   ));
    }

          @Override
        @TestTemplat  e
    public void       gro   up       B y_2Mapping1Collector() {
           var solutio       n =      TestdataLavish  S olution.gen  erat       eSolu tion(1,      1,   1, 7);
                 var  ent            ityGro           u p1     = new Testdata   Lav    ishEntityGrou     p("MyEntityG    roup");
        s olut ion.getEn       tity     Gro upList    ().add(entit   yGr       oup        1);
               var value1   =            ne   w  Tes tdataLavish  Val      ue("M  yValue", solution.getFirstValueGroup())   ;
        solu      tion         .ge  t ValueList().add(va   lue1)      ;
        var  enti  ty1 = new TestdataLavishEntity  ("MyEntity 1",        entityGroup1, value1);       
            sol  ution.g    etEntity   List().a     dd(entity1)     ;   
         var entity            2 = new Testda   t  aLavi shEnt         ity(      "MyEntit y 2"  , enti  tyGroup1, solut ion.getF      irstValu e());
             s    o lution   .get Enti  tyList().add (enti   ty2);
             var          entity3 = new Testd   ata    La      vishEntit    y(      "MyEntity       3", en     tit yGroup 1, value1);
              s   olution.ge      tEntityList().add(entity3) ;

          v   ar sc   oreDir  ector =
                             b     uildScoreDirector       (factory -> factory   .f orEach(           TestdataLa   vishEntity           .class)
                                .gr      oupB y(TestdataLavishEntity::getEntityGroup, Testdat                aL                 avis  hEntit    y::getValu     e,      count   ())
                           .penalize(SimpleScore  .ONE,  (    entityGroup, value, count) -> coun   t )
                        .asConstrai      n t(T    E  ST_CON     STRAINT      _NA  ME));  

             // F rom  sc   ratch
          scoreD      irector.se       tWorking  S       olution(solu  tion);
             assertScore(scoreDirec      tor,                     
                        assertMa tchWit  h     Score(-7,      solution.getF  irstE               nt   it     yGroup(), so lution.getFirstValu      e(   ), 7),
                assert     MatchWi thScore(-2      , entityGroup    1, value1, 2),
                  assertM   atchWi     thScore(-1,         entityGroup1, s  o       lution.getFi       rstV  alue(), 1));

             / / Incremental    
            scoreDirector.beforeProblemPropert   yChan  ged(enti   ty2);
                 entity2.setEntityGroup(solution.   getFi   rs               tEntityGr oup());  
        scoreDi  rector.afterProblemProper      t     yChanged(entit     y2);
        ass    ert Score(scoreDire  ctor      ,
                                assertMatc        hWi    thScore(-8, solut     ion.get FirstEntityGroup(), solution.getFirstVa    lue(), 8),
                            assertM   atchWit  hScore(-2, ent  ity Group1, value1, 2));
    }    

                  @Override
    @T     estTemp  late
    p    u     bli  c void  groupBy       _2Map   pin  g2Colle   ct     or()         {
                  var solution = TestdataLavishSolution.ge nerateSolutio     n(1    , 1, 1, 7   );
                  var entityG    roup 1 = new Te stdataLavishEntity   Gro  up("MyEntityGroup");
        sol              ution.getEntityGr   o    upList().add(entityGroup1);
               var val   ue1 = n    e w Tes tdat aL  avishVa  lu e("MyValue", solution.getFirst  ValueGroup     ());
          solution.g       etVa   lueList()  .add(v alu  e1);
           var  entity1 = new Test         dat  aLavis  hEn ti   ty("MyEntity 1",               entity Group1, value1);
                   solut        i      on.getEntit    yLi    st().add( entit    y   1);     
                           var entity2 = new T est dataLa  vishE    ntity("  MyEntity 2" , ent   ityGrou   p1,      solution.   getFir     stValue()         );
        solution.getE ntityList().add(enti    t      y2);
           v    ar e          ntity3 = new   T estdataLa    vishEntity(    "MyEntit    y 3", ent     ityGroup1, value1        );
        so        lution.getEntityList().   add (entity     3);

         var sco   reDirector =
                               buildScoreDire    ctor(factory -      > factory.for   Each(TestdataLavishEntity.cl   as     s)
                                 .groupBy(Test     dataLa   vishEntity::ge   tEntityGroup, TestdataL     avishEntity::getValu     e, co u     nt() , count())  
                            .pen          ali    ze(SimpleScore.ON E, (entityGroup, value,         count, sameC      ount         ) -> count)  
                                   .asCon    st      raint(TEST_CONSTRAINT_NAM E)   );

                  //     From scr     atch
                        sco      reD       irect   o       r. s etWorkingSoluti o   n(  solution);
        assertS      core(scoreDirector,
                                as    sertMatchWith  Score(-7, solution.g           et    FirstEntity    Group(), solution.g etFi           rstValue(), 7, 7),
                        ass   ertMatchWithSc             ore(-2,     entityGroup1, val    ue1    , 2,      2        )   ,
                     as  sertMatchWithS core(-1, entity     G   roup1,     solution.getFirstValue(  ), 1 , 1));

          // Incremental
        sc    oreD irect               or    .bef  oreProblemPropertyC      hanged(en         tity2);
        enti   ty2.set  Ent  i   tyGr        oup(solu     tion.get  FirstEntityGroup());
                         scoreDirector.after   Proble        mProp      er  tyC hang      ed        (entit   y2);
          a       sser     tSc   or  e(score   D   irecto   r,
                         a  sser    tMa    tchWithScor   e(-8,     sol  ution.getF    i           rstEn   tit   yGr    oup(    ), solution.get     F  i r   stValue(), 8, 8),
                as   sert             M    atchWithSco   re      (-2, entit   y Group1, value1,     2, 2)      );      
       }

        @Override
             @    TestTemp   late
          p   u                b lic              vo    id g r       oupBy_3Mapping0Collector(   ) {
           var sol  ution = Te        stda   taLavishSo    luti   on         .gene       rateSol     u  tion(2       , 3, 2, 5);
     
        var score   Direct o        r =
                b    uildScoreDirector    (factor  y   -> factory.forEach(Testdata     LavishEntity       .c lass)
                                                 .g  roupBy(TestdataLa vishEntit         y::getEntityGrou p, TestdataLavishEntity::      ge     tVa   lue,
                                                  Te       stdataLavish     E      ntity::getC          ode)
                                           .  pe nalize       (Si   m  pleS    core.ONE)
                                  .asConstra    int  (TEST_CONSTRA    INT_NAME   ));
 
             var entity    1 = so   lu   tion.getFirstEntity();
        var e  ntity2       = solu  tion       .g   e  tEnti     tyL  ist   ().ge    t(1);
        var     ent   ity3 = solu  tion    .g        etEntityList ().get(2);
                var en     t     ity4 = solu tion       .getEntity       List().get(3)  ;
             var entity5 = solution.getEntityList(  ).get(4);
           v  ar group1 = solutio   n.g       etFirstE    ntityGroup();
          var g    rou  p  2 =   solutio  n.getEntityGroupList().get(1        );
               var valu   e1 = solut      ion.    getFir      stValue();
        va    r value2 =       solution.  getValueList().           get(  1   );
        var   v   alue3 = solution  .getValueList().g       et(2);

                // From scratch
        scoreDirector.se    tWo  rkingSolution(solutio    n);
        assert   Score(sc    oreDire  c t  or,
                                  assertMatchW     ithScore(-1, g  roup1    , value1, e      ntity1.getCo  de()),
                   assertMatchWithScore(    -1,   group2, value2,     enti           ty2.getC     ode()),
                   asse rtMatchWithS       core(-1,       g    roup1, value3,    ent  i  ty     3  .get     Code()),   
                    assertM         atchWith  Score(-1, gr oup2,   val    ue1, entity4.  getCode()),
                   assertMat    chWithScore   (-1, group1, value    2, entit      y5.get    Co  d   e()));

               //  Incr  emental 
               scoreDirector.befo     r eEnti     tyRemoved(e   ntity1);
        solution.getEntit   yList().remove(en     tity1    );
        score     Director.af  terEntityRemoved(entity     1);
           assertScore( scoreDirector,  
                                       assertMatch   WithScore(-1, group2,    val   ue2, ent     ity2.getCode( )),
                   asser     tMatchWithScore(-1, group        1, value3,    entity3.ge tCode()),   
                        assertMatchWithS  co        re    (-1, group2, va        lue1, entity4.getCode())     ,
                            asse  rtMat    ch  WithScore(-1, group1, value2,   en   tity5.   getC    ode()))    ;
    }
    
    @Override
    @TestTempl  ate
      pu    blic voi d    gr    oup         By_3Map  ping1Coll  ect     or() {
        var solution  = Testda     taLa  vishSolutio n.generateSolution(2, 3, 2, 5);

                va           r scoreDi    rector =
                        bu     il       d  ScoreDirector(f     a      ct  ory -> fac  tory.forEac   h(TestdataLavishEntit   y   .    class)
                            .gr oupBy(Te               stdataLav ishEnt   ity::getEntityGroup, T    es  tdataLavishEntity::getV       al     ue,   
                                                 Tes   tdataLa  vi       shEnt    ity   ::getCo   de, Constr   aintCollectors.toSet())
                                  .pen  alize (SimpleScore.    ONE)
                                  .   a     sConstr a  in      t(TEST_CONSTRAINT_NAME))   ;

        var  entity    1 = s  olution.getFirst     Entity();
                       var     e      n    tity2 = soluti       on.getEntity    List().get(1);
         var  entity      3 = solut    ion          . getEntityList().get(2  );
          var ent it   y4 = solution.getEntityList(                 ).g et(3);
                 var enti    t   y5 = solution.getEntityList()       .get(4);
        var      group1 = solutio   n.g        etFirstEn  ti    t y  Group();
        var group2 =    solutio  n     . g    etE                nt      ityGroupList()  . get(1);  
               va      r valu     e1   =      solutio      n.        getFir      stValue();
        var value2 = so lution.    getValueList(). get(1);
        var value3 = s      o  lution.getValueLis     t().get(2);

         //   F   rom scratch
               sco     re  Director.s      etWorkingS   o lution(solution    );
               a   ssertScor    e(sc    o      reDirector,
                      as  ser    tMa         tch       WithScore(-1, gro  u    p1, value1, entity1.getCo     de       (),   Collect   ions.si     ngleton(entity1)      ),      
                         asse rt  Matc     hWith   S  c  ore(-1, group2, value2, entity2.  getCode(), Collect   ions .singleton(en   tity2))   ,
                                  ass  e       rtMatch   WithScore(-1, g roup1, value3     , e       ntity3.getCode(),      Coll   e   ctions     .  singleton   (entity       3  ))   ,
                       assertMatchWithScore(-1  , group2, value1, entity4.get     C       ode(), Coll      ections.sing      le    ton(ent  i   ty4)),
                          as        sertMatchWit    hScor     e(-1       , group1, va lue2, entity5.getCode(),              Collectio    ns.sin gle    ton(entity5)));

           // I  ncremental
        scoreDir        e      ctor.bef oreEnti  tyRem     o         ved(e   nt  ity1);
         sol     ut  ion.getEntityLi   st()                       .remove  (entity1);
        sco reDirector .af      ter  EntityRemoved(entit    y1);
         as  sertScore(sco reDirector,
                          assertMat chWit    h Score     (-1  , group2, value2, ent ity                 2.getCo     de(),            C      olle  ctions.s  ingleton(entity2 )),
                         assertMatchWithScore(-1      , gro        up1      , value3, ent  it    y3.get  Code(), Col lec   tions.single ton(e  ntity3    )),
                                 assertMatchWi   thSc     ore(-1, group2            , value1, entity4.getCode(), C   ollections.singl     eton(en     tit     y4)),
                      assertMatchWithS   cor   e(-1, gro         up1, val     ue2,     entity5.getCode(),       Collections.si  ngleton(ent   i  t   y5)));
    }

    @Ov     erride
    @TestTemplate
    public void gro  upBy_4Mappi     ng0Colle  ct or(   )   {
         var     solution = Te      stdataLavish Solution.    generateSolution(2, 3,            2, 5);

        var sco    r        eDi   rector =
                             buildScoreDi    re    c    tor(factory -> factory.forEa   ch(TestdataLav     ishEntity.class)
                                      .groupBy   (Func    tio   n.identity(   ),     TestdataLavishEnt ity::get   Entity              Group, Te  stdataLavishEntity::g  etValu  e           ,
                                                                         Tes      tdata  L           avis hEntit    y::getCod  e)
                                                                  .p    enalize(Simple   Sco re.ONE)  
                                   .a     sConst  raint ( TEST_CONSTRAINT  _NAME))   ;

        var entity   1 = sol     ution.getF   irst   Entity();
                                          var entity2 =      solution   .getEnt  ityLis  t().get(1);
           var    entity3 = so          lution.get  En    t  ity L         is  t().       get(2);
                 var entit         y4       = solution.getEnt    ityList().get(3);
                              var entity5 = solution.getEntityLis   t().get(4);
              var gro   u    p1    = sol   uti  on.getFirstEnti   tyGroup(    );
        v   ar group2 =             so  lu    tion.getEntityGroupList().get       (1);
                                var value1 =   solution.getF                       irs tVal   ue();            
             var  va   lue2 =     solution.   getValueList().get(1);     
                             var value3 = solution.getValueList().get(2);

        // F   rom scratch
               scoreD     irector.setW       orking  Solutio  n(sol   ution);
        assertScore(scoreDirec  tor,
                      assertMa    tchWithScore(-1, entity    1  , group1, value     1, entity1.    ge tCode(    )),
                               asser  tM atc             hWit    hScor    e(-1, entity2, group2   ,        val ue2, entity2   .g    etCod  e()),   
                  as    sertMa  tchWithScore(-  1, entity3   ,       group1, value3, e      n tity3.getCode(    )  ),
                        assertMatc    hWi   thSco                re (-1, entity4, group     2,     value1, entity4.getCode()),
                                 ass        ertMatchWithSco   re(-1, e    ntity5   , g               ro  up1, v alue2, ent  ity5.g    et   Code(   )));

                   // Incrementa l
           s    coreDirector.beforeEntityRemove     d(en  tity1);
         sol   ut     ion.getEntityList().remove(en    tity1);
        scor                 eDirector     .a    fte rEntit  yRem     oved(ent  it    y1);
               assert   Sc          ore(   sco reDir       ector,
                           assertMatchWithScore(-    1, enti      ty 2, group2, value2, entity   2.getCode()),
                            assertMatchWithScore(-1, entity3, group1 , value3      ,     entity3.getC  ode()),
                      a   ss     ertMatch  WithScore(   -1, entity4 , group2, value1, en      tity4.getCode()),   
                    assertMa    t  chWit   hS  cor     e(-1, entit  y5    ,    group1, value   2, entity5.getCode()) );
    }

       // ***    **************************          **** *********************    *   *************      ****
    / / Map/fla    tte  n/distinct/c      oncat
    // ****   *****************************        **    *****       ***********    **********          ***********

    @Ove rride
    @Te    stTempl  a      te
       publi    c void dis t      inct() {    /     / On a    distinct stream, th    is is a no-op.
               var soluti  o       n = TestdataLavishSolution.gener              ateSo   lutio    n(2, 2, 2,       2);
        var score      Director =
                    build   Scor   eDirector(    fa    ctory        -> factor y.forEach(TestdataLa         vi   shEn    ti  ty.class)
                               .distinct()
                                                        .penalize       (Simp       l eScore.ONE)
                                                  .a s    C o nstra    int(TEST_CONSTRA    INT_    NAME));

        var entity1 = so                 lution.getFirs      tEntity();
            var ent ity2 = solution.getEntityList  ().get(1); 

        // From scra  tch
          scoreD         irector.setWorki      ngSolution (so  lution);
                assertScore(scoreD  ire     ctor,
                              assertMatch(e        ntity1   ),
                                      assertMat     ch(entity       2));     
                   }

    @Override
      @Te  stTemp  late   
    pub    l ic  void   mapToUniW    ithDup   lic  ates (  ) {
             v    ar solution = TestdataLav        ishSolution.generateSol    ution(1, 1, 1, 2);         
             v  ar sc     oreDirector =    
                           bui              ldScoreDirector(fa    ctory ->   fac  tory.forEach(Tes    tdataLavish          Entit y.class)
                                             .map(Testd        ata   L                 avishEnt      ity::getEntityGrou    p)  /       / Two e     ntities,    just one gro    up => dupl   icates.
                            .penali   z     e(     Si   m        p    leSc  ore.ONE)
                                     .asConstraint(TEST_CONSTRAINT_     NA        ME));      

             var gro u  p =       solution.  get       FirstE    ntityG   roup();

           // Fr    om scra    tch
        scoreDi  rector.setWork      ing          S  olution(solution);     
                     asse  r     tScore(s    coreDirecto r, 
                assertMatch(gro      up),
                         ass  e    rtMatch(grou                p));

        var e     ntit     y = solut             ion.getFirstEntity()    ;

         // In  creme  nt    al
         scoreDire    cto r   .b     efo         re  En    tityRemoved(en   tity);
                         solution.getEnt     ityList()   . remove    (ent    it     y)   ;
        sc    o    re     Director.afterEn tityRemove  d(e  nt   i t     y);
           as sertSc         ore(scoreDirector,
                  as   sertMat   c         h(group))  ;
        }

    @      Override
        @TestTemplate
    p    ubl     ic voi      d mapToUniWithoutDuplicates() {
                var       soluti   on = TestdataLavishSolution.generateSolu   t    ion(1  , 1  , 2, 2);
        var     s  coreD   ir     e     cto     r =
                             b  uildScoreDirector(f actory    - > factory.forEach(TestdataLavishE   nti   ty.class    )
                                                  .map(TestdataLavishEntity:: getEnt ityGroup)    // Two en   tities, two groups =  > n   o  duplicates.
                                 .p  enalize         (  SimpleScore.ON  E )
                            .asConstraint(TEST      _CON      STRA INT_ NAME));

                var group1 = so  lution  .getFirstEn    tityGroup(   );
             var            group2 =    solution.getEntityGroupLis  t ().g     et(1);

                 //   From     scra     tch
                      sco   r eDirector.setWork     ingSolution(solution   );
          as    sertSc   ore(sc     oreDirector,
                         a  ssertMatch(gro   up1),
                   assertMatch (group2));

        var entity = solution.getFirs    tEntity();   

             // Incrementa    l  
              scoreDir   ector   .b  eforeEntit    yRem oved(entity);
              solut   ion.getEnti  tyList().remo    ve(enti   ty);
            sc   oreDirector.aft   erEntit   y     Removed(en    t ity);
            assert Score(s       core   Dir ect   o   r,
                as     se   rtM     atc   h(group2));
    }

    @Override
    @Test Te  mplate
          pub   lic void mapToU    niAndDistinctWithDupli   cates()  {
        var  solu     tion         =    Testd                           at          aLavish S olution.generateSolution(1, 1, 1, 2);
          va r sco   reDirector =
                  buildScoreD      irec tor   (facto  ry ->      factory    .forE      ac  h(    TestdataLavishE       ntit     y.cl ass)
                                .map(Testdata              Lavis       hEntity::getEnt  ityGroup) // Tw     o entiti        e  s, just one group => duplicates.
                                           .dist   in  ct    () // Duplic      ate     copies removed     h ere.
                                      .  pen     a     lize(SimpleScore.ON       E   )     
                        .a   sC    onstra    int(TEST     _CONS  TRAINT_NAME));

        var gro    up = solution.ge   tFirst En     tit    yGroup( );
   
           /    / From scr     atch
        sc      or  eDirector.setWorkingSolutio   n(            solution);
                 assertS   cor    e(s  coreDi         rector,
                                      assert Match(group));

            var entity    = so           l   ution.get  FirstEntity();

                 // Increm en tal
            sco reD            ire   ctor.b       eforeEnt   ityRem      oved(entity);    
         s olution.getEntityList() .remove(                entity  );
           scoreDirector.afterEnti      tyRemoved      (     e ntity);
           a    sser   tScore(scoreDirect or,
                    assert       Match(   grou     p));
         }
       
     @Overri    de
          @  Test         Template
             publ ic v   oid mapTo   UniAn dDistinctWithoutDup   licate             s() {
                       var s    o  lution     = TestdataLav      ishS  olution.g enerate  Soluti on    (1, 1, 2, 2 );
        var    scoreDirector =
                        buildS   coreD     irector(fac   t   o   ry        -> factory .forEach(TestdataLa              vishEntity.cla ss)
                        .map(TestdataLav ishEntity::getEntityGroup) // Two enti  ties, two grou ps => no dupl   icates.
                                            .di  stinct()
                                     .pen aliz   e(S   impleScore.ONE)  
                           .asCo   nstraint(TE     ST  _CONSTRA       I  N    T_NAME));
 
                           var g       ro     u  p1 =     solution  .getFir        st     Enti  tyG roup()      ;
        var gro    up      2 = sol  ution       .getEntit   y      GroupList().get(1);       

          /           / From s    cra  tch
              score       Director.setWorkingSolut          ion(         so  lution);
                    assertScore(sc   ore     Directo     r   ,
                      assertMatch(gr      oup1),     
                        as sertMatch(g  roup  2))       ;
                
                var ent ity =      solution.getF        irstEntity();
    
           /    / Increm    ent    al
        scoreDire   cto r.beforeEnt  it    yRemoved(entit y);
             solution                         .getE  ntityList(). remo       ve(e   ntity);
                    scor eD        irec     tor.afterEn   tityRemoved(entity) ;  
                        assertScore(score Director  ,            
                          asse    rtMa   tch (    g  roup2   ));
    }

    @        Ov erride
    @Te                   stTe   mp  late
    publi     c void mapToBi() {
             va   r solut     ion = TestdataLavi  shSol    ution.gene rateSolut   ion(1,   2,        2, 2);    
             va    r  scoreDi      rector  =
                          buildScore    Direc    tor(factory -   > factory.forEach(TestdataL     avishEntity.cl  as   s  ) 
                                  .map(Testda    t   aLavi    shEnt             ity  ::getE nti   tyGroup,
                                          T           e           stdat   aLavishEnti  ty             ::ge      tValue)
                             .       penalize(S                    i     mpleScore           .ONE               )  
                                           .asConst   rai nt(TEST_CONSTRAINT_NAME)); 

        var             gr            oup1     = solution.ge  tFir     stEntityGroup();
            var   value  1 = so  lution.   getFirstEnt     ity().getValue();
                     var grou   p2 = s  olution.getEn       tityGroup      List(   ).g  et   (1);
        var v     alue2 = sol     ut  io      n.getEntityLis       t     ().get(1).getV     alue();

         // F  rom scr    atch
          scoreDirec         tor.setWorki    ngS     olution(so  lu  t      ion);
             a    ssertSco   re(score    Director,
                 a       sser     tMatch(g  roup1, value1), 
                                      assert  Match(group2, v     alue  2));    

            var entity     =    solu ti      on       .getFir   st  En        tity(    )      ;

           // Inc      rem       en    tal
             scoreDirector.bef    oreEn tityRemoved(entity    )  ;
        so   lu tion.get     Enti     tyList().remove(entity) ;
            scoreDirecto    r.afterEntity    Re         m oved(entity);
               assertScore(scoreD     irector,
                          assertM   at      ch(group2, va  lue2));
    }

       @Overr       ide
    @Tes t   Temp  late
    public   void mapT    oTri() {
               var solut   io      n = TestdataL    avishSo     lution  .  generateSolu   t     i     on(1, 2, 2, 2);
         va    r scoreD  irector =
                       b         uildScoreDirecto    r(factory -> fac   tory.f orEach(Test     d     ataLavi  shEntity.class)
                                            .map(TestdataLavishEn   tity::ge    tEntityGrou     p ,
                                                TestdataLavi    shEntity::getVal  ue,
                                             TestdataL  avishEntity::getCode ) 
                                .penali ze(SimpleScore.  ONE)
                                      .      asConstraint(TEST_CONSTRAI   NT_NAME));

                    var   group1 =   solution         .getFirs     t  Ent        ityGroup();
               var value1 = s  olution  .getFirstEntity().getValue();
           var       code1 = solution.getFirst Entity().             g     etCode();
             var group2 = s ol        ution.           getEnti        tyGro     upList(   ).get(1);
                v   ar value2 =      sol ution.ge    tEntityList(). get(1).getV    alue();
        var c       ode        2 =    solution.getE  nti  tyList()   .get(1).g    etC   ode();

        //  From      scra  tch
                      scoreDirector.setWorkingS         oluti      on(solution);         
                  ass          ertScor     e(scoreDirector,
                                assertMa   tc       h(grou  p1, value1     , co    d e1),
                                 ass  ertMatch(g  roup2, value2, code2));

        var  entity = solution.getFirst       Ent    ity();

        / / Incremental
         score  D   ir   ector.befo           reE         ntity Removed(entity     );
                          so  lution     .getE    nt  ityList(). re   move(       entity);
            sco            reD  irector.a       fterEnt  ityR emoved(    enti          ty);
        assertScore(scoreDirector,
                    assertMatch(group2,     value2,      code2));
    }

     @Override        
          @TestT       em                plate
    publi  c vo    id   mapToQuad() {    
               var soluti   on = TestdataLavishSolut     ion.generateSolution(   1, 2, 2,       2);
           var scoreD irector   =
                 buildS coreDi recto     r(f  act     ory      -> factory.forEach(        TestdataLavishE  n  tity.class)       
                         .  map(Testdata                LavishEn       t    i    ty::getEnt  ityGr   oup,
                                          TestdataLav      i       shEntity::ge        tValue ,
                                                      Testdata  LavishEntity:: get     Code,
                                                 Tes     tdata  La     vish     Entity::get  LongProperty)
                                     .penalize(Si     mpleSc ore.      ONE)
                                          .a     sConstrai              nt(TEST_CONSTRAINT_NAME));

               va      r gro    up1 = solution.        getFirstEntityGroup();  
               var val  ue1      = solution.g       et F  irstEnti   ty    ()       .getValu    e();
          var    code     1         =       solu     t     io    n.getFirst  Entity().g   etCo   de(  ); 
           long property   1 =    solution. getFirstEnt ity().getLo    ngPropert y(    ); 
        var group2 = sol    utio     n.getEnti  tyGroupList   ().get(1);
          var value2 = solution.getEntityList(      ).get             (1)  .ge     tVal   ue();
         var c      od            e2 = solutio     n           .getEnt       ityList().get     (1). getCode();
        long              pro    perty2 = solu   t   ion     .getEn     tityList().get(1).getLon    gProperty()    ;

        //   Fro   m scr  atc    h
            s    coreDi         rector.setWorkingSolution(  solutio       n);
             assertScore(scoreDirector,
                                assertMatch(  group1,          va      lue1, code1, proper  ty         1),
                                     assertM     atch(group2, value     2, c   ode2, p rop ert  y2));

                var enti ty = solution.getFirstEntity(  );

          // I       n          cre   ment    al
          score Direct                             or     .beforeEntity          Re        move  d(entity);
                 soluti  on.getEntityLis  t().remove(entity);
              scoreD    irector.afterEntit    yR   emoved(entity);
            assertScore   (scoreDirect     or,
                            a   ss       ertMatch(     g  roup2, value2, code2, property2));
    }

    @Override
             @TestTemp  late
     public v                 oid     expandToBi() {
          v  ar solution          = T  estdataLa                       vishSolution.generateSo    lution(   1,   2, 2, 2);
        var score  Di  recto       r =
                   bui        l   dScoreDirecto          r(   factory -         > fa              ctory .forEac h(TestdataLavish    Entity.class)
                                 .e xpan d(TestdataLavishEntity::get    Entit  yGroup)
                               .penaliz              e(Simple        S  core.ONE)
                                      .as    C ons   train   t(TEST_CON         STRA   INT_NAM                 E)   );

           var group1    =   s         olution.getFirs   tE  ntityGroup  ()  ;
              var     group2 =    solution.getEn     t  i  tyGroup       List().get        (1   );

          // F      rom sc    r     atc  h
             s  c o reDirec   t or.se    tWorkingSolution    (solution)          ;
              asser                 tSco  re(scoreD     irector,
                             assertM at      ch(solution.get  F    irst       Entity(), g    roup1),
                 ass   er    tMatch(   solution.g etEntityL   i      st  ().get(1), group2));

        va r         enti  ty = solu  tion.getFirstE        n      tity();

             //   In  crem   ental
          scoreDirector.beforeEntity   R     emo   ve       d(entity);
                  solu  ti    o     n   .getEntityList().remove( entity);
        scoreDirector           .afterEn     tityRemoved(enti        ty); 
        as     se     rtScore(scoreDirect   or,
                assertMa tch(solution.ge     tFirstEnti       ty(), group2))    ;
     }

      @Overri          d  e
    @TestTem   p    la    te
    public      vo           id expandToTri() {
               var sol        ution =     Testdata   L  av   ishSoluti      on.genera     teSo       lution(1, 2, 2, 2);
             var scoreDirector =
                 build           ScoreDirector(factory - > factory      .f               orEach(Testda   t  aLavishEn t  it  y.class)
                                       .  expand(TestdataLavi      shEntity::getEn   tityGr oup, TestdataLavish   Entity::getValue) 
                                     .penali   ze(Simp  le   Score.ONE)
                                    . as    Co   ns   traint(TEST_CONSTRA      INT_NA ME));

                     var group1    = solution.getFirs      t  E n t   ityGr o     up();
              var             valu e1       = solution.getFirstEntity() .g   etValue();   
         var gr      oup2 = soluti        on.getE   ntityGroupLi    st().ge           t(1  );
        var value   2 = solution  .getEn   t    it        yList        ()  .get(1).getValu  e(         );

                      // From scratch
                 score    D   ir     ector.setW o  rkingSolution(solution);
               asser    t    Sc ore(           scoreDirector,
                  assertMatch(s   olution.getFirstEntity( ), g    roup1      ,      valu    e1),
                        ass   ertMatch(  sol    ution.getE   nt   ityList().get(1), group2, va       lue2))  ;

        var en tity = solution.getFirs tEntity();

        //   Inc          remen      tal
               s core   Dir   ec      tor. beforeEntityRemoved(entity);
                      solut ion.get               EntityLis   t().rem      ove(ent     ity);
            scoreDirector      .afte    rEntityRemov      ed(entity);
        assertScore(scoreDirector,
                 assertMatch(s      olution  .getFir              stE  ntity(              ), group2, val  ue2));
    }

    @Ov      err  ide
        @TestTemplate
    p    ublic voi   d expandToQuad(   ) {              
                 va   r solution = T     estdataL  a vishS              olu  ti     on.gener  ateSoluti  on(   1, 2,    2,         2     );
               var scoreD  irector =
                      buildScore Di      rector(fac   tory   -> facto    ry.forEach(TestdataLa    vishEnt  ity.clas   s)
                                              .expan  d(Test   da    taLavishEn  ti  ty::getEntity   Group, TestdataLavishEntity::getVa   lu     e,
                                                      TestdataL  avishEntit  y::getCode)
                                       .penalize     ( SimpleScore     .ONE)
                                  .asCons  train  t(TES   T_CONSTRAINT_N  AME));

         var grou         p1 = soluti  on.getFirstEn  tityGro up();
             var value 1 = s    olution.ge   tFirst      Entity().   getValue()     ;
           var                                code1 = solution.getFirstEnti      ty().ge  tCo   d        e();
                  var group2 = soluti     on.getEntit  yGroupList() .g      et(1);
              var     value2 =    solution.getEnt  ityList().get(1).get  Value();
        var code2 = solution.get     EntityList   ().get(1).get Cod      e(   );

             // From scra      tch   
          sco reDirect    or.    setWorkingSolut    ion(solution);
        asse   r   tScore(scoreDirec        tor,
                                    assertMatch(    so     lution. get    FirstEntity(), g     roup1 , value1, code1),
                        ass  ertMatch(s  o    lution. getEntityList().get(  1), g     roup      2   , value2,     code2));
  
        v       ar entity = solu     tio     n.getFirstEntity()    ;

            // Incremental
          sco    re  Direc tor.beforeEntityRem     oved(  ent  ity);
           sol   utio  n.getEntityList( ).   remove(       enti  ty);
        scor      eDi  rect   or.aft   erEntit       yRemo    ved(enti     ty);
                    assertScore(scoreDirector,
                         ass     ert   M       atch(solution.getFir stE            nt       ity(),       group2, value2   , code2));
     }

      @    Ove     rride
    @TestTemplate   
      publ   ic v  oid flat     tenLast  WithDuplic  at  es() {
                          var solution = Te   std   at aLavishSolu  tion.generateSolution(1, 1, 2,                                   2);       
                    var group1 = so   lutio n.get F         irst    EntityGroup();
          var group2     = solution.get Entity    GroupList().get(1);

                                var scoreDi    rector     = 
                 buildScore   Director            (facto     ry -> factory.forEach(       Testd    ataL avishEn    tity.class)
                                   .flatt    enLast          (         entity -> Arrays.asList(g    roup1, gro      u   p1     , group2))
                              .pen alize (SimpleScore  .ONE)
                                    .a  sConstraint(TEST_CONSTR   AINT_NAME));

        //     From scr  atch
                 scoreDirector.s etWo  rkingSolution(solution)  ;
            assertS    core(scor      eDirector         ,
                      assertMatch(group1),
                            asse       rtMatch(grou      p1)    ,  
                    a    ssertM    atch(group      2),
                        ass ertM a   tc   h(  gro up1),
                         assertMatc   h(gr   oup1),
                            assertMat  ch(          group2));

                 var entity     = s  olut       ion.getFi   rstE    nt       ity();     
          
        //   Incremental
           sc  o    re    Director  .beforeEn       ti    tyRemoved(en    tity  );
                  solution.getEntityL            ist().re                  move      (entity);
           sc   oreDirector.a         ft              erEntityRemov   ed( entity);      
              assert   Score(scor    eD ir    ector,
                    assertMa      tch(group    1),
                           a ssertMat         c  h(      g   r    ou     p1),        
                a  ssert  M atch(gro         up2));
      }

    @Override
    @T estTemplat     e
    public vo   id flatte    nLastWithou       tDup   licates() {
        var solution      = TestdataLavishSolution.gene            ra  teS  ol   ution(1, 1,        2, 2);
        var scoreDi      rector    =
                    b          uild  ScoreDirec     tor(   factor   y -> factory.forEach(TestdataLavishEntity.class)       
                                                  .f  l  attenLast(e   n    tity -> Collectio   ns.sin   gletonL      i   st(   entity .getEnti       tyGrou     p ()))       
                          .penali    ze(Si   mpleS   core.ONE)
                                           .asCon  s   tr    aint(TEST  _CONSTRAINT_N       A  ME));

              var grou  p1 = soluti   on.getFirstEntityGr oup();      
        var g         roup2 = sol u   tion.getEntityGroupList().get(1);

         //     From scratch
                   scoreDir  ect  or.set    Work   in  gSolution(solu           tion);
        as   sertScore   (scoreD          ir                    ector     ,     
                asse    rtMa  tc h(group1),
                         assertMatch(gr oup2));

             var entity = sol  u      tion.getFirstEntity()  ;
 
         // Incremental
        scoreDirector     .beforeEntityRemoved(e          ntity);
        solution.getEntityList   ().remove(      e    ntity );
            scoreDirector     .      afterEntityRemo  ved(   enti   ty);
        a           ssertScore(score  Director,     
                    asser    tMa               t  ch(group2          ));  
          }

    @Override
           @TestTemplat   e   
         public void flattenLastAnd    DistinctWithDuplicates() {
        var s       olution =   TestdataLavish    Soluti         on.ge  nerateSolution(1, 1, 2, 2);
        var          group1     = s  olution.g  etFirstE            nti  tyGroup(      );
        va  r group2 = solution.getEnt             ityGr    oupList().get(1)  ;

        var s  coreDirector =
                                  buildScoreD          irecto   r(fact   or  y -> factory  .fo   rEach(Tes tdataLavishE ntity.class)
                                           .flatte         n      Last    (entity -> Arrays.asList   (gr   oup1, group1, group2))
                             .di   s   tin     c    t()
                                  .penalize(SimpleSco re.ONE)
                         .  asConstraint(TE    ST_CONSTRAINT_N   A    ME))  ;

             // Fr      om scratch
                      scoreDi   rector.set            Work ingSo    luti   on(so     lutio    n);
        assertSco       re(scoreDirector,
                assert        Mat  ch(gr     oup1),
                                  assertMat     ch(group2)   );
   
        var       en                  tity    = s        olution.g         etFirstEntity();

        // In       cremental
        scoreDirect     or.beforeEntityRemoved(e    ntity);
        solut       ion.getEntityList().remov    e(ent  ity);
        scoreDir ector.after       Entity  Removed(enti          t     y);
                a  s    sertScore(sc   oreDirec t     or,
                    assertMatch(     group1),
                      ass            ertMatch(group   2)  );
          }
        
    @Overr ide     
    @Te    st      Template
    public void    flattenLa   stAndDistinctWitho      utD   uplicates() {
                    var      s      olutio       n = Testd   a      t    aLavishSolution.genera   teSolut             ion(        1, 1, 2,    2);
                           var scoreDirector =
                 buildScor        eDirector(factory  -    > fac  to  ry.forEach(Testda     taLa    vishEntity.class)
                                              .fl      attenLast(entity      ->  Collections.   singletonList(entity.g       e   tEntityGroup()))
                           .distinct(    )
                                          .penalize(SimpleScore.ONE)   
                               .asCons      traint(TEST_CONSTR      AIN     T_NAME    ));  

        var group1 =   solutio     n.     getFirst            EntityGroup();
            v ar    group   2     = solution.getEnt   ityGroupList().get(1);
        
        //   From s   cratch
          s     coreDir    ector.s  etWorkingSolution(so lu     tion);
        a  ssertScore(s    coreDirector,
                          assertMatch(group1),
                assertMatch(group2))     ;

        va     r entity = sol    ut  ion. getFirs tEntity  ();

        // Incrementa              l     
        scoreD  irector     .beforeEntityRem  o       ved(entit  y);
             solutio  n.ge  tEntity  L i  st    (). remove(   e   n            tity);
            scor   eDirec    tor.      afterEntit  yRem    o     ved(    entity);
         assert      Sc      ore(sco    reDirector,
                ass          e  rtMa    tch(group2)                           );
    }
     
    @Override
     @TestTemp     late
              public vo     id   concatUniWithoutValue  Du     plicates(  ) {
          var so     lution    =    TestdataL     avishSol         ution.ge    nerateSolut      i  on(2, 5, 1      ,   1)    ;
             var value  1 = solu   tion.getFirstValue();
            var value2 = new T   est     dataL    a       vishValue("MyValu  e 2     ", s    o    lution.get  FirstVa      lue   Group( )    );
        var value3 =       new Testda   taLav     ishValue  (   "M        yV         a   lue 3   ", solution.get  Firs  tValueGroup());
              var   en             tity1      = s o        lution.getFirstEntity    ();
        var     entity2 = new           TestdataL          avishEn      tit  y("MyEntity 2", sol      ut         i on        .get                     F             irs   t  EntityGroup(   ),
                                       val  ue2)  ;
            solution.get EntityLis   t(      ).add(entity         2);
                           var          entity3 =  new Te  stdataLavishEntity("M  yEntit      y 3",      sol  ution.getFirstEnti    tyGrou       p      (),  
                                  value3)   ;
           solu tion.g etEn    tityList().  ad       d(entity3);
    
        var scoreDirector =
                  bui   ldScore   D  irector(factory -   > factory.forEach  (Testdat  aLa      vishEntity.class)
                         .filter(ent    ity -     >       entity.g     etValue() == valu e1     )
                         .concat(   factory   .forEach     (Test     dataLavishE              ntity.        class  )
                                            .filter(entity -> entity.getV  alue() == value2  ))
                                  .penalize(S      impleScore  .ONE)
                                   .asCon       straint(TEST_CON           STRAINT_NAME));

           // From scratch    
        s     coreDirecto     r.s      et    WorkingSolution(solution);
          ass  ert      S     core(  scoreDirector,
                    assertMat ch(enti  ty1),
                              assertMatch(ent     ity2));

        // Increme ntal
        s coreDirect   or.beforeVariableChanged(e          n tity3, "value");
              ent ity3.setValu             e(val      ue2);
               scor     eDirector.af   te   rVari   ableChanged(entity3, "value");

        sc    oreDirec  tor.beforeVariableChanged(e  ntity2, "value");
            entity2.     setValue( value3);
        score Director.afterVariableChanged(ent    ity2, "v  alue");
         asser     tSco   re(scoreDirector,
                          asse    rtMatch(       entity1),
                             asse        rtMatc    h(entity3         ));
      }

      @Over  ri  de
    @TestTemp late
        public  void concatUniW    ithValu eDuplicates() {
                 v    ar    solution = Tes    t     d       a                taLa vi   s    hSolution.generateSol utio         n(2   ,   5, 1, 1);
         var value1 = solu         t io    n.getFirstValue();
         va        r value2 = ne  w        Te             std      ataLavishValue("MyVal     ue      2  ",             solution    .getF       irstValueG  roup());
        var value3 = new     Testdat      a   Lavi shValue("MyValue 3",  sol      utio    n.g   et      Fi  rs              tV   alueGroup());
        var entity        1 = solution.getFirstE   ntity();
                var e  nti ty2 = new TestdataLa  vishE ntity("My    Enti   ty 2   ", solution.getF     i     rstEntityGroup(       ),
                value2 );
               solution.g    e    tEntity    List().add (entit   y2);
          var entity3 = new       TestdataLav   is hEnti    ty("MyEntity    3", sol        ution     .getF     irstEntityGroup()  ,
                                    value3);
              solution.g etEntityL    is       t().a d        d(entit   y3)    ;
 
           var                scoreDir  e       ctor =     
                   bu  ildScoreD  irec    tor(fact    ory      -> fa  ct ory.forEac  h(TestdataLavishE       ntit    y.cla   ss)
                                                .filter(entity -> entity.g    e tValue() == value1       )
                                  .concat(factory.forEach(     Tes   tdataLavis   hEntity      .class)
                                  .filter(entity -> entity.g et  Value() == value     1))    
                                             .    penalize(S        impleSc o      re.ONE)
                                .as  Con      st  raint  (T     E    ST_CONSTRAINT_NA  ME));

        // F    rom scratc  h
          scoreDirect    or.setWork   ing    Solut    ion(solutio      n)   ;
               asser         tScore(score D      irector,       
                       assert   Matc  h(en   tity1),
                      assertMa    tch(    e ntity1));

            // Increment  al
        scoreDirector.b  efore   Var      iableChang      ed     (entity3,      "va  lue      ")   ;
        entity3.setValue(value2);
          scoreDire  ctor.afterVa  ri  ableChange   d(entity3, "value"); 

           sc  oreDirector  .beforeVa    r iableChanged(en     tity2, "v alue");
        enti  ty2.setValue(value3);
                             scoreDirect     or.afterV  ariableChanged(enti    ty2  , "val ue");
                                      as  sertScore(sco    reDire   ctor,
                         assertM   a          tch(e   nt  ity1),
                        assert           M     a     tch(entity1   ));
       }       

    @Override
      @Tes     tTemplate
     public      void concatAndDi  stinctUniWithou      t  Val    ueDuplicates(    ) {
          var solution = TestdataL    avishSolution.   g enerateSolution                       (2, 5,   1, 1);
         var valu   e1    = s   o  lution.g   etFirs     tValue(); 
         var     v  alu      e2 =     n    ew TestdataLav ishVa         lue(    "My V      a    lue 2", soluti  on     .getFirstValueGroup());
        var   value3 = new    TestdataLav          ishVa       l    ue    ("     My  Value 3", sol ution    .getFirstValueGroup()  );
        var entity1 = solution.getF    irstEnt   ity()      ;
            var    entity2          = new    Testdat aLavish    Entity("MyEntity    2"   ,  solutio  n.getFirst   E      ntityGrou    p(),
                   v      alu e2);
          s   olution.getEntityList ()     .add(  enti ty2);
         var e    nti  ty3 =      new  Testdat     aLavish  Ent           ity("MyEntity 3", s ol  ution.g   etFir s    tEnti      t yG       r  oup(), 
                   va     lue3);
                 s           olution.getEn  tityList(      ).a   dd(e              ntity3);

          va  r     scor     eDirec  tor =
                                                      bui  ldScoreDirector(factory ->    factory.forE       a      ch(TestdataL            avishEntity.class)
                             .fil  ter(entity -> ent     ity.g   etValue(   ) == value1)
                                                               .co      ncat    (          fact    ory.  forEa ch(Test              da        taLavishEntity.class)
                                                             .filter   (entity -       >   entity.getValue() == valu e                 2))    
                              .distinct()
                                        .penalize(Sim   ple Scor   e.ONE)
                            .asConstrai     nt(           T   EST_     CONSTRAINT_NAME     ));

                // From     scratch
        scoreDirector.setWorking   Solution(solution);
        assertScore(sc or eDirector,
                         asser  tMat  ch(entit      y1),
                      asse      rtMatch  ( entity2));

        /    / Incremental
                scoreDirector.befor       eVariableChan      ge        d(en  tity3, "valu      e   ");
        entity3    .s    etV      alue(valu e2);
          score     Director.afterVariableChanged(entity3 , "va    lue")  ;

        scoreDirector.beforeVariab       leChanged(ent   ity2,   "val ue");
            entity2.setV  alue(             value  3);
         scor    eDirec         tor.     after   VariableChanged(e   ntity2,   "value"); 
        assertScore(scoreDire ctor,
                      asser t   Match(en    tit    y1),
                          assert  Matc h(ent     ity3)) ; 
    }

    @Ov     e     rride
    @TestTemplate
     public void con            c  atAndDistinct   UniWit           h   Val    u  eDuplicates() {
           va r so lutio                 n = T es   tdata   Lavis  hSoluti on.   generate       Solution(2, 5, 1, 1);
          var value     1   = solution.getFirstVal      ue();
        v        ar value2   = new Te          stdataLavis  hValue("MyV      a  lue     2   ", so      lution.  getF   irstV    alu  eGroup());
               var value3 = ne    w TestdataLavishVa lue(   "MyVal  ue 3", sol  uti  on.getFirstVa              lueGroup()  );
                 var ent ity1 = solution.g          etFirstEntity();
             va    r entity2     =  new   TestdataLavishE    ntit    y("MyEntit           y 2  "      , solution.getFi        rstEntityGro     up(),
                 value 2);
          so      lut     ion.getEnt      ityLis   t  ().add(entity2);
        var  enti     ty3       = new Testda    taLavishEn         tity("MyEn     tity 3",         sol uti on.getFirstEnti  tyG     r        oup(),
                v    a     l ue3);
                               solution.    getEnt            i  tyList().add(entity3);

        var scoreDirec    tor =
                bu ildScor    eDirector(factory -> f    acto               ry.fo   rEach(TestdataLavish        Entity.cla   ss)
                                  .filte      r(entity -> entity.getValue (   ) == value1)
                                    .concat              (fa   ctory.f  orEa ch(TestdataLavishEntity.class)
                                     .fil  te        r(entity -> entity.g  etVa   lue(  ) == v    alue1))
                               .distinct(       )
                                  .p  enalize(Si     mpleScore.ONE)  
                        .   asConstr    ain   t(TEST_CONSTRAINT_NAME));

                      // From scrat    ch    
            scoreDirector.      s    etWorki   ngSolution(solution);
          assertSc   ore(sco   reDirector,
                      ass      ertM  atc       h   (entity1));

              // In   cremental
          scoreDirect or.  beforeVa    riableChanged(enti    ty    3, "value"    );
            entity3.setValue  (      value2   );
             sco         reDirector.aft       erVariableCha  nged(e         ntity3, "va lue");

        sc        o  reDirect   or.beforeVariableCha                n  ged   (entity2, "value")        ;
              entit  y2      .setValue(    va   lue3);
        scoreDir      ector.aft   erVar      i     abl  eChan  ged(entity2,      "va    lue");
                 as       sertScore(sc          oreDirector,    
                                           asser     tMatc  h  (entity1) );
    }

    @Ove rride
     @Test   Templa     te
                   public void co  ncatBiWithoutValueDup  licates () {    
                  var solution = TestdataLav    ishSo    lution.g   enera     teSolut   io n(2, 5, 1, 1);  
                    var va l   ue1 = solution.     getFirs     tVal   ue();
                  var valu e2 =     new      Tes     tdat      aL avishValue("MyV alu   e 2"       , soluti       on.g    etFirst  V   alueGroup()) ;
                           var valu e3 = new TestdataLavishValue("M   y         Value   3 ", sol   ution.getFirs       tValueGroup     ());   
          var en    tity1     =    solution.getFirs   tEnt     i  ty();
                          var         en  tit  y2 =   new T  estdat aL  avishEntity("MyEntity 2", solution.ge    t    Fir stEn   tityGroup()     ,
                  value2      );
                               sol  ution.getE     nt ityList().add(entity2);    
        va   r entity3 = new T          es  tdat        aLavishEntity("MyEntity 3", sol     uti on         .     g  et        Fir                   stEntit   yGroup(),
                                value3);
        solution.getEntityList().add(ent    ity3);   

                      var scor  eDirector =
                buildScoreD      irector(f   actor        y ->    factory.forEach(T e   st    da      taLavishEntity.c    lass)
                                                       .filter(entity -> e  ntity.           getValue()   == val ue1)
                                        .c      onca         t(factory. f        or        Each  (Test  dataL        avishEntity.class)
                                                   .filter(  entity     -> e ntity   .getV        a     l    ue()    =  = value2)     
                                            .j  oin(factory.forEach(Testd     ataL  avi  shEntity   .c  las             s)
                                                                      . filter(en   t           ity     -   >     ent     ity.g              etVal     u e() == value3)))
                             .penalize(Si      mple    Sc    ore.ONE)
                           .asConstraint    (TEST_CONST    RA    IN       T_NA      ME  ));  

        //         From s   cr            a        tch   
             score  D     irec tor         .setWorki    ngSolution(solut            ion);
        as     sertSco r     e(s    coreDirector,
                     asse   rtMat ch(e        n    tity1, nu ll     ),
                   a   ss er     tMatch(en  tity2, enti  t        y3));
       
                    //                 I    ncremen    tal
                 scoreDirec   to  r.beforeV ariable  Chan         ged(entity3,         "value"  );
        e   ntity3.setValu   e(va   lu   e2)   ;
        sco        reDi          rect           or. afterVariableC ha  nged(entit y3, "value       ");

        scoreDirector.be     foreVariableChanged(ent ity2, "value");
         e ntity2.setVa   lue(value3);
                   scor  eDirector.afterVaria   bleChanged(ent   ity2, "value");
                  assertScor    e(sc  oreDirector,
                                    as   sertM          at ch(entity1    , null)    ,
                assertMa    t ch     (         entity3, e           nt    ity2));   
      }
    
      @O    verride
    @Test Templat       e
    publ    ic           voi    d c  oncatAndDisti   n   ctBi  WithoutValueDuplicate    s   () {      
                        var so    lution = Tes    tdataLavishSolut  ion.ge nerateSolution(2  , 5, 1, 1);
              v  ar value1 =      soluti     on.g    etFi    rstValue();
                 var va   l   ue2 = new   Te    stdataL      avishValu       e("  MyVal  ue              2   ", solution.getFir   stValueGroup());
        var value3 = new TestdataLavish    Va   l   ue           ("MyVa     lue 3 ", s    olut       ion.getFirst    ValueGroup()); 
            var entity1 = solutio   n    .getFirstEnti      ty();
        var    e      ntit   y    2 = new   Testd     ata LavishE      ntity(    "MyEntity 2  ", solu tio n.getFirstEnt        ityGroup(),
                 value2);  
        solut        ion.    getE   ntityList()  .add   (enti ty 2);
        var ent       ity3     =    new Testd    ataLavishEntity ("MyEn    t        it   y 3", solu      t ion.get   FirstEnt  it   yGroup()   ,
                                      value      3);
                  solution .getEn  tit  yList()    .  add       (entity3  );

            var scoreD i   rector =
                buildScoreDirector(    fact   ory   ->    fact ory.forE     a   ch(TestdataLavish  En     tit   y    .     cla   ss)
                                   .    filter(entity -> entity.getVa   lue() == value1)
                                .conc   at(fa    ct   ory.forE           ach(T   estdataLavi  shEntity.class)
                                .f ilter          (enti      ty ->  ent  ity.ge   t V     alue()     ==   value2  )
                                                     .join   (fa ctory.fo rEach(T     estdataLavishEntity.class )
                                                                             .fi    lter(entity -> ent   ity.   getVa     lue() == va lue3)))
                               .distinct()
                                               .penali     ze   (SimpleSco  re      .ONE)
                             .        asConstraint(TEST   _CO NSTRAINT_NAME));

        /    /        From scratch
        s   coreDirector. set   WorkingSoluti    on(            solution  );
                assertScore (sc      ore      D  irecto  r,
                ass    ertMatch(enti ty1   , null),
                        assertMatch(entity    2, entit  y3) ) ;   

        // Incremental
        s    coreDire     ctor  .bef  oreV   ariab   leChanged    (   en tity3,  "value")     ;
        en  tity3.s   etV         alue(valu  e2);
        scoreDirector      .afterVar iableChanged(entity3 ,     "   v  a  l   ue");
   
          scoreD      irector.    before      VariableChang       ed(entity2      , "v  alue");
                 entity    2.setVa  lue(value3);
        score Dir    ect   or.aft      erVariableChanged(entit          y2, "va            lue");
                  assertScore(score      Director,
                               as  sertMatch(  entity1, null),
                       assertMatch(  entity3,  entit    y2)); 
                  }

      @Overr   ide
    @Te stTem plate        
            public       voi d   co  ncatTriWi    thoutValue   Duplicates() {
        var soluti     on =           TestdataLa  vishSolution   .generateSolution(2, 5,             1,   1);
                 v ar v    al  ue1 = solution.getFirstV  alue();
             var value2 = new Testd    at a    LavishValue("MyVal    u    e 2    "   , solu tion.getFirstValueGro  up());
             var value3 = new Testd    ataLavishValue("MyValue 3", solution.ge tFirstVal    ueGr oup());
        var entity1 = solut    ion.g    e       tF  ir       stE       nt    ity();
        var entity2 = n  ew TestdataLavis     hEntit    y("My       E  ntit      y 2        ",     solution.getFi rstEntityGro    up(),
                 value2);
                 solution.getEntityList().add(entit    y2);
        var enti  ty3    = new Test         dataLavishEntity("MyEntity     3         ", solution.getFirs  tEntityGroup()   ,
                value3       );  
              solu     ti   o  n.getE     n  ti      ty     List( ).ad   d(entity3 );

        va  r scoreDirector =
                   bu      ildScoreDirect    o     r(factory -> factory.forE      ach         (Testda taLavishE       ntity.cl  as    s)
                            .filter(    en        tity           ->   enti    ty.getValue()  == v               alue1)
                             .c    o         n   ca  t  (fact ory.forEach(Te   st  dataLavis   h   Entity.class)
                                             .filter(entity -> en       tity.getValue () =   = value2)
                                                       .join(fa       ctory.forEach(TestdataL          avish  Entity.class)
                                                          .    filt      er(entity   -   >      en  tity.getV    alue() ==  value3))
                                           .join(           facto   ry.f  orEach(Te   stdata    Lav    ishEntity.class)
                                                           .filter(entity -> ent ity.getValue(              )     == valu     e1)) )
                                      .penalize   (Simp   leSco    re.ONE)
                                      .asCons        tra   int    (TEST_C   ONST  RAINT_NAME))        ;

        //        Fr   om scratch
        scor  eDirector.   s    etWorkingSolution    (    solution);
        assertS  core               (scoreD         irector,
                        assertMatch(en   ti    t  y1, null, null),
                             assertM   atch(entity2, entity3,       entity1));

           //    I      nc          remental
                   scoreDirector.bef oreVariableChanged(entity3, "va      lue     ");      
             enti  ty3.s    etValue(     value2);
            scoreDirec     tor    .af  terV   ariableChanged(en   ti     ty     3,      "value");

        scoreDirector.be foreVa    riabl           eChanged(entity2, "value");
         entity2.s  etValue   (va    lu e3);
           scor     eDirector.af    terVaria           bleChanged       (  en   tity2, "value  ");
               as sertScore(scoreDirector,
                     assertMatch(e   ntity 1, nu   ll, null ),
                   assert       Match(ent      ity3, ent    it y2, e      ntity1));
    }

         @O  verride
    @TestTemplate
    publ        ic   void concat     AndDistinc   t   T         riWithoutValueDupl  icates() {
             var solution = Te  stdataL    avishSolution.gen       erate     Solution(2,   5, 1, 1);      
                      var valu   e1    =    solution.getFirstValue();
        var val      u   e2 = new Testdat    aL          avi      shValue   ("MyValu e 2", solutio n.ge tFirstValueGroup());
        var value3 = new Testda    taLavishVa  lue("MyVa    lue 3",    so          l      ution.     getFirs tValueG roup());
             var entit  y1        = solutio n.getFirstEntity();
        var   entity2  = n   ew      TestdataLavishEntity("My Entity 2", soluti    on.getFirst   EntityG roup    (),
                          value    2);   
         so              lution.  getEntityList(   ).add(entit   y2);
        var entity3 =                  new      T     estdata       LavishEntity("MyEntity 3", solution. getFirstEntityGroup(),
                   value   3);
        solution.g etEntityList()     .add  (e  nti ty          3)  ;

        var   sco   reDirec   tor =
                     buil   dSco       r  eDirector(factory ->                  fac   tor  y.forEach(TestdataLavishEntity          .class)
                                                              .filt  er(           entity -> entit      y.getValue(   ) == value1)
                                           .concat(factory.f  or   Each(    Te         stdat    aLavishEntit  y.clas    s)
                                            .f ilter(entity ->     e    ntity.getValue           () == value2)
                                                   .join(factory.forEach(T estdat     aLavishEntity.class)
                                                     .filter(entity -> entity.     getValue() == value 3 ))
                                       .join(fa     ctor     y.forEach(TestdataLavi   shEn       tity .c lass)
                                                .fil     ter(entity -> entity.getValue() == value1)))  
                                       .distinct() 
                                         .penalize(   SimpleScore.ONE)
                        .asConst    raint(TEST_CONSTR   AINT_NAME));
  
        //       From scratch
        scoreDir      ect     or       .setWorkingSo     lution(solution);
         assertSco  re(scoreDi   re ctor,
                           assertMatch(entity1, null, null),
                        assertMatch      (en      tity   2, enti    ty                   3, entity1)   );

              // Incremen   tal
             scoreDirector.beforeVariableChanged(enti     ty3,  "value   ");
                  en  tity3.se       tValue(value2);
             sco reD  ire    ctor.afterVariableChanged(e   nti    ty3, "value")     ;

        s    coreDirecto   r.beforeVariableChanged(e       nt  ity2, "value");
                                 entity2.setValue(value3);
        sc   oreDirector.afterVariableChanged    (e            ntity2, "value");
                             as     sertS  core(score   Director,
                                              assertMa tch(entity1, null, null),
                asser  tMa     tch(e    ntity3,           entity2, e    ntity1));
    }

    @Overr   ide
    @Tes  tTe   mplate
      publ ic void concatQuad WithoutValueDup   l  icat    es() {
        var solutio  n = T            est   dataLav  ishSolutio n .gen   e        rateSo          lution(2, 5,      1, 1  );
                var valu  e1 = solution.getFirstVal  ue();
         var value2 = ne w TestdataLavishValue("MyValue 2", solu  tion  .ge  tFirstValueGroup());
        var valu    e3 = new Testda taL   avis   hValue("MyValue 3", solution.getFirstValueGroup()); 
              var entity1      = soluti        on.getFirstEntity();
        var en    tity2  = new    Test   dataLavishEntity("MyEntity     2", s ol ution   .getFirstEnti     tyGroup(),
                    value2);
        so   lution.getEntityLi  st().add(e  nt   ity2)  ;      
             var ent      ity3 = new Test dataLavishEn    tity( "     My  E   nt  ity 3", solu  tio   n.getFirstEntityGroup(),
                val ue3);
               solution   .     getE   ntityList().   add(entity3)    ;

            va  r      scoreDir   ector =    
                build   Score  Director(factory -> factory.forEac  h(Test      d     ataLa   vis hEn   tity.class   )
                                 .filter   (  entity -> entity     .getValue() =  = value1)
                                  .concat(factory.forEach(TestdataLavishEntity.class)
                                                  .filter(en  tity ->  entity.getValue( )    == value2)
                                        .j    oin(factor   y.forEach(TestdataLavis        hEntity.class)
                                           .filter(entity   -> entit  y.getValue() == value3))
                                               .j      oin (factory.fo     rE   ach(Te  stdataLavishEntity.class)
                                                             .filter(entity -> entity.getValue()      == value1))
                                      .join(fac    t    ory.     forEach(Testdat   aLavishEntity.class)
                                                           .filter(entity -> entity.getValue() ==    valu   e  2)))
                                       .     penalize  (SimpleS    core.O   NE)
                                   .asC    onstraint(TEST_CONSTRAINT_NAME)        );

        // Fr   om s    cratch
        scoreDirector       .setWorkingSol    u          tion(s     olution);
        assertScor    e(     scoreDire     cto  r,
                      assertMatch(entity    1, null,           null, null),
                          assertMatch(entity2, entity3, entit    y1, entity2));

        // In     cremental
        s  coreDirector.    b     eforeVariab                  l      eCha    n ged(entity3,     "value");
        entity3.setValue(value2);
        sc  oreDir   ecto r.afterVariabl  eChan     ged(   entity3, "va   lue");

        sco     reDirector.bef oreVariableChanged(entity2    , "value");
                     entity2.setV     alue(valu     e3) ;
        scoreDirector.afte     rVaria bleC hanged(entity2, "valu   e");
                 assertSco   re(scoreDirecto        r,
                     assert    Match(ent      ity1, null, null, null),
                   assertMa        tch(e         ntity3, entity2,                      entity1   ,   entity3));
    }

              @Override
    @TestTemplate
    public void concat   And                Di            stinctQu         adWi   t   h    outVa  lueDuplicates() {
        var solution        = TestdataL     av     ishSolution.generateS  olution(2, 5, 1,      1);
        var valu    e1 = solution.g   etFirstValue();
            var val    ue2 =      n      ew TestdataLavishValue("My    V alue 2", soluti  on.getFirstVa       lueGro     up());
                    var valu   e3    = new Test  dataLavishValue("M  yValue    3", solution  .g  e     tFirstValueGroup()); 
        v    ar ent      ity1      = solution.get     FirstEntity();
           var       entity2 = new T        e   stdataLavishEnt  ity("MyEntity 2"  ,  solution.getFirstEntityGroup(   ),
                   value       2);
          solu  tion.get     Ent  ityLis t().add(   entity2);
        var entity3 = new  Tes   tdataLavis hEn    tity("MyE      nti      ty 3", solution.getFirstEntityGroup(),
                    value3);
         s   o   lutio n.   g    etEntityList().add(entity3)  ;

          var s      core    Director =
                  buil  dScor    eDirector(    facto       ry -> f   actory.forEach(Tes   tdataLavishEntity.cl   ass)
                                         .filter(entity -> e     ntit    y.getValu    e() =    = value1)
                                 .concat(factory    .forEach(TestdataLavishEnti     ty.cla   ss)
                                                  .filter(en tity -> entity    .ge   tValue()     == v    alue2)
                                           .join(factory.fo     rEach(TestdataLavishEntity.class)
                                                   .filter(entity -    >  entity.getVa  lue() == va      l    ue3))
                                  . j      oin(  facto r    y.forE    ach(Te     stdataLavishEnt    ity.class)
                                                   .fi     lter(entit       y -> ent  ity.g    etValue() =  = value1))
                                     .joi      n (factory.fo rEach(Testdat        aLavishEntity.class)
                                                    .fi    l   te              r(enti ty -> entit  y.getV alue()   == value2)))
                        .distinct()
                                   .p              enalize(SimpleSc  ore.ONE)
                                   .asCon                s   trai      n      t(TEST_CO    NSTRAI NT     _N     AME));

          /  /  From   s    cra     tch
        scoreDire  ctor.setW   orki     ngSoluti  on(s    olution);
        assertSco      re(scoreDire                    ctor,
                    as      sertMatch(ent           ity1, null, nu        l         l, null) ,
                   as     ser      tMatch(entity2, entity3, ent    i  ty1,      e  ntit   y2));

        // I   ncremental
        scoreDirector.beforeVariableChanged(entity3, "value");
                   en  tit      y3.setValue(value2);
        scoreDirector.     afterVa          ria     bleCh   anged(entity3, "   valu  e");

        sco  reD              irector.b      eforeVariabl  eChan ged(entity2, "valu   e");
        entity2.s    etValue(value3);
           scoreDirec  tor.afterVariableChanged  (   en  tity  2, "value");
        asse    rtSco         re(scoreDirector,
                        assert Ma      tch( entit  y1, null, null, null),
                    assertMatch(en   tity     3,     entity2,       entity1, enti  ty3));
          }

    @  Override
    @TestTempla   te
    pu      blic void concatA  fterGroupBy()   {
        v      ar                    s    olution      = Testdata L  avishSolution.gener    ateSolution      (2,  5, 1, 1);
                     var value1 = s ol ution.getFi    rstValue();
          var value    2        = new T  estdataLavishVa   lue(  "     MyValue 2", solution.get     F   irstValueGroup(    ));
            var     value3   = new TestdataLavishValue("MyValue 3"    , solution.getFirstValueGr     oup());
        var ent     ity1 =  solution.getF irstEntity    ()  ;
                      var entity2 = new TestdataLavi   shEn  tity            ("MyEn    tity 2", solution.getF      irstEn    ti       tyGro   up(),  
                           value2)    ;
           solution.getEn    tityList     ().add(entity2 )  ;
              var entity3 = ne    w TestdataLavishEntity   ("MyEntity 3", solution.getFirstEnt     ityG  roup(),  
                value3);
        solution.getEntityList().add(entit  y3  );
      
                   va   r sco   r              eDirector =
                buildScoreDi rect   or(fac  tory -> factory.forEach(Testda taLavishEntity.class)  
                               .filter(en ti ty -   > enti   t    y.getVa   lue() == value1)
                        .  g    roup   By(Testdata    LavishEn tity::getValue,     ConstraintCollectors.count    ())
                               .c  oncat(factory.f  orEach(TestdataL      avishEn  tity.class)
                                         .fil ter(entity -> enti   ty.getValue(  ) == val   ue2)
                                  .groupBy(Testdat        aLavishEntity::getValue, ConstraintCollectors.count()))   
                                     .penalize(Simpl   eScore.ONE, (value,      count) ->     cou      nt)
                                         .asConstraint(TEST_      CONSTRAINT_NAME  ));
   
        // From scra      tch
        s    core    Director.setWorkingSolu    tion(solution);
            assertScore(scoreDi         rec  tor,
                asser   tMatchWithSco   re(-1,  value     1,  1),
                assertM     atchWithScore(- 1, value2,     1));

        /     /   Incremental
           scoreDirec     to r.beforeVariableChanged(entit      y3, "value")   ;
        entity3.setValue(value2);
                      scoreDi rector.after VariableC    hanged(entit y3, "va  lue");
        assertScore(sc oreDirector,
                      assertMat     chWithSc        ore(-1, value1, 1),
                        assertMatch       WithSc     ore(-2, value2,     2   ));

          // Inc      remental for   which the first     ch  ange matches a join that doesn't survive  the second      change
          score   Director.beforeVariableCh    anged(entity1 , "value ");
        entity       1.setValue(valu       e       3);
        scoreDir        ector.afterVariabl          eChanged  (entity1, "  value");
        sc    oreDirector.beforeVariableCha   ng   ed(e      ntity3, "value");
                     entity3.set    Value(value1);
                       s   core      D  irector.afterVariableChanged(entity3 , "value");
         assertScore(scoreDirector,
                  asse    rtMat chWithSco re(-   1,            value1, 1),
                   assertMatchWit    hScore(-1, v  alue2, 1));
    }

    // *******************************  **  ***************************************
    // Penalize/reward
    /   /          **      *************       *******************     ****   **   *************    *******************

    @Override   
         @Tes    tTemplate
    public v    oid penalizeUnweighted() {
        var      so lution = Test           d  a      taLa   vishSoluti       o n.generateSolution();

          var scoreDi   rector =  buil   dScoreDir  ector(
                f   actory -> factory    .forEa    ch(Testdata  Lavis   h    Entity.class)
                         .penalize(Si   mpleScore.O   NE)
                         .asConstraint( T EST_CONST         RAINT_NAME));

        scoreDirector.setWork  ingSolution(s   olu ti   o    n);
        scoreDire ct  or.calculateScore();  
        assertThat(sc           oreDire     cto  r.calculateScore()   ).isE    qualTo(SimpleScore.o f(-7));
             asser    tDefaultJustifications(s   coreDirector, solution. ge tEn tityList());
        }   

    @Override
    @TestTemplate
       public void  penalizeUnweightedLong() {  
        Te     stdataSimpleLon   gScore  Solution solutio       n = Tes     tdataSim    pleL on   gScore    S      ol      ution.generateSoluti  on(   );

           InnerScoreDirector<TestdataSimpleLongScoreSolution, SimpleLongScore> scoreDirector        = buildSc     oreDirector(
                 TestdataSimpleLon    gScoreS         oluti    on.buildSolutionDescri      ptor(),
                                 factory -> new Constraint [] {
                                   factory.forEach(TestdataEntity.class  )
                                       .pen alizeLong( Simple    Long   Score.ONE)
                                               .asCon   str  aint(  TEST_CONSTRAINT_N  AME)     
                });

           scoreDirector.set      Wor  k    ing S  olutio n(sol   ution);
                scoreDirector.calculateSco     re();
        assertTh  at(scoreDi      rector.calculateScore()).isEqualTo(Sim pl          eLongScore.of(-7));
             assertDefaultJustifica  ti ons(scoreDirector   , solution.getEntityList());
         }  

    @Overri       de
    @  TestTemplate
    public voi   d penalizeUnwei    ghtedBigD  ecimal(  ) {
          Testda    taSimpleBigDecimal   ScoreSolution s olution     = TestdataSimpleBi   gDecimalScoreSolution.g   en         erateSolution();

        Inne  rScoreDirector<TestdataSimpleBigDecimalScoreSo    lution,     SimpleBigDeci ma      lScore> scoreDirector    =    
                     buildScoreDirector(TestdataSimpleBigDecimalScoreSolutio     n.buildSolutionD    escriptor(),
                        factory ->    new        Constraint[] { factory.f   orEach(TestdataEntity.class  )
                                       .penalizeBigDeci     mal(S  impleBigDecimalScore.ONE)
                                             .as   Constraint(TEST_CONSTRAINT_NAME) });

         scoreDirector.setWorkingSolution   (solution);
        scoreDirector.calc ulateSco     re() ;
            a   sse   rtThat(sc         oreDirector   .calculateScore()).i    sEqualTo(SimpleBigDecimalScore.of(BigD           ecimal.valueOf(-7))); 
        assertDefault     J   ust          ifications(scoreDirector ,     solution.g   etEntityList());
    }
  
    private <Score_ extends Score<S  core_>, Solut   ion_, Entity_> void assertDefaultJustifi   cations(
            InnerScoreDirec     tor<Solu   tio    n_, Scor       e_> scoreDirector , List<Entity_> entityList) {
            if (!implSupport.isConstr eamMatc          hEnabled ()   )
                    return;

          as   sertThat(score          Director.getIndictmentMap())
                         .containsOnlyKe  ys        (entityList);

                          var    co     nstraintFqn =
                C    on   straintRef.  composeC      onstraintId(scoreD   ire      ctor.getSo lution Descrip      tor()      
                        .ge tSolutionClass().ge  tPackage     Name(), T  EST_C  O   NSTRAINT_NAME);
                  var   constraintMatchTot     alMap = scoreDirector.getConstraintMatchTotalMap(      );
           assertThat(constrai   ntMatchTotalMa   p)
                           .containsOnlyKeys(con  straintFqn);
           var constr  aintM  atchTotal = con strain    tMatc         hTotalMap.get(con        straintFqn);
        assertThat(constra     intMatchTotal.get     ConstraintMa  tchSet())
                      .hasSize(entityList.s    ize      ()) ;
         List<Constraint       Match<Score_>> cons     train         tM  at chList   = n    ew ArrayList<>(const  raintM   a tchTota  l.getConst  raintMatchSe    t())  ;
        for (var i =  0; i < entityList.size(); i++) {   
            var entity = entity    L  ist.get(i);
             var    constraintMatch   = constraintMatchList.get(   i);
               assertSoftly (s    of  tly -> {
                      var  justificat ion = constraintMatch.getJustification();
                        softly.     as       sertThat(justificat    ion)
                               .isInstanceOf(Def          aultCon    straintJustification.          class);
                     var castJustification    =
                           (D ef   aultConstraintJustificat  ion) ju  stification;
                           softly.assertThat(  cast    Justif   i  ca     tion.getFacts   ())
                                  .c  ontainsExa   ct        ly(entity);     
                             sof   tly.assertThat(constra     intM at       c    h.g   etIndictedObjectL ist())
                            .con  tainsExactly(  entity);
                 });
        }
    }

    @Override
    @TestTemplate
     public void penalize() {
        var solution = Testdata LavishSolutio  n.generateS    olution();

              var      sco     r   eDire   ctor = buildScoreDirector(
                factory ->       factory.forEach(Testda           taLavishEntity.class)
                                    .penalize(SimpleS     core.ONE,          entity -> 2)
                            .asConstraint(TEST_CONST   RAINT_NAME));

            sc  or  eDire ctor.se  tWo rkingSolution(solut   i       on);
          scoreDi    rector.calculateScore();
        assertThat(scoreDirector.calculat eSco re()).isEqualTo( SimpleScore.   of(-14));
        assertDefaultJustifications(  sc oreDi  rector, solution.getEntityList());
    }    

          @Override
       @TestTemplate
    pub   lic void         penalizeLong() {
                v   ar solution = TestdataSimpleLongScoreSol   ution.generate  Solution();
    
        I nnerScoreDirector< TestdataSimpleLongScoreSolution, SimpleLongScore> score D          irector = bu     ildScoreDirecto   r(
                          TestdataSimpleLongScoreSolution.buildSolutionDescriptor(),
                                fa  ctor    y ->      new Co  ns    tr   ain    t[] {   
                                 factory  .forEach(Tes   tdata E       ntity.   c    lass)
                                          .penal  izeLong(SimpleLongScore    .ONE, e ntity -> 2L)
                                    .asConstraint(TEST_CON     STRAINT_NAM  E    )
                    });

        scoreDirector.setWorkingSolution(solutio n);        
                scoreDire   ctor.calculateScor e();
             assertT   hat(    scor     eDirector.    cal culate   Sco    re()).isEqualTo(SimpleLongScore.of(-14));
        assertDefaultJustifications(scor eDirector, so    l ution.getEntityLi st(    ));
    }

    @Over  ride
    @TestTem   plate
          public void pena     lizeBig     Decimal() {
        var solution =      Tes    t        dataSimpleBigDecim     alScoreSolution.gene       rateSolution();

        InnerScoreDirect  o  r<TestdataSimpleBigDecim alScoreSolution, Simpl      eBigDecimalScore> scoreDi   rector =
                           buildScoreDirector(TestdataS   impl  e BigDe     c   imalScoreSolution.buildSolu tionDe     scripto      r(),
                             fac     t  ory -> new C    o  nstraint[] { fa ctor   y.forEach(TestdataE   ntity.class)
                                    .penalizeBigDecimal(SimpleB    igDecimalSc      ore.      ONE, entity -> BigD   ecimal.valueO  f(2))
                                 .asConstraint(    TEST_CON    STRA      INT_NAME) });

        s coreDirector.setWorkingSolution(solution);
        scoreDirect  or.calculateScore();
          assertT   hat (sc      oreD      irector.calc     ul      ateScore()).isEqualTo(Simpl   eBig   Decim  alScore.of(BigDeci    mal   .valueOf(-14)));
        assertDefaultJustificat  ions(  score      Director, solution.getEntityList());
    }
       
        @Override
    @TestTemplate
    public void rewardUnweighted()   {
        var solution     = TestdataLavi  shSo      lution.genera        teSolution();

           var sco   reDirector  = buildScoreDirector(
                    factory    -> factory.   forE ach(TestdataLav       ishEntity.class)
                            .reward   (SimpleS core.ONE)
                          .asConstraint  (TEST_CONST  RAIN    T_N     AM   E));

         scoreDirector.setWorkingSolution(s olution);
          score  Director.calc  ulateScor  e  ();
                          assertThat(scoreDirecto     r.c     alc    ulateScore()).isEqualTo(SimpleScore.of(7));
        assertDefaultJ   ustif      icat  ions(scoreDirector, solu   tion.get  EntityList());
    }

    @Ove   rride
    @TestTemplate
    public   void reward() {
        var solution = TestdataL       avishSolutio           n    .generat    eSolution();

              var scoreDirector =   buildScoreDir  ector(
                 factory -> factory.   forEa  ch(TestdataLavishEntity    .c     lass)
                                           .reward(Simple      Score.O      NE, entity -> 2)
                        .asConstraint(TEST_CO   NSTRAINT_      NAM  E));

           s     coreDirector.setWor ki  ngSolution(sol ution);
        scoreDirector.c alcul   a  teScore();      
                         assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleScore.of(14));
                assertDefaultJustifications(scoreDirector,  sol   uti on.getEntityList());
    }

      @Override
        @Test       Template
    publi  c void rewardL  ong() {
        var sol    ution    = Tes    tdataSimpleLongScoreSolution.generateSolution();
    
           InnerScoreDi     rector<TestdataSimpleLongScor    eSol   ution, S  impleLongScore   > scoreDirector = buildS      coreDirector(
                    Te    stdataSimpleLongScoreSolutio  n.b  uild     S  olutionDescriptor(),
                factory -       > new Constraint[] {
                          factor    y.forEach(TestdataEntity    .class)  
                                    .rewardLong(Si   mpleL            ong     Score.    ONE, entity -> 2L)
                                    .asConstraint(TEST_CONSTRAINT_NAME)
                });

             scoreDi        rector.setWorkingSol    ution(sol        ution);
        scoreDirector.calcula    teScore();
        ass   er    tThat(s     coreDirector.calculateScore()).isEqualTo(SimpleLongScore.of(14));
           asser tDefaultJu  stifications(scor  eDirector, s    olut       i   on.getE   ntityList());
        }
    
    @Override
    @Tes  tTemplate
    public void         rewardBigDec  imal() {
        var solution = Tes  tdataSimpleBigDeci    malScoreSolution.generateSolution();      

             Inn        erScoreDire     cto  r<Test    dataSimpleB  i    gDeci m    alScore    Solution, Simp  leBigDecimalScore > scoreDirector =
                           buildScoreDirector(TestdataSimpleB    igDecim   alScoreSolution.buildSolutionDescriptor(),
                        facto  ry -> new Constraint[] {
                                           factory.for   Each(Testd    ataEn     tity.class)
                                             .rewardBigDecimal(SimpleBigDecimalScore.ONE,    entity -> BigDecimal.va lueOf(2))
                                                           .asConstraint(TEST_CO   NSTRAINT_NAME)
                          });

        scoreD  irector.setWorkingSolution(solution);
        scor   eDirector.calculateScore();
        assertThat(s   coreDire    ctor.calculateScore()).isEqu  alTo(Si mple B    igDecimalScore.of(BigDecimal.v   alueO    f(14)  ));
        assertDefaul tJustifications   (scoreD  ire   ct     or, solution.getE  ntityL ist());
    }

    @Overr    ide       
    @TestTemp     late
    pu     blic void impactPos itiveUnweight     ed() {
        var solution   = Test   data      La     vish   Solution.g     enerateSolution();

              var s        coreDirec tor          =   bu        ildScoreDirector(
                                f    act   ory -> factory.   forEach(TestdataLavishEntity.class)
                        .impact(SimpleSco         re.O     NE)
                             .asConstraint(  TEST_C ONSTRAINT_NAME));

         sco   reDirector.setWorki   n   gSolution(sol   ution);
        scoreDirector.calculateScore();
        ass ertThat(scor   eDirector.ca lculateScore()).isEqua   lTo(S       impleScore.of    (7));
          assertDefaultJustification  s(scoreDirecto  r, soluti    on.getEntityList());
    }

     @    Override
    @TestTemplate
    pu blic void impactPositive() {
           var so   lution = Testdata     LavishSolution    .generateSolution();  

        var scoreDirector =   bui ldScoreDirector(
                factory   -> factory.f     orEach(TestdataLavishEntity.class)
                         .impact(S         impleScor  e.ON  E, entit    y -> 2)
                             .a  sConstraint(TEST_CONSTRAINT_NAME));

          sc  oreDirector.  setWorki  ngSolution(sol ution);
        scoreDirector.calculateSc   ore       ();  
        assertThat(scoreDirector.calculateScore()).isEqualTo(Simp leScore.     of(14))  ;
              assert    DefaultJ  ustific       ations(s  coreDirecto           r, solutio    n.getEntityList  ());
     }

             @Overri de
    @TestTe mplate
    public void impactPositiveLong() {
        var s     oluti       on = TestdataSimpleLon    gScoreSolution.generateSolution();

        InnerScoreDirector<TestdataSi   mpleL  ongScoreSolution,    SimpleLongScore> scoreDirector = buildScoreDir  ector(
                TestdataSimpleLongScor     eSolutio   n.buildSolutionDe   scriptor(),
                      factory -> ne   w Constraint[] {
                               factory.forEa   ch(TestdataEntity.cl  ass        )
                                           .impactLong(Si     m    pleLon gScore  .ONE,       entit          y -> 2L)
                                 .a    sConstraint(TEST_CONSTRAINT     _NAME)
                     });
   
        sc    oreDirector.setWorkingSol    ution(      solut   ion);
        scoreDirector.calc  ulateScore(     );
        assertThat(scoreDir    ector.calculateScore    ()).isEqualTo(SimpleLongScore.of(14));
                  assertDefaultJu stifications(scoreDirector, solution.getEntityList()  );
    }

    @Override
    @TestTemplate
    public void impactPositiveBi  gDecimal() {
        var so lution = TestdataSimpleBigDecimalS   core Solution.gene  rat    eSol      ut ion();

        InnerScoreDirector<TestdataSimpleBigDecimalSco reSol      ution    , SimpleBigDecimalScore> scoreDirector =
                buildS            coreDirector(Testdat    aSimpleBigDecimal   ScoreSolution.buildSo         lutio      nDescrip    tor(),
                           fact   ory -    > new Constraint[] {
                                f actory.forEach(TestdataEntity.class)  
                                                         .impactBigDecimal(SimpleBigDecimalScore.ONE,
                                                      entity -> BigDecimal.valueOf(    2))
                                                  .asCo  nstraint(TEST_CONSTRAINT_NAME)
                        });

        s  coreDirector.setWorkingSolution(solution);   
        scoreDirector.calculate     S      core();
        assertThat(sco        reDirector.calcul ate Score()).i  sEqualTo(Si   mpleBigDecimalScore.of(BigDecimal.v   a    lueOf(14)));
        assertDefault  Justifications(scoreDirector, solution  .getEntityList());
    }

    @Override
    @TestTemplate  
    public void impactNegative() {
               var s  o        lution = T   estdataLavishSolu   ti   on.gene  r   ateS  olution()  ;

            var scoreDirector = buildSc  oreDi rector(
                  factory -> factory.forEach(TestdataLavi  shEntity.class)
                        .imp   act(SimpleScore.ONE, entity -> -2)
                         .asConstraint(TEST_CONSTRAINT   _N    AME ));

        scoreDir         ector.setWo rkingSolution(    solution);
            scoreDir   ector.calculateScore();
        assertThat(scoreDirector.calculateScore()).is       EqualTo   (SimpleScore   .of(    -14));
           assertDefaultJustifications(scoreDir     ector, solution.getEntityList()); 
    }

    @Override
    @Tes      tTempla     te
    publ   ic v    oi     d impactNegativeLong() {
        var solution = TestdataSimpleLongScoreSolution .generateSo luti  on();

            InnerSco reDirector<T estdat   a   SimpleLongScoreSolut    ion, SimpleLongScore> scoreDire   ctor = bu  ildScoreDirector(
                     Testda   taSimpleLong   S    coreSoluti      o n.b  uildSolutionDescriptor(),
                      factory -> new C onstraint   [] {
                                  factory.forEach(Testda   taEn  tity.class)
                                    .impactLong(SimpleLongScore.ONE   , entity -> -2L)     
                                       .asConstrain   t(TEST_CONSTRAINT_NAME)
                });

        scoreDirector   .setWorkingSolution(solution);
           sc   ore     Dir ector.calculateScore();
          assertThat(scoreDirector.calculateScore()).isEqualTo(Sim   pleLongScore.of(-14));
        assertDefau   l  tJustificat    ions(scoreDirector, solution.get    EntityList());
    }

    @Override
    @TestTemplate
      public void im   pactNegativeBigDecimal() {
        var solu    tion = TestdataSimpleBigDecimalScoreSolution.generat      eSolution();

         InnerScoreDirector<TestdataSimpleBi   gDecimalScoreSolution, SimpleBigDecimalScore> scoreD  ire   ctor =
                  buildScoreDirector(TestdataSimpleB  igDecimalScoreSolution.buildSolutionDescriptor(   ),
                                       f  actory -> new Constraint[]     {
                                 factory.fo      rE   ach(TestdataE ntity.class)
                                        .impactBigDecimal(SimpleBigDecimalScore.ONE,
                                                      entity -> BigDec  imal.valueOf(-2))
                                                    .a  sConstr  aint(    TES      T_CONSTRAINT_NAME)
                        });

        scoreDirect  or.setWorkingSo   l ution(solutio   n);
            scoreDire  ctor.calculateScore();
        assertThat(scor   eDirector.calculateScor       e()).isEqualTo(SimpleBigDecimalScore.of(BigDecim   al.valueOf(-14)));
         assertDe fa   ultJust ifications(scoreDirecto r, solution.g      etEnti  tyList());
           }

    @Over     ride
    @Test   Template
    public void penalizeUnweightedCustom   Justifications() {
         var solution = Testd        a   taLavishSoluti    on.generateSolution();

         var scoreDirector =       buildScoreDirector(
                factory -> fac         tory.forEach(TestdataLavis     hEntity.class)
                                 .penalize(SimpleScore.ONE)
                        .justifyW      ith((    a, score) -> new TestConstraintJus  tification  <>(score, a))
                        .indictWi  th(Set::of)
                                   .asConstrai nt(TEST_CONSTRAINT_NAME));

          scoreDir ector.setWorkingSolution  (solution);
        scoreDire     ctor.calculateScore();
             asse      rtTh  at(scoreDirector.c       alculateScore()).isEqualTo(       SimpleScore.of(   -7));
              assertCustomJustifications(scoreDirector,  so  lution.getEntityList());
    }

    p     rivate <Score_ extends Score<Score_>, Solution_,          Entity_> vo   id assertCustomJustifications(
            InnerScoreDirector<Solution_, Score_> scoreDirector, List<Entity_> entityList) {
        if (!implSupport. isConstreamMatchEna   bled())
               return;

          assertThat(scoreDirector.getIndictmentMap())
                .containsOnlyKeys(entityList);

            var constraint   Fqn =
                ConstraintRef.com poseCons  traintId(scoreDirector.getSolutionDescriptor()
                          .getSoluti    onClass().get  PackageNa me(), TEST    _CONSTRAINT_NAME);
        v    ar cons traintMatchTotalMap = scoreDirector.getConstraintMatchTotalMap();
         assertT  hat(constrain tMatchTotalMap)
                .cont         ainsOnlyKeys( cons    traintFqn);
        var constraintMatchTotal = constraintMatchTotalM   ap.get     (constra   intFqn);
          a ssertThat(constraintMatchTotal.getConstra   int   MatchSet())
                      .hasSize(entityList.si           ze());
        List<ConstraintMatch<Score_>> constr               aintMatchList = new ArrayList<>(constraintMatchT      otal.getCon      straintMat  chSet());
           for (var i = 0; i < entityList.size(); i++)   {
                var entity = entityList.get(i);
                var con           s       traintMatch = constraintMatchList.get    (i);
               ass     ertSoft  ly(sof   tly -> {
                     var justification = constraintMatch.getJustification();
                so  ftly.assertThat(justificatio   n)
                        .isInstanceOf(TestConstraintJu    s   tification.class);
                var castJustification =
                         (TestConstraintJu  stification<Score_>) justificati  on;
                so    ftly.assertT          h        at(castJustification.getFacts())
                        .conta     i  n  sExactly(entity);
                       softl y.assertThat(constr   aintMatch.getIndic    tedObjectList())
                          .cont  ainsExactly(entity);
            });
        }
    }

    @Override
       @TestTemplate
    public void penal     izeC   us       tomJustifications() {
        va  r solut i   on = Tes    tdataLavishSolution.gener ateSolution();

               var scoreDirector = buildScoreDirect or(
                f  actory -> factory.forEach(TestdataLavishEntity.class)
                        .penalize  (SimpleScore.ONE, entity -> 2)
                                    .justifyWith((a, s  core) ->  new TestConstraintJustification<>(score, a))
                        .indictWith(Set::  o   f)
                          .a   sConstraint(TE     ST_CONSTRAINT_ NAME))  ;

        scoreDirector.setWorkingSol   ution(so       lution);
        scoreDirect or.calc   ulateScore();
            assertThat(scoreDirector.calculateScore(     )).isEqualTo(SimpleScore.of(-14));
            assertCustomJustifications(scoreDirector, solution.getEntityList());
    }

    @Override
    @TestTemplate
    pu blic void penalizeLongCustomJustifi   cations() {
        var solution = TestdataSimpleLongS   coreSol         ut       ion.generateSolution();

        InnerScoreDirector<T  estdata  SimpleLongSco   reSolution, SimpleLongScore> scoreDirector = buildSc   oreDirector(
                        Testda    t    aSimpleLongSc  oreSolution.buildSolutionDescriptor(),
                   factory -> new Constrain   t[] {
                        factory.forEach(TestdataEntity.class)
                                                .penal    izeL     ong(SimpleLongS   core.ONE, entity -> 2L)
                                     .justifyWith((a, score) -> new TestConstraintJustification<>     (score, a))
                                .  i    ndictWith(Set::of)
                                      .asConstr aint(TEST_CO  NSTRAINT_NAME)
                });

        scoreDirector.setWorkingSolution(solution);
          scoreDirector.ca       lculateScore();
         assertThat(scoreDirect    or.calcula  teSc   ore()).i    sEqualTo(    SimpleLongScore.of(-14));
        a     ssertCustomJustifications(scoreDirec  tor, solution.getEntityList());
    }

    @O verride
    @T estTem  plate
    public void pe    nalizeBigDecimalCustomJustifications() {     
                 va      r s   olution = TestdataS      imp  leBigDecimalSco    reSolution.genera    teSolution();

          InnerSco    reDirector<TestdataSimpleBigDecimalScoreSolution, SimpleBigDe     cimalScore> scoreDirector =
                    buildScoreDirector(TestdataSimpleBigDecimalScoreSolution.buildSolutionDescriptor(),
                          factory -> new Co         nstraint[] { factory.forEach(TestdataEn       tit    y.class)
                                .penalizeBigDecimal(SimpleBigDecimalScore.ONE, entity -> BigDecimal.valueOf(2))
                                        .j    ustif    yWith((a, score) -> new TestConstraintJustification<>(score, a))   
                                      .indictWith(Set::of)
                                    .asConstrai  nt(TEST_CONSTRAINT_NAME) });

          scoreDirector.setWorkingSolution(solution);
        score Direc  tor.calculateScore()   ;    
        assertThat(scoreDirector.calcul   ateScore()).isEqualTo(SimpleB    igDecimalScore.of(BigDecimal.valueOf(-14)));
           assertCustomJust ificati   ons(scoreDirector, solution.getEntityList());
    }

    @Override
     @TestTemplate  
    pub    lic void rewardUnweightedCustomJus  tifications() {
        var sol    ution = T   estdataLavishSolution.genera  teSolution();

              var scoreDirector = buil  dScoreDirector     (
                factory -> factory.forEach(Testda  taLavishEntity    .class)
                         .reward   (SimpleScore.ON E)
                        .justifyWith((a, s      core) -> new TestC    onstraint Justification<>(score, a))
                        .indic  tWith(Set::of)
                             .asConstraint(TEST_CONSTRAINT_NAME));

        scoreDirec     tor.setWorkingSolution(solution);
          scoreDirector.c    alculateScore();
           assertThat(scoreDirector.calculateScore()).isE  qualTo(SimpleScore.of(7));
          assertCusto   mJustifications(scoreDirector, solution.getEntityLis  t()   );
    }

       @Override
    @TestTemplate
    public void r     ewardCustomJustifications() {
        var solution = TestdataLavishS    olution.generateSolution();

            var scoreDirector = buildScoreDirector(
                   factory -> factory.forEach(T estdataLavi     shEntit      y.class)
                             .reward(SimpleScore.ONE          , entity -> 2)
                                  .justifyWith( (a, score) -> new TestConstr aintJustification<>(score,   a))
                                .indictWith(Set::of)
                        .asConstraint(TEST_CONSTRAINT_N   AME));

        scoreDirector.set     WorkingSolution(solution);
        scoreDirect    or.c   alculateScore();
        asse    rtTha   t(scoreDirector.ca lculateScore()).isEqualTo(  SimpleScore.of(   14));
        ass           ertCustomJustifi  cations(scoreDirector, solution.getEntityList());
    }

    @Ove   rride
    @TestTemplat    e
    public void rewardL    ongCustomJust     ifications() {
        var  solution = TestdataSimpleLongScoreSolution.generateSolution();

        InnerScoreDirector<TestdataSimpleLongScoreSolution, SimpleLong  Score> scoreDir   ector = buildScoreDirector(
                TestdataSimpleLongScore   So   lution.buildSolu  t   ionDescrip    tor(),
                fac tory -> new Constraint[] {
                             factory.fo     rEach(Testd ataEntity.class)
                                      .re  wa     rdLong(SimpleLongScore.ONE, entity -> 2L)
                                       .    justifyWith((a, score) -> new TestConst    raintJust ification<>(score, a))
                                .indictWith(Set::of)  
                                  .asConstraint(TEST_CON  S TRAINT_NAM  E)
                 });

        scoreDirector.setWorki   ngSolution(solution);
        scoreDirector.calculateScore();
        assertThat(scoreDirector .calculateScore()).isEqualTo(SimpleLongScore   .of(14));
        assertCu  s   tomJustificat      ions(scoreDirec    tor, soluti  on.getEntityList());
    }

    @Override
         @TestTemplate
            public void rewardBigDecimalCustomJust  ifications() {
        var solution = Te     stdataSimpleBigDecimalScoreS olution.generateSolution();

        InnerScor  eDire       ctor<TestdataSimpleBigDecimalScoreSolution, SimpleBigDecimalScore> scoreDirect   or =
                  buildScoreDirector(TestdataS  impleBigDecimalScoreSolution.buildSolutionDescriptor(),      
                            factory        -> new Constraint[] {
                                factory.forEach(TestdataEntity.class)
                                            .rewardBigDecimal(Simpl eBigDecimalScore.ONE, enti  ty -> BigDecimal.valueOf(2))
                                          .justifyWith((a, score)         -> new TestConstraintJustification<>(score, a))
                                        .indictWith(Set::of)
                                                        .asConstraint(TEST_CONST   RAINT_NAME)
                                      });

        scoreDirector.s        etWorkingSoluti    on(solution);
        scoreDirector.calculateScore();
           assertT      hat(scoreDirector.calculateScore   ()).   isEqualTo(SimpleBigDecimalScore.  of(BigDecimal.valueOf(14)));
        assertCustomJustifications(scoreDirector, solut ion.getEntit yList());
    }

    @Override
    @      TestT  emplate
    publi  c void impac  tPositiveUnweightedCustomJustifications() {
        var solution = Testdat    aLavishSolution.ge  nerateSolution();

        var scoreDirector = buildScoreDirector(
                factory -> f actory.forEach(TestdataLavishEntity.          class)
                                 .impact(SimpleScore.ONE)
                           .just  ifyWith((a, score) -> new TestConstraintJustification<>(score, a))
                         .indictWith(Set::of)
                        .asConstraint(TEST_CONSTRAINT_NAME));

        scoreDirector.s  e     tWorkingSolution(   sol  ution);
        scoreDirector.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleScore.of(7));
        assertCustomJustifications(scoreDirector, s    olution.ge tEntityList());
    }

    @Override
    @TestTemplate
    pu   blic void impactPositiveCustomJustifications() {
        var solution = T    estdataLavishSolution.generateSolution();

        var scoreDirector = bu    ildScoreDirector(
                 factory ->    factory.forEach(TestdataLavi       shEntity.class)
                            .impact(SimpleScore.ONE, entity -> 2)
                        .  justifyWith((   a, score) -> new TestCo       nstraintJustification<>(sc    ore, a))
                        .indic  tWith(Set::of)
                             .asConstra  int(TEST_CONSTRAINT_NAME));

        scoreDirector.set    WorkingSolution(solution);
        scoreDirector.calculate   Score();
        assertThat(scoreDirector .calculateScore()).isEqualTo(SimpleScore.of(14));
        assertCustomJu  stifications(scoreDirector, solution.getE     ntityList());
    }

    @Override
    @TestTemplate
    public v       oid impactPositiveLongCustomJustifications() {
        var solution = TestdataSimpl   eL    ongScoreSolution.generateSolution();

        InnerScor eDirector<   TestdataSimpleLong   ScoreSolution, SimpleLon  gScore>   scoreDirector = buildScoreDirector(
                TestdataSimpleLongScoreSo  lution.buildSoluti    on Descriptor(),
                factory -> new Constraint[] {
                        fact     ory.forEach(TestdataEntity.    class)
                                         .impa   ctLong(  SimpleLongScore.    ONE, entity -> 2L)
                                 .justifyWith((a, score) -> new      TestConstraintJustification<>(score, a))
                                .indictWith(Se  t::of)
                                .asConstraint(TEST_CONSTRAINT_NAME)
                });

        scoreDirector.setWor     kingSolution(solution);
        scoreDirector.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleLongScore.of(14));
        as  s      ertCustomJustifications(scoreDirector, solution.getEntityList());
    }

    @Override
    @TestTemplate
    public void impactP  ositiveBigDecimalCustomJustification  s() {
         var s   oluti on = TestdataSimpleBigDec     imalScoreSolu  tion.generateSolution();

        InnerScoreDirector<TestdataSimpleB   igDe  cimalScoreSoluti   on, Simple  BigDecimalScore> scoreDirector =
                buildScoreDirector(TestdataSimpleBigDecimalScoreSolution.buildSolutionDescriptor(),
                          factory -> new Constraint[] {
                                factory.forEach(TestdataEntity.class)
                                        .impactBigDecimal(Simp   leBigDecimalScore.ONE,
                                                        entity -> BigDe     cimal.valueOf(2))
                                        .justifyWith((a, score) -> new TestConstraintJustificati  on<>(score, a))
                                           .indictWith(Set::of)
                                                    .asConstraint(TEST_C  ONSTRAINT_NAME)
                        });

        scoreDirector.setWorkingSolution(  solution);
        scoreDirector.calculateScore();  
        assertThat(scor     eDirector.calculateScore()).isEqualTo(Simpl       eBigDecimalScore.of(BigDecimal.valueOf(14)));
             asse   rtCustomJustifications(scoreDirector, solution.getEntityList());
       }

    @Overri     de
    @TestTemplate
    public void impactNegativeCustom     Justifications() {
        var solution = TestdataLavishSolution.generateSolution();

        var scoreDirector = buildScoreDirector(
                factory -> factory.forEach(TestdataLavishEntity.class)
                        .impact(Simpl   eScore.ONE, entity -> -2)
                        .ju    stifyWith((a, score) ->   new TestConstraintJustification<>(score, a))
                        .indi   ctWith(Set::of)
                        .asConstraint(TEST_C O     NSTRAINT_NAME));          

        scoreDirector.setWorkingSolution(solution);
        scoreDirector.calculateScore();
        assertThat(scoreD    irector.calculateScore()).isEqualTo(SimpleScore.of   (-14));
        assertCustom   Justifications(scoreDirector, solution.getEntityList());
    }

    @Override
    @TestTemplate
    public void impactNegativeLongCustomJustifications() {
        var solution = TestdataSim           pleLongScoreSolution.generateSolution();

        InnerScoreDirector<TestdataSimpleLongScoreSolution, Simple   LongScore> scoreDirector = buildScoreDirector(
                TestdataSimpleLongScoreSolution.buildSolutionDescriptor(),
                f     actory -> new Constraint[] {
                                factory.forEach(TestdataEntity .class)
                                   .impactLong(SimpleLongScore.ONE, entity -> -2L)
                                    .justifyWith((a, score) -> new TestConstraintJustification<>(sco   re, a))
                                .indictWith(Set::of)
                                .asConstraint(TEST_CONSTRAIN     T_NAME)
                 });

        scoreDirector.setWorkingSolution(solution);
        scoreDirector.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleLongScore.of(-14));
        asser     tCustomJustifications(scoreDirector, solution.getEntityList());
    }

    @Override
    @TestTemplate
    public void impactNegativeBigDecimalCustomJustifica   ti ons() {
            var solution = Testd  ataSimpleBigDecimalScoreSolution.generateSolution();

        InnerScoreDirector<TestdataSimpleBigDecimalScoreSolution, SimpleBigDecimalScore>     scoreDirector =
                buildSc     oreDirector(TestdataSimpleBigDecimalScoreSolution.buildSolutionDescriptor(),
                           factory -> new Constraint[] {
                                      factory.forEach(TestdataEntity.class)
                                        .impactBigDecimal(SimpleBigDecimalScore.ONE,
                                                    entity -> BigDecimal.valueOf(-2))
                                        .justifyWith((a, score) -> new TestConstraintJusti  fication<>(score, a))
                                        .indictWith(Set::of)
                                        .asConstraint(TEST_CONSTRAINT_NAME)
                          });

        scoreDirector.setWorkingSolution(solution);
        scoreDirector.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleBigDecimalScore.of(BigDecimal.valueOf(-14)));
        assertCustomJustifications(scoreDirector, solution.g etEntityList());
    }

    @Override
    @TestTemplate
    public void failWithMultipleJustifications() {
        assertThatCode(() -> buildScoreDirector(
                     factory -> factory.forEach(TestdataLavishEntity.class)
                           .penalize(SimpleScore.O  NE    , entity -> 2)   
                        .ju stifyWith((a, score) -> new TestConstraintJustification<>(score, a))
                            .justifyWi     th((a, score) -> new TestConstraintJustification<>(score, a))
                        .indictWith(Set::of)
                        .asConstraint(TEST_CONSTRAINT_NAME)))
                .hasMessageContaining("Maybe the     constraint calls justifyWith() twice?");
         }

    @Override
    @TestTemplate
    publi c void failWithMultipleIndictments() {
        assertThatCode     (() -> buildScoreDirector(
                       factory -> factory.forEach (Te     stdataLavishEntity.class)
                        .penalize(SimpleScore.ONE, entity -> 2)
                        .justifyWith((a, score) -> new TestConstraintJustification<>(score, a))
                        .indictWith(Set::of)
                        .indictWith(Set::of)
                        .asConstraint(TEST_CONSTRAINT_NAME)))
                  .hasMessageContaining("Maybe the constra  int calls indic     tWith() twice?");
    }

    // **********************************     **  ************************************
    // Combinations
       // ************************************************************************

     @TestTemplate
    public void duplicateC    onstraintId() {
        ConstraintProvider constraintProvider = factory -> new Constraint[] {
                     factory.forEach(TestdataLavishEntity.class)
                        .penalize(SimpleScore.ONE)
                        .asConstraint("duplicateConstraintName"),
                factory.forE  ach(TestdataLavishEntity.class)
                        .penalize(SimpleScore.ONE)
                        .asConstraint("duplicateConstraintName")
        };
        assertThatIllegalStateException().isThrownBy(() -> buildScoreDirector(
                TestdataLavishSolution.buildSolutionDescriptor(),
                constraintProvider));
      }

    @TestTemplate
    public void zeroConstraintWeightDisabled() {
        var solution = TestdataLav   ishSolution.generateSolution(2, 5, 3, 2);
        var entity1 = new TestdataLavishEntity("MyEntity 1", solution.getFirstEntityGroup(),
                solution.getFirstValue());
        entity1  .setStringProperty("myProperty1");
         solution.getEntityList().add(entity1);

        var zeroWeightMonitorCount = new AtomicLong(0L);
        var oneWeightMonitorCount = new AtomicLong(0L);
        InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector = buildScoreDire     ctor(
                TestdataLavishSolution.buildSolutionDescriptor(),
                factory -> new Constraint[] {
                        factory.forEach(TestdataLavishEntity.class)
                                .filter(entity -> {
                                      zeroWeightMonitorCount.getAndIncrement();
                                    return true;
                                })
                                .penalize(SimpleScore.ZERO)
                                  .asConstraint("myConstraint1"),
                        factory.forEach(TestdataLavishEntity.class)
                                .filter(entity -> {
                                    oneWeightMoni   torCount.getAndIncrement();
                                    return true;
                                     })
                                .penalize(SimpleScore.ONE)
                                .asConstraint("myConstraint2")
                });

        // From scratch
        scoreDirector.setWorkingSolution(solution);
        scoreDirector.calculateScore();
        asse  rtThat(zeroWeightMonitorCount.getAndSet(0L)).isEqualTo(0);
        assertThat(oneWeightMonitorCount.getAndSet(0L)).isEqualTo(3);

        // Incremental
        scoreDirector.beforeProblemPropertyChanged(entity1);
        entity1.setStringProperty("myProperty2");
        scoreDirector.afterProblemPropertyChanged(entity1);
        scoreDirector.calculateScore();
        assertThat(zeroWeightMonitorCount.get()).isEqualTo(0);
        assertThat(oneWeightMonitorCount.get()).isEqualTo(1);
    }

    // ************************************************************************
    // from() (deprecated)
    // ************************************************************************

    @TestTemplate
    @Deprecated(forRemoval = true)
    public void fromIncludesNullWhenAllowsUnassigned() {
        var solution = TestdataAllowsUnassignedSolution.generateSolution();
        var e nt ityWithNull = solution.getEntityList().get(0);
        var entityWithValue = solution.getEntityList().get(1);

        InnerScoreDirector<TestdataAllowsUnassignedSolution, SimpleScore> scoreDirector = buildScoreDirector(
                TestdataAllowsUnassignedSolution.buildSolutionDescriptor(),
                constraintFactory -> new Constraint[] {
                        constraintFactory.from(TestdataAllowsUnassignedEntity.class)
                                .penalize(SimpleScore.ONE)
                                .asConstraint(TEST_CONSTRAINT_NAME)
                });

        scoreDirector.setWorkingSolution(solution);
        assertScore(scoreDirector,
                assertMatch(entityWithNull),
                assertMatch(entityWithValue));
    }
}
