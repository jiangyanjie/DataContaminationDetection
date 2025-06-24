
package com.transportation.SIRI_IL.SOAP;

import   javax.xml.bind.annotation.XmlAccessType;
import      javax.xml.bind.annotation.XmlAccessorType;
import      javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaTy    pe;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCa         lendar;


/**
 *  Type for G   ene      ral SIRI Request.
 * 
 * <p>Java class for AbstractRequestStructure complex ty pe.
 *        
 * <   p>The followin   g schema fragment specifies the   expected    content contained within this class.
 * 
 *    <pre>
 * &lt;complexType name="AbstractR e      questStructure">
    *    &lt;complexContent> 
 *        &        lt;restriction base="{ht   t    p://ww       w.w3.o                   rg/200 1/XMLSchema   }any   Type">
 *                 &lt;sequenc   e>
 *              &lt;element   ref="{http:/  /www .siri.org.uk   /s    ir     i}Request  Timestamp"/>
 *                &l  t;/sequ     ence>
 *     &lt ;/restr    iction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 *      </pre>
 * 
 * 
 */
@XmlAccessorType(X   mlAccessType.FIELD)
@Xml  Type(name = "AbstractRequestStruc  ture", propOrder = {
    "requestTimest  am  p"    
})
@X     mlSeeAlso({
    AbstractServiceRequestStru    cture.class,  
    ProducerReq  uestEndp  ointStructure.class,
    Co  ns umerRequestEndpointStructure.class  ,
       RequestStr   uctu   re.class
 })
p       ublic abstract class Abstract     RequestStructure {

    @XmlE    lemen     t(name = "R  eq       uestTim    es  tamp", required = true)
     @Xm        lSchem   a   Type(name =          "da  teTime         ")
    prot  ected XMLGregor         ianC          alen    da  r requ    estTimestamp;

      /**
     * Gets   the v   alue of the     r    equestTime            stamp prope  rty    .
            * 
     *   @ret       urn  
     *          possible o       bject  is
                *     {@link      XMLGrego       r  ianCal endar      }
                *        
     */
    pub  lic XMLGre    goria   nCal  endar getRequestT    imestamp()  {
             return r     equestTimestamp;
          }

    /**
     * Sets the value o     f   the    requestTimestamp property  .
     * 
        * @p aram value
     *          a      llowed object     is
     *          {@link XMLGregoria   nCalendar }
     *     
     */
    public void setRequestTimestamp(XMLG     regorianCalendar value) {
        this.requestTimestamp = value;
    }

}
