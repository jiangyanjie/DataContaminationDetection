/*
  * To     c   hange  this templ    ate, choose Tools | Temp   lates
 * and open    the template in the editor.
 */
package org.opencyc.cycobje ct;

import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
imp   ort or  g.junit.Test;
import static o  rg.junit.Assert.*;
import org.opencyc.api.CycAccess;
import org.opencyc.ap    i.CycConne  ction;         

/**
 *
 * @autho r daves
 */
  public c    la    ss CycFormulaSentenceTest {

  private   static String hostname                = CycConne   ction.DEFAULT_HOSTNAME;
  private static    int port = CycConnection  .DEFAULT_BASE_   PORT;
  private static f  inal boolean SHOULD_S   ET_CONNECTION_PARAMS_INTERACTIVELY = false;
    private static boolean areConnectionPara  msSet = !SHOULD_   SET_CON   NECTION_PARAMS_INTER  ACTI VELY;
  pr    iv   ate static Cy   cAccess cycAccess = null;    
  
  publ            ic CycFormul   aSentenceTest()     {
  }

  @BeforeClass
  public sta      tic void setU      pClass() throws Exc         epti   on    {
    cy       cAccess =      getCyc();
  }

  private    static Cy cAcces  s getCyc       ()       {
    if (cycAcce          ss ==      null   ) {
      i        f         (SH    OULD_SET _C     ONNECTION_PARAMS_IN    TERACTI    VELY && !areC onnectio      nParamsSe      t) {
         c      ycAcc  ess = CycAccess.g   etNewCycAc    cessInteractively();
        hostname =   cy      cAccess  .g      etHost     N    ame();                    
        port      = cycAcc  ess.getBasePort();
                   areConnectionParam    sSet =       true;
         }       else {
          try {
          c   ycAc   cess = new CycAc    c       ess(h          ost    name, port);
        } catch (Exc  e     ption ex) {
                fail(ex.getMes  sage());
        }
        }
    }  
       return cycAccess;
  }

  @After            Clas  s
  public stat    ic vo  id tearDownC     lass() throw      s E   xcept  ion {
               cycAcc   ess.close();
  }
  
  @Before
  public voi    d setUp() {
        }
  
  @After
  p      ublic void tearDown() {
      }

  /*  *
     * Test of makeCycF    o                rmu  laSenten ce method, of cla    ss CycFor          mulaSen   tence      .
   *  /
  //@Tes         t
  public    v  oid t  estMak   eCycF    ormulaSentenc e() {
    System   .out.    println("m   akeCycFormulaSentence");
       Object[] ter  ms = null;
    CycFormulaSentence expResul     t = n     ull;
    CycFormu  l    aSe  nten  ce result = CycForm   ulaSentence.makeCy     cForm    ulaSentence(terms);
    assertEquals(expResult, result);
           // TODO review the generated  test code and re   m ove the default           call to fail.
    fail(   "The test cas       e     is a prototype.");
  }

  /**
   * T  est o  f makeCo       njunction method, of c    las    s CycFor mulaSentence.
   */
  //@Test
    public void testMakeConjunctio   n_CycFo       rmulaSentence Ar   r() {
         Syst  em.out.println("makeConj    unction");
    CycFormulaSentence[]     conjuncts =      null;
    CycFormula  Sentence ex    pResult = null;  
       CycFormulaSentence result = CycFo   rmulaS    entence.ma       keConjunction(conjuncts);       
        asse   rtEquals(expResult, result);
             // TODO re  view the generated test code and remo ve    the de   f      ault call to fail      .
    fail("The test case is a prototype.");
   }

  /* *
   * Test of makeConjunct  ion met     hod, of class CycForm   ulaSentence.
   */
  //@   Test
  public void testMakeCo   njunction_Iterable() {
    System.out.println     ("makeConjuncti   on");
    Iterable<  CycFormulaSentence>    conjuncts = null;
    Cy    cFormulaSe    ntence expResult = null;            
    CycFo  r    mulaSentence result    = CycFormula  Sentence.makeConjunction(co  n  jun     cts);
         assertEquals(expR     esu  l  t, resu  lt);
    // TODO review the generat  ed test code and remove the def   ault call to fai     l.
    f  ail("The test c as  e  is a prototype.");
   }

  /**
   *    Test of makeDis  junction method,           of cla ss CycFo   rmul   aSen      tence.
   */
  /    /@Test
           publ  ic vo id testMa       keDisjunction() {
        System.ou   t.println("makeDisjuncti   o  n")    ;
    Iterab le<C     ycFormu  laSentence> conjuncts = null;   
    CycFormulaSentence ex    pResult = null;
    CycF      ormulaSe  ntence result = Cyc    FormulaSen   tence.makeDisjunction (conjuncts);
    assertEq       ua    ls(expRes    ult, result);
    // TODO review the generated  test code and r  emove the def   ault call to fail.      
               fail ("The tes  t case is a protot       ype.");
  }

  /**
   * Test of makeN      ega    tion  method, of cla ss CycFormu  laSentence.
   */
  //@Test
  public void testM   akeN    egation()            {
    System.      out.printl    n   ("makeNegation");
    Cyc      FormulaSentence sentenc e = nul  l;
      CycFormulaSentence expResult = null;
     CycFormulaSente   nce          result =   CycFormulaSentence.makeNegation(se     n    tence);
    assert   Equals(expResult      , result   );
    // TODO review the gen     era   ted test code   and r    emove t    he       default   call     to fail.
     fai   l("The test cas  e is a prototype.");
  }

  /**
      * Test of conver    tIfPromising method, of class CycFormu  laSenten     ce.
   * /
  //@Te    st
  pub lic vo      id testConvertIfPromisi   ng() {
    Sys tem.ou   t.println(    "convertIfPromisi    ng");
    Objec t obj = n      u   ll;
    Object expRe      sult = null  ;
    Object result = Cyc F    ormulaSentence.convertIfPromising(   obj);
        assertEquals(exp   Result, result);
    // TODO review t  he   ge    nerated t          est code and remove t   he def  ault call to fail.
        fail("The      test case    is a prototype.");
  }

  /**
       * Test of isCondition  alSentenc    e method, of class CycFormulaSen       tence .
   */
   //@Te st
  public  void testIsConditiona   lSentence() {
            System.ou   t.p      rintln("isConditiona    lSentence"); 
      CycFor  m      ulaSentenc  e insta   nce = null;
    boolean expResult = fals e;
          boolean result = instan    ce.isC        on  ditionalS    entence();
    assertEquals(    expResult, result)    ;
    // TODO review the generated test code and remove the    default call to fail.
       fail("T     h     e t       est case is a protot   yp          e.");
  }

  /*   *
     * Test of i  sC   onjunction            method, of         class C    ycFormulaSentence.
   */
  //@Tes    t
     public v   oid te    stIsConjunction() {
    System. out.println("isConjunction");    
    CycForm   ul  aSen    tenc   e ins      tance = nu  ll;
    boolean exp   Result  = false;
    boolean result  =        instance.isConj   unc     tion();
    asser   tEquals(expResult, result);
       //   TODO re    view    the generate  d t   est c   ode   and remove      the      defau lt           call to fail.
            fail("T    he test case     is a protot  y pe.");    
           }   

  /**
   * Test of isLogicalConnectorSentence method, of c    lass     Cyc    FormulaSentence.
   */
  //@Test
  public void testIsLogicalC onnectorSentence() {
    Sys    tem.out.println("i   sLogicalConnectorSentence");
    CycFor  mulaSen    tence inst    an     ce = null ;
            boolean expR   esult =   false;
    boolean result = instance.isLo      gical    ConnectorSentence();
          asser     tEquals(expResult, result)   ;
    // TODO     review the gen          e  rate d t  est co   de and remove the def  ault call to f    ail.
          fail(" The t   es t case is       a proto  ty  pe        .");     
  }

  /      **
   * Test of isExisten  tial met     hod,        of class CycFor   mulaSentence.
   */
  //@T   est
    publi   c  vo  id testIsExiste     ntial    () {
       Sy         st         em.out.println("isExistential");
                CycFo  rmulaSentence instance = nu ll;
    b   oolean expResult = false;         
    boo lean result = i ns ta     nce.is   Exis   te     n     t   ial();
           assertEquals(exp  Result, resu lt    );
      // TODO review the g   enerated test code and remove the   defa      ult call to fail.
     fail("T    he test case is a pro   to   type.");
  }
    
  /**
   * Test of     isUni  vers    al metho     d, of class CycFormulaSenten     ce.
   *  /
     //@Test
  p  ubl ic v  oid testIsUnivers   al() {
    Sy  ste m.o        ut.print       ln("isUniversal");
    CycFor      mulaSent         en  ce instance = null;
     boolean expResult       = fal     se;
    boolean res    ult    = i nstance.isUniversal();
    assertEqu  als (ex   pResult, resu lt);    
       // TOD O revi    ew the generated      test c  ode and remove the defaul   t cal    l      to fail.
    fa  il("Th  e tes     t case   is a p   roto  ty pe.");
    }

  /**
   * Te      st of getOpt     imizedVarName s method  , of class C    ycFormulaSentence.
   *  /
  //@Test
  public void tes  tGetOptimizedVa   rNames   () throws Except  ion         {
    S   y   stem.   out.println("getOptimizedVarNames");
    Cyc        Access access  = null;
    CycFormulaSentence        instan ce = null;  
    Map expResult = null;
       Map result = inst     ance   .getOptimizedV arNames(access);
    assertEquals(expResult, resul    t);
    // TODO r  eview the generated test code and    remove the default call to fai          l.
    fa il("T    he    test case is a p  r   ototype.");
  }

  /**
   * Test       of getSimplifie  dSentence method, of class Cyc  FormulaSenten        ce.
   */
      //@Test
  publi  c void      testGetSimpl      ifiedSentence_CycAccess(   ) throw    s Exception {
    System.out.pr  intln("       getSimplifiedS entence");
    Cy   c         Access acces   s = null;
    CycF ormulaSentence instance = null;
    Cyc Se  ntence      ex     pResult = null;
     CycSentence res  ult = instance.getSimp   l    ifiedSentence(ac cess);  
    assertEquals(expR esult, result);
    //      TODO revi  ew the generated test code and remove the de   fault call to fail.
    fail("The test ca  se  is a prototype.");
  }
        
  /**
   * Test          of getSimplifiedSenten  ce method   , of class CycFormulaSen ten  ce.
   */
  //@Test
  public voi    d   testGetSimplifiedSentence_CycAccess_  E   LMt() throws Exception        {
    System.out.println("getSimp  lifiedSentence ")   ;
    CycAcc      ess access = null;
    ELMt mt = null;
     CycFormu laSentence inst   ance = null;
    CycSentence expRe   su  lt = null;
    C    ycSentence     result = in sta     n   ce.  getSimplifiedSentence(access, mt);  
    assertEq  uals(expResult, result);
    // TODO review the generated test  code and re      move the def     ault call to fail.
    fail("The test case       is a    prototype.");
   }

  /**
      * Test of getN    onWffAsse    rtExplan  ation met hod,       of class Cy    cFormulaSentence.
   */
     @Test
  publi     c void testGetNonWff        As     se  rtExpl   anat    i    on _CycAccess() {
      System.out.println("getNo n     WffAs    sertExpl  anati    on");
    CycFormulaSentence instance = cycAccess.ma  keCycS     entence("(#$isa #$M   arkD  u   ggan-SoccerPlayer #$Bio            logi ca   lLi   v    ing  Object)");
       String expResult = null;
    String r    esult = instance .getNonWffAssertE   xplanation(cycA        c   c      ess);
    assertEquals(expR    esult,    res ult);
    // TODO rev       iew the         generated tes t       code      and remove the default call to f  ail.      
//          fail("The test ca    s    e is a prototype.");   
      }

  /*       *
   * Test of getNonWffAsse    rtExplanation method    , of class CycFormulaS       entence.
         */
  @T       est
  publi c void testGetNonWffAs     sertExplanation_CycAccess_ELMt(   ) {
    System.out.pri      ntln("getNo     nW    ffAssertE          xplanation");
        ELMt mt = CycF  orm ulaSentence.getDefaultSimplifierMt();
    S      tring result = null;
      CycFormulaSentence   instance = cycAcce    ss.makeCycSentence           ("(#$genls     #$M           arkDuggan-S occerPlayer #$Bio     logica  lLiving    Object)");
    r esult = instance.    getNonWffAsse    rtExp        lanation(cycAccess, mt);
    assertT rue (re     sult instanceof String);
         // TODO revie     w the generated test code an           d   remov   e the default   c    all to     fail.
//       fail("The t est         case is a          prototype.");
  }
                  
  /**
   * Test of getNonWff   Explanation method, of class CycFormul            aSente   nce.
   */   
  // @Test
  public void test  G  etNonW         ffExp     lanation   _CycAccess(  )      {
    System.out.pr   intln("getN  onW     ffEx    planation");
      CycAccess access = null;          
           CycForm   ulaSente   nce ins  tan   ce = null;
        String   expResult = "";
    Strin  g result =   instance.getNonWffExpla    nat  io     n(a  ccess);
      assertEquals(expResul    t, resu     lt);
      // TODO re   view the           gener   ated t      est code and remove the d  efa   ul  t call to f               ail.
    fai    l  ("The test c       ase is a      prototype.");  
  }

  /**
   *  Test of     getN     onWffEx    p  lanation metho d ,         of class CycFor     mulaSentence.
   */
  //   @Test
     public void   test   GetNon W    ffExplanation_Cy   cAccess_EL    Mt() {
    S   ystem.out.print    l  n("getNon   WffEx  plana  tion");
    CycAccess a        ccess   = null;
    ELMt mt = null;
    CycFo rmulaSentence instance = null;
    Str ing expResult = "";
    String    result = in  stance.          getNonWffExplanation(access, mt);
    assertE   quals(expR    esult           ,      res     ult);
      // TOD  O review the generated test code  and r   emove the default call to fail.
        fail("The test case is a  prototype.");
  }

  /**
   * Test of deepCopy    met    hod, of class  CycFormulaSen tence    .
   */
  //@Tes           t
   public void testDeep       Cop  y()    {
      System.out.println("    deepCop         y");
    CycFormulaSentence instance =   null;
    CycForm   ula       Sentence exp     Result  = null;     
    CycFo rmula    Sentence result = instance.deepC      opy();
     assertEquals(expResul   t         ,      result)        ;
    //      TODO revie w t        he generated test code and remove the default c all        to fail.
    fail(   "T he test case    is a prototyp  e. ");
  }

  /**
      * Test of substituteNonDe    structive      m   ethod,   of class CycFormulaSentence.
   */
     //@Test
  public voi   d tes   tSu    bstituteNonDestructive() {
    System.out.println("substituteNonDestructive");
    CycObje  ct original = null     ;
    CycObject replacemen     t = null;
       CycFormulaSentence instance = nul l;
    CycFormulaSent        enc e ex  pResul       t = null;
    CycFormulaSentence result = instance.substituteNonDestruc tive(original, repl        ace    ment);
      asse  rtEquals(expResult, result);
        // TODO revie  w the generate    d test     c ode and remove th     e defau   lt call    to fail.
    fail("The test case is a prototype.");
  }

       /**
   * Test of substi           tuteDestructive method, of class CycFo       r  mulaSentence.
   */
  //@Test
  public void testSubstituteDestructi ve() {
    System.out.println("substituteDestructive");
    CycO  bject original = null;
    CycObject r      epl      acement = null;
    C     ycForm   ulaS    e    ntence instance = null;
    instance.substit     uteDestructive(o    riginal, replacement);
    //   TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
       * Test of treeSubstitute method, o      f class   CycF  ormulaSentence.
   */
  //@Test
  public void testTreeSubst     itute() throws Exception {
    System.out.println("treeSubstitute");
    CycAccess access =    null;
    Map<CycObject, Object> substitutions = null;
    C   ycFormulaSentence instance = null;
       CycFormulaSentence exp  Result = n  ull;
    CycFormulaSentence result = instance.treeSubstitute(access, substitutions);
    assertE quals(expResult, result);
    // TODO   review the generated test code an  d remove the default call to fail.
    fail("The test case is a prototype.");
  }

  /**
   * Test of clone method, of class CycFormulaSentence.
   */
   //@Test
    public void testClone() {
    System.out.println("clone");
    CycFormulaSentence instance = null;
    Object expResult = null;
    Object result      = instance.clone();
    assertEquals(expRe  sul   t, result);
    /   / TODO review the generated test code and remove the default call to fail.
    fail("The test case is a    prototype."     );
  }

  /**
   * Test of compareTo method, of class CycFormulaSentence.
   */
  /    /@Test
  public void testCompareTo() {
    System.out.println("compareTo");
    Object o = null;
    CycFormulaSentence instance = null;
    int expResult = 0;
    int result = instance.compareTo(o);
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }
}
