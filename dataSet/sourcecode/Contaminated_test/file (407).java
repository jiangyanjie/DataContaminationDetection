
package DBGDPV3;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DATATRNRS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DATATRNRS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TRNUID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="STATUS" type="{http://www.dnb.com/DNB_WebServices/Providers/OrderAndInvestigations/GDP_V3/wsp_GDP_V3}STATUS2" minOccurs="0"/>
 *         &lt;element name="INVESTRS" type="{http://www.dnb.com/DNB_WebServices/Providers/OrderAndInvestigations/GDP_V3/wsp_GDP_V3}INVESTRS" minOccurs="0"/>
 *         &lt;element name="TEXTRS" type="{http://www.dnb.com/DNB_WebServices/Providers/OrderAndInvestigations/GDP_V3/wsp_GDP_V3}TEXTRS" minOccurs="0"/>
 *         &lt;element name="DATARS" type="{http://www.dnb.com/DNB_WebServices/Providers/OrderAndInvestigations/GDP_V3/wsp_GDP_V3}DATARS" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DATATRNRS", propOrder = {
    "trnuid",
    "status",
    "investrs",
    "textrs",
    "datars"
})
public class DATATRNRS {

    @XmlElementRef(name = "TRNUID", type = JAXBElement.class)
    protected JAXBElement<String> trnuid;
    @XmlElementRef(name = "STATUS", type = JAXBElement.class)
    protected JAXBElement<STATUS2> status;
    @XmlElementRef(name = "INVESTRS", type = JAXBElement.class)
    protected JAXBElement<INVESTRS> investrs;
    @XmlElementRef(name = "TEXTRS", type = JAXBElement.class)
    protected JAXBElement<TEXTRS> textrs;
    @XmlElementRef(name = "DATARS", type = JAXBElement.class)
    protected JAXBElement<DATARS> datars;

    /**
     * Gets the value of the trnuid property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTRNUID() {
        return trnuid;
    }

    /**
     * Sets the value of the trnuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTRNUID(JAXBElement<String> value) {
        this.trnuid = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link STATUS2 }{@code >}
     *     
     */
    public JAXBElement<STATUS2> getSTATUS() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link STATUS2 }{@code >}
     *     
     */
    public void setSTATUS(JAXBElement<STATUS2> value) {
        this.status = ((JAXBElement<STATUS2> ) value);
    }

    /**
     * Gets the value of the investrs property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link INVESTRS }{@code >}
     *     
     */
    public JAXBElement<INVESTRS> getINVESTRS() {
        return investrs;
    }

    /**
     * Sets the value of the investrs property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link INVESTRS }{@code >}
     *     
     */
    public void setINVESTRS(JAXBElement<INVESTRS> value) {
        this.investrs = ((JAXBElement<INVESTRS> ) value);
    }

    /**
     * Gets the value of the textrs property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link TEXTRS }{@code >}
     *     
     */
    public JAXBElement<TEXTRS> getTEXTRS() {
        return textrs;
    }

    /**
     * Sets the value of the textrs property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link TEXTRS }{@code >}
     *     
     */
    public void setTEXTRS(JAXBElement<TEXTRS> value) {
        this.textrs = ((JAXBElement<TEXTRS> ) value);
    }

    /**
     * Gets the value of the datars property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link DATARS }{@code >}
     *     
     */
    public JAXBElement<DATARS> getDATARS() {
        return datars;
    }

    /**
     * Sets the value of the datars property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link DATARS }{@code >}
     *     
     */
    public void setDATARS(JAXBElement<DATARS> value) {
        this.datars = ((JAXBElement<DATARS> ) value);
    }

}
