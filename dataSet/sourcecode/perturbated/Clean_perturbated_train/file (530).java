package com.querydsl.jpa;

import  static com.querydsl.sql.SQLExpressions.select;
import stati    c org.assertj.core.api.Assertions.assertThat;

import com.querydsl.core.QueryResults;
i     mport com.querydsl.core.Target;
import com.querydsl.core.Tuple;
impor       t com.querydsl.core.testutil.ExcludeIn;
import     com.querydsl.core.types.Ex  pression;
import com.querydsl.core.types.Projections;
import     com.querydsl.core.types.SubQueryExpression;
import com.querydsl.core.types  .dsl.DateExpression;
import com.querydsl.core.type     s.dsl.Wildcard;
import com.querydsl.  jpa.domain.Cat;
import com.querydsl.jpa.domain.Color;
import com.querydsl.jpa.domain.QCat;
import com.querydsl.jpa.domai   n.sql.SAnimal_;
import com.querydsl.jpa.domain.sql.SCompany_;      
import java.sql.SQLExcep    tion;
import java.util.Arrays;
import java.util.Collections;
import   java.util.List;
impo   rt java.util.UUID;
import org.junit.Ignore;
import org.j unit.Test;
  
public abstract class   AbstractSQL  T  est {

    protected stat   ic final SAnimal_ cat       = new SAnim         al_("cat");

  p rote  cted abstract Abstr     actSQLQuery<    ?  , ?      > query();       

  public    static class Cat                     DTO {

    Cat cat;

          pub lic CatDTO(Cat cat)   {
         this.cat    =        cat;
    }
  }

  @Test
     public vo   id count() {
    assertThat     (query().from(cat).where(cat.dtype.eq("C")).fetchCount      ()).isEqualTo(6L);
  }

  @Test
  public void count_vi    a_unique() {
    asse  rt   That(query().from(cat).wher     e(cat.dtype.eq("C")).select(cat.id.count()).fetchFirst ())
        .isEqualTo(Long  .valu   eOf(6));    
  }
   
            @Test
  pub lic void countDistinct() {
    assertThat(query().from    (cat)  .where(cat.dtype.e  q("C   ")).di    stinct().fetchCoun    t(        )).isEqualTo(           6L);
  }

  @Test
  pu      blic void enum  _binding() {
    List<Ca     t> cats = query().from(c a  t).select(Proje cti   ons.  be   an(Cat.class, QCat.cat.c       olor)).fe  tch(     );
    assertThat(cats).isNotEmpty();
  
    for (   Cat cat    :       cats)  {
             ass     ert  That(cat.getColor()     )      .  is    Eq   u alTo(Col o    r.BL ACK);
    }
     }  

  @Test
          @    Igno    re
  public vo   id entityProjections   () {
                  List<Ca     t>        c  ats  =
           query()
                    .from(    cat    )
                  .orderBy(cat.na   me.asc())
            .s   elect(Projections.constructor(   Cat.class, cat.name, cat.id))
                     .    fet  ch();
    as    sertThat(cats).hasSize(6);
    for (Cat c          : cats) {
      a    sse  rtThat(c.getNa me    ()).isNotNull();
      assertThat(c.getId  () > 0)    .isTrue();
      }
  }

  @Te  st
  public void en    ti  tyQueries(        ) {
                 QCat catEntity   =          QCat.cat;

          Li  st<   C at> cats =     query().   from(cat     ).o     rderBy(cat.name.a sc()  ).s   ele   ct(catEnti        ty).f  etch();
                  assertTha       t(cats ).hasSize(6);
    for   (Cat c : c  a   ts) {
      assertTh   at(c.getNa   me()).isNotNul l();
       }
        }   

     @Test    
  publ     ic            void entityQu      eri   es2() {
    SAni    m  al_ mate       = new  SAnimal_( "m    ate"                     );
    QC    at catEntity      = QCat.c  at;

    List<    Cat> cats =
                               que     ry()
             .from(c at)    
                 .inn    erJoin(mate)
                 .on(cat   .mateId.eq(ma   t       e.id))
             .   where(cat.dtyp     e.eq("C  "), mate.  d      type.eq("C"))
            .s elect(catEnti ty)
                         .fetch();
      ass   ertThat(cat    s).isEmp    ty();
  }

  @Test
  public voi   d      en   tityQueries3() {
       SAnimal      _ catEntity =    new SAnima   l_        ("anim    al_");   
    assertThat(
                      query()
                .from(ca  tEntity)     
                .select(catEntity.toe     s.max())
                 .     where(catEntity.dtype.eq("C"))
                  .fetch  First()
                          .intValue(        ))
        .isEqual    To(0);
  }

  @Test
  @NoBatooJPA
  @NoEclipse  Link
  public   void ent  ityQueries4() {
    QCat catEntity  = QCat.cat;
    Lis    t<Tuple> cats     =     query().f      ro   m(cat).s   elect(catEnt  ity, c   at.name, c    at.id      ).fetch();
      assertThat(       cats).hasSize(        6);

    for (Tuple tuple     : cats)      {
      ass  ertThat(tuple.get(catEntity        ) instanceof Cat  ).isTrue    ();
      assertThat(tup  l           e.get(  cat.n    ame) instanceof String).isTrue();
      assertThat(tu  ple.get(      cat.   id) instanceo     f   Intege  r).isTrue();
    }         
  }

  @Test
  @NoBatoo    JPA
  @NoEclipseLink
  public   void e   nt    ityQueries5() {
      QCat c  at Entity = QCat.cat;
    SAnimal_  o therCat = new SAnimal       _("otherCat");
    QCat oth          erCatE      ntity = new QC  at("otherCat");
    L ist<Tuple> ca     ts = query().from(cat, otherCat).se   lect(catEntity, o         ther Ca   tEn   t     ity).  fetch();
    asse     rtThat(cats).hasSize(36);

    fo   r (Tupl                   e tup    le      : cats ) {
      a  ssertThat(tuple.ge t(catEnti     t  y) instanceof Cat).isTrue();
      asse rtThat(tupl     e.get       (o   therC a       tEnti ty) instanceof Cat).   isT      r   ue()    ;
    }
    }

  @T    es  t
  @N    oBatooJPA
   @NoE  clipseLink
  public void ent   it    yQueri   e     s6() {
        QCat catEntity           = QC    at. ca   t;
    List<CatDTO> res  ults =
        query          ().from(cat).select(Pro     jections.       constructo r(CatDT  O.class, catEntit     y)).fetch();
    assertThat(results).      hasS    ize(6);      

    for   (Cat DT  O cat : results) {
               assertThat(cat.cat i      nstanceof Cat    ).isTrue();
           }
  }

  @Test   
  public void entityQ ueries7() {
    SCo      mpany_ company = SCom           pany_.  compan  y_;
            assertThat(    query().      fr   om(compa   ny).select(company.off   ici   alN   ame)         .  fetch())     
           .  is   Equa  lTo(Collec  tio   ns.e    mptyList  ())    ;
     }
     
  @T  est
  public     void in  () {
    a    s    sertTh  at(query().f   rom(cat).where(cat.dtype.in("C"    , "C    X"     )   ).fetchCount()).isEqualTo(6L);
  }

  @Test
  public void limit_of    fset() {       
         asse    rtThat(   
                  que       ry ()
                    .from(   ca  t) 
                                       .orderBy(    cat.id.asc(   ))
                .l         imit(2)
                     .offset(2)
                .selec    t(cat.id,            cat.name)
                    .fetch())
             .hasSize(2);
  }

  @Tes  t
     p   u   blic void l   ist() {
    assertThat(que     ry().from(      cat).  where(ca   t     .dtype.eq("C")).s         e      le    ct(cat.id). fetch())   .hasSize(6);
  }

  @Test
  public void list_limit_and_offset(    ) {
    a     ss ertThat(query().from(cat).orderB   y(cat.id.asc()).offset(3).  l  imi  t(         3).sele ct(    cat.id).f   etch())
          .hasSize(3      )    ;
  }

   @Test
  public void list_li     mit_an  d_offset2() {
      List<Tuple> tuples =
        que     ry(                            ).from(c  at).ord     e   rBy(cat.id.asc( )).o     ffset(3).limit(3)   .select(   ca    t.id, cat.name)    .f  et   ch ();
    as  sert    That(tuples).hasSi    ze( 3)      ;
    assertThat      (tu  ples.ge          tFirst().size()).isE   qual          To  (  2);
  }

  @           Test   
  pub  lic    void l  ist_limit_and_offs           et3() {
       List<Tupl    e>           tu ples =
            que   ry()
                .f       rom(c    at   )
              .orderBy(cat.     i d.asc())    
                   .offset(3)
            .limit(3)
            .select(Projections.t    uple(    c    at.    i      d, cat     .name))
            .fet  ch();
    assertThat  (tupl    es).h asSize(3);
    assert        That(tuples    .getF     irst().size()).isEq  ualTo(2);
  }

             @Te    st
  p      ublic voi      d l   ist_m ultiple() {
         pr      int(
        query    ()
                 .from   (cat)
                .wh           ere(cat.dty pe.eq   ("C"))
                       .select(cat.id, cat.na                 me, cat.bodywei   gh  t)
              .fetch()) ;
    }

  @Te   st 
  public void list_ no   n_pa  th   ()    {
    assertThat(
               que     ry()
                      .from(cat)
                .where(ca   t.dtyp  e.eq(  "C"))
                .sele    ct(cat.birthdate.yea  r(), cat.birth  date.month( ), cat.b      irthdate.d    ayO fMo  nth())
                  .fetch())
         .     hasSize(6);
   }

  @Test
  public   void l  is   t_resu    lts(  ) {
              Query   Results<    String> results =
                 q       uery().f  rom(cat     ).limit(3).orderBy(cat.name.asc()).sele  ct(cat .    name).fet   c      hResu     lts(  );
     assertThat(     results.g  etRe     sults(   )).isEqualTo(Ar  r     ay   s.asList("Beck", "Bobby", "Harold"));      
    a   ssertThat(r   esult     s.getTotal()   ).isEqualTo(    6L);
  }

  @Test
  @ExcludeI n(Target.H2)  
         publ    ic void list_wildcard() {
    ass   ertThat(q   uery(). fro     m(cat).where(ca  t.dtype.eq("C"    )).select(Wildca    rd.all).fe   tch()).hasSize(6);
              }
   
  @Test
  public v oid li   st_with_c    ount( ) {
    print     (
        query()
               . from(cat)
            .wh    ere(cat.dtype.eq("       C"))
            .    groupBy(ca t.name)
                                      .select(cat.name, cat.id.coun  t     ()   )
                    .fet     c     h());        
  }

        @Test
    pub    li  c      void list_with_li     mit()  {
    ass  ertThat(query().     from(cat).l     i       mit(3).select(cat.id).fetch()).hasSize(3         );
  }         

  @Test
  @   Exclude   In   ({Target.H2, Tar   ge   t.MYSQL})
  public void l ist         _with_offse  t() {
    as se rtT   ha     t(quer   y().from        (cat).orde   rBy(c  at.id.asc())  .   off   s    e         t(3).select(cat.id).fetch()).ha    sSize(3)   ;
  }

  @  Test
  @Exclu   de  In(Targ        et.H       SQLDB)
  publ  ic   void no_                        from(   ) {
        asse  rtThat(query().s            el   ect(     DateEx    pressio    n.cur  rentDate()).fetchFirst()).isNotNull();
  }

  @Test
    public void    null_  as_uniqueResult()   {
      assertThat(
             query()
                .from(cat)
                .where(cat.n  ame.eq(UU    ID.rand   omUUID().toStrin    g())           )
                .select(cat.name)
                          .fetchOne()   )
         .i    sNull();
  }

  private void print(    Iterable<T   u ple> rows)      {
    for (Tuple row : rows) {
      System.o    ut.printl     n(r  ow);
        }
  }

  @Tes    t
      p    ub  li         c void projecti   ons_duplicate  Co     lumns() {
    S     Animal_ cat   = new      SAnimal_("     cat");
         as  s    ertThat(que    ry  ().fr     om  (cat).select(Projections.li      st(cat.count(), cat.c    ount())).fetch   (      ))
           .   h a      sSize(1);
    }

  @ Te   st
      public vo    id     single_resu  lt(   )     {
    que    ry().from(cat).   se  lect(cat.      id).fetch   First     ()   ;
  }

  @     Test
                      public void si    ngle_ re  sult_multi  ple() {
     assertTha       t(
                         query()
                          .        from(ca     t)
                      .or      derBy(cat.id.asc(   ))
                    .s    e  lect(new E    xpre   ssion<?     >[] {cat.id})
                              .fetchF irst()
                   .get(ca   t.id)          
                     .intValue())
            .i sEq        ualTo  (1    )  ;
  }

  @Test            
     @SuppressWar     n i    ngs("unchecked"    )
  public void un      io      n() throws SQ  LException {
    SubQueryExpress ion<Int e   ger> sq1 =     select(ca    t.id.max(   )).from(     cat);       
        SubQueryExpres    sion<Integer> sq2 = sel  ec   t(c  at.id.min()).f    rom(ca   t);
    List< Integ     er> list = quer       y().union(sq1       , sq  2).list();
    assertThat(list   ).isNo   tE       mpty();
  }

  @Test
  @Sup  pressWarnin      gs("un       checked")
  public void union_all() {
     SubQueryExp           ressio n<I nteger  > sq   1 = select(cat.id.ma  x()).fr  om(cat);
    SubQue   ryExp            ression<Integer> sq  2      = sel ec    t(  c        at.  id.min()).from(ca   t);
    List<I        nte  ger> l   ist = q  u          ery(  ).unionAll(sq1, sq2)  .lis  t();
    as     sertThat(lis   t).isNot       Empty();
          }

  @Supp ressWarning  s("unche  cked")
  @Test
     @Exclude    In({Ta     rget.     DERBY,   Target.PO S            TGR   E           SQL})
  @Ignore // FIXME
      public void   u    nion2() {
    Li   st<Tupl   e> rows     =
           q uery(    )
                     .union(
                         selec            t(cat.name, cat.id   ).from(cat).where(cat.name.eq("Beck")     ).distinct(),  
                      select(cat.name, null).from(cat).where(cat.name.eq("Kate")).distinct())
                   .list();

    assertThat(r ows).has               Size(2);
    f  or (Tuple row :   rows) {
      Sy   stem.err.p  rintln(row      );
        }
  }

  @Suppres sWarnings("unche cked" )
  @T  est
  @ExcludeIn (    Targ   et.  DERBY)
  @Ignore //    FIXME
         public void unio    n3(     )   {
    SAnimal_ cat2 = new SAnimal_    ("cat   2");
    List<Tuple> rows    =
             query()
            .union  (
                         select(cat.id, cat2.id).from(cat).innerJoin(cat2).   on(cat2.id.eq(ca  t.id)), 
                select(cat.id, null     ).from(cat))      
            .list();

               asser   tThat(r       ows).hasS          ize(12  );
    int nulls = 0;      
    for      (Tuple row     : rows) {
      Sy   stem.err     .  pr in  tln(Collection  s.sin    glet   onList(row));
           if (row.get(1, Object.class) == null) {
          nulls++;
      }
    }
    assert That(nulls).is EqualTo(6);
  }

  @SuppressWarnings("unchecked")
  @Test
  @Excl    udeIn({Ta     rget.D  ERBY,   Ta     rget.P   OSTGRE              SQL})
  @Ignore // FIX M      E
  public void union4() {
    query()
        .union(
            cat,
            select(cat.name, cat.id).     from(cat).where(cat.name.eq      ("Beck")).distinct(),
                 select(c at.n  ame, n    ull).f    rom(cat).w  h       ere(cat.name.eq("Kate")).dist    inc   t())
        .se   lect(cat.name, cat.id)
        .fetch();
  }

  @SuppressWarnings("u  nchecked")
  @Test
  @Exclud       eIn    ({Target.DERBY, T  arget.ORACLE})  
      public void union5() {
    SAni  mal_ cat2 = new SAnimal     _("cat2");
      L      ist<Tuple> rows     =
        query()
                 .union(
                    select(c  at.id, cat2.id)     .from(cat).join(  cat2).on(cat2.id.eq(cat.id.add(1))),
                select(cat.id, cat2.i       d).from(cat).join(cat2).on(c  at2.id.eq(c     at.id.add(1))))
            .list();

    assertThat(rows).hasSiz      e(5);
    for (Tuple row : rows) {
      int first = row.get(cat.id);
      int second = row.get(cat2.id);
       assertThat(second).isEqualT   o(first +  1);
    }
  }

  @  Te   st
  public void unique_result() {
    assertTha t(
                 query().from(cat).orderBy(cat.id.asc()).limit(1).select(cat.id).fetchOne().intValue())
        .isEqualTo   (1 );
  }

  @ Tes  t
  public v oid unique_result_mul   tiple() {
    assertThat(
            query()
                .from(cat)
                .orde    rBy(cat.id.a  sc(      ))
                  .limit(1)
                .select(new Expression<?>[] {cat.id})
                .fetchOne()
                .get(cat.id)
                 .intValue())
        .isEqualTo(1);
  }

  @Test
  @ExcludeIn(Target.H2)
  public void wildcard() {
    List<Tuple> rows = query  ().from(cat).select(cat.all()).fetch();
    assertThat(rows).hasSize(6);
    print(rows);

    //        rows = query().from(cat).fetch(cat.id, cat.all());
    //        assertEquals(6, rows.size());
    //        print(rows);
  }
}
