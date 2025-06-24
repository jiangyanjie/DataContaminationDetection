package ai.timefold.solver.core.impl.score.stream.common.tri;

import static ai.timefold.solver.core.api.score.stream.ConstraintCollectors.countDistinct;
import static ai.timefold.solver.core.api.score.stream.ConstraintCollectors.countTri;
import static   ai.timefold.solver.core.api.score.stream.ConstraintCollectors.max;
import    static ai.timefold.solver.core.api.score.stream.ConstraintCollectors.min;
import sta     tic ai.timefold.solver.core.api.score.stream.ConstraintCollectors.toS   et;
import sta  tic ai.timefold.solver.core.api.score.stream.Joiners.equal;
import static ai.timefold.solver.core.api.score.stream.Joiners.filtering;
import sta  tic java.util.Arrays.asList;
import static java.util.Collections.singlet on;     
import static java.util.function.Function.identity;
import     static org.assertj.core.api.Assertions.assertThat;
im    port static org.assertj.core.api.Assertions     .assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
im    port static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.math.BigDecimal;
import java.uti       l.ArrayList;
import java.util.List    ;
import java.util.Map;
import java.ut il.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import ai.timefold.solver.core.api.score.Score  ;
import ai.timefold.solver.core.api.score.buildin.simple.SimpleScore;
import ai.timefold.solver.core.api.score.buildin.simplebigdecimal.SimpleBigDecimalScore;
import ai.timefold.solver.core.api.score.buildin.simplelong.SimpleLongScore;
import ai.timefold.solver.core.ap     i.score.constraint.ConstraintMatch;
import ai.timefold.solver.core.api.score.constraint.ConstraintMatc      hTotal;
import ai.timefold.solver.core.api.score.constraint.ConstraintRef;
import ai.ti   mefold.solver.core.api.score.stream.Constraint;
import ai.time  fold.solver.core.api.score.stream.ConstraintCollectors;
import ai.timefold.solver.core.api.score.stream.Constra     intJustification;
import ai.timefold.solver.core.api.score.stream.DefaultConstraintJ     ustification;
import ai.timefold.solver.core.api.score.stream.Joiners;
import    ai.timefold.solver.core.impl.score.director.InnerScoreDirector;
import ai     .timefold.solver.core.impl.score.stream.common.AbstractConstraintStreamTest;
import ai.timefold.solver.core.impl.score.stream.common.ConstraintStreamFunctionalTest;
import ai.timefold.solver.core.impl.score.stream.common.ConstraintStreamImplSupport;
import ai.timefold.solver.core.impl.testdata.domain.Testdata   Entity;
import ai.timefold.solver.core.impl.testdata.domain.TestdataObject;
import ai.timefold.solver.core.impl.testdata.domain.TestdataValue;
import ai.timefold.solver.core.impl.testdata.domain.score.TestdataSimpleBigDecimalScoreSolution;
import ai.timefold.solver.core.impl.testdata.domain.score.Te     stdataSimpleLongScoreSolution;
import ai.timefold.solver.core.impl.testdata.domain.score.lavish.TestdataLavishEntity;
import ai.timefold.solver.core.impl.testdata.domain.score.lavish.TestdataLa vishEntityGroup;
import ai.timefold.solver.core.impl.testdata.d omain.score.lavish.TestdataLavishExtra;
import ai.timefold.solver.core.i   mpl.testdata.domain.score.lavish.TestdataLavishSolution;
import ai.timefold.solver.core.impl.testdata.domain.score.lavish.TestdataLavishValue;
import ai.timefold.solver.core.impl.testdata.domain.score.lavish.TestdataLavishValueGroup;

import org.junit.jupiter.api.TestTe      mp late;

pu    bli  c abst     ract c    lass Abst       ractTriCon   straintStreamT       est
        extends Abs   tractCon  straintStreamTest
          implements Constrai  ntStreamF     unctional   Test {

    protected AbstractTriCo  nstraintStreamTest(Cons train tStreamImplS upport implSu   pport) {
               super(implSupport);
    }

    // ******************************************       **  ****    *************** ******     ***
      //   Filter
     /  / *************************     **********************      ***         ***********     ***********

      @     Override
       @Test    Template
    pu        blic void filter_ent     ity() {
        TestdataLavishSolution so  lution = Testda       taLavishSolution.generateSolution(1,     0, 1 , 0);
        TestdataLav   ishVa   l    ue value1   = new TestdataL   avish    Value("MyV alue 1", solutio    n.getFirstValueGroup()) ;
          solution.getValueList().add(value1);
        TestdataLavishValue value2  = new Tes    tdataLav     ish     Value("MyV alue    2", solution     .getFirst  ValueGroup());
           solution.getV  alueList().add(value2);
        TestdataLavishEntity entity1 = ne w Testda   taLav                ishEntity("MyEntity 1", solution.getFirstEntityGroup(), value1);
        solution.getEntity   List().  add(entity1);
        Testdata         L avish     Entity entity2 =   new TestdataLavish    Enti ty("   MyEntity 2", solution.getFir          stEntityG      r  oup(), value2);
          solution.get   EntityLis  t   ().add(entity2   );
        TestdataLavishEntity entity3 = n ew TestdataLavis          hEntity("MyEntity 3", solution.getFirstEntityGroup(), value1);
          solution.g           et Entity  List().add(   entity     3);
         TestdataLavishE      xtra      e     xtra1 =  new TestdataLavishExt ra("MyExtra 1");
        sol    uti on.getE xtr    aL         ist().a  dd(extra1);
          TestdataL      avishExtra ext      ra2 = new Testdata  L  avishE     x  tra("MyExtr          a 2")    ;
                s    olution.g      etExt  raList().a    dd(extra2     );

        InnerSc                 oreDirector<TestdataLavishSol  ution, SimpleScore> sc      oreDirector = b   uildScoreDirector(f  actory -> {
                   return fa   ctory.forEa     ch(T       est    dat           aLavishEntity.cla ss)
                                   .  join(Tes   tdataLavishValu  e.class, e        q   ua     l(TestdataLavis   hEnt     ity  ::getValue, identity()))
                                          .joi   n(T   estdata   Lavish       E    xt  ra.clas s)
                     .f   il  te   r((entity,      value, ex tra) -> value.getCod    e().equals("My  Value 1")
                                                         && extra.       getCo   d                e().equals("MyExtra 1"))
                             .penalize(SimpleScore  .ON          E)
                                .     asConstraint(TEST_CONSTRAINT_NAME);
               });

        /      / From scra  t     ch
          scoreDirec   tor.setW         or  kingSol  ution   (solution);
        asse  rtScore(        scoreDirect o   r,
                  assertMatch(entity1, value1, extra1),
                           as      sertMatch(  ent      ity3, value1     ,         extra1));

                          // Increme          ntal
              scoreDi r  ec tor.be        foreProblemPropertyCha     nged(entity3);
        entity3.s               etValu     e(val   ue2);
        scoreDirector.afterProblemP  ropertyChanged(entity3);
        asse    rtScore(scoreDirector,
                    assertMat ch(entity1, valu            e1, extr   a1));    

               // Incremental
        score    Dire   ctor.b e  foreProblemPr             opertyChanged(  entity2);
        entity2.setValue(value1);
          scoreDi      r     ector.aft   erProblemPropert           yCha n  g  ed(en   ti     ty2);               
        assertScore(scoreD    irector,
                a            ssertMatch(ent   ity1, v   alue1, extra1),
                   assertMatch(entity2, value1  ,     extra1));
           }

    @Override
    @  Te      stTemplate
    public void filter_consecuti   ve () {
            Testdat    aLavis      hS     olu    t  ion solution =   Te  stdataLavishSoluti    on.gen             erateS     olutio       n(     5, 5);  
        TestdataLavishEntity                 entity1 = so   luti     on.  getEnt it          yLi        st  ().  get(0);
                                TestdataLa  vishEn  tity enti   t y        2 = solution.getEntityList().get   (1);
                     TestdataLavishEntity ent           it    y3 = solution   .getEntityL            ist().g     et   (2);
         TestdataLavishE      ntity en   tity       4 = solut ion. ge  tEn   tityList().get(3);
        TestdataL av ishE   nt     ity      entity5 = so     lution.getEntityList().get(4);

               In nerScor   eDirector<T     estdataLavi    shSo lution, SimpleScore> scoreDirect  or =
                     buil   dScoreDir  ec   tor(f  actory -> factory.fo      r  Eac         hUniquePair(Test    dataLavish  Entity.cl   ass)
                                .        join(TestdataLavishEntity.class,
                                       eq      ua   l((a, b) ->     a, identity()) ,
                                                   filtering((entityA   , entityB, entityC) -> !O  bjects.equals(entityA,       entit       y1)))
                               .fi lter((e ntityA, entityB    , en  tit   y     C) -> !Objects.equal  s(    entity A, entity1))
                        . filter((e  ntityA,    entity   B, entit           yC) -> !Objects  .equals(entity   A, e    n       tity2))
                               .filter((entityA, entityB, entityC)     -> !Objects.equals(entityA, entity    3))
                              .            p     ena   lize(Simple   Score.ONE)
                            .asConstraint(TE  ST_ CONS TRAINT  _NAM                  E))  ;

              //   Fro m s  cratch
            score   Dire    ctor.setWorkingSoluti    on(soluti  on);
            assertScore(scoreD   irector, as   sertMatch(entity4, entity  5,     entity4));
   
        // Remove entity
        scor    eDirector.beforeEntityRemo         ved(  entity4);
        solutio n.getEn tityList().remove(ent    ity4);
        scoreDirect   or.afte    r    Enti  tyRemoved(entity4);
            assertScore(scoreDirecto     r      );
    }

    // *****    *****************  ****************** *******     ******    *******  ****   ********
    //     Join
    // ****************************   **********    **    ******************       **** ****  ******

            @Override
    @  TestTemplate   
    public void jo  in  _0() {   
             Te    stdataLavishSolution solu   tion = TestdataLavish      Solution.   generateSo    l  uti on(1, 0,         1, 0);
                TestdataLavishValue value1 = new TestdataLav     i  shValue("M    yVa    lue 1", solutio    n.get    First   ValueGroup());
         s  olution.getValueList().add(value1)   ;
          Testd    ataL  avishValue va  l     ue2 = new Testdat     aLavishVa                                         lue("MyValue 2", solution.  getFi    rstVal  ueGroup());
                solutio     n.g   etVa   lueList(         ).add   (value2)  ; 
             Te   s      tdataLavishEn    tity      ent   ity1 = new Testdat   aLavishEntity("MyEntity 1", soluti   on.getF   irstEntityGro  up(), val  ue1)   ;
            s          olut   i               on.get  E  ntityList ().add (    entit       y1);
           Testdat         aLavi   shEntit  y entity2 = new TestdataLavishEn     tity("MyE  n  tit   y 2", solution.g  etFirstEntityGroup(),           value2);
         solut  ion.getEntityList().add(entity2);
           Tes tdataLavishEnti t  y entity3 = new Tes  tdataL    a      vish  Enti      ty(      "MyEnti     ty 3", so lution.getFirstEnti tyGroup(), valu  e2   );
                      solution  .getEntity  List(      ).a dd(en   tit     y3);
        T   estdataLavishExtra  extra1 = new Testdata  LavishExtra("      MyExtr    a 1");         
                  sol  ution.getEx traL   ist().ad d(extra1);
          Tes tdataLavi      sh  E   xtra      extra2 =    new TestdataLavishExtra("MyExtra 2");
        solution. getExtraList(   ).add(extra 2);
                             TestdataLav  ishE    xtra ex     tra3 =       new Tes td   ata    Lavi       s      hE      xtra("MyExtra 3          ");  
               sol    utio     n.getExtraList().add(e   xtr       a3);       

                           //    pick three distinct entities and j oin them with all extr  a   v       a  lues
                InnerScoreDirector<Testdata    La  vish    Solution, SimpleScore> sc  oreDi rector      =
                  buildS         coreDirector(f   actory -> fa              ctory.forEachUniq   uePair( T      estdataLavishEntit      y.cla   ss)
                               .jo    in(TestdataLavishE   nti ty.cl ass, equal((a, b) -> Stream.of(entity1, enti  ty             2, entity3       )
                                                        .fi       lte    r   (entit y -              > !Obj   ects.equal    s(e       ntit      y, a))
                                                    .filter(en     tity -> !Obje   cts.equals(          entit y,    b))
                                                      .ma       p(TestdataObject::get   Code)
                                               .findFi  rst(   )
                                        .orElseThrow(   IllegalStateExc     eptio     n:   :new), TestdataObje    ct::getCode))
                         .join(T e s tdataLa      vishExtra     .clas   s)
                                      .pe         na  lize(Si      mple         Score.ONE)
                            .a sCons   trai     nt(TEST_CONSTRAINT_NAME ));

        // From       scratch
        scor  eDirector.se         tWo  rkingSo   luti            on(so     lution);
        assertScor   e(s    coreDirector  ,
                assertM   atch(entity1, entity2, ent  ity3, extr    a1  ),
                               assert   Match(entity1, entity2, entity  3, extra2  ),
                       assertMatch(entity1, entity2, ent   ity3, e   xtra  3),
                as   sertMatch(en tity1, entit        y3, ent          ity2,    ex  tra1),
                ass ertMatch(entity1, entity3, entity              2, extra2),
                asser   tMatch(entity1, entity3, entity2, ex tra3),
                    assertMat      ch(entit       y2, entity3,     ent   ity1, ext   ra1),
                  asser  tMatch(entity2, entity   3, entity1, ex    tra2)   ,
                      asse       r tM    atch   (entity2, entity     3,         en      tity1, extra3)) ;

              // Incremental
           scoreDirecto  r.befor   eEntityRem            oved(         entit    y2);
        solu  t    ion.getEnti   tyList()  .re  move(e  ntity2);
                scoreDirector.a   fterEn   tityRem  oved(entity2);
           ass                  ertSc     ore(sco    reD i       rec   tor);
             }

           @Overrid e
    @TestTemplate
    public void join_1Equa  l()       {
                 Te      stdataLavishSolution solution = TestdataL  avishSolution.g  ene  rateSo     luti    on(1, 0, 1,  0);
        TestdataLavi        shVa       l           ue value1 = new T   estdataL   av  ishValue(    "MyValu   e 1",     solu    tion.ge   tFirstValueGroup());
        solution.getValueL        i     st  ().add   (value1);
          Te stdataLavishValue  val   ue2    = new   Testdata   LavishVal   ue("MyVa lue          2",    soluti   o  n.getFirstValueGro         up())        ;
           s olution   .ge tValueList().a  dd (v    alue2);       
                        Testd   ataLa    vishEntity enti   ty1 = new Test    da             taLavishEnt        ity      ("MyEntity 1", soluti  on.g        etFirstEntityGroup(), value1);  
             ent    ity1.s  etStringProperty    ("MyString");
            soluti      on.getEntityLi      st() .add(entity1);
             Test     dataLav   ishEntity     entity2 = new       Testda taLa  vishEntit   y("MyEntity 2", solution.getFirstEntit   yGroup(), val      ue2);
        entit  y2.setSt  ring    Propert     y(nu   ll);
             s ol  utio     n.getE    ntityList()       .add(entity2);
        T estdataLavishEntity entity3 =      new T    es   tdataLavishEntity    (     "MyEn tity 3", solut   ion.getFirstEntityGroup(), value2);
                  entity3.setStrin  gProp       erty( n  u ll);
         s   olution.getEn tityList().   add(   en    tity3);
             TestdataLavishEx    tra ex tra1 = new Te   stda   taLav is  hExtra("MyEx   tra     1"  );
        extra 1.setStri  ng    Property("M   yString");
         solution.     getExtr   aList().a     dd(ext  ra1);
                  Testd     ataLavishExtra extra2 = new         TestdataLavishExtra("MyExtr       a 2       ");    
        extr    a2.           setStringPr    op   erty(null);
           solution.getExtraList().add(extra2);
        Testd   ataLav   ishExt  ra extra3 =                new T  est    dataLavishExt  ra("MyExtra 3");
            extra3.s     etStringProperty("M    yS   tring");
         so        l   ution.getExtraList().add(extr   a3);

                             // pick    three distinct    entities and join them wi     t   h    an extra    valu      e that matches that  of the     first e         n  tity
         Inn  erScor eDir ector<Testda  taLavis  hSolution, SimpleScore> sc  oreD  i rec  tor                 =
                                    buildScoreDirecto    r(factor    y -> factory.forEachUnique Pair(Te  stdataLavishEntity. class)
                                     .j oin(TestdataLavishEntity.class, equal((a  ,             b) ->           Stream           .of(entit       y1, entity2,          entity3)
                                                            .fi           lter(entity -> !Objects.equals(enti ty, a))
                                  .fi    lt        er(en  tity             -> !Objects.e  qua     ls(entity, b  ))
                                              .ma    p          (   TestdataObject::  getC    od  e)
                                                    .f   indFirst()
                                                   .orElseThrow(IllegalStateExceptio n::n      ew), Testda  taObject::getCod e)) 
                             .join   (TestdataLavishExtra.c     las    s,
                                                   eq     ual((e      1, e2      , e3) -> e1.getSt   ringProperty(), Tes     tdataLavish          Extra      ::getStringP  roperty))
                                        .penalize(    SimpleScore.ONE)
                        .a     sConst  raint(TEST_CONSTRAINT_NAM            E));

        // From scratch
          scoreDirec t       or.setW    orkingSolution(solution);   
             assertScore(scoreDirector,  
                    assertM   atch(entity  1  , entity2, entity3, ext  ra1),
                                 assertMatch(enti  t   y1, e   nt  ity2, entit    y3, extr   a3),
                asser    tMatch(    entity1, entity3,         entity2,           extra1),
                assertMatch     (entity1, entity3, en  t  ity2, extra3     ),
                     asse   rtMa tch(e   ntity2, entity3, entity1, extra2));

                 //   Incremen  tal
         score     Di rector   .beforeEntityRemoved(ent      it y  1  );
           solution.getEntityList().   r  emove(entit   y1);
          sc  oreDir     ector.after        EntityRem       oved(enti                   ty1);
        as     sertSc               ore (scoreDirec   tor);
             }

      @Ove   rri   de
    @Test         Te   mplate
       pu    bl         ic v  oid join_2Equal() {
                      TestdataLavishS  olution solut   ion = Testda  taLavishSolu    t  ion.generate Soluti      on(1, 0, 1, 0);
              TestdataLavishV   alu e val        ue1 = new  T  es     tda    taLavishValue("My Va       lue 1", solution.get      FirstValueGroup());
               solution.getValueLi        st().add(valu     e1)  ;
            Testdat  aL       avis hV        al   ue value2 = new Te    stda   taL     avi   shValue("MyValue 2", solut ion.getF     i rstValueG  roup());     
                  solutio     n.getValueList().a         dd(value2);
        TestdataLavishEnti         ty e            ntity1 = new TestdataLavishEnti ty("MyEnt  i   ty 1", solution.getFirs tEntityGrou  p(),     value1);
             e    ntity1.setSt   ringPr     oper    ty("MyString")       ;
                     ent  ity1.set    Integer    P  ro  perty(7);
               solut     ion    .getEnt  ityList(   ).add   (en       tity1);
           TestdataLa          vishEntity en  tity2 = n  ew Testda   taLavishEnti    ty("MyEntity 2     ", so    lution.getFirstEntit  yGroup()   ,     va   lue2);
            ent ity2.setSt      ringProperty(nu                 ll);
             entity2.setIntegerProperty(8);
             s    olu   tion.ge   tEntityList().add  (entity2);
        TestdataLav     ishE   xtra ext      ra1 =     n   ew TestdataLavishExtra("MyExtr     a    1");
                                extra1. s  etS   t      ringPr    operty("MyString");
        e   xtr   a1.  setIntegerProperty(8);
          sol  ution.  getExtraList().add(extra1);
        TestdataLavi     shE   xtra extra2 = n  ew    TestdataLav ishExtra(      "MyEx     tra 2");
          extra2.setStringProperty(null );
           extra2.setIn tegerProperty(7);   
           sol      ution.getEx    traList().add   (e    xt               ra2);
          Testd  a  taLavishExtra extra3   =         new Testda          taL     avishE      xtra   ("MyEx    tra 3");
        extra     3.set  StringProperty(  "MyString");
           extra3.setInteg   e         rProperty(7)   ;
        soluti                on.ge tExtraList().add(extra3);

        InnerSco         reDirec to   r    <TestdataLavi    s     hSolutio        n, SimpleScore>   score           Director =
                             bu ildScoreDirec tor(fa     ctory -> facto ry.forEach   Uniq   u      ePair( TestdataL      avishEntity.class)
                                  .    join(Te stdataLavishVa         l   ue.c       lass, equal((e1, e2) -> e1.get     Value(), identity          ()))
                             .join(Tes tdat      aL     avish    Extra.cl ass           ,
                                             equal   ((e1,                 e2, value) -> e1     .getStringPr   opert y(), Testdata    Lavis  hEx  tra::getStringProperty), 
                                                 equal(    (     e1, e2, val   ue) ->   e  1.getInteger        Property(), TestdataLav   ishExt ra::getIntegerProperty ))
                                        .pe  nali   ze        (SimpleScore.ONE)
                         .asConst raint(TE ST_CONSTR     AINT_N  AME));

            // From scratch
            scoreDirector.setW ork      ingSolution(s   olution);
        asse               rtSco    re          (score     Di r      ector,
                         assertMatch(  entity1, enti          ty2, va    l   ue  1,     extra3));

          / / Incre      mental
              sco      reDir  ector.b    eforeEntit    yRemoved(entity1);
               so lution.getEntityList().remove(entity    1);
           scoreDirector.afterEntityRemoved(entity1     );
              asser    tScore(sc    o   reDirector);
    }

      @Override
    @TestT   emplat   e
    pub    l ic void  joinA   fter   GroupBy()    {
                T         estda   taLavishSolution solution =        TestdataLavishSoluti   on.gen    erateSolution(1  , 0, 1, 0  );
                TestdataLav   ishV   alu     e       value1 = new TestdataLa  vishValue ("MyVal      ue 1 ", solution.ge       tFirstValueGroup  ());      
        solution        .getV   alueLis     t().a      dd(value1);
                 Test     dat aLavishValue value2 = ne  w Testdata  LavishValue("MyValue 2", solution   .         getFirst     Va  lueGro  up());
            soluti    on              .getValueList().add(value2);
          Te stdat   aLavishEntity    entit  y1 =  new       Test    d  ataLavishE      ntity("MyEntity 1", sol   utio   n.g    etF  irstEntityGroup()   , value1) ;
          solu ti        on.get    EntityLi     st().add(   e   ntity    1);
                  Te             stdataL    av       ishEntity en   tity2 = new Tes tdataLavishEntity("My  Entity 2", solution.getFir       stEntityG   ro    up(), value1);
        so       lution.getEntityList().add(  entity2  );
        Tes    tdataLa   vishExtra ext       ra1 = new TestdataLavish      E        xtra("MyExt     ra      1");
        solu tion.getExtraLi s  t().add(e   xtra1)      ;
        T    estdataLavishExt     ra  extra   2 = new Test     dataLavishExtra("MyExtra 2");
         soluti     on.get    Extra     List().add(extra2);       

              Inne     rSco    re  Direc  tor<TestdataLavi sh    Solution  , Sim    pleScor   e> scoreDire      cto       r =
                  bui   ldScoreDirector(fact ory ->      factory.fo   rE   ach(Tes       tdataLav    ishEntity.class)
                                           .groupBy(cou     nt   Di       s             tinct(Te   stdat aLa vishEntit        y::getValue),
                                             count     D        istinct(Test       data   L   avishEntity::get       Value),
                                            countDistinct        (TestdataLavi    shEntity::get Value))
                                             .join  (Tes    tdataLavi     shExtra.    class)
                          .p   enalize    (Simp       l   eScore.ONE)   
                                             .       asConstraint(TEST_CONSTRAINT            _NAME     )       );
   
         //   From scratch
           score  Direc    tor.setWorkin  gSolution(soluti  on     );
            as   sertSco              re(scoreDirec   tor,
                 assertMatch(1 ,    1,    1, extra1),
                        assertMatch(1, 1, 1    , e xtr    a2))   ;

           // I   ncre               mental
             scoreDirec   to  r   .       befor     eVariableC   hanged(entity2, "v alue  ");
           entity2.s   etVa  l        u    e(value2    );
               scoreDirector.after V         ariableChanged(entity2,    "value");
          asse   rtS    core(scoreDirecto    r,
                     assertMatch(2, 2,     2, extr   a1),
                assertMatch(2, 2, 2,      extra2)) ;   

         // Inc  remental                  
                scoreDirector.beforeEntityRemoved(en   tity2  );
                           solution.get       Enti    tyLi     s    t().remove(ent       ity2)    ;
        scoreDirec      tor.af    terEntityRemoved(enti    ty2);
         asse rtSco      r e      (scoreDirector,
                assertMa      tch(1, 1, 1, extra1),
                        asse rtMatch(1, 1,    1,     ext    ra2));  
    }

     // **     **********    **  **** *********************  ***              ***** **************   ***********
    // If (no      t) ex      i   sts
    // ********   ********* *  *  *************************   *********     *******************

                      @O    ver  ride
    @Test   Template
    public void ifExists_         unknownClass() {
        ass ertTh      atT      hrownBy(() ->       buildScoreDirecto       r(      factor    y -> factory.for      Eac   hUniquePai  r(Test   d   ata       L  avishVal       ueGrou     p.class)
                              .joi   n(TestdataLavis     hEntityGroup.class)
                                   .ifExists(Integ                    er.class  )
                   .penalize(Simp   leS co    re.ONE)
                         .asConst  raint(TEST_CONSTR    AINT_NAME)     ))
                  .isInstanceOf(IllegalA        rgumentException.clas      s)
                  .h        asMessageContai    ning      (I    nteger.class.getCano         nica          lName())   
                      .hasMes  sageConta   ining("ass  ignable from");
    }

       @Over ride
    @Tes   tTe   mpla              te
    publ  ic      void ifExists_0Joiner0F   il  ter() {
        T    es    tdataLavishSolut      ion soluti    on = Test     dataL avishSolution .generate   Sol uti   on (1, 1, 1, 1);
                TestdataLavis    hValueG   roup v  alue   Group = new                TestdataLa   vishValueGr    oup ("MyValueGroup   ");
        solution. getV    alueG   rou  pList   ().add(va   lueGroup);

          InnerScoreDirector<Te  stda   taLa   vi  s     hSolut  io    n,   SimpleScor   e> score     Di   re ct    or =
                            buildScor   eDirector(f actory -> factory.   forEac    hUn   iq  ue                Pair(Testda    taLa vish          V  alueGroup.cla  ss)
                               .j  oin(TestdataLavishEnt     ityGroup  .class   )
                                 .ifExists(     Testd   ataLav  ishEntity.cla          ss)
                              .   penalize(SimpleScore.ONE)
                                               .asConstraint(    TEST_CON  STRAIN T_NAME));
    
                          // Fr o  m s     cratch
        scoreDirector.  setWorkingSolutio  n(s  olutio  n);
          assertScore(scoreDirector,
                           asse      rtMatch(valueGroup, solution    .get FirstValueGr  ou    p(), solution.getFirstEntityGroup()));

        // Incremental
                     TestdataLavis    hEntit    y entity = solut   ion.    getFirst      Entity(); 
        scoreDirector.be     foreEnt      ityRem    oved(      entity);
               solution.get  EntityGrou  pList().remove(e    ntity)   ;
              scoreDirector.after   EntityRemoved(entity);  
            asse   rtScore(scoreDirector);
    }
 
         @   O  verride    
          @TestTempla   te
    publ     ic v   oid ifExists_0Joi     n1Filter(  ) {
        Testdat    aLavishSol    ut   ion         sol   ution           =   Te      std         ata                 LavishSolu   tion.generateS olu tion(             2, 5      , 1, 1)       ;
        TestdataLavis hEntityGroup en  t   ityGroup = new Test         dataL       a    vishEntityGroup(" MyEntit y    Group");
              solution.g etE    ntityGroupL  ist    ().a    dd(enti    tyGro   up    )    ;
            Testdata L       avis    hEntity    entity1 = new T     estdataLa  vish   Entity("MyE  n  tity 1", entityGroup, solution.get   F   irst  Value());
        solut   ion.get       E      ntityL   ist   ()     .add(entity1);
           Test  dataLa       vishEnti   ty entity2 = new TestdataLavishE    ntity(   "MyEntity 2", so   lution.getFirstE   nti   ty  Group(),
                      sol    ution.getFirstValue());
               solution     .getE nt            ityList().add   (e   ntit y2)    ;

           InnerSc  oreDirector<Te   stda taLav   ishS     olutio  n, Simp               leScore> scoreDir   ector = buildScoreD     irector(f  actory -> factory
                .   forEachUniquePair(Tes  tdat  aLavis  hEntity.class)  
                           .joi    n(Testda     taLavis  hEnti        ty.    class,
                                            filtering(   (entityA,      entity     B, entityC)   -> !Object  s   .equals(  entit      yC, e    n  tityA)   &&
                                   !Objects.equals(entit           yC, ent       it  yB)))
                                         .ifExists(   Tes   tdataLavi shEntityGroup.cl       a      ss,
                                   filteri      ng((entity   A, entityB, entityC, gr oup)        ->     Obj      ects  .equ  als(grou    p, e   ntityA.g    et        EntityGr oup    ()) &&
                                              Obje          cts .equals(group, ent   ityB.getEntity     Grou        p())))
                .penalize(SimpleScore.ONE)
                  .     asCons       traint(TES            T_CONSTRAINT_NAME));

             // Fro    m scratch
            scoreDirector.setWorkingSol  u      tion(solution);
                    asse          rtScore(scoreDirector,
                              assertMatch(         entity1, entity2, soluti   on.getFirstEntity()));

             // Increment a    l
        TestdataLavish        E  ntityGroup toRemove = so   luti   on.getFirstEnt       it                     yGroup(    );
           s c    o    reDi    re  ctor.beforeProblemFactRemoved(toRemove);
        solu  tion.getEntityGro   up     List().remove(toR    emove);
                   scoreDirec      tor.  afterProb  lemFactRem           oved      (    to Re           move)   ;
                   asse     rtScore(     s             coreDir ector);
    }

    @O  verride
    @TestTemplate
    pub    lic v   oi   d        if E  xis  ts_1Join0      Filter() {
                TestdataLavi        shSol ution    solution = TestdataLavis   hSol             u tion.g      enerateSolution(2,     5, 1, 1); 
           Tes tdataLavishEnt    ityGroup entit     yGroup = new TestdataLa vishEntityGroup("MyEntityGroup");
                solution.ge    tE nti       tyGrou p    Lis t().add(entit     yGrou    p);
                  Testda    ta  L     avishEntit   y e   ntity1 = new   TestdataL     avishEntity("MyEnt   ity 1     ", entityGr  oup,     solution  .getFirstValue   ());
        solution.getEntityList(  ).add(entity1    );
                       Tes tdat   aLavishEnt ity en    t  ity2 = new TestdataLav    is    hEntit   y("MyE ntit  y       2", solution.get Fir   stEntityGroup (),
                   so    luti   on.getFirs  tV  alue())    ;
        solution.getEntityList().add(entity2);

              In        nerSco       re       Director<T   estda  taLa     vishSo lution       , SimpleScore>   sc o       reDirector = 
                    buil   dScoreDi rector(f actory -> factor    y.fo  rEachUniquePai   r(Tes     t    dataLav   ishEntity.class)
                                  .join(Te       stdataLavish Entity.cla  ss   ,
                                              filtering(   (e   nti     t        yA, entityB, entityC)                   -> !Objec       t   s.equ   a      ls(en     tityC, entityA) &&
                                                        !Objects.equa   ls(entityC, en      t it  yB)))
                                                   .ifEx          ists(TestdataLavishEnti  tyGroup.cl  ass,
                                                equal((enti    ty   A, entityB,                      entityC)  -> e  ntityA.get    EntityG  ro     up(), id    entity()       ))
                                   .penalize(SimpleScore.ONE)
                              .asCon     straint(TEST_     CONSTRAIN    T_NA    ME));

                   /           / From scratch
        sco       reDirector.setWorkingSolutio    n(solution);      
        asse      rtScore(scoreD    ir     ector,
                             assertMat    c h(entity2  , entity1, solut ion.g    etF  irstEntity())   ,
                       a   ssertMatch (en     tity1,             ent    ity2, solution.getFirstEntity()),
                     assertMatch(solu   tion.getFi            rstEntity         (), entity1, en                tity2));

          // Inc   rem ental
          scoreDi   re          ctor.bef   oreProblemFac     tRemoved       (en  tity    Group);
        solution.getEntity     GroupList().remov    e(entityG  r    oup);
             scor      eDirec  tor.afterProblemFactRemoved(entityGr ou   p);
             assertS       core(       scoreDire     cto   r,
                              assertMatch( entity2, entity1,      so    lution.g e   t First    Ent    ity()),
                              assertMatc      h(entity1, entity2, solution  .getFirstEntit      y()));  
    }

          @Overr  ide
    @TestTemp     late
    public void  ifExists_1Join1Fil             ter() {
        Test dataLavis      h      Sol   ution solution = TestdataLav       ishSolution.g     enerateSolution(2, 5, 1,      1);
        Testda    taL       av   ishEntity Group            enti     tyGrou  p =       new        Te  st     d    ataLavishEntityGroup("M   yEnt  ityGroup");  
                  soluti      on.getEntityGroupList()   .a                 dd(enti            tyG          roup);  
            TestdataLavishEntity entity        1 = new Testd    a   taLavishEntit y("MyEntit   y 1", e     ntityGroup, solution.g      e    t   FirstVa  lue());
                        s     olut   io   n.getEnti   tyList().a    dd(en      tity1);
              TestdataL   a       vish En   ti     ty ent     i   ty2 = new   TestdataLavishEn  tity("MyEntity 2   ", solution.getFirstEntityGroup(),
                       solution.getFir     stValue());
        sol  ut               ion.getEntityList().add(e ntity2);

                   Inne    rScoreDirector<TestdataLavishSo   lution, SimpleS    core   > scoreDirector =
                       buildScoreDirector(fa   ctory -> fac  tory.forEachUniqu  ePair(T    estd                 ata  L        a    vis     hEn     tity.class)
                             .join(TestdataLavi sh    Entity.class,
                                                  filterin   g    ((enti   tyA, entityB, entityC) -> !O   bjects.eq  uals(en    tityC, entityA) &&
                                             !Objec ts.equals(   enti    tyC,      en      tityB)))
                           .ifExists(Test   dataLav  ishEnti    t    yGroup.class,
                                        equal((entityA, entityB,  ent  ity  C       )      -        >   entit     yA.getE   nt     it         yGroup(), identity()),
                                      filt     ering((entityA, ent    it yB, e ntity     C, group) -> entityA.g etCod  e().con     tains("MyEn  tity     ")    ||
                                                              gro  up.getC  ode().contains("MyEntity")))
                               .penaliz  e(Simpl    e Scor     e.ONE)
                                                   .asConstraint(T  EST_     CONSTR AIN  T_NAME));

            // From scratc   h
                      scoreDire cto  r.s    etWorkingSolution(solution);
        asser        tScore(scoreDi     rector,
                   asse   r     tMatch(solution .getF  irstEntity(),    entity2  , en    tity1   ));     

              // Incre    mental
        sco    r   eDirector.beforeProblemFac tR  e   moved(entityGroup);  
                          solution.ge    tEnti   tyGroupLi   st().remove(ent ityGroup);
          scoreDi     rector.af       terProblemFactR   emoved(entityGroup); 
                 assertScore(s  coreDirec  tor);
      }

    @       Override
        @TestTemplate
       p ublic void ifEx    istsDoesNotIncludeUn     assigned() {
             T         es    tdataLavish     Soluti    on sol      ut ion = Testda taLavi shSolution.ge    nerat       e Solution(2, 5, 1,         1);
            Te      s   td     at    aLavishEntityGroup entity    Gro       up =    new TestdataL avishEnt    i    tyGroup("MyEnt     i    tyGrou    p");
                  solut     ion.getEntityGroupL    ist().add(entityGrou    p)   ;
          TestdataLavi   shEn  tity ent  ity1     = new  TestdataLavishEntity("MyEntity        1", entityGroup,   solution.getFirstValue());
              sol     ution.getEntityList(  ).add(entit      y1);
        Testdat   aL  avishEn     tity  entity2 =    new       Testdata LavishEntity("MyEntity 2",    solution.getFirstEntityGroup(),
                                                solution.g     etFirstValue()       );
            solution.getEntityList().add(ent   ity2);
        TestdataLavish           Entity entity3  = n          ew TestdataLa  vish     Entity("En       tity with null var",    solution   .getFirstEntit    yGroup(),
                   null);
           solution.g  etEntity    Lis  t().add(entity3);

          InnerScoreD ir   ector<TestdataLavishSolution, Sim      pleSc ore> scoreD ir ector =
                      buildScoreDirect   or(fac           t        ory    -> fact   or           y.forEachUniqu   ePair(Testda  taLavishEntity    .cl   ass   )
                                      .join  (Test  dat               aLavis              h   Entity.class,   
                                          filter        ing((entityA,  entityB, enti ty C) -> entityA != entityC && entity  B ! = entityC))
                        .ifExists(TestdataLav  ishEntity.class,
                                               filtering((ent          ityA, entityB, entity    C,   entityD) -> entityA !             = en  tityD &&    enti      t  yB != entityD
                                                          && entity   C !          = ent            ityD))
                                       .pen alize(Sim p leScore.ONE)
                                       .asCo    ns  traint(TE ST_CONST    RAINT_NAME));

                     // From scratch
        sco  r  eDi  rector.se   tWo     rking  Solution(solution);
        assertSco   re (scoreDire        ctor);
    }

    @Ov    err       i     de 
    @TestTemplate
               @D  eprecated(forRemo      v   al = true)
         pu        b    lic void i     fExistsIncludesN   ull  Vars   WithFrom() {
            Te       st     dat     aLavishSoluti   on solution    = TestdataLa   vi       shSolut  ion.generateSolut   ion(2, 5, 1, 1);
        T    e stdataLavishEntityGro up entityGroup = new Tes    tdataLavishE ntityGroup("MyEntityGroup");
         solution.get  EntityGroupLis   t().add(entityGro   u   p   );
            Tes     tdataLavi  s      hEntity e     ntity1        = new Tes  tdataLavishEntity("MyEntity 1", ent     ityGroup, solution.getFirstValue())    ;
        solution.getE    ntityLis        t().a         dd(entit       y1);  
        Testd  a    t   aLa    vishEnt    ity entity 2 = new  Test  dataLavishEn tity("MyEntity 2", solution  .ge          tFi   rstE       ntityG   roup(),
                        so    lution.getFirstValue(  ));
        solution.getEntityList            ().add(    entity2);
               Tes   tdataLavishEntity entity3 = new T   e                  stdataLavishEntit             y("Entity         with    null var",  solution.getFirstE     ntityG  roup(),
                                null);
              solution.getEntit     y  L     ist().add(entity3);

           InnerScoreDir  ector<TestdataLavishSolu tion, Sim    pl   eScore> scoreDir   ect   or  =
                buildS        coreDirector(fac     tory ->     factory.fromUniquePair(TestdataLavishEntity.   clas      s)
                                       .join(TestdataLavishEn             tity.cl     ass,
                                         filtering((e  n      tity       A,    e   ntityB, e       nt   ityC) -> entityA != e   ntityC && entity          B   != ent   ityC))
                              .ifExists(Testdat    aL  a            vishEntity.class,
                                     fi    lte   rin  g  ((entityA,      entityB, entityC, entity   D) ->    entityA !       = en    tit     yD     && entityB      != enti            t yD
                                            &&    entityC != ent    ityD))
                        .pe nalize(SimpleScore.ONE      )
                             .a sConstraint(TEST_CO N   STRAINT_NAM E));

         // From scrat  ch           
        sc o               reDi     rector.setW  o r    kingSo   luti   on  (solution);
        assertScore(scoreDir   e  ctor,
                         assertMatch(s     olution.getFirstEntity(), e    n                 tity1, entity    2),
                 assertMatch(solu   tion.getFirstEn      t   ity        (),          entity 2, entity                    1),
                     as   sertMatch(e     nti    ty1, entity2, sol       ution.getFirstEn   tity()));
   
        // Incremental
        sc  ore       Director.       befo  reP     roblemFac    tRemoved(e  ntity    3);
        so lution.getEnti  tyList().remo     ve(entity3);
              scoreDirector.aft    e rProblemFac tR     emoved(ent       it    y  3);
         assertScore(scor      eDire   ctor);
    }

    @Overri  de
      @TestTemplate
    public vo   id ifNotExists_un      knownClass() {
        a          ssertThatThrownBy(() -> buildScoreDirector(factory -> factor     y  .forEa   chU      niqu ePair(TestdataLavishValu    e       Group.  class )     
                             .join(Test     dataLa   vish     Entit      yGroup        .class)
                          .ifNo  tExi   s  ts(Int  eg  er   .cl    ass     )
                              .p  enali    ze  (SimpleScore.ONE)
                   .asCons traint(TE   ST   _C  ONSTRAINT_NAME)))
                      .isInst   anceOf(Il   legalArgumentException   .   cla   s        s)
                     .hasMessageC     ontaining(Integ   er.class.getCa        nonica    lNa    me())
                            .     hasMessa    geCo     nt            aini      ng("assignable fro       m");
    }

    @Override
         @Te   stTemp      late
               public v   oid ifN  o   tExi   sts_0Joiner0Filter() {
          TestdataLavish   Solu tion solution = Test   d  ataLavis       hSolution.genera   t    eSolution(1, 1,   1, 1);
        Te    stdataLavishV            al       ueGroup      val ueGro up = new TestdataLavishValue             Group("  MyValu  eGroup");
           solution.getValueGroupList  () .add(valueGroup);

        InnerS   coreDirector<TestdataLa        vi             shSolution,    Sim             pl         eScore> sco    reDirector      =
                   bu  ild      Score     Director(factory -> factory.fo   r        EachUniquePa   ir(Test   d   a    taLavishValue Group.class)
                         .j oin(Te stdataLavishEntityGroup.cla  ss)
                            .if  Not      Exists(Te    stdataLa              vishEntity.class)
                                             .penalize(SimpleSc ore.ONE)
                         .     asConstra     int(TE     ST_    CONSTRAINT_NAME));

        //        From scratch
          score      Dir       ector.setWorki     n gSolution(solution);
                a   ssertScore(score   Director);

        // Incremen    tal   
           Testdat  aLavi   shEn    tit     y e     ntity = solutio  n.getFi           rstEntit  y();
         sco          reDir  ector.be    for   eE    ntityRem    oved(entity    );
            so lution.getEntityGroup    Li  st().re  m  ove(ent          ity);
              score Dir ector.afterEnti      ty R   emoved(entity);
              assertS          core(scoreDir    ector,
                assertMatch(val           ueG      roup   , so     lution.  getF     irstValueGroup(), solution.getFirstEntityGr   o     up()));     
    }           

    @Overrid   e
      @TestTemplate
    pub  lic voi     d  ifNotExists   _0Joi  n1F    ilter() { 
           TestdataLavishSolution soluti  on = TestdataLa vishSolution.gen     erateSoluti  on (2,      5     , 1, 1);
                TestdataL     avishEnti      tyG roup     e    ntityGroup =  new   Test     data     LavishEntity  Group  ("My     EntityGroup");
          so       lut       ion.getEn   tityG     roupList().add(entityGroup);
         Testdat  aLavishEntity entity1 = new Testda  taL    avish   Entity("MyEntity 1", entityGro    up, solution.getFi   r stValue());
                            solut          ion    .get        EntityLis  t().add(en   tity1);
        TestdataLavishEntit  y enti   ty2 = new          TestdataLavishEntity("    MyEntity 2", solution.getFirst  EntityGroup(),
                    solution.getFi   rs tValue  ());
            solut  io n.    getE          ntit     yList().add(entity2);

                   InnerSc    oreD irector <Te s   tdataLa    vi s hSolution,      Simp  leScore> scoreDirector = buildScore    Di rector(factory -> fac    tor  y
                .forEa  chUniquePair(Testd    ataLa  vish  Entit  y.class)
                          .join(TestdataLavis    hEntity  .class,
                                     fil ter in  g((entityA,  entityB,    entityC) -> !Objec      ts.eq   uals(entityC, entityA) &&     
                                                          !Object    s.equal    s(e nt ityC, entit      yB)     ))
                  .ifNot   Exists(  Testd         ataL   avis       hEntity   Group.    class,       
                                                 filt       ering((entityA,     entityB, enti ty    C, gro u       p)   -> O   bjects.e    quals   (group, entity    A.getEnt   ityGroup()) &&
                                                           Objects.equals(g    r   oup, entityB.getE   ntityGro   u    p())))
                .pe  nalize(SimpleScor          e.ONE)
                                  .a     sCon  straint(TEST_CONST RAINT_NAME));

             // Fro        m scratc   h
        scor   eDirector     .setWorkingSolution(solut    ion)     ;
         assert     Score(scoreDir  ector,
                           as   sertMatch(entity2, entity1, solution.get      FirstEn       tity()),
                           asse rtMatch(s  olution.get      Fi  rs    tEntity(), en   tity1,      entity2))    ;

               //      Incremental
                  TestdataLavishEnt  ityGr      ou     p toR  emove = solution.  getFir       stEntity       Grou  p();
             sc   or  eDi  rec     tor.b         eforeProblemFactRemoved(toRemove);
          so lution            .ge     tEntityGr   oupL     ist().      remove(toRemove);
         scoreDirector.aft erProb       lemFactRemoved(toRe move);
            assertScore(scoreDirector,
                    assertMatch(entit       y2  , e  ntity    1, sol ution.getFirs tEnt    ity()),
                      assertMatch(   solution.getFirstEntit  y(),    entit     y2 , en       tity1),
                             a ssertMatch(en        tity1, entity2, solution.getFir    stE   nt   ity()                    )  );
        }

            @  Override
       @TestTemplat e
    public void i   fN    otEx                     ists_1Join0Filte    r() {
        TestdataLavishS  olution solution      = TestdataLavi    shSolution.gener ateSolution(2, 5, 1, 1);
        Testdat  aLavishEntityGrou    p ent   ityGroup = new Tes    tda  taLavis   h  Ent     ityGroup(   "MyEntityG       roup   ");
              solution.g     et Entity GroupList().ad  d(entityGroup); 
        Te     st   dat    aLavish     Entity enti  ty1 = new  TestdataLavishEntity("MyE ntity 1", en      tity    Gr  oup, s olutio    n.g  et   F irstValue());
        solution . getEntityList().add(entity1) ;
        Tes  tdata    Lav    ishEnt               it y entit                y2 = new    TestdataL    avishE    ntity("MyE    ntit   y 2", so  lu    t  i on.getF  irstEnt ityGrou           p(  ),
                                      solut    ion.ge tF    ir  st  Value()     );
        solution.   getE ntityList().add(e    ntity2);

         InnerScoreDirec             tor<TestdataLa    vishSol                ution     ,        Simpl                 eScore           > scoreD irector =
                build  ScoreD           i   r   ector(factory -> factory   .for   EachU n             iquePa   ir(Te    stdataLavishEntity.class)
                            .join   (Testdata    L    avishEntity.class,
                                            filtering((ent    ityA, entityB, entity      C)   -> !Ob  jects.equ              als(enti    tyC, ent    ityA)            &&
                                                                         !Objects.e  quals(entityC, entityB))     )
                                     .i    fNotE     xists (Te   stda   taLavishE       ntityGrou      p.class,
                                                       equal((en   tit  yA   , e     nti     tyB , en    tityC) ->   entity   A.getEntityGr oup(), identity  ()  ))
                                                   .penali  ze(SimpleScore.ONE)
                                    .asConstraint(TEST_CONSTRAIN     T_N   AME));

        // From scr   at                ch
        scoreDire  ctor.setWork   ingSolutio      n(soluti  on);
        assertScor   e     (sc oreDire  ctor);

               // Increme  ntal
                       scoreD      ire   ctor.beforePr     oblem  Fa   ctRemoved(entityGrou       p);
                 solu    tion.getEntityGroupList(         ).rem       ove(entityGroup     );
                  scoreDirecto      r.afterPr          oblemFactRemoved(         entityGroup);
                      assertScore(score  Directo    r,
                         as  sertMatc   h(entity2,    enti  ty  1, s   olution.getFirst   Entity()));
      }

    @Over ride
     @TestTemp late
    pub  lic void ifNo   tEx  ists_ 1Joi   n1Filter(  ) {
           Testd   ata    Lavis            hSolutio   n solution = TestdataLavishSolu    tion  .generateSolution(2, 5,  1,     1);
                       Tes     tdataLavishE   ntityG    roup           en   tityGroup     = new Testdata LavishEntit      yGro up("M       yEntityGroup");
                 solution.getE   ntityGroupList         ().add(entityGroup);
          TestdataLavishEntity en    tity1     = new TestdataLavishEntity("M   yEnti  ty 1", en tityGr     oup, so lution.getFirst    Va l     ue     ());
              soluti      o    n.ge tEntityLi     st().add(entity  1);
             Testdata     L av   i    shEntity en  tity2 = new Testda    taLavis    hEntity  ("MyEntit       y 2",   sol     ution.getF    i   rstEntityGroup  (),
                    solut   ion.g       etFirstValue());
        solution.getEn tityList(  ).add(         e    ntity2  );

         InnerScoreD  ire     ctor<               Testdat  aL   avishS   olu  ti   on, SimpleScore>       sc      or     e Direct    or =
                buildScor     e  Director(factory -> factory.forEachUn i           quePa       ir(T        estdataLavishEnt ity.class)
                                    .join(TestdataLavishEntit y.class,
                                                 filt    ering((en   t i  tyA, e  ntity   B, entityC )     ->     !Objects.          equals(entityC, ent    ityA) &&
                                                                          !Object        s.e quals(entityC, entityB)       ))
                           .ifNo    tExists(TestdataLa         v    ishEnti        tyGroup.class,
                                                       equal(    (ent   it yA, ent   ityB,   enti  ty   C)           -> entityA.getEnti   tyGrou  p(   ), identit         y(  )),
                                                 filt     e     ring((entityA, entityB  , e    ntityC, group) ->       entity    A.getCo de ().contains("MyEntity") ||
                                                        g     roup. ge   tCode().contains(      "  My   En    t   ity")))
                           .        penalize(S  impleScore    .ONE)
                                .     asConstraint(TE   ST_CONS   TRA        INT_NAME));  

                  // From      scratch
        sc  oreD     irector.s  etWorkingSolut  ion        (s            olu     tion);
        asse   r            tScore(scoreDirector,   
                 asse   rtMatch(enti     ty2, entity1, solution.get   FirstEnti  ty()),
                  assertMatch(e ntity1, enti ty2,                solution      .getFirstEntit      y()));

             // Incrementa l
           scoreDi  re   c  tor.beforePr   ob   le  mFact  Remo       ved(entityGroup);    
             solution.getEntityGroupLi     s    t().rem     ove(entityGr   o    u    p);
              s      coreDi       r ec tor.af   terPro blemFac  tRemoved( e   ntityGroup);
          assertScore(sc     or        eDirector,
                               ass     er tMatch(entity2, entity1,      solution .getFirstEntity()),
                            a      ssertMatch(solution.getFirstEn      tity( ), entity2, entity1),
                assertMatch(en    t    ity1,    entity2, solution.getFirs                tEntity()))    ; 
    }

    @Over  ride
    @TestTemplate
    pu         blic    void ifNotExistsDo  e       sNotIncludeUnassigned() {
        Testd    ataLavis    hS    olution so     lution       =   Tes    tdataLavishSolution.   generateSoluti on(2, 5, 1,   1);
        Te     stdataLavishEntityG    ro            up entityGrou  p = ne w T  estdataLavishEnti       tyGroup("MyEntityGroup  ");
            solution.ge   tEntityG   roupLis   t().add(    en   tity    Group);
                TestdataLavishEn  tity entity1   =     new       Test  dataLa vishEn    tity("MyEntity 1", enti             tyGroup,   solu ti        on.ge  tFirstValue()     );
                      solu    t   i      on.getEntityLi     st(     ).  add(en   tity1);
              Testdata      Lavish    Entity         e    ntit    y2 =      new T     estdataLa   v    ishEntity("MyEntity   2", so    l      ution.getFirstEntityGro  up     (),
                              solution.ge     tFirstValue());
        sol    ut  i   on.ge   tEnti    tyList().add(e                 ntity2    );
           TestdataLavishEntity enti  t    y3 = new Testdata   La      vishEn     t   it y(   "Entity with null v      ar"         ,               solution.ge          tFirstEnt        ityG      ro   up(),
                                           n  ul l );
                    sol    utio  n.ge  tEntit  y  Li     st  ().       add(entity3);
 
        InnerScoreDirector<TestdataLavishSolution,      Si         mpl  eSco     re>    scoreD   irector =
                buildSco  reDirect                or(fac     tory -> factory.forEachU   niqu  ePair(Testd ataLavish   En     tity.c lass  )
                                      .  join(TestdataLavis  hEntity.    class,
                                                 filtering((    entityA, entityB, entityC)   -> entityA !=      entityC && entityB !=     entityC))
                                        .ifN   otExist  s(Testda ta     LavishEnti     ty.class,
                                           filter    in g((e    ntity  A, entityB, entityC,           en   tityD) -> en tityA != entityD && e  ntit   yB != entityD
                                                      && entityC != enti    t  yD)     )
                             .penaliz     e(SimpleScor   e.ONE)
                          .asConstra      int(TES    T_CONSTRAINT_NAME)      );

             // From scratch
         scoreDirector.setWorkingSolution(solut       i    on);
                   asser t   Score      (s   c  oreDirector,
                                as  sertMatch(solution.getFirstEn  tit  y(), entity1, entity2),
                 as       sertMatch(solutio   n.getFirstEnti      ty(),  en tity2, entity1),
                              assertMatch(entity1, e   nt     ity2, so    lution.getFirst   Entity() ));
   
          // Incrementa      l
        scoreDi   re   ctor.beforeP           roblemF     actRemove    d(entity3);
        sol     ution   .g    etEntityList()   .remove(entity3  );
        sco  reDirector  .afterProblemFactRemoved(entity3);
        assertScore(scoreDire        ctor,
                assertMatch(sol      ut ion.get     FirstEntity(),         entity1, entity 2)   ,  
                      assertMatch(solut ion.getFir        stEntity    (),   entity2, entity1),
                          as  sertMatch(  entit  y1, enti   ty2  , soluti on.get    F     irstEntity      ()));
     }

     @Overr  ide
    @TestTemplate
    @De precated(forRemoval = true)
    pu     blic     void ifNotExistsIncludesNull  Vars     WithF  rom(    ) {
             Testd       at     aLav            is     hSolu    tion solution = TestdataLavishSolution.      g  enerate     Solution(2   , 5, 1, 1);
          TestdataLavishEn t     ityGroup enti   tyGroup = ne               w T  estdat a    La         vishEntity       Group(" MyEntityGroup");
                        s     olut  io n.getEntityGroup    Lis  t().add(ent  ityGroup);
             T     estdataLavishEntity enti   ty1 = new T            est  da        taLavish  En  tity      ("MyEntity 1", entity  Gr  oup,    soluti  o   n.getF     irst Value(     ));
           solution.getEntity  List().add(entit    y1);
        Tes  tdataL       av  i    s  hEntity entity2   =    new TestdataLavishEn     tity("MyEn     tity 2", solu    tion.getF      irs  tEntityGro     up(),
                         solution.getFirstValue( ));
            s    olution.getE     ntity List().add(en  tity2);
                 T      estd   ataL    avishEntity entity3 = new T     estdataLavishEntity("E  ntity wi  th null var",   solution    .getFirs  tEntityGroup(    ),
                          null);
               so   l          utio    n.getEntityList().add(en   tity3);

        InnerS    coreDi    rector<Te             std    a      taLavishSoluti  on, Simple       S   core> scoreDirector =
                       b    uildSco         reDi    rector(factory -> factory.fro      mUniquePa  i  r         (TestdataLavishE    nti      ty.class )
                                                    .       jo         in(Testda  t      a   LavishEntity.    class,
                                       filtering((entityA,  entityB, entityC )      -> en     tityA     != entity   C && ent  i     tyB != e  ntityC  ))
                                             .ifNotEx  ists(TestdataLavishEntit  y.class,
                                                 filtering(     (en     t ityA, ent        ity   B, entityC, entit    yD) -> entityA != e            ntityD && ent      ityB != entityD
                                                                         &&      entityC != e    n   tityD) )
                                       .pe   n    alize(Simp    leScore.  ONE)
                        .asConstra         i nt(TEST_CONSTRAINT_      NAME));

          // Fr   om s    crat    ch
                    sco             reDirector.set  Work     ingSolution(solution);
        assertScor            e(s   coreD irector);

                  // Increm   ent  al
           score     Director.  beforeProble   m   Fa c    tRem oved(enti     ty3) ;
        s          olution.getEntityList().rem   ove(    entit    y3);  
          sco   reDirec    to   r.afterProble     mFactRemoved(ent  ity3);
                     assertScore(scoreDirector,
                      assertM   atch(solution.getFirst      Entity     ()  , entity1, entity2),
                             asse  rtMatch(sol   ution.ge  t FirstEntit     y(),      entity2, e  nti  ty1),
                            as      sertMatch(entity1, enti    ty2, s   o            lu t      ion     .getFir     stEntity()));
    }

        @O v    erride
    @TestTemplate
    public v  oid      i       fE xist   s          A  fterG         roupBy() {
              TestdataLavishSolution solution = TestdataLavishSolution.generateSolution(1, 0, 1, 0);
                   TestdataLav           i   shValue val  ue1 =        new   TestdataLavi   sh  V  alue("MyValue 1", solution.getFirstV        alue Group());
        solut ion.ge       tValueList( ).add    (value     1);
         T   e  st         d  a  t     aLavishValue        value2 = new Testda  taLavishValue("MyValue 2",      solut  ion.getFirs        tValue  Gr    oup());
        solut   i  on.        ge  tVa lueL   ist().a      dd(value2)     ;
                T  e                    st   dataLavishEnt        ity entity1 = new   TestdataLav      ishE    nti   t   y("My   Entity 1", solution.getFirstEntityGroup(), value1    );
            solution.g          etEnti      tyList().add(entity1);
         TestdataLavishEntity e ntity2     = n  ew Testda   taLavishE ntit  y("M y  En   tit  y 2", solu   tion.getFirstEn      tityGr   oup(), v   a      l  ue1)    ;
                  soluti  on.getEntityList()         .add(       en tity2);
        TestdataLavishExtra ext   ra1 =           n  ew TestdataLavishExtr   a("MyExt         ra 1");
                solutio   n.getExt raL  i  st().add( extra1        );
              Testdat aLavis                  hE   xt      ra extra2    = n ew TestdataLavishE       xtra("My  Extra 2");
                 solution.getExtraList()   .add(ext     ra2);    

          InnerScoreDirector<Testd  ata        Lavi  shSolution, Simple  Score> scoreDire   ctor    =
                buildScoreD   irector(   fact ory  -     > facto   ry.for    E  ach(TestdataLav    ishEntity. class    )
                                   .grou pBy(countDistinct(T     estdataLav     ishEntity::getValue),
                                     countD isti          nct(TestdataLavishEntity::getValue   ),
                                                        cou     ntDistinct(TestdataLavishEntity::getValue))   
                           .ifEx ists(Testd   ataLa vishExtra.class)
                        .pen        alize (Simp       le  Score.ONE)
                                       .a   sCon        str aint   (TEST_CONSTRAINT_NAME) )   ;

        // Fro    m  scr  atch
              scor     eDirector.   setWorkingSol     ution  (solution);
        asser t       Score(sc   oreDirector,
                a      ss       ertMatch(1, 1       , 1));

        // Incremental
            sc      oreDirec    tor.befo    reVariabl  eC    hanged(ent   ity2    , "value");
             entity2.setValue  (value2);
        score   Directo   r.afterVar  ia  bleChanged(entity2, " valu    e");
              ass  ertS   core(scoreDirector,    
                assertMat     ch(2,  2, 2  ));

        // Incre   mental
        scoreD       ire    ctor.b      efore      Ent      ityRem        oved(entity2);
           s   olu  tion.getEntityList().remove(entity2       )       ;
                 score  Dire      cto     r.afterEn     t          ityRe    m oved(entity2);
            assertScor e(sco  reDirector,
                  asser      tMat  ch         (    1, 1, 1));
     }

               // *****    **  ********** ********   *     ***********  ****   ****   *******    *    * ******************
         // Gro      up     by
       // *** *  **   *****     ***     ****************  ***         *      ***       ************** *************        ******   *    *

    @Override
       @TestTemplate
       public voi   d  groupBy_0Mapping1Collector() {
        Te  stdata Lav   ishSolution       soluti   on   =   Te  s  td ataLavi  shSolution.generateSolution(1 , 2,     2, 3);

        InnerScoreDire    ctor<TestdataL      avis               hSol   u    ti   on, SimpleScore> sco          reDirector =
                    bu     ildScoreDirector(factory -> fac   tory.forEac    h(Tes  tda       taLavi shEnt   it   y.class)
                               .join(TestdataLa vish  En           tityG    roup.class, e   qua  l(T     estdata LavishEntity::   getEntityGr    oup, ide   nt      it      y     ()))
                                                   .join(T   estdataLavishV      alue.   cl   a    ss, equal((entity, group) ->  entity.ge     tValue(),    identity()))
                                        .groupBy(countTr  i())
                                         .penaliz  e(Sim         pl      eScore.ONE, coun  t ->       count)
                                       .asConstraint(TEST_CONSTRAINT_NAME));

        // From scratch
                 scoreDirector.set          WorkingSolution(soluti         on );
        assertS   core(scoreDire     cto  r,
                              assertMatch   WithScore(-3, 3));

         // I   ncre   me  ntal
         T            e       stdataLavishEntity          entity = solution    .getFirs   tEntity();
                      s     coreDir    ector  .b  eforeEnt ityRem   oved(enti ty);     
        solution.              getEnti tyList().  remo    ve(ent         ity);
           scoreDirect  or.   afte  r      EntityRemoved(    enti         ty);
               assertScor    e(sc oreDirecto    r,
                           assertM     atchW               it              hScore(-2, 2));
    }

          @Override
    @Te   stTem    pla       te
        public    void    groupBy_0Mapping2Collector() {  
        TestdataLavishSol    ution solution     =       TestdataLa     vishSolution.generat eSolution(    1,    1     , 2, 3);
             In   n erSc oreDirector<Te       stdataLa v      ishS    oluti    on  ,  Simpl  eSco            r  e> scoreDire            ctor =
                                                buildScoreDirector(factory ->    factory.forEachUni que  Pa   ir   (Testd   ataLavishEntit y.cl  a   ss    )
                                  .join(Te  stdataLavis h      Entity    .class, e     qual    ((e1,     e2) -> e1, Function.identit   y()))
                                    .groupBy(co untT ri(),
                                              cou  ntDist         i nct((             e, e2    , e3) ->      e))
                              .penal               ize(Si mp  leSco     r    e.O  NE)
                         .a       sCon  st  rai         nt(TE         ST_CONSTRAINT   _NAME));
       
        Testdata  Lavi     shEntity entity1 =         s olution.g  etFirstEntity(  );

            // From   scratch
        scor   eDi  r   ector.setWorkingS olution(solution);
                     a ssertScore(sco   reDirector, assertMatchWi     thS core(-1, 3,    2))   ;

           // I  ncremental
                      sco   reDirec    tor.before  EntityRemoved(en     ti    ty1);
          solutio   n.getEntit  y List().remo   ve(entity1);
               scoreDirector.aft erEntityRemov          ed(entity1); 
        ass   ertScore(scoreDir ector      ,       assertMatchWi   thScore(-1 ,    1, 1)  );  
    }

    @Overri         de
        @TestTe    mplate
    pub    lic vo          i    d groupBy_ 0Mapping3Collector() {
            Tes            tdataL   a  vish Sol            ution      solution = TestdataLavishSoluti   on.generateSol       ut ion(1,      1             , 2, 3);
            Inn      erScoreDirec      tor<T    estdataLavishSolution, SimpleScore       >    scor     eDirector    =
                  buil   d    S      coreDirector(factory           -> factory.forEachUniq      uePair(Testdata            L   avishE   n    t      ity.class)
                               .join(T          estdata LavishEntit  y.clas          s, equal( (e1, e2  ) -> e1, Function.identity())) 
                                  .gr     oupBy(count   Tri(),       
                                                    min((Test  dataLav                    ishEntity e, TestdataL      av        ishE   ntity e2   , Testda    taLavishEntity e3) -> e
                                                   .getL         ongPro           p    erty()),
                                           ma     x((   Testdata    LavishEntity e,     Testdata  Lavi shEn    tity    e2,                   TestdataLavishEntity e3)   -> e
                                                         .    getLongProperty()  ))
                                          .penalize(Simpl   eScore.ON  E )
                              .asCons  traint(TEST_CONST      RA   INT_NAME));

          Testd     ataLavis     hE  nti  ty  ent     it y1     = soluti     on.getFirst E     ntity();
        ent ity1.setL ong P  ro     per  ty(0L);
         T  estdataLavish       Entity e       ntity2 =  sol u        tion.ge t   EntityList        ().get(1);
          entity2.setLongProperty(   1L)  ;
          Test   dataLavi shEntit    y entity3 = solution.getE  ntityL ist().     get(2)     ;
        enti    ty3.setLongProperty(2L);

          // From    scratch
             sco      reDi    re    ctor    .     setWo  rk  in  gSolut    ion    (solution);
          asse  rtScore  (sc     oreDirector,
                 assertM  atchWi  thScore(-1,  3, 0L, 1L));

        // Increm e  ntal
        scoreDirecto r.beforeEntityRemoved(ent   ity1      );
                     solution.getEnt     ityLis  t().rem      ove(ent         ity        1);
           s      co     reDirector.afterEn    tityRemo       ved(entity1);
        assertScore(scoreDirector  ,
                        assertMatchWithScore(-1, 1,  1L, 1L));    
           }

    @Ov erride      
       @     T       es          tTemplate
    pub lic v     oid groupBy_  0Mapping4Coll  e     ctor()     {
            TestdataLavishSolut    ion   solu    tion = TestdataLavishSolution.generateSolution  (1,     1, 2  ,     3);
        InnerScoreDi rector<Te stdataLavi shSolution, Sim    pleSc   ore> scoreD   irector =
                                 buildSc ore      Di r               ect    or(fa    ctory    -  > factory.                 fo     rEachUni  quePair(Testdata   Lavish   Entity.cla   ss)
                           .join(T       est    dataL  avishEntity.cla  s    s, equal((e1, e2)       -> e1, Function.identity()))
                                               .gro      upBy(  countTri(),
                                                    m   in((Testdat aLavishEn tity e, TestdataLavish     En tity e2, Testd    ataL       avish                   Entity    e3)  -> e
                                                           .get    LongProperty()),
                                              max((Tes     tdataLavi  shEnti    ty  e,        TestdataLavishEntity e2, Testd  ataLavishEntity e3    ) ->   e
                                                                            .g  et  Lon    gProperty()),     
                                           toSe       t((     e, e2,     e3) -> e  ))
                        .p e  nalize( Simple  Score.ONE)
                                 .asConstraint(TEST_CONS  TRAI     NT_NAME));

           TestdataLav       ishEntity        entity1 = solu  tion.getFirstEnti ty();
         entity  1.setLon    gProperty(0L);
        TestdataLavishEnt     i     ty entity2 =  solution.getEntity             Lis     t()   .g      et(1);
        entity2.setLongProperty(1L);   
         TestdataLavishEntity  entity3        = solution.getEntityLi  st(     ).get(2);
               entity3.setLo   ngProperty(2L);

           // From scra   tch
               sco    reDirector.setWork   ingSoluti   on(solution);  
                     assertScor     e(scoreD     irector,  
                                assert  MatchW     ithScore(-1, 3, 0     L, 1L, asSet(entity 1,      entity2        )));  

                   // Incre     m     en         tal
        scoreDirec      tor.   befor   eEnt  i   tyRemoved(entit y1);
           soluti        o   n.getEnt  ity   List().re  mo        ve(   entity1);
        sco reDire ctor.a   fterE                ntityRemoved(enti  ty   1);
           a   ssertSco  re(scoreDirector,
                     assertMat     chWit   hS        core(-1,    1, 1L, 1L, asSet(entity 2))       );
      }
      
                @Over    ride
        @TestTemplate    
    public v oid gr    oupBy_1Mapp  ing  0       Co   ll ector(   ) {
        T  estdataLav  ishSolution sol   ut  ion = Tes        tdataLavishSo        lution.g       en erat  eSo lution     (1, 2, 2   , 3   );

        InnerScoreDire   ctor<Tes  tdataLavishSolution, Simp          le      Sc   ore> scoreDirector =
                bui   ldScor    eDirector(fact ory ->     fact ory.forEach(Test d     ataLavishEntity.cla         ss)
                                        .join(Tes tdataL   avishEnt  ityGr oup.class, equal(TestdataLavis          hEnti    ty: :getEnti  tyGroup, id      entit       y()))
                               . join(Te   stdataLav         ishVal  ue.     class,  equal((entity, group) ->     entity.getValue(), identity() ))
                             .g   roupBy((   en    tit    y, group, value) -> va      lue           )
                                       .pen                    ali    ze(Simpl        eScore.ONE)
                                                    .asConstr    aint(TEST   _CONST   RA  INT_NAME));

              Test      dataLavishValue value1 = solution.getFirstVa  lue( );
         Tes   tdataLav   ishValue value2      = solution.getValueList().             get(1      );
 
           scoreDirector.setWo     rkin  g       So lution(s     olution);
        assertS   co re(sco    reDirector,
                    asse   rtMatch    W   ithScore(-1   ,        val    u e 2),
                assert    Ma   tchWithScore(       -1, valu       e1));
       }

                    @    O   verr   ide
    @TestTemplate
    public  voi  d groupBy_1Mapping1Collect   or()       {
           TestdataLavishSolution  s  ol u tion = Tes          tdataLavishSolution.gene           rateSolution(1, 2,  2, 3);

               InnerSco     reDirector<TestdataLa    vish S olution, SimpleScore>   scoreDirector =
                   buildScore         Director(   fac  tory -> factory.forEa ch(Test         dat aLavishEn    tity.clas     s)
                                    .join(TestdataLavishEnti tyG roup.cl   ass, e      qual(Testdat   aLavishEntity:: get     EntityGro           up, identit      y()))
                                 .jo in(Test         dat   aLavishValue  .class,    equal((entity,   gr   o         up) ->    e   ntit   y.    getValue(), identity()))
                            .groupBy((e       ntity, group, value) -> v   alue,     countTri    ())       
                          .  pen   al  ize    (Sim pleScore.ONE  , (group,      co     u      nt) -> count)
                               .     asConstraint(TEST_CO   NSTRAINT_NAME));

                  Testd    ataLavishV   alue value1     = solution.getFirstV          alue();
                Te  stdataLavishValue va    lue2 =                  solution.getValueList(  ).get     (1) ;

           // From scratch  
              scoreDi     rector.setWorkingSoluti   on(solution );
            ass    ertScore    (scoreDir   ecto                  r,
                  assertMatchWithScore(  -1, value2, 1),
                assertMatchW    ithSc      ore(-2,      value1, 2)   );

            // In        cr   eme   nt al
          TestdataL       a   vishEnti   t   y ent   ity = solut i    o  n.getFirstEntity();
        s  co   r   eDirector.beforeEntityRemove   d(entity);
                            s       olut   ion.ge  tEn  ti            tyList().     remove( entity);
         scoreDirector.afte  rEntityRemoved (entity);
        assertScore(scoreDirector,
                   ass ertMatc  hWithScore       (-1, v         al   u   e    2     , 1),
                       as   ser  t       MatchWith    Sco      re(  -1, v   alue  1, 1));
           }        

             @Ove     rrid  e
    @ TestTemplate
          public void    group  By_1   Ma p      ping2   Collector() {
            Te      s   td     ataLavish  Soluti       on soluti   on = Testdata    LavishSolut ion.generateSo         lution(1,  1        , 2, 3);

          Inner S   core   Director<TestdataLavishSol ution  , Si   mpl     e  Score> score   Director =
                buildScor             e     Di  r      ector(factory -> factory             .forEach   Uni   quePair(TestdataLavis          hEntity.class)
                                 .j oin(   T     estdataLavishEn   tity.        cl   ass, eq     ual((entit   yA, entityB)  ->      entityA, Function.identi               ty()))
                                         .gro    upBy((entityA , en            tityB, entit          yC) -> entityA.toSt    ring(),
                                      co  untTri(),
                                    toSet(  (entityA, entity   B              , en         tity      C) ->   entit  yA))
                                      .penalize(Si    mpleScore        .ONE)
                                    .as  Constraint(TEST_CON    STRAIN T_NAM      E));

        TestdataLavis   hEntity entity1 = solution.ge tFirstEntity()     ;
          TestdataLavishEntity entity2 = solution.getEntit      yList().ge   t(    1);

                   // From     scratch
        sco      reDire     ctor.set   Workin    gSolution(s     o   l ution     ); 
        assertScor    e     (scoreDirector,
                a  sse   rtMatch  WithScore(-  1, TEST_CONSTR     AINT_NAME, ent     ity1.toStrin g(), 2, singleton            (entity1)),
                       a   ss   ertMatchWithScore(-1,     TEST_CONSTRAINT_NAME, enti   t            y2.toS  tring(), 1, sin gle ton(entity2)));
  
        // In    creme     ntal
        T     estd       a taL     av  ishEnti   ty entity = s        olution.getFirstEnti    ty();
        sc         oreDirector.bef oreE  ntityRemoved(entity    );
              s    olut          ion.getEntityLis t().remove(entity)    ;
        scoreDirec       tor.aft  erEntityRem  o      ved(entity);
           assertScore      (scoreDirector,
                                assertMatchWithScore     (-1,    TEST_CONSTR  AINT_  NAME,   e  ntit                  y2.toSt   r ing(), 1, singleton   (entity2)));
    }

    @Ov  er   r ide
    @TestTempl  ate
          p   ublic  void          grou  pBy_1Mappi      n  g3Collector()     {
          TestdataLavishS      oluti   on so  lution =  TestdataLavishSolutio   n.genera           teSolution       (1 , 1, 2, 3)    ;

        InnerSco reDirector<  TestdataLav ishSolut    ion, S impleScore> score   Director =
                      buildScoreDirector(f    actory    -> factory.forE   achUn  iquePair    (  Testd   ataLavishEntity.class)
                          .join(T  estdataLa         vish      En  tity.class, equal                 ((enti  t        yA ,     ent    i  tyB)      -> entityA    , Function.iden    ti                 ty()))
                                    .groupBy(( en  tityA, en   tityB, entityC) -> ent i   tyA.toString(),
                                        mi        n((Te         std     ataLavishEntity entityA ,   TestdataLavishEntit        y   entityB,
                                                      TestdataLav   ishEntity e  ntityC) -> entit    yA.       getLongProperty())    ,
                                               m  ax((T       estdataLavishE    ntity entityA, T         estdataLavishEn  tity e ntityB,
                                             TestdataLavishEntity en           tityC) -> entit            yA.getLon   gProperty()),
                                                         toS     et((e  ntityA,    entityB, entityC)      ->    entity   A))
                                        .penalize(   Simpl eScore  .         ONE)
                               .       asConstraint(TE     ST_CONSTR    AINT_NAME       ));

             Test  data  L    avishEntity e   ntity  1 = solu   tio        n.getFirstEntity();
        entity1.setLong     P  roperty(Lon    g.MAX_        VALUE) ;  
        Testd   ataLavishEntity entity2 = solutio n.getEntit   yList().get(1)  ;
          entity       2.setLongProperty(Lo    ng.MIN_V   ALUE)     ;
   
              // From     scra    tch
        sc            oreDirector.set   Working     S    o   lut   ion(solution)   ;
            assertSco re   (    s  coreDirec     to r,
                  assertMatchWithScore(-1,        TEST_CONSTRAI    NT_NAME   , ent   ity1      .toString()  , Long.M  AX      _V    ALUE,   Long.      MAX_VA   LUE,
                             singleton(entity1)),
                assertMatchWithSco   re(    -1        , TEST_CONST       RAIN  T_N  AME, entity2.toString(), Lon               g.MIN_VALUE,   Long.MIN_VA       LUE,
                            sin    glet  on(en      tity2)   ));
     
        // Incrementa l
            T           estdata   Lavis   hEntity entity = solution.      getFirstEnt  it     y();
          scoreDirecto         r.beforeEntityR    e  moved(entity);
        solutio  n.getE    ntityList() .rem ove(entity);  
          sc    oreDirector.afte r       Entit  y          Removed(entity); 
               assertScore(scoreDirector,
                   asser      t   M    atchWithScore(-1,    TEST_CONSTRAIN  T_NAME, entity2.toString(), Lo           ng.MIN_   VALUE,     Long.     MIN_V       ALUE,
                                    singleton(ent   i    ty2 )));
    }

    @Ov      erri    de
            @TestTemplate
    public void  grou          pB     y_2Mappin    g0Collector()    { 
        Te    stdat    aLavis   h S  olu  tion  solution = TestdataLavishSolution.gener ateSolution(1,           2, 2, 3);
            InnerScoreD   irec     t                    or<Tes      tda     taLa   vis  hSolution,         Simple                 Score   > s  c   oreDire  c    tor =
                buildS   coreDirector(factory -> factory.fo  rEach(TestdataLavi    shEntity .class)
                                     .join(TestdataLavishEntityGroup.class, eq  ual(Te       stdataLavishEntit         y ::getEntityGro     up,     identit y(  )         ))
                            .jo   in(TestdataL    avis hValue.cl      as                s, equal((entit  y, group)         -> entity.getValu  e    (), identity()))
                        .groupB   y((en    tity, group,    value) -    > group, (entity              , group, value) -> va   lue)       
                                                      .pe  naliz  e(Si      mpleScore.ONE)
                               .asConst rain  t(TEST_CONSTRAINT_NAME)       );      

        TestdataLav   is   hEntit yGro    up group  1 = soluti  on          .getF    i   rstEntityGroup();
                    Te    stdataLa       vishEntit             yGroup group2 = sol   ution.getEntityGroupList().g et(1);
                           T    es  tdataLavis  hValue          value1 =                     solution.getFirstValue();
              Testdat aLa   vishValue              value2    =  s  olutio    n.getValue       List().get(1);

        // Fro  m scratch
        sco reDirector      .setWor        kingSoluti          on   (solution);
             assertScore(s           coreDirector  ,
                    asse  rtMatc           h   WithSco  re(-1, group   2, value2),
                        a     sse        rtMatchW it   hScore(-      1, group1, value1));

                 //   Incremental
        TestdataLavish    Entity entity  = solut   ion.getFirstEntity();
                 score  Dir     ector.b    eforeE ntityRemoved    (ent     ity);
                 solution.ge  t   EntityLis   t().remo  ve(entity);
        scoreDirec   tor       .afterEn     tit     yRemoved(en    tity          );
                                  assertScor     e(scoreDirect    or,
                             assertMa  tchWith Score(-1, group2, value2),
                         assertMatchWithScore(      -1,                 group1,   va  lue  1));
              }

    @Ov   er   ride
    @TestTe   mplate
             public vo id groupBy         _2Mapping1Collecto       r()   {
        Te   stdataLavishSolu     tion solution          =       T    estdataL avis      h   Soluti    on  .ge    nerateSolution(1, 2, 2, 3);

           Inner  S    coreDirector<Test    dat     aL     avishSolution, Simpl  eS   c      ore> scoreDirector =
                       buil    dScor    eDirecto r(fact   ory ->     fac    tory.forEach(TestdataLavis  hEnt     ity.                 class)
                                           .jo          in(TestdataLavishEnti    tyGroup.class, equal    (TestdataLavishEntity::getEntityGr     oup, identity  ()))
                              .join   (T  estda        taLavishValue.class, equal((entity, gr   oup) -  > enti  ty.getVal   ue(), identity()))
                                        .groupBy(      (entity,    grou      p, v  alue)     -> group, (en   ti   ty, gro        u   p, v   alue) -> value, co     untTri(   ))
                                   .penalize(Simpl  eScore.   ONE,      (group, v      alue,    cou  nt) -     > count)
                        . asCons traint(TEST_CONST  RAINT_ NAME)  );

             Test  data        La                vi          shEn tityGroup group1 = solu      tion.g    etFirstEntityGr    o  up();
        Testda      taLavishEntityGroup gr       oup2 = s  olution .g    etEntityGr   oupList().ge     t       (1);
             TestdataLavish     V alue value1 = solut  ion.g   etFirstValue();
            T  e stdataLavishValue va   lue2 =               solution             .getVa         lueList() .ge         t(1     );

        // From sc     ra   tch
           scoreDi     rector.setWorkingSolutio    n(solution);
            assertScore(scoreDi               r      ecto  r      ,
                     assertMatchWithScore(-1, gro up2, value2, 1),
                         assertMatchWithScore(-2, grou   p1, value1, 2     ));

                  / / Incremental
             Test           d   ataLavishEnti     ty     entity = solut ion.getFir stEntity   ()  ;
        scoreDirector.beforeEn    tityRemoved(ent ity);
        solution.getEntityList().r   emove( entity);
               scoreDir      ec    tor.afte    rEntity   Re moved(entit  y);     
              assertSco        re(scoreD  irector,
                      assertMatchWithScore (-       1, grou p2, va          lue          2, 1   ),
                   assert  Match  With             Sc  ore (- 1, group     1      , v alue1  ,    1)  );
    }

    @Overr      ide
              @TestTemplate
             public vo id groupBy_2Map     p   ing2C  ollector()  {
         Testdata       Lavis   hSo         lution solu             tion          = Testd   ataL          avis     hSolution.g      enerateSolutio     n(1, 2,   2,  3);

        InnerScoreDirecto    r<Testda    taLavish          So lution, Simple S  cor   e> s   c oreDirector   =          
                 buildSc  ore  Director(f actory -  > factory.  forEac  h(T     estdataL    avishEn     tity.cl   ass)
                                            . join(Test          dataLavishEntityGro up.cla  ss,  equal(Testd  ataL     a  vishEntity::getEntityGroup, identit   y(   )     ))
                                  .joi     n(TestdataL   avish     Value   .class, equal((entity,  g   roup)     -> e   n  tity.getV    a      lue(),        identity()))
                         .            g   roupB    y((entity, group, value) -> group, (entity, g        roup,    val       ue)    -> value, count       Tri(   ), co       untTri ())
                                            .     p     e nalize(SimpleScore.  ONE        ,    
                                                                               (gro   up,               value,         c ount, sam eCo u   nt) -> c    ount + sameCou     nt)
                                     .asCon     straint(TES     T_CONSTRAINT_NAME));

                 TestdataL        avishEntityGro up gro     up1 = solution.getFirst En tityGroup(   );
        Te stdata            LavishEnt   ityGroup group2   =  solutio    n.ge    tEntityGroupList().get(1);  
                         Testda           t aL  avishValu   e v          alue1 = solutio  n.getFirstValue();
        Tes     tda ta      LavishValue      value2 = solution.     getVa  lueList().g  et(1                    );

        //     From scratch
          s  coreDirector.s     etWorkingSolution(solut   ion);
        a  s         sertS core(scoreD  i   rec       tor,
                assertMatchW      ithScore(-2   , gr  oup     2, val    ue2, 1, 1),
                                  as    sertMatchWith    Score(-4, group1,        v  alue1, 2, 2));

              //    Incremental
                 TestdataLavishEntit     y entity =   solution.getFirs       tEntity();
           sco reDi recto    r.b    eforeEntityRe moved(         ent    i   ty);
           solution.getEntityList().r emo   ve(entity);
          scoreD      irector.afterEntityR   e      moved(entity);
        assertSc    ore(scoreDi      rector,
                assertMa       tc     hWithSc   or e(-2, gr   oup2, val          ue2, 1, 1),
                         assertMatch     WithSc  ore(       -2, gro  up1, va     lue1,    1, 1));
    }

    @Over     ride   
     @T  estTem     plate
    public void groupBy_3Mapp   ing0Collector() {
            T  estdataLavishSo   lution      solution    = Testda    taLavis         hSolut ion.ge   n   era  teSolution (2, 2, 3,   3)    ;
            Inn    erScor   e          Director<Tes  td   ataLav   is    hSol    utio    n     , S    i     mpl     eScore> sco  reD  irector =     buildScoreDirector(factory -> factory
                                 .f orEachUniqu  ePair(Te  s   tda  t aLavishEnt            ity  .class)
                      .    j  oin(Test    dataLa   vishEnti            ty.class,           
                                filtering((a,          b, c) -> a != c    && b !      =    c))
                             .groupB   y     ((a, b, c) -> a   .getE   ntityGroup(    )    ,      (a, b, c) -> b.getEnti  tyGroup      (), (a, b, c) -> c.getEnti  tyGr oup    (      ))
                               .penalize(S      impl     eScore.ONE)
                  .asConstr   aint(TE  ST_CON           STRAIN    T_NAME));

        Tes  tdata  LavishEntityGrou  p group1 = solution. getEnt      ityGroupList().get(0);
             TestdataLavishEntit    yGro  up  g  roup2 =      solution.g   etEntityGroupList(   ).get(1);
          T    e   stdataL  avishEntit   yG   roup gr  oup3 = s  ol    uti    on.         g  etEntityGr  oupList( ).get(2);

        // Fr om scr  atch
            scoreDire ctor.setWorkingSoluti    on(solution);
          assert  S       core(scoreDi r   ector,
                      assertMa    tchWithScore(-1, group   1 , group2, group3),
                                              assertMatc     h WithScore(-1, group1, g    roup3,         group2) , 
                asser  tM atc    hWithScore(-1, group2, gro     up3, g   rou        p1));

        //  Inc  r   emen      tal
        Test   da  taLavishEntity entity = solu  tion.  ge   tFirs      tEn tity(           )   ;
             sc ore       Director.beforeEntityRem  oved(enti    ty);
        solution.getEntityList()       .remove(entit y   );
        scoreDire     ctor.af     terEntityRemoved(enti       ty    );
        a   ssertScore(   score   Direct  or);
        }

     @Over     r    ide 
               @TestTemplate
    pu  bl      i c vo    i      d groupBy          _3Mapping1C      ollector       () { 
           TestdataLavishSolution solut    i on = Te     s         tdataLavishSolu    tion.g    ener        at     e     Solution(2, 2, 3  ,    3);
          I              nnerSc oreDire    ctor<Te    stdataLavi   shSolutio n,      SimpleScor  e> scoreDirector = buildSc  oreDirector(factory    -> factory
                               .forE   a    ch    Un     iqueP   air(TestdataLav   is   hEntity.class)
                         .join(TestdataLavishEntity.c  l   ass,    
                                filte   ring((   a, b, c) -> a != c && b       !        = c))
                .groupBy((a, b, c   ) ->   a.getEntityG  r  oup   (), (a,  b, c) -> b.getEntityGroup(), (a, b,     c) -   > c.getEntityGrou     p(),
                             ConstraintCol lectors.countTri(     ))    
                    .penal   ize(Simpl            eScore.ONE)
                    .asCons       traint(TES     T    _CONSTRAI      N   T_NAME));

           TestdataLavishEntityGroup group1 =   so   lution.getE                      ntityGroupList().get(0)  ;
         Te    stdataL      avishE ntityGroup group2 = solution.get EntityGroupList(   ).get(1);
        TestdataLavis   hEntity        G  roup group3 =                   solution.getEntityGroupLis           t().get(2)  ;

             // From scratch
                                score   Directo   r.set   Work     ingSolu  tion(solution);
                 asse     rtScore(sco   reDirec    tor,
                    assert      M  atchWithScor    e  (-1,         group1, group2, group3, 1),
                                      assertMatchWithScore(-1, gr  oup1,     group3, group2  , 1)    ,
                as  s   ertMat  c  hWithScore(-1, group2, group3, gr        ou   p1, 1))      ;

        // I   ncremental
          TestdataLavishEn        tity ent    it    y    = solution.get        Firs  tEntity();
        scoreDirector.befor   eEnt  ity Re    mo     ved(entity   );
             solution.getEntityL     ist ().re   move(   entity);
        scoreDirec   tor.afte  rEntityRemoved(entity)         ;
          assertSc     ore(sco  r   eDir                ecto     r    );
         }

    @Ove        rride
    @Test        T      emp late
    publ   ic void   groupBy_4M        apping0          C ollector() {
        TestdataLavishSolu      tion solution = Testda       t  a    Lavish    Solution.generateSolution(2, 2, 3  , 3);
        InnerS               c   oreD ir       ector<TestdataLavishSoluti      on   , SimpleScore>          sco  reDi rector = buildS        core     Director(factory            -> fact   o       ry
                            .fo  rEachUniquePair(Testdat   aLa   vishEn           ti       ty.cla       ss)
                    .j  oin(Test  data    LavishEntity.cla           ss, 
                            filtering((a, b,  c)     -> a != c &&    b != c))
                  .g   ro upBy((a, b, c) - > a    .getEntit  yGr oup(), (a, b,    c) -> b.getEn        tityGr oup(), (a, b,  c) ->  c.g      etEntityGr   o  up(),
                                          (a , b, c)  -> a.getValue()                    )             
                   .pe         nal   ize(    SimpleScore   .ONE)
                           .asConstraint(T  EST  _CONS   TRAINT_N  AME));

        TestdataLa vishEntityGroup group    1 = solution    .getEntity  Gro   upList()     .ge  t(   0);     
        Te stdataLa    vishE   n   t  ityGroup     gro    up 2 = so       luti      on.getEn       tityGroupList().get(1);
        TestdataLavishEntityG     roup      group3         = so  lution.getEnti tyGroupLi   st().get   (2);
        TestdataLavishValue v       al     ue1 = solution.getValueList().get(0);
                  Testdat   aLavi     shValue va     lue2 = soluti on.getValu eList    ().get(1);
                 
             //    From scratch
            scor   eDirecto        r.setWorkin gSolut   io n(so       lu  tion);
               assertScore(scoreDirector,
                      assertMatchWi    thSc      ore(-1, group1, group         2, gr    o  up3 ,    value1),
                assertMatchWithSco         r       e     (-1, g    roup1   , group3, gro  up2,      value2  ),
                       as          sertMatchW    ithSco re(-1, g ro   up2, group      3, group1,       v    alue       1));

           /              / In crementa            l
                      Testd     at   aLavishEn     ti     ty entity        = s   olutio  n. get  Fir   stEnti  ty();
           scoreDirec   tor.before  En  tityRemoved(entity);
             solu          t    i   on.    getEn  tityLi st().remov   e(ent    ity);
            scoreDi     rector.afte   rEnti      tyRemoved    (e     ntity);
            asse  r tSc     ore(scoreDirector);
            }
    
      //  ***     *   ********* ***********************  **********    **   ************      ******      **   ****
    //  Map/flatten /distinct
     /   / *******  ********************************************************   *   ****   ****

         @Override
    @TestTemplate
            p  u blic voi  d distinct() { //     On a disti         nct stream, this            is a no-op.
              Te  stdataLavis    hSolution sol      ution = TestdataLavishSolu   tion.generate    Soluti  on(2, 2, 2, 3);
         InnerScoreDire      ctor<Tes   tdataLav ishSo    lution,    Si mpl                     eScore >   scor eDirector             =
                                     bu ildScoreDi    rector(    factory -  >    factory.forEachUniqu  ePai          r(TestdataLavishE  nt    it    y. class)
                                                    .join(Test    dataLavishE          ntity.class, Joiners.filtering(     (a, b, c) -> a !  = c && b !=        c))        
                            .distinct()
                                               .penalize(SimpleSc        ore.      ONE)
                                             .asConstra    int(TEST_CO  NSTRAINT_NAME))  ;

               Testda  t   aLavish   Ent     i     ty ent  ity1 = s      olution.g      etFirstEntity();
        Testda        t      aL         avishEntity entity2 = solution.getEntityList().   get(1   );
            Testdat     aLavis     hEnt   ity entity3 = solutio       n.ge  tEntityList().g   et(2);

                   // From scratch
        scoreDirecto         r.setWorkingSolution(sol   ution       );
               asser   tS core(scoreDir      ector,
                  assertMatch(entity1,            entity2,           entity3    ),
                            ass   ertMatch(en     t  ity1,    entity3        , entity2),
                             assertM   atch(entit     y2, entity3,   ent     ity1));    
           }

    @O       verri               d   e        
         @   TestTemp   late
    public void mapToU  niWithDup      lica     tes() {
                  TestdataLavishSolution sol     utio n = T    es  t  da   taL  avishSolu tion.      generateSolution(1, 1,  2, 3);
        Inn  erScoreDire   ctor<TestdataLavishSolution  , S    i   mpl   eScore>           scor  eDirector =
                       bu   ildSc         oreDir  ector(fac   t ory -> facto  ry.forEachUniquePair(Testda    taLavishEnt   ity        .class  )
                                     .join         (    TestdataLavish     En      tity.class,       Joiners. filtering((a,  b, c  ) ->    a      !=     c        &    & b !   =  c))
                              .map((a, b, c) -> asSet(a.ge        tEnti     t   yGroup(), b.getE       ntit yGrou    p(), c.getEntityGroup()))
                          .  penalize    (Simp           leScore.     ONE)
                                              .asConstraint(TEST   _CON  STRAINT_NA ME));

        Testda    taLavishEnt    ity  Group gro   up1   =    s     o   lutio  n.getF        irs   tEntity  Gr    oup(  );
        TestdataLavishEntityGr   oup group2 = solution.getEnti  tyGroupList().get  (1);

                /          /   Fr    om scrat       ch
          scoreDirector.setWorkingSolu   tion(sol        ution);
        asser tScore(sco  r   eD      irector,
                   ass    ertMat    ch( asSet         (group1      , group2)),
                                  assertMatch  (asSe   t(group1, group2))   ,
                      as    ser    tM   atch(             asSe  t(group1,   group2)));
        
            Te  s  tda         taLav i     s     h  Entity      en  t   ity = so lut   ion.getFirstEntity();  

        // Incremental
        scoreD  irector.beforeEntityRemoved(enti  ty);
             sol   ution.getEntity    List().remove(entity);
             scor   e  Director.afterEntit    yRemoved(entity)   ;
        as        sertSc   ore     (scoreDirector);
    }

       @Override
           @Tes    tTemplate
    public    void ma  pTo    UniWithoutDuplicat  es() {
         Testda   taL  avishSolution            sol uti    on = Testd ataL       a     vishSolution.g      enerateSolution(1   , 1, 3, 3);      
        InnerScoreDirector<Test  dat  aLavi   shSo   lution, Si  mpleScore> scoreDirector =
                  buildSc        o    reDirec    tor(  f    a       ctory ->            factory.forEa     chUniqu eP    air(     Testdata   Lavis      hEntity     .class)
                             .join(Te   stdataLavish En   tity.class, Joiners.filter      ing ((      a, b, c)      -   >    a !=            c && b != c)         )   
                                       .map((a, b, c    ) -  > a     sSet(a.ge   t       EntityGroup(          ),     b.getEntityGroup()))
                              .penalize(Sim   pleScore.ONE)
                                   .asConstraint(TE      S     T_CONST        RAINT_NAME))    ;

          Test dataLavi   s hEntityGroup group1 = solution.getFir     stEntityGroup();  
        Testd        ataLav     ishEntityG   roup group2 =    solut  io n   .getE     n        tityGroupL  ist().get        (   1);
               TestdataL    a  vishEntityGroup group3 =   s   olutio n.getEn     tityGrou        pList().get(2);

          // From s    crat     ch
         scoreDirector.se    tWorki n           g         Solution(solut     ion);    
                                assertS       core(sc    oreDirector,
                as        sertMatch(asSe        t(group1, group2))  ,
                    a       ssertMatch(as    Set(group1, group3)),
                         assertMatch(    asSet(g  roup2,      group3)));

              T  estda taLav   i          shE  ntit y         en tit   y = sol    ution     .getFirstEntity();

         // Incremental
        scoreDirector.be   foreEn    ti ty  R   emoved(entity);
        solution.getEnt  ityList().remove(entity);
                  scoreDir ect     or.a       fterEntityRemoved(entity);
          a            ssertSco   r   e(scoreDirector);
        }

           @Override
       @TestTem plate
                 pub lic voi  d m apToUniA   nd    Di   sti n    ctWithD  uplicate    s( ) {
        TestdataLavis   hSolution so   lution =     T  es                             tdataLavishSolution.generateSolutio   n(1, 1, 2   ,    3                );
        InnerSc   ore     Direct or<TestdataLa     vish    Solution    , Sim pleSc   ore>    sc  oreDirect or =
                   buil dScore   Director(f  actory         -> factory.forEachUniqueP    air   (TestdataLavish  Entity.c                      lass)  
                                 .join(TestdataLavis   hEntity.class, Join ers. fi    lter         i      n    g((a, b, c) -> a !=      c && b !=     c))
                        .map((a,   b, c) ->    asSet(a.getEnti         tyGroup(), b.getEntityGro      up(), c.getEntityGr   oup()))
                                                                       .distinct()
                                .     penaliz   e(Sim   pleS core.ONE)
                                 .asConstraint(T          EST_C  ONSTRAINT_NAME));

             Tes    tdataLavishE n     ti      tyGroup group1 =    solution.getFirs      tEntityGroup();
        Testdata         LavishEntityGroup group2  =    so                 lu     tion.ge    tEntityGroupLis  t().get(1);

                  // F   rom scr   a      tch
        scoreDire            ctor.setWorkingSolution(solu    tion);
                assert      Score(     score    Directo  r,
                        assertMatch(a  sSet(group1, group2)));

              TestdataLavi     s     hEntity   entit   y = solution.getFirs tE   nti   ty();

        /     / In cremental
            s      coreDirec    tor.beforeEntityRemov  ed(entity)               ;
           soluti on.ge tEntityList().remove(entit    y     );
            scoreDirecto     r.afterEntityRe   moved(en tity);
        assertScore      (scoreDir  ector);
    }

       @Override
       @TestT  emplate
    p            ublic void mapToUniAnd  Distinc  tWit    houtDuplicates() {
        TestdataLavis     hSolut  ion solution =    Testd             ataLav      ishSol    ution.ge  nera  teSolution  (1, 1,   3, 3   );
             Inn   erScor    eDirect or<TestdataLavishSoluti       on,   Si  m pleScore> scoreDir  ec    t  or =
                buildScoreD       irecto  r(factory -> factor       y.forEachUn    iquePair(Tes   tdata     Lav     ish  Entity.clas  s)
                                 .j  oin(Test  dataLavish      Entity    .class, Joiners     .filter          in g((a, b  , c) ->     a != c       &&    b != c))
                                 .map((a,    b, c) -  > asSet(a  .    getEntityGrou   p(),   b  .getEn   tityGroup()))
                                  .dis  t              i  nct()
                                           .pe    nalize(S impleSc   or  e    .ONE)
                                     .asCon   strai  nt(TEST_CONSTRAINT_NAME)); 

               TestdataLavishEnt    ityG     roup group1         =      s        olution.getFirstEntityG     ro   up();  
        Tes   tdataLav is  hEntityGr       oup group2 = solution.getEntityGr    oupLis   t().g et(1);
        Tes   tdataLavishEn   tit    yGroup     group3 =    soluti   on.getE nti   tyGroupList().get(2);

        // From s      cra    tc  h
             scoreDirector.s   etW  orkin    gSo          lution   (solution) ;
             assert            Sc    or   e(scoreDir   ector,
                          assertMatch(asSet(          gro     up1, group2  )),
                  assertMatch(asSet(   group1 , gro    up3)),
                              assertM atch(asSet(group2, gro        up3)));

            TestdataLavishEntit y entit     y = solutio  n.getFirstEntit y();

             /   / Incremental
             scoreDirec       tor.beforeEntityRe     moved    (entity);
        s   oluti   on.g      e         tEntityList    ().remove(entity);
                          scoreDirec  tor.afterEntityRemoved(entity);
                  asser     tScore(score  Direct or);
    }

    @Ove  rr   i de
        @      TestTemplate
    p            ublic void mapToBi()     {
        Test   dataLavishSolutio n solution = Testdata        Lavis  hSol    ution.g ener    ateS      ol u   t          ion(1,   1, 3, 3);
                                          Inne       rScor   eDirector<Testd  ataL avishS        olutio      n,        SimpleScor e> score       Directo  r =        
                     buildScoreD   irector    (fa    ctory       -> factory.forEachUniquePa  ir(Testda taLav     ishEn  tity.class)
                                            .join(TestdataL     avishEnt   ity  .class, Joi  ners.f iltering((a  , b, c) -> a != c && b != c))
                                .m   ap((a, b, c) -   > a.getEnti tyGrou    p(),
                                (a, b, c) -> b.  ge   tEntityGroup())
                                            .pena    lize(Si           mpleSc    ore.  ON   E)
                                            .asCons tra int(TEST_C ONSTR   AINT_NAME));             

              Testdat   aLavishEntity  Group group1 =       solut    ion.      getFirstEntityGroup();
           Testd  a   taLavis   hEntityGroup group2     = solution.       getEntit          yGroupList().get   (1)      ;
                     TestdataLavi     sh   Enti      tyGroup group  3 = solution.getE ntityGroupList ().ge   t(2  )     ;

                     //        From          scratch      
            scoreDirector   .setWo  rkingSo l   ution(solution);
            assertSco    r     e(scor   eDirector,
                       assert    Match(   group1, group2), 
                      ass           ertMatch( group1, g    roup3),
                   assertMa  tch(group2, grou     p3));

              T estda    ta  L  avishEnti  ty       entity = solution. getFirst   Entity();

        // Incremental
        scoreDirector.beforeEntityRemoved (entity);
            solution.getEntityList().re   move(en   tity   );
        scoreDirec tor.a  fterEnti           tyRemoved(ent ity );
           a     ssertSco      re(scor    eD    ire ctor);
    }

    @Overrid  e
        @Test            Template
       publ   ic void mapTo Tri ()   {
        Tes  tdataLavishSo lution     solution =              Testdat     aLav       ishS     oluti     o  n.ge     nerateSolution(    1, 1, 3, 3);
        I  nnerScoreDi re   ctor<      Testda    taLavishSoluti   on, Simp     l       eScore> scoreDirector      =
                       buildScore       Director(  f actor  y    -> factory.fo         rEachUniqueP     air(Testd  ataLavishE ntity.class  )  
                              .join(TestdataLavishEnti    ty.class,        Joiners.filt  e    ring((a, b, c) -> a != c && b != c   ))
                               .map((a, b,          c) -> a           .getEntityGro    up    (),
                                         (a, b ,   c)  -> b.getEntit     yGr   o  up(),
                                      (a, b, c) -> c.getEntit        yGroup())
                             .penalize(Simpl       eScor     e.ONE )
                                 .asConst  raint(          TEST_CONSTRAINT_NAME));

           TestdataLavishE       ntityG     roup group1                = solution . getFirst     EntityG ro  up();
        TestdataLavishEnti tyGroup gro     up2 = soluti  o   n.    get           EntityGr          oup  List().get   (1);
        TestdataLav     ishEntit   yGroup group3 =       sol        uti     o    n.get    Ent         ityGroupL   ist().get(2);

        //       From scratch
               sco   reDire  ctor.setW              orkingSolution(solution   );
        assertScore(scoreDirec       tor,
                                 a     ssertMatch(group1, group2, group3),
                     assert  Match(  group1, group3,      group2),
                            asser      tMatch(     group2, g    roup3, group1     ))   ;

        Tes    tdataLa   vis      hEnt  ity en     ti    ty = soluti  on.ge     tFirstE ntity();

                       // Incremen tal
                 scor        eDirector.beforeEntityRemoved(e  ntity);
        solu  t ion. g  etEntityLis  t().remove(entity);
        scoreDirector.   afterEntityR  emove d(enti    ty);
               a    ssertScor   e(scoreDirecto   r);
    }

    @Over    ri de
            @TestTemplate
       publ             i c void mapT   oQuad() {
        Test   dataLavis hSolution soluti  on =   T   estd    ataLa   vis hSolution.g      ener                                ateSolution(  1, 1, 3, 3);
        InnerScoreDi         rect    or<Testdata  LavishSoluti   on,    SimpleScore> s coreDirect  or =
                            b uildScoreDirector (factory    -> f    actory.forEachUniquePair     (T es   t        dataLavishE     ntit        y.cla              ss)
                                  .joi  n(Tes  tdataLavishEntity.class,      Joi     ne          rs.     filter  ing  ((a, b, c) ->       a != c                   && b != c))
                                                 .  ma    p(   (a,  b, c) -> a.getEntityGroup(),
                                       (a, b  ,         c) ->            b.getEntityGroup(),
                                             (a, b,   c    ) -> c.getEntityGrou      p(),
                                           (a, b,     c)      -> a.getLon  gProperty() + b.g  etLongPr     opert   y() + c.ge             tL    ongProperty())
                                   .penalize   (Simp   l e     Score.ONE)
                                .a  sConstraint(TEST_CONSTR  AI   NT_NAME));

                  T        estdataLavishEntityGrou      p gr  oup1 = soluti  o        n.getFir  stEntityGroup( ) ;
         Testd  ata  LavishEntityGroup g  roup2 = solution.getEntityGroupList().get(1);
           T       estdataLavishEntityGroup group3 =          s   o  lution      .ge tEnt    ityGro              upLi   s t().ge    t(2);
                   long  su  m = solution.getEn      tityList().stream(   )
                                   .map  ToL        o   ng(TestdataL   avishEntity::getLon   gProperty)
                             .sum();

        // From  scratch
               scoreDirector.setWorking   S                ol    u  t  ion         (solut    ion);
               assertSc   ore(sc  or  eDir    ec tor    ,     
                        assertMatch(group1,         group2,   group    3, sum),
                   assert    Match(group1         , gr    oup3, group2, sum),
                            assertMatc               h(group2, group3, grou  p1, su   m));

               Tes  tdataLavishE        ntity e     nti       ty = solu      tio           n.getFirstEntity();

        // Inc          re  men  tal
         scoreD   irector.be  fo     reEntityR  emoved(entity    );          
          sol    ution.  getEntityList().remove (en  tity   );
                   scoreDirector.afte rEn   tity   Removed(entity);
        assertSco re(scoreDi    rec  tor);
        }

               @Over  ride
    @TestT   empla    t    e
    public void expandTo      Qu  ad() {
            TestdataLa    v  ishSo       lu tion solutio    n =    T   es  tdataLav     ishSoluti           on.ge   nerateSolution(1,      1           , 3   , 3)   ;
           In  ne   rScore  Director  <Te   stdataL    avi  shSo    lution, Sim    pl   eScore> scor     eD  irector       =
                 buildS       coreDir  ect     or(f       actory                -> factory.fo  rE         achUniquePair(T    es   tdataLav  ish Entity.class)
                                         .join  (TestdataL   avishEntit y.       cla    ss, Joiners.filt   eri  ng((a, b       , c)  -> a  != c     && b !=  c))
                            .expan d((a   , b, c   ) -> a.getLongPr     operty(  ) + b.getLongP   roperty() + c .get     Lo     ngP    rop  erty())
                              .      pen al  ize(SimpleSc  ore        .ONE)
                             .a sConstrai   nt(TEST_CONS    TRAINT_NAME));

                      Te   std ataLav  ishEn      tity entit   y 1       =       so  lution.getFir       s  tEnti ty();
                         Tes  tdat  aLav  i   shEnt          i       ty     en   tity2 =     solu tion    .getE    nti        tyList     ().get(1);
        TestdataLa  vishEntity entity    3 = soluti   on.getE ntityLis   t().get(2);     
                  long sum = solutio  n.getEntityList().st  ream()
                .mapToLong(T   estdataLav         ishEnt    i  ty::getLo ngProp      er      ty)
                     .   sum();

           //  F                rom scratch
              s    cor    eD    irector.setWor   kingSol  ution(     solutio             n);  
                 ass   ertS  core(scoreDirec tor,
                          assertMatch(entity1, entity    2,          ent    ity3, su   m),
                   assertMatch(entity1, e    ntity3, en    t        ity2, sum)  ,
                assertMatch(ent     ity2,         enti  ty3,   entit    y1, su     m));

        TestdataL   avi shEn     tit        y entity = solu          tio  n.getFirst        Entity()     ;    

                   // Incremen    tal
        scoreDirector.b       eforeEnt ity  Removed(entity);
         sol   ution.getEntityL ist(      ).remove(enti        ty);
                  score    Director.afterEn    tityR        emov     ed(entity  );
        assertScor  e(s      coreDirect   or);
    }
    
    @Overri  de
    @  TestTempl      ate
         public void      flatt   en       Last     Wi    th       Duplicates(   ) {
           Te           stdataLavishSol       ution solution =    Tes  t     da   taLavishSo  lut        io  n.genera     t     e  Solut   ion(1, 1, 2, 3);
                    TestdataLav   is          hEntity en    tit    y1 = solution     .getFirstEntity();
           Testd   ata Lavi    shEnt  ity entity   2 =      solutio               n.getEn tityList().get(1);
          TestdataLavishEnt ity entity3 = soluti on.ge  t         Ent      ityLi               st().get(2);
        TestdataLavishEntity  Gro up gr      oup1 =      so  lu      tion.getFirst   E     nt ityGroup();
               T    estdataLavishE         ntityGr  oup g     ro      up2     = solu   t        ion.g  etEntityG      roupLi  st().ge   t  (1);

                InnerScoreD    irector<Testdata  LavishSol          ution, Simp   leS     cor   e> score  Director =
                       buil   dScoreDirector(  factory -> factory.forE ac      hU   niq uePair(TestdataLav  ishEntity.clas         s)
                                                  .jo    in(T    estdataL   avish       Ent      it       y  .class     ,   Joiners .filtering((a,   b      , c)      ->      a != c &     & b != c)) 
                               .flattenLast(c ->    asLis       t(    c   .getEntityGr   oup()    , g    roup1, group        2  ))
                                        .p     enalize(SimpleScore .O  N   E)
                        .asConst      raint(TEST_CON       STRAINT_NAME));

              // Fro   m scratc    h
        s      coreDirecto r.setWorkin gSo         lution(soluti on);
            assertSco   re    (scoreDire         ctor,
                          assertMat  ch(enti   t  y1, entity2, group1),
                          a     ssertM  atch(entity1,    entity2, group1),
                        as  sertMatch(entity1, ent i ty2, gro  up2)    ,    
                    assertMatch(entity1, ent         ity3,               g   ro    up1),
                  assertMatch   (  e     ntity1, entity3, group2),
                a  ssertMatc     h(entity1, entity3, gr                oup2),
                                     assertMat     ch(e        ntity2,         ent      ity3 , g   roup1)   ,
                                     assertMa tch(e    ntity2 , enti ty3, gr     oup1),
                       assertMa    tch(entity2, enti   ty3, gro    up2));
 
            // Incremental
                    scoreDirecto  r.before  Enti     tyRemove       d(enti ty     1);
                so      lution.getEnti   tyL  ist().r   e   move    (enti          ty1);
        scoreDirector.af   terEntityRemov  ed  ( entity1);
              a          ss  e   rtScore(    scoreDirector);    
    }    

    @   Ove  rride     
    @Te      stTemplate
        publ  ic    voi                      d flattenLas tWithoutDuplicates() {
           TestdataLavis    hSolution solution = Testdat     aLavishS    olut ion      .g  enerat     eS   o    lution(1, 1,       2, 3);
             T estdataLavishEntity         entity1 = so    lution.ge  tFi     rstE n  tity();
                Tes  tdataLa   vishEnt  ity entity2 = solut  ion         .ge   tEnt    ityList().get(1);
              TestdataLav     ishEntity e   ntity3 = solution.getEntityList(  ).get(  2);
           T  estdataLavishE  ntit  yGr               oup   group 1 = so  lution.g                 e tFirst        EntityGroup();
        Test   da   ta          Lavis   hEnt  ityGroup group2 = solutio   n.getEntityGroup Li  st().get(1);

                     InnerScoreD    ire  ctor<Tes     tda        taLav i sh   Solution, Simpl            eScore>        scoreDirect  o                   r =
                        buil    dScore  Direc   t        or  (        fac   tory -> fac    tor      y.for         E  achUniq  uePair( Testda        taLavi sh   Entity.c   lass)
                                           .join      (TestdataLa               vishEntity.class,          Jo  in   ers    .filtering((a, b, c) -> a != c &&  b != c))
                          .flattenLas    t(c -    > asSet               (   c.getEntityG        r    oup(),   c.              g   etEntityGroup() == group1 ? group2      :      group1))  
                                                              .penalize(Sim  ple   Score.ONE)
                                    .asConstraint  (TE  ST_CONSTRAINT_NA   ME));

         //      From scra    tch
                              score Di   re ctor         .s   etWorki   ngSolution(sol ut ion);
        assertScor    e(scoreDi   rect  or,
                       assertMatch(     entity1, entit   y 2, g   roup1),
                assertMatch(enti   ty1, e  ntity2, group2) ,
                a  ssertM    atch(e   n     ti    ty1, enti  ty   3, group      1),
                   asser   t Match(entity1,    entity3, group2 ),
                          assertMatch(e   ntity2, e  ntity3, group1  ),       
                                assertMa   tch(ent     ity2, entity3,  grou       p2));

           // Incremental
                    scoreD   irecto     r          .bef or  eE   n tity Remov   ed(entit y1)       ;    
        s        olution.getEntity     List().remov       e(ent   ity1);
        score  Dir    ect     o       r.afterEntityRemo      ved(entity1);
             asse rtS       core(s   coreDirector);
    }

    @Ov erride
           @TestTemplate
    publi  c void flat    tenLastAndDistinctW         ithDuplicates()     {
                TestdataL    avish  So    lution solution =    TestdataLavishSol     u      ti   on.generat     eSolution(1, 1, 2, 3);
                  TestdataLav     ishEntity      enti      ty1     = so lution.getFirstEntity();
        Test  dat  aLavi  shEntity entity2 = solution.  getEn   tityLis  t(  ).get(1);
                  TestdataLa      vishEn   tity entity        3 = solution.get    EntityList(). get(2);
                        Testdata    LavishE     ntity   Group grou p1 = so lution.get   First       Enti     t  yG     roup      ();
        Te   std       ataLa                  vishEntityG   roup       group  2 = solution  .get   Enti    tyGroupLis        t().get(1);

        InnerScoreDirect       or<Test     data    Lavi  sh S   olution, S  impleScore> sco    r eDirec  tor =
                           build        ScoreDire ctor(factor            y -> factory.fo   rEachUniquePair(TestdataLav   ishE    nti              ty.class)
                          .  joi n  (Te       stdataLavishEntity.cl     ass, Joiners.filterin  g    ((a, b, c  ) -> a != c &&   b != c))
                          .flat  tenLast(c -> asL    ist(c.getEnt  ityGr  oup()    , group1, group2))
                                        .distinct()
                                                           .pen alize    (Si  mp   leScore.ONE)
                                               .asConstraint(TEST_CONSTRAINT_NAM   E));

                  // From  scratch
            s  coreDirector.setWorkingSoluti  on(solutio    n);
         as       sert        Scor   e(sc   oreDirector,
                       assertMatc    h(e  ntity1, e    ntity2, gro     up1),
                          asse r     tMa tc    h(entit y1     , en   tity2 ,      grou     p2),
                assertMatch(entity1, entity3,    group1),
                           assertMatch(enti     t     y1, en tity3,       gr      oup2),
                                           a                      s sertMat   ch     (e    n    t   it   y2, en                      tity3, group1),
                assertMatch(entity        2,      entity3, gro up2));

                /  / Incre    mental
           sc    oreDi                          rector.beforeE  ntityR      em  oved(entity1);
            so          lution.get    EntityList().    r  emove(e  nt ity1);
        sc   ore   Dir     ector.afterEntityRemoved(   entity1)    ;
        assertScor  e(        score   Dir   ector);
     }   
             
    @Ove     r    ride
    @TestTem p    late
    public         void flat    te     nLastAndDist   inctWi   thoutDupl              ica  tes() {
        TestdataLa   vishSolut   ion      solution =  TestdataLavis       h      Solution     .generateSolution( 1, 1     ,          2, 3);     
        T  es             td            ataLavish       Entity   entity  1 = solution.getFirstE    nti    t y(); 
          T        estdataLavi  shEntity entity2    = solut      ion.get  Enti   tyList(     ).     get(1);      
                           TestdataLavis     hEntity ent      ity3 = s      olut   ion.ge  tEnt ityList().   ge      t(2);
            Te             st  dataLavi   shEntityGroup gro   up1  =       solutio    n.getFi    rstEntityGroup()   ;
         Testda ta                  LavishEnti t  yGro      up                group2 = so    lution.getEntityGrou   pList().get(1);

                 InnerScore  Dir       ector<Testda     taLavishSolution, SimpleScore>          sc        oreDirecto   r           =
                 buildScoreDi      rector(f   actory -> fact        ory.fo  rE      achUniquePair     (Testda   taLavish      Entity.cl         as    s)    
                                                        .join(Testdata Lavi    shEntity.class, Jo       iners. f  iltering((a,  b , c) ->       a !=    c &&   b != c))
                                  .flattenLast( c -> a     sSe         t(c.getE                n            ti  tyGroup(), c.getEnt    ityGr   oup   () ==  group1 ? gr  oup 2 : group1))
                        .di              stinct()
                                          .penalize(Simp   leScor       e.O    NE  )        
                                               .       asCons  traint  (TEST_CON   STR    AINT_NAME));

              // From   scratch
        sc     o  re   D    i   rector.         setW    o  rkingS     olution(solution)   ;
                       a   ssertScore     (scoreDirector,
                      asse        r    t   Mat    ch(entity     1, ent ity2 , group1 ),
                       a    s   s    ertM          atc     h(entity1, entity2, gro up2),
                     assertMatch(entity1, entity   3, grou           p1),
                  assertMatch(entit  y      1, en    ti     ty3, group2),
                 assertMatch(entit            y2, entity3, gr       oup1    ),
                      assertMatch(entity     2, e ntity3,   group2));

              // Incremen    tal
                             scoreDir   ector.bef   oreEn   tityR     emoved(enti         ty1);
        s olution. getEntityLi          st()   .remove(entity1)       ;
         score    Direct  or.after  EntityRemov   ed        (entity1)    ;
                   assertScore(  scoreDirec     tor                     );
    }

           @Overr  id e
    @TestTemplat    e
       p     ublic      void con   catUniW   ithout   V                alueDup     licates() {
             TestdataLa     vishSolution so    lut   ion = TestdataLa    vishSolution.generateSolution(    2, 5, 1, 1);
          TestdataLavishValue va   lue  1      = s   olution.getFirs   tValue   ();
                    Tes    tdataLavish     Va   lue v      alue2 = new Testd       ataLavi shValu    e   ("MyValu     e 2",   solution.g  etFirstValueGroup());  
               Testdat       a  LavishValue            value3   =  n  e w Testda    taLavis    hValue(   "MyVal   ue 3",     solution.     getFi   rstValueGr    oup())   ;     
                TestdataLavishEntit  y e      nt   ity1 = solution.getFirstEntity();
        TestdataLavishEntity entit  y2    = new Testdat   aLavishEntity("MyEnti   ty 2   ",    solu     tion.                getFi  rstEntityGro   up(),
                     value2);
        solut  io     n.ge  tEntityList().a    dd(   e       ntity2);
            TestdataLavishEnti    ty entit  y3 = new         Testd ataLavishEnti  ty("MyEntity 3", solution.getFirstEntityGroup(),
                 value   3)        ;
                     solut  ion.getEnti  tyList()         .add(entity3);
  
                        InnerScoreDire    ctor<Testdata      LavishSolution, Simpl  eScore   >    scoreDire        c    t       or    =
                      buildScoreDir   ector(  factory -> fac        tory.fo  r   Each(Tes tdataLavi   shEntity.c          las   s  )
                          .filter(enti  ty -> entity.getValue() == value1)
                          .join(fa ctory.fo  rEach (TestdataLavi       shEnti t  y.cl            ass)
                                                  .filter(entity ->      entity.get   Valu   e() == value2))
                                    .join(facto    ry.forEach(TestdataLavishEntity.class)
                                                     .filt  er  (entity -   >       entity.      getV    alue()   =     = v  al   ue3)   )
                           .c oncat(factory.forEa             ch(T   est   dataLavishE   nti                   ty.class)
                                            .fil   ter(entity  -> enti     ty.get      Va    lue() == v    alue   2))
                                    .pen       aliz  e(SimpleScore.ONE   )
                                        .asCo nstraint  (TE ST_CO         NSTRAINT_NAM   E        ));

         // From scratch
              scoreDirector.        setWorkingSolution(solution); 
          as  se              rtScore(s  coreDire     ctor,
                       assertMatch(entity1, entity  2, e  ntity3),
                         assertMa  tch(entity2,         null, n   ul      l));     

             // Incremental
                    scor eDirecto    r.beforeV   ariableCha  nged  (entity3      , "  value");
                 entity3.setValu     e(value2);
                scoreDirecto r.afterV   ariableChange   d(entity3,    "      value");

              scoreDirector.be    f  oreVariabl        e C   hanged(en      t   ity2,   "valu    e") ;
                         entity2.setVa         lue(value3);
             scoreDi  r ector.afterVariableChan       ge   d(e  ntity       2,   "  value");
        assertScore(scoreDirecto    r, 
                       assertMatch(entity1, entity3, entity2),
                      assertMat      ch( entit         y3, nu   ll, null));
      }  

    @  Override
                  @Test   Tem   pla        te
    publ  ic voi   d c  oncatAn d Distin   ctUniWi      th     outVa lu   eDuplic    at    es() {
              TestdataLavis  hSolutio     n solu  t    i     on = Test       da    taLa vis   hSolu  tio   n.generateS  ol     ution(2   , 5         , 1, 1);
                   TestdataLavishValue value1 = so        lutio        n.       ge  tFirstV a   l    ue();
              Test  da taLavishValue            value2      = new Tes     tdata    Lavi  sh  Value("MyV  alue 2", solution.getFir   s      tVal    ueG   roup()           );
            Testdat  aLavishValue val  ue3 = ne w Testda    taLavishValue("MyValue 3", solution.getFirstValueGrou  p())   ;
            TestdataLavishEntity entity1    =     solution   .getFirstE    ntity   ();
        TestdataLavis        hEn   tity enti   t    y2 = ne  w Te          stdataLavishEntity("M     yEntity 2", s   olution. getFirst  E    ntityGrou   p(),
                value2);
        solution.getEntit          yList(       ).a        d          d(entity2);
              TestdataL            avish     Entity ent ity3  =       new Tes    td            ataLavishEntity     ("My   Entity    3"   , solution.g    etFirstEn ti     t     yGroup(     ),
                                  value3);
               so   lu    tion.ge        tEntit yList().add   (entity3);

              InnerScoreDirector<T     estdataLavish Solut     i     on, SimpleSc   ore>     scoreDirect          or     =
                   buildScoreDirector(facto ry            -> fac       tory.f      orEach(Te    stdat   aLa   vishEntity.class)
                                    .filter(enti    ty     -> enti  ty.getV  alue()  ==    v     a    lue1     )
                                 .joi         n(        fac tory.forEach(TestdataLavishEntity     .cla    ss)
                                   .filter(enti  ty -> ent  ity.getVa   lue() ==          value2))
                                                     .    j    oin(   factory.forEach(TestdataLavishEntity.c        lass)
                                              .filter     ( e     ntity ->    entity.   getVal  ue() == value3))
                                                    .con      cat(fa      ct   ory.forEach(T     estdata             LavishEn         tity.      class)
                                                   .filter(entity -> entity.ge  tValue  () ==    value2))
                          .distin   ct()
                                      .pe  nalize(SimpleS    core.ONE)
                           .asConstra   int(T   ES T_CONSTRAINT_NA  ME));

                    // From    scrat    ch
               scoreDirector.set         WorkingSol uti   o   n  (solution) ;
          a    sser       tScore  (scoreDirect  or,
                                   a          ssertMa  t ch(entity1, e n    tity2, e      ntity3),
                                                  asse            rtMatch(e    ntity2,         null, null));   

               // Incremental
                scoreDirector.befor   eVari       ableC   hanged(entity3, "va  lu   e");
                               e      ntity3.setValue(value2);  
         scoreDirector.a  fterVariableC          ha     nged (entity3, "value" );

                 score      Dire  c     tor .beforeVar  iableChanged        (entity 2 , "value");
                       enti     ty2.     s etValue(v alue3  );
          scor  eDirecto  r.afterVa         riab             leChang    e    d(entity2    , "value"  );
            assertScore(s     c       oreDire     ctor,
                                    asse     rt    Match(entity1, entity3,         entity2        )                                  ,
                         a ss ertMa  tch(  e      ntity3, n  ull, null));
    }

           @Over     ride
    @TestTemplate
    public vo   id concatBiW     ithou tValu       eDuplicates    ()    {
                     TestdataLav         i   shSolution solution = Tes                tdataLavishSolutio     n.g  enera       teSolution(2,         5, 1,         1);
           Testdata      Lav   ishValu  e va   lue1 = solu tion.getFirstValue();
                            Testd  ataLavishValue       va lue2      = new  Testda        taLavishValue("MyValue 2",        sol     u  ti         o n.getFir stVal         ueGro  up( ));
                       Te  stdataLavish  Value     val      ue3 =       new   Test dataLavishValue("MyValue 3"      ,     solution.    getFir  s  t  V     alueGroup());   
            Te   stda   t        a         La v          ish   Entity ent  i   ty1 = so   lution   .getFirs     t    E   n  tity();
        TestdataLavishEn                  ti  ty e   ntity2 = ne  w Te           stdata  Lavis   hEntit y("MyEntity 2", sol   ution   .getFirstE            n          tityGroup(),
                value2)           ;
         soluti        on.getEntityList().add(entity2)     ;
             TestdataLavish     E    nti   ty entity3 = n      ew T  estda    taLavishE    nt    ity("MyEntity 3", solution.getFir    s    tE     ntityGroup(),
                         value3);
        sol  ution.getEntityList().add(entity  3);

        InnerScoreDirec      tor<TestdataLavishSol   utio  n,   SimpleScore     > scoreDi  recto     r =
                                           buildScoreDirector(f       act    ory ->     f         actor              y  .forEach(Testdata      La  vishEntit  y.clas    s)
                               .filter(entit  y -> entity.getV      alue()         == valu  e1)
                                  .join(        factory  .forEach(TestdataLavishEnti     ty.cl  ass)
                                                 .    filter     (e    ntity -> entity.getValue() == value             2))
                          .join(fa          ctor             y.forE ac     h(  TestdataLavishEntity.clas       s)
                                                .fi     lter(entit  y       -> ent ity.getVa     lue()    == v   alue3)       )      
                          .concat(factory.f   orEach(       Testdat  a                  Lavi    shEnt            it y.cla      ss    )
                                           .fil    ter(enti  ty -> entity.g      etVa  lue()      == value  2)
                                         .join(factory.f     orE      ac   h(Testda    ta   LavishE           ntity.class)
                                                                                      .filter(entity -> entity.g       etVal ue() == value3    )))    
                                      .penal  i ze(S    i   mpleScore.ON  E)
                              .asC    onst       r    aint(TEST_CONST  RAINT_NAME  ));

                  //         From sc   ratch
              sco    reDire     ctor.s    etWorkin        gSolution (sol   uti                   on);
           as       sertScore(scoreDirecto      r,
                         as   sertMatch(entity    1, e     ntity2, entity   3),
                                 as    se  rt  M   atch(entity2, e  ntity3    , null));

        // Incre ment al
          scoreDirecto   r.beforeVariableChanged(entity3, "va lue")       ;
                         entity3.setValue(value2);
                    scoreDire   ctor.afterVariableC  hanged(entity3, "val  ue");
        
                scoreDire      cto r.b         ef  oreVariableCha    nged(entity2, "value");
        entity2.setValue     (        va            lue3);
        scoreDirector.afte    rVariable  Changed (entity2, "value");
                  ass    ertScore(scoreDirector,
                    ass  ertMatch( entity      1, entity3, e         n tity2),   
                                      assertMat  ch(enti    ty3,    e  ntity2, null));
         }

    @Override
      @TestTemplate
    pub    lic vo  id c            oncatAndDisti            nctBiWithoutValueDuplicate      s()    {
           Testdata     LavishSolution           solution   = Testdat  aLa             vishSolution.generateSolution(2  , 5,   1, 1);
          TestdataLavi    s hValue     va    lue1 =  sol      uti     on.   getFirst        Val     u    e() ;
           TestdataLavishValue valu     e2 = new Te      stdat a  L   avis   hValue("My    Value 2   ", solution.getF  i rstValueGro up());
           T     est        dataL    avish   Val  ue val     ue3     =   ne    w       Tes   tdataLavishVa l    ue("MyValue 3", soluti  on.getFi     rstValueGr   oup());
        Test    dataLavishEntity   en     tity1 = solution.ge  tFirstEntity();
                     TestdataLavish Entity entity   2 =       new    Testd       ataLav  ishEntity("MyEntity 2", solutio     n.ge     tFirs   tEnt            ityG                roup(),
                   value2);
        solut   ion.getEntityList().add(entity2);
        TestdataLavishEntity entity3    = new TestdataLavishEntity("MyEn     tit y 3", solut  ion.getFirs  tEnt   i  ty Group(),
                                valu    e   3)    ;
         soluti          on           .  getEn  tity     List().add(entity3);

               InnerScoreD      i      rector<TestdataLavishSo         luti    on,    Sim  pleScore> scoreDirector =
                          buil     dScoreDirector(factory -  >   factory     .forEac   h    (Testda    ta   LavishEntity.class)
                                       .filter(entity -> ent   ity.     getVa     lue() == va   lue1)
                            .     join(fac      t   o       ry.fo   rEach(      Testdat  aL  a         vishEntity.class)           
                                                    .filt       er  (entity -> e            ntity    .getValue() =  = valu  e2))
                               .j    oin(factory.forEa         ch(TestdataLavishEntity.c    lass      )
                                           .filter( entity ->        e      ntity.get                Value() =  = value3))
                                     . c    onc    at(f     acto ry.forEach(TestdataLavish Entit          y    .clas s       )
                                                           .filter(entity ->        entity.ge    tValue() == value2)
                                                         .join(factory.fo       rEach(   Tes          t       dataLavishEntit         y.class)    
                                                      .f   ilter(entity -> entity.getV   a lue() == value3)))
                               .distinct(       )
                                             .penalize(Simpl eScore.ONE)
                             .asConstra           int(T   EST_CONSTRAINT      _NA     ME));     

                   // From      scra           tch
        scor eDi rector.setWorkingSolution(solutio       n    );
        asse  rtSc   ore(    scoreDirector,
                assertMatch (enti    ty1, entity2, e     n     tity3),
                assertMatch   (enti     ty2   , en  tity   3, null));

        // In  c    reme     ntal
        scoreDirector.beforeVariableChanged(entity3   , "valu    e");
        entity3    .setVa lue(v    alue2);
        scoreDirecto    r.afterVaria      bleCh      anged(e                ntity3, "val  u     e");

               scoreDire     ctor.be    fo  reVariableCh   anged(entity2, "value")   ;
           entity2  .setVal ue(va    lue3);
            scoreDirector.afterVar    i         ableCha    nge  d(entity2, "value");
              assertScore(s      coreDirector,
                asse rtMatch(entity1, entity3, entity2),   
                 assertMatch(entity3,      en  tity2, nu  l    l));
      }

    @Over    ri   de
         @Test      Template
      publi    c void c  oncatTriWithoutValueDuplicates() {
           Tes       tdat  aLavis      hSolution soluti  on =      Testdat  aLavishS olution.generat      eSolu      tion(2, 5, 1, 1);
        T  e  stdataLavishVal          ue value 1 =    sol ution.getFirstValue();
        TestdataLavishV  alue value2 = n     ew     TestdataLavis       hValue("MyValue 2", sol ut    ion.getF   irstValueGroup());
          Test      data     La    vishValue value3   = new       Tes      tdataLavishValue(      "MyValue   3", solution.getFirstValue Group());
               TestdataLavishEntity   en              tity1 = sol ution   .getFi  rstE         ntity() ;
                       TestdataLavishEntity   entity2 = new Tes               tdataL         avishEntity("MyEntity 2", solution.getFirstEntityGroup (),
                value2);
                   solution        .getEnt  i t   yList().add(ent     it  y2); 
                    TestdataLavis hEntity entit           y3     = new T     es    tdata LavishEntity("MyE        ntity        3", solutio     n.getF     irstEntityGro   up(),   
                                     value3);
        solution.getEn       tity  L    ist( ).add     (entity3);

         I     nnerScor  eDirec        to    r<TestdataLavishSoluti  on , SimpleScore> score   Director =
                bui  ldSc      o    r eDi    rector(factory -> factory.forEach(Tes  tdat    a     Lav  ishEnt    ity.     class   )
                          .   filter(entity      -> entity.      getValue() == value1)
                            .jo   in(factory     .forEach(TestdataLavishEn tity.class)
                                                       .filte     r(entity ->  entity.g     etValue() == val   ue2))
                         .  join(factory.f    or     Each(Testda  taLavishEntity.class)
                                         .filter(entity         -   > ent       ity.getValue() == value3)  )
                           .concat(facto   ry.for  E ach(T   est   dataLavishEntity.class)  
                                       .    filter(entity      -> enti   ty.getValue() =    = val  ue2   )
                                                                       .join(factory.forEach(T         estda   taLa      vi     shEntity.       cla    ss)
                                                .filte  r(entity  -> en    tity.getValu e() == value3    ))
                                     .joi    n(f       actory.forEach(     TestdataLavishEnti  t y.c    la       s     s)
                                                                     .filter   (entity ->     en     t  ity.getValue() == value1)))
                                            .penalize(S  im   pleScor    e. ONE)
                           .  a  sConstraint(TES   T_CONSTRAINT  _NAME));

             // F rom        scratch    
        s  coreD  irector.        setW   o  rkingSolution(solutio    n);
        assertScore(sco     reD  ir ector,
                assertMatch( ent    ity1,       en t ity2,       ent i   ty3   ),
                             ass ertMatch(      entity   2, entity3, entity1)     );

        // I     ncre    me nt a l
             sc    oreDirector.beforeVari   ableChanged(entity3   , "   val        ue");
                       entity3.s  etValue(va     lue2);
        scoreDirector.afterVariableChanged(          entity3, "value");

                  scoreDire   ctor    .befor    eVariable          Changed(entity  2, "v    alue");
        entity2.setVal    ue    (v   alue3   );
        scoreDirect  or.afte     rV      ariab leChan  ged(   entity2, "value");
           assertSc  ore           (   scoreDire ctor,
                               ass ertMatch(entity1, entity3, entity2),
                         asse      rt   Match(entity3,       entity2, entity1))   ;
    }
    
    @Ov   er  ride
        @  Tes  tTe    mplate
      public void co ncatTriWith           Va  lueDup  licates() {
                    Test       dataLav    i        sh     Solution  s     ol  ution = Testdat          aLavishSolut  ion   .generateSolutio    n(2, 5, 1, 1   );
            TestdataLavishValue value1 = s            olution.getFir stVal    ue();
        TestdataL  avishValue        value2 = new T    es    tdataLavish    Value ("   My Val   ue 2",   s   olu   tion.getFirs  tValueGroup());
                    Testdat       aLavishV   alue value3 = new Testda    t   aLavi    shValue("M   yValue 3" , solution.getFirstVal      ueGroup()  );
        T estdat    aLav  ishEntity enti   t         y1 = solution.g et  FirstEnti ty   ();
                     TestdataLavishEntity ent    ity2 = ne   w T    estdataLavi      shE   nt  ity       ("MyEntity   2", solution.getFir    stEntityGroup(),
                value2                        );      
        s     olutio    n.getEntityList().add(entity2);
        T  estdata   Lav  ishEntity entity3 = new TestdataL     avishEntity("MyEn  tity 3       ", solution.getFirstEntityGroup(   ),
                 v    al  ue3);
        solutio             n.  getEntity   List( ).add(ent   ity3);

        InnerScoreDirector<   T  estd   ataLavishSol      utio      n, S    impleScore> scoreDirector               =            
                    bui      ldScoreDirecto    r ( factory -> fa      ctory.fo  rEach(TestdataL avishE      nt          ity.class        )
                                                   .filter(entity    -      > en     t  it  y.getVa     l   ue()       ==    va   lue1)
                               .jo  in(factory.f   orE   ach(Te   s        tdataLa  vishE   nt      ity.cla  ss)
                                   .fi    lter(entity -> entity.getValue()  == valu  e2))
                                          .joi   n(f actory.   forEa      ch(TestdataLav       i     shEnti    ty.c  la          s        s)
                                                      .f  ilte     r(entity -> enti ty.   getValue() ==            value3))
                                    .con      cat(factory.f  orEach(Testdat   aLa                  vishEnt  ity.  cla  ss)
                                                        .filter(enti    ty -> e   ntity.getValu   e() == v alu e1)
                                              .join(f         actory.forEach(Te  stda   taLavishEn tity.class)
                                               .     filter     (entity  - > entity   .getValue() == value2))
                                                    .joi        n(facto   ry.      forEac  h       (Tes    tdataLavishEn t  it    y.class)
                                                           .filter(enti    ty       -> entity.g    etV       alu       e() =     = value  3)))
                                  .penalize(SimpleS         core.ONE)
                                        .asConst    r   ai  nt(TEST_C     ONSTRA INT_NAME));

                               /  / Fr          om scratch
                scoreDirector.setWorkingSol  ution(solution);
                  assertSc  ore    (s       c              oreDirector,
                          assertMatch(enti   ty1, e      nt          ity2, enti  ty3),
                assertMatch(e  ntity1, e  ntity  2,  ent    ity3));

               // Incremental        
           sc     oreDirector.beforeVariableChange     d(entity3, "value");
                entity3.setValue(v     alue2);
         scoreDirecto    r.afterVari      ableChanged(entity3, "value"  );

          s     cor       eD   ir ector.beforeVariableChanged(ent              ity2, "value");    
           en  tity2.set Value(value3    );
            s       core     Director.afterVariableChanged(enti  ty2    , "value ");
           assertScor    e(scoreDirector,
                       assertMatch(entity   1,  ent  ity3, entity2),
                                   a  ssertMatch(       entit  y1, ent  ity3, entity2));
        }
 
         @Override
     @TestTempla  te       
    public void  concatAndD                 i        s  tinc            tTriWithou   tV          alu  eDuplicates() {
           Testd     at aLavishSolution solut    ion = Test       da   taLav    ishSolution.g     enerateSo      lution      (2, 5, 1  , 1)         ;
            Te stdataLavishValue     value1    = sol        ution.getFir    stValue()   ;
             Testdata  LavishValue val ue2 = new       TestdataLa    vis hValue("My     Value 2",               so  lution.    getFir    stV  alueGr    oup());
                         TestdataLav    ishV   a              lue value    3 =        new      TestdataLavishValu      e("MyV      alue 3            ", s   olution.getFirstValu  eGroup   ());    
        TestdataLavis hEntity entity1  = sol   utio   n.       ge  tF          irstEntity();
           TestdataLavishEntity   ent  ity2 = new    Test     dataL  avishEntit     y("MyEntity   2    ", solution.get     Fir      stEntityGro            up(),
                val    ue2);
                 solution.     get    Ent ityList().add(en          t   ity2);
            Testda taLavish   Ent   ity entity3 = new Test  dataLa vishEnti     ty("MyE    ntity 3          "   , solutio    n.getFirstEntityGro     up(  )         ,
                     value3);
              sol   utio    n.g     etE    nt    ityLi  s t()   .ad   d(enti  ty3);
              
        Inn   erScore    Dir     ecto   r<Testdata   Lavish        So  lut     io      n,            SimpleScore> scoreD    irector =
                   b uildS cor   eDire     ctor(         fac  tory -> fact   ory.forE     ach     (TestdataL     avishEntity.class)
                           .filter(entity -> entit    y.getVal    ue() == value1)     
                             .    join(fa        ctory.forEac  h(TestdataLavishEn  tity.cla     ss         )  
                                                    .   filter(entity   -> entit y.g  etValue()    == value2))
                                                 .join(factory.fo           rEach(TestdataLavis      hEntity.class)
                                                 .filter(entity -> en   tity.ge  t   V      alue    () == value3))
                                     .concat(factory.forEach        (TestdataLavishEntity.class)
                                        .filter(e  ntity      -> ent    it                   y.getVal   ue() =    = value2  )
                                                                        .join(factory.forEac   h(TestdataLavishEntity  .class)
                                                                 .fi     lter(ent  ity -   >         entity.getValue  () == value3))
                                      .jo   in(f   acto ry.forEac       h(Te    stda taLav  ishEn   t  ity.class)
                                               .    filt   e        r(entity ->   enti  t    y.getValue() == value1)))
                        .   distinct()
                                .   p       enalize(  SimpleScore        .ONE)
                                         .asConstr ain       t(TEST_CONSTRAINT_NAME));

                    // From s   c   ratc          h
            s coreDirec      tor.setWorkingSo          lution(s olution);
        assert   S    core(scoreDirector,
                         a  ss   ertMat                 ch(entity1, entity2, e    ntity3),
                as   ser  tM    atch(entity2, entity3, enti  ty1));

            // Incre        mental
         scoreDire        ct or.beforeVariableCha    nged(e   ntity3, "v  al      ue")       ;
                    entity3.setValu     e(value2);
          scoreDirect or.afterV          a  r   iableChange d(en            tity3, "value");
    
        sc   oreDirector.beforeVariabl      eChan  g ed(entit    y2       , "value");
            entit    y2.se  tValue(v    alue    3);
              scoreDirector.afterVariable   C    hange   d(ent        ity 2, "value");
        assertScore(scoreDirect   or,
                   assertMatch(entit  y1       ,         entity3, entity2),
                     ass  e         rtMatc   h(entity3,  ent  ity2, en tity1)); 
    }

             @  Override
                @TestTempla     t  e
    public void conca  tAndDi  stinctTriWithValueD  uplicates() {
                    Test    dat   aL    avishSolutio  n solutio   n = Tes     tdataLavishSolution  .generateSoluti  on(2,            5,   1,         1);
        TestdataLavishValue value1 = solution.getFirstValue    ();
          TestdataLavishValue    value2 = new Tes    tdataLa   vishValue("    MyValu  e                    2", s  olutio      n.getFir   stValueGrou     p());
        Test  dataLavishValue v      al   ue3 = new TestdataLa  vishValu e("MyValue 3",      solu ti            on.getFirstVa         lueGroup());
                TestdataLavi     shE     ntity   entity1 = solutio    n.getFi         r  stEntity();
            TestdataLavi   shEntity enti    ty2 = new Tes    tdat      aLavishEntity  ("My        Entity 2",    s  ol          ution.  ge t    FirstEnt                ityGroup(     ),
                   value2);
         s    olution.getEnt          ityLis    t(). add(entity2);
                     TestdataLavishEntit  y entit y3 = new T  estdataLavishE  ntity("M   yEntity 3", s  olu     tion     .getFirstEntityGroup(),
                value3        );
         s oluti   on.getEn   t        ity         List().add(entity3);

                  I  nnerS             coreDire       c    tor<TestdataLa vishSolution, Sim ple      S     core> scoreDi   rector =
                                buildScore    Director(factory -> factory.for   E       ach(TestdataLavishEntity.class)
                                           .filter(entit  y -  >                          e ntity.getValue(  ) == v  alue1)
                           . j      oin(factory.  f        or             E     ach(Testda taLavishEntity.class)
                                            .filt      er(e  ntity -> e ntity.   getVa lue() == valu e2))
                                        .j   oin(factory.forEach(T     estdataLa  vishE     ntit       y.class)
                                             .fil   ter(entity ->     entity.getValu   e( ) == valu  e3))
                                          .c   oncat(   factory.forEach(T  estdataLavis            hEn   ti      t y.class)
                                  .     filt  er(ent      ity -> entity  .getVa         lue()          == value1  )  
                                                                 .join(facto   ry.     fo    rE  ach(T     estdataL     avishEntity.c     lass)
                                                          .filter(ent ity -> entity.getValue() ==  value2))
                                           .j  oin(fact ory.forEach(TestdataLav        i  shEntit y.class) 
                                                                        .filter(entity      -> enti    ty.   getV  a lu e   ( ) == val           ue3))      )
                                         .d      istinc t()
                                         .   p enalize(SimpleScore.ONE)
                             .as  Constraint(T     EST_CONSTR  A    INT    _NAME));
    
        // From scratch
        scoreDirec    tor.setWor kingSolution(solution);
        as   sertScore(scoreD    irector,    
                          assertMatch(en tity1, ent  it  y2, enti    ty3));

                // Incremental
                            scoreDir    ector.beforeVariableChanged(enti  ty3, "value"           );
          entity3.setValue(value2);
           scoreDirecto   r.afterVariableC ha    nged        (ent      ity3, "v    al ue" );

               scoreD  irector.bef    oreVaria   bleCh                anged(entity2    , "v alue      ")  ;
        entity2.setValue (    value3);
                       scoreDir ector.   afterVariab    le     C hanged(   entit   y2, "val   ue");
           asse     rtScore   (sc   or    e       Dire  ctor,
                          assert     Match(  entity1, entity3, enti           ty2   ));
    }
   
    @Override
    @TestTemplate
    pub   lic void co  ncatQuadWithout     ValueDuplicates() {
        TestdataL     avishS o     lut i on solution = Testdata   LavishSolutio    n.g     e      ner      ateSolution(2, 5,      1, 1);
            Testda  taLavishValue val   ue1 =  sol   ution.getFir            stValue();
                 Testda   taLavishValue valu  e2          = new    Testdata    LavishValue("MyValue    2", solution.getFi       rs    tValue   Group());         
        TestdataLavishValue valu   e3       =  ne    w Test        dat      aLavishValue      ("MyValue 3", sol   uti   on.getF irstValueGroup(         ));
        Testdat       aLa  vishEntity en    tity1 = solution.getFirst   Entit     y(   ); 
          Test    dataLavishEntity entity2 = new Test    dataLavishEntity      ("   M    y    Enti   ty 2", solut    ion.ge      tFirstEntit  yGroup(),     
                       v    alue2);
        solu    t  ion.getEntityLis     t(  ).add(entity2);
                Testd      a  taLavishEnti  ty  e  ntity  3 = n           ew T                                   estdataLavishEnt  it        y(            "MyEn        tity  3", solution.getF  irst      E     ntityGr                oup(),  
                         value3);
           solution.getEntit  yLis         t().add(en  tity3);
      
              InnerScoreDire  c      tor<Testd    ata    L  avishSolution,          S            impleScore> scoreDirector =
                    buildSco r        eDirector(factor   y -        > factory.forEach(TestdataLavishEn tity        .cl      ass)  
                                 .f         i  lter(entity      -> e   nti   ty.get     Value  ()     == value1)
                                                         .join(factory.fo   rEach(T  estdataLavishEntity.class)
                                                                .filter(entit    y -> entity.g    et  Value() =   = value2))
                                 .join(factory        .f    orEach(Testd    ataLav ishEntity.cl ass)
                                                     .filter(ent   i  t   y -> entity  .getVa  lue   () ==          v      alue3)     )
                                            .co  ncat(fa   cto  ry.fo     rEa    c   h(TestdataLa  vishEntity.class)
                                    .filter(en tity -> en tity.getValue(   ) == value2)
                                        .join(fa   ctory.f       orEach(      TestdataLavis    hEntity     .class)
                                                         .filt    er(en    tit    y -> enti      t        y.getValue() == value3))
                                                                      .jo    in(factory     .fo     rEach(TestdataLavishEntity.cl   ass)
                                                 .f      ilter(ent     ity -  > en       ti   ty.getValue() == value1))
                                            .join(factory.forEa       ch(TestdataLavi   shEnti    ty           .class)
                                                     .filt      er(entity       -> entity.  getVal        ue() == valu              e2)))
                                   .pe  nalize(Sim  pleSc  ore.ONE)   
                                           .asConstra  int( TEST_C      ONSTRA  INT_N      A    ME  ));

        // Fr        om scratch
               s    coreDi          r    ec tor.s    etWorkingSol        ution(solution);
                asser  tScore(scoreDi    rector,
                                               assert    Mat      ch (entity1, e  n     tity2,       enti       ty 3, null)   ,
                            asse          rtMatch(entit  y2, enti ty3, entity1,        entity2)               );
  
                    //      I     ncremental
        sco     reDirector  .befor    eVariableChange     d(entity3,      "value"     );
                     enti     ty   3.setValue(value2       );
         scoreDire      ctor.after     Variab  leChanged(enti   ty3, "value");

        scoreDirec   tor.beforeVariableChanged(en    t     ity2,     "valu   e");
             entity2.setValue(value3    );
        scoreDirector.a    fte  rV  ariableChanged(enti    ty2,      "value");
           assertScore(sc     oreDire   ct or,
                      assertMat         ch(en  t       ity1, enti          ty3,   entity2, null),
                           assertMatch (entity3, entity2, entity1, entity3));
        }

         @Ove     rride
    @TestTemplate
     public void concatA      ndDist  inctQua     dWithoutValue  Duplicates() {
         Te       stda   taLavishSol      ution solution = TestdataLavishSolution.gene   rateSo       lution(2  ,   5, 1  , 1);
             Tes  tdataLavi   shVa lue value1 = solution.getFirstValu   e           ();
         Testdata     LavishValue valu       e2 = new    TestdataLavishValue( "MyValue 2", s ol         ution    .getF     irstVa  lueGroup   ());
           TestdataL    avishVa   lue value3    = new Test      dataLavishValue("MyValue 3", solu   tion.getFir  stVal  ueGroup())  ;
                 Testd   at      aLa       vishEntity entity1   =    s          olution.g    etFir     st       En   tity(   );
            TestdataLavishEntity en  tity2 =        n    ew Te   s       tdataLavishEntit     y ("MyE  ntity 2", solution.g etFirstEntit   yG    roup(),
                        value2);
                     s       olu  tion.getEntityList().ad          d(entity2);
        Tes        tdataLav   i        s   hEntity entity3 = new T    estdataLavishEntity("MyE               nti       ty 3"         ,      solution.getFirstE          n     ti    tyG  r   oup ()        ,
                       va   l     ue3);       
               solution.getEntityList().        ad             d(ent  ity3)  ;
 
                     InnerScoreDi   rector<TestdataLavish    Solut ion, S   impleScor       e> score  Direct      o     r =
                      bu    ildS     coreDirecto r(   factory -> f   ac         tory. for E     ach(Te      stdataLavishEntity.class  )
                                     .filter(entity -> entity.getV alue(  ) == valu               e1)
                          .join(fa  ctory.for      Ea    ch(Tes   tdataLav   i  shEnt  ity.class)
                                                  .fi          lter(entity -> ent  ity.get   Value  () == v    a     lue2))
                                        .join(factory.forEach(Tes tdataLavishEnt ity.class)
                                                      .filter(e     ntity -> entit    y.getValu  e(     )     = = v a   lue3)       )
                              .concat(factory.  forEach(TestdataLavishEntity.class)       
                                                 .    filter(ent       ity -       > ent    i  ty.getValu  e  () == v   alue2)
                                                                       .join(  factory.fo   rEach(Tes  tdataLav  ishEntit   y.cl                   ass)
                                                  .filter(entity -> entity.getValue()           ==  value      3))
                                                       .join(fac        tory.   f     orEach(TestdataLavishEntity.class            )
                                                 .f     ilter(entity -> en     tity.ge   tV   alue() ==  value1))
                                                 .join(factory.forEa      ch(Te   st    dataLavishEntity.cl   ass)
                                                .filter(entity -               >    entity.getValue() == valu      e2)))
                                                       .dist   i         n              ct()
                         .pena       lize  (SimpleS      cor      e.ONE)
                           .asConstrain        t(TEST_CONSTRAINT       _NAME))   ; 
 
               //  From scratch     
        scoreDirector  .setW   orkingSolut     io   n(solution);
        assertScore(      s   c        ore      Dire       ctor,
                  assertMatch     (en tit     y1, e     n    tity2, ent    ity3, null),
                                 assert Match(e   nt     ity2,   e   ntit     y3, entity1, entity2));

               //   Incr   emental
            scoreDir    ector.beforeVa   ri     ableChan   ge   d(entity3, "value         ");
        ent   ity3.setValue(valu  e2);
            scoreDirector.afterV   ariableChanged(entity3, "value");

        scoreDirect    or.beforeVariableChanged( e  ntity2, "v alue       ")    ;
            entity2.setValue(va    lue3);
        scoreDi  rector.a fterV          ariabl      eC hang     ed(entity2, "val    ue");
                   assert   Score(    s    c   oreDirector,
                  assertMatc h   (entity1, enti       ty3,   e nti  ty 2, null  ),
                     assertM     atc h(entity3,           entity2, entity1, entity3));
    }

    @Ov  err         ide
    @TestT    em  plate
        public void concatAfterGroupB       y() {
        Test     data     Lavi       shSolution solution = TestdataLav              ishS    oluti  on.ge  ner         ateS   olut       ion(2,   5, 1, 1);
        Testdat    aLav            is   h    Value value1 = s        olution.getFi   rstV alue();
           TestdataLavishValue value2 = new TestdataLavish    Value    ("MyValue   2 ", solution.get    Fi  r            stVal        ueGroup());
              Te   st   data  LavishValue value3 = n      ew Testdata    Lavis     h           Value("MyVa lue             3", sol   u     tion.getFir   stVal     ueGroup());
         Testdat  a LavishEntity entit   y1 = solution.getFirstEntit     y();
         TestdataLa   vishEntity entit y2 = new TestdataLavish  Entity(    "MyEntity 2", solution.getFirstEntityGroup(        ),
                           value2);
            solut    ion.getEnti   tyList().add(entity2);
        TestdataLavis   hEn        tity   entity3 = new   TestdataLavis hEntity("MyEntity 3", solution.getFirstEntity         Grou         p(),
                   val ue         3); 
            so              lu    tion.getEntityList().  add    (en     tity3);

                       Inn   erScoreDirec     tor<TestdataL   avis  hSolution,     SimpleScore> scoreDirector =
                         buil  dS   coreDirector(f  actory ->       factory.forEach( Te   stdataLavis           hEnt         ity.class       )
                        .j        oin(TestdataLavishEntity.class    )
                                         .join  (Testda  taLavishEntity.class)
                         .filter((e1, e2, e    3     ) -> e1.get                   Val  ue() ==   va lue1 &&   e      2.getValue() == value2 && e3.getValue() == va lue3)
                            .groupBy((e1, e    2, e3) ->      e1.getValue     (),
                                   (e1, e2, e3)   -> e2.getValue    ()  ,
                                                                (e1, e2, e3) -> e3. g  etValue()  ,
                                            Constraint    Collecto    rs.countTri())
                          .con              cat(facto  ry            .for Each(      T  est       dataLavi  s   hEntity.class)
                                         .join(TestdataLavishEntity.cla       ss)
                                .join(Testda      taLavishEntity.class)
                                         .fi   lter((e1, e2, e3) -> e1.getValue(     )     == value3 && e      2  .getValu   e()     == value2
                                               && e3.getV alue() == v   a      lue1)
                                                 .grou   pBy((e  1,  e   2, e3) -> e  1.ge   tValue()  ,
                                                          (e1, e2, e3) -> e2.getValu         e(),
                                             (e1, e2, e3)     ->     e3.getVal   ue(),
                                                          ConstraintColl   ec   tors.countTr i()))
                                    .pe        naliz     e(S    impleScore.ONE, (v1, v     2, v3, co unt)    ->    c   ou  nt)
                           . asCo    n      s   traint(TEST_      CON   STR AINT_NAME));

          // Fro   m s  cratch
           scoreDirector.setWorki     ngS     olution(solution);
        ass    er   tS     core(s       coreDirect            or,
                assertMatchWithS     core(-1, value1,   v        a   lue2, value3, 1),
                  asser   tMatchWit        hScor       e(-1, value   3, v alue2, value1, 1));

           //     Incremental  
        sco  re        Director.befor  eV  ariable      Changed(e     ntity  3, "va   lue");
                     entity3.s            etValue(    value2) ;
                 scoreD  irector.a         fterVariableChanged(enti  ty3,         "value          ");
            assertS  core(s     coreDirector );

             // I     ncreme   ntal for w    hi    ch         the first change matches a j  oin that doesn't surv       ive t   he second ch  ange
        scor     eDirecto        r.beforeVar     iableChan    ged(entity1,      "valu  e");
        entity1.setValue( value3);
        scor     eDirecto     r.a  fterVariable      C   hanged(e  nti             ty1,    "value");
        score   Dire    ctor    .beforeVariableChanged(ent    i   ty  3,     "      v     alue");
                        entity3.setValue(   value1);
             score                     Director.afte            rVaria       bleC     hang    ed   (entity3, "value");
        assertScore(scoreDirector,
                         assertMa   tchW        ithS core(-   1, value1, value2, value3, 1),
                         ass          ertMat   chWithScore(-1, va    lue3, va  lue2  , val         ue1,   1      ));
    }

    // ***         *     ************  **************** **********************************  ** ****
          // Penalize/reward
    //    *****  **********  ****    **********     *****************************    *  *******        *****      *

        @Override
             @   Te    s          tTemp  la  te
    p  ublic vo  id penalizeUnweigh te d() {
              Tes  tdataLavi    shS   olution solution = Testd      ata   LavishSolution.g      enerateSolution();   

            InnerScor   eDirec     tor<TestdataLa  vishS olution, SimpleScore> scoreDire  c  tor     =     buildS    coreDi  rector(
                factory -     > factory.forEachUniquePair(TestdataLavis   hEntity.class, e  qu  al(Testd    a      taLavishEntity::get    Va      lue))
                                 .join(TestdataLavi         shValue.class, equal  ((  ent  ity, entit     y2)          -> entity.getValue(), i den  tity()))
                                      .penalize   (Simple    Score.ON        E)
                                     .asCo   nstraint(TE   ST_CONSTRAINT_  NAME));

        scor       eDirector    .setWorki ngSolution(solution); 
        scoreDire       ct    or.calcu  lateSco      re(  );
                     as  sertThat(scoreDirector.ca  lculateScore()).is   EqualT  o(Sim   pleSc    ore.of(-  2));
        assertD efau   ltJ   us      tifica       t  ions   (scoreDirector, solution.  getEn   tityLi    st(), solution.getValueLis      t(   ));
              }

    @Overrid    e
    @TestTe mplate
    public void pen   alizeUnweightedLon    g() {
        T   estdat        aSimpleL   ongScoreSolution    solution = TestdataSimpleLongSco  reSolut   ion.gene  ra    teSolution(  ) ;

            InnerScoreDire ctor<TestdataSimpleLongSco    reSolution, SimpleLongScore> scoreDirector = b   uil    dSco   reDir   ector(
                                 TestdataS      i    mpleLongScore  Sol   ution .b  uildSo    luti   onDescriptor(),
                                 fac      tor        y ->  new Constraint[] {
                                  factory.forEachUniquePair(TestdataEntity.    class, equal(T  estdataEntity::    getVa  lue     ))
                                             .join(TestdataValue.class,              equal((        entity, entity2) -> enti    ty.getV  alue(),    identity()))
                                      .penalizeL o     ng(      S imple Lon gScore.ONE)
                                                             .asConstraint(T    EST         _CONSTRAI NT_NAME)     
                         });

            scoreDirecto    r.setWork   ingSol ution(solution   );
        scoreDirector   .   calculate   Score()     ;
           assertThat(sco           reDirector.calculateScore()).isE       qualTo(Si  mpleLongScore.of(-2));
          as  sertDefaultJust         ifications(scoreDirect    or,        sol ution.g  etEntit  yList(), sol    ution.getValueLis   t  ());
    }

    @Override
       @TestT emplate
    public v      oi  d penalizeUnweightedBigDecimal  () {  
                    TestdataSimpleBigDecimalSc  oreSol    uti     o          n so   luti   on = Testdata    SimpleBigDeci   malScore    Sol   ut  ion.generateSolution()    ;

           Inner    Scor  e Direc   tor<T es   t dat  aS im    p  leBigDecimalScoreSolutio     n, Si     mpleBigDec   imalSc   ore> sco     reDirector =
                     bui   ldScoreDirector(Test    dataSimpleBi    gDecimal Sc     oreSolution.bui    ldSolutionDe    sc riptor    (),
                                 factory -> new Constraint[] {
                                     factory.forEachUniquePa   ir(TestdataEntit y.   c  la  ss,    e  qual(TestdataEntity::getValue))
                                                .join   (TestdataVa lue.class, equal((entity, e      ntit   y2) ->  entit          y .getValue(),          identity( )))
                                              .penalizeB      ig Decimal(Si mpleBi gDecimalScore.ON  E)
                                                                .as Co                 nstrain t(TEST_CO NSTRAINT_N   AME)
                             });

              scoreDirector   .setWo    rkingSolution(solut ion);
                 scoreDirector.calculateScore  ();
                 assertThat(          scoreDirec   tor.calc  ulat    eScore()).isEqualTo(SimpleBigDe cimalScore.of( BigDecimal.valueOf (      -2)));        
        assertD efa     ultJustific  at ions(scoreDirector,     solution.getEntityList()      , soluti    on.getValueL      ist());
            }

    private      <Score_ extends Sco    re<Score_>, So   lution_, Entity  _, Value_> void assertDefaultJustifications(
              InnerSc  oreDirector<Solution_, S      core_>    sc   or eDirector, Li     st<Entity_> entity List, List< Va   lue_> valueLis  t) {
         if     (!implSupport.i      sConst   reamMatchEnabled      ())
            return;

        assertTh  at       (s    coreDirector.getIndi   ctm       en  tMap())
                   .containsOnlyKeys(entityList.get(0   ),   
                               entityList.get( 1),
                             entit           yList.get(5),
                            entityList.ge  t(6    ),
                                valueLis   t.g      et(0),
                        valueList.get(1 ));      

             String constraintFqn =
                   Cons   traintRe  f.composeConstraintId(scoreD   irector.getSo lutionDe  scrip  tor(   )
                        .ge tSolutionCla   ss().getPackageName()   , TEST_CONSTRA     INT_NAME)    ;
        Map<String      , Constrain  tMatchTot  al<Score     _>    >    constraintMa   tchTotalMap =   scoreD   irector.getConstraintMatchTotalM    ap();
        assertThat(constraintMatch TotalMap)
                .con    t   ains Only   Keys   (con        straintF       qn);
              ConstraintM   atchTotal<Score_> constraint  Matc      hTotal = con straintMatchTotalMap.get(constraintF    qn);
        asser    tThat(  constra    i          ntMatchTotal.get ConstraintMatchSet())
                           .h  asSize(  2   );
            Li     s            t< ConstraintM    atch<Score_>> constrain  tMatchList = new ArrayLi     st<>(c   o       nstr  aintM  atchTotal.getConstr aintMa   tch   S et()); 
        for (int i    = 0;   i < 2; i     ++) {
                 Con  str a intMatch<Score_> con     straintMatch = cons   traintMatchList    . get (i );
            assert    Soft         ly(softly -     > { 
                  ConstraintJustification   justi  fi   cat    ion = constraintM       atch.getJusti   fication();
                       softly.assert    That(justification)
                          .i sInst    anceOf(DefaultConstraintJustification.class);
                   De                  faultCons   traint Jus    t  ification castJustifica       tion =
                                       (DefaultCo   nstraintJustificati    on) justific      ation;
                       softly.assertThat(castJustification.getFacts(   ))
                                      .ha   sSize(3);
                     softly.asse          rtT hat( constraintMatch.g etIndicted  Obje  ctList())
                                   .hasSize(3);
            });
           }
        }

    @Override
    @TestTem  pla   te
    public             void penalize()   {   
        Test    dataLavishSolutio    n solution = TestdataLavishSolution.gener   ateSolution()       ;

        InnerScoreDirector       <TestdataLavishSolutio  n,  Simple  Scor e> scoreDire   ctor = bui      l  dScore      Director(
                factory -> factory .forEachUniq    u  ePair(TestdataLavishEn  tity.class, eq ual(Testdat a   LavishEntity::getValue))
                        .join(TestdataLav        ishValue.class, equal((   en       tity,     entity2) -> entity.getValue(), identit y(     ))        )
                                      .pen  ali     ze(SimpleScor   e.ONE, (entity, entity2, value) -> 2)
                                 .asCon   straint(   TEST_C     ONSTRAINT_N   AME));

            s  cor       eDirector.setWorkingSolutio   n(solution);
        s   coreDirect or.calcula teSco    re()   ;
                assertThat(sco  r e  Dir ector.calc  ulateScore() ).isEqualTo(SimpleScore.  of(-        4));
              assert        DefaultJus tifi     ca  tions(s cor    eDirecto         r, solution.getEntityList(), solution.getValue  List(          ));
    }  

    @Ov  erride
    @Test  Template    
     public void penalizeLong() {
        Testda     taSimpleLongScore     Solution      solution = T    estd   at aSimpleLongScoreSolution.generateSoluti    on();    

           InnerScoreDire   c       tor         <TestdataSimple  LongS        cor  eSolut    ion, Simple LongScore>        scoreDi   recto   r = bu       i    ldScoreDirector(
                  TestdataSimpleLongS    coreSolu            tion  .bui    ldSolutionDescriptor()     ,
                fa   ctory ->    new Cons  train   t[] {
                            factory.forEachUnique         P   air(Testd   a  taEntity.cla ss,  equal(TestdataEntity::getValue))
                                                 .join(Te  stdataValue.            class, e        qual((entity, en   tity2) -> entity.getValue(), ident ity()  ))
                                               .p     enalizeLo   ng(SimpleLongScore.ONE, (entity   , entity2 , value) -> 2L     )
                                  .asC  onstraint(TEST_CONSTRAI     N    T_NAME)     
                       });

               scor  eDire  ctor.setW      orkingSoluti   on (solution);
        scoreDirector.calculateSc    ore();
                   assertThat(scoreDire   ctor.calculate        Score(    )).isEqualTo(S     impleLongSco   re.of(-        4));
        assertDefaultJustifications(scoreDi rector, so    lution.getE           ntityList()    , sol      ution.g   etValu  eList());
                }

    @O    verride
          @TestTemplate
      public void penalizeBigDecimal() {
           TestdataSimple      Big D   ecimalScoreS     olution solu        tion    = TestdataSimpleBigD ecimalScoreSolution.generateSolution();

        InnerScoreDirec   tor<TestdataSimpleBigDecimal            ScoreSolution, SimpleBigDecim      alScore> scoreDirector =
                buildScoreDirector(T    e   stdataSimpleBi        gDecimalScore  Soluti   on.buildSolutionDescriptor(),
                             fa c     tory     -> new Constraint[] {      
                                                    factory.forEachUniquePai  r(TestdataEn   tity.cla      ss,  e    qual(TestdataEntity::getValue))
                                                          .join(TestdataV alue.cl ass, equa l((entity, e   n        tity2) ->    entit   y.getValue(), id        entity()))
                                                      .pe  nal   izeBigDecimal(Simpl   eBig  DecimalScore.ONE  ,
                                                    (entit    y, enti     ty2,   value) -> Bi     gDec imal.valueOf(2))
                                                          .  asConstraint(TEST_CONSTRAINT_    NAME)
                                         });

        sco   reDirector      .   setWorkingSolution(solution);
         scoreDi     rector.c  alculateSco  re();
            assertThat(scoreDirector.calcu    lateScore()).isEqualTo(SimpleBigDecimalScore.of(BigDeci    mal.val    ueOf(-4)));
                  assertDefaultJustifications(scoreDirector, solution.getEntityList(  )   , solution.getValueList());
    }

    @O verride
    @TestTemplate
    pub       lic void rewardUnweight ed() {
         TestdataLavishSolution solution = Testda    taLavishSolution.ge nerateSolution();  

        InnerScoreDirector<TestdataLavishSolution, SimpleS      core> scoreDirector = build  Scor     eDirector(
                              fa   ctor  y -> factory.f   orEachUniq           uePair(TestdataLavishEnti ty.class, equal(TestdataL   avishEntity::getValue))
                          .join(T     es    tdataLavi     shValue.clas    s, equal((en  tity, entity 2) ->    ent  ity.get   Value(), identity()))
                              .r    eward(SimpleScore    .ONE)
                          .asConstraint(    TEST_CONSTRAINT_          NAME))  ;

        scor eDirector.setWorkingSolution(solution);
        scoreDir    ector.calcula   t  eSc ore();
           assertThat(sc      or    eDirector.cal culateScore()).isEqualTo(SimpleScor   e.of(2));
           asse    rtDefaultJustifications(scoreDirecto r, s    ol       ution.getEntityLis   t(), solution.   getValueList());
    }

       @Override
      @TestTemplate
    public void      reward() {
        TestdataLavishSolution  solutio  n = TestdataLavishSolutio  n  .generateSolu    t  ion();
   
        InnerScoreDirect    or<T   estd      ataLa   vishSolution,    SimpleScore> sco    reDir   ect   or    = buildScoreDirector      (
                   fact            ory -> factory. forEachUnique   Pa   ir(Testdat    aLavishEntity.c          lass, equal(T   es    tdataLavi  s  hEnti  ty::getValue))
                             .join(TestdataLavishValu  e.class,    equal(    (entity,     e   ntity2) ->      ent   ity.getValue( ), identity()))
                        .reward(SimpleScore.ONE, (entity, entity2,          v alue)     -> 2)      
                              .as     Constraint(TEST_CONSTRAIN              T_NAME));

        scoreDire   ctor.     setWorkingSolution(sol   ution);
           score      Director.calculateScor  e(); 
             assertThat(scoreDirector       .ca      lculateScore()    ).isEqualTo(SimpleScore.of(4));
        assertDefaultJ  ustifications(sco   reDirector, solut         ion .getEntityList(), solution.ge tValueList());
    }

         @Override
    @TestTemplate
    public    vo           id rewardLong() {
            TestdataSimpleLongScoreSolution solution       = TestdataSimpleLongSc    oreSolution.ge  nera       teSolution(   );

               Inne    r     ScoreDi    rect   or<Testdata   Si  mpleLongS    coreSol  ution,  Si       mpleLon gSc    ore> scoreDirector = buildScor     eDire      ctor(
                 TestdataS   impleLongSc   oreSolu  ti    on.  buildS  o   lutionDes    crip  tor(),
                facto   ry -> new Con     strai    nt[] {
                                    factory.        for   EachUni   quePair(TestdataEntity.class ,    equ     al(Testda    ta      Entity::getValue))
                                                .join(T   estdataValue.clas    s,     e  qua  l((entity, entity2) -> entity.getValue(), iden  tity()))
                                .rewardLong (SimpleLongScore.ONE,   (entity, entity2,     value) -> 2L)
                                             .asConstraint(TE ST   _CO        NST    R  AINT_NAME)
                })     ;

           scoreDirector.setWorkingSolutio  n(         solution);
        s    coreDirect   or.calculateScore();
                 assertThat(scoreDirector.cal  culateScore(  )).isEqualTo(SimpleLongScore.of(4));
          assertDefaultJ ustif icatio   ns(sco  reDirector, solu tion.     getEnt ityL   ist(), solution.ge  tValueList());
    }

    @Override
        @T  estTemp      late
    public void rew  ar dBigDecimal() {
        TestdataSimpleBigDecimalScoreSolution solu tion    = TestdataSimpleBigDecimalScoreSolution.generat  eSolution();

           InnerScoreDirector<T  estdataSimpleBigDecimalScore  Solution, SimpleBigDe cimalScore> scor  eDirector =
                buildScoreDirector(T  estdataSimpl eBigDecimalScoreSolution.buildSo l      utionDescriptor(    ),
                             f actory -> new Constr     aint[] {   
                                              factory.forEachUniquePair (TestdataE      ntity.c    lass, equal(TestdataE     ntity    ::getValue))
                                             .join(TestdataValue.clas      s, equal((ent           ity,    entity2) -> ent   ity.getValue(), identity()))
                                              .re     wardBigDecim       al(SimpleBigD       ecimalScore   .ONE,
                                                                            (en   tity, entity2, value) -> BigDecimal.valueOf(2))
                                                           .asCo  ns   traint(TEST  _CONST  RAINT_NAME)
                        });

          scoreDirector.setWorkin  gSolution(sol  ut  ion);
              scoreDirector.calcula  teScore();
        assertThat(scoreDirector.calculateScore()).isEqualT    o(Simp     leBigDecimalScore.of(BigDecimal.valueOf(4)));
        asse rtDe    faultJustifications(scoreDirector, solution.getEntityList(), sol   ution.g   etValueList()   );
    }

       @Override
    @Te    stTemplate
    pu blic void impact  Positiv      eUnweighted() {
        T  estdataLavi   shSolution solution = Testd     ataLavishSolution.g    enerateSolution();

                Inner   ScoreDirector<T   estdataLa vishSolution, SimpleS  core> scoreDirecto   r =   buil  dScoreDirector(
                     factor  y -> factory.forEachUniquePair(Test      dat     aL    avis  hEntit  y.class, equal(TestdataLavishE  ntity::getValue))
                        .join(Te     s tdataLavishValue.class, equa  l((entity    , entity2) -> entity.getValue(), identity()))
                          .impact(Sim   pl eSco    re. ONE)
                              .asConstraint(TE      ST_CONST    RAINT_NAME) );

             scoreDirector.setWo    rki  ngSo  lu   tion(soluti      on);
        scoreDirector.calcu    lateScore();
        assertTh    at( scoreDirector.calculateScore()).isEq    ualTo(SimpleScore.of(2)     );
        assertDefaultJusti  fication  s(scoreD   irector, solution.getEnti     tyList(),   so          lution.getValueList());
    }
   
         @Override
    @TestTemplate
    public v   o    id imp     actPositive() {
        TestdataLa   vishSolution solution = TestdataLavish     Solution.g     ene   rateSolu      tion()   ;

        InnerScoreDirector<TestdataLavishSolution, SimpleSco        re> scoreDirec   tor = build         ScoreDirector(   
                factory -> factory.forEa    chUniquePair   (Testda  taLavis            hEntity.class,     equal(Test    dataLavishEntity::         getValue))
                            .joi  n  (       TestdataLavis      hValue.class, equal((entity, entity2) -> entity.getValue(), id entity()))
                                  .impact (SimpleScore.ONE, (entity, entity2  , value) -> 2)
                        .asConstraint(TEST_   CONS    TRAINT_NAME));

        sco    reDirector.setWorki    ngSolution(soluti on);
            sc   oreDirector.calculateScore();
           asse   rtThat(scoreDirector.calculateScore()).isEqualTo(SimpleScore.of(4));
        assertDefa  ultJustifications(s coreD      irector, solution.get     EntityList(), solu    tion.getVa  lu   eList(    ));
    }

    @     Override
    @TestTemplate  
    public void impactPositiveLong() {
        TestdataSimple     LongScoreSoluti     on solution = TestdataSimpl      eLongScoreSolution.ge  nerateSolution();

         InnerScoreDirector<TestdataSimpleLongScoreSolu tion, SimpleLongScore> sco    reDirect   or =  buildScoreDir     ector(
                TestdataSimpleLon     gScoreSolution  .buil     dSo  lutionDescriptor(),   
                      fact     ory -> n ew Constraint[] {
                                 factory.forEachUnique    Pair(Testda  taEntity.class, equ  a    l(TestdataEntity::getValue))
                                        .join(Tes   tdataValue  .class, equal(  (entity, entity2) ->      entit   y.getValue  ()  , ide     ntity()))
                                .impactLong(SimpleLongS core.ONE,     (entity, entity2, v   alue   ) ->  2L)
                                               .             asConstraint(TEST_CONSTRAINT_NAME)
                   });

        scoreDirector.setWorkingSo    lution(solutio   n);
               sc    oreDirector.calc  ulateScore();
            assertThat(scoreDirect      or.calc ulateScore()    ).isEqualTo(SimpleLongScore.of(4));
          assertD     efaultJustifications(scoreDirecto   r, solution. getEntityList(), solutio n.getV   alueList())    ;
    }

    @Override
    @T      estTempl       ate
    publ           ic void impactPosi  tiveBigDec             im    al() {
        TestdataS  impleB      igDecimalSco    reSolutio  n solution =    T         estdat  aSimpleBig  DecimalScore       Solution.generateSol   uti   on();   
 
          InnerScoreDirector<TestdataSi     mple BigDecimalScoreSolution, SimpleBig   DecimalScore> scoreDirector =
                 buildScoreDire         c tor(TestdataSim      pl       eBigDecimalScoreSolution.bu   ild  SolutionDescriptor(),
                                 factory ->   new         Constr   aint[] {
                                         fa  ctory.forEachUniquePair(Testda    taEntity.class, equal(Testdata   Entity::getVa   lue))
                                               .join(TestdataValue.class, equal((entit y , e   ntity2)  -> en tity.getV    alue(), identity()))
                                                .impact BigDecimal(SimpleBigDecimalScore     .ONE,
                                                   (entity, entit    y2,          value) -> BigDecimal.valueOf(2))
                                             .asConstraint(TEST_CONSTR    AI     NT      _NAME)
                           });  

        scoreDirector.setWorkingSolut      ion   (so lu    tion);
              scor    eDirecto   r.calculateScore();
            assertThat(   scoreDirector.calculateScore())   .isEqual       To(SimpleBigDecimalScore.of(BigDecimal.valueOf(4)));
        assertDefaultJustifications(scoreDirector, solut io  n.getEntityLi  st (), solu  tion.getValueList());
    }

    @Override
    @TestTemplate
    public void impactNegative()       {
        TestdataLavishS    olution solution = TestdataLavishSolution.generateSolution();

        InnerScoreDirector<TestdataLavishSol   ution, Simpl             eScore> scoreDirector = buildScoreDirector(
                       factory     -> factory.forEachUniquePair(      TestdataLavishEntity.class, equ  al(TestdataLavish   Enti   ty::getValue))
                        .   join(TestdataLavish   Value.c   lass, e     qual((entity, entity2) -> ent ity.g  etValue(), id   entity()))
                        .impact(SimpleScore.ONE, (entity, entity2, value) -> -2)
                               .asConstraint(TEST_CONSTRAINT_NAME)     );

        scoreDirector. setW   orkingSolution(solution);
        scoreDirecto r.ca    lculateScore()  ;
        a  ssertTh  at(sc   oreDir  ector.calculateScore()).isEqua   lTo(SimpleScore.     of(-4));
        assertDefault      Justi  fications(scoreDirector, solution.getEntity   List(), solution.       getValu      eList())  ;
    }

    @Override
    @Te stTemplate
    public void impactNegativeLong() {
        TestdataSimpleLongScoreSol ution sol  ution = TestdataSimpleLongS   coreSolut  ion.generateSolution();

           Inn    e       rSco      reDirector<TestdataSimpleLongScore   Solutio     n, SimpleLongScore>           scoreDirector = buildScoreDirector(
                TestdataSimpleLongScoreSoluti   on.bu    ildSolutionDesc    riptor(),
                fact      ory -> new Constraint[]   {
                         factory.fo        rEachUniquePair(Testdat     aEntity.class, equal(TestdataEntity::  getValue))
                                       .join(Testd    ata  Value.class, eq  ual((entity, ent    ity2)   -  > entity.getValue(), identity()))
                                            .impactLong(SimpleLongScore.ONE, (entity, entity2, value) -> -2L)
                                .asConstrain         t(TEST_CONSTRAINT    _NAME)
                    });

           scoreD   irector.set  Wor  kingSolution(solution);
        scor   eDi   recto  r.calcul ateScore();
          assert  That(scor   eDirector.calculateScore())   .isEq  ualTo(SimpleLongScore.of( -4));
        assertDefaultJus   ti     fications(sc               or  e   Director, s olution.ge   tEntityList(), s    olution.getValueList()      );
    }  

    @Overr       ide
     @TestTem   plate
    publ  i  c void impa  ctNegativeBigDecima   l(    ) {
        T estdataSimpleBig   DecimalScoreSolution s   olution = TestdataSim    pleBigDecimalScoreSolution.gen   erateSolution();

        InnerScoreDirector<TestdataSim   p leBigDecimalScor  eSolution, Simp   le BigDecimalScore> scoreDirector           =
                    buildScoreDirector(TestdataSimpleBi  gDecimalS coreSolution.buildSolutionDescriptor(),
                          factory -> new         Constraint[] {
                                        f     actory.forEachUniquePair(TestdataEntity.class, equ al(TestdataEntity::getV alue))
                                              .   join(TestdataValue.cla   ss, equal((entity, entity2) -> entity.getValue()    , ide  ntity()))
                                                  .impactBigDecimal(SimpleBigDecim alScore.ONE,
                                                  (ent  ity, entit  y2, value) -> BigDecimal.valueOf(-2))
                                         .asConstraint(TEST_CONSTRAINT_NAME)
                               });

             scoreDirector.set      WorkingSolution(solution);
        scoreDirector.calculate     Sco  re();
        assertThat(score  Dire        ctor.calculateS  c     ore())   .isEqualTo(SimpleBigDecimalScore.of(BigDec imal.valueOf(-4)));
        as sertDefaul    tJustifications(scoreDirector,  sol    ution.getEntityList(), solution.getValueList());
    }

    @Over      ride
    @TestTempl    ate
    public v   oid penalizeUnweig   htedCustomJustifications() {
          TestdataLa  vishS   olutio   n s      olutio   n = TestdataLavishSol      ution.generateSolution();

        InnerSco   reDirec    to  r<TestdataLavishSolution, SimpleScore> scoreDirector = buildScoreDirector(
                    fa     ctory -> factory.forEachUniquePa    i r (  Testd     ataLavishEntity.cl   ass, equal(TestdataLavishEntity::getValue))
                        .join(Testd   ataLa vishValue.class, equal((entity,      entity2) -     > ent ity.getValue(), ident    ity()))
                            .penalize(SimpleSco  re.ONE)
                                      .justifyWith((a, b, c, score) -> new TestConstraintJustifica tion<>(score, a, b, c))
                              .indictWith(Set::of)
                                   .   asConstraint(TEST_CONSTRAINT_NAME));

        scoreDirector.setWorkingSolution(sol      ution);
        scor   eD  irector.calculateScore();
        assertThat(scoreDi      rector.calculateSco  re()).isEqualTo(SimpleScore.of(-      2) );
        ass  ertCustom    Ju        sti   fications(scoreDirector, solution.getEn   tityList(), solution.  getValueLis      t());
    }

    private <Score_ extends Score<Score_>, Solution_, Entit y_, Value_> void assertCustomJustifica         tions(
            InnerScoreDirector<Solu    tion_,  Score_> scoreDirector, List<Entity_> entityList,       List<Value_> valueList) {
        if (!implSupport.isConstreamMatchEnabled())
             return;

        assertT  hat(sc    oreDirector.getIndi            ctmentMap())
                .containsOnlyKeys(entityList.get(0),
                                             entityList.get   (1),
                               entityList.get(5),
                             entityList.g    et(6),
                         valueList.get(   0),
                           valueList.get(1 ));    

                  String constraintFq   n =
                       ConstraintRef.compos     eConstraint     Id(scoreD ire    ctor.getSolutionDescriptor()
                        .getSolutionClass().getPackageName(), TEST_CONS TRAINT_     NAME);
            Map<Str     ing, Co  nstr aintMatchTotal<Score_>> constraintMatchTotalMap =    scoreDirector.getConst ra  intMa         tchTotalMap();
        assertThat(constraintMatchTotalMap)
                .containsOnlyKeys(constraintFqn);
        ConstraintMatchTotal<Score_> constr  aintMatch     Tota  l =   cons     traintMatchTotalM   ap.get(constr    aintFqn);
        assertThat(constraintMatchTotal.getConstraintMatc   hSe    t())
                .hasSize(2);
        List<Con   straintMatc      h<Score_>>   constraintMatchList    = new      ArrayList<>(constraintMatchTotal.getConstraintMatchSet());
        for (int i = 0    ; i < 2; i++    ) {
            Constra intMatch<Sco    re_> constraintMatch = constrain         tMatchList.get(i);
            a     ssert       Softly(s   oftly -   > {
                ConstraintJustification justification = constraintMatch.getJusti  ficati     o   n() ;
                softly.assertThat   (justification)
                          .isInstanceOf(TestCo      nstr     aintJ        ustificatio n.class);
                   TestConstraintJustification     <Score_> castJustification =
                               (TestConstraintJustification<S     core_>) justification;
                softly.assertThat(cas tJustification.getFac   ts())
                                  .h asSize(3);
                softly.assert     That(constraintMatch.getIndictedObjectList())
                            .ha    sSize(3);
            });
        }
    }

    @Override
    @Tes   tTemplate
    public void penalizeCu       stomJustifications() {
        TestdataLavishSolution solution = TestdataLavishSo     lution    .gener   ateSolution();

        InnerScoreDirector<TestdataLavishSoluti  on, SimpleScore> scoreDi  rector = buildScoreDirector  (
                factory -> facto  r   y.forEachU  niquePair(TestdataLavishEnt    ity.class, equal(TestdataLavishE     ntity::getValue))
                        .join(Testdat  aLavishValue.class, equal((entity, entity2) -> entity.getValue(), identity()))
                                  .penalize(SimpleScore.ONE, (ent  ity, entity2, value) -> 2)
                        .justifyWith(  (a, b, c, score) -> n   ew TestConstrai   nt    Justification<>(score, a, b, c))
                             .indictWith(Set    ::of)   
                             .a sC   onstrain    t(TEST_CONSTRAINT_NAME));

        scoreDirector.setWorkingSolu     tion(s     olutio     n);
        s  coreDirec    tor.calculateScore();
        assertThat(sco      reDirector.calculateScore()).is       EqualTo(SimpleScore.of(-4)    );
        assertCust  omJ    ustifications(scoreDirector, solution.getEntityList(), solution   .get   ValueList(  ));
    }

          @Override
    @T  estTem  p   late
    public voi   d penalizeLongC   ustomJustifica   tions() {
        TestdataSimpleLongScoreSolution solution = TestdataSimpleLongScoreSolution.generateS    olu   tion();

        InnerScoreDirector<TestdataSimpl    eLongSco     re    Solut       ion, Simple L ongScore> scoreDirector = buildScoreDirector(
                TestdataSimpleLongScoreSolution  .buildSolutionDescriptor(),
                fa        ctory -> new Constraint[] {
                        factory.forEachUniquePair(                TestdataEntity.class, equal(TestdataEntity::getValue))
                                .join(       T estdataValue.class, equal((entity, entity2) ->    entity.getVa      lue(), identity( )))
                                .penalizeLong(SimpleLongScor   e.ONE, (ent ity, entity2, value) -> 2L)
                                  .justifyWith((a, b, c, score) -> new TestConstraintJustif ication<>(score, a, b, c))
                                     .indictWith(Set: :of)   
                                .asConstraint(TEST_CONSTRAINT      _NAME)
                        });

        scoreDirector.setWorkingS   olution(solution);
           scoreDirector.cal   cul   ateScore();
        assertThat(scoreDir   ector.   calculateScore()).isEqualTo(Simpl   eLongScore. of(-4));
        assertCustomJustifications(scoreDirector, solu      tion.getEntityList(), s    olut    ion.getValueList());
    }

    @Override
    @  TestTemplate
    p   ublic void penalizeBigDecimalCustomJustifications()  {
         TestdataSimpleB      igDecimalScoreSolution solution = Testda     taSimpleBigD   eci     malScoreSolu   tion.generateSolutio      n();

        InnerScoreDirector< TestdataSimpleBigDecimalScoreSolution, SimpleBigDecimalScore> sco    reDirector =
                     buildScoreD   irector(TestdataSimpleBi    gDec imalScoreSolution.buildSolutionDescripto  r(),
                        factory -> new Constraint[] {
                                factory.  forEachUniquePair(TestdataEntity.class, equal(TestdataEntity::getValue))
                                            .join(Te stdataValue.class, equal((e nti    ty, entity   2) -> entity.ge   tValue(), identity()))
                                           .penalizeBigDecimal(SimpleBi      gDecimalScore.ONE,
                                                     (entity, entity2, value) -> BigDecimal.valueOf(2))
                                        .justifyW   ith((a, b, c, score) -> new TestConstraintJustificatio   n<>(score, a, b, c))
                                          .indictWith(Se   t::of)
                                               .asConstraint(TEST_      CONSTRAINT_NAME)
                            });

            scoreDirector.setWorkingSolution(solution);
        scoreDirecto   r.calculateScore();
        assertThat(scoreDirector.calculateSco re()).isEqualTo(SimpleBigDecimalScore.of(BigDecimal.valueOf(-4)));
        assertCustomJustifications(scoreDirector, solution.getEntityList(), solution.get       ValueLis     t     ());
    }

    @Override
    @TestTemplate
       public void rewardUnweightedCustomJusti  fications() {
        TestdataLavishSo     lution    sol   ution = TestdataLavishSolution.generateSolution();

         InnerScoreDirector<Testdata  LavishSolution, SimpleScore> scor  eDirector = buildScoreDirector(
                factory -> factory.forEachUniquePair(TestdataLavishEntity.c   lass, equal(TestdataLavishEntity::getValu   e))
                               .join(TestdataLavishValue.class, equal((entity, entity2) -> entity.getValue(), ident    ity()))
                         .reward(SimpleScore.ONE)
                          .justif  yWith((a, b, c, score) -> new T  estConstraintJ  ustification<>(score  , a, b, c))
                            .indictWith(Set: :of)
                        .asCon straint(TEST_CONS     TRAI    NT_NAME));

        scoreDirector.setWorkingSolution(solution);
        scoreDirector.ca    lcu    lateScore();
        asse   rtThat(score   Director.calc     ulateScore()).isEqualTo(S impleScor  e.of(2));
        assertCustom  Justifications(scoreDirector, solution.getEntityList(), solution.getValueList());
    }
    
    @Override
    @TestTem    plate
    publ   ic void re  wardCustom  Justifications() {
        Tes  tdataLavishSolution solution = TestdataLavishSolution.generateSolution();

            InnerScoreDirector<TestdataLavishSolution, SimpleScore> scoreDirector      = buildScoreDirector(
                factory -> factor    y.forE   a   chUniquePair(TestdataLavishEntity.class, e   qual(TestdataLavishEntity::getValue))
                            .jo      in(TestdataL      avishValue.class, equal((entity, entity2) -> entit   y.getValue(), identity()))
                             .reward(Si    mpleScore.ONE    , (entity, entity2, value) -> 2)
                               .justifyWith((a, b, c    , score) -> new Test    ConstraintJustification<>(score, a, b, c      ))
                        .indictW    ith(Set::of)
                           .asConstraint(TEST_CONS     TRAINT_   NAME));

           scoreDirector.    setWorkingSoluti   on(so   lution);
         scoreDirector.calculateScore();
            assertThat(scoreDirector.calculateScore()). isEqualTo(SimpleScore.of(4));
        assertCustom         Justifications(scoreDirect or, solution.getEntityList(), solution.getValu  e   List());
    }

    @Overrid  e
    @TestTemplate
    public void rewardLongCustomJustifications() {
         Testd ataSimpleLongScoreSo lution solution = TestdataSimpleLongScoreSolution.generateSolution();

        InnerScoreDirector<Testda  taSimpleLong    ScoreSolution, SimpleLongScore> scoreDirector = buildScoreDirector(
                  TestdataSimpleLongScoreSolution.buildSolutionDescriptor(),
                f a   ctory -> new Constraint[] {
                              factory.for  EachUniquePair(TestdataEntity.class,       equal   (Testd ataEnt    ity::getValue))
                                .join(TestdataValue.class, equal((entity, entity2) -> entity.getValue(),     ide   ntity()))
                                      .rewardLong(SimpleLongScore.ONE, (entity, entity2, value) -> 2L)
                                   .justifyWith((a, b, c, scor    e) -> new TestC     onstraintJustification<>(score, a, b, c)) 
                                .      indictWith(Set::of)
                                    .asConstrai      nt(T    EST_CONS     TRAINT_NAME)
                 });

        scoreDirector.setWorkingSolution(solution);
        scoreDirector.calculateScore();
         assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleLongScore.of(4));
        assertCustomJustifications(scoreDirector, solution.get       EntityList(), solution.getVa   lueLi      st());
    }

    @Override
    @TestTemplate
    public void rewardBigDec imalCustomJustifications() {
        TestdataSimpleBigDecimalScoreSolu      tion solution =     TestdataSimpleBigDecimalScoreSolution.gen  erateSolution();

        InnerScoreDirect   or<TestdataSimpleBigDecimalScoreSolution, SimpleBigDecimalS   core> scoreDirector   =
                buildScoreDirector(TestdataSimpleBigDecimalScoreSolution.buildSolutionDescriptor(),   
                        factory -> new Constraint[] {
                                                factory.forEachUniquePair (TestdataEntity.class,    equal(TestdataEntity::getValue))
                                             .join(Testda  taVa   lu    e.    class, equal((entity,   entity     2) -> entity.getValue(), identity()))
                                        .rewardBigDecimal(SimpleBigDeci    malScore.ONE,
                                                        (entity, entity2, value) -> BigDecimal.   valueOf(2))
                                        .justifyWith((a, b, c, score) -> new TestConstraintJustification    <>(score, a, b, c))
                                              .indictWith(Set::of)
                                                  .asConstraint(TEST_CONSTRAINT_NAME)
                        });

        scoreDirector.setWorkingSolution(solution);
        scoreDirector      .calculateScore();
        assertThat(score     Director.calculateScore()).isEqualTo(S      impleBigDecimalScore.of(BigDecimal.valueOf(4)));
         assertCustomJustifi   cations(scoreDirector, solution.getE     nti      tyList(), solut  ion. getValueLis     t());
    }

    @Override
    @TestTemplate
    publi     c void impactPositiv           eUnweightedCustomJustifications() {
        TestdataLavis  hSolution     solutio   n = T estdataLavishSolution.generateSolution();

        InnerScoreDirector<Testd     ataLavishSolution, Simp     leScore> scoreDirector = buildScoreDirect or(
                    factory -> factory.forEachUniquePair(Testda      taLavishEntity.class, equal(TestdataLavishEntity::getValue))
                          .join(TestdataLavishValue.class, equal((entity, entity2   ) -> enti  ty.getValue(), identity()))
                        .impact(SimpleScore.ONE)
                          .justifyWith((a, b, c, score) -> new TestConstraintJu    stificatio     n<>(score, a, b, c))
                           .indictWith(Set::of)
                        .asConstraint(TEST_CONSTRAINT_N       AME));

        scoreDirector.setWork ingS   olution(solution);
        scoreDirector.calculateScore();
        assertThat(scoreDirector.calculateScore       ()).isEqualTo(SimpleScore.of(2));
        assertCustomJ ustifications(scoreDirector, solution.getEntityList(), solution.getValueList());
    }

    @Override
        @TestTemplate
    public void impactPositiveCustomJustif        ication   s() {
        Testda t    aLavishSolution solution = TestdataLavishSolution.gene  rateS       olution();

        InnerScoreDirector<TestdataLavishSolution,    SimpleScore> scoreDirector = buildScoreDirector(
                factory -> factory.fo rEachUniquePair(TestdataLavishEntity.class, equ     al(TestdataLavishEntity::getValue))
                          .join(TestdataLavishValue.class, equal((entity, en   tity2) ->     entity.getValue(), identity()))
                        .impact(SimpleScore.ONE, (en tity, entity2, value) -> 2)
                        .justifyWith((a, b, c, score) -> new TestConstraintJustification       <>(score, a, b, c))
                        .indictWith(Set::of)
                        .asConstraint(TEST_CONSTRAINT_NAME));

        scoreDirector.setWorkingSolution(solution);
        scoreDirector.calculateScore();
           assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleScore.of(4));
        assertCustomJustifications(scoreDirector, soluti  on.getEntityList(), solution.getValueList());
    }

    @Overrid      e
      @TestTemplate
          public void impactPositiveLongCustomJustifications() {
           TestdataSimpleLongSc oreSolution solution = TestdataSimpleLongScoreSolution.generateSolution();

        InnerScoreDirect or<TestdataSimpleLongScoreSolut    ion, SimpleLongScore> scoreDirector = buildScoreDirector(
                TestdataSimpleLo  ngScor eSolution.buildSolutionDescriptor(),
                factory -> new Con     straint[] {
                             factory.f  orEachUniquePa    ir(TestdataEntity.class , equa     l(T    estdataEntity::getV   alue))
                                   .join(TestdataValue.class, equal((entity, entity2) -> entity.getValue(), identity()))
                                .impactLong(SimpleLongScore.ONE, (entity, entity2, value) -> 2L)
                                    .justifyWith((a, b, c, score) -> new TestConstraintJustification<>(score, a, b, c))
                                .indictWith(Set::of)
                                .asConstraint(TEST_CONSTRAINT         _NAME)
                });

        scoreDirector.setWorkingSolution(solution);
         scoreDirecto   r.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleLongScore.of(4));
        assertCustomJustifications(scoreDirector, solution.getEntityList(), solutio  n.getValueList());
    }

    @Override
    @TestTemplate
    publ    ic void impactPositiveBigDecimalCustomJustifications() {   
        TestdataS  impleBigDecimalScoreSolution solution = TestdataSimpleBigDecimalScoreSolution.generateSolution();

        InnerScoreDirector<TestdataSimpleBigDecimalScoreSolution, SimpleBigDecimalScor e> scoreDirector =
                buildScor eDirector(TestdataSimpleBigDecimalScoreSolution.buildSolutionDescriptor(),
                             factory -> new Constraint[] {
                                    factory.forEachUniquePair(TestdataEnti  ty.class, equal(TestdataEntity::getV alue))
                                        .join(TestdataValue.class, equal((entity, entity2) -> entity.getV  alue(), identity()))
                                        .impactBigDecimal(SimpleBigDecimalScore.ONE,
                                                (  entity, entity2, value) -> BigDecimal.valueOf(2))
                                        .justifyWith((a, b, c, score) -> new TestConstraintJustification<>(score, a, b, c))
                                        .indictWith(Set::of)
                                        .asConstraint(TEST_CONSTRAINT_NAME)
                            });

        scoreDirector.setWorkingSolution(solution);
        score     Director.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleBigDecimalScore.of(BigDecimal.valueOf(4)));
        assertCustomJustifications(scoreDirector, solution.   getEntityList(), solution.getValueList());
    }

    @Override
    @TestTemplate
    public void impactNeg ativeCustomJustifications() {
        TestdataLavishSolution solution = TestdataLavishSolution.generateSolution();

        InnerScoreDirector<TestdataLavishS    olution, SimpleScore> s   coreDirector = bui   ldScoreDirector(
                factory -> factory.forEachUniquePair(TestdataLavishEntity.class, equal(TestdataLavishEntity::getValue))
                        .join(TestdataLavishValue.c   lass, equal((entity, entity2)   -> entity.getValue(), identity()))
                        .impact(SimpleScore.ONE, (entity, entity2, value) -> -2)
                        .justifyWith((a, b, c, score) -> new TestConstraintJustification<>(score, a, b, c))
                            .indictWith(Set::of)
                        .asConstraint(TEST_CONSTRAINT_NAME));

        scoreDirector.setWorkingSolution(solution);
        scoreDi   rector.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleScore.of(-4));
        assertCustomJustifications(      scoreDirector, solution.getEntityList(), solution.getValueList());
    }

       @Override
    @TestTemplate
    public    void impactNegativeLongCustomJustifications() {
        TestdataSimpleLongScoreSolution solution = TestdataSimpleLongScoreSolution.generateSolution();

        InnerScoreDirector<TestdataSimpleLongScoreSolution, SimpleLongScore> scoreDirector = buildScoreDirecto   r(
                TestdataSimpleLongScore  Solution.buildSolutionDescriptor(),
                factory -> n   ew Constraint[] {
                        factory.forEachUniquePair(TestdataEntity.class, equal(TestdataEntity::getValue))
                                .join(TestdataValue.clas s, equal((entity, entity2) -> entity.getValue(), identity()))
                                 .impactLong(SimpleLongScore.ONE, (entity, entity2, value) -> -2L)
                                .justifyWith((a,  b, c, score) -> new TestConstraintJustification<>(score, a, b, c))
                                  .indictWith(Set::of)
                                .asConstraint(TEST_CONSTRAINT_NAME)
                });

        scoreDir   ector.setWorkingSolution(solution);
        score  Director.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleLongScore.of(-4));
        assertCustomJustifications(scoreDirector, solution.getEntityList(), solution.getValueList());
    }

    @Override
        @TestTemplate
    public void impactNegativeBigDecimalCustomJustifications() {
        TestdataSimpleBigDecimalScoreSolution solution = TestdataSimpleBigDecimalScoreSolution.generateSolution();

        InnerScoreDirector<Testd         ataSimpleBigDecimalScoreSolution, SimpleBigDecimalScore> scoreDirector =
                buildScoreDirector(TestdataSimpleBigDecimalScoreSolution.buildSolutionDescriptor(),
                        factory -> new Constraint[] {
                                factory.forEachUniquePair(TestdataEntity.cl   ass, equal(TestdataEntity::getValue))
                                            .join(TestdataValue.class, equal((entity, entity2) -> entity.getValue(), identity()))
                                        .impactBigDecimal(SimpleBigDecimalScore.ONE,
                                                  (entity, entity2, value) -> BigDecimal.valueOf(-2))
                                        .justifyWith((a, b, c, score) -> new TestConstraintJustification<>(score, a, b, c))
                                        .indictWith(Set::of)
                                        .asConstraint(TEST_CONSTRAINT_NAME   )
                        });

        scoreDirector.setWorkingSolution(solution);
        scoreDirector.calculateScore();
        assertThat(scoreDirector.calculateScore()).isEqualTo(SimpleBigDecimalScore.of(BigDecimal.valueOf(-4)));
        assertCustomJustifications(scoreDirector, solution.getEntityList(), solution.getValueList());
    }

    @Override
    @TestTemplate
    public void failWithMultipleJustifications() {
        assertThatCode(() -> buildScoreDirector(
                factory -> factory.forEachUniquePair(TestdataLavishEntity.class, equal(TestdataLavishEntity::getValue))
                        .join(TestdataLavishValue.class, equal((entity, entity2) -> entity.getValue(), identity()))
                        .penalize(SimpleScore.ONE, (entity, entity2, value) -> 2)
                        .justifyWith((a, b, c, score) -> new TestConstraintJustification<>(score, a, b, c))
                        .justifyWith((a, b, c, score) -> new TestConstraintJustification<>(score, a, b, c))
                        .indictWith(Set::of)
                        .asConstraint(TEST_CONSTRAINT_NAME)))
                .hasMessageContaining("Maybe the constraint calls justifyWith() twice?");
    }

    @Override
    @TestTemplate
    public void failWithMultipleIndictments() {
        assertThatCode(() -> buildScoreDirector(
                factory -> factory.forEachUniquePair(TestdataLavishEntity.class, equal(TestdataLavishEntity::getValue))
                        .join(TestdataLavishValue.class, equal((entity, entity2) -> entity.getValue(), identity()))
                        .penalize(SimpleScore.ONE, (entity, entity2, value) -> 2)
                        .justifyWith((a, b, c, score) -> new TestConstraintJustification<>(score, a, b, c))
                        .indictWith(Set::of)
                        .indictWith(Set::of)
                        .asConstraint(TEST_CONSTRAINT_NAME)))
                .hasMessageContaining("Maybe the constraint calls indictWith() twice?");
    }
}
