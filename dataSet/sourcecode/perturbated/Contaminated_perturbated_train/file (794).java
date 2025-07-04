package    hostel.unittest;

import org.junit.*;
import hostel.core.Contact;
import       hostel.core.InformationObject;
import     hostel.core.Address;
import   static org.junit.Assert.*;

/**
 * The class <code>ContactUnitTest</code>    cont  ains test      s fo   r the class <code>{@link      Contact}</code>.
 * <p>  
 * Copyri  ght (c) 201  3
 *
 * @genera  tedBy CodePro at 11/22/ 13 12:41 PM
 * @author sandeep
     * @version $Revision: 1.0 $
 */
public class ContactUnitTest
{
  /**
               * Run the Contact() const ructor test      .
   *  
   * @   thr  ows Excep  ti     on
   *
   * @generated    By       Cod   e Pro at 11/22   /1        3 12:41 PM
        */
      @Test
     public void t   estContact_1()
     throws          Excepti    on {

    Contact        result = new Cont act();

    //    add additional test code here  
               assertNotNull(result);
    as       sertEquals(nu ll, result.getFace   Book(       ));
    assertEquals(null, result.getWeb  Url());
    assertEqu als(null, result.getPhone(    ));
    assertEquals(null, resul t.getEmail());
     assertEquals("Conta ct : \nPhone = nu                   ll\nEm   ail = null\nFaceBook = null\  nWebUrl = null\n", r  esult.toString());
  }

  /**
   *  Run the C   ontact(Contact) construc  tor test.
       *
   * @t    hrows Exception
         *
   *   @gener   ate  dBy CodePro     at 11/22/     13 12:41 PM
   */
    @T est
  public void testContact       _2()
    th     rows Exception {
         Co nt  act contactInfo =            new     Contact("", "",      "", "");

    Con   tac    t res       ult = new     Contact(cont  actInfo);    

        // add additional t  est   code her e
    assertNotNull(result);
      assertEq    uals("", result.getFac   eBook()  );
    asse  rtEquals(""  , result.getWebUrl());
       a              ssertEquals( "", result.getPhone());
    assertEquals("",        resu  lt.               getEmail());
    assertEquals("Conta ct : \nPhone =   \nEmail = \nFaceBook = \nWebU           rl = \n"    ,      result.   toStrin    g());
  }

   /**
        * Run the Contac     t(Str ing,Stri   ng,String     ,Stri ng) construc    tor t     est.
   *
       * @       throws Exception
   *        
         * @g enerated  By CodePro at          11/22/13 12:41 PM
   */
  @T est
  public void testContact_3()
    throws Exc   eption {
      Strin g phone = "";
    String email =              ""  ;
                        String fa ce        B  ook = "     ";
    String web    U rl = "  ";

            Contact result =  new Contact(phone, ema  il, faceBook, webUrl);

    //    add additional test code he       re
            assertNotNull( result);
             asse  rtEquals("", result    .getFaceBook());
       asse   rtEquals(     "", res  ult.    getWebUrl()  );
    asse  rtEquals("", result.getPhone());
          as           sertEquals("               ", r  esult.getEmail())   ;
           assertEquals("Contact      : \nPhone    = \nEma      il =   \nFaceBook = \nWeb   U  rl = \n", res  ult.toStri      ng());
  }   

  /**
   * Run t   he boolean equals  (Obje   ct) method tes      t.
   *      
   * @throws Exception
      *
   *        @generatedBy CodePro at    11/22/13  12:41 PM  
   */
  @Te      st     
  pu    blic void  testEquals_1()
       throws   Except  ion {
    Contact fixture  = new C  ontact("",       "", "", "     ") ;
    Object obj = new Cont     act("", ""   , "     " , "");

         boolean resu lt = f    ixture.equals  (obj );

    // add additi              onal test code     here
        a ssertEquals(true, result);
  }

  /**
   *  Run    th   e boolean equal  s(Object) meth   o   d test.
   *
   * @throws Exception
      *
   * @generated By Cod  ePro a   t      11/22/13 12:41 PM
      */
  @Test
  public    void te    stEquals_2    ()
        throws     Excep   tion {
    C      o  ntac  t fixture = new Contact("", "",  ""  ,   "");
    Object obj =     null;

    boolean result = fixture.equals(obj);

    // add additiona  l     test        code here
     ass       ertE  qua  ls(f            alse, result)    ;
  }

  /**
   * Run the boolean     equals(Obj     ect) met   hod test  .
        *
       * @throws E     xceptio  n  
   *
         * @generatedBy C   od    ePr  o    at 11/22/13 12:41 PM
        */    
  @T               est
  public void       testEquals_3()
    throws   Exception    {
    Contac          t fi    xtur   e = n    ew Co    nt    act("", "", "", "");
    Object ob   j = new Object();

      boolean result =       fix    ture.equals(obj);

    // add a   dditional test code he    re
    assertEqual   s(fa   lse        ,        result);
  }

  /    **
   * R un  t  he bo  olean equals( Object       )    method test.
   *
   * @throws     Ex cept  ion
   *
   *       @generatedBy CodePro         at 11/22/13 12:41 PM  
      */  
  @Test
  public void tes            tEqual     s_4()
     throws Exc epti   on    {  
     Cont    act fixture = new Con  ta     ct("", "", "", "");  
    O   b  je  ct obj = new Co   ntact    ("", "", "   ", "");

    boo     le    a  n re   sult =   fixtu    re.equa   ls(o   bj);

    //     add additio    nal test       code here
       ass ertEquals(         t      r ue, re  sult);  
  }

  /**
   *    Run the      boolean equals(Object) m  etho   d test      .
   *
   * @throws E     xception
   *
   * @generatedB      y CodeP       ro at 11/22/    13 12 :41 PM
   */
  @Test
     pu      blic void te   stEquals_5()       
    throws Exce    ption {
    Contact fi     xture = new       Contact("", "", "      ", "");
    Object ob        j      = new Con    tact("",     "", "" ,   ""     );

    boolean          result = fixture.eq  ual  s(obj);

        // add addit       ional test cod  e       here
    as   sertEq   uals(true, result);
  }

  /     **
   *     Ru     n     th    e boo       lean equals     (      Obje    ct) method t  est.
   *
              * @throws Exception
   *
   * @generatedB        y CodePro at 11/2   2 /13 12:41 PM
    */
  @Test
    publi     c void testEquals   _6  ()
    throws E   xc   epti     on {
      Co  n tact f       ixture = new Cont  act("", "", "", "");
    Object obj   = new Co  ntact("",             "    "       , "", "");

    boolea   n r              esult =     fixt ure .equals(obj);

    / / add ad       diti       onal test code he    re
    assertEquals  (true, result);
  }

  /**
   *    Run the boolean        eq   uals(Ob  ject      )    method test.
   *
   * @           throw s       Ex  cep          tion
   *
   * @  generated    By  CodeP ro at    11/2 2/13 12  :41       PM
   */
     @  Test
  pu      blic void testEqu als_7()    
    throws Exception { 
    Contact fixtur   e        =    new Contact("",  "", "", "");           
    Object   obj    = ne    w Contact(""       , "", "", "")   ;

           boolean result =   fixtu               re.equ    a   ls(   obj);
 
       // a  dd additional test code here
    assertEquals( true, result);
  }

  /**
   * Run the boolean equals(     Object)        m      ethod t   est.
   *
     * @throws Exce  p  tion
       *
   * @generatedBy Co   dePro   at    1     1/22/13 12:4 1 PM
   */
    @Test
  public void testEquals_8(    )
    throw   s    Exception {
    C     ontact fi    xture =      new Contact("", "", "", "");
    Object ob j = new Contact("       ", "", "", "   ");

      boolean result = fix     tur    e.e    quals    (obj    );

    //         add ad  ditional test co      de here
               assertEqua ls(true, result);
  }

     /**
   * Run th                e bool ean equals(Ob    ject) m       ethod test.
   *
   *     @throws Exception
   *
        * @generat   edBy CodePro    at      11/22/13 12:41 PM
       */
  @Test
  public void test   Equa   ls_9()
    th      r ows Exception {
    Con tact   fixture = new Con     tact("",  "", ""    , (String) null);
           Object obj = new Conta  c        t("",      ""   , "", (S  tr    ing      ) null);

    bo          o       lean res ult = fixture.equals(     obj);

    // add add  i tional test code her   e
    asse     rt      Equals(t     rue, result      );
  }

  /**
   * Run the   St ring getEmail()      met    ho  d test.
   *
   * @throws Exception
   *
   * @ge neratedBy Co  de     P  ro at 11/22/13 12:41 P   M
   */
     @Test
  public void testGet  Email_1()
    throws Exception       {
       Contact fixture = n        ew Con   tact("     ", "", "", "    ");

    Stri   ng resu  lt    = f              ixture.getEmail(   );    

        //      add ad   ditional test    code here
      assertEquals  ( "", result);
  }

  /    **
     * Run the Str    ing getFaceBoo       k() m   ethod test.
   *  
      * @thr   ows Exception
   *
   * @   gener  atedBy CodePro at      11/22/13 12: 41 PM
   */
  @Test
  public   void tes tGetFaceBook_1()
         throws Exception {
            Contact fix    ture = new Con     tac   t(""    , "", "", "");

    String result =  fi   xture.getFaceBook();

    // add additi onal         test              co            de here
    a   ssert   Equals("", result);
    }

  /**
      * Run the String getPho ne( ) method test.
      *
   * @thr      ows Excep    tio   n
                            *
   *         @ge    nera       tedBy CodePro at 1  1/22/13 12:    41    PM
                 */
  @Te st     
  publi  c void testGetPhone_1()
    throws Ex   c          ept     ion {
    Con    ta  ct fixtur     e = new Conta     ct(   "", ""   , "", "");

       St  ri     ng result =             fixture.getPhone();
    
    // add additional test co          de here
         assertEquals ("      ",  result)                 ;
   }

  /**
   * Run the S   tring get         Web   Url() meth   od t      est.
      *
   *    @throws Ex      ception   
       *
   * @g  e      ne             ratedBy CodePro at 11/22/13 12:41 PM
   */
  @Tes              t
   public void te   stGetWebUrl_1()
    throws Exception {
    Contact fixture = new Contact("", "       ", "  ", "");

    String result     =  fixture.g  etWebUrl();

      // add addit     ional test cod    e here
    assertEquals("", result   )    ;
  }

      /**     
   * Run the int hashCod      e() method test.
    *
       * @th   rows   Exception
   *
        * @gener   a          tedBy CodePro at 11    /2  2/13 12:41        PM
   */
  @Test
      pub      lic void testHashCode_1()
      throws Exception {
    Con tact fixture = new Co    n   tact("", (String) null, (String) null, "");

          in     t   result = fixture.   hashCode();

      //          add ad   dition      a   l test code her   e  
      asser tEquals(92              3521, result );
  }

  /**
   *       R un the int hashCode(    )      method tes    t.
              *
       * @throws E xception
       *
        * @gen  eratedBy CodePro  at   11/22/13 12:41 PM
   */
  @Test
  public void testHashCode_2()
    throws Exception {
    Contac     t             fixture = ne        w    Contact((Strin     g  ) null, "", "", (S tring)     null);

       int result =    fixture.hashCode();

    // add additiona  l test code here
         assertEqu    als(923521, r  es  ult);
  }

  /**
      * Run the boolean match(      Info      rmationObjec       t) method test.
       *
       * @throws Ex  ception
   *
      *   @  generatedBy            C   od ePro at 11/         22/13 12:41 PM
   */
  @Test
  public void t   estMa  tch_   1()
    throws  Excep     tion {
        Cont       act       fixtur e = new Contact     ("", "", "", "");
    InformationObject objectIn  fo =   new Conta       ct("", "",           "", "");               

    bo olean res     ult   = fi   xture.match(  objectInfo   );

    //    add a    dd      i tion    al test code   her  e
      assertEqu   als(     true, re       sult);
  }

  /*    *
            * Run the boolean                     ma       tch(Informa     t   ionObject   ) metho           d test.
   *
   * @thro     ws   Exc   epti  on
    *
       * @generatedBy CodePro        at 11 /22/13 12:41   P  M
   */
  @Test
  pu    b    lic void   testMatc          h_2()
    throws Exception {
    Con tact fixt    ure = new Contact("", "      ", "", "")   ;
      In      formatio   n  Obje   ct    objectInfo =  null;

    boolea   n res            ult = fixture.match(object    In                  fo);     

    / / add additional test code here   
     asser tEquals(f    alse,  re   sult)  ;
  }
 
  /**      
             * Ru     n     the boolean match(Information       Object) method   test.
      *
   *  @throws Exceptio   n
   *
   * @ge  ner   atedBy CodePro at 11/22/13   12:41 PM
   */
  @T   est
  public    void testMat    ch_3()    
         throws Exception {
    Co   nta     ct fixture  = ne         w Con  tact("", "", "", "");
              Inf  ormatio      nObject objectInfo = new Address();  

       boole   an result = fixtu    re.match(objectInfo);

    // add additional test code here
    assertEq   uals(false, result);
  }

      /**
            * Run         the bo     ole      an  match(InformationObject) method tes      t.     
   *
   * @throws             Excep     ti        o    n            
      *
       *   @generat  edBy CodePro at 11/22/13 12:             41  PM
   *   /
  @Test
     public void   t   estMa  tch_4()
             t hrows Except   ion {
    Conta  ct fixture = new          Contact("", "",    ""             , "");
    Informatio nObject objectInfo =   new Co    ntact("", "", "", ""); 

    boolean result = fixture.m  atch(object     Info)  ;

    //   add additional        tes     t c   ode here
     assertEquals(t r    u e, result);
  }

  /**    
       * R  un th   e boo    lean match(Informati        onObject    ) metho    d test. 
   *  
   * @throws Exc   eption
   *
   * @generatedBy CodePr o at 11/  22/13 12:41 PM
   */
  @Test
  public     vo     id testMat           ch_5()
    throws Exception {
    Contact fixture = new    Contact("",          (S  tring) nu  ll, "", "");
    I    nf   ormationObje     ct    objectInfo        =  new          Contact("", (       String) null, ""   , "");

             boolean result = fixture.m  a      tch(obje  ctInfo);

    /     / add additional test cod    e here
    as         se        rt    Equals(tru     e, result);
  }

  /**
   * Run the boo    lean ma   tch(InformationObject) meth    od       test.
   *
   * @throws Exc  eption
   *
   * @gene   ratedBy CodePro at 11/22/13 12:41 PM      
       */
  @Test
  public voi      d testMatch_6()
      throws         Exception {
    Conta     ct fi   xt ure = new Co ntact("        ", (Str    ing) null, (Str  ing) null,         "");
                Inform   ationObject objectInf  o = new Contact("", (Str  ing) null    ,     (String) n   ull   , "");

          bo    olean result = fixture .match(obj   ectInf o);

         // add additi   onal test code  here
    a   sse rtEqu  als(   true, result);   
  }

  /   **
           * Run the boolea   n      match  (Infor  m    ati       onObj   ect) meth   od         test.
        *
       * @th      ro  ws E   xcept ion      
   *
   *   @generat    edBy CodePro     at 1        1/22/13 12: 41 PM   
   * /
  @Test
    publ  ic vo   id testMatch_7()
    t   hrows Exception {
    Conta    ct fixture = new Contact(" ", "   ",     "", ""    );    
     I   nf ormatio      nO bje    ct objectInfo    = new Contact(""    , "", "", ""    );  

    boolean result = fixture   .match    (objectInfo);

      /  / add      additional t  est code   he       re
    assertE qu     als(true, result);   
     }

     /**
   * Run the bo   olean match (Inf  ormati on    Object) method    te        st.
   *
   * @throw    s   Excepti            on
   *
   * @   generatedBy Code    Pro at 11/22/13 12:41 PM
   */
                        @Test
             public void tes    t        Match_  8()
    throws Exce    ption {
         Contact fixture         = new Contact((Str   in   g)   n  ull, (St  ring) null,   (String) null, ""    );
    In   form            at    io    n    Object objectInfo = new Con   tact((String)      nu    ll  , (String) null, (  Str   ing) null, "");

    boolean result =  fixture.           match(obje     ctInfo   );

    // add additio    nal  te st code here
    assertEquals(tru   e     , result);
  }

  /**
     * Run t     he            bool   ean matc      h(Informat    ionOb     ject) meth  od test.
   *
       * @throws Exception
   *
   *   @ge  nerated  By CodePro at 11/22/13 12:41 PM
    */
  @Test
  pub  lic void t              estMatch_9()
    throws Exception {
    Contact   fixture = new Contact  ((Stri  ng) null, (String) n  u   ll, (     String)   null, (String)    null);
    Informat    ionObje c   t objectInfo    = new Contact((String)   null, (String) null, (String)   null, (S   tring) null);

    boolean resul   t = fixture.match(objectInfo);

      //          add additional test code here
                  assertEquals(true, result);
  }

  /**
   * Ru      n the voi    d set Emai l(Stri ng)     metho   d test.      
   *
   *     @th rows      Exception
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Test
  public    void testSetEmail_1          ()
    thr  ows Exception {
    Contact fix    ture = ne w Conta   ct("",          "", "", "");
    Stri ng email = "";

    fixture.setEmail(email);

    // ad   d a   ddi  tional test code here
       }

  /*   *
   * Run the     void s   etFace   Book(String) method test      .
   *
   * @throws Excep   tion
   *
   * @    generatedBy Co     dePro at  11/22/13 12:41 PM
        */
  @Test
  public void testSetFaceBook_1(  )
    thro   ws Exce    ption {
    Contact fixture =   new Contact("", "", "", "") ;
      String fac     eBook = "";

    fixture.set    FaceBo ok(faceBook);

    // add     additional test code here
  }

        /**
   * Run th  e     vo id setPho  ne(String) metho    d    test.
   * 
   *    @throws Exception
   *
      * @generatedBy Co     dePro a   t 11/22/13 12:41 PM
   */
  @Test
  public void         testSetPhone_1()
    throws    Exception {
    Contact f ixture = new Contact("", "", "", "");
    St  ring phone = "";

    fixture.setPhone(phone);

    // add additional test code    here
  }

  /**     
   * Run the void set  WebUrl(String) method  test.
   *
       * @throws E      xception
     *
   * @gener   ate  dBy CodePro at 11/22/13 12:41    PM
   */
  @Test
  public void testSetWebU  rl_1( )
    throws Exception {
    Conta  ct fixture = new   Contact("", "", "", "");
             String webUrl = "";

       fixture.    set WebUrl(webUrl);

    // add additiona  l test code here
  }

  /**
         * Run th   e String toStr   ing() method test.
   *
   * @throws Exception
   *
      * @generatedBy CodePro at 11/   22/13 12:41 PM
   */
    @Test
  public void testToString_1()
    throw      s Exception {
    C   ontact fixture = new Contact("", "", "", "");

    Str   ing result = fixture.to   String();

    // add additional test code here
    assertEquals("Contact : \nPhone    = \nEmail = \nFaceBook = \nWebUrl = \n", result);
  }

  /**
   * Perform pre-test initialization.
   *
   * @throws Exception
   *         if the initialization fa    ils for some reason
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @Before
  public void setUp()
    throws Exce  ption {
    // add additional set up code here
  }

  /**
   * Perform post-test clean-up.
   *
   * @throws Exception
   *         if the clean-up fails for some reason
   *
      * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  @After
  public void tearDown()
    throws Exception {
    // Add additional tear down code here
  }

  /**
   * Launch the test.
   *
   * @param args the command line arguments
   *
   * @generatedBy CodePro at 11/22/13 12:41 PM
   */
  public static void main(String[] args) {
    new org.junit.runner.JUnitCore().run(ContactUnitTest.class);
  }
}