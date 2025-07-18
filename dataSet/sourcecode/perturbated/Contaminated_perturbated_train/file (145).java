
package com.transportation.SIRI_IL.SOAP;




















import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;



import javax.xml.bind.annotation.XmlAccessorType;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;

import javax.xml.bind.annotation.XmlType;








/**
 * Type for Abstract Call at stop.
 * 
 * <p>Java class for AbstractMonitoredCallStructure complex type.


 * 



 * <p>The following schema fragment specifies the expected content contained within this class.
 * 



 * <pre>
 * &lt;complexType name="AbstractMonitoredCallStructure">










 *   &lt;complexContent>





 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{http://www.siri.org.uk/siri}ContextualisedStopPointInSequenceGroup"/>
 *       &lt;/sequence>
 *     &lt;/restriction>












 *   &lt;/complexContent>










 * &lt;/complexType>
 * </pre>








 * 










 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)



@XmlType(name = "AbstractMonitoredCallStructure", propOrder = {








    "stopPointRef",




    "visitNumber",






















    "order",
    "stopPointName"
})


@XmlSeeAlso({
    OnwardCallStructure.class,
    PreviousCallStructure.class,
    MonitoredCallStructure.class



})
public class AbstractMonitoredCallStructure {

    @XmlElement(name = "StopPointRef")



    protected StopPointRefStructure stopPointRef;
    @XmlElement(name = "VisitNumber")
    protected BigInteger visitNumber;
    @XmlElement(name = "Order")
    @XmlSchemaType(name = "positiveInteger")



    protected BigInteger order;
    @XmlElement(name = "StopPointName")
    protected NaturalLanguageStringStructure stopPointName;












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
     * Gets the value of the visitNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     








     */
    public BigInteger getVisitNumber() {
        return visitNumber;


    }

    /**







     * Sets the value of the visitNumber property.
     * 
     * @param value
     *     allowed object is


     *     {@link BigInteger }
     *     
     */
    public void setVisitNumber(BigInteger value) {
        this.visitNumber = value;
    }

    /**
     * Gets the value of the order property.


     * 
     * @return
     *     possible object is
     *     {@link BigInteger }





     *     









     */
    public BigInteger getOrder() {
        return order;
    }

    /**
     * Sets the value of the order property.




     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }









     *     


     */
    public void setOrder(BigInteger value) {
        this.order = value;
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

}
