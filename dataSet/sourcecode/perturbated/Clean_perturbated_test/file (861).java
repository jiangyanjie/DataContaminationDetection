//
//     This file     was generated         b y the JavaTM Architecture for XML Binding(JAXB) Reference Implementation,       v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.c om/xml/jaxb</a> 
// Any modifications t      o th  is  file will be     lost upon recompilation of t     he source schema. 
// Generated on:        2014    .10.30 at 12:18:2   6 PM  EDT 
//


package com.rsa.transarmor.dto;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
im   port javax.xml.bind.annotation.XmlElement;   
import javax.xml.bind.annotation  .XmlType;


/**
 * <p>Java class for CreditResp  onseD  etails   c    omple     x type.
 *  
   * <p>The following schema fragm ent     specifies the expected  conten   t contained within this class.
 *  
 * <pre>
 * &lt;complexType nam        e="CreditResponseDetails">
 *   &lt;complexContent>     
 *     &l t;res        triction  base="{http://www.w3.org/      2001/XMLSchema   }anyType">
 *             &lt;s    equence>
 *         &lt;element name="    Common    Grp" type="{com/firstdata/Merchant/gmfV3.1     0}C         ommonG r  p" mi   nOccurs="0"/>
 *             &lt;element name="BillPayGr   p" type="{com/firstdata/Merc ha  nt/gm fV3.10}BillPayGrp" minOc  curs="0"/>
      *            &lt;element name="CardGrp" type="{com/firstdata/  M    er   chant/gmfV3.10}CardGrp" minOccur      s="0"/    >
 *         &lt;element name="Ad  dtlAm  tGrp" type="{com/firstdata/M  erchant/gmf V3.    10}AddtlAmtGrp" maxOccurs    ="6" minOccurs=" 0   "/>
 *         &lt;element name="EMVGrp" t   ype="{com/fir   stdata/M  erchant/gmfV3.10}    EMVG    rp" minOccurs="0"/>
 *             &lt;element nam       e="TAGrp" type="{c om/firstdata/Merchant/gmfV3.10   }TAGrp     " minOccurs="0"/  >
        *         &l     t;element na     me="  OfferGrp    " type="{c om/firstdata/M         erchant/gmfV3.10}Of ferGrp"    minOccurs="0"/>
 *         &lt;element n   ame="SecrT   xnGrp" t ype="{ com/firstdata/Merchant/  gmfV3.1 0}SecrTxnGrp" minOccurs=  "0"/>
 *         &lt  ;choice>
 *              &lt;element  name=  "VisaGrp" type="{com/firstdat     a/Merchant/gmfV3.10}Visa   Grp" minO     c   curs="0"/>
 *                   &  lt   ;element      nam    e="MCGrp  " type="{com/firstdata/Merchant/gmfV3.10}MCGrp         " minOcc  urs="0"/>
     *              &lt;element name=     "  DSGrp" type="{com/f   irstdata/Merchant/gmfV3.10}DSGrp" minOccurs="0"      />
 *              &lt;ele m  ent name="AmexGrp"     ty    pe=  "{   com/firstdata/Me  rchant/gmfV3.10}AmexGrp" minOccurs="0"/>
 *         &lt;/choic    e>
    *              &    lt;element name="Cust InfoGrp" type="{com/firstda    ta/Merchant/gmfV3.10}CustInfoGrp" m      inOc   curs="0"/   >
 *          &lt;elem   ent na   me="Resp Grp" t  ype="   {com/f  irstdata/Merchant/gmfV3.10}RespGrp" minOccurs="0"  />
 *         &lt;element n   ame="O   ri     gAuthGrp" type="{co   m/firstdata/M erch    ant/gm  fV3.10}OrigAuthGrp" minOccurs="   0"/>
 *              &lt;eleme      nt na  me="File      DLGrp  " type="{com/firstdat      a/Merc    hant/g     mfV3.     10}Fil        eDLGrp"       minOc  c   urs="0"/>
 *       &lt;/sequen  ce>
 *     &l    t   ;/restriction>
    *   &lt;/complexContent>
 *       &lt;/  compl  exType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(na   me =       "C     reditResp  onseDet   ails", propOrder = {
    "comm      onGrp",
    "bill     PayGrp",
      "cardGrp  ",
          "addtlA     mtGrp",
     "emvGrp",
    "taGrp" ,       
            "offerGrp",
    "secrTxnGrp",
    "visaGr  p",
        "mcGrp",
           "dsGrp   "    ,
    "amexGrp",
    "cust I     nfoG     rp",
    " respG      rp",
    "origAuthGrp",
    "fi    le  DLGrp"
      })
public     class      CreditResponseDetails {

    @XmlElemen  t(name = "CommonGrp")
     protect  ed CommonGrp     com      monGrp;
    @XmlElement(name =   "BillPayGrp")
       prot     ected BillPayGrp b  ill   P     ayGrp;
    @XmlElement(n   ame     = "CardG    rp")
    protected CardGrp cardGrp  ;
              @Xm   lElement(name = "AddtlAmtGrp   ")
        p  rote  cted List<AddtlA     mt    Grp> addtlAmtGrp;       
    @X   mlElement(name = "EMVGrp")
    protecte  d EMVGrp emvGrp;
      @XmlElement(name = "T  AGr      p")
    protect  ed TAG    rp taGrp;
    @XmlElement(name        = "OfferGrp")
       prot  ected O       fferGrp offerGrp    ;
    @X  mlEl  ement(name =  "SecrTxnGr   p    ")
      protec  ted SecrTxnGrp        se    crTxn      Grp;
    @XmlElement          (name = "       Visa  Grp")
    protected    Vi sa Grp visaGr    p;
    @Xm       lElem   ent(name       =    "MCGrp" )
    protect   ed MCGrp mc  Grp;
      @XmlElement(name = "D           SGrp")
     protected DSGrp      dsGrp;
    @XmlElemen     t(name = "AmexGr p")
    pr   otecte   d       A    mex   Gr        p amexGrp;
           @  Xm       l    Ele      men         t(  name =    "C ustIn   f        oGrp"       )      
           protected   Cu stInfoGrp        custI      nfoGrp;
             @XmlEleme  nt(na        m    e   =    "  RespG   rp")
        p     r   otected R    e    spGrp r      espGrp;
           @    Xm  lElement(name = "Orig               AuthGrp")
      protect  ed OrigAuthGrp   origAu thGrp;   
           @XmlElement(n    ame = "F  ileDL            Grp")
    protected    FileDLGrp fileDLGrp;

    /**
     * Gets the v      alue      of t        he  com   monGr     p property.
     *  
        * @r  eturn
         *         po   ssible object is        
        *              {@link Co  mmo            nGr    p    }
     *        
     */
    pu     bl  ic     Comm      o  nG   r  p         get   Co        mmonGr   p() {
              retu    r    n co  mmonGrp;
        }

                     /**
       * Sets the    value              of the commonGrp    pr opert   y.
      * 
       *         @pa    r        am val               ue
        *     allowed object is
     *     {@link Commo   n    Grp }
     *     
        */
       pub    l   ic void set    C        ommonG    rp(Comm     onGrp valu    e) {
        this.comm  onGrp =  value   ;
      }

    /**
        *       Gets t   he value of           the b     i            llPayG        rp property.
     * 
        * @retur    n
     *      p  oss      ible object is
     *        {@link Bi    llPayG  rp }       
          *           
                  */
    pub  l ic         BillPa    yG          rp get    Bi   l         lPayGrp  () {   
             ret    urn billPa y  G  rp;     
          }

    /** 
        * Sets                     the  value of th  e billPayGrp       proper   ty.
         *    
     *    @param va   l ue
            *            allowed object is
           *     {@link Bil lPa    y   Grp    }
     *     
              */
      public void setBi  llP   ay   Grp(BillPayGrp              va       lue) {
                        this.bi                  llP  ayGrp =    val    u   e;
       }

                 /**
     *       Gets     t    h e    v     a     lue of the c  ard    G  rp p  ro     perty.
             * 
     * @return
       *                          p ossible object is
       *               {@link    CardGrp }
     *       
       */
    pu      blic    Car   d Grp g   etC    ar  dGrp() {   
             return  cardGrp;
          }

         /**
                         *     S   ets th  e   valu       e o     f   the ca     r    dGrp property.
     * 
                        * @param value
                *                 allowe     d   o  bject is   
     *                     {@lin     k CardG     rp }
                          *     
     */
                       public void setCa   rd    Grp(CardG         rp   value    ) {
          this.cardGrp = value;
    }

           /**
         *   Gets th      e value of the addtlAmtG        rp  property  .      
                * 
       * <p>
         * Th  is ac      cess      or method retu    rns  a       refer                ence  to the liv      e list,
     * no    t  a      sn  apshot.              T he        refo    re any modificatio n you ma           ke       to the
     * r         e   t       urned        lis  t will be present            i       nside the JAX  B object.
      * This is w  hy t    here is not a <COD     E      >      se       t</CO   DE>    method  fo  r the addtlAmt    Grp proper    t   y.
           * 
     * <p>
     * F    or example,   to add a new    item, do   as f     ol  l    ows   :
     *        <pr   e>
            *    getAddtlAmtGrp().add   (newI    tem);
                    *   </pre>
     * 
     * 
                       * <p>
                   *  Objec     ts of          the follow             ing type(s) are allowe  d in the li  s           t           
      * {@link Add  t      l   Am  tGrp   }
         * 
     * 
     */
            p    ublic  List<Addtl       Amt          G       rp> getAd   dtlAmtGrp() {
                   if   (addtlAmtGrp     ==         nul   l) {
                                              ad dtl      AmtGrp = new A rrayList< AddtlAmtGrp>();
            }
         return   this           .    addtlAmtGrp       ;
    }
   
    /** 
            *    Gets t                             he     v        al ue of the em                   vGrp p     r      operty.        
           *                     
                 * @     return  
     *         possible o   bject  i    s
     *     {@li   nk      EMVG    rp }  
     *     
                              */
       pub   lic     EMVG     rp getEM V   G r       p()   {    
           retu    rn e   mvGrp;
            }

    /**  
          * S   ets the value of t     he        emv     G        r     p   property.
         * 
     * @param value
     *     allowed o   b jec   t     is
         *     {@lin k EMVGrp      }
     *     
        */
     public void      setE   MVGrp(EMVGr      p va lue) {
        th  is.emvGrp = value          ;
         }

    /*     *
        * Gets        the  va           lue of th    e taGrp         p    rop       erty.
     * 
          * @return
     *     p   ossible object is           
       *          {@lin    k TAG   rp }
                *     
            */
          public TAGrp getTAGr      p   ( ) {
            retu    rn  taGrp;
     }

      /*  *         
       * Sets the v   alue of the taGrp pr   op    e    rt         y.
                * 
       *    @          p            ar am va     lue
     *           allowed   objec t is
                        *            {@      link   TAGrp   }
         *       
            *   /
       public void    setTAGrp(TAGrp   value) {
               this.taGrp = val u  e;
     }

       /**
          * Gets the value of the of             ferGrp   property.
           *   
         * @      r  eturn
             *                      possibl     e object is
     *     {@ link OfferG  r   p }
        *     
           */
           publi  c Of          fe    rGr p ge tO       ff        erGrp() {
               return offerGrp;
    }

    /*        *   
                 * Sets th   e   val   ue of       the offe r  Grp property.  
     * 
        * @p         aram val ue
                 *     al         lowed object is
     *     {@lin   k         OfferGrp       } 
     *                   
      */
    public void setOfferG   r  p(Off erGr p     va lue      )                    {
                          thi  s.offerGrp =   v alu  e  ;
        }
   
        /*  *
     *      Ge  ts   the va lue of th      e      s            e               crTxnGrp prop  erty.        
     *        
     * @retur   n
     *          po   ssible    objec  t is
           *                       {@link Sec    r     TxnG     rp }
     *                        
        */
            pub           lic SecrTxn     Gr          p get        S ecrT  xnG               rp() {
                          ret   urn secrTxnGrp   ;
             }
  
        /**
          * Set    s the    val ue of      t      he se    cr   Txn  Grp  pro pe rty.
     * 
            * @param va   lue
         *     allowed object is
     *     {@link S  e   crTxn   Grp }
                 *     
     */
     public voi   d setSe   cr                    TxnG   rp     (  Se    crTxnG rp valu  e) {
                 this.secrTx  nGr            p = va       lue ;
    }

    /**
     * Ge      ts   the value o     f th    e visaGrp property            .
         *                          
                 * @return  
       *        possi ble objec   t       is
         *     {@link VisaGrp }
               *                
     */
                 public     Vis  aGrp getVi     saGrp() {
                                 r     etur    n visaGrp;
      }
          
              /**
         * Sets    the   va      l     ue  of t        he vi   saGrp pro                per    ty.
            * 
            *   @p      aram value   
           *     allowed    object is
     *        {     @link VisaGrp }
        *     
     */
    public void  setVisaG rp(Visa  G      rp value) {
                  this.   visa    Grp =   va      lue;
    }
 
    /**
           * Get     s the v alue of the   mc        Grp pr       operty.   
     * 
     * @ret  urn
          *            possible objec  t is
                *      {@link MCGrp }
     *          
     */
        p       ubli    c MCGrp   get       MCG     rp() {
              return mcGrp;
          }

      / **
                   * Sets the                   value of   th    e mcGr    p prop ert            y.
             * 
     * @pa     r           am     valu   e
              *      allowe    d o bject    is
          *      {@link MCGrp }
     *           
     */
               public           void set  MCGrp(MCGrp value) {
                     thi   s.mcGrp = value;
    }

    /**
     * Gets the value of the dsGrp      proper  ty.
     *   
     * @r      et   urn
        *                possi ble object is
     *     {@    link DSGrp }
     *          
     */
     publ   ic DSGrp g    e   tD   SGrp    () {
         return dsGr   p;
    }
     
     /**
        * Sets    the value of the dsGrp  property.
     *     
     * @param value   
       *         all  owed  object i s
     *     {@link DSGrp }
             *     
     */
    p ublic v   oid setDS    Grp(DSGrp       value) {
        this.dsG    rp = value;  
        }

    /**
        *      Gets the value of the amexGrp pr operty.
         * 
       * @return
     *     possible object is
     *     {@l    i   nk Am   exGrp }
       *      
     */
    public AmexGrp get   Am   exGrp() {
                    retu rn amex    Grp;     
    }

    /     *      *
     * Sets the value of  the amexGrp p      roperty.
            *   
     * @param value
     *             allowe    d    object is
     *       {@link AmexG     rp }
     *       
     */
    p     ublic void setAm   exGrp(AmexGrp value) {
                this.ame     x  Grp = v   alue;
    }
  
    /**
     * Gets the value of t  he      custInfoGrp pr     operty.
     * 
     * @re  turn
     *     possible object is
     *     {@link     C   ustInfoGrp }  
       *        
     */
    public Cus    tIn     fo     Grp getCustInfoGrp() {
              return cust   InfoGr   p;
         }

    /**
     *         Sets the v     alue o   f the custInfoGrp property.
     * 
        *        @par    am value
     *                  allo  wed object is
     *     {@link CustInfoGrp }  
     *     
        * /
     publ ic void se  tCustInfoGrp(     CustInfoGrp value) {
        th   is.custInfoGrp =   value;
    }

    /**
     * Get     s the value of the respGrp pr   operty.
     * 
     *  @r           eturn
         *         possibl   e object     is
     *     {@link RespGrp }
     *     
     */
    p ubli   c RespG    r      p getRespG  rp() {
        return res   pG rp;
    }

    /**
     * Sets the value of the res pGrp    property.
     * 
     * @par      am value
     *     allowed object is
       *     {@link RespGrp }
     *     
     */
       public void setRespGrp(RespGrp value) {
        this.resp    Grp = value;
       }
   
     /     ** 
     * Gets th       e value of the     origAuthGrp property.
     * 
     * @return
     *     possible object is
     *       {@link OrigAuthGrp }
     *     
     */
    public  OrigAuthGrp get   O     rigAut   hGrp()    {
        return origAuthGrp;
    }

    /**
     * Sets the value of the origAuthGrp property.
     * 
      * @param value
     *     allowed object is
     *     {@link Ori  gAuthGrp }
     *     
     */
    public void setOrigAuthGrp(OrigAuthGrp value) {
        this.origAuthGrp = value  ;
    }

    /**
     * Gets the value of the file    DLGrp property.
             * 
     * @return
     *     possible object is
     *       {   @link FileDLGrp }
     *     
     */
    public FileDLGrp getFileDLGrp() {
        return fileDLGrp;
    }

    /**      
     * Sets the value of the fileDLGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link FileDLGrp }
        *     
     */
    public void setFileDLGrp(FileDLGrp value) {
        this.fileDLGrp = value;
    }

}
