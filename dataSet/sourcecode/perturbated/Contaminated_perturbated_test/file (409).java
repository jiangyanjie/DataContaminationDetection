









package DBGDPV3;







import javax.xml.bind.JAXBElement;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;





/**




 * <p>Java class for DATATRNRS3 complex type.
 * 






 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>

 * &lt;complexType name="DATATRNRS3">





 *   &lt;complexContent>





 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TRNUID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>








 *         &lt;element name="STATUS" type="{http://www.dnb.com/DNB_WebServices/Providers/OrderAndInvestigations/GDP_V3/wsp_GDP_V3}STATUS6" minOccurs="0"/>
 *         &lt;element name="DATARS" type="{http://www.dnb.com/DNB_WebServices/Providers/OrderAndInvestigations/GDP_V3/wsp_GDP_V3}DATARS3" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>




 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 






 */









@XmlAccessorType(XmlAccessType.FIELD)




@XmlType(name = "DATATRNRS3", propOrder = {
    "trnuid",
    "status",
    "datars"



})
public class DATATRNRS3 {

    @XmlElementRef(name = "TRNUID", type = JAXBElement.class)
    protected JAXBElement<String> trnuid;
    @XmlElementRef(name = "STATUS", type = JAXBElement.class)
    protected JAXBElement<STATUS6> status;


    @XmlElementRef(name = "DATARS", type = JAXBElement.class)
    protected JAXBElement<DATARS3> datars;

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
     *     {@link JAXBElement }{@code <}{@link STATUS6 }{@code >}
     *     
     */




    public JAXBElement<STATUS6> getSTATUS() {
        return status;
    }








    /**
     * Sets the value of the status property.
     * 
     * @param value



     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link STATUS6 }{@code >}


     *     




     */
    public void setSTATUS(JAXBElement<STATUS6> value) {
        this.status = ((JAXBElement<STATUS6> ) value);
    }





    /**


     * Gets the value of the datars property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link DATARS3 }{@code >}
     *     
     */
    public JAXBElement<DATARS3> getDATARS() {









        return datars;
    }




    /**
     * Sets the value of the datars property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link DATARS3 }{@code >}
     *     
     */
    public void setDATARS(JAXBElement<DATARS3> value) {
        this.datars = ((JAXBElement<DATARS3> ) value);
    }

}
