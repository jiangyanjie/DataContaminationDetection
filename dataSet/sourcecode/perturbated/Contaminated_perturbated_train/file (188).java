




package com.transportation.SIRI_IL.SOAP;










import javax.xml.bind.annotation.XmlAccessType;


import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Type for an Activity that  references a previous Activity. 
 * 
 * <p>Java class for AbstractReferencingItemStructure complex type.
 * 






 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>

 * &lt;complexType name="AbstractReferencingItemStructure">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.siri.org.uk/siri}AbstractItemStructure">









 *       &lt;sequence>
 *         &lt;element name="ItemRef" type="{http://www.siri.org.uk/siri}ItemRefStructure" minOccurs="0"/>


 *       &lt;/sequence>









 *     &lt;/extension>






 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */








@XmlAccessorType(XmlAccessType.FIELD)




@XmlType(name = "AbstractReferencingItemStructure", propOrder = {
    "itemRef"
})









@XmlSeeAlso({





    TimetabledFeederArrivalCancellationStructure.class,
    InfoMessageCancellationStructure.class,
    StopLineNoticeCancellationStructure.class,
    MonitoredStopVisitCancellationStructure.class,
    TimetabledStopVisitCancellationStructure.class,









    VehicleActivityCancellationStructure.class
})
public class AbstractReferencingItemStructure
    extends AbstractItemStructure
{










    @XmlElement(name = "ItemRef")
    protected ItemRefStructure itemRef;




    /**
     * Gets the value of the itemRef property.
     * 
     * @return


     *     possible object is
     *     {@link ItemRefStructure }
     *     
     */
    public ItemRefStructure getItemRef() {
        return itemRef;








    }

    /**
     * Sets the value of the itemRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemRefStructure }
     *     
     */
    public void setItemRef(ItemRefStructure value) {
        this.itemRef = value;
    }

}
