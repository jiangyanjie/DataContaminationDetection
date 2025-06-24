
package       DBFullReportsClient;

   import javax.xml.bind.annotation.XmlAccessType;
import    javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElemen   t;
import javax.xml.bind.annotation.XmlTy    pe;


/**
 * <p>Ja   va class for CustomerInformat     ionAdvancedType complex type.
 * 
 * <p>    The following schema fragment specifies the exp    ected content contai    ned withi n th is class   .
 * 
 * <pre>
 * &lt;complexType name     ="CustomerInformatio   nAdvancedType">
 *   &lt;complexContent     >
 *     &lt;restr    iction base="{h      ttp://www.w3.or   g/2001/XMLSc  hema}      anyType    ">
 *       &lt;seq        uenc        e>
 *         &lt;element name="Si     gnonCredentials" type="{http://gateway.dnb.c om/getProdu    ct} SignonCredentialsAdvance    dType"/>
 *            &lt;element name=   "EndUserRe     f" type="{http://gateway.dnb.com/getProduct     }EndUserRef     Type" minOccurs="      0"/>
  *       &lt;/    sequen  ce>
 *     &lt;/restr ictio  n>       
 *   &lt;/complexContent>
 * &lt;/co  mplexType>
 * </pre>
 * 
   * 
 */
@XmlAcc     essorType(XmlAccessType.    FIELD)
@X   mlType(name = "Cus    tomerI     nformationAdvancedType", propOr   der = {
           "sig   nonCredentials",
    "endUserRef"
})   
public class  Cu  stomerInformationAd  vancedType {
  
    @      XmlElement(name = "SignonCreden    t    ials", re   quired = tr   u  e)  
    p    rotected Sig nonCredential  sAdvanced    Ty  pe signonCredentials;  
      @XmlElement(name = "End UserRef")
        p   rote  cted Stri    ng endUserRef;

    /**
       *      G            ets   t   he value        o    f the     sig    no  nCreden tials property.      
     *      
     *   @return
      *     p  ossibl         e o    bje c   t is
     *     {@link Si   gnonCreden   ti alsAdvancedType }
            *     
                   */     
     pu   blic SignonCredentia  lsAdvancedType getSignonCredentials() {
                       retu rn signon   Cre  dentials;
    }

          /**
            * Sets       the    value     of the sign           onCred          e ntia                  ls p  ro    p     erty   .
                * 
      *   @param    value
     *     al   lowed object is
             *                 {    @   lin  k Signon  Cr e       d entialsA  dvancedT  ype }           
      *          
     */
                  public void setSignonCrede              ntials(  Sig  n  on Cre      d  en   tials   AdvancedType va   lue)      {
             th     is.  signonCredent  i        als = va   lue;
    }

    /**
     * Gets the   val  ue of th  e endUserRe    f property.
     * 
     * @ret           u       rn
     *         p  ossibl e objec       t is
     *     {@link String }
     *     
         */
    public    Stri      ng getEnd    UserRe  f  () {
        return endUserRef;
      }

    /**
         * Sets the value     of the endUserRef property.
     * 
     * @p   aram value
     *           allowed o  b     ject is
     *          {@link Str  ing }
     *          
     */
    public void setEndUserRef(String value) {
        this.endUserRef = value;
    }

}
