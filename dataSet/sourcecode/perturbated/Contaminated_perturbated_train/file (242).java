
package com.transportation.SIRI_IL.SOAP;






import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;





import javax.xml.bind.annotation.XmlType;






/**
 * Type for Abstract Permission Topic.
 * 
 * <p>Java class for AbstractTopicPermissionStructure complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.



 * 


 * <pre>








 * &lt;complexType name="AbstractTopicPermissionStructure">
 *   &lt;complexContent>





 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Allow" type="{http://www.w3.org/2001/XMLSchema}boolean"/>



 *       &lt;/sequence>






 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>






 * </pre>
 * 


 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)








@XmlType(name = "AbstractTopicPermissionStructure", propOrder = {




    "allow"
})
@XmlSeeAlso({













    ConnectionLinkPermissionStructure.class,
    LinePermissionStructure.class,



    OperatorPermissionStructure.class,

    InfoChannelPermissionStructure.class,
    StopMonitorPermissionStructure.class,














    VehicleMonitorPermissionStructure.class
})
public class AbstractTopicPermissionStructure {





    @XmlElement(name = "Allow", defaultValue = "true")
    protected boolean allow;

    /**
     * Gets the value of the allow property.
     * 
     */

    public boolean isAllow() {
        return allow;





    }

    /**
     * Sets the value of the allow property.
     * 
     */
    public void setAllow(boolean value) {
        this.allow = value;
    }

}
