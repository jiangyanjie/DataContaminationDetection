




package com.transportation.SIRI_IL.SOAP;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;




import javax.xml.bind.annotation.XmlType;



import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;











/**
 * Type for limitation on navigation

 * 







 * <p>Java class for AccessibilityLimitationStructure complex type.










 * 
 * <p>The following schema fragment specifies the expected content contained within this class.


 * 
 * <pre>
 * &lt;complexType name="AccessibilityLimitationStructure">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LimitationId" type="{http://www.ifopt.org.uk/acsb}LimitationIdType" minOccurs="0"/>
 *         &lt;element name="ValidityCondition" type="{http://www.ifopt.org.uk/ifopt}ValidityConditionStructure" minOccurs="0"/>
 *         &lt;group ref="{http://www.ifopt.org.uk/acsb}MobilityLimitationGroup"/>
 *         &lt;group ref="{http://www.ifopt.org.uk/acsb}SensoryLimitationGroup"/>





 *         &lt;element ref="{http://www.ifopt.org.uk/ifopt}Extensions" minOccurs="0"/>
 *       &lt;/sequence>


 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>








 * </pre>


 * 
 * 





 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccessibilityLimitationStructure", namespace = "http://www.ifopt.org.uk/acsb", propOrder = {






    "limitationId",
    "validityCondition",
    "wheelchairAccess",
    "stepFreeAccess",





    "escalatorFreeAccess",
    "liftFreeAccess",
    "audibleSignalsAvailable",
    "visualSignsAvailable",
    "extensions"



})
public class AccessibilityLimitationStructure {

    @XmlElement(name = "LimitationId")


    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String limitationId;
    @XmlElement(name = "ValidityCondition")
    protected ValidityConditionStructure validityCondition;





    @XmlElement(name = "WheelchairAccess", required = true, defaultValue = "false")
    protected AccessibilityStructure wheelchairAccess;
    @XmlElement(name = "StepFreeAccess", defaultValue = "unknown")
    protected AccessibilityStructure stepFreeAccess;
    @XmlElement(name = "EscalatorFreeAccess", defaultValue = "unknown")





    protected AccessibilityStructure escalatorFreeAccess;







    @XmlElement(name = "LiftFreeAccess", defaultValue = "unknown")
    protected AccessibilityStructure liftFreeAccess;









    @XmlElement(name = "AudibleSignalsAvailable", defaultValue = "false")















    protected AccessibilityStructure audibleSignalsAvailable;
    @XmlElement(name = "VisualSignsAvailable", defaultValue = "unknown")



    protected AccessibilityStructure visualSignsAvailable;



    @XmlElement(name = "Extensions", namespace = "http://www.ifopt.org.uk/ifopt")





    protected Extensions extensions;


    /**



     * Gets the value of the limitationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLimitationId() {
        return limitationId;
    }

    /**
     * Sets the value of the limitationId property.
     * 


     * @param value
     *     allowed object is
     *     {@link String }





     *     
     */
    public void setLimitationId(String value) {
















        this.limitationId = value;
    }


    /**


     * Gets the value of the validityCondition property.
     * 
     * @return

     *     possible object is
     *     {@link ValidityConditionStructure }




     *     
     */
    public ValidityConditionStructure getValidityCondition() {



        return validityCondition;

    }




    /**








     * Sets the value of the validityCondition property.
     * 
     * @param value



     *     allowed object is
     *     {@link ValidityConditionStructure }








     *     
     */





    public void setValidityCondition(ValidityConditionStructure value) {


        this.validityCondition = value;



    }



    /**
     * Gets the value of the wheelchairAccess property.
     * 











     * @return
     *     possible object is
     *     {@link AccessibilityStructure }
     *     




     */



    public AccessibilityStructure getWheelchairAccess() {
        return wheelchairAccess;
    }








    /**



     * Sets the value of the wheelchairAccess property.
















     * 




     * @param value







     *     allowed object is




     *     {@link AccessibilityStructure }



     *     
     */



    public void setWheelchairAccess(AccessibilityStructure value) {


        this.wheelchairAccess = value;







    }


















    /**









     * Gets the value of the stepFreeAccess property.



     * 
     * @return
     *     possible object is
     *     {@link AccessibilityStructure }
     *     



     */
    public AccessibilityStructure getStepFreeAccess() {
        return stepFreeAccess;
    }



    /**
     * Sets the value of the stepFreeAccess property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccessibilityStructure }
     *     
     */



    public void setStepFreeAccess(AccessibilityStructure value) {
        this.stepFreeAccess = value;
    }

    /**
     * Gets the value of the escalatorFreeAccess property.
     * 
     * @return


     *     possible object is




     *     {@link AccessibilityStructure }

     *     
     */
    public AccessibilityStructure getEscalatorFreeAccess() {


        return escalatorFreeAccess;
    }
















    /**








     * Sets the value of the escalatorFreeAccess property.










     * 

     * @param value
     *     allowed object is
     *     {@link AccessibilityStructure }
     *     

     */


    public void setEscalatorFreeAccess(AccessibilityStructure value) {



        this.escalatorFreeAccess = value;
    }





    /**
     * Gets the value of the liftFreeAccess property.
     * 




     * @return
     *     possible object is
     *     {@link AccessibilityStructure }
     *     
     */
    public AccessibilityStructure getLiftFreeAccess() {


        return liftFreeAccess;
    }

    /**






     * Sets the value of the liftFreeAccess property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccessibilityStructure }
     *     


     */
    public void setLiftFreeAccess(AccessibilityStructure value) {
        this.liftFreeAccess = value;
    }

    /**
     * Whether a Place has Audible signals for the viusally impaired.
     * 


     * @return

     *     possible object is
     *     {@link AccessibilityStructure }
     *     
     */
    public AccessibilityStructure getAudibleSignalsAvailable() {
        return audibleSignalsAvailable;
    }

    /**


     * Sets the value of the audibleSignalsAvailable property.





     * 

     * @param value
     *     allowed object is
     *     {@link AccessibilityStructure }
     *     
     */
    public void setAudibleSignalsAvailable(AccessibilityStructure value) {
        this.audibleSignalsAvailable = value;
    }

    /**
     * Whether a Place hasVisual signals for the hearing impaired.
     * 
     * @return
     *     possible object is
     *     {@link AccessibilityStructure }
     *     
     */
    public AccessibilityStructure getVisualSignsAvailable() {
        return visualSignsAvailable;
    }

    /**
     * Sets the value of the visualSignsAvailable property.
     * 
     * @param value
     *     allowed object is


     *     {@link AccessibilityStructure }
     *     
     */
    public void setVisualSignsAvailable(AccessibilityStructure value) {
        this.visualSignsAvailable = value;
    }

    /**
     * Gets the value of the extensions property.
     * 
     * @return
     *     possible object is
     *     {@link Extensions }
     *     
     */




    public Extensions getExtensions() {
        return extensions;
    }

    /**
     * Sets the value of the extensions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Extensions }
     *     
     */
    public void setExtensions(Extensions value) {
        this.extensions = value;
    }

}
