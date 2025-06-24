
package DBGDPV3;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DCSN_INFO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DCSN_INFO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CR_DCSN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DCSN_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RPT_REQ_USERID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RPT_GNRT_DT_TME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ACTN_MSG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="INP_CRCY_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="REQ_CR_AMT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OTSD_CR_BAL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="USER_DEF_1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="USER_DEF_N" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CR_LMT_CRCY_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CR_LMT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BILB_IND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RSK_BAND" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DM_AGE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CURR_CNTL_AGE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DM_RSK_RATG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DM_SIC_TYPE_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DM_SIC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DM_NET_WRTH" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DM_SLS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CR_RULE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CR_RULE_VER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DCSN_INFO", propOrder = {
    "crdcsn",
    "dcsnid",
    "rptrequserid",
    "rptgnrtdttme",
    "actnmsg",
    "inpcrcycd",
    "reqcramt",
    "otsdcrbal",
    "userdef1",
    "userdefn",
    "crlmtcrcycd",
    "crlmt",
    "bilbind",
    "rskband",
    "dmage",
    "currcntlage",
    "dmrskratg",
    "dmsictypecd",
    "dmsic",
    "dmnetwrth",
    "dmsls",
    "crrule",
    "crrulever"
})
public class DCSNINFO {

    @XmlElementRef(name = "CR_DCSN", type = JAXBElement.class)
    protected JAXBElement<String> crdcsn;
    @XmlElementRef(name = "DCSN_ID", type = JAXBElement.class)
    protected JAXBElement<String> dcsnid;
    @XmlElementRef(name = "RPT_REQ_USERID", type = JAXBElement.class)
    protected JAXBElement<String> rptrequserid;
    @XmlElementRef(name = "RPT_GNRT_DT_TME", type = JAXBElement.class)
    protected JAXBElement<String> rptgnrtdttme;
    @XmlElementRef(name = "ACTN_MSG", type = JAXBElement.class)
    protected JAXBElement<String> actnmsg;
    @XmlElementRef(name = "INP_CRCY_CD", type = JAXBElement.class)
    protected JAXBElement<String> inpcrcycd;
    @XmlElementRef(name = "REQ_CR_AMT", type = JAXBElement.class)
    protected JAXBElement<String> reqcramt;
    @XmlElementRef(name = "OTSD_CR_BAL", type = JAXBElement.class)
    protected JAXBElement<String> otsdcrbal;
    @XmlElementRef(name = "USER_DEF_1", type = JAXBElement.class)
    protected JAXBElement<String> userdef1;
    @XmlElementRef(name = "USER_DEF_N", type = JAXBElement.class)
    protected JAXBElement<String> userdefn;
    @XmlElementRef(name = "CR_LMT_CRCY_CD", type = JAXBElement.class)
    protected JAXBElement<String> crlmtcrcycd;
    @XmlElementRef(name = "CR_LMT", type = JAXBElement.class)
    protected JAXBElement<String> crlmt;
    @XmlElementRef(name = "BILB_IND", type = JAXBElement.class)
    protected JAXBElement<String> bilbind;
    @XmlElementRef(name = "RSK_BAND", type = JAXBElement.class)
    protected JAXBElement<String> rskband;
    @XmlElementRef(name = "DM_AGE", type = JAXBElement.class)
    protected JAXBElement<String> dmage;
    @XmlElementRef(name = "CURR_CNTL_AGE", type = JAXBElement.class)
    protected JAXBElement<String> currcntlage;
    @XmlElementRef(name = "DM_RSK_RATG", type = JAXBElement.class)
    protected JAXBElement<String> dmrskratg;
    @XmlElementRef(name = "DM_SIC_TYPE_CD", type = JAXBElement.class)
    protected JAXBElement<String> dmsictypecd;
    @XmlElementRef(name = "DM_SIC", type = JAXBElement.class)
    protected JAXBElement<String> dmsic;
    @XmlElementRef(name = "DM_NET_WRTH", type = JAXBElement.class)
    protected JAXBElement<String> dmnetwrth;
    @XmlElementRef(name = "DM_SLS", type = JAXBElement.class)
    protected JAXBElement<String> dmsls;
    @XmlElementRef(name = "CR_RULE", type = JAXBElement.class)
    protected JAXBElement<String> crrule;
    @XmlElementRef(name = "CR_RULE_VER", type = JAXBElement.class)
    protected JAXBElement<String> crrulever;

    /**
     * Gets the value of the crdcsn property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCRDCSN() {
        return crdcsn;
    }

    /**
     * Sets the value of the crdcsn property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCRDCSN(JAXBElement<String> value) {
        this.crdcsn = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the dcsnid property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDCSNID() {
        return dcsnid;
    }

    /**
     * Sets the value of the dcsnid property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDCSNID(JAXBElement<String> value) {
        this.dcsnid = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the rptrequserid property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRPTREQUSERID() {
        return rptrequserid;
    }

    /**
     * Sets the value of the rptrequserid property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRPTREQUSERID(JAXBElement<String> value) {
        this.rptrequserid = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the rptgnrtdttme property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRPTGNRTDTTME() {
        return rptgnrtdttme;
    }

    /**
     * Sets the value of the rptgnrtdttme property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRPTGNRTDTTME(JAXBElement<String> value) {
        this.rptgnrtdttme = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the actnmsg property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getACTNMSG() {
        return actnmsg;
    }

    /**
     * Sets the value of the actnmsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setACTNMSG(JAXBElement<String> value) {
        this.actnmsg = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the inpcrcycd property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINPCRCYCD() {
        return inpcrcycd;
    }

    /**
     * Sets the value of the inpcrcycd property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINPCRCYCD(JAXBElement<String> value) {
        this.inpcrcycd = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the reqcramt property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getREQCRAMT() {
        return reqcramt;
    }

    /**
     * Sets the value of the reqcramt property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setREQCRAMT(JAXBElement<String> value) {
        this.reqcramt = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the otsdcrbal property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOTSDCRBAL() {
        return otsdcrbal;
    }

    /**
     * Sets the value of the otsdcrbal property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOTSDCRBAL(JAXBElement<String> value) {
        this.otsdcrbal = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the userdef1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getUSERDEF1() {
        return userdef1;
    }

    /**
     * Sets the value of the userdef1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setUSERDEF1(JAXBElement<String> value) {
        this.userdef1 = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the userdefn property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getUSERDEFN() {
        return userdefn;
    }

    /**
     * Sets the value of the userdefn property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setUSERDEFN(JAXBElement<String> value) {
        this.userdefn = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the crlmtcrcycd property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCRLMTCRCYCD() {
        return crlmtcrcycd;
    }

    /**
     * Sets the value of the crlmtcrcycd property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCRLMTCRCYCD(JAXBElement<String> value) {
        this.crlmtcrcycd = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the crlmt property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCRLMT() {
        return crlmt;
    }

    /**
     * Sets the value of the crlmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCRLMT(JAXBElement<String> value) {
        this.crlmt = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the bilbind property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getBILBIND() {
        return bilbind;
    }

    /**
     * Sets the value of the bilbind property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setBILBIND(JAXBElement<String> value) {
        this.bilbind = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the rskband property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRSKBAND() {
        return rskband;
    }

    /**
     * Sets the value of the rskband property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRSKBAND(JAXBElement<String> value) {
        this.rskband = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the dmage property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDMAGE() {
        return dmage;
    }

    /**
     * Sets the value of the dmage property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDMAGE(JAXBElement<String> value) {
        this.dmage = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the currcntlage property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCURRCNTLAGE() {
        return currcntlage;
    }

    /**
     * Sets the value of the currcntlage property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCURRCNTLAGE(JAXBElement<String> value) {
        this.currcntlage = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the dmrskratg property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDMRSKRATG() {
        return dmrskratg;
    }

    /**
     * Sets the value of the dmrskratg property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDMRSKRATG(JAXBElement<String> value) {
        this.dmrskratg = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the dmsictypecd property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDMSICTYPECD() {
        return dmsictypecd;
    }

    /**
     * Sets the value of the dmsictypecd property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDMSICTYPECD(JAXBElement<String> value) {
        this.dmsictypecd = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the dmsic property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDMSIC() {
        return dmsic;
    }

    /**
     * Sets the value of the dmsic property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDMSIC(JAXBElement<String> value) {
        this.dmsic = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the dmnetwrth property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDMNETWRTH() {
        return dmnetwrth;
    }

    /**
     * Sets the value of the dmnetwrth property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDMNETWRTH(JAXBElement<String> value) {
        this.dmnetwrth = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the dmsls property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDMSLS() {
        return dmsls;
    }

    /**
     * Sets the value of the dmsls property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDMSLS(JAXBElement<String> value) {
        this.dmsls = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the crrule property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCRRULE() {
        return crrule;
    }

    /**
     * Sets the value of the crrule property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCRRULE(JAXBElement<String> value) {
        this.crrule = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the crrulever property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCRRULEVER() {
        return crrulever;
    }

    /**
     * Sets the value of the crrulever property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCRRULEVER(JAXBElement<String> value) {
        this.crrulever = ((JAXBElement<String> ) value);
    }

}
