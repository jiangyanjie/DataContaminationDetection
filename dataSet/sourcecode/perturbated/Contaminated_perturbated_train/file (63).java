


package com.transportation.SIRI_IL.SOAP;

import java.math.BigInteger;



import java.util.ArrayList;


























import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;






import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;




/**



 * Type for an interchange Activity.
 * 
 * <p>Java class for AbstractDistributorItemStructure complex type.
 * 






 * <p>The following schema fragment specifies the expected content contained within this class.






 * 
 * <pre>
 * &lt;complexType name="AbstractDistributorItemStructure">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.siri.org.uk/siri}AbstractItemStructure">




 *       &lt;sequence>









 *         &lt;group ref="{http://www.siri.org.uk/siri}InterchangeDistributorIdentityGroup"/>






 *         &lt;element name="DistributorJourney" type="{http://www.siri.org.uk/siri}InterchangeJourneyStructure"/>




 *         &lt;element name="FeederVehicleJourneyRef" type="{http://www.siri.org.uk/siri}FramedVehicleJourneyRefStructure" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>





 *     &lt;/extension>
 *   &lt;/complexContent>

 * &lt;/complexType>

 * </pre>
 * 
 * 




 */










@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractDistributorItemStructure", propOrder = {
    "interchangeRef",





    "connectionLinkRef",
    "stopPointRef",
    "distributorVisitNumber",



    "distributorOrder",




    "distributorJourney",
    "feederVehicleJourneyRef"
})
@XmlSeeAlso({











    StoppingPositionChangedDepartureStructure.class,



    DistributorDepartureCancellationStructure.class,





    WaitProlongedDepartureStructure.class
})


public class AbstractDistributorItemStructure







    extends AbstractItemStructure
{

    @XmlElement(name = "InterchangeRef")





    protected InterchangeRefStructure interchangeRef;
    @XmlElement(name = "ConnectionLinkRef", required = true)




    protected ConnectionLinkRefStructure connectionLinkRef;




    @XmlElement(name = "StopPointRef")
    protected StopPointRefStructure stopPointRef;
    @XmlElement(name = "DistributorVisitNumber")
    protected BigInteger distributorVisitNumber;



    @XmlElement(name = "DistributorOrder")
    @XmlSchemaType(name = "positiveInteger")


    protected BigInteger distributorOrder;

    @XmlElement(name = "DistributorJourney", required = true)
    protected InterchangeJourneyStructure distributorJourney;
    @XmlElement(name = "FeederVehicleJourneyRef")
    protected List<FramedVehicleJourneyRefStructure> feederVehicleJourneyRef;


    /**








     * Gets the value of the interchangeRef property.













     * 
     * @return
     *     possible object is




     *     {@link InterchangeRefStructure }
     *     
     */
    public InterchangeRefStructure getInterchangeRef() {
        return interchangeRef;
    }



    /**
     * Sets the value of the interchangeRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link InterchangeRefStructure }
     *     
     */
    public void setInterchangeRef(InterchangeRefStructure value) {






        this.interchangeRef = value;
    }
























    /**
     * Gets the value of the connectionLinkRef property.








     * 




     * @return





     *     possible object is





     *     {@link ConnectionLinkRefStructure }
     *     
     */
    public ConnectionLinkRefStructure getConnectionLinkRef() {








        return connectionLinkRef;
    }




    /**
     * Sets the value of the connectionLinkRef property.





     * 







     * @param value


     *     allowed object is
     *     {@link ConnectionLinkRefStructure }
     *     


     */








    public void setConnectionLinkRef(ConnectionLinkRefStructure value) {
        this.connectionLinkRef = value;
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
     * Gets the value of the distributorVisitNumber property.
     * 
     * @return
     *     possible object is















     *     {@link BigInteger }
     *     
     */
    public BigInteger getDistributorVisitNumber() {





        return distributorVisitNumber;
    }

    /**
     * Sets the value of the distributorVisitNumber property.
     * 


     * @param value




     *     allowed object is
     *     {@link BigInteger }
     *     




     */
    public void setDistributorVisitNumber(BigInteger value) {



        this.distributorVisitNumber = value;










    }

    /**
     * Gets the value of the distributorOrder property.
     * 
     * @return
     *     possible object is











     *     {@link BigInteger }
     *     
     */
    public BigInteger getDistributorOrder() {
        return distributorOrder;
    }

    /**
     * Sets the value of the distributorOrder property.
     * 
     * @param value




     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDistributorOrder(BigInteger value) {
        this.distributorOrder = value;
    }




    /**


     * Gets the value of the distributorJourney property.
     * 
     * @return


     *     possible object is
     *     {@link InterchangeJourneyStructure }

     *     
     */
    public InterchangeJourneyStructure getDistributorJourney() {
        return distributorJourney;
    }




    /**
     * Sets the value of the distributorJourney property.
     * 
     * @param value
     *     allowed object is



     *     {@link InterchangeJourneyStructure }
     *     
     */
    public void setDistributorJourney(InterchangeJourneyStructure value) {
        this.distributorJourney = value;
    }

    /**













     * Gets the value of the feederVehicleJourneyRef property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the feederVehicleJourneyRef property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFeederVehicleJourneyRef().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FramedVehicleJourneyRefStructure }
     * 
     * 
     */
    public List<FramedVehicleJourneyRefStructure> getFeederVehicleJourneyRef() {
        if (feederVehicleJourneyRef == null) {
            feederVehicleJourneyRef = new ArrayList<FramedVehicleJourneyRefStructure>();
        }
        return this.feederVehicleJourneyRef;
    }

}
