
package DBGDPV3;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CORPORATE_LINKAGE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CORPORATE_LINKAGE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DUNS_NBR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ENQ_DUNS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OUT_BUS_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PRIM_NME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BR_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BUS_STRU" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LOCN_STAT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HQ_NME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HQ_DUNS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HQ_CTRY_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CTRY_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PNT_NME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PNT_DUNS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PNT_CTRY_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DOM_ULT_PNT_NME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DOM_ULT_PNT_DUNS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DOM_ULT_CTRY_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GBL_ULT_PNT_NME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GBL_ULT_PNT_DUNS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GBL_ULT_CTRY_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CORPORATE_LINKAGE", propOrder = {
    "dunsnbr",
    "enqduns",
    "outbusind",
    "primnme",
    "brind",
    "busstru",
    "locnstat",
    "hqnme",
    "hqduns",
    "hqctrycd",
    "ctrycd",
    "pntnme",
    "pntduns",
    "pntctrycd",
    "domultpntnme",
    "domultpntduns",
    "domultctrycd",
    "gblultpntnme",
    "gblultpntduns",
    "gblultctrycd"
})
public class CORPORATELINKAGE {

    @XmlElementRef(name = "DUNS_NBR", type = JAXBElement.class)
    protected JAXBElement<String> dunsnbr;
    @XmlElementRef(name = "ENQ_DUNS", type = JAXBElement.class)
    protected JAXBElement<String> enqduns;
    @XmlElementRef(name = "OUT_BUS_IND", type = JAXBElement.class)
    protected JAXBElement<String> outbusind;
    @XmlElementRef(name = "PRIM_NME", type = JAXBElement.class)
    protected JAXBElement<String> primnme;
    @XmlElementRef(name = "BR_IND", type = JAXBElement.class)
    protected JAXBElement<String> brind;
    @XmlElementRef(name = "BUS_STRU", type = JAXBElement.class)
    protected JAXBElement<String> busstru;
    @XmlElementRef(name = "LOCN_STAT", type = JAXBElement.class)
    protected JAXBElement<String> locnstat;
    @XmlElementRef(name = "HQ_NME", type = JAXBElement.class)
    protected JAXBElement<String> hqnme;
    @XmlElementRef(name = "HQ_DUNS", type = JAXBElement.class)
    protected JAXBElement<String> hqduns;
    @XmlElementRef(name = "HQ_CTRY_CD", type = JAXBElement.class)
    protected JAXBElement<String> hqctrycd;
    @XmlElementRef(name = "CTRY_CD", type = JAXBElement.class)
    protected JAXBElement<String> ctrycd;
    @XmlElementRef(name = "PNT_NME", type = JAXBElement.class)
    protected JAXBElement<String> pntnme;
    @XmlElementRef(name = "PNT_DUNS", type = JAXBElement.class)
    protected JAXBElement<String> pntduns;
    @XmlElementRef(name = "PNT_CTRY_CD", type = JAXBElement.class)
    protected JAXBElement<String> pntctrycd;
    @XmlElementRef(name = "DOM_ULT_PNT_NME", type = JAXBElement.class)
    protected JAXBElement<String> domultpntnme;
    @XmlElementRef(name = "DOM_ULT_PNT_DUNS", type = JAXBElement.class)
    protected JAXBElement<String> domultpntduns;
    @XmlElementRef(name = "DOM_ULT_CTRY_CD", type = JAXBElement.class)
    protected JAXBElement<String> domultctrycd;
    @XmlElementRef(name = "GBL_ULT_PNT_NME", type = JAXBElement.class)
    protected JAXBElement<String> gblultpntnme;
    @XmlElementRef(name = "GBL_ULT_PNT_DUNS", type = JAXBElement.class)
    protected JAXBElement<String> gblultpntduns;
    @XmlElementRef(name = "GBL_ULT_CTRY_CD", type = JAXBElement.class)
    protected JAXBElement<String> gblultctrycd;

    /**
     * Gets the value of the dunsnbr property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDUNSNBR() {
        return dunsnbr;
    }

    /**
     * Sets the value of the dunsnbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDUNSNBR(JAXBElement<String> value) {
        this.dunsnbr = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the enqduns property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getENQDUNS() {
        return enqduns;
    }

    /**
     * Sets the value of the enqduns property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setENQDUNS(JAXBElement<String> value) {
        this.enqduns = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the outbusind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOUTBUSIND() {
        return outbusind;
    }

    /**
     * Sets the value of the outbusind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOUTBUSIND(JAXBElement<String> value) {
        this.outbusind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the primnme property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPRIMNME() {
        return primnme;
    }

    /**
     * Sets the value of the primnme property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPRIMNME(JAXBElement<String> value) {
        this.primnme = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the brind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getBRIND() {
        return brind;
    }

    /**
     * Sets the value of the brind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setBRIND(JAXBElement<String> value) {
        this.brind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the busstru property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getBUSSTRU() {
        return busstru;
    }

    /**
     * Sets the value of the busstru property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setBUSSTRU(JAXBElement<String> value) {
        this.busstru = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the locnstat property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getLOCNSTAT() {
        return locnstat;
    }

    /**
     * Sets the value of the locnstat property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setLOCNSTAT(JAXBElement<String> value) {
        this.locnstat = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the hqnme property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getHQNME() {
        return hqnme;
    }

    /**
     * Sets the value of the hqnme property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setHQNME(JAXBElement<String> value) {
        this.hqnme = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the hqduns property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getHQDUNS() {
        return hqduns;
    }

    /**
     * Sets the value of the hqduns property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setHQDUNS(JAXBElement<String> value) {
        this.hqduns = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the hqctrycd property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getHQCTRYCD() {
        return hqctrycd;
    }

    /**
     * Sets the value of the hqctrycd property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setHQCTRYCD(JAXBElement<String> value) {
        this.hqctrycd = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the ctrycd property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCTRYCD() {
        return ctrycd;
    }

    /**
     * Sets the value of the ctrycd property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCTRYCD(JAXBElement<String> value) {
        this.ctrycd = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the pntnme property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPNTNME() {
        return pntnme;
    }

    /**
     * Sets the value of the pntnme property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPNTNME(JAXBElement<String> value) {
        this.pntnme = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the pntduns property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPNTDUNS() {
        return pntduns;
    }

    /**
     * Sets the value of the pntduns property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPNTDUNS(JAXBElement<String> value) {
        this.pntduns = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the pntctrycd property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPNTCTRYCD() {
        return pntctrycd;
    }

    /**
     * Sets the value of the pntctrycd property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPNTCTRYCD(JAXBElement<String> value) {
        this.pntctrycd = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the domultpntnme property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDOMULTPNTNME() {
        return domultpntnme;
    }

    /**
     * Sets the value of the domultpntnme property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDOMULTPNTNME(JAXBElement<String> value) {
        this.domultpntnme = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the domultpntduns property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDOMULTPNTDUNS() {
        return domultpntduns;
    }

    /**
     * Sets the value of the domultpntduns property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDOMULTPNTDUNS(JAXBElement<String> value) {
        this.domultpntduns = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the domultctrycd property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDOMULTCTRYCD() {
        return domultctrycd;
    }

    /**
     * Sets the value of the domultctrycd property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDOMULTCTRYCD(JAXBElement<String> value) {
        this.domultctrycd = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the gblultpntnme property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getGBLULTPNTNME() {
        return gblultpntnme;
    }

    /**
     * Sets the value of the gblultpntnme property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setGBLULTPNTNME(JAXBElement<String> value) {
        this.gblultpntnme = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the gblultpntduns property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getGBLULTPNTDUNS() {
        return gblultpntduns;
    }

    /**
     * Sets the value of the gblultpntduns property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setGBLULTPNTDUNS(JAXBElement<String> value) {
        this.gblultpntduns = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the gblultctrycd property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getGBLULTCTRYCD() {
        return gblultctrycd;
    }

    /**
     * Sets the value of the gblultctrycd property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setGBLULTCTRYCD(JAXBElement<String> value) {
        this.gblultctrycd = ((JAXBElement<String> ) value);
    }

}
