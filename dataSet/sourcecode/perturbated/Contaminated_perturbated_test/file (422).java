







package com.transportation.SIRI_IL.SOAP;

import java.util.ArrayList;













import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


import javax.xml.bind.annotation.XmlType;


import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;




import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;







/**
 * Data type for Planned Vehicle Journey (Production Timetable Service).
 * 
 * <p>Java class for DatedVehicleJourneyStructure complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.




 * 
 * <pre>
 * &lt;complexType name="DatedVehicleJourneyStructure">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">










 *       &lt;sequence>
 *         &lt;element name="DatedVehicleJourneyCode" type="{http://www.siri.org.uk/siri}DatedVehicleJourneyCodeType"/>

 *         &lt;group ref="{http://www.siri.org.uk/siri}TimetableAlterationGroup" minOccurs="0"/>
 *         &lt;group ref="{http://www.siri.org.uk/siri}JourneyPatternInfoGroup"/>




 *         &lt;group ref="{http://www.siri.org.uk/siri}ServiceInfoGroup"/>
 *         &lt;group ref="{http://www.siri.org.uk/siri}JourneyInfoGroup"/>










 *         &lt;group ref="{http://www.siri.org.uk/siri}DatedServiceInfoGroup"/>



 *         &lt;group ref="{http://www.siri.org.uk/siri}TimetableRealtimeInfoGroup"/>
 *         &lt;group ref="{http://www.siri.org.uk/siri}OperationalBlockGroup" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="DatedCalls">
 *             &lt;complexType>










 *               &lt;complexContent>










 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">




 *                   &lt;sequence>
 *                     &lt;element ref="{http://www.siri.org.uk/siri}DatedCall" maxOccurs="unbounded" minOccurs="2"/>
 *                   &lt;/sequence>


 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;group ref="{http://www.siri.org.uk/siri}DatedCallsAsFlatGroup"/>









 *         &lt;/choice>
 *         &lt;element ref="{http://www.siri.org.uk/siri}Extensions" minOccurs="0"/>



 *       &lt;/sequence>








 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 










 */



@XmlAccessorType(XmlAccessType.FIELD)



@XmlType(name = "DatedVehicleJourneyStructure", propOrder = {
    "datedVehicleJourneyCode",




    "vehicleJourneyRef",
    "extraJourney",

    "cancellation",
    "journeyPatternRef",
    "vehicleMode",



    "routeRef",
    "publishedLineName",
    "directionName",
    "externalLineRef",
    "operatorRef",
    "productCategoryRef",
    "serviceFeatureRef",
    "vehicleFeatureRef",
    "vehicleJourneyName",
    "journeyNote",




    "destinationDisplay",





    "lineNote",

    "headwayService",




















    "monitored",



    "blockRef",
    "courseOfJourneyRef",
    "datedCalls",



    "datedCall",
    "extensions"
})
public class DatedVehicleJourneyStructure {

    @XmlElement(name = "DatedVehicleJourneyCode", required = true)












    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)

    protected String datedVehicleJourneyCode;



    @XmlElement(name = "VehicleJourneyRef")
    protected VehicleJourneyRefStructure vehicleJourneyRef;



    @XmlElement(name = "ExtraJourney", defaultValue = "false")
    protected Boolean extraJourney;
    @XmlElement(name = "Cancellation", defaultValue = "false")
    protected Boolean cancellation;





    @XmlElement(name = "JourneyPatternRef")



    protected JourneyPatternRefStructure journeyPatternRef;

    @XmlElement(name = "VehicleMode")
    protected List<VehicleModesEnumeration> vehicleMode;
    @XmlElement(name = "RouteRef")









    protected RouteRefStructure routeRef;
    @XmlElement(name = "PublishedLineName")
    protected NaturalLanguageStringStructure publishedLineName;





    @XmlElement(name = "DirectionName")
    protected NaturalLanguageStringStructure directionName;
    @XmlElement(name = "ExternalLineRef")
    protected LineRefStructure externalLineRef;
    @XmlElement(name = "OperatorRef")
    protected OperatorRefStructure operatorRef;


    @XmlElement(name = "ProductCategoryRef")
    protected ProductCategoryRefStructure productCategoryRef;
    @XmlElement(name = "ServiceFeatureRef")
    protected List<ServiceFeatureRefStructure> serviceFeatureRef;




    @XmlElement(name = "VehicleFeatureRef")
    protected List<VehicleFeatureRefStructure> vehicleFeatureRef;
    @XmlElement(name = "VehicleJourneyName")
    protected NaturalLanguageStringStructure vehicleJourneyName;
    @XmlElement(name = "JourneyNote")
    protected List<NaturalLanguageStringStructure> journeyNote;






    @XmlElement(name = "DestinationDisplay")
    protected NaturalLanguageStringStructure destinationDisplay;
















    @XmlElement(name = "LineNote")



    protected NaturalLanguagePlaceNameStructure lineNote;
    @XmlElement(name = "HeadwayService")
    protected Boolean headwayService;




    @XmlElement(name = "Monitored", defaultValue = "true")
    protected Boolean monitored;









    @XmlElement(name = "BlockRef")


    protected BlockRefStructure blockRef;

    @XmlElement(name = "CourseOfJourneyRef")

    protected CourseOfJourneyStructure courseOfJourneyRef;
    @XmlElement(name = "DatedCalls")
    protected DatedVehicleJourneyStructure.DatedCalls datedCalls;



    @XmlElement(name = "DatedCall")
    protected List<DatedCallStructure> datedCall;
    @XmlElement(name = "Extensions")
    protected ExtensionsStructure extensions;





    /**




     * Gets the value of the datedVehicleJourneyCode property.
     * 
     * @return









     *     possible object is








     *     {@link String }
     *     



     */
    public String getDatedVehicleJourneyCode() {
        return datedVehicleJourneyCode;
    }




    /**
     * Sets the value of the datedVehicleJourneyCode property.


     * 
     * @param value



     *     allowed object is





     *     {@link String }
     *     
     */
    public void setDatedVehicleJourneyCode(String value) {












        this.datedVehicleJourneyCode = value;


    }












    /**



     * Gets the value of the vehicleJourneyRef property.

     * 












     * @return
     *     possible object is







     *     {@link VehicleJourneyRefStructure }
     *     
     */
    public VehicleJourneyRefStructure getVehicleJourneyRef() {
        return vehicleJourneyRef;





    }

    /**





     * Sets the value of the vehicleJourneyRef property.


     * 
     * @param value
     *     allowed object is




     *     {@link VehicleJourneyRefStructure }
     *     
     */
    public void setVehicleJourneyRef(VehicleJourneyRefStructure value) {
        this.vehicleJourneyRef = value;















    }














    /**



     * Gets the value of the extraJourney property.
     * 
     * @return
     *     possible object is


     *     {@link Boolean }


     *     











     */
    public Boolean isExtraJourney() {
        return extraJourney;
    }













    /**
     * Sets the value of the extraJourney property.

     * 






     * @param value
     *     allowed object is



     *     {@link Boolean }


     *     





     */
    public void setExtraJourney(Boolean value) {
        this.extraJourney = value;











    }



    /**




     * Gets the value of the cancellation property.





     * 
     * @return
     *     possible object is






     *     {@link Boolean }






     *     







     */
    public Boolean isCancellation() {






        return cancellation;
    }





    /**



     * Sets the value of the cancellation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCancellation(Boolean value) {


        this.cancellation = value;
    }

    /**
     * Gets the value of the journeyPatternRef property.






     * 





     * @return
     *     possible object is
     *     {@link JourneyPatternRefStructure }
     *     
     */
    public JourneyPatternRefStructure getJourneyPatternRef() {
        return journeyPatternRef;



    }





    /**
     * Sets the value of the journeyPatternRef property.


















     * 
     * @param value
     *     allowed object is


     *     {@link JourneyPatternRefStructure }
     *     
     */
    public void setJourneyPatternRef(JourneyPatternRefStructure value) {
        this.journeyPatternRef = value;
    }

    /**
     * Gets the value of the vehicleMode property.


     * 
     * <p>





     * This accessor method returns a reference to the live list,


     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vehicleMode property.
     * 
     * <p>





     * For example, to add a new item, do as follows:
     * <pre>
     *    getVehicleMode().add(newItem);
     * </pre>
     * 











     * 


     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VehicleModesEnumeration }
     * 
     * 
     */
    public List<VehicleModesEnumeration> getVehicleMode() {



        if (vehicleMode == null) {




            vehicleMode = new ArrayList<VehicleModesEnumeration>();
        }
        return this.vehicleMode;
    }

    /**
     * Gets the value of the routeRef property.



     * 
     * @return

     *     possible object is
     *     {@link RouteRefStructure }
     *     
     */
    public RouteRefStructure getRouteRef() {
        return routeRef;
    }

    /**

     * Sets the value of the routeRef property.
     * 

















     * @param value
     *     allowed object is
     *     {@link RouteRefStructure }
     *     
     */
    public void setRouteRef(RouteRefStructure value) {












        this.routeRef = value;
    }







    /**

     * Gets the value of the publishedLineName property.
     * 
     * @return
     *     possible object is
     *     {@link NaturalLanguageStringStructure }


     *     





     */






    public NaturalLanguageStringStructure getPublishedLineName() {
        return publishedLineName;


    }





    /**



     * Sets the value of the publishedLineName property.
     * 







     * @param value
     *     allowed object is

     *     {@link NaturalLanguageStringStructure }

     *     
     */
    public void setPublishedLineName(NaturalLanguageStringStructure value) {
        this.publishedLineName = value;
    }










    /**

     * Gets the value of the directionName property.













     * 
     * @return
     *     possible object is
     *     {@link NaturalLanguageStringStructure }
     *     
     */
    public NaturalLanguageStringStructure getDirectionName() {












        return directionName;
    }












    /**
     * Sets the value of the directionName property.
     * 
     * @param value















     *     allowed object is
     *     {@link NaturalLanguageStringStructure }





     *     
     */













    public void setDirectionName(NaturalLanguageStringStructure value) {
        this.directionName = value;
    }



    /**
     * Gets the value of the externalLineRef property.
     * 
     * @return

     *     possible object is
     *     {@link LineRefStructure }
     *     
     */
    public LineRefStructure getExternalLineRef() {
        return externalLineRef;




    }




    /**









     * Sets the value of the externalLineRef property.
     * 


     * @param value
     *     allowed object is
     *     {@link LineRefStructure }
     *     
     */
    public void setExternalLineRef(LineRefStructure value) {





        this.externalLineRef = value;








    }









    /**
     * Gets the value of the operatorRef property.







     * 


     * @return











     *     possible object is



     *     {@link OperatorRefStructure }
     *     
     */


    public OperatorRefStructure getOperatorRef() {



        return operatorRef;
    }





    /**




     * Sets the value of the operatorRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link OperatorRefStructure }


     *     
     */













    public void setOperatorRef(OperatorRefStructure value) {


        this.operatorRef = value;
    }






    /**



     * Gets the value of the productCategoryRef property.
     * 
     * @return
     *     possible object is
     *     {@link ProductCategoryRefStructure }
     *     
     */
    public ProductCategoryRefStructure getProductCategoryRef() {
        return productCategoryRef;
    }




    /**
     * Sets the value of the productCategoryRef property.
     * 








     * @param value








     *     allowed object is
     *     {@link ProductCategoryRefStructure }
     *     
     */
    public void setProductCategoryRef(ProductCategoryRefStructure value) {





        this.productCategoryRef = value;
    }













    /**
     * Classification of service into arbitrary Service categories, e.g. school bus. Recommended SIRI values based on TPEG are given in SIRI documentation and enumerated in the siri_facilities package.Gets the value of the serviceFeatureRef property.
     * 
     * <p>



     * This accessor method returns a reference to the live list,


     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the serviceFeatureRef property.
















     * 







     * <p>
     * For example, to add a new item, do as follows:
     * <pre>









     *    getServiceFeatureRef().add(newItem);
     * </pre>






     * 
     * 









     * <p>
     * Objects of the following type(s) are allowed in the list


     * {@link ServiceFeatureRefStructure }
     * 
     * 



     */

    public List<ServiceFeatureRefStructure> getServiceFeatureRef() {



        if (serviceFeatureRef == null) {



            serviceFeatureRef = new ArrayList<ServiceFeatureRefStructure>();
        }



        return this.serviceFeatureRef;



    }

    /**
     * Gets the value of the vehicleFeatureRef property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.



     * This is why there is not a <CODE>set</CODE> method for the vehicleFeatureRef property.
     * 



     * <p>
     * For example, to add a new item, do as follows:
     * <pre>







     *    getVehicleFeatureRef().add(newItem);


     * </pre>















     * 



     * 






     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VehicleFeatureRefStructure }
     * 
     * 
     */
    public List<VehicleFeatureRefStructure> getVehicleFeatureRef() {



        if (vehicleFeatureRef == null) {
            vehicleFeatureRef = new ArrayList<VehicleFeatureRefStructure>();



        }
        return this.vehicleFeatureRef;


    }













    /**
     * Gets the value of the vehicleJourneyName property.








     * 
     * @return





     *     possible object is
     *     {@link NaturalLanguageStringStructure }
     *     
     */




    public NaturalLanguageStringStructure getVehicleJourneyName() {
        return vehicleJourneyName;
    }

    /**
     * Sets the value of the vehicleJourneyName property.
     * 
     * @param value




     *     allowed object is




     *     {@link NaturalLanguageStringStructure }








     *     
     */
    public void setVehicleJourneyName(NaturalLanguageStringStructure value) {
        this.vehicleJourneyName = value;
    }




    /**
     * Gets the value of the journeyNote property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the




     * returned list will be present inside the JAXB object.




     * This is why there is not a <CODE>set</CODE> method for the journeyNote property.
     * 








     * <p>





     * For example, to add a new item, do as follows:


     * <pre>







     *    getJourneyNote().add(newItem);
     * </pre>
     * 
     * 
     * <p>


     * Objects of the following type(s) are allowed in the list
     * {@link NaturalLanguageStringStructure }

     * 
     * 
     */




    public List<NaturalLanguageStringStructure> getJourneyNote() {
        if (journeyNote == null) {

            journeyNote = new ArrayList<NaturalLanguageStringStructure>();
        }
        return this.journeyNote;










    }

    /**



     * Gets the value of the destinationDisplay property.

     * 
     * @return
     *     possible object is
     *     {@link NaturalLanguageStringStructure }




     *     
     */



    public NaturalLanguageStringStructure getDestinationDisplay() {
        return destinationDisplay;



    }









    /**
     * Sets the value of the destinationDisplay property.
     * 


     * @param value


     *     allowed object is




     *     {@link NaturalLanguageStringStructure }
     *     
     */









    public void setDestinationDisplay(NaturalLanguageStringStructure value) {
        this.destinationDisplay = value;
    }

    /**
     * Gets the value of the lineNote property.
     * 
     * @return
     *     possible object is
     *     {@link NaturalLanguagePlaceNameStructure }
     *     
     */
    public NaturalLanguagePlaceNameStructure getLineNote() {

        return lineNote;


    }



    /**
     * Sets the value of the lineNote property.
     * 







     * @param value
     *     allowed object is
     *     {@link NaturalLanguagePlaceNameStructure }
     *     
     */
    public void setLineNote(NaturalLanguagePlaceNameStructure value) {
        this.lineNote = value;
    }

    /**
     * Gets the value of the headwayService property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHeadwayService() {




        return headwayService;
    }



    /**




     * Sets the value of the headwayService property.
     * 
     * @param value

     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHeadwayService(Boolean value) {
        this.headwayService = value;
    }

    /**


     * Gets the value of the monitored property.
     * 
     * @return











     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMonitored() {
        return monitored;
    }

    /**


     * Sets the value of the monitored property.
     * 
     * @param value




     *     allowed object is
     *     {@link Boolean }
     *     






     */






    public void setMonitored(Boolean value) {
        this.monitored = value;
    }

    /**




     * Gets the value of the blockRef property.


     * 
     * @return
     *     possible object is
     *     {@link BlockRefStructure }
     *     
     */
    public BlockRefStructure getBlockRef() {
        return blockRef;







    }



    /**
     * Sets the value of the blockRef property.



     * 
     * @param value
     *     allowed object is


     *     {@link BlockRefStructure }
     *     



     */
    public void setBlockRef(BlockRefStructure value) {


        this.blockRef = value;




    }




    /**
     * Gets the value of the courseOfJourneyRef property.


     * 


     * @return




     *     possible object is
     *     {@link CourseOfJourneyStructure }
     *     

     */
    public CourseOfJourneyStructure getCourseOfJourneyRef() {
        return courseOfJourneyRef;
    }



    /**
     * Sets the value of the courseOfJourneyRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link CourseOfJourneyStructure }
     *     
     */
    public void setCourseOfJourneyRef(CourseOfJourneyStructure value) {



        this.courseOfJourneyRef = value;
    }

    /**
     * Gets the value of the datedCalls property.
     * 



     * @return
     *     possible object is






     *     {@link DatedVehicleJourneyStructure.DatedCalls }
     *     
     */
    public DatedVehicleJourneyStructure.DatedCalls getDatedCalls() {
        return datedCalls;
    }

    /**
     * Sets the value of the datedCalls property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatedVehicleJourneyStructure.DatedCalls }
     *     
     */
    public void setDatedCalls(DatedVehicleJourneyStructure.DatedCalls value) {

        this.datedCalls = value;
    }

    /**
     * Gets the value of the datedCall property.
     * 
     * <p>









     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.



     * This is why there is not a <CODE>set</CODE> method for the datedCall property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>






     *    getDatedCall().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatedCallStructure }
     * 
     * 


     */
    public List<DatedCallStructure> getDatedCall() {
        if (datedCall == null) {
            datedCall = new ArrayList<DatedCallStructure>();
        }
        return this.datedCall;
    }

    /**
     * Gets the value of the extensions property.
     * 
     * @return
     *     possible object is
     *     {@link ExtensionsStructure }
     *     
     */
    public ExtensionsStructure getExtensions() {
        return extensions;
    }

    /**
     * Sets the value of the extensions property.
     * 




     * @param value
     *     allowed object is
     *     {@link ExtensionsStructure }
     *     
     */
    public void setExtensions(ExtensionsStructure value) {
        this.extensions = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>




     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element ref="{http://www.siri.org.uk/siri}DatedCall" maxOccurs="unbounded" minOccurs="2"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "datedCall"
    })
    public static class DatedCalls {

        @XmlElement(name = "DatedCall", required = true)
        protected List<DatedCallStructure> datedCall;

        /**
         * Gets the value of the datedCall property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the datedCall property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDatedCall().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DatedCallStructure }
         * 
         * 
         */
        public List<DatedCallStructure> getDatedCall() {
            if (datedCall == null) {
                datedCall = new ArrayList<DatedCallStructure>();
            }
            return this.datedCall;
        }

    }

}
