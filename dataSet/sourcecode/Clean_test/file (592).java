
package ExperianLtdCompanySearchWS;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ConvictionDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConvictionDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Date" type="{http://schema.uk.experian.com/experian/bi/generic/bsns/v100/basetypes}CCYYMMDD" minOccurs="0"/>
 *         &lt;element name="Reason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Fine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Costs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConvictionDetails", propOrder = {
    "date",
    "reason",
    "fine",
    "costs"
})
public class ConvictionDetails {

    @XmlElement(name = "Date")
    protected CCYYMMDD date;
    @XmlElement(name = "Reason")
    protected String reason;
    @XmlElement(name = "Fine")
    protected String fine;
    @XmlElement(name = "Costs")
    protected String costs;

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link CCYYMMDD }
     *     
     */
    public CCYYMMDD getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link CCYYMMDD }
     *     
     */
    public void setDate(CCYYMMDD value) {
        this.date = value;
    }

    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReason(String value) {
        this.reason = value;
    }

    /**
     * Gets the value of the fine property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFine() {
        return fine;
    }

    /**
     * Sets the value of the fine property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFine(String value) {
        this.fine = value;
    }

    /**
     * Gets the value of the costs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCosts() {
        return costs;
    }

    /**
     * Sets the value of the costs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCosts(String value) {
        this.costs = value;
    }

}
