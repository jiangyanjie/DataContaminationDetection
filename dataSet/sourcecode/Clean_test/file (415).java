
package DBFullReportsClient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ApplicationAreaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicationAreaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ApplicationIdentifier" type="{http://gateway.dnb.com/getProduct}ApplicationIdentifierType"/>
 *         &lt;element name="RequestCreationDateTime" type="{http://gateway.dnb.com/getProduct}RequestCreationDateTimeType"/>
 *         &lt;element name="UniqueReferenceIdentifier" type="{http://gateway.dnb.com/getProduct}UniqueReferenceIdentifierType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplicationAreaType", propOrder = {
    "applicationIdentifier",
    "requestCreationDateTime",
    "uniqueReferenceIdentifier"
})
public class ApplicationAreaType {

    @XmlElement(name = "ApplicationIdentifier", required = true)
    protected String applicationIdentifier;
    @XmlElement(name = "RequestCreationDateTime", required = true)
    protected XMLGregorianCalendar requestCreationDateTime;
    @XmlElement(name = "UniqueReferenceIdentifier", required = true)
    protected String uniqueReferenceIdentifier;

    /**
     * Gets the value of the applicationIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationIdentifier() {
        return applicationIdentifier;
    }

    /**
     * Sets the value of the applicationIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationIdentifier(String value) {
        this.applicationIdentifier = value;
    }

    /**
     * Gets the value of the requestCreationDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRequestCreationDateTime() {
        return requestCreationDateTime;
    }

    /**
     * Sets the value of the requestCreationDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRequestCreationDateTime(XMLGregorianCalendar value) {
        this.requestCreationDateTime = value;
    }

    /**
     * Gets the value of the uniqueReferenceIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniqueReferenceIdentifier() {
        return uniqueReferenceIdentifier;
    }

    /**
     * Sets the value of the uniqueReferenceIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniqueReferenceIdentifier(String value) {
        this.uniqueReferenceIdentifier = value;
    }

}
