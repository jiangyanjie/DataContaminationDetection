
package   com.transportation.SIRI_IL.SOAP;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
impo    rt javax.xml.bind.annotation.XmlElement;
       import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import   javax.xml.bind.annotation.XmlType;
impo rt javax.xml.bind.annotation.adapters.CollapsedStrin    gAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/* *
     * Type f   or COmmon Sub     scri   ption Request
       * 
 * <p>Java class for AbstractSubscri   ptionRequestStructure complex type.
 * 
 * <p>The fo llowing schema fragme    nt spe  ci      fi    es     the expected c onten        t contain     ed    within t        his class.
       * 
 * <pre>
 * &lt;comple xType name="AbstractSubscriptionRequ    estStructure">
 *    &lt;comp       le   xContent>
 *     &lt;extensio   n base="{http://www.siri.org.uk/sir  i}   RequestStruc   ture">
 *        &lt;sequen   ce>
 *                 &lt;g    rou    p ref="{http://www.siri.       org.uk/siri}SubscriberEndpointGroup"/>
 *         &lt;element name="SubscriptionContext" ty   pe="{http://www.     siri.org.uk/siri}          SubscriptionContextStructure" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extensi  on>
 *       &lt;/complexContent>
 * &lt;      /  complexType>
 * </pre>
 *    
 * 
 */
@XmlA    ccessorType(XmlAccessType.FIELD)
@XmlType(name =   "Abstra     ctSub    scriptio    nRequestStructure", prop   Order = {
    "c       onsumerAddress",
    "subscriptionFilterI        dentifier",
    "subscriptionContex    t"
})
@Xml  SeeAlso({
    Subsc  riptionRequestStru    cture.class
})
public    abstract    class AbstractSu  bscriptionRequestStructure
    extends RequestStru cture
{

    @XmlElement(name = "ConsumerAddress")
    protect      ed String consum   erAddress;
    @XmlElemen t(name = "  Subscr  iptionFilterIdentifier")
       @XmlJavaType   Adapter(  C   ollapsedStringAdapter.class)
    @XmlSchemaType(name = "NM  TOKEN  ")
    pr   otec  ted Str  ing su   bscriptionF ilterIdenti        fie  r   ;
    @XmlE   leme  n    t(nam      e         = "S   ubscriptionContext") 
    prote       c                   ted Subscriptio              nContextStructure subscriptio nC  ontext;

    /**
     * Ge  ts th    e value of t       he  cons    umerA    d  dre s s property.                 
          * 
         * @    r  eturn
                 *     pos  s ible     object is
     *     {@link String       }  
         *            
     */
    public String           getConsum   e   rAd dress() {        
        retur  n consumer                           Addr  es   s;
                }
  
            /**
                    * Se      ts t   he va            lue of the consumerAd       dress property.
     * 
          *                 @para m value   
     *       allowed object     i   s
     *        {@   link S   tring      }
                *     
     *   /
      pub lic void  setCon  su   merAddres  s(String val   u  e ) {
            this.c    on   s umerAddress = value;
          }

    /**
     * Gets the value o   f th                e su   b   scriptionFi         l   te    rIdentifie   r     pr    oper  ty. 
     *   
           * @ret urn
     *            possible ob       j  e            ct is
             *                     {@link String }
     *     
       */
    publ      ic  String ge     tSubscrip  ti  onFilt        erId e     ntifier() {
        r et urn subs      crip tionFilterIdentifie            r;
        }

     /**
        * Sets the value         of the subsc riptio       nFilterIdenti    fie  r property.
            * 
          * @param     va   lue
     *       al  lo    wed    obje   ct is
         *     {@l ink String }         
     *     
     */
     pu  bli c void setSubscripti   onF    il    te     rIdentifier( S       tring value) {
          th is.subscriptionF  ilte  rIden tifier = v   alue;
    }

    /**
                   * G      ets the value of the subscri       pti   onContext p     roperty.
     *            
                     * @return
     *         possible object is
        *       {@link SubscriptionContextStructure }
      *     
      */
    public SubscriptionContextStructure getSubscriptionContext() {
        return subscriptionContext;
    }

    /**
     * Sets the value of     the subscriptionContext property.
     * 
     * @param value
     *     allowed object is
     *           {@link SubscriptionContextStructure }    
     *     
     */
    p  ublic void setSubscriptionContext(SubscriptionContextStructure value) {
        this.subscriptionContext = value;
    }

}
