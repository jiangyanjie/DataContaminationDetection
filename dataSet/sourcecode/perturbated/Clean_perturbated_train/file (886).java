/*
 *    ORACLE PROPRIETARY/CONFIDENTI   AL. Use is subject            to license terms.
 *
 *
    *
 *
 *
    *
  *
 *
     *
    *
 *
         *
 *
                *
 *
 *
 *
 *
 * 
 *
 */

/*
 *
 *
 *
   *
 *
     *   Copyright (c) 2000    Worl d Wide Web Consortium,
 * (Massachusetts Institute    of Technology, I     nstit    ut    National de
 *  Recherche en Informatiq   ue et  en Automatique, Kei   o Universit    y). All
 * Right     s R eserved. T    his program is distr  ibuted under the W3      C's Soft  ware
 * Intellectual Prop  erty License. This program is d      istributed in the
 * hope that it will be usefu l, but WITHOUT   ANY WARRANTY;     without even
 * the implied warranty of MERC  HANTABILITY or     FITNESS FOR A PARTICU    L     AR
 *    PURPOSE.
 * See W3C License http:      //www.w3.org/Consortium/Legal/ for more details.
 */

package org.w3c.dom.css;

import org.w3c.dom.DOM   Exception;

/**
 *  The <code>CSS2Properties</code> interface represents a convenience
 *         mechanism for retrieving            and setting      p  roperties withi    n a
 * <code>CSSSty        leDeclaratio    n</code>. The att         ributes of this interface
 *     corresp             ond to    all the pro     perties s   pecified in     CSS2. Ge   tting an attribute
        *   of this   interface is equivalent to ca    lling the
 * <code>getPropertyValue</code> me   thod of       the
 * <code     >CSS     StyleDeclaration</c        ode>    interface. Se  tting   an attribute of this
 * interface       i     s equivalent to calling the <c    ode>setProperty</code> method of
 * the <code>CSSStyleDeclaration</code> interface.
 * <p> A conforman t imp   lementation of the CSS modul     e is no        t r      equired              to
 * imple      ment the <co   de>CSS2P  roperties</code> int      erface. If      an implementation
   * does im     plement this interface, the expectation is tha    t lang     ua  ge-specif ic
       * methods can be used    to   cast from an ins tan   ce o  f the
 * <code>CSSStyleDeclaration</cod   e> interface to the
 * <cod   e>CSS2Prop      erties</code> interface.
      * <p> If an implement   ation does i   mpl ement this   interface,   it is expected to
 * understand the spec    ific syntax of the shortha   nd properties, and apply
   * their semantics;  when the <c   ode>margin</co    de> property is  set, f or
 * example, the <code>marg   inTop</code>, <code>m argin    Right</code>,
 * <code>marginBottom</code> and <code>ma     rg     inLeft</code>   properties  are
 * actually being     set by the underly   ing implementa    tion.  
 * <p> When dealing with CSS "shorthand" properties, the shorthand properties
 * should be decomposed into their component longhan          d pro     perti         es as
 * appropriate, and when     quer  y    ing f or    their value, the form    returned shou          ld
      * be th    e shortest for    m exactl  y     equivale nt to the declarati    ons made in the
 * rulese  t. However, if th       ere is no shorthand dec     laration th  at could       be
 * added to the r  u         lese  t withou t changing in any way the    rules alre   ady
 * declared     i  n th  e r     u        leset (i.e., by add   ing longhand rules that were
       * previously not  declared i   n th e ruleset), then the empty string should b        e
 * returned  for the shorth    and property.
 * <p> For  ex   ample, querying for the     <c  ode>font </code> property should not
 * return "norma      l   normal   normal 14p    t/normal Arial, sans-s   er   if", when "14pt
 * Aria  l, sans-serif" su     ffice     s. (The normals are init ial values,  and are
 * implied by use of the l  onghand property  .)
 * <p       > If   the values for all         the longhand properties th             at   c         ompose a particular
 * string are the i      nitial values, then             a strin  g consisting of all th        e
 * ini  t   ial values  should          be r    etu  rned (e.g. a     <code>bor  der-width</code> value
 * of "medi u    m" sh  ould be returned  as      such, n   ot as "").
 * <p> For    some shorthand prope   rties that take missing val    ues fr    om other
 * sides, such as the  <code>ma     rgin</code>, <code>padding</code >,   and
 * <code>border-[width     |       style|colo  r]</code> propertie   s , the min imum num  ber  of
 * sides possible should be    used; i.e.,      "0p    x 10px" will be returned instead
 * of "0      px 10px     0px 10px"   .
 * <p> If the val ue of a s   horthand pro  perty can n   o         t be decomposed     in     to its    
 *  compone         nt longhand properti   es,         as is the case for  the  <code>font</c      ode>
    * property wit    h a val     ue of "   menu", query  ing for the val  ues of     the  component
 * longhand properties should return    the empty    st   rin       g.      
 *  <p>S     ee also the <a hr    ef  =' http:/      /www.w3.org/TR/2000/REC-DO M-Le   vel-2-Style-20001113'>Document Object Mode      l (DOM)     Level         2 S  tyle Sp   e   cificat i   o  n</a>.
 * @s        ince D     OM Level 2
 */  
public in terface    CSS   2Prop   erties {
    /**
         *           See the a   zimut h prop     ert  y de finiti   on in CSS2  .
           */
         public    Str ing ge     tAzimut          h();        
    /**
       *  See the azi    muth property definition in C    S     S2   .
      * @exc     eption DOMExcep tion
     *   SY   NTAX_ERR:   R     aised if the          new value h  a    s a synt   ax erro r and is
              *        unpars    able.
     *    <br>NO_MODIFICATION_ALLOWED_E RR:   Raised  if      this     proper      ty is re    adonly.
     */
              public void setAzimuth  (St    ring    azim          u   th)
                                                                                           thro ws DOMException;     

      /**
       *  See     the backgr ound proper ty definition in CSS2.
         */
    pub    lic String g   etBac   kgrou  nd  (); 
    /*     *
        *  See the background     pr        o  perty definition in CSS2.
     * @e       xceptio n DOME   xc        ep         t   ion  
     *         SYNTAX_E       RR:   Ra   ised  if      the new v  a    lue             has a synta x error and is
        *        unparsable.
           *   <b      r>NO_MODIFICATION_ALLOWED_ERR: Raised i  f this   property is  readonl   y.
     *    /
            public vo   id       setBackgro   und(     Strin  g backgro    und)
                                                          thro  ws DO               ME        xce   ption;  

    /**   
        *      Se       e    the ba  ck  ground-att   achment     property definition in CSS  2.
        */
    publi   c Stri        n   g ge   tBa         ckg      r    o   u nd    At  tachme    nt();
    /**
     *  S        ee the bac   kgroun  d        -attach   ment proper     ty  definitio n in     CSS2.
     * @exc      eption D O  MExce   pt    io n
     *        SYNTAX_ERR: Rais  ed if  the ne  w valu       e        has a syntax e   rror and i  s
     *          unparsable.             
      *           <br>NO_MO  DIF   ICATI ON     _A  LLOWED_ERR: R         aised i            f this pro   perty is    readon  ly.
            */
       public void setBackground Attachment(Strin    g backgroundAttachme   nt)
                                                                            throws DOMEx  c          eption;

    /**
       *                  See the      backg    round         -color prope    rty def       i   ni tio   n in CSS2     .
     */
    p     u     blic String getBackgroundColor  (); 
     /    **
       *             See        the background-color p     rope    rty definition in       CSS2.
     * @e x      ceptio        n       DOMExceptio    n
         *           SYN         TAX_ERR: R   aise             d if the   new    val        ue has    a syntax error and is
     *      unparsa    ble.
     *      <br>N     O_MODIFI CATION_ALLOWED_ERR: Raised if this proper  ty is  rea donly.
          *   /
                   p   ub li   c voi   d setBackgroundColor(String ba ckgr oun   dColor)   
                                                   throws D      O        MException  ;
 
           /**
         *  See the bac kground-image property     defini   tion in CSS2.
          */
          p    ublic S        tr                ing getB      ackgr     oundImage();
       /**
          *   See th    e    bac         kground-imag   e p  roperty de   fin  ition  in C      SS2.
           * @exce      ption        DOM Ex                      cepti   on
     *   SY      NT  AX_ERR: Raised if the ne    w value   has a syntax error and     is
          *      u      nparsable.
      *   <br>  NO_MODIFICATIO      N _ALLOWED_ERR: R    ai   se  d if this propert   y is reado   nly.
              */     
               p    ubli c v        oid setBa   ckgroundIma g  e(String backgr  oun  dImage)
                                                                                        throw   s DOMEx   ceptio  n    ;

         /**
        *  See t   he ba    ckground-positi   on proper  ty definition i      n CSS2.
     */       
    public Str     ing getBackgroundPositio       n();
    /     **
        *  See   the backgroun d-position pr        opert  y defi nit  ion                     in CSS2. 
     * @ex    cepti    on DOMExc e  pti   on
     *   SYNTA X_ERR : Ra ised if      the new   value has a syntax error         and is
                         *   u npar sa   b   le.
        *   <b  r>   NO  _MODIFICATION_  A       LLOWED_ER    R: Rai sed  if this     pr ope  rt          y i     s        readonly.
       */
          public voi                  d        setBackg       roun  dP    ositio     n(Str   ing backgroundPosition)
                                                                                              thro  ws DOMExcep   tion;

          /**
     *  See                     the background-repeat pr  ope rty      defin    ition in CSS2.   
        */
                 public S          tring         get            B       ac    kground    Repea    t();
      /**
     *  See the background-repeat property       def   initi  on in   CSS2 .
     * @exception      D    OMException
           *   S     YNTA X_ERR: Raised if the n         e    w value     has a syntax er           ror   and        is
           *     unpar  sable.
              *   <br>NO_MODIFICATION _ALLOWED_ERR  : Rais         e   d if th                         is     prop  er        ty   is rea    donly.
     */   
    public   void setBackgroundRe peat(String backgroundRepeat)
                                                                   throws DOMExceptio n;

       /* *
     *    See the border pro   pe    rty definition in CSS2.
             */
      publ      ic Strin     g  getB     or  der();
      /**  
        *  See          the borde      r       pro        p  erty definition in             CSS2.
        * @exce     pt     ion DOME  xc    epti    on
         *                    SY NTAX_E    RR: Raised i  f       the  new value has   a   s   ynta     x error an         d   is
         *   un     pars      able.     
     *       <  br    >NO_M    OD    IFICATI    ON        _AL   LOWED_ERR: Ra  ised if thi  s  pr    operty is readonly            .
     */
           public    voi      d setBord  er(String      border)
                                                            thr  ows DOMException;

        /**
     *      See   the b   orde  r-co       lla     pse p   roper   ty de finition in CSS2.
     */
     public String g  etBorderCollapse();
    /                  *   *   
     *  See the borde   r-coll apse property def     in  ition      i n CSS2.
                             * @e  xception DO       M      Exception
          *      SY  NTAX_E   RR:    R    aised    if        the new      value         has        a syntax error and is
     *          un      parsab le.
     *   <br>NO_MOD             IFICATION_  ALLOW     E         D_ER  R: R          aised if this prop              e r  ty i      s readonly.
        */
           publi   c vo   i    d s   e  tBorder  Colla            ps e(S  tring borderCollapse) 
                                                             throws D      O   M E    xce  ption;

        /*       *
               *  See the      bor          der-     color   property   defi   nition    in CSS2.
     */
    pu     b      lic S    t           r    ing getBorderColor();
    /**
     *    See   t  he      bor     der-c    olo r    property def  init    ion in CSS2.
     *      @ex cept       ion DOMExcep    tion
               *          SYNTAX_ERR: Rais       ed if   the new value has a syntax   er       r or and is
        *       unparsabl  e.
              *      <br>N O_MODIFI    CATION_A  LLOWED_ERR     : Raised     if this prope    rty is readonly.
     */
                  public void setBo            r    derCo     lor(String borderColor)
                                                      t     hrows DOMEx   ception;

    /**
     *  See th e     border-sp    ac i        n    g pr   op      erty definit   io   n in    CSS2.
      */
             public Str        ing g  etBo   rd    erSpacing();
          /**
     *  S   e e th        e border-sp      ac    ing     pro  p   erty de     f      inition in  CSS2.
      * @excep tio   n    DOMExcep tion 
     *          SYNTAX_ERR: Raised    if the new value has a syntax             error and is
          *   unparsable.
          *   <br>NO_MODIFICATIO  N_ALLOWED_ERR: Raised    if t   his  property is readonly .
             * /
        publi  c void setBorderSpacing(Strin         g bor             der  Spacin   g)
                                                                      throw       s    DOMExcep        t ion;

    /**
                    *  See  the bo rder-        style        property defin           ition in    CSS2.
     */
       public Str      ing getB      orderStyle(); 
       /**
     *  See the border-style     proper     ty    d      efinit    i   on in CSS2.
           * @exception  DOM      Exception          
         *   SYNT  AX_ERR: Rai    sed if the ne  w v  a lue has a synt    ax   e   rror and is
      *       unparsable.         
     *   <       br>NO_MODIF            I  CATION                     _AL       LO      WED_ERR: Rai        sed if this property     is rea donly.
     */
          public void se   tBord   erSt    yle     (       Stri    ng borderStyl       e)
                                                          throws         DOME  xception;

      /             **
     *  Se e the   bo      rd     er-    t                o  p prop  ert       y definition     in       CSS2.
     */    
        public String  getBorde                               rTop ();
    /**
           *               Se            e         the border-   top pr operty definition i    n CS   S2.
       * @except     ion    DOME   xception
      *   SYNTAX_E      RR: Raised  if the new valu       e has            a      syn tax           error and is
             *                        unparsable.
     *                <br>NO_MODIFICATIO  N_ALLOWED_E      RR: Rai    sed if this   property is readonly.
          */
            pub   lic v         oid setBorderTop(String borderTo     p)
                                                         throw       s DOMExceptio n;

    /**
        *  See the borde   r     -right     prop  erty definition in CSS2.
     *      /  
    p               ublic String g     etBorderRig  ht()  ;
         /**
      *  S       e  e          the border    -right property definition   in     C SS2.  
     * @exception DOMExc ept     ion   
        *   SYNTAX_ERR: R           ais       ed if the new value has a syn  tax error and     is
      *   unparsabl         e.  
         *   <br>NO       _MODIFICATION_  ALLOWED_ERR: R   a ised      if thi    s            property i           s   rea       do      nly.
           */
    public vo  id       setBor    de                   rRight(   S  tr  ing borderRight)
                                                                       throws DOME      xception;

                  /**
            *  See the bord    er-b       ottom p   roperty def  inition in CSS2.    
     */
    publi    c S  tr       ing get     Border Bot   tom();
             /**
     *       Se  e the      border-b    ottom pro      perty     definition in CSS2.
           * @exc           e  ption DOMExc     eption
     *   SYNTAX_ERR: Rai  s  ed   i          f the new v   alue ha    s     a       s       yntax     e   rror and i   s
         *    unparsable.
     *   <br>NO_MODIFICAT  IO    N_ALLO  WED_ERR: Raised if thi    s   propert        y is read     o  nly.        
                    */
    publ     ic   void setBorderBo   ttom(String borderBot  tom)
                                                              th     rows DOMException;

       /**
        *  See the border-left pr  operty d   efinition     in CS    S   2.
     */
            public String  ge    t BorderLe  ft( );
    /**
                *     See t   he border-     left p roperty d  efi  nition in CSS2.
     * @exception DOMExcep    t      ion
            *   SYNTAX_E     RR:  Raised if the ne w value has a syntax error and is
     *                   unparsable   .
          *   <br>N O_MOD IFICATION_ALLOWED_ERR: Raised if th      is property   i s rea        d   only.
     */
            p     ubl   ic v     oi          d setB                    o      rderLef t    (S   tring bord  erLeft)
                                                                                        th  rows       DO    MExcepti on;

               /**
           *  See the bo  rder-top-col or        p        r    o    perty definition in CSS2.
          *   /
    public  String getBorderTopC         o      lor();
    /**
     *  See   th       e     b                         ord              er- top-color property d              efinition in    CSS2.
     * @exc eption DOMExceptio          n
     *   SYNTAX_ERR: Ra          ised if the new value has a          s     y     ntax    error and  is
     *      unparsa    ble.
       *   <br    >  NO_M     OD    IF     I        CAT  IO     N_ALLOWED_ERR: Raise    d    if this proper   ty i  s   reado     nly.
             *    /
    public void setBorderTopCo  lor  (String borderTo     p        Color    )
                                                       throws             DOMExcepti             on;
  
    /**
     *           See th                  e     border-rig ht-c    olor p  ro   perty defini  tion i     n   CSS2.
           */
     public S   tr    ing getBorderRig  ht   Co  lor  ();
    /**
     *      See the border-right-color     p   ro pert        y d  efinition     in CSS2.
             * @exception        DOMException
             *   SYNTAX_            ERR: Raise   d if t      he       new   value has a s    yntax  error and is
        *   unparsa    ble.
     *           <b  r>NO_M  ODIFICATIO N_ALL  OWED_ERR: Rai  sed        if this p  r       operty is reado   nl    y.
       *          /
    public void setB                orderRightColor(String    borderRigh  tC                    olor)
                                                                             throws DOME   xc     ep       tio          n;

    /*    *
     *    See      t he          border                   -bott    om-color   pr   operty def       i   niti    on  i     n CSS2.
     */
              public Strin      g getBor  derBo              ttomColo    r();
      /*   *
     *        S  ee  the border-  bot       t   o        m-c   ol     or property definition i               n CSS2.
     *   @exceptio   n DOM  Excep  tion
     *                SYNTAX_ERR: Ra  ised i    f       the new value ha   s a        syntax er    ror and   i  s
     *   unparsable.
            *   <br>   NO_MODIFI     CATI    ON_   ALLOWED_ERR: Ra    is     ed    if thi       s pr operty is readonly.
         */
      public                  v         o id setB     order     BottomColor(  String     borderBottomColor)
                                                               th ro    ws DOME   xception;

           /**
                         *          See      the bo  r   der-left-color pr     ope       rty defi  n     it       ion in CSS2  .
         */
          pu         bl i    c String getB        or derLeftColor();
    /*    *
     *             See the  border-  l  eft  -color    p   roperty de   fin           ition in CSS2.
         *    @exc       eption DOMExce ption
     *       SYNTAX_ERR:     Raised i     f t   he n    ew va lu      e has a syntax er      r  o        r and is
               *   un  p  ar         s    able.
                   *   <br    >N O_MODIFICATION_ALLO     WED_ERR: Raised if t  h  is proper       ty  is re     ad     onl y.
     */
    pub     lic void     set   Bord   erLeftCol  or(String bo  r   derLeftColor)   
                                                               throws DOMException;

    /**
        *  S   ee the border-top-styl    e property def  inition in CSS2.
     */
       public   String getBor    derTopStyl           e();
    /**
      *  S   ee   t  he   borde r-top-style prope  rty def    ini    tio  n in CSS2  .
     * @exceptio           n DOME        xception    
                    *        SYNTAX_ERR:     R a ised if         the ne  w value     ha  s  a sy         ntax error         and is 
     *   unp ar   s     abl    e.
            *   <br>N    O_MODIFI  CAT  ION_A      LLOW    ED_ERR: Raised if this pro            perty is re          ad  only.
         */
    public vo    id    setB   orderTopS tyle  (String         bo rderTopStyle)
                                                                                 throws DOMExc      eption;
      
       /**
     *     See the  b   orde    r-right-style prop  erty definitio     n in CS S2.
        */   
    publ          ic S  tring getBo     rderR ightS     tyl   e();
    /**
     *  S  ee the   bord    er- right-     style p     roperty definition i n CSS2.
     * @e xceptio   n D  OME       xcepti    o n
     *       SYN  TAX   _ERR: R      aised if the new     value        h as a syntax e  rror    and is
     *      unparsab   le.
      *   <br             >NO_    MODI FICATION_ALLOWED_ERR: Raised if th   is  property is readonly.
        */
         pu   blic void     setBo  r derRight  Sty    le(St      ring borderRightS  ty   le)       
                                                               throws DOMException;

                   /*     *       
     *  See t           h   e border-b        ott      om-style property defi    nition in CSS2.
     */
      pub     lic String getBorderBott omStyl    e();
    /**
       *  S   ee t     he  border-bo      ttom-style pr  opert            y   de      finit       ion  in CSS2  .
           * @exception DOM Exception
      *           SYNT  A X_ERR:    Ra  ise  d if           t         h       e new v  al       ue has a     syntax error   and is
        *   unparsable.
       *   <br>NO_MOD IFICATI  ON_A    L  LOWED_ERR: Ra             ised i    f this  proper  t   y is read           only.
     */
    publi  c       vo id setBorderB  otto    mStyle(String borderBottom   Style)
                                                                thr ows DOMExcept  ion;

                         /**
     *        See t   he borde        r      -left -style property d  efiniti   on in CSS2.   
          */
    public       Stri      ng getB         orderLeftSty  le()     ;    
    /**
     *  Se  e  the     bor   der-left-s     tyle p    roperty definit ion               i  n CSS     2   .   
     * @exception  DO MEx       cept      ion
         *   SY    NTAX_ERR: Raised if the new          value has a sy        ntax err            or and is
        *   unparsable.
     *          <br>NO_MODI      FICA      TIO     N_ALL OWED_  ERR: Raised    i f      this   p   roperty is          readonly.  
     */
     public void  setB    or      derLeftS  t  yle(S    tring      borderLeftSty    le)
                                                                         t    hrows DOM  Exception;

                 /*  *
              *  Se      e the      border-top-width property   definition i   n    CSS  2.  
                */
    publi c String getBorderTo    pWidth( );
      /**
     *  See    the border-top-    width property             def inition i  n CSS2.
        *      @e    xcept  ion DOM  Exception
           *    SYNTAX_ERR:     Rais ed if the ne  w value h   a   s a syntax       error and is
              *     unpars   ab     le.
     *   <br>NO_MODI          FI           CA  TION_ALL   OWED_ERR: Raised     if this property i s readonly.
     */
     p     ub  li  c void setBo   rderTopWidt  h(String borderTo pWi      dth    )
                                                                    t    h                         ro       ws DOMException;

    /*   *
     *  See     the bo    r     der-right      -widt    h pro     perty d  efini  tion in     CSS2.
        */
    publ    ic String getBorderRightWi       dth();
    /**
         *  See the border-   right-wid  th    prope   rty           defi  nit      ion in C  SS2.
           * @excepti       on DOMExc ep       ti    on
     *    SYNTAX_ERR: R     aised if the new va  lue ha     s a syntax er  r   or   an       d    is    
      *   u     nparsable.  
     *       < b           r>NO_   MO    DI  FICATION _ALLOWED_        ERR: Ra     ised i    f this             proper     ty is readonly.
       */
    pu   bli      c void setBorderRightWidth  (String    border   RightWidth)
                                                                   throws DO   MException;

         /**
     *  See t   he bor          der-      bottom-w idth    property defin       it    ion in CSS2.   
     */
     public       String getBorderBo   t   tomWidth();       
     /**    
     *  See    t  he border-bottom-wid  th   p     rope       r    t      y definit   ion in       CSS2.
         * @exc     eption D  OMException
          *    SY   N      TAX_ER    R: Raised   i   f the     new val  ue   has                 a syntax erro  r        and is
               *   u   np     arsable.
       *   <br   >   NO_      M       O    DI      FI       CATION_AL      LOWED  _ERR: R  ai   sed    if th   i  s property i    s read     only    .
       */
        p   ub   li        c  void setBo     rde        rBottomWidth(String   b       orde rBottomWidth)
                                                                          th      r       ow  s DO   ME     xcept   ion;

    /**
            *    See     th e    bord er     -left-width         prope       rty defini  tion    in CSS     2      .
     */
    p             ublic   String getB  orderL    eftWi  d   th();
    /                          **
       *  See the bor   de     r-left   - width p             roperty defin    itio    n in CS    S2.
     * @ex     c  ept    ion DOMExc      ep   tion
     *   SYN     TA   X   _ERR : Raised if       the new va        l  ue          ha   s a              syn   tax    error an  d is
     *   u  nparsab le        .
              *      <br>NO_MODIFICAT    ION_ALLOW    ED_ERR: Raised if this prop     er ty i        s readonl     y.
     */              
    public   void set  Borde       rLe  ftWidth(St  ring b  or       derLeftWidth  )
                                                              throws DOMExcepti   on;     

         /**
     *  S           ee t        he bord   er-width p  ropert     y de     fini   tion i  n CSS  2.
                            */
        public String getBorderWidt h();
           /** 
     *  S    ee the    border-w     idth pro perty de       finition in CSS2.
     *  @e    xception    DO     MException
           *          SY     NT  AX   _ERR: Raise d if the new   value has a syntax er ror an    d is
     *   u  nparsable.      
     *   <br>NO_MOD     IF   ICATION_ALLOWED_ERR: Rais   ed i   f thi s property                  is readonly.
                */
    p     ublic void setBorderWidt      h(String bord           erWidth)
                                                     throws   DOMExcept     ion;

    /**
     *            See the bo ttom    property d       efin   ition in CSS2.
        */
    public Strin      g     ge    tBo   ttom()    ;
    /**
     *  S        ee the bottom propert y definiti        on    in      CSS2.
           *    @      ex    c eption DOMExcept         ion
     *      SYN  TAX_   ERR:      Raised if th  e n  ew value h         as a synt  ax error a  nd is
        *     unparsable.
     *      <br>NO_MODIFICATION_     ALLOWED_ER          R    :     Raised if th         is pro     perty is reado  nly.
      */
    public   void s           etBottom(String bot      tom)
                                                     throws DOMException     ;

        /            **
     *  See the ca    ption-side p  roperty    d efin ition in CSS2.  
     */
      pub    l  i  c   String getCaptio     n              Side(   );
          /**         
        *  See the caption  -side     proper   ty defin   it     ion in CSS      2.
         * @    ex    ception       DOMEx  c eption
     *   SYNTAX       _ERR: Raised if the n  ew val   ue  has a s   yntax er        ror and  i  s
                            *   unpars    able.
              *   <b  r>NO_MODIFICATION_ALLOWED_ERR:     Raised    if t   his property is re  adonly.
     */
    p         ublic voi      d set  Cap      tionS           ide    (String captionSid           e)
                                                                   thro              ws DO M    Exc         e      ption  ;

    /**
                    *        See th   e clear pro    pert  y def         inition        in CSS2.
     */  
    public String get  Clear();
              /**
     *  See the c   lear pr oper ty     definition in       CSS2.
         * @exception DOMExcep    tion   
     *   S  YNTAX_ERR:     Rai    sed if      t   he                  new va l              u      e has a sy           ntax e             rror a    n   d           is
              *       unparsable.
     *   <br>NO_    MO   DI          FICAT   ION_AL  LOWED      _ERR: Raised   if thi  s property is      readonly.
     */
                public vo   id        s  etC lea        r(S     tr   ing clea    r  )  
                                                            th   rows DOMExc          epti         on;

    /**
                   *  See         th           e   cli  p property d  ef   init       ion            i   n          CSS  2.
         *  /
    p     ublic Stri  ng get   Clip();
       /**
      *  Se  e the clip pr    operty defi       nitio n  in   CS         S2.
          * @e  xception DO      MExce    ption
               *        SYNTAX_                 E              RR:   Raised if the new     value has a     s yn     tax       error and i          s
       *   unpa      rsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Rais      ed i               f th   is property is readonly.
           */
        public void setClip(S    t  ring clip)
                                                                  th   rows DOMExcep tion;

            /**  
              *     See the color propert   y defin          ition in CS  S      2.
     */
     pub  lic String getColor();
    /    *       *
                         *  Se        e     the   color      p   roperty defini   ti   on  in   CSS2.
           * @exception DOMExce     pti    on
              *      SYNTAX_ER   R: Ra  i            sed     i                    f the new value has   a     syntax error       a  nd is
        *   unparsable.
       *   <br>N O_    MODI   FICATION   _A     LLOW              ED_ERR:   R   aised if this prope rty is readonly  .
               */
        p ublic  void setColor(Strin    g co lor)    
                                                             throws  DOMExceptio     n;

           /      **
     *  See th    e c       on   tent pro   pe       rty def  inition   in  CS    S2.
     */
         public String g    etCont           ent();
    /**
        *       See the               content property defini    tion in C   SS2.
     * @except     ion     D   OMEx          ception
        *   SY N    TAX_ERR: Rais                ed     if the new va lue  has a    syntax e    rro    r    and    is
     *                u    nparsabl e.
       *       <br>NO_MODIFI  CATION  _ALLO   WED  _ERR:     Rai   se   d     if this proper       ty is readon      ly    .
           */
    pub   l ic vo   id setCo ntent(String c    ontent)
                                                           t hro    ws      DOMException;

    /  **
           *  See the   counter-increment property de  f      ini    tion in CS  S2.
         */  
      pu  bl  ic St  r       ing getCounterIncre    ment(); 
    /    **
      *  See    the      c     o       unter-incre      ment p   roperty defini   tion in CSS2.
       * @ex      cepti       on DOMExce   ption
                *    SYNTAX_ERR: Raised if the new value has   a synta x e r   ror an     d is
          *     unparsa  bl    e.
     *   <  br>       NO_MODIFICATION_ALLOWED_ERR:    Raised i   f    this   property is   readon    l      y.
     */
    public     void    set    C         o unterIncrement(S     tr             ing count      er   I  ncrement)
                                                                                          thr  ows D    O MEx      c         eption;

    /      **
                  *  See  the counter-res  et property definiti on     in CSS2   .
           *        /
    pu        bl ic       String get        Count                        erReset();
       /**
     *  Se        e    th e co      unter-reset   p    roperty defi   nitio      n in C     SS2     .
     * @          exception     D   OMEx  ce  ptio    n
                   *   SY    NTAX_ERR:       Raised   if the new value has a sy   ntax e     rror and is
             *   unparsable     .   
          *       <br>N   O_MODIFICATION_ALLOWED_ERR: Raised if th    is p      rope      rty is readonly.
           */
    publ   ic void         set    CounterRes   et(Str     in    g co       unterRese   t)
                                                                         throws   DOMExcepti   on;
  
        /*     *
     *     See the cue propert  y def           inition        in CSS2.
       */
    publi  c St      ring ge       tCue();
    /**
      *  See the       cue     prop   erty defi     niti        on in         CSS2.
         * @ex   ception  DOMExce   p     tio      n
         *   SY  NTAX_     ERR: Rai     s    ed if t          he new    val   ue     ha    s    a s     ynta x error    and is
               *   u     np   a       rsable.
     *   <br>NO_MO     DIFICATION_ALLO        WED_     ERR: Raised if this p   roperty is readonly. 
     */
      pu   blic      v       oid       setCue     (String       cue) 
                                                                              throws DOMException;

         /**  
     *  See the        cue-after p  roperty definition i  n CSS2 .
     *         /
                   public   S   tri    ng    get      C  ueAfte            r();
     /**
     *  See the cu    e-af  t   er pr     o   pert       y de   finition in CSS     2  .
           *       @exception DOMException
     *     SYNTAX_ERR: Rai    sed if t   he new val       ue has a   syntax    error and    i   s
        *      unparsable.
     *   <br>NO_MODIFICATION_ALLOWED          _ERR: Ra      ised i  f      this property is readonly.
     */
    public void setCueAfter(String cue   Afte        r)
                                                                         throws    DOMExcept    ion;

    /       **
     *  Se        e the cue-bef  ore pro           perty  d  e     fin     iti            on in CSS2.
     */     
       public St  r     ing getCueBefore  ();
        /**
        *  See th   e        cue-before  prop   erty  definition in      CSS2  .
                *  @excepti    on DOMException
          *   SYN   TAX  _  ERR: R  ais              ed if the        ne  w va  lue has    a syntax error and is
          *   unparsable.
       *      <br>NO _MODIFICAT      ION_ALLOWED         _ER    R:          Rai    se        d if t       his   property is readonly.
                   */
           publ   ic void  setCueBef      ore(String cueBe fo  re)  
                                                                           thro    ws DOMExcep   tion     ;

      / **  
            *  See the cu rsor p             roper     t   y defin    ition i n CSS2          .
     *     /
       pu       b    lic St  rin g      getCur  sor();
    /    **
     *     See th    e cursor propert       y d  efinition i   n CS   S2.
          * @  ex     ception  D     OMExcept   ion
        *   SYNTAX_ERR: Ra  ised if      the       new  value h   as a syntax error and is
          *     unp  arsable.
     *    <br>NO  _M    ODIFICATI  ON_ALLOWED_ERR : Rai se    d if t   his      prop   er  ty is r  e  adon    ly       .
        */  
    public void setCursor   (String  cursor)        
                                                                 thr     ows  DOME xc   eption     ;

       /**
      *      See t    he direction pro   per  ty definition in C  S   S2  .
           */
    public Stri        ng getDirection   ();
    /**  
     *  Se e the d  irection property de            finit      ion i  n     CSS2.  
     *  @exc       epti      on DOME xcept ion  
         *               S     YNTAX      _ER    R: R  aised if the   new value has a s  y     ntax e rror    an      d     is 
            *   u   nparsa      bl            e     .
     *   <br> NO_MODIFICATION            _AL       L         OW      E     D_ER        R: R  aised if this     pro     perty i         s readonly.
         */
    pub     li   c vo  i  d s        etD  irectio            n(String directi  on)               
                                                                               throws DOMException;
 
         /**
       *  See the dis    play property definit     ion in CSS2.
     */    
    p  ub   li    c        String  getD isplay();    
    /**
        *  Se     e     t     he dis  play p         roperty  def    inition in CSS2.
            *    @e   xcepti    on DOMException
        *       SYN TAX_ERR: Raised if the ne   w val   ue  h      as           a   syntax     e     rr or and is
         *   unparsable.
     *    <br  >N       O_MODIFICAT   ION_   ALLOWED_ERR : Raised i    f    t    his pr   operty is  read     only .
     */
    public vo    id  s   etDisplay(St  ring display)
                                                                                throws      DOMException  ;

      /   **
     *  Se  e t   h      e    elevation p                     roperty definiti     on       in CSS2.
     *      /
    pu  blic  String  getElevation   ();   
       /**
     *  S  ee the elevati on p      roperty de  finition  in CS   S2.
                        *    @ex       ception DOMException
         *         SYNTA  X_ERR: R               aised if the n  ew value ha  s a    syntax error an   d             is
     *   un      par     sable.
     *   <br>NO_  MODIFICATION_ALLOWED_ER     R   : Ra  ise d        if this property is   readonly.
             */
         public v o    id s   etElevati o  n(     Stri    ng elevat    i    on)
                                                                                               t  h            row  s DOMExceptio     n;

      /**  
               *    See       the empty-cel          l        s p    roper   ty definiti                     o      n in CSS2.  
               */     
    public Str  i   ng getEmptyCel    ls     ()  ;
         /**
     *  Se      e the empty-cells      propert    y de finit    ion in CSS2.
     * @exce    pt  io   n          DOMException  
     *   SYNT   AX_ERR    :   Raised      if t  he new value has          a syntax error   and is
     *       unparsable.
     *      <br>NO       _MODIFICATION_A  LLOWED_ERR: Raised     if      th    is property is rea         do        nly      .
         */
    public void setEmptyC   ells(String emp     tyC     el     ls)
                                                                        throws D    OMException;

          /**     
       *  See the       floa   t  pr    operty definition i  n  CS     S2      .
     */
    pu   blic St ring getCssFloat  ()   ;
    /**
     *          See      the f       loat p     roperty def    inition in CSS2.  
                   * @exce      ption DOM         Exception
     *     SY    N    TAX_ERR: Raised          if the new value h      a s a syntax error   and is     
               *    unpars    ab le.
     *      <br>NO_  MODIFIC   ATION_ALLOWE           D_ERR:  Ra    ise           d if thi     s         property is    r ea    donly.
     */
                 public void s et     CssFloat(String c   ssFloa   t)
                                                                                                  thro   ws DOMExce            ption;  

    /**
        *  See the font             propert       y defin     iti   on i   n    CSS2.
     */
    p  ublic S    trin g getFont();
    /**
     *  See the f  ont             property defin     itio        n in CSS2.
        * @exception   DO   MExce      pti   on
     *           S Y NTA     X_    ERR: Raise       d if th          e new value has a syntax e   rror and   is
      *      u       nparsable.
     *         <br>   NO_M ODI  F          IC ATION_ALLOWE  D_ERR    : Rai    se d if   this      pr   opert   y is readon     ly.
     */
    public void set    Font(Str   ing f    ont)
                                                                              thro   ws DOMException    ;

    /     **
                  *   See the font-fam          ily      p  r      oper   ty d     efinition   in            CSS2    .  
             */
       publ   ic  Str               ing              getFontF            amily();
    /**       
     *        See the  fo              nt-f     am ily pr   operty defi      nition in CSS2.
     * @e  xcepti   on DOMEx        ception
       *       SY            NTAX_ERR: Raised if the new valu  e h  as a  syn     tax error and is
        *   unparsable.
         *           <br>NO_MOD            IFICAT  ION_ALL  OWED_ERR: Raised if        this pr  o   perty is  reado      nly.
           */      
    pu   bl                          ic void setFontFamily(S   t ring  font            Fam              ily)
                                                                                       throws DOMException;

    /**
        *  See the     font-size      pro pe  rty definition    in CSS2.
           *  /
    p  ublic       St           ring ge tFont       Si     ze();
    /                      **
     *  See the font-size p  roperty definitio      n      in CSS2.
     * @            except   ion   DOM       Exception
     *        SY    NTAX_ERR: Raised if            the new    val    ue has a s y               ntax error    and i       s
     *                     un   parsab    l  e.
      *   <  br>NO_MODIFICATION_A      LL  O     WED_ER R: Raised if     this property is readonly.
     */
     public void setFo         ntSize(String fontSize)    
                                                        throws DOMException;

        /*            *
     *  S ee the fo       nt-size-a     djust prop erty    def         i   nition in    CSS2.
          */
    p ublic String getFontS  izeAdj    ust(  );
                /** 
           *  S    ee   the font-size-adjust prop erty  defini   tion i   n CSS    2.
      * @e  xcep   tion DOMExc   eption  
     *     SYN    TAX_ERR  :   Raise d if t he  new val     ue has        a syntax error a      nd is
       *   u  nparsable.
     *                   <b r>NO_MODIFICA     TION_AL    LOWED_ER  R: Raised if this pr      operty is   readonly.
       */
         public voi   d setFont Size Adju   st(String       fontSizeAdjust)
                                                                                   throws    DOMExce  ption;

        /*   *
     *  See the f    ont-stre tch p  r   operty definition in   CS   S2.
      */
    pub  l  ic     St      rin  g    getFo       ntStretch(       )  ;
     /**
     *  See the font     -st  retch proper   ty d     efin    iti   on in CSS2.
          * @exce    ption D  OMExcep  ti     on 
       *           SYN   TAX_ERR:      Raised    if the new  value    has a syntax err   o   r and is
       *    unp   arsable.
              *   <br>N  O_MO DIFICATION_A    LLOWED_ERR  : Raised if thi s     p   rope         r    ty is re  adonly.
     *   /
      p     ubl ic void set    F ontStretch( String       fo    ntStretch)         
                                                                             throws DOME  xception;     

    /**
        *  See the     font-style property de  fin  i  t      ion in CSS2.
        *   /
       pu     blic Stri   ng      getFontSty le();
    /**
         *  S   ee t          he font-style   property definition in CS  S2  .
      * @exception DOMEx   cepti     on
     *             SYNTAX_   E           RR: Raise          d if the new     value  h   a  s a syn ta      x          er              ro  r           and is
      *    unparsable.
     *   <br>NO_   MOD    I    FIC  ATION_AL LOW   ED_ERR: Raised if    this propert  y    is readonly.
     */
    public  void     se    tFontStyle(Strin       g fontSty   le)
                                                                                      throws DOMEx      cep  tion;

           /**
          *  S        ee the     font-variant      property  definition i    n CSS2.
            */
    p  u blic    String getFon   tVar  i   ant      ();
    /**
     *  See the    font-varian    t  property definition in CSS2.
     * @exce      ptio                n DOMException
            *      SYNTAX_ERR: Raised i         f the new va         lue has    a      syntax     error   and   is  
     *   unparsable.
        *       <br    >NO_MODIFICATIO        N  _ALLOWE   D_ER             R:        R                 aised if this pr operty i        s re adonly.
                                 */
           public void se   tFontVaria          nt  (St    ring f        o  ntVaria          nt)
                                                                        throws DOME   xcept              ion;

     /**
     *    See the font   -weight property definition in  CSS2 .
     *  /
              p       ubl     ic St  rin g getFontWeight      ();
    /**
      *  Se  e the fo   n  t-weight p  roperty definit  ion in CSS2.
        *   @exception D    O  MException
      *    SYN     T     A  X       _E  RR: Raised          if the new value   h    as a syntax error     a     nd is
     *       unpa   rsabl     e.  
        *   <br>NO_MODIFICATIO N_ALLO WED_       ERR: R aised if th  is property is readonly.  
         */   
    publ  i   c void    se      tFontWeight(String f    ontW     eight)
                                                                              thro    ws D   OME xc  eption   ;
  
      /**  
     *      See the    hei    ght property defin           ition in    CSS    2.
     *  /
        public String    getH    eight();
             /**
       *    See t    he h       ei      gh  t       p  roper  t                y definition i  n CSS2.
     * @excepti         on DO  MExce       pti    on
     *   SYNTAX_      ERR: Raise   d if the new val  u   e has a syntax  error an  d is
          *     unpar   sable  .
      *   <      br>NO_MODIFIC   ATION_ALLOWED_E    RR: Raise       d if this prop     erty is read only  .
     */
     publ     ic void s      et     Height(String he   ight)
                                                                   throws DOMException;

    /**  
           *  See the l  eft pr operty d  efin           ition in CSS  2 .
                           */  
          pub lic S t   ring getLeft();
    /**
     *  See the left pr operty d efinition      in   C  SS      2.
     * @exception DOMException   
     *   SYNTAX_ERR: Raise  d   if the n   ew  value has a s  yntax error and is   
        *       u   np     arsable.
            *   <   br>NO_MO  DIF     ICATI  O  N    _ALLOWED_ER  R:    Raised if this proper     ty i s readonly.
     */
    public void    se t      Left(String left      )
                                                                                          throws DOMException;
      
    /**   
     *  See the le  t  ter-spacing property     de       finit  ion in   C SS2.
     */ 
        pu   blic Strin             g    ge    tLe  tt         er   Spacing();
         /        **
     *  See   the   l  e  tter-spac          ing pro       perty d   efi  nition in CS    S2.
     * @exception   DOMException
      *       SYN    TAX_ERR    : Rai  sed if the ne  w      va      lue   has    a syn   tax error    and    is
          *   unparsab   le.   
            *   <b r>NO_M   ODIFICATION_ALLOWED  _  ERR  : Raised if thi s      property                   is     readonly  .
     */
    pub       lic void setLett           er     Spacing    (String letterSpa   ci   ng      )     
                                                                                                    thro    ws DO MEx         ce  p   t    i on  ;

             /**
        *  See     the     line-hei   gh t       prop   er    ty de              finiti        on in   C   SS2.
     */
    pub    lic Strin   g get         LineHeight();
       /**
         *  S ee the line- heig  ht prop   erty d   efiniti    on      i      n     CSS2     .
     * @e      xcept ion D OMExcep  tion
         *      SYNTAX_ERR: R    aised      if the      n  ew value has a syn    tax er     ror and is
       *            unparsable .
        *           <br>NO_  MODIFIC        ATION_AL    L   OWED_ERR: Raise  d           i   f thi         s pro  pe rty  is re adonl y.
                       */
           p ub    l   ic voi    d set  LineHeigh   t(   Stri   ng lin  eHei        ght)
                                                                                          throw  s D  OMExcep         tion;

        /    *        *
     *  See the l      ist-             style property definition        in CS      S2.
     */
       public S   tri   ng getListSt   yle();
                  /*  *
     *     S   ee the list-style property    defi nition in CSS2.
          * @exc   ep  tio  n DOMExcep    ti           on
                       *   SYNTAX_  ERR: R     ai   se    d           if the n  ew          value   has      a sy     n   tax error and              is   
       *      un   parsabl e.
           *   <b  r>N   O_MO    DIF       ICATI   ON_ALL    OWED_ERR: Raised     if this prop erty is readon  ly    .
           */
             public      vo   id  s  etListStyle(         Stri   n g listS    tyle)    
                                                                                   th    r         ows DOM Excep  tion  ;

        /            **
     *  See the li      st-styl e -image       propert  y definition in CS     S 2.
         *  /
        pub    l                 i    c       Stri  ng getListStyleImage();            
     /**
     *  See     the l                         i    st-style-  image property   defi    nit   ion in CSS2.
             * @exception DOMExcepti   on
          *         SYNT    AX_ERR  : Raise       d if the     n      ew value     has a s   yntax error        a  nd is
            *      u nparsable.
                     *         <br>  NO      _MODIFICATION_ALLOWED  _ERR: Raised if this prop   erty is readonly.
          *     /
       p  u    b    lic void setListStyle Image(String listS   t  yleImage)
                                                         throw       s DOMException;
     
    /**
        *  See the      list-style-p  osition prop    erty           definition in C             SS2.
     */  
    public String g  etListS    tylePo si     tion();
     /**
     *         See th      e l   i st-styl e-po sit ion   property definition in CSS2.
     *         @exception       DOMException
     *   SYNT   AX_ER      R: Raised if the         new   value has a sy        n    tax error  and   is
     *   unparsable.
     *       <br >NO_MODIFICAT I          O  N_ALLOWED_  ERR: Rais   ed if this property is  readonl   y.     
     */
           p    ub       lic void              set     L  istStylePosition(Str      ing list  StylePosition)  
                                                              throws DOME  xce           ptio  n;

      /**  
     *       See the li   st-style-ty    pe property       definition in CSS2.
      */
    pu         b  li   c Str   ing getList   Styl  eType()    ;
      /*    *
      *          See the list-st  yle-type property definition in C SS2.        
        * @exc    e   p   ti    on    DO        M    Exception
           *            SYNTA   X_ERR: Raised i      f the new value has a synta      x error and is
           *   unparsa          ble      .  
     *   <br>N       O_MO  DIFICAT   ION_A  LLOWED_ERR:          Rais  ed   if this property is readonly.
     */
       pu   blic void s  etL   istStyleType(St ring listStyleTy    pe)
                                                                      throws DO        MExce  ption;     

    /**         
                  *  See th   e     margin p   ropert   y de    finiti     on   in CSS2.
     *    /
         public String getMargin();
          /*      *
     *  See the marg  in prope   r ty    definition in CSS2  .
          * @exc eptio   n DOMExcept   ion
          *          SY  NTAX_ERR:       R   a       ised if the     new v alu    e has a sy        n     tax    error  and  is 
                             *   un               par   sable.
     *                      <br>  NO _MODI  FICAT   ION_ALLOWED_ERR: Rais      ed   if t    his property is reado       nly.
      */
    p      ublic     void setM  ar gin            (   Strin g marg   in)
                                                                   throws   D      OMException ;
      
          /**
          *  See th e     margin      -top property d   e     finition in CSS2.
     */
        publ   ic String ge   tM     arginTop(  );
    /  **
                    *  See   t   he    margin-top pro   pert   y     defi n   ition in CS     S2.
     * @excepti on DOMException
     *   SYNTAX_E   RR: Raise    d if th    e     new  val u             e     has a         sy     ntax err   or a     nd is
              *   u      np   arsa         bl     e.
         *   <br>NO _MODIFICATION_ALLOWE      D_E R            R: Raised      if this    pro           p        erty is reado nl       y.
              */
                   p  ubli c void              setMarginT op(S        tring margi    nTop)       
                                                                                          throws DOMException;

         /**
     *   See the margin-r       i   g   ht property  d  efini            tion in CSS2.
      */
          public       Strin       g g     e    tMar      ginRight(   );
       /**
         *   Se      e t           he margi  n-right   property def      initi            on in CSS2.
           * @except io   n   D  OMException
     *   SYNT      AX_E        RR: Rais    e d if               the n  ew          val  u  e has a syntax error     a nd is
     *   unp  arsable.
          *         <br   >N                          O   _MODIFICA TION_ALLOWED_ER  R   :    Rai sed if t his prop e       rty is readonly.
             */  
        public void       setMarginRight(St      ring marg     inR   i   g          ht)
                                                                    throws DO MExcepti    on;

       /**
     *   S  ee the margin-bottom       pr    operty       defi   n        itio  n in   CSS2.
     */ 
    public String  g               et    Margi          nBottom()    ;
    /   **
              *  See     the m    a  rgi n-bottom pro        p   e    rty  def   inition in CS  S2.
         * @excep    ti   o n DOMException  
      *   SYNTAX_ERR: Raised if t  he new value h  as a  synta    x     error an     d is
               *     unp   ar            sable.
                *         <br>N O_MODIF    ICATION _ALLOWED_E R     R: Ra          ised    i  f       thi   s property is reado nly.
     */
     p      ublic void     setMarginBottom(String marginBotto     m   )
                                                                                     throws    DOMExc  epti  on;

      /**
     *         See     the marg in-lef    t pro  perty def ini  tion i  n CSS2.
     *   /
    pu blic String getMargin     L   eft();
      /**
     *     Se   e the marg  in-      left         pr     ope  rty defi    nition     in CSS2.
     *  @      exception DOMException
     *   SYNTAX_ERR: R  aised if t      he new val  ue has a syntax error and is
       *   unpa rsable.
     *   <br>NO_MODIFICAT   ION_ALLOWED_ERR:          Rais     ed if this property is           read     only.
                 */
    public void setM                      argi    nLeft(S     tring m argi       nLeft)
                                                                        throws      D          OME   xc e pti   on;

    /**
                 *  See the marker-offset property  definition i  n CS      S2.
       */
                       pu  b      lic String getMarke  rOf fset();  
           /  **  
     *  See the marker-offset   property definition i  n CSS2.
      * @exception DOMEx  cep  tion
     *     SY           NT       AX_ERR:     Rai    se         d if the    new value    has           a sy   ntax    error and is
             *           unpar   sable   .
             *   <br    >    N    O_MOD    IF ICA     TION_AL  LOWED_    ERR: Ra ised if this     property is readonly      .
               */
          publi     c void setMarkerOffset(Strin   g markerOffset)
                                                                 thro    ws DOMExce        ption;

    /**
         *  See the marks     property definition in CS     S2.
         */
    pub  lic       St   rin      g ge   tMar    ks()  ;
    /    **
        *  See      the m   arks property defin       ition in   CSS2.
               *  @e  xc    eption DOMException    
      *          SYNTAX_ERR: Raised i    f the new value has a  syntax erro r a    n  d i     s  
     *   unpa        rs       able.
      *   <br>NO _  MODIFICATION_ALLOW  ED_ERR: Raised if thi  s pro   perty         is readonl         y.
            */   
            pub   l   ic void    setMa       rk           s(String marks)
                                                      t hrows DO    MException;

        /**   
     *  See the m       ax-  hei    ght  property definition           i  n CS    S   2.
              *  /
          public St    ri ng getMaxHeigh         t();
    /*   *
     *  See the max-    height pr    ope rty d efinitio    n in CSS2.
           * @exception DOM  Exc        e   ptio    n
           *   SYN  TA  X_  ER R     : Raised if t  he new valu e has a sy   n  tax  err    o   r and         is
         *   unpar s            a       ble               .
     *   <br     >NO_MODIFICATION_A   LLOWED_      E          RR : R     aised i    f thi    s          property is readonly.
       */
    public vo  id s  etM axHeight(S          tring ma    xHeight)
                                                                           thr  o            w     s DOME  xception  ;
    
          /**
         *    S     e      e        the max      -w   i dth property definition i   n C S        S2.
           */
        public           String ge    tMaxWidth();
    /**
        *  Se   e the max-width property  defi     niti  on in CSS2.
     *     @exc     eption D OM   Ex   ceptio  n
     *   SYNT       A  X_ERR:        R    aised i   f   the new         va   lu     e has      a   syntax error and      is
                    *   u n       parsable.    
     *            <      b r  >NO   _MODIF   ICATIO     N    _AL    LOWED_ER        R:    Raised i  f    th     is   pr    operty   is       r   eadonly.
                   *    /
    public voi d s     etMaxWidt h(St   rin  g maxWidth )
                                                                         throws DOME   xcepti  on;

    /    **     
         *  See th                e min-h e  ig        ht proper      ty      definition in         CSS2.
     */    
     publ     ic S   t ring getMinHeigh  t();
                    /   **   
         *  Se       e the      min-        heigh       t proper    ty defin  ition        i  n CSS2.   
     *    @e  xcep    t    ion      DOM Except     ion
     *      SYNTAX_ERR: Raised if the new     value has a syntax err    or    and is       
            *         un   pars able .
            *   <     br>NO_MODIFICATION_ALLOWED_E    RR:        Raise    d if t  his pro  perty is readonl    y.
     */
           public void setMinHeight(String                       minH    eight)  
                                                                               thr   ows          DOMExc  ep           tio   n;

          /**
       *  See the min  -width p ropert  y d        efinit  ion in CS   S2.
          */
    p   ublic String   getM i  n   Widt   h();
      /**
     *  See  the min-width property definiti  on in CSS2.           
        * @ex  ception DOMException
     *     SYNTAX_ERR: Rai       se         d    if th    e    new v alu  e ha    s a       syntax   erro   r and is
        *   unpa   rsable.
            *           <br>NO       _MODIFIC        ATION_ALL   OWED_ERR: Rais ed   if th   is  p        r   ope  rty   is re   ado   nly          .
     */
             p         ublic v   oid    set Min  Width(String        minWid th)
                                                                   throws DOMExcep      tion;
                
     /**         
       *  See the        orphans p  roperty definit ion in    C     SS2.
          */
    public String   getOr  phans ();
    /**
           *  See        the orph    ans prop    er    ty defi     nition in CSS2.
              * @ex          ception DOMExc     eption
     *      SYNTAX_ER   R:       Raised if    t              he            n      ew value has a syntax error a   nd is
     *   unparsable.
         *   <br>NO_MODIFI  CATIO     N_ALLOWED_E   RR: R   ais ed        if this prop   e     r  ty is r      ea  donly.
       */    
        public voi  d setOrp  hans(Strin g orphans)
                                                                            t        hrows D                     OMExce       ption;

    /**
              *           See the            outline    property defi   nitio   n      i    n CSS2    .
            *   /   
    p            ublic Stri  ng      getOutline();
        /**
             *  See the outline property def      inition in CS        S2.
                   *      @ex   cepti  on    DOMException     
        *   SYNTAX_E    RR: Ra   i  sed if th e ne   w va      lue ha       s a s         ynt    ax                error      and is 
            *      unparsable.           
        *   <br>NO_MODIFICA    TION   _    ALL OWED_ERR: Raised if this propert  y is readonly.
        */
         public   v  oid setOutline(String outline)
                                                                           thr       ows    DOMExce           pti on;

      /**
             *  See th     e out  line      -color property   definition  in   CSS2.
             */
    public     Stri  ng     getO  utl             ineColor();
    /**
             *  See     the o   u  tline-  c olor proper   ty   definition in CSS 2.
     * @ex  cep  tion DOMException
      *                SYNTAX_ERR   : R  aised if th         e  new     value         h     as a s   yntax e   rror and   is
     *   unpars   a              ble.
     *   <br> NO_MODIFICATION_  ALLO WED_E       RR: Raised    if t        h   is property is r     e           a   do           nly.
          *         /
    p ublic void setOutlineColor(S  tring o  utlineColor)
                                                                                 throws DOME     xception;

    /**
     *  See     the         outlin e-style prope   rty defi  nition in CSS2.
              *  /
    public S trin  g get     Out       l      ineStyle();
    / **
         *       See the      outl  ine-style proper  ty    definition    in    CSS2.
       * @excep    tion             DOMExceptio   n
     *              SYNTAX_ERR: Rais   ed if t he n e        w val  ue has a                 syn   tax error an     d is
     *   unparsable. 
      *   <br>NO_MO  DIFIC     ATION_ALLOWED  _E       RR: Ra  ised if th     is prop      erty                 i   s re    adonly.
                          */
    pub     lic   void setOutl           ineSt yle(String outlineSt      yle)
                                                                throws DOM   Exc  ept    ion;

      /**   
     *  See the o  utl  ine-w       idth p    roper        ty  definition in C   SS    2.  
       *   /
        pu blic String      getOu             tlineWidth     ();
    /**
     *  See the ou                 tline-width property  de       finition in CSS2.
        * @exception DOMExcepti              on
     *   S   YNTAX_ERR           : Raised if         the new va   lue has         a syntax  e      rror and is
       *           unparsa  ble.       
       *   <br>    NO_      MODIF  ICA   TION_             ALLOWED_ERR      : Raised  if this property i  s readonly.
       */
    p   ubl    ic voi          d set  OutlineWidth(String outl  ine        W       idth)
                                                        throws        DOMException;

      /**
          *  Se   e the           overflow        prope  r  ty      d    ef        in ition i      n   CSS2.
     */
    pu     blic Strin g getOverflow();
      /**
       *  See the overf  low property definition             in CSS2.
            * @exce   ption DOMException
     *   SYNTAX_ERR: Raised       i    f the n ew value has a syn       ta     x e        rror and         is
     *    unp        arsable.
     *   <br>NO_M  ODIFICATION_ALLOWE    D  _ER  R: Ra    is       ed      if  this prop    e   rty is           r     ead  only.
             */
        publ    ic       void      set       Overflow(St ring ove             rflow)
                                                                t  h   rows       D                        OMException;  

    /**
     *  Se   e the padding           p        ropert   y def     init    ion in CSS2   .          
     */
    public String getPaddi ng();
       /**
     *   See the paddi      ng  prop    erty definition in CSS    2     .
         * @e   xcepti  on DOMExcep  tion
         *   SYN  TAX_ERR:    Ra  ise  d  if       t   he new val  ue       h    as a          syntax error and is
      *                unparsable.
          *          <br>NO_MODIFICATION_AL        LOWED_ ERR: Rai     sed if this property    i     s readonly.
          *             /
      publi  c void setPa     dd ing(Stri   ng pa  dding)  
                                                                    throws       DOMExceptio   n;

     /*     *
         *  See the p       ad         ding-top     property definition in   CSS2.
     */
        p  ublic Strin    g getP  addin  gTo   p   ();
        /**
     *  See the p  adding-top proper  ty def   inition          in CSS2.
      * @exception DOMException
     *                   SYNTAX    _ERR: Rai   sed i   f      the new value  has a     syn  t          ax er   ror a    nd is
                    *    unparsable.
        *      <br>NO_MO    DIFICATION_ALLOWED      _ERR: Raised if this property is rea donly.
     */
          public         vo    id set   Paddin gTop(St    ring p     adding        Top)       
                                                                               throws DOM     Exception;
   
    /**
     *  See the       pa      dding-righ t property definitio    n in CSS2.
          */       
      publi   c String g   e        tPadd i  ng                      Right        ();
    /**
     *     See   th    e padd  in     g- right pro    pert   y def    inition in CSS2.
            * @excep         tion DOMException
             *     SYNTAX_ERR: Raised if t h  e new value ha s a    syntax error    and         is
             *          unparsable.
     *   <br>NO_MODIFICA     TION_      AL  LO  WED_ERR: Ra       ise       d if this prope    rty is    readonl    y.
     *     /
      publ   i       c                  vo      id    set   PaddingRight(Stri     ng paddingRight)
                                                              throws DOMException;

    /         **
     *  See the padding-bottom property definition         in CSS2.
       */
    public String g  etPaddingBottom();
    /**
          *  See t  he padding-bottom property   definit  ion in     CSS2.
         * @exception DO  MException
     *   SYNTAX_ERR: R    aised if the     ne  w value  has a syntax error   a     nd is
     *               unparsable.
     *   <br>NO_ MODIFI  CATION_ALLOWE  D_ERR: Ra   ised if this pr    operty is readonly.
        *         /
       public void setPaddingBottom(Stri   ng paddingBottom)
                                                                          thr o   ws DOM  Exception;

     /**
     *   S ee the  paddin  g-left property        def  in  ition i   n C SS2.
           */
         public String      getPaddingLe   ft();
       /**
        *  See t    he pad    ding   -left pr   o  perty   def    inition in CSS   2.
       * @exception DOMException
     *       SYNTAX_ERR: Rai  s         ed   if    the new va lue    has a syntax e  rror and is
        *   unpars  able      .
             *   <br>NO_MO  DIFICAT            ION_ALLOWED _ERR: Raised if this property is  readonly.    
     */
    pu  b        lic  void     setP      addingLeft   (String p  addingLe    ft  )
                                                             thr    ows DOMExcep    tion;

      /**
           *  See th  e p a    ge pro   pert   y def         inition in CSS     2.
               */
    public       String getPag     e();
    /**
     *  See   the page prop       erty d    efinition    in CSS2   .             
           * @exception DO       M   Exception
       *   SYNTAX     _ERR:   Rai  sed if the  new      value            has   a       sy     ntax er     ror and is
        *      unparsable  .
        *   <br>NO_M       ODIFICATION_     ALLO  WED_E     RR:    Rais    ed    if this prop      erty is      readonly   .
     */
    pu       blic             void setPag  e(String page)
                                                                           throws DOMExcept   ion;

          /**
                  *  See the page-break-after property defi          n ition in CSS2   .
      */
    p   ublic String getPage   BreakAfter();
    /**
     *  See the page-bre      a  k-af   ter   p   roperty defin  i     tion  in CSS2.
     * @exceptio    n DOMException
         *   SYNTA       X_E  RR: Raised if the new value               has a syntax error a    nd    is
             *   unpa   rsable.
        *   <br>NO_   MODIFICATION_ALLOWED_ERR: Raised if    this pr    operty          is readonly  .
     * /
     publi    c void setPageBrea kAfter(     String pageBreakAfter)
                                                     throws DOME  xcepti   on;
  
    /**
     *  See the page      -br   eak-bef  ore property definiti        on in CS  S            2.
     */
      public         String getPa           geB reakB  efore();
         /  **
     *    See    the    page-   b reak-before  property definit  i     on in CSS2.  
     * @   exceptio n DOMException
       *   SYNTAX_ERR: Ra   ised if the new value  h                 as a syntax error   a       nd is
     *   u  nparsabl     e.
     *      <  br>NO_MODI   FI  CATI         O   N_ ALLOWED_ERR: Rai sed if this property is         readonly.
     */
       public void se t       PageBr  eakBefore(String pa      geBr       eakB  efore)
                                                                 throws DOMExceptio n;
                
    /**        
     *     See the page-break-inside pro   pert  y definition in CS    S2 .
     */
    public   S       t  r    ing        getP    ageBre    ak   Insi   de();
    /  **
        *  See t   he page     -break-inside  pr  operty definition  in CSS2.
     * @except ion DO   MExcept ion
       *   SYNTA X_ERR: Ra      ised if the n   ew va     l   ue      h   as a syntax e     rror and is
     *   unparsable.
     *   <br>NO_MODIFICATIO     N_ALLOWED_ERR: Raised   if this prope   r     ty    is read      only.
     */
     publ   ic void setPageBreakInside(String pageBreakI  n   side)
                                                            throws DOMException;

    /**
      *  See the pause property defin   ition in CSS2.
       *  /
     public String getPa                      use();
    /**
                *  See the   pause property      definition     in CSS2.
     *     @exception DO  MExce    ption
          *    SYNTAX_ERR: Raised if    the new       va lue has a syntax error       and is
     *   u     nparsable.
        *    <br>NO_MOD   IFIC      ATION_ALL     OWED   _ERR: Raised if this property  is reado   nly.
     */
                   public      v       oid setPause(String pause)
                                                          th      ro  ws DOMException;

         /**
     *  See the pause-after property        definitio       n in  CSS2.
     */
             public String g etPauseAfter();
     /**
          *  S ee the pause-after property definition in CSS    2     .
     * @exceptio  n DOME    xceptio       n
     *   S YNTAX_ERR:            R    aised i  f the new value ha   s a s  y   ntax erro      r and     is
         *     unpars    ab    le.
     *   <br>NO_MO DIFI CATION_ALLO       WED_ERR: Raised if this property is readonly.
       */
    public void setPauseAfter(St  ring pauseAfter)
                                                             throws DOMExcepti   on;

    /*        *
     *      See      the pause-b   efore   property de  finition in CS    S2    .
           */  
    public       Strin   g getP            auseBefore();
    /**
        *  S  ee the pause-    b e    fore property    d    efinition in CSS2.
     * @exception DOMException
     *   SYNTAX_ERR: Raise d    if t    he new v   alue has a syn    tax error and i     s
     *     unparsable.
          *     <b r>NO_MODIFIC  A    TION     _A LLOWED_ERR: Raised if     this p  roperty      is rea   donly.
     */
        publi   c void setP    aus eB     efore(String    p  auseBefore)
                                                                    thr   ows DOMExce  pti  on;

       /  **
             *  S  ee the pitch  pro  pert y def inition in CSS  2.
        */
      public String getPitch();
    /**   
     *  See the p  it  ch    proper  ty   definition in CSS2.   
        * @exception DO M           Exceptio      n 
            *      SYN   TAX_ERR: R   a      i     sed if the new valu       e has        a   sy           ntax error  a    nd is
     *      unpa rsa    ble.
     *   <br>NO_M     ODIFICATION_ALLOWED_ERR: Raised if     this  property is readonly.
     */
       public void setPitch(String pitch)
                                                        throws      D  O MExcepti    on;

    /**
        *  See the pitch-range property      definition in CSS2.
     */
        public String getPitchRange(); 
       /**
     *  See the  pitch-range p  roperty  defin   ition in   CSS2.
     * @excepti        on DOMException
     *      S    YNTAX_   ERR: Raised if the new valu  e has a syntax error and is
     *    u   nparsable  .
     *   <br>    NO_MODIFICATION_AL  LOWED_ERR: Raised if t his property is readonly   .
     */
     public void setPitchRa     nge(      String pitchRan  ge       )
                                                   throws     DOMExce      ption;

    /*   *
     *  See the pl    ay-duri    ng property de      finition i   n CSS  2.
        */
          publi c String ge  tPlayDuring();
    / **
     *      See the play-duri   ng p   roperty definition   in      CSS2.
      * @exception DO   MExcepti      on
     *   SYNTAX_ERR: Raised if the   new       value ha     s a   s   yntax error and is
        *   unpar   sable     .
            *   <br>N  O_MODIFICATIO     N_ALLOW   ED_ERR: R    aised if this prope   rty is readonly.
      */
    public void setPlayDuring(String playDuring)
                                                    throws DO   MException;

    /**
        *  See the po           sitio n        p        roperty defin ition   in CSS2.         
     */
    public   Stri  ng getPosition();
    /**
     *    Se    e the position property    definition in CSS2.
     * @exception DOMException       
     *   SYN     TAX_ER      R: R    a  ised if the new value has a syntax error and is
     *   unpa   rsa ble. 
     *   <br>NO_MODIFIC     ATION_ALLO  WED_ERR:    Raised if this property is        readon       ly.
             */
    public vo  id set    Posit  i on(String posit   ion)
                                                    throws DOMExcepti  on;

    /**  
     *     See the      quotes        property definition in CSS2.
     */
     public St       ring get  Quote   s();
           /**
     *      See   the quotes    property defini     t    ion in CSS2.
     * @exception        DOMException
     *   SYNTAX_ERR: Raised if the new   value has a   sy  ntax er ror and is
     *   unparsable.
     *    <br>NO_MODIFIC ATION_ALLOWED_ERR: Raised if this property is readonly.    
             */
    public void   setQuotes(String  quotes)
                                                      throws DOMException;

    /**
             *            See the r  ichness property definition     in CS            S2.
     */
    public String getRic             hness();    
    /**
     *  See the richnes      s property definition in CSS2.
     *         @exc   ep tion DOMException
     *   SYNTAX_ERR: Ra       ised if t  he new value         has a syntax error and is
     *   unparsable.
     *   <br>NO _MODIFI   CATION_ ALLOWED_ERR: Raised if this       p        roperty is readon    ly.
          */
    public void set       Richness(St ring richness   )
                                               throws DOMExcept  ion;

    /**
     *  S  ee the right prop   erty definit   ion in CS    S2.
              */
    public String  getRig  ht();
    /* *   
     *  See th    e righ     t property defi           nition in CSS2.
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syn         tax error and is
     *       unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR:          Raised if t his property is   readonly.
     */
      public void    setRight(String    right)     
                                                 throws DOMExcep      t ion;

          /**
     *  See t   he size prop    erty def     inition in CSS2.
         */
    pu     blic String getSize()  ;
    /**
     *  See t  he si  ze property d       efinition in     CSS2.
     * @exception DOMEx  ception
     *   SYNT    AX_ERR: Raised if the ne  w valu   e has a syntax err   or and i     s
     *   unparsable.
     *                <br>NO_MOD       IFI    CATION_ALLOWED_ERR: Raised if this pro perty    is       reado    nly.
            */
    public void se     tSize(String size)
                                                       throws DOME   x   cept    ion;

        /**
     *  See the speak p      roperty def    inition in CSS     2.
     */
    pu   blic S         tring get Speak();
    /**
     *  See the speak property definition in CSS2.
     * @exception DOMExcepti on
     *   SYN  TAX_E         RR:             Raised if t     he new value has a syntax error  a  nd is
     *            un   parsabl     e.
        *   <br>NO_MODIFI  C    ATION_ALLOWED  _ERR: Raised if   this property is    readonly.
     */
    pu      blic void setSpeak(Str           in  g speak)
                                                          thro     ws DOMExce  ption;

    /**
     *  See     the speak-header prop    e  rty definition in CSS2.    
       */
    public String getSpeakHeader();
      /**
     *  See       the speak-header property   definition in CSS2.
     * @exception DOMException   
     *   SYNTAX_ERR: Rais  ed if the new v alue ha    s a synt   ax error and is
       *     unparsable.
     *   <br>NO_MOD    IFICATION_ALLOWED_ERR       : Raised  if this    property i       s readonly.
     */
    public void    setSpeakHead  er(Str   ing speakHeader)
                                                      throw    s DO         MException;

    /**
            *  See th   e speak-numeral property defini   tion in CSS 2.
          */
    public String getSpeakNumeral();
    /**
     *  See the speak-numeral pro   perty definition     in CSS2.
     * @exceptio      n     D    OMException
     *   SYNTA    X _ERR: Raised if the new value has   a   syntax error       and is
     *   unpars  able   .
     *   <br >NO_    MO    DIFICATION_ALLOWED_  ERR: Raised if   thi    s property     i s readonly .
     */
        public v oid setSpeakNum    eral(St    ring speakNumeral)
                                                        throws DOMExce ption;

    /**
     *  See the speak-punctuation propert   y definit ion in CSS2.
     */
    public String get     SpeakPunctuation();
        /**
     *        See the speak-punctuation property definition in CSS2.
     * @exception DOMException
     *      SYNTA    X_ERR: Raised i       f the new value has   a syntax error and is
     *              unparsab le.
     *         <br>NO_MODIFICATIO  N_ALLOWED  _ERR: Raised if this property is readonly.
     */
    public void setSpeakPunctuati       on(String speakPunctuation)
                                                            throws DOMExc    eption;

    /**
     *  See the       speech-rate property definition in CSS2.
     */
    public String getSpee  chRate();
    /**
     *  See the speec   h-rate prope   rty definition in CSS 2.
     * @exception DOMExcept     ion
     *   SYNTAX_ERR: Raise        d if the new value has a syntax error and is
     *         unpar   sable.
        *   <br>NO_MODIFICATION_A   LLOWED_ERR: Rais        ed if this property is readonly.
     */
    public   void setSpeechRat  e(String speechRate)
                                                    throws DOMExcepti    on;

    /**
                  *          See t  he stress property de  finition i   n CSS2.
     */
          public String getStress();
     /**
     *  See the str    ess propert  y definiti  on in CSS2.
     * @exception DOMExceptio     n
     *   SYNTAX_ E   RR: Raised if t   he new value has a syntax err    or and is
     *   unparsable.
     *   <br>NO_ MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    publ ic void setSt  ress  (String stress  )  
                                                     throws DOMException;

    /**
     *  See the table-layo  ut property definition in CSS2.
     */
    p    u   blic String g    etTableLayout();
                  /**
     *  See  the table-layout pro   perty definition in CSS2.
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a s   yntax error and  is
     *     unpa rsable. 
     *   <br>NO_MODIFICATION_ALLOWED_ERR: R     ais      ed if   this property is readonly.
     */
    public  vo   id setTableLayout(String tab leL     ay  out)
                                                         throw   s DOMEx     ception;

       /**
         *  See t   he text-align property definition in CSS2.
        */
    public String getTex  tAl     ig     n();
    /**
     *        See the text-align      property definition in CSS2.
              * @exception DO  MException
     *   SYNTAX_ERR: Raised if the ne  w value has a synt        ax error and is
     *   unparsable.
     *      <br>NO_MODIFICATION_ALLOWED_          ERR: Raised if this p    roperty is read      only.
     */
    publ  ic void setTextAlig    n(String textAlign)
                                                throws DOMException;

    /**
          *   See the text-d   ecoration property definition in CSS2.   
     */
       publ       ic    String getTextDecoration();
        /**
     *  See  the text-decoration property def     inition in CSS2.
       * @excep     tion DOMExce    ption
      *   SYNTAX_ERR: Raised if the new value has a      syntax error and is
               *   unparsable.
       *   <br>NO_MODI   FICATION_ALLOWED_ERR: Ra   ised if    this property is  readon ly.
     */
    public v   oid setTextDecoration(String textDecorat i on)
                                             throws DOMExce ption  ;

    /**           
     *  See the text-indent property definition in CSS2.
       */
    pub  l     ic String getTextIndent();
    /**
     *  See the text-indent property definition in CSS2.
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax e    rror and     is
     *             unparsable.
     *   <br>NO_M    ODI     FICATION_ALLOWED_ERR: Raised if this property is r    eadonly.
     */
    public vo     id setText      Indent(S    tring textIndent)
                                                             throws DOMException;

    /**
       *  See the text-shadow property de finition in CSS2.
     */
    public Stri    ng getTextShadow();
    /**
     *  See the t        ext-shadow pr      operty definition in CSS2.
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax e rror and is
     *   un   parsable.
     *   <br>NO_MOD    IFICATION_ALL  OWED  _ERR: Raised if t   his property is readonly.
        */
    public     void setTextShadow(String textShadow)
                                              throws     DOMException;

    /**
     *  See the text-transform property definition in CSS2.
     */
    publ       ic String getTextTransform();
    /**
     *  See the   text-transform property definition in      C  SS2.
     * @exception DOME  xception
     *   SYNT     AX_ERR: Raised if the new value has  a synt      ax error a   nd is
     *   unparsable.
     *   <br>NO_MODIFI     CATION_ALLOWED_ER  R: Raised if this property is read   only.
     */
       public v  oid setTextTransform(String textTransform)     
                                                               throws DO   MException;

    /**
     *  See the top property definition in CSS2.
     */
    publi c String getT       op();
    /**
     *      See the t         op property definition   in CSS2.
     * @exc  eption DOMException
     *   SYNTAX_ERR: Raised if the new val     ue has a syntax error and is
     *   unp    arsa   ble.
           *   <br>NO_MODIFIC     ATION_ALLOWED_ERR: Raised if this      property is readonly.
     */
    public void setTop(String t    op)
                                                 throws DOMException;

    /**
     *  See the unicode  -bidi property definition in CSS2.
     */
    public String getUnicodeBidi();
    /  **
      *  See the u  nicode-bidi property def inition in CSS2.
     *    @exception DOMException
     *   SYNTAX_ERR: Rais ed if the new value has a syntax error and is
       *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setUnicod eBidi(String unicodeBidi)
                                             t      hrows DOMException;

    /**
        *  See the verti     cal-align property    definition in CSS2.   
     */
    public Stri      ng getVerticalAlign();
        /**
     *  See the vertical-align property definition in CSS2.
     * @exception DOMException
     *   SYNTAX_ERR: Rai   sed if the new    value has a syntax    error and is
     *   unparsable.
     *   <br>NO_MOD   IFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
          pu  blic void setVerticalAlign(String verticalAlign)
                                             throws DOMException;

    /**
        *  See the visibility property definition in CSS2.
     */
    public String getV      isibility();
    /**   
     *  See the visibility property definition in CSS2.
     * @exception DOMExce ption
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *        unparsable.
     *   <br>NO_MODIFICATION_AL  LOWED_ERR: Raised if this property is r        eadonly .
     */
    public     void   setVisibility(String visibility)
                                               throws DOMException;

    /**
     *  Se   e th  e voice-f amily property definition in CSS2.
     */
    public String getVoiceFamily();
    /**
     *  See the vo  ice-family proper   ty defin    ition in CSS2.
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax     error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
          public void setVoiceFamily(String voiceFamily)
                                               throws      DOMException;

    /**
     *  See the volume property definition in CSS2.
     */
    public String getVolume();
    /**
     *  See the volume property definition in  CSS2.
     * @except   ion DOMException
     *   SYNTAX_ERR: Raised if the new value has a syn    tax error and is
     *   unparsable. 
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if t  his pr     operty is readonly.
     */
    public void set   Volume(String volume)
                                                throws DOMExc eption;

    /**
     *  See the white   -space property definition in CSS2.
     */
    public String getWhiteSpace();
    /**
     *  See the white-space property definition in CSS2.
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.  
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Rai   sed if this property is readonly.
     */
    public void setWhiteSpace(String whiteSpace)
                                             throws DOMException;

    /**
     *  See the widows property definition in CSS2.
     */
    public String getWid      ows();
    /**
     *  See the widows property definition in CSS2.
     * @exception DOMException
      *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setWidows(String widows)
                                             throws DOMException;

    /**
     *  See the w     idth prop        erty definition in CSS2.
     */
    public String getWidth();
    /**
     *  See the width property definition in CSS2.
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsab    le.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setWidth(String width)
                                             throws DOMException;

    /**
     *  See the word-spacing property definition in CSS2.
     */
    public String getWordSpaci ng();
    /**
     *  See the word-spacing property definition in CSS2.
     * @exception DOMException
     *   SYNTAX_ERR: Ra ised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setWordSpacing(String wordSpacing)
                                             throws DOMException;

    /**
     *  See the z-index property definition in CSS2.
     */
    public String getZIndex();
    /**
     *  See the z-index property definition in CSS2.
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the new value has a syntax error and is
     *   unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this property is readonly.
     */
    public void setZIndex(String zIndex)
                                             throws DOMException;

}
