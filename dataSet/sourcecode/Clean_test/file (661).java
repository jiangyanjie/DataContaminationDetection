
package ExperianLtdCompanySearchWS;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CorporateStructure complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CorporateStructure">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CompanyOwnership" type="{http://schema.uk.experian.com/experian/bi/generic/business/limited/v100/basetypes}CompanyOwnership" minOccurs="0"/>
 *         &lt;element name="Subsidiaries" type="{http://schema.uk.experian.com/experian/bi/generic/business/limited/v100/basetypes}Subsidiaries" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CorporateStructure", propOrder = {
    "companyOwnership",
    "subsidiaries"
})
public class CorporateStructure {

    @XmlElement(name = "CompanyOwnership")
    protected CompanyOwnership companyOwnership;
    @XmlElement(name = "Subsidiaries")
    protected Subsidiaries subsidiaries;

    /**
     * Gets the value of the companyOwnership property.
     * 
     * @return
     *     possible object is
     *     {@link CompanyOwnership }
     *     
     */
    public CompanyOwnership getCompanyOwnership() {
        return companyOwnership;
    }

    /**
     * Sets the value of the companyOwnership property.
     * 
     * @param value
     *     allowed object is
     *     {@link CompanyOwnership }
     *     
     */
    public void setCompanyOwnership(CompanyOwnership value) {
        this.companyOwnership = value;
    }

    /**
     * Gets the value of the subsidiaries property.
     * 
     * @return
     *     possible object is
     *     {@link Subsidiaries }
     *     
     */
    public Subsidiaries getSubsidiaries() {
        return subsidiaries;
    }

    /**
     * Sets the value of the subsidiaries property.
     * 
     * @param value
     *     allowed object is
     *     {@link Subsidiaries }
     *     
     */
    public void setSubsidiaries(Subsidiaries value) {
        this.subsidiaries = value;
    }

}
