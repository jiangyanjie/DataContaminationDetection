





package com.transportation.SIRI_IL.SOAP;

import javax.xml.bind.annotation.XmlAccessType;


import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import javax.xml.bind.annotation.XmlSchemaType;



import javax.xml.bind.annotation.XmlType;


import javax.xml.datatype.XMLGregorianCalendar;













/**
 * Type for Origin and Destination stop of a Vehicle Journey




 * 
 * <p>Java class for DatedVehicleJourneyIndirectRefStructure complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.





 * 









 * <pre>
 * &lt;complexType name="DatedVehicleJourneyIndirectRefStructure">





 *   &lt;complexContent>


 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">




 *       &lt;sequence>
 *         &lt;element name="OriginRef" type="{http://www.siri.org.uk/siri}StopPointRefStructure"/>
 *         &lt;element name="AimedDepartureTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="DestinationRef" type="{http://www.siri.org.uk/siri}StopPointRefStructure"/>
 *         &lt;element name="AimedArrivalTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>




 *   &lt;/complexContent>




 * &lt;/complexType>
 * </pre>




 * 
 * 




 */
@XmlAccessorType(XmlAccessType.FIELD)





@XmlType(name = "DatedVehicleJourneyIndirectRefStructure", propOrder = {
    "originRef",








    "aimedDepartureTime",
    "destinationRef",
    "aimedArrivalTime"
})



public class DatedVehicleJourneyIndirectRefStructure {

    @XmlElement(name = "OriginRef", required = true)
    protected StopPointRefStructure originRef;




    @XmlElement(name = "AimedDepartureTime", required = true)
    @XmlSchemaType(name = "dateTime")








    protected XMLGregorianCalendar aimedDepartureTime;





    @XmlElement(name = "DestinationRef", required = true)
    protected StopPointRefStructure destinationRef;

    @XmlElement(name = "AimedArrivalTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar aimedArrivalTime;







    /**





     * Gets the value of the originRef property.
     * 
     * @return
     *     possible object is
     *     {@link StopPointRefStructure }
     *     
     */
    public StopPointRefStructure getOriginRef() {



        return originRef;
    }






    /**







     * Sets the value of the originRef property.


     * 
     * @param value
     *     allowed object is
     *     {@link StopPointRefStructure }
     *     



     */
    public void setOriginRef(StopPointRefStructure value) {
        this.originRef = value;










    }



    /**







     * Gets the value of the aimedDepartureTime property.


     * 
     * @return



     *     possible object is







     *     {@link XMLGregorianCalendar }







     *     













     */
    public XMLGregorianCalendar getAimedDepartureTime() {
        return aimedDepartureTime;





    }

    /**
     * Sets the value of the aimedDepartureTime property.
     * 




     * @param value




     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */













    public void setAimedDepartureTime(XMLGregorianCalendar value) {
        this.aimedDepartureTime = value;
    }






    /**
     * Gets the value of the destinationRef property.
     * 




     * @return
     *     possible object is
     *     {@link StopPointRefStructure }



     *     
     */
    public StopPointRefStructure getDestinationRef() {


        return destinationRef;
    }

    /**
     * Sets the value of the destinationRef property.
     * 
     * @param value
     *     allowed object is


     *     {@link StopPointRefStructure }
     *     
     */
    public void setDestinationRef(StopPointRefStructure value) {
        this.destinationRef = value;
    }

    /**
     * Gets the value of the aimedArrivalTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAimedArrivalTime() {
        return aimedArrivalTime;
    }





    /**
     * Sets the value of the aimedArrivalTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAimedArrivalTime(XMLGregorianCalendar value) {
        this.aimedArrivalTime = value;
    }

}
