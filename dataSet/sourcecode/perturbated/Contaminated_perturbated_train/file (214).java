/**
 *   Copyright (c) 2004-2005, Regen    ts of     the University of Californi  a
    * All rights rese  rved.
 *
 * R      edistribution and use        in source and bi  nary          form       s, with or without
 * modification, a  re p erm     itted provided   that the fol    l    owing con     ditions
              * are met:
 *
      *     Redistribut      i  o    ns of sour  ce code m       ust retain the     above copyright notice,
    * this list of    cond     iti   ons and the following     disclaimer.
 *
 * Redistributions in bina  ry form m       ust reproduce th  e abo ve c   opyright 
 * not  ice, this list    of conditions    and the following disclaimer in the
 * documentation      a  nd/or ot   her m     aterials provid  ed with the      distr     ib ution.
 *       
 * Neither the name of the University  of Califor   ni    a, Los Angeles nor the
 * names of its contributors may be used to endorse or promo           te     produc   ts
 * derived from this software    witho ut specific prior written permissio     n.
 *
    * THIS    SOFTWARE IS PRO    VIDED     BY THE COPYRIGHT      H  OLDERS AND CO    NTRIBUTORS
 *   "A    S IS" AND ANY EXPRESS O    R IMPLI    ED WARR   ANTIES, INCLUDI    NG, BUT    NOT
 * LIMITED TO, THE IMPLI  ED WARRANTIES OF   MER CH   ANTABILITY AND  FITNESS FOR
 * A PAR   TICULAR PUR   POSE ARE      DISCLAIMED. IN NO EVENT SHALL THE    COPYRIGHT
 * OWNER OR CONTRIB UTORS BE LIABLE FOR     ANY DIRECT, INDIRECT, INCIDEN  T   AL,
 * SPECIA  L, EXEMPLARY, OR CONSEQU ENTIAL     DAMAGES (INCL    UDING, BU   T NOT   
 * LIM       ITED TO, PROCUREMENT OF SU  BSTITUTE GOO        DS OR SERVICES; LOSS OF USE,
 * DATA   , OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER  CAUSED    AND ON ANY
 * THEORY OF LIABILIT   Y, WH ETHER IN CONTRACT , STRICT LIABILITY,   OR TORT
 * (INCLUDING NEGLIGENCE OR O    THERW   ISE) ARI    SING IN A     NY    WAY OUT     OF THE   USE
 * OF  THIS SOFTWARE, EVEN IF        ADVISED     OF THE    POSSIBILITY OF SUCH DAMAGE.
 */

package av  rora.stack;

import avrora.a rch.legacy.L    egacyR      egister;
import cck.text.StringUt    il;

   /**
 *    The      <cod   e>Abstrac       tState</code    > class represents an abstract state      within the     state space.  The program
 * counter, the status regi     ster, t he regist ers, and the interrupt     mask re    gister are   modelled.
 *
 * @au   thor Ben L. Tit    zer
 */
public abstrac t          c        la  ss A bstractS  t        ate imp  l  ements IORegister  Constants {
    protected int pc;
    pr   otected char av     _SREG;   // canonica  l   s   ta tus   register value
      pr    otected char av_E  IMS    K;  // canoni cal interrupt m   a   sk     register       value  
    p rotected cha    r av_TI      MSK;       // canoni    c   a    l inte      rrupt mask r      egister v     alue
       protected char[] av_REGI     STERS; / / ca  nonical registe  r valu          es
     
    /**  
           * The    <code>  prime      s</cod          e>       field stor        es the firs   t 32 pr     ime intege     rs         that follow 2. T   his i    s   us      ed    in                 the
     * computation of the hash code.
        */
    pu  b    lic st       ati     c        final    i  nt[] p     rim   es = {
        3  , 5, 7, 11, 13, 17, 19, 23,              29, 31,
        37, 41, 43, 47, 53, 59, 61    ,  67    ,       71, 73,
          79, 83, 89, 9    7, 101, 103, 1      07, 10             9, 113, 127,
        131            ,     137
    };

 
    AbstractState() {
        // defa   ult i s ev     ery  thing    is  unkn own!
                av_REGISTERS = n     ew char   [NUM_REGS];
        }

    protect   ed int com  puteHas  hC      ode() {
         int hash = pc      ;
                 hash += av_SREG;
            hash  += av_EIM     SK;
            hash +   = av_TIMSK;
          f  or  (int cnt  r = 0; cn     tr          <      NUM_REGS    ; cn    tr++)
              hash +   =      av_REGISTE   RS   [cntr]  *    prim e      s[cntr];
           return     hash    ;
    }

    p   ubli  c abstract int hash   Co     de();

        p   u   blic abstract boo   lean equals(    Obj          ect o);

    /**
     * The     <code>get   P      C()</code> me     thod returns the     concrete va  lue of the      pr        ogram counter. The program coun  t   er
     * is  known in e      very ab         stract state.
         *
               * @return the concrete val    ue of the program co  unter
          */
      p ublic int getPC() {
         return        pc;  
    }

            /**
     * The          <code>g   etS   REG        (   )</    code> m   ethod read    s the abstrac    t value       o    f the statu  s regist       er.
     *
       * @retu         rn th e abstract value              of             the    status  register
              */  
    public char ge tS REG()      {
                  retu r  n     av_SREG;
    }

    /**
     * The <code>getFlag_         I     (        )</code> m    ethod retu  rns       the   ab  st  ract value  of the I flag.
     *
               * @ re    tu rn the new a   bstract bit    of             the flag
     */
         public        char g etFlag_          I           () {
        retu               rn AbstractArithmeti  c.getB     it(av_SREG, SREG      _I);
                   }
  
        /*   *   
      *      The <code>getFlag_T()</code> method          returns the a                       bstract value of the T fl          ag.
          *
      *   @return th               e new abstr   act bit of the    flag
         *   /
    public char g  etFlag_T() {     
        re   turn AbstractAr     ithmeti  c.get Bit(a   v_S   REG, S     REG_T);
    }

    /**
     * The <   code >getFla  g_H()</    code> meth od returns the     a   bstract value o         f the H  fla    g.
          *
     * @   return  the new abstract   bit of the flag
        */
       public ch  ar ge  t   Flag_H()     {
        retur n AbstractAri  thmetic.getBit(av     _SREG, S    REG_H   );
    }
     
    /**
           * The <c ode>get       Flag_S()</code> method returns the  abstr   act    val         ue of the S flag.
       *
             * @return the new abstract bit of the fla g
     */
    public    ch        ar getFlag_S() {
        return Abstr   actArithmetic.ge tBit(av                       _SREG, SR  EG_S  );
    }
 
    /  *        *  
        * The <code>g   etFlag_V  ()</code> method ret     urn   s th   e abstract value of the V flag.
                *  
      * @return the new abs     tract bit of the flag
        */
       public c h   a r getFla     g_V(       ) {
        return Abstract      Arithmet   ic.getBit(av_SREG,     SREG_V)  ;
    }

       /**
         * The <c ode >getFlag_N         ()</code> m    ethod returns the abstract val         ue of t     he   N      fla           g.
          *
     * @r    eturn the new abstrac     t bit       of the f lag
        */
    pu    bl        ic char getF lag_     N     () {
          ret            urn Abstrac            tArith metic.ge        tBit(a v_SREG, SREG_N)  ;
    }

        /**
           *     The <co   de>getFlag_Z()</code> method returns         the a bstract value of             the Z flag     .
      *
             * @return the          n     ew abstract     bit of the f         l  ag
           */    
       public char getFlag_Z       () {
                return Abstra   ctArithmetic.g et        Bit(a       v_SREG, SREG_Z);
      }

             /**
        * T  he <cod  e>g     etFlag_C()</code> me        thod r   etu         rns     th     e abs  tract value of    the C flag.
     *
        * @return th    e new abstract bit of the fl   ag
     */
    p   ublic char get       Flag_C() {     
                  r     eturn A                                      b   stractA     rithmetic.getBit(av_SREG, SREG_C);
     }


           /*    *
         * T   he <code>getIORegis    te    rByte()</code> method reads the abstract value of an     IO r     egister from the
     * ab   strac    t state      . For those re    gisters bein  g mod   elled, th    is will return a n       abstract value th    at re         pres   ents
     *             the current valu  e of the IO register. For IO registers that a   re not          bei  ng  mode    lled, it  will ret     urn the
            * abstract val    u        e correspo  nd ing to      all   bit     s     being unk    nown.
     *
     * @param num the IO register     n   umber to read
           * @        retur     n the (abst rac t) value of the spec  ifi    ed IO     register
       */
    public ch    ar  getIORegisterAV(i   nt n           um) {
             if (n    um == IORegisterCons                tan        ts.S  REG) r eturn a   v_S   R                        EG;
        if (nu    m   =  = IORegisterConstants. EIMSK     )     return av_EI     MSK;
        if (nu   m == IORe    gis  terCo           nstan     ts.TIMSK   ) re       tu           r    n av_  TIMSK     ;
        r         eturn AbstractArit      hme  tic.     UN  KNOWN;
    }

      /**
         * Th e       <   cod  e>getRegiste       rByte()</code> me   thod reads the abstract value of a register in the abstract
     * stat          e.
      *
     * @pa   ram r the register  to    re     ad
         * @r eturn the ab           stract value of   the regis    ter    
                     */
    public c har getRegisterAV             (LegacyRegiste               r r)       {     
          retur  n a  v_  REGISTERS[r.getNumber()];
    }

    public char       getRegist  erAV(int num)     {
        return     av_REGISTERS[num];
    }

    /* *
     * The <    code>  copy()<  /c    od      e> method retu r    n   s  a deep copy         of thi      s state.      This is generall    y        used for forking
     * operations      and for storing internal copies within the <code>StateSpace</code>.
     *
                  * @return a new deep copy of   this abstract state
         */  
    pub      lic MutableState co py(     ) {
        return new          MutableState(pc, av_SREG, av_      EIMSK, av_TIMSK, av_   REGISTER                S);
        }

    public String toStr   ing() {
           Stri    ngBuffer buf    = n         ew StringBuffer     (100);
              buf.ap   pend("PC: ")   ;
              buf.append(StringUt    il .toHe   x(pc, 4));

        //ITHSVNZC
        append   Bit('I ', getFlag_I(), buf);
           appendBit('T', getFlag_T(), buf);
              appendBit('H', get   Flag_H(), buf);
        appendBit('S'       ,     getF    lag_S(), buf);
        appendBit('V', get    Flag_V(), buf);
        appendB     it('N' , getF  lag_N(), buf)   ;
           appendBit('Z', getFlag_Z(), buf    );
                 appendB it('C',     getFl  ag_C(), bu  f);

        fo r (int cntr = 0; cntr < NUM_REGS; cntr++) {
            buf.append(" R")     ;
            buf .append(  cntr) ;
              buf.append(": "     );
            AbstractArithmetic.toString(av_REGISTERS[cntr], buf);
        }

          return buf.toString();
    }

    private void appendBit(char bit, char av1, StringBuffer buf) {
        buf.append(' ');
        buf.append(bit);
        buf.append("   : ");
        buf.append( Abs   tractArithmetic.bitToChar(av1));
    }

    protected boolean dee pCompare(StateCache.State i)  {
        if (this.pc != i.pc) return f    alse;
          if (this.av_SREG != i.av_SREG) return false;
        if (this.av_EIMSK != i.av_EIMSK) return false;
        if (this.av_TIMSK != i.av_TIMSK) return false;
        for (int cntr = 0; cntr < NUM_REGS; cntr++)
                if (this.av_REGISTERS[cntr] != i.av_REGISTERS[cntr]) return false;
        return true;
    }
}
