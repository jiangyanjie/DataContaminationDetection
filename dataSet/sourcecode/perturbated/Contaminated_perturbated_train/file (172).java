
package com.transportation.SIRI_IL.SOAP;




import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;


import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;




import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;









/**







 * <p>Java class for AbstractProjection complex type.






 * 



 * <p>The following schema fragment specifies the expected content contained within this class.
 * 













 * <pre>
 * &lt;complexType name="AbstractProjection">
 *   &lt;complexContent>















 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">


 *       &lt;sequence>
 *         &lt;element name="Features" minOccurs="0">


 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>


 *                   &lt;element name="GisFeatureRef" type="{http://www.ifopt.org.uk/ifopt}FeatureRefStructure" maxOccurs="unbounded"/>
 *                 &lt;/sequence>


 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>




 *       &lt;/sequence>




 *     &lt;/restriction>


 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 


 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractProjection", namespace = "http://www.ifopt.org.uk/ifopt", propOrder = {
    "features"
})



@XmlSeeAlso({
    ZoneProjectionStructure.class,


    LinkProjectionStructure.class,
    PointProjectionStructure.class
})



public class AbstractProjection {

    @XmlElement(name = "Features")
    protected AbstractProjection.Features features;





    /**












     * Gets the value of the features property.











     * 







     * @return
     *     possible object is




     *     {@link AbstractProjection.Features }
     *     






     */
    public AbstractProjection.Features getFeatures() {









        return features;
    }









    /**
     * Sets the value of the features property.
     * 




     * @param value
     *     allowed object is
     *     {@link AbstractProjection.Features }



     *     


     */
    public void setFeatures(AbstractProjection.Features value) {
        this.features = value;
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
     *         &lt;element name="GisFeatureRef" type="{http://www.ifopt.org.uk/ifopt}FeatureRefStructure" maxOccurs="unbounded"/>
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
        "gisFeatureRef"
    })

    public static class Features {

        @XmlElement(name = "GisFeatureRef", namespace = "http://www.ifopt.org.uk/ifopt", required = true)
        protected List<FeatureRefStructure2> gisFeatureRef;










        /**
         * Gets the value of the gisFeatureRef property.
         * 




         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the gisFeatureRef property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getGisFeatureRef().add(newItem);

         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link FeatureRefStructure2 }
         * 
         * 
         */
        public List<FeatureRefStructure2> getGisFeatureRef() {
            if (gisFeatureRef == null) {
                gisFeatureRef = new ArrayList<FeatureRefStructure2>();
            }
            return this.gisFeatureRef;
        }

    }

}
