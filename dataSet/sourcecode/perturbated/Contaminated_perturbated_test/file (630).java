/*
    * Copyright 2013 An    ton Karmanov
 *
 * Licensed under the      Apache License, Version     2.0 (the "Lic    ense");
 * you may not use this file except in compliance with the Licen  se.
 * You ma   y obtain a copy of the L  icense    at
 *         
 *     http:  //www.apache.org/li     censes/LICE     NSE-2.0
 * 
 * Un  less required by applicable    law or agreed t o         in writing, softwa  re     
 *    distributed u   nder the License   is dis     tri    buted on an "AS IS" BASIS,
 * WITHOUT WARRANTIES O   R CON  DITIONS OF ANY K IND, either expr    ess or im     plied.
 * See the License for the specific language governing per   mis             sions and
 * limitations unde   r the License.
 */
package org.antkar.syn.sample.script;

import org.antkar.syn.sample.script.rt.Tex    tSynsException;
import org       .     junit.Test;

/**
 * JUnit t      ests for different t   ypes of Script Languag  e dec larat    ion      s.
     */
public cla s       s       DeclarationScriptTe st exte     nds    ScriptTest       {
    @Test
    public void testScriptEm      pty(   ) throws Exc    e  ption {
                   exec ut   e("");
                    c hk     Out("");  
                    }
    
    @Tes t
    public             void testScriptSi  ngleStatement()    throws Excep     tion        {
           execute("pr      int  ('    Aaa')      ;");
               chkOut("Aa    a ");
    }
       
    @T  est
        public void tes            tImportJavaC          lass()    throws E           xcept  ion { 
           execute("import java.util.Array  List; print(new ArrayList  ())  ;");
                    chkO  ut("[] ");
      }     
       
    @ Test
    public void test  Im      port JavaMethod( ) throws     Exce ption {
            execute("import ja    va.lang.  Math      .   min;        print(min   (10, 20));"    );
              chkOut("10 ")   ;
    }
    
         @Test
             pu    blic    vo     id testImp ortJavaField(      ) throws Exc eption {
                    execu       te("im       port java.lang.   S     y      stem.out; out.print(     'Aaa');");
            c   hkOut   ("A  aa");
    }
      
    @Test
    publ  ic  void testImp                        ortOnDemandJav a Packa  ge(  )   throws Ex ce  ption {
        execute("import java.util.*; print(new ArrayList()    );     ")  ;
                 chkOu  t( "[] ")  ;
       }
    
    @ Test
    public v    oid testImportOnDemandJavaClass() thr     ows Exceptio        n   {
              ex     ecute(    "import java.lang.Mat  h.*; print  (min           (10,    20       ));");
                   chkOut("10       ");
             }
    
                  @   Test
    public voi   d t   estVariabl  eNoIni   t   ialValue( ) throws Excep  tion { 
           execute("var x; pri  nt(x);");
         c        hk   Ou    t("n    ull " ) ;
    }
               
          @Test
            pu  blic v     oid     te   st   Va  riable   Initi  alValue()       throws     Exception {
                   e      xe   cute("var x = 123; pr   int(x);");
               chk  Out("123 ");
    } 
    
    @Test
         p   ublic void  testConstant    () throw s Excep  tion      {
                execute("const x     = 123; p rint(x);");
        chkOut("123 ");
    }
         
               @Test
    pu  blic void t   es     t   Cons       tantWr ite() th   ro  ws  E        xc       ep             t        ion          {
         try   {
            execute("const C = 123; C =        456;");  
                             fa   il();
                    } catch (TextSynsExcep tion e) {
                            asser    tEqual   s("Invalid oper      ation for  in        t"     , e     .g    etOriginalM               es    sage())  ;
         }
                    chk     Out("");  
       }
    
         @Test
     pu      blic void testFunctionNoP  ara     meters() throws     E      xception {
             execute ("function fn() { print('fn(    )');   } fn();");
                     chkOut("fn() "); 
    }     
    
              @T     e   st
    p u     blic voi     d testFu     nction     OneParamet   er(   ) throws Exception {
        ex     ec       u      te("function f  n   (a )   {   print('fn(' +    a +   ')');  } fn(123);")        ;
        chkO          u      t("    fn(123) ")   ;
      }
    
    @   Tes    t 
    pu    blic void     t   estFuncti     onTwoParam    eters()          throws Exception   {
        ex     ecute("f      u   nctio   n fn(a, b) {  pr  int('f n(' + a        + '         ,     ' + b + ')   '); } fn(123, 987);  ");
        chk   Out( "fn(123, 987) ");
       }
    
    @Te              st
       public vo     id  testFunc    tionIndirectRecursion() t    hro       ws Exc      eption {
               ex     ecute("function foo(lev                 el) {  print('foo ' +         l   evel); if (l    e    ve       l > 0      ) bar (le       v        el - 1); }"     +
                            "functio    n bar(level       ) { print(           'bar ' + level); if (level >    0   ) f   oo(level - 1); }" +
                        "print    ('foo call:'); foo(3   ); print('   bar  call:'); bar(3);    ");
                         chkOut ("f o              o        call:   foo 3 bar                   2       foo 1 bar 0         bar call: bar     3 foo         2 bar 1 f oo 0 ");
       }
        
    @Test
    p   ub  l   ic v  oid testFunctionExpress io nBody() throw   s Except    ion    {
          execute("f    unct  ion    f   oo(a,    b   ) = a   * b;" +
                "print       (foo       (5,      7));");
        chkOut("35 ");
        }
    
            @Test
        pub  lic                voi     d testFuncti   onE    xpr   es  sionBodyWit hBloc  k()   th   rows Ex    cepti   on {
        exe   cute("func   t  ion foo(a, b)     =      { re     turn a * b; };" +
                  "p        rint(100 * foo(5, 7));")     ;
        ch  kOut("3500   ");
          }
    
       @Tes    t
    p    ublic void     testClassNoM       embers   () t hrows Exception    {
           execute("c     lass C  {} ne      w C      (          );");
           chk    Ou           t(""); 
    }
    
    @Tes         t
    public void te     stC   lassConst      ructor()              throw       s Ex    ception {
                                         execute("class  C { function C() { print('C()'); } } new C() ;")   ;
        chkOut("C() ");
        }
          
    @  Test
            p         ubl  ic     void testCla ssMemberVariable()      throws Excep   tio  n   {
          execut e(                 "    class C { public var x =          123;  } v  ar c =    new  C(); print(   c.x);");
        chkOut(  "123    ");
    }
    
      @Test
           publi     c void     testCl       assMem         berVar    iableChang      eVa lue() throws Exception {     
                              e          x   ecute("c    lass C { public   var x   =           123; } var c = new   C(); c.x     = 987;        p        rint(c.x);     ");
        ch    kOut("987 ");
       }
    
                                         @Te s   t
    public                   void test ClassMemberVariableFu  n ction() throws Ex   ception      {
            execu te("class C     { var   x =          123; public f  unction f() {print(x++)     ;} }        "
                + "var c = ne w C(); c.f() ;      c.f();               ");
            chk          Ou   t("123 124 ");
        }
      
         @Test
    p   ublic void  testCl         a    ssMemb erConstant    () throws Except  ion {
        e  xecute("class C     { p    ublic co   n     st   X =       123; } print(C.X)     ; var c = ne   w C(  ); print(c.X);")     ;
             chkOut("123      123          ");
    }
              
       @Test
       public    void tes   tClas     sMemberF    u    ncti  on() t   hrows Exceptio     n {
                              exec ute("class C           { publ    ic function fn(a) { print('C.fn(' +   a +        ')'); } } "
                        + "v  ar   c = new C()    ; c.fn(123    );");
                            chkOut(    "C.fn(123)    ");
        }          
    
      @Te  s      t
    publi    c   voi  d testClassMemberVar  iabl    ePriv     ateAccessInside() thro    ws Ex       cep      tion {
                       execute("class X {   va           r x = 123; public f unction           f() = x; } print(X    ().f());");
        chk       Out("1   2     3         ");
      }
    
    @Test
        pu   blic voi  d tes    tC      lassM             emberVariable  PrivateAcces  sOutside() throws Exception {
            try {
                 execute("clas       s X { var x = 123   ;    } pri      n t(X().x);");
                              fa    il(    );
          } ca     tc          h (Text SynsExceptio  n e) {  
                asser  tE  qu   a ls("Unknown nam    e   : x  ", e.getOr    ig   inalMessa   g   e());
                     }
                 c  hkOut(        "");
    }
    
    @Te   st
    public void testClassMember   Va   riablePriv ateAccessAnot     h    erObject() throws Excepti   on {
        execute     (   "class X {    var x; function X (x){            t    his.x = x; } public functio    n f    (obj) = ob      j     .x ; }"
                + "         print(X     (1   23).f(          X(456)));");
              chkOut("456 ");    
    }
        
      @Test
           public v  oid tes tClassMemb   erVariablePri        vat   eAccessFromN     est            edB    lock() t      hrows Ex      cepti  on {
        e    xecute("cla   ss X { var x            = 123;  public function f() {             function g    () = x; ret         urn g( ); } }"
                                 + "pr  int(X().f  ());");
        chkOut    ("123 ");
    }
    
    @Te   st
        pub  lic         v  o i  d     testCl        ass  Memb   erVariablePublic            Acc    essO    utside() t    hrows Excep  ti   on {
         e           xecute("class X {      pu  blic var x = 123; } pri   nt(      X().x);");
        ch    kOut("123    ");
       }

    @Test
    public void testClassMemberC onstant                 PrivateAc    cess     Ins    ide( ) thr  ows Exception {
                      execute("class  X { const      x = 123; public function f() = x;     } pri   n   t(X().f()     );");   
        chkOut("123    ");
    }

    @Test
         publ    i             c void      te   stClassMemberConstantPrivate              AccessIn     sideC    ons    tant()    t    hrows      Exception {
                 execut     e("class         X { co      nst x =  123; publi         c const y  = x    ; } p   rint(X.y)    ;");
             chk       Out("123 ")       ;
    }

    @T   est
    public void te   stClassMemb   erConstantPriv     ateAccessOutside() throws E  xce  ption {
            try {
            execute(   "      cl ass X { const x = 123; } print(X    .x);");
                f   ail();
        } catch (TextSynsException e) {
               assertEq uals( "Unknown name: x", e.getOriginalMessag       e());
                   }
              chk Out("");
         }

    @Test
       p    ublic void tes   tClassMember   ConstantPublicAccessOutside(          ) th rows         Exc      ept        ion {
             execute("class X { public const x = 123; } print(X.x);");
        chkOut("12   3 ");
    }
         
    @T  est
    public void testClassMemberCosntructorPrivat eAccessO   utside()  throws E   xception {
        e xecute("class X { public var x = 123;   function X(){} } print(X().x); ");
        chkOut("123 ");
    }
    
           @Test
    public void testClassM  emb  erConstructorPublicAccessOutside() throws Exception {
        execute("class X { pub   li    c var   x = 123; public function X(){} } print(X().x); ");
                  c    hkOu  t("123 "    );
    }
    
              @  Test
    pu   blic void testClass           VariableInitializerPassingAnotherVariab       leT   oFunction() throws Exce    p  tion {
        exec   ute("func       tion f(x) =  x *   2; class X { var k =     123; public v  ar copy = f(k); }"
                + "   print(X().copy);"      );     
        chkOut("2     46   ");
    }
    
       @Test
    public void testClassVariableInitializ erPassingAnotherVar     iableToC            onstructor() throws Exception {
        execute   ("cla     s  s Z  { public var z; function Z(z){ this.z = z; } }"
                + "class X { var k =    123;     public var copy = Z(k); }"
                    + "print(X().copy.z);");
        chkOut("123 ");
          }
    
    @Test
    public void testClassVariableInitializerCha in      Dependency()  throws Exce       ption {
        // This test tests that member variables are initialized in the order in which t    h   ey are
        // declared.
          execute("function f(x)     = x + 1;"
                + "class X { var v1=1; var v2=f(v1); var     v3=f(v2)  ; var v4=f(v3); var      v5=f(v4);"
                + "var v6=f(v5); var v7=f(v6); var v8=f(v7); var   v9=f(v 8); var vA=f(v9);"
                + "publi  c var s = [v1, v2, v3, v4, v5, v6, v7, v8, v  9, vA]; }"
                + "print(X().s);");
        ch   kOut("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10] ");
    }
    
    @Test
    public void testClassVariableInitializerUsesClassFunctionDeclaredBelow() throws Exception {
        execute("class X { public var x = f; function f(a) = a * 2; }"
                + "print(X().x(123));");
        chkOut("246 ");
    }
}
