






package ExperianNonLtdBusinessSearchWS;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**









 * <p>Java class for CPSNonLtdSICInfo complex type.


 * 




 * <p>The following schema fragment specifies the expected content contained within this class.








 * 







 * <pre>








 * &lt;complexType name="CPSNonLtdSICInfo">


 *   &lt;complexContent>







 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">


 *       &lt;sequence>
 *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>








 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>




 * </pre>


 * 
 * 






 */


@XmlAccessorType(XmlAccessType.FIELD)





@XmlType(name = "CPSNonLtdSICInfo", propOrder = {
    "type",
    "description"



})
public class CPSNonLtdSICInfo {

    @XmlElement(name = "Type")
    protected String type;
    @XmlElement(name = "Description")
    protected String description;







    /**
     * Gets the value of the type property.
     * 







     * @return
     *     possible object is
     *     {@link String }



     *     


     */
    public String getType() {
        return type;









    }






    /**
     * Sets the value of the type property.
     * 
     * @param value













     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }





    /**



     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }







     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

}
