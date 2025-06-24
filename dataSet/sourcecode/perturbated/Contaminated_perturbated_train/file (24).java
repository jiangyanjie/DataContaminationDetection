
package   com.transportation.SIRI_IL.SOAP;

import    javax.xml.bind.annotation.XmlAccessType;
i  mport javax.xml.bind.annotation.XmlAccessorType;
imp    ort javax.xml.bind.annotation.XmlElem   ent;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Type      for Capabil     ities of StopMonitopring   Service.
 * 
 *  <p>   Java clas s for AbstractCapabilitiesSt ructu   re       comple x t    yp e.
 * 
 * <p>        The following schema     fragment s   p      ecifie   s the expected content contained with    in this class.
   * 
 * <pre>
 * &lt;complexType name="AbstractCapabilitiesStructure">
 *   &lt;compl    exContent    >
 *     &lt   ;restriction base="{http://www.w3.org/200 1/   XMLSchema}anyType">
 *           &lt;sequence>
 *          &lt;elemen t name="GeneralInteraction" type="{http://w   ww.siri.org.uk/siri}CapabilityGeneralInteractionS   truct     ur   e" minOcc             urs="0"/     >
 *                  &lt;element name="TransportDescription" type="{ht    tp://www.siri.org.uk/s  iri}Transport            DescriptionStru      cture" minOccurs="0"/    >
 *       &lt;/seque    nce>
 *     &lt;/restrictio  n>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </      p  re>
 *   
    * 
 */
@XmlAc   cessorType(XmlAccessType.FIELD)
 @Xm     lType(name = "AbstractCapabilit  iesStructure", propOrder =    {
    "generalI    nteraction",
    "transportDescription"
})
@XmlSeeAlso({
      VehicleMonitoringServiceCapabilitiesStructure.class,
    ConnectionTimetableServiceCapabilitiesStructure.class,
    Pro       ductionTimetableServiceCap     abili     tiesStructure.cla  ss,
       F acilityMonitoringS   erviceCapabilitiesStruct  ure.class,
    StopTimetableServiceCapabilitiesStructur  e.c  lass,
    GeneralMessageServiceCapabilitiesStructure.class,
    SituationExchangeServiceCapabilitiesStructure.class,
    Sto pMonitoringServiceCapabilitiesStructure.class,
    EstimatedTimetableServiceCapabilitiesStruc   ture.class,
      ConnectionMonito   ringServi   ceCapabilitiesStructur   e.class
})
public class AbstractCapabi   li  tiesStructure {
    
           @XmlElement(name = "GeneralInte       ractio n")
    protected CapabilityGe     neralInt   eract         ionStructure g   eneralInteraction;
    @X       mlElement     (nam         e = "TransportDe  scripti     o  n  ")
    protected TransportDe      scriptionStructur         e transportDescription;

    /**
              *               Gets the value of th   e g     e       neralInteraction propert    y.
           * 
     *   @ ret  urn
     *     possib  le obj     ec   t is
           *                 {@link    CapabilityG  en          eralI nteracti   onStruc     ture    }
     *     
           */   
    publi               c        Capabi       lityGenera   lIn  teraction       Structure  getG   e neralInteraction()     {
                return g   eneralInterac     tion;    
    }

    /**
     * S ets  the value of      the gen     eralIntera    ction property.
             * 
     *        @param value
          *          all        owed ob ject is
         *     {@link       Ca        pabili tyGen  eralInter  action       Structure            }
            *           
       */
        public void setGeneralI   n    teraction(CapabilityGe    neralInte   ract   ionStructure value) {
           this.generalInter     a   ction =     value;      
    }

                  /**
      * Gets the value of      the    transp   ortDescription property.
     * 
       *       @return
         *     possibl e object is
            *         {   @link TransportDescriptionS  tructure }
     *     
     */
    public   T   ransportDescriptionStructure ge tTran   sportDescription() {
                 r eturn transportDescription;
       }

         /**
     * Sets the value of     the transpo    rtDescription pr   operty.
     * 
         * @param value
     *     allowed object is
     *     {@link TransportDescriptionStructure   }
     *     
     */
    public void setTransportDescription(TransportDescriptionStructure value) {
        this.transportDescription = value;
    }

}
