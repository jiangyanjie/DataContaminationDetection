/**
 * Copyright  (c) 2004-2005,   Rege    nts of the Univer  sity of Califo   rnia
 * All    rights reserve  d.
 *
 * R   edis   tribution and use    in    so  urce and binary forms, with or witho    ut
 * modification, are pe  rm     itted     provi      ded that the following conditions
 * are       me  t:
  *
  * Redistributions of source code must r etain the abo    ve copyright      notice,
  * this list of conditions and the following disclaimer.
 *
 *    Redistributions in binary form must repr    oduce the          above c   o  pyright
 * notice, this list of co            n   ditions a        n  d the following  disclaimer in the
 * documentation a nd/or other materials provided with the distribut  ion.
 *
 * N    either the n  ame of the Universit        y of   California, Los    Angeles nor th  e 
 * names of its cont     ributors may be us     ed t                o endors  e or        p  romote products
 * derived fro      m this software without s  pecific    prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBU   TORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED    WARRANTIES, INC   LUDING, BUT NOT
 * LI  MITE     D TO, THE IMPLIED WARRANTIES OF MERCHANTA  BILITY AND FITNESS FO    R  
        * A                PARTICULAR PURPOSE    ARE DISCL            AIMED.     I   N NO EVENT SHAL     L THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR AN  Y DIRE CT  ,     INDI   RECT, I  NCIDENTAL,
 * SPECIAL ,    EXEMPL    ARY, OR CONSEQUENTIA     L DAMAGES (INCLUDING, BUT NOT  
 * LIMITED    TO, PROCUREMENT OF SU    BS TITUTE GOODS OR SERV    ICES; LO  SS OF   USE,
 * DATA, OR PROF   IT  S; OR BUSI    NESS INTER   RU   PTION) HOWEVER    CAUSED AND ON AN    Y  
 * THEORY OF LIABILITY, WHETHER      IN CONTR   ACT, STRI  CT LIAB   ILITY, OR TORT
 * (INCLUDING NE    GLIGENCE OR OTHERWISE     )    AR    ISIN       G IN ANY WAY OUT OF THE US  E
 * OF  THIS SOFTWARE, EVEN IF AD  VISED OF THE POSSIBILITY OF    SUCH DAMAGE.
 */     

package ji    ntgen.    jigir;

import cc     k.parser.S    ourcePoi  nt;
import cck.text.StringUtil;
imp ort jint     gen      .types.Typ   e  Ref;

/**     
 *   The <code    >C    onversionExpr<  /cod       e> class r     epresents a conv     ersio   n     of a v          alue from one ty    pe to another.
 *    
 * @autho    r Ben L. Titze           r
 */
public class Conv  ersionExpr exten   ds            E    xpr   {

             /**   
     * The <code> typename</code> field s   tores a reference to the na   me of the map whose  element    i    s         being
         *   acce  ssed.
             */
     public fina         l T ypeRef typeRef;
  
    /**
       * The          <code>ex   pr</c  ode>       field stores        a referenc          e          s to the expression which is evalu             ated   to              yield the expr
             * into the    map.
     */
        public final  Expr    e  xp          r;

    /   **
      * The constr    uctor of            the <code>Co  nver sionExpr</ code> class i   ni  tiali    zes the p              ublicly accessa      ble fields that
           * rep         rese  n     t the mem    bers of   this expression
     *
     * @p      aram s           the     string name of the map as a token
         * @par          am   i a   n expressio    n representing the expr into the    map
     */
    public Conver   sionExpr(Expr     i,        TypeRef s) {
            t ypeRef      = s ;       
             e    xpr = i   ;
    }   
           
    /**
     * The <code>accept()</code> metho   d implements        on    e half of the    visitor    pattern so     tha      t cl      ie  nt    visitors can
     * traver     se the syntax tree easily and       in an extensible way.
     *
           * @param v the   visitor    to acce   pt
     */
    pub     lic    vo     id         accept(CodeVisitor v) {
        v    .v      isit(this);
    }

             /**
       *   T    he <code>acc  ept(  )</      code> met hod implements one ha  lf    of the visit      o          r   pat    tern         for rebuilding of
     *  expressions. T         h     is  vis          itor allows code to be slightly mo d     ified   while only writing visi      t m           ethods f   or the
     * parts of the        sy          ntax tree af  fected.
       *
            * @p  aram     r   t  he rebuilder   t  o a  ccept
        * @return the result      of call ing the appropriate <code>v    isi       t()</   code> method of the r        ebuil     de      r
     */
        pu  blic <Res,   Env> Re  s accept    (CodeAccumula  tor<Re          s,     Env> r          , Env env)   {
        re         turn r.visit   (this, env);
    }

    /**
      * The <code>toString() </code> method re       cursive    ly converts t  h  is    ex          pression to a       string.     For binary
      * operations, inner     expressions will be nested within parent  hese        s   if their pre cedence is lower than the
     * p  recedence of the parent     e  xpression.
          *
     * @ret  urn    a string representation of this expression
     */     
    public     Strin  g toString() {
          return StringUti   l.embed("$" +    typ   eRef, expr);
    }

    /**
     * The <  code>getP  recedence()</code> method gets the binding precedence for this expression. This is u   sed
            * to compute when    inner expressions must be nested wit       hin parentheses in order to   pres    erve the implied
     * order o   f evaluation.
     *
     * @return an integer    representing the precedence of this expression; higher numbers are higher
     *           precedence
     */
    public int getPrecedence() {
          return PREC_TERM;
    }

    public SourcePoint getSourcePoint() {
        return expr.getSourcePoint();
    }
}
