







package com.transportation.SIRI_IL.SOAP;





import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;




import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;








/**
 * Type for Data supply Request
 * 
 * <p>Java class for DataSupplyRequestStructure complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 






 * <pre>
 * &lt;complexType name="DataSupplyRequestStructure">



 *   &lt;complexContent>
 *     &lt;extension base="{http://www.siri.org.uk/siri}ConsumerRequestEndpointStructure">






 *       &lt;sequence>
 *         &lt;group ref="{http://www.siri.org.uk/siri}DataSupplyTopicGroup"/>
 *       &lt;/sequence>
 *     &lt;/extension>


 *   &lt;/complexContent>



 * &lt;/complexType>
 * </pre>
 * 
 * 















 */




@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataSupplyRequestStructure", propOrder = {
    "notificationRef",




    "allData"
})
public class DataSupplyRequestStructure







    extends ConsumerRequestEndpointStructure
{

    @XmlElement(name = "NotificationRef")
    protected MessageRefStructure notificationRef;



    @XmlElement(name = "AllData", defaultValue = "false")





    protected Boolean allData;



    /**

     * Gets the value of the notificationRef property.



     * 




     * @return
     *     possible object is
     *     {@link MessageRefStructure }

     *     
     */







    public MessageRefStructure getNotificationRef() {


        return notificationRef;


    }












    /**







     * Sets the value of the notificationRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageRefStructure }
     *     
     */
    public void setNotificationRef(MessageRefStructure value) {
        this.notificationRef = value;









    }

    /**
     * Gets the value of the allData property.
     * 



     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */







    public Boolean isAllData() {
        return allData;
    }

    /**
     * Sets the value of the allData property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAllData(Boolean value) {




        this.allData = value;
    }

}
