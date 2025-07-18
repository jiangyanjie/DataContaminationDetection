





package com.transportation.SIRI_IL.SOAP;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;





import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;






import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;















/**
 * Common parameters for all situations.
 * 
 * <p>Java class for ContextStructure complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.




















 * 


 * <pre>
 * &lt;complexType name="ContextStructure">



 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>




 *         &lt;element name="CountryRef" type="{http://www.ifopt.org.uk/ifopt}CountryRefStructure" minOccurs="0"/>
 *         &lt;element name="ParticipantRef" type="{http://www.siri.org.uk/siri}ParticipantRefStructure"/>
 *         &lt;element name="TopographicPlaceRef" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" minOccurs="0"/>





 *         &lt;element name="TopographicPlaceName" type="{http://www.siri.org.uk/siri}NaturalLanguageStringStructure" minOccurs="0"/>
 *         &lt;element name="DefaultLanguage" type="{http://www.w3.org/2001/XMLSchema}language" minOccurs="0"/>




 *         &lt;element name="NetworkContext" type="{http://www.siri.org.uk/siri}NetworkContextStructure" minOccurs="0"/>

 *         &lt;element name="Actions" type="{http://www.siri.org.uk/siri}ActionsStructure" minOccurs="0"/>
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
@XmlType(name = "ContextStructure", propOrder = {
    "countryRef",
    "participantRef",
    "topographicPlaceRef",
    "topographicPlaceName",


    "defaultLanguage",


    "networkContext",
    "actions",





    "extensions"
})




public class ContextStructure {



    @XmlElement(name = "CountryRef")
    protected CountryRefStructure countryRef;















    @XmlElement(name = "ParticipantRef", required = true)
    protected ParticipantRefStructure participantRef;








    @XmlElement(name = "TopographicPlaceRef")

    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)










    @XmlSchemaType(name = "NMTOKEN")
    protected String topographicPlaceRef;
    @XmlElement(name = "TopographicPlaceName")
    protected NaturalLanguageStringStructure topographicPlaceName;



    @XmlElement(name = "DefaultLanguage")



    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)


    @XmlSchemaType(name = "language")
    protected String defaultLanguage;
    @XmlElement(name = "NetworkContext")




    protected NetworkContextStructure networkContext;
    @XmlElement(name = "Actions")
    protected ActionsStructure actions;
    @XmlElement(name = "Extensions")
    protected ExtensionsStructure extensions;




    /**
     * Gets the value of the countryRef property.



     * 





     * @return
     *     possible object is
     *     {@link CountryRefStructure }
     *     


     */
    public CountryRefStructure getCountryRef() {
        return countryRef;




    }

    /**




     * Sets the value of the countryRef property.


     * 
     * @param value


     *     allowed object is

     *     {@link CountryRefStructure }




     *     
     */
    public void setCountryRef(CountryRefStructure value) {










        this.countryRef = value;
    }

    /**
     * Gets the value of the participantRef property.
     * 
     * @return




     *     possible object is
     *     {@link ParticipantRefStructure }



     *     


     */



    public ParticipantRefStructure getParticipantRef() {


        return participantRef;








    }

    /**
     * Sets the value of the participantRef property.
     * 
     * @param value









     *     allowed object is
     *     {@link ParticipantRefStructure }
     *     








     */



    public void setParticipantRef(ParticipantRefStructure value) {
        this.participantRef = value;


    }

    /**
     * Gets the value of the topographicPlaceRef property.




     * 
     * @return










     *     possible object is
     *     {@link String }
























     *     
     */

    public String getTopographicPlaceRef() {
        return topographicPlaceRef;





    }













    /**
     * Sets the value of the topographicPlaceRef property.

     * 



     * @param value






     *     allowed object is
     *     {@link String }


     *     
     */
    public void setTopographicPlaceRef(String value) {
        this.topographicPlaceRef = value;
    }










    /**
     * Gets the value of the topographicPlaceName property.
     * 
     * @return
     *     possible object is




     *     {@link NaturalLanguageStringStructure }
     *     
     */







    public NaturalLanguageStringStructure getTopographicPlaceName() {
        return topographicPlaceName;
    }



    /**
     * Sets the value of the topographicPlaceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link NaturalLanguageStringStructure }
     *     
     */
    public void setTopographicPlaceName(NaturalLanguageStringStructure value) {


        this.topographicPlaceName = value;
    }

    /**






     * Gets the value of the defaultLanguage property.






     * 
     * @return


     *     possible object is
     *     {@link String }


     *     
     */



    public String getDefaultLanguage() {
        return defaultLanguage;
    }




    /**

     * Sets the value of the defaultLanguage property.



     * 
     * @param value
     *     allowed object is




     *     {@link String }
     *     



     */
    public void setDefaultLanguage(String value) {
        this.defaultLanguage = value;
    }














    /**
     * Gets the value of the networkContext property.
     * 








     * @return




     *     possible object is
     *     {@link NetworkContextStructure }
     *     
     */
    public NetworkContextStructure getNetworkContext() {
        return networkContext;
    }




    /**
     * Sets the value of the networkContext property.
     * 



     * @param value
     *     allowed object is
     *     {@link NetworkContextStructure }
     *     
     */
    public void setNetworkContext(NetworkContextStructure value) {
        this.networkContext = value;
    }

    /**
     * Gets the value of the actions property.
     * 
     * @return
     *     possible object is
     *     {@link ActionsStructure }
     *     
     */
    public ActionsStructure getActions() {
        return actions;
    }

    /**






     * Sets the value of the actions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActionsStructure }
     *     
     */
    public void setActions(ActionsStructure value) {
        this.actions = value;
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
