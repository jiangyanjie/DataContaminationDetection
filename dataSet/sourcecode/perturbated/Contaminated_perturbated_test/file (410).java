
package   DBGDPV3;

impo    rt javax.xml.bind.JAXBElement;
import       javax.xml.bind.annotation.XmlAccessType  ;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
       

/**
    *     <p>Java class for DATATRN      RS4 complex type.   
 *      
 * <p>The     following     schema fragmen t specifies the e xpec   ted conte  nt contained with     in this class.
 * 
 * <pre>
 * &lt;complexType name="DATATRNRS     4">
 *     &lt;complexCon  tent>
 *     &lt;restriction base  ="{http://www.w    3.org      /20           01/XML    Schema}anyType">
 *                &lt;sequence   >
 *            &lt;element name="STATUS" type="{http://www.dnb.com/DNB_WebServices   /   Providers/Ord        erAndInve   stigat   ions/GDP_V3/wsp_GDP_V3}STAT US8" minOccurs="0"/>
 *         &lt;element name="DATAR    S" type="{http:/ /www.dnb.com/DNB_WebServices/Providers/OrderAndInvestigations/    GDP_V3/wsp_G  DP_V3}DATA  RS4" minOccurs="0"/>
 *       &lt;/sequen c e>
 *      &lt     ;/restric   tion>
 *   &lt;/complexContent>
 * &lt  ;/comple  xType>
 * </pre>
 * 
 * 
 */
@XmlAcc      essorType(XmlAccess       Ty pe.F      IELD)
@XmlTyp e(name        = "DATATRN     RS4    ",    propOrder   = {
     "status",
    "datars"
}    )
pu  blic class DATATRNRS4 {

            @XmlElementRef(n  ame = "STATUS", type =      JAXBElement.class)
          p rot      ected J   AXBEl   ement<STATU S8> sta   tus;
           @XmlE   le  mentRef      (na     me = "DATARS      ", type =            JAXBElement   .c lass)
          protected J    AXBElement<DATA  RS4> datars;

    /**
              *       Gets th e valu   e          o    f the status property.             
     * 
      * @ret   urn   
            *     possi     ble object is  
        *     {    @ l      i   nk JAXBEl           ement         }{@ code <}{@           link STATUS8 }          {@code >}       
     *         
         *         /
                public JAXBElement<ST    ATUS8> ge    tSTATU   S() {
        return statu   s;
       }

    /**
     *         Sets        the va    lue    of the stat us property.
     * 
       *     @param value
         *         all       ow   ed object is
        *     {   @link JAXBElem ent }{@code <}{@lin    k STAT      US8 }{@co     de >}
        *     
                       */ 
     p         ublic vo                       i        d setSTATUS(  JAXBElemen     t<STATUS8> value)  {
                   t  his.  sta  t   us =        ((JAX BElement    <STA  TUS8> )      value    );
            }

    /*  *
     * Gets th e         value of      th e datars  property.
     * 
     * @return
     *          possible    object is
        *         {@link J      AXBElement }{    @code <}{@link D  ATARS4 }{@c    ode >}
     *                    
          */
    public JAXBElemen    t<DATAR   S  4> getDATARS() {
        return datars;
    }

    /**
     * Sets the value of the datars pro   perty.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement } {@code <}{@link DATARS4 }  {@code >}
     *       
     */
    public void setDATARS(JAXBElement<DATARS4> value) {
        this.datars = ((JAXBEl ement<DATARS4> ) value);
    }

}
