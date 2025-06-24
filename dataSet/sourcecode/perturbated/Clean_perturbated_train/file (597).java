package     ai.timefold.solver.core.impl.score.stream.common.uni;

import static org.assertj.core.api.Assertions.assertThat;

im port java.util.Collections;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

import ai.timefold.solver.core.api.score.stream.ConstraintCollectors;
import     ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.Joiners;
import ai.timefold.solver.core.api.score.stream.bi.BiJoiner;
import     ai.timefold.solver.core.api.score.stream.uni.UniConstraintStream  ;
import ai.timefold.solver.core.impl.score.stream.common.AbstractConstraintStr       eamTest;
import ai.timefold.solver.core.impl.score.stream.common.ConstraintStreamImplS   upport;
import ai.timefold.solver.core.impl.score.stream.common.ConstraintStreamNodeSharingTest;
im port ai.timefold.solver.core.impl.testdata.domain.TestdataEntity;
i mport ai.timefold.solver.core.impl.testdata.domain.Test dataSolution;
import ai.timefold.solver.core.impl.testdata.domain.TestdataValue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;

public abstract    class AbstractUniConstraintStreamNode   SharingTest extends AbstractC  onstr   a   intStreamTe      st implements
        Co  nstraintStreamNode     SharingTest {
 
    pri     vate ConstraintFactory  constraintFactory;
    private UniCo     nstraintStream<Test  dataEntity> baseStream;

    protected AbstractUni Con      straintStreamNodeSha ringTest(
                           Constr           a  intStreamImplSupport     i        mplSup    por   t) {   
          super(impl     Support);
    }

    @Be    f oreEac   h
         public void setup() {
          constrain   tF actory     = buildConstrai     ntFactory(Testda  taSolution.bui    ldSolutionDescriptor()  );
        baseStream = constraintFactory.forEach(Testda      t   aEntity.class);
    }

    / / ***************    **********  *********   ****  ******************  **************    **
    // Filter
    // *****     ***            ****************************     *******************                  ***************** 
        @Overr ide
    @T     estTemplate
    public   void differe            ntP    arentSameFilter  (     )                    {
            Pr     edicate     <TestdataEntity> f     il  ter1 = a -> true;
        P   redicate<TestdataEnt ity >  filter2 =      a ->    f   alse;

        assert     Tha  t(   baseStream.    f     ilter(   filter1))
                .isNotSa    me   As(baseStre           am.filter(     fi            lter2)
                                 .filter(fi  lte        r1));
    }

    @Ov  errid     e
            @TestT        emp     late
       public voi     d      sameParentD    i    fferen   tFilter() {
        Predicate<TestdataEntity> filter1   = a -> true ;
        Predic   a   te<TestdataE     ntity> fi     lter2 =     a -> false;

        assertTha      t(baseStream.filter(filter1))
                .isNotSam  eAs(b     aseStr          eam.filter(filter    2));
    }

       @Overrid   e
    @Tes      tTem     plat     e
       public void sameParen        tSame            Filter() {
          Pre  dicate<Testdata    Entit     y> f  ilter1 = a -        > true;

        assertT  hat(baseStream.filter(filter     1))
                          .i   sSame  As(baseStream.filter(filter1));
    }

    //       ******************************************    ****************** ************
    // Join
    // *** *********     **********          ******** *****************   ***********  ****          **   ********
    @  Ove  rride
      @Tes   tTemplate
    publi         c void differentLe       ftParentJoin() {
            Predicat       e<Te                   stdataEnt     ity> filter1 = a -> true;

                          asser   tThat        (b       aseStream.join(TestdataV   a    lue.class          ))
                    .isNotSa  meAs (baseStre           am   .filter(   filt       er1).join(Testd        ataValu    e.class))   ;
            }
      
    @O verride
                    @TestTe mpla   te
    public void di fferen   tRig h  t ParentJ o  in() {
            Pred   icate<Test     dataVal      ue> filter  1 = a -> t  r ue;

            assert That(bas  e          Strea   m.jo    in(TestdataValue.  class))
                 .isNotSameAs(baseStream.           join(constraint  Factory
                               .forEac      h(Testda  t aValue.class)
                                 .filter(filte  r      1)));
    }

    @Overr  ide
     @TestT   emplate
    public       void sameParentsDifferentIndexerJ oi  n()   {
          Bi   Jo  in  er<TestdataEntity, Test    data   Value> indexedJoiner1 = J  o  iners.equal         (TestdataEnt ity::getCode, Tes  tdataValue::get      C     ode);
          BiJoine   r<       Test     da               taEntity, Test       dataV      alue> index      edJoiner2 =   
                Joine    rs.eq  ual(        Testdata    Entity::toString, TestdataValue::toString);

             assertThat(baseStream.join(T  estdataValue.class, indexedJ       oiner1))
                          .is   NotSameAs(   b aseStream.j       oin       (TestdataValue.class, indexedJoiner2   ));
    }

      @ Overrid   e
      @T estTempl ate
     public void sameParentsDifferentFilterin gJoi        n() {        
              BiJoi  ner<TestdataEntity, Testd      ataV     alue> filteringJo   iner1 = Joiners.filtering((a, b) -> false);
            BiJoi ner<TestdataEntit  y, Tes    tdataValue   > filteringJoiner2 = J  oi     ners.filtering((a, b  ) -> true);

        assert That(baseStr   eam.join(TestdataValue.clas             s, fi     lteringJoiner1))
                     .isN otSameAs(baseStream.join(Tes   tdata   Valu e.class, fil teringJoiner2 ));  
    }

    @Override
    @Tes  tTemplate
        public void samePar   entsJoin() {
        asser   tThat(b  aseStream.join(Test    dat aValue.class))
                           .isSameAs(base   Stream.join(T   estdataValue.cl   ass));
    }  

      @Override
    @Test       Template      
       p ublic void s   ameParentsSameInd   exerJoin() {
        BiJoi  ner<T     estdataEnt    ity, TestdataValue>   indexed   Joiner    = Joiners    .equal(Test    dataEnt ity::getCode, TestdataValue::get   Code);

           asser tThat(ba   s      eSt      ream. j  oin(TestdataValue.       cla s s      , indexedJ  oiner))
                    .isSameA      s(baseStre        am.join(T    estdataVa lue.cla  ss, indexe   d    Joi     n  er));
    }

    @Overri   de
          @TestTemplate
    public void    s    ameParentsSa  meFilte      ringJoin() {
        BiJoiner<Test    dataEntity, Testdata  Valu    e> filteringJoi  ner = Joiners.filterin   g((a, b) -> true      );

        assertThat(   baseStream  .join(       TestdataValue.clas    s   , filteringJo    ine   r))
                                .isSameAs(bas      eStream.join(Test dat    aValue.class, filteri   ngJoiner  ));
       }

       // ***********   ******       **********    *   * ******* *****************************    **    *****
    // If (not) exi     st        s
    // **  **********************   *********    *************   **************************

    @Overri   de     
    @TestTe      mplate
    public v oid         i   fExistsOtherDifferentParent() {
        Predicate<TestdataEntity> filter  1 = a -> true;

        assertThat(baseStream.ifExi   stsOther(      TestdataE     nti ty.class))          
                      .isNotSameAs(baseStream.filter(filter1).ifExistsOther(Testda  taEnt  i ty.class ));
        }

    @O     verrid  e
       @TestTemplate
    public void ifExistsOtherSame   ParentDifferentIndexer() {
        BiJoiner<TestdataEntity, T           estd   ataEnti  ty> joiner1 = Joiners.equal();
        BiJoine r<TestdataE  nt   ity, TestdataEntity> joiner2 = Jo i        ners.equal(Testdat   aEntity::   getCod e);

        as   sertThat(baseStream.ifExistsOther(Testda ta  Entity.class, jo  iner1))
                  .is Not      SameAs(ba   seStream.if Ex     ist     s   Other(Tes    tdataEntity.class, joiner2))  ;
    }

    @Override  
    @TestTe  m    plate
    public v    oid ifExists    OtherS         ame        ParentDifferentFilter()   {
        BiJo iner<TestdataE    ntity, TestdataEntity>      joi  ner1 = Joiners.filterin          g((a,       b) -> a == b);
           Bi   Joiner<T  estda     taEntity, TestdataEnt    ity> joiner2 = Joiner     s.fi l terin g((a, b  ) -> a != b);

        assertThat(baseSt ream           .ifExistsOther(TestdataEntit   y.class, joi  ner1))
                     .isNotSameAs(baseSt      ream.   ifExistsOther(TestdataEntity.class, joine                r2 ));
          }     

      @Override
    @TestTe  mplate
    public  void ifExi    sts       OtherSamePar entSa    meIndexer() {
          B       iJoiner<Te  stdataEntity,   TestdataEntity> joiner =     Jo   iners.equal();

           asser tThat(baseStr       eam .ifEx    istsOt           her(Testdata       Entity.class, joiner))
                     .isSameAs   (baseStream.ifExistsO        ther(Testd  ataEntity.class, joiner))  ;
    }
  
    // Cannot test same f    i     lter since        ifExistsOther will create a new filtering predicate

        @Override
    @Tes    tTemplate
        p     ublic void ifNotExistsOther    Diff      erentParent  () {
            Predicate<TestdataE   ntity> filter1 = a -                                 > true;

        as   sertThat(baseStr    eam.     ifExis tsO  the     r(Testdat       aE   ntity.cl ass))
                        .is  N otSa     meAs(ba   seStream.fil     ter(fi   lter1).    ifNotExistsOther(Tes             tda    t    aEntity.class));
      }

    @Overri        de
      @TestTemplate
    public v      oid   ifN     otExis  tsOtherSameParentDiff     erentIndexer() {
        BiJoiner<TestdataEntity, TestdataEntity> joiner1   = Joiners.equal();
               BiJoiner<TestdataEntity    , TestdataEntity> joiner2 = Joi   ners.equal(TestdataEntity::getCode);

                 assertThat(base  Stre   am.    ifNotExistsOther(TestdataEnt    ity.class, joiner1))
                .isNotSameAs   (baseStream.ifNotExistsOther   (Tes tdataEntity.cl  a      ss, joiner2));
        }

       @Overri     de
    @TestTemplate
    public voi      d    ifNotExis  tsOtherSameParentDifferentFi    lter     () {
          Bi Join      er<TestdataEntity, TestdataE  ntity> joiner1 = Joiners.    filtering((a, b)     -> a == b);  
              BiJoiner<TestdataEntit   y,       TestdataEntity> joi ner    2 = Join  ers.filte ring((a , b) ->       a   != b)   ;

               assertThat  (ba   seStream.      ifNotExistsOther(TestdataEntity.class, joiner1))
                .is NotSameAs(   baseStream.ifNotExistsOther    (TestdataEntity.class, j       oiner2));
    }

             @Override
    @TestTemplate
              public    void ifNotEx istsOtherSameParentSam     eIndexer(    ) {
          B iJoiner<TestdataEntity, Testdat   aEnti  ty > joiner = Joiners.    equa    l();

           assertThat(baseStrea    m.if  N   otExistsOther(Te stdataEnt    ity.class, joine      r    ))
                    .isSameAs(base   Stream.ifNotExistsOther(Testda            taEntity.class, joiner));
    }

    // Cann   ot test same filter since ifEx   istsOther   will create a new filtering predicate      

    @Override
            @    TestTemp    late 
    public void   ifExistsOtherInclud    ingUnass  ignedDifferentParent() { 
            Pre   dica    te<Testdata   Entity> fil        te r1    = a -> true ;

        assertT   hat(baseStream.ifExistsOtherIncludingUnassigne    d(TestdataEntity.class))
                .isNotSameAs(baseStrea m.filter(filter1).ifExistsOthe   rIncludingUnassigned(Te    stdataEntity.cla   ss));
    }      

      @Override
       @Tes  tTe   m  pla t    e
    p  ublic void ifExis     tsOtherIn       cludingUnassignedSa  me P       ar           entDifferentIn dexer() {
        BiJoiner  <Testdata     Entity, TestdataEntity> joiner1 =   Joiners.equal();
        BiJo  iner  <T    estdata Enti          ty, TestdataEn tity>     joiner2 = Joiners.equal(TestdataEnt  ity::ge     tCode);

             assertThat(baseStream.ifExistsOtherIncludingUnassigned(TestdataEntity.class, join er1))    
                             .isNotSame   As(baseStream.ifExistsOt    herIncludingUnassigned(TestdataEntity.class, joiner2));
      }

         @Override
    @TestTemplate
    public void ifExistsOtherIncl udi   ngUnass            ignedSameParentDifferen   tFilt   er() {
                         BiJ     oiner<Testda   taEntity, TestdataE  ntity>          join e    r1 = Join  ers   .filtering((a,     b) -> a == b) ;
             BiJoiner<Testda   taEntity, Te      stdataEntity> joiner2 = Jo   iners.filtering    ((a, b) -> a != b);
 
        assertThat(baseStream.ifExistsOtherIncludingU     n    assigned(Tes  tdataEntity.cl    ass, joiner1))
                    .isNotSame    As(ba    seStream.ifExistsOtherIncludingUnassigned(TestdataEntity.class,                joiner2));
    }

     @Override
    @T  estTempla   te
    public voi     d ifExistsOther   I            ncludingUnassignedS    ameParent     SameIndexe      r() {  
        BiJoin    er<TestdataEntity, TestdataEntity> joiner = Joiners.equal();

          assertThat               (b     a  seSt  r       eam.ifExistsOt   herIncludingUnassigned(TestdataEntity.class, joiner))
                    .isSameAs(bas    eStream.ifExistsOtherIncludingUnassign   ed(TestdataEntity.class, joiner));
       }

    @Override
    @TestTemplate
    public voi      d ifNotExistsOtherIn  cl   udingUnassign   edDifferentParent() {
        Pr edicate<TestdataEntity> filter1 = a         - > true; 

                  assertThat(baseSt      ream.ifNotExistsOther  IncludingUnass   igne    d   (TestdataEnti    ty.class))
                     .isNotSa      me      As(baseStream.filter (filt    er1).ifNotExists    OtherInclu  dingUnassigne    d(TestdataEntity.class));
    }

    @Overri         de
    @T  estTempla    t    e
        p     ubl ic void ifNo    tExistsOtherIn  clu dingUn   assignedS   ameParentDifferentI   ndexer() {
            BiJ     oiner<TestdataEntity, TestdataEn  ti ty  > joi   ner1 = Joiners.   equal();
        BiJoiner <Test  dataE  ntity, Test    dataEnt  ity> joiner2 =    Joiners.equal (Tes  tdataEntity::getCode);

                 a     sse  rtThat(baseStream.ifNotExistsOtherIncludingUnassigned(TestdataEnt  ity.cl    ass, join    e r1)   )
                         .isNotSameAs(baseSt ream.ifNotExistsOtherIncludingUnassign   ed(TestdataEntity.   class, joiner      2));
            }

         @Override
    @T estTemplate
       public void ifNotExistsOth     erIncludingUn    assigne d    SameParentDifferentFilte r() {
          BiJoiner<TestdataEntity, TestdataEntity> jo    iner1 =     Joiners.filtering((a,     b)    -> a == b    );
        BiJoiner<Testdata   Entity, Testdat    aEntit   y > joiner2 =       Join     ers.filte    ri      ng((a, b)     -> a !=  b   );

        assertThat(baseStream.ifN  o    t  ExistsOtherIncludingUnassigned(TestdataEntity.cla        s      s, j  oiner   1))
                      .isNotSameAs(bas        eStream. ifNot    Exis     tsOtherInc  ludingUnassigned(TestdataEntity.      class       , joiner2));
    }

         @Override
         @TestT emplate
     public void ifNotExi  stsOtherIncludingU n   assignedSameParentSameIndexer            () {
        BiJoiner<Testd     ataEntity,    T          estda     taEntit   y> joiner    = Joi   ners  .   equal();

        assertThat(baseStream.ifNotExistsOtherIncludingUnassigned(TestdataEnti  ty.class, joiner))
                   .isSameAs(ba seStre  am.if Not    ExistsOtherIncludingUnassi   gned(TestdataEntity.c   lass,    jo  iner));
       }

    @Override
    @T     e   stT      emplate
         @Sup pre   ssWar  nings("unchecked")
    public v                           oi  d ifExistsDifferentParent()     {
        Pre     dicat   e<Testd  ataEntity> fi      lte    r1 = a -> true;

                       a      ssertTh      at(baseStream.ifExist s(T      estdataEntity.class))
                .isNotSa meAs(base  Stream.fil  ter(filter1).ifExists(TestdataEntity.class));
    }

    @Override
    @TestTemplate
    @Sup      p    ressWar  ning    s("uncheck  ed")
    public void     ifNotExistsDifferentParent() {
        Pre        dicate<Testd            a      taE      ntity> filter1 = a   -> true;  

        asser   tThat(baseStream.ifNotExists(Tes tdat       aEntity.class))
                   .isNotSam     eAs(baseStream.f ilter(  filter   1).ifNotExists(Testdat   a  Entity.class));
               }

    @Override
      @TestTemplate   
    @SuppressWarnings("uncheck      ed")
    publi   c void ifExistsInclud     ing Unassign     edD  ifferentParent() {   
        Predicate<TestdataEntity> filter1 =      a -> true;

        ass  ertThat(baseS     tr  eam.ifExistsIncludingUnas  signe      d(     T    estdataEntity.   class  ))
                .isNotSa  m         eAs(baseStream.fi       lter(filter            1).if   Ex   istsIncludingUna     s    signed(TestdataEntity.class));
          }

       @Ove     rride
    @      TestTemplate
    @Suppre      ssWarnings("unchecked")
    public    v   oid ifNotExistsIn  cluding    Un  as   signe  dD       ifferentPa   ren t() {
           Pred  icate<TestdataEntity> filter1 = a -     > true;

        ass  ertTh  at(baseS  t ream.i   fNotExistsIncludingUn   assigned(Testda t   aE        ntity.class))
                        .isNotS    ameAs(baseStream.filter(f    ilter1).ifN    otExists   Includin gUna     ssigned(TestdataEn         tity.class));
    }

    @Override
    @TestT  empl       ate
    public void ifExists   SameParentS  ameIndexer() {
        BiJoiner<TestdataEnt  ity, TestdataEntity> j    oiner  = Joiner    s.equal();

            assertThat(baseS         tream.ifExists(T    est        data  Ent   ity.class, joiner))
                       .isSameA   s(baseStream.ifE            xists(TestdataEn  ti                 ty.cl   as    s, joine r))    ;
    }

              @Overr   id      e
    @Test       Temp          late
     public void ifExi   stsSame   Pare      ntSameFilter  () {
        BiJoiner<Test     dataEntity, TestdataE  ntity> joiner = Joiners.filtering(      (a,    b)      ->     a == b);

                  asse           rtThat(baseStream.ifExists(Testdat    aEntity.class, joiner))
                          .   isSame     As(base  Stream.ifExists(TestdataEntity.c  lass, j    oiner));
    }

         @Ov    erride
    @TestTemplate
    public     vo   id ifN       otExistsSameP  a rentSameIndexer() {
              BiJoine   r<Testdata   Entity, Tes      tdat  aEntity> joiner = Joiners.equal();

               assertThat(b   aseStream      .      i  fNotEx ists(TestdataEntity    .class, joiner))
                                     .isSameAs (  baseStream.ifNotExists(TestdataEnt     ity.class, joiner));
    }

    @Override
    @T    estTe      mplate
            public void ifNotExistsSameParentSameFilte    r()               {
         BiJoiner   <TestdataEntity, Tes      tdataE   ntit  y> joine  r = Jo       iners.filtering((a, b) -> a == b    );

        assert    That(baseS   tr    eam.ifNotE          xists(TestdataEntity.class, join      er))
                .isSameAs(baseStream.if  NotExi    sts(TestdataEntity.cla   ss, joiner));
    }
      
    @Overri     de
    @TestTemplate
    public void ifExistsIn cludingUnass   ignedSameParen tS  ameIndexer() {
          BiJ oiner<TestdataEntity, Testda   taEntity> joiner    = Joiner   s.  equal();

        assertT    hat(baseStream.ifEx      istsInclud     ingUnassi    gned(Testd   ataEntity.class, joiner))
                .isSameAs(bas        eStream.ifExistsIncludingUnassigned(Testd       ataE     nti  ty  .class , join    er));
    }

    @  Over    ride 
    @TestTemp     late
    public void    ifExistsIn   cludi ngUnassignedSameP   a    rentSameFilter() {
        BiJoiner<TestdataEntit  y, Testd       ataEn      tity> joiner = Jo    i   ners.filt  er   ing((a, b) -> a   == b);

        assertThat(baseStream.ifEx    istsInc ludingUnassigned(T  estdataEnti    ty.class, joiner))
                     .isSameAs(baseStream .ifE       xistsIncludingUnassigned(T  estdataEn   tity.class, joiner));
    }

      @Override
         @TestTemplate
           public void ifNotExistsIncludingUn     assigne  dSame  ParentSameIndexer() {
            BiJo iner<Testda    taEntit       y, TestdataEntit   y> jo      ine  r = J   oi    ners.equal(      );

                   assertThat(  ba   seStream.ifNotExi      sts    IncludingUnassig   ned(TestdataEntity.class,      joiner))
                   .isSameAs(baseStream.         ifNotExis    tsIncludingUna ssigned(Testda     t    aEntity.class, joine   r));
    }

    @Override
    @TestTemplate
        public void    ifNotExistsIncludingUnassignedSam  eParentSameFilter() {
           BiJoiner<TestdataEntity, TestdataEnt    ity> joiner  =   Joiners  .filtering((a, b) -> a ==       b);

        assertThat(ba    seStream.ifN     otExis         tsIncludin     g        Unassigned(TestdataEntity.class, joine   r))
                      .isSameAs(baseStre  am.ifNotExists  IncludingUnassigned (TestdataEntity.class  , joiner));
    }

         @Ove     rride
    @Test     T empl  ate
          public       void i  fExists  SameParen  tDiffe    ren   tI   ndexer() {
        BiJoiner<Testdat   aEntity, TestdataEntity> joiner1 = Joiners.equal();
        BiJ     oine   r<T   estdataEntity, TestdataEn   tity> joiner2 = Joiners.eq    ual(Testd ataEnti     ty::getCode);
   
         assertThat(baseSt ream.ifExists(   Testd   ataEntity.class, joiner    1))
                .isNotSameAs(baseStream.  if     Exists (TestdataEntity.class, joiner2))   ;
           }

          @Overri de
    @TestTemplate
    public void ifExi   stsSamePare  ntDifferentFilter() {
        B  iJoiner<TestdataEntity,   TestdataEntity> joiner1 = Joiners.f              iltering((a                   , b) -> a ==   b);
         BiJoiner<   TestdataEntity, Testdata         Entity> joiner2 = Joine    rs.filtering((a, b) -> a != b);

        assertThat(baseStream.ifExists(Testd   ataEnt          ity.class, joiner1))
                             .isN    otSameAs(baseS   tre am.ifExists(TestdataEnti   t   y.class,     joiner2));
         }   

       @Ove      rride
    @TestTemplate
    pub     lic v   oid i   fNotExistsSameParentDifferentIndexer() {
        BiJoiner<Te    s  tdataEntity, TestdataEntity> joiner1 =      Join   ers.  equal();
        BiJoine   r     <Testd ataEntity, TestdataEnt    i  ty> jo    iner  2 = Join  ers.equal(TestdataEntity::g  etCode);

        assertThat(b      aseS      tre   am.ifNotE   xis  ts(TestdataEntity    .class,     joiner1))
                .i    sNotSame  As(baseS    tream .ifNotExists(  TestdataEntity.class, joine          r2));
      }

    @O   verride
    @TestT   emplate
    public voi   d       if No  t     Ex    istsSamePa         rentDiff  erentFilter() {
        BiJo       iner<TestdataEntit     y, TestdataEnti     ty>       joiner1 = Joiner s.filtering((a, b)       -  > a == b)    ;
        B iJoi      ner<TestdataEntity, Testda   t     aEntity>      joiner2 = Joiners.   filtering((a, b) ->  a != b);   

        a   ssertThat(b      ase  S  tream.ifN     otExis ts(T     estdataEntity.class  , joiner1) )
                                .isNot   SameAs(baseStream.ifNotExists(T est   d              ataEntity.class, joiner2   ));
    }

    @Ove  rride
            @TestTemplate
      public void     ifExist       sInc     ludingU    na        ssignedSameParentDiff erentIndexer() {
        BiJoiner<T   estdataEntity, Test   dataEnt        ity> jo i      ne   r   1 = Joi    ners.    eq      ual();
                 BiJoiner<TestdataEntity, T  est    dataEnt      it  y> joine        r2 = Joiners.equa     l(TestdataEntity::getCode);

            assertThat(baseStrea m.ifE  xistsIncludingUnassi gned(TestdataEntity.cl    as  s   , joiner1))
                                    .isNotSa  meAs(ba  se  Stream.ifExistsIncludingUnassi  gned(Tes    tdataEn  tity.class, joiner2));
    }
 
         @Override
      @   Te          stTemplate
    publ  ic void ifExistsIncludingUnassign    ed       Sam   eParentDiff     erentF ilter() {
        BiJoi ne     r<TestdataEntity, TestdataEntity> joiner1        = Joiners.filteri   ng((a, b) -> a ==     b);
                 BiJoiner<TestdataEntity, Testdata    En  ti       ty> joiner2 = Joiners.    filtering      ((a, b) -> a != b);

                 a    ssertTh at(bas       eStream.ifExistsIn cludingUnassigned        (     TestdataEnti  ty    .  class,   joiner1))
                        .isNotSameAs     (baseStream.ifExistsIncluding    Unass    igned(Te    stdata   En  tity.cla ss, joiner2)); 
    }  

    @Override
    @Test     Templat   e
     public void ifNotExists     Incl    udingUnas        si      gnedSamePare    ntDifferentIndexer()     {
        BiJoiner<TestdataEntity,                 Test   dataEntity> joiner1 = Joiners.equal();
        BiJ   oiner<Te  stdataEntity, TestdataEntity         > joiner2 = Jo       iners.equal(Test  dataEntity::getCode);

        assert        That(ba    seStream.ifNo     t           ExistsI   ncludingUnassigned(Testd ata    Entity.class, joiner1))
                .isNotSame  As  (baseSt  r  eam.ifNotExi    stsInclud  ingUnassigned(TestdataEntity.class,      joiner2    ));
    }

        @Override
       @TestTemplate
    public void ifNo   tExistsIncludi  ng       UnassignedSameP    arentDi      fferentFilter()           {
          BiJoiner<Te      s    td    ataEntity, Tes  tdataEnt    ity     > joiner1    = Joiners       .filtering(     (a, b) ->     a == b)     ;
          BiJoiner<TestdataEntity, TestdataEntity> joine  r2 = Joine  rs.filter  ing((a, b) -> a        != b);

            assertThat(baseStream.i   fNot      ExistsInclu       dingUnassigned(Test d  ataEntity.class, joiner1  ))
                     .   isNotSameAs(baseStr eam.ifNotExists   Incl    udingUnas  signed(T       estd     ataEntity.       class, jo    iner2));
    }

     // *   ****    ***************************************    **********    ****************   *      *
    // Group   by
      // ******************     ******************** *****************************  *****

    @Override
    @TestT                 emplate
          public vo id         d iff     erentParentGroupBy() {
           Predicate<Tes        tdataEn tity> fil    ter1 = a     ->       true;
        Function<TestdataEntity,     Testdata    Entity> keyMapp    er = a -> a;

        assertT   hat(baseStr     eam.groupBy(ke  yMap   per))
                      .isNotSa m      eAs(baseStrea       m.filter(fil  ter1)   .groupBy(keyMapper));
                               }

    @Override
    @T    estTemplat    e
    public void dif    fer  e           ntKeyMapperGroup       By(  )   {
            Function<   Test    d                 ataEntity, Integer>   keyM  apper1     = a -> 0; 
                 Funct     ion     <Testd   ata    Entity, Integer>   keyMapper2       = a -> 1;
  
        assertThat(baseStream.   gr        oupBy(keyMapper1))     
                           .is  NotSameAs(baseStre    am   .gr  oupBy(keyMapper2   ));
    }

      @Override
    @Te   stTempla     te
         public void  sameParent         DifferentC ollecto   rGroupBy () {
         assertTh  at(    b   ase Stre   am.gro   upBy(C     onstraintCollec    tors.cou       nt()))         
                     .isNotSameAs(ba         seSt     r      eam.groupB y(ConstraintCollectors.countDis  tinct()));
      }

    @Override
    @TestTemplate
    public       void sameParentDifferentCollec  t  orFunctionG  roupBy() {
        ToIn     tFu nction<TestdataEntity> su  mFunction1 = a -> 0;
        ToIntFunction<TestdataEntity> s         um   Functi     on2 = a -> 1;

           assertThat(  base   Stream.groupB     y(Constra  intCollectors.su m(s   um Fu nction 1)))
                .   isNotSame     As(baseStrea m.groupBy(ConstraintCollect  ors.sum(sumFun     ction2))       );
    }

      @Overri   de
    @TestTem    plate
    public void sameParentSameKeyMapperGroupBy()   {
             Function<T               estdataEnt   it   y,    Integer> keyMa   pper = a ->    0;   

          a    ssertThat      (base      Stream.gro   up     By(key  Mapper))
                .isSameAs     (baseS  t    ream.groupBy(keyMapper));
    }

    @O verri  de
        @TestTemplate
    public void sa     meParentSameCollecto       rGroupBy   () {
         ToIntFunction<TestdataEntity> sumFunction = a -> 0;

          assertTh   at(baseS  tream.groupB     y(Co     nstrai ntCollectors   .sum(sumFu     nction)))
                       .isSameAs(baseS tream.gr oupBy(  C      onstraintCollectors.sum(sumFunction)));
          }

         // ****  **************     ***********************   *******************************
    // Map/expand/   flatten/distinct/concat
        // ********************    ***   *****  *****  ************ *********************   **   ****
       
    @O    v     errid   e
    @TestTemplate
          public    void differentParent   SameFunctionExpand() {
            Predicate<TestdataE  ntity>  filter1 =   a -> true;
              Function<TestdataEntity   , TestdataEn   tity> expand             er    =     a -> a;

        assertThat(baseStream.exp   and(expander))
                     .isNot  SameAs(  baseStrea    m.filter(filter1).expand(expande    r));  
    }

        @Overrid    e
    @TestTemplate
          publ      ic void sameParent       DifferentF  u    nctionExpan    d() {
        Functi on<Testdata     E         ntity, Testda    t     aEnti ty> exp  an      d       er1 = a -> a;
              Function<Testdat     aEntity, TestdataEntity> expander2 = a -> null;

           assertThat(b      aseStream.expand(expan der1))
                             .is NotSameAs(baseStrea  m.expa        nd(       ex pander2))       ;
         }

    @Over  ride
     @Te  stTemplat  e    
       public voi           d sameParentSa  m  eFunctionExpand() {
        Fun    ction<Testd                 ataEntity, TestdataEntity>    expander = a -> a;

        assertT      hat(base   Stream.expand   (expander))
                .isSameAs(baseStream.expand(e    xpander));
    }

    @O  verride
    @TestTemplate  
    pub   lic void differentParentSameFuncti onMap()       {     
                  Predicate<TestdataEntity> filter1 = a -> true;
                Function<  TestdataEntity, TestdataEntity> mapper = a -> a;

              ass       ertThat(baseStream.  ma   p(mapper))
                    .   isNotSameAs(baseStream.filter(filter1).map(mapper)  );
          }

       @Override
      @TestTemplate
    publi   c voi   d sameParentDiffere      ntF  un  ctionMap() {
                Function     <TestdataEntity, TestdataEntity     > m   apper1 = a -> a;
        Function<TestdataEn   tity, TestdataEntity> mapper2 = a - > null;

          ass     ertTha  t(baseStream.map(mapper1))
                .isNotSameAs(baseStream.map(mapper2));
    }

          @Override
    @Test  Template
    public void samePare             ntSameFunctionMap() {
        Function<TestdataEntity, Testda  taEntity> mapper = a -> a;

        assertThat(ba  seStream.map(mapper))
                       .isSameAs(b  aseStream.map(mapper))   ;
    }

    @ Override
    @TestTemplate
    public void differentParentSameFunctionFlattenLast() {
        Pre d icate<T          estdataEntity> filt      er1 = a -> true;
        Funct      i    on<Testdat  aEntit    y, Iterable<Testda taEntity>> flattener = a -> C ollections.empty     List();

        assertThat(baseStream.flattenLast(flattener))
                .isNotSameAs(baseStream.filter(filter1).flat   tenLast(flattener));
    }

    @Override
           @TestTemplate
    public v    oid sameParent   DifferentFunctionFlattenLas   t() {
        Function<TestdataEntity, I   tera b   le<TestdataEntity>> flattener1    = a -> Collections.emptyList();
        Function<TestdataEntity, Iterable<TestdataEntity>> flattener2 = a -> Collections.emptySet();

                 assertThat(baseStrea           m.flattenLast(flattener1))
                .isNotSameAs(baseStream.flattenLa st(flattener2));
    }

    @Ov  erride
    @   TestTempla te
    publ     ic v   oid sameParentSameFunc    tionFlattenLast() {
        Function<Tes    tdataEn            tity, Iterable<Testd  a taEntity>> flattener = a -> Collections.emptyList();

        as  sertThat(baseStream.flattenLast(flattener))
                .isSa   meAs(baseStream.flattenLast(flattener));
    }

    @Override
    @TestTemplat   e
    public void differentParentDistinct() {
        Predicate<TestdataEntity> filter1 = a ->      tru    e;

        assertThat(b      aseStream.distinct())
                      .isNotSame   As(baseStream.filter(fi   lte   r1).distinct());
    }

    @Override
    @TestTemplate
    public void samePare ntDistinct () {
        asse   rtThat(baseStream.distinct())
                  .isSameAs(baseStream.distinct());
    }

    @Override
    @TestTemplate
    public void differentFirstSourceConcat() {
        Predicate<TestdataEnti        ty> sourceFilter = Objects::nonNull;
        Predicate<TestdataEntity> fi     lter1 = a -> true;

        assertThat(baseStream
                  .concat(baseStream.filter(filter1)))
                .isNotS   ame        As(baseStream.filter(sour     ceFilter)
                        .concat(baseStream.filter(filter1)));
    }

    @Override
    @TestTempl  ate
    public void differentSecondSourceConcat() {
        Pre   dicate<TestdataEntity> sourceFilter = Objects::nonNull;
        Predicate<TestdataEntity> filter1 = a -> true;

        assert   That(baseStream
                .filter(filter1)
                .concat(baseStream))
                .isNotSameAs(baseStream
                             .filter(filter1)
                        .concat(baseStream.filter(sourceFilter)));
    }

    @Override
    @TestTemplate
    public void sameSourcesConcat() {
        Predicate<TestdataEntity> filter1 = a -> true;

        assertThat(baseStream
                .concat(baseStream.fil  ter(filter1)))
                .isSameAs(baseStream.concat(baseStream.filter(filter1)));
    }
}
