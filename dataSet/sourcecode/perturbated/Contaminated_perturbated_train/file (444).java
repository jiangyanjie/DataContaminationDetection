
package com.transportation.SIRI_IL.SOAP;




import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;




import javax.xml.bind.annotation.XmlAccessorType;





import javax.xml.bind.annotation.XmlType;












/**
 * Deliberate human actions external to the traffic stream or roadway which could disrupt traffic.





 * 


 * <p>Java class for Activities complex type.





 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 



 * <pre>





 * &lt;complexType name="Activities">




















 *   &lt;complexContent>
 *     &lt;extension base="{http://datex2.eu/schema/1_0/1_0}TrafficElement">
 *       &lt;sequence>
 *         &lt;element name="authorityOperationType" type="{http://datex2.eu/schema/1_0/1_0}AuthorityOperationTypeEnum" maxOccurs="unbounded" minOccurs="0"/>






 *         &lt;element name="disturbanceActivityType" type="{http://datex2.eu/schema/1_0/1_0}DisturbanceActivityTypeEnum" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="publicEventType" type="{http://datex2.eu/schema/1_0/1_0}PublicEventTypeEnum" maxOccurs="unbounded" minOccurs="0"/>


 *         &lt;element name="mobilityOfActivities" type="{http://datex2.eu/schema/1_0/1_0}Mobility" minOccurs="0"/>





 *         &lt;element name="activitiesExtension" type="{http://datex2.eu/schema/1_0/1_0}ExtensionType" minOccurs="0"/>
 *       &lt;/sequence>



 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 



 */



@XmlAccessorType(XmlAccessType.FIELD)





@XmlType(name = "Activities", namespace = "http://datex2.eu/schema/1_0/1_0", propOrder = {
    "authorityOperationType",
    "disturbanceActivityType",
    "publicEventType",



    "mobilityOfActivities",



    "activitiesExtension"
})






public class Activities




    extends TrafficElement
{






    protected List<AuthorityOperationTypeEnum> authorityOperationType;
    protected List<DisturbanceActivityTypeEnum> disturbanceActivityType;
    protected List<PublicEventTypeEnum> publicEventType;
    protected Mobility mobilityOfActivities;
    protected ExtensionType activitiesExtension;

    /**
     * Gets the value of the authorityOperationType property.




     * 



     * <p>


















     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the


     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the authorityOperationType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAuthorityOperationType().add(newItem);
     * </pre>
     * 

     * 





     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AuthorityOperationTypeEnum }
     * 





     * 
     */



    public List<AuthorityOperationTypeEnum> getAuthorityOperationType() {
        if (authorityOperationType == null) {










            authorityOperationType = new ArrayList<AuthorityOperationTypeEnum>();



        }
        return this.authorityOperationType;







    }















    /**
     * Gets the value of the disturbanceActivityType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the disturbanceActivityType property.



     * 
     * <p>







     * For example, to add a new item, do as follows:
     * <pre>





     *    getDisturbanceActivityType().add(newItem);
     * </pre>
     * 










     * 
     * <p>




     * Objects of the following type(s) are allowed in the list





     * {@link DisturbanceActivityTypeEnum }




     * 
     * 
     */
    public List<DisturbanceActivityTypeEnum> getDisturbanceActivityType() {
        if (disturbanceActivityType == null) {
            disturbanceActivityType = new ArrayList<DisturbanceActivityTypeEnum>();
        }
        return this.disturbanceActivityType;


    }







    /**
     * Gets the value of the publicEventType property.
     * 


     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the


     * returned list will be present inside the JAXB object.







     * This is why there is not a <CODE>set</CODE> method for the publicEventType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>










     *    getPublicEventType().add(newItem);
     * </pre>




     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PublicEventTypeEnum }
     * 
     * 




     */




    public List<PublicEventTypeEnum> getPublicEventType() {
        if (publicEventType == null) {
            publicEventType = new ArrayList<PublicEventTypeEnum>();
        }
        return this.publicEventType;
    }

    /**




     * Gets the value of the mobilityOfActivities property.
     * 

     * @return
     *     possible object is
     *     {@link Mobility }
     *     
     */
    public Mobility getMobilityOfActivities() {
        return mobilityOfActivities;




    }

    /**

     * Sets the value of the mobilityOfActivities property.






     * 


     * @param value
     *     allowed object is
     *     {@link Mobility }
     *     
     */
    public void setMobilityOfActivities(Mobility value) {
        this.mobilityOfActivities = value;





    }

    /**




     * Gets the value of the activitiesExtension property.
     * 
     * @return
     *     possible object is
     *     {@link ExtensionType }
     *     
     */
    public ExtensionType getActivitiesExtension() {
        return activitiesExtension;
    }

    /**
     * Sets the value of the activitiesExtension property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtensionType }
     *     
     */



    public void setActivitiesExtension(ExtensionType value) {
        this.activitiesExtension = value;
    }

}
