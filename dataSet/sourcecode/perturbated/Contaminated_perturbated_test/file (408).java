
package DBGDPV3;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import  javax.xml.bind.annotation.XmlAccessorType;
impor   t javax.xml.bind.annotation.XmlElementRef;
impor t javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DATATRNRS2 co    mplex type.     
 * 
 *   <p>The      follow   ing   schema fragment specifies the  expect   ed co    ntent co   ntained w  ithin this class.
 * 
 * <pre>
 * &lt;c ompl   e  xTy  pe name="DATATRNRS2   ">
 *       &lt ;complexContent>
 *     &lt;restriction base="{   http    ://www.w3.org/2001   /XML    Schema}anyT    ype">
 *       &   lt;s    equence    >
 *         &lt;element name  ="TRNUID"   type="  {h       ttp:   //www.     w3  .org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="STATUS" type="{http://   w  ww.dnb.com/DNB_WebServi     ces/Pr    o viders/O rderAndIn vestigations/GDP_V3/w sp_GDP_V3}STATUS4" minOccurs="0"  />
 *         &lt;element name="DATARS" type="{http://w   ww.dnb.com/DNB_WebServices/Providers/OrderAndInvestigation   s/GDP_V3/wsp_ GDP_V3}    DATARS2"     minOccurs="0"/>
 *             &lt;/sequence>
 *     &lt;/    restriction>
 *        &lt;/complexContent>
  * &lt;/complexType>
 * </     p  re  >
 * 
 *   
 */
@XmlAccessorType(X   ml     Acc   essType.FIELD)
@XmlType(name = "DATA TRN     R   S2", propOrder = {
    "         trnuid",
    "status",
        "datar   s"
})
public  class DATATRNRS2 {

               @XmlElementRef(name = "TRNUID",    ty      pe      =     JAXBElement.class)
    prote ct  ed JAXBElement<String> trnuid;
         @   XmlE   lementRef(nam e = "STATU    S", type =  JAXBElement.class) 
    protec     ted JAXBElement<ST           ATUS4> s  tatus;
       @XmlEle  me         ntRef( name =   "    D  ATARS", typ     e = JAXBElement.cl  a  ss)
    p     rotected JAX  BElemen   t<D    ATARS2> data    rs;
    
    /**
            * Gets the value of the trnuid pr     operty.
         * 
        * @re           turn
         *     possi     ble object is
     *     {@link JAXBEle   ment                  }{@   code <}{@link Stri ng }{@code    >}
     *     
       */
        public JAXB  Ele  men   t<Stri                  ng> getTR                   N   U   ID() {
                   ret   ur n trnuid;
     }

    /*   *   
                 * Sets the valu    e of  the trnu         id propert      y.
     * 
                 * @p              aram value
     *                 allowed               object is
     *       {@l   ink JAXBElement }{@co  d e <}{@li nk   St    ring }{@cod      e >}
     *      
             * /
    public    vo id setTRNUI D    (JAX        BEl    ement   <Stri      ng> v    a    lue) {
             this.trnuid =        (    (JA       XB  Elemen  t<Strin  g> )    val ue);
    }

    /**
      * Get      s the valu    e o     f      th     e st   a    tus propert    y.
     * 
       * @return
              *     possi     ble o bject    is
     *        {@link JAXBElemen      t }{@code   <}{  @ link STATUS4   }      {@code    >  }   
       *        
         */
        public JAX   BElement<STATUS4> getSTA    TUS(   )                    {
                    return sta tus   ;
     }

    /**
      * Sets    the value   of          the status                   property.
     * 
                * @pa  ram      value
     *     allowed obj   ect  is
                 *      {@l     ink     JAXBElement   }{@code <}{@link S    TATUS4 }{@co      de >}
           *     
     */
    public void setSTATUS (     JAX     B         Element<STATUS4> value   ) {    
            this.        st  atus = ((JAXBEle   ment<STATUS4> ) valu   e)   ;
    }

    /**
     *      Gets t   he v     alue of the datars pro  perty.
     *           
         *    @return
         *     possible object is
      *          {@link J         AXBElemen   t }{@code <}   {@link DATARS2    }{@code   >}
     *     
     */
       public JAXBElement<DATARS2> ge   t     DATARS() {
        return     datars;
    }

    /**
     * Sets     the     valu    e of the datars p roperty.
          * 
     * @param value
     *     allow     ed object is
     *     {@link JAXBElement }{@code <}{@link DATARS2 }{@code >}
     *     
     */
    public void setDATARS(JAXBElement<DATARS2> value) {
        this.datars = ((JAXBElement<DATARS2> ) value);
    }

}
