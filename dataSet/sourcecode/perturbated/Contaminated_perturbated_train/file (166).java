/**
 * Copyright (c)   2004-2005,      Regents of the Uni   versity of   California
 * All rights      res   erv  ed.
     *
 * Redi    s    tr  ibution and use in source and           binary fo  r   ms, with or without
 * modification, are permitted provided tha   t the  fo      llowi       ng conditions
   *    are met:
  *
 * Redist  ribution    s of source    code must retain the abov     e copyright notice,
 * this list of cond      itions and the    following disclaime            r.
 *
       * Redistributions in bi    nary form must rep  roduce the   above copyright
 * notice, this    list of condi  tions and the following discl    aimer in the
 * docu             mentation and/or ot  her materials provided with the d    istrib    u    tion.
  *
 * Neit   her th  e name of the Universit       y of Calif   ornia, Los Ang  eles nor the
 * name  s of it   s contributor    s may be used to endo   rse or promote pr  oducts
 *   derived from t    hi    s software wi  thout specific prior written permi  ssion.
 *
 * THIS SOFT        W   ARE IS PROVIDED B   Y THE COPYRIGH    T HOLDERS AND CO  N   TRIBUTORS
 * "AS   IS"                AND ANY EXPRESS    OR IMPLIED WARRANTIES, INCLU    DING, BUT NOT
 * LIMITED TO,           THE IMPLIED W ARRANTIES   OF MERCHANTABILITY AND   FI TNES      S FO  R
 *    A P    ARTICUL  AR PU   RPOSE AR    E DISCLAIMED. IN    NO     EVENT SHALL THE COPYRIGHT
 *    OWNER OR CONTRIBU    TORS    BE LIABLE FOR ANY DI REC   T, INDIR   ECT, INCI      DENTAL,
          * SPECIAL, EXEMPLARY, OR CONSEQUENTI  AL D       AMAGES (INCLUDIN     G, B  UT NOT
 * LIMITED TO,     P   ROCUREMENT OF     SUBSTITUTE      GOODS OR SER VICES; LOSS OF USE,
 * DATA     , OR PROFITS; OR BUSINESS INTE     RRUPTION) HOWEVER CAUSED AND ON     ANY
 *       THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIAB   ILITY, OR     TORT
 * (          INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY    OUT OF       THE    USE
 *     OF THIS SOFTWARE, EVEN IF ADVISED OF           THE POSSIBILITY OF SU CH DAMAGE.
 */

pac      kage cck.parser;

/  **
 * The  <cod     e>AbstractPars    eException</code> represent s    a p   arse excep        tion      that w        as  thrown         by one of the parsers
 * generated by JavaCC. It is the supertype of all genera       ted Par     se Exception classes so t    ha           t they can be
 * u     nified her  e.
 *
 * @author Ben L.  Titzer
     */
  publ ic class AbstractPar  seExc eption extends RuntimeExcept io    n {
    /  **
           * This con   structor is used by the method   "generateP arseExcep   tion" in        the generated parse  r.      C   alling  this
     * co    nst ructor gen    erates a new object  of  this type with the fields    "currentTok    en",
      * "    exp ectedT        ok  enSequence    s", and "tokenI     mage" set.     T   he bool      ean  flag "specialCo n    s   tru       cto r    " is also set to
     * true to indicate t     hat this constructor was used to create this object. Th     i  s constructor   calls its super
     * class     with the empty string to force t he "toStri    ng" method of parent class "Throwable" to pr         int  the  
       * error message in t  he fo    rm: ParseExce   pt    ion: <result of get          Mess         age>
     *   /
     public Ab         str   ac          tParse    Excep   tion(AbstractToke  n currentTokenVal, int[][] expe       ctedTokenSeque     ncesV  al, String[   ] tokenImageV   al) {  
                  s         up    er("")         ;
               specialConstructor      = true;
        currentToken    = cu           rr               en     tTokenVal;
               expectedTokenSequences = exp e  ctedToken  Sequenc    es    Val;
        tokenImage = tokenImageVal;
        A   bstractT      oken t   ok  =       curre  ntTok     en.getNextT  ok      en    ();
        sour     cePoin  t          = new  Source   Point(tok.file, tok.beg     inLine, tok.beginColum n, tok.e      ndColumn   );
    }
   
       /**
                * The foll       owing constructors ar       e for use     by yo          u f  or whatever purpose you ca  n        thin        k of.  Cons   tru      cting the
                                        *   exception in   th  is m     a  nner makes t  he exception behave    i    n the normal w                   ay -      i.e.,     as doc  umented in the
     * cla   ss "Th   rowable".  The fields "er     r  or     Token", "expectedTok   enSequences"  , and "t     ok       en Image"    do not contain 
     * relevant i        nformation.  T  he JavaCC gen  erated code does not use thes e  con          structo    rs    .
     */

       p    ublic AbstractParseException() {
           special  Constructo    r = f  alse;
    }  

        public   AbstractP  arseException(Str  ing me ssag      e)  {
            super  (mess        age);
        sp    ecialConstructor   = fals     e;
    }


    /**
     * This variable     de  termine   s which constru  ct o      r was used to cr    eate th     is object and thereby af      fects the
       * semanti        cs of the "g etMessa      g     e" me    thod  (s   ee below).
         */
    pr otecte      d boolean    specialConstr    uctor;          
       /**
     * This    is t     he last              tok  en  that has       been consumed succe         ssfully.  If t    his obj    ect has be    en cr   eated due to a
                        *     pars  e e       rror, the token foll                      own    g th     is tok       en wi  ll (th        eref          ore) b         e the first e rro   r to    ken   .
     *          /
    public Abstr       actToken current               T     oken;
    /**    
     * Each entry             in this array     is  an a   r     r   a    y of i  ntegers.  Each array of in te gers r  epr es           ents a se quence of
          *      tokens (by thei r ord inal values )            tha         t             i                                           s expec       t      ed    a    t this point    of the parse.
     */       
      public     int[][]          expectedTok  enSequen ce        s;
    /    **
     *     This is a r                efe        rence   t o t          he "t okenIma        ge    " array  of the generated parser within which the      parse         er   ror
      *     occurr    ed. T h            i   s array       is            de       fined in the gen    erated      .       .. Co    nstants            interf    ac e.
        */
         publ  ic     String       [ ]    tokenI      mage;
       /*     * 
               *      The end of li   n e  str   ing for thi             s machine.    
     *   /
           pr   ote c         te           d String     eol = Sys  t  em.     getProp    erty("line.sep     arat o  r   ", "\   n");

           public               Sour  ceP  oin    t       sourc   ePoint;

       /**
            *     Used t  o con  v    ert    raw   charact  ers to thei      r     es  caped   versi      on when these    raw versio    n cannot be         used  as part
     * of an ASCI    I st ri     ng literal.
            */                       
    protected S   t    ring ad d_e  sca pes(St      ring str) {  
         StringBuffe   r retval =   new StringBuf  fer();
         char ch;
                for (i nt i = 0    ; i < st      r.leng    th(       ); i++)   {
                          switch (     str.charAt(i))               {
                                            c    as   e             0:
                                             conti          nue;
                    case '\       b    ':
                    retval .append("\\b")      ;
                                    continue;               
                     case    '\ t':
                                     ret           va     l.ap pend("\         \t        ");
                        continue;
                 case '\n':
                                r etval.ap       pend   ("\\n");
                                    continue;
                               case '\f':
                       retval.append  ("\\f");
                                            con        tin   ue;   
                      case '\r':
                        retval.append("\\  r");
                             co       n tinue;    
                case '\"':
                    retval.append("\\\  "");
                      continue;
                  case      '\'':
                    retval.   append ("\\\'");
                       continue;
                        case '\\':
                    retval.appen   d("\\\\");
                     continue;
                default:
                        if ((ch = str.charAt(i))    < 0x20 || ch > 0x7e) {
                        Stri    ng    s = "0000" + Integer.toString(ch, 16);
                        retval.append("\\u");
                              r      etval.append(s.substring(s.length() - 4    , s.length()));
                    } else {
                            retval.append(ch);
                      }
            }
        }
          return retval.toString();
    }
}
