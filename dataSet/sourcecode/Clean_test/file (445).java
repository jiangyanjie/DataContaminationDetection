
package DBLookUpClient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CONT_CNST_SVCS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CONT_CNST_SVCS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PAST_24HRS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PAST_1_TO_2_DAYS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PAST_3_TO_7_DAYS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PAST_8_TO_14_DAYS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PAST_15_TO_30_DAYS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PAST_31_TO_60_DAYS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PAST_61_TO_90_DAYS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PAST_91_TO_180_DAYS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TOT_SRCH" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CONT_CNST_SVCS", propOrder = {
    "past24HRS",
    "past1TO2DAYS",
    "past3TO7DAYS",
    "past8TO14DAYS",
    "past15TO30DAYS",
    "past31TO60DAYS",
    "past61TO90DAYS",
    "past91TO180DAYS",
    "totsrch"
})
public class CONTCNSTSVCS {

    @XmlElement(name = "PAST_24HRS", required = true, nillable = true)
    protected String past24HRS;
    @XmlElement(name = "PAST_1_TO_2_DAYS", required = true, nillable = true)
    protected String past1TO2DAYS;
    @XmlElement(name = "PAST_3_TO_7_DAYS", required = true, nillable = true)
    protected String past3TO7DAYS;
    @XmlElement(name = "PAST_8_TO_14_DAYS", required = true, nillable = true)
    protected String past8TO14DAYS;
    @XmlElement(name = "PAST_15_TO_30_DAYS", required = true, nillable = true)
    protected String past15TO30DAYS;
    @XmlElement(name = "PAST_31_TO_60_DAYS", required = true, nillable = true)
    protected String past31TO60DAYS;
    @XmlElement(name = "PAST_61_TO_90_DAYS", required = true, nillable = true)
    protected String past61TO90DAYS;
    @XmlElement(name = "PAST_91_TO_180_DAYS", required = true, nillable = true)
    protected String past91TO180DAYS;
    @XmlElement(name = "TOT_SRCH", required = true, nillable = true)
    protected String totsrch;

    /**
     * Gets the value of the past24HRS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPAST24HRS() {
        return past24HRS;
    }

    /**
     * Sets the value of the past24HRS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPAST24HRS(String value) {
        this.past24HRS = value;
    }

    /**
     * Gets the value of the past1TO2DAYS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPAST1TO2DAYS() {
        return past1TO2DAYS;
    }

    /**
     * Sets the value of the past1TO2DAYS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPAST1TO2DAYS(String value) {
        this.past1TO2DAYS = value;
    }

    /**
     * Gets the value of the past3TO7DAYS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPAST3TO7DAYS() {
        return past3TO7DAYS;
    }

    /**
     * Sets the value of the past3TO7DAYS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPAST3TO7DAYS(String value) {
        this.past3TO7DAYS = value;
    }

    /**
     * Gets the value of the past8TO14DAYS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPAST8TO14DAYS() {
        return past8TO14DAYS;
    }

    /**
     * Sets the value of the past8TO14DAYS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPAST8TO14DAYS(String value) {
        this.past8TO14DAYS = value;
    }

    /**
     * Gets the value of the past15TO30DAYS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPAST15TO30DAYS() {
        return past15TO30DAYS;
    }

    /**
     * Sets the value of the past15TO30DAYS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPAST15TO30DAYS(String value) {
        this.past15TO30DAYS = value;
    }

    /**
     * Gets the value of the past31TO60DAYS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPAST31TO60DAYS() {
        return past31TO60DAYS;
    }

    /**
     * Sets the value of the past31TO60DAYS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPAST31TO60DAYS(String value) {
        this.past31TO60DAYS = value;
    }

    /**
     * Gets the value of the past61TO90DAYS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPAST61TO90DAYS() {
        return past61TO90DAYS;
    }

    /**
     * Sets the value of the past61TO90DAYS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPAST61TO90DAYS(String value) {
        this.past61TO90DAYS = value;
    }

    /**
     * Gets the value of the past91TO180DAYS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPAST91TO180DAYS() {
        return past91TO180DAYS;
    }

    /**
     * Sets the value of the past91TO180DAYS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPAST91TO180DAYS(String value) {
        this.past91TO180DAYS = value;
    }

    /**
     * Gets the value of the totsrch property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTOTSRCH() {
        return totsrch;
    }

    /**
     * Sets the value of the totsrch property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTOTSRCH(String value) {
        this.totsrch = value;
    }

}
