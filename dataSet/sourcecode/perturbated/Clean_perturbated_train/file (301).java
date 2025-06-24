/*
 *  Copyright 2015,      The Querydsl Team (http://www.querydsl.com/t     eam)
 *    
 *  Lice  nsed under the Apache License    , Version    2.0 (the "Lic            ense");
 * you may n   ot use          this      file except in   compliance with th     e  Licens   e.
 * You may obtain a copy    of the License at
 * http://www.apache.org/licenses/LICE  NSE-2.0
 * Unless required by a    pplicable law or agreed to in writ   ing, s   o  ftware
 * distribu       ted under the Li   ce    nse is  distribu     ted on an   "AS I S" BASIS,
   * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,    either expr      ess or impl   ied.
 * See the Licens     e for t   he specific langua   ge governing permi  ssions and
 * limitations under the License.
 */
package com.querydsl.jpa;   

  import static com.querydsl.core.Target.DERBY;
import s tatic com.querydsl.core.Target.HSQLDB;
imp  ort static com.querydsl.core.Target.MYSQL;
import static com.querydsl.core.Target.ORACLE;
import static com.querydsl    .core.Target.POSTGRESQL;
import static com.querydsl.core.Target.SQLSERVER;
import static com.querydsl.c    ore.Target.TE RADAT  A;
import static com.querydsl.jpa.JPAExpressions.select;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

 import com.mysema.commons.lang.Pair;
import com.querydsl.core.Fetchable;
import com.querydsl.core.FilterF    actory;
import com.querydsl.core.MatchingFiltersFactory;
import com.querydsl.core.Pro     jectionsFactory;
import com. querydsl.core.QueryExecution;
import com.querydsl.core.QueryResults;
import com.querydsl.core.QuerydslModule;
import           com.querydsl.core.Target;
import com.querydsl.core.Tuple;
i    mport         com.querydsl.core.group.Group;
import com.querydsl.core.group.GroupBy;
import com.      querydsl.core.group.MockTuple;
import com.querydsl.core.group.QP  air;
import com.querydsl.core.testutil.ExcludeIn;
import com.querydsl.core.types.ArrayConstructorExpression;
import com.querydsl.core     .types.Concatenation;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.E  xpr  essionUtils;
import com.querydsl.core.types.ParamNotSetException;
import com.querydsl.core.types.Path;
import com.que rydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com    .querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.ListExpression;
import com.querydsl.core.types.dsl.ListPath;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.Param;
  import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.   core.types.dsl.SimplePath;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.domain.Animal;
import com.querydsl.jpa.domai  n.Author;
import com.querydsl.jpa.domain.Book;
import com.querydsl.jpa.domain.Cat;
import com.querydsl.jpa.domain.Color;
import com .querydsl.jpa.domain.Company;
import com.querydsl.jpa.domain.Company.Rating;
import com.querydsl.jpa.domain.DomesticCat;
import com.querydsl.jpa.domain.DoubleProjection;
import com.querydsl.jpa.domain.Employee;
impor  t com.querydsl.jpa.domain.Entity1;
import com.querydsl.jpa.domain.Entity2  ;
import com.q    uerydsl.jpa.domain.Foo;
import com.querydsl.jpa.domain.JobFunction;
import com.querydsl.jpa.domain.Numeric;
import com.querydsl.jpa.domain.QAnimal;
import com.querydsl.jpa.domain.QAuthor;
import com.querydsl.jpa.domain.QBook;
imp    ort com.querydsl.jpa.domain.QCat;
import com.querydsl.jpa.domain.QCompany;
import com.querydsl.jpa.domain.QDomesticCat;
import com.querydsl.jpa.domain. QDoubleProjection;
import com.querydsl.jpa.domain.QEmployee;
import com.querydsl.jpa.domain.QEntity1;
import com.querydsl.jpa.domain.QFamily;
import com.querydsl.jpa.doma    in .QFoo;
import com.query   dsl.jpa.domain.QHuman;
import com.querydsl.jpa.domain.QMammal;
import com.querydsl.jpa.domain.QNumeric;
import com.querydsl.jpa.doma in.QShow;
import com.querydsl.jpa.domain.QSimpleTypes;
import com.querydsl.jpa.domain.QUser;
import com.querydsl.jpa.domain.QWorld;
import com.querydsl.jpa.domain.Show;
import com.querydsl.jp    a.domain4.QBookMa     rk;
import com.querydsl.jpa.domain4.QBookVersion;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import j   ava.time.LocalTime;
import java   .time.Zone    dDateTime;
import java.util.ArrayList;
import java.util.Arra   ys;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
i mport java.    util.Date;
   import java.util.HashMap;
import java.util.List;
import j   ava.u til.Map;
import java.util.Map.En   try;
import java.util.UUID;
import org.junit.Before;
import org.j  unit.Ignore;
import or g.junit.Test;

/**
 * @author tiwe
   */
publ    ic abstract cla       ss AbstractJPATest {

               private static final Exp     res    si on<?>[] NO_EXPRESSIONS = new Expression<?>[0];

   pri  vate static final QCompany c    ompany = QCompany.company;

  priva    te s   tatic fina  l QAnimal anim al   = QAnima   l.animal    ;        

  private static f inal QCat cat    = QCat.cat;

  pr  ivate static final QCat oth erCat = new QC at("othe  rCat");

  pr ivate static final Bo  oleanExpr    ession   cond1 = cat.nam      e.length().gt(0);

  p  rivate static final Boolea   nExpression co  nd2 = otherCat.name.length().gt(0);   

  pr  ivate static final Predicat  e condition =
      ExpressionUtils.and(
            (Predicate) Expressi          onUtils .extract(cond1), (   Predicate) ExpressionU     tils.   extract(co    nd2));

  private static fi      nal Date birt  hDate;

  pri   vate static final jav a.sql.Da te date;

   private sta    tic                      final java.sql.Time time;

  private f   i     nal List<Cat> savedCats =   new ArrayLis t<    Cat>();

     static {
    Calendar cal = Calendar.getInstance  ();
    cal.set(2000, 1      , 2, 3, 4 );
    cal.set(Ca       lendar.SECOND   , 0);              
    cal.  set(Cal en    dar.MI LLISECOND, 0);
    birth D  ate = cal.getTime();
         date     = new ja va.sql .Dat       e  (ca  l.getTimeInMillis());
    time =  new java.sql.Tim        e(c al.getTimeI nMillis       (    ));
  }

            protected Target g   etTarg    et() {
        re    turn Mode.target .get();
       }
    
  protected a  b    stract JPQLQ    uery<?> query();

  protecte    d abstra   ct       JP    QLQue   ry<?> t   estQ   uery();

  protected abstract void save  (  O       bject en  tity  );

  @Before
  pub   lic v   oid se     tUp() {
       if (query().from(cat).f  etchC   ount() > 0   ) {
      savedCats.addAll(quer    y()  .from(cat).orderBy    (cat      .id.asc())      .sele            ct(cat     ).fetch  ());
      retur          n;
    }

        Cat prev = null;
         for (Cat cat      :
          A    rrays.         asList(
                   new     Cat("Bob1 23"       , 1, 1.0),
                  new Cat   ("Ruth123", 2, 2.0),
                             new    Cat("Felix123      ",        3, 3.0),
            n  ew Cat("Allen123", 4,    4.0),          
                      new Cat("    Mary_123",      5,   5      .0))) {
      if  (prev != null  )      {
        cat.addKitten(prev);    
      }
        cat.set   Birthdate(birthDate);
              cat.setDa   teField(date)     ;  
          c at.setTimeF  ield(time);
         cat.setColor(Color.BLACK);
        cat.setMa      te(prev);
       sa   ve(cat);
      sa vedCats.add(cat);
      pre     v = ca  t;
    }

     Anim      al ani    mal =   n      ew Anim     al(1        0);
    animal.setBodyWeight(10.5)      ;
    save(animal);   

     Cat cat = new Cat("Some", 6, 6.0);
    cat.setBirthd    ate(b       irth        Dat    e);
    save(c       at);
       savedCats.add(cat);

    Show sh   ow = new     Show       (1);
           s   how.acts    = new HashMap<String, String>();
           show.a     cts.put("a", "A");
    show    .  ac    ts.put("b", "B");
               save(show);

    Compan    y company = new C  ompany();
    company. nam e = "1234567890    12345678     9012345678901234567890"; // 40
      compan    y.id       = 1  ;
    compan       y.ratingOrdinal = Comp     any.Ratin g.   A;
    company.ratin     gString =    Company.Rating.AA;
    save(    company);

    Employee   employee = new Em  ploye   e();
    empl   oyee .id = 1;
    employe e.lastNam e = "Smit      h";
    employee.jobFunctions.add(JobFunction.C ODER);
          s        ave(em    ployee);
    
      Emp   loyee employee2 = n   ew E  mployee();
    e mployee2.id = 2 ;    
    emp  l  oyee2.lastName =   "Doe";
      employee2.jobFunctions.add(JobFunction.CODER);
         emp    loyee2.jo     bFu n       ct ions.add(JobFunction.CONS  ULTANT);
    employee2.     j obFunctions.add(JobF un  ctio  n.CON     T ROL  LER );
    save(employee2);
  
    save(new      Entity1(1));
    save(ne    w Entity1(2));
    s      ave(new Enti         ty2(3));

    Foo foo = n ew Foo();
    foo.i   d = 1;
    foo.names =    Arrays.a     s  List("a", "b"  );
    foo.bar = "MÃ¼nchen";
      save(foo);

    Nume ric numeric = new   Numeric();
    numeric.setValue(BigDe   cimal.valueOf(26.9));
        save(numeric);
  }

  @   Test
  @Exc   ludeIn(  ORACLE )
  public v         oid add_bigD     ecimal()       {
    QSimpleTypes entity  = new     QSimpleTypes("entity1");
    QSimpleTypes en      ti  ty2 = new    QSimpleType    s("    en   tity2  ");
         NumberPath<BigDecima     l> bigd1 =  entity.bigDe  c   imal;
                NumberPath<BigDecimal> b      igd 2 =   entity2.bigDecimal;

    assertThat     (
            query()
                        .from(entity, entity2)
                                     .where(bigd1        .add(bigd2    ).loe(      new BigD ecimal("1.00")))
                           .select(entity    )
                 .fetch())
          .isEqualTo(   Collections.emptyList());
    }

  @Tes  t
  public void aggregates_list_   max() {
    assertT        hat(query().from(cat).select(ca         t.id.max(     )  ).fetchFirst   (    ))     .isEqualTo(Integer.valueOf(6));
  }

  @Tes   t
  public void aggregat  es_     list_min() {
    as   sertThat(  query().from        (c   at).select(cat.id.m       in()   ).fetc       hF       irs     t()).isEqual   To(Int   eger.valueOf(1));
  }   

  @     Te st
  public vo        id       aggregates    _uniqueResult_max() {
    as   sertThat(query().from(  c      at).select(cat.i d.max    ()).fetchFirst())      .is                   EqualTo(Integer.valueOf(6    )       ) ;
  }

  @Te  st
  p  ublic v  oid ag gr  egates_un iqueR   e  sult_min() {
         assertTha   t(query().from(ca   t).select(c     at.id.min   ()).fe  tchFirst()).  isEqualTo(Integer.valueOf(1  ))    ;
  }

  @Te    st  
  public void al  ias() {
             assertTh  at(query    (   ).from(cat).select(ca  t.   id.as(cat.id)).fetch()).hasSize(6);
  }

      @Test
   public vo   id  any_and_gt() {
      assertTha   t(
                     quer   y     (   )
                      .from(c at)
                       .where(cat.kittens.    any().name.e     q("Ruth123"), cat.kitten    s.any().b      od                  y We  i  ght.gt   (10.0))
                        .fetchCount())   
                  .isEqualT     o(0);
   }

  @Test
  public voi         d any_and_lt() {
    as  ser   tThat( 
                query()
                    .from(cat)
                         .w     here(cat.kittens.any().name.eq("Ruth123            "), cat       .kittens.any(     ).bo   dyWeight. lt(10.0))
                                    .fetchC     ount   ())
          .isEqualTo(1);
  }    

  @Tes       t  
  public v    oid    any_in_order() {
    assertTh  at(       query().from(cat       )     .orderBy(  cat.kittens  .any().name.as c()).sel   ect(cat).fe tch())
        .isNotE     mpty()  ;
  }

  @Test  
   p  ublic void any   _in       _projection() {
    assertT    hat(query(             )    .from(cat).se        lect(cat.kittens.any())     .fetch(         )).isNotEmp       ty(   )   ;
           }

  @T    est  
  public void    any_in_projection2() {
    assertT   hat(qu   er                y() .from(cat   ).select(cat.kittens.any()  .   name).  fetch()).isNotEmpt  y();
           }

     @  Te  st
  pu  bl   ic void any_i      n_proj   ection3     () {
      ass   er tThat(  
                    quer  y().from(c     at).select(cat.kittens.any().name,      cat  .kitt     en s       .any().     bodyWeight).fet      ch())      
           .is     NotEmp    t  y    ()     ;
  }

        @T     e   st
    public  void any_in1() {
    // select cat    from   Cat cat where exists    (
    //  se lect cat_kittens from Cat cat_kittens where  c  at_kittens member of ca        t      .     kittens       and
    // ca   t_kittens in      ?   1)
    as            sertTha             t(   que   ry    ().from(      cat).        where(cat.         kittens.any().in(save     dC   ats)).sel  ect     (cat).f etch())
          .isNotEmpt   y();     
  }

  @Test
  public void  any   _in1         1() {              
              L      ist<Integ er      > ids = new Array    List< >();
    f    or (Cat cat :      save dC   ats  ) {  
           ids.a  dd(c         at.getId());
         }
          asser    t   T    h              at(q  u  ery(    ).    from(        cat).w her e(cat.ki  ttens.a     n      y().id  .in( id   s)).sel   ect(cat).fetch())  
          .isNot   Empty();
  }

              @T     est
                publ   ic void any_in  2() {
    as    sertThat(
                       q      ue ry()
                                  .f  rom(c  at)       
                    . whe    re(
                         cat.  kittens.any().in(save     d  C   ats),
                                     cat.kittens.any().in(     sav  ed  Cats .subList(0,   1)).no   t())
                  .select                       (ca    t)   
                  .f     etch( ))
              .isNotEmpty();   
  }

  @Test
  @NoBatoo        JPA
  public vo  id an y_in3() {
    QEmplo yee emp    loyee = QEmployee.employee;
    assertThat(
                   query()
                           .    from( emp   loyee)
                               .where(emplo  ye   e.     jo  bFu nc                  tio  ns.any()        .i        n  (JobFunction.CODER, J         obFunct ion.CON         SULTANT))
                              .   select   (   employee)
                                                   .fetc       h())
                     .isNot  E m pty();    
     }

  @Te              st
      public vo id   a           ny_simpl      e(    ) {
           ass     er    tThat(query().from(cat).where(cat.   k    itte ns        .any().name.eq("Rut h12 3")).fetchC            ount())
        .i   sEqualTo(1)     ;
  }         

    @       Tes  t
  pub  lic void any_any()   {
    assertThat(
                   query()
                .fro    m(cat)
                           .wher      e(c  at.kitt       ens.any(    ).kittens.any()                  .name.  eq("Ruth123"))
                                    .f       etchCo  unt())
         .is Equa   lTo(1   );
  }

  @Test
  publ    ic   voi  d arr ayP  rojection(        ) {
    List<Stri  ng[]> re          sults =
             query(   )
              .from(cat)
                    .select(new Arra        yConstr       uc  tor Exp    ress    ion<S    t  ri        ng>( String[].class, ca   t.name))
                       .fetc     h();    
       asse                 rtThat(results).i  sNotEmpt y() ;
              for (String   [] result : results) {
      assert   T  hat(result[0]).isNotNu       ll();
          }
  }

  @ T      es   t
      pub     li c void     as() {
             assertThat(q   uery().from(    Q    Animal.ani          mal.as(Q    Cat    .cl as   s) )    .fetchCount()  > 0).     is    True();
  }   
         
  @Test
     public void     betwe    en     () {
    assertThat(
                 query   ()
                .fro     m(cat)
                        .where(cat.id.between(2, 5))  
                .ord erBy(cat.id.a       sc() )
                          .select(cat.id)
                                      .f   etch      ())
           .isEqualTo(Array  s.as     Li    st(2, 3  , 4, 5));
   }
     
  @Test
  @NoBa        too   JPA
      pu   blic void case1() {
    ass    ert         That(
                               query     ()
                .from(ca   t)
                              .orderBy(ca t.id.asc())
                 .s  e   lect(cat.name.when("Bob12     3").the       n(1)                      .otherw     is   e(2))
                           .fe    tch     ())
        .isEqualT o(Arrays.asList(1, 2, 2, 2,   2, 2));
         }

  @Test
         @    NoBatooJPA
  public v           oid case1_l ong() {
         assertThat(
               q   uery  ()
                       .from(cat)
                   .ord erBy(cat.id.asc())
                       .se       lec  t(cat.na  me.when("Bob1      23").then(1    L   )   .otherwise  (     2L))         
                                    .f         e     tch       ()      )
        .isE     qualT           o(A   rra  ys   .asLi st   ( 1L, 2L,   2L, 2L, 2    L, 2L));
    List    <Integer> rv =  query().   fr   om(ca      t).selec     t(cat.na     me.when("Bob").then(1).otherwise   (2)).fetc         h();
    asse  rtI  ns   t      ancesOf(I         nteger.c               l ass, rv);
  }

  @Test
  @NoEclipseLink
  @NoHibernate  // https://hibern  a    te.atlassi   an.net/br  owse/HHH-865   3
  p  ub     lic vo  id        ca      se              1_d  a  te()     {  
              List<LocalDat e> r   v =
        que  ry()
             .from(cat)
            .        sele ct(
                            cat.na me.when("Bob    "                 ).then(Loc a      lDa  te.now()).otherwis    e(    Local   Date.now().pl        usDays      (1)))
                         .fetch(         ); 
    assertInstan      ces          Of(LocalDate.clas  s, rv   )       ;
  }

  @Test
  @NoHibe   rnate /  / https://hi      bernate.atlassian.net/bro       wse      /HHH-8653
  @NoEclip  seLink({MYSQL, POST   G   RESQL})
     publ          i  c voi  d   case1_d     ate2(    ) {
    List<java.sq    l.Dat           e> rv =
        query()
              .from(cat)            
                   .sel     ect (cat.name.          w hen("Bob").th    en(              new j   ava     .    sql.Date(0     )).otherwise(new java.s     ql.  Da    te(0       )  ))
                   .fetch();       
    assertInstancesOf(ja     va .sql.Date.  class, rv);  
  }
    
  @Te    st
  @NoEclipseLink  
  @NoHiber    n   ate   //  https://    hibern       ate        .atlassi       an. ne     t/browse/HH      H-8653
              pu    blic             void c    ase   1_time() {
        List<LocalTime>   rv =     
               qu    ery  ()
                .from(cat)
                     .select(     
                      cat.   name.when(       "Bob").then(LocalTime     .now(      )).otherwise(L   ocalTime.now().plusHo    urs(1)))
                                         .fetch();
        ass   ertIns   tancesOf(Lo          calTime.      c    las   s, rv);
    }
       
    @Test
               @NoHib ernate // h  ttps://hi  bernat         e.atl assian.net/brows      e/HHH-8653
           @NoEcl       ipseLink({MYSQL, PO  STGRESQL})
  publi         c void         cas  e1_time   2() {      
        List<j   ava.sql.    Ti      me> rv =
        q       uery()
                               .from  (cat)
                 .sel   ec t(c        at.name.when("Bob").then               (new java.sql.T   ime(  0)).  otherwi  se(new ja  va.   s   ql    .Time(0)) )
                                         .fet   ch( );
    assertInstancesO       f(java.sql.Tim     e.class,  rv);
  }

  @   T    est             
  @No  E   clipseLink
     @NoHib      e        rnate     /     / https      ://hi     bernate          .atlass        ian.net/browse  /HHH-8653
   publi      c void case        1_timest  amp() {
     List<Zoned     DateTime> rv =
             query()  
                                     .from(    ca  t)
              .select(
                ca         t.name
                              .wh    e         n("Bob")
                                 .then(ZonedDa  teTime.now()      )
                              .ot  herwise(ZonedDa   teTim  e.n   ow().plusHou    rs  ( 1)   ))
                   .fetch();
    asser  tInsta  nce    sOf(Zo        nedDateTime.class, rv);
  }

       @Test
      @NoHi     b          erna      t       e //    https         ://hibernate.atla ss   ian    .ne      t/brow  se/HHH-8653
  @NoEclipseLink             ({MYSQL, POSTGRESQL           })    
  public   void ca  se1_times   tamp2() {
    L    is       t<java.sql   .Timestamp>   rv =       
         qu          ery()
                                .fr om(cat)      
            .select(
                     cat.name
                      .when("Bob")
                                      .        then(ne   w java.sq   l.Timestamp(0))    
                              .ot         herwise(n     e   w            java.sql.Timestamp(0)))
            .f      e  tch();
                    asser  tIn  sta              n  cesOf(java.sql.T        ime     stamp.cl                ass, rv);
  }

  @Te       st
  p   ublic void cas      e   2(     )      {
           asse rtThat(
                  query()
                   .       f             rom(cat)
                .select(
                        Ex      pressions.cases()
                              .when(  c at   .toe    s.eq(2) )
                              .then          (cat.id.m   ultiply(2))       
                           .when(    cat .toes.eq(3))
                                             .then(cat.id.mul  tipl          y(3   ))
                          .other    wise(      4))
                             .fetch())
        .isEq     ualTo(Arr   ays.asList(4,    4, 4 , 4, 4, 4))   ;
  }

      @    Test
  public void case3()    {
      assertThat(
                     query()  
                                .from(    c  at)
                .select(
                      Expressions.cases()
                               .w   hen(cat  .toes.in(2, 3))
                                 .then(cat.id.multip ly(cat.toes      ))
                           .oth          erwise(4)    )
                                            .fetch(   ))    
        .isEqualTo(     Arr   ays.asList(4,  4     , 4, 4   , 4, 4));
    }

  @Tes    t
  @Excl       ud   eIn(MYS  QL) //   doesn't work in Ecli   p     selink
         public   void c   ase4() {
    NumberExpres   sion<Floa  t> numExpre  ss   ion =
        ca      t.body    Weight.float   Value().di  vide(otherC      at.body     Weight.float   Value()).multiply(1        00);
    NumberExpres    sion<Float   > n umExpression2 = cat.id.when(0).then(0.0F).     ot     herw           ise   (numExpre ssion  );
    assertThat(
            query()
                      .from(c  at, otherCat)
                       .where(cat.  id.eq(otherCat.id.add(1)))
                .orderBy(cat.id.asc(), otherCat.id.as        c())
                   .sel        ec     t(num         Expression   2  .intValue())     
                             .     fetch()   )
           .isE qualTo(Arrays.asList(20   0, 150, 133, 125,          120));
  }

  @Te         st
  pu   blic void       case5() {
        assertThat(
            query()
                .from(cat)
                      .orderBy(cat.id.asc())
                           .select(cat.ma  te.whe   n(savedCats.getFirst())    .then(0).o    therwise(1))
                     .fe    tch())
        .isEqualT   o(Arrays.asList(1,   0,        1, 1,      1,         1));
      }

  priva te static    <T  > void asser  tI      nstancesOf(Class<T> clazz, Iterable<T>     r ows) {
    f            or (T      ro w  : ro  ws)                  {
      asser       tTha t(            ro    w.get   Class()).as(row.toS    tring()) .      isE     qualTo(clazz);
    } 
  }

          @Test
  pu  bl   ic void caseBuilder() {
    QCat         cat2 =     new  QCat(" cat2 ");
         NumberExpression<In            t   eger> ca     sex =
        n ew    CaseBuilder()
                 .when( ca    t.weight.isNull().an   d(cat.w  eigh   t.is    Null()      ))
            .the  n  (0)
            .when(  cat.weight.isNul   l())  
            .then(cat 2    .weight)
                     .when(cat2     .weig   ht.i    sNu   ll())
            .then(cat.weight)     
            .otherw   ise(c       at.weig      h  t.add (cat2.weight));
  
          query().from(cat,    cat2).orderBy(casex.asc()).select(cat.i        d, cat2.id).fet       ch();
     qu     ery().from(cat, cat   2).orderBy(casex.desc()).    s            elect(    cat.  id  , ca  t2.id).fetch()  ;
         }

  @Test
  public     voi    d cast() {
      List<Cat> cats = query    ().f r    om(c  at).select(cat)       .fetch();
    List<Integer> w        eights     =
        query().from (cat).selec   t(cat. bodyWeight .c  astT     oNum(Integer     .clas    s)).fetch()   ;
    for (int i         = 0; i <      cats.size(); i++) {
      asse         rtTh  a     t(weights   .get(i)).isEqualTo(I  nt     eger.valueOf((in   t) (cat  s.g et(  i).      getBodyWeigh   t())));
       }
  }     

  @Test
  @     Excl udeIn(SQLSER  VE R)
  pub  lic voi   d cast_toString(   ) {
              for (Tu pl  e tuple     :    query().fro      m   (cat        ).selec         t(ca t.breed, cat.br             eed.st ringVa  lu e()).fetch()) {
         assertTh   at   (tu          ple.get(cat.breed.stringVal  ue() )).isEqualTo(tuple.ge    t(cat.breed).to           String ());
        }
      }

  @Test
  @E    xcl    udeIn(S     QLSE      RVER)
  public void cast_toString_append() {
    for (T   up  le tuple :
           qu  ery().fro  m(cat).select(ca       t.bre ed, cat.breed.s   tringValue().app   end("test")).fetch()) {
         as   se   rtThat(tupl   e.get(c   at.breed.strin  gVa    lue().append("test"))) 
          .isEqualTo(tuple.get(cat.breed).toString() + "t   est");
    }
       }

  @Test
  public        void         collecti  on_predic ates() {
    ListPath<Cat, QCat> path = cat.kitten s ;
         /     /              path.eq(s   avedC    at   s),
       //            pa      th.in(savedCa    ts),
          //                path   .isNot     Nu   ll(),
    //            path.isNull(),
    //                 path.n              e(savedCats),
     //                 path     .notIn(sa    vedCats)
         /  /                    path.when(other)
    L   ist<Pre    di  cate> pr  edi cat        e s = Collections.empty    Lis t()            ;
          fo   r (      Predic       ate pred : pr     edicates) {
      Sy stem.err.   p    rintln(pred);
           que    ry()  .from(cat    ).w   here(pred).select(c   at).fetch() ;
        }
     }

  @Test
  public void colle  ct     io       n_pro       jections()         {
    L           istPath<Ca           t,         QCat> pa   th = c  at.ki     ttens;
    //                       pat     h.fetchCount(),
                 //               path.countD     i    s  tinct()
         List<Expression<  ?>> p     rojection  s = Collections.emptyList()   ;
    for    (Ex pr    ession<?> p roj : projections) {
          Syst           em.err.println(proj)  ;
      query().from(             cat).select(proj).fetch();
       }
  }

  @Test
  public void constant() {
    /     / select cat.id, ?               1 as const from       Cat cat
    Li st<Cat> cat     s = query   ().from(cat).select(ca     t).fetch();
                    Path<String> path =     Ex     pressions.stringPath("const")   ;
       List<Tu    ple> tupl             es =
           query().from(cat).select(cat.id,        Exp           ressions.constantAs(  "a     bc", path)).fetch();
    for (int   i = 0; i    <        cats.siz e(); i++) {
        asse   rtThat(tu  ples.ge t(i).     get(cat.i d)).isEqualTo(Integer.v alueOf(cats.get(i).g  etId() ));
      a     ssertThat(tuples.g  et(i).get(path)).isEqualTo("abc");
    }
       }

  @Test
  p        ublic   vo           id  constan  t2() {
    a  ssertThat      (qu    ery().fr      om(cat).select(cat.id, Expre         ss   io   ns.constant("n        ame")).fet  ch()).isNotEmpty();
        }

    @      T   est
  p   ublic void con  stru  ctorProjec             tion() {
    List<    Projection> projections =
                    query().from    (cat).select(Projections.const   ruc   tor(Pro    jectio     n     .class, cat.   name,            c at))      .fe  tch();
    assertThat(pr  oject          ions).isNotEm   pt   y()     ;
    fo  r (Projection proj    ec    tion           : pro    jections) {
      as sertTha   t     (projectio  n).i   sN   o   tN    ull();
       }
  }

  @Test
  pu    blic v      oid constructorProjection2() { 
    List <Projec tion>        p r  oje    ctions = query().f      rom(cat).sele   ct(new     QProjecti  on(cat.name, c        at      )).fetc  h();
    assertThat(projections).isN  otEmpty();
       for (P               ro     jection proje     ction  : p    rojectio    ns) {
      assertThat(projectio n).isNotNu    ll()    ;
    }    
  }

     @Test
  public void constr    uctorProjection3() {
    List<Projection> projec tions =
            q u    ery().from(c    at).      se       lect  (new QProjection(ca t.    id, Express ions. FAL  SE)).   fe    t    ch();
             ass     ertTha            t(projecti  ons).isN        otEm          pty();
          for (Proje     ction    p   ro   jection :    pr  ojections) {
              assertT   h   at(projec       tio n).isN   otNull()        ;
    }
    }

                     @T    es t
  public void    contai ns_ic() {
    Q   F    oo   fo o      = QFo   o.foo; 
    assertTh  at(   quer   y()     .from(foo).where(foo.bar.containsIgnor     e   Case("MÃ¼nchen")).fetc   hCount())
           .isEqualTo(      1) ;
  }     

  @Tes              t
              publ ic void contains1() {
    assertThat(query  (    ).from(cat).where  (cat.name.co      ntains("eli    ")).fetchCount()).isEq   ualTo(1);
  }

  @  Test
  pub   lic void   co   ntai     ns2() {
    as  sertTha            t(   query().from(cat).wher    e(cat.kittens.con tains(savedCats.ge    tFir     s t())).fet    chCou    nt())
             .isEqualT  o(1 L);
  }

  @Test
  public     void con     tains3   () {
    assertThat(query().from(cat).where(cat.name.cont             ains("_")).fetc   hCount()).isEqualTo(1L);
    }

     @Test
  public void co  nt ains4() {
         QEmployee emplo      yee =  QE mploy  ee .employee;
    assert That(
                qu    ery()
                    .from(emp       loyee)
                .where(
                                          employee.jobF    unctio        ns.co      nta      i       ns(JobFunc    tion.    C    O    DER),
                              employee.jobFunctions.contains(Job Function.CONSUL       TANT),
                                employ    ee.j   obFunctions.size().     eq(2))            
                          .sele    ct(employe       e)
                  .fetch             ())
            .isEqualTo(C         ollectio ns.emptyLi         st());
  }

  @Test
  public void count() {
     QShow   show  = Q  Sho     w           .show;
    a  ss ertThat(q       uery().from(show).fetc   hCo   u            nt() > 0).isTrue     ();
  }

                    @Te st
  publ     ic void count_distinct () {
       QC at    cat = QCat.cat;
    query().from(cat).gro    upBy(cat.    id).select(cat.id, cat.breed.count     Distinct       ()).fet ch();
  }

      @Te   st
       @NoBatoo   JPA
  @NoHi    bernate
      p  ublic void count_d  isti   n c  t2() {
    QCat cat = QCat.cat;
    query()
        . from(cat)
        .groupBy(cat.id)
             .    select(cat.id, cat.birthdate. d ayOf    Month().countDistin  ct())
        .fetch();
  }

  @Test
  @N oEclipseLink
  @Exclu  deIn(SQLSERVE  R     )
  publi    c voi d    distinct_orderB  y() {
                  QCat cat = QCat. cat;
       List<Tup   le> r   esult        =
                qu     ery(   )
                .select(cat.id ,               cat.mate.id)
                      .dis tinct   ()
            .f      r          om(cat)
                               .orderBy(cat.ma   t e    .id.asc().nullsFi   rst(),          cat.id.asc().              nu      llsFirst())
               .fetch( )    ;
       a    ssertThat(result)
                   .containsExac    t ly(
                    ne    w MockT                uple   (n  ew Object[] {1  , null}),
            new MockTu   pl    e(new Object[]     {6, null}),
                 new   MockTuple(ne     w           Object[] {2           , 1}),  
                             new MockTupl     e(new Object[] {3, 2}),
                  new MockTuple(new O    bject  [] {4, 3}),
              ne        w MockTuple(new O  bject[] {  5,    4}));
  }

  @Test
  @NoHibern      ate
  @Exc  lud eI   n    (MYSQ    L )
  pub                 lic void    d   istinct_order  By2() {
    Q          Cat cat = QCat    .cat      ;
    List<Tuple>         result =     
              quer   y(     )  
                          .   select(cat.id, cat.mate.id  )
            .distinct()
                  .from(cat)
            .ord      erBy(cat.mate.          id.   as  c().nul  lsFirs t())
            .fetch   ();
       assertThat(result)
            .conta   insExactl    y(
                new MockTuple(new Object      [] {2, 1}),
                 new    MockTuple(new Obj       ect[] {3, 2}),
            new MockTup    le(new Obj  ect[  ] {4, 3}),
               new Moc    kTupl  e(ne    w Obj   ect[] {5, 4        })   );
  }

  @Test
     @  N  oE     clipseLin     k(HS   QLDB)
     p    ub       lic v  oid count_dis          tinct3() {   
    Q    Cat ki tt en =     new QC at("kitten");
    assertTh  at(
              que     ry()
                      .from(cat)
                     .le    ftJoin(cat.   kitte    ns,       kitten           )
                     .select(k   itten.coun t   Dis  tinct())
                       .fetc   hOne()
                .intValue())
        .isEqualTo(4);
    asse   rtTh       at(
                 query()
                          .fro m(cat)
                .leftJo in(cat.k  ittens              , kitten)
                     .select(k    itten.c   ountDistinct()   )
                   .f     etchCount())
               .isEqualTo(6);   
  }

   @Test
  p         ublic      v     o   id dis   tinctResults() {
    System.out.println("-- fetch resu lts");  
    QueryRe su lt   s <Dat    e> res = qu     e   ry().from(cat).li      mit(2).select(cat.birth date).fetc hResul t s();   
    assertThat(r     es.getResul ts()).    h      asSize(2);
           assertT hat(re     s.getTotal()  ).i    s   EqualTo(6L)   ;
               System     . o    ut.print   ln         ();

    System.o  ut.println("-- fetch distin    ct   r   esults");
         res = query().from(cat).    limi t(2).distinc   t().select(cat.birthdate)     .fetc   h   Results(    );
    assertT hat(res.g  e tResults(                    )).h asSiz   e(1);
    assertThat(res.g  e     tTota   l()).isEqual    To (1L)   ;
           System.out.   println();

       System.out.println(     "-- fetc     h distinct");
    assertThat(query().from(cat)  .distin  ct().sele   ct(  cat.bir    thdat   e).  fetch()).has   Size(1)       ;
  }

  @Test
       p     u    blic     void dat   e()  {     
    a s           se  rtThat(query().from(cat   ).se     le   ct(cat.birthdate.year())  .fetchFirs   t().intValue())
        .isEqualTo(2000);
    assertThat(quer  y().f rom(cat).select(cat.  b    irthdate.  yearMonth()).fetchFirst().intVal         ue())
                 .isEqualTo(200002)   ;
    asse  rtThat(q  uery().fr   om(cat).select(            cat.birthdate.month()).fetchFirst(     ).intV   alue()  )
        .isEq   ualTo(2);
      assertThat(query().from(cat   ).select(cat.bi   rthdate.dayOfMonth()).fetchFirst().intValue())  
            .isEqua lTo(2);
    assertThat(qu     ery().from(ca   t).select(cat.birthdate.hour()).fet   chFirst(  ).intValue      ()).isEqu   al       To(3)     ;
    assertTh      at(query(  ).from     (   cat).select(ca    t.birthdate.mi    nute()).fetchFirst()   .  intValue())
        .i     s    E     qu    al     To(4);
       assertThat(quer   y().fr      om(cat).s        elect(cat.birthd   ate.se    cond()).fetchFirst().    intValue())
           .isEqualTo(0);
   }

  @Te             st
  @NoEclipseLink({DERBY, HSQLDB})
  @No      Hibernate({DERBY, POSTGR        ESQ  L, S  QL SERVER})
  public void   date_ye   arWeek  ()   {
    int value = query().from(cat).selec   t(cat.birthdate.yearWeek()).fetchF  irst();
    asser   tThat(value     == 20000   6 || value == 200005).isT  rue();
     }

  @Test
  @NoEclipseLink({D  ERBY, HSQLDB})
        @NoHibernate({DERBY, P    OSTGRESQL, SQLSERVE   R})
      public void dat     e_w  eek() {
      int va lue  = query().fro m(cat).sel        ect  (cat.birth  da te.week()).fetchFirs   t    ();  
    ass    er    t      That(v  alue == 6 || va     lue       ==     5)   .isTrue();
    }

  @Test
     @ExcludeI n(ORACLE)
  publ    ic v           oid divi    de() { 
        QSimpleTypes    en  tit   y = new QSimpleTypes("entity1");
          QSimpleTypes entity    2   = new    QSimpleType  s("entity2");

    assertThat(
            query()
                      .from(entity, entity2)
                          .wh           e   re(enti     ty.ddouble.divide(  entity2.d  double).loe(2.0  ))
                .select(entity)
                .fetch(  ))          
          .isEqua lTo(Co    llec  tions.emp tyLi      st        ());

       asse  rtThat(
                 que    ry   ()   
                .fro   m(entity,       entity2)
                        .where(   en     tity     .d    double.divide(e nti    ty2.iint).loe(2.0))
                .select(entity  )
                    .fetch())
            .isEqualTo(Coll ections.empty             Li st())     ;

    assertThat(
            query()
                                                 .from(entit  y, e       ntity2)
                   .whe   re(entity.iint.div    ide(    e      ntity2.ddouble). loe(2.0))     
                        .selec     t    (entity)
                      .fetch())
            .isEqualTo(Colle  cti     ons.empt    yList());
 
        ass  ert    Tha        t(
            query       ()
                       .from(e       ntity, entity2)
                      .where(entity.iint.div       ide(entity 2.    iint).loe( 2))
                .     select(e   ntit          y)
                           .fetch())
                   .isEqualTo(Col   lections.emp  ty    L     ist());
  }

  @Test
  @E      xcludeIn(OR  ACLE)
        pub    lic void divide_bigDec  i mal    () {
        QSi  m   p  leTypes entity = new QSimp  leTypes("entity1");
    QSimpleTy   pes entit y2 = new    QSimp   leT       ypes("en    tity2");
      Number   Path<BigDecimal> big    d1 = entity.bigDec    imal;
                  NumberPath<Bi    gDecimal> bigd2 =      entity2.b  igDecimal;  

    assertThat  (
                q    uery()
                .from(enti       ty, entity2)   
                .wher   e(  big    d1.di     vide(bigd2).loe(new    BigDecimal("1.00")))         
                  .sele ct(entity)
                      .   fet        ch     ())
           .isEqua     lTo(Co  llections.       emptyList());

    assertThat(   
                        query()
                  .from(entity, entity2)
                .where(entity.d   doub    le.d   ivide(b    i    gd2).loe(new B   igDec     ima  l("1.00"    )))
                      .s elect(     ent     ity)
                         .f      etch())
            .isEqualTo(Colle  ctions.emptyList());

    assert     Tha   t(
              query()
                      .f    rom(entity,        e   ntity2      )
                   .where(bigd1.divid    e(entity.ddo     uble).loe(new BigDecimal("1.00")))
                  .select(en    tity)
                    .   f  etch())
         .isEqu  alTo(Collections   .  em  ptyLis   t());
  }

  @Test
  public void e ndsW  it  h() {
    assertThat(     query().fr om(cat).w   he  re(cat.name.en   dsWith("h123")).fetchCount()).isEqua      lTo(1);
   }

      @T     est
  public void endsWi     th_ignoreCase  ()   {
        assertThat(query().fr om(c    at).where(cat.   name.  endsWi   thIgnoreCa    se("H123"   ))    .fe          tchCo un   t()) 
        .isEqua     lTo(1);
       }

  @Test
  public void ends      With2() {
    a  ssertThat(query().from (ca     t).where     (cat.name.endsWith("X")).fetch      Count()).      isEqualTo(0);
  }

  @Test
     public v   o     id endsWith3() {
    assertThat(qu  ery().f  rom(c    at).where(cat.   name.endsWith("_123"   )).fetchCount(  )).isE      qualTo(1)         ; 
  }

  @Test
  @NoBa tooJPA
  public void enum_eq() {       
    asser   tThat(query().fro          m(company).w here( comp   a  ny.ratingOrdinal.eq(Rati  ng.A)).fetchCount())
            .isEqualTo(     1);
    asse  rtTha t    (query().from(company).       where(   comp   any  . ratingString.eq(Rating.AA)).fetchCount())
                   .isEqua  lTo(1    );
  }

  @Test 
    @NoB     ato   oJPA
  public void enum_ in()     {
           assertThat(
               query().from(com  pany)     .where(company.ratingOr      di nal.in    (Rat  ing .   A, Rat  ing.AA)).fetc  hCount())
        .isEqualTo(1);
    as sertTha   t(
                query().from(company).where(c    ompa ny.rating    Str      ing.in(Rating.A, Rating.AA)).fetchC ount())
        .isEqualTo(1);
            } 

  @T est    
  @NoBato      oJPA
  p  ublic void enum_in2() {
        QEmployee em   ployee =   Q      Employee   .employ   ee;

    JPQLQuery<?>    quer  y   = query();     
    query
        .fro m(e   mployee)
          .where(employee.lastNa  me.eq("Smith")   ,     emp        l oyee.jobFunctions.c ontains(J   obFunct       ion .CODER));
      as       sertThat(query.     fetchCount())   .isEqu        alTo(1L); 
  }

  @Test
            @ExcludeI  n(SQLSERVER)
  pu    blic void e   num_st   artsWith(   ) {
        a   ssertTh     at(
            query()
                .from(company)
                    .wher e(company  .ratingS    tring.stringValue() .startsWith(   "    A"))
                .fetc  hCount     ())
        .i   sE  qualTo(1);
  }

  @Test
  @NoE     cli  pseLi   nk(HSQLDB)
  public void fact  oryExpressions() {
        QCa t cat = QCat.c at;
    QCat cat   2 = new QCat("cat2");
    QC at     kitten = new  QCat("k    itten");
    JPQLQu      ery<Tuple> query =
         query()
                     .from   (cat)
               .leftJoin(cat.mate, cat2)
                      .leftJoin(cat2.kitt    en      s, kitten)
              . sel   ect(Pro   j   e     cti  ons.tuple(c       at.id, new QFamily(cat, cat2, kitten).skipNul    ls())  )     ;
    asse  rtThat        (que     ry.fetch()).has  Size(6);
    assertThat(query.limit(1).fetchOne(   )).isN  otNull(  );
    }

  @Test
  @NoE clipseLink
    @No      OpenJPA
        @NoBat         ooJPA
  pub   lic vo     id fetch()      {
    QMammal mammal = Q    Mammal.mam  mal;
       QHuman human =     new QHuman("mammal");
           query()  .from(mam       mal).leftJoin(human   .hairs).fetc   hJoin().select(     mammal).fet   ch();
       }

  @Test
    @NoEclipseLink
     @NoOpen     JPA
  @NoBatoo   JPA
      publi      c v   o    i       d fetch2()   {
    QWorld       world = QWorld.wo  rld;
    QMammal            mammal = QMammal.mammal;      
       QHuman human = new          QHuman("mamm  al");
        qu   ery  ()
               .from(world)
          .leftJoin(worl d.mammals, ma  m     mal    )
              .fetchJoin()
        .le     ftJoin(human.hairs)
                      .fetchJoin()
              .sele ct(world)
             .fetch();
  }

     @Tes  t
     @ExcludeIn({MY          SQL, DERBY})  
  @NoB   at ooJPA
  public void groupBy() {
    QAutho r au          thor =    QAuthor.   author;
    QBook book = QBoo   k.book;

      for (int i = 0; i < 10; i++) {
       Author a = new Author();
       a.setNam    e(String.valueOf(i));
           save(     a);
      for (int j = 0; j < 2; j++) {
        Book b = new Book();
        b  .setTitle(String.valueO   f(i) + "       " + Str  ing.valueOf           (j));
           b.se  tAuthor(a);
          save(b);
       }
    }

              Map<L    ong,   List<   P   air<L ong, String>>>    map =
        query()
            .fro m(  author)
            .join(author.book   s, book  )
                   .transform  (
                    Gro    upBy.groupBy(author.id). a   s(GroupBy.list(QPair    .c  reate    (bo  ok.id   , book.ti tle)) ));

    for (Entr      y    <Long,    List<Pa  ir<Long, String>>>     entry : map.entrySet()) {
      System.out.println("autho        r = " + entry.getKey());

        for ( Pair<Long, String> pair                   : entry.get              Value())      {
              Sy  stem.ou  t.println("  book = " + pai    r  .g  etFirst() + "," + p  ai   r.getSec     ond());
      }
    }
          }

     @T     est
          public  void groupBy2() {
        //         select        cat0_.name as col_0_0_, ca      t0_.breed as col_1_0_, sum(cat0_.bodyWeight)      as
     // col_2_0_
    /  /          from animal_ c   at0_ where cat0_.   DTYPE in ('C', 'DC')     and c at0_.body    Weight            >?
      //          group by   cat0_.name, cat0_.breed
    query()
        .from(c   at)
        .where(cat   .body    Weight.gt(0))
        .groupBy(c    at.nam e,     c    at.breed)
        .select     (cat.    name, cat.breed, cat.bodyW   eig  ht.  sum   Double())
        .fet     ch  ();
  }

  @Test
     @N    oEclipseLink
  publ   ic void gro   u pBy_ye    a  rMonth() {
    query()    
        .fr    om(cat   )
            .groupBy(cat.birthdate.ye   arMonth())
                  .orderBy(cat.birthdate.yearMonth().asc())
             .sel    e          ct(  c  at.id.count())
               .fetch();
  }
      
      @Test
       @Ignore // FIXME
  public vo        id     groupBy_count     ()   {
    List<Intege  r> ids = query().from(cat      ).gro      upBy(c               at.id).select(cat.id).fetc     h();
    long   count = quer  y().  from( cat).groupBy(ca   t.id).f         etchC ount ();
    Qu   er   yRe   s   ults<In   teger> resu    lts =
               query().from(              cat).groupBy(cat.i   d)     .limit(1)     .sele  ct(cat   .id).fetchResult             s(     )  ;

           lon  g catCount =  query()   .from(cat).fetchCount  () ;
                assertThat(ids)      .ha    s      Siz          e((int) catCount);
      assertThat(coun t).isEqualTo(cat Count)     ;
            assertT  hat(results.getResults()).hasSize((int)        cat    Count);
    assertThat(result        s.   getTotal()).isEqualTo(c   atCount );
  }

  @Test
  @Ignore // FIXME
  public     void groupBy_di stin  ct      _count() {
           List<Integ     er> id   s =
        que      ry(    ).from(cat).g rou   pBy(cat.id)    .distinct().select(Expression    s.ONE).  fetch();
    QueryResults<Integer> re    sults   =
        query()
               .from(cat)
                 .groupBy(cat.id)
            .limit(1)
                     .disti  nct()
            .select(Expressions.          ONE)
               .fetchRe     sults();

          assertThat(ids).hasSize(1);
         assertTh    at(result     s.getResults(  )).hasSize(1);
    assertThat(results.getTotal()).isEqualTo(1    );
    }

     @Test
    @NoHibernate // https: //h ibern   at   e.atlassian.net/  browse/   HHH-1902
  public void groupBy_select(    )       {
    // s     elect leng      th(my_colu   mn) as colum     n_size from my_table group     by column_size  
    NumberP ath<Integer>    length = E       xpre   ssions.numberP  ath(Integer.class, "len");      
    a   ssertThat(
            query()    
                          .  select(cat.name.length().as(length          ))
                  .from(cat)
                         .orderB       y(length.asc())
                    .   gr oupBy(length)
                                  .fetch ())
        .i      sEqualTo   (Array  s.asList(4, 6, 7, 8));
  }

  @T  est
  public void gro      upBy_results() {
    QueryResults<     In t     eg             e           r> re  sults = query().from(cat  ).groupBy(cat.id).select(cat.id).fetchResults() ;
     assert   That(r  esults.get Total()).isEqual  To(6);
    a  sser             tThat(re              sults.getResul  ts()).hasS     ize(6);
  }

  @Tes  t
  public void groupBy_results2() {
    Qu   eryRe      s              ul   ts<In    teger     > re su   lts =
         query().fr   om(cat).groupBy(cat.bi  rt    hdate   ).select(cat.id  .max   ()).fetchResults(          ) ;
    asser     tTh   at(results.g   etT         o    tal()).isEqu     alTo(1);
             as  se   rtThat(results. get  Results()).hasS     ize(1);
  }

     @Test
  public voi     d   in() {
    assertThat         (query(    )      .from(cat).where(cat.na  me.        in    ("Bo   b123", "R   uth123", "Fe    lix123")).fetchCount())
        .isEqualT    o(3     L);
    assertThat(query().from(cat).where(cat.id.in(      A        rray  s    .asLi     st(1   , 2, 3)      ))      .fe   tch   Count()) 
           .isEqualTo(3L);
    assert T   hat(query()        .from(          cat).wher      e(cat.na  me   .in(Arrays.asList  ("A", "B      ",  "C"))).fetchCount())
          .isEqualTo(0L);
  }

     @Test
  public void in2()   {
            a           ssertThat(query().fr  om(cat).where        (c              at.id.in(1, 2, 3)).fetchCount())    .isEqualTo(3L);
    ass      er      tThat  (query().from(cat).where(ca  t.name.in("A", "B     ", "C"))      .fetchCount()).isE qu       a       lT o(0L);
  }      

  @Test
   public void in3()     {
          assertThat( query().from(cat   ).where(cat.name.in("A,   B,C".split("         ,"))).fetchC   ount()).isEq    u      alTo(0);
   }

  @Test
  p    ublic void in4() {
    /   / $     .parameterRelease.id.eq(releaseId).and($.par   ameter  Grou     ps.any().id.in(filter   .getGroups()));
    assertThat(
                          query()
                           .f     rom(  c at)
                .where(cat.  id.eq(1), cat.kitt          ens.any().id .in(1, 2, 3    ))
                 .select(cat)
                        .fetch())
        .isEqualTo(Coll ections.em           ptyList());
  }      

     @Test
       public vo     id in  5() {
    as sertThat   (q          uery().f rom(cat).where(    cat.mate.in(sa   vedCats)   ).fetchCount()).is  EqualTo(4L);
  }

  @Test
  @Ignore
           publ  ic    void in6() {
         // query().from(cat   ).wher e(cat.kittens.in(save  dCats)).fetchCo     unt();
    }

  @Test
  public void in7() {
    assertThat(query().fr    o m(cat).where(c   at.kittens.a ny   () .in(savedCats)).fetch Count()).    isEqualTo(4L);
  }

         @T  est
    publ            ic v     oid in_empty() {
         assertThat(query().from(cat).where(cat.name.in  (Collections.e    mptyL ist()     )  ).fetchCount())       
                      .is       EqualTo(0);
       }
     
  @Test
  @NoOpenJPA
  public void indexO f() {
     assertThat(
                que   ry()
                                   .from(cat    )
                      .where(ca    t.name        .eq("Bo  b1  23"))
                      .se          lect(ca  t  .nam    e      .indexO     f("B"))
                     .fetchFirst())
        .         isEqualTo(Integer.valueOf(0));
  }

  @Test
  @N  oOpen  JPA
  public vo      id indexOf2() {
    assertThat(
               query()
                    .fro    m(cat)
                       . whe    re(    cat.name.eq("Bob123"))
                    .select(cat.name.        indexOf("o"))
                            .fetchFirst())
          .isEq  ualT   o(Integer.valu      eO     f(1));
  }

     @Test
  pu   blic void ins   tanceOf_cat    () {
    assertThat(quer    y    ().from(cat).where(cat.instanceOf(   Cat.c   lass)).fetchCount()      ).is  EqualTo(6L);
  }

  @Te st
  publ  ic void      instanceOf_domes   ticCa   t() {
    assertT   h   at(que   ry().   fro  m(ca         t)   .whe  re(cat.instanceOf(DomesticCat.cla   ss)).fetchCo  unt())
             .isEqualTo(0L  );
        }

    @    Test
  publi c v  oid ins  ta   nceOf_entity1() {
    QEntity1 entity1 = Q      Entity1.e ntity1   ;
       assertThat(query().from(entity1).where    (entity1.instanceOf(Entity1.class)).fe   tc      hCou    nt())
        .isEqualTo(2L);
  }
  
  @ Test
     p       ublic void insta    n ceOf_entity2() {
    QEntity1 entity1 = QE     nt   ity1.entity1;
    assertThat(query().   from(en   t  ity1).where(e   n     ti    ty1    .in stanceOf(     Entity2.cl  ass)).fetchCount())      
            .isEqualTo(1L );
     }

  @T    est
  @N    oHibernate     // https://hi  berna   te.atlas   sian.net/brows            e    /HHH-6686 
  publi   c void isE    mpty_elemen            tCollection() {
    QEmpl      oye          e em ploye      e = QEmployee.emp  loyee;
    as  sertThat(query().from(employee).whe       re(   employee.jobFunctions.isEmpty()) .fetchCo unt())
             .isEqualTo(0 );
  }
       
  @        Test
  publ ic vo          i    d isEmpty_relation() {
    assertTh   at   (query() .from(cat).whe         re(cat.kittensSet.isEmpty()).fetchCount()).i   sEqualT  o(6L);
    }

  @T    est
  @NoEc  lipseLink
  @Exclud      eIn({ORACLE, TERADATA})      
   public  void joinEmbeddab   le()  {  
    QBookVersion bookVe     rsi on =     QBookV         ersion.bookVersion;
    QBook     M       ark boo     kMark = QBookMark.bookMar        k;

    assertThat(
               query()
                .from(bookVe     rsion)
                  .join(bookVersio    n.   definition.boo   kMar      ks,    bookMark)
                .where(
                             bookVer     sion  .definition.bookMarks.size().eq(1),
                       boo   kMa  rk.page.eq(2357L).or(bo   okMark      .page.eq(2356          L)))
                                .     sel     ect(bookVersion)
                          .fetch())
        .isEqualTo(C ollect        ions.   emptyLis       t() );  
       }

  @Tes   t
  public v  oi    d length() {
    assertThat(query  ().from(    c at).  where(cat   .name.l        ength().gt(0   ))  .fetchCount()).isEqualTo     (6);
  }

  @Test
  publi  c void like() {
     assertThat(query().from                (cat).where(cat        .na   me.like(  "!")).fetchCount()).is    EqualTo(0);
    assertT hat(query()   .fr   om(cat          ).where(ca    t.name. like(         "\\")).        fe      tchCount()).isEqualTo(0       );
  }

  @Tes      t
  p   u   blic void limit() {
      List<String> names1 = Array  s.asList("Allen123", "Bob1  23");   
    assertThat(quer   y().from(cat).o   rderBy(   ca   t.name.asc()).li  mit(2).      select(ca     t   .n     a    me).fetch())
        .isEqu   a    lTo(na           mes1);
  }

  @Test
  public      void limi    t     _an d_offset() {
         List<String> names3 = Arrays.asList("Fe    lix123",   "Mary_123");
         assertTha t(
                         qu      ery().from(c  at).orderBy  (cat.name.asc()).limit(2).    offset(2).select(cat.n    am   e).fe  tch())
            .isEqu alTo(names3)    ;
     }

  @Test
          public voi     d limi t 2() {
    assertThat(query().from(cat).ord er  By(cat.na    me.asc()).   limit(1).select(    cat.name).fetch())        
           .   isEqualT    o (Col lections.singletonList("Allen123"));
  }

  @Test
         public   vo id    limi   t3() {
    asser  tTh           at(query().fro  m(cat).li    m     it(Long.MAX_VAL     UE).se lec  t(cat).f   etch()).hasS    ize(6);
     }

  @Test
  publ      ic void lis      t_elementCo  llecti   on_of_enum() {
         QEmployee employee = Q     Employee.emp     loy   ee;
           // QJobF uncti     on jo     bFunction =     QJ   obFunction.jobFunction     ;
        EnumPat    h<   JobF unct     io  n>  jobFun    ction = Expressions   .enumPath(JobFunc tion.class, "j  f");

        List<    JobFunctio     n> jobFunc    tions =
               que    r    y   (        )   
                  .from(employee)
            .innerJoin(employee.jobFunctions     , job   Function)
            .     sel    ect(jobFunc   tion)
                           .fetch();
    assertTha      t(jo  b        Functions).hasSize(4);
     }

  @Test
  @NoBatooJPA
     pu     blic void     list_elementCollection _of_string()     {
    QF  oo foo = QFoo  .foo;
      String      Path str = Expressions   .stri     ng  Pat  h("str"); 

     List<String> strings = q      uery().from(foo).   innerJ            oin(foo.nam   es, str).s  elect(st  r).fetch(    );
    assertThat  (strings       ).hasSize(2);
       as  sertThat(strings)        .  c     ontains("a");
    as    s    ertThat(     stri    ngs).contains("b");       
  }

  @ Test
  @NoEclip     se  Link(HSQLD   B)
  public vo  id list    _ord  e      r_get() {
       QCat cat = QCat.cat;
       ass  ertTh       at(query().from(cat).or   derBy(cat.kittens.get(0).name.asc()).fetch()).h      asSiz      e(6);
  }

  @Te    s      t
     @NoEc       l   ipseLin  k(HSQLDB)
  public v    oid list_order_get2(   )   {
    QCat cat = Q      Cat.cat;
      assert   That(query(). from(cat)  .orderBy(cat.mate.kittens.get(0).name.asc()).  fetch()).hasSize(6);
       }

  @Test
  public v  oid ma   p_get  ()    {
     QShow show        = QShow.show;
    asse     rt          That(quer     y().from  (show).select(show.acts.get("a")).fetch     ())
        .isEqualT  o(Collectio  ns.    singletonList("A"));
  }

  @Test
  @NoHiber     nate
  public void map_get2() {
    QSho   w s   how = QShow.show;
       a   ssertThat(          query(              ).from    (show).where(show.acts.   get("a").eq("A")   ).fetchCount()).isEqualTo(1);
  }

   @Test
     @NoEclipseLink
  pu  blic void map_order_get() {
       QShow show = QShow.show;   
      a  ssertThat(query().from(sh    ow)       .orderBy(show.pare      nt.a cts.get("A").asc()).fetch()).hasSi ze(1);
  }

  @Test
  @      N   oEcl ipseL ink
    public    void map   _order_get2() {
    QS    how show = QShow.sh  o w;
      QShow parent = new QShow("parent"  );
             assertThat(
                 que  ry   ()
                .from(sho   w    )    
                          .    leftJoin(show  .parent, parent)   
                         .       orderB    y(paren   t.acts.get   ("A").asc())
                         .fetch())
                 .has      Size(       1);
  }

      @     Tes             t
  pub   lic void map  _containsKey() {
    QShow       show = QShow.show;
    assertTha    t     ( query().from(s  how).where(sh     ow      .acts.containsKey("a")).fetc    hCo    unt()).isEqualTo  (1L);
        }

  @Test
  publ   ic void map_contains   Key2() {
    QShow show = QShow.sh     ow;
             assert    That(query(). from(show).   where   (    show.a    cts.     containsKey("b")).fetchCount(   )).   isEq  ualTo   (1L);       
     }

  @Test
  publi   c void map          _co      ntainsKey3(   ) {        
       QShow s              how    = Q    Show.show;
          a   ssertThat(query(    ).from(show).wh   ere(show.a    cts.containsKey(" c")).fetchC   ount(  )   ).is    EqualTo(0 L);
  }

   @Test
  public void map_cont  ain  sValue() {
    QShow show = QShow.sho w      ;
    assertThat(query(     ).from(show).where(show.acts.containsVa     lue("A")).fetchCou  nt())             .isE     qual    To(       1     L);
     }

  @Test
  pu                         bli  c v         oid map  _containsValue2() {
    QShow show   = QShow.show;
       ass  ert T       hat(query().f        rom(show).w    here(show.a   cts.c    ont   ainsValue("B")).f    etchCount()).isEqu  alTo(1L)     ;
  }        
  
  @Test
  public void map_containsValue3() {
    QShow s     how = QShow.show;
    assertThat(query().from(sho  w)   .where(s              how.ac        ts.c  on    tainsValue("C")).fetc        hCount      ()).isEq ualT       o(0L);
  }

  @Test
  public void map _c        ontain          s() {
             QSho   w show      = QS    how  .show;
    assert   That(query().fro    m(show).wh  ere(show.acts   .contains("a",   "       A")).fet   chCount()   )    .isEqualTo(1L);
    assertThat(query          ().from        (show).where  (show.a cts.con  tains ("X    ", "X")).fetchCount()). isEq ualTo(     0L);
  }

  @Test
    publi c v           oi                  d map_groupB y() {
      Q  Show show =         QSho    w.s    how      ;
    asse               rtThat( 
             q       uery().from(show   ).select(  show.acts.ge t("X")).groupBy(s         how.acts.get("a")).fe    tchCount())
        .isE qualTo(1);
  }

  @T est
  @Ignore
       public voi       d map_j  oin()    {
       /  / selec   t m.te          xt     f   rom Show   s      join  s.acts      a        wher    e    key(a) = '   B'
    Q   Show show = QShow.show;
    S tri  ng Path act = Exp            ressions.s   t            ringPath("act");   
    assert    That(query(    ).fro     m(show    ).join(show.acts , act).       select(  a   ct).f etch  ())
           .isEqualTo(Collections  .emptyList());
         }
  
  @Test
  publ   ic void max(         ) {
    assertThat(qu    ery().from(      c at).se lect(ca  t  .bodyWeight.m ax()).fetchFir            st())
        .isC  loseTo(6.0, wi  thin(0.0001));
  }

  @Test
  public void     min() {
       ass  ert           Tha     t(query(     ).from(ca    t ).s   elect(cat.bodyWeight.min()).fetch  First())
        .isCloseTo  (1  .0, within(0.0001));
  }

  @Test
  @ExcludeIn(ORACLE)
   p    ublic void     mult   iply() {
    QSimpleTypes   entity      = new QSimpleTypes("entity1");
      QSim           pleTy  pes enti  ty2 = new QSimpleTypes("entity2");

       ass  e   r   tT  h        at(
              query()
                       .   from(entity,       entity2)
                    .wh      ere( enti   ty .ddouble.multi   ply(entity2.ddoubl    e   ).loe(2.  0))
                .select(e  ntity) 
                .fetc   h()) 
                 .isEqualTo(Coll  ections.empt   yLi      s       t());
  }

            @Test
  @Exc    ludeIn(ORACLE)
  pu  blic void multiply_bigDecimal() {
          QSimpleTypes     entity   = ne w      QSimpl    eTypes    ("e      ntity1");
    QSim    pleTypes    ent    ity2 =     new   QSi          mpleTypes("entity2");
      NumberPath<BigDeci   mal>     bigd     1 = enti      ty.bigDecimal;  
    N     umber  Path<Big      Decima    l>     bigd2 = entity2  .bigDecimal;

    assertThat(
            q                    uery()
                           . from(entity, enti     ty2)
                .w     here(bigd1.multiply   (bi     gd2).loe (n ew BigDec   imal("1.0    0")))
                   .s            elect(entity    )
                .fetch())
        .isEq   ualTo(Collecti   on        s.emptyList());
  }

  @Test
   public    vo     id nestedProjection(   )       {
    C  oncat      enation concat = ne   w          Conca      te   nation(cat  .       name, cat. name);
       List<T    up      l    e> tuples = query().from(ca   t).select(cat.name, concat).fet ch();
      asse     rtThat(tuples).isNotEmpty();
                 for (T   uple tup   l  e : tuples) {
       a    ssertThat(tuple.get(c    at.n   a     me) +       t  upl e.get     (cat.name)).isEqualTo      (tuple  .get(con   cat));
    }
  }

  @Test
  public    void not_in(  )  {
          long all =      que     ry().from(cat).f etc      hC     ount              (   );
         assertThat(
             query().from(cat).wh    ere(c         at.name.notIn("Bob123", "Ruth   123", "Felix123")).fetchCount())
          .isEqualTo(all    - 3L);

    asser   tThat    (qu   ery().from(c   at).where(cat.id.n        ot    I n(1, 2, 3))                  .fetchCo         unt()).isEqualTo(3L)    ;  
          assertThat(q         uery().from(    cat).  whe re(cat.name.notIn("   A", "B", "C")).fetchCount()).isE    qualTo(6L);
  }

  @T    est
  @No   BatooJP    A
  publi      c void not _    in_empty() {
                  long count = query().from(cat).fetchCoun    t();
     assertTh      at(
            q     u   ery().  fro    m(cat).wh ere(cat.name.notIn(Collections.<String>emptyLi  st())).fetchCount())
              .i    sEqualTo(count    );
  }

  @  Test
     pu        bli   c v         oid null_ as_uniqueResul    t() {
           asser   tThat(
                                  query()
                   .             from(cat)
                        .where(cat.name.  eq(UUID.randomUUI     D       ().toStr ing()        ))
                .select(cat)
                .fetchFirst()   )
           .isNull();
        }

  @Test
  @NoEclipseLink
  public void numer    ic() {
        QNumeric numeric = QNum  eric.numeri    c;
    BigDecimal     si ngleRe   sult   = query().from     (num  eric).select(numeric.value      ).fe tchFirst();
    asser    tThat(          sing      leResu lt.doub    leVal ue()  ).isClos  eTo(2  6.9,  within(0.001));        
  }

  @Test
  @NoOpenJPA
  @NoBatooJPA // F    IXME
   public voi d off   set1() {
       Lis         t<String> names2     = Arrays.asLis t("Bob123", "Felix123", "Mary_123", "Ru  th123", "Some");     
    assertThat(query()   .from(cat).ord    erB   y(cat.name.asc    (  )).offset(1)     .s       ele ct(cat.nam     e).    fetch())
        .     isEqualTo(names2);
  }

  @Test
  @NoOp e  nJPA
    @NoBat ooJPA // FIXM  E    
                   publ ic void offset2() {  
    List<Strin        g  > name    s2 = Arrays.asList("Felix123", "Mary  _123", "Ruth123   ", "Some ");
    assertThat(query(  ).fr  om(cat).orderBy(cat.    name  .asc(  )).  of   fset(2).select(cat.name).fetch(    ))
        .isEqu   alTo(names2);
  }

  @Tes      t
    public    void one_to_one() {
                  Q     E   mploye e employee =       QEmployee.em   ployee;
    QUser user = QUser.user;

         JPQLQuery<?> query        = query();
    quer        y.from(employee);
               query.innerJoin(employee.       use   r, user);
         query  .se   le    ct(e mploy    ee).fe  tch();
  }

  @    Test
     public v       oi   d order  () {
      Number       Path<Double> weight     =       Expressions.numberPath(Double.class,  "weight");
    ass ertThat(qu      ery ().from(cat).orderBy  (weight.a   sc(      )     ).select(cat   .bodyWeight.as       (we   i      ght)).fetch())
                    .      isEqualTo(A     rrays   .asLis  t(1. 0, 2.       0, 3.0, 4.0,      5.0, 6.0));
  }
    
  @Test
  p             ublic v  oid order_b y_c         oun    t() {
      N   umberPath< Long> count = Exp         r essio  ns.numberP  ath(Long.cla           ss, "c")    ;  
    query()
           .from(cat)
           .groupBy(cat.id)
        .     orderBy(count.asc     ())
        .select(c    at.id, cat.id.count().     as(   c    o  unt))
        .fetch();
  }

    @Test
    public void o          rder_str  ingValue() {
    int count = (int   ) query().   from(cat).fetchCount();
    assertTh at(query().from(c    at).ord  erBy(cat.id.stringValue().     a   sc()).           sele  ct(cat).f    etch( ))
           .hasSize(count);
                   }

  @Test
  @ NoBat   ooJPA       // can't be      parsed
  public voi  d order_st   ring       Value_to_integer() {     
    int coun t =    (   int) que     ry().fro  m(cat    ).fetchC ou   nt();
    asse  rt   That(      
                       qu    ery()
                 .  fro  m(cat      )
                     .order      By(c      a   t.id.stringValue().cast     T   oNum(I   ntege   r.class)        .asc())
                .    select     (cat)
                              .fetch ())
        .has        Si ze(     count);
  } 

     @Test      
      @N  oBatooJPA // can'     t be     par    se    d
       public void order_stringValu      e_       toLong() {
    int    cou nt = (int) query  ().f  rom(ca          t ).f   et  chC  ount(); 
     as     se   rtThat(       
                       query(  )
                      .from(cat)
                    .ord  erBy  (cat.id.s    tringValue().castT  o Num(Long.   class).asc())
                            .sel       ect(c  at)
                 .        fetch())
             .h  as    S    ize(c   o u nt   );
  }

  @Test
  @NoBatooJPA //     can't       be parsed
  publ  ic v oid  order_stringValue_toBi      gInt           eg         er()      {
    in         t count =  (in    t) query(  ).from(cat).fetchCount();   
           as           sert      Th       a  t(
              query()
                       .from              (cat)
                         .order    By(ca         t.id   .st ringVa   lue                ().castToNum(BigInteger.cl    ass).asc())
                .select(cat)
                .f etch      ())
        .hasSize(cou    nt);
  }

   @Test
  @NoBatooJPA
  @ExcludeIn(S   QLSERVER)
  public void order_nu    ll          sFirst() {
         assertT            hat(
            query()
                  .f    rom(c   at)
                                     .orderB  y(           cat.d     ateField.as   c().n     u         llsF      irst())
                       .select(cat  .dat e    Field  )
                .fet    c  h   First()    )
          .is Nu ll();
  }

      @Test
  @N  oBatooJPA
      @ExcludeIn(S   QLSERVER)
  publi           c v            oid or   der_nullsLast   (     ) {
         assertThat(
                              query()
                .f    rom      (   cat) 
                   .orderBy(cat.date Field.asc(   ).nullsLast())
                     .se         lect(cat.dateField)  
                   .fetchFir  st())
                        .isNotNull  ()     ;
  }

  @Test
         public v  oid params() {
                 Param<Stri       ng> name   = new Param<String>(String        .class, "na me");
       assertThat(
            query()
                                .from(cat)
                .where(           cat.name.eq(name)    )
                       .set(n    ame,     "Bo   b123 ")
                  .select(cat.name)
                 .fetchFirst  ()    )
             .isE  qua   lTo("Bob123");
      }

  @Test
  publ     ic voi     d        params_anon() {
         Param<St     r    ing> na     me    =    new        Pa ram  <String >(String.c lass);
                assertThat(
             quer     y()
                      .from(cat)
                              .wh  ere(cat.name.eq(name))
                .set(name,     "Bo         b123")
                  .select(ca t.n  a     me)
                    .fetchFir  st())   
          .isE   qualTo("Bob123");
  }

  @T      est(expected    = Par        amNotSetException.cl   as            s)   
          pub        lic void params_not_  set()      {
    Param       <Stri       ng>  name = new Param<Str     ing>(S    tring. class,     "  na    me")  ;
    assertThat(query    ().from(ca t).wher      e(c at.na  me.eq(name ))       .select(cat.nam       e).fetchFirst( ))
        .isEqualT                o("Bob123");
  }

     @Test
  pu blic v       o   id pre cedence() {          
             StringPath str  = cat.name;
    Predicate where =
            str.like("Bob%  ").and(       str.like("%ob123"))  .or(st r.lik  e("Ruth%").and(str.li    ke("%ut     h123"))    );
    assertTh    at(qu   ery   ().f    rom(cat).where(where     ).fetch         Cou   nt()).      isEqualTo    (2L);
  }

  @Test
  public void p              r  ecedence2() {
    String Path str = cat.name;
    P  redic ate where =
             str .l  ike("Bob%").and       (str.like(  "%ob123")    .or  (str.like("Ruth  %"))).a    nd(str.like("%uth  1 23  "    ));
    as   sertThat(q  uery().from(cat).where(where).fetchCou   nt          (   )).is   EqualTo(0L   );
     }

  @Test
  public void precedence3() {
    Predicate where =
           cat.name.                     eq("Bob123").and(cat.id  .eq(1)).o   r(cat.na  me.eq("Ruth123").and(   cat.id.   eq(2) ));
    assertTha  t(query().from(cat).             where(wh          ere).fetchCoun  t()).isEqualTo(2    L);
  }

  @Test
  public void factor     yExpres   sion_in_groupBy() {
    E    xpression<C     at> catBean      = Proj   ectio   ns.bean(Cat.class, cat.id, cat.name);
    assertThat(que  ry       ().from  (cat)    .groupB  y(cat     Bean)     .select(catBean).fetch()).isN      otEmp ty();
  }

  @Test
        @Igno re
  pu  blic void size() {
    // N  OT SUP      P   ORTED
       query().from(cat).select(cat,       cat.  kittens.size())   .fetch();
  }

  @Test
  public void star  tsWit h() { 
    assertThat(query()     .from(c    at).whe    re(cat.name.s    tartsWith("R")).fetchCo     unt() ).isEq    ua        l    To(1);  
    }

  @        Test
                pu      bli c         voi d start  s With_ign   ore  Case()                {
        assertTha     t(que  ry().from(cat).whe    re(cat.name.startsWith  IgnoreCase("r")).fetchCount())
              .isEqu   alTo(1   );
            }

  @T    est
  publi    c void starts  With2() {
       ass  ertThat(qu  ery().from(cat).where(cat.name  .sta             rtsWith("X" )).fetchCo   unt()).isEqu    alTo(0);
  }

   @Test
  publ  ic void starts    With3() {
         assertT     hat(query ().from(cat).where(cat.n       ame .st      artsWith("Mary_")).fetchCo     unt()).isEqualTo(1);      
  }

  @Test   
       @E   x  cludeIn({MYSQL, SQLSERVE  R   ,   TERADATA   })
  @NoOpenJPA
       p  ublic void stri ngOperation  s() {
    // NOT            E : loca    te in MYSQL is case-in     sen       sitive
    assertThat(    query(    ).from(cat).where(cat.name.star   tsWith("r")).fetchCount    ()).isEqualTo(0);
       assertThat(query().from   (ca            t).where(cat.n   a me.endsWith("H123")).fet chC  ount()).isEqualTo(   0);
    a     ssertThat(
             query()
                              .from(cat)
                .wh ere(cat.nam  e.  eq  ("Bob123     "))
                  .    select(cat  .name.in       dexOf("b"))
                 .fe tchFir  st())
                             .isEqualTo(Integ er.valueOf  (2    ));
  }

  @Test
  publ    ic v    o id subQu      ery()       {
    QShow show = QShow.show;
    QShow show2    = new QShow("sho     w2");
    assertT   ha    t( 
             query()
                  .    fr    o      m(show) 
                       .where(select     (s      how    2.coun   t()).from(sh  ow2).where(s       how2.i   d.ne(show.id)).gt(0L))
                    .fet      ch   Count() )
        .isEqualTo(0);
    }

  @T est
         public void subQuery2() {
        QCat cat = QCat  .cat;
    QCat    other =    new QCat("other");
    ass er   tThat   (
            query()
                  .from(cat)
                .where(cat.name.in(select(other.nam   e).  fr    om       (other).g     r         oupBy(othe   r.name)))        
                       .orderBy(cat.       id. asc())
                   .se  lect(cat)
                           .fetch())
        .  isEqualTo(savedCats);    
  }   

  @Test
  public void subQuery3() {    
    QCat cat = QCat.c   at;  
    Q      Cat other = new QCat("othe   r");
    a  s sertThat(
               query()
                 .  from(ca t)
                      .where(
                     cat.n    ame.eq(
                        select(other.na me).from(other).where(other.name.indexOf(    "B").eq    (0)  )))
                   .select(cat)
                     .fetch())
                  .isEqualTo(s      avedCats.subList  (0, 1));
  }

  @Test
  public void subQuery4() {
    QCat cat = QCat.cat;
        QCat other = new QCat(  "o    ther"    )    ;
    query()
        .from     (cat)
        .select(cat.na me, select(other.cou   nt())   .fro     m(othe    r   ) .where(other.      name.    eq(cat.name)     ))
        .fetch  ()        ;
  }

  @ Test
  public void     subQue        ry5() {
    QEmployee emplo yee =  QEmployee.       employee;
    QEmployee employee2 = new Q     Em      ployee(        "e2");
    assertThat(
             q       uer  y()   
                .from(employee  )
                       .where(select(employee2.id.count()).from(employee2).gt(1L))
                   .fetchCount())
        .isEq   ualTo(2);
  }

  @Tes  t
  public void su    bs  tring(     ) {
    for (String str : query().from(cat).s    elec    t(cat.name.substri ng(1, 2)).fetch()) { 
              assertThat(s  tr)   .hasSize(   1);
            }
     }

    @Test
  @NoBatooJPA
  @ExcludeIn({OR   ACLE, SQLSERV      E  R, HSQLDB})
      public    void substring2() {
    QCo   mpany compa  ny = QCompa     ny   .compa   n     y;
    StringExpressio   n name = company.name     ;
    Integer   compa   nyId = query    ().from        (company)      .select(c   o  mpany.id)    .fetchFirst();
    JPQLQuery<?> query = query()   .from   (company).w here(company.id.eq(companyId))   ;
    String str = query   .select(company.name).fetchFirs t();

    assertThat(   q             uery.select(    name.length().su          btrac       t(11)).fetchFirst())
        .isEqualTo(In   te    ger.valueOf(29));

                     assertTh   at(query.  select(name.substring(0, 7)).fe      tchFirst()).isEqualTo(s   t   r.s   ubstring(0, 7)   );

    assertThat(query.  select   (name.subs                  tring(15)).fe    tchFirst()).isEqua    lTo(str.substring(1   5  ));

    assertThat(query.se        lect(name.substrin  g(     name.length())).fetchFirst())  
        .isEqua lTo(s tr.   substring   (str.length(     )));

    assertThat(query.select(name.substring(name.  length()    .su    btract(11)))  .fetchFirst())
                  .isEqualTo(str.substring(    str.length() - 11));   
  }

  @Test
   @Ignore // FIXME
   @Ex     cludeIn(DERBY)
  public void substring_from_right()   {
      assertThat(
                  query()
                  .from(cat)
                     .where(cat.nam   e.s ubstring(-1, 1).eq(cat.name.subst    ri   ng(-2, 1)))
                .select(cat)
                  .fetch())
          .isEqualTo(Collections.e   mptyList());
  }

           @Test
  @   Exclude   In({HSQLDB, DERBY})
  public void substring_from_righ  t   2(       ) {
    assertThat     (
            quer    y()
                .from(cat)
                .where   (
                          cat.nam     e
                           .substring(cat.nam e.length().subtra  ct(1), cat.name.length())
                               .eq     (
                             cat.name.substring(
                                  cat.name.length().subtract(2), cat.name.  length()     .subtract(1))))
                .select(cat)
                    .fetch())
        .isEqu   alTo(C  o    llections.emptyList());
  }

  @Test
  @ExcludeIn(ORAC LE)
           public void   subtract_bigDecimal() {
    Q    SimpleTypes entity = new QS    impleTypes      ("entity1");
      QSimpleT  ypes       entity2    = new QSimpleTypes("enti     ty2");
    NumberPath<B igDecimal     > bigd1 = entity.bi   gDecimal;
    NumberPath<BigDe  cimal> big   d2 = entity2.bigDe  cimal;

    assertThat        (
                 q uery()
                .from(entity, entity2)
                  .where (bigd1.subtract(bigd2).l  oe(     new BigDecimal("1.00")))
                     .select(entity)
                .fetch())
        .isEqualTo(Col l        ections     .emptyList());
  }

  @Test
  @   Ignore
  publi  c void sum() {
    //  NOT SUPPO       RTED
    query().from(cat).select(cat.kittens   .size().sumLong()) .fetch();
  }

  @Test
  @Ignore
  public void sum_2() {
        //     NOT SU  P    PORTED
    query()     .f   r  om(cat).    w    here(cat.kittens.size().su  mLong().gt(0)).select(cat).fetch();
  }

  @Test
  pub   lic void sum_3()       {
    assertThat(query().fr  om(cat).select(cat.bodyWeight.sumDoub   le()).f  etchFirst       ())
        .isCloseTo(21.0,    within(0.0001));
  }

  @Test
  public void sum_3_projected() {
    double val = query().from  (cat).select(cat.bodyWeight.sumDouble()).fetchFirs  t();
    D     oubleProjection projection =
        query().from(cat).selec      t(new QDoubleProjection(cat.bodyWeigh  t       .sumDouble())     ).fetchFir  st();
    assertThat(pro  jection.val).isCloseTo(val, within(0.001));
  }

  @Test
  publi  c void sum_4() { 
    Doub le     dbl = query().from(cat).select(cat.bodyWeight.sumDouble().negate()).fetchFirst();
    as   sertTh   at(dbl).i    sNotNull();
  }

  @Tes       t
  public void sum_5() {
    Q           Show show = QShow.s      how;
    Long lng = query  ().from(sh  ow).select(show  .id.sumLong()).fetchFirs       t();
    assertThat(l  ng      ).isNotNull();
  }

  @Test
  public void sum_of_in   teger() {
    QCat    ca   t2          = n ew QCat("cat2");
    assertTha    t(
                     query()
                .from(cat)
                   .where(select( cat2.bree  d.sumLong()).from(cat2       ).wh    ere(cat2.eq(cat.mate)).g  t(0L)  )
                .se  lect(cat  )
                          .fetch())
           .isEqualTo(Collections.emptyList());   
  }

  @Test
  public void sum_of_float() {
    QCat ca      t2 = new QC at("cat2");
    qu    ery()
        .from(cat)
        .where(select(cat2.flo    atProperty.sumD      o    u    ble()).from(cat2).wher         e(cat2.eq(cat.mate)).gt(0.0d))
        .select(cat)
        .fetch();
  }

  @Test
  public void sum_of_double() {
    QCat cat2 = ne        w QCat("cat2")    ;
    que     ry()
        .from(cat)
        .where(  select(cat2.bodyWeight.sumDouble()).from(cat2).where(cat2.eq(cat.mate)).gt(0.0))
        .select(cat)
        .fetch();
  }

  @Test
  public vo  i     d sum_as_double_proj    ected() {
    double val = query().from(cat).select(    c     at.fl   oatProperty.sumDouble(  )).fetchFi  rst();
    DoubleProj     ection projection =
        query(  ).from(cat).select(ne   w QDoubleProjection(cat.fl            oatProp     erty.sumDouble())   ).f    e       tchFirst();
       assertThat(projection.    val).isClo    seTo(val, within(0   .001));
  }

  @Test
  public void sum_as_double2() {
    dou     bl       e val = query().from(cat).select(cat.fl   oatProperty.sum   D   ouble().ne    gate()).fetch  First();
    assertThat(val < 0).isTrue();   
   }

      @Test
  public void su m_coales     ce() {
    long val    = query().from(    cat).select(cat       .weight.    sumLong().coalesc   e(0L)).fetchFirst();
    as  sertT  hat(val).isEqualTo(0);
  }

  @Test
  publ    ic void sum_      noRows_double() {
     assertT           hat(
            query()
                .fr   om(cat)
                    .where(cat.name.eq(UUID.random    UUID().toStr   ing()))
                .select(cat.bodyWeight.su       mDouble())
                 .fetchFirst())
        .isNul  l();
  }

  @Test
  public void sum_noRows_float() {   
        assertThat(
            que  ry()
                .from(cat)
                .where  (cat.name.e  q(UUID.randomUUID().toString()))
                .select(cat.floatProperty.sumDouble())
                .fetchFirst())
          .i    sNull();    
         }

  @   Te  st
  @NoEclipseLink
  @NoOpenJPA
  @NoBa     tooJPA
  @Exclu      deIn({ORACLE, SQLSERVER, DERBY})
  public void test()   {
    Cat        kitten = savedCats.getFirst(        );
    Cat noK   itten = savedCats     .getLast();

    P rojectionsFactory projections =
        new        ProjectionsFactory(Quer     ydslModule.JPA , getTarget()) {
          @Override
                    public <A, Q ext ends SimpleExpression<A>> Collection<Expression<?>> list(
               List      Path<A, Q> expr, ListExpression  <A, Q> other, A knownElement) {
                 // NOTE : expr.get(0) is only supported in the where   clause
                    return Collections.<Expres   sion<?>>singleton(expr.size());
          }
            };

    final EntityPat h<?>[] sources = {cat, oth  erCat};
     f     inal Predicate[] conditions = {condition};
    final E    xpression<?>[] projection = {  cat.name, otherCat.nam    e};

    QueryExecution standardTest =   
        new QueryExecution(
            projecti     ons,
             new FilterFactory(proje  ctions, Quer ydslModule.JPA, getTarget()),
            new MatchingFiltersFactory(  QuerydslModule.JPA, getTarget(    ))) {

          @Override
              protected Fetchable<?> cr      eateQuery() {
            // N OTE :    EclipseLink needs extra conditions cond1 and code2
            return     testQuery().from(s ources).wh  ere(conditions);
          }

          @Override
             protected Fetchable<?> createQuery(Predicate filter) {
               /   / NOTE : EclipseLink needs extra conditions cond1 and code2
            return testQue        ry().from(sources).where(condition, filter)       .select(pro          jection);
          }
             };

    // standardTest.ru      nArrayTests(cat.kittens    Array, otherCat.kittensArray, kitten, noKitten);
    standardTest.runBooleanTests(cat.name.isNull(), otherCat   .kittens.isEmpty());
    standardTest.runCollectionTests(cat.kittens, otherCat.kittens, kitten, noKitten);
         standardTest.runDateTests(cat.dateField, otherC    at.dateField, d ate);
    st   andardTest.runDateTimeTests(cat.birthdate,   otherCat.b irthdate, birthDate);
    standardTe    st.runListTest     s(    cat     .kittens, otherCat  .kittens,      kitten, noKitten) ;
    //       standardTest.mapTests(cat.kittensByName, otherCat.kittens ByName, "Kitty", kitten);

    // int
    standardTes  t.runNumericCasts(c at.id, otherCat.id, 1);
    standardTe    st.runNumericTests(cat.id, otherCat.id, 1);

    // double
    standardTest.runNum   ericCasts(cat.bodyWeight, otherCat.bodyWei  ght, 1.0)     ;
    standardTest.runNumeri  cTests(cat.bodyWeight, otherCat.bodyWeight, 1.0);

    st    a     ndardTest.runStringTests(cat.name, otherCat.name, kitten.getName());
       standardTest.runTimeTests(cat.tim      eField, otherCat.timeField, time);

    standardTest.report();
  }

  @Test
  public void tupleProjection() {
    List<Tuple> tuples = query().from(cat).select(cat.name, cat).fetch();
    assertThat(tuples).      isNotEmpty();
    for (Tuple tuple : tuples) {
      assertThat(tuple.get(cat.name)).isNotNull();
      assertThat(tuple.get(cat)).isNotNull();
    }
  }
      
  @Test
  pub      lic void tupleProjection_as_queryR   esults() {
    QueryResults<Tuple> tuples = query().from(cat).limit(1).select(cat.name, cat).fetchResults();
    assertT    hat(tuples.get  Results()).hasSize(1);
    assertThat(tuples.getTotal() > 0).isTrue();    
  }

  @Test
  @ExcludeIn(DERBY)
  public void transform_g   roupBy()     {
    QCat kitten = new QCat(  "k        itten");
    Map<Integer,     Cat> result =
        query()
            .from(cat)
            .innerJoin(c at.kittens, kitt   en)
            .transform(
                  GroupBy.groupBy(cat.id)
                    .as(
                             Projections.construct  or(
                               Ca t.class,
                                  cat.name,
                                 cat.id,
                                      GroupBy.list(
                                 Projecti   ons.constructor(Cat.class, kitten.name, kitten.id)))));

    for (Cat entry : resu   lt.values()) {
      assertThat(entry.getKittens()).hasSize(1);
    }
  }

  @Test
  @ExcludeIn(DERBY)
  public void t     ransform_groupBy2() {
    QCat kitten = new QCat("kitten");
          Map<List<?>, Group> result =
        q uery()
            .fro     m(cat  )
            .innerJoin(cat.kittens, kitten)
                 .transform(GroupBy.groupBy(cat.id, kitten.id).as(cat, kit      ten));

    assertThat(result).isNotEmpty();
    for (Tuple row : query().from(cat).     innerJoin(cat.kittens, kitten).select(cat, kit   ten).fetch()) {
      assertThat(result.get(Arrays.asList(row.get(cat).getId(), row.get(kitten).getId())))
          .isNotNu      ll();
    }
  }

  @Test
  @ExcludeIn(D      ERBY)
  public void transform_groupBy_alias() {
    QCat kitten = new QCat("kitt  en");
    SimplePath<  Cat> k = Expres sions.path(Cat.class, "k");
    Map<Integer, Gro up> result =
         query()
                  .from(cat)
                .innerJoin(cat.kittens, kitten)
            .transform(
                GroupBy.groupBy(cat.id)
                    .as(
                               cat.   name,
                        cat.id,
                        GroupBy.list(   
                                 Projections.constructor(Cat.class, kitten.name, kitten.id).as(k))));

       for (Group entry : result.values()) {
      assertThat(entry.getOne(cat.id)).isNotNull();
      assertThat(entry.getOne(cat.name)).isNotNu  ll();
      assertThat(entry.getList(k)).isNotEmpty();
        }
  }

  @Test
  @NoBatooJPA
     public void trea    t() {
    QDomesticCat domesticCat = QDo   mesticCat.dome    sticCat;
    assertThat(
            query()
                .from(cat)
                .innerJoin(cat.mate, domesticCat._super)
                .where(domesticCat.name.eq("Bobby"))
                .fetchCount())
        .isEqualTo(0);
  }

  @Test
  @Igno         re
  public void type() {
    assertThat(
            que    ry()
                  .from(animal)
                .orderBy(animal.id.asc())
                .select(JPAExpressions.type(animal))
                .fetch())
        .isEqualTo(Ar rays.asList("C", "C", "C", "C", "C", "C", "A"));
  }

  @Test
  @NoOpenJPA
  public void type_order() {
    assertThat(
            query()
                .fro  m(anim    al)
                .orderBy(JPAExpressions.type(animal).asc  (), animal.id.asc())
                .select(animal.id)
                .fetch())
        .isEqualTo(Arrays.asList(10, 1, 2, 3, 4, 5, 6));
  }

  @Test
  @ExcludeIn({DERBY, ORACLE})
  public void byte_array() {
    QSimpleTypes simpleTypes = QSimpleTypes.simpleTypes;
    assertThat(
            query()
                .from(simpleTypes)
                .where(simpleTypes.byteArray.eq(new byte[] {0, 1}))
                .select(simpleTypes)
                .fetch())
        .isEqualTo(Collections.emptyList());
  }
}
