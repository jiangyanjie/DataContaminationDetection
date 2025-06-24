
package ExperianLtdCompanySearchWS;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DebtorsDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DebtorsDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Debtors" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="AccountsReceivableTradeDebtors" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="DebtorsSubsidiaryAssociateJoint" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="PrepaymentsAccruals" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="DebtorsGroupLoans" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="DebtorsDirectorLoans" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="OtherDebtors" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DebtorsDetails", propOrder = {
    "debtors",
    "accountsReceivableTradeDebtors",
    "debtorsSubsidiaryAssociateJoint",
    "prepaymentsAccruals",
    "debtorsGroupLoans",
    "debtorsDirectorLoans",
    "otherDebtors"
})
public class DebtorsDetails {

    @XmlElement(name = "Debtors")
    protected Long debtors;
    @XmlElement(name = "AccountsReceivableTradeDebtors")
    protected Long accountsReceivableTradeDebtors;
    @XmlElement(name = "DebtorsSubsidiaryAssociateJoint")
    protected Long debtorsSubsidiaryAssociateJoint;
    @XmlElement(name = "PrepaymentsAccruals")
    protected Long prepaymentsAccruals;
    @XmlElement(name = "DebtorsGroupLoans")
    protected Long debtorsGroupLoans;
    @XmlElement(name = "DebtorsDirectorLoans")
    protected Long debtorsDirectorLoans;
    @XmlElement(name = "OtherDebtors")
    protected Long otherDebtors;

    /**
     * Gets the value of the debtors property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDebtors() {
        return debtors;
    }

    /**
     * Sets the value of the debtors property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDebtors(Long value) {
        this.debtors = value;
    }

    /**
     * Gets the value of the accountsReceivableTradeDebtors property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAccountsReceivableTradeDebtors() {
        return accountsReceivableTradeDebtors;
    }

    /**
     * Sets the value of the accountsReceivableTradeDebtors property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAccountsReceivableTradeDebtors(Long value) {
        this.accountsReceivableTradeDebtors = value;
    }

    /**
     * Gets the value of the debtorsSubsidiaryAssociateJoint property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDebtorsSubsidiaryAssociateJoint() {
        return debtorsSubsidiaryAssociateJoint;
    }

    /**
     * Sets the value of the debtorsSubsidiaryAssociateJoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDebtorsSubsidiaryAssociateJoint(Long value) {
        this.debtorsSubsidiaryAssociateJoint = value;
    }

    /**
     * Gets the value of the prepaymentsAccruals property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPrepaymentsAccruals() {
        return prepaymentsAccruals;
    }

    /**
     * Sets the value of the prepaymentsAccruals property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPrepaymentsAccruals(Long value) {
        this.prepaymentsAccruals = value;
    }

    /**
     * Gets the value of the debtorsGroupLoans property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDebtorsGroupLoans() {
        return debtorsGroupLoans;
    }

    /**
     * Sets the value of the debtorsGroupLoans property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDebtorsGroupLoans(Long value) {
        this.debtorsGroupLoans = value;
    }

    /**
     * Gets the value of the debtorsDirectorLoans property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDebtorsDirectorLoans() {
        return debtorsDirectorLoans;
    }

    /**
     * Sets the value of the debtorsDirectorLoans property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDebtorsDirectorLoans(Long value) {
        this.debtorsDirectorLoans = value;
    }

    /**
     * Gets the value of the otherDebtors property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getOtherDebtors() {
        return otherDebtors;
    }

    /**
     * Sets the value of the otherDebtors property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setOtherDebtors(Long value) {
        this.otherDebtors = value;
    }

}
