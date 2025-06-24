
package ExperianLtdCompanySearchWS;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CROChanges complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CROChanges">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NumCROChanges" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="CROChangeDetail" type="{http://schema.uk.experian.com/experian/bi/generic/business/limited/v100/basetypes}CRO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CROChanges", propOrder = {
    "numCROChanges",
    "croChangeDetail"
})
public class CROChanges {

    @XmlElement(name = "NumCROChanges")
    protected Integer numCROChanges;
    @XmlElement(name = "CROChangeDetail")
    protected List<CRO> croChangeDetail;

    /**
     * Gets the value of the numCROChanges property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumCROChanges() {
        return numCROChanges;
    }

    /**
     * Sets the value of the numCROChanges property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumCROChanges(Integer value) {
        this.numCROChanges = value;
    }

    /**
     * Gets the value of the croChangeDetail property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the croChangeDetail property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCROChangeDetail().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CRO }
     * 
     * 
     */
    public List<CRO> getCROChangeDetail() {
        if (croChangeDetail == null) {
            croChangeDetail = new ArrayList<CRO>();
        }
        return this.croChangeDetail;
    }

}
