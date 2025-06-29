
package com.transportation.SIRI_IL.SOAP;







import java.util.ArrayList;
import java.util.List;






import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import javax.xml.bind.annotation.XmlElement;




import javax.xml.bind.annotation.XmlType;























/**
 * Specification of periods defined by the intersection of days, weeks and months. 
 * 
 * <p>Java class for DayWeekMonth complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.





 * 
 * <pre>
 * &lt;complexType name="DayWeekMonth">
 *   &lt;complexContent>








 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>

 *         &lt;element name="applicableDay" type="{http://datex2.eu/schema/1_0/1_0}DayEnum" maxOccurs="7"/>
 *         &lt;element name="applicableWeek" type="{http://datex2.eu/schema/1_0/1_0}WeekOfMonthEnum" maxOccurs="5"/>





 *         &lt;element name="applicableMonth" type="{http://datex2.eu/schema/1_0/1_0}MonthOfYearEnum" maxOccurs="12"/>


 *         &lt;element name="dayWeekMonthExtension" type="{http://datex2.eu/schema/1_0/1_0}ExtensionType" minOccurs="0"/>










 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 

 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)


@XmlType(name = "DayWeekMonth", namespace = "http://datex2.eu/schema/1_0/1_0", propOrder = {
    "applicableDay",
    "applicableWeek",


    "applicableMonth",




    "dayWeekMonthExtension"
})


public class DayWeekMonth {



    @XmlElement(required = true)


    protected List<DayEnum> applicableDay;




    @XmlElement(required = true)
    protected List<WeekOfMonthEnum> applicableWeek;









    @XmlElement(required = true)
    protected List<MonthOfYearEnum> applicableMonth;



    protected ExtensionType dayWeekMonthExtension;




    /**
     * Gets the value of the applicableDay property.
     * 




     * <p>
     * This accessor method returns a reference to the live list,



     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the applicableDay property.
     * 
     * <p>
     * For example, to add a new item, do as follows:

     * <pre>
     *    getApplicableDay().add(newItem);
     * </pre>
     * 
     * 
     * <p>






     * Objects of the following type(s) are allowed in the list
     * {@link DayEnum }
     * 

     * 





     */
    public List<DayEnum> getApplicableDay() {
        if (applicableDay == null) {
            applicableDay = new ArrayList<DayEnum>();


        }






        return this.applicableDay;
    }










    /**
     * Gets the value of the applicableWeek property.
     * 











     * <p>
     * This accessor method returns a reference to the live list,








     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the applicableWeek property.





     * 




     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getApplicableWeek().add(newItem);



     * </pre>
     * 


     * 
     * <p>
     * Objects of the following type(s) are allowed in the list










     * {@link WeekOfMonthEnum }
     * 
     * 
     */
    public List<WeekOfMonthEnum> getApplicableWeek() {


        if (applicableWeek == null) {
            applicableWeek = new ArrayList<WeekOfMonthEnum>();

        }











        return this.applicableWeek;



    }





    /**
     * Gets the value of the applicableMonth property.
     * 





     * <p>









     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.


     * This is why there is not a <CODE>set</CODE> method for the applicableMonth property.



     * 

     * <p>



     * For example, to add a new item, do as follows:
     * <pre>
     *    getApplicableMonth().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MonthOfYearEnum }
     * 
     * 


     */
    public List<MonthOfYearEnum> getApplicableMonth() {
        if (applicableMonth == null) {
            applicableMonth = new ArrayList<MonthOfYearEnum>();
        }
        return this.applicableMonth;
    }

    /**
     * Gets the value of the dayWeekMonthExtension property.
     * 
     * @return
     *     possible object is
     *     {@link ExtensionType }
     *     
     */
    public ExtensionType getDayWeekMonthExtension() {
        return dayWeekMonthExtension;
    }

    /**
     * Sets the value of the dayWeekMonthExtension property.
     * 

     * @param value
     *     allowed object is
     *     {@link ExtensionType }
     *     
     */
    public void setDayWeekMonthExtension(ExtensionType value) {
        this.dayWeekMonthExtension = value;
    }

}
