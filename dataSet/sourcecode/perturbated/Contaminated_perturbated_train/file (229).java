







package com.transportation.SIRI_IL.SOAP;



import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;




import javax.xml.bind.annotation.XmlElement;




import javax.xml.bind.annotation.XmlSchemaType;





import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


import javax.xml.datatype.XMLGregorianCalendar;












/**
 * Type for SIRI Service subscriptions






 * 




 * <p>Java class for AbstractSubscriptionStructure complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 




 * <pre>
 * &lt;complexType name="AbstractSubscriptionStructure">
 *   &lt;complexContent>

 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>




 *         &lt;group ref="{http://www.siri.org.uk/siri}SubscriptionIdentityGroup"/>
 *         &lt;element name="InitialTerminationTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>


 *       &lt;/sequence>



 *     &lt;/restriction>
 *   &lt;/complexContent>

 * &lt;/complexType>
 * </pre>
 * 






 * 




 */






@XmlAccessorType(XmlAccessType.FIELD)




@XmlType(name = "AbstractSubscriptionStructure", propOrder = {



    "subscriberRef",
    "subscriptionIdentifier",
    "initialTerminationTime"
})




@XmlSeeAlso({
    SituationExchangeSubscriptionStructure.class,





    EstimatedTimetableSubscriptionStructure.class,
    StopTimetableSubscriptionStructure.class,
    StopMonitoringSubscriptionStructure.class,



    VehicleMonitoringSubscriptionStructure.class,
    ConnectionTimetableSubscriptionStructure.class,
    ConnectionMonitoringSubscriptionRequestStructure.class,
    GeneralMessageSubscriptionStructure.class,
    FacilityMonitoringSubscriptionStructure.class,
    ProductionTimetableSubscriptionStructure.class
})
public abstract class AbstractSubscriptionStructure {

    @XmlElement(name = "SubscriberRef")















    protected ParticipantRefStructure subscriberRef;
    @XmlElement(name = "SubscriptionIdentifier", required = true)
    protected SubscriptionQualifierStructure subscriptionIdentifier;



    @XmlElement(name = "InitialTerminationTime", required = true)

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar initialTerminationTime;






    /**












     * Gets the value of the subscriberRef property.
     * 
     * @return
     *     possible object is




     *     {@link ParticipantRefStructure }















     *     
     */
    public ParticipantRefStructure getSubscriberRef() {
        return subscriberRef;
    }

    /**





     * Sets the value of the subscriberRef property.
     * 
     * @param value





     *     allowed object is
     *     {@link ParticipantRefStructure }






     *     
     */





    public void setSubscriberRef(ParticipantRefStructure value) {
        this.subscriberRef = value;
    }













    /**

     * Gets the value of the subscriptionIdentifier property.













     * 
     * @return



     *     possible object is
     *     {@link SubscriptionQualifierStructure }
     *     
     */
    public SubscriptionQualifierStructure getSubscriptionIdentifier() {







        return subscriptionIdentifier;
    }


    /**






     * Sets the value of the subscriptionIdentifier property.
     * 
     * @param value
     *     allowed object is


     *     {@link SubscriptionQualifierStructure }
     *     
     */



    public void setSubscriptionIdentifier(SubscriptionQualifierStructure value) {
        this.subscriptionIdentifier = value;


    }








    /**






     * Gets the value of the initialTerminationTime property.
     * 
     * @return
     *     possible object is

     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getInitialTerminationTime() {
        return initialTerminationTime;
    }






    /**
     * Sets the value of the initialTerminationTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setInitialTerminationTime(XMLGregorianCalendar value) {
        this.initialTerminationTime = value;
    }

}
