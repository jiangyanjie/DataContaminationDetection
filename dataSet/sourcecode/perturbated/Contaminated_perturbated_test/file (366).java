
package com.transportation.SIRI_IL.SOAP;








import javax.xml.bind.annotation.XmlAccessType;

import javax.xml.bind.annotation.XmlAccessorType;




import javax.xml.bind.annotation.XmlElement;


import javax.xml.bind.annotation.XmlType;
















/**
 * Type for Data ready Acknowledgement Response. 



 * 
 * <p>Java class for DataReadyResponseStructure complex type.












 * 



 * <p>The following schema fragment specifies the expected content contained within this class.



 * 
 * <pre>


 * &lt;complexType name="DataReadyResponseStructure">
 *   &lt;complexContent>

 *     &lt;extension base="{http://www.siri.org.uk/siri}ConsumerResponseEndpointStructure">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.siri.org.uk/siri}Status" minOccurs="0"/>


 *         &lt;element name="ErrorCondition" minOccurs="0">



 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">


 *                 &lt;sequence>




 *                   &lt;choice>
 *                     &lt;element ref="{http://www.siri.org.uk/siri}UnknownSubscriptionError"/>



 *                     &lt;element ref="{http://www.siri.org.uk/siri}OtherError"/>
 *                   &lt;/choice>
 *                   &lt;sequence>
 *                     &lt;element name="Description" type="{http://www.siri.org.uk/siri}ErrorDescriptionStructure" minOccurs="0"/>
 *                   &lt;/sequence>




 *                 &lt;/sequence>



 *               &lt;/restriction>





 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>



 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>



 * &lt;/complexType>
 * </pre>
 * 

 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)








@XmlType(name = "DataReadyResponseStructure", propOrder = {
    "status",
    "errorCondition"
})
public class DataReadyResponseStructure
    extends ConsumerResponseEndpointStructure
{

    @XmlElement(name = "Status", defaultValue = "true")




    protected Boolean status;
    @XmlElement(name = "ErrorCondition")








    protected DataReadyResponseStructure.ErrorCondition errorCondition;








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
     *     {@link DataReadyResponseStructure.ErrorCondition }











     *     








     */
    public DataReadyResponseStructure.ErrorCondition getErrorCondition() {
        return errorCondition;
    }







    /**
     * Sets the value of the errorCondition property.
     * 
     * @param value

     *     allowed object is



     *     {@link DataReadyResponseStructure.ErrorCondition }
     *     
     */
    public void setErrorCondition(DataReadyResponseStructure.ErrorCondition value) {
        this.errorCondition = value;



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








     *         &lt;choice>





     *           &lt;element ref="{http://www.siri.org.uk/siri}UnknownSubscriptionError"/>




     *           &lt;element ref="{http://www.siri.org.uk/siri}OtherError"/>


     *         &lt;/choice>
     *         &lt;sequence>
     *           &lt;element name="Description" type="{http://www.siri.org.uk/siri}ErrorDescriptionStructure" minOccurs="0"/>
     *         &lt;/sequence>
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




        "unknownSubscriptionError",

        "otherError",


        "description"
    })





    public static class ErrorCondition {





        @XmlElement(name = "UnknownSubscriptionError")
        protected UnknownSubscriptionErrorStructure unknownSubscriptionError;
        @XmlElement(name = "OtherError")
        protected OtherErrorStructure otherError;
        @XmlElement(name = "Description")



        protected ErrorDescriptionStructure description;



        /**
         * Gets the value of the unknownSubscriptionError property.
         * 
         * @return
         *     possible object is
         *     {@link UnknownSubscriptionErrorStructure }





         *     
         */
        public UnknownSubscriptionErrorStructure getUnknownSubscriptionError() {
            return unknownSubscriptionError;


        }

        /**
         * Sets the value of the unknownSubscriptionError property.



         * 
         * @param value
         *     allowed object is
         *     {@link UnknownSubscriptionErrorStructure }

         *     
         */


        public void setUnknownSubscriptionError(UnknownSubscriptionErrorStructure value) {
            this.unknownSubscriptionError = value;
        }

        /**
         * Gets the value of the otherError property.
         * 
         * @return
         *     possible object is
         *     {@link OtherErrorStructure }
         *     










         */
        public OtherErrorStructure getOtherError() {
            return otherError;
        }






        /**
         * Sets the value of the otherError property.
         * 


         * @param value
         *     allowed object is
         *     {@link OtherErrorStructure }
         *     




         */
        public void setOtherError(OtherErrorStructure value) {
            this.otherError = value;
        }

        /**
         * Gets the value of the description property.
         * 
         * @return
         *     possible object is
         *     {@link ErrorDescriptionStructure }
         *     
         */
        public ErrorDescriptionStructure getDescription() {
            return description;
        }



        /**



         * Sets the value of the description property.
         * 

         * @param value
         *     allowed object is
         *     {@link ErrorDescriptionStructure }
         *     
         */
        public void setDescription(ErrorDescriptionStructure value) {
            this.description = value;
        }

    }

}
