











package com.transportation.SIRI_IL.SOAP;









import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.Duration;









/**


 * Connection between two stops within a connection area. Used within teh context of one or other end
 * 
 * <p>Java class for ContextualisedConnectionLinkStructure complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 







 * <pre>



 * &lt;complexType name="ContextualisedConnectionLinkStructure">




 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">


 *       &lt;sequence>








 *         &lt;element name="ConnectionLinkCode" type="{http://www.siri.org.uk/siri}ConnectionLinkCodeType" minOccurs="0"/>


 *         &lt;element ref="{http://www.siri.org.uk/siri}StopPointRef" minOccurs="0"/>
 *         &lt;element ref="{http://www.siri.org.uk/siri}StopPointName" minOccurs="0"/>
 *         &lt;group ref="{http://www.siri.org.uk/siri}ConnectionDurationGroup"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>




 * &lt;/complexType>
 * </pre>
 * 
 * 






 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContextualisedConnectionLinkStructure", propOrder = {



    "connectionLinkCode",
    "stopPointRef",
    "stopPointName",
    "defaultDuration",
    "frequentTravellerDuration",
    "occasionalTravellerDuration",
    "impairedAccessDuration"

})


public class ContextualisedConnectionLinkStructure {



    @XmlElement(name = "ConnectionLinkCode")



    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)



    protected String connectionLinkCode;

    @XmlElement(name = "StopPointRef")
    protected StopPointRefStructure stopPointRef;
    @XmlElement(name = "StopPointName")




    protected NaturalLanguageStringStructure stopPointName;








    @XmlElement(name = "DefaultDuration")

    protected Duration defaultDuration;
    @XmlElement(name = "FrequentTravellerDuration")






    protected Duration frequentTravellerDuration;
    @XmlElement(name = "OccasionalTravellerDuration")
    protected Duration occasionalTravellerDuration;
    @XmlElement(name = "ImpairedAccessDuration")
    protected Duration impairedAccessDuration;

    /**
     * Gets the value of the connectionLinkCode property.


     * 

     * @return




     *     possible object is
     *     {@link String }
     *     
     */
    public String getConnectionLinkCode() {







        return connectionLinkCode;
    }



    /**




     * Sets the value of the connectionLinkCode property.
     * 
     * @param value




     *     allowed object is
     *     {@link String }


     *     
     */




    public void setConnectionLinkCode(String value) {











        this.connectionLinkCode = value;
    }

    /**
     * Gets the value of the stopPointRef property.
     * 
     * @return
     *     possible object is
     *     {@link StopPointRefStructure }






     *     
     */
    public StopPointRefStructure getStopPointRef() {
        return stopPointRef;









    }

    /**
     * Sets the value of the stopPointRef property.




     * 
     * @param value


     *     allowed object is
     *     {@link StopPointRefStructure }
     *     




     */
    public void setStopPointRef(StopPointRefStructure value) {








        this.stopPointRef = value;
    }













    /**


     * Gets the value of the stopPointName property.





     * 


     * @return









     *     possible object is
     *     {@link NaturalLanguageStringStructure }
     *     
     */









    public NaturalLanguageStringStructure getStopPointName() {
        return stopPointName;
    }




















    /**
     * Sets the value of the stopPointName property.
     * 
     * @param value
     *     allowed object is
     *     {@link NaturalLanguageStringStructure }


     *     


     */

















    public void setStopPointName(NaturalLanguageStringStructure value) {
        this.stopPointName = value;



    }










    /**



     * Gets the value of the defaultDuration property.





     * 


     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getDefaultDuration() {
        return defaultDuration;
    }

    /**




     * Sets the value of the defaultDuration property.
     * 
     * @param value


     *     allowed object is
     *     {@link Duration }
     *     
     */






    public void setDefaultDuration(Duration value) {





        this.defaultDuration = value;




    }

    /**







     * Gets the value of the frequentTravellerDuration property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getFrequentTravellerDuration() {



        return frequentTravellerDuration;
    }




    /**
     * Sets the value of the frequentTravellerDuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }





     *     



     */




    public void setFrequentTravellerDuration(Duration value) {



        this.frequentTravellerDuration = value;
    }

    /**
     * Gets the value of the occasionalTravellerDuration property.




     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     


     */
    public Duration getOccasionalTravellerDuration() {
        return occasionalTravellerDuration;
    }

    /**
     * Sets the value of the occasionalTravellerDuration property.
     * 


     * @param value
     *     allowed object is




     *     {@link Duration }
     *     
     */
    public void setOccasionalTravellerDuration(Duration value) {
        this.occasionalTravellerDuration = value;
    }



    /**
     * Gets the value of the impairedAccessDuration property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }


     *     
     */
    public Duration getImpairedAccessDuration() {
        return impairedAccessDuration;
    }

    /**
     * Sets the value of the impairedAccessDuration property.
     * 
     * @param value
     *     allowed object is



     *     {@link Duration }
     *     
     */
    public void setImpairedAccessDuration(Duration value) {
        this.impairedAccessDuration = value;
    }

}
