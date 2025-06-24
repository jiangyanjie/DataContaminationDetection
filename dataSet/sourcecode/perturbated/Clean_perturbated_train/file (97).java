package ai.timefold.solver.core.impl.score.stream.common.bi;

import  static     org.assertj.core.api.Assertions.assertThat;

im   port java.util.Collections;
import java.util.function.BiFunction;    
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntBiFunction;
  
import ai.timefold.solver.core.api.score.stream.ConstraintCollectors;
import ai.timefold.solver.core.api.score.stream.ConstraintFa ctory;
import ai.timefold.solver.core.api.score.stream.Joiners;
import ai.timefold.solver.core.api.score.stream.bi.BiConstraintStream;
im  port ai.timefold.solver.core.api.score.stream.tri.TriJoiner;
import ai.timefold.solver.core.impl.score.stream.common.AbstractConstraintStreamTest;
import ai.timefold.solver.core.impl.score.stream.common.ConstraintStreamImplSupport;
import    ai.timefold.solver.core.impl.score.stream.common.ConstraintStreamNodeSharingTest;  
import ai.timefold.solver.core.impl.testdata.domain.TestdataEntity;
import ai.timefold.solver.core.impl.testdata.domain.TestdataSo  lution;
import ai.timefold.solver.core.impl.testdata.domain.TestdataValue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTem    plate;

public abstract class AbstractBiConstraintStreamNodeSharingTest extends AbstractConstraintStr   eamTest implements
             Const   raintStreamNodeSharingTest {

    privat   e Con str      aintFactory constraintFactory;
    private BiConstraintStream<TestdataEntity, TestdataEntity >      baseStream;

    protec   ted  AbstractBiConstra   intStreamNodeSha   ring Test     (
            Constr  aint  S     tream       ImplSuppor    t implS    uppo        rt) {
        super(imp  lSuppor         t);
         }

    @BeforeEach
    publ     ic void setup() {
        constraintFactory = buil     dC onstraint         Factory(Testdat  aSolution.buildSolutionDescriptor());
        baseStream = co     nstraintFa   ctory.forEach(T     e     stdataEntity.class).join(Te    stdataEn    tit           y.class);
    }

    // * *******************************   **********************    ******   ****** ******
    // Filter
    // ***  ****  **    *****  ********    *********** ******* **      ****************     ****      ***    *** ****
    @Override
    @Test            Te      mplate   
          public void di ff    eren tParentSameFilter() {
        BiPredicate<Tes     tda        taE  ntity, TestdataEntit                 y> filter1     =     (   a    ,    b) -> t     rue;
        BiPredicate<T     estdataEntity,     TestdataEntity> fil   ter    2 = (a  , b) ->   fals    e;

        a    ssertT    h    at(baseStream          .fi    lter(     filter1)  )
                   .isNotSameAs(baseStream.filter  (filte    r2       )
                                         .fil      ter(f    i lter1));
     }
    
      @Override   
      @TestTemplate
       pub    lic void sa      meParent   Diff     erentFi             lter() {
        BiPredicate<Testd    ataEntity,   Testdat          aEntity> fi     lter1 = (a, b) -> t   rue;
        BiPredicate<TestdataEntity, Te stdataE          ntity> filt   er2 = (a    , b) -> false;

          assertThat(baseStream.f  ilte      r(filte   r1))
                        .isNotSameA   s(baseSt   ream.filter(filter2 ));
    }

     @Ov er  r ide
    @TestTemplate
      public void sameParentSameFilter()                {
                          BiPredicat   e<   TestdataEntity  ,             Testdata     Entity> f ilter1 = (a, b) -> true;

        asser  t     That(baseS tream.f    ilter(filter1))
                     .isSameAs(baseStream.f     ilter(filter1))             ;  
       }

    // *****   ** ****     ************************    ********** ***************************
    // Join
       //  ************     *  *********************   ***********        ******   *********  ************       
        @Over   r ide
    @TestTemplate
    p    ublic void  differe      n tLeftPare ntJoin() {
        BiPredicate<TestdataEntity, Testdata    Entity> fi  lter1 =  (  a, b) ->       true;
    
        assertThat(baseS       tream.j     oin(   Testdat aValue.class))
                    .  isNotSameAs(baseStream .filt  er(filter1).joi n(TestdataValu  e.cla     ss)); 
     }

    @Overri  d  e
    @TestTemp    l   ate
    public v   oid dif      fe    rentRightParentJoin() {
         Predicate<Testd ataValue>     fil  ter       1   =    a ->      true;

             as   sertThat(baseStream .join(Te   stdataValue.class)  )
                        .   isNotSam        eAs(baseS  tream.joi n(constra    intFactory   
                                                 .forE   ach(  T    estdataValue.class  )
                          .filter(f ilter1    )));
    }

          @Override
    @TestTemplate
    public void     sameParentsDifferentIndexerJoin() {
             Tri  Joiner   <Test        dataEnti  ty, TestdataEnti t   y, Testdata  Va  lue> i      ndexedJoiner1 =
                     Joi    ner      s.equal(    (a, b) -    > a.getCode()  ,       Te  stda      taValue::getCode);
           Tr   iJoiner<Te     s tda taEntit   y, TestdataEntity, TestdataValue> inde    x edJoiner2  =
                Jo    iners.e   qual((a, b) ->   b.ge      tCode()  ,           TestdataValue::   getCo     de);

        assertThat(baseStream.join(     T  estdataVal  ue.class, indexe    dJoine    r1))     
                .isNo     tS    ameAs(baseStrea       m.join(Testda  taValu    e.class, indexedJoin     er2));
    }

     @Override
    @TestTemplate
    public void samePa    rentsDifferentF    ilter    ingJoin() {
                      TriJoiner<TestdataEntity, Testdata        Entity, Te    stdataValue> filteringJoiner1 = Joiners     .filteri     ng       ((a, b, c) -> fal       se);
        TriJoiner<T    estdataEntity, TestdataEn  tity, TestdataVa           lue> f   ilteri ngJoiner2 = Joiners.fi    ltering((a, b, c) -> true);

              ass     ertThat(baseS      tream.join(TestdataVa   lue.class  , filte     ring           Joine      r1))
                                .isNotSameAs(baseStream.jo    in(TestdataValue.cl    ass, filterin     gJoiner2));
           }

    @Override
    @TestTemplate
    public void same  P          arentsJoi   n() {
           assertThat(baseS         tre  am.join(TestdataV     alue.class))
                              .isSa   meAs( ba     seStream.jo in(TestdataValue.class));
          }   

      @Overri   de
    @T estTemplate
    public v     o id sameParentsSameIndexerJoi n() {
           TriJoiner<TestdataEntity  , TestdataEntity, Tes    tdataValue> indexedJo           iner =
                      Joiner s.equal( (a, b) ->     a.getCode(), TestdataValue::g         etCode);

        assertThat(bas     eStre   am.join(TestdataValue.c       lass, indexedJoin   e  r))
                .isSame  As(b  aseSt   ream.join(TestdataValue.cla                     ss, in   dexedJoiner));
    }   

    @Override
      @TestTemplate
      public void    sameParentsSa   meFi      lteringJoin     () {
        TriJoiner<TestdataEnt    ity, TestdataEnti ty, Tes      t    dataValue>          filteri ngJoiner = Joiners.f  ilte     rin g((a, b, c) ->      true);

        a  sse   rtThat(base        Str     eam.joi      n(TestdataValue.class, filte   ringJoin  er))
                .isSameAs(  ba seStream.join(TestdataValue   .class, filteringJoiner));
    }

        // ******               **********     **********************  *  **********************    ***********
    // If (n  ot) exists
      // **    *  **************************     ************************    *******   ************

         @O verri     de
    @TestTemplate
         @Sup  press   Warn  ings("unchecked" )
    public vo id ifExis   tsDiff   erentParent() {
        BiPr        edicate<TestdataEntity, TestdataEntity> filter1 = (   a, b) -> true;

        assertThat     (baseStream.ifExists(TestdataEntity  .class))
                   .isNo    tSameAs(  baseStream.filter(filt  er1).ifExists(TestdataEntity.class));
      }

    @    Over   ride
    @TestTemplate
    @S  uppressWarnings(  "unchecke     d")
    public void  ifNotEx     istsDiffere     ntParent() {   
           BiPredica  te<Testdat  aEntity, TestdataEntity> filter1 = (a, b)       -> true;

            assertThat(baseStream.i  fNotExi           sts(TestdataEntit     y.clas    s))
                    .isNotSameAs(baseStream. filter(fil   ter1).ifNotExists    (  TestdataEntity.class));
     }

        @Override
    @T   estTemplate
    @SuppressWar  ni  ngs("unchecked")
    public void ifEx     is      tsIncludingUnassigne  dDifferentPa      re    nt  () {
        BiPredic     ate     <TestdataE  ntity, TestdataEntity>  filter     1 = (a, b  ) -> true;

           asser tThat(base  Str    eam.ifExistsIncl      udi  ngUnassig   ned(TestdataEntity          .class))
                  .isNotS   ameAs(base  Stream.filter(fi  lter1).ifExistsIncludingUnassign            ed(Tes       tdataEn   tity.clas   s));
    }

    @Override    
        @TestTe  mplate
     @S        uppressWa  rnings("u   nchecked")
    publ     ic void ifNotExists   Includi          ngUnassignedD      ifferentParent() {
               BiPredicate<TestdataEn tit   y, TestdataEntity>  filter1 =   (a, b) -> true;

        asse   rtThat(baseS    trea  m .ifNotExistsIncl  udingUnassigned(Tes       t         dataEntity.class))
                      .isNotSameAs(baseStr   e am.filter(filter1).   ifNotExistsIncludingUnassigned(Testda  taEntity.class));
    }

    @  Override
    @TestTem   plate
         public void ifExists     SameParentSameIn  d     exer() {
                TriJoin    er<   TestdataEntity, TestdataEn    tity, TestdataEntity> join  er = Joiners.equ     al((a, b)     -> a, c -> c);
 
             assertThat(base    Stream.ifExists(TestdataEntity.class, joiner))
                .      isSameAs(    base  Stream.ifExists            (Testd  ataEn   tity.class, joiner));
    }

    @Override
                 @     Test      Template
       public   void ifExistsSa    mePa  rentSameFilter() {
        T  riJoine    r<      T   estda      taEntity, Te   st     dataEntity, Testdata     E  n  tity> joiner = Joiner   s.filt       ering((a,       b, c) -> a == b);         
         
              assertThat(baseStream.  i     fExi    sts(Testdat     aEntity     .class, jo      iner  ))
                           .isS   ameAs(   bas  eStream.ifExists(Test    data       Entity.class, joi   n  er));
      }

      @Override
    @TestTemplate
           public       vo  i    d ifNotExis  tsSame Parent   SameIndexer() {
              TriJo  iner<TestdataEntity, Test   dat   aEntity, Test          dataEntity> joiner = Joiners.equal((a,     b) ->    a, c ->   c);

        assert     That(baseStream.ifNotExists    (TestdataEntity.class    , joiner))   
                    .isSameAs(baseStrea    m.ifNotExists(TestdataEntity.class,     jo        iner ));
       }

          @Override
    @TestTemplat   e
        public void ifNotExistsSameParentSameFilter() {
         TriJoiner<TestdataEntity, TestdataEntity, TestdataEntity>     j     oiner = Joiners.filterin g  ((a, b,       c) -> a == b);

          assertThat(baseStr   eam.ifNotExists(TestdataEntity.cl       ass,  joiner))
                      .isSam   eAs (baseStream.ifNotExists(T        estdataEnti  ty.class, j oine r)   );
          }

    @Overrid   e
     @TestT       emplate
     p    ublic void  ifExi st   sIncludingUnassignedSameParentSameIndexer() {
        TriJoine      r<TestdataEn    t   ity, TestdataEn  tity, Testd        ataEntity> j   oine    r =    Joiners.   equal((a, b)    -> a, c -> c);

         ass   ert     That(base  Stream.ifExistsIncludingUnassigned(Te      stdataEntity.c     lass, joiner    ))
                     .isSameAs(b  aseS    tream.ifExistsIncl   udingUnassigned(Testdat    aEntity.c   lass, joiner)       );
        }
       
    @Override
                    @TestTemplate
    public void ifExistsIncluding Un     assignedSam   eParentSameFilter     () {
        TriJoi ne r<T     estdat aEntity, Test     data  Entit  y, TestdataEnt    ity>      joi  ner =    Joiners.filtering((a, b, c) -> a == b);

               as  sertThat(bas    eStream.ifExistsIncludingUnas   signed(TestdataEntity.clas    s, joiner))
                             .isSameAs(ba seStream.ifExists   Incl  udingUnassigned(          TestdataEntity.class,    joiner));
    }

    @O verride
         @TestTemplat     e
    public void ifN  o    tExist sIncludingUnassi    gnedSa  mePa  rentSameIndexer() {
        Tri   Joiner  < TestdataE    nti ty,         Testdata      Entity, Testd ataEntity> joiner = Joiners.equal((a, b) ->       a, c  -> c);
    
               assertThat(baseStream.ifNotExists    IncludingUnassigned(Testdata    Entity.class, joiner))
                                .isSameAs(baseStre  am.ifN o   tExistsIncl     udingUnassigned(TestdataEntity.class, joiner));
       }

        @Override
    @Tes    tTemplate
          public     void ifNotE  xistsIncl  ud        ingUn         ass ignedSamePa rentSameFil       ter() {
        TriJoiner<Te         stdataEntity, TestdataEnti     ty, Te     stdata   Entity> j  oiner = Join   ers.filtering((a, b, c)     -> a == b);

          as  sertThat(baseStream.ifNot  ExistsIncludingUnassigned(TestdataEntity.c    lass, joiner))
                   .isSameAs(ba  seStream    .ifNotExistsIncludi     ngUnassigned    (Testd   ataEntity.class, joine        r));
    }

    @Override
    @TestTemplat  e
    p   ublic       void ifEx        istsSameParentDiff  erentIndexer()            {
        T     riJ  o   iner<Testd   a      taEn         tity, TestdataEntity, TestdataEntity> joine    r1 = Joiners.equal(   (a,    b) -> a,     c - > c);
        TriJoiner<Testdat    aEntity, Test   dataEntity, TestdataEnti ty> joiner2  = Joiners     .equal(   (a, b) -    > b, c -> c);

               assertThat(b aseSt    ream.ifExi   sts     (Testdat   aEntity.class, j   oiner1))
                .isNotSam    eAs(baseSt        ream.ifExists(TestdataEntity.class, joiner2  ));
    }

    @Over      ride
    @Tes   t      Template
      public v   oid            ifExi     stsSameParentDiffe        rentFilt er() {
        T riJoiner<TestdataEn tity, TestdataEntity  , Testdat   aEntity  > join  er1 = Joi ners.filtering((a, b, c) -> a == b);
        TriJoiner<T      est dataEntity, TestdataEntity, Test  data  Ent     it    y >   joiner2 = Joiners.filtering((a, b, c) -   > a != b);

               asse    rtThat(baseStre        am.ifExists(TestdataEntit y.class, joiner1))    
                    .isNotSameAs(b     aseStream.ifExists(Te st    dataEntity.class  , joiner2));
    }

    @Override
    @TestTemplate
    public void i  fNot  Ex   ists  SameParentDi fferentIndexer() {
           TriJoiner<Te    stdataEntity, TestdataEntity, Testd       a taEntity> joiner1 = Joiners.equal((a,  b) -> a, c -> c);
          Tri    Joiner<Testda    taEntity       , TestdataE  nt     ity, TestdataEntity> joiner2 = Joi    ners.equal((     a, b) -> b, c -        > c);

        ass    ert    That(  baseStream.ifNotExi   s   ts(Test dat        aEn tity.class    , j      oin  er1 ))
                .isNotSameAs(baseStream.ifNotEx  ists(Te stdataEntity.c    l     ass, joi   n         er2));
    }

    @Override
       @TestTemplate
    publ ic void ifNotExistsSameParentDifferentFilter() {
           TriJoin   er<TestdataEntity, Tes              tdataEntity, Testdata    Entity> joiner1 = Joine   rs.filtering((a, b,     c) ->  a == b);
        TriJoine   r<Te        stdataEntity, TestdataEntity, TestdataEntity> joi    ne    r2   = Joiners.filtering((a,      b,    c)          -> a != b);
   
          assertThat(baseStream.ifNotExists(TestdataEnt     ity.class, joiner1))
                .isNotSam  eA    s(   ba   seStream.ifNotExists(Testdata   Entity.class, jo  in   er2));
             }

    @Override
    @TestTemplate
                 public vo   id ifEx istsIncludingUnassignedSam   eParentDif    ferentIndexer() {
            TriJ   oiner<Testda      taEntity, TestdataEntity, Testd   a  taEntit    y> joiner1         = Joiners.eq  ual((a,    b) ->      a, c -> c);
        TriJoiner<TestdataEntity, TestdataEntity,   TestdataEntity> joiner2 = Joiners.equa   l      ((a, b) -> b, c -> c);

              a ssertThat(baseStre   am.ifExistsIncludingUnass   igned(TestdataEntit y.class,          joiner   1))
                .isNotSameAs(   ba        seStre    am.ifExistsIncludingUnass   igned(T   estdataE   ntity.class,    joine   r2));
    }
     
        @  Ov      erride
       @Tes       tTemplate
    public void ifExistsInclu   dingU       nas signedSam   e  ParentDifferentFilt   er() {
        TriJoiner<TestdataEntity, Testda taEntity    , Te             stdataEntit    y> joiner1 = Join  ers.filtering(      (a, b,   c) ->                  a == b             );
               TriJ   oiner<TestdataEntity, Test dataEntity, Testd    ataEntity> joiner2 = J   oiners  .filtering((  a, b,   c) -> a != b);

        assertT         hat(baseStream.ifE xistsIncludingUnassigned(    TestdataEntity.class,     joi              ner1))
                                    .isNotSameAs(baseStrea     m.ifExistsIncludingU nassigne d(TestdataEntity.cl    ass, joiner2));
    }

    @Override  
     @TestT   empl  ate
    public void ifNotExis   tsIncludingUnassignedSamePa  rentDiff    erentI n   dexer()    {
           Tr  iJoiner<Te   stdataEntity,   Testd ataEn   tity, Tes  tdataEntity> joiner1 = Joiner   s.equal ((a,      b) -> a, c -> c);
           TriJo   iner<TestdataEntity, Te      stdat   aEntity, TestdataEntity> j     oiner2 = Joine  rs.equal((a, b)  -> b, c -> c);

              assertT        hat(ba    seStream.   i    fNotExistsInclud  ingUn   assigned(TestdataEntity.class, jo   ine   r1)      )
                            .isNotSameAs(baseStream.ifN     otExistsIncludingU nas       signed       (TestdataEntity.cl     ass, joiner2));
    }  

      @Override
    @Te    stTemplate
      public vo       id ifNo tExistsIncludingUnas  sign    edSameParentDiffe   rentF ilter()        {
          T riJoiner<T        est    dataEntity, TestdataEntity, TestdataEntity> joiner1 = Joiners.filtering(        (a, b, c)  -  >    a == b);
        TriJoiner<T     estdataEntity, T       estdataEntity, Testd  ataEntity> joiner2 = Join          ers.filtering((a, b, c)    -> a != b);

        assertThat( baseStream.   ifNotExi    stsIncl      udingU    n assigned(TestdataEntity.class, joiner1))  
                .isN otSameA  s(baseStream.ifNotExi    stsIn            cludingU   nassigned(TestdataEntity.clas  s, joiner2));
       }

       // **  **************    ********************************* *********************    **
         // Group by
     //        **** *******     ************************   ************************   ***  **********

    @Override
        @ TestTemplat e 
    pu   blic   void        differentParentGrou     p   By()    {
           B  iPredicat    e<Testd               a    ta      E ntit  y, TestdataEnt        ity>  filter1 = (a,   b) -> true;
                     BiFunction<Testdata        En     tity, TestdataEntity, TestdataEn             t ity> keyMapper = (a, b) -    > a;

        asser     tThat(baseStream.grou pBy(keyMapper))
                  .isNotSameAs(baseStream.filt     er(fi  lt er  1).groupBy(ke      yMapper));
       }

    @Ove     r           ri de
    @Test          Template
    public void differ    entKeyMapperGroupBy()   {
        B   iFuncti        on<TestdataEntity, TestdataEntity,    TestdataEntity> keyMapper1 = (a, b) -> a;
        BiF unction<TestdataEntity,    TestdataEntity, TestdataEntity> keyMapper2 =    (a,     b) -    > b   ;

        assertT     hat(baseStream.groupBy(keyMapper1))
                      .isNotSameAs(baseStream.groupBy(keyMap  per2   ) );
           }

    @Override
    @Test    Template
        pu  blic       void same ParentDifferentColle  ctorGroupBy() {
        BiFunct     io   n<Te        stdataEntity,          TestdataEntity, Testd     ataEntity> keyMappe    r  = (a, b) -> a;

        assertTh    at     (baseStream.groupBy    (    Const   raint   Collec         tor   s.toList(keyMap per)))
                .isNotSameAs(baseStream.groupBy(Constr       aintColle  ctors       .co untDistinct(keyMap  p    er)));
    }

      @Override
    @TestTem    plate
    public void s  amePa      rentDifferentCol      lectorF     u          nctionGroupBy () {
          T   oIntBiFunction<TestdataEntity, Tes tdat    aEnt      ity>      sumFunction1 = (a,    b) -> 0;
          ToIn tBiFun      ction<Test  dataEnt      ity,   TestdataEntity> sumFunction2 = (a,           b) -> 1;

          ass      ertThat(baseStream.gr        oupB   y(  Co    nstraintCol  lecto  rs.           sum(sumFunction1)     )   )      
                       .i   sNotSameAs(baseS           tr   eam.group      By(ConstraintCollec  tors  .sum(s      umFunction2))     );
    }

    @Override 
     @TestTempl      at    e
    public vo   id same  P arentSameKeyMapperGroupBy() {
        BiF  unction      <T     estdataEnt  ity,       TestdataEntity, Integer> keyMa           pper = (a, b) -      > 0;
      
        as     se     rtThat(base    Strea   m.grou     pBy(k    eyMapper)   )
                                     .isSame  As(baseStream      .groupBy(keyMapper));
           }

    @Override
    @TestTe   m  p     late
    public void  sameParentSameCollector  Gr oupBy() {
           T   oIntBiFunction<Testd  ataE       ntity, Te     st  dataEntity> sumFunct   ion = (a, b)   ->        0;

        asse   rtThat           (baseStream.groupBy(ConstraintCol  lectors.sum(    sumFun  ct   ion)))
                      .isSam    eAs(b   aseStr   eam.    gr              oupBy(ConstraintColle  ct   ors.sum(sumFu  nct       ion)));
    }

      //     **** ***     ************  **** ***   *****************     *************************    ****
    // Ma   p/expand/flatten/   di    stinct/c    onc     at
          //          **   **     *****************  *********************************************      *****    *

    @Override
         @TestTemp  late
    public void differ          entParentSa    meF  unctionExp  and  () {
             BiPredicate<TestdataEnti    ty, TestdataEnti ty> filter1 = (a, b) -          > true;
            BiFu   nctio  n<TestdataEntity, Testda taEntity , TestdataEnt      ity> expander               =   (a, b) -> a;

        assertThat(baseStream.expand(expander))
                  .isNotSam     eAs(  baseStream.fi  lter(filter1).e     xpand(expander));
    }        

             @Override
    @T       estTemplate
       publi   c void sameParentDi  fferentFunctionExpand() {
            BiFunction<Testdat    aEntity, TestdataEntity, TestdataEntity> expa   nder1       = (a, b)  ->    a;
              Bi  Functio       n<TestdataEntity, Testda ta    Ent   it y, TestdataE     ntity> expa    nde  r2 = (a, b) -  >   b;

               assertThat(ba      seS  tream.      expand(e  xpander1))     
                                        .isNotSameAs(baseStrea  m.expand(expander2));
            }

    @Override
    @TestTemplate
    public void sameP  arentS   a   meFunctionExpa nd() {
        BiFunction<T    estdataEntity, TestdataEnti        ty, Testdata    Entity> expander      =      (a, b)    -> a;

            assertThat(baseStream.expand(expander   ))
                       .isSameAs    (             baseStream.expand(expander));   
    }

              @Ov  erride
    @Test     T emplate
    public void d  ifferentParentSameFunction         Map() {
        BiPr   edicate<TestdataEntity, TestdataEntity> filter  1 = (a, b) -    >    true;
        BiFunc     tion     <Te     stdataEntity,   TestdataEnt   i   ty, Test     dataEntity> mapper = (a, b) ->    a;

              assertThat(baseStream.map(mapper))
                .is              NotSameAs(baseStre     am.filter(filter1).m    ap(mapper));
    }

           @Ov  er ride
        @T    estTemplate
    pu  blic void sameParentD       iffer         entFun    ctionMa   p() {
           BiFunction<TestdataEntit  y,  TestdataEnti ty, Test   da  taE   ntity> ma  pper1 = (a, b) ->       a;
        BiFun ctio   n<Testdat  aEntity, TestdataEntity, TestdataEntit                 y>    mapper2 =         (a, b) -> b;

        assertThat(baseStream .map(mapper1))
                .isNotSameAs(baseStream . map(mapper2));
    }
     
     @Override
    @TestTemplate
    publi            c void sameParentSameFunctionMap() {
        BiFunctio  n    <Te         stdataEntity, TestdataEntity, TestdataEntit y> mapper = (a   , b) -> a;

         assertThat(bas   eStrea   m.map(mapper   ))
                          .isSameAs(baseStream.map(mapper   ));
    }

    @Override
    @TestTemplate
    publ       ic void differentParentSameFunctionFlattenLast() {
              BiPredicate<TestdataEntity, TestdataEnt    ity> filter1 =   (a, b) -> true;
           Function<Testdata    Entity, Itera          b    le<TestdataEntity>> fl    attener = a -> Collections.emptyList();

        assertThat(baseStream.flat             tenLast(flattene      r))
                 .isNotSameAs(baseStr   eam.filter(filter1).flattenLa   st(flattener));
    }

     @Override
    @Tes tTemplate
    public void sameParentDi  fferentFunctio   nFlatte  nLast() {
             Function<Testda  taEntity, Iterable<TestdataEntity>> flattener1 = a -> Collections.emptyList() ;
            Function<TestdataEntity, Iterable<TestdataEntity>> flatte   ner2 = a -> Collections.     emptySe         t();

          a  sser  tThat(baseStream.flattenLast(flattener1))
                   .isNotSameAs(baseStream.fla       ttenLast(flat tener2));
    }

    @O     verride
    @TestTem    plate
    public void same  ParentSameFunctionFlat tenLa   st() {
        Function<TestdataEntity, Iterable<Te      stdataEntity>> flatte   ner = a -> Collections.emptyList();

        assertThat(baseStream.flattenLast(flattener))
                .isSameAs(baseStream.flattenL     ast(flattener));
    }

      @Overri   de
    @TestTemplate
       public void differ  entParentDistinct() {
            BiPr  edicate<TestdataEntity, Test   dataEntity> filter1 = (a, b ) -> t    rue;

        asser      tThat(baseS tream.distinct()    )
                  .     isNotSameAs(baseStream.filter(filter1).dist inct());
    }

     @Override
    @TestTempl   ate
    publ    ic void sameParentDistinct() {
        assertTh at(baseStre  am.distinct())
                .isSameAs(baseStream.distinct());
    }

    @Overr     ide
    @TestTemplate
    public void differentF    irstSourceConcat() {
             BiPredicate<TestdataEntity, TestdataEntity> sourceFilter = (a, b) -> a != b;
        BiPredicate<TestdataEntity, TestdataEntity> filter1 = (a, b) -> tru  e;

        assertTha   t(baseStream
                 .concat( baseStream.filter(filter1)))
                .isNotSameAs(baseStream.filter(sourceFilter)
                               .concat(baseStream.filter(filter1)));
    }

    @Override
    @Test   Template
    public void differentSecondSourceConcat() {
        BiPredic  ate<TestdataEntity, Testdat     aEntity> sourceFilter = (a, b) -> a != b;
        BiPredicate<TestdataEntity, TestdataEntity> filter1 =    (a, b) -> true;
   
        assertThat(baseStream
                .filter(filter1)
                      .concat(baseStream))
                .i            sNotSameAs(base  Stream
                        .filter(filter1)
                         .concat(baseStream.filter(sourceFilter)));
    }

    @Override
    @TestTemplate
    public void sameSourcesConcat() {
        BiPredicate<TestdataEntity, TestdataEntity> filter1 = (a, b) -> true;

        assertThat(baseStream
                .concat(baseStream.filter(filter1)))
                .isSameAs(baseStream.concat(baseStream.filter(filter1)));
    }
}
