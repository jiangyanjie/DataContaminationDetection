






package com.transportation.SIRI_IL.SOAP;

import java.util.ArrayList;





import java.util.List;




import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;



import javax.xml.bind.annotation.XmlElement;











import javax.xml.bind.annotation.XmlType;






import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;




import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**



 * Type for Delivery for Connection Monitoring.

 * 







 * <p>Java class for ConnectionMonitoringFeederDeliveryStructure complex type.


 * 







 * <p>The following schema fragment specifies the expected content contained within this class.








 * 


 * <pre>
 * &lt;complexType name="ConnectionMonitoringFeederDeliveryStructure">

 *   &lt;complexContent>
 *     &lt;extension base="{http://www.siri.org.uk/siri}AbstractServiceDeliveryStructure">


 *       &lt;sequence>
 *         &lt;group ref="{http://www.siri.org.uk/siri}ConnectionMonitoringFeederPayloadGroup"/>
 *         &lt;element ref="{http://www.siri.org.uk/siri}Extensions" minOccurs="0"/>
 *       &lt;/sequence>


 *       &lt;attribute name="version" use="required" type="{http://www.siri.org.uk/siri}VersionString" fixed="1.3" />
 *     &lt;/extension>











 *   &lt;/complexContent>
 * &lt;/complexType>


 * </pre>
 * 
 * 



 */




@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConnectionMonitoringFeederDeliveryStructure", propOrder = {
    "monitoredFeederArrival",












    "monitoredFeederArrivalCancellation",
    "extensions"
})
public class ConnectionMonitoringFeederDeliveryStructure
    extends AbstractServiceDeliveryStructure



{

    @XmlElement(name = "MonitoredFeederArrival")
    protected List<MonitoredFeederArrivalStructure> monitoredFeederArrival;













    @XmlElement(name = "MonitoredFeederArrivalCancellation")
    protected List<MonitoredFeederArrivalCancellationStructure> monitoredFeederArrivalCancellation;
    @XmlElement(name = "Extensions")
    protected ExtensionsStructure extensions;









    @XmlAttribute(name = "version", required = true)



    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String version;



    /**



     * Gets the value of the monitoredFeederArrival property.



     * 







     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the monitoredFeederArrival property.
     * 
     * <p>
     * For example, to add a new item, do as follows:




     * <pre>


     *    getMonitoredFeederArrival().add(newItem);
     * </pre>
     * 
     * 








     * <p>
     * Objects of the following type(s) are allowed in the list



     * {@link MonitoredFeederArrivalStructure }











     * 
     * 
     */
    public List<MonitoredFeederArrivalStructure> getMonitoredFeederArrival() {
        if (monitoredFeederArrival == null) {




            monitoredFeederArrival = new ArrayList<MonitoredFeederArrivalStructure>();







        }
        return this.monitoredFeederArrival;
    }







    /**


     * Gets the value of the monitoredFeederArrivalCancellation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,






     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the monitoredFeederArrivalCancellation property.
     * 




     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMonitoredFeederArrivalCancellation().add(newItem);




     * </pre>
     * 
     * 



     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MonitoredFeederArrivalCancellationStructure }









     * 
     * 
     */

    public List<MonitoredFeederArrivalCancellationStructure> getMonitoredFeederArrivalCancellation() {
        if (monitoredFeederArrivalCancellation == null) {
            monitoredFeederArrivalCancellation = new ArrayList<MonitoredFeederArrivalCancellationStructure>();





        }
        return this.monitoredFeederArrivalCancellation;
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







    /**





     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        if (version == null) {
            return "1.3";
        } else {
            return version;


        }
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

}
