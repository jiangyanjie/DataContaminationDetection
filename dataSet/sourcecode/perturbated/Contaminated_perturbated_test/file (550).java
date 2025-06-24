
package    DBGDPV3;

import       javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
  import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 *     <p>Ja  va c     lass for DCSN_      INFO complex ty     pe.
 * 
 * <p>The following schema fragment  specifie  s       the       expec    ted content contained   w  ithin this          class.
 * 
 * <pre>
 * &lt;co   mplexType name="DCSN  _INFO">
 *   &lt;complexContent>
 *     &lt;restriction bas      e="{http:     //www.w3      .org /2001/XMLSc  hema}anyType">
 *       &lt;sequence>
 *         &lt;element name=     "C    R_DCSN" type="{http://www.w3.org/200     1/X      MLSchema}string  " minOccurs="0"/      >
    *              &lt;elem      ent       name="DCSN_ID" typ         e="{http://w      w    w.w3.org/2001/XMLSchema     }s  tring"    minOccurs="0"/>
 *          &lt;element name="RPT_R  EQ_USERID" type="{http://www. w3.org/200    1/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="R PT_GNRT_DT_TME" type="{http://www.w3.org/2001/XMLSch      ema    }string    " minO      ccurs="0"/  >
 *         &lt;     elem    ent     name="ACTN_MSG" type="{http://www.w3.org/2001/XMLSchema }string" minOccurs="0"/>
 *         &lt;element name="INP_CRCY_C D" ty  pe="{http:/ /w ww.w3.org/2001/XMLSchema}string" minOccu       rs="0"/>
 *                  &lt;element name="R EQ_CR_AMT" type="{ht    tp://www.w3.org/2001/XMLSchem    a}string" minOccurs="0"/          >
 *              &lt;element name="OTSD_CR_BAL" type="{http://www.w3.org/2001    /      XMLSche  ma}s   tring" minOccur s="0"    / >
   *         &lt;element name="USER_DEF_1" t    ype="{http://www.w3.org/2001/XMLSchema            }string" minOccurs="0"/>
 *         &lt;element name="USER_D     EF_N"     t   ype="{   http://w  ww.w3.org/2001/ XMLSchema}string"  m        inOccurs="0" />
     *         &lt;element name="CR  _LMT_CRC   Y_CD"    type="    {h   ttp://www.w3.org/2001/XMLSchema}string" minOccu     rs="0"/> 
      *                  &lt;element name="C  R_LMT" type="{http  ://ww    w.w3.org/2001/XM   LSchem a}string" minOccurs="0"  />
 *         &lt;element name="BILB_IND" type="{http://ww  w.w3.org/2001/XMLSche    ma}strin     g" minOccurs="0"/>
 *             &lt;    element name="RSK_BAN   D" typ     e="{http://w    ww.w3.org/2001/XMLSchema}strin      g" minOccu    rs="0"/>
 *         &lt;elem        ent name="DM_AGE" typ e="{http:      //w      ww.w3.org/2001/XM  LSchema}string" minOccurs="0"/>
 *             &l t;  element name="CURR_CNTL_AGE" type="{ht  tp://www.w3. org/2001    /XM LSchema}string" minOccurs="0"/>
     *          &lt;ele  ment name="DM_RS    K_RATG" type="{http://w   ww.w3.o     rg/   2001/XMLSchema}str      ing" minOccurs="0"/>
 *         &lt;element name="DM_SIC_TYPE_CD  " type="{http://www.w3.org/200     1/X   MLSchema}string" m  inOccurs="0"/>
 *         &lt;elemen   t name="DM_SI  C" type="{http://www.w3.org/2001/XMLSch  ema}string" minOccurs="     0"/>
 *         &lt   ;eleme  nt name=  "DM_NET_WRTH" type="{http://www.w3.org/2001/XMLS   chema}stri  ng" minOccurs="0"/      >
  *         &lt;element name="DM_SLS" type="{http:/   /       www.w3.org/2001/XMLSchema}str  ing" m          inOcc  urs="0"/>
 *           &lt;elemen  t name="CR_RULE" type=   "{http:      //ww   w.w3.org/2001/XMLSche   ma}string" mi  nOccurs="0"/>
 *         &lt;element name="CR_RULE_VER" typ   e="{http://ww    w.w3.org/200   1/XMLSchema}string"     m         inO  ccurs=        "0"/>
 *       &lt;/sequence>
 *         &lt;/restriction>
 *   &lt         ;/complexContent>  
     * &lt;/co     mplexType>
 *        </pr   e>
 *    
 * 
   */
@   XmlAccessorType(XmlAccessType   .FIELD)
@XmlTyp   e(name = "DCSN_IN    FO",  propOrde       r = {
         "    crdcsn",
             "dc     snid",
    "rptrequserid",
    " rp  tgnrtdttme",
              "         actnm  sg",
       "    inpcrcycd",
        "reqcramt",
       "otsdcrbal",
      "userdef1",
    " userdefn",
    "crlmtcrcycd",
       "crlmt",
                        "bilb          ind",
    "rsk  band",
    "dm   age",
        "     c  u  rrcntlage",    
    "dmrskratg",
    "d   msictypecd",
    "dmsi   c",
       "dm  netwrth",
    "dmsls",
    "crr    ule",
    "crrulever"
})
public clas     s DCSNINFO     {

    @XmlElementRef(name = "CR_     DCSN", type = JA  XBElement.class)
    protec ted JAXBElement<String> crdcsn;
    @       Xml    ElementRef(name = "DCSN_ID", type  = JAXBElement.class)
    protec   ted    JAXBElement<String> dcsnid;
    @XmlElement     Ref(na  me =   "RPT_REQ_USERID", type = JAXBElement.class)
      protected JAXBElement<String> rptrequserid;
      @X    mlElement   Ref(name = "RPT_GNRT_DT_TME"   , type     =   JAXBElement.class)
     protected JAXB    Element<S  tring> rptgnrtdttme;
    @XmlElemen tRe    f(name = "ACTN  _MSG", type = JAXBElement.class)
    protected  JAXBEleme        n  t<String> actnmsg;
      @XmlElementRef (name  = "INP_CR CY      _  CD  ", typ    e = JAXBElement.clas      s)
    protected      JAXBElemen   t<String> inpcrc ycd;
       @XmlElementRef(name =  "REQ_CR_AMT", ty  pe = JAXBElement.class)
      protected JAXBElement<St  ring> reqcramt;
       @XmlEl  emen   t    Ref(name = "OTSD_CR_BAL", type = JAX   BE   lement .class)  
    protected JA    XBElement<Str  ing> otsdc   rbal;
    @XmlElementRef         (name   = "US    ER_DEF_1", type = JAXBElement.class)  
    prot   ected JAXBElement<Str   in g>   userdef1;
    @XmlElementRe  f(name = "USE   R_DE      F_N", ty    pe     = JAXBElement.class)
    protected JAXBElement<   String> userdefn;
    @XmlElementRef(na me = "CR_   LMT_CRCY_CD", ty      pe = JAXBElement.   class)
    protected JAXBElement<String> cr   lmtcrcycd;
    @          XmlElementRef(name =  "CR_LMT", type = JAXBElement       .class)
    prot  ected JAXBElement<String> cr lmt;
    @XmlElementRef(n    ame = "B   ILB_IND", type = JAXBEl ement.class)
    protecte   d J  AXBElem   ent<String> b   ilbind;
    @X  mlEle  me     ntRef(name = "RSK_BAND", type = JAXBElement.cla ss)
        protected JAXBElement<St      ring> rskband;
    @XmlElementRef(na     me = "DM_AGE", t      ype        = JAXBElement.class         )
    pro              tected JAXBElement<Stri            n  g> dmage;
    @XmlElementRef(name = "CURR_CNTL_AG       E", type = JAXB El   ement.c   lass)
    p   rote     cted JAXB   Element<Stri ng> c    urrcntlage;  
     @   XmlEleme   nt       Ref(n  ame = "DM_RSK_R   ATG", type = J   AXB   Elemen   t.class)      
        protecte  d    J        AXBElem    ent<  String> dmrskratg         ;   
     @XmlEleme nt  Ref(name      = "DM_S    IC_TYPE_CD", ty pe = JAXBElem    ent.class   )
         prote    cted JAXBElement<Str       ing>    dmsic typecd;
    @XmlEleme     ntRef(name = "DM_SIC",    type = JAXBElement.cl  ass    )
    prote    c ted JAX     BEl         em     ent<      Strin            g> dmsi   c;
          @XmlElementRef(name = "    DM_NET_    WRTH", t ype = JAX      BElem     ent.cla      ss)
    prot    ec te   d JA XBElement<S  t    ri     ng>     dmnetw   rth;
    @XmlEleme               ntRef(name        =  "DM_SLS",   ty      pe = JAXBElement.c     lass    )
       protected JAXBElemen     t<String> d  msls;
       @XmlElemen      tRef(name =     "CR_RULE", type     = JA XBEl            ement.cl  a ss)
    protected JAXBElement<String       > crru   l e;
    @X   m   lElementRef(n   a  me =     "         CR_R   ULE_VER", type =     JAXBE   le    ment.clas      s)
       protecte   d JAXB   Elemen      t<Strin   g>   crrulever        ;

    /**
       * G     ets the v              alue of th               e cr   dcsn        pr          operty.
     * 
     * @retur   n
              *           pos sible object       is
              *     {@li    nk            JAXBElemen   t }{@code <    }{@li   nk St      ring }{@    c  ode >}     
       *             
     */
           p    ublic JAXBElem    ent< S    tring> getC  RDCS           N() {
                     return crdcs   n;
    }

    /**
     * S   et s t   he value o   f the      cr    dcs      n property.
     * 
       *      @par am value
     *       a llowed object is
     *     {@li     nk JAXBEle  ment }{@co    de     <}{@l ink String }  {@c    ode >}
     *     
     */
        pub       lic void setCRDCSN(JAXBElement<   St      ring> value) {
            this.crdcsn = ((J     AXBEl  ement<Str ing > ) value);
       }  

    /**  
      * G ets t   h e value of the dcs      nid property.
     * 
         * @ret    urn    
      *      poss    ib            le   obje   ct           is
      *         {   @link JAXBEl       eme   nt      }{@c   od       e <}{@l   ink String }{@cod    e   >}  
          *     
            */      
      public       JAXB     E   l  ement<S             tring> getDCSNID(       ) {
                           re  turn dcsni  d;
          }

    /    **          
     *   Sets the value      of t   he dcsnid pro  perty.
                  * 
     * @param val   u     e
           *     al  l   ow    ed     ob  ject     is
           *       {@link JAXBEl  e     ment }{@cod      e <}{@link String }{@code >}
       *     
              */
    public void se  t DCSNID   (      JAXBE  le  m       ent<String> valu        e)    {
              this.dcsnid = ((JA         X    BElement<  Stri    ng> ) value)    ;
    }

    /**
     *    Gets the value of the rp  trequserid property.
         * 
     * @             retur   n
             *                             possibl   e object is
           *              {@link      JAXBElem  ent    }     {@cod  e               <}{@lin    k String }{  @code >}
     *     
        */
           p            ublic JAX  BElement<Stri ng> getRPTREQUSERI          D() {       
                 retu      rn rpt       re quser             id;
     }

    /**
         *      Se                t     s the value           of the rp        trequserid p  roperty.
     * 
      * @p    aram value
            *            a   l  lowed obje      ct     i     s
             *          {@link JAXBElement }{@code <}{@link String }{@code >  }
     *       
     */
    p   u        bli      c void se   t         RPT       REQUS  ERID(JAXBElement<String    > val  ue     ) {
                t  his.rptr    equse     rid = ((JAXBEleme   nt  <Stri   n             g> ) value);
       }

     /**
       *   Gets the value       of the rpt gnrt       dttme pro  per    ty.
             *    
     * @retu     rn
     *        po   ssible object  is    
              *     {@link JAXBE   lem        en  t }{@code <}{@      link Strin  g }{@code >}
        *     
           */
     public  JAX    BElement<String> getRPTGNRTD       TTME() {
            r  etur    n rp tgnrtdttme;
    }

    /**   
     *    Sets        the value  o   f the       r   ptgnrtdttme p   roperty.
        * 
     *     @param value
         *     allowe  d objec  t   i   s
            *             {@  li   nk JAXBElement  }{  @co  de <}{@link                    Str  in g             }{@  code >}
     *        
       *    /
         publi    c  void        se          tRPTG  NRTDT TM    E(JA XBE    leme  nt<   String> value)   {      
            this.rptgnr      tdttm e =    ((JAXBEleme  nt<     Str    ing> ) valu          e);
       }
             
        /*   *
               * Gets the value of t  he actnm              sg    prope        rt    y      .
                 * 
        * @re      tur      n
     *              poss           ible obj        ect is
     *        {@link JAXBElement }{@code <}{@l       ink String }{@code >}
            *     
           *    /   
         public JAXBElement<String> getACTNMSG() {
        retu    rn        ac                     tnms         g;
          }
  
        /**
                  *    Set s the    va   lue of the       actnms    g pro     perty.
         * 
       * @param     va lue
         *     allowed       obj         ec  t is
     *               {@li    nk JAXBElem     ent }{@code <}{@li     nk String }{@code >}
            *         
     */    
    pub   li       c void setACTNMSG(JAXBElement<St        ri    ng> val   ue)         {
           th      is.actnms   g = ((J  AXBEle  ment<String      > )   va    lue);
           }

      /**
                          * Ge         ts     th   e value        o  f the    inpc  rcycd proper  ty.
     * 
                * @re    turn
            *              pos  sible obje   ct i    s
        *     {@       link J    AXBElement }{@code <}    {@link St        ring    }  {   @code >}
     *      
     */          
    p u    blic JAXBElemen    t  <S  tring> g     etI  NPCRCY    CD() {
           return i      npcr        c   yc      d; 
    }

    /*      *
      * Sets the va             lue of the inpcrcycd    prope   rty.
       *     
           *    @        param      v   a  lue  
     *        allowed object is
     *                     {         @link JAX    BE                lement }{ @co  de <}{@l   ink S  t    ring }        {    @code >}
         *             
     */
    public void   s   etIN    PCRCYCD(JAXBElement<St  ring> va  lue) { 
        t   h    is  .inpcrcycd = (   (J    AX                  BElement<  String> ) value);
          }                   

        /**
                 * Gets   the   value of the   reqcramt property     .
                * 
         *           @ret  u  rn
           *     pos   sible object i  s
            *        {@ l         ink JAXBElem     ent   }   {@code      <}{@link String }   {@code >   }
     *     
       */
    public JA  XBElemen    t<Str     ing> getRE     QCR AMT(   ) {
          ret  urn reqcramt  ;
    }

         /**
     *   Set  s the v   alue    of th     e    reqcramt    propert          y   .
     * 
             * @param    value
      *     allowed obj ect is
     *        {@link              J       AXBEl    ement }{@co     de <}{@l             ink St  ring }{@c      ode >}
     *     
      */
       publ   ic void setREQ               CRAMT(JAXB        Element<String> value)       {
              this.reqc    ramt = ((J        AXBElemen    t<String>  ) val   ue);
                  }  

    /**
             * Ge       ts the value o     f   the  otsd    crbal property  .          
        * 
         * @return
            *      possible object   is
     *     {@link JAXBElemen                  t }  {@ co    de <}{@link St     ring }{@c    ode >}
          *     
     *  /
          public JAXBElement   <  S               tri  n      g> getO TSDCRBAL()       {
           r    eturn otsdcrbal   ;   
       }   

          /**
     * Se        t  s the value of th       e     otsd      c  rbal     property.
       * 
     *   @param val    ue
     *         al  lowed object is
     *        {@link JAXBElemen     t             }{@cod     e <}{@link String }            {@code >}   
         *         
           */ 
      public        voi     d   se    t  OTS  DC  RB       AL(JAXBElement< String> value    )        {
                       this   .otsdcrb al = ((JAXB    Element<Str   ing> )      value        );
          }

                /         **
     * Gets th        e value of     the    userdef1 p roperty.
       * 
        * @return
                  *     possi             b le    o   bject is
     *        {@link JA     XBElemen   t                 }{@     code <}{@lin         k Strin      g   }{@code  >}
     *             
              */          
    public  JAXBE      lement<St      ring>        getU  SE     RDE                   F1()               {
                return userdef1;
               }

    / **
      * Sets the v      alue o   f the user   d  ef1 propert   y.  
         *    
         * @pa  ram value   
     *     allowed o      bje   ct  is
                             *     {@li  nk            JAXBElemen  t }{ @code   <}{@l     ink St    r ing }{@co    d   e >}
     *               
     */
         publ   ic void setUSERD      E     F1(JAXBElem  ent<String> v   alue    )    {
           t h      is.us  erdef1 = ((JA                XBElement<St   r   in          g> ) value);
    }

    /**
     *            G  ets the value of the userde   fn p   roper         t   y.
     *     
        * @return
     *          p     ossible o       bject is
     *          {@link  J     AXBEl  ement }{@cod  e <}{@link String   }{  @code >}
             *     
     */
          public JAXB   Elem  en         t<String         > g  etUS   ERDEFN() {
                      retu   r   n           userde            f                 n;
          }

    /**
                 * Se    ts the value of the   user  defn p     ropert   y.
           *     
     *  @param               value
     *        allow      e   d o b   jec  t is
     *           {@link     JAXBEleme   nt     }{@code < }{@  link String }{@code             >}
       *      
     */
    pub    lic void setUSERDEFN(       JAXBE  lem    ent<St    ri    ng> va     lue) {
          this.use       r       de   fn =    ((JA   X   BEle         ment  <String> ) value);
                }     
                 
         /**
         *    Gets the  value of th      e cr  lmtcrcyc   d property           .
     * 
                         * @return
     *      possible obje ct  is
               *     {@    link JA XBEl      em     ent }{          @cod e <}{@link Strin g    }{@c ode     >      }        
     *         
     */
      public JAX   BE     le  ment<String>           ge           tCRLM   TCRCYCD() {
           return c     rl       mtc   rc   ycd ;
    }  

           /**
     * Sets the value            of the    crlmtcrc   yc     d p         roperty       .
            * 
     *   @    p     aram va lue 
     *     allow    ed object is
     *         {@li    nk         JAXB     Element }{  @code <}{  @link S  tring }{@  code >}
     *     
                  * /
       public void       setCRLMTCRCYC  D(JAXB       El    ement<Str  ing> va    l    ue) {
               this.c   rlmtc   rcycd  = ((JAXBElem   ent<Stri ng> ) v     al      ue);
       }    

    /**
     * Gets  t       he value of the crlmt property.
     *    
      * @r eturn
              *              possible               ob       ject is
     *                          {@link  JAXBEl       eme    nt } {@c o  de <}  { @link S  trin        g  }{@cod    e >}
     *        
     */
         pub   lic JA    XB  Element     <String> getCRLMT()    {
        return crlmt;
        }

         /**
            * Sets t  he value  of t        h           e crlmt pr  ope  rty.
      * 
     * @param value
     *     all     owed obje      ct            is
     *     {@link JAXBElement    }{@code <}{@lin      k String }{@ code >}
     *           
     */    
        public vo        id setCRLMT(JA  XBEleme  nt<String     > v   alue) {
                                    this.crlmt = ((       JA  XBElement<String> ) v      alu            e)   ;
         }

             /**
      * Gets the value   of the b      ilbi     nd        property.
           *   
            * @return
     *     possible o       bject is
         *                  {@     link     JA  XBEle           ment }{@code     <}{@li  nk Str    in   g   }{@code >}
     *        
     */
        public JAXBEleme   nt<String>   get     BILBIND() {
                       return bilbi   n d;
         }

    /   **
     *       Sets the value of th  e   bilbind        property    .
      * 
         * @param valu   e
      *     all  o     wed o  bject i   s
     *     {@lin  k  JAXBEleme   nt  }{@code <     }{@link    Strin                g }{@c o   de          >}
     *         
            */
        public vo     i       d se    tBILBIND(JAX    BElemen t<S t     ring>       valu          e) {
              this.bilbin d      = ((JAXBEl   e    me  n    t <S tring> )         va       lue);
    }

    /**
     * Gets         the valu         e of t    he  r   sk       b                 and property.
                   * 
        * @return
     *     possible obj     e  c  t i   s
           *     {@link JAXBEl     eme    nt }{@     cod     e <}{@link S    tri     ng }{@code >}
       *         
     */
      p       ublic    JAXBElemen    t<Stri    ng> getRSK     BAND()         {
            retu    r     n rskband;
         }        

    /**
     * Sets    the value of t                 he rs         k    ba  n          d property     .
     * 
     * @par  am      valu  e
       *     allow ed object is
     *      {@link JAXB  Eleme   nt }{@c  ode <}{@link S   t      ring }{@code >}
        *           
     */
    public void setRSK BAND(  JAXBElemen   t<String> value) {
           t  his  .     rskband            = ((JAX    BEleme   n    t  <String> )    value);
        }

    /** 
     * Gets     the value of the  dmag  e property.
        * 
     * @retu    rn
     *     possible object is
     *       {@link JAXB Element }{@code <     }{@      link Strin   g }{@code      >}
         *       
     */
    public JAXBElement  <S tring> get     DMAGE()       {
        return dmage;
     }

         /**
        * Sets    t he val  ue of t      he   dmag       e prope         rty.
            * 
      * @param va lue
                *        allo   wed object      i    s
     *      {@link JA     XBEleme       nt       }{@co        de <}{   @l      i   nk    Strin                g      }{@c      o  de >}
         *         
     */
    p ublic    void set   DMAGE(JA     XBElement<String     > value) {
                  th is   .   d   mage = ((JAXBElement<St ring> ) value);    
    }

    /**
     * Gets the valu  e    o f the cu   rrcntlage property.
     * 
         * @return
             *          possible obje  ct is
     *            {@link JAX       BEl   ement }{@code <}{@link S     tring }{@code >   }
       *          
     */  
    p   ublic JA    XBElement<String> getCURRCNTLAGE() {     
        return c  ur   r   cntlage;
    }

      /**
     *       Sets the val    ue o      f the c  u  rrcnt  lage property       . 
     * 
     * @ param va lue
        *     allowed object is
     *     {@lin   k JAXBElement   }{    @code <}{@link String }  {@code >}
         *     
          */
    publi    c     voi    d    setCU RRCNTLAGE(J  AXBElement<String> value) {
          this.cur    rcntlage = ((JAXBElement<String> ) va    l  ue);
    }

        /**
         * Gets the val   ue o    f the d  mrskratg       pro      perty.
     * 
     *     @return
       *     possible obje   ct     is      
     *     {@link JAXBElement }{@code <}{@link String     }       {@  code >}
     *      
     */
    pub lic JAXBE          lement<Str  ing> getDMRSKRATG() {
                 return dm     rskra              tg;
                        }

    /*  *
     * Sets the v  alu   e of the dmrskratg    pro   pe             rty.
      * 
     * @param   value
        *     al     lowed object is
     *       {   @lin            k JAXBElement     }{@cod   e <}{@link Strin   g    }{@code >}
     *     
     * /
    pu  blic void setDM    RSKRATG(JAXBElem ent<  S  trin   g>  value) { 
        t    his.dmrskratg =   ((JAXBElement<String> ) value);
    }

    /**
          * Get  s the value               of t he dmsictypecd prope                rty.
     *     
     * @return
     *     possible ob  je              ct is
     *     {@link JAXBEl   emen       t }{@code <   }{@link    St  ring }{@code >  }
            *     
     */
    public JAXBElement<String>      getDMSICTYP  ECD() {
            return dmsictypecd;
    }

    /**
     * Sets the value    of the dmsict   ypecd p    roperty.   
     * 
     * @par am value
     *          allo     wed object is
     *           {@link JAXBElement }{@code <}    {@link String   }{@code >}
     *     
       */
     public void set     DMSI   CTYPE    CD(JAXBElem       ent<String> value) {
              this.  dmsictypecd = ((JAXBElement<String>   )  value); 
    }

    /**
     * Gets the value of     the d   m  sic proper ty.
        * 
       * @return
         *     possible object is
     *     {@link JAXBElement      }{@code <}{@link Stri   ng } {@code >}
     *       
     *      /
    public JAXBElement<Strin  g> getDM SIC() {
             r   eturn         dmsic;
      }

        /**
          * Sets     the value    of the dmsic pro pert   y.
     *     
     *    @par  am value
     *     allowed object is
     *        {@lin k JAXBElem   ent }{    @code <}{@link St  ring }{@code >}
         *     
          */
    public void setDMSIC(JAXBElem     ent<String>  value) {
        this.dmsic      = ((    JAXBElement<String> ) value);
    }

        /**
      * Gets the value of the dmnetwrth property.
     * 
       * @return
     *     possible objec   t    is
     *     {@l  i             nk JAXBElemen t }{@code <}{@link String }  {@code >}
     *     
     */
    public JAXBElement<String> getDMNE       TWRTH() {
        retu  rn dmnetwrth;
    }

    /**
     * Sets the value of the dm   netwrth property.
        * 
        * @param value
     *          allowed object is
     *     {@link JAXBElement }{@code <   }{@link String }{@code >}
     *        
       */
    p    ublic void  setD MNETWRTH(JAX   BEle     ment<String>    value) {
        this.d         mnetwrth = ((JAX   B   Element<String> ) value);
    }

       /*  *
            * Gets th    e  value of the d   msls property    .
     * 
     * @return
     *     possible object is
       *       {@link JAXBEleme   nt }{@code <}{@link String }{@code >}
      *       
     */
    public JAXBElement<S   tring> getDMSLS() {
          return dmsls;
    }

    /**
     * Sets the value of the d  msls property.
       * 
        * @para     m value
     *     a llowed ob   ject is
         *     {@link JAXBElement }{@code <}{@link    Strin   g }{@code >}
     *     
     */
    publ      ic void setDMSLS(JA    XBElement<String> value) {
        this.dmsls = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the val    ue of the crrule    property  .
     * 
     * @return
       *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<Stri     ng> getCRRULE() {
        return crrule;
    }

    /**
     * Sets the valu  e of the crrule property     .
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCRRULE(JAXBElement<St   ring> value) {
            this.crrule = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the crrulever property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String  }{@code >}
        *     
     */
    public JAXBEl ement<String> getCRRULEVER() {
        return crrulever;
    }

    /**
     * Sets the value of the crrulever property.
             * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link S   tring }{@code >}
     *     
     */
    public void setCRRULEVER(JAXBElement<String> value) {
        this.crrulever = ((JAXBElement<String> ) value);
    }

}
