
package ExperianLtdCompanySearchWS;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreditorsDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreditorsDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Creditors" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="AccountsPayableTradeCreditors" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="SubsidiaryAssociateJoint" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="GroupLoans" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="DirectorLoans" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="AccrualsDeferredIncome" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="SocialSecurityVAT" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="TaxationPayable" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="OtherCreditors" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditorsDetails", propOrder = {
    "creditors",
    "accountsPayableTradeCreditors",
    "subsidiaryAssociateJoint",
    "groupLoans",
    "directorLoans",
    "accrualsDeferredIncome",
    "socialSecurityVAT",
    "taxationPayable",
    "otherCreditors"
})
public class CreditorsDetails {

    @XmlElement(name = "Creditors")
    protected Long creditors;
    @XmlElement(name = "AccountsPayableTradeCreditors")
    protected Long accountsPayableTradeCreditors;
    @XmlElement(name = "SubsidiaryAssociateJoint")
    protected Long subsidiaryAssociateJoint;
    @XmlElement(name = "GroupLoans")
    protected Long groupLoans;
    @XmlElement(name = "DirectorLoans")
    protected Long directorLoans;
    @XmlElement(name = "AccrualsDeferredIncome")
    protected Long accrualsDeferredIncome;
    @XmlElement(name = "SocialSecurityVAT")
    protected Long socialSecurityVAT;
    @XmlElement(name = "TaxationPayable")
    protected Long taxationPayable;
    @XmlElement(name = "OtherCreditors")
    protected Long otherCreditors;

    /**
     * Gets the value of the creditors property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCreditors() {
        return creditors;
    }

    /**
     * Sets the value of the creditors property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCreditors(Long value) {
        this.creditors = value;
    }

    /**
     * Gets the value of the accountsPayableTradeCreditors property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAccountsPayableTradeCreditors() {
        return accountsPayableTradeCreditors;
    }

    /**
     * Sets the value of the accountsPayableTradeCreditors property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAccountsPayableTradeCreditors(Long value) {
        this.accountsPayableTradeCreditors = value;
    }

    /**
     * Gets the value of the subsidiaryAssociateJoint property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSubsidiaryAssociateJoint() {
        return subsidiaryAssociateJoint;
    }

    /**
     * Sets the value of the subsidiaryAssociateJoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSubsidiaryAssociateJoint(Long value) {
        this.subsidiaryAssociateJoint = value;
    }

    /**
     * Gets the value of the groupLoans property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getGroupLoans() {
        return groupLoans;
    }

    /**
     * Sets the value of the groupLoans property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setGroupLoans(Long value) {
        this.groupLoans = value;
    }

    /**
     * Gets the value of the directorLoans property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDirectorLoans() {
        return directorLoans;
    }

    /**
     * Sets the value of the directorLoans property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDirectorLoans(Long value) {
        this.directorLoans = value;
    }

    /**
     * Gets the value of the accrualsDeferredIncome property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAccrualsDeferredIncome() {
        return accrualsDeferredIncome;
    }

    /**
     * Sets the value of the accrualsDeferredIncome property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAccrualsDeferredIncome(Long value) {
        this.accrualsDeferredIncome = value;
    }

    /**
     * Gets the value of the socialSecurityVAT property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSocialSecurityVAT() {
        return socialSecurityVAT;
    }

    /**
     * Sets the value of the socialSecurityVAT property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSocialSecurityVAT(Long value) {
        this.socialSecurityVAT = value;
    }

    /**
     * Gets the value of the taxationPayable property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTaxationPayable() {
        return taxationPayable;
    }

    /**
     * Sets the value of the taxationPayable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTaxationPayable(Long value) {
        this.taxationPayable = value;
    }

    /**
     * Gets the value of the otherCreditors property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getOtherCreditors() {
        return otherCreditors;
    }

    /**
     * Sets the value of the otherCreditors property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setOtherCreditors(Long value) {
        this.otherCreditors = value;
    }

}
