/*
 *     To ch  ange this   t      emplate   , choose Too     ls     | T   emplates
 *     and op en the template in the editor.
 */

imp      ort java.io.Serializable;
import java.util.Scanner;
import java.util.Stack;

/**
 * Custom formula generated from strin     g
 * (An expression tree)
     * @author   ali.kocatur k.1
 */
public class CustomFormula i   mplemen   ts Form        ula {

    private Formul aNod    e       exTree;
    private Variab   leNode     z; // NOTE: VariableNodes are NOT THREAD-SAFE
      pr   i  vate Variab        leNode c; // Again, NOT      THREAD-SAFE

    /  **
           *        Constructs a CustomFor   mula accor   ding to the gi   ven pos   tfix   expression         
     *       @param        sex
        */
    publ           ic C  ustomFormula(St   r ing sex) {
         z = new   Vari  a  b       le   Node();
          c = new Vari     ableNode();
         exTree =  buildExpression(    s    ex);
                  exTree.  simplify();
        }

       /**  
     * Sets    the      expre      ssion represe  nted by this Cus    tomFormula accordin    g   to the
     * g   iven pos        tfix expression
       * @param  sex
       * @throws   IllegalAr    gu  mentExcep      tion if a  n ope rand is missing       or           an opera   tor
     *  is un    recognized
             */  
      public void setExpr     es   si  on(  String sex) throws
                     IllegalArgumentE xc   eption {
              ex Tree      = buil        dExpres  sion(se   x)   ;
            exTree   .simpli           fy();
    }
   
    /         **
            * Builds  a For    mulaNode according     to the given postfix e        x    press    i          o      n
            * @par   am s   ex
       * @retu      rn a FormulaNode repr esenting the root of the   expression tre          e
           * @t    h  rows I l   legalArgume    ntException if an operand is miss  ing o   r a   n ope rator   
      * is unrecognize    d
               */
                    private FormulaNode buildExpression(Stri  ng se  x)
                                      throws  Il  leg       alArg  umentException {
               Scanner sexscn = new Scanner(sex);
          Stack< String> s exstk   = ne  w  St     ack<String>();
  
            wh   ile  (   s      exscn.h asNext()) {    
                  se   x  stk.pu       s    h(sexsc  n        .next(   ));
              }

                       r eturn buildExp ression(sexst     k);     
    }

    /**
        *       Build     s a FormulaNode fr  o   m the     given expres      sion st     ack.  N  OTE: w  ill e  m   pty
     * the  stack.
         * @p ar am    sexstk
       * @r     e   turn        a Form    ulaNode rep resenting  the    root of the new    expre     ssio   n tree
                  * @throws IllegalArgumen    tE xcep   tion       i f       a      n operand is miss     ing (st      ack
     *      unexpectedly empt          y) or    an ope    rator is   unrecognized
       */
    private    FormulaNod    e         buildExpression (St            ack<Str              in g> sexs  tk   )
                                         thro   ws Illega    lArgumentExce      ption {
            if (s   exstk.isEmpty()         ) {
                       retu      rn null;
        }            
            S  tring sop =      sexstk.pop();
        if (sop.equals(   " z")) {
                 r etur     n z;
         } else if (sop. equals("  c")) {
             return c     ;
                  } else if (   sop.       equal     s(     "i")) {
                 return new     ExpressionN    ode   (   new Complex(0  , 1));
             } else     if (sop.equa ls("* ") || sop.    equal   s("+") || s   op.equals("/" )
                        || so  p.equ      al   s("-")  || sop.e quals("^"  )) { // bina    ry operators
                                 Fo      r mulaNode ren = buil    dExpr  ession   (sexstk);
                        FormulaNode len     =             bui    ldE   xp  ression(    sex      stk);
                           if (len == null || ren == null) {
                           t hrow new Illegal      Arg    u         m   entE  x  ception("Malfo   rme d expres  sion")    ;
                  }
                          return new E       xpressionNo    de(sop, len,    ren);    
         } el              se if         (sop.equ      als("tan")                   ||    sop.  equa        ls   ("s   in"   ) || sop.    equ     als("cos"   )
                 ||       sop.equals("atan"   ) || so    p.equ      als( "asin") || so  p.equals    (     "acos"              )
                                    || sop.equals("tanh")  ||     sop.equals("sinh     ") || sop.eq uals("c   osh")
                  || s       op. eq            u       als("  atanh") |       | sop. equals("asinh")     || sop   .         equa       ls    ("acosh"   )
                         || sop.  equals(    "  log") || so  p.equals("ln" ) || sop.  equals("abs"    )
                     ||       sop.equa       l       s("mod") || s     op.equals ("abs2") || so  p  .equals("re"      )          
                                   |   |   sop.equals("im") || sop.e  quals("re") ||         s       op.eq            uals("a  rg  ")
                    || sop   .  equal  s("conj") ||              sop  .   equals("exp")) {  // una        ry operators
                             Form    ulaN        ode  len = buildExp  r       e ssion   (sexstk   );
                            if (len == null) {
                       t   hrow ne   w   Ill      e    galArgum  entEx    cepti   on("Malfo     r   med e   xpre               s  sio        n");
                   }
            re   turn new   Expre     ssionNode(sop, l     en         , null   );
              } else      {
                    try {
                    return n   ew Expr   es    sion    Node(n    ew Com    plex(Double.parse               Double(sop), 0   ));   
                          } ca         tc   h (NumberFormatException e) {
                                 th      row      new Ill      egalArgumentException(        "    Unr ecogni        zed oper       ator: " +  sop);
             }
                 } 
    }

     /**
            * Eva  luate        s the formula   for the   given v   a   lues       of z      and c.       NOTE:   This           
       *     method is  not thr ead     safe   ; if ca    lc   () is called  while a p revi               ou  s          ca  lc()      on
     *   the sam  e   object is   not fini    s    hed, the z and c  v       a lu    es may have c  hang        ed
          * partway    through t    he       calculation.
     * @param z
             * @p aram c
            * @retur                  n 
        * /
           publ      ic Complex calc      ( Comp    lex z ,            Co  mplex c    )    {
         this.  z. setValu        e(z        );
         this.c.setValu    e(c );
        return    exTr  ee.evalua   te();
    }

    /       **
      * An interfa     ce specify   in g          m   e       th ods for  an evalu  a b   le node of  the e xpr    essio  n   
        * t   ree
     */
      private                 int       erface F            ormulaNo   d     e ext  ends  Serializa     ble {

            /**
                *   E    va luat  es     this nod  e.
               * @r   eturn the result of eva      luation
            */
                            pub   lic Comp    lex      e     va     l  ua     te();

          /**  
         * Determines   whether the value     of this   node   wi        ll change if  any             
                       *   VariableNode  s    in th     e    e   xpr   e s   sionTr   ee   are modi    fied.    A constant    n    od      e
           *     wi              l  l a  l way       s re   t    urn true an    d a Vari            ableNod  e wil   l always ret       urn f    als  e.  
            *   A  n Expressio          nNo   d  e wi      ll return   fa    lse unle       ss a  ll of its childr    en re     t u       rn
               *             true.        
                       *  @retu         rn true if    evaluate() will        always      return   t  he sa    me valu    e,
                                   * oth    erwise   false
                */     
                      publi      c    boo  lean isC     on  stant();   

                 /**
                           * Simplifi  es this                 node and its children to   reduce op    erations tha      t
                 * alway   s return the     same valu  e.
             */
         public voi d   simplif   y();
          }

    /**
          * A nod    e   that is eithe    r an operat   io   n (with ch  ildren)   or a nu m   eric    v   alue.
           */
       private     c    las  s Exp   ressionNo de implements      Fo   r      mulaNode {    

              privat e Co     mplex numeric;     
        private St       ring o     perator;
                  p    rivat  e       Formul   aNode left;
             pr     ivate Formu    laNode righ   t     ;
    
        / **
                  * Constr     ucts   an Expr     es        sionNode wi  th the        given numer    ic val  ue   
               * @param n 
             *  /
               pub     lic      Express  ionNode       (Comple  x     n) {
                 numeric = n;
                           o          perator = n            ull;
                 left =   right =           null;  
            }
  
           /**
         *  Con str u   cts   an operator ExpressionNode
                * @p     ara      m  op     the o   peration to b     e p  erformed    
          * @  param l the left parameter of the         ope   ration
                     * @param r the      ri  g   ht parameter of the     operation, if applica          ble
         */
            public Exp  re           ssionNo  de(String o    p, Fo  rmulaN             ode l,     Fo   rm    ul  aNode r)    {  
                              ope   rator = op;
                  n    umeric = null  ;    
                    left = l;
                            r                   igh    t = r;
          }
         
        /**                  
              *       E   v   aluates th     is Expre          ss  ionNode    an       d  , if necess     ary, its c      hildren.
             * @return the res   ult of evaluation
              */
        p  ub     l                 ic    Complex evaluate(   ) {
               if (num   eric != null)                {      
                         retur n             numeri       c;  
                                 } el   se i     f (operato r.equal s  ("+   ")  ) {
                return left.evaluate    ().add (       right.   evaluate());
                       } else if (operat   or.equal      s    ("*" )) {
                                          return         left.evaluate().m  ult (right.    evaluate());
                      } else  if (op  erator.equals("     -")  ) {
                                       return left.e     valuate().s    ub(righ     t.eval  u   ate());     
             }     else if (   operator .equals("/      "    )) {
                         return left.eval             uate().       di         v(right.evaluate());  
            } else if   (operator.equals("^")        ) {
                                                ret     urn     left.ev          aluate()   .pow(  right.evaluate())   ;
                }   else if    (operat  o  r.e   q    ual s("sin"))       {
                                 return l   eft.evaluate()      .s  in();      
                         }          e   lse if (operat or.equals(   "c              os")) {
                     r   et  urn left.eva     luate().c     os( );
                         } els e if (o   pe rator.       e quals("tan"))       {
                                    retu  rn l    eft.evaluate().   ta      n();
            } else if     (op      erato                r.  eq   u     als("as      in")) {
                     re  turn      left.eva             l    u  a   te().asin();
                            } els    e if    (op    erator.equa   l     s(    "acos")) {
                 ret  urn left.eval  uate().a     c   os(  );
               }        el      se    if (oper       ator.   equals("atan"))     {
                               r          eturn left.evalua            te().at   an();    
                                   } else   if (operator.equa   ls("s  i                  n h"              )) {
                            re    turn left.ev         alu   ate(       ).sin  h                            ();
                                 }  else if (operato r  .equals("cosh"  )) {
                                             r      e      tu  rn left.e   valua       te().cosh  (   )          ;
                 } els     e if (operato                r.equal      s("tanh"))     {
                                 return left.e  va luate ().      tanh  ();
                       } else if             (operator.equals("as    inh")) {
                              return left.evalua te().asi            nh()     ;
                                 } el   se if (    o             perator.e   q   ua    ls("      acosh")) {    
                        return     left.evaluate().acosh       ();
            } else            if (operator.eq             uals    ("atanh"   ))  {
                        r       et     urn left.eval     uat  e().ata       nh();     
                        } else if (     operat    or    .equal  s("  lo g")          |    |  ope          r   ator.equa           ls("ln"))      {
                          r   eturn left.evaluate().lo  g(     );
                 } el se if        (operator.equ  als(    " abs") || operat   or.equal  s("mod")) {
                return new     C   omplex(left.eval    uate   ().abs(),    0 )        ;
                      } else        if  (op  erat     o  r.equals("abs2")) {
                             return n        ew Complex(left   .evaluate().abs(      )    , 0);
               } else if    (operator.equals("re"          )) {
                              return new      C       ompl  ex   (  left.evaluate().getReal(), 0);    
                } else if (ope     rator.equ      al  s("im")) {
                         return     ne w     C   omplex(left.evaluate().getImag(), 0);
                } else if (operator.equal   s( "sqr     t"))         {
                                 return lef      t.eva     luate()          .sqr          t();
                    } else   i f (operator.equals("arg")) {
                       re     turn new C   omplex(left.eva   luate().arg(),   0   );
                 } else if (ope   rato        r.    eq  u   als("exp       "))      {
                                      return lef     t.evaluate().                      exp()  ;
            }    else if (operator.equ    als("co nj     "   )) {
                   return  left.evaluat     e().conj(    );    
                       }        
            throw new      Illegal    ArgumentEx     ception("Fun    ction "      + operator
                                                    + " not implemented");
                }

        /**
           * Det    ermines whether this ExpressionNode will always evaluate to the
         * same value
         * @r eturn true if the evaluation       is constant, otherwise false
             */
        public boolean is    Co   nstan t() {
                 r  et   urn nu  meric      != null             || left.  is   Constant() && (right =       = null || righ     t.isConstant());
              }
  
            /**
          *    If this ExpressionNode is con    stant, it wil      l  be simplified to  a
         * nu      meric    .  O therwi   se, its child       ren wi   ll   be   simplif  ied.
           */
        p  ublic    void     simplify(     ) {
                     if   (numeric !=         null) {
                   return; 
            } else if (left.isConstant()   &   & (r  ight == null || right.isConstant())) {
                   nu  mer    ic =   eval   uate();
             }     e  lse {
                left.simplify( );
                     i  f (right    != null) {
                              right.simp  lify();
                }
                }
        }

        /**
             * N   OTE: Never used
         * @return the left   child
            */
           public FormulaNo    de getLeft(  ) { 
            r eturn left;
        }

        /**
         * NO    T     E: Never used
                * @ret   urn     the rig       ht    child
         */
        public FormulaNode getRight() {
                       return right ;
           }

        /**
         * Sets the   left c     hild.  
         * NOTE: Never   used
              * @param l the new le  ft child
              */
        public void setLeft(Fo    rmu      laN  ode l) {
                left = l;
        }

        /**
         * Sets the right ch   ild.  
          * Note: Never    used
         * @param r the new   right child
         */
            public void setRight(FormulaNode r) {
            right = r;  
        } 
    }

    /**
     * A        FormulaNode containing an adjustable value
     */
    private class VariableNode implements FormulaNode {

        private Complex val    ue;

              /**
          * Constructs        a VariableNode with an i   nitial value of 0 + 0i
         */
        public VariableNode() {
            value = new Complex();
        }

        /**
         * Sets the v   alue of this VariableNode to the given value
         * @param v 
            */
        public void setValue(Complex v) {
            value = v;
        }

          /**
         * Gets the value of this VariableNode
         * @return 
         */
        public Complex evaluate() {
            return value;
        }

        /**
             * Returns false because a VariableNode is variable, not constant.
         * @return false
         */
        public boolean isConstant() {
            return false;
        }

        /**
         * Does nothing because a variab     le cannot be simplified to a constant
         * value.
         */
        public void simplify() {
        }
    }
}
