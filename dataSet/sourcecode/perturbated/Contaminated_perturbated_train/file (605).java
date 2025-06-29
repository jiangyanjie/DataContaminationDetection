




package com.transportation.SIRI_IL.SOAP;




import javax.xml.bind.annotation.XmlAccessType;








import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;



import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;



import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;













/**






 * Type for Request for Connection Timetable Servcie 

 * 
 * <p>Java class for ConnectionTimetableRequestStructure complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.

 * 


 * <pre>
 * &lt;complexType name="ConnectionTimetableRequestStructure">









 *   &lt;complexContent>






 *     &lt;extension base="{http://www.siri.org.uk/siri}AbstractFunctionalServiceRequestStructure">



 *       &lt;sequence>
 *         &lt;group ref="{http://www.siri.org.uk/siri}ConnectionTimetableTopicGroup"/>
 *         &lt;group ref="{http://www.siri.org.uk/siri}ConnectionTimetableRequestPolicyGroup"/>
 *       &lt;/sequence>
 *       &lt;attribute name="version" use="required" type="{http://www.siri.org.uk/siri}VersionString" fixed="1.3" />
 *     &lt;/extension>
 *   &lt;/complexContent>


 * &lt;/complexType>




 * </pre>
 * 
 * 



 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConnectionTimetableRequestStructure", propOrder = {









    "arrivalWindow",









    "connectionLinkRef",



    "lineRef",







    "directionRef",
    "language"




})



public class ConnectionTimetableRequestStructure

    extends AbstractFunctionalServiceRequestStructure
{

    @XmlElement(name = "ArrivalWindow")
    protected ClosedTimestampRangeStructure arrivalWindow;
    @XmlElement(name = "ConnectionLinkRef", required = true)
    protected ConnectionLinkRefStructure connectionLinkRef;






    @XmlElement(name = "LineRef")
    protected LineRefStructure lineRef;
    @XmlElement(name = "DirectionRef")
    protected DirectionRefStructure directionRef;





    @XmlElement(name = "Language", defaultValue = "en")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)

    @XmlSchemaType(name = "language")
    protected String language;
    @XmlAttribute(name = "version", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)

    protected String version;

    /**
     * Gets the value of the arrivalWindow property.
     * 
     * @return




     *     possible object is
     *     {@link ClosedTimestampRangeStructure }
     *     



     */
    public ClosedTimestampRangeStructure getArrivalWindow() {
        return arrivalWindow;
    }








    /**
     * Sets the value of the arrivalWindow property.
     * 








     * @param value
     *     allowed object is
     *     {@link ClosedTimestampRangeStructure }








     *     

     */
    public void setArrivalWindow(ClosedTimestampRangeStructure value) {
        this.arrivalWindow = value;





    }









    /**




     * Gets the value of the connectionLinkRef property.
     * 





     * @return




     *     possible object is

     *     {@link ConnectionLinkRefStructure }
     *     
     */
    public ConnectionLinkRefStructure getConnectionLinkRef() {
        return connectionLinkRef;
    }





    /**









     * Sets the value of the connectionLinkRef property.




     * 
     * @param value
     *     allowed object is



     *     {@link ConnectionLinkRefStructure }
     *     




     */
    public void setConnectionLinkRef(ConnectionLinkRefStructure value) {
        this.connectionLinkRef = value;



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


     * Gets the value of the language property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguage() {
        return language;
    }

    /**



     * Sets the value of the language property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }









     *     
     */
    public void setLanguage(String value) {
        this.language = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {



        if (version == null) {
            return "1.3";
        } else {
            return version;
        }



    }

    /**
     * Sets the value of the version property.
     * 
     * @param value


     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

}
