






package com.transportation.SIRI_IL.SOAP;





import java.util.ArrayList;





import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;





import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;











/**
 * Type for Assesment
 * 
 * <p>Java class for AccessibilityAssessmentStructure complex type.


 * 
 * <p>The following schema fragment specifies the expected content contained within this class.


 * 
 * <pre>



 * &lt;complexType name="AccessibilityAssessmentStructure">
 *   &lt;complexContent>


 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">




 *       &lt;sequence>
 *         &lt;element name="MobilityImpairedAccess" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Limitations" minOccurs="0">
 *           &lt;complexType>



 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">


 *                 &lt;sequence>

 *                   &lt;element name="AccessibilityLimitation" type="{http://www.ifopt.org.uk/acsb}AccessibilityLimitationStructure" maxOccurs="unbounded"/>
 *                 &lt;/sequence>





 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>

 *         &lt;element name="Suitabilities" minOccurs="0">













 *           &lt;complexType>









 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">








 *                 &lt;sequence>
 *                   &lt;element name="Suitability" type="{http://www.ifopt.org.uk/acsb}SuitabilityStructure" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>

 *           &lt;/complexType>


 *         &lt;/element>





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








@XmlType(name = "AccessibilityAssessmentStructure", namespace = "http://www.ifopt.org.uk/acsb", propOrder = {













    "mobilityImpairedAccess",

    "limitations",
    "suitabilities",
    "extensions"
})
public class AccessibilityAssessmentStructure {






    @XmlElement(name = "MobilityImpairedAccess")
    protected boolean mobilityImpairedAccess;
    @XmlElement(name = "Limitations")
    protected AccessibilityAssessmentStructure.Limitations limitations;
    @XmlElement(name = "Suitabilities")
    protected AccessibilityAssessmentStructure.Suitabilities suitabilities;














    @XmlElement(name = "Extensions", namespace = "http://www.ifopt.org.uk/ifopt")







    protected Extensions extensions;





    /**
     * Gets the value of the mobilityImpairedAccess property.
     * 
     */




    public boolean isMobilityImpairedAccess() {

        return mobilityImpairedAccess;
    }


    /**













     * Sets the value of the mobilityImpairedAccess property.
     * 
     */
    public void setMobilityImpairedAccess(boolean value) {
        this.mobilityImpairedAccess = value;
    }














    /**
     * Gets the value of the limitations property.
     * 
     * @return
     *     possible object is
     *     {@link AccessibilityAssessmentStructure.Limitations }
     *     
     */
    public AccessibilityAssessmentStructure.Limitations getLimitations() {
        return limitations;
    }





    /**
     * Sets the value of the limitations property.
     * 





     * @param value





     *     allowed object is
     *     {@link AccessibilityAssessmentStructure.Limitations }



     *     
     */
    public void setLimitations(AccessibilityAssessmentStructure.Limitations value) {
        this.limitations = value;



    }









    /**



     * Gets the value of the suitabilities property.
     * 
     * @return














     *     possible object is









     *     {@link AccessibilityAssessmentStructure.Suitabilities }





     *     
     */


    public AccessibilityAssessmentStructure.Suitabilities getSuitabilities() {


        return suitabilities;


    }






    /**
     * Sets the value of the suitabilities property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccessibilityAssessmentStructure.Suitabilities }
     *     


     */
    public void setSuitabilities(AccessibilityAssessmentStructure.Suitabilities value) {
        this.suitabilities = value;
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





     *         &lt;element name="AccessibilityLimitation" type="{http://www.ifopt.org.uk/acsb}AccessibilityLimitationStructure" maxOccurs="unbounded"/>












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
        "accessibilityLimitation"
    })



    public static class Limitations {

        @XmlElement(name = "AccessibilityLimitation", namespace = "http://www.ifopt.org.uk/acsb", required = true)
        protected List<AccessibilityLimitationStructure> accessibilityLimitation;

        /**
         * Gets the value of the accessibilityLimitation property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the











         * returned list will be present inside the JAXB object.









         * This is why there is not a <CODE>set</CODE> method for the accessibilityLimitation property.
         * 
         * <p>




         * For example, to add a new item, do as follows:
         * <pre>






         *    getAccessibilityLimitation().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list


         * {@link AccessibilityLimitationStructure }
         * 


         * 
         */

        public List<AccessibilityLimitationStructure> getAccessibilityLimitation() {








            if (accessibilityLimitation == null) {
                accessibilityLimitation = new ArrayList<AccessibilityLimitationStructure>();
            }




            return this.accessibilityLimitation;
        }

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
     *         &lt;element name="Suitability" type="{http://www.ifopt.org.uk/acsb}SuitabilityStructure" maxOccurs="unbounded"/>
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
        "suitability"
    })
    public static class Suitabilities {

        @XmlElement(name = "Suitability", namespace = "http://www.ifopt.org.uk/acsb", required = true)
        protected List<SuitabilityStructure> suitability;

        /**
         * Gets the value of the suitability property.




         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the


         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the suitability property.
         * 
         * <p>




         * For example, to add a new item, do as follows:
         * <pre>
         *    getSuitability().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link SuitabilityStructure }
         * 
         * 
         */
        public List<SuitabilityStructure> getSuitability() {
            if (suitability == null) {
                suitability = new ArrayList<SuitabilityStructure>();
            }
            return this.suitability;
        }

    }

}
