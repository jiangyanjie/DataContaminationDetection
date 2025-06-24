
package ExperianLtdCompanySearchWS;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CurrentDirectors complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CurrentDirectors">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ForeignAddressFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DirectorCompanyFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DirectorNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AppointmentDate" type="{http://schema.uk.experian.com/experian/bi/generic/bsns/v100/basetypes}CCYYMMDD" minOccurs="0"/>
 *         &lt;element name="AppointmentIndicator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DirectorshipLength" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LatestReturns" type="{http://schema.uk.experian.com/experian/bi/generic/bsns/v100/basetypes}CCYYMMDD" minOccurs="0"/>
 *         &lt;element name="NOCRef" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NumberConvictions" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ConvictionDetails" type="{http://schema.uk.experian.com/experian/bi/generic/business/limited/v100/basetypes}ConvictionDetails" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="DirectorDetails" type="{http://schema.uk.experian.com/experian/bi/generic/business/limited/v100/basetypes}DirectorDetails" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CurrentDirectors", propOrder = {
    "foreignAddressFlag",
    "directorCompanyFlag",
    "directorNumber",
    "appointmentDate",
    "appointmentIndicator",
    "directorshipLength",
    "latestReturns",
    "nocRef",
    "numberConvictions",
    "convictionDetails",
    "directorDetails"
})
public class CurrentDirectors {

    @XmlElement(name = "ForeignAddressFlag")
    protected String foreignAddressFlag;
    @XmlElement(name = "DirectorCompanyFlag")
    protected String directorCompanyFlag;
    @XmlElement(name = "DirectorNumber")
    protected String directorNumber;
    @XmlElement(name = "AppointmentDate")
    protected CCYYMMDD appointmentDate;
    @XmlElement(name = "AppointmentIndicator")
    protected String appointmentIndicator;
    @XmlElement(name = "DirectorshipLength")
    protected String directorshipLength;
    @XmlElement(name = "LatestReturns")
    protected CCYYMMDD latestReturns;
    @XmlElement(name = "NOCRef")
    protected String nocRef;
    @XmlElement(name = "NumberConvictions")
    protected Integer numberConvictions;
    @XmlElement(name = "ConvictionDetails")
    protected List<ConvictionDetails> convictionDetails;
    @XmlElement(name = "DirectorDetails")
    protected DirectorDetails directorDetails;

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
     * Gets the value of the directorCompanyFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirectorCompanyFlag() {
        return directorCompanyFlag;
    }

    /**
     * Sets the value of the directorCompanyFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirectorCompanyFlag(String value) {
        this.directorCompanyFlag = value;
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
     * Gets the value of the directorshipLength property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirectorshipLength() {
        return directorshipLength;
    }

    /**
     * Sets the value of the directorshipLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirectorshipLength(String value) {
        this.directorshipLength = value;
    }

    /**
     * Gets the value of the latestReturns property.
     * 
     * @return
     *     possible object is
     *     {@link CCYYMMDD }
     *     
     */
    public CCYYMMDD getLatestReturns() {
        return latestReturns;
    }

    /**
     * Sets the value of the latestReturns property.
     * 
     * @param value
     *     allowed object is
     *     {@link CCYYMMDD }
     *     
     */
    public void setLatestReturns(CCYYMMDD value) {
        this.latestReturns = value;
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
     * Gets the value of the numberConvictions property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumberConvictions() {
        return numberConvictions;
    }

    /**
     * Sets the value of the numberConvictions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumberConvictions(Integer value) {
        this.numberConvictions = value;
    }

    /**
     * Gets the value of the convictionDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the convictionDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConvictionDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConvictionDetails }
     * 
     * 
     */
    public List<ConvictionDetails> getConvictionDetails() {
        if (convictionDetails == null) {
            convictionDetails = new ArrayList<ConvictionDetails>();
        }
        return this.convictionDetails;
    }

    /**
     * Gets the value of the directorDetails property.
     * 
     * @return
     *     possible object is
     *     {@link DirectorDetails }
     *     
     */
    public DirectorDetails getDirectorDetails() {
        return directorDetails;
    }

    /**
     * Sets the value of the directorDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link DirectorDetails }
     *     
     */
    public void setDirectorDetails(DirectorDetails value) {
        this.directorDetails = value;
    }

}
