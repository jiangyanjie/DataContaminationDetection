/*
 *     Copyr    i       ght 2013 Steve Chaloner
 *
 *    Lic     en     sed under        the Apache License, Version 2.0 (th     e                 "  License");
     * you may    not us    e          thi     s file except in       complia       nce wi      th    the L  icense.
 *       You m ay obtai  n a copy of the License at
 *
 *     http://w  ww.  apache.org/licen   ses/LICENSE -2.0
 *
 * Unle   ss required by applic   able law o          r agre     ed to i  n wr  iting, soft    ware
 *   distrib  uted under the Li cense is distribute       d on an "AS IS"  B ASIS,
 * WITHOUT WARR   ANTI       ES OR CONDITIONS OF ANY KIND, either      express or implied.
 * See the    License for th e specific language governing permissions and
 * limitations under the License.
 */
  package be.objectify.deadbolt.core;

import be.objectify.deadbolt.core.models.Permission;
import be.objectify.deadbolt.core.models.Role;
import be.objectify.deadbolt.core.models.Subject;
import  org.junit.Assert;
import org.junit.Test;
import org.m   ockito.Mockito;     

import ja       va.util.ArrayList;
 import java.util.Arrays;
       import java.      util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pa     ttern;

/**
 * @author Ste  v e          C           haloner (steve@o          bjec      tify.be)
    */
p    ublic c        l     ass D    eadbol     tAnal yzerTest
{
       @Test
         pu   b     lic    void  testCh   eckRo le       _nullSubject()
        {
               A     sse         rt.as s    ertFal    se          (n   e    w DeadboltAnalyzer(       ).checkRole(Optional.empty(),
                                                                                               new Str  ing   []{"admin", "editor"}));
       }     

    @T   es    t
    public void testChec  kRo                 le_nullRoles()
    {
           final Subject subject = Moc    kito.moc                k(Subject.class);
                      Mo cki      to.whe n(subject.getRoles()    ).th  enReturn(n   ull);

               Assert .asser   tFalse(new Dead   bol    tAn     aly zer().che ck   R     ole(Opt    iona   l  .of(subject), 
                                                                                                                  new Str  ing[]{"  admin", "editor"}));            
    }  

          @Test
              public voi    d testCh      eckRole_   emptyR    o   les()
         {
             final Su      bject subject      = Mockito.mo   c   k(    Su   bj        ect .class);
          Moc    ki   to.whe  n(s   ubjec       t.getRoles(     )).thenReturn(Col  lections    .EMPTY_LIST);

             Ass ert.asser  tFalse    (new De a    dboltA       nalyzer().checkRole(Optional.of(subject   ),
                                                                                                  new       String[]{"ad     min",        "e   ditor         "}));
        }

           @Test
    public         v    oid testCheckRole_multi  pleRo      les()
          {
              final   List<TestRol  e> roles = new Ar       rayLi  s        t<TestRole>();
                      roles.a   dd(new Te   stRole("adm  in  "));
           ro  les.add  (      n ew TestRo                 le   ("e      ditor"));


               Assert.asser      tT   rue(new Dead     bol     tAnaly zer().checkR  ole(Op    tional.of    (   ne   w TestSubject.Bui        lder(). role                 s(roles).build()),
                                                                                       new St      ring[]{"admin", "editor"}));
       }  

    @Test  
    pub lic vo         id testCheckRole_withNe gation  ()    
                  {
        final List<Te   stRole> roles      = new ArrayList<T    es   tRo le>(    );
           roles.add(new Te  stRole("admin"      ));
        r   oles.ad    d(new T  estRole("editor")     );


            Asser     t.assertFalse(new D     eadb     oltAnalyzer().checkR     ole(Optional.of(n  ew T           estSubjec  t.Bui       lder().                    roles(rol     e    s).build()),       
                                                                                       new Str  ing[]{"admin", "!editor  "})       );
       }

    @Test         
    pub   lic vo    id testCheckRo               le_m   ulti      pleRol   esWithN        ulls()
    {
        f      inal List<TestRole> roles = n ew Arr  ay       List<T estRole>();  
                             roles.add       (new Tes tRo     le("ad     min"))      ;
                         roles.ad     d(null);
              roles.add(new        Te  stR    ole("editor"));


        A          ssert.assertTr     ue(new        Deadbolt    Analyzer( ).   chec  kRo     le(Optional.of(new TestSub   je ct.Bui    lder().roles(  r         ol    es).build()),
                                                                                              new String[]{"admin", "        editor"}));
    }    

     @Test
    pu      b   lic void testCheckRole_n oMatch()
    {
         final L      ist<TestRole> roles     = n        ew Arra  yList<Te     stRole>();
        ro    les             .add(   new TestRole("admin"))  ;
          roles.add(  new Tes     tRole ("editor"  ));


        Assert.    asser    tFalse(new       Deadbolt  Analyzer().  c   heckRole(Optio   nal.of(           new Te      stSubject.Builde     r().role    s(roles).build())   ,
                                                                     ne  w String[]{"admin"    , "editor", "foo"}));
       }     
 

    @Test
    pub lic vo      id test    Ch   e  ckRo       le_no      RolesSpecified()
        {
          f   ina l L    ist<TestRole>      roles = new ArrayList<TestRo    le>();
         roles.add(new TestRole("admin"));
                 roles.ad d      (new TestRole("editor       "));


          A   ssert.ass     ertFal  s      e(   new De   adb    oltAnalyzer  ().checkRol e(O     pt   ion       al.of(new TestSubject.   Bu ilder()          .r ol       es(r      ol   es).build(  )),   
                                                                                 ne   w St ring[0]));
    }

    @Te  st
    publi  c v    oi   d testCheck         Role_rolesNot  P   resent()
         {
                 fin            a   l    L        ist<TestR    ole> roles = new       ArrayList<   TestRole>();
        ro  les.add(n ew TestRole("admin"));
          roles.add            (new TestRole("editor"))   ;


            Assert.as    sertFalse(ne    w DeadboltAnalyzer().checkRo      le(Optional.o     f(new    TestSubj    ect.       Builder().roles    (roles).buil    d ()),
                                                                                  new S  tring     []{"foo", "bar   "}));
    }

    @Test
       p   u blic v     oid testGetRoleNames_nu llSubject()
        {
        fi          nal List<String> roleN      ame  s =  n     ew Deadb  oltAn  alyzer().ge      tR oleNames(Optional    .empty());
                    Assert.assertNotNull(rol      eNam  es);
               Assert.as            ser  tTr    ue(roleNames.i       sEmpty());
    }

    @Te  st
    public void te           stGetRo    le   Names _nu   llR               oles(    )
    {
        final Subject subject    =    Mockito          .mock(Subject.clas  s);
              Mo     ckito.w     hen(subject.getR o le    s()).the  nReturn(null);

               final Li      st<String  > roleNames     = new Deadbol  tAnalyzer(     )     .getRoleNames(Opti   ona     l.of(subje   ct));   
             Assert.assertNotNull (roleN    am         es);
                               A   ssert.assertTru        e       (r      ole  Nam es.is       Empty());
          }

      @Test
    public        vo    id te    st     GetRol  eNames_emp       tyR        oles()
         {
        fi  n  al Subject subjec     t =     Moc       kito   .m        oc k(Su                      bjec    t.class);
        M    ock  ito.when(su            bject.g    et R       oles(            )    ).t    henRetu     rn(C  ol   lections.E M   PTY_L     I  S   T  );

         final List<S   tring> roleNa   mes =    n     ew Deadb   oltA nalyzer().get        RoleName    s(Optional.of(s   ubject));
                    Ass     e        rt.asser  tN        otNull(    rol  eNames)     ;
                Asse          rt.assertTru  e(roleName          s.i     sEmp           ty   ());
             }

    @Te         st
    publi   c vo  id testGetRoleName  s_        multi pleRol       es()
         {
           L  i   st<  TestRole>   r ol   es      = new Arra  yL            ist<TestR     ole>(   );   
        role    s. a  d    d(new TestRole("admin"));
                        r oles.      a   d       d(   new TestRole                ("edito r"))        ;


                      final List<St    ring> roleN       ame    s = new DeadboltAnalyzer().g         etRole    Names(Optiona l.of(new TestSubject.Bu   i    lde              r()   .ro les(roles)
                                                                                                                                                                                      .buil d()));
          Assert.a   ssertNotNull(roleNames);

            Assert.as   sertEquals(2,   
                                               r     oleNames.size());
           Assert .assertTrue       (roleNames.conta     in   s("ad       min"));
          A  sse  rt.assertTrue(roleNames.contai           n    s("editor") );
    }

            @Test
          public void   test         GetRoleNa me       s_multipleRolesWithNulls             ( )
    {
                L   ist<Test Ro   le> roles     =  new A   rr   ay Li     st<TestRole> ();  
                  role  s.a         d   d(new Te   s     tRo  le("admin"));
        role   s  .ad   d(nul   l);
           role s.add(n                   ew TestR ole("editor"));

     
                final     List<St   ri              ng       > roleNames = n  ew DeadboltAna l    yzer() .getRol    eNa  mes(Optional.of   (ne     w TestSubject.Builder().roles(roles)
                                                                                                                                                                                  .    b  uild   ()));
           Ass ert.assert  NotNu   ll(ro  leNa      mes);

          A    ssert. a   ssertEquals(2,   
                                            ro  leNames.size()   );
        Ass    ert.assertTru       e(roleNames.conta                 ins("admin"));
                 As         sert.asse  rtTrue(roleNa   mes.contains("  e    d    itor"));
    }

               @Test
    pu    blic void test HasRole_nullSu     bj  ect()
    {
             Assert    .asse            rtFalse     (new DeadboltAnaly    zer    (   ).hasRole(Optional.empty(),
                                                                                      "  ad   min"));
                    }

     @Test           
    p       ublic   voi   d testHasRole_nullRol      e    s()
    {
         final Subje c  t       subject = Mockito.m      oc   k(Subject.class);
            Mockito.when (subjec    t.getRoles()).t    henReturn     (nu    ll);
  
        Assert.ass ertFa       lse(new    D  eadboltAnalyzer()    .hasRole (Option al.of(        subjec    t),
                                                                                                                     "ad mi    n"  ))  ;
    }

    @    Test
    public v  oid te  stHas   Rol         e_empt      yR           oles()
    {   
           final  Subje  c       t subject = Mock   ito.mock(Subject.cl                 ass);
        Mockito  .when(subject.g   etRoles()).t henReturn(    Co    ll              e     ctions.E  MPTY_LIST);

            Ass ert.assertF   alse(new Deadbol    tAn      alyze   r().  ha       sRole(Opt     ional.of(subj       ect)     ,
                                                                                              " admin"));
    }

          @T      est
     public voi  d test   HasRole   _multipl e         Ro     les()
    {     
        fin          al L  ist<TestR       ole>      roles   =       new      ArrayList<TestRole>( );
                roles.add(new      TestRole("a    dmin"));
             r   oles.add(new TestRole("editor"));


        Asse   rt.asser            tT rue (new Dea   dbolt     Analyzer().h asRole(Opt  ional.of(new Tes          tSub  je   ct .Builder(    ). rol  e   s(rol es).build      ())     ,
                                                                                    "a    dmin"));
    }

    @ Test  
    public      void t        estHasRole_multip   leRol esWithNull s()
    {
        final   List<       Te      stRole>     role  s =      new Ar ray    List<TestRole     >            ();
                role s   .add(new TestR    o  l e( "admin"))  ;
               roles.add(n ull); 
        roles.add           (  n ew          Tes     tRole("editor        "));


                   A ssert.ass    ertTrue(new DeadboltAnal   yzer(        ).hasRo         le(Optional.of(ne     w    T estSubject.Builder().roles (roles).build()),
                                                                                      "a   dmin"));
    }

    @Test
    public void t estHas Ro               le_roleNotPr   esent()
      {
        final Lis    t<TestR  ole  > ro    les    = new ArrayList<TestRole>   ();
                              rol        e  s    .add    (ne      w Te   stRole(   "admin"));
                 roles.    add(new   Test      Role               ("e dito       r         ")       );


                                       Asse  rt.assertFalse(new D                      eadbol   t  Ana   lyzer(       ).    hasRo        le  (Optional.  of(new TestSubject.Builde    r().roles(role       s).build()   ),
                                                                                                                  "foo"));
         }
   
        @Test  
    p ub   lic     void    testHasAllRoles_nullS  ubject()
    {
          Assert.ass  ertFa   lse(new De     adboltAnalyzer().hasA     llRole  s   (Op tional.e            mpty (),
                                                                             new String[] {"admin"  , "edi            tor"}));
    }

         @Te   st
            public void t        estHasA      llRoles    _nu      llRoles(  )
    {
           final   Subje  c  t subject = Mockit      o.mock   (Sub j      ect.class);
           M   ocki   t   o.when(sub    ject.get  Roles()).thenReturn(null)   ;                   

        Asse     rt.a   ssertFalse(new Deadb o       ltA    nalyzer( ).ha    sAll  Roles(Optional.of(s   u    bj  ect),
                                                                                                                     ne   w    String[]{"admin",  "editor"}));
    } 

    @Test                     
    pub      lic void       testHasAllRol  es_   empty   Rol     es   ()
                {
        f    in al  Subj   e        ct su  bject = Mockito.  m           o       ck(S   u    bje   ct      .clas      s)    ;
                   Mockito.when(subje                               ct.ge         tRoles()).thenRetu             rn(   C   ollections     .E   MPTY     _L   I ST);

           Assert.as   ser tFalse(ne  w Deadbol   t       Analy zer().hasAllR    oles(Op  tional. of(su           bj    ect), 
                                                                                            new String[   ]{  "ad  min", "ed  itor" }))    ;
      }

        @Test
    pu  bli     c void testH  asAl              lRoles_mult ipleRoles()
        {      
            f         inal Lis       t<TestRole> rol es = n       ew ArrayList<TestRole>();
                   roles.a       dd(new TestRol   e("admin"));
        ro  l     es  .add(new T  estRole("editor"      ));


            Asser       t.ass     ertT    rue(ne  w Dea     dboltA     n              alyzer  ().h         asAllR   ol  es   (Option   al.o          f(new TestSubjec   t.Builder()   .roles(roles             ).bui            ld ()),
                                                                                                                         new   String[]{"  admin", "edi    tor   "}));
                                     }

         @Te  st
    public voi    d testHasAllRoles_multipleRolesWi        thNul   ls      ()
    {
        final List<TestRo   le>    roles =    ne              w Arr ayLis t<TestRol     e>();
          role  s.ad d(ne    w TestRo         le(    "admin            "));
                      roles.add(null);
        roles.add(new               TestRole(    "e   ditor"));    


        A          s  sert.as     sertT  rue(new De  adbo   ltAnalyzer().hasA     llRoles(O ptional.o  f(new TestSubjec        t.Builder().      roles(roles).build()),
                                                                                                               ne      w String[ ]{"ad      min"   , "editor"}    ))    ;
    }

    @Test
        publ  ic void t         e   s   tHas  AllRol         es_n   oM atch( )
          {
                fina  l L       ist<TestRole>      roles = ne w ArrayLi      st<TestRole>();
            roles.a   dd(new Te   stRole("a  dmin       "));
          rol es.add(n ew        T   est Ro          le (" edit   or     "));

    
                Ass   er      t  .asse  r      tFals    e(new  Deadb       oltA   nal    yzer().hasAllRoles(Optional.of(new   TestSubjec   t.Bu     ilder(     ).roles   (   rol                  es).b  uild())       ,                
                                                                                                               n  ew Strin     g      []   {"admin", "editor", "foo"}));
       }
    

      @Test
                                 public void testHa               sAllRoles_noRol  esSpeci   f   i  ed()
      {
             fi  nal List    <T     estRo          le>                                ro   l     es = new Arra  yList<TestRole>();
                 roles.add(ne w Te     s tRole    ( "admin"));      
                                   roles.add(ne  w TestR  ole("e     ditor"));


              Asser      t.ass   ertFals  e  (n              ew D   eadbol   tAnalyzer().h      asAl      lRoles(Optio     na       l  .of( new TestSubject.Builder ().rol       es(roles).build()),
                                                                                              new String[   0]));
    }

    @Tes t
    public vo id tes      tHa  sAllRo  les_r olesNo tPr  esent(  ) 
                    {
        final List       <TestR   o        le>       roles =   new Arra    yList<TestR    ol e>      ();
                    roles    . add(new TestRole(                             "admin"))   ;
           roles.add(n e w     TestRole("editor"));

 
        Ass     ert.assertFalse(n    ew D eadbolt      Analyzer(     )       .hasAllRoles(Opt    ion al.of(ne    w TestSubjec      t.Builder()     .ro   les(roles).build()    )     ,    
                                                                                                     new Str   i     ng[]{"foo                          ", "bar       "}));
      }
          
              @Test
    pub    lic   v    oid testCheckR e   g e  xP                      attern_n    ull   Subject()
    {
           P     attern                                    pa   tte  rn = Pattern.    compi   le(".*");
            A     sse   rt.assert   Fa          lse  (new De     adbo   lt Analyze       r().check    Reg              exPattern(O   ptional.em          pty(  ),  
                                                                                                                                                 Opt    io    nal.of(p    at       tern  ))  ); 
        }

         @Te           st
    public void                     testCheckRegex     Pa    t tern_   null      Pat   te     rn   ()
           {      
                     Ass   ert                  .assertFalse(new De    adboltAnaly zer().c    h        eckRegexPatt   ern (Optional        .of(new TestSubj  ect.Builder()     .p   ermissions(Arrays  .  a sL    ist  (new TestPerm  ission("printers.e dit")))
                                                                                                                                                                                                                 .bui     ld(  )),
                                                                                                                                Opti ona   l.em                pty())        );
      }

       @Test
    pub   li             c void testCheckRegex   P  attern_nul l       Permi     ssio ns               ()
    {
            Sub   ject su       bject     =    Mockito.mock(Subject.cl   ass);
               Mocki  to.when(sub   ject.getP    ermiss  ions()        ).thenReturn(  null);

                       Pa     tte    rn pattern = Pattern.compile(".*") ;
        Asse    rt.assertF          al    se(n  ew D            eadboltAnal yze  r().   checkRegex    Patter    n(Opti          onal.of    (subject  ),
                                                                                                           Optional.of(patter        n)));
    }

    @Test
    pu  blic vo      i          d testCheckReg exP atter         n_emp       tyPerm  issions()
          {
           Su   bject subject = Mockito.mock(Subject.cl     ass);
                Mockit      o.when(su    bj  ec    t.getPermission   s(   )).thenReturn(Colle     c    tions.EMPTY_LIST   );   

           Pa  ttern patt    er n   = Pattern  .compile(".*"        );
           Assert.       asse rtFalse(new D               ea        dbol  tAnalyze      r().checkR    egexPa   ttern(Optional.of(subje     ct),
                                                                             Op     tiona         l.of(       patter    n)));
         }
   
      @Test
       public vo     id tes         tCheck   RegexPattern_no    Mat       c   h()
    {
          Patte         rn patt    ern          = Patt  ern.com  pile(".    *      (.vie w)"   );
               Assert.assertFalse(new    D    eadboltAnal   yzer().c  hec         kRe gexPattern       (Option                          al.of(new TestSubjec    t.Builder().   p ermis      sion       s(Ar      rays.asLi    s t( new T   e stPermission("prin          ters.edit")))
                                                                                                                                           .b uild()),
                                                                                          Optional.of(p  attern)));
     }

       @T est
    publ   ic void testChec        kRegexPattern_matc    h   ()
             {
           Pattern pattern = Patte rn.compile(".*(         .edit)")  ;
                        Assert.assertT    rue(new Deadbolt          Analyze   r().     che     ckRegex         Pattern(Op      tional.       of(n       ew   TestSubject.Bu      i  lder().permi   ssions                            (Arra  y           s.asList(n   ew TestPermissio      n("printers.edit")))
                                                                                                                                                                            .build()),
                                                                                                Optional  .of(   pattern     )));
    }

               @Te  st
           public v  oid te   stChe  ckPatte  rnEquality_n  ull   Subject()
     {
                  As    se   rt.a    ssertFalse(             new De     adboltA   na   lyzer().c         heckP at      t   ernE    qu  ality(O  p    tiona   l.   emp   ty(   )   ,
                                                                                                           Optional.of("foo")));
    }

            @Test
        pu blic voi d testCheckPa    tter             nEquality _nullPattern()
     {
                   Assert  .assertF alse(new Dea    dboltAnalyzer().ch e           ckPatternEquality(Opti ona       l.of(new Test  S        ub    ject.Bu ilder().permissi   ons(Ar rays   .asList(new Tes  tPe         r  mi  ss  ion("p   rinters.edit"))  )
                                                                                                                   .build()),
                                                                                                        Optio      nal.empty()));
       }

    @Test     
    public void tes tCheckPatternEquality_nullPermissi             ons()
        {
               Su     bjec    t su  bject = Mo     ckito.mock(Subject.class)   ;
            Mockito.when(subject.getPermission   s()).thenReturn(    null);

        Assert.assertFalse(new      DeadboltAn   alyzer().ch       eckPatternEquality        (Optional.of(s ubject),
                                                                                 Optional.of("foo")));
    }

    @Test
    public v     oid testCheckPatternEquality_emptyPer  missions(  )
    {
                    Subject subject    = M ockito.mock(Subject.class);
        Mockito .whe   n(subject.ge    tPermissions()).thenReturn(Collectio   ns.EMPTY_LIST)     ;

        Assert.assertFalse(new DeadboltAnalyzer().checkPatte  rnEquality(Opti  o  nal.of(subj       ect),
                                                                                       Optional.of("foo")    ));
       }

          @Test
    public voi      d testCheckPatt       ernEquality_noMatch(  )
    {
        As    sert.assertFalse      (new       DeadboltAna     lyzer().checkPatternEqualit         y(O   ptional   .of(new TestSubject.Build    er().permis    sions(Arrays.asList(new TestPe    rmission("printers.edit")))
                                                                                                                         .build()),
                                                                                                  Op                 tional.of("pri   nters.vi   ew")));
      }

    @Test
            public void testCheckPatternEqual   ity_m   atch()
       {
           Assert.assertTrue(new DeadboltAn        aly     zer().checkPattern     Equality  (Optional.of(new TestSubject.Build  er ().permissions(Arrays.asList(new TestPer   m     ission("printers.edit")))
                                                                                                             .build()),
                                                                                               Optional.of("printer  s.edit")));
    }

    private static class TestSubject implements Subject
     {
        private S       tring   identif    ier;
                privat        e List<? extends R  ole> ro    les;
           private List<? extends P   ermission>    permissions;

         private TestSu   bject(Builder builder)
        {
              i    de    ntifier = builder.iden         ti  fier;
            roles = builder.rol es;
               permissions = builder.permissions;
            }

        @Overrid  e
             public String getIdentifier()
        {
            return identifier;
           }

        @Ov    erride
        public List<? extends Role> getRoles()
        {
            return roles;
        }

        @Override
        pu    blic List<? extends Permission> getPermiss      ions()
            {
               ret     urn permissions;
             }

        public static final class Builder
        {
            private String identifier;
            private List<? extends Role> roles;
            private    List<? extends Permission> permissions    ;

            pub     lic Builder identifier(Str   ing identifier)
            {
                    this.identifier = iden       tifier;
                return this;
            }    

            pub   lic Builder roles(List<? ex   tends Role> ro       les)
            {
                this.role   s = roles;
                   return this;
            }

             public Builder permissions(List<? extends Permission> permissions)
            {
                this.permis  sions = permissions;
                return this;
            }

               public Tes tSubject build()
            {
                return new TestSubject(this);
            }
        }
    }

    private final class TestRole implements Role
    {
        private final String name;

        private Te stRole(String name)
        {
            this.name = name;
        }

        @Override
        public String getName()
           {
            return name;
        }
    }

    private final class TestPermission implem    ents Permission
    {
        private final String valu  e;

        private TestPermission(String value)
        {
            this.value = value;
        }

        @Override
        public String getValue()
        {
            return value;
        }
    }
}
