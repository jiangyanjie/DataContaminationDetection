



package com.transportation.SIRI_IL.SOAP;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;






import javax.xml.bind.annotation.XmlElement;



import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;




/**




 * Type for Abstract Permission.
 * 
 * <p>Java class for AbstractPermissionStructure complex type.




 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AbstractPermissionStructure">







 *   &lt;complexContent>


 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">


 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="AllParticipants" type="{http://www.siri.org.uk/siri}EmptyType"/>
 *           &lt;element name="ParticipantRef" type="{http://www.siri.org.uk/siri}ParticipantRefStructure"/>
 *         &lt;/choice>
 *         &lt;element name="GeneralCapabilities" minOccurs="0">







 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="RequestResponse" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                   &lt;element name="PublishSubscribe" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>

















 *           &lt;/complexType>







 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>




 * </pre>
 * 




 * 







 */
@XmlAccessorType(XmlAccessType.FIELD)




@XmlType(name = "AbstractPermissionStructure", propOrder = {
    "allParticipants",
    "participantRef",
    "generalCapabilities"



})






@XmlSeeAlso({
    VehicleMonitoringServicePermissionStructure.class,






    ConnectionServicePermissionStructure.class,
    StopMonitoringServicePermissionStructure.class,


    FacilityMonitoringServicePermissionStructure.class,
    SituationExchangeServicePermissionStructure.class,
    GeneralMessageServicePermissionStructure.class






})
public class AbstractPermissionStructure {

    @XmlElement(name = "AllParticipants")





    protected String allParticipants;



    @XmlElement(name = "ParticipantRef")











    protected ParticipantRefStructure participantRef;
    @XmlElement(name = "GeneralCapabilities")





    protected AbstractPermissionStructure.GeneralCapabilities generalCapabilities;








    /**
     * Gets the value of the allParticipants property.











     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAllParticipants() {
        return allParticipants;
    }






    /**


     * Sets the value of the allParticipants property.



     * 


     * @param value
     *     allowed object is


     *     {@link String }
     *     
     */
    public void setAllParticipants(String value) {










        this.allParticipants = value;
    }


















    /**
     * Gets the value of the participantRef property.
     * 
     * @return
     *     possible object is
     *     {@link ParticipantRefStructure }




     *     





     */
    public ParticipantRefStructure getParticipantRef() {
        return participantRef;


    }

    /**








     * Sets the value of the participantRef property.
     * 

     * @param value










     *     allowed object is





     *     {@link ParticipantRefStructure }
     *     
     */
    public void setParticipantRef(ParticipantRefStructure value) {






        this.participantRef = value;
    }


    /**
     * Gets the value of the generalCapabilities property.






     * 
     * @return
     *     possible object is
     *     {@link AbstractPermissionStructure.GeneralCapabilities }
     *     
     */
    public AbstractPermissionStructure.GeneralCapabilities getGeneralCapabilities() {
        return generalCapabilities;




    }

    /**
     * Sets the value of the generalCapabilities property.





     * 
     * @param value
     *     allowed object is


     *     {@link AbstractPermissionStructure.GeneralCapabilities }






     *     
     */
    public void setGeneralCapabilities(AbstractPermissionStructure.GeneralCapabilities value) {
        this.generalCapabilities = value;






    }


    /**







     * <p>Java class for anonymous complex type.
     * 





     * <p>The following schema fragment specifies the expected content contained within this class.



     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">






     *       &lt;sequence>
     *         &lt;element name="RequestResponse" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *         &lt;element name="PublishSubscribe" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *       &lt;/sequence>



     *     &lt;/restriction>




     *   &lt;/complexContent>
     * &lt;/complexType>


     * </pre>
     * 
     * 




     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {






        "requestResponse",
        "publishSubscribe"
    })
    public static class GeneralCapabilities {

        @XmlElement(name = "RequestResponse", defaultValue = "true")
        protected boolean requestResponse;
        @XmlElement(name = "PublishSubscribe", defaultValue = "true")
        protected boolean publishSubscribe;

        /**
         * Gets the value of the requestResponse property.
         * 
         */
        public boolean isRequestResponse() {
            return requestResponse;
        }

        /**
         * Sets the value of the requestResponse property.
         * 
         */
        public void setRequestResponse(boolean value) {
            this.requestResponse = value;
        }

        /**
         * Gets the value of the publishSubscribe property.
         * 
         */
        public boolean isPublishSubscribe() {
            return publishSubscribe;
        }

        /**
         * Sets the value of the publishSubscribe property.
         * 
         */
        public void setPublishSubscribe(boolean value) {
            this.publishSubscribe = value;
        }

    }

}
