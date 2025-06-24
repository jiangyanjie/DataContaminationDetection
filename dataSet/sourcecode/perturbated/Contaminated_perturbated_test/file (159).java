/*
     * To change this templ ate, ch    o  ose Tool    s        | Templates
 * and open the template  in the editor.
 */       
package org.opencyc.api;

import java.io.IOException;
import java.ne   t.UnknownHostException;
import java.text.Field       Position;
import java.text.ParseEx   ception;
import java.text.ParsePosition;
import org.junit.*;
import static org.junit.Assert.*;
import org.opencyc.cycobject.CycLis    t;
  
/**
 *
 * @author dave  s
 */
public clas s     CycLFormatTest {
                private Cyc   LFormat ins    tance;     
    private St   r ing newLine;
      
    publ  ic CycLFormatT     est()         { 
      }

     @BeforeC    lass
      pub lic st   atic       void setUpClass() throws     Exception           {
       }

    @AfterClass
    public static void tearDownC       l                      ass() throws Exceptio    n {  
    }
    
        @   Before
      public voi    d setUp() thro  ws UnknownHostExc     eptio  n, IOException {
        CycAccess cyc = new  CycAccess("localh  ost     ", 3600)     ;
          instan       ce =     CycLForm       at.getIn          stance(cy c);
        // (princ  -to-string #              \\Newl   i ne) does not w  ork, because CycList parser cannot han dle #\ .    ..     :     P
                     newLine           = cyc.conv           erseString("(clet (result) ( cwith-output-t        o-str  in  g (s  re        sul    t) (terpri s)) (ide      ntity re  sult))");
    }
       
    @    After
    public void  tearD    own () {
    }

    /**   
     * Test             of getInst  ance method, of cl  ass CycLForm       at.  
        */
    //@Test
    public void testGetInsta   nce() {
        System.out.println(        "g   etInst     ance")    ;    
               CycAccess          cyc =  null;
                CycLFormat expResult   = null;
               CycLForm     at resul t      = CycLF    ormat.get   Ins    tance(cyc);
        assertEqual     s(expResult, resu        l  t)         ;
        // TODO revi   ew the generated  tes  t cod   e   an   d  remove t    he de   fau   lt c            a   ll to fail.
                     fail  ("The test case is a prototype.")          ;
    }

    /*  *
     *  Te       st of    setWra   pLines method,    of class CycL  Form   at   .
         */
          //@Test
       publi      c void   testSetWrapLi  nes    () {
           S  ystem.out.println("set     WrapLines  ");
        boolean       newlines = false;
           CycLFormat instance =     nu ll;
                instance       .se     tWr  apLines    (newlines);        
        // TO  DO review the generated test code     and remove the default call        to fail.     
        fail("The  test case is a    protot      ype.");
    }

    /**
        * Te     st of    setShowHa shDo llar meth           od, of class CycLFormat. 
           */
            //@Test
             public v    oid testSetShowHashDollar() {
           System.   ou      t     .println("setShowHashD  ollar")    ; 
                boolean showHashDollar = false;
           CycLForm          at     i    nstance = nul    l;
                         instance.setShow          Hash    Dollar(s                 howHa        shDollar);
                   // TODO revie         w th   e generated test      code and remove the defa  u      lt   call t o fail                  .
          fai   l(    "The test c  ase is a p        rototy  pe.");
    }



             /**
     * Test of parse       Object method, of class CycLFo  rm       at.
     */
    @T  est
    public void testParseObject() {
        System.out.println(    "tes       tPar  s    eOb     ject")  ;
             Stri         ng source = " (#$implies (#$isa ?X    #$Cat) (#   $likesAs  Friend ?X #$C  ycAdministrator))"; 
          ParsePosition pos        = new ParsePos ition(0);
        Object        expRe sult = null;
           Object result = instance.parseObj ect(source, po     s)   ;
           assertTr   ue(resul   t inst anceof        CycList);
                  // T  ODO revie    w the g  enerated tes   t code     and r  emove the   defaul     t call to fail.
        //f  ail("The test case is a prototype.");  
    }      
    
          /**      
             * Test of format    method, of class Cy  c LF     ormat.   
     */
    @T      e   st
    public void testFor     mat() throws P          a rse         Exc  eption {    
         Sys tem.out. pr           intln(      "    format");
        Obje  c t obj = instance.pa              rs eObje         ct("(#$  implies (#$ is a ?X #$Cat)  (#$l     ikesAsFriend ?       X #$CycAdministrator))", new ParseP  o   siti  o n(0));
             S     tri        ngBuffer toAp            pe  ndTo = new S  tr  i   ngB    uffer();
                  FieldPosition   pos = nu   ll;
        String   expResult =     "(#$implies (#$is        a ?   X # $Cat) ( #     $   likesAs      Friend ?X   #$         Cy          cAdminist r              ator  ))";
        instance.setShowHashDo    llar    (true);
          i nstance.       setWrapLines(fal    se);
        S  tringBu  ffer result = instan  ce.f    or  mat(obj,        toAppendTo, p     os);
        assertEquals(exp     Res  ult, result.to  S          tring());
          // T ODO      review the generated test code an     d remove the  default c      all to fa     il.
               
    }
    
        @Te   st
    public void testFo    rmatNoHashDol  lar(     ) throws Pa     rse   Exc   ep             t   ion {
           System.out.println("formatNoHashDollar");
        Object obj = instance.    par  seObject( "(#$implies (#$isa ?X #$Cat) (#       $likesAsFriend    ?X #$C    yc Administr     ator))", new ParsePosition(0)); 
              StringBuffer toA  ppen  d    To = new     StringBuf       fer()  ;
        FieldPosition pos = nul     l;
              String expRe        su  lt =    "(imp lies    (isa ?X Cat)      (lik     esAsFriend ?X CycAdm    i   nistrator))";
        ins   tance.setShow HashDollar(false);
               instance.s etW  rapLines(fal se);
          StringBu   ffer    res   ult   = instan      ce.for      mat(obj, toAppe   ndTo    , p         os);
                          assertEqua   ls(exp     Result, result.toString());
         // T     ODO review the ge nerated test code and remove the def       ault cal       l      to fai    l.
             
             }
      
       @Test
    public  void testForma     tWra  p() throws Pa   rseException {
        Syste   m.out.println("formatWrap");
                     Object o     bj   = instance.parseObject("(#$implies (#$isa ?X #$Cat) (#$likesAsFriend ?X #$CycAdministrator)      )", new ParsePo   s           it  ion(          0));
        StringB   uff                      er             toAppendTo = new Stri          ngBuffer();
              FieldPos     ition p  os = null;
                        String expRes ult = "(#$implie  s " + newLine +"  (#$isa ?X #$Cat)     " + n ewLine    + "  (#$like       sAsFriend ?X #$CycAdminis trator))";
                  instan  ce.set   ShowHashD      ollar(true);
        instance.setWrapLines(true);
               StringBu   ffer result = instance.format(o  bj, toAppendTo    , pos);
        assertEquals(expResult, result.toString());
            // TODO review the generated test code        a     nd remove the defau    lt call to fail.
          }
    
    @Test
    public void testFormatWrapNoHashDo     llar() throws ParseException {
        System.out.println("formatWrapNoHashDollar");
        Object obj = instance.parseObject("(#$imp     lies (#$isa ?X #$Cat)     (#$likesA    sFr iend ?X #    $C   ycAdminis    trator)   )", new ParsePosi   tion(0));
          StringBuffer toAppendTo = new StringBuffer();
        FieldPos        ition p    os = null;
        String     expResult = "(implies " + newL ine + "  (isa ?X Cat) " + newLine + "        (likesAsFriend ?X CycAdministrator))  ";
        instance.setShowHashDollar(false);
        instance.setWrapLines(true);
        StringBuffer result = instance.format(obj, toAppendTo, pos);
        assertEquals(expResult, result.toString());
        // TODO review the generated test code and remove the default call to fail.
        
    }
}
