
package com.transportation.SIRI_IL.SOAP;

import  javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import  javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 *  Abstrac t Servic e Reque            s t for  SIRI      Se   rvice req  uest
 *         
   *    <p>Java c lass for AbstractServiceR  equestStructure com    plex type.
 *  
  * <p>The fol      lowing schema fragment spe cifies t    he expected con   tent co     ntained w ithin this class.
 * 
 * <pre>
 * &lt;complexType name="A bstractServiceRequestStructure">
 *            &lt;complexCont      ent>
 *          &lt;extension base="{http://www.s   iri.org   .uk/siri}Abstra ctRequest   Structu  re">
 *       &lt;sequence>
 *                     &lt;group ref="{http://www .siri.org.uk/siri}Con         tex   tualisedRequestEn      d  pointGroup"/>
 *       &lt;/sequence>
 *     &     lt;/extension   >
 *   &lt;/complexContent>
  * &l  t;/complexType>
 * </pre>
 * 
      * 
 */   
@XmlAc   cessorType(XmlAccessType.FIELD)
@XmlType(name = "Abst    ractService   RequestStructure",     pro     pO rder  = {
    "messageIdentifier"
})
@XmlSeeAlso({
    ServiceCapabilitie  sRequestStructure.clas   s,
        Abstrac tFunc tionalServiceRequestStructure.class
})
  public abstract cl   ass      AbstractServiceRequestStructure
     extends AbstractRequ       estStructure
{

    @X  mlEle  ment(n    ame =      "     Mes   sageIdentifie   r")
        pro tected Mess         ageQu      alifierStructure mes  sageIdentifier;

    /**
                              * Gets th     e value  of the messageIde     ntifier   proper     ty.
                      *   
     * @return
             *      p          o ssib   le object is
          *     {@link Messa   g   eQualifierStructure }
     *               
     */
      public Message            Qua     lif        ierS t   ructure         ge  tMessageId  entifier() {
        return          messageIdentif  ier;
               }

    /     **
     * Sets t he value of t    he mes  sageIde   ntifier property.
           * 
     * @param value        
     *     allowed object is
     *     {@link MessageQualifierStructure     }
       *     
     */
    public voi    d setMessageIdentifier(MessageQualifierStructure value) {
             this.messageIdentifier = value;
    }

}
