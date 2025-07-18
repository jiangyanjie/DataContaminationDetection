//


// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.10.30 at 12:18:26 PM EDT 
//




package com.rsa.transarmor.dto;




import java.util.ArrayList;


import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;




import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;





/**


 * <p>Java class for DebitRequestDetails complex type.










 * 







 * <p>The following schema fragment specifies the expected content contained within this class.



 * 
 * <pre>





 * &lt;complexType name="DebitRequestDetails">
 *   &lt;complexContent>








 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">










 *       &lt;sequence>





 *         &lt;element name="CommonGrp" type="{com/firstdata/Merchant/gmfV3.10}CommonGrp" minOccurs="0"/>
 *         &lt;element name="BillPayGrp" type="{com/firstdata/Merchant/gmfV3.10}BillPayGrp" minOccurs="0"/>
 *         &lt;element name="AltMerchNameAndAddrGrp" type="{com/firstdata/Merchant/gmfV3.10}AltMerchNameAndAddrGrp" minOccurs="0"/>






 *         &lt;element name="CardGrp" type="{com/firstdata/Merchant/gmfV3.10}CardGrp" minOccurs="0"/>

 *         &lt;element name="PINGrp" type="{com/firstdata/Merchant/gmfV3.10}PINGrp" minOccurs="0"/>


 *         &lt;element name="AddtlAmtGrp" type="{com/firstdata/Merchant/gmfV3.10}AddtlAmtGrp" maxOccurs="6" minOccurs="0"/>







 *         &lt;element name="TAGrp" type="{com/firstdata/Merchant/gmfV3.10}TAGrp" minOccurs="0"/>
 *         &lt;element name="OfferGrp" type="{com/firstdata/Merchant/gmfV3.10}OfferGrp" minOccurs="0"/>
 *         &lt;element name="VisaGrp" type="{com/firstdata/Merchant/gmfV3.10}VisaGrp" minOccurs="0"/>
 *         &lt;element name="DebitGrp" type="{com/firstdata/Merchant/gmfV3.10}DebitGrp" minOccurs="0"/>










 *         &lt;element name="OrigAuthGrp" type="{com/firstdata/Merchant/gmfV3.10}OrigAuthGrp" minOccurs="0"/>
 *         &lt;element name="ProdCodeGrp" type="{com/firstdata/Merchant/gmfV3.10}ProdCodeGrp" minOccurs="0"/>
 *         &lt;element name="ProdCodeDetGrp" type="{com/firstdata/Merchant/gmfV3.10}ProdCodeDetGrp" maxOccurs="10" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>


 *   &lt;/complexContent>



 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)





@XmlType(name = "DebitRequestDetails", propOrder = {




    "commonGrp",
    "billPayGrp",


    "altMerchNameAndAddrGrp",
    "cardGrp",










    "pinGrp",



    "addtlAmtGrp",
    "taGrp",
    "offerGrp",
    "visaGrp",
    "debitGrp",
    "origAuthGrp",






















    "prodCodeGrp",









    "prodCodeDetGrp"
})
public class DebitRequestDetails {

    @XmlElement(name = "CommonGrp")
    protected CommonGrp commonGrp;
    @XmlElement(name = "BillPayGrp")
    protected BillPayGrp billPayGrp;
    @XmlElement(name = "AltMerchNameAndAddrGrp")


    protected AltMerchNameAndAddrGrp altMerchNameAndAddrGrp;


    @XmlElement(name = "CardGrp")
    protected CardGrp cardGrp;
    @XmlElement(name = "PINGrp")



    protected PINGrp pinGrp;



    @XmlElement(name = "AddtlAmtGrp")
    protected List<AddtlAmtGrp> addtlAmtGrp;
    @XmlElement(name = "TAGrp")
    protected TAGrp taGrp;
    @XmlElement(name = "OfferGrp")
    protected OfferGrp offerGrp;
    @XmlElement(name = "VisaGrp")
    protected VisaGrp visaGrp;




    @XmlElement(name = "DebitGrp")
    protected DebitGrp debitGrp;
    @XmlElement(name = "OrigAuthGrp")





    protected OrigAuthGrp origAuthGrp;
    @XmlElement(name = "ProdCodeGrp")



    protected ProdCodeGrp prodCodeGrp;





    @XmlElement(name = "ProdCodeDetGrp")


    protected List<ProdCodeDetGrp> prodCodeDetGrp;





    /**
     * Gets the value of the commonGrp property.




     * 
     * @return














     *     possible object is



     *     {@link CommonGrp }
     *     





     */






    public CommonGrp getCommonGrp() {
        return commonGrp;

    }

    /**



     * Sets the value of the commonGrp property.
     * 
     * @param value





     *     allowed object is





     *     {@link CommonGrp }
     *     


     */




    public void setCommonGrp(CommonGrp value) {






        this.commonGrp = value;
    }








    /**
     * Gets the value of the billPayGrp property.

     * 
     * @return
     *     possible object is








     *     {@link BillPayGrp }
     *     
     */



    public BillPayGrp getBillPayGrp() {
        return billPayGrp;
    }





    /**
     * Sets the value of the billPayGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link BillPayGrp }
     *     







     */
    public void setBillPayGrp(BillPayGrp value) {
        this.billPayGrp = value;
    }



    /**




     * Gets the value of the altMerchNameAndAddrGrp property.
     * 


     * @return
     *     possible object is
     *     {@link AltMerchNameAndAddrGrp }
     *     
     */






    public AltMerchNameAndAddrGrp getAltMerchNameAndAddrGrp() {





        return altMerchNameAndAddrGrp;
    }





    /**
     * Sets the value of the altMerchNameAndAddrGrp property.
     * 
     * @param value
     *     allowed object is


     *     {@link AltMerchNameAndAddrGrp }
     *     





     */
    public void setAltMerchNameAndAddrGrp(AltMerchNameAndAddrGrp value) {
        this.altMerchNameAndAddrGrp = value;
    }




    /**


     * Gets the value of the cardGrp property.






     * 
     * @return








     *     possible object is
     *     {@link CardGrp }
     *     









     */
    public CardGrp getCardGrp() {
        return cardGrp;
    }












    /**
     * Sets the value of the cardGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardGrp }



     *     
     */
    public void setCardGrp(CardGrp value) {
        this.cardGrp = value;
    }






    /**















     * Gets the value of the pinGrp property.
     * 
     * @return
     *     possible object is
     *     {@link PINGrp }
     *     








     */
    public PINGrp getPINGrp() {


        return pinGrp;





    }

    /**
     * Sets the value of the pinGrp property.
     * 




     * @param value
     *     allowed object is





     *     {@link PINGrp }
     *     





     */
    public void setPINGrp(PINGrp value) {







        this.pinGrp = value;
    }

    /**
     * Gets the value of the addtlAmtGrp property.
     * 
     * <p>




     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the









     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addtlAmtGrp property.






     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddtlAmtGrp().add(newItem);
     * </pre>
     * 




     * 







     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AddtlAmtGrp }
     * 









     * 







     */
    public List<AddtlAmtGrp> getAddtlAmtGrp() {
        if (addtlAmtGrp == null) {
            addtlAmtGrp = new ArrayList<AddtlAmtGrp>();


        }
        return this.addtlAmtGrp;


    }








    /**
     * Gets the value of the taGrp property.







     * 
     * @return









     *     possible object is
     *     {@link TAGrp }







     *     
     */



    public TAGrp getTAGrp() {
        return taGrp;
    }




    /**
     * Sets the value of the taGrp property.
     * 
     * @param value








     *     allowed object is
     *     {@link TAGrp }
     *     














     */


    public void setTAGrp(TAGrp value) {





        this.taGrp = value;
    }

    /**
     * Gets the value of the offerGrp property.
     * 
     * @return
     *     possible object is
     *     {@link OfferGrp }
     *     







     */



    public OfferGrp getOfferGrp() {
        return offerGrp;
    }




    /**




     * Sets the value of the offerGrp property.
     * 



     * @param value





     *     allowed object is

     *     {@link OfferGrp }
     *     
     */




    public void setOfferGrp(OfferGrp value) {




        this.offerGrp = value;




    }

    /**




     * Gets the value of the visaGrp property.
     * 
     * @return







     *     possible object is



     *     {@link VisaGrp }














     *     
     */


    public VisaGrp getVisaGrp() {





        return visaGrp;
    }


    /**
     * Sets the value of the visaGrp property.

     * 




     * @param value



     *     allowed object is
     *     {@link VisaGrp }
     *     
     */



    public void setVisaGrp(VisaGrp value) {
        this.visaGrp = value;
    }

    /**
     * Gets the value of the debitGrp property.
     * 
     * @return
     *     possible object is
     *     {@link DebitGrp }
     *     
     */
    public DebitGrp getDebitGrp() {
        return debitGrp;
    }

    /**
     * Sets the value of the debitGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link DebitGrp }
     *     
     */
    public void setDebitGrp(DebitGrp value) {

        this.debitGrp = value;
    }

    /**
     * Gets the value of the origAuthGrp property.
     * 
     * @return
     *     possible object is
     *     {@link OrigAuthGrp }
     *     





     */
    public OrigAuthGrp getOrigAuthGrp() {
        return origAuthGrp;
    }

    /**
     * Sets the value of the origAuthGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrigAuthGrp }


     *     
     */
    public void setOrigAuthGrp(OrigAuthGrp value) {
        this.origAuthGrp = value;
    }





    /**
     * Gets the value of the prodCodeGrp property.
     * 
     * @return
     *     possible object is
     *     {@link ProdCodeGrp }
     *     
     */
    public ProdCodeGrp getProdCodeGrp() {
        return prodCodeGrp;
    }

    /**
     * Sets the value of the prodCodeGrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProdCodeGrp }





     *     
     */
    public void setProdCodeGrp(ProdCodeGrp value) {
        this.prodCodeGrp = value;
    }

    /**
     * Gets the value of the prodCodeDetGrp property.
     * 


     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the prodCodeDetGrp property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProdCodeDetGrp().add(newItem);




     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProdCodeDetGrp }





     * 
     * 
     */
    public List<ProdCodeDetGrp> getProdCodeDetGrp() {
        if (prodCodeDetGrp == null) {
            prodCodeDetGrp = new ArrayList<ProdCodeDetGrp>();
        }
        return this.prodCodeDetGrp;
    }

}
