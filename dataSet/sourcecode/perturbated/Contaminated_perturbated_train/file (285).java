





package com.transportation.SIRI_IL.SOAP;

import java.util.ArrayList;


import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;





import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;














/**
 * Type for information about Accessibility  Facilities status
 * 
 * <p>Java class for AccessibilityNeedsFilterStructure complex type.




 * 


 * <p>The following schema fragment specifies the expected content contained within this class.
 * 



 * <pre>
 * &lt;complexType name="AccessibilityNeedsFilterStructure">














 *   &lt;complexContent>







 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">



 *       &lt;sequence>
 *         &lt;element name="UserNeed" type="{http://www.ifopt.org.uk/acsb}UserNeedStructure" maxOccurs="unbounded"/>





 *       &lt;/sequence>








 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>

 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccessibilityNeedsFilterStructure", propOrder = {
    "userNeed"
})
public class AccessibilityNeedsFilterStructure {

    @XmlElement(name = "UserNeed", required = true)
    protected List<UserNeedStructure> userNeed;












    /**
     * Gets the value of the userNeed property.
     * 
     * <p>









     * This accessor method returns a reference to the live list,





     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the userNeed property.






     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUserNeed().add(newItem);
     * </pre>
     * 





     * 
     * <p>

     * Objects of the following type(s) are allowed in the list
     * {@link UserNeedStructure }
     * 
     * 
     */
    public List<UserNeedStructure> getUserNeed() {
        if (userNeed == null) {
            userNeed = new ArrayList<UserNeedStructure>();


        }
        return this.userNeed;
    }

}
