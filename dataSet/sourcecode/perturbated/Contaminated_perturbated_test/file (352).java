









package com.transportation.SIRI_IL.SOAP;





import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;







/**


 * Name spaces.




 * 
 * <p>Java class for DataNameSpacesStructure complex type.

 * 







 * <p>The following schema fragment specifies the expected content contained within this class.








 * 


 * <pre>
 * &lt;complexType name="DataNameSpacesStructure">





 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="StopPointNameSpace" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="LineNameSpace" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="ProductCategoryNameSpace" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="ServiceFeatureNameSpace" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>

 *         &lt;element name="VehicleFeatureNameSpace" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>



 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>

 * </pre>




 * 




 * 
 */



@XmlAccessorType(XmlAccessType.FIELD)


@XmlType(name = "DataNameSpacesStructure", propOrder = {
    "stopPointNameSpace",
    "lineNameSpace",
    "productCategoryNameSpace",
    "serviceFeatureNameSpace",
    "vehicleFeatureNameSpace"
})
public class DataNameSpacesStructure {

    @XmlElement(name = "StopPointNameSpace")
    @XmlSchemaType(name = "anyURI")
    protected String stopPointNameSpace;




    @XmlElement(name = "LineNameSpace")




    @XmlSchemaType(name = "anyURI")


    protected String lineNameSpace;


    @XmlElement(name = "ProductCategoryNameSpace")
    @XmlSchemaType(name = "anyURI")
    protected String productCategoryNameSpace;
    @XmlElement(name = "ServiceFeatureNameSpace")
    @XmlSchemaType(name = "anyURI")







    protected String serviceFeatureNameSpace;



    @XmlElement(name = "VehicleFeatureNameSpace")
    @XmlSchemaType(name = "anyURI")
    protected String vehicleFeatureNameSpace;


    /**
     * Gets the value of the stopPointNameSpace property.
     * 
     * @return
     *     possible object is




     *     {@link String }









     *     




     */
    public String getStopPointNameSpace() {
        return stopPointNameSpace;









    }




    /**




     * Sets the value of the stopPointNameSpace property.
     * 



     * @param value
     *     allowed object is


     *     {@link String }
     *     






     */


    public void setStopPointNameSpace(String value) {





        this.stopPointNameSpace = value;
    }
















    /**




     * Gets the value of the lineNameSpace property.










     * 






     * @return
     *     possible object is
     *     {@link String }
     *     







     */





    public String getLineNameSpace() {
        return lineNameSpace;
    }








    /**
     * Sets the value of the lineNameSpace property.

     * 







     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLineNameSpace(String value) {


        this.lineNameSpace = value;

    }

    /**


     * Gets the value of the productCategoryNameSpace property.


     * 
     * @return















     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductCategoryNameSpace() {
        return productCategoryNameSpace;


    }



    /**
     * Sets the value of the productCategoryNameSpace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     




     */




    public void setProductCategoryNameSpace(String value) {
        this.productCategoryNameSpace = value;
    }

    /**

     * Gets the value of the serviceFeatureNameSpace property.








     * 
     * @return





     *     possible object is
     *     {@link String }
     *     
     */




    public String getServiceFeatureNameSpace() {




        return serviceFeatureNameSpace;
    }

    /**
     * Sets the value of the serviceFeatureNameSpace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceFeatureNameSpace(String value) {
        this.serviceFeatureNameSpace = value;
    }












    /**
     * Gets the value of the vehicleFeatureNameSpace property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVehicleFeatureNameSpace() {
        return vehicleFeatureNameSpace;
    }





    /**
     * Sets the value of the vehicleFeatureNameSpace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */




    public void setVehicleFeatureNameSpace(String value) {
        this.vehicleFeatureNameSpace = value;
    }

}
