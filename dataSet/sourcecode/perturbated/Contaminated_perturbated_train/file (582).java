



package com.transportation.SIRI_IL.SOAP;








import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;





import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;





import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;














/**
 * Type for Delivery for Connection Monitoring Capability.





 * 
 * <p>Java class for ConnectionMonitoringCapabilitiesResponseStructure complex type.
 * 



 * <p>The following schema fragment specifies the expected content contained within this class.
 * 







 * <pre>









 * &lt;complexType name="ConnectionMonitoringCapabilitiesResponseStructure">




 *   &lt;complexContent>
 *     &lt;extension base="{http://www.siri.org.uk/siri}AbstractServiceCapabilitiesResponseStructure">
 *       &lt;sequence>


 *         &lt;element ref="{http://www.siri.org.uk/siri}ConnectionMonitoringServiceCapabilities" minOccurs="0"/>

 *         &lt;element ref="{http://www.siri.org.uk/siri}ConnectionMonitoringPermissions" minOccurs="0"/>

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
@XmlType(name = "ConnectionMonitoringCapabilitiesResponseStructure", propOrder = {
    "connectionMonitoringServiceCapabilities",
    "connectionMonitoringPermissions",
    "extensions"
})
public class ConnectionMonitoringCapabilitiesResponseStructure
    extends AbstractServiceCapabilitiesResponseStructure



{










    @XmlElement(name = "ConnectionMonitoringServiceCapabilities")
    protected ConnectionTimetableServiceCapabilitiesStructure connectionMonitoringServiceCapabilities;
    @XmlElement(name = "ConnectionMonitoringPermissions")









    protected ConnectionMonitoringPermissions connectionMonitoringPermissions;
    @XmlElement(name = "Extensions")
    protected ExtensionsStructure extensions;
    @XmlAttribute(name = "version", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)




    protected String version;

    /**
     * Gets the value of the connectionMonitoringServiceCapabilities property.



     * 
     * @return







     *     possible object is
     *     {@link ConnectionTimetableServiceCapabilitiesStructure }




     *     

     */
    public ConnectionTimetableServiceCapabilitiesStructure getConnectionMonitoringServiceCapabilities() {
        return connectionMonitoringServiceCapabilities;


    }

    /**


     * Sets the value of the connectionMonitoringServiceCapabilities property.



     * 







     * @param value




     *     allowed object is
     *     {@link ConnectionTimetableServiceCapabilitiesStructure }
     *     









     */
    public void setConnectionMonitoringServiceCapabilities(ConnectionTimetableServiceCapabilitiesStructure value) {
        this.connectionMonitoringServiceCapabilities = value;
    }





    /**

     * Gets the value of the connectionMonitoringPermissions property.
     * 
     * @return
     *     possible object is
     *     {@link ConnectionMonitoringPermissions }
     *     








     */
    public ConnectionMonitoringPermissions getConnectionMonitoringPermissions() {


        return connectionMonitoringPermissions;
    }










    /**
     * Sets the value of the connectionMonitoringPermissions property.





     * 
     * @param value


     *     allowed object is
     *     {@link ConnectionMonitoringPermissions }





     *     



     */
    public void setConnectionMonitoringPermissions(ConnectionMonitoringPermissions value) {



        this.connectionMonitoringPermissions = value;
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
