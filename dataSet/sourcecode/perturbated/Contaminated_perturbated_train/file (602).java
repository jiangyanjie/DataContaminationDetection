



package com.transportation.SIRI_IL.SOAP;








import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**




 * Type for capabaility request.
 * 




 * <p>Java class for ConnectionTimetableCapabilityRequestPolicyStructure complex type.


 * 







 * <p>The following schema fragment specifies the expected content contained within this class.
 * 




 * <pre>
 * &lt;complexType name="ConnectionTimetableCapabilityRequestPolicyStructure">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.siri.org.uk/siri}CapabilityRequestPolicyStructure">
 *       &lt;sequence>
 *         &lt;element name="ForeignJourneysOnly" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>








 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>















 * &lt;/complexType>
 * </pre>
 * 
 * 












 */




@XmlAccessorType(XmlAccessType.FIELD)








@XmlType(name = "ConnectionTimetableCapabilityRequestPolicyStructure", propOrder = {
    "foreignJourneysOnly"
})
public class ConnectionTimetableCapabilityRequestPolicyStructure





    extends CapabilityRequestPolicyStructure
{

    @XmlElement(name = "ForeignJourneysOnly")
    protected Boolean foreignJourneysOnly;


    /**


     * Gets the value of the foreignJourneysOnly property.
     * 
     * @return


     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isForeignJourneysOnly() {




        return foreignJourneysOnly;
    }

    /**

     * Sets the value of the foreignJourneysOnly property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setForeignJourneysOnly(Boolean value) {
        this.foreignJourneysOnly = value;
    }

}
