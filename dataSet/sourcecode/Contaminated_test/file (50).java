
package ExperianLtdCompanySearchWS;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CurrentSecretary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CurrentSecretary">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ForeignAddressFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SecretaryCompanyFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DirectorNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AppointmentDate" type="{http://schema.uk.experian.com/experian/bi/generic/bsns/v100/basetypes}CCYYMMDD" minOccurs="0"/>
 *         &lt;element name="AppointmentIndicator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NOCRef" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SecretaryDetails" type="{http://schema.uk.experian.com/experian/bi/generic/business/limited/v100/basetypes}SecretaryDetails" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CurrentSecretary", propOrder = {
    "foreignAddressFlag",
    "secretaryCompanyFlag",
    "directorNumber",
    "appointmentDate",
    "appointmentIndicator",
    "nocRef",
    "secretaryDetails"
})
public class CurrentSecretary {

    @XmlElement(name = "ForeignAddressFlag")
    protected String foreignAddressFlag;
    @XmlElement(name = "SecretaryCompanyFlag")
    protected String secretaryCompanyFlag;
    @XmlElement(name = "DirectorNumber")
    protected String directorNumber;
    @XmlElement(name = "AppointmentDate")
    protected CCYYMMDD appointmentDate;
    @XmlElement(name = "AppointmentIndicator")
    protected String appointmentIndicator;
    @XmlElement(name = "NOCRef")
    protected String nocRef;
    @XmlElement(name = "SecretaryDetails")
    protected SecretaryDetails secretaryDetails;

    /**
     * Gets the value of the foreignAddressFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForeignAddressFlag() {
        return foreignAddressFlag;
    }

    /**
     * Sets the value of the foreignAddressFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForeignAddressFlag(String value) {
        this.foreignAddressFlag = value;
    }

    /**
     * Gets the value of the secretaryCompanyFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecretaryCompanyFlag() {
        return secretaryCompanyFlag;
    }

    /**
     * Sets the value of the secretaryCompanyFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecretaryCompanyFlag(String value) {
        this.secretaryCompanyFlag = value;
    }

    /**
     * Gets the value of the directorNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirectorNumber() {
        return directorNumber;
    }

    /**
     * Sets the value of the directorNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirectorNumber(String value) {
        this.directorNumber = value;
    }

    /**
     * Gets the value of the appointmentDate property.
     * 
     * @return
     *     possible object is
     *     {@link CCYYMMDD }
     *     
     */
    public CCYYMMDD getAppointmentDate() {
        return appointmentDate;
    }

    /**
     * Sets the value of the appointmentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link CCYYMMDD }
     *     
     */
    public void setAppointmentDate(CCYYMMDD value) {
        this.appointmentDate = value;
    }

    /**
     * Gets the value of the appointmentIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppointmentIndicator() {
        return appointmentIndicator;
    }

    /**
     * Sets the value of the appointmentIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppointmentIndicator(String value) {
        this.appointmentIndicator = value;
    }

    /**
     * Gets the value of the nocRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOCRef() {
        return nocRef;
    }

    /**
     * Sets the value of the nocRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOCRef(String value) {
        this.nocRef = value;
    }

    /**
     * Gets the value of the secretaryDetails property.
     * 
     * @return
     *     possible object is
     *     {@link SecretaryDetails }
     *     
     */
    public SecretaryDetails getSecretaryDetails() {
        return secretaryDetails;
    }

    /**
     * Sets the value of the secretaryDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link SecretaryDetails }
     *     
     */
    public void setSecretaryDetails(SecretaryDetails value) {
        this.secretaryDetails = value;
    }

}
