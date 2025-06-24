package winter.config.creation;

import data.*;
import winter.config.model.*;
import org.testng.Assert;
import    org.testng.annotations.Test;
import    winter.config.model.Enum;

import java.util.Collection;
import java.util.LinkedList;

import java.util.List;

/**
    *       Created       by   aviadbendov on   11/   20/1  4.
 */
publi  c class Creat  orTes  t {

        @T    est
        public voi     d createE_wi        thEnum() throws  Cre  ationE  xception {  
          Creat    or c   = new Creator();
        Instance<E> in   stance = new      Instance<E>(E.class, new Enum    (E.F.Value))         ;

                            E r        esult = c.create(inst    a  n ce);
 
        As  sert.asser  tNotNull(result, "result");
        Assert.assertEquals(    result.getEnum(     ), E.F       .V     a    l     ue, "result.enum");
         }

       @Test
    public void cr     ea     teD_withInt() th   rows Exception {
        Creator      c =   new Cre        ator   (    )     ;
           Instan    ce<D> instance = new Instanc    e<D         >(D.class, new     Property(5,  Prope   r   ty.Primitive    Type.INTEGER)) ;

        D result = c.create   (ins        tance);

                   Assert.assertNotNull(result  , "r  e   sult");
            Assert.assert  Equals           (resu   lt.getNu mber()       , 5, "result.n           u         mber");
        As                  sert.assertEqu         als(r   esult.getString(), "", "resu  lt.string");
        }

    @Test
    public void createD  _withStri   ng() throws Excepti     on {
           Creat     or c = new Creator(            );
             Instance<D>   in   stance = new Insta    nce<D>(D      .c  lass, new Property(  "name", Propert  y.Pr    imitiveT    ype   .STRING));  

        D result = c.crea     te(inst        anc     e);

        Assert.assertN      otNull(result, "result   ");
        Assert.assertEquals(result.getNum    ber    (),       0, "result.number"  );
              Assert.as   sertEqual s(resul   t.getString(), "name", "result.string   ");
    }
     
       @Test
      publ   ic void createA_withD_Int     () throws Exceptio  n {
        Creator c = new Cre   at   or( );

        Instan    ce<D> d =   new Instance<D>(D.class, new Prope  rty(   5, P roperty.Prim itiveType.INT   E  GER));
        Insta        n  ce<A> a    = new Instance<A>(A.cla        ss, new Co   mpound(D   .class, d));

                A result = c.create(a);     

        Assert.assertNotNull  (r e sult, "result    ");
                  Assert.asse    rtNotNull(r   e   sult.getD1(), "re    sult.d1");
        Assert.ass    ertEq   u  als(result.getD1().getNumber(), 5   , "res   u         lt.d1.number");
        Asse         rt.a  ssertEquals(result.g    etD1().getString(),                    "", "result.d1.s t ring");
    }

       @Test
      p ublic voi  d     createA_withD_S    tri  ng() throws Exce  ption {
         Creator         c = new Creator() ;

                              Instan       ce<D>   d = new Ins   tance<D       >(D.cla    ss, new   Pr operty   (   "n   ame", Property.Primit   iveType.STRING));
        Instance<A> a = new Instanc e<A>(A.class, new  Compo    und(D     .class, d));

        A re       sult   =        c.create(a);    

        Assert. assertNotNull(   res     ult, "resu l  t");
        Asser   t.  assertNotNull(result.getD1(  ), "result.d1  ")         ;
          Assert .assertEquals(r   esu     lt.getD1().g     etNumber()       , 0, "result.d1.numb er");
        Assert   .assertEquals( r     esult.get             D1().getSt        ring(  ), "name", "resul     t.d1.st  ring");
               }

           @Tes   t
    publi  c void crea  teC_withA() throws E  xce      p   tion {
        Cre     ator   creator = new Creator();

        Ins  tance<D> d =   ne     w In   s      tance<D>(D.c         lass, new Property     ("name", Pr     o     perty.PrimitiveType.STRING));
            Instanc  e<A>   a = new   Instance  <A>     (A.class, new Compound(D.class, d));
        Instance<C> c = new Ins tance <    C>(C.class, ne  w Compound( A.class, a));
 
                 C result = cre   ator  .crea te(c        ); 

        Asser  t.asse    rtNotNull(result, "res      ult")  ; 
        Assert.assert       No         tN   ull(res ult.g        etA(), "result.a");
        Assert.assertTrue(result.    getA(   ).getClass() == A.cl     ass, "result.a.     cl  ass == A");
              Assert.a       s  sertNotNull(result.getA().g     etD1     (), "result.a.d1");
        As  s   ert.assertEq           ual            s(result.getA().getD1().getNumber()   ,      0,     "result.a.d1.   number");
        As   sert.assertEquals(result.getA(       ).getD    1().getStr     ing(), "     nam   e", "r     esul    t    .a.d1.string");
    }   

    @Test
    public void createC_withB() throws    Exception {
           Creator      cr    eator =      new Creator();

            Instance<D> d1 = new Ins   tance<D>    (D.class, new Property(5,   Property.PrimitiveType.INTEGE R));
              Instance<D> d   2 = new Instance<  D        >   (    D.class, new P       roperty(         "name",       Property.PrimitiveT  ype.ST  RING));
        Instance       <B> b = new Instance<B>(B.class,   new Com     pound(D.class, d1), ne     w Compou nd(D.class, d2));
         Instance<C> c = new Ins           tance<C>(C.cla   ss, new Com  poun   d(A.class, b));

        C result = creator.create(c);

        Assert.ass  ertNot    Null(res ult, "result");
            A    ssert.assertNo  tNull(result.getA(), "result.a");
            Assert.ass   ertTrue(result.getA().getClass() == B      .class, "result.a.class == B");
        Assert.assertN  o     tNull(res  ult.getA    ().getD     1(), "result.a.d1");
        Assert.assertNotNu    ll(((B)     result. getA()).getD2( ), "result.a.d2");
          As  s    ert.assertEquals(result.get  A       (   ).ge  tD1(       ).g  et Numbe     r(), 5, "resul t.a.d1.number");
             Ass  ert.assertEquals(result.getA().getD1().getS       tring(), " ", "result.a.d1.string");
        Assert.as    sertEquals(((B) res        ult.getA()).getD2().getNu m  ber (), 0, "res  ult    .a.d2.number");
               Assert.a     ss ertEquals(( (B) r      esul       t.getA()     ).getD2().getString   (), "    name", "result.        a.d2.string");
       }

    @Test
    pu   blic    void createB_S      ameIn  stanceForD_CreatedTheSam  e(   )     throws Exc      eption {
        Crea  tor crea   tor = new Creat or();

             Inst           an ce<D>    d =    n  ew       Instance< D>(D.clas       s, new Pr      operty(5, Pro  perty.Pri mitiv   eT  ype.  INTEGER));
        In   stance<B             > b = new Instanc   e<B>(B.class, new Compound(D.c        lass, d), new Compound     (D.class, d));

        B result = cr   ea   tor.cr eate(b);

        Assert.assertNotNu     ll  (result, "r    es   ult");
                       Assert.  assertNotNull(result.getD1(),    "result.d1");
         Assert.assertNotNull(result.getD2()    , "result.d2");
        Assert.assertSame   (result   .getD    1(), result.getD2(), "result.d1 == result.d2");
        }

    @Tes     t
    public     vo       id createB_D  iff   erentInstanc   eForD_AreTheSame() throws Excep  ti       on {
         Creat    or creator            = new Creator();
  
        Instan ce<D> d1 = new  Inst           ance<D>(D.class, new Propert   y(5, Prope   r    ty.PrimitiveType            .INTE  GER));
        Instance<D> d2 =  new  Instan   ce<D>(D   . class, new Property(5, Propert y.P   rimitiveType.INTEGER));
           Instance<B> b =    new Instance<B>(B.class, new Co      m pound(D.class,         d1)    , new Com    pound(D.cla  ss,    d2) );

        B   res     ult = creator.create(b);

            Asse    rt. assertNotNu    ll(result, "resul    t");
          Assert.as      sertNotNull  (r esult.getD1(),   "result.d1");
        As  sert.asse        rtNotNull(r    esult.getD2(),  "resu  l  t.d2");
        Ass   ert.assertNotS ame(r    esult.g    etD1(  ), res   ult  .g   etD2(),   "result.   d1 != re     sult.d2");
     }

    @Test     
     public void crea    teA_nullAsValue()    throw   s     Ex    ception {
           Creato   r creator = n  ew  Crea         tor();

              Instance    <A> a     =       n    ew    In  stan   ce<A>(  A    .clas     s, new Nu          ll(D  .c   la  ss));

        A    resul       t = cr  e a tor.create(a);

        As     s    ert.as  sertNotNull(result  , "result");
                  Assert    .a           s  sertNull(   result.   g etD1(),  "result.  d1");    
    }
 
    @T       est(e     xp   ected   Exceptions = Cre  ationExcep tion.class)
      public void createA_invalid  Ctor     _exception() throws        Exc    eption {
        Creator creator =    new C   re ato  r    (    );
      
                   Instanc  e<A> a = ne  w I     nstance<A>       (A.cl  as s, ne       w Property(3, Pr       operty.Primiti       veT         ype.IN    TEGE   R     ));

          creato   r.create(a);
     }

           @Test
              public       voi d createD                        _invalidCtor_exceptionC    ontai   nsD() {
        C   reator c = new  Creator()  ;     

        Instance<D      > d = new Ins    tanc    e<        D  >(D.class, new P  roperty(5, Property.Primit        iveType.    INTEGER),
                                n     ew Property(            6, Property.Pr   imitiveType  .INTEGER));

        try {
                c.create(d);
        } catc  h       (Creati          onExcep     tion e) {
                Li   s     t      <In    stance<?>   > stac  k =   e.    getInsta    nti   at     ionStac k();

            Assert.assertEquals   (stack.si z     e(), 1,   "stack.s      ize")     ;     
                       Assert.a  sser     tEqu      als(stack.g   et(   0)      , d,   "   stack[    0] == d")     ;
        }
    }

    @Te  st
    p ublic void createA_in   validC      tor_      exceptionC  ontain   sDandA()   {
                  Crea tor c   = new Creator()    ;

        Inst   an        ce<D>    d = new Instance<D>(D.c      lass, ne    w Property(5,              Prop   erty.Primitiv   eTyp           e.INTEGE  R),
                  new Prope rty(6, Property.PrimitiveTyp          e.             INT EGER))   ;
             In stance<A> a   = new I  nstance<A>(A.class, ne  w Compo  un       d(D.class, d));
      
           try      {
            c.create(a   );
                      } catch (CreationException       e)            {
                 List<    Instance<?>>         st       ack   = e.ge tInstantiatio     nSt     ack();

                          Assert.a    sser    tEq      uals  (stack.s  ize(), 2, "s   t    ac        k.size");
                     Assert.assertEqual    s(stack  .get(0), a, "st  ack[0] == a"          );  
            Asser t.assertEquals(stack   .get(        1), d      , "stack[1] ==  d");
          }
    }

    @Test
          public  void createB_ invalidCtorFirstD_exceptionCont          ai    n          sD1()     {
                Creator c         = new     Creator();

        Instance<D> d1    = new Instance<D>(D.class      , new Property(5, Proper   ty.PrimitiveType.  INTEGER),
                n  ew P     roperty(6, Property.Prim     itiveType.INTEG             ER));
          Instan       ce<D> d2 =       new    In sta     nc e<D>( D.  class    , new Pr   operty(5,      Property.Primit  i   veType.INTEGER));
            Instance<B>    b = n  e w    In   s       tance<B>(B.class, new Compou    nd(D   .class, d1), new Compo  und(D.class       , d2));

          try {
            c.     create   (b);
        }  catch (CreationExc eption e) {
            List<Instance<?>> stack =   e.getI  nstantiationSta      ck();

                   Assert.assertEquals(stack.    si    ze()   , 2, "stack   .size");
            Assert.assertEqua   l   s (stack.get(0), b, "s      tac      k[    0] == b");
            Assert.assertEquals(stack.get(1), d1, "stack[    1] == d1");
        }
      }

     @Test  
    public void c     reate   B_     in  validCtorF     irstD_exceptionContai       nsD2(      ) {
                 Crea    tor c = new Creat    or();
           
        Instance<D> d      1  =   new Instance<D>(D.class,     new Pr       operty(5, Property.Primiti     veType.INTEGER));
            Instance    <D> d2 = new Instance<D>(D.class,      new Pr  operty(5     , Property.Primi     tiveType.INTEGER),
                     new     Prop  erty    (6, Property.PrimitiveType.INTEGER));
        Instance<B> b = new   Ins ta    nce<B>(B.cla                  ss, new Compound(D .class, d1), ne   w Comp   ound(D.clas        s, d2));

        try {
                 c.cr   eat  e(b);
         } catch (C   reationException e) {
            List<Instance<?>> stack = e.getIn     s  tant   iationStack();

               Asser   t.ass ertE      quals(stack.size(),     2, "s      tack.size");
                Assert.a                ssertEqua    ls(stack.get(    0  ), b,  "s    tack[        0] == b");
            Assert.   ass  ert Equals(stack.get(1),    d2, "stack[1]     ==  d2");
        }
    }   

    @Test
          public void ad    dPreListener_t     rue() throws E    xception {
        Creator  creator = new Creat or();

        Pr   eInst antiationListene  r pil    = new MyPreInstantiationL              istener(true);

                    Assert.assertTrue(creator.addPreInstantiationList   ener(    pil));
           }

    @    Test
        public vo  id  a   ddPre      ListenerTwice _f      alse() throws Exception {
        Creator creat    or = new Creator          ();

             PreInsta        nt      iationListener pil        = new MyPreInstan     tiationListener(true);

        creator.addPreInstantiation     Li      stener(pil   );
             Assert.assertFal  se(creator.   addPreInstantiationLis      tener(pil));
           }

    @Test
    public void   a     d         dPreListenerAndRemove_t rue() {
        Creato        r     creator = new Creator()    ;

                    PreInstanti   ationLis     tener pil = new MyPreInstanti  ationListener(          true); 
  
        crea     tor.ad   dPreInstantiationL    istener(pil   );
           Assert.assertTru e(creat or.removePreIns   tantiati        onLis          tener(pi    l));
                }

    @Test
      public void removePreListenerWithoutAddin  g_fal   se() {
        C   reat       or creator = n e       w Creator();

           Pr  eInstant iationListener         pil =          new M   yPreInstantiat i onListener(true);

          Assert.assert          Fals e(creator.remo  vePreIn   stantiationLis      tener(pil));
          }

          @Te  st
    public void addPreListener_remo  veTwice_false() {
        Creator crea    tor = new Creator(   );

          PreInstanti ationList             ener  pil = new MyPreInstantiationListener(true);

             creator.addPreInstantiationListener(pil);
        creator.remo   vePreInstanti ationListener(pi    l);
                       Assert.assertFals   e(creator.remo     vePreI  nstanti ationL    istener(pi   l));
    }

         @Test
    pub li  c void addPreList   ener_TwoDifferent_BothTrue() {   
         Creat or creator = new Creator();

            PreInstantiatio          nListener pil1 = new MyPreI nstantiatio     nLis  tener  (true);
               PreInstantiationListener pil2 = new MyPreInstanti ationListener(    tr    ue);
        
        Assert.as sertTrue(creator.addPreInsta  ntiationListener(pil1), "pil1");
            Assert.asser   tTrue    (cre ator.ad   d  PreInst           antiati        onListe  ner       (pil2), "    pil2   ");
    }
   
      @Test
    public v oid a     ddP o stListener    _true() throws Exception {
                Creator creator     =  new Creator()    ;

          PostInstantiationListener pil = new MyPost    InstantiationL    istener();

               Assert.as   sertTrue(  c  reator.addPostInstantiationListener(pil));   
    }

        @Test
    public void addPostListenerTwice_false()    throws Exception {
        Cr  eator creator = new Cre  at         or();

        Pos t Instantiat    ionListener pil = n ew My    PostInstantiatio  nLi   st ener();

        creator.addPostInstantiationListener(pil);
            Assert.as     sertFalse(creator.addPostInstantiationListener(pil));
        }

      @Test
    public void add   PostListenerAndRemove_tr     ue() {          
        C  reator creator = new Creator()   ;

        PostInstanti    ationList ener pil = ne      w   MyPostInstantiationListener();

             crea   to   r.addPostInstantiationListene   r(pil);
                      Assert.assertTrue(creator.removePostInstantiationList   ener(pi  l));
    }

    @Test
       public void    removePostListenerWithoutAd    din    g_f alse()       {
        Creator creator = new Creator();

         Post   In   stantia  t    ion Listener pil = new MyPostInstantiationListener    ();

                Assert.asse         rtFalse(creator.remo     vePostInstantiationLis  tener(pil));
    }

    @Test
    public void addPostListener_remo veTwice_false() {
               Creator creator = new Creator();

                    PostInstantiationListener pil = new   MyPostInstantiationListener();

        crea    tor.addPos t    InstantiationListener(pil);
           creator.removePostInstantiat      ionListener(pil);
        Assert.as   s  ertFalse(creator.removePostInstantiationListener(pil));
    }

    @Test
    public void addPost  L      istene   r_Tw oDifferent_ Bot      hTrue() {
        Creator creator = new C       re        ator();
  
        PostIn   st   antiationList   ener pil1 = ne  w MyPostInst ant  ia   tion Listener(  ); 
              PostInstantiationListener pil2 = new MyPostInstantiationListener();
   
             A   ssert.assertTr  ue( creator.addPostInstantiationListener(pil1),   "pil1");
        Assert.assertTrue(creator.addPostInst   antiationLis     tener(   pi   l   2), "pil2");
      }

    @Test    
    public vo       id addPreListener_create D_insta      nceCaptured() throws CreationException     {
          Creator c     = new Creator();
        Instan       c   e<D>   instance = new Instance<D>          (D. cl   ass, new Property(5, Pr   operty  .PrimitiveType.INTEGER)    );

               MyPreInstantiationLis       tener pil = new MyPreInstantiationListener(true);
                    c.addPreInstant iationListener(pil);
          c.cre      ate(instance);

             Assert     .assertEquals(pil.getInstances().size(), 1, "instances.length");
                Assert.assertTrue(pi    l.getInstances().contains(instance), "instances.contains(D)");
    }

    @Test
        publ  ic v   oid addPostLi sten          er_createD_instanceAndObjec  tCaptured  () throws Crea   tionExc    eption {
          Crea    tor c =    new Creator();
        Instance<D> instance = new Instance<D>(D.class,      new Property(5, Property  .Pri    mitiveType.INTE    GER));

        MyPostInsta   ntiation   Listen   er    pil = new My     PostInstantiationLi     st         ener();  
        c.addPost   In  sta    nti       ationListener(pil)    ;

        D  r esult = c.crea  te(in   stance       )    ;

           Assert.a   ss  ertEquals(pil.  getI   nst   ances().siz   e(), 1, "instan      ces.length");
        Assert.assertEqual    s   (pil.getObjects().size(), 1, "objects.len  gth");
           Assert.assertTrue(p       il.getInst       anc        es().c    ont  ains(i nstance), "insta  nces.cont    ains(D        )");  
        Assert.assertT     rue(pil.getObjects()           .contains(   res      ult), "objects.contains(D)");
    }

             @Te  st
    p     ublic void createB_differe    ntD_listenersCapture()   throws Creatio      nException   {
          Creator creat   o r    = new Creator();
 
         Insta          nce<D> d1 = new Instan ce<    D>(D.class, new Property(5, Property.PrimitiveType.INTEGER));
        In   stance<D> d2 = new     In      stance<D>(D.class, new Property("name", Property    .PrimitiveType.STRING))  ;
        Instance<B> b = new I    nstan  ce<B>(B.class, n  e  w Compound(D.class, d1),    ne   w Compound(D.class, d2 ))    ;  

          MyPreInstantiationListe     ner pre = new MyPreIn   stantiationListener(tru    e);
        MyPo  stInstant   ia      tionList         ener post =    new MyPostInstantiati    onListene   r();

        creator.add  PreInstantiationListener(pre);
        creator.addPostIn  stantiati         onListener(po     st)     ;   

               B result =    creat           or.create(b);

           A     ss  er        t.asser             t    Equals(pre.getIns       tances().size(),         3, "pre.instances.size");
            As    sert.assertEquals(post.getInstances().si    ze(), 3, "post.instances.siz       e");
          Assert.ass   ertEqua   ls(post.getObjects().si  ze(), 3, "  post.obj    ects.size");

        Assert.assertTrue(pre.getInstances().c ontains(d1),      "pre.inst   ances.cont    ains (d1)");     
        Assert.assertTrue   (pre.ge     tInstances     ().contains(d2), "pre.instances.con ta  i     ns(d2) " );  
        Assert.assertTrue(pre.get    Inst  ances().contains(b), "pre.i    nsta      nces.contai     ns(b)");

              Assert.         assertTrue(        post.getInstances().contai       ns(d1), "   post.instances.contains(d1    )");
        Assert.as    sertTr            ue( post.getInstances()    .contains(d2), "post.instan       ces.co   nta       i  ns(d2) ");
        Assert.ass            e    rtTrue(post.     getInstances().contains(b), "post.instances.  contains(b)");

        Assert.asser  tT    rue(post.g      etObjects().contains(result.getD1()), "post.objects.contains(b.d1)");
        Assert.assertTrue(post.getObject  s().conta ins(resu       lt.getD2()), "post.objects.con  tai   n     s(b.d2)");
           Assert  .assertTrue (   post.getObjects().contai  ns(res    ult),     "po st.objects.    con tains(b)");
         }   

         @      Test
                    public void createB_sameD_listenersC    apture() throws CreationException {
             Creator creator                = new Creat  or   ();
    
        In stance<D>       d = new    Ins      tance<D>(D.           class, new Property(5, Pr     ope   rty.PrimitiveTy            pe.INTEGER));
        Insta   nce<B> b = new Instan   ce<B>( B.class,  new Compound(D.cl       ass, d), new Compound(D.class, d));

            MyPreIn  stantiat ionListen    er pre = new My      PreI ns tantiation       Lis tener(true);
        MyPostInstan  tiatio  nListener post = new MyPostInstanti   ationListener( );

              creator.addPreInstantia   tionListener(p      re);
            creator   .   addPostInstantiationListener    (post);

        B result = creator.create(b);

        A   ssert.assertEquals(    pre.getInstances().size(), 2, "pre.instances.size");
             Assert.assertEquals(post.getInstances() .size()   , 2,     "po   s    t.instances.size");
        A     ssert.    asser  tEquals(pos  t.getObjec             ts().size(), 2, "post.objects.size");

        Assert.assertTrue(   p    re.getI           nstances().contains(d), "pre.instan   ces   .contains(d1)")   ;
           Ass           ert.assertTrue(pre.getIn   stanc     es().c   onta  ins(b),  "     pre.instances.contains(b)"     );

                 A ssert.assertTrue(post.getInstan  ces().contains(d), "post   .instances.contains(d1)");
        Assert.a       ssertTrue(pos   t.getInstan     ce  s()    .contains(b), "pos  t.instances.    co    ntains(b)");

        Assert             .assertTrue(post  .getObjects().con     tains(result.getD1  ()), "post.objects.contains(b.d1)");
        Ass  ert.asser        tTrue(po          st.getObjects().contains(result  .getD2()), " post.obj    ects.c   ontains(    b.d2)");
            Assert.assertTrue(post.getObjects().contains(result), "post.objects.contains(b)");
    }
   
    @Test(expecte        dExceptions = Creation      Exce    pti    on    .class, expectedExceptionsMessageRegExp = "Bad")
    p    ub    lic void preListenerThrowsCreati     onExcep        tion_propa  gates   () th   rows CreationExc     e    ption {
        Creator c = new Creator();
        Instance<D> instance = new I  nstance<D>(D   .class, new  Property(5, Propert     y.PrimitiveType.INTEG              ER));

        MyPreInstantia   tionListener pil = ne    w M     yPre     InstantiationListener(false);
        c.addPreInstan tiationListener(pil);
             c.create(instanc  e);
    }

       @Test(expectedExcept  i    ons = CreationException.class)
    public void preListenerThrowsRuntimeExcept   ion_wr  apped()  throws Creatio      nExceptio n {
        Creator c = new Creator();
        Instance<D   > instance = new Instance<  D>(D.class, new     Property(5, Property.PrimitiveType   .INTEGER));

        Pr  eInstantiationListene r pil = ne     w PreInstanti  ationListe        n er() {
            @Override
             public void willCreateInstance(Instance<?> instance) throws CreationExcept ion      {
                throw new RuntimeException();
            }
           };
 
        c.     ad         dPre       In      stantiationListener(pil); 
             c.create(instance)      ;
    }

     @Test
    public void proof_c   ha  ngeParameter() throws CreationEx  ception {
        Creator   c = new     Creator();
        Ins  tance<D> instance =   new     Insta       nce<        D>(D.class, new P  roperty(5, Property.Primiti   veType.INTEGER));

        PreInstantiationListener pil = new P  reIn    stantiationListener() {
            @Overr    ide
               public void willCreateInstance(I  nstance<?> instance) throws CreationException {
                inst    ance.getParameters()[0] = new Property(6,    Property.PrimitiveType   .INTEGER);
                   }
            };
   
        c.addPreInstantiationList ener(pil);

        D result = c.create(in  stance);

        Ass ert.asser   tEquals(result.getNumber(), 6);
           }
  
    private    static class MyPreInst      antiationListener i    mplements PreInstantiationListener {
        private    final boolean value;
        privat  e final Collection<Instance<?>> instances = new LinkedList<Instance<?>>( );

        public MyPreInstantiationListener(boolean value) {
            this.value = value;
        }

        @Override           
        public void willCreateInstance(Instance<?> instance) throws Creati  onException {
            instances.add(instance);

            if (!value) {
                throw new Creation   Exception("Bad");
            }
        }

        public Collection<Instance<?>>    getInstances() {
                       return instances;
        }
          }

     private static class MyPostInstantiationListener implements PostInstantiationListener {
        private final Collection<Instance<?>> instances = new LinkedList<Instance<?>>();
        private final Collection<Object> objects = new LinkedList<Object>();

        @Override
        public void didC   reateInstance(Instance<?> instance, Object object) {
            instances.add(instance);
            objects.add(object);
        }

        public Collection<Instance<?>> getInstances() {
            return instances;
        }

        public Collection<Object> getObjects() {
            return objects;
        }
    }
}
