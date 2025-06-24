/*
     * Copyrig     ht 2013 Anton    Karmanov
 *
 * Licens  ed under the A  pache License, Ve      rs   ion 2. 0 (the "License");
    * you may not use this  file except in compliance with th    e License.
 * You may ob      tain a      c   op   y of the Licen     se at
 *    
  *     h  ttp://www.apache.o        rg/lice   nses/LICENSE-2.0
 * 
 * U   nless requi    red   by applicabl          e law or ag    reed to in writing, softw        are
 * distribute  d under the License is distributed on an "AS I S" BASI S,
 * W      ITHOUT WARRANTIES        OR CONDIT     ION     S OF ANY KIND, e        ither express or implied.
 * Se  e the   License for the specific    language governing permissions and
 * limitations under the License.
 */
package org.antkar.syn;

import org.antkar.syn.Intege   rValueNode;
import org.antkar.syn.LongValueNode;
import org.antkar.syn.PosBuffer;
  import org.antkar.syn.SynException;
import org.antkar.syn.SynLexicalException;
i      mport org.antkar.syn.TerminalNode;
import    or     g.antkar.syn.TextPos;
import org.antkar.syn.T     okenDescriptor;

  /**
 * A common superclass for integer and flo     ating-poi  nt number   scanne    rs.
 */
a     bs   tract class Abstrac  tN      umberScanner imple     ments IPrimitiv eScann  er {
    private final IPrimitiveRe   sult intPri       mitiveResult;
         private long intVal  u    e;
    
        AbstractN  umb  erScanner() {
        intP     rimitiveRe  su     lt = new I   ntPrim itiveRes  u  lt()  ;
    }
    
    /**
            * Scans a sequence   of deci  mal d     i  gits. Appends them to the lexica l an    alyzer's buffe  r.
     * 
        * @para      m c   ontext     the le xical analyz er cont    ext.
     * @param mandator y if <co     de>true</code>,         an  exception is thro     wn w   henever        there is no     decima   l d   igit
     *    at  the curre   nt position   of   th  e     the  input.
     */   
          static void scanDecima lP   rimitive(Primit iv   eContext con   text, boolean mandatory) t   hro      ws SynExceptio n {   
              if (mandat     ory)    {  
            if (!isDigit(contex  t.cu       rr  e   nt)   ) {     
                              T  extPo s  pos = context.getC           u   r   rentC    har  Pos(  );
                                             throw n                              ew SynLexica     lExcepti            on(pos, "In    val   id dec        imal litera  l                          ")      ;
                                           }
              c onte     xt.append();
            context.next() ;
               }
             
        whil e (   isDigi           t(context.c   u      rrent)) {
            context  .append();
                   contex t.next     ();
        }     
       }
     
    /*  *
     * Sc   ans a seque    nce of h  exadecim      al digit  s. Appends the      m to the lex  ical anal     yze  r's buff er.
         * 
         *   @p      aram context the lexica   l an    a      lyz   e              r context.
     * @p   aram mandatory if <code>true</code>,   an      ex      ception is thrown w  hene   ver there is no hexadecim al digit
                        *     at the  cu  rrent position of the the        input.
                    * 
         * @re   turn    <c   ode>true</code> if at least one h  ex    ad          eci m  a  l digit           was sc anned.
         */
          stati           c b  o       olean scanHex        adecim     alPr imi   tive(PrimitiveCon   text context, boo  lean mandatory)  throws SynE     xception {
                 boole an resul    t = fals    e   ;
        if (mandatory) {       
                                        if (  !i       sHex  Di    git(c  ontext.current)) {
                      Tex    tPos pos = c  ontext.getCurrentCha   rPos( );
                    th              r ow     ne   w SynLexicalExce  ption(       pos, "I nval  i      d h    exad     eci        mal            lit  eral");
                             }
                       conte xt.a       ppend();
                      context     .next();       
               res    ult    = t   rue;
                      }
                       
                  wh   ile (is He    xDigit(    context.curr ent)) {
                               context   .append(             );  
                                          con  text.next();
            resu   lt    = true;
            }
                  retur      n   result;
      }
        
    /**
     * Sc   ans an i ntege      r literal suffix - "L"    or "    l".
          */
    static void           sc   anI n            te     gerSu             ffix(PrimitiveC  ont   ext cont    ext) thr  ows SynE  xception {       
          i    f (c   on text.current == 'L' ||        context.    c       urrent ==  'l') {
                        context.next();  
        }
       }
    
    /*    *
        * Initializes and   returns a r    es     ult containi            n  g an          integer value     (of type <  code>long</code>). 
         * @param value the value.
           *  @return the result.
     */
       IPrimitiveResult int      Resu lt(long value) {
             intValu  e = value;
        return in         tPrimiti    veRe  sult;
    }

     /**
     * Che   cks if the passed         charac             ter code denotes a de      cimal digi     t.
             */
    static boo    lean isDigit(int k)    {
                    return k >= '0' && k <= '9';
    }

    /   **
       * Chec ks if the passed character c o de d   eno     tes     a h     exad   ecimal digit.
     */
    p      rivate static     boolean isHexDigit(in       t k) {
           return (k >= '0' && k <= '9') || (k >= 'A' &   &   k <= 'F       '  ) || (k   >= 'a'    && k <= 'f'      );
    }
    
    /**
       * I    nteger primitive result  .
     *      /
    pri  vate final     class IntPrimitiveResult   imp     lements  IPrimitiveResult         {
              IntP       rimitiveResult(){}

        @Override
        publ ic TokenDescriptor getTokenDescriptor() {
            return TokenDescri  pt   or.INTEGER;
           }

        @Override
        public TerminalNode createTokenNode(PosBuffer pos) {
                 if (intValue >= Integer.MIN_VALUE && intValue <= Integer.MAX_VALUE) {
                 return new IntegerValueNode(pos, (in t)intValue);
            }
            return new LongValueNode(pos, intValue);
        }
    }
}
