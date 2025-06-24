
package DBGDPV3;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DATARS3 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DATARS3">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SRVRTID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GDA_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CACHE_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PRD_DT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SECTION" type="{http://www.dnb.com/DNB_WebServices/Providers/OrderAndInvestigations/GDP_V3/wsp_GDP_V3}SECTION4" minOccurs="0"/>
 *         &lt;element name="CORPORATE_LINKAGE" type="{http://www.dnb.com/DNB_WebServices/Providers/OrderAndInvestigations/GDP_V3/wsp_GDP_V3}CORPORATE_LINKAGE" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DATARS3", propOrder = {
    "srvrtid",
    "gdaid",
    "cacheind",
    "prddt",
    "section",
    "corporatelinkage"
})
public class DATARS3 {

    @XmlElementRef(name = "SRVRTID", type = JAXBElement.class)
    protected JAXBElement<String> srvrtid;
    @XmlElementRef(name = "GDA_ID", type = JAXBElement.class)
    protected JAXBElement<String> gdaid;
    @XmlElementRef(name = "CACHE_IND", type = JAXBElement.class)
    protected JAXBElement<String> cacheind;
    @XmlElementRef(name = "PRD_DT", type = JAXBElement.class)
    protected JAXBElement<String> prddt;
    @XmlElementRef(name = "SECTION", type = JAXBElement.class)
    protected JAXBElement<SECTION4> section;
    @XmlElementRef(name = "CORPORATE_LINKAGE", type = JAXBElement.class)
    protected JAXBElement<CORPORATELINKAGE> corporatelinkage;

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
     * Gets the value of the gdaid property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getGDAID() {
        return gdaid;
    }

    /**
     * Sets the value of the gdaid property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setGDAID(JAXBElement<String> value) {
        this.gdaid = ((JAXBElement<String> ) value);
    }

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
     * Gets the value of the section property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link SECTION4 }{@code >}
     *     
     */
    public JAXBElement<SECTION4> getSECTION() {
        return section;
    }

    /**
     * Sets the value of the section property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link SECTION4 }{@code >}
     *     
     */
    public void setSECTION(JAXBElement<SECTION4> value) {
        this.section = ((JAXBElement<SECTION4> ) value);
    }

    /**
     * Gets the value of the corporatelinkage property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link CORPORATELINKAGE }{@code >}
     *     
     */
    public JAXBElement<CORPORATELINKAGE> getCORPORATELINKAGE() {
        return corporatelinkage;
    }

    /**
     * Sets the value of the corporatelinkage property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link CORPORATELINKAGE }{@code >}
     *     
     */
    public void setCORPORATELINKAGE(JAXBElement<CORPORATELINKAGE> value) {
        this.corporatelinkage = ((JAXBElement<CORPORATELINKAGE> ) value);
    }

}
