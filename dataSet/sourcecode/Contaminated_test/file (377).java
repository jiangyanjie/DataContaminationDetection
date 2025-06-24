
package DBGDPV3;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DATARS4 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DATARS4">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CACHE_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PRD_DT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SRVRTID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RPT" type="{http://www.dnb.com/DNB_WebServices/Providers/OrderAndInvestigations/GDP_V3/wsp_GDP_V3}RPT" minOccurs="0"/>
 *         &lt;element name="CUST_INP_DATA" type="{http://www.dnb.com/DNB_WebServices/Providers/OrderAndInvestigations/GDP_V3/wsp_GDP_V3}CUST_INP_DATA" minOccurs="0"/>
 *         &lt;element name="UPD_FLDS" type="{http://www.dnb.com/DNB_WebServices/Providers/OrderAndInvestigations/GDP_V3/wsp_GDP_V3}UPD_FLDS" minOccurs="0"/>
 *         &lt;element name="KEY_FLDS" type="{http://www.dnb.com/DNB_WebServices/Providers/OrderAndInvestigations/GDP_V3/wsp_GDP_V3}KEY_FLDS" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DATARS4", propOrder = {
    "cacheind",
    "prddt",
    "srvrtid",
    "rpt",
    "custinpdata",
    "updflds",
    "keyflds"
})
public class DATARS4 {

    @XmlElementRef(name = "CACHE_IND", type = JAXBElement.class)
    protected JAXBElement<String> cacheind;
    @XmlElementRef(name = "PRD_DT", type = JAXBElement.class)
    protected JAXBElement<String> prddt;
    @XmlElementRef(name = "SRVRTID", type = JAXBElement.class)
    protected JAXBElement<String> srvrtid;
    @XmlElementRef(name = "RPT", type = JAXBElement.class)
    protected JAXBElement<RPT> rpt;
    @XmlElementRef(name = "CUST_INP_DATA", type = JAXBElement.class)
    protected JAXBElement<CUSTINPDATA> custinpdata;
    @XmlElementRef(name = "UPD_FLDS", type = JAXBElement.class)
    protected JAXBElement<UPDFLDS> updflds;
    @XmlElementRef(name = "KEY_FLDS", type = JAXBElement.class)
    protected JAXBElement<KEYFLDS> keyflds;

    /**
     * Gets the value of the cacheind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCACHEIND() {
        return cacheind;
    }

    /**
     * Sets the value of the cacheind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCACHEIND(JAXBElement<String> value) {
        this.cacheind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the prddt property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPRDDT() {
        return prddt;
    }

    /**
     * Sets the value of the prddt property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPRDDT(JAXBElement<String> value) {
        this.prddt = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the srvrtid property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSRVRTID() {
        return srvrtid;
    }

    /**
     * Sets the value of the srvrtid property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSRVRTID(JAXBElement<String> value) {
        this.srvrtid = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the rpt property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RPT }{@code >}
     *     
     */
    public JAXBElement<RPT> getRPT() {
        return rpt;
    }

    /**
     * Sets the value of the rpt property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RPT }{@code >}
     *     
     */
    public void setRPT(JAXBElement<RPT> value) {
        this.rpt = ((JAXBElement<RPT> ) value);
    }

    /**
     * Gets the value of the custinpdata property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link CUSTINPDATA }{@code >}
     *     
     */
    public JAXBElement<CUSTINPDATA> getCUSTINPDATA() {
        return custinpdata;
    }

    /**
     * Sets the value of the custinpdata property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link CUSTINPDATA }{@code >}
     *     
     */
    public void setCUSTINPDATA(JAXBElement<CUSTINPDATA> value) {
        this.custinpdata = ((JAXBElement<CUSTINPDATA> ) value);
    }

    /**
     * Gets the value of the updflds property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link UPDFLDS }{@code >}
     *     
     */
    public JAXBElement<UPDFLDS> getUPDFLDS() {
        return updflds;
    }

    /**
     * Sets the value of the updflds property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link UPDFLDS }{@code >}
     *     
     */
    public void setUPDFLDS(JAXBElement<UPDFLDS> value) {
        this.updflds = ((JAXBElement<UPDFLDS> ) value);
    }

    /**
     * Gets the value of the keyflds property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link KEYFLDS }{@code >}
     *     
     */
    public JAXBElement<KEYFLDS> getKEYFLDS() {
        return keyflds;
    }

    /**
     * Sets the value of the keyflds property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link KEYFLDS }{@code >}
     *     
     */
    public void setKEYFLDS(JAXBElement<KEYFLDS> value) {
        this.keyflds = ((JAXBElement<KEYFLDS> ) value);
    }

}
