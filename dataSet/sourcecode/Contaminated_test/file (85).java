
package DBFullReportsClient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CustomerInformationAdvancedType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerInformationAdvancedType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SignonCredentials" type="{http://gateway.dnb.com/getProduct}SignonCredentialsAdvancedType"/>
 *         &lt;element name="EndUserRef" type="{http://gateway.dnb.com/getProduct}EndUserRefType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerInformationAdvancedType", propOrder = {
    "signonCredentials",
    "endUserRef"
})
public class CustomerInformationAdvancedType {

    @XmlElement(name = "SignonCredentials", required = true)
    protected SignonCredentialsAdvancedType signonCredentials;
    @XmlElement(name = "EndUserRef")
    protected String endUserRef;

    /**
     * Gets the value of the signonCredentials property.
     * 
     * @return
     *     possible object is
     *     {@link SignonCredentialsAdvancedType }
     *     
     */
    public SignonCredentialsAdvancedType getSignonCredentials() {
        return signonCredentials;
    }

    /**
     * Sets the value of the signonCredentials property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignonCredentialsAdvancedType }
     *     
     */
    public void setSignonCredentials(SignonCredentialsAdvancedType value) {
        this.signonCredentials = value;
    }

    /**
     * Gets the value of the endUserRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndUserRef() {
        return endUserRef;
    }

    /**
     * Sets the value of the endUserRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndUserRef(String value) {
        this.endUserRef = value;
    }

}
