
package DBGDPV3;

import    javax.xml.bind.JAXBElement;
imp  ort javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
i mport javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/         **
 * <p>Java class for DATARS4 co   mplex type.
  * 
 *   <p>The foll       owing schema f ragment specifies the expected    cont   ent cont  ained within this      class.
    * 
 * <pre>
 * &lt;complexTyp      e name="DATAR      S4">
         *   &lt;comple     xContent>
 *     &lt;restriction base="{http://www.w3.org/2001/       XMLSc   hema}anyType">
     *         &lt;  sequence>     
  *         &lt;element name="C       ACHE_IND"   ty pe="{http: //www.w3.org/200   1/XMLSchema}string" minOccu    rs="0"/>
 *                   &     lt;element name="PRD_DT"         type="{http://www.w3 .org/2001/XMLSchema}string" minOccurs="0"/   >
 *         &lt;element na    me="S   RVRTI    D" type=   "{http://www.            w3.org/2001/XMLSchema}string" minOccurs="0"/>  
 *           &lt;element n  ame="R PT" type  ="{http://    www.dnb.com/DNB_WebServices/P roviders        /OrderAnd  Investigations/G DP_V3/wsp_GDP_V3}RPT" minOccurs="0"/>
 *         &lt;    element nam e="CUST_INP_DATA" type="{http://www.dnb.com  /DNB_WebServices/Providers/OrderAndInvestigations/GDP_V3/wsp_GDP_V3}CUST_INP_DATA" minOcc  urs="0"/>
 *         &lt;element name="UPD_FLDS" type="{http://www.dnb.com/DNB_WebS  ervices/Providers  /OrderAndI      nvestigations/GDP_V3/wsp_GDP_V3}UPD_FLDS" minOc  curs="0"/>
 *                &lt;eleme   nt name="KEY_FLDS" type="{http://www.dnb.com/DNB_   WebServices/Provi    ders/   OrderAndInvestigat ion      s/GDP_V3/wsp _   GDP_V3}KEY_FLDS" minOccur    s="0"/>
 *               &lt;/sequence>
 *     &lt;/restri   ction>
 *   &lt;/compl exCo  nten  t>
    * &   lt;/complexType>
 * </pre  >
 * 
  * 
 */
@XmlAcce   ssorType(Xm     lAccessType.FIELD)      
@XmlT    ype(nam        e = "DATARS4",             propOrder = {
    "cacheind" ,
              "prddt   ",
        "sr  vrtid          ",
    "rpt" ,
          "custinpdat    a",
     "updflds",
      "keyflds"
})    
public cla    ss DATARS4 {

          @XmlElementRef(name =     "CACH     E_     IND", type = JAXBElement.class)
      protected JAXBEleme n t<Stri   ng> cacheind;
            @XmlElementRef(name = "PRD_DT", type = J   AXBElement.class)
    prot ect  ed JAXBElement<String> prddt;
       @XmlElementRef(nam   e = "SRVRTID", type        = JAXBEl   ement.c   lass    )
    p  rotec    ted    JAXBElement<String>         srvrtid;
    @XmlElemen      t       Ref(name = "RPT", type =          JAXBElement.class)
           protected JAXBElement<RPT>        rpt;
       @XmlElementRef(    n  ame = "    CUST_  I    NP_DATA", t    ype = JAXBElem   ent.class)
              pr    otected JAXBElem      e      n         t<CUSTINPDATA> custinpdata;       
    @XmlElementRef(name =      "  UP D_FL    DS", ty        pe      = JAXBEl ement.class)
    protected JAXBElement<UPDF  LDS> updf     lds      ;
    @   XmlEl    em    entRef  (name    = "KEY_FL DS",     type = JAX    BElement     .cla    ss)
    p    rotec     ted JAXBEl    ement<KE         YFLD    S> keyflds;

                /**
           * Gets the     value o    f the cacheind prop e   rty.
        *  
     *   @re  turn
       *     possible object is
       *           {@lin   k JAX     BElement }{@cod   e <}     {@lin  k String }    {@code >}
     *               
                   */
                           pu bl  ic JAXBE          lem        en   t<St     ri     ng> g  etCACHEIND( ) {
        return cacheind;
    }

            /*    *
      *           Set   s    the         v  al     u   e of the cachei    nd p r ope      r ty.
     * 
       *   @param v  alue
     *     allowed obj   ec  t           i        s
     *        {@link JAXBElement }{@code <    }{@lin   k     Stri ng }{@code     >}
       *     
        */
         publi   c   void setCAC     HEIND   (JAXBElement<String      > value) {   
               this.c        ac    heind = (      (JAXBElement   <String> ) value                       );
         }

    /* * 
      * Gets    the value of the p   rddt    property .
     * 
      * @        r    e   turn
                     *               pos  s    i  ble   object       is
     *     {@li  nk   JAX     BElement }{@code <}   {    @l    i nk String }{    @cod  e >}
              *      
          */     
    public    JAX     B     E   lement<String> getPRDDT() {
                           re turn prddt;
    }

            /**  
                   * Sets the      value         of the prddt prop   erty.
     * 
      *    @    para    m valu e
          *        all    owed ob     ject is
     *            {@link J AXBElement }{@cod     e   <}   {@  li    nk String }{@c            ode >}
     *     
     */
    pu  blic void setPRDDT    (JAXB Elemen t<Str  ing> value)  {
              this.prddt =     (        (JAXBE  l     ement<String> ) val  ue);
       }
  
      /*  *
         * Gets th      e value of t   he sr    vrt     id propert   y.
     * 
        * @ret    urn
        *         possi  ble ob  je         c  t                   is
     *             {@link JAXBEleme      nt }{@code <}{            @l    ink String }{@cod      e >}        
     *         
        */
      publ         ic JAXBEle  me         nt<String> g  etSRVRTID() {
                                     retur n srvrtid;
    }

             /**
                    *   Sets the valu    e of t       he      srvrtid   property.
         *  
              * @param va      l           ue
     *     allowe     d object     is     
        *     {@link    JAX    BElement  }    {@     c  ode <}        {    @link       String }   {@code >} 
        *     
             */
        public        void se         tS      RVRTI D(    JA XBEl   ement<String>    value) {
                                 thi      s.  srvrtid = ((JAX  B  El   em    ent<String>       )       valu  e)     ;    
        }

    /*   *
     * Gets the value of th           e     rp   t    pro    perty.
     *           
     * @return
      *     po        s sible obj         e   ct i    s
      *                 {@li   nk JAXBEl  ement    }{@code <}        {@l   ink RPT }{    @cod           e >}
     *        
      */
       public JAXBEleme      nt<RPT> getRPT(      ) {
                 r  etu         rn rpt  ;
      }

    /**
     *       Sets the value of       the rp   t  p  rop         erty.
     * 
     * @    param    valu      e
                   *     all   owed objec  t  is
     *     {@  link JAX   BElement  }{@code          <}{@l  ink RPT    }{               @code >      }
     *     
     *    /
    publ   ic    void setRPT   (JAXBElement     <     RPT> value) {
        th        is.rpt = ((JAXBEl    ement<RPT> ) value);
            }

      /**
        *  Gets th  e  valu             e of th e        custin    pdata property.
     * 
     * @   return  
         *       p   ossible object is
            *          {@link JAXBElement }{@code           <}{@l       ink C  USTINPDATA }{@code >}
     *     
                */
    public JAXBEl      ement<CUSTINPDATA>            getCUSTINPDATA() {
        return custin        pdata;    
    }
                 
    /**
      * Sets the value of the custinpdat   a proper  ty.
         * 
     * @param    value
                *     al  lowe   d         object is    
        *       {@link J  AXBElement }{   @code    <}{@l         ink C     USTINPD    ATA }{ @code  >}
        *         
     */
    pu    blic void setCUSTINPDA     TA(JAXB       Element<CUSTINPDATA> value  ) {
          this.custinpdata = ((      JAXB    Element           <CUSTINPDATA> ) val    ue);
    }

        /**
                * Gets the value of     the updflds property   .
      * 
     * @r   eturn
        *     possible object is
         *     {     @li       nk JAXBElement }{@co   de <}         {@link UPDFLDS }{@code >}
        *     
     */
    p  ub   lic JAX   BEle          men     t<UPDF       LDS> getUPDFLDS() {
          return updflds;
       }

    /**
     * Sets    t           he value of t   h        e updflds property.        
     *        
     * @param    va    lue
     *     allowed object is
     *      {@link JAXBElement  }{@code <}{@link UPDFLDS }{@code >}
     *     
     *     /
    public void setUPDFL  DS(JAXBElement<UPDFLDS> value) {
        this.updflds  = ((JAXBElement<UPDFLDS> ) va lue);
                }

    /**
     * Gets the value o     f the keyf   lds property.
     * 
     * @return
     *     possib   le object is
     *     {@link JAXBElement }{@code <}{@link KEYFLDS }{@code >}
     *     
        */
    public    JAXBElement<KEYFLDS> getKEYFLDS() {
        return keyflds;
    }

    /**
     * Sets the value of the keyflds property.
     *     
     * @param value
     *     allowed obj   ect is
     *       {@link JAXBElement }{@code <}{@link KEYFLDS }{@code >}
     *     
     */
    public void setKEYFLDS(JAXBElement<KEYFLDS> value) {
        this.keyflds = ((JAXBElement<KEYFLDS> ) value);
    }

}
