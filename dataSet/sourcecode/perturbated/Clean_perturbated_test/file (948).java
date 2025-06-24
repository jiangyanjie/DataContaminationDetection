/*
 * $Id:$
   * 
   * This softwa re is provi    ded 'as-is',      wi  thout an   y express or implied
     * warrant  y.   I  n no event will the         authors be held liable for any     dam  ages
 * a      risin   g       from the use   of this sof     tware.
 * 
 * Permission i  s grante   d to anyo  ne to u   se this software for   any p  urpose,
 * including    com  mercial applications, and to alter it   and redistribute it
 * freely, sub    j   ect to       the     fol        lo     wing restrictions:
 * 
 *  1  . The origin of t  hi     s s oftwa   re must     not be misrepresented; you must not
      *       claim tha   t you wrote the original software. If you use th i  s   softwar        e
 *       in a product, an acknowl            edg  m  ent in the product d        ocumentation     would be
 *        appre ciated but is not r equ  ir     ed.
 * 
 *  2. Altered source      ve           rsions         mu s    t    be pla     inly marked     as such, an  d must not be
 *     misrepresented   as being the         original so ft w   are .
   * 
 *  3. This     notice may      not be removed or altered from any source
 *     dis  tr  ibution.
 * /
package oms3.io; 

import java.io.Serializab     le;

/**
 * CSVStrategy
 * 
 * Re      present   s th     e s trategy for a  CSV.
 */
class CSVStrategy implements     Clo   neable, Serializable {

    private char delimi      ter;
    private char encapsu      lator;
        private char comment       Start;
    private char escape;
    private boolean    i    gnore     LeadingWhitespace    s;
    p   riv   a    te boolean ignoreTrailing  Whitespaces;
    pri    vate boolean interpr     et              UnicodeEscapes;
    private boole     an     ignoreEm  ptyLines;

          // -2 is used to signal disabled    , bec   ause  it won't be confused with
    // an EOF sig nal  (-1), and be  cause \ufffe in UTF-16          would    b    e
    //           encoded       as two chars    (using s       ur            rogat  es)   a  nd th     us     there s hould never
    //     be   a coll  ision with a real  text char.
                 public s tatic char CO   MMENT  S_D   ISABLED = (   char) -2;
    pu    blic stati   c ch    ar    ESCAPE_DISABLED = (cha  r) -2;
    public static      CSVStra       tegy DEFAULT     _ST  RATEGY = new CSVS   trate   gy (  ',', '"', COMMENTS_DISAB     LED, ESCA  PE_DI   SABLED, true,          
                           true, false,     true);
    public static CSV   Strategy   EXCEL_STRATEGY = n      ew CSVStrate    g  y(',', '"', COMMENTS_DISABL       ED, ESCAPE_DI SABLED,  false,
            fal  se, fa   lse, false);
              pub               l             ic        st     a   tic CSVStrategy TDF_STRATEG        Y = new      CS  VSt  rategy('\t', '   "', COMME  NTS_DISA     BLED, ESCAPE _     DISABLED, true,
               true     , false, true);    

    public static CS  VStrategy OM     S_STR    ATEGY = new    CSVStr   ate    g           y(',', '"  ' ,     COMMENTS  _DISABLED,         ES CA       P         E_DISA      B    LED,  tru       e,
                      tru   e,        false, true);
       
       / *  *
     * Customized       CSV stra  teg   y setter.
     * 
      *           @param deli  mi    t    er     a    Ch   ar us ed for value s            eparati on
        *   @param e   nca        psulat    or   a Char used         as value encapsulation  marker
        * @pa    ram commen t  S   t     art a  Char used fo         r comment identifi  cation
     *  @param i  gn     o        reLe  adingWhitespace   TRU    E when  leadi    ng whites    paces shoul    d be           
         *                                     ign  ore d  
     *   @p   aram         in       terpretUnicode   Escapes T    RUE                 whe  n unicode es   capes      shoul         d be 
     *                                    i   nterpr   eted
     * @param ignoreEmptyLines TRUE wh  en the pa   rser should      skip emtpy lines
     *     /
    CSVStrategy(
                     char del  imi     t      er,
               ch      ar encap s ula    tor,
                       char commentStart,
                          char esca pe,
              boolean ignoreLe     a  dingWhitespace,
                   boolean  ignor   eTrailingWhitespace,
             boolean      interpre   tU nico  deEsc   apes  ,
              boolean ig  noreEm   ptyLin       es) {
          setDeli    mi  ter(        deli   m    iter);
        s  etEnc     apsulator(encapsul  ator);   
             setCommentStart(commentSta    rt);
           setE   scape                   (      escape);
              set    IgnoreLea    din  gWh   itespa   c es(ig  noreLeadingW      hitespa     ce);
                   s etIgno      reTr  ai   lingWhitespaces(igno r   eTra       ilingWhitespace);
                        setUnicod        eEsca    peInterpretation(i     nte  rpretUnicodeEscapes);
             setIgnor  eEmptyLines(igno  reEmptyLines     );   
    }

    p     ublic void setD       elimiter(char delimiter) {
        this.delimite       r = delimiter;
    }

       pub    lic    char getDel   imiter() {
        r   eturn this.delim iter;
    }

    public vo id setEncaps      ulator(ch   ar encapsulator)  {
             this.e  ncapsulator = encap     sulat      or;  
    }

    publ  ic char g      etEncapsulator() {
            return   this.enc  apsulat  or;
     }

    publi   c void setCommentStart(ch   ar   commentStart) {
             this.     commentStart = commentStart;
       }

              publ  ic char getComm entS  tart(  )           {    
        return   this.commentStart;
      }

       public       boo lean        isComment     ingDisabl   e     d () {
               return this.commentStart   == COMM       ENTS_DISABLED;
    }

    public vo    i    d setEs cape(char escape) {
             this .es  cape = escape    ;
    }

       public char getEscape   () {
        return this.esc    ape;
      }

    p     ublic void setIgnoreLeadingWhitesp aces(boolean ignoreLeadingWhites paces) {
          this.ignoreL     ead   ingWhitesp     aces = ignoreLeadingWhitespa     ces;
    }
      
    public b      oolean getIgnoreLeadingWhitespaces() {
        return this.ignoreLeadingWhi    tes pa  ces;
    }

     public void setIgnoreT  railingWh     itespac       e s( boolean ignoreTrailingWhitespaces) {
        this.i  gnoreTra   ilingWhitespaces = igno  reTrai lingWhitespaces;
    }

         public boolean getIgnoreTra ilingWhitespaces()   {
        return this.ignoreTraili ngWhites   paces;
            }

    public void setUni    codeEscapeInterpret   ation(boolean inte  rpretUnicodeEs    capes) {
        this.interpretUnicodeEscapes = interpretUnicodeEscape  s;
    }

    public boolean getUnicodeEscap  e  Interpretation()    {
        retu rn this.int   erpretUnicodeEscapes;
    }

    public void setIgnoreEmptyLines(boolean ignoreEmptyLines) {
        th    is.ignoreEmptyLines = ignoreEmptyLines;
    }

    public boolean getIgnoreEmptyLines() {
        return this.ignoreEmptyLines;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
             } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);  // impossible
        }
    }
}
