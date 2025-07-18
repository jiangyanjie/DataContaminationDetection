



package com.transportation.SIRI_IL.SOAP;

import java.util.ArrayList;



import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;



import javax.xml.bind.annotation.XmlAccessorType;




import javax.xml.bind.annotation.XmlElement;

import javax.xml.bind.annotation.XmlType;








/**





 * Type for Production Timetable of a line.
 * 
 * <p>Java class for DatedTimetableVersionFrameStructure complex type.






 * 


 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatedTimetableVersionFrameStructure">


 *   &lt;complexContent>




 *     &lt;extension base="{http://www.siri.org.uk/siri}AbstractItemStructure">
 *       &lt;sequence>







 *         &lt;element name="VersionRef" type="{http://www.siri.org.uk/siri}VersionRefStructure" minOccurs="0"/>
 *         &lt;group ref="{http://www.siri.org.uk/siri}LineIdentityGroup"/>
 *         &lt;group ref="{http://www.siri.org.uk/siri}JourneyPatternInfoGroup"/>





 *         &lt;group ref="{http://www.siri.org.uk/siri}ServiceInfoGroup" minOccurs="0"/>
 *         &lt;group ref="{http://www.siri.org.uk/siri}DatedServiceInfoGroup"/>
 *         &lt;group ref="{http://www.siri.org.uk/siri}TimetableRealtimeInfoGroup"/>







 *         &lt;element name="DatedVehicleJourney" type="{http://www.siri.org.uk/siri}DatedVehicleJourneyStructure" maxOccurs="unbounded" minOccurs="0"/>





 *         &lt;element ref="{http://www.siri.org.uk/siri}Extensions" minOccurs="0"/>
 *       &lt;/sequence>




 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>





 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatedTimetableVersionFrameStructure", propOrder = {
    "versionRef",
    "lineRef",

    "directionRef",
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
    "destinationDisplay",
    "lineNote",





    "headwayService",





    "monitored",
    "datedVehicleJourney",
    "extensions"




})






public class DatedTimetableVersionFrameStructure
    extends AbstractItemStructure

{




    @XmlElement(name = "VersionRef")
    protected VersionRefStructure versionRef;
    @XmlElement(name = "LineRef", required = true)
    protected LineRefStructure lineRef;



    @XmlElement(name = "DirectionRef", required = true)
    protected DirectionRefStructure directionRef;



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
    @XmlElement(name = "DestinationDisplay")

    protected NaturalLanguageStringStructure destinationDisplay;
    @XmlElement(name = "LineNote")
    protected NaturalLanguagePlaceNameStructure lineNote;

    @XmlElement(name = "HeadwayService")








    protected Boolean headwayService;
    @XmlElement(name = "Monitored", defaultValue = "true")
    protected Boolean monitored;
    @XmlElement(name = "DatedVehicleJourney")
    protected List<DatedVehicleJourneyStructure> datedVehicleJourney;
    @XmlElement(name = "Extensions")
    protected ExtensionsStructure extensions;

    /**
     * Gets the value of the versionRef property.







     * 



     * @return







     *     possible object is




     *     {@link VersionRefStructure }
     *     
     */
    public VersionRefStructure getVersionRef() {
        return versionRef;
    }

    /**


     * Sets the value of the versionRef property.




     * 

     * @param value

     *     allowed object is






     *     {@link VersionRefStructure }



     *     
     */
    public void setVersionRef(VersionRefStructure value) {



        this.versionRef = value;
    }








    /**



     * Gets the value of the lineRef property.


     * 
     * @return






     *     possible object is
     *     {@link LineRefStructure }


     *     
     */






    public LineRefStructure getLineRef() {









        return lineRef;
    }

    /**
     * Sets the value of the lineRef property.





     * 
     * @param value
     *     allowed object is


     *     {@link LineRefStructure }




     *     
     */





    public void setLineRef(LineRefStructure value) {
        this.lineRef = value;
    }

    /**
     * Gets the value of the directionRef property.
     * 



     * @return



     *     possible object is
     *     {@link DirectionRefStructure }


     *     
     */
    public DirectionRefStructure getDirectionRef() {
        return directionRef;








    }






    /**






     * Sets the value of the directionRef property.
     * 






     * @param value
     *     allowed object is
     *     {@link DirectionRefStructure }

     *     


     */
    public void setDirectionRef(DirectionRefStructure value) {
        this.directionRef = value;
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
     * Gets the value of the datedVehicleJourney property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datedVehicleJourney property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatedVehicleJourney().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatedVehicleJourneyStructure }
     * 
     * 
     */
    public List<DatedVehicleJourneyStructure> getDatedVehicleJourney() {
        if (datedVehicleJourney == null) {
            datedVehicleJourney = new ArrayList<DatedVehicleJourneyStructure>();
        }
        return this.datedVehicleJourney;
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

}
