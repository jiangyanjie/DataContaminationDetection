








package com.transportation.SIRI_IL.SOAP;










import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;















import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;












/**
 * Type for capabilities response.

 * 


 * <p>Java class for AbstractServiceCapabilitiesResponseStructure complex type.
 * 




 * <p>The following schema fragment specifies the expected content contained within this class.











 * 



 * <pre>
 * &lt;complexType name="AbstractServiceCapabilitiesResponseStructure">
 *   &lt;complexContent>


 *     &lt;extension base="{http://www.siri.org.uk/siri}ResponseStructure">
 *       &lt;sequence>




 *         &lt;group ref="{http://www.siri.org.uk/siri}ContextualisedResponseEndpointGroup"/>
 *         &lt;sequence>
 *           &lt;group ref="{http://www.siri.org.uk/siri}CapabilityStatusGroup"/>

 *         &lt;/sequence>


 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 




 */






@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractServiceCapabilitiesResponseStructure", propOrder = {

    "requestMessageRef",
    "status",
    "errorCondition"

})
@XmlSeeAlso({
    GeneralMessageCapabilitiesResponseStructure.class,
    ConnectionTimetableCapabilitiesResponseStructure.class,
    EstimatedTimetableCapabilitiesResponseStructure.class,




    ConnectionMonitoringCapabilitiesResponseStructure.class,







    VehicleMonitoringCapabilitiesResponseStructure.class,
    StopTimetableCapabilitiesResponseStructure.class,
    FacilityMonitoringCapabilitiesResponseStructure.class,
    ProductionTimetableCapabilitiesResponseStructure.class,
    StopMonitoringCapabilitiesResponseStructure.class,
    SituationExchangeCapabilitiesResponseStructure.class
})
public class AbstractServiceCapabilitiesResponseStructure
    extends ResponseStructure













{









    @XmlElement(name = "RequestMessageRef")
    protected MessageQualifierStructure requestMessageRef;







    @XmlElement(name = "Status", defaultValue = "true")
    protected Boolean status;











    @XmlElement(name = "ErrorCondition")


    protected ServiceDeliveryErrorConditionStructure errorCondition;











    /**







     * Gets the value of the requestMessageRef property.
     * 
     * @return
     *     possible object is
     *     {@link MessageQualifierStructure }
     *     
     */
    public MessageQualifierStructure getRequestMessageRef() {
        return requestMessageRef;
    }

    /**



     * Sets the value of the requestMessageRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageQualifierStructure }








     *     
     */
    public void setRequestMessageRef(MessageQualifierStructure value) {
        this.requestMessageRef = value;
    }













    /**
     * Gets the value of the status property.



     * 
     * @return
     *     possible object is




     *     {@link Boolean }
     *     
     */


    public Boolean isStatus() {
        return status;









    }







    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setStatus(Boolean value) {
        this.status = value;
    }




    /**
     * Gets the value of the errorCondition property.
     * 



     * @return

     *     possible object is
     *     {@link ServiceDeliveryErrorConditionStructure }
     *     
     */

    public ServiceDeliveryErrorConditionStructure getErrorCondition() {
        return errorCondition;
    }

    /**
     * Sets the value of the errorCondition property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceDeliveryErrorConditionStructure }





     *     
     */
    public void setErrorCondition(ServiceDeliveryErrorConditionStructure value) {
        this.errorCondition = value;
    }

}
