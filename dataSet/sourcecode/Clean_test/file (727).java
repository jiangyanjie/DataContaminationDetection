
package DBFullReportsClient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CourtInformationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CourtInformationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CourtName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CourtAddress" type="{http://gateway.dnb.com/getProduct}AddressType" minOccurs="0"/>
 *         &lt;element name="CourtType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AttorneyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourtInformationType", propOrder = {
    "courtName",
    "courtAddress",
    "courtType",
    "attorneyName"
})
public class CourtInformationType {

    @XmlElement(name = "CourtName")
    protected String courtName;
    @XmlElement(name = "CourtAddress")
    protected AddressType courtAddress;
    @XmlElement(name = "CourtType")
    protected String courtType;
    @XmlElement(name = "AttorneyName")
    protected String attorneyName;

    /**
     * Gets the value of the courtName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCourtName() {
        return courtName;
    }

    /**
     * Sets the value of the courtName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCourtName(String value) {
        this.courtName = value;
    }

    /**
     * Gets the value of the courtAddress property.
     * 
     * @return
     *     possible object is
     *     {@link AddressType }
     *     
     */
    public AddressType getCourtAddress() {
        return courtAddress;
    }

    /**
     * Sets the value of the courtAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressType }
     *     
     */
    public void setCourtAddress(AddressType value) {
        this.courtAddress = value;
    }

    /**
     * Gets the value of the courtType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCourtType() {
        return courtType;
    }

    /**
     * Sets the value of the courtType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCourtType(String value) {
        this.courtType = value;
    }

    /**
     * Gets the value of the attorneyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttorneyName() {
        return attorneyName;
    }

    /**
     * Sets the value of the attorneyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttorneyName(String value) {
        this.attorneyName = value;
    }

}
