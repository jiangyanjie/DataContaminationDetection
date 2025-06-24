
package DBGDPV3;

import  javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
impor  t javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 *   <p>Java cl    ass for      CORPORATE_LI   NKAGE complex ty   pe.
   * 
 * <p>The   fo    llowing schema fragment sp        ecifies the   exp  ected content     contained with   in this    class.
 * 
      *         <p re>
 * &lt;complexType name="CORPORATE_LINKAGE">
 *   &lt;complexContent>
 *     &lt;restri       ction    base="{http      ://     www.  w3.       org/2001/XMLSchema}anyType">
 *         &lt;seq  uence>
 *               &lt;element name=   "DUNS_NBR" type="{http://www.w3.org/200 1/XMLSc    hema}string" minOccurs="0"/>
 *                          &lt;element name="ENQ_DUNS" type="{http://www .w3.org/20   01/XMLS     chema}stri      ng"       minOccur    s="0"/>
 *             &lt;element   name="OU   T_BUS_IND" type="{http://   www.w3.org/2001/XMLSchema}strin g" minOccurs="0"/>
 *         &lt;eleme nt name="PRIM_NME" type="{     ht     tp:/   /www.w3.    org/2001/XM      LSchema}string" minOccurs=      "0"  / >
 *         &lt;eleme   nt name="BR_IND" type=  "{http:// www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                     &lt;el   ement name="BUS_STRU" type="    {http://www.w3  .   org/2001/ XMLSch em      a}string" minOccurs="0"/>
 *         &lt;element name="LOCN_STAT" type="{ht   tp://w   ww.w3.org/2001/XMLSchema}string" minOccu  rs   ="0"/>
 *               &lt;element name="H  Q_NME" type="  {http://www.w3.org/2001/XM LSchema}string" minOcc  urs     ="0"/>
 *         &lt;element name="HQ_DUNS" type="{ht    tp://www.w3.org/2001/XMLSchema}stri   ng         " minOccurs  =  "0"/>
 *         &lt;elemen     t nam   e="HQ_CTRY_CD" type="{http://w ww.w3.org/2001/XMLSch      em   a}string" minOccurs="0"/     >
        *         &lt;element name="CTRY_CD" type="{http ://w   ww.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *               &lt;element name="PNT_NME" type  ="{htt  p://www.w3.org  /2001/XMLSchema}string " minOc      curs="0"/    >
    *           &lt;element name="PN        T_DUNS" type="{ht  tp://www.w3.org/2001/XMLSchema}string"  min   Occurs="0   "/>
 *         &lt;ele   ment   name    ="PNT_CTRY_      CD" type="{http://w ww.w3.org  /2001/X  MLSchema}strin  g" minOccurs="0"/>
 *           &lt;element name="DOM_ULT_PNT_NME" type="{http://www.w3.org/2001/XMLSchema  }  s  tring" minOccurs="0"/>
 *                   &lt;element  name="D  OM_ULT_PNT_DUNS" type="{http://www.w3.o      rg/2001/XMLS  chema}stri  ng" minOccurs="0"/>
 *         &lt;element name="DOM_UL    T_CTRY_CD" t     ype="{h       ttp://www.w3.org/2001/XMLSchema}string" minOccurs="0"            />
 *          &lt;element name="GBL_ULT_PNT_NME"   type="{http://www.w3.org/2001/XMLSchema}strin     g"       minOccurs="0"/>
    *         &lt;element name="GBL_ULT_PNT_DUNS" ty pe="{http://www.   w3.   org/2001   /X   MLS    chema}string   " minOccur       s="0"/  >
 *          &lt;   element name="GBL_ULT_   CTRY_CD" type="{http://www.   w3.org/2001/XMLSchema}string" min    Occurs="0"/>
 *         &lt;   /sequence>
 *     &lt;/restri    ction>
 *   &lt;/     com          plexC   onten  t>   
 * &lt   ;/complexT         ype>
 * </pr      e>
 * 
 * 
 */   
@Xml Acc     essor    Ty      pe(XmlA        ccessTy   p        e.FIELD)
@X      mlType(nam     e = "COR  PORA TE_L  I     NKAGE"  , p      ropOrder   = {
       "dunsnbr",
    "enqdun    s",
    "outbusi      nd",
             "primnme",
    "brind",
          "busstru",
    "locnstat",
    "hq nme",
    "hqduns",
          "    hqctrycd",
    "ct               rycd",
        "pntnme",
    "pntduns",
    "pntctrycd   ",
    "domultpntnme",
    "domultpntduns",
    "domu ltctrycd      ",     
    "gblultpntnme",
       "gblul     tpntduns",
    "gblultctrycd"
})
    publi  c class CORPORA   TELINKAGE {

    @XmlElementRef(name = "D   UNS_NBR", type = JAXBEl    eme  nt.class)
      pr    otected JAXBElement<Stri    n   g> dunsnbr;
    @XmlElementRef(na    me = "ENQ_DUNS    ", typ   e =   JAX    BElemen    t.class    )
    protected JAXBElement<Stri  ng> enqduns;
    @Xm  lElementRef   (name = "OU    T_BUS_I   ND",   type = JA   XB   Eleme        n t.class)
    prote  cte d JAXBEl  ement<Strin      g> outb     usind;
    @XmlEl   ementRef(  name = "PRIM_       NME", type = JAXBElement.class)
      protected      JAXB    Ele   ment<String  > primnme;
    @  XmlElem   entRef(name = "BR_IND",   type = JA   XBElem  ent.cla ss)
    protected JAXBElement<String> b   rind;
         @XmlElementR ef(name = "BUS_STRU", type = JAXBEleme   nt.class)
    protected JAXBE lement<String>    busstru;
           @XmlEle me  ntRef(name = "LOCN_S      TAT", type = JAXBElement.c       l  ass)
    protected JAXBElement<String> locnstat;
        @  XmlEleme      ntRef(name = "HQ_NME", type    = JAXBElement.class   )
              protected JAXBE    lement<String> hqnme;
    @XmlElementRef(name = "H    Q_DUNS", type = JAXBElement.class)
    protected JAXBE   lement<Stri ng> hqduns;
    @  XmlElementRef(name = "HQ_CTRY   _CD", typ      e = J   AXBElement.class)
       protected JAXBElement<String     > hqctrycd;     
    @X  mlEle  mentRef(name = "CT RY_CD",         type = JAXB  Element.class)
    protected J    AXBElement<String> c  trycd;   
    @      XmlElementRef(nam  e      = "PNT_NME",      type = JAXBEl  ement.clas s)
    prot   ected JAXBElement<String> pntnme;
                @XmlElementRef   (name =    "P   NT_DUNS", type = J    AXBEl  ement.c   lass)
    protected JAXBElement<String> pntduns;
    @XmlElementRef(name = "PNT_CTRY_CD   ",   type = JAXBE    lement.clas   s)
    prot     ected JAXBElement<String> pntct    rycd;    
     @XmlElementRef(name   = "DO   M_ULT_PNT_NME", ty   pe = JAXBE  lement.cl    ass)
    pr  o     tected   JAXBE           lement<String> dom    ul     tpntnme;
    @XmlEle      mentR      ef(name    =     "DOM_ULT_PNT_DUNS   ", t   ype = JAXBE    lement .class)
            p   rotected JAXBElem               ent<Str  ing> domul   tpnt         du   ns;
    @XmlElementRef(na  me = "DOM_    ULT_     CTRY_CD", type = J            AXBElement.  class)
           prot ec                t  ed JAXBEl           e  ment<String   >            domul t    ctrycd;
    @XmlEleme    ntRef(name = "GB  L        _ULT       _PNT_NME", type =   JAXB  Element.c   lass)
              pr   ot  e       cted J    AXBElement<St   ring > gblult  pntnme;
    @X    mlElementRef(name = "GBL_ULT   _PNT     _DUNS", type = JAXBElement.class)
    protected JAXB      El emen  t< String> gblultpntdu    ns;
    @XmlElem    e ntRef(name = "  GBL_ULT_   CTRY    _CD", type  = JA  X      BElement   .class)
      protected JAXBEle ment<String> gblultctrycd      ;

    /**
        * Gets the value o      f   the duns    nbr property.
     * 
     * @r eturn
                              *     pos   sible object             is
     *              { @link JAXBElement }   {@cod   e <}    {     @li  n   k String }{@  code              >}
                *              
                  */
    public JAXBElement  <Stri        ng> getDUNSNBR(  ) {
              retur  n dun   snbr  ;
    }

    /**
       * Sets the value of the dunsnbr prop  erty .
        * 
          * @param        value
           *      allowed object is
            *       {@link JAXBElement }    {@code <}{@link String   }{  @     c    o         de >}
               *      
       */    
    public v       oi    d setD                    U          NSNBR(  JAXBElement<      St      ring> v   alue)     {
             this.dunsnbr =     (      (JAXBElement<String>   ) value);   
          }

      /**
     *   G    ets the value of t   he enqd  uns pr   ope rt    y.
            *   
                   * @re t      urn
     *         po   ss       ible object is
               *         {@link JA  XBElement }{@c ode <   }{@link      String }{@co       de    >}
                 *                
           */
    public      JAX   B    Element<String > getENQDUNS() {
                            return enqdu   ns;
          }

    /        *  *
          *      Sets th e value o    f the     enqd     uns   property.
        * 
         * @param value
     *      allowed object         is
     *         {@l    ink J A  XB El   em  ent }{@code <  }{@link St      ring }{@code >}
     *      
       */   
    publi     c void setE          NQDU    NS(JAXBElem       ent<Stri  ng>    value) {
          th  is.enqd     uns =   ((JAXBEl        emen t<Strin                    g>     ) val  ue);     
    }    

     /     **
     * Gets    the        value   of        t        h                    e ou t           bus ind proper    ty     .
     *     
                * @return
     *     possible object     is
     *         {@l     ink               JAXB  Elem    en      t }{@co de <}{         @link Stri     ng }{@c   ode >}
     *              
              */
    p    ublic JAXBEle       ment<St    ri  ng>    get        OUTBUSIND() {
        retur  n outbusind;
        }

    /**
     * Sets   t     he         va    lue of the ou  tbu   sind propert     y.
                  * 
     *         @  param value
     *               a    llowed        object is
       *     {@link     JAX    BEl  ement }{@code <}{@lin         k Str    ing  }{@c    o de >}
            *       
     */      
             pu    bl    ic         void se        tOUTBUSI   ND(     JAXBElement<Stri      ng  > value) {
                      this.outbusi nd = ((JAXBElement<String> ) v  alue   );
             }

    /**
         * Gets the               valu     e of th     e p    rimnme p    rope   rty                       .
           * 
            * @re       turn
     *     poss   i   ble object is
          *     {@   l ink JAXBElem    ent }{@    co    de <}{ @link  String       }{@co   de >}
     *       
                                 */
     public      JAXBElemen     t<String> getPRI           MNME() {
                                 ret  urn      primnme;
             }
      
      /   **
       * S      ets the value of  the     primnme property    .
       *       
     * @param value
     *                     allowed objec   t            is
         *                  {@   lin  k JA     XBElement }{@c  o    de <     }{@l   ink String      }{@code     >}
     *     
         */
          public       void se               tPRIMNME( JAXBElement<St    ring>       val      ue      )  {
                 this.pr  imnme = ((JAX  B   Elem    ent<    St   ring> ) value);
    }

    /**     
      * Gets the value of the b  rind prop    ert             y.    
     *  
          * @ return
        *     possible o   bje     c   t is
     *     {@l    ink JAX  BElemen       t }  {@code <  }{@    link     S      t   ring }   {@code >        }
          *      
          *     /
           public JAXBElement<String> getB  R          IND () {
                retu  rn brind       ;
    }

          /*      *
      * Sets the  value of the b    rind pr  o       per  ty.
      *   
           * @param value          
     *       a  llowed obje     c t is   
      *            {@lin  k J         AXBEl           ement }{@code     <}{@       link String }{@co      de >}
     *         
              */
          public void    setBRIN   D(J  AX          BElemen  t<String  > valu        e) {
                  this.br i     nd =       ((JAX BElem   e      nt   <          S t      ring> ) value     );
      }

    /**    
       * Gets   the value of the          busstru property.
     * 
                  *           @   return     
         *       possible      object i        s      
     *       {@link JAXBElemen   t }{@code <}{@link Str     ing }{@code >} 
     *                            
        */
       publi c     JAXBE     lem    ent<Str i    ng> getBU SSTRU()   {
                 re  tur  n   busst ru;
    }

    /**
            * Sets the value of the buss   tru pro   pert                   y.
         * 
        * @   par  am valu  e
        *                           allowed ob    ject is
       *        {@link JAXBElement }           {@code < }{@link Strin g }{@co       de    >}
         *                            
     *  /
          public   void    setBUSSTRU(JAXBElemen   t<S   tr   ing>          va   lue) {
        this.b usstru = ((JA   XBElemen    t<Str         ing> ) value      );
     }  

    /**
                              * Get                  s the    val ue of the lo        cnstat property.     
            * 
           *            @return
     *     p   ossi  ble        obj   e  ct is
          *     {@link JAXBElement } {@     co   de        <}{@link St   r         i  ng }   {@c    od   e >}
         *     
                       */
           public   JAXBE    lement  <String> getL OCNST  AT( ) {
        return  l  o            cnsta  t;
         }
  
    /**
           * Sets t   he value of the locnstat pr   operty.
     * 
     * @para  m value
       *            allowe    d   object       is
              *          {   @li  nk JAXBElem       ent }{@cod    e <         }   {@link String }{@code   >}
        *     
       *   /
    public void set        LOCNSTAT(JAXB Element<String> va lue) {
              this.locnsta   t =     ((JA   XBElement<String>                   ) value);
          }

     /**
      *      Gets the   valu e of    the hqnme pr   operty.
               *      
                      * @r      etur  n        
     *            po  ssi      bl   e obje   ct   is
                  *     {@link JAXBElement }     {@    co  de  <}{@link String }{@code >}
                   *                 
       */
    pub lic JAXBElement<Stri   ng> getHQNM E() { 
                  ret    urn    h   qnme   ;
      }

          /**
         * Sets th         e value      of    the     hq      nm     e   prop  erty.
          * 
              * @pa   ram   value
     *        a   llowed obje   ct is
     *     {@l     in     k         JAXBE         lement }    {@c ode <}        {@link          String }{@co  de >}
     *     
     */
       public vo           id    setHQ  NME(  JAXBElement<         Stri              ng  > value) {
               t   hi    s.hq    nme =                ((JAX   BEl    ement<Strin   g>      ) va     lue);
     }

        /**
     *     Gets the    valu  e of   the             h    qd  un      s prop                         erty.
                       * 
     *    @return
       *     poss   ible ob  jec    t  is
        *         {@link       JAXBE      lem ent }{@code <      }{@l   ink St       ring   }{@code   >}          
            *     
     */     
    p   ublic        JAXBEl    ement<  St r    ing   > getH   QDUNS()        {
        ret        urn  hq            duns;
       }

       /**   
            * Sets the val   ue        of the hqdu    ns prope  r     ty.
     * 
            *               @param val ue
       *     al    lowed ob je  ct is
     *     {@link JA X     B    El   ement }{  @code <}{@link St         ring }{@     c             ode > }
     *         
         */
    public    vo    id setHQ  DUNS  (JAXBElemen  t<String> v            alu e  ) {
        this.hqduns = ((JA      XBElement<Stri    ng>  ) value);
      }

             /     **    
     * Get  s  the value of t  he            hqc    try             cd prop       erty.  
                       * 
       * @return
            *     pos   sible object          is    
       *     {@lin                  k      JA     XBEle           m ent }{@co  de <}{@lin    k Str      ing }{@code      >}
     *     
             */
    p    ubli       c JAXBElement<Str  in   g>   getHQCTRYCD   ()   {
           return hqct    ryc  d;
                  }

    /*          *
     * Set  s th    e value of the hqctrycd property.
        *        
                * @pa  r   am   val  ue     
     *     all  o   wed    object i                    s
             *            {@    lin k JAXBElement }{@code     <}{@link St           ring         }{@    co          de >}
       *                        
     */
         p     ubl           ic void  setHQ          CTRYCD       (JA      XBE       lement<String>     va  l       ue) {
             this  .hqctry      cd = ((JA    XBE  lement    <String> )   va     lue);
    }

    /*  *
     * G  ets the value of  the ctry  cd property.
     * 
     * @ret        urn
          *     possible obj    ect is   
        *                 {@link JAXBE     lem      ent }{@code <}{@link Strin g }   {@code >}
     *       
              */
                     public J       AXBEleme       nt<String> getCTRYCD() {     
                          retur  n c   trycd      ;
    }   

    /     **   
     * Sets th     e value of           the ctrycd prope    rty.
     *        
     *   @  p          aram v  alue    
     *     al  lo wed object is
        *            {@l     i   nk JAXBEleme          nt    }     {@code <}{@link String }  {@   code >}
       *     
            */
    public v        oi     d setCTRYCD(J   AXBEl   em    en t<Stri    ng> val ue) {
        th    i       s.ctr ycd = ((J     AXBEl  ement<Str  in   g> ) val   u               e);
    }

    /**
                * Gets the    value  o              f   the    pn    tnme         prope              rty.
      *   
       * @re    tur  n
      *            possibl    e object is
     *     {@link JAXBEle    ment }{@cod  e <}{@lin   k Str  in  g        }{@code >}
     *          
      *      /
           public JAXBElement  <String> get  PNTNME() {
                    retur   n pntnme;
    }
    
      /**
      * Sets the valu  e of the     pntnme property.
         *       
       * @param value 
       *      allowed ob   ject      is
              *        {@link JAXBEleme       nt }{@co       de <}{       @link S   t     ring }  {@code >}
       *          
     */
    pub        li   c void setPNTNME (JAX    BElement<Str        ing> valu  e)            {
           this.pntn   me = ((JAXBEl   ement<String> ) value);
    }

    /**
        * Gets the value of the pntduns      prop     erty.
                 * 
         * @  retu rn
      *     possibl   e obje    ct is
                  *         {      @     link J   AXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElem    ent<String>   getPN        TDUNS() {
           return       pnt    duns;
    }

    /**
       * Sets the va      lue of the pntduns property.  
     *       
     * @param va  lue
      *       allowed obj  ect is
     *        {@link  J   A    XBElement }{ @                      code <}{@link St     rin  g }{@code                   >}
         *       
     */
     publ    ic void setPNTDUNS(JAXBElement    <Stri           ng> val ue) {
                      this.pntdun     s =    ((JAXBElement<Strin      g> ) v       alue);
    }

    /**
     * Gets the va      lue of the p ntctrycd    prope         rty.
         *     
     * @retur       n
         *     possibl            e      object is
       *     {@link JAXBElement }{@cod   e <}{@   link S     tring }{@code >}
     *       
        */
            public         JAXBElem     ent<Str   ing> getPNTCTRYC    D() {
             return pntctry  cd;
    }

    /**
       *     S    e   ts the value of the pntctrycd property.
     *     
        *        @p  a    ram val ue
        *     allowe d objec   t is
     *              {@link JAX   BElement }{@code <}{@link Stri     ng }     {@code >}
     *     
     */
        pu      blic      void setPNTC  TRYCD(JAXB    Elem             ent<String> value) {
        this.pn    t  ctrycd     = ((JAXBElement<String> ) value);
    }

            /**
     * Gets the va                lue of  the domu l    tpntnme pro pe   rty.
     * 
     * @return
     *       poss  ible ob     ject is
     *       {@link       JAXBElement }{@code <}{@link Strin     g }{  @code >}
       *        
     */
    pu   bli       c JAXBEle   men t<St       ring> getDOMULTPNTNME() {
        return domult  pntnme;
       }

           /**
         * Se   ts the value of t        he d    omultpn  tnme property.    
     * 
     * @p   aram   value
     *     allowed object is
     *        {@link JA     XBElement   }{ @    cod e <}{@li   nk   String } {@code >}
     *             
     */
    p     ublic void setDOMU L TPNT     NME(    JAXB    El   ement<S tring> va  lue)   {
           t  his.domultpntnme =  ((JAXBEl    ement<String> ) value);
        }

      /**
     * Ge         ts the value of the domu  ltpntduns pr  operty.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@lin        k String }{@code >}
     *     
           */ 
          pu blic JAXB       Element<String   > getDOMULT PNTDUNS()    {
           return domult   pnt duns  ;
    }  

    /**
     * Sets the value of the domultpntdu    ns p    roperty.
     *           
          * @param value
     *     allowed object     is
     *     {@link JAXBElem    ent }{@code <}{@link    Stri      ng }{@code >}
     *          
     */
    public void setDOMULTP  NTDUNS(JAXBEl     ement<String> value) {
        this   .domul   tpntduns = ((JAXBElement<String> ) va  lue);
    }

       /**  
     * Gets the value of the domu   ltc  trycd      p roperty.
     * 
     * @return
     *       possible object is
     *        {@link JAXBElement }{@code   <}{@link String }        {@code >}
        *     
           */
    publ       ic JAXBE    lement<String> getDOMULTCTRYCD    () {
        return domultctrycd;
     }

    /**
     * Sets the value of the domultctrycd property.  
     * 
     * @param value
     *     allowed object is
     *          {@link JAXBElement }{ @code <}{@link      String }{@code >}
     *     
     */
    public void setDOMULTCTRYCD(JAXBElement<String> value) {
        this.   domu    ltctrycd = ((JAXBElement<String> ) value);
        }

    /**
     * Gets    the value of the gblu  ltpntnme property.
     * 
     * @return
      *     possible object is
       *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getGBLU   LTPNTNME() {
        return gblultpntnme;
    }

    /**
     * Sets t      h    e value o   f the gblultpntnme p   ro  perty.  
     * 
     * @param value
     *     allowed obje     ct is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *       
      */
    public    void setGBLULTPNTNME(JAXBElement<   String> value) {
        this.gblultpntnme    = ((JAX BElement<String> ) value);
    }

      /**
     * Gets the value of the gblultpntduns property.
     * 
           * @return
     *     possible obj  ect is     
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */   
    public JAXBElement<String> getGBLULTPNTDUNS() {
        return gblultpntduns;
    }

    /**
     * Sets the value of the gblultpntduns property.
     * 
     * @param value
     *     allowed object is
       *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *         
     */
    public void setGBLULTPNTDUNS(JAXBElement<String> value) {
        this.gblultpntduns = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the  value of the gblultctrycd property.
     * 
     * @return
       *     possible ob     ject is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *        
     */
    public JAXBElement<String> getGBLULTCTRYCD() {
        return gblultctrycd;
    }

    /**
     * Sets the value of the gblultctrycd property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAX BElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setGBLULTCTRYCD(JAXBElement<String> value) {
        this.gblultctrycd = ((JAXBElement<String> ) value);
    }

}
