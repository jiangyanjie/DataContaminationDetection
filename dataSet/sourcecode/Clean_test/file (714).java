
package DBFullReportsClient;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CountyCourtJudgmentSummaryType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CountyCourtJudgmentSummaryType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TotalJudgmentsQuantity" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="TotalJudgmentsAmount" type="{http://gateway.dnb.com/getProduct}AmountType" minOccurs="0"/>
 *         &lt;element name="TotalClosedJudgmentsQuantity" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="TotalClosedJudgmentsAmount" type="{http://gateway.dnb.com/getProduct}AmountType" minOccurs="0"/>
 *         &lt;element name="YearwiseJudgmentsSummaryEntry" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Year" type="{http://gateway.dnb.com/getProduct}DNBDate" minOccurs="0"/>
 *                   &lt;element name="JudgmentsQuantity" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                   &lt;element name="JudgmentsAmount" type="{http://gateway.dnb.com/getProduct}AmountType" minOccurs="0"/>
 *                   &lt;element name="ClosedJudgmentsQuantity" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                   &lt;element name="ClosedJudgmentsAmount" type="{http://gateway.dnb.com/getProduct}AmountType" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CountyCourtJudgmentSummaryType", propOrder = {
    "totalJudgmentsQuantity",
    "totalJudgmentsAmount",
    "totalClosedJudgmentsQuantity",
    "totalClosedJudgmentsAmount",
    "yearwiseJudgmentsSummaryEntry"
})
public class CountyCourtJudgmentSummaryType {

    @XmlElement(name = "TotalJudgmentsQuantity")
    protected BigInteger totalJudgmentsQuantity;
    @XmlElement(name = "TotalJudgmentsAmount")
    protected AmountType totalJudgmentsAmount;
    @XmlElement(name = "TotalClosedJudgmentsQuantity")
    protected BigInteger totalClosedJudgmentsQuantity;
    @XmlElement(name = "TotalClosedJudgmentsAmount")
    protected AmountType totalClosedJudgmentsAmount;
    @XmlElement(name = "YearwiseJudgmentsSummaryEntry")
    protected List<CountyCourtJudgmentSummaryType.YearwiseJudgmentsSummaryEntry> yearwiseJudgmentsSummaryEntry;

    /**
     * Gets the value of the totalJudgmentsQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTotalJudgmentsQuantity() {
        return totalJudgmentsQuantity;
    }

    /**
     * Sets the value of the totalJudgmentsQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTotalJudgmentsQuantity(BigInteger value) {
        this.totalJudgmentsQuantity = value;
    }

    /**
     * Gets the value of the totalJudgmentsAmount property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getTotalJudgmentsAmount() {
        return totalJudgmentsAmount;
    }

    /**
     * Sets the value of the totalJudgmentsAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setTotalJudgmentsAmount(AmountType value) {
        this.totalJudgmentsAmount = value;
    }

    /**
     * Gets the value of the totalClosedJudgmentsQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTotalClosedJudgmentsQuantity() {
        return totalClosedJudgmentsQuantity;
    }

    /**
     * Sets the value of the totalClosedJudgmentsQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTotalClosedJudgmentsQuantity(BigInteger value) {
        this.totalClosedJudgmentsQuantity = value;
    }

    /**
     * Gets the value of the totalClosedJudgmentsAmount property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getTotalClosedJudgmentsAmount() {
        return totalClosedJudgmentsAmount;
    }

    /**
     * Sets the value of the totalClosedJudgmentsAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setTotalClosedJudgmentsAmount(AmountType value) {
        this.totalClosedJudgmentsAmount = value;
    }

    /**
     * Gets the value of the yearwiseJudgmentsSummaryEntry property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the yearwiseJudgmentsSummaryEntry property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getYearwiseJudgmentsSummaryEntry().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CountyCourtJudgmentSummaryType.YearwiseJudgmentsSummaryEntry }
     * 
     * 
     */
    public List<CountyCourtJudgmentSummaryType.YearwiseJudgmentsSummaryEntry> getYearwiseJudgmentsSummaryEntry() {
        if (yearwiseJudgmentsSummaryEntry == null) {
            yearwiseJudgmentsSummaryEntry = new ArrayList<CountyCourtJudgmentSummaryType.YearwiseJudgmentsSummaryEntry>();
        }
        return this.yearwiseJudgmentsSummaryEntry;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Year" type="{http://gateway.dnb.com/getProduct}DNBDate" minOccurs="0"/>
     *         &lt;element name="JudgmentsQuantity" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *         &lt;element name="JudgmentsAmount" type="{http://gateway.dnb.com/getProduct}AmountType" minOccurs="0"/>
     *         &lt;element name="ClosedJudgmentsQuantity" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *         &lt;element name="ClosedJudgmentsAmount" type="{http://gateway.dnb.com/getProduct}AmountType" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "year",
        "judgmentsQuantity",
        "judgmentsAmount",
        "closedJudgmentsQuantity",
        "closedJudgmentsAmount"
    })
    public static class YearwiseJudgmentsSummaryEntry {

        @XmlElement(name = "Year")
        protected DNBDate year;
        @XmlElement(name = "JudgmentsQuantity")
        protected BigInteger judgmentsQuantity;
        @XmlElement(name = "JudgmentsAmount")
        protected AmountType judgmentsAmount;
        @XmlElement(name = "ClosedJudgmentsQuantity")
        protected BigInteger closedJudgmentsQuantity;
        @XmlElement(name = "ClosedJudgmentsAmount")
        protected AmountType closedJudgmentsAmount;

        /**
         * Gets the value of the year property.
         * 
         * @return
         *     possible object is
         *     {@link DNBDate }
         *     
         */
        public DNBDate getYear() {
            return year;
        }

        /**
         * Sets the value of the year property.
         * 
         * @param value
         *     allowed object is
         *     {@link DNBDate }
         *     
         */
        public void setYear(DNBDate value) {
            this.year = value;
        }

        /**
         * Gets the value of the judgmentsQuantity property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getJudgmentsQuantity() {
            return judgmentsQuantity;
        }

        /**
         * Sets the value of the judgmentsQuantity property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setJudgmentsQuantity(BigInteger value) {
            this.judgmentsQuantity = value;
        }

        /**
         * Gets the value of the judgmentsAmount property.
         * 
         * @return
         *     possible object is
         *     {@link AmountType }
         *     
         */
        public AmountType getJudgmentsAmount() {
            return judgmentsAmount;
        }

        /**
         * Sets the value of the judgmentsAmount property.
         * 
         * @param value
         *     allowed object is
         *     {@link AmountType }
         *     
         */
        public void setJudgmentsAmount(AmountType value) {
            this.judgmentsAmount = value;
        }

        /**
         * Gets the value of the closedJudgmentsQuantity property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getClosedJudgmentsQuantity() {
            return closedJudgmentsQuantity;
        }

        /**
         * Sets the value of the closedJudgmentsQuantity property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setClosedJudgmentsQuantity(BigInteger value) {
            this.closedJudgmentsQuantity = value;
        }

        /**
         * Gets the value of the closedJudgmentsAmount property.
         * 
         * @return
         *     possible object is
         *     {@link AmountType }
         *     
         */
        public AmountType getClosedJudgmentsAmount() {
            return closedJudgmentsAmount;
        }

        /**
         * Sets the value of the closedJudgmentsAmount property.
         * 
         * @param value
         *     allowed object is
         *     {@link AmountType }
         *     
         */
        public void setClosedJudgmentsAmount(AmountType value) {
            this.closedJudgmentsAmount = value;
        }

    }

}
