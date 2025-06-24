
package ExperianNonLtdBusinessSearchWS;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DebitMonthly complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DebitMonthly">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ExpMonth" type="{http://schema.uk.experian.com/experian/bi/generic/bsns/v100/basetypes}CCYYMM" minOccurs="0"/>
 *         &lt;element name="DBT" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="NumExp" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DebitMonthly", propOrder = {
    "expMonth",
    "dbt",
    "numExp"
})
public class DebitMonthly {

    @XmlElement(name = "ExpMonth")
    protected CCYYMM expMonth;
    @XmlElement(name = "DBT")
    protected Integer dbt;
    @XmlElement(name = "NumExp")
    protected Integer numExp;

    /**
     * Gets the value of the expMonth property.
     * 
     * @return
     *     possible object is
     *     {@link CCYYMM }
     *     
     */
    public CCYYMM getExpMonth() {
        return expMonth;
    }

    /**
     * Sets the value of the expMonth property.
     * 
     * @param value
     *     allowed object is
     *     {@link CCYYMM }
     *     
     */
    public void setExpMonth(CCYYMM value) {
        this.expMonth = value;
    }

    /**
     * Gets the value of the dbt property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDBT() {
        return dbt;
    }

    /**
     * Sets the value of the dbt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDBT(Integer value) {
        this.dbt = value;
    }

    /**
     * Gets the value of the numExp property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumExp() {
        return numExp;
    }

    /**
     * Sets the value of the numExp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumExp(Integer value) {
        this.numExp = value;
    }

}
