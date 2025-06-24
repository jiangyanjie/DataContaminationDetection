
package   com.transportation.SIRI_IL.SOAP;

impo    rt javax.xml.bind.annotation.XmlAccessType;
    import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType  ;


/*       *
 * Type for     Uniq  u  e reference to this reques   t,      cr ea    ted by Consumer. May be  used to referenc   e  the r   eque   st    in subsequ   ent interact   ions.   Used by WSDL.. 
 * 
 * <p>Ja   va        cla       ss for ConsumerReque      stEndpointStructure   complex     typ  e.
 * 
     * <p>The following     schema fragment spec  ifies the exp       ected content contained within t  hi     s         class.
 * 
 * <pre>
 * &lt;complexType name="ConsumerRequestEndpointStructure">
 *     &lt;compl  exConte       nt>
 *     &lt;extension    base="        {http://w ww.siri.org.uk/siri}AbstractRequestStructure"     >
 *           &lt;sequence>
 *         &l     t;group ref="{htt    p://www.siri    .o   rg.uk/sir    i }ConsumerRequestEndp   ointGroup"/  >
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &       lt;/complexC    onte       n  t>
 * &lt;/co   m    pl  exType>
 * </p          re>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConsumerRequestEndpoin  tStructure", p    ropOrder = {
    "address",
    "consumerRef",
    "messageIdentifier    "
})
@Xm  lSeeAlso({
    DataSu      pplyRequestStru    cture.class
})
public      class ConsumerRequestEndpointStructure
    extends AbstractR      equestS      tructu re
{

    @X   mlElement   (name = "Address")
       protected S    t      ring address  ;
    @XmlElem   ent    (name = "Con   sumerRef")
                  protected Participant  R    e    fStru cture c    onsumerRef;
          @XmlElement(n ame = "MessageIde    ntifie  r")
       pro  tected Mes sageQualifierStruct   ure  messageIde              ntifier;

                   /**      
        * Gets the   value of the           addr   ess proper       ty.
       *        
         *           @ret   urn
          *        possible   o                    bj          ec   t       i  s    
     *           {@link      String }
                  *        
       * /
       public St       ri   ng getAdd    re                      ss() {
              return address  ;
    }

      /**
                 * Sets   the                value of    the ad        dr        e   ss property    .
     * 
     * @param v alue
          *        all  o wed    obj       ect     is
                  *                {@l   ink String }
     *     
            */     
    pub   lic void set       Address(S   t rin    g                         value) {   
               this.a       d        dress = value;    
    }

    /*                        *
     * Gets th  e value o        f the cons  umerRef property.
     *    
                * @r    eturn
     *               poss     ible o     bject is  
            *      {@link Par     ticipantRef S    t  ru   cture      }
     *        
     */
    publi       c Partic   ipa        ntRef      Str  ucture getCon    sumer         Ref     () {
                return     consu   merRef;   
    }

           /**                           
          * S  ets th   e     value of the consumerRef       pr     operty.
     * 
     * @param value
     *         allowed o  bject is
            *         {      @link Partic   ipan        tRefStructure }
     *       
     */
       public void setConsu  merRef     (Part             icipantRefStructure value) {
               this       .consumerRef = value;
        }

    /**   
     * Gets the value      of the messageIdentifier property.
     * 
           * @return
     *     possible object is
       *       { @link MessageQ ualifierStructure }
     *     
     */
      public MessageQualifier  Structure getMessageIdenti      fier() {
        return       messageIdentifie   r;
    }

    /**
     * Sets the value of the messageIdenti    fier     property.
        * 
     * @param value
     *     allowed object is
     *     {@link MessageQualifierStruct    ure }
     *     
     */
    public void setMessageIdentifier(MessageQualifierStructure value) {
        this.messageIdentifier = value;
    }

}
