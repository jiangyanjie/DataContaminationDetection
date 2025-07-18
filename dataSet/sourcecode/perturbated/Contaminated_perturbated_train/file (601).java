




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
 * Data type for Delivery for Connection TimetableService.
 * 






 * <p>Java class for ConnectionTimetableCapabilitiesResponseStructure complex type.




 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 


 * <pre>
 * &lt;complexType name="ConnectionTimetableCapabilitiesResponseStructure">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.siri.org.uk/siri}AbstractServiceCapabilitiesResponseStructure">









 *       &lt;sequence>
 *         &lt;element ref="{http://www.siri.org.uk/siri}ConnectionTimetableServiceCapabilities" minOccurs="0"/>
 *         &lt;element name="ConnectionTimetablePermissions" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://www.siri.org.uk/siri}PermissionsStructure">

 *                 &lt;sequence>










 *                   &lt;element name="ConnectionTimetablePermission" type="{http://www.siri.org.uk/siri}ConnectionServicePermissionStructure" maxOccurs="unbounded" minOccurs="0"/>




 *                 &lt;/sequence>




 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>







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
@XmlType(name = "ConnectionTimetableCapabilitiesResponseStructure", propOrder = {
    "connectionTimetableServiceCapabilities",
    "connectionTimetablePermissions",










    "extensions"




})
public class ConnectionTimetableCapabilitiesResponseStructure




    extends AbstractServiceCapabilitiesResponseStructure



{

    @XmlElement(name = "ConnectionTimetableServiceCapabilities")
    protected ConnectionTimetableServiceCapabilitiesStructure connectionTimetableServiceCapabilities;
    @XmlElement(name = "ConnectionTimetablePermissions")
    protected ConnectionTimetableCapabilitiesResponseStructure.ConnectionTimetablePermissions connectionTimetablePermissions;
    @XmlElement(name = "Extensions")



    protected ExtensionsStructure extensions;
    @XmlAttribute(name = "version", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String version;

    /**
     * Gets the value of the connectionTimetableServiceCapabilities property.
     * 
     * @return
     *     possible object is
     *     {@link ConnectionTimetableServiceCapabilitiesStructure }




     *     
     */
    public ConnectionTimetableServiceCapabilitiesStructure getConnectionTimetableServiceCapabilities() {
        return connectionTimetableServiceCapabilities;
    }




    /**
     * Sets the value of the connectionTimetableServiceCapabilities property.






     * 

     * @param value









     *     allowed object is
     *     {@link ConnectionTimetableServiceCapabilitiesStructure }




     *     
     */
    public void setConnectionTimetableServiceCapabilities(ConnectionTimetableServiceCapabilitiesStructure value) {




        this.connectionTimetableServiceCapabilities = value;
    }









    /**






     * Gets the value of the connectionTimetablePermissions property.
     * 



     * @return
     *     possible object is


















     *     {@link ConnectionTimetableCapabilitiesResponseStructure.ConnectionTimetablePermissions }
     *     










     */
    public ConnectionTimetableCapabilitiesResponseStructure.ConnectionTimetablePermissions getConnectionTimetablePermissions() {
        return connectionTimetablePermissions;
    }

    /**
     * Sets the value of the connectionTimetablePermissions property.

















     * 


     * @param value
     *     allowed object is
     *     {@link ConnectionTimetableCapabilitiesResponseStructure.ConnectionTimetablePermissions }
     *     
     */
    public void setConnectionTimetablePermissions(ConnectionTimetableCapabilitiesResponseStructure.ConnectionTimetablePermissions value) {
        this.connectionTimetablePermissions = value;
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









    /**
     * <p>Java class for anonymous complex type.





     * 
     * <p>The following schema fragment specifies the expected content contained within this class.


     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://www.siri.org.uk/siri}PermissionsStructure">
     *       &lt;sequence>


     *         &lt;element name="ConnectionTimetablePermission" type="{http://www.siri.org.uk/siri}ConnectionServicePermissionStructure" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/extension>
     *   &lt;/complexContent>




     * &lt;/complexType>
     * </pre>
     * 
     * 
     */




    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "connectionTimetablePermission"
    })






    public static class ConnectionTimetablePermissions
        extends PermissionsStructure
    {

        @XmlElement(name = "ConnectionTimetablePermission")
        protected List<ConnectionServicePermissionStructure> connectionTimetablePermission;

        /**
         * Gets the value of the connectionTimetablePermission property.
         * 


         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the connectionTimetablePermission property.


         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getConnectionTimetablePermission().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ConnectionServicePermissionStructure }
         * 
         * 
         */
        public List<ConnectionServicePermissionStructure> getConnectionTimetablePermission() {
            if (connectionTimetablePermission == null) {
                connectionTimetablePermission = new ArrayList<ConnectionServicePermissionStructure>();
            }
            return this.connectionTimetablePermission;
        }

    }

}
