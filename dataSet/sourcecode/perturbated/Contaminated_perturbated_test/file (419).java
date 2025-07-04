








package com.transportation.SIRI_IL.SOAP;



import java.math.BigInteger;


import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

















import javax.xml.bind.annotation.XmlElement;



import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;


import javax.xml.datatype.XMLGregorianCalendar;









/**




 * Data type for Planned Vehicle Journey Stop (Production Timetable Service).
 * 
 * <p>Java class for DatedCallStructure complex type.







 * 




 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatedCallStructure">






 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>




 *         &lt;group ref="{http://www.siri.org.uk/siri}StopPointInSequenceGroup"/>
 *         &lt;group ref="{http://www.siri.org.uk/siri}CallPropertyGroup"/>





 *         &lt;element name="CallNote" type="{http://www.siri.org.uk/siri}NaturalLanguageStringStructure" maxOccurs="unbounded" minOccurs="0"/>







 *         &lt;group ref="{http://www.siri.org.uk/siri}AimedCallGroup"/>
 *         &lt;element name="TargetedInterchange" type="{http://www.siri.org.uk/siri}TargetedInterchangeStructure" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.siri.org.uk/siri}Extensions" minOccurs="0"/>
 *       &lt;/sequence>












 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>




 * 




 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)


@XmlType(name = "DatedCallStructure", propOrder = {
    "stopPointRef",




    "visitNumber",
    "order",
    "stopPointName",







    "timingPoint",
    "boardingStretch",
    "requestStop",






    "destinationDisplay",
    "callNote",






    "aimedArrivalTime",
    "arrivalPlatformName",




    "arrivalBoardingActivity",

    "aimedDepartureTime",
    "departurePlatformName",










    "departureBoardingActivity",



    "aimedHeadwayInterval",
    "targetedInterchange",
    "extensions"
})
public class DatedCallStructure {

    @XmlElement(name = "StopPointRef", required = true)


    protected StopPointRefStructure stopPointRef;
    @XmlElement(name = "VisitNumber")
    protected BigInteger visitNumber;
    @XmlElement(name = "Order")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger order;
    @XmlElement(name = "StopPointName")











    protected NaturalLanguageStringStructure stopPointName;
    @XmlElement(name = "TimingPoint", defaultValue = "true")
    protected Boolean timingPoint;
    @XmlElement(name = "BoardingStretch", defaultValue = "false")
    protected Boolean boardingStretch;
    @XmlElement(name = "RequestStop", defaultValue = "false")
    protected Boolean requestStop;
    @XmlElement(name = "DestinationDisplay")


















    protected NaturalLanguageStringStructure destinationDisplay;
    @XmlElement(name = "CallNote")

    protected List<NaturalLanguageStringStructure> callNote;
    @XmlElement(name = "AimedArrivalTime")
    @XmlSchemaType(name = "dateTime")



    protected XMLGregorianCalendar aimedArrivalTime;
    @XmlElement(name = "ArrivalPlatformName")
    protected NaturalLanguageStringStructure arrivalPlatformName;



    @XmlElement(name = "ArrivalBoardingActivity", defaultValue = "alighting")


    protected ArrivalBoardingActivityEnumeration arrivalBoardingActivity;
    @XmlElement(name = "AimedDepartureTime")



    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar aimedDepartureTime;
    @XmlElement(name = "DeparturePlatformName")




    protected NaturalLanguageStringStructure departurePlatformName;
    @XmlElement(name = "DepartureBoardingActivity", defaultValue = "boarding")
    protected DepartureBoardingActivityEnumeration departureBoardingActivity;
    @XmlElement(name = "AimedHeadwayInterval")
    protected Duration aimedHeadwayInterval;
    @XmlElement(name = "TargetedInterchange")








    protected List<TargetedInterchangeStructure> targetedInterchange;

    @XmlElement(name = "Extensions")
    protected ExtensionsStructure extensions;

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








    /**
     * Gets the value of the timingPoint property.

     * 

     * @return
     *     possible object is
     *     {@link Boolean }









     *     
     */
    public Boolean isTimingPoint() {
        return timingPoint;
    }




    /**
     * Sets the value of the timingPoint property.
     * 
     * @param value
     *     allowed object is




     *     {@link Boolean }




     *     











     */




    public void setTimingPoint(Boolean value) {
        this.timingPoint = value;
    }






    /**




     * Gets the value of the boardingStretch property.
     * 




     * @return
     *     possible object is



     *     {@link Boolean }



     *     


     */
    public Boolean isBoardingStretch() {
        return boardingStretch;




    }



    /**
     * Sets the value of the boardingStretch property.




     * 
     * @param value
     *     allowed object is









     *     {@link Boolean }
     *     
     */
    public void setBoardingStretch(Boolean value) {






        this.boardingStretch = value;
    }







    /**
     * Gets the value of the requestStop property.





     * 










     * @return





     *     possible object is




     *     {@link Boolean }
     *     
     */
    public Boolean isRequestStop() {
        return requestStop;
    }



    /**
     * Sets the value of the requestStop property.
     * 
     * @param value
     *     allowed object is



















     *     {@link Boolean }
     *     









     */









    public void setRequestStop(Boolean value) {




        this.requestStop = value;
    }


    /**





     * Gets the value of the destinationDisplay property.
     * 
     * @return
     *     possible object is
     *     {@link NaturalLanguageStringStructure }
     *     
     */
    public NaturalLanguageStringStructure getDestinationDisplay() {





        return destinationDisplay;
    }

    /**
     * Sets the value of the destinationDisplay property.




     * 
     * @param value









     *     allowed object is
     *     {@link NaturalLanguageStringStructure }
     *     
     */



















    public void setDestinationDisplay(NaturalLanguageStringStructure value) {



        this.destinationDisplay = value;
    }






    /**
     * Gets the value of the callNote property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.








     * This is why there is not a <CODE>set</CODE> method for the callNote property.
     * 



     * <p>





     * For example, to add a new item, do as follows:
     * <pre>








     *    getCallNote().add(newItem);

     * </pre>



     * 
     * 



     * <p>

     * Objects of the following type(s) are allowed in the list
     * {@link NaturalLanguageStringStructure }
     * 
     * 
     */
    public List<NaturalLanguageStringStructure> getCallNote() {

        if (callNote == null) {
            callNote = new ArrayList<NaturalLanguageStringStructure>();
        }
        return this.callNote;

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




    /**




     * Gets the value of the arrivalPlatformName property.










     * 


     * @return



     *     possible object is




     *     {@link NaturalLanguageStringStructure }


     *     
     */


    public NaturalLanguageStringStructure getArrivalPlatformName() {
        return arrivalPlatformName;


    }



    /**
     * Sets the value of the arrivalPlatformName property.
     * 
     * @param value
     *     allowed object is













     *     {@link NaturalLanguageStringStructure }



     *     



     */
    public void setArrivalPlatformName(NaturalLanguageStringStructure value) {
        this.arrivalPlatformName = value;


    }

    /**

     * Gets the value of the arrivalBoardingActivity property.
     * 



     * @return
     *     possible object is



     *     {@link ArrivalBoardingActivityEnumeration }



     *     
     */
    public ArrivalBoardingActivityEnumeration getArrivalBoardingActivity() {
        return arrivalBoardingActivity;



    }

    /**
     * Sets the value of the arrivalBoardingActivity property.


     * 
     * @param value
     *     allowed object is
     *     {@link ArrivalBoardingActivityEnumeration }
     *     
     */
    public void setArrivalBoardingActivity(ArrivalBoardingActivityEnumeration value) {
        this.arrivalBoardingActivity = value;




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
     * Gets the value of the departurePlatformName property.
     * 
     * @return
     *     possible object is
     *     {@link NaturalLanguageStringStructure }



     *     
     */
    public NaturalLanguageStringStructure getDeparturePlatformName() {






        return departurePlatformName;


    }

    /**
     * Sets the value of the departurePlatformName property.
     * 
     * @param value
     *     allowed object is






     *     {@link NaturalLanguageStringStructure }



     *     






     */
    public void setDeparturePlatformName(NaturalLanguageStringStructure value) {



        this.departurePlatformName = value;
    }







    /**
     * Gets the value of the departureBoardingActivity property.
     * 
     * @return
     *     possible object is
     *     {@link DepartureBoardingActivityEnumeration }
     *     
     */
    public DepartureBoardingActivityEnumeration getDepartureBoardingActivity() {


        return departureBoardingActivity;
    }







    /**
     * Sets the value of the departureBoardingActivity property.
     * 



     * @param value
     *     allowed object is
     *     {@link DepartureBoardingActivityEnumeration }
     *     
     */
    public void setDepartureBoardingActivity(DepartureBoardingActivityEnumeration value) {
        this.departureBoardingActivity = value;

    }

    /**
     * Gets the value of the aimedHeadwayInterval property.
     * 
     * @return
     *     possible object is




     *     {@link Duration }
     *     
     */


    public Duration getAimedHeadwayInterval() {
        return aimedHeadwayInterval;
    }

    /**
     * Sets the value of the aimedHeadwayInterval property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setAimedHeadwayInterval(Duration value) {

        this.aimedHeadwayInterval = value;
    }

    /**
     * Gets the value of the targetedInterchange property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the targetedInterchange property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTargetedInterchange().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TargetedInterchangeStructure }
     * 
     * 
     */
    public List<TargetedInterchangeStructure> getTargetedInterchange() {
        if (targetedInterchange == null) {
            targetedInterchange = new ArrayList<TargetedInterchangeStructure>();
        }
        return this.targetedInterchange;
    }

    /**
     * Gets the value of the extensions property.
     * 
     * @return
     *     possible object is
     *     {@link ExtensionsStructure }
     *     
     */
    public ExtensionsStructure getExtensions() {
        return extensions;
    }

    /**
     * Sets the value of the extensions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtensionsStructure }
     *     
     */
    public void setExtensions(ExtensionsStructure value) {
        this.extensions = value;
    }

}
