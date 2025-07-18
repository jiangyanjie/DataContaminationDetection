










package com.transportation.SIRI_IL.SOAP;



import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * The details of the delays being caused by the situation element defined in the situation record.
 * 




 * <p>Java class for Delays complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>










 * &lt;complexType name="Delays">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="delayCoded" type="{http://datex2.eu/schema/1_0/1_0}DelayCodeEnum" minOccurs="0"/>






 *         &lt;element name="delaysType" type="{http://datex2.eu/schema/1_0/1_0}DelaysTypeEnum" minOccurs="0"/>
 *         &lt;element name="delayTimeValue" type="{http://datex2.eu/schema/1_0/1_0}Seconds" minOccurs="0"/>



 *         &lt;element name="delaysExtension" type="{http://datex2.eu/schema/1_0/1_0}ExtensionType" minOccurs="0"/>






 *       &lt;/sequence>













 *     &lt;/restriction>



 *   &lt;/complexContent>
















 * &lt;/complexType>
 * </pre>
 * 






 * 


 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Delays", namespace = "http://datex2.eu/schema/1_0/1_0", propOrder = {
    "delayCoded",
    "delaysType",
    "delayTimeValue",
    "delaysExtension"


})
public class Delays {





    protected DelayCodeEnum delayCoded;
    protected DelaysTypeEnum delaysType;
    protected Float delayTimeValue;







    protected ExtensionType delaysExtension;

    /**




     * Gets the value of the delayCoded property.
     * 


     * @return
     *     possible object is
     *     {@link DelayCodeEnum }
     *     


     */
    public DelayCodeEnum getDelayCoded() {




        return delayCoded;
    }





    /**

     * Sets the value of the delayCoded property.
     * 
     * @param value
     *     allowed object is






     *     {@link DelayCodeEnum }
     *     


     */
    public void setDelayCoded(DelayCodeEnum value) {
        this.delayCoded = value;




    }








    /**
     * Gets the value of the delaysType property.
     * 
     * @return








     *     possible object is
     *     {@link DelaysTypeEnum }






     *     












     */
    public DelaysTypeEnum getDelaysType() {
        return delaysType;




    }

    /**
     * Sets the value of the delaysType property.





     * 


     * @param value

















     *     allowed object is
     *     {@link DelaysTypeEnum }



     *     








     */



    public void setDelaysType(DelaysTypeEnum value) {

        this.delaysType = value;
    }





    /**
     * Gets the value of the delayTimeValue property.
     * 








     * @return
     *     possible object is
     *     {@link Float }
     *     





     */
    public Float getDelayTimeValue() {
        return delayTimeValue;
    }

    /**
     * Sets the value of the delayTimeValue property.

     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setDelayTimeValue(Float value) {
        this.delayTimeValue = value;
    }




    /**
     * Gets the value of the delaysExtension property.
     * 
     * @return
     *     possible object is
     *     {@link ExtensionType }
     *     
     */
    public ExtensionType getDelaysExtension() {
        return delaysExtension;
    }

    /**
     * Sets the value of the delaysExtension property.
     * 



     * @param value
     *     allowed object is
     *     {@link ExtensionType }
     *     
     */
    public void setDelaysExtension(ExtensionType value) {
        this.delaysExtension = value;
    }

}
