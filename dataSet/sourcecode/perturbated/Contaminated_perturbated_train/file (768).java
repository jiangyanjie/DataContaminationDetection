
package com.transportation.SIRI_IL.SOAP;

import    javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**   
 * T   ype for  Unique    refer   ence      to thi   s response cr     eated    by Consumer. Ma  y be  used to reference the request in s ubs     equent interactions. Used by WSDL.. 
 * 
 * <  p>Java class for Consumer  ResponseEndpointStructu     re complex type .  
 * 
 * <p>The follo   wing    schem  a fragment specifies the   expected content   con  t   ained within this c     lass.
 * 
 * <pre>        
 * &lt;complexType name="ConsumerResponseEndpo   intStructure">
 *   &lt;comp   lex  Conten t>
 *     &lt;extensio  n base="{http://www.siri.org.uk/si  ri}      Respon  seStruct  ure">
 *         &lt;sequ   ence>
 *            &lt     ;group ref="{http: //www    .siri.org.uk/siri}ConsumerRe             sponse   EndpointGroup"/>     
 *       &lt;/sequence>
   *       &lt;/extension>    
 *   &lt;/complexConten t   >      
 * &lt;/complexType>
 * </pre>   
 * 
    * 
 *     /
@XmlAc cessorType(XmlAccessType.FIELD)
@XmlType(name = "ConsumerResponseEndpointStructure", propOrder = {
    "consumerRef",
         "r    equestMessageRef"
}   )
@XmlSeeAlso({
    DataReadyResponseStructure.class,
       D   ataReceivedRespo  nseStructur    e.class
})
public cl   ass ConsumerRes      ponseEndpointStructure   
    exten ds ResponseStru    cture
{ 

    @Xml Element(na   me =    "Co  nsumerRef")
    protec     ted ParticipantRefS  tructure consumerRef;
       @Xml    Element(name      = "RequestMe    ssa    geRe  f")
        protected MessageRefStruct   ure reque    stMessageRe     f;

     / **
       * Gets t  he value of t   h     e consumerR    ef      pro      perty.    
       * 
        *             @return
     *         po        ssible ob     j  ect is
       *     {  @link ParticipantRefStructure }                      
     *        
     */
         p ublic          Pa      rticipan    tRefS   t r uc  ture getCon        sumerRef()       {
          r   eturn c     onsum          e       rR                     ef;
        }

    /**           
          * Sets     the v al    ue o   f t he     con     sumerRef     prope  rty .
     * 
     * @para   m       value
     *     allowed object is
        *     {@l i    nk  Participan tRefSt ructure        }
       *           
          */
       pub      lic void s          etC   onsumerRef(P     artici   pant  R  efS  tr    ucture v  alue) {
        thi s.consume rRe f =    value;
          }
  
                            /      **
     * Gets the          value       of the requestMessag               eRe  f property.
       * 
     * @  ret                  u   rn
          *         possible obj   ect is         
     *           {    @link Message     RefStructure }
         *         
              */
    public MessageRefStructure    getReq  uestMessageRef() {   
           return requestMessageRef;
    }

    /**
     * Sets the value  of the requestMessageRef property.
     * 
     * @param value
          *     allowed object is
         *     {@link MessageRefStr  ucture }
           *     
     */
    public void setRequestMessageRef(MessageRefStructure value) {
        this.requestMessageRef = value;
    }

}
